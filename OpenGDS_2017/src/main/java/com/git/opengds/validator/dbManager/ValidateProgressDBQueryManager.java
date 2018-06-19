package com.git.opengds.validator.dbManager;

import java.util.HashMap;

public class ValidateProgressDBQueryManager {

	/**
	 * shp_layercollection_qa_progress tb 검수 상태 update query 생성
	 * 
	 * @param pIdx
	 *            shp_layercollection_qa_progress tb Index
	 * @param validateStart
	 *            검수 상태
	 * @return HashMap<String, Object>
	 */
	public HashMap<String, Object> getUpdateSHPProgressingState(int pIdx, int validateStart) {
		String tableName = "\"" + "shp_layercollection_qa_progress" + "\"";
		String updateQueryStr = "update " + tableName + " set state = " + validateStart + " where p_idx = " + pIdx;
		HashMap<String, Object> updateQueryQueryMap = new HashMap<String, Object>();
		updateQueryQueryMap.put("updateQuery", updateQueryStr);
		return updateQueryQueryMap;
	}

	/**
	 * shp_layercollection_qa_progress tb 오류 레이어 명 update query 생성
	 * 
	 * @param pIdx
	 *            shp_layercollection_qa_progress tb Index
	 * @param errTableName
	 *            오류 레이어 명
	 * @return HashMap<String, Object>
	 */
	public HashMap<String, Object> getInsertSHPErrorTableName(int pIdx, String errTableName) {
		String tableName = "\"" + "shp_layercollection_qa_progress" + "\"";
		String updateQueryStr = "update " + tableName + " set err_layer_name = '" + errTableName + "' where p_idx = "
				+ pIdx;
		HashMap<String, Object> updateQueryQueryMap = new HashMap<String, Object>();
		updateQueryQueryMap.put("updateQuery", updateQueryStr);
		return updateQueryQueryMap;
	}

	/**
	 * shp_layercollection_qa_progress tb 검수 완료 update query 생성
	 * 
	 * @param pIdx
	 *            shp_layercollection_qa_progress tb Index
	 * @return HashMap<String, Object>
	 */
	public HashMap<String, Object> getInsertSHPResponseState(int pIdx) {
		String tableName = "\"" + "shp_layercollection_qa_progress" + "\"";
		String updateQueryStr = "update " + tableName + " set response_time = " + "CURRENT_TIMESTAMP"
				+ " where p_idx = " + pIdx;
		HashMap<String, Object> updateQueryQueryMap = new HashMap<String, Object>();
		updateQueryQueryMap.put("updateQuery", updateQueryStr);
		return updateQueryQueryMap;
	}

	/**
	 * shp_layercollection_qa_progress tb 모든 검수 상태 select query
	 * 
	 * @return Object
	 */
	public Object getSelectAllSHPValidateProgress() {
		String tableName = "\"" + "shp_layercollection_qa_progress" + "\"";
		String selectQuery = "select * from " + tableName + " order by request_time DESC";
		HashMap<String, Object> selectQueryMap = new HashMap<String, Object>();
		selectQueryMap.put("selectAllQuery", selectQuery);
		return selectQueryMap;
	}

}
