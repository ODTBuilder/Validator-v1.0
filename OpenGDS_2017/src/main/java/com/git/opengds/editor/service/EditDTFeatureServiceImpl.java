package com.git.opengds.editor.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.type.dxf.feature.DTDXFFeature;
import com.git.gdsbuilder.type.dxf.feature.DTDXFFeatureList;
import com.git.gdsbuilder.type.ngi.feature.DTNGIFeature;
import com.git.gdsbuilder.type.ngi.feature.DTNGIFeatureList;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.parser.json.BuilderJSONParser;
import com.git.opengds.user.domain.UserVO;
import com.vividsolutions.jts.io.ParseException;

@Service
public class EditDTFeatureServiceImpl implements EditDTFeatureService {

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
				DTNGIFeatureList createFeatureList = (DTNGIFeatureList) editMap.get(state);
				for (int i = 0; i < createFeatureList.size(); i++) {
					DTNGIFeature createFeature = createFeatureList.get(i);
					editDBManager.insertNGICreateFeature(userVO,tableName, createFeature, src);
				}
			} else if (state.equals(isModified)) {
				DTNGIFeatureList modifyFeatureList = (DTNGIFeatureList) editMap.get(state);
				for (int i = 0; i < modifyFeatureList.size(); i++) {
					DTNGIFeature modifyFeature = modifyFeatureList.get(i);
					editDBManager.updateNGIModifyFeature(userVO,tableName, modifyFeature, src);
				}
			} else if (state.equals(isDeleted)) {
				List<String> featureIdList = (List<String>) editMap.get(state);
				for (int i = 0; i < featureIdList.size(); i++) {
					String featureId = featureIdList.get(i);
					editDBManager.deleteNGIRemovedFeature(userVO,tableName, featureId);
				}
			}
		}
	}

	private void editDxfFeature(UserVO userVO, String tableName, HashMap<String, Object> editMap) {

		Iterator stataIterator = editMap.keySet().iterator();
		while (stataIterator.hasNext()) {
			String state = (String) stataIterator.next();
			if (state.equals(isCreated)) {
				DTDXFFeatureList createFeatureList = (DTDXFFeatureList) editMap.get(state);
				for (int i = 0; i < createFeatureList.size(); i++) {
					DTDXFFeature createFeature = createFeatureList.get(i);
					editDBManager.insertDXFCreateFeature(userVO,tableName, createFeature);
				}
			} else if (state.equals(isModified)) {
				DTDXFFeatureList modifyFeatureList = (DTDXFFeatureList) editMap.get(state);
				for (int i = 0; i < modifyFeatureList.size(); i++) {
					DTDXFFeature modifyFeature = modifyFeatureList.get(i);
					editDBManager.updateDXFModifyFeature(userVO,tableName, modifyFeature);
				}
			} else if (state.equals(isDeleted)) {
				List<String> featureIdList = (List<String>) editMap.get(state);
				for (int i = 0; i < featureIdList.size(); i++) {
					String featureId = featureIdList.get(i);
					editDBManager.deleteDXFRemovedFeature(userVO,tableName, featureId);
				}
			}
		}
	}

}
