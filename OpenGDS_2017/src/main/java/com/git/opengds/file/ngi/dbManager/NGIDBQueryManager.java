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

package com.git.opengds.file.ngi.dbManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.git.gdsbuilder.type.ngi.feature.DTNGIFeature;
import com.git.gdsbuilder.type.ngi.feature.DTNGIFeatureList;
import com.git.gdsbuilder.type.ngi.header.NDAField;
import com.git.gdsbuilder.type.ngi.header.NDAHeader;
import com.git.gdsbuilder.type.ngi.header.NGIHeader;
import com.git.gdsbuilder.type.ngi.layer.DTNGILayer;

/**
 * QA20DBQuery 생성 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 4:00:14
 */
public class NGIDBQueryManager {

	public HashMap<String, Object> getSelectNGILayerCollectionIdx(String collectionName) {

		String tableName = "\"" + "qa20_layercollection" + "\"";
		String selectQuery = "select c_idx from " + tableName + " where file_name = '" + collectionName + "'";
		HashMap<String, Object> selectQueryMap = new HashMap<String, Object>();
		selectQueryMap.put("selectQuery", selectQuery);

		return selectQueryMap;
	}

	public HashMap<String, Object> getInsertNGILayerMeataData(String type, String collectionName, Integer idx,
			DTNGILayer qa20Layer) {

		String layerID = qa20Layer.getLayerID(); // layer_id
		String layerName = qa20Layer.getLayerName(); // layer_name

		NGIHeader ngiHeader = qa20Layer.getNgiHeader();

		String version = ngiHeader.getVersion(); // file_version
		String ngiDim = ngiHeader.getDim(); // ngi_dim
		String ngiBound = ngiHeader.getBound(); // ngi_bound

		List<String> ptRepresentStr = ngiHeader.getPoint_represent(); // ngi_mask_point
		List<String> lnRepresentStr = ngiHeader.getLine_represent(); // ngi_mask_linestring
		List<String> rgRepresentStr = ngiHeader.getRegion_represent(); // ngi_mask_region
		List<String> txRepresentStr = ngiHeader.getText_represent(); // ngi_mask_text
		boolean ptRepresent = false;
		boolean lnRepresent = false;
		boolean rgRepresent = false;
		boolean txRepresent = false;
		if (ptRepresentStr.size() != 0) {
			ptRepresent = true;
		}
		if (lnRepresentStr.size() != 0) {
			lnRepresent = true;
		}
		if (rgRepresentStr.size() != 0) {
			rgRepresent = true;
		}
		if (txRepresentStr.size() != 0) {
			txRepresent = true;
		}
		String layerTableName = "\"geo" + "_" + type + "_" + collectionName + "_" + layerName + "\"";
		String insertQueryColumn = "insert into " + "\"qa20_layer_metadata" + "\""
				+ "(layer_id, layer_name, layer_t_name, file_version, ngi_dim, ngi_bound, ngi_mask_point, ngi_mask_linestring, ngi_mask_region, ngi_mask_text, c_idx, current_layer_name)";
		String insertQueryValue = "values('" + layerID + "', '" + layerName + "', '" + layerTableName + "', '" + version
				+ "', '" + ngiDim + "', '" + ngiBound + "', '" + ptRepresent + "', '" + lnRepresent + "', '"
				+ rgRepresent + "', '" + txRepresent + "', " + idx + ", '" + layerName + "')";

		HashMap<String, Object> insertQueryMap = new HashMap<String, Object>();
		insertQueryMap.put("insertQuery", insertQueryColumn + insertQueryValue);
		insertQueryMap.put("lm_idx", 0);
		return insertQueryMap;
	}

