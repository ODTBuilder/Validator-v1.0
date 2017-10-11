package com.git.opengds.generalization.dbManager;

import java.util.HashMap;

import com.git.gdsbuilder.generalization.rep.DTGeneralReport;
import com.git.gdsbuilder.generalization.rep.DTGeneralReport.DTGeneralReportNumsType;

public class GenResultDBQueryManager {

	public HashMap<String, Object> getInsertGenResultQuery(String collectionName, String layerName,
			DTGeneralReport resultReport) {

		String tableName = "\"" + "shp_layercollection_gen_result" + "\"";
		String insertQuery = "insert into " + tableName
				+ "(collection_name, before_features_count, after_features_count, before_points_count, after_points_count, layer_name) values("
				+ "'" + collectionName + "',"
				+ resultReport.getDTGeneralReportNums(DTGeneralReportNumsType.ENTITY).getPreNum() + ", "
				+ resultReport.getDTGeneralReportNums(DTGeneralReportNumsType.ENTITY).getAfNum() + ", "
				+ resultReport.getDTGeneralReportNums(DTGeneralReportNumsType.POINT).getPreNum() + ", "
				+ resultReport.getDTGeneralReportNums(DTGeneralReportNumsType.POINT).getAfNum() + ", '" + layerName
				+ "')";

		HashMap<String, Object> insertMap = new HashMap<>();
		insertMap.put("insertQuery", insertQuery);
		return insertMap;
	}
}
