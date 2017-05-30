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

package com.git.gdsbuilder.convertor.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GeoserverUrlToGeoJsonImpl {
	/**
	 *
	 * @author JY.Kim
	 * @Date 2017. 5. 9. 오후 5:49:39
	 * @param urlStr - Geoserver의 json URL
	 * @return JSONObject - Geoserver 레이어의 JSONObject
	 * @throws IOException JSONObject
	 * @throws
	 * */
	public JSONObject convertJsonFromUrl(String urlStr) throws IOException{
		URL request = new URL(urlStr);
		URLConnection connection = request.openConnection();
		connection.setDoOutput(true);
		JSONParser jsonParser = new JSONParser();
		JSONObject geo = new JSONObject();
		try {
			geo = (JSONObject) jsonParser.parse(new InputStreamReader(request.openStream(), "UTF-8"));
			return geo;
		} catch (ParseException e) {
			return null;
		}
	}
	
}