	public HashMap<String, Object> getNGILayerTbCreateQuery(String type, String collectionName, DTNGILayer ngiLayer,
			String src) {

		String layerTypeStr = ngiLayer.getLayerType();
		boolean isTextLayer = false;
		if (layerTypeStr.equals("TEXT")) {
			layerTypeStr = "POINT";
			isTextLayer = true;
		}
		String tableName = "\"geo" + "_" + type + "_" + collectionName + "_" + ngiLayer.getLayerName() + "\"";
		String defalutCreateQuery = "create table " + tableName + " (" + "f_idx serial primary key" + ","
				+ "feature_id varchar(100)" + "," + "feature_type varchar(100)" + "," + "geom geometry(" + layerTypeStr
				+ ", " + src + ")" + "," + "num_rings numeric" + "," + "num_vertexes numeric" + ", "
				+ "style_id varchar(100)" + ", ";

		if (isTextLayer) {
			defalutCreateQuery += "text varchar(100),";
		}

		NDAHeader ndaHeader = ngiLayer.getNdaHeader();
		if (ndaHeader != null) {
			List<NDAField> fields = ndaHeader.getAspatial_field_def();
			for (NDAField field : fields) {
				String key = "\"" + field.getFieldName() + "\"";
				String typeStr = field.getType();
				String valueType = "";
				if (typeStr.equals("string") || typeStr.equals("STRING") || typeStr.equals("String")) {
					valueType = "varchar(100)";
				} else {
					valueType = typeStr;
				}
				defalutCreateQuery += key + " " + valueType + ", ";
			}
		}

		int lastIndext = defalutCreateQuery.lastIndexOf(",");
		String returnQuery = defalutCreateQuery.substring(0, lastIndext) + ")";
		HashMap<String, Object> query = new HashMap<String, Object>();
		query.put("createQuery", returnQuery);

		return query;
	}

	public List<HashMap<String, Object>> getNGILayerInsertQuery(String type, String collectionName,
			DTNGILayer qa20Layer, String src) {

		String tableName = "\"geo" + "_" + type + "_" + collectionName + "_" + qa20Layer.getLayerName() + "\"";

		List<HashMap<String, Object>> dbLayers = new ArrayList<HashMap<String, Object>>();
		DTNGIFeatureList features = qa20Layer.getFeatures();
		for (int i = 0; i < features.size(); i++) {
			DTNGIFeature feature = features.get(i);

			// default
			String featureType = feature.getFeatureType();
			Integer numparts = null;
			Integer numVertexts = null;

			if (featureType.equals("POLYGON")) {
				numparts = Integer.parseInt(feature.getNumparts());
			}
			if (featureType.equals("LINESTRING") || featureType.equals("POLYGON")) {
				numVertexts = Integer.parseInt(feature.getCoordinateSize());
			}

			String insertDefaultQuery = "insert into " + tableName
					+ "(feature_id, feature_type, geom, num_rings, num_vertexes, style_id, ";
			String insertDefaultValues = " values('" + feature.getFeatureID() + "'," + "'" + featureType + "',"
					+ "ST_GeomFromText('" + feature.getGeom().toString() + "', " + src + ")" + "," + numparts + ","
					+ numVertexts + ", '" + feature.getStyleID() + "', ";

			if (featureType.equals("TEXT")) {
				insertDefaultQuery += "text, ";
				insertDefaultValues += "'" + feature.getText() + "', ";
			}

			// properties
			HashMap<String, Object> properties = feature.getProperties();
			int propertiesSize = properties.size();
			if (propertiesSize != 0) {
				Iterator keys = properties.keySet().iterator();
				while (keys.hasNext()) {
					String key = (String) keys.next();
					Object value = properties.get(key);

					if (value instanceof String) {
						insertDefaultQuery += "\"" + key + "\"" + ", ";
						insertDefaultValues += "'" + value + "', ";
					} else {
						insertDefaultQuery += "\"" + key + "\"" + ", ";
						insertDefaultValues += value + ", ";
					}
				}
			}
			int lastIndextC = insertDefaultQuery.lastIndexOf(",");
			String returnQueryC = insertDefaultQuery.substring(0, lastIndextC) + ")";
			int lastIndextV = insertDefaultValues.lastIndexOf(",");
			String returnQueryV = insertDefaultValues.substring(0, lastIndextV) + ")";

			String returnQuery = returnQueryC + returnQueryV;
			HashMap<String, Object> query = new HashMap<String, Object>();
			query.put("insertQuery", returnQuery);

			dbLayers.add(query);
		}
		return dbLayers;
	}

