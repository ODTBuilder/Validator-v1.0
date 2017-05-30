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

package com.git.gdsbuilder.validator.json;

import java.util.Iterator;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * GeoJSON의 검수를 수행한다.
 * 
 * @author dayeon.oh
 * @data 2016.10
 */
public class DTGeoJSON {

	JSONObject trueJSON;
	JSONObject falseJSON;

	public JSONObject getTrueJSON() {
		return trueJSON;
	}

	public void setTrueJSON(JSONObject trueJSON) {
		this.trueJSON = trueJSON;
	}

	public JSONObject getFalseJSON() {
		return falseJSON;
	}

	public void setFalseJSON(JSONObject falseJSON) {
		this.falseJSON = falseJSON;
	}

	/**
	 * GeoJSON을 검수한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param geoJSON
	 *            검수를 수행할 GeoJSON
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	public void geoJsonValidate(JSONObject geoJSON) throws ParseException {

		// System.out.println(tempJSPN.toString());
		JSONArray trueJSONs = new JSONArray();
		JSONArray falseJSONs = new JSONArray();

		Iterator iter = geoJSON.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			if (key == null) {
				break;
			} else {
				if (key.equals("type")) {
					String type = (String) geoJSON.get(key);
					if (type == null || !type.equals("FeatureCollection")) {
						break;
					}
				}
				if (key.equals("features")) {
					JSONArray features = (JSONArray) geoJSON.get(key);
					if (features == null) {
						break;
					} else {
						int featureSize = features.size();
						for (int i = 0; i < featureSize; i++) {
							JSONObject feature = (JSONObject) features.get(i);
							if (featureValidate(feature)) {
								JSONObject geometry = (JSONObject) feature.get("geometry");
								DTGeometry dtGeometry = new DTGeometry();
								if (dtGeometry.geometryValidate(geometry)) {
									trueJSONs.add(feature);
								} else {
									falseJSONs.add(feature);
								}
							} else {
								break;
							}
						}
					}
				}
			}
		}

		trueJSON = createGeoJSON(trueJSONs);
		falseJSON = createGeoJSON(falseJSONs);
		// System.out.println("");
	}

	private boolean featureValidate(JSONObject feature) {

		boolean trueFeature = true;
		int trueCount = 0;
		Iterator iter = feature.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			if (key == null) {
				trueFeature = false;
			} else {
				if (key.equals("id")) {
					if (feature.get(key) != null) {
						trueCount++;
					}
				} else if (key.equals("type")) {
					if (feature.get(key) != null) {
						trueCount++;
					}
				} else if (key.equals("geometry")) {
					if (feature.get(key) != null) {
						trueCount++;
					}
				} else if (key.equals("properties")) {
					if (feature.get(key) != null) {
						trueCount++;
					}
				}
			}
		}
		if (trueCount != 4) {
			trueFeature = false;
		}
		return trueFeature;
	}

	/**
	 * FeatureCollection 타입의 GeoJSON을 생성한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param features
	 *            검수를 수행할 SimpleFeature
	 * @return JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject createGeoJSON(JSONArray features) {
		JSONObject geoJSON = new JSONObject();
		geoJSON.put("type", "FeatureCollection");
		geoJSON.put("features", features);
		return geoJSON;
	}

	/**
	 * 오류로 분류된 JSONObject 객체를 FeatureCollection 타입의 GeoJSON으로 생성한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param features
	 *            검수를 수행할 SimpleFeature
	 * @return JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject createErrorJSON(JSONObject falseJSON) throws SchemaException {

		DTGeometry dtGeom = new DTGeometry();
		JSONArray errFeatures = new JSONArray();
		JSONArray features = (JSONArray) falseJSON.get("features");
		int featureSize = features.size();
		for (int i = 0; i < featureSize; i++) {
			JSONObject feature = (JSONObject) features.get(i);
			String featureID = (String) feature.get("id");
			JSONObject geometry = (JSONObject) feature.get("geometry");
			JSONArray errPoint = dtGeom.getFirstPoint(geometry);

			JSONObject returGeom = new JSONObject();
			returGeom.put("type", "Point");
			returGeom.put("coordinates", errPoint);

			errFeatures.add(createErrorFeature(featureID, returGeom, "UnKnownType"));
		}
		return createGeoJSON(errFeatures);
	}

	@SuppressWarnings("unchecked")
	private JSONObject createErrorFeature(String errfeatureID, JSONObject errPoint, String errType) throws SchemaException {
		JSONObject obj = new JSONObject();
		obj.put("type", "Feature");
		obj.put("id", errfeatureID + "_err");
		obj.put("geometry", errPoint);

		JSONObject properties = new JSONObject();
		properties.put("errType", errType);
		properties.put("errfeatureID", errfeatureID);

		obj.put("properties", properties);
		return obj;
	}

	/**
	 * 두개의 GeoJSON을 하나의 GeoJSON으로 합친다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.10
	 * @param obj1
	 *            합치고자하는 GeoJSON
	 * @param obj2
	 *            합치고자하는 GeoJSON
	 * @return JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject merge2GeoJSON(JSONObject obj1, JSONObject obj2) {

		JSONObject returnObj = new JSONObject();
		returnObj.put("type", "FeatureCollection");

		JSONArray returnArray = new JSONArray();
		returnArray.addAll((JSONArray) obj1.get("features"));
		returnArray.addAll((JSONArray) obj2.get("features"));
		returnObj.put("features", returnArray);

		return returnObj;
	}
}
