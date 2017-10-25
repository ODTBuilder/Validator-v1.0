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

package com.git.opengds.parser.edit.feature;

import java.util.Iterator;
import java.util.Set;

import org.geotools.data.DataUtilities;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.json.simple.JSONObject;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.geojson.GeoJsonReader;

/**
 * JSONObject를 QA20Feature 객체로 파싱하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 1:49:32
 */
public class EditDTFeatureParser {

	JSONObject featureObj;
	SimpleFeature shpFeature;

	public EditDTFeatureParser(String type, JSONObject featureObj, String state)
			throws ParseException, SchemaException {
		this.featureObj = featureObj;
		if (type.equals("shp")) {
			shpFeatureParse();
		}
	}

	public SimpleFeature getSHPFeature() {
		return shpFeature;
	}

	public void setSHPFeature(SimpleFeature shpFeature) {
		this.shpFeature = shpFeature;
	}

	private void shpFeatureParse() throws ParseException, SchemaException {

		// SimpleFeature
		SimpleFeatureType simpleFeatureType = null;

		String featureID = (String) featureObj.get("id");
		GeoJsonReader re = new GeoJsonReader();
		JSONObject geomObj = (JSONObject) featureObj.get("geometry");
		String geomStr = geomObj.toJSONString();
		Geometry geom = re.read(geomStr);

		JSONObject propertiesObj = (JSONObject) featureObj.get("properties");
		Set keySet = propertiesObj.keySet();
		int keySize = keySet.size();

		String keysStr = "";
		Object[] values = new Object[keySize + 1];

		// geometry
		keysStr += "geom:" + geom.getGeometryType() + ",";
		values[0] = geom;

		int i = 1;
		Iterator iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			// if (!key.equals("feature_id") && !key.equals("feature_type")) {
			Object value = propertiesObj.get(key);
			String typeStr = value.getClass().getSimpleName();

			if (typeStr.equals("Long")) {
				typeStr = "String";
				values[i] = value.toString();
				keysStr += key + ":" + typeStr + ",";
				i++;
			} else {
				values[i] = value;
				keysStr += key + ":" + typeStr + ",";
				i++;
			}
			// }
		}

		simpleFeatureType = DataUtilities.createType(featureID.toString(), keysStr.substring(0, keysStr.length() - 1));
		this.shpFeature = SimpleFeatureBuilder.build(simpleFeatureType, values, featureID);
	}
}