	public List<HashMap<String, Object>> getAspatialFieldDefsInsertQuery(int lmIdx, NDAHeader ndaHeader) {

		List<HashMap<String, Object>> fieldDefs = new ArrayList<HashMap<String, Object>>();

		if (ndaHeader == null) {
			return null;
		} else {
			List<NDAField> fields = ndaHeader.getAspatial_field_def();
			for (int i = 0; i < fields.size(); i++) {
				NDAField dtField = fields.get(i);

				String insertQueryColumns = "insert into nda_aspatial_field_def(f_name, f_type, f_size, f_decimal, f_isunique, lm_idx)";
				String insertQueryValues = "values ('" + dtField.getFieldName() + "', '" + dtField.getType() + "', "
						+ Integer.parseInt(dtField.getSize()) + ", '" + dtField.getDecimal() + "', '"
						+ dtField.isUnique() + "', " + lmIdx + ")";

				HashMap<String, Object> insertQueryMap = new HashMap<String, Object>();
				insertQueryMap.put("insertQuery", insertQueryColumns + insertQueryValues);
				fieldDefs.add(insertQueryMap);
			}
			return fieldDefs;
		}

	}

	public HashMap<String, Object> getAspatialFieldDefsInsertQuery(int lmIdx, NDAField dtField) {

		String insertQueryColumns = "insert into nda_aspatial_field_def(f_name, f_type, f_size, f_decimal, f_isunique, lm_idx)";
		String insertQueryValues = "values ('" + dtField.getFieldName() + "', '" + dtField.getType() + "', "
				+ Integer.parseInt(dtField.getSize()) + ", '" + dtField.getDecimal() + "', '" + dtField.isUnique()
				+ "', " + lmIdx + ")";

		HashMap<String, Object> insertQueryMap = new HashMap<String, Object>();
		insertQueryMap.put("insertQuery", insertQueryColumns + insertQueryValues);

		return insertQueryMap;
	}

	public List<HashMap<String, Object>> getPtRepresentInsertQuery(int lmIdx, List<String> ptList) {

		List<HashMap<String, Object>> fieldDefs = new ArrayList<HashMap<String, Object>>();

		if (ptList == null || ptList.size() == 0) {
			return null;
		} else {
			for (int i = 0; i < ptList.size(); i++) {
				HashMap<String, Object> dbPtRep = new HashMap<String, Object>();
				String ptReStr = ptList.get(i);

				int index = ptReStr.indexOf(" ");
				String no = ptReStr.substring(0, index);
				String value = ptReStr.substring(index + 1);

				String insertQueryColumns = "insert into ngi_point_represent(p_rep_no, p_rep_value, lm_idx)";
				String insertQueryValues = "values (" + Integer.parseInt(no) + ", " + "'" + value + "', " + lmIdx + ")";

				dbPtRep.put("insertQuery", insertQueryColumns + insertQueryValues);
				fieldDefs.add(dbPtRep);
			}
			return fieldDefs;
		}
	}

	public List<HashMap<String, Object>> getRgRepresentInsertQuery(int lmIdx, List<String> rgList) {

		List<HashMap<String, Object>> fieldDefs = new ArrayList<HashMap<String, Object>>();

		if (rgList == null || rgList.size() == 0) {
			return null;
		} else {
			for (int i = 0; i < rgList.size(); i++) {
				HashMap<String, Object> dbPtRep = new HashMap<String, Object>();
				String rgReStr = rgList.get(i);

				int index = rgReStr.indexOf(" ");
				String no = rgReStr.substring(0, index);
				String value = rgReStr.substring(index + 1);

				String insertQueryColumns = "insert into ngi_region_represent(r_rep_no, r_rep_value, lm_idx)";
				String insertQueryValues = "values (" + Integer.parseInt(no) + ", " + "'" + value + "', " + lmIdx + ")";

				dbPtRep.put("insertQuery", insertQueryColumns + insertQueryValues);
				fieldDefs.add(dbPtRep);
			}
			return fieldDefs;
		}

	}

