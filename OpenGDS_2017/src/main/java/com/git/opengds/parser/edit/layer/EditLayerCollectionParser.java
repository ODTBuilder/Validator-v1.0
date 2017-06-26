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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.git.gdsbuilder.edit.qa10.EditQA10Collection;
import com.git.gdsbuilder.edit.qa10.EditQA10Layer;
import com.git.gdsbuilder.edit.qa20.EditQA20Collection;
import com.git.gdsbuilder.edit.qa20.EditQA20Layer;
import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa10.layer.QA10LayerList;
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
	EditQA20Collection editQA20Collection;
	EditQA10Collection editQA10Collection;

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
		} else if (type.equals("dxf")) {
			dxfCollectionParser();
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public JSONObject getCollectionObj() {
		return collectionObj;
	}

	public void setCollectionObj(JSONObject collectionObj) {
		this.collectionObj = collectionObj;
	}

	public EditQA20Collection getEditQA20Collection() {
		return editQA20Collection;
	}

	public void setEditQA20Collection(EditQA20Collection editQA20Collection) {
		this.editQA20Collection = editQA20Collection;
	}

	public EditQA10Collection getEditQA10Collection() {
		return editQA10Collection;
	}

	public void setEditQA10Collection(EditQA10Collection editQA10Collection) {
		this.editQA10Collection = editQA10Collection;
	}

	public void dxfCollectionParser() throws ParseException {

		this.editQA10Collection = new EditQA10Collection();
		Iterator iterator = collectionObj.keySet().iterator();
		while (iterator.hasNext()) {
			String state = (String) iterator.next();
			if (state.equals("create")) {
				QA10LayerList createLayerList = new QA10LayerList();
				JSONArray layerArr = (JSONArray) collectionObj.get(state);
				for (int i = 0; i < layerArr.size(); i++) {
					JSONObject layerObj = (JSONObject) layerArr.get(i);
					EditLayerParser layerParser = new EditLayerParser(type, layerObj, state);
					EditQA10Layer layer = layerParser.getEditQA10Layer();
					QA10Layer createLayer = new QA10Layer(layer.getLayerName(), layer.getOrignName(),
							layer.getLayerType(), layer.getOriginLayerType());
					createLayerList.add(createLayer);
				}
				editQA10Collection.addAllCreateLayer(createLayerList);
				editQA10Collection.setCreated(true);

			} else if (state.equals("remove")) {
				JSONObject removeObj = (JSONObject) collectionObj.get(state);
				JSONArray layerNames = (JSONArray) removeObj.get("layer");
				String scope = (String) removeObj.get("scope");
				if (scope.equals("all")) {
					editQA10Collection.setDeleteAll(true);
				}
				QA10LayerList deletedLayerList = new QA10LayerList();
				for (int i = 0; i < layerNames.size(); i++) {
					String layerName = (String) layerNames.get(i);
					QA10Layer deleteLayer = new QA10Layer();
					deleteLayer.setLayerID(layerName);
					deletedLayerList.add(deleteLayer);
				}
				editQA10Collection.addAllDeleteLayer(deletedLayerList);
				editQA10Collection.setDeleted(true);

			} else if (state.equals("modify")) {
				QA10LayerList modifiedLayerList = new QA10LayerList();
				JSONArray layerArr = (JSONArray) collectionObj.get(state);
				for (int i = 0; i < layerArr.size(); i++) {
					JSONObject layerObj = (JSONObject) layerArr.get(i);
					EditLayerParser layerParser = new EditLayerParser(type, layerObj, state);
					EditQA10Layer layer = layerParser.getEditQA10Layer();
					// layerName : currentName
					QA10Layer modifiedLayer = new QA10Layer(layer.getLayerName(), layer.getOrignName());
					modifiedLayerList.add(modifiedLayer);

					Map<String, Object> geoLayerMap = new HashMap<String, Object>();
					geoLayerMap.put(layer.getOrignName(), layer.getGeoServerLayer());
					editQA10Collection.putGeoLayer(geoLayerMap);
				}
				editQA10Collection.addAllmodifiedLayer(modifiedLayerList);
				editQA10Collection.setModified(true);
			}
		}
	}

	public void ngiCollectionParser() throws ParseException {

		this.editQA20Collection = new EditQA20Collection();
		Iterator iterator = collectionObj.keySet().iterator();
		while (iterator.hasNext()) {
			String state = (String) iterator.next();
			if (state.equals("create")) {
				QA20LayerList qa20LayerList = new QA20LayerList();
				JSONArray layerArr = (JSONArray) collectionObj.get(state);
				for (int i = 0; i < layerArr.size(); i++) {
					JSONObject layerObj = (JSONObject) layerArr.get(i);
					EditLayerParser layerParser = new EditLayerParser(type, layerObj, state);
					EditQA20Layer layer = layerParser.getEditQA20Layer();
					QA20Layer createLayer = new QA20Layer(String.valueOf(i), layer.getLayerName(), layer.getNgiHeader(),
							layer.getNdaHeader(), layer.getLayerType(), null);
					qa20LayerList.add(createLayer);
				}
				editQA20Collection.addAllCreateLayer(qa20LayerList);
				editQA20Collection.setCreated(true);
			} else if (state.equals("remove")) {
				JSONObject removeObj = (JSONObject) collectionObj.get(state);
				JSONArray layerNames = (JSONArray) removeObj.get("layer");
				String scope = (String) removeObj.get("scope");
				if (scope.equals("all")) {
					editQA20Collection.setDeleteAll(true);
				}
				QA20LayerList deletedLayerList = new QA20LayerList();
				for (int i = 0; i < layerNames.size(); i++) {
					String layerName = (String) layerNames.get(i);
					QA20Layer deleteLayer = new QA20Layer();
					deleteLayer.setLayerName(layerName);
					deletedLayerList.add(deleteLayer);
				}
				editQA20Collection.addAllDeleteLayer(deletedLayerList);
				editQA20Collection.setDeleted(true);
			} else if (state.equals("modify")) {
				QA20LayerList modifiedLayerList = new QA20LayerList();
				JSONArray layerArr = (JSONArray) collectionObj.get(state);
				for (int i = 0; i < layerArr.size(); i++) {
					JSONObject layerObj = (JSONObject) layerArr.get(i);
					EditLayerParser layerParser = new EditLayerParser(type, layerObj, state);
					EditQA20Layer layer = layerParser.getEditQA20Layer();
					String layerName = layer.getLayerName();
					QA20Layer modifiedLayer = new QA20Layer(String.valueOf(i), layerName, layer.getNgiHeader(),
							layer.getNdaHeader(), layer.getLayerType(), null);
					modifiedLayer.setOriginLayerName(layer.getOrignName());
					modifiedLayerList.add(modifiedLayer);

					Map<String, Object> geoLayerMap = new HashMap<String, Object>();
					geoLayerMap.put(layerName, layer.getGeoServerLayer());
					editQA20Collection.putGeoLayer(geoLayerMap);
				}
				editQA20Collection.addAllmodifiedLayer(modifiedLayerList);
				editQA20Collection.setModified(true);
			}
		}
	}
}
