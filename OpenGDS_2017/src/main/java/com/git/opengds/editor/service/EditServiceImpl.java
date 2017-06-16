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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.type.qa10.feature.QA10Feature;
import com.git.gdsbuilder.type.qa10.feature.QA10FeatureList;
import com.git.gdsbuilder.type.qa20.feature.QA20Feature;
import com.git.gdsbuilder.type.qa20.feature.QA20FeatureList;
import com.git.opengds.file.ngi.service.QA20DBManagerService;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.parser.json.BuilderJSONParser;

@Service
public class EditServiceImpl implements EditService {

	protected static final String none = "none";
	protected static final String isModified = "modify";
	protected static final String isCreated = "create";
	protected static final String isDeleted = "remove";

	protected static final String isNgi = "ngi";
	protected static final String isDxf = "dxf";
	protected static final String isShp = "shp";

	@Inject
	EditDBManagerService editDBManager;

	@Inject
	QA20DBManagerService qa20DBManager;

	@Inject
	GeoserverService geoserver;

	@Override
	public void editLayerCollection(String editJSONStr) throws Exception {

		// 옵션 넘겨 받음
		// Object obj = parser.parse(new FileReader("D:\\editFinal.txt"));
		// JSONObject editJSONObject = (JSONObject) obj;

		JSONParser jsonParser = new JSONParser();
		JSONObject editJSONObject = (JSONObject) jsonParser.parse(editJSONStr);

		// layerEdit
		// JSONObject layerEditObj = (JSONObject) editJSONObject.get("layer");
		// Map<String, Object> edtCollectionListObj =
		// BuilderJSONParser.parseEditLayerObj(layerEditObj);
		// Iterator edtLayerIterator = edtCollectionListObj.keySet().iterator();
		// while (edtLayerIterator.hasNext()) {
		// String type = (String) edtLayerIterator.next();
		// EditQA20LayerCollectionList edtCollectionList =
		// (EditQA20LayerCollectionList) edtCollectionListObj
		// .get(type);
		// if (type.equals(this.isNgi)) {
		// for (int i = 0; i < edtCollectionList.size(); i++) {
		// EditQA20Collection editCollection = edtCollectionList.get(i);
		// String collectionName = editCollection.getCollectionName();
		// if (editCollection.isCreated()) {
		// // collection 중복 여부 확인
		// Integer collectionIdx =
		// editDBManager.checkCollectionName(collectionName);
		// if (collectionIdx != null) {
		// // 1. 중복되었을 시(이미 존재하는 collection에 레이어 테이블만 create)
		// editDBManager.createQa20Layers(type, collectionIdx, editCollection);
		// } else {
		// // 2. 중복되지 않았을 시 collection insert 후 레이어 테이블 create
		// editDBManager.createQa20LayerCollection(type, editCollection);
		// }
		// }
		// if (editCollection.isModified()) {
		//
		// }
		// if (editCollection.isDeleted()) {
		// List<String> deletedLayerNames =
		// editDBManager.dropQa20LayerCollection(type, editCollection);
		// geoserver.removeGeoserverLayers(deletedLayerNames);
		// if (editCollection.isDeleteAll()) {
		// geoserver.removeGeoserverGroupLayer(editCollection.getCollectionName());
		// }
		// }
		// }
		// } else if (type.equals(this.isDxf)) {
		//
		// } else if (type.equals(this.isShp)) {
		//
		// }
		// }

		System.out.println("");

		// featureEdit
		JSONObject featureEditObj = (JSONObject) editJSONObject.get("feature");
		Map<String, Object> edtFeatureListObj = BuilderJSONParser.parseEditFeatureObj(featureEditObj);
		Iterator edtFeatureIterator = edtFeatureListObj.keySet().iterator();
		while (edtFeatureIterator.hasNext()) {
			String tableName = (String) edtFeatureIterator.next();
			HashMap<String, Object> editMap = (HashMap<String, Object>) edtFeatureListObj.get(tableName);
			String collectionType = BuilderJSONParser.getCollectionType(tableName);

			if (collectionType.equals("dxf")) {
				editDxfFeature(tableName, editMap);
			}

			if (collectionType.equals("ngi")) {
				editNgiFeature(tableName, editMap);
			}
		}
		System.out.println("");
	}

	private void editNgiFeature(String tableName, HashMap<String, Object> editMap) {

		Iterator stataIterator = editMap.keySet().iterator();
		while (stataIterator.hasNext()) {
			String state = (String) stataIterator.next();
			if (state.equals("created")) {
				QA20FeatureList createFeatureList = (QA20FeatureList) editMap.get(state);
				for (int i = 0; i < createFeatureList.size(); i++) {
					QA20Feature createFeature = createFeatureList.get(i);
					editDBManager.insertQA20CreateFeature(tableName, createFeature);
				}
			} else if (state.equals("modified")) {
				QA20FeatureList modifyFeatureList = (QA20FeatureList) editMap.get(state);
				for (int i = 0; i < modifyFeatureList.size(); i++) {
					QA20Feature modifyFeature = modifyFeatureList.get(i);
					editDBManager.updateQA20ModifyFeature(tableName, modifyFeature);
				}
			} else if (state.equals("removed")) {
				List<String> featureIdList = (List<String>) editMap.get(state);
				for (int i = 0; i < featureIdList.size(); i++) {
					String featureId = featureIdList.get(i);
					editDBManager.deleteQA20RemovedFeature(tableName, featureId);
				}
			}
		}
	}

	private void editDxfFeature(String tableName, HashMap<String, Object> editMap) {

		Iterator stataIterator = editMap.keySet().iterator();
		while (stataIterator.hasNext()) {
			String state = (String) stataIterator.next();
			if (state.equals("created")) {
				QA10FeatureList createFeatureList = (QA10FeatureList) editMap.get(state);
				for (int i = 0; i < createFeatureList.size(); i++) {
					QA10Feature createFeature = createFeatureList.get(i);
					editDBManager.insertQA10CreateFeature(tableName, createFeature);
				}
			} else if (state.equals("modified")) {
				QA10FeatureList modifyFeatureList = (QA10FeatureList) editMap.get(state);
				for (int i = 0; i < modifyFeatureList.size(); i++) {
					QA10Feature modifyFeature = modifyFeatureList.get(i);
					editDBManager.updateQA10ModifyFeature(tableName, modifyFeature);
				}
			} else if (state.equals("removed")) {
				List<String> featureIdList = (List<String>) editMap.get(state);
				for (int i = 0; i < featureIdList.size(); i++) {
					String featureId = featureIdList.get(i);
					editDBManager.deleteQA10RemovedFeature(tableName, featureId);
				}
			}
		}
	}
}
