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

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.postgresql.util.PSQLException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.gdsbuilder.edit.qa20.EditQA20Collection;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.qa20.collection.QA20LayerCollection;
import com.git.gdsbuilder.type.qa20.feature.QA20Feature;
import com.git.gdsbuilder.type.qa20.header.NDAHeader;
import com.git.gdsbuilder.type.qa20.header.NGIHeader;
import com.git.gdsbuilder.type.qa20.layer.QA20Layer;
import com.git.gdsbuilder.type.qa20.layer.QA20LayerList;
import com.git.opengds.editor.dbManager.EditLayerDBQueryManager;
import com.git.opengds.file.ngi.persistence.QA20LayerCollectionDAO;
import com.git.opengds.file.ngi.service.QA20DBManagerService;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class EditDBManagerServiceImpl implements EditDBManagerService {

	@Inject
	private DataSourceTransactionManager txManager;

	@Inject
	private QA20LayerCollectionDAO dao;

	@Inject
	private QA20DBManagerService qa20DBManager;

	public Integer checkCollectionName(String collectionName) {

		EditLayerDBQueryManager queryManager = new EditLayerDBQueryManager();
		HashMap<String, Object> queryMap = queryManager.getSelectLayerCollectionIdx(collectionName);
		HashMap<String, Object> returnMap = dao.selectQA20LayerCollectionIdx(queryMap);
		if (returnMap == null) {
			return null;
		} else {
			return (Integer) returnMap.get("c_idx");
		}
	}

	@Override
	public void createQa20LayerCollection(String type, EditQA20Collection editCollection) throws Exception {

		String collectionName = editCollection.getCollectionName();

		// create GeoLayerInfo
		GeoLayerInfo layerInfo = new GeoLayerInfo();
		layerInfo.setFileType(type);
		layerInfo.setFileName(collectionName);
		layerInfo.setOriginSrc("EPSG:5186");
		layerInfo.setTransSrc("EPSG:3857");

		// get createdCollectionInfo
		QA20LayerList createLayerList = editCollection.getCreateLayerList();
		QA20LayerCollection createCollection = new QA20LayerCollection();
		createCollection.setFileName(collectionName);
		createCollection.setQa20LayerList(createLayerList);
		// input DB layer
		GeoLayerInfo returnInfo = qa20DBManager.insertQA20LayerCollection(createCollection, layerInfo);
		System.out.println("DB성공");

	}

	@Override
	public void createQa20Layers(String type, Integer idx, EditQA20Collection editCollection) throws PSQLException {

		String collectionName = editCollection.getCollectionName();

		// create GeoLayerInfo
		GeoLayerInfo layerInfo = new GeoLayerInfo();
		layerInfo.setFileType(type);
		layerInfo.setFileName(collectionName);
		layerInfo.setOriginSrc("EPSG:5186");
		layerInfo.setTransSrc("EPSG:3857");

		QA20LayerList createLayerList = editCollection.getCreateLayerList();
		EditLayerDBQueryManager queryManager = new EditLayerDBQueryManager();
		for (int i = 0; i < createLayerList.size(); i++) {
			QA20Layer qa20Layer = createLayerList.get(i);
			
			// createQA20Layer
			HashMap<String, Object> createQuery = queryManager.qa20LayerTbCreateQuery(type, collectionName, qa20Layer);
			dao.createQA20LayerTb(createQuery);

			// insertLayerMedata
			HashMap<String, Object> insertQueryMap = queryManager.getInsertLayerMeataData(type, collectionName, idx,
					qa20Layer);
			int lmIdx = dao.insertQA20LayerMetadataEdit(insertQueryMap);

			NDAHeader ndaHeader = qa20Layer.getNdaHeader();
			// aspatial_field_def
			List<HashMap<String, Object>> fieldDefs = queryManager.getAspatialFieldDefs(lmIdx, ndaHeader);
			if (fieldDefs != null) {
				for (int j = 0; j < fieldDefs.size(); j++) {
					dao.insertNdaAspatialFieldDefsEdit(fieldDefs.get(j));
				}
			}
			NGIHeader ngiHeader = qa20Layer.getNgiHeader();
			// point_represent
			List<HashMap<String, Object>> ptReps = queryManager.getPtRepresent(lmIdx, ngiHeader.getPoint_represent());
			if (ptReps != null) {
				for (int j = 0; j < ptReps.size(); j++) {
					dao.insertPointRepresentEdit(ptReps.get(j));
				}
			}
			// lineString_represent
			List<HashMap<String, Object>> lnReps = queryManager.getLnRepresent(lmIdx, ngiHeader.getLine_represent());
			if (lnReps != null) {
				for (int j = 0; j < lnReps.size(); j++) {
					dao.insertLineStringRepresentEdit(lnReps.get(j));
				}
			}
			// region_represent
			List<HashMap<String, Object>> rgReps = queryManager.getRgRepresent(lmIdx, ngiHeader.getRegion_represent());
			if (rgReps != null) {
				for (int j = 0; j < rgReps.size(); j++) {
					dao.insertRegionRepresentEdit(rgReps.get(j));
				}
			}
			// text_represent
			List<HashMap<String, Object>> txtReps = queryManager.getTxtRepresent(lmIdx, ngiHeader.getText_represent());
			if (txtReps != null) {
				for (int j = 0; j < txtReps.size(); j++) {
					dao.insertTextRepresentEdit(txtReps.get(j));
				}
			}
		}
	}

	@Override
	public void insertCreateFeature(String layerName, QA20Feature createFeature) {
		
		EditLayerDBQueryManager queryManager = new EditLayerDBQueryManager();
		//queryManager.getInertFeatureQuery(layerName, createFeature);
	}
}
