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
import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollectionList;
import com.git.gdsbuilder.type.geoserver.parser.GeoLayerCollectionParser;
import com.git.gdsbuilder.type.qa20.feature.QA20Feature;
import com.git.gdsbuilder.type.qa20.feature.QA20FeatureList;
import com.git.gdsbuilder.type.validate.layer.ValidateLayerTypeList;
import com.git.opengds.parser.edit.feature.EditFeatureParser;
import com.git.opengds.parser.edit.layer.EditLayerCollectionListParser;
import com.git.opengds.parser.validate.ValidateTypeParser;

/**
 * ValidateJSON을 파싱하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 4:08:24
 */
public class BuilderJSONParser {

	/**
	 * JSONObject를 ValidateLayerTypeList, LayerCollectionList로 파싱 @author
	 * DY.Oh @Date 2017. 4. 18. 오후 4:08:26 @param j @return
	 * HashMap<String,Object> @throws FileNotFoundException @throws
	 * IOException @throws ParseException @throws SchemaException @throws
	 */
	public static HashMap<String, Object> parseValidateObj(String j)
			throws FileNotFoundException, IOException, ParseException, SchemaException {

		HashMap<String, Object> validateMap = new HashMap<String, Object>();

		JSONParser parser = new JSONParser();
		JSONObject jsonObj = (JSONObject) parser.parse(j);

		// 타입검수 파싱
		JSONArray typeValidates = (JSONArray) jsonObj.get("typeValidate");
		ValidateTypeParser validateTypeParser = new ValidateTypeParser(typeValidates);
		ValidateLayerTypeList validateLayerTypeList = validateTypeParser.getValidateLayerTypeList();

		// 도엽들 파싱
		JSONObject layerCollections = (JSONObject) jsonObj.get("layerCollections");
		GeoLayerCollectionParser collectionParser = new GeoLayerCollectionParser(layerCollections);
		GeoLayerCollectionList collectionList = collectionParser.getLayerCollections();
		if (collectionList.size() == 0 && validateLayerTypeList.size() == 0) {
			return null;
		} else {
			validateMap.put("typeValidate", validateLayerTypeList);
			validateMap.put("collectionList", collectionList);
			return validateMap;
		}
	}

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
			EditQA20LayerCollectionList edtCollectionList = editLayerCollectionListParser.getEdtCollectionList();
			editLayerListMap.put(type, edtCollectionList);
		}
		return editLayerListMap;
	}

	public static Map<String, Object> parseEditFeatureObj(JSONObject editFeatureObj)
			throws com.vividsolutions.jts.io.ParseException {
		// feature 편집
		Map<String, Object> editFeatureListMap = new HashMap<String, Object>();
		Iterator featureIterator = editFeatureObj.keySet().iterator();
		while (featureIterator.hasNext()) {
			String layerName = (String) featureIterator.next();
			Map<String, Object> editFeatureMap = new HashMap<String, Object>();
			JSONObject stateObj = (JSONObject) editFeatureObj.get(layerName);
			Iterator stateIterator = stateObj.keySet().iterator();
			while (stateIterator.hasNext()) {
				String state = (String) stateIterator.next();
				if (state.equals("created")) {
					QA20FeatureList featureList = new QA20FeatureList();
					JSONObject featuresObj = (JSONObject) stateObj.get(state);
					JSONArray featuresArry = (JSONArray) featuresObj.get("features");
					for (int i = 0; i < featuresArry.size(); i++) {
						JSONObject featureObj = (JSONObject) featuresArry.get(i);
						EditFeatureParser featureParser = new EditFeatureParser(featureObj, state);
						QA20Feature feature = featureParser.getQa20Feature();
						featureList.add(feature);
					}
					editFeatureMap.put("created", featureList);
				} else if (state.equals("modified")) {
					QA20FeatureList featureList = new QA20FeatureList();
					JSONObject featuresObj = (JSONObject) stateObj.get(state);
					JSONArray featuresArry = (JSONArray) featuresObj.get("features");
					for (int i = 0; i < featuresArry.size(); i++) {
						JSONObject featureObj = (JSONObject) featuresArry.get(i);
						EditFeatureParser featureParser = new EditFeatureParser(featureObj, state);
						QA20Feature feature = featureParser.getQa20Feature();
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
			editFeatureListMap.put(layerName, editFeatureMap);
		}
		return editFeatureListMap;
	}
}
