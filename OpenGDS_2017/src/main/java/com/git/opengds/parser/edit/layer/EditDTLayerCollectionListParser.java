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
import org.json.simple.JSONObject;

import com.git.gdsbuilder.edit.shp.EditSHPLayerCollection;
import com.git.gdsbuilder.edit.shp.EditSHPLayerCollectionList;
import com.vividsolutions.jts.io.ParseException;

/**
 * JSONObject를 EditDTLayerCollectionList 객체로 파싱하는 클래스. SHP 파일 레이어 Collection
 * Table 수정
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 1:47:16
 */
public class EditDTLayerCollectionListParser {

	/**
	 * EditSHPLayerCollectionList 객체로 변환할 JSONObject 객체
	 */
	JSONObject collectionListObj;
	/**
	 * 파일타입
	 */
	String type;
	/**
	 * JSONObject 객체가 변환된 EditSHPLayerCollectionList 객체
	 */
	EditSHPLayerCollectionList edtSHPCollectionList;

	/**
	 * EditayerCollectionListParser 생성자
	 * 
	 * @param type
	 *            파일타입
	 * @param collectionListObj
	 *            EditSHPLayerCollectionList 객체로 변환할 JSONObject 객체
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws SchemaException
	 */
	public EditDTLayerCollectionListParser(String type, JSONObject collectionListObj)
			throws FileNotFoundException, IOException, ParseException, SchemaException {
		this.type = type;
		this.collectionListObj = collectionListObj;
		if (type.equals("shp")) {
			shpLayerCollectionListParse();
		}
	}

	/**
	 * 파일타입 반환
	 * 
	 * @return
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
	 * EditSHPLayerCollectionList 객체로 변환할 JSONObject 객체 반환
	 * 
	 * @return JSONObject
	 */
	public JSONObject getCollectionListObj() {
		return collectionListObj;
	}

	/**
	 * EditSHPLayerCollectionList 객체로 변환할 JSONObject 객체 설정
	 * 
	 * @param collectionListObj
	 *            EditSHPLayerCollectionList 객체로 변환할 JSONObject 객체
	 */
	public void setCollectionListObj(JSONObject collectionListObj) {
		this.collectionListObj = collectionListObj;
	}

	/**
	 * JSONObject 객체가 변환된 EditSHPLayerCollectionList 객체 반환
	 * 
	 * @return EditSHPLayerCollectionList
	 */
	public EditSHPLayerCollectionList getEdtSHPCollectionList() {
		return edtSHPCollectionList;
	}

	/**
	 * JSONObject 객체가 변환된 EditSHPLayerCollectionList 객체 설정
	 * 
	 * @param edtSHPCollectionList
	 *            JSONObject 객체가 변환된 EditSHPLayerCollectionList 객체
	 */
	public void setEdtSHPCollectionList(EditSHPLayerCollectionList edtSHPCollectionList) {
		this.edtSHPCollectionList = edtSHPCollectionList;
	}

	/**
	 * JSONObject를 EditDTLayerCollectionList 객체로 파싱
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws SchemaException
	 */
	public void shpLayerCollectionListParse()
			throws FileNotFoundException, IOException, ParseException, SchemaException {
		edtSHPCollectionList = new EditSHPLayerCollectionList();
		Iterator editCollections = collectionListObj.keySet().iterator();
		while (editCollections.hasNext()) {
			String collectionName = (String) editCollections.next();
			JSONObject collectionObj = (JSONObject) collectionListObj.get(collectionName);
			EditDTLayerCollectionParser collectionParser = new EditDTLayerCollectionParser(type, collectionObj);
			EditSHPLayerCollection dtCollection = collectionParser.getEditSHPCollection();
			dtCollection.setCollectionName(collectionName);
			edtSHPCollectionList.add(dtCollection);
		}
	}
}
