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

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.SchemaException;
import org.json.simple.JSONObject;

import com.git.gdsbuilder.convertor.DataConvertor;

/**
 * 데이터 변환을 수행하는 클래스
 * @author SG.Lee
 * @Date 2017. 4
 * */
public class DataConvertorImpl implements DataConvertor {
	
	/**
	 * @since 2017. 4
	 * @author SG.Lee
	 * @param collection
	 * @return
	 * @see com.git.gdsbuilder.convertor.DataConvertor#simpleToGeojson(org.geotools.data.simple.SimpleFeatureCollection)
	 */
	@Override
	public JSONObject simpleToGeojson(SimpleFeatureCollection collection) {
		return new SimpleToGeojsonImpl().build(collection);
	};

	/**
	 * @since 2017. 4
	 * @author SG.Lee
	 * @param geojson
	 * @param attribute
	 * @return
	 * @throws SchemaException
	 * @see com.git.gdsbuilder.convertor.DataConvertor#geoJsonToSimpleFeatureCollecion(org.json.simple.JSONObject, org.json.simple.JSONObject)
	 */
	@Override
	public SimpleFeatureCollection geoJsonToSimpleFeatureCollecion(JSONObject geojson, JSONObject attribute) throws SchemaException {
		return new GeoJsonToSimpleImpl().converToSimpleFeatureCollection(geojson, attribute);
	};

	/**
	 * @since 2017. 4
	 * @author SG.Lee
	 * @param geojson
	 * @return
	 * @throws SchemaException
	 * @see com.git.gdsbuilder.convertor.DataConvertor#geoJsonToSimpleFeatureCollecion(org.json.simple.JSONObject)
	 */
	@Override
	public SimpleFeatureCollection geoJsonToSimpleFeatureCollecion(JSONObject geojson) throws SchemaException {
		return new GeoJsonToSimpleImpl().converToSimpleFeatureCollection(geojson);
	};

	/**
	 * @since 2017. 4
	 * @author SG.Lee
	 * @param urlStr
	 * @return
	 * @throws IOException
	 * @see com.git.gdsbuilder.convertor.DataConvertor#convertJsonFromURL(java.lang.String)
	 */
	@Override
	public JSONObject convertJsonFromURL(String urlStr) throws IOException {
		return new GeoserverUrlToGeoJsonImpl().convertJsonFromUrl(urlStr);
	};
}
