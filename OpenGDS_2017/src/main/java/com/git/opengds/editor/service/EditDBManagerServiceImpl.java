/*
 *    OpenGDS/Builder
 *    http://git.co.kr
 *
 *    (C) 2014-2017, GeoSpatial Information Technology(GIT)
 *    
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 3 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */

package com.git.opengds.editor.service;

import java.net.MalformedURLException;
import java.util.HashMap;

import javax.inject.Inject;

import org.opengis.feature.simple.SimpleFeature;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.gdsbuilder.edit.shp.EditSHPLayerCollection;
import com.git.gdsbuilder.type.shp.collection.DTSHPLayerCollection;
import com.git.gdsbuilder.type.shp.layer.DTSHPLayer;
import com.git.gdsbuilder.type.shp.layer.DTSHPLayerList;
import com.git.opengds.file.shp.dbManager.SHPDBQueryManager;
import com.git.opengds.file.shp.persistence.SHPLayerCollectionDAO;
import com.git.opengds.user.domain.UserVO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class EditDBManagerServiceImpl implements EditDBManagerService {

	@Inject
	private SHPLayerCollectionDAO shpDAO;

	@Override
	public void insertSHPCreateFeature(UserVO userVO, String tableName, SimpleFeature createFeature, String src) {
		SHPDBQueryManager queryManager = new SHPDBQueryManager();
		HashMap<String, Object> insertLayerQuery = queryManager.getInsertSHPFeatureQuery(tableName, createFeature, src);
		shpDAO.insertSHPLayer(userVO, insertLayerQuery);
	}

	@Override
	public void deleteSHPRemovedFeature(UserVO userVO, String tableName, String featureId) {

		SHPDBQueryManager queryManager = new SHPDBQueryManager();
		HashMap<String, Object> selectIdxqueryMap = queryManager.getSelectSHPFeatureIdxQuery(tableName, featureId);
		int fIdx = shpDAO.selectSHPFeatureIdx(userVO, selectIdxqueryMap);
		HashMap<String, Object> deleteFeatureMap = queryManager.getDeleteSHPFeatureQuery(tableName, fIdx);
		shpDAO.deleteSHPFeature(userVO, deleteFeatureMap);
	}

	@Override
	public void updateSHPModifyFeature(UserVO userVO, String tableName, SimpleFeature modifiedFeature, String src) {

		SHPDBQueryManager queryManager = new SHPDBQueryManager();
		// 1. featureID 조회
		String featureID = modifiedFeature.getID();
		HashMap<String, Object> selectIdxqueryMap = queryManager.getSelectSHPFeatureIdxQuery(tableName, featureID);
		int fIdx = shpDAO.selectSHPFeatureIdx(userVO, selectIdxqueryMap);

		// 2. 해당 feature 삭제
		HashMap<String, Object> deleteFeatureMap = queryManager.getDeleteSHPFeatureQuery(tableName, fIdx);
		shpDAO.deleteSHPFeature(userVO, deleteFeatureMap);

		// 3. 다시 insert
		HashMap<String, Object> insertFeatureMap = queryManager.getInsertSHPFeatureQuery(tableName, modifiedFeature,
				src);
		shpDAO.insertSHPLayer(userVO, insertFeatureMap);
	}

	@Override
	public Integer selectSHPLayerCollectionIdx(UserVO userVO, String collectionName) {
		SHPDBQueryManager queryManager = new SHPDBQueryManager();
		HashMap<String, Object> queryMap = queryManager.getSelectSHPLayerCollectionIdx(collectionName);
		Integer cIdx = shpDAO.selectSHPLayerCollectionIdx(userVO, queryMap);
		if (cIdx == null) {
			return null;
		} else {
			return cIdx;
		}
	}

	@Override
	public boolean dropSHPLayer(UserVO userVO, String type, Integer collectionIdx, String collectionName,
			DTSHPLayer layer) {

		try {
			boolean isSuccessed = false;

			SHPDBQueryManager queryManager = new SHPDBQueryManager();
			String layerName = layer.getLayerName();

			HashMap<String, Object> metadataIdxQuery = queryManager.getSelectSHPLayerMetaDataIdxQuery(collectionIdx,
					layerName);
			Integer mIdx = shpDAO.selectSHPLayerMetadataIdx(userVO, metadataIdxQuery);

			// layerMetadata 삭제
			HashMap<String, Object> deleteLayerMetaQuery = queryManager.getDeleteSHPLayerMetaQuery(mIdx);
			shpDAO.deleteSHPLayerMetadata(userVO, deleteLayerMetaQuery);

			HashMap<String, Object> dropQuery = queryManager.getDropSHPLayerQuery(type, collectionName, layerName);
			shpDAO.dropSHPLayer(userVO, dropQuery);
		} catch (RuntimeException e) {
			throw new RuntimeException();
		}
		return true;
	}

	@Override
	public void deleteSHPLayerCollection(UserVO userVO, Integer cIdx) {

		SHPDBQueryManager queryManager = new SHPDBQueryManager();
		HashMap<String, Object> deleteLayerCollectionQuery = queryManager.getDeleteSHPLayerCollectionQuery(cIdx);
		shpDAO.deleteSHPLayerCollection(userVO, deleteLayerCollectionQuery);
	}

	@Override
	public boolean createSHPLayer(UserVO userVO, String type, Integer cIdx, String collectionName,
			DTSHPLayer createLayer, String src) throws MalformedURLException {

		try {
			SHPDBQueryManager queryManager = new SHPDBQueryManager();

			// createQA20Layer
			HashMap<String, Object> createQuery = queryManager.getSHPLayerTbCreateQuery(type, collectionName,
					createLayer, src);
			shpDAO.createSHPLayerTb(userVO, createQuery);

			// insertLayerMedata
			HashMap<String, Object> insertQueryMap = queryManager.getSHPLayerMetaInertQuery(type, collectionName,
					createLayer, cIdx);
			shpDAO.insertSHPLayerMetadata(userVO, insertQueryMap);
		} catch (RuntimeException e) {
			throw new RuntimeException();
		}
		return true;
	}

	@Override
	public Integer createSHPLayerCollection(UserVO userVO, String isdxf, EditSHPLayerCollection editCollection) {

		String collectionName = editCollection.getCollectionName();

		DTSHPLayerList createLayerList = editCollection.getCreateLayerList();
		DTSHPLayerCollection createCollection = new DTSHPLayerCollection();
		createCollection.setCollectionName(collectionName);
		createCollection.setShpLayerList(createLayerList);

		SHPDBQueryManager queryManager = new SHPDBQueryManager();
		HashMap<String, Object> insertCollectionQuery = queryManager.getInsertSHPLayerCollectionQuery(collectionName);
		int cIdx = shpDAO.insertSHPLayerCollection(userVO, insertCollectionQuery);
		return cIdx;
	}
}
