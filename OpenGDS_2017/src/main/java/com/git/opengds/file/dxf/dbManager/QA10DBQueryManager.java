package com.git.opengds.file.dxf.dbManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.git.gdsbuilder.type.qa10.feature.QA10Feature;
import com.git.gdsbuilder.type.qa10.feature.QA10FeatureList;
import com.git.gdsbuilder.type.qa10.layer.QA10Layer;

public class QA10DBQueryManager {

	public HashMap<String, Object> getInsertLayerCollection(String collectionName) {

		String insertQuery = "insert into qa10_layercollection(collection_name) values('" + collectionName + "')";
		HashMap<String, Object> insertQueryMap = new HashMap<String, Object>();
		insertQueryMap.put("insertQuery", insertQuery);
		insertQueryMap.put("c_idx", 0);
		return insertQueryMap;
	}

	public HashMap<String, Object> qa10LayerTbCreateQuery(String type, String collectionName, QA10Layer qa10Layer, String src) {

		String layerType = qa10Layer.getLayerType();
		String layerId = qa10Layer.getLayerID();
		String tableName = "\"geo" + "_" + type + "_" + collectionName + "_" + layerId + "\"";
		String defaultCreateQuery = "create table " + tableName + "("
				+ "f_idx serial primary key, feature_id varchar(100), geom geometry(" + layerType
				+ "," + src + "), feature_type varchar(50)";

		if (layerType.equals("TEXT")) {
			defaultCreateQuery += ", text_value varchar(100)";
		}
		defaultCreateQuery += ")";
		HashMap<String, Object> query = new HashMap<String, Object>();
		query.put("createQuery", defaultCreateQuery);
		return query;
	}

	public List<HashMap<String, Object>> qa10LayerTbInsertQuery(String type, String collectionName,
			QA10Layer qa10Layer, String src) {

		String layerType = qa10Layer.getLayerType();
		String layerId = qa10Layer.getLayerID();
		String tableName = "\"geo" + "_" + type + "_" + collectionName + "_" + layerId + "\"";

		List<HashMap<String, Object>> dbLayers = new ArrayList<HashMap<String, Object>>();
		QA10FeatureList features = qa10Layer.getQa10FeatureList();
		for (int i = 0; i < features.size(); i++) {
			QA10Feature feature = features.get(i);
			String defaultInsertColumns = "insert into " + tableName + "(feature_id, geom, feature_type ";
			String values = "values ('" + feature.getFeatureID() + "', " + "ST_GeomFromText('"
					+ feature.getGeom().toString() + "'," + src + "), '" + feature.getFeatureType() + "'";

			if (layerType.equals("TEXT")) {
				defaultInsertColumns += ", text_value";
				values += ", '" + feature.getTextValue() + "'";
			}

			defaultInsertColumns += ")";
			values += ")";
			HashMap<String, Object> query = new HashMap<String, Object>();
			query.put("insertQuery", defaultInsertColumns + values);
			dbLayers.add(query);
		}
		return dbLayers;
	}

	public HashMap<String, Object> getInsertLayerMeataData(String type, String collectionName, int cIdx,
			QA10Layer qa10Layer) {

		String layerId = qa10Layer.getLayerID();
		String layerTableName = "geo" + "_" + type + "_" + collectionName + "_" + layerId;
		String insertQueryColumn = "insert into " + "\"qa10_layer_metadata" + "\"" + "(layer_id, layer_t_name, c_idx)";
		String insertQueryValue = " values('" + layerId + "', '" + layerTableName + "', " + cIdx + ")";

		HashMap<String, Object> insertQueryMap = new HashMap<String, Object>();
		insertQueryMap.put("insertQuery", insertQueryColumn + insertQueryValue);
		// insertQueryMap.put("lm_idx", 0);

		return insertQueryMap;
	}

	public List<String> getLayerColumns() {
		List<String> columns = new ArrayList<String>();
		columns.add("layer_id");
		columns.add("feature_id");
		columns.add("geom");
		columns.add("feature_type");
		return columns;
	}

	public HashMap<String, Object> getInertFeatureQuery(String tableName, QA10Feature createFeature) {

		String insertDefaultQuery = "insert into \"" + tableName + "\"" + "(feature_id, geom, feature_type)";
		String insertDefaultValues = " values('" + createFeature.getFeatureID() + "'," + "ST_GeomFromText('"
				+ createFeature.getGeom().toString() + "', 5186)" + ",'" + createFeature.getFeatureType() + "')";

		HashMap<String, Object> insertQuery = new HashMap<String, Object>();
		insertQuery.put("insertQuery", insertDefaultQuery + insertDefaultValues);
		return insertQuery;
	}

