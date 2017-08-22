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

import com.git.gdsbuilder.edit.dxf.EditDXFLayer;
import com.git.gdsbuilder.edit.dxf.EditDXFLayerCollection;
import com.git.gdsbuilder.edit.ngi.EditNGILayer;
import com.git.gdsbuilder.edit.ngi.EditNGILayerCollection;
import com.git.gdsbuilder.edit.shp.EditSHPLayer;
import com.git.gdsbuilder.edit.shp.EditSHPLayerCollection;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayer;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayerList;
import com.git.gdsbuilder.type.ngi.layer.DTNGILayer;
import com.git.gdsbuilder.type.ngi.layer.DTNGILayerList;
import com.git.gdsbuilder.type.shp.layer.DTSHPLayer;
import com.git.gdsbuilder.type.shp.layer.DTSHPLayerList;
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
	EditNGILayerCollection editNGICollection;
	EditDXFLayerCollection editDXFCollection;
	EditSHPLayerCollection editSHPCollection;

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
		} else if (type.equals("shp")) {
			shpCollectionParser();
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

	public EditNGILayerCollection getEditNGICollection() {
		return editNGICollection;
	}

	public void setEditNGICollection(EditNGILayerCollection editNGICollection) {
		this.editNGICollection = editNGICollection;
	}

	public EditDXFLayerCollection getEditDXFCollection() {
		return editDXFCollection;
	}

	public void setEditDXFCollection(EditDXFLayerCollection editDXFCollection) {
		this.editDXFCollection = editDXFCollection;
	}

	public EditSHPLayerCollection getEditSHPCollection() {
		return editSHPCollection;
	}

	public void setEditSHPCollection(EditSHPLayerCollection editSHPCollection) {
		this.editSHPCollection = editSHPCollection;
	}

	public void shpCollectionParser() throws ParseException {

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
			} else if (state.equals("modify")) {
				// 잠시 보류
			}
		}
	}

	public void dxfCollectionParser() throws ParseException {

		this.editDXFCollection = new EditDXFLayerCollection();
		Iterator iterator = collectionObj.keySet().iterator();
		while (iterator.hasNext()) {
			String state = (String) iterator.next();
			if (state.equals("create")) {
				DTDXFLayerList createLayerList = new DTDXFLayerList();
				JSONArray layerArr = (JSONArray) collectionObj.get(state);
				for (int i = 0; i < layerArr.size(); i++) {
					JSONObject layerObj = (JSONObject) layerArr.get(i);
					EditDTLayerParser layerParser = new EditDTLayerParser(type, layerObj, state);
					EditDXFLayer layer = layerParser.getEditDXFLayer();
					DTDXFLayer createLayer = new DTDXFLayer(layer.getLayerName(), layer.getOrignName(),
							layer.getLayerType(), layer.getOriginLayerType());
					createLayerList.add(createLayer);
				}
				editDXFCollection.addAllCreateLayer(createLayerList);
				editDXFCollection.setCreated(true);
			} else if (state.equals("remove")) {
				JSONObject removeObj = (JSONObject) collectionObj.get(state);
				JSONArray layerNames = (JSONArray) removeObj.get("layer");
				String scope = (String) removeObj.get("scope");
				if (scope.equals("all")) {
					editDXFCollection.setDeleteAll(true);
				}
				DTDXFLayerList deletedLayerList = new DTDXFLayerList();
				for (int i = 0; i < layerNames.size(); i++) {
					String layerName = (String) layerNames.get(i);
					DTDXFLayer deleteLayer = new DTDXFLayer();
					deleteLayer.setLayerID(layerName);
					deletedLayerList.add(deleteLayer);
				}
				editDXFCollection.addAllDeleteLayer(deletedLayerList);
				editDXFCollection.setDeleted(true);
			} else if (state.equals("modify")) {
				// 잠시 보류
				DTDXFLayerList modifiedLayerList = new DTDXFLayerList();
				JSONArray layerArr = (JSONArray) collectionObj.get(state);
				for (int i = 0; i < layerArr.size(); i++) {
					JSONObject layerObj = (JSONObject) layerArr.get(i);
					EditDTLayerParser layerParser = new EditDTLayerParser(type, layerObj, state);
					EditDXFLayer layer = layerParser.getEditDXFLayer();
					// layerName : currentName
					DTDXFLayer modifiedLayer = new DTDXFLayer(layer.getLayerName(), layer.getOrignName());
					modifiedLayerList.add(modifiedLayer);

					Map<String, Object> geoLayerMap = new HashMap<String, Object>();
					geoLayerMap.put(layer.getOrignName(), layer.getGeoServerLayer());
					editDXFCollection.putGeoLayer(geoLayerMap);
				}
				editDXFCollection.addAllmodifiedLayer(modifiedLayerList);
				editDXFCollection.setModified(true);
			}
		}
	}

	public void ngiCollectionParser() throws ParseException {

		this.editNGICollection = new EditNGILayerCollection();
		Iterator iterator = collectionObj.keySet().iterator();
		while (iterator.hasNext()) {
			String state = (String) iterator.next();
			if (state.equals("create")) {
				DTNGILayerList qa20LayerList = new DTNGILayerList();
				JSONArray layerArr = (JSONArray) collectionObj.get(state);
				for (int i = 0; i < layerArr.size(); i++) {
					JSONObject layerObj = (JSONObject) layerArr.get(i);
					EditDTLayerParser layerParser = new EditDTLayerParser(type, layerObj, state);
					EditNGILayer layer = layerParser.getEditNGILayer();
					DTNGILayer createLayer = new DTNGILayer(String.valueOf(i), layer.getLayerName(),
							layer.getNgiHeader(), layer.getNdaHeader(), layer.getLayerType(), null);
					qa20LayerList.add(createLayer);
				}
				editNGICollection.addAllCreateLayer(qa20LayerList);
				editNGICollection.setCreated(true);
			} else if (state.equals("remove")) {
				JSONObject removeObj = (JSONObject) collectionObj.get(state);
				JSONArray layerNames = (JSONArray) removeObj.get("layer");
				String scope = (String) removeObj.get("scope");
				if (scope.equals("all")) {
					editNGICollection.setDeleteAll(true);
				}
				DTNGILayerList deletedLayerList = new DTNGILayerList();
				for (int i = 0; i < layerNames.size(); i++) {
					String layerName = (String) layerNames.get(i);
					DTNGILayer deleteLayer = new DTNGILayer();
					deleteLayer.setLayerName(layerName);
					deletedLayerList.add(deleteLayer);
				}
				editNGICollection.addAllDeleteLayer(deletedLayerList);
				editNGICollection.setDeleted(true);
			} else if (state.equals("modify")) {
				// 잠시 보류
				DTNGILayerList modifiedLayerList = new DTNGILayerList();
				JSONArray layerArr = (JSONArray) collectionObj.get(state);
				for (int i = 0; i < layerArr.size(); i++) {
					JSONObject layerObj = (JSONObject) layerArr.get(i);
					EditDTLayerParser layerParser = new EditDTLayerParser(type, layerObj, state);
					EditNGILayer layer = layerParser.getEditNGILayer();
					String layerName = layer.getLayerName();
					DTNGILayer modifiedLayer = new DTNGILayer(String.valueOf(i), layerName, layer.getNgiHeader(),
							layer.getNdaHeader(), layer.getLayerType(), null);
					modifiedLayer.setOriginLayerName(layer.getOrignName());
					modifiedLayerList.add(modifiedLayer);

					Map<String, Object> geoLayerMap = new HashMap<String, Object>();
					geoLayerMap.put(layerName, layer.getGeoServerLayer());
					editNGICollection.putGeoLayer(geoLayerMap);
				}
				editNGICollection.addAllmodifiedLayer(modifiedLayerList);
				editNGICollection.setModified(true);
			}
		}
	}
}
