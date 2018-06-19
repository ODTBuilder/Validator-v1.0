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
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.SchemaException;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import com.git.gdsbuilder.FileRead.en.EnFileFormat;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayer;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerList;

/**
 * GeoServer로부터 레이어 정보를 받아오는 클래스. GeoLayer 객체로 파싱
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 1:39:13
 */
public class GeoLayerParser {

	/**
	 * Geoserver 작업공간 이름
	 */
	private String workspaceName;
	private String getCapabilities; // http://서버주소/geoserver/wfs?REQUEST=GetCapabilities&version=1.0.0
	/**
	 * Geoserver 저장소 이름
	 */
	private DataStore dataStore;
	/**
	 * 파일포맷
	 */
	private EnFileFormat fileFormat;
	/**
	 * 파일이름
	 */
	private String collectionName;
	/**
	 * 레이어 이름
	 */
	private String layerName;
	/**
	 * 
	 */
	private JSONArray layers;
	/**
	 * Geoserver 레이어 정보를 담고 있는 GeoLayer 객체
	 */
	private GeoLayer layer;
	/**
	 * 다수의 Geoserver 레이어 정보를 담고 있는 GeoLayer 객체 목록
	 */
	private GeoLayerList layerList;

	/**
	 * GeoLayerParser 생성자. Geoserver 레이어를 GeoLayer 객체로 파싱
	 * 
	 * @param workspaceName
	 *            작업공간 이름
	 * @param dataStore
	 *            저장소 이름
	 * @param fileFormat
	 *            파일포맷
	 * @param collectionName
	 *            파일이름
	 * @param layerName
	 *            레이어 이름
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws SchemaException
	 */
	public GeoLayerParser(String workspaceName, DataStore dataStore, EnFileFormat fileFormat, String collectionName,
			String layerName) throws FileNotFoundException, IOException, ParseException, SchemaException {
		this.workspaceName = workspaceName;
		this.collectionName = collectionName;
		this.layerName = layerName;
		this.fileFormat = fileFormat;
		this.dataStore = dataStore;
		this.layer = layerParse();
	}

	/**
	 * GeoLayerParser 생성자. Geoserver 레이어를 GeoLayer 객체로 파싱
	 * 
	 * @param workspaceName
	 *            작업공간 이름
	 * @param getCapabilities
	 * @param fileFormat
	 *            파일포맷
	 * @param collectionName
	 *            파일이름
	 * @param layerName
	 *            레이어 이름
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws SchemaException
	 */
	public GeoLayerParser(String workspaceName, String getCapabilities, EnFileFormat fileFormat, String collectionName,
			String layerName) throws FileNotFoundException, IOException, ParseException, SchemaException {
		this.workspaceName = workspaceName;
		this.getCapabilities = getCapabilities;
		this.collectionName = collectionName;
		this.layerName = layerName;
		this.fileFormat = fileFormat;
		Map connectionParameters = new HashMap();
		connectionParameters.put("WFSDataStoreFactory:GET_CAPABILITIES_URL", getCapabilities);
		connectionParameters.put("WFSDataStoreFactory.TIMEOUT.key", 999999999);
		this.dataStore = DataStoreFinder.getDataStore(connectionParameters);
		this.layer = layerParse();
	}

	/**
	 * GeoLayerParser 생성자. 다수의 Geoserver 레이어를 GeoLayerList 객체로 파싱
	 * 
	 * @param workspaceName
	 *            작업공간 이름
	 * @param dataStore
	 *            저장소 이름
	 * @param fileFormat
	 *            파일포맷
	 * @param collectionName
	 *            파일이름
	 * @param layers
	 *            레이어 이름 목록
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws SchemaException
	 */
	public GeoLayerParser(String workspaceName, DataStore dataStore, EnFileFormat fileFormat, String collectionName,
			JSONArray layers) throws FileNotFoundException, IOException, ParseException, SchemaException {
		this.workspaceName = workspaceName;
		this.collectionName = collectionName;
		this.layers = layers;
		this.fileFormat = fileFormat;
		this.dataStore = dataStore;
		this.layerList = layersParse();
	}

	/**
	 * 작업공간 이름 반환
	 * 
	 * @return String
	 */
	public String getWorkspaceName() {
		return workspaceName;
	}

	/**
	 * 작업공간 이름 설정
	 * 
	 * @param workspaceName
	 *            작업공간 이름
	 */
	public void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
	}

	/**
	 * GeoLayer 객체 반환
	 * 
	 * @return GeoLayer
	 */
	public GeoLayer getLayer() {
		return layer;
	}

	/**
	 * GeoLayer 객체 설정
	 * 
	 * @param layer
	 *            GeoLayer 객체
	 */
	public void setLayer(GeoLayer layer) {
		this.layer = layer;
	}

	/**
	 * GeoLayerList 객체 반환
	 * 
	 * @return GeoLayerList
	 */
	public GeoLayerList getLayerList() {
		return layerList;
	}

	/**
	 * GeoLayerList 객체 설정
	 * 
	 * @param layerList
	 *            GeoLayerList 객체
	 */
	public void setLayerList(GeoLayerList layerList) {
		this.layerList = layerList;
	}

	/**
	 * @return
	 */
	public String getGetCapabilities() {
		return getCapabilities;
	}

	/**
	 * @param getCapabilities
	 */
	public void setGetCapabilities(String getCapabilities) {
		this.getCapabilities = getCapabilities;
	}

	/**
	 * GeoServer로부터 레이어 정보를 받아 GeoLayer 객체로 변환하여 반환
	 * 
	 * @return GeoLayer
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws SchemaException
	 */
	@SuppressWarnings("unchecked")
	public GeoLayer layerParse() throws FileNotFoundException, IOException, ParseException, SchemaException {
		SimpleFeatureCollection sfc = null;
		if (this.dataStore != null) {
			String fullLayerName = "geo_" + fileFormat.getStateName() + "_" + collectionName + "_" + layerName;
			System.out.println(fullLayerName);
			try {
				SimpleFeatureSource source = this.dataStore.getFeatureSource(workspaceName + ":" + fullLayerName);
				sfc = source.getFeatures();
			} catch (NullPointerException e) {
				return null;
			}
			if (sfc != null) {
				GeoLayer layer = new GeoLayer();
				layer.setLayerName(this.layerName);
				layer.setLayerType("layerType");
				layer.setSimpleFeatureCollection(sfc);
				return layer;
			} else {
				return null;
			}
		} else
			return null;
	}

	/**
	 * GeoServer로부터 다수의 레이어 정보를 받아 GeoLayerList 객체로 변환하여 반환
	 * 
	 * @return GeoLayerList
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws SchemaException
	 */
	public GeoLayerList layersParse() throws FileNotFoundException, IOException, ParseException, SchemaException {

		GeoLayerList layerList = new GeoLayerList();
		for (int i = 0; i < layers.size(); i++) {
			this.layerName = (String) layers.get(i);
			GeoLayer layer = layerParse();
			if (layer != null) {
				layerList.add(layer);
			}
		}
		return layerList;
	}
}
