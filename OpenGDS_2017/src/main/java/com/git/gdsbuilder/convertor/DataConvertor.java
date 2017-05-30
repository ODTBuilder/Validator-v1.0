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

package com.git.gdsbuilder.convertor;

import java.io.IOException;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.SchemaException;
import org.json.simple.JSONObject;

/**
 * 데이터 변환을 지원하는 클래스
 * @author SG.Lee
 * @Date 2017. 5. 11. 오전 12:06:59
 * */
public interface DataConvertor {

	/**
	 * SimpleFeatureCollection클래스를 JSONObject클래스로 변환
	 * @author SG.Lee
	 * @Date 2017. 2
	 * @param collection
	 * @return JSONObject
	 * @throws
	 * */
	public JSONObject simpleToGeojson(SimpleFeatureCollection collection);
	
	/**
	 * 속성을 가진 JSONObject를 SimpleFeatureCollection으로 변환하여 반환한다.
	 * @author SG.Lee
	 * @Date 2017. 2
	 * @param geojson
	 * @param attribute
	 * @return SimpleFeatureCollection
	 * @throws
	 * */
	public SimpleFeatureCollection geoJsonToSimpleFeatureCollecion(JSONObject geojson, JSONObject attribute) throws SchemaException;
	
	/**
	 * 속성이 없는 JSONObject를 SimpleFeatureCollection으로 변환하여 반환한다.
	 * @author SG.Lee
	 * @Date 2017. 2
	 * @param geojson
	 * @return SimpleFeatureCollection
	 * @throws
	 * */
	public SimpleFeatureCollection geoJsonToSimpleFeatureCollecion(JSONObject geojson) throws SchemaException;
	
	/**
	 * Geoserver WFS JSON요청 URL을 JSONObject클래스로 변환하여 반환한다.
	 * @author SG.Lee
	 * @Date 2017. 2
	 * @param urlStr
	 * @return JSONObject
	 * @throws
	 * */
	public JSONObject convertJsonFromURL(String urlStr) throws IOException;
}
