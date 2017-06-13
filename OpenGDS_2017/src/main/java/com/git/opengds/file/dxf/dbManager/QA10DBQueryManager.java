package com.git.opengds.file.dxf.dbManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.git.gdsbuilder.type.qa10.feature.QA10Feature;
import com.git.gdsbuilder.type.qa10.feature.QA10FeatureList;
import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa10.layer.QA10LayerList;

public class QA10DBQueryManager {

	public HashMap<String, Object> getInsertLayerCollection(String collectionName) {

		String insertQuery = "insert into qa10_layercollection(file_name) values('" + collectionName + "')";
		HashMap<String, Object> insertQueryMap = new HashMap<String, Object>();
		insertQueryMap.put("insertQuery", insertQuery);
		return insertQueryMap;
	}

	public HashMap<String, Object> qa10LayerTbCreateQuery(String type, String collectionName, QA10Layer qa10Layer) {

		String layerType = qa10Layer.getLayerType();
		String layerId = qa10Layer.getLayerID();
		String tableName = "\"geo" + "_" + type + "_" + collectionName + "_" + layerId + "\"";
		String defaultCreateQuery = "create table " + tableName + "("
				+ "f_idx serial primary key, layer_id varchar(100), feature_id varchar(100), geom geometry(" + layerType
				+ ", 5186))";

		HashMap<String, Object> query = new HashMap<String, Object>();
		query.put("createQuery", defaultCreateQuery);
		return query;
	}

	public List<HashMap<String, Object>> qa10LayerTbInsertQuery(String type, String collectionName,
			QA10Layer qa10Layer) {

		String layerId = qa10Layer.getLayerID();
		String tableName = "\"geo" + "_" + type + "_" + collectionName + "_" + layerId + "\"";

		List<HashMap<String, Object>> dbLayers = new ArrayList<HashMap<String, Object>>();
		QA10FeatureList features = qa10Layer.getQa10FeatureList();
		for (int i = 0; i < features.size(); i++) {
			QA10Feature feature = features.get(i);
			String defaultInsertColumns = "insert into " + tableName + "(layer_id, feature_id, geom) ";
			String values = "values ('" + feature.getLayerID() + "', '" + feature.getFeatureID() + "', "
					+ "ST_GeomFromText('" + feature.getGeom().toString() + "', 5186))";

			HashMap<String, Object> query = new HashMap<String, Object>();
			query.put("insertQuery", defaultInsertColumns + values);
			dbLayers.add(query);
		}
		return dbLayers;
	}

	public HashMap<String, Object> getInsertLayerMeataData(String type, String collectionName, int cIdx,
			QA10Layer qa10Layer) {

		String layerId = qa10Layer.getLayerID();
		String layerTableName = "\"geo" + "_" + type + "_" + collectionName + "_" + layerId + "\"";
		String insertQueryColumn = "insert into " + "\"qa10_layer_metadata" + "\"" + "(layer_id, layer_t_name, c_idx)";
		String insertQueryValue = " values('" + layerId + "', '" + layerTableName + "', " + cIdx + ")";

		HashMap<String, Object> insertQueryMap = new HashMap<String, Object>();
		insertQueryMap.put("insertQuery", insertQueryColumn + insertQueryValue);
		insertQueryMap.put("lm_idx", 0);

		return insertQueryMap;
	}

//	public List<String> getLayerColumns(String layerID) {
//
//		QA10LayerList layerList = qa10LayerCollection.getQa10Layers();
//		QA10Layer layer = layerList.getQA10Layer(layerID);
//
//		List<String> columnNames = new ArrayList<String>();
//		Hashtable<String, Object> columns = layer.getLayerColumns();
//		Iterator iterator = columns.keySet().iterator();
//		while (iterator.hasNext()) {
//			String columnName = (String) iterator.next();
//			columnNames.add(columnName);
//		}
//		return columnNames;
//	}
//
//	public String getLayerType(String layerID) {
//		QA10LayerList layerList = qa10LayerCollection.getQa10Layers();
//		QA10Layer layer = layerList.getQA10Layer(layerID);
//		return layer.getLayerType();
//	}

}
