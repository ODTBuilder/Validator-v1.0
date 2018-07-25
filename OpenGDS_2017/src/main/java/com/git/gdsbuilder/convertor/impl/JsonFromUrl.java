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

package com.git.gdsbuilder.convertor.impl;

import java.io.IOException;
import java.util.Properties;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.SchemaException;
import org.json.simple.JSONObject;

import com.git.gdsbuilder.convertor.DataConvertor;
import com.git.gdsbuilder.geolayer.data.DTGeoLayer;
import com.git.gdsbuilder.geoserver.factory.DTGeoserverPublisher;
import com.git.gdsbuilder.geoserver.factory.DTGeoserverReader;
import com.git.gdsbuilder.geosolutions.geoserver.rest.GeoServerRESTReader;

public class JsonFromUrl {
	private static DTGeoserverReader dtReader;
	static {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			properties.load(classLoader.getResourceAsStream("geoserver.properties"));
			dtReader = new DTGeoserverReader(properties.getProperty("url"), properties.getProperty("id"),
					properties.getProperty("pw"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SimpleFeatureCollection readJsonFromUrl(String collectionName, String layerName, int max, boolean flag,
			String fileType) throws IOException, SchemaException {

		try {
			DataConvertor dtConvertor = new DataConvertorImpl();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			Properties properties = new Properties();

			properties.load(classLoader.getResourceAsStream("geoserver.properties"));

			String serverUrl = properties.getProperty("url");
			String wsName = properties.getProperty("user");
			String fullLayerName = "geo_" + fileType + "_" + collectionName + "_" + layerName;
			/*String urlStr = serverUrl + "/" + wsName + "/ows?service=WFS&version=1.0.0&request=GetFeature&typeName="
					+ wsName + "%3A" + fullLayerName + "&maxFeatures=" + max + "&outputformat=json";*/
			String urlStr = serverUrl + "/" + wsName + "/ows?service=WFS&version=1.0.0&request=GetFeature&typeName="
					+ wsName + "%3A" + fullLayerName + "&outputformat=json";
			JSONObject geo = dtConvertor.convertJsonFromURL(urlStr);
			DTGeoLayer dtGeoLayer = dtReader.getDTGeoLayer(wsName, fullLayerName);
			JSONObject attribute = dtGeoLayer.getAttInfo();

			if (!flag) {
				SimpleFeatureCollection simpleFeatureCollection = dtConvertor.geoJsonToSimpleFeatureCollecion(geo);
				return simpleFeatureCollection;
			}

			SimpleFeatureCollection simpleFeatureCollection = dtConvertor.geoJsonToSimpleFeatureCollecion(geo,
					attribute);
			return simpleFeatureCollection;
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private JSONObject getNativeLayerType(JSONObject geo, String layerFullName) {

		int dash = layerFullName.lastIndexOf("_");
		String layerType = layerFullName.substring(dash + 1);

		geo.put("layerID", layerFullName);
		geo.put("nativeType", layerType);

		return geo;
	}

}