	public List<HashMap<String, Object>> getTxtRepresentInsertQuery(int lmIdx, List<String> txtList) {

		List<HashMap<String, Object>> fieldDefs = new ArrayList<HashMap<String, Object>>();

		if (txtList == null || txtList.size() == 0) {
			return null;
		} else {
			for (int i = 0; i < txtList.size(); i++) {
				HashMap<String, Object> dbLnRep = new HashMap<String, Object>();
				String txtReStr = txtList.get(i);

				int index = txtReStr.indexOf(" ");
				String no = txtReStr.substring(0, index);
				String value = txtReStr.substring(index + 1);

				String insertQueryColumns = "insert into ngi_text_represent(t_rep_no, t_rep_value, lm_idx)";
				String insertQueryValues = "values (" + Integer.parseInt(no) + ", " + "'" + value + "', " + lmIdx + ")";

				dbLnRep.put("insertQuery", insertQueryColumns + insertQueryValues);
				fieldDefs.add(dbLnRep);
			}
			return fieldDefs;
		}
	}

	public List<HashMap<String, Object>> getLnRepresentInsertQuery(int lmIdx, List<String> lnList) {

		List<HashMap<String, Object>> fieldDefs = new ArrayList<HashMap<String, Object>>();

		if (lnList == null || lnList.size() == 0) {
			return null;
		} else {
			for (int i = 0; i < lnList.size(); i++) {
				HashMap<String, Object> dbLnRep = new HashMap<String, Object>();
				String lnReStr = lnList.get(i);

				int index = lnReStr.indexOf(" ");
				String no = lnReStr.substring(0, index);
				String value = lnReStr.substring(index + 1);

				String insertQueryColumns = "insert into ngi_linestring_represent(l_rep_no, l_rep_value, lm_idx)";
				String insertQueryValues = "values (" + Integer.parseInt(no) + ", " + "'" + value + "', " + lmIdx + ")";

				dbLnRep.put("insertQuery", insertQueryColumns + insertQueryValues);
				fieldDefs.add(dbLnRep);
			}
			return fieldDefs;
		}
	}

	public HashMap<String, Object> getInertNGIFeatureQuery(String tableName, DTNGIFeature createFeature, String src) {

		HashMap<String, Object> insertQuery = new HashMap<String, Object>();
		// default
		String featureType = createFeature.getFeatureType();
		Integer numparts = null;
		Integer numVertexts = null;

		if (featureType.equals("POLYGON")) {
			numparts = Integer.parseInt(createFeature.getNumparts());
		}
		if (featureType.equals("LINESTRING") || featureType.equals("POLYGON")) {
			numVertexts = Integer.parseInt(createFeature.getCoordinateSize());
		}

		String insertDefaultQuery = "insert into \"" + tableName + "\""
				+ "(feature_id, feature_type, geom, num_rings, num_vertexes, ";
		String insertDefaultValues = " values('" + createFeature.getFeatureID() + "'," + "'" + featureType + "',"
				+ "ST_GeomFromText('" + createFeature.getGeom().toString() + "'," + src + ")" + "," + numparts + ","
				+ numVertexts + ",";

		// properties
		HashMap<String, Object> properties = createFeature.getProperties();
		if (properties != null) {
			int propertiesSize = properties.size();
			if (propertiesSize != 0) {
				Iterator keys = properties.keySet().iterator();
				while (keys.hasNext()) {
					String key = (String) keys.next();
					Object value = properties.get(key);
					insertDefaultQuery += "\"" + key + "\"" + ", ";
					insertDefaultValues += "'" + value + "', ";
				}
			}
		}

		int lastIndextC = insertDefaultQuery.lastIndexOf(",");
		String returnQueryC = insertDefaultQuery.substring(0, lastIndextC) + ")";
		int lastIndextV = insertDefaultValues.lastIndexOf(",");
		String returnQueryV = insertDefaultValues.substring(0, lastIndextV) + ")";

		String returnQuery = returnQueryC + returnQueryV;
		insertQuery.put("insertQuery", returnQuery);
		return insertQuery;

	}

