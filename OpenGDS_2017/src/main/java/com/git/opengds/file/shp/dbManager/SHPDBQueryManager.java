package com.git.opengds.file.shp.dbManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.feature.type.AttributeType;

import com.git.gdsbuilder.type.shp.layer.DTSHPLayer;
import com.vividsolutions.jts.geom.Geometry;

public class SHPDBQueryManager {

	public HashMap<String, Object> getInsertSHPLayerCollectionQuery(String collectionName) {

		String insertQuery = "insert into shp_layercollection(c_name) values('" + collectionName + "')";
		HashMap<String, Object> insertQueryMap = new HashMap<String, Object>();
		insertQueryMap.put("insertQuery", insertQuery);
		return insertQueryMap;
	}

	public HashMap<String, Object> getSHPLayerTbCreateQuery(String type, String collectionName, DTSHPLayer shpLayer,
			String src) {

		String layerName = shpLayer.getLayerName();
		String layerTypeStr = shpLayer.getLayerType();

		String tableName = "\"geo" + "_" + type + "_" + collectionName + "_" + layerName + "\"";
		String defalutCreateQuery = "create table " + tableName + "(f_idx serial primary key" + ","
				+ "feature_id varchar(100)" + "," + "feature_type varchar(100)" + "," + "geom geometry(" + layerTypeStr
				+ ", " + src + ")" + ", ";

		List<String> attriKeyList = new ArrayList<>();
		SimpleFeatureCollection featureCollection = shpLayer.getSimpleFeatureCollection();

		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		if (featureCollection != null) {
			SimpleFeatureType sft = featureCollection.getSchema();
			List<AttributeDescriptor> attriDescriptros = sft.getAttributeDescriptors();
			for (int j = 0; j < attriDescriptros.size(); j++) {
				AttributeDescriptor attriDescriptor = attriDescriptros.get(j);
				String attriName = attriDescriptor.getName().toString();
				if (attriName.equals("the_geom")) {
					continue;
				} else {
					defalutCreateQuery += attriName;
					AttributeType attriType = attriDescriptor.getType();
					String typeName = attriType.getBinding().getSimpleName();
					if (typeName.equals("String")) {
						defalutCreateQuery += " varchar(100), ";
					} else if (typeName.equals("int") || typeName.equals("Integer")) {
						defalutCreateQuery += " int, ";
					} else if (typeName.equals("Double")) {
						defalutCreateQuery += " double precision, ";
					} else if (typeName.equals("Long")) {
						defalutCreateQuery += " int, ";
					}
					attriKeyList.add(attriName);
				}
			}
			returnMap.put("attriKeyList", attriKeyList);
		}
		int lastIndext = defalutCreateQuery.lastIndexOf(",");
		String returnQuery = defalutCreateQuery.substring(0, lastIndext) + ")";
		HashMap<String, Object> createQuery = new HashMap<String, Object>();
		createQuery.put("createQuery", returnQuery);
		returnMap.put("createQueryMap", createQuery);

		return returnMap;
	}

	public List<HashMap<String, Object>> getSHPLayerInsertQuery(String type, String collectionName,
			List<String> attriKeyList, DTSHPLayer shpLayer, String src) {

		String layerName = shpLayer.getLayerName();

		String tableName = "\"geo" + "_" + type + "_" + collectionName + "_" + layerName + "\"";
		List<HashMap<String, Object>> insertQuerys = new ArrayList<HashMap<String, Object>>();
		SimpleFeatureCollection featureCollection = shpLayer.getSimpleFeatureCollection();
		SimpleFeatureIterator iterator = featureCollection.features();
		while (iterator.hasNext()) {
			SimpleFeature feature = iterator.next();
			Geometry geom = (Geometry) feature.getDefaultGeometry();
			String insertColumns = "insert into " + tableName + "(feature_id,  feature_type, geom, ";
			String insertValues = "values('" + feature.getID() + "', '" + geom.getGeometryType().toUpperCase() + "', "
					+ "ST_GeomFromText('" + geom.toString() + "', " + src + "), ";
			for (int i = 0; i < attriKeyList.size(); i++) {
				String attriKey = attriKeyList.get(i);
				Object attriValue = feature.getAttribute(attriKey);
				insertColumns += attriKey + ", ";
				if (attriValue instanceof String) {
					insertValues += "'" + attriValue + "', ";
				} else {
					insertValues += attriValue + ", ";
				}
			}
			int lastIndextC = insertColumns.lastIndexOf(",");
			String returnQueryC = insertColumns.substring(0, lastIndextC) + ")";
			int lastIndextV = insertValues.lastIndexOf(",");
			String returnQueryV = insertValues.substring(0, lastIndextV) + ")";

			String returnQuery = returnQueryC + returnQueryV;
			HashMap<String, Object> insertQuery = new HashMap<String, Object>();
			insertQuery.put("insertQuery", returnQuery);
			insertQuerys.add(insertQuery);
		}
		return insertQuerys;
	}

