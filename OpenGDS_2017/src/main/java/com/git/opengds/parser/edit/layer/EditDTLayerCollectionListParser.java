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

import com.git.gdsbuilder.edit.dxf.EditDXFLayerCollection;
import com.git.gdsbuilder.edit.dxf.EditDXFLayerCollectionList;
import com.git.gdsbuilder.edit.ngi.EditNGILayerCollection;
import com.git.gdsbuilder.edit.ngi.EditNGILayerCollectionList;
import com.git.gdsbuilder.edit.shp.EditSHPLayerCollection;
import com.git.gdsbuilder.edit.shp.EditSHPLayerCollectionList;
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
	EditNGILayerCollectionList edtNGICollectionList;
	EditDXFLayerCollectionList edtDXFCollectionList;
	EditSHPLayerCollectionList edtSHPCollectionList;

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
			ngiLayerCollectionListParse();
		} else if (type.equals("dxf")) {
			dxfLayerCollectionListParse();
		} else if (type.equals("shp")) {
			shpLayerCollectionListParse();
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

	public EditNGILayerCollectionList getEdtNGICollectionList() {
		return edtNGICollectionList;
	}

	public void setEdtNGICollectionList(EditNGILayerCollectionList edtNGICollectionList) {
		this.edtNGICollectionList = edtNGICollectionList;
	}

	public EditDXFLayerCollectionList getEdtDXFCollectionList() {
		return edtDXFCollectionList;
	}

	public void setEdtDXFCollectionList(EditDXFLayerCollectionList edtDXFCollectionList) {
		this.edtDXFCollectionList = edtDXFCollectionList;
	}

	public EditSHPLayerCollectionList getEdtSHPCollectionList() {
		return edtSHPCollectionList;
	}

	public void setEdtSHPCollectionList(EditSHPLayerCollectionList edtSHPCollectionList) {
		this.edtSHPCollectionList = edtSHPCollectionList;
	}

	public void ngiLayerCollectionListParse()
			throws FileNotFoundException, IOException, ParseException, SchemaException {

		edtNGICollectionList = new EditNGILayerCollectionList();
		Iterator editCollections = collectionListObj.keySet().iterator();
		while (editCollections.hasNext()) {
			String collectionName = (String) editCollections.next();
			JSONObject collectionObj = (JSONObject) collectionListObj.get(collectionName);
			EditDTLayerCollectionParser collectionParser = new EditDTLayerCollectionParser(type, collectionObj);
			EditNGILayerCollection dtCollection = collectionParser.getEditNGICollection();
			dtCollection.setCollectionName(collectionName);
			edtNGICollectionList.add(dtCollection);
		}
	}

	public void dxfLayerCollectionListParse()
			throws FileNotFoundException, IOException, ParseException, SchemaException {
		edtDXFCollectionList = new EditDXFLayerCollectionList();
		Iterator editCollections = collectionListObj.keySet().iterator();
		while (editCollections.hasNext()) {
			String collectionName = (String) editCollections.next();
			JSONObject collectionObj = (JSONObject) collectionListObj.get(collectionName);
			EditDTLayerCollectionParser collectionParser = new EditDTLayerCollectionParser(type, collectionObj);
			EditDXFLayerCollection dtCollection = collectionParser.getEditDXFCollection();
			dtCollection.setCollectionName(collectionName);
			edtDXFCollectionList.add(dtCollection);
		}
	}

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