	public HashMap<String, Object> getSelectNGIFeatureIdxQuery(String tableName, String featureID) {

		HashMap<String, Object> selectQuery = new HashMap<String, Object>();
		String querytStr = "select f_idx from \"" + tableName + "\" where feature_id = '" + featureID + "'";
		selectQuery.put("selectQuery", querytStr);

		return selectQuery;

	}

	public HashMap<String, Object> getDeleteNGIFeatureQuery(String tableName, int fIdx) {

		HashMap<String, Object> deleteQuery = new HashMap<String, Object>();
		String queryStr = "delete from \"" + tableName + "\" where f_idx = '" + fIdx + "'";
		deleteQuery.put("deleteQuery", queryStr);

		return deleteQuery;
	}

	public HashMap<String, Object> getDropNGILayerQuery(String type, String collectionName, String layerName) {

		HashMap<String, Object> dropQueryMap = new HashMap<String, Object>();
		String layerTableName = "\"geo" + "_" + type + "_" + collectionName + "_" + layerName + "\"";
		String queryStr = "drop table " + layerTableName;
		dropQueryMap.put("dropQuery", queryStr);
		return dropQueryMap;
	}

	public HashMap<String, Object> getDropNGILayerQuery(String tableName) {

		HashMap<String, Object> dropQueryMap = new HashMap<String, Object>();
		String queryStr = "drop table " + tableName;
		dropQueryMap.put("dropQuery", queryStr);
		return dropQueryMap;
	}

	public HashMap<String, Object> getInsertNGILayerCollectionQuery(String collectionName) {

		String insertQuery = "insert into qa20_layercollection(file_name) values('" + collectionName + "')";
		HashMap<String, Object> insertQueryMap = new HashMap<String, Object>();
		insertQueryMap.put("insertQuery", insertQuery);
		return insertQueryMap;
	}

	public String getLayerType(String type) {

		if (type.equals("POINT")) {
			type = "Point";
		} else if (type.equals("LINESTRING")) {
			type = "LineString";
		} else if (type.equals("POLYGON")) {
			type = "Polygon";
		}
		return type;
	}

	public List<String> getLayerCoulmns(DTNGILayer layer) {

		List<String> columns = new ArrayList<String>();
		NDAHeader header = layer.getNdaHeader();
		if (header == null) {
			return null;
		}
		List<NDAField> fields = header.getAspatial_field_def();
		for (int i = 0; i < fields.size(); i++) {
			NDAField dtField = fields.get(i);
			columns.add(dtField.getFieldName());
		}
		return columns;
	}

	public HashMap<String, Object> selectCountAllNGIFeaturesQuery(String collectionType, String layerCollectionName,
			List<String> layerIDList) {

		String countQueryStr = "select ";
		for (int i = 0; i < layerIDList.size(); i++) {
			if (i > 0) {
				countQueryStr += " + ";
			}
			String layerID = layerIDList.get(i);
			String tableName = "\"geo" + "_" + collectionType + "_" + layerCollectionName + "_" + layerID + "\"";
			countQueryStr += "(select count (*) from " + tableName + ")";
		}
		countQueryStr += " as all_feature_count";
		HashMap<String, Object> countQueryMap = new HashMap<String, Object>();
		countQueryMap.put("countQuery", countQueryStr);
		return countQueryMap;
	}

