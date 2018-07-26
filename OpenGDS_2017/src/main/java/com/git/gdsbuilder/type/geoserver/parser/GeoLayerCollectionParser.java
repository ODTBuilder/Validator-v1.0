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

package com.git.gdsbuilder.type.geoserver.parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.feature.SchemaException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.git.gdsbuilder.FileRead.en.EnFileFormat;
import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollection;
import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollectionList;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayer;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerList;

/**
 * JSONObject를 LayerCollection 객체로 파싱하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 1:35:41
 */
public class GeoLayerCollectionParser {

	private JSONObject collectionObj;
	private GeoLayerCollectionList layerCollections;
	private String workspaceName;
	private String getCapabilities;
	private EnFileFormat fileFormat;
	private DataStore dataStore;
	private List<String> validateLayerList;

	/**
	 * LayerCollectionParser 생성자
	 * 
	 * @param collectionObject
	 * @param validateLayerList
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws SchemaException
	 */
	public GeoLayerCollectionParser(JSONObject collectionObject, String workspaceName, String getCapabilities,
			EnFileFormat fileFormat, List<String> validateLayerList)
			throws FileNotFoundException, IOException, ParseException, SchemaException {
		this.workspaceName = workspaceName;
		this.collectionObj = collectionObject;
		this.getCapabilities = getCapabilities;
		this.fileFormat = fileFormat;
		this.validateLayerList = validateLayerList;
		Map connectionParameters = new HashMap();
		connectionParameters.put("WFSDataStoreFactory:GET_CAPABILITIES_URL", getCapabilities);
		connectionParameters.put("WFSDataStoreFactory.TIMEOUT.key", 999999999);
	//	connectionParameters.put("WFSDataStoreFactory:BUFFER_SIZE", 999999999);
		this.dataStore = DataStoreFinder.getDataStore(connectionParameters);
		collectionParser();
	}

	/**
	 * layerCollections getter @author DY.Oh @Date 2017. 3. 11. 오후
	 * 1:36:58 @return LayerCollectionList @throws
	 */
	public GeoLayerCollectionList getLayerCollections() {
		return layerCollections;
	}

	/**
	 * layerCollections setter @author DY.Oh @Date 2017. 3. 11. 오후
	 * 1:37:01 @param layerCollections void @throws
	 */
	public void setLayerCollections(GeoLayerCollectionList layerCollections) {
		this.layerCollections = layerCollections;
	}

	public String getWorkspaceName() {
		return workspaceName;
	}

	public void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
	}

	public String getGetCapabilities() {
		return getCapabilities;
	}

	public void setGetCapabilities(String getCapabilities) {
		this.getCapabilities = getCapabilities;
	}

	public EnFileFormat getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(EnFileFormat fileFormat) {
		this.fileFormat = fileFormat;
	}

	/**
	 * 레이어 정의정보가 저장되어있는 JSONObject를 LayerCollection 객체로 파싱하는 클래스 @author
	 * DY.Oh @Date 2017. 3. 11. 오후 1:37:03 @throws FileNotFoundException @throws
	 * IOException @throws ParseException @throws SchemaException void @throws
	 */
	private void collectionParser() throws FileNotFoundException, IOException, ParseException, SchemaException {

		JSONArray collectionNames = (JSONArray) collectionObj.get("collectionName");
		String neatLineLayerName = (String) collectionObj.get("neatLineLayer");
		JSONArray layerNames = (JSONArray) collectionObj.get("layers");
		JSONArray checkedLayerNames = checkValidateLayerList(layerNames);

		this.layerCollections = new GeoLayerCollectionList();
		for (int i = 0; i < collectionNames.size(); i++) {
			// 도엽이름
			String collectionName = (String) collectionNames.get(i);
			GeoLayerCollection layerCollection = new GeoLayerCollection(collectionName);
			// 도곽선
			GeoLayerParser neatLineParser = new GeoLayerParser(workspaceName, dataStore, fileFormat, collectionName,
					neatLineLayerName);
			GeoLayer neatLineLayer = neatLineParser.getLayer();
			layerCollection.setNeatLine(neatLineLayer);
			// 레이어
			GeoLayerParser layersParser = new GeoLayerParser(workspaceName, dataStore, fileFormat, collectionName,
					checkedLayerNames);
			GeoLayerList layerList = layersParser.getLayerList();
			if (layerList != null) {
				layerCollection.setLayers(layerList);
				layerCollection.setFileFormat(fileFormat);
				layerCollections.add(layerCollection);
			}
		}
	}

	private JSONArray checkValidateLayerList(JSONArray layerNames) {
		JSONArray checkedLayerNames = new JSONArray();
		for (int i = 0; i < layerNames.size(); i++) {
			String layer = ((String) layerNames.get(i)).replaceAll(" ", "");
			for (int j = 0; j < validateLayerList.size(); j++) {
				String validateLayer = validateLayerList.get(j);
				if (layer.equals(validateLayer)) {
					checkedLayerNames.add(layer);
				} else {
					continue;
				}
			}
		}
		return checkedLayerNames;
	}
}
