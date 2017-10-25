package com.git.opengds.editor.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONObject;
import org.opengis.feature.simple.SimpleFeature;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.gdsbuilder.type.shp.feature.DTSHPFeatureList;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.parser.json.BuilderJSONParser;
import com.git.opengds.user.domain.UserVO;
import com.vividsolutions.jts.io.ParseException;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class EditDTFeatureServiceImpl implements EditDTFeatureService {

	protected static final String none = "none";
	protected static final String isModified = "modified";
	protected static final String isCreated = "created";
	protected static final String isDeleted = "removed";

	protected static final String isShp = "shp";

	@Inject
	EditDBManagerService editDBManager;

	@Inject
	GeoserverService geoserver;

	String src = "5186";

	@Override
	public boolean editFeature(UserVO userVO, JSONObject featureEditObj) throws ParseException,
			org.json.simple.parser.ParseException, SchemaException, FileNotFoundException, IOException {

		boolean isSuccessed = false;

		Map<String, Object> edtFeatureListObj = BuilderJSONParser.parseEditFeatureObj(featureEditObj);
		Iterator edtFeatureIterator = edtFeatureListObj.keySet().iterator();
		while (edtFeatureIterator.hasNext()) {
			String tableName = (String) edtFeatureIterator.next();
			HashMap<String, Object> editMap = (HashMap<String, Object>) edtFeatureListObj.get(tableName);
			String collectionType = BuilderJSONParser.getCollectionType(tableName);
			if (collectionType.equals(isShp)) {
				isSuccessed = editShpFeature(userVO, tableName, editMap);
			}
		}
		return isSuccessed;
	}

	private boolean editShpFeature(UserVO userVO, String tableName, HashMap<String, Object> editMap) {

		try {
			Iterator stataIterator = editMap.keySet().iterator();
			while (stataIterator.hasNext()) {
				String state = (String) stataIterator.next();
				if (state.equals(isCreated)) {
					DTSHPFeatureList createFeatureList = (DTSHPFeatureList) editMap.get(state);
					for (int i = 0; i < createFeatureList.size(); i++) {
						SimpleFeature createFeature = createFeatureList.get(i);
						editDBManager.insertSHPCreateFeature(userVO, tableName, createFeature, src);
					}
				} else if (state.equals(isModified)) {
					DTSHPFeatureList modifiedFeatureList = (DTSHPFeatureList) editMap.get(state);
					for (int i = 0; i < modifiedFeatureList.size(); i++) {
						SimpleFeature modifiedFeature = modifiedFeatureList.get(i);
						editDBManager.updateSHPModifyFeature(userVO, tableName, modifiedFeature, src);
					}
				} else if (state.equals(isDeleted)) {
					List<String> featureIdList = (List<String>) editMap.get(state);
					for (int i = 0; i < featureIdList.size(); i++) {
						String featureId = featureIdList.get(i);
						editDBManager.deleteSHPRemovedFeature(userVO, tableName, featureId);
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
