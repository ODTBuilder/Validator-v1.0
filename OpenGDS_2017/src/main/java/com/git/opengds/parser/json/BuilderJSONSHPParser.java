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
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.edit.shp.EditSHPLayerCollectionList;
import com.git.gdsbuilder.type.shp.feature.DTSHPFeatureList;
import com.git.opengds.parser.edit.feature.EditDTFeatureParser;
import com.git.opengds.parser.edit.layer.EditDTLayerCollectionListParser;

/**
 * 편집 레이어 정보 JSONObject를 EditSHPLayerCollectionList로 파싱하는 클래스. Client 파라미터 파싱
 * 
 * @author GIT
 *
 */
public class BuilderJSONSHPParser {

	/**
	 * 편집 레이어 정보 JSONObject를 EditSHPLayerCollectionList로 파싱
	 * 
	 * @param editLayerObj
	 *            편집 레이어 정보 JSONObject
	 * @param type
	 *            파일 타입
	 * @return EditSHPLayerCollectionList
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws com.vividsolutions.jts.io.ParseException
	 * @throws SchemaException
	 */
	public static EditSHPLayerCollectionList parseEditLayerObj(JSONObject editLayerObj, String type)
			throws FileNotFoundException, IOException, com.vividsolutions.jts.io.ParseException, SchemaException {

		// layer 편집
		JSONObject collectionListObj = (JSONObject) editLayerObj.get(type);
		EditDTLayerCollectionListParser editLayerCollectionListParser = new EditDTLayerCollectionListParser(type,
				collectionListObj);
		EditSHPLayerCollectionList edtCollectionList = editLayerCollectionListParser.getEdtSHPCollectionList();
		return edtCollectionList;
	}

	/**
	 * 편집 피처 정보 JSONObject를 Map 객체로 파싱
	 * 
	 * @param stateObj
	 *            편집 상태(created, modified, deleted)
	 * @param layerType
	 *            레이어 타입
	 * @return Map<String, Object>
	 * @throws ParseException
	 * @throws com.vividsolutions.jts.io.ParseException
	 * @throws SchemaException
	 */
	public static Map<String, Object> parseSHPFeature(JSONObject stateObj, String layerType)
			throws ParseException, com.vividsolutions.jts.io.ParseException, SchemaException {

		JSONParser jsonParser = new JSONParser();

		Map<String, Object> editFeatureMap = new HashMap<String, Object>();
		Iterator stateIterator = stateObj.keySet().iterator();
		while (stateIterator.hasNext()) {
			String state = (String) stateIterator.next();
			if (state.equals("created")) {
				DTSHPFeatureList featureList = new DTSHPFeatureList();
				JSONObject featuresObj = (JSONObject) stateObj.get(state);
				JSONArray featuresArry = (JSONArray) featuresObj.get("features");
				for (int i = 0; i < featuresArry.size(); i++) {
					String geoStr = (String) featuresArry.get(i);
					JSONObject featureObj = (JSONObject) jsonParser.parse(geoStr);
					EditDTFeatureParser featureParser = new EditDTFeatureParser("shp", featureObj, state);
					SimpleFeature feature = featureParser.getSHPFeature();
					featureList.add(feature);
				}
				editFeatureMap.put("created", featureList);
			} else if (state.equals("modified")) {
				DTSHPFeatureList featureList = new DTSHPFeatureList();
				JSONObject featuresObj = (JSONObject) stateObj.get(state);
				JSONArray featuresArry = (JSONArray) featuresObj.get("features");
				for (int i = 0; i < featuresArry.size(); i++) {
					String geoStr = (String) featuresArry.get(i);
					JSONObject featureObj = (JSONObject) jsonParser.parse(geoStr);
					EditDTFeatureParser featureParser = new EditDTFeatureParser("shp", featureObj, state);
					SimpleFeature feature = featureParser.getSHPFeature();
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
