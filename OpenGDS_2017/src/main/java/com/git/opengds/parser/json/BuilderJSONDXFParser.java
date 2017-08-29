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

import com.git.gdsbuilder.edit.dxf.EditDXFLayerCollectionList;
import com.git.gdsbuilder.type.dxf.feature.DTDXFFeature;
import com.git.gdsbuilder.type.dxf.feature.DTDXFFeatureList;
import com.git.opengds.parser.edit.feature.EditDTFeatureParser;
import com.git.opengds.parser.edit.layer.EditDTLayerCollectionListParser;

public class BuilderJSONDXFParser {

	public static EditDXFLayerCollectionList parseEditLayerObj(JSONObject editLayerObj, String type)
			throws FileNotFoundException, IOException, com.vividsolutions.jts.io.ParseException, SchemaException {

		// layer 편집
		JSONObject collectionListObj = (JSONObject) editLayerObj.get(type);
		EditDTLayerCollectionListParser editLayerCollectionListParser = new EditDTLayerCollectionListParser(type,
				collectionListObj);
		EditDXFLayerCollectionList edtCollectionList = editLayerCollectionListParser.getEdtDXFCollectionList();
		return edtCollectionList;
	}

	public static Map<String, Object> parseDXFFeature(JSONObject stateObj, String layerType)
			throws ParseException, com.vividsolutions.jts.io.ParseException, SchemaException {

		JSONParser jsonParser = new JSONParser();

		Map<String, Object> editFeatureMap = new HashMap<String, Object>();
		Iterator stateIterator = stateObj.keySet().iterator();
		while (stateIterator.hasNext()) {
			String state = (String) stateIterator.next();
			if (state.equals("created")) {
				DTDXFFeatureList featureList = new DTDXFFeatureList();
				JSONObject featuresObj = (JSONObject) stateObj.get(state);
				JSONArray featuresArry = (JSONArray) featuresObj.get("features");
				for (int i = 0; i < featuresArry.size(); i++) {
					String geoStr = (String) featuresArry.get(i);
					JSONObject featureObj = (JSONObject) jsonParser.parse(geoStr);
					EditDTFeatureParser featureParser = new EditDTFeatureParser("dxf", featureObj, state);
					DTDXFFeature feature = featureParser.getDXFFeature();
				//	feature.setFeatureType(layerType);
					featureList.add(feature);
				}
				editFeatureMap.put("created", featureList);
			} else if (state.equals("modified")) {
				DTDXFFeatureList featureList = new DTDXFFeatureList();
				JSONObject featuresObj = (JSONObject) stateObj.get(state);
				JSONArray featuresArry = (JSONArray) featuresObj.get("features");
				for (int i = 0; i < featuresArry.size(); i++) {
					String geoStr = (String) featuresArry.get(i);
					JSONObject featureObj = (JSONObject) jsonParser.parse(geoStr);
					EditDTFeatureParser featureParser = new EditDTFeatureParser("dxf", featureObj, state);
					DTDXFFeature feature = featureParser.getDXFFeature();
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
