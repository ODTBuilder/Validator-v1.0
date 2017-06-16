package com.git.opengds.file.dxf.dbManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kabeja.dxf.DXFVariable;

import com.git.gdsbuilder.type.qa10.feature.QA10Feature;
import com.git.gdsbuilder.type.qa10.feature.QA10FeatureList;
import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa10.structure.QA10Header;
import com.git.gdsbuilder.type.qa10.structure.QA10Tables;
import com.git.gdsbuilder.type.qa20.header.NDAHeader;
import com.git.gdsbuilder.type.qa20.header.NGIField;

public class QA10DBQueryManager {

	public HashMap<String, Object> getInsertLayerCollection(String collectionName) {

		String insertQuery = "insert into qa10_layercollection(collection_name) values('" + collectionName + "')";
		HashMap<String, Object> insertQueryMap = new HashMap<String, Object>();
		insertQueryMap.put("insertQuery", insertQuery);
		insertQueryMap.put("c_idx", 0);
		return insertQueryMap;
	}

	public HashMap<String, Object> qa10LayerTbCreateQuery(String type, String collectionName, QA10Layer qa10Layer) {

		String layerType = qa10Layer.getLayerType();
		String layerId = qa10Layer.getLayerID();
		String tableName = "\"geo" + "_" + type + "_" + collectionName + "_" + layerId + "\"";
		String defaultCreateQuery = "create table " + tableName + "("
				+ "f_idx serial primary key, layer_id varchar(100), feature_id varchar(100), geom geometry(" + layerType
				+ ", 5186), feature_type varchar(50))";

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
			String defaultInsertColumns = "insert into " + tableName + "(layer_id, feature_id, geom, feature_type) ";
			String values = "values ('" + feature.getLayerID() + "', '" + feature.getFeatureID() + "', "
					+ "ST_GeomFromText('" + feature.getGeom().toString() + "', 5186), '" + feature.getFeatureType()
					+ "')";

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

	public HashMap<String, Object> getInsertHeader(int cIdx, QA10Header header) {

		String columnsStr = "insert into qa10_layercollection_header(c_idx, ";
		String valuesStr = "values(" + cIdx + ",";
		Map<String, Object> variablesMap = header.getDefaultValues();
		List<DXFVariable> variables = (List<DXFVariable>) variablesMap.get("defualtHeader");
		for (int i = 0; i < variables.size(); i++) {
			DXFVariable variable = variables.get(i);
			String name = variable.getName(); // column
			Iterator<String> keyIt = variable.getValueKeyIterator();
			while (keyIt.hasNext()) {
				String column = (String) keyIt.next(); // code
				Object value = variable.getValue(column); // value
				columnsStr += "\"" + column + "\"" + ", ";
				valuesStr += "'" + value + "'" + ", ";
			}
		}
		int lastIndextC = columnsStr.lastIndexOf(",");
		String returnQueryC = columnsStr.substring(0, lastIndextC) + ")";
		int lastIndextV = valuesStr.lastIndexOf(",");
		String returnQueryV = valuesStr.substring(0, lastIndextV) + ")";
		String returnQuery = returnQueryC + returnQueryV;
		HashMap<String, Object> query = new HashMap<String, Object>();
		query.put("insertQuery", returnQuery);
		return query;
	}

	public HashMap<String, Object> getInsertTables(int cIdx, QA10Tables tables) {

		String insertStr = "insert into " + "\"" + "qa10_layercollection_tables" + "\"" + "(" + "\"" + "LTYPE" + "\""
				+ "," + "\"" + "LAYER" + "\"" + "," + "\"" + "STYLE" + "\"" + "," + "\"" + "DIMSTYLE" + "\"" + ","
				+ "\"" + "VIEW" + "\"" + "," + "\"" + "VPORT" + "\"" + ", c_idx) values(" + tables.isLineTypes() + ", "
				+ tables.isLayers() + ", " + tables.isStyles() + ", false, false, false, " + cIdx + ")";

		HashMap<String, Object> insertMap = new HashMap<String, Object>();
		insertMap.put("insertQuery", insertStr);
		insertMap.put("tb_idx", 0);
		return insertMap;

	}

	public List<HashMap<String, Object>> getInsertTablesLineTypes(int tbIdx, Map<String, Object> lineTypes) {

		String tableNameLt = "qa10_layercollection_tables_LTYPE";
		List<HashMap<String, Object>> lineTypesQuery = getInsertTableEntityQuery(tbIdx, tableNameLt, "lineTypes",
				lineTypes);
		return lineTypesQuery;
	}

	public List<HashMap<String, Object>> getInsertTablesLayers(int tbIdx, Map<String, Object> layers) {

		String tableNameLa = "qa10_layercollection_tables_LAYER";
		List<HashMap<String, Object>> layersQuery = getInsertTableEntityQuery(tbIdx, tableNameLa, "layers", layers);
		return layersQuery;
	}

	public List<HashMap<String, Object>> getInsertTablesStyes(int tbIdx, Map<String, Object> styles) {

		String tableNameSt = "qa10_layercollection_tables_STYLE";
		List<HashMap<String, Object>> stylesQuerys = getInsertTableEntityQuery(tbIdx, tableNameSt, "styles", styles);
		return stylesQuerys;
	}

	private List<HashMap<String, Object>> getInsertTableEntityQuery(int tbIdx, String tableName, String entityName,
			Map<String, Object> entity) {

		List<HashMap<String, Object>> queries = new ArrayList<HashMap<String, Object>>();
		List<DXFVariable> valuses = (List<DXFVariable>) entity.get(entityName);
		for (int i = 0; i < valuses.size(); i++) {
			String defaultColumnsStr = "insert into " + "\"" + tableName + "\"" + "(";
			String defaultValuesStr = "values(";

			DXFVariable commons = (DXFVariable) entity.get("common");
			HashMap<String, Object> tmpMap1 = getDXFVariableColumns(commons);
			defaultColumnsStr += tmpMap1.get("columns");
			defaultValuesStr += tmpMap1.get("values");

			DXFVariable tmpVariable = valuses.get(i);
			HashMap<String, Object> tmpMap2 = getDXFVariableColumns(tmpVariable);
			defaultColumnsStr += tmpMap2.get("columns");
			defaultValuesStr += tmpMap2.get("values");
			defaultColumnsStr += "tb_idx, ";
			defaultValuesStr += tbIdx + ", ";

			int lastIndextC = defaultColumnsStr.lastIndexOf(",");
			String returnQueryC = defaultColumnsStr.substring(0, lastIndextC) + ")";
			int lastIndextV = defaultValuesStr.lastIndexOf(",");
			String returnQueryV = defaultValuesStr.substring(0, lastIndextV) + ")";
			String returnQuery = returnQueryC + returnQueryV;
			HashMap<String, Object> query = new HashMap<String, Object>();
			query.put("insertQuery", returnQuery);
			queries.add(query);
			defaultColumnsStr = "insert into " + "\"" + tableName + "\"" + "(";
			defaultValuesStr = "values(";
		}
		return queries;
	}

	private HashMap<String, Object> getDXFVariableColumns(DXFVariable variable) {

		String columnsStr = "";
		String valuesStr = "";
		String name = variable.getName(); // column
		Iterator<String> keyIt = variable.getValueKeyIterator();
		while (keyIt.hasNext()) {
			String column = (String) keyIt.next(); // code
			Object value = variable.getValue(column); // value
			columnsStr += "\"" + column + "\"" + ", ";
			valuesStr += "'" + value + "'" + ", ";
		}

		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("columns", columnsStr);
		returnMap.put("values", valuesStr);

		return returnMap;
	}

	public String getLayerType(String layerId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getLayerColumns() {
		List<String> columns = new ArrayList<String>();
		columns.add("layer_id");
		columns.add("feature_id");
		columns.add("geom");
		columns.add("feature_type");
		return columns;
	}

}
