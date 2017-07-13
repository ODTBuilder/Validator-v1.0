package com.git.opengds.parser.json;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.git.gdsbuilder.edit.qa10.EditQA10LayerCollectionList;
import com.git.gdsbuilder.type.qa10.feature.QA10Feature;
import com.git.gdsbuilder.type.qa10.feature.QA10FeatureList;
import com.git.opengds.parser.edit.feature.EditFeatureParser;
import com.git.opengds.parser.edit.layer.EditLayerCollectionListParser;

public class BuilderJSONQA10Parser {

	public static EditQA10LayerCollectionList parseEditLayerObj(JSONObject editLayerObj, String type)
			throws FileNotFoundException, IOException, com.vividsolutions.jts.io.ParseException, SchemaException {

		// layer 편집
		JSONObject collectionListObj = (JSONObject) editLayerObj.get(type);
		EditLayerCollectionListParser editLayerCollectionListParser = new EditLayerCollectionListParser(type,
				collectionListObj);
		EditQA10LayerCollectionList edtCollectionList = editLayerCollectionListParser.getEdtQA10CollectionList();
		return edtCollectionList;
	}

	public static Map<String, Object> parseDXFFeature(JSONObject stateObj, String layerType)
			throws ParseException, com.vividsolutions.jts.io.ParseException {

		JSONParser jsonParser = new JSONParser();

		Map<String, Object> editFeatureMap = new HashMap<String, Object>();
		Iterator stateIterator = stateObj.keySet().iterator();
		while (stateIterator.hasNext()) {
			String state = (String) stateIterator.next();
			if (state.equals("created")) {
				QA10FeatureList featureList = new QA10FeatureList();
				JSONObject featuresObj = (JSONObject) stateObj.get(state);
				JSONArray featuresArry = (JSONArray) featuresObj.get("features");
				for (int i = 0; i < featuresArry.size(); i++) {
					String geoStr = (String) featuresArry.get(i);
					JSONObject featureObj = (JSONObject) jsonParser.parse(geoStr);
					EditFeatureParser featureParser = new EditFeatureParser("dxf", featureObj, state);
					QA10Feature feature = featureParser.getQa10Feature();
					feature.setFeatureType(layerType);
					featureList.add(feature);
				}
				editFeatureMap.put("created", featureList);
			} else if (state.equals("modified")) {
				QA10FeatureList featureList = new QA10FeatureList();
				JSONObject featuresObj = (JSONObject) stateObj.get(state);
				JSONArray featuresArry = (JSONArray) featuresObj.get("features");
				for (int i = 0; i < featuresArry.size(); i++) {
					String geoStr = (String) featuresArry.get(i);
					JSONObject featureObj = (JSONObject) jsonParser.parse(geoStr);
					EditFeatureParser featureParser = new EditFeatureParser("dxf", featureObj, state);
					QA10Feature feature = featureParser.getQa10Feature();
					feature.setFeatureType(layerType);
					featureList.add(feature);
				}
				editFeatureMap.put("modified", featureList);
			} else if (state.equals("removed")) {
				List<String> featureIdList = new ArrayList<String>();
				JSONArray featuresArr = (JSONArray) stateObj.get(state);
				for (int i = 0; i < featuresArr.size(); i++) {
					String featureId = (String) featuresArr.get(i);
					featureIdList.add(featureId);
				}
				editFeatureMap.put("removed", featureIdList);
			}
		}
		return editFeatureMap;
	}
}