	public HashMap<String, Object> getSHPLayerMetaInertQuery(String type, String collectionName, DTSHPLayer shpLayer,
			int cIdx) {

		String layerName = shpLayer.getLayerName();
		String tableName = "geo" + "_" + type + "_" + collectionName + "_" + layerName;

		String insertQueryColumn = "insert into shp_layer_metadata(layer_name, layer_t_name, c_idx, current_layer_name)";
		String insertQueryValues = "values('" + layerName + "', " + "'" + tableName + "', " + cIdx + ", '" + layerName
				+ "')";

		HashMap<String, Object> insertQueryMap = new HashMap<String, Object>();
		insertQueryMap.put("insertQuery", insertQueryColumn + insertQueryValues);

		return insertQueryMap;
	}

	public HashMap<String, Object> getInsertSHPFeatureQuery(String tableName, SimpleFeature createFeature, String src) {

		String insertColumns = "insert into \"" + tableName + "\"(";
		String insertValues = "values(";

		SimpleFeatureType sft = createFeature.getFeatureType();

		List<AttributeDescriptor> attriDescriptros = sft.getAttributeDescriptors();
		for (int j = 0; j < attriDescriptros.size(); j++) {
			AttributeDescriptor attriDescriptor = attriDescriptros.get(j);
			String attriName = attriDescriptor.getName().toString();
			if (attriName.equals("geom")) {
				insertColumns += "geom, ";
				Geometry geom = (Geometry) createFeature.getAttribute(attriName);
				insertValues += "ST_GeomFromText('" + geom.toString() + "', " + src + "), ";
			} else {
				insertColumns += attriName + ", ";
				Object attriValue = createFeature.getAttribute(attriName);
				if (attriValue instanceof String) {
					insertValues += "'" + attriValue + "', ";
				} else {
					insertValues += attriValue + ", ";
				}
			}
		}
		int lastIndextC = insertColumns.lastIndexOf(",");
		String returnQueryC = insertColumns.substring(0, lastIndextC) + ")";
		int lastIndextV = insertValues.lastIndexOf(",");
		String returnQueryV = insertValues.substring(0, lastIndextV) + ")";

		String returnQuery = returnQueryC + returnQueryV;
		HashMap<String, Object> insertQuery = new HashMap<String, Object>();
		insertQuery.put("insertQuery", returnQuery);
		return insertQuery;
	}

	public HashMap<String, Object> getSelectSHPFeatureIdxQuery(String tableName, String featureId) {

		HashMap<String, Object> selectQuery = new HashMap<String, Object>();
		String querytStr = "select f_idx from \"" + tableName + "\" where feature_id = '" + featureId + "'";
		selectQuery.put("selectQuery", querytStr);

		return selectQuery;

	}

	public HashMap<String, Object> getDeleteSHPFeatureQuery(String tableName, int fIdx) {

		HashMap<String, Object> deleteQuery = new HashMap<String, Object>();
		String queryStr = "delete from \"" + tableName + "\" where f_idx = '" + fIdx + "'";
		deleteQuery.put("deleteQuery", queryStr);

		return deleteQuery;
	}

