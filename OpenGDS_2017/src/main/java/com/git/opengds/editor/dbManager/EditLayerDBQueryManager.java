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

package com.git.opengds.editor.dbManager;

import java.util.HashMap;

public class EditLayerDBQueryManager {

	public HashMap<String, Object> getSelectLayerCollectionIdx(String collectionName) {

		String tableName = "\"" + "qa20_layercollection" + "\"";
		String selectQuery = "select c_idx from " + tableName + " where file_name = '" + collectionName + "'";
		HashMap<String, Object> selectQueryMap = new HashMap<String, Object>();
		selectQueryMap.put("selectQuery", selectQuery);

		return selectQueryMap;
	}

	// String layerTbName;
	//
	// public EditLayerDBQueryManager(String layerTbName) {
	// this.layerTbName = layerTbName;
	// }
	//
	// public HashMap<String, Object> selectFeatureQuery(String tableName,
	// String featureID) {
	//
	// HashMap<String, Object> selectQuery = new HashMap<String, Object>();
	// String querytStr = "select f_idx from \"" + tableName + "\" where
	// feature_id = '" + featureID + "'";
	// selectQuery.put("selectQuery", querytStr);
	//
	// return selectQuery;
	// }
	//
	// public HashMap<String, Object> deleteFeatureQuery(String tableName, int
	// fIdx) {
	//
	// HashMap<String, Object> deleteQuery = new HashMap<String, Object>();
	// String queryStr = "delete from \"" + tableName + "\" where f_idx = '" +
	// fIdx + "'";
	// deleteQuery.put("deleteQuery", queryStr);
	//
	// return deleteQuery;
	// }
	//
	// public HashMap<String, Object> insertFeatureQuery(String tableName, int
	// fId, QA20Feature feature) {
	//
	// HashMap<String, Object> insertQuery = new HashMap<String, Object>();
	// // default
	// String featureType = feature.getFeatureType();
	// Integer numparts = null;
	// Integer numVertexts = null;
	//
	// if (featureType.equals("POLYGON")) {
	// numparts = Integer.parseInt(feature.getNumparts());
	// }
	// if (featureType.equals("LINESTRING") || featureType.equals("POLYGON")) {
	// numVertexts = Integer.parseInt(feature.getCoordinateSize());
	// }
	//
	// String insertDefaultQuery = "insert into \"" + tableName + "\""
	// + "(f_idx, feature_id, feature_type, geom, num_rings, num_vertexes, ";
	// String insertDefaultValues = " values(" + fId + "," + "'" +
	// feature.getFeatureID() + "'," + "'" + featureType
	// + "'," + "ST_GeomFromText('" + feature.getGeom().toString() + "', 5186)"
	// + "," + numparts + ","
	// + numVertexts + ",";
	//
	// // properties
	// HashMap<String, Object> properties = feature.getProperties();
	// int propertiesSize = properties.size();
	// if (propertiesSize != 0) {
	// Iterator keys = properties.keySet().iterator();
	// while (keys.hasNext()) {
	// String key = (String) keys.next();
	// Object value = properties.get(key);
	// insertDefaultQuery += key + ", ";
	// insertDefaultValues += "'" + value + "', ";
	// }
	// }
	// int lastIndextC = insertDefaultQuery.lastIndexOf(",");
	// String returnQueryC = insertDefaultQuery.substring(0, lastIndextC) + ")";
	// int lastIndextV = insertDefaultValues.lastIndexOf(",");
	// String returnQueryV = insertDefaultValues.substring(0, lastIndextV) +
	// ")";
	//
	// String returnQuery = returnQueryC + returnQueryV;
	// insertQuery.put("insertQuery", returnQuery);
	// return insertQuery;
	// }
	//
	// public HashMap<String, Object> createLayerQuery(QA20Layer layer) {
	//
	//
	// return null;
	// }

}
