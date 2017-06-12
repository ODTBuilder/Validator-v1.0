package com.git.opengds.file.dxf.dbManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.qa10.collection.QA10LayerCollection;
import com.git.gdsbuilder.type.qa10.feature.QA10Feature;
import com.git.gdsbuilder.type.qa10.feature.QA10FeatureList;
import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa10.layer.QA10LayerList;

public class QA10DBQueryManager {

	QA10LayerCollection qa10LayerCollection;
	GeoLayerInfo layerInfo;

	public QA10DBQueryManager() {

	}

	public QA10DBQueryManager(QA10LayerCollection qa10LayerCollection, GeoLayerInfo layerInfo) {
		this.qa10LayerCollection = qa10LayerCollection;
		this.layerInfo = layerInfo;
	}

	public void setQa10LayerCollection(QA10LayerCollection qa10LayerCollection) {
		this.qa10LayerCollection = qa10LayerCollection;
	}

	public List<HashMap<String, Object>> qa10LayerMetadata() {

		QA10LayerList layerList = qa10LayerCollection.getQa10Layers();
		List<HashMap<String, Object>> dblayers = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i < layerList.size(); i++) {
			HashMap<String, Object> dbLayer = new HashMap<String, Object>();
			QA10Layer layer = layerList.get(i);
			String layerID = layer.getLayerID();
			dbLayer.put("layerID", layerID);
			// 잠시 생략
			dblayers.add(dbLayer);
		}
		return dblayers;
	}

	public HashMap<String, Object> qa10LayerTbCreateQuery(String layerID) {

		String fileName = qa10LayerCollection.getFileName();
		QA10LayerList layerList = qa10LayerCollection.getQa10Layers();
		QA10Layer layer = layerList.getQA10Layer(layerID);
		String layerType = layer.getLayerType();
		String fileType = layerInfo.getFileType();

		String tableName = "\"geo" + "_" + fileType + "_" + fileName + "_" + layerID + "\"";
		String defaultCreateQuery = "create table " + tableName + "(" + "f_idx serial primary key, " + "geom geometry("
				+ layerType + ", 5186),";

		Hashtable<String, Object> columns = layer.getLayerColumns();
		int columnsSize = columns.size();
		int tmp = 0;
		Iterator iterator = columns.keySet().iterator();
		while (iterator.hasNext()) {
			String columnName = (String) iterator.next();
			String columnType = (String) columns.get(columnName);

			defaultCreateQuery += columnName;
			defaultCreateQuery += " ";
			defaultCreateQuery += columnType;
			defaultCreateQuery += ", ";

			if (tmp == columnsSize) {
				break;
			} else {
				tmp++;
			}
		}
		int lastIndext = defaultCreateQuery.lastIndexOf(",");
		String returnQuery = defaultCreateQuery.substring(0, lastIndext) + ")";
		HashMap<String, Object> query = new HashMap<String, Object>();
		query.put("createQuery", returnQuery);
		return query;
	}

	public List<HashMap<String, Object>> qa10LayerTbInsertQuery(String layerID) {

		String fileName = qa10LayerCollection.getFileName();
		QA10LayerList layerList = qa10LayerCollection.getQa10Layers();
		QA10Layer layer = layerList.getQA10Layer(layerID);
		String fileType = layerInfo.getFileType();
		String tableName = "\"geo" + "_" + fileType + "_" + fileName + "_" + layer.getLayerID() + "\"";

		List<HashMap<String, Object>> dbLayers = new ArrayList<HashMap<String, Object>>();
		QA10FeatureList features = layer.getQa10FeatureList();
		for (int i = 0; i < features.size(); i++) {
			QA10Feature feature = features.get(i);
			String defaultInsertColumns = "insert into " + tableName + "(geom, ";
			String values = "values (" + "ST_GeomFromText('" + feature.getGeom().toString() + "', 5186), ";

			Hashtable<String, Object> properties = feature.getProperties();
			Iterator proIterator = properties.keySet().iterator();
			while (proIterator.hasNext()) {
				String key = (String) proIterator.next();
				Object value = properties.get(key);
				defaultInsertColumns += key + ", ";
				values += "'" + value + "', ";
			}
			int lastIndextC = defaultInsertColumns.lastIndexOf(",");
			String returnQueryC = defaultInsertColumns.substring(0, lastIndextC) + ")";
			int lastIndextV = values.lastIndexOf(",");
			String returnQueryV = values.substring(0, lastIndextV) + ")";
			String returnQuery = returnQueryC + returnQueryV;
			HashMap<String, Object> query = new HashMap<String, Object>();
			query.put("insertQuery", returnQuery);
			dbLayers.add(query);
		}
		return dbLayers;
	}

	public List<String> getLayerColumns(String layerID) {

		QA10LayerList layerList = qa10LayerCollection.getQa10Layers();
		QA10Layer layer = layerList.getQA10Layer(layerID);

		List<String> columnNames = new ArrayList<String>();
		Hashtable<String, Object> columns = layer.getLayerColumns();
		Iterator iterator = columns.keySet().iterator();
		while (iterator.hasNext()) {
			String columnName = (String) iterator.next();
			columnNames.add(columnName);
		}
		return columnNames;
	}

	public String getLayerType(String layerID) {
		QA10LayerList layerList = qa10LayerCollection.getQa10Layers();
		QA10Layer layer = layerList.getQA10Layer(layerID);
		return layer.getLayerType();
	}
}