	public HashMap<String, Object> getSelectNGILayerMetaDataIdxQuery(Integer cIdx) {
		HashMap<String, Object> selectQuery = new HashMap<String, Object>();
		String tableName = "\"" + "qa20_layer_metadata" + "\"";
		String selectQueryStr = "select lm_idx from " + tableName + " where c_idx = " + cIdx;
		selectQuery.put("selectAllQuery", selectQueryStr);
		return selectQuery;
	}

	public HashMap<String, Object> getSelectAllNGILayerMetaDataQuery(int lmIdx) {
		HashMap<String, Object> selectQuery = new HashMap<String, Object>();
		String tableName = "\"" + "qa20_layer_metadata" + "\"";
		String selectQueryStr = "select * from " + tableName + " where lm_idx = " + lmIdx;
		selectQuery.put("selectAllQuery", selectQueryStr);
		return selectQuery;
	}

	public HashMap<String, Object> getSelectNGILayerTableNameQuery(Integer mIdx) {
		HashMap<String, Object> selectQuery = new HashMap<String, Object>();
		String tableName = "\"" + "qa20_layer_metadata" + "\"";
		String selectQueryStr = "select layer_t_name from " + tableName + " where lm_idx = " + mIdx;
		selectQuery.put("selectQuery", selectQueryStr);
		return selectQuery;
	}

	public HashMap<String, Object> getDeleteTextRepresentQuery(Integer mIdx) {
		HashMap<String, Object> deleteQuery = new HashMap<String, Object>();
		String tableName = "\"" + "ngi_text_represent" + "\"";
		String deleteQueryStr = "delete from " + tableName + " where lm_idx = " + mIdx;
		deleteQuery.put("deleteQuery", deleteQueryStr);
		return deleteQuery;
	}

	public HashMap<String, Object> getDeletePointRepresentQuery(Integer mIdx) {
		HashMap<String, Object> deleteQuery = new HashMap<String, Object>();
		String tableName = "ngi_point_represent";
		String deleteQueryStr = "delete from " + tableName + " where lm_idx = " + mIdx;
		deleteQuery.put("deleteQuery", deleteQueryStr);
		return deleteQuery;
	}

	public HashMap<String, Object> getDeleteLineStringRepresentQuery(Integer mIdx) {
		HashMap<String, Object> deleteQuery = new HashMap<String, Object>();
		String tableName = "ngi_linestring_represent";
		String deleteQueryStr = "delete from " + tableName + " where lm_idx = " + mIdx;
		deleteQuery.put("deleteQuery", deleteQueryStr);
		return deleteQuery;
	}

	public HashMap<String, Object> getDeleteRegionRepresentQuery(Integer mIdx) {
		HashMap<String, Object> deleteQuery = new HashMap<String, Object>();
		String tableName = "ngi_region_represent";
		String deleteQueryStr = "delete from " + tableName + " where lm_idx = " + mIdx;
		deleteQuery.put("deleteQuery", deleteQueryStr);
		return deleteQuery;
	}

	public HashMap<String, Object> getDeleteAsptialFieldQuery(Integer mIdx) {
		HashMap<String, Object> deleteQuery = new HashMap<String, Object>();
		String tableName = "nda_aspatial_field_def";
		String deleteQueryStr = "delete from " + tableName + " where lm_idx = " + mIdx;
		deleteQuery.put("deleteQuery", deleteQueryStr);
		return deleteQuery;
	}

	public HashMap<String, Object> getDeleteNGILayerMetaQuery(Integer mIdx) {
		HashMap<String, Object> deleteQuery = new HashMap<String, Object>();
		String tableName = "\"" + "qa20_layer_metadata" + "\"";
		String deleteQueryStr = "delete from " + tableName + " where lm_idx = " + mIdx;
		deleteQuery.put("deleteQuery", deleteQueryStr);
		return deleteQuery;
	}

	public HashMap<String, Object> getDeleteNGILayerCollectionQuery(Integer cIdx) {
		HashMap<String, Object> deleteQuery = new HashMap<String, Object>();
		String tableName = "\"" + "qa20_layercollection" + "\"";
		String deleteQueryStr = "delete from " + tableName + " where c_idx = " + cIdx;
		deleteQuery.put("deleteQuery", deleteQueryStr);
		return deleteQuery;
	}

