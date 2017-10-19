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
 * GeoServer로부터 레이어 정보를 받아오는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 1:39:13
 */
public class GeoLayerParser {

	private String workspaceName;
	private String getCapabilities; // http://서버주소/geoserver/wfs?REQUEST=GetCapabilities&version=1.0.0
	private DataStore dataStore;
	private EnFileFormat fileFormat;
	private String collectionName;
	private String layerName;
	private JSONArray layers;
	private GeoLayer layer;
	private GeoLayerList layerList;

	/**
	 * LayerParser 생성자
	 * 
	 * @param collectionName
	 * @param layerName
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
	 * LayerParser 생성자
	 * 
	 * @param collectionName
	 * @param layers
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

	public String getWorkspaceName() {
		return workspaceName;
	}

	public void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
	}

	/**
	 * layer getter @author DY.Oh @Date 2017. 3. 11. 오후 1:39:21 @return
	 * Layer @throws
	 */
	public GeoLayer getLayer() {
		return layer;
	}

	/**
	 * layer setter @author DY.Oh @Date 2017. 3. 11. 오후 1:39:30 @param layer
	 * void @throws
	 */
	public void setLayer(GeoLayer layer) {
		this.layer = layer;
	}

	/**
	 * layerList getter @author DY.Oh @Date 2017. 3. 11. 오후 1:39:28 @return
	 * LayerList @throws
	 */
	public GeoLayerList getLayerList() {
		return layerList;
	}

	/**
	 * layerList setter @author DY.Oh @Date 2017. 3. 11. 오후 1:39:26 @param
	 * layerList void @throws
	 */
	public void setLayerList(GeoLayerList layerList) {
		this.layerList = layerList;
	}

	public String getGetCapabilities() {
		return getCapabilities;
	}

	public void setGetCapabilities(String getCapabilities) {
		this.getCapabilities = getCapabilities;
	}

	/**
	 * 해당하는 Layer 객체를 GeoServer로부터 받아 반환 @author DY.Oh @Date 2017. 3. 11. 오후
	 * 1:39:24 @return @throws FileNotFoundException @throws IOException @throws
	 * ParseException @throws SchemaException Layer @throws
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
				// SimpleFeatureIterator iter = sfc.features();
				// while(iter.hasNext()) {
				// SimpleFeature sf = iter.next();
				// System.out.println(sf.getDefaultGeometry().toString());
				// }
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
	 * 해당하는 Layer 객체를 GeoServer로부터 받아 반환 @author DY.Oh @Date 2017. 3. 11. 오후
	 * 1:39:35 @return @throws FileNotFoundException @throws IOException @throws
	 * ParseException @throws SchemaException LayerList @throws
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