	public HashMap<String, Object> getSelectSHPLayerCollectionIdx(String collectionName) {

		String tableName = "\"" + "shp_layercollection" + "\"";
		String selectQuery = "select c_idx from " + tableName + " where c_name = '" + collectionName + "'";
		HashMap<String, Object> selectQueryMap = new HashMap<String, Object>();
		selectQueryMap.put("selectQuery", selectQuery);

		return selectQueryMap;
	}

	public HashMap<String, Object> getSelectSHPLayerMetaDataIdxQuery(Integer cIdx, String layerName) {

		HashMap<String, Object> selectQuery = new HashMap<String, Object>();
		String tableName = "\"" + "shp_layer_metadata" + "\"";
		String selectQueryStr = "select lm_idx from " + tableName + " where c_idx = " + cIdx + " and "
				+ "current_layer_name = '" + layerName + "'";
		selectQuery.put("selectQuery", selectQueryStr);
		return selectQuery;
	}

	public HashMap<String, Object> getDeleteSHPLayerMetaQuery(Integer mIdx) {

		HashMap<String, Object> deleteQuery = new HashMap<String, Object>();
		String tableName = "\"" + "shp_layer_metadata" + "\"";
		String deleteQueryStr = "delete from " + tableName + " where lm_idx = " + mIdx;
		deleteQuery.put("deleteQuery", deleteQueryStr);
		return deleteQuery;
	}

	public HashMap<String, Object> getDropSHPLayerQuery(String type, String collectionName, String layerName) {

		HashMap<String, Object> dropQueryMap = new HashMap<String, Object>();
		String layerTableName = "\"geo" + "_" + type + "_" + collectionName + "_" + layerName + "\"";
		String queryStr = "drop table " + layerTableName;
		dropQueryMap.put("dropQuery", queryStr);
		return dropQueryMap;
	}

	public HashMap<String, Object> getDeleteSHPLayerCollectionQuery(Integer cIdx) {
		HashMap<String, Object> deleteQuery = new HashMap<String, Object>();
		String tableName = "\"" + "shp_layercollection" + "\"";
		String deleteQueryStr = "delete from " + tableName + " where c_idx = " + cIdx;
		deleteQuery.put("deleteQuery", deleteQueryStr);
		return deleteQuery;
	}

	public HashMap<String, Object> getInsertSHPRequestState(int validateStart, String collectionName, String fileType,
			int cidx) {
		String tableName = "\"" + "shp_layercollection_qa_progress" + "\"";
		String insertQueryStr = " insert into " + tableName
				+ "(collection_name, file_type, state, request_time , c_idx) values ('" + collectionName + "'," + "'" + fileType
				+ "', " + validateStart + ", " + "CURRENT_TIMESTAMP" + ", " + cidx + ")";
		HashMap<String, Object> insertQueryMap = new HashMap<String, Object>();
		insertQueryMap.put("insertQuery", insertQueryStr);
		return insertQueryMap;
	}

	public HashMap<String, Object> getSelectSHPLayerCollectionIdxQuery(String collectionName) {

		String tableName = "\"" + "shp_layercollection" + "\"";
		String selectQuery = "select c_idx from " + tableName + " where c_name = '" + collectionName + "'";
		HashMap<String, Object> selectQueryMap = new HashMap<String, Object>();
		selectQueryMap.put("selectQuery", selectQuery);

		return selectQueryMap;
	}

	public List<String> getLayerCoulmns(DTSHPLayer createLayer) {

		List<String> columns = new ArrayList<>();
		SimpleFeatureCollection sfc = createLayer.getSimpleFeatureCollection();
		SimpleFeatureType sft = sfc.getSchema();
		List<AttributeDescriptor> attriDescriptros = sft.getAttributeDescriptors();
		for (int j = 0; j < attriDescriptros.size(); j++) {
			AttributeDescriptor attriDescriptor = attriDescriptros.get(j);
			String attriName = attriDescriptor.getName().toString();
			columns.add(attriName);
		}
		return columns;
	}
}
