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

package com.git.opengds.parser.edit.layer;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONObject;

import com.git.gdsbuilder.edit.shp.EditSHPLayer;
import com.vividsolutions.jts.io.ParseException;

/**
 * JSONObject를 QA20Layer 객체로 파싱하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:14:38
 */
public class EditDTLayerParser {

	protected static String create = "create";
	protected static String modify = "modify";
	protected static String delete = "delete";

	JSONObject layerObj;
	String originLayerType;
	EditSHPLayer editSHPLayer;

	public static String getCreate() {
		return create;
	}

	public static void setCreate(String create) {
		EditDTLayerParser.create = create;
	}

	public static String getModify() {
		return modify;
	}

	public static void setModify(String modify) {
		EditDTLayerParser.modify = modify;
	}

	public static String getDelete() {
		return delete;
	}

	public static void setDelete(String delete) {
		EditDTLayerParser.delete = delete;
	}

	public JSONObject getLayerObj() {
		return layerObj;
	}

	public void setLayerObj(JSONObject layerObj) {
		this.layerObj = layerObj;
	}

	public String getType() {
		return originLayerType;
	}

	public void setType(String type) {
		this.originLayerType = type;
	}

	public EditSHPLayer getEditSHPLayer() {
		return editSHPLayer;
	}

	public void setEditSHPLayer(EditSHPLayer editSHPLayer) {
		this.editSHPLayer = editSHPLayer;
	}

	/**
	 * EditLayerParser 생성자
	 * 
	 * @param layerName
	 * 
	 * @param layerObj
	 * @param state
	 * @throws ParseException
	 * @throws SchemaException
	 */
	public EditDTLayerParser(String type, JSONObject layerObj, String state) throws ParseException, SchemaException {
		this.originLayerType = type;
		this.layerObj = layerObj;
		if (type.equals("shp")) {
			if (state.equals(create)) {
				shpCreatedLayerParse();
			} else if (state.equals(modify)) {
				shpModifiedLayerParse();
			}
		}
	}

	public void shpModifiedLayerParse() {

	}

	public void shpCreatedLayerParse() throws SchemaException {

		editSHPLayer = new EditSHPLayer();

		String layerType = (String) layerObj.get("layerType");
		String layerName = (String) layerObj.get("layerName");

		// JSONArray attrArray = (JSONArray) layerObj.get("attr");
		//
		// String temp = "";
		// Object[] objects = new Object[attrArray.size() + 1];
		// objects[0] = null;
		// for (int i = 0; i < attrArray.size(); i++) {
		// JSONObject attr = (JSONObject) attrArray.get(i);
		// String fieldName = (String) attr.get("fieldName");
		// boolean isNull = (boolean) attr.get("nullable");
		// String type = (String) attr.get("type");
		// temp += fieldName + ":" + type + ",";
		// objects[i + 1] = "default";
		// }
		// String featureID = "temp";
		// SimpleFeatureType simpleFeatureType =
		// DataUtilities.createType(featureID,
		// "the_geom:" + layerType + "," + temp.substring(0, temp.length() -
		// 1));
		// SimpleFeature simpleFeature =
		// SimpleFeatureBuilder.build(simpleFeatureType, objects, featureID);
		//
		String upLayerType = layerType.toUpperCase();
		editSHPLayer.setLayerName(layerName + "_" + upLayerType);
		editSHPLayer.setOrignName(layerName + "_" + upLayerType);
		editSHPLayer.setLayerType(upLayerType);
	}
}
