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

import com.git.gdsbuilder.edit.dxf.EditDXFCollection;
import com.git.gdsbuilder.edit.dxf.EditDXFLayerCollectionList;
import com.git.gdsbuilder.edit.ngi.EditNGICollection;
import com.git.gdsbuilder.edit.ngi.EditNGILayerCollectionList;
import com.vividsolutions.jts.io.ParseException;

/**
 * JSONObject를 QA20LayerCollectionList 객체로 파싱하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 1:47:16
 */
public class EditDTLayerCollectionListParser {

	JSONObject collectionListObj;
	String type;
	EditNGILayerCollectionList edtQA20CollectionList;
	EditDXFLayerCollectionList edtQA10CollectionList;

	/**
	 * EditayerCollectionListParser 생성자
	 * 
	 * @param type
	 * @param collectionListObj
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws SchemaException
	 */
	public EditDTLayerCollectionListParser(String type, JSONObject collectionListObj)
			throws FileNotFoundException, IOException, ParseException, SchemaException {
		this.type = type;
		this.collectionListObj = collectionListObj;
		if (type.equals("ngi")) {
			ngiCollectionListParse();
		} else if (type.equals("dxf")) {
			dxfCollectionListParse();
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public JSONObject getCollectionListObj() {
		return collectionListObj;
	}

	public void setCollectionListObj(JSONObject collectionListObj) {
		this.collectionListObj = collectionListObj;
	}

	public EditNGILayerCollectionList getEdtQA20CollectionList() {
		return edtQA20CollectionList;
	}

	public void setEdtQA20CollectionList(EditNGILayerCollectionList edtQA20CollectionList) {
		this.edtQA20CollectionList = edtQA20CollectionList;
	}

	public EditDXFLayerCollectionList getEdtQA10CollectionList() {
		return edtQA10CollectionList;
	}

	public void setEdtQA10CollectionList(EditDXFLayerCollectionList edtQA10CollectionList) {
		this.edtQA10CollectionList = edtQA10CollectionList;
	}

	public void ngiCollectionListParse() throws FileNotFoundException, IOException, ParseException, SchemaException {

		edtQA20CollectionList = new EditNGILayerCollectionList();
		Iterator editCollections = collectionListObj.keySet().iterator();
		while (editCollections.hasNext()) {
			String collectionName = (String) editCollections.next();
			JSONObject collectionObj = (JSONObject) collectionListObj.get(collectionName);
			EditDTLayerCollectionParser collectionParser = new EditDTLayerCollectionParser(type, collectionObj);
			EditNGICollection dtCollection = collectionParser.getEditQA20Collection();
			dtCollection.setCollectionName(collectionName);
			edtQA20CollectionList.add(dtCollection);
		}
	}

	public void dxfCollectionListParse() throws FileNotFoundException, IOException, ParseException, SchemaException {
		edtQA10CollectionList = new EditDXFLayerCollectionList();
		Iterator editCollections = collectionListObj.keySet().iterator();
		while (editCollections.hasNext()) {
			String collectionName = (String) editCollections.next();
			JSONObject collectionObj = (JSONObject) collectionListObj.get(collectionName);
			EditDTLayerCollectionParser collectionParser = new EditDTLayerCollectionParser(type, collectionObj);
			EditDXFCollection dtCollection = collectionParser.getEditQA10Collection();
			dtCollection.setCollectionName(collectionName);
			edtQA10CollectionList.add(dtCollection);
		}
	}

}
