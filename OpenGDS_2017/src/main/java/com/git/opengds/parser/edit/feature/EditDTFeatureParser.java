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

import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONObject;

import com.git.gdsbuilder.type.dxf.feature.DTDXFFeature;
import com.git.gdsbuilder.type.ngi.feature.DTNGIFeature;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;
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
	DTNGIFeature qa20Feature;
	DTDXFFeature qa10Feature;

	public EditDTFeatureParser(String type, JSONObject featureObj, String state) throws ParseException {
		this.featureObj = featureObj;
		if (type.equals("dxf")) {
			dxfFeatureParse();
		} else if (type.equals("ngi")) {
			ngiFeatureParse();
		}
	}

	public DTNGIFeature getQa20Feature() {
		return qa20Feature;
	}

	public void setQa20Feature(DTNGIFeature qa20Feature) {
		this.qa20Feature = qa20Feature;
	}

	public DTDXFFeature getQa10Feature() {
		return qa10Feature;
	}

	public void setQa10Feature(DTDXFFeature qa10Feature) {
		this.qa10Feature = qa10Feature;
	}

	public void dxfFeatureParse() throws ParseException {

		String featureID = (String) featureObj.get("id");
		GeoJsonReader re = new GeoJsonReader();
		JSONObject geomObj = (JSONObject) featureObj.get("geometry");
		String geomStr = geomObj.toJSONString();
		Geometry geom = re.read(geomStr);

		qa10Feature = new DTDXFFeature(featureID, "", geom);
	}

	public void ngiFeatureParse() throws ParseException {

		String featureID = (String) featureObj.get("id");

		GeoJsonReader re = new GeoJsonReader();
		JSONObject geomObj = (JSONObject) featureObj.get("geometry");
		String geomStr = geomObj.toJSONString();
		Geometry geom = re.read(geomStr);

		String featureType = geom.getGeometryType();
		String coorSize = String.valueOf(geom.getNumGeometries());
		String numparts = null;

		if (featureType.equals("Polyon")) {
			Polygon polygon = (Polygon) geom;
			numparts = String.valueOf(polygon.getNumInteriorRing());
		}

		JSONObject propertiesObj = (JSONObject) featureObj.get("properties");
		HashMap<String, Object> properties = new HashMap<String, Object>();
		if (propertiesObj != null) {
			Iterator iterator = propertiesObj.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				if (!key.equals("feature_id") && !key.equals("feature_type") && !key.equals("num_rings")
						&& !key.equals("num_vertexes")) {
					Object value = propertiesObj.get(key);
					properties.put(key, value);
				}
			}
			qa20Feature = new DTNGIFeature(featureID, featureType, numparts, coorSize, geom, null, properties);
		} else {
			qa20Feature = new DTNGIFeature(featureID, featureType, numparts, coorSize, geom, null, null);
		}
	}
}