	public HashMap<String, Object> getSelectNGILayerMetaDataIdxQuery(Integer collectionIdx, String layerName) {
		HashMap<String, Object> selectQuery = new HashMap<String, Object>();
		String tableName = "\"" + "qa20_layer_metadata" + "\"";
		String selectQueryStr = "select lm_idx from " + tableName + " where c_idx = " + collectionIdx + " and "
				+ "current_layer_name = '" + layerName + "'";
		selectQuery.put("selectQuery", selectQueryStr);
		return selectQuery;
	}

	public HashMap<String, Object> getUpdateNGILayerMeataLayerNameQuery(Integer lmIdx, String currentName) {
		HashMap<String, Object> updateQuery = new HashMap<String, Object>();
		String tableName = "\"" + "qa20_layer_metadata" + "\"";
		String updateQueryStr = "update " + tableName + " set current_layer_name = '" + currentName + "'"
				+ "where lm_idx = " + lmIdx;
		updateQuery.put("updateQuery", updateQueryStr);
		return updateQuery;
	}

	public HashMap<String, Object> getUpdateNGILayerMeataBoundaryQuery(Integer lmIdx, String boundary) {
		HashMap<String, Object> updateQuery = new HashMap<String, Object>();
		String tableName = "\"" + "qa20_layer_metadata" + "\"";
		String updateQueryStr = "update " + tableName + " set ngi_bound = '" + boundary + "'" + "where lm_idx = "
				+ lmIdx;
		updateQuery.put("updateQuery", updateQueryStr);
		return updateQuery;
	}

	public HashMap<String, Object> getNdaAspatialFieldFidxQuery(Integer lmIdx, String originFieldName) {
		HashMap<String, Object> selectQuery = new HashMap<String, Object>();
		String tableName = "\"" + "nda_aspatial_field_def" + "\"";
		String selectQueryStr = "select f_idx, f_name from " + tableName + " where lm_idx = " + lmIdx
				+ " and f_name = '" + originFieldName + "'";
		selectQuery.put("selectQuery", selectQueryStr);
		return selectQuery;
	}

	public HashMap<String, Object> updateNdaAspatialFieldQuery(int fIdx, NDAField modifiedfield) {
		HashMap<String, Object> updateQuery = new HashMap<String, Object>();
		String tableName = "\"" + "nda_aspatial_field_def" + "\"";

		String updateQueryStr = "update " + tableName + " set ";

		String fieldName = modifiedfield.getFieldName();
		String type = modifiedfield.getType();
		String size = modifiedfield.getSize();
		String decimal = modifiedfield.getDecimal();
		String isUniqueStr = "";
		boolean isUnique = modifiedfield.isUnique();
		// if (isUnique) {
		// isUniqueStr = "TRUE";
		// } else {
		// isUniqueStr = "FALSE";
		// }
		updateQueryStr += "f_name = '" + fieldName + "', f_type = '" + type + "', f_size = " + size + ", f_decimal = '"
				+ decimal + "', f_isunique = " + isUnique;
		updateQueryStr += " where f_idx = " + fIdx;
		updateQuery.put("updateQuery", updateQueryStr);

		return updateQuery;
	}

	public HashMap<String, Object> getSelectTextRepresentQuery(int lmIdx) {
		HashMap<String, Object> selectQuery = new HashMap<String, Object>();
		String tableName = "\"" + "ngi_text_represent" + "\"";
		String selectQueryStr = "select * from " + tableName + " where lm_idx = " + lmIdx;
		selectQuery.put("selectAllQuery", selectQueryStr);
		return selectQuery;
	}

