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

import java.util.Iterator;

import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class GeoJsonToSimpleImpl {
	/**
	 * 속성을 가진 JSONObject를 SimpleFeatureCollection으로 변환하여 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @param geo
	 *            변환할 JSONObject
	 * @param attribute
	 *            속성값
	 * @return JSONObject
	 * @throws SchemaException
	 */
	public SimpleFeatureCollection converToSimpleFeatureCollection(JSONObject geo, JSONObject attribute)
			throws SchemaException {

		return buildFeatureCollection(geo, attribute);
	}

	private SimpleFeatureCollection buildFeatureCollection(JSONObject geo, JSONObject attribute)
			throws SchemaException {

		DefaultFeatureCollection defaultFeatureCollection = new DefaultFeatureCollection();

		JSONArray features = (JSONArray) geo.get("features");
		int featureSize = features.size();
		for (int i = 0; i < featureSize; i++) {
			JSONObject feature = (JSONObject) features.get(i);
			if (feature.get("properties") != null) {
				defaultFeatureCollection.add(buildFeature(feature, attribute));
			} else {
				defaultFeatureCollection.add(buildFeature(feature));
			}
		}
		return defaultFeatureCollection;
	}

	@SuppressWarnings("rawtypes")
	private SimpleFeature buildFeature(JSONObject feature, JSONObject attribute) throws SchemaException {

		JSONObject property = (JSONObject) feature.get("properties");
		String featureID = feature.get("id").toString();
		Geometry geometry = buildGeometry(feature);

		String geometryType = geometry.getGeometryType();
		// SimpleFeature
		SimpleFeatureType simpleFeatureType = null;
		SimpleFeature simpleFeature = null;

		int size = attribute.keySet().size() + 1;
		Object[] objects;
		objects = new Object[size];
		objects[0] = geometry;

		int j = 1;
		String temp = "";
		Iterator iterator = attribute.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			Object value = property.get(key);
			String valueType = (String) attribute.get(key);

			if (valueType.equals("Long")) {
				valueType = "String";
				objects[j] = value.toString();
				temp += key + ":" + valueType + ",";
				j++;
			} else {
				objects[j] = value;
				temp += key + ":" + valueType + ",";
				j++;
			}
		}
		simpleFeatureType = DataUtilities.createType(featureID.toString(),
				"the_geom:" + geometryType + "," + temp.substring(0, temp.length() - 1));
		simpleFeature = SimpleFeatureBuilder.build(simpleFeatureType, objects, featureID);

		return simpleFeature;
	}

	/**
	 * 속성이 없는 JSONObject를 SimpleFeatureCollection으로 변환하여 반환한다.
	 * 
	 * @author dayeon.oh
	 * @data 2016.02
	 * @param geo
	 *            변환할 JSONObject
	 * @return JSONObject
	 * @throws SchemaException
	 */
	public SimpleFeatureCollection converToSimpleFeatureCollection(JSONObject geo) throws SchemaException {

		return buildFeatureCollection(geo);

	}

	private SimpleFeatureCollection buildFeatureCollection(JSONObject geo) throws SchemaException {

		DefaultFeatureCollection defaultFeatureCollection = new DefaultFeatureCollection();

		JSONArray features = (JSONArray) geo.get("features");
		int featuresSize = features.size();
		for (int i = 0; i < featuresSize; i++) {
			JSONObject feature = (JSONObject) features.get(i);
			defaultFeatureCollection.add(buildFeature(feature));
		}
		return defaultFeatureCollection;
	}

	private SimpleFeature buildFeature(JSONObject feature) throws SchemaException {

		String featureID = (String) feature.get("id");
		Geometry geometry = buildGeometry(feature);
		String geometryType = geometry.getGeometryType();
		SimpleFeatureType simpleFeatureType = simpleFeatureType = DataUtilities.createType(featureID,
				"the_geom:" + geometryType);
		SimpleFeature simpleFeature = simpleFeature = SimpleFeatureBuilder.build(simpleFeatureType,
				new Object[] { geometry }, featureID);
		return simpleFeature;
	}

	@SuppressWarnings("unchecked")
	private Geometry buildGeometry(JSONObject feature) {

		GeometryFactory geometryFactory = new GeometryFactory();

		JSONObject jsonGeometry = (JSONObject) feature.get("geometry");
		String jsonGeometryType = (String) jsonGeometry.get("type");

		// SimpleFeature
		if (jsonGeometryType.equals("Point")) {
			// Geometry
			JSONArray coordinates = (JSONArray) jsonGeometry.get("coordinates");
			JSONArray doubleCoordinates = new JSONArray();
			for (int i = 0; i < coordinates.size(); i++) {
				Object object = coordinates.get(i);
				String type = object.getClass().getSimpleName();
				if(type.equals("Integer") || type.equals("Long")){
					String strObj = object.toString();
					Double douObj = Double.valueOf(strObj).doubleValue();
					doubleCoordinates.add(douObj);
				}else{
					Double douObj = (Double) coordinates.get(i);
					doubleCoordinates.add(douObj);
				}
			}
			//Double x = (Double) coordinates.get(0);
			//Double y = (Double) coordinates.get(1);
			Double x = (Double) doubleCoordinates.get(0);
			Double y = (Double) doubleCoordinates.get(1);
			Point point = geometryFactory.createPoint(new Coordinate(x, y));
			return point;

		} else if (jsonGeometryType.equals("LineString")) {
			// Geometry
			JSONArray outerCoordinates = (JSONArray) jsonGeometry.get("coordinates");
			Coordinate[] coordinateArray;
			coordinateArray = new Coordinate[outerCoordinates.size()];

			int outerCoordSize = outerCoordinates.size();
			for (int k = 0; k < outerCoordSize; k++) {
				JSONArray innerCoordinates = (JSONArray) outerCoordinates.get(k);
				JSONArray doubleInnerCoordinates = new JSONArray();
				for (int i = 0; i < innerCoordinates.size(); i++) {
					Object object = innerCoordinates.get(i);
					String type = object.getClass().getSimpleName();
					if(type.equals("Integer") || type.equals("Long")){
						String strObj = object.toString();
						Double douObj = Double.valueOf(strObj).doubleValue();
						doubleInnerCoordinates.add(douObj);
					}else{
						Double douObj = (Double) innerCoordinates.get(i);
						doubleInnerCoordinates.add(douObj);
					}
				}
				
				//Double x = (Double) innerCoordinates.get(0);
				//Double y = (Double) innerCoordinates.get(1);
				Double x = (Double) doubleInnerCoordinates.get(0);
				Double y = (Double) doubleInnerCoordinates.get(1);
				
				coordinateArray[k] = new Coordinate(x, y);
			}
			LineString lineString = geometryFactory.createLineString(coordinateArray);
			return lineString;

		} else if (jsonGeometryType.equals("Polygon")) {

			// Geometry
			JSONArray outerCoordinates = (JSONArray) jsonGeometry.get("coordinates");

			Coordinate[] coordinateArray;
			LinearRing linearRing = null;
			LinearRing holes[] = null;
			Polygon polygon = null;

			int outerCoordSize = outerCoordinates.size();
			for (int k = 0; k < outerCoordSize; k++) {
				JSONArray innerCoordinates = (JSONArray) outerCoordinates.get(k);
				coordinateArray = new Coordinate[innerCoordinates.size()];
				int innerCoorSize = innerCoordinates.size();
				for (int r = 0; r < innerCoorSize; r++) {
					JSONArray innerCoor = (JSONArray) innerCoordinates.get(r);
					JSONArray doubleInnerCoor = new JSONArray();
					
					for (int i = 0; i < innerCoor.size(); i++) {
						Object object = innerCoor.get(i);
						String type = object.getClass().getSimpleName();
						if(type.equals("Integer") || type.equals("Long")){
							String strObj = object.toString();
							Double douObj = Double.valueOf(strObj).doubleValue();
							doubleInnerCoor.add(douObj);
						} else{
							Double douObj = (Double) innerCoor.get(i);
							doubleInnerCoor.add(douObj);
						}
					}						
					//Double x = (Double) innerCoor.get(0);
					//Double y = (Double) innerCoor.get(1);
					Double x = (Double) doubleInnerCoor.get(0);
					Double y = (Double) doubleInnerCoor.get(1);
					coordinateArray[r] = new Coordinate(x, y);
				}
				linearRing = geometryFactory.createLinearRing(coordinateArray);
			}
			polygon = geometryFactory.createPolygon(linearRing, holes);
			return polygon;

		} else if (jsonGeometryType.equals("MultiPoint")) {

			// Geometry
			JSONArray outerCoordinates = (JSONArray) jsonGeometry.get("coordinates");
			Coordinate[] coordinateArray;
			coordinateArray = new Coordinate[outerCoordinates.size()];
			int outerCoordSize = outerCoordinates.size();
			for (int k = 0; k < outerCoordSize; k++) {
				// Geometry
				JSONArray innerCoordinates = (JSONArray) outerCoordinates.get(k);
				JSONArray doubleInnerCoordinates = new JSONArray();
				
				for (int i = 0; i < innerCoordinates.size(); i++) {
					Object object = innerCoordinates.get(i);
					String type = object.getClass().getSimpleName();
					if(type.equals("Integer") || type.equals("Long")){
						String strObj = object.toString();
						Double douObj = Double.valueOf(strObj).doubleValue();
						doubleInnerCoordinates.add(douObj);
					}else{
						Double douObj = (Double) innerCoordinates.get(i);
						doubleInnerCoordinates.add(douObj);
					}
				}
				
				//Double x = (Double) innerCoordinates.get(0);
				//Double y = (Double) innerCoordinates.get(1);
				Double x = (Double) doubleInnerCoordinates.get(0);
				Double y = (Double) doubleInnerCoordinates.get(1);
				
				coordinateArray[k] = new Coordinate(x, y);
			}
			MultiPoint multiPoint = geometryFactory.createMultiPoint(coordinateArray);
			return multiPoint;

		} else if (jsonGeometryType.equals("MultiLineString")) {

			// Geometry
			JSONArray outerCoordinates = (JSONArray) jsonGeometry.get("coordinates");
			Coordinate[] coordinateArray;
			LineString lineStrings[] = new LineString[outerCoordinates.size()];

			int outerCoordSize = outerCoordinates.size();
			for (int k = 0; k < outerCoordSize; k++) {
				JSONArray innerCoordinates = (JSONArray) outerCoordinates.get(k);
				coordinateArray = new Coordinate[innerCoordinates.size()];

				int innerCoorSize = innerCoordinates.size();
				for (int r = 0; r < innerCoorSize; r++) {
					JSONArray innerCoor = (JSONArray) innerCoordinates.get(r);
					Double x = (Double) innerCoor.get(0);
					Double y = (Double) innerCoor.get(1);
					coordinateArray[r] = new Coordinate(x, y);
				}
				lineStrings[k] = geometryFactory.createLineString(coordinateArray);
			}
			MultiLineString multiLineString = geometryFactory.createMultiLineString(lineStrings);
			return multiLineString;

		} else if (jsonGeometryType.equals("MultiPolygon")) {

			// Geometry
			JSONArray firstOuter = (JSONArray) jsonGeometry.get("coordinates");
			Coordinate[] coordinateArray;
			LinearRing linearRing = null;
			LinearRing holes[] = null;
			Polygon[] polygons = new Polygon[firstOuter.size()];

			int firstOutSize = firstOuter.size();
			for (int a = 0; a < firstOutSize; a++) {
				JSONArray firstInnerCoor = (JSONArray) firstOuter.get(a);

				int firstInnerSize = firstInnerCoor.size();
				for (int k = 0; k < firstInnerSize; k++) {
					JSONArray secondInnerCoor = (JSONArray) firstInnerCoor.get(k);
					coordinateArray = new Coordinate[secondInnerCoor.size()];

					int secondInnerSize = secondInnerCoor.size();
					for (int r = 0; r < secondInnerSize; r++) {
						JSONArray thirdInnerCoor = (JSONArray) secondInnerCoor.get(r);
						JSONArray doubleThirdInnerCoor = new JSONArray();
						for (int i = 0; i < thirdInnerCoor.size(); i++) {
							Object object = thirdInnerCoor.get(i);
							String type = object.getClass().getSimpleName();
							if(type.equals("Integer") || type.equals("Long")){
								String strObj = object.toString();
								Double douObj = Double.valueOf(strObj).doubleValue();
								doubleThirdInnerCoor.add(douObj);
							}else{
								Double douObj = (Double) object;
								doubleThirdInnerCoor.add(douObj);
							}
						}
						
						//Double x = (Double) thirdInnerCoor.get(0);
						//Double y = (Double) thirdInnerCoor.get(1);
						
						Double x = (Double) doubleThirdInnerCoor.get(0);
						Double y = (Double) doubleThirdInnerCoor.get(1);
						coordinateArray[r] = new Coordinate(x, y);
					}
					linearRing = geometryFactory.createLinearRing(coordinateArray);
				}
				polygons[a] = geometryFactory.createPolygon(linearRing, holes);
			}
			MultiPolygon multiPolygon = geometryFactory.createMultiPolygon(polygons);
			return multiPolygon;
		}
		return null;
	}

}
