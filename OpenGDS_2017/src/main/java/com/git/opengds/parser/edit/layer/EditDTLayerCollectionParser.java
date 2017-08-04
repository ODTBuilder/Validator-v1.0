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

import com.git.gdsbuilder.edit.dxf.EditDXFCollection;
import com.git.gdsbuilder.edit.dxf.EditDXFLayer;
import com.git.gdsbuilder.edit.ngi.EditNGI0Layer;
import com.git.gdsbuilder.edit.ngi.EditNGICollection;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayer;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayerList;
import com.git.gdsbuilder.type.ngi.layer.DTNGILayer;
import com.git.gdsbuilder.type.ngi.layer.DTNGILayerList;
import com.vividsolutions.jts.io.ParseException;

/**
 * JSONObject를 QA20LayerCollection 객체로 파싱하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:14:22
 */
public class EditDTLayerCollectionParser {

	JSONObject collectionObj;
	String type;
	EditNGICollection editQA20Collection;
	EditDXFCollection editQA10Collection;

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
	public EditDTLayerCollectionParser(String type, JSONObject collectionObject)
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

	public EditNGICollection getEditQA20Collection() {
		return editQA20Collection;
	}

	public void setEditQA20Collection(EditNGICollection editQA20Collection) {
		this.editQA20Collection = editQA20Collection;
	}

	public EditDXFCollection getEditQA10Collection() {
		return editQA10Collection;
	}

	public void setEditQA10Collection(EditDXFCollection editQA10Collection) {
		this.editQA10Collection = editQA10Collection;
	}

	public void dxfCollectionParser() throws ParseException {

		this.editQA10Collection = new EditDXFCollection();
		Iterator iterator = collectionObj.keySet().iterator();
		while (iterator.hasNext()) {
			String state = (String) iterator.next();
			if (state.equals("create")) {
				DTDXFLayerList createLayerList = new DTDXFLayerList();
				JSONArray layerArr = (JSONArray) collectionObj.get(state);
				for (int i = 0; i < layerArr.size(); i++) {
					JSONObject layerObj = (JSONObject) layerArr.get(i);
					EditDTLayerParser layerParser = new EditDTLayerParser(type, layerObj, state);
					EditDXFLayer layer = layerParser.getEditQA10Layer();
					DTDXFLayer createLayer = new DTDXFLayer(layer.getLayerName(), layer.getOrignName(),
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
				DTDXFLayerList deletedLayerList = new DTDXFLayerList();
				for (int i = 0; i < layerNames.size(); i++) {
					String layerName = (String) layerNames.get(i);
					DTDXFLayer deleteLayer = new DTDXFLayer();
					deleteLayer.setLayerID(layerName);
					deletedLayerList.add(deleteLayer);
				}
				editQA10Collection.addAllDeleteLayer(deletedLayerList);
				editQA10Collection.setDeleted(true);

			} else if (state.equals("modify")) {
				DTDXFLayerList modifiedLayerList = new DTDXFLayerList();
				JSONArray layerArr = (JSONArray) collectionObj.get(state);
				for (int i = 0; i < layerArr.size(); i++) {
					JSONObject layerObj = (JSONObject) layerArr.get(i);
					EditDTLayerParser layerParser = new EditDTLayerParser(type, layerObj, state);
					EditDXFLayer layer = layerParser.getEditQA10Layer();
					// layerName : currentName
					DTDXFLayer modifiedLayer = new DTDXFLayer(layer.getLayerName(), layer.getOrignName());
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

		this.editQA20Collection = new EditNGICollection();
		Iterator iterator = collectionObj.keySet().iterator();
		while (iterator.hasNext()) {
			String state = (String) iterator.next();
			if (state.equals("create")) {
				DTNGILayerList qa20LayerList = new DTNGILayerList();
				JSONArray layerArr = (JSONArray) collectionObj.get(state);
				for (int i = 0; i < layerArr.size(); i++) {
					JSONObject layerObj = (JSONObject) layerArr.get(i);
					EditDTLayerParser layerParser = new EditDTLayerParser(type, layerObj, state);
					EditNGI0Layer layer = layerParser.getEditQA20Layer();
					DTNGILayer createLayer = new DTNGILayer(String.valueOf(i), layer.getLayerName(), layer.getNgiHeader(),
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
				DTNGILayerList deletedLayerList = new DTNGILayerList();
				for (int i = 0; i < layerNames.size(); i++) {
					String layerName = (String) layerNames.get(i);
					DTNGILayer deleteLayer = new DTNGILayer();
					deleteLayer.setLayerName(layerName);
					deletedLayerList.add(deleteLayer);
				}
				editQA20Collection.addAllDeleteLayer(deletedLayerList);
				editQA20Collection.setDeleted(true);
			} else if (state.equals("modify")) {
				DTNGILayerList modifiedLayerList = new DTNGILayerList();
				JSONArray layerArr = (JSONArray) collectionObj.get(state);
				for (int i = 0; i < layerArr.size(); i++) {
					JSONObject layerObj = (JSONObject) layerArr.get(i);
					EditDTLayerParser layerParser = new EditDTLayerParser(type, layerObj, state);
					EditNGI0Layer layer = layerParser.getEditQA20Layer();
					String layerName = layer.getLayerName();
					DTNGILayer modifiedLayer = new DTNGILayer(String.valueOf(i), layerName, layer.getNgiHeader(),
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
