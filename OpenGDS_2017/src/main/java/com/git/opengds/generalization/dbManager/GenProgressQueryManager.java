package com.git.opengds.generalization.dbManager;

import java.util.HashMap;

public class GenProgressQueryManager {

	public HashMap<String, Object> selectGenLayerTbNamesCountQuery(String type, String collectionName, Integer cIdx) {
		String tableName = "\"" + type + "_" + "layercollection_gen_progress" + "\"";
		String countQueryStr = "select count (*) from " + tableName + " where c_idx = " + cIdx;
		HashMap<String, Object> selectQueryMap = new HashMap<String, Object>();
		selectQueryMap.put("selectQuery", countQueryStr);
		return selectQueryMap;
	}

	public HashMap<String, Object> getInsertRequestState(int validateStart, String collectionName, String fileType,
			String layerName, int cidx) {
		String tableName = "\"" + fileType + "_layercollection_gen_progress" + "\"";
		String insertQueryStr = " insert into " + tableName
				+ "(collection_name, file_type, state, request_time, layer_name, c_idx) values ('" + collectionName
				+ "'," + "'" + fileType + "', " + validateStart + ", " + "CURRENT_TIMESTAMP" + "," + "'" + layerName
				+ "', " + cidx + ")";
		HashMap<String, Object> insertQueryMap = new HashMap<String, Object>();
		insertQueryMap.put("insertQuery", insertQueryStr);
		return insertQueryMap;
	}

	public HashMap<String, Object> getUpdateProgressingState(Integer pIdx, String fileType, int state) {

		String tableName = "\"" + fileType + "_layercollection_gen_progress" + "\"";
		String updateQueryStr = "update " + tableName + " set state = " + state + " where p_idx = " + pIdx;
		HashMap<String, Object> updateQueryQueryMap = new HashMap<String, Object>();
		updateQueryQueryMap.put("updateQuery", updateQueryStr);
		return updateQueryQueryMap;

	}

	public HashMap<String, Object> getInsertResponseState(Integer pIdx, String genTbName, String type) {

		String tableName = "\"" + type + "_layercollection_gen_progress" + "\"";
		String updateQueryStr = "update " + tableName + " set response_time = " + "CURRENT_TIMESTAMP"
				+ ", gen_layer_tb_name = '" + genTbName + "' " + " where p_idx = " + pIdx;
		HashMap<String, Object> updateQueryQueryMap = new HashMap<String, Object>();
		updateQueryQueryMap.put("updateQuery", updateQueryStr);
		return updateQueryQueryMap;
	}

}