	public HashMap<String, Object> getSelectFeatureIdx(String tableName, String featureID) {

		HashMap<String, Object> selectQuery = new HashMap<String, Object>();
		String querytStr = "select f_idx from \"" + tableName + "\" where feature_id = '" + featureID + "'";
		selectQuery.put("selectQuery", querytStr);

		return selectQuery;
	}

	public HashMap<String, Object> getDeleteFeature(String tableName, int fIdx) {

		HashMap<String, Object> deleteQuery = new HashMap<String, Object>();
		String queryStr = "delete from \"" + tableName + "\" where f_idx = '" + fIdx + "'";
		deleteQuery.put("deleteQuery", queryStr);

		return deleteQuery;
	}

	public HashMap<String, Object> getSelectLayerCollectionIdx(String collectionName) {

		String tableName = "\"" + "qa10_layercollection" + "\"";
		String selectQuery = "select c_idx from " + tableName + " where file_name = '" + collectionName + "'";
		HashMap<String, Object> selectQueryMap = new HashMap<String, Object>();
		selectQueryMap.put("selectQuery", selectQuery);

		return selectQueryMap;
	}

	public HashMap<String, Object> getInsertTables(int cIdx, Map<String, Object> tables) {

		LinkedHashMap<String, Object> commons = (LinkedHashMap<String, Object>) tables.get("common");

		String tableName = "\"" + "qa10_layercollection_table_common" + "\"";
		String tableColumnQuery = "insert into " + tableName + "(";
		String tableValuesQuery = "values(";

		Iterator tableIt = commons.keySet().iterator();
		while (tableIt.hasNext()) {
			String code = (String) tableIt.next();
			Object value = commons.get(code);
			tableColumnQuery += "\"" + code + "\"" + ",";
			tableValuesQuery += "'" + value + "', ";
		}
		tableColumnQuery += "c_idx" + ")";
		tableValuesQuery += cIdx + ")";

		String returnQuery = tableColumnQuery + tableValuesQuery;
		HashMap<String, Object> query = new HashMap<String, Object>();
		query.put("insertQuery", returnQuery);
		query.put("tc_idx", null);

		return query;
	}

	public List<HashMap<String, Object>> getInsertTablesLayers(int tbIdx, Map<String, Object> tables) {

		List<HashMap<String, Object>> layerQuerys = new ArrayList<HashMap<String, Object>>();
		List<LinkedHashMap<String, Object>> layers = (List<LinkedHashMap<String, Object>>) tables.get("layers");

		String tableName = "\"" + "qa10_layercollection_table_layer" + "\"";
		for (int i = 0; i < layers.size(); i++) {
			LinkedHashMap<String, Object> layer = layers.get(i);
			String layerColumnQuery = "insert into " + tableName + "(";
			String layerValuesQuery = "values(";
			Iterator layerIt = layer.keySet().iterator();
			while (layerIt.hasNext()) {
				String code = (String) layerIt.next();
				Object value = layer.get(code);
				layerColumnQuery += "\"" + code + "\"" + ",";
				layerValuesQuery += "'" + value + "', ";
			}
			layerColumnQuery += "tc_idx" + ")";
			layerValuesQuery += tbIdx + ")";

			String returnQuery = layerColumnQuery + layerValuesQuery;
			HashMap<String, Object> query = new HashMap<String, Object>();
			query.put("insertQuery", returnQuery);

			layerQuerys.add(query);
		}
		return layerQuerys;
	}

	public List<HashMap<String, Object>> getInsertBlocks(int cIdx, List<LinkedHashMap<String, Object>> blocks) {

		List<HashMap<String, Object>> blockQuerys = new ArrayList<HashMap<String, Object>>();

		String tableName = "\"" + "qa10_layercollection_block_common" + "\"";
		for (int i = 0; i < blocks.size(); i++) {
			String blockColumnQuery = "insert into " + tableName + "(";
			String blockValuesQuery = "values(";
			LinkedHashMap<String, Object> blockMap = blocks.get(i);
			// block
			LinkedHashMap<String, Object> block = (LinkedHashMap<String, Object>) blockMap.get("block");
			Iterator blockIt = block.keySet().iterator();
			while (blockIt.hasNext()) {
				String code = (String) blockIt.next();
				Object value = block.get(code);
				blockColumnQuery += "\"" + code + "\"" + ",";
				blockValuesQuery += "'" + value + "', ";
			}
			blockColumnQuery += "c_idx" + ")";
			blockValuesQuery += cIdx + ")";

			String returnQuery = blockColumnQuery + blockValuesQuery;
			HashMap<String, Object> query = new HashMap<String, Object>();
			query.put("insertQuery", returnQuery);
			query.put("bc_idx", null);

			blockQuerys.add(query);
		}
		return blockQuerys;
	}

