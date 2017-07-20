package com.git.opengds.editor.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.type.qa10.feature.QA10Feature;
import com.git.gdsbuilder.type.qa10.feature.QA10FeatureList;
import com.git.gdsbuilder.type.qa20.feature.QA20Feature;
import com.git.gdsbuilder.type.qa20.feature.QA20FeatureList;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.geoserver.service.GeoserverServiceImpl;
import com.git.opengds.parser.json.BuilderJSONParser;
import com.git.opengds.user.domain.UserVO;
import com.vividsolutions.jts.io.ParseException;

@Service
public class EditFeatureServiceImpl implements EditFeatureService {

	protected static final String none = "none";
	protected static final String isModified = "modified";
	protected static final String isCreated = "created";
	protected static final String isDeleted = "removed";

	protected static final String isNgi = "ngi";
	protected static final String isDxf = "dxf";
	protected static final String isShp = "shp";

	@Autowired
	EditDBManagerService editDBManager;

	@Autowired
	GeoserverService geoserver;

	String src = "5186";
	
	/*public EditFeatureServiceImpl(UserVO userVO) {
		// TODO Auto-generated constructor stub
		editDBManager = new EditDBManagerServiceImpl(userVO);
		geoserver = new GeoserverServiceImpl(userVO);
	}*/

	@Override
	public void editFeature(UserVO userVO, JSONObject featureEditObj) throws ParseException, org.json.simple.parser.ParseException {

		Map<String, Object> edtFeatureListObj = BuilderJSONParser.parseEditFeatureObj(featureEditObj);
		Iterator edtFeatureIterator = edtFeatureListObj.keySet().iterator();
		while (edtFeatureIterator.hasNext()) {
			String tableName = (String) edtFeatureIterator.next();
			HashMap<String, Object> editMap = (HashMap<String, Object>) edtFeatureListObj.get(tableName);
			String collectionType = BuilderJSONParser.getCollectionType(tableName);
			if (collectionType.equals(isDxf)) {
				editDxfFeature(userVO,tableName, editMap);
			}
			if (collectionType.equals(isNgi)) {
				editNgiFeature(userVO,tableName, editMap);
			}
		}
	}

	private void editNgiFeature(UserVO userVO, String tableName, HashMap<String, Object> editMap) {

		Iterator stataIterator = editMap.keySet().iterator();
		while (stataIterator.hasNext()) {
			String state = (String) stataIterator.next();
			if (state.equals(isCreated)) {
				QA20FeatureList createFeatureList = (QA20FeatureList) editMap.get(state);
				for (int i = 0; i < createFeatureList.size(); i++) {
					QA20Feature createFeature = createFeatureList.get(i);
					editDBManager.insertQA20CreateFeature(userVO,tableName, createFeature, src);
				}
			} else if (state.equals(isModified)) {
				QA20FeatureList modifyFeatureList = (QA20FeatureList) editMap.get(state);
				for (int i = 0; i < modifyFeatureList.size(); i++) {
					QA20Feature modifyFeature = modifyFeatureList.get(i);
					editDBManager.updateQA20ModifyFeature(userVO,tableName, modifyFeature, src);
				}
			} else if (state.equals(isDeleted)) {
				List<String> featureIdList = (List<String>) editMap.get(state);
				for (int i = 0; i < featureIdList.size(); i++) {
					String featureId = featureIdList.get(i);
					editDBManager.deleteQA20RemovedFeature(userVO,tableName, featureId);
				}
			}
		}
	}

	private void editDxfFeature(UserVO userVO, String tableName, HashMap<String, Object> editMap) {

		Iterator stataIterator = editMap.keySet().iterator();
		while (stataIterator.hasNext()) {
			String state = (String) stataIterator.next();
			if (state.equals(isCreated)) {
				QA10FeatureList createFeatureList = (QA10FeatureList) editMap.get(state);
				for (int i = 0; i < createFeatureList.size(); i++) {
					QA10Feature createFeature = createFeatureList.get(i);
					editDBManager.insertQA10CreateFeature(userVO,tableName, createFeature);
				}
			} else if (state.equals(isModified)) {
				QA10FeatureList modifyFeatureList = (QA10FeatureList) editMap.get(state);
				for (int i = 0; i < modifyFeatureList.size(); i++) {
					QA10Feature modifyFeature = modifyFeatureList.get(i);
					editDBManager.updateQA10ModifyFeature(userVO,tableName, modifyFeature);
				}
			} else if (state.equals(isDeleted)) {
				List<String> featureIdList = (List<String>) editMap.get(state);
				for (int i = 0; i < featureIdList.size(); i++) {
					String featureId = featureIdList.get(i);
					editDBManager.deleteQA10RemovedFeature(userVO,tableName, featureId);
				}
			}
		}
	}

}
