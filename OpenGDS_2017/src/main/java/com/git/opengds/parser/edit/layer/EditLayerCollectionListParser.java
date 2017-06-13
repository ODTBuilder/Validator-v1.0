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

import com.git.gdsbuilder.edit.qa20.EditQA20Collection;
import com.git.gdsbuilder.edit.qa20.EditQA20LayerCollectionList;
import com.vividsolutions.jts.io.ParseException;

/**
 * JSONObject를 QA20LayerCollectionList 객체로 파싱하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 1:47:16
 */
public class EditLayerCollectionListParser {

	JSONObject collectionListObj;
	String type;
	EditQA20LayerCollectionList edtCollectionList;

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
	public EditLayerCollectionListParser(String type, JSONObject collectionListObj)
			throws FileNotFoundException, IOException, ParseException, SchemaException {
		this.type = type;
		this.collectionListObj = collectionListObj;
		if (type.equals("ngi")) {
			ngiCollectionListParse();
		}

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public EditQA20LayerCollectionList getEdtCollectionList() {
		return edtCollectionList;
	}

	public void setEdtCollectionList(EditQA20LayerCollectionList edtCollectionList) {
		this.edtCollectionList = edtCollectionList;
	}

	public void ngiCollectionListParse() throws FileNotFoundException, IOException, ParseException, SchemaException {

		edtCollectionList = new EditQA20LayerCollectionList();
		Iterator editCollections = collectionListObj.keySet().iterator();
		while (editCollections.hasNext()) {
			String collectionName = (String) editCollections.next();
			JSONObject collectionObj = (JSONObject) collectionListObj.get(collectionName);
			EditLayerCollectionParser collectionParser = new EditLayerCollectionParser(type, collectionObj);
			EditQA20Collection dtCollection = collectionParser.getEditCollection();
			dtCollection.setCollectionName(collectionName);
			edtCollectionList.add(dtCollection);
		}
	}
}
