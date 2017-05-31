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

import javax.inject.Inject;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.gdsbuilder.edit.qa20.EditQA20Collection;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.qa20.collection.QA20LayerCollection;
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

}
