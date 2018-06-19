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

package com.git.opengds.parser.edit.layer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.git.gdsbuilder.edit.shp.EditSHPLayer;
import com.git.gdsbuilder.edit.shp.EditSHPLayerCollection;
import com.git.gdsbuilder.type.shp.layer.DTSHPLayer;
import com.git.gdsbuilder.type.shp.layer.DTSHPLayerList;
import com.vividsolutions.jts.io.ParseException;

/**
 * JSONObject를 EditDTLayerCollection 객체로 파싱하는 클래스. SHP 파일 레이어 Collection Table
 * 수정
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:14:22
 */
public class EditDTLayerCollectionParser {

	/**
	 * EditDTLayerCollection 객체로 변환할 JSONObject 객체
	 */
	JSONObject collectionObj;
	/**
	 * 파일타입
	 */
	String type;
	/**
	 * JSONObject 객체가 변환된 EditSHPLayerCollection 객체
	 */
	EditSHPLayerCollection editSHPCollection;

	/**
	 * EditDTLayerCollectionParser 생성자
	 * 
	 * @param type
	 *            파일타입
	 * @param collectionObject
	 *            EditDTLayerCollection 객체로 변환할 JSONObject 객체
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws SchemaException
	 */
	public EditDTLayerCollectionParser(String type, JSONObject collectionObject)
			throws FileNotFoundException, IOException, ParseException, SchemaException {
		this.collectionObj = collectionObject;
		this.type = type;
		if (type.equals("shp")) {
			shpCollectionParser();
		}
	}

	/**
	 * 파일타입 반환
	 * 
	 * @return String
	 */
	public String getType() {
		return type;
	}

	/**
	 * 파일타입 설정
	 * 
	 * @param type
	 *            파일타입
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * EditDTLayerCollection 객체로 변환할 JSONObject 객체 반환
	 * 
	 * @return JSONObject
	 */
	public JSONObject getCollectionObj() {
		return collectionObj;
	}

	/**
	 * EditDTLayerCollection 객체로 변환할 JSONObject 객체 설정
	 * 
	 * @param collectionObj
	 *            EditDTLayerCollection 객체로 변환할 JSONObject 객체
	 */
	public void setCollectionObj(JSONObject collectionObj) {
		this.collectionObj = collectionObj;
	}

	/**
	 * JSONObject 객체가 변환된 EditSHPLayerCollection 객체 반환
	 * 
	 * @return EditSHPLayerCollection
	 */
	public EditSHPLayerCollection getEditSHPCollection() {
		return editSHPCollection;
	}

	/**
	 * JSONObject 객체가 변환된 EditSHPLayerCollection 객체 설정
	 * 
	 * @param editSHPCollection
	 *            JSONObject 객체가 변환된 EditSHPLayerCollection 객체
	 */
	public void setEditSHPCollection(EditSHPLayerCollection editSHPCollection) {
		this.editSHPCollection = editSHPCollection;
	}

	/**
	 * JSONObject를 EditDTLayerCollection 객체로 파싱
	 * 
	 * @throws ParseException
	 * @throws SchemaException
	 */
	public void shpCollectionParser() throws ParseException, SchemaException {

		this.editSHPCollection = new EditSHPLayerCollection();
		Iterator iterator = collectionObj.keySet().iterator();
		while (iterator.hasNext()) {
			String state = (String) iterator.next();
			if (state.equals("create")) {
				DTSHPLayerList createLayerList = new DTSHPLayerList();
				JSONArray layerArr = (JSONArray) collectionObj.get(state);
				for (int i = 0; i < layerArr.size(); i++) {
					JSONObject layerObj = (JSONObject) layerArr.get(i);
					EditDTLayerParser layerParser = new EditDTLayerParser(type, layerObj, state);
					EditSHPLayer layer = layerParser.getEditSHPLayer();
					DTSHPLayer createLayer = new DTSHPLayer(layer.getLayerName(), layer.getLayerType());
					createLayerList.add(createLayer);
				}
				editSHPCollection.addAllCreateLayer(createLayerList);
				editSHPCollection.setCreated(true);
			} else if (state.equals("remove")) {
				JSONObject removeObj = (JSONObject) collectionObj.get(state);
				JSONArray layerNames = (JSONArray) removeObj.get("layer");
				String scope = (String) removeObj.get("scope");
				if (scope.equals("all")) {
					editSHPCollection.setDeleteAll(true);
				}
				DTSHPLayerList deletedLayerList = new DTSHPLayerList();
				for (int i = 0; i < layerNames.size(); i++) {
					String layerName = (String) layerNames.get(i);
					DTSHPLayer deleteLayer = new DTSHPLayer();
					deleteLayer.setLayerName(layerName);
					deletedLayerList.add(deleteLayer);
				}
				editSHPCollection.addAllDeleteLayer(deletedLayerList);
				editSHPCollection.setDeleted(true);
			}
		}
	}
}
