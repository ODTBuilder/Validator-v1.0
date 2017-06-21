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

import com.git.gdsbuilder.edit.qa20.EditQA20Collection;
import com.git.gdsbuilder.type.qa20.layer.QA20Layer;
import com.git.gdsbuilder.type.qa20.layer.QA20LayerList;
import com.vividsolutions.jts.io.ParseException;

/**
 * JSONObject를 QA20LayerCollection 객체로 파싱하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:14:22
 */
public class EditLayerCollectionParser {

	JSONObject collectionObj;
	String type;
	EditQA20Collection editCollection;

	/**
	 * EditLayerCollectionParser 생성자
	 * 
	 * @param type
	 * 
	 * @param type
	 * 
	 * @param collectionObject
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws SchemaException
	 */
	public EditLayerCollectionParser(String type, JSONObject collectionObject)
			throws FileNotFoundException, IOException, ParseException, SchemaException {
		this.collectionObj = collectionObject;
		this.type = type;
		if (type.equals("ngi")) {
			ngiCollectionParser();
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public EditQA20Collection getEditCollection() {
		return editCollection;
	}

	public void setEditCollection(EditQA20Collection editCollection) {
		this.editCollection = editCollection;
	}

	public void ngiCollectionParser() throws ParseException {

		this.editCollection = new EditQA20Collection();
		Iterator iterator = collectionObj.keySet().iterator();
		while (iterator.hasNext()) {
			String state = (String) iterator.next();
			if (state.equals("create")) {
				QA20LayerList qa20LayerList = new QA20LayerList();
				JSONArray layerArr = (JSONArray) collectionObj.get(state);
				for (int i = 0; i < layerArr.size(); i++) {
					JSONObject layerObj = (JSONObject) layerArr.get(i);
					EditLayerParser layerParser = new EditLayerParser(type, layerObj, state);
					qa20LayerList.add(layerParser.getQa20Layer());
				}
				editCollection.addAllCreateLayer(qa20LayerList);
				editCollection.setCreated(true);
			} else if (state.equals("remove")) {
				JSONObject removeObj = (JSONObject) collectionObj.get(state);
				JSONArray layerNames = (JSONArray) removeObj.get("layer");
				String scope = (String) removeObj.get("scope");
				if (scope.equals("all")) {
					editCollection.setDeleteAll(true);
				}
				QA20LayerList deletedLayerList = new QA20LayerList();
				for (int i = 0; i < layerNames.size(); i++) {
					String layerName = (String) layerNames.get(i);
					EditLayerParser layerParser = new EditLayerParser(layerName);
					QA20Layer deleteLayer = layerParser.getQa20Layer();
					deletedLayerList.add(deleteLayer);
				}
				editCollection.addAllDeleteLayer(deletedLayerList);
				editCollection.setDeleted(true);
			} else if (state.equals("modify")) {
				QA20LayerList modifiedLayerList = new QA20LayerList();
				JSONArray layerArr = (JSONArray) collectionObj.get(state);
				for (int i = 0; i < layerArr.size(); i++) {
					JSONObject layerObj = (JSONObject) layerArr.get(i);
					EditLayerParser layerParser = new EditLayerParser(type, layerObj, state);
					modifiedLayerList.add(layerParser.getQa20Layer());
				}
				editCollection.addAllmodifiedLayer(modifiedLayerList);
				editCollection.setModified(true);
			}
		}
	}
}
