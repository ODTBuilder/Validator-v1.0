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
 * JSONObject를 EditSHPLayer 객체로 파싱하는 클래스.
 * SHP 파일 레이어 Table 수정
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:14:38
 */
public class EditDTLayerParser {

	/**
	 * Edit State. Layer 생성
	 */
	protected static String create = "create";
	/**
	 * Edit State. Layer 수정
	 */
	protected static String modify = "modify";
	/**
	 * Edit State. Layer 삭제
	 */
	protected static String delete = "delete";

	/**
	 * EditDTLayer 객체로 변환할 JSONObject 객체
	 */
	JSONObject layerObj;
	/**
	 * 기존 DTLayer의 타입
	 */
	String originLayerType;
	/**
	 * JSONObject 객체가 변환된 EditDTLayer 객체
	 */
	EditSHPLayer editSHPLayer;

	/**
	 * Create Edit State 반환
	 * 
	 * @return String
	 */
	public static String getCreate() {
		return create;
	}

	/**
	 * Create Edit State 설정
	 * 
	 * @param create
	 *            Create Edit State 반환
	 */
	public static void setCreate(String create) {
		EditDTLayerParser.create = create;
	}

	/**
	 * modify Edit State 설정
	 * 
	 * @return String
	 */
	public static String getModify() {
		return modify;
	}

	/**
	 * modify Edit State 설정
	 * 
	 * @param modify
	 */
	public static void setModify(String modify) {
		EditDTLayerParser.modify = modify;
	}

	/**
	 * delete Edit State 설정
	 * 
	 * @return String
	 */
	public static String getDelete() {
		return delete;
	}

	/**
	 * delete Edit State 설정
	 * 
	 * @param delete
	 */
	public static void setDelete(String delete) {
		EditDTLayerParser.delete = delete;
	}

	/**
	 * EditDTLayer 객체로 변환할 JSONObject 객체 반환
	 * 
	 * @return JSONObject
	 */
	public JSONObject getLayerObj() {
		return layerObj;
	}

	/**
	 * EditDTLayer 객체로 변환할 JSONObject 객체 설정
	 * 
	 * @param layerObj
	 *            EditDTLayer 객체로 변환할 JSONObject 객체
	 */
	public void setLayerObj(JSONObject layerObj) {
		this.layerObj = layerObj;
	}

	/**
	 * 수정 전 DTLayer의 타입 반환
	 * 
	 * @return String
	 */
	public String getType() {
		return originLayerType;
	}

	/**
	 * 수정 전 DTLayer의 타입 설정
	 * 
	 * @param type
	 *            수정 전 DTLayer의 타입
	 */
	public void setType(String type) {
		this.originLayerType = type;
	}

	/**
	 * JSONObject 객체가 변환된 EditDTLayer 객체 반환
	 * 
	 * @return EditSHPLayer
	 */
	public EditSHPLayer getEditSHPLayer() {
		return editSHPLayer;
	}

	/**
	 * JSONObject 객체가 변환된 EditDTLayer 객체 설정
	 * 
	 * @param editSHPLayer
	 *            JSONObject 객체가 변환된 EditDTLayer 객체
	 */
	public void setEditSHPLayer(EditSHPLayer editSHPLayer) {
		this.editSHPLayer = editSHPLayer;
	}

	/**
	 * EditDTLayerParser 생성자
	 * 
	 * @param type
	 *            기존 DTLayer의 타입
	 * @param layerObj
	 *            EditDTLayer 객체로 변환할 JSONObject 객체
	 * @param state
	 *            Edit State
	 * @throws ParseException
	 * @throws SchemaException
	 */
	public EditDTLayerParser(String type, JSONObject layerObj, String state) throws ParseException, SchemaException {
		this.originLayerType = type;
		this.layerObj = layerObj;
		if (type.equals("shp")) {
			if (state.equals(create)) {
				shpCreatedLayerParse();
			}
		}
	}

	/**
	 * 생성 SHP Layer 파싱
	 * 
	 * @throws SchemaException
	 */
	public void shpCreatedLayerParse() throws SchemaException {

		editSHPLayer = new EditSHPLayer();

		String layerType = (String) layerObj.get("layerType");
		String layerName = (String) layerObj.get("layerName");

		String upLayerType = layerType.toUpperCase();
		editSHPLayer.setLayerName(layerName + "_" + upLayerType);
		editSHPLayer.setOrignName(layerName + "_" + upLayerType);
		editSHPLayer.setLayerType(upLayerType);
	}
}