	public HashMap<String, Object> getSelectResionRepresentQuery(int lmIdx) {
		HashMap<String, Object> selectQuery = new HashMap<String, Object>();
		String tableName = "\"" + "ngi_region_represent" + "\"";
		String selectQueryStr = "select * from " + tableName + " where lm_idx = " + lmIdx;
		selectQuery.put("selectAllQuery", selectQueryStr);
		return selectQuery;
	}

	public HashMap<String, Object> getSelectPointRepresentQuery(int lmIdx) {
		HashMap<String, Object> selectQuery = new HashMap<String, Object>();
		String tableName = "\"" + "ngi_point_represent" + "\"";
		String selectQueryStr = "select * from " + tableName + " where lm_idx = " + lmIdx;
		selectQuery.put("selectAllQuery", selectQueryStr);
		return selectQuery;
	}

	public HashMap<String, Object> getSelectLineRepresentQuery(int lmIdx) {
		HashMap<String, Object> selectQuery = new HashMap<String, Object>();
		String tableName = "\"" + "ngi_linestring_represent" + "\"";
		String selectQueryStr = "select * from " + tableName + " where lm_idx = " + lmIdx;
		selectQuery.put("selectAllQuery", selectQueryStr);
		return selectQuery;
	}

	public HashMap<String, Object> getSelectNadAspatialFieldQuery(int lmIdx) {
		HashMap<String, Object> selectQuery = new HashMap<String, Object>();
		String tableName = "\"" + "nda_aspatial_field_def" + "\"";
		String selectQueryStr = "select * from " + tableName + " where lm_idx = " + lmIdx;
		selectQuery.put("selectAllQuery", selectQueryStr);
		return selectQuery;
	}

	public HashMap<String, Object> getSelectAllNGIFeaturesQuery(String layerTbName,
			List<HashMap<String, Object>> aspatialFields) {

		String selectQueryStr = "select f_idx, feature_id, feature_type, ST_AsText(geom) as geom, num_rings, num_vertexes, style_id, ";
		String[] strs = layerTbName.replaceAll("\"", "").split("_");
		String layerType = strs[4];
		if (layerType.equals("TEXT")) {
			selectQueryStr += "text, ";
		}
		int size = aspatialFields.size();
		for (int i = 0; i < size; i++) {
			HashMap<String, Object> fieldMap = aspatialFields.get(i);
			String fieldName = (String) fieldMap.get("f_name");
			selectQueryStr += "\"" + fieldName + "\"";
			if (i == size - 1) {
				selectQueryStr += " from " + layerTbName;
			} else {
				selectQueryStr += ", ";
			}
		}

		HashMap<String, Object> selectQuery = new HashMap<String, Object>();
		// String selectQueryStr = "select * from " + layerTbName;
		selectQuery.put("selectAllQuery", selectQueryStr);
		return selectQuery;
	}

	public HashMap<String, Object> getDeleteNGIProgressQuery(int cIdx) {
		HashMap<String, Object> deleteQuery = new HashMap<String, Object>();
		String tableName = "qa20_layercollection_qa_progress";
		String deleteQueryStr = "delete from " + tableName + " where c_idx = " + cIdx;
		deleteQuery.put("deleteQuery", deleteQueryStr);
		return deleteQuery;
	}

	public HashMap<String, Object> getDeleteDXFProgressQuery(int cIdx) {
		HashMap<String, Object> deleteQuery = new HashMap<String, Object>();
		String tableName = "qa10_layercollection_qa_progress";
		String deleteQueryStr = "delete from " + tableName + " where c_idx = " + cIdx;
		deleteQuery.put("deleteQuery", deleteQueryStr);
		return deleteQuery;
	}

	public HashMap<String, Object> getDeleteNGILayerCollection(int cIdx) {
		HashMap<String, Object> deleteQuery = new HashMap<String, Object>();
		String tableName = "\"" + "qa20_layercollection" + "\"";
		String deleteQueryStr = "delete from " + tableName + " where c_idx = " + cIdx;
		deleteQuery.put("deleteQuery", deleteQueryStr);
		return deleteQuery;
	}

}
