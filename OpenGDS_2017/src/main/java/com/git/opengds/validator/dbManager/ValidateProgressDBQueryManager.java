package com.git.opengds.validator.dbManager;

import java.util.HashMap;

public class ValidateProgressDBQueryManager {

	public HashMap<String, Object> getSelectQA20ValidateProgressPid(String collectionName) {

		String tableName = "\"" + "qa20_layercollection_qa_progress" + "\"";
		String selectQuery = "select p_idx from " + tableName + " where collection_name = '" + collectionName + "'";
		HashMap<String, Object> selectQueryMap = new HashMap<String, Object>();
		selectQueryMap.put("selectQuery", selectQuery);

		return selectQueryMap;
	}

	public HashMap<String, Object> getInsertQA20RequestState(int validateStart, String collectionName, String fileType,
			int cidx) {
		String tableName = "\"" + "qa20_layercollection_qa_progress" + "\"";
		String insertQueryStr = " insert into " + tableName
				+ "(collection_name, file_type, state, request_time , c_idx) values ('" + collectionName + "'," + "'"
				+ fileType + "', " + validateStart + ", " + "CURRENT_TIMESTAMP" + "," + cidx + ")";
		HashMap<String, Object> insertQueryMap = new HashMap<String, Object>();
		insertQueryMap.put("insertQuery", insertQueryStr);
		return insertQueryMap;
	}

	public HashMap<String, Object> getUpdateQA20ProgressingState(int pIdx, int validateStart) {
		String tableName = "\"" + "qa20_layercollection_qa_progress" + "\"";
		String updateQueryStr = "update " + tableName + " set state = " + validateStart + " where p_idx = " + pIdx;
		HashMap<String, Object> updateQueryQueryMap = new HashMap<String, Object>();
		updateQueryQueryMap.put("updateQuery", updateQueryStr);
		return updateQueryQueryMap;
	}

	public HashMap<String, Object> getInsertQA20ErrorTableName(int pIdx, String errTableName) {
		String tableName = "\"" + "qa20_layercollection_qa_progress" + "\"";
		String updateQueryStr = "update " + tableName + " set err_layer_name = '" + errTableName + "' where p_idx = "
				+ pIdx;
		HashMap<String, Object> updateQueryQueryMap = new HashMap<String, Object>();
		updateQueryQueryMap.put("updateQuery", updateQueryStr);
		return updateQueryQueryMap;
	}

	public HashMap<String, Object> getInsertQA20ResponseState(int pIdx) {
		String tableName = "\"" + "qa20_layercollection_qa_progress" + "\"";
		String updateQueryStr = "update " + tableName + " set response_time = " + "CURRENT_TIMESTAMP"
				+ " where p_idx = " + pIdx;
		HashMap<String, Object> updateQueryQueryMap = new HashMap<String, Object>();
		updateQueryQueryMap.put("updateQuery", updateQueryStr);
		return updateQueryQueryMap;
	}

	public HashMap<String, Object> getSelectQA10ValidateProgressPid(String collectionName) {
		String tableName = "\"" + "qa10_layercollection_qa_progress" + "\"";
		String selectQuery = "select p_idx from " + tableName + " where collection_name = '" + collectionName + "'";
		HashMap<String, Object> selectQueryMap = new HashMap<String, Object>();
		selectQueryMap.put("selectQuery", selectQuery);
		return selectQueryMap;
	}

	public HashMap<String, Object> getInsertQA10RequestState(int validateStart, String collectionName, String fileType,
			int cidx) {
		String tableName = "\"" + "qa10_layercollection_qa_progress" + "\"";
		String insertQueryStr = " insert into " + tableName
				+ "(collection_name, file_type, state, request_time , c_idx) values ('" + collectionName + "'," + "'"
				+ fileType + "', " + validateStart + ", " + "CURRENT_TIMESTAMP" + ", " + cidx + ")";
		HashMap<String, Object> insertQueryMap = new HashMap<String, Object>();
		insertQueryMap.put("insertQuery", insertQueryStr);
		return insertQueryMap;
	}

	public HashMap<String, Object> getUpdateQA10ProgressingState(int pIdx, int validateFail) {
		String tableName = "\"" + "qa10_layercollection_qa_progress" + "\"";
		String updateQueryStr = "update " + tableName + " set state = " + validateFail + " where p_idx = " + pIdx;
		HashMap<String, Object> updateQueryQueryMap = new HashMap<String, Object>();
		updateQueryQueryMap.put("updateQuery", updateQueryStr);
		return updateQueryQueryMap;
	}

	public HashMap<String, Object> getInsertQA10ErrorTableName(int pIdx, String errTableName) {
		String tableName = "\"" + "qa10_layercollection_qa_progress" + "\"";
		String updateQueryStr = "update " + tableName + " set err_layer_name = '" + errTableName + "' where p_idx = "
				+ pIdx;
		HashMap<String, Object> updateQueryQueryMap = new HashMap<String, Object>();
		updateQueryQueryMap.put("updateQuery", updateQueryStr);
		return updateQueryQueryMap;
	}

	public HashMap<String, Object> getInsertQA10ResponseState(int pIdx) {
		String tableName = "\"" + "qa10_layercollection_qa_progress" + "\"";
		String updateQueryStr = "update " + tableName + " set state = response_time = " + "CURRENT_TIMESTAMP"
				+ " where p_idx = " + pIdx;
		HashMap<String, Object> updateQueryQueryMap = new HashMap<String, Object>();
		updateQueryQueryMap.put("updateQuery", updateQueryStr);
		return updateQueryQueryMap;
	}

	public HashMap<String, Object> getSelectAllQA10ValidateProgress() {
		String tableName = "\"" + "qa10_layercollection_qa_progress" + "\"";
		String selectQuery = "select * from " + tableName + " order by request_time DESC";
		HashMap<String, Object> selectQueryMap = new HashMap<String, Object>();
		selectQueryMap.put("selectAllQuery", selectQuery);
		return selectQueryMap;
	}

	public HashMap<String, Object>  getSelectAllQA20ValidateProgress() {
		String tableName = "\"" + "qa20_layercollection_qa_progress" + "\"";
		String selectQuery = "select * from " + tableName + " order by request_time DESC";
		HashMap<String, Object> selectQueryMap = new HashMap<String, Object>();
		selectQueryMap.put("selectAllQuery", selectQuery);
		return selectQueryMap;
	}

}
