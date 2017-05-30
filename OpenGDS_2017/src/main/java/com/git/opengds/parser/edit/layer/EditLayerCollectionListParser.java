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

import com.git.gdsbuilder.type.qa20.collection.QA20LayerCollectionList;
import com.git.gdsbuilder.type.simple.collection.LayerCollection;
import com.git.gdsbuilder.type.simple.collection.LayerCollectionList;
import com.vividsolutions.jts.io.ParseException;

/**
 * JSONObject를 QA20LayerCollectionList 객체로 파싱하는 클래스
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 1:47:16
 * */
public class EditLayerCollectionListParser {

	JSONObject collectionListObj;
	String type;
	LayerCollectionList layerCollectionList;
	QA20LayerCollectionList qa20LayerCollectionList;
	
	/**
	 * EditayerCollectionListParser 생성자
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
		if(type.equals("ngi")) {
			ngiCollectionListParse();
		}
		
	}

	/**
	 * collectionListObj getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 1:47:20
	 * @return JSONObject
	 * @throws
	 * */
	public JSONObject getCollectionListObj() {
		return collectionListObj;
	}

	/**
	 *
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 1:47:22
	 * @param collectionListObj void
	 * @throws
	 * */
	public void setCollectionListObj(JSONObject collectionListObj) {
		this.collectionListObj = collectionListObj;
	}

	/**
	 * qa20LayerCollectionList getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 1:47:25
	 * @return QA20LayerCollectionList
	 * @throws
	 * */
	public LayerCollectionList getLayerCollectionList() {
		return layerCollectionList;
	}

	/**
	 *
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 1:47:27
	 * @param qa20LayerCollectionList void
	 * @throws
	 * */
	public void setLayerCollectionList(LayerCollectionList layerCollectionList) {
		this.layerCollectionList = layerCollectionList;
	}

	/**
	 * JSONObject를 QA20LayerCollectionList 객체로 파싱
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 1:47:29
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws SchemaException void
	 * @throws
	 * */
	public void ngiCollectionListParse() throws FileNotFoundException, IOException, ParseException, SchemaException {

		LayerCollectionList collectionList = new LayerCollectionList();
		Iterator editCollections = collectionListObj.keySet().iterator();
		while(editCollections.hasNext()) {
			String collectionName = (String) editCollections.next();
			JSONObject collectionObj = (JSONObject) collectionListObj.get(collectionName);
			EditLayerCollectionParser collectionParser = new EditLayerCollectionParser(type, collectionObj);
			LayerCollection collection = collectionParser.getLayerCollection();
			collectionList.add(collection);
		}
		this.layerCollectionList = collectionList;
	}
}
