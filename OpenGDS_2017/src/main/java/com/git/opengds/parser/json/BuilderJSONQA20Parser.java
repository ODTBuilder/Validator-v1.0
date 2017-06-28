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

/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2012, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */

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

import com.git.gdsbuilder.edit.qa20.EditQA20LayerCollectionList;
import com.git.gdsbuilder.type.qa10.feature.QA10Feature;
import com.git.gdsbuilder.type.qa10.feature.QA10FeatureList;
import com.git.gdsbuilder.type.qa20.feature.QA20Feature;
import com.git.gdsbuilder.type.qa20.feature.QA20FeatureList;
import com.git.opengds.parser.edit.feature.EditFeatureParser;
import com.git.opengds.parser.edit.layer.EditLayerCollectionListParser;

/**
 * ValidateJSON을 파싱하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 4:08:24
 */
public class BuilderJSONQA20Parser {
	
	public static Map<String, Object> parseEditLayerObj(JSONObject editLayerObj)
			throws FileNotFoundException, IOException, com.vividsolutions.jts.io.ParseException, SchemaException {

		// layer 편집
		Map<String, Object> editLayerListMap = new HashMap<String, Object>();
		Iterator layerIterator = editLayerObj.keySet().iterator();
		while (layerIterator.hasNext()) {
			String type = (String) layerIterator.next();
			JSONObject collectionListObj = (JSONObject) editLayerObj.get(type);
			EditLayerCollectionListParser editLayerCollectionListParser = new EditLayerCollectionListParser(type,
					collectionListObj);
			EditQA20LayerCollectionList edtCollectionList = editLayerCollectionListParser.getEdtQA20CollectionList();
			editLayerListMap.put(type, edtCollectionList);
		}
		return editLayerListMap;
	}

	public static Map<String, Object> parseEditFeatureObj(JSONObject editFeatureObj)
			throws com.vividsolutions.jts.io.ParseException, ParseException {

		// feature 편집
		Map<String, Object> editFeatureListMap = new HashMap<String, Object>();
		Iterator featureIterator = editFeatureObj.keySet().iterator();
		while (featureIterator.hasNext()) {
			String tableName = (String) featureIterator.next();
			String collectionType = getCollectionType(tableName);
			String layerType = getLayerType(tableName);
			JSONObject stateObj = (JSONObject) editFeatureObj.get(tableName);
			Map<String, Object> editFeatureMap = new HashMap<String, Object>();
			if (collectionType.equals("ngi")) {
				editFeatureMap = parseNGIFeature(stateObj, layerType);
			}
			if (collectionType.equals("dxf")) {
				editFeatureMap = parseDXFFeature(stateObj, layerType);
			}
			editFeatureListMap.put(tableName, editFeatureMap);
		}
		return editFeatureListMap;
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

	public static Map<String, Object> parseNGIFeature(JSONObject stateObj, String layerType)
			throws ParseException, com.vividsolutions.jts.io.ParseException {

		JSONParser jsonParser = new JSONParser();

		Map<String, Object> editFeatureMap = new HashMap<String, Object>();
		Iterator stateIterator = stateObj.keySet().iterator();
		while (stateIterator.hasNext()) {
			String state = (String) stateIterator.next();
			if (state.equals("created")) {
				QA20FeatureList featureList = new QA20FeatureList();
				JSONObject featuresObj = (JSONObject) stateObj.get(state);
				JSONArray featuresArry = (JSONArray) featuresObj.get("features");
				for (int i = 0; i < featuresArry.size(); i++) {
					String geoStr = (String) featuresArry.get(i);
					JSONObject featureObj = (JSONObject) jsonParser.parse(geoStr);
					EditFeatureParser featureParser = new EditFeatureParser("ngi", featureObj, state);
					QA20Feature feature = featureParser.getQa20Feature();
					feature.setFeatureType(layerType);
					featureList.add(feature);
				}
				editFeatureMap.put("created", featureList);
			} else if (state.equals("modified")) {
				QA20FeatureList featureList = new QA20FeatureList();
				JSONObject featuresObj = (JSONObject) stateObj.get(state);
				JSONArray featuresArry = (JSONArray) featuresObj.get("features");
				for (int i = 0; i < featuresArry.size(); i++) {
					String geoStr = (String) featuresArry.get(i);
					JSONObject featureObj = (JSONObject) jsonParser.parse(geoStr);
					EditFeatureParser featureParser = new EditFeatureParser("ngi", featureObj, state);
					QA20Feature feature = featureParser.getQa20Feature();
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

	public static String getCollectionType(String layerName) {

		int firstIndex = layerName.indexOf("_");
		String tempStr = layerName.substring(firstIndex + 1);
		int lastIndex = tempStr.indexOf("_");
		String layerType = tempStr.substring(0, lastIndex);

		return layerType;
	}

	public static String getLayerType(String layerName) {

		int firstIndex = layerName.lastIndexOf("_");
		String layerType = layerName.substring(firstIndex + 1, layerName.length() - 1);

		return layerType;
	}
}
