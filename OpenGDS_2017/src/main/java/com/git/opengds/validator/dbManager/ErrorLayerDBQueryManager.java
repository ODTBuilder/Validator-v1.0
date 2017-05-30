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

package com.git.opengds.validator.dbManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.type.validate.error.ErrorLayer;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

/**
 * ErrorLayerDBQuery 생성 클래스 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 4:09:29
 * */
public class ErrorLayerDBQueryManager {

	ErrorLayer errLayer;

	/**
	 * ErrorLayerDBQueryManager 생성자
	 */
	public ErrorLayerDBQueryManager() {
		super();
	}
	
	/**
	 * ErrorLayerDBQueryManager 생성자
	 * @param errLayer
	 */
	public ErrorLayerDBQueryManager(ErrorLayer errLayer) {
		super();
		this.errLayer = errLayer;
	}

	/**
	 * err_layer Tb Create Query 생성
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 4:09:35
	 * @return HashMap<String,Object>
	 * @throws
	 * */
	public HashMap<String, Object> createErrorLayerTbQuery() {

		String collectionName = this.errLayer.getCollectionName();
		String tableName = "\"" + "err_" + collectionName + "\"";
		String dafaultCreateQuery = "create table " + tableName + "(" + "err_idx serial primary key" + ","
				+ "collection_name varchar(100)" + "," + "layer_name varchar(100)" + "," + "feature_id varchar(100)"
				+ "," + "err_type varchar(100)" + "," + "err_name varchar(100)" + "," + "geom geometry(point, 5186)"
				+ ")";

		HashMap<String, Object> createQuery = new HashMap<String, Object>();
		createQuery.put("createQuery", dafaultCreateQuery);
		return createQuery;
	}

	/**
	 * err_layer field insert Query 생성
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 4:09:39
	 * @return List<HashMap<String,Object>>
	 * @throws
	 * */
	public List<HashMap<String, Object>> insertErrorLayerQuery() {

		String collectionName = this.errLayer.getCollectionName();
		String tableName = "\"" + "err_" + collectionName + "\"";

		List<HashMap<String, Object>> inertQueryMaps = new ArrayList<HashMap<String, Object>>();

		List<ErrorFeature> errFeatures = errLayer.getErrFeatureList();
		for (int i = 0; i < errFeatures.size(); i++) {
			ErrorFeature errFeature = errFeatures.get(i);
			// String layerID = errFeature.getLayerID();
			String layerName = errFeature.getLayerName();
			String featureID = errFeature.getFeatureID();
			String errType = errFeature.getErrType();
			String errName = errFeature.getErrName();
			Geometry errPt = (Point) errFeature.getErrPoint();

			String insertQuery = "insert into " + tableName + "("
					+ "collection_name, layer_name, feature_id, err_type, err_name, geom) values" + "(" + "'"
					+ collectionName + "'" + "," + "'" + layerName + "'" + "," + "'" + featureID + "'" + "," + "'"
					+ errType + "'" + "," + "'" + errName + "'" + "," + "ST_GeomFromText('" + errPt.toString()
					+ "', 5186)" + ")";

			HashMap<String, Object> insertQueryMap = new HashMap<String, Object>();
			insertQueryMap.put("insertQuery", insertQuery);
			inertQueryMaps.add(insertQueryMap);
		}
		return inertQueryMaps;
	}

	/**
	 * err_layer Tb selectAll Query 생성
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 4:09:42
	 * @param collectionName
	 * @return HashMap<String,Object>
	 * @throws
	 * */
	public HashMap<String, Object> selectAllErrorFeaturesQuery(String collectionName) {

		String tableName = "\"" + "err_" + collectionName + "\"";
		String selectQuery = " select "
				+ "collection_name, layer_name, feature_id, err_type, err_name, ST_X(geom) as x_Coordinate, ST_Y(geom) as y_Coordinate "
				+ "from " + tableName;

		HashMap<String, Object> selectQueryMap = new HashMap<String, Object>();
		selectQueryMap.put("selectAllQuery", selectQuery);
		return selectQueryMap;
	}

	/**
	 * err_layer Tb select Query 생성
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 4:09:44
	 * @param collectionName
	 * @param layerNames
	 * @return HashMap<String,Object>
	 * @throws
	 * */
	public HashMap<String, Object> selecctErrorFeaturesQuery(String collectionName, List<String> layerNames) {

		String tableName = "\"" + "err_" + collectionName + "\"";
		// String selectQuery = " select " + "collection_name, layer_name,
		// feature_id, err_type, err_name, ST_AsText(geom) as geom " + "from " +
		// tableName + " where ";
		String selectQuery = " select "
				+ "collection_name, layer_name, feature_id, err_type, err_name, ST_X(geom) as x_Coordinate, ST_Y(geom) as y_Coordinate "
				+ "from " + tableName + " where ";
		for (int i = 0; i < layerNames.size(); i++) {
			String layerName = layerNames.get(i);
			if (i != 0) {
				selectQuery += " OR ";
			}
			selectQuery += " layer_name = " + "'" + layerName + "'";
		}
		HashMap<String, Object> selectQueryMap = new HashMap<String, Object>();
		selectQueryMap.put("selectQuery", selectQuery);
		return selectQueryMap;
	}

	/**
	 * err_layer Tb select All Count Query 생성
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 4:09:48
	 * @param layerCollectionName
	 * @param layerIDList
	 * @return HashMap<String,Object>
	 * @throws
	 * */
	public HashMap<String, Object> selectCountAllFeaturesQuery(String layerCollectionName, List<String> layerIDList) {

		String countQueryStr = "select ";
		for (int i = 0; i < layerIDList.size(); i++) {
			if (i > 0) {
				countQueryStr += " + ";
			}
			String layerID = layerIDList.get(i);
			String tableName = "\"geo" + "_" + layerCollectionName + "_" + layerID + "\"";
			countQueryStr += "(select count (*) from " + tableName + ")";
		}
		countQueryStr += " as all_feature_count";
		HashMap<String, Object> countQueryMap = new HashMap<String, Object>();
		countQueryMap.put("countQuery", countQueryStr);
		return countQueryMap;
	}

}