	public List<HashMap<String, Object>> getInsertBlockEntityQuery(int bIdx, LinkedHashMap<String, Object> block) {

		List<HashMap<String, Object>> insertQueryList = new ArrayList<HashMap<String, Object>>();

		// entities
		List<LinkedHashMap<String, Object>> entities = (List<LinkedHashMap<String, Object>>) block.get("entities");
		for (int j = 0; j < entities.size(); j++) {

			LinkedHashMap<String, Object> entity = entities.get(j);

			String tableName = "";
			String typeCode = "0";
			String typeValue = (String) entity.get(typeCode);

			if (typeValue.equals("TEXT")) {
				tableName = "\"" + "qa10_layercollection_block_text" + "\"";
			} else if (typeValue.equals("ARC")) {
				tableName = "\"" + "qa10_layercollection_block_arc" + "\"";
			} else if (typeValue.equals("VERTEX")) {
				tableName = "\"" + "qa10_layercollection_block_vertex" + "\"";
			} else if (typeValue.equals("POLYLINE")) {
				tableName = "\"" + "qa10_layercollection_block_polyline" + "\"";
				getInsertBlockPolylineQuery(tableName, bIdx, entity);
				continue;
			} else if (typeValue.equals("CIRCLE")) {
				tableName = "\"" + "qa10_layercollection_block_circle" + "\"";
			}

			String insertColumnQuery = "insert into " + tableName + "(";
			String insertValueQuery = "values(";

			Iterator entityIt = entity.keySet().iterator();
			while (entityIt.hasNext()) {
				String code = (String) entityIt.next();
				Object value = entity.get(code);

				insertColumnQuery += "\"" + code + "\", ";
				insertValueQuery += "'" + value + "', ";
			}
			insertColumnQuery += "bc_idx" + ")";
			insertValueQuery += bIdx + ")";

			String returnQuery = insertColumnQuery + insertValueQuery;
			HashMap<String, Object> query = new HashMap<String, Object>();
			query.put("insertQuery", returnQuery);

			insertQueryList.add(query);
		}
		return insertQueryList;
	}

	private void getInsertBlockPolylineQuery(String tableName, int bIdx, LinkedHashMap<String, Object> entity) {

		List<HashMap<String, Object>> insertQueryList = new ArrayList<HashMap<String, Object>>();

		String insertColumnQuery = "insert into " + tableName + "(";
		String insertValueQuery = "values(";

		Iterator entityIt = entity.keySet().iterator();
		while (entityIt.hasNext()) {
			String code = (String) entityIt.next();
			Object value = entity.get(code);
			if (code.equals("vertexs")) {
				tableName = "\"" + "qa10_layercollection_block_vertex" + "\"";
				insertQueryList.addAll(getInsertBlockVertexQuery(tableName, bIdx, value));
				continue;
			}
			insertColumnQuery += "\"" + code + "\", ";
			insertValueQuery += "'" + value + "', ";
		}
		insertColumnQuery += "bc_idx" + ")";
		insertValueQuery += bIdx + ")";

		String returnQuery = insertColumnQuery + insertValueQuery;
		HashMap<String, Object> query = new HashMap<String, Object>();
		query.put("insertQuery", returnQuery);

		insertQueryList.add(query);

	}

	private List<HashMap<String, Object>> getInsertBlockVertexQuery(String tableName, int bIdx, Object vertexObj) {

		List<HashMap<String, Object>> insertQueryList = new ArrayList<HashMap<String, Object>>();

		List<LinkedHashMap<String, Object>> vertexMapList = (List<LinkedHashMap<String, Object>>) vertexObj;
		String insertColumnQuery = "insert into " + tableName + "(";
		String insertValueQuery = "values(";
		for (int i = 0; i < vertexMapList.size(); i++) {
			LinkedHashMap<String, Object> vertex = vertexMapList.get(i);
			Iterator entityIt = vertex.keySet().iterator();
			while (entityIt.hasNext()) {
				String code = (String) entityIt.next();
				Object value = vertex.get(code);
				insertColumnQuery += "\"" + code + "\", ";
				insertValueQuery += "'" + value + "', ";
			}
			insertColumnQuery += "bc_idx" + ")";
			insertValueQuery += bIdx + ")";

			String returnQuery = insertColumnQuery + insertValueQuery;
			HashMap<String, Object> query = new HashMap<String, Object>();
			query.put("insertQuery", returnQuery);

			insertQueryList.add(query);
		}
		return insertQueryList;
	}
}
