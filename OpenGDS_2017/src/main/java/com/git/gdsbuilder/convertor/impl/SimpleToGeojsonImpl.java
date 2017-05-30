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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.geojson.geom.GeometryJSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;

import com.vividsolutions.jts.geom.Geometry;

public class SimpleToGeojsonImpl {
	
	/**
	 * SimpleFeatureCollection을 JSONObject로 변환하여 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @param simpleFeatureCollection
	 *            변환할 SimpleFeatureCollection
	 * @return JSONObject
	 */
	public JSONObject build(SimpleFeatureCollection simpleFeatureCollection) {

		return buildFeatureCollection(simpleFeatureCollection);
	}

	@SuppressWarnings("unchecked")
	private JSONObject buildFeatureCollection(SimpleFeatureCollection featureCollection) {

		JSONArray features = new JSONArray();
		
		JSONObject obj = new JSONObject();
		obj.put("type", "FeatureCollection");
		obj.put("features", features);
		SimpleFeatureIterator simpleFeatureIterator = featureCollection.features();

		// int i = 0;
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	private JSONObject buildFeature(SimpleFeature simpleFeature) {

		JSONObject obj = new JSONObject();
		obj.put("type", "Feature");
		obj.put("id", simpleFeature.getID());
		obj.put("geometry", buildGeometry((Geometry) simpleFeature.getDefaultGeometry()));
		obj.put("properties", buildProperties(simpleFeature));
		return obj;
	}

	@SuppressWarnings("unchecked")
	private JSONObject buildProperties(SimpleFeature simpleFeature) {

		JSONObject obj = new JSONObject();
		Collection<Property> properties = simpleFeature.getProperties();

		for (Property property : properties) {
			obj.put(property.getName().toString(), property.getValue() == null ? "" : property.getValue().toString());
		}
		return obj;
	}

	private List<String> buildPropertiesType(SimpleFeature simpleFeature) {

		Collection<Property> properties = simpleFeature.getProperties();
		List<String> typeArray = new ArrayList<String>();
		for (Property property : properties) {
			String tempType = property.getType().toString();
			int firstIndex = tempType.indexOf("<");
			int lastIndex = tempType.lastIndexOf(">");
			String propertyType = tempType.substring(firstIndex + 1, lastIndex);
			typeArray.add(propertyType);
		}
		return typeArray;
	}

	private JSONObject buildGeometry(Geometry geometry) {
		GeometryJSON gjson = new GeometryJSON();
		Object obj = JSONValue.parse(gjson.toString(geometry));
		JSONObject jsonObj = (JSONObject) obj;
		return jsonObj;
	}
}
