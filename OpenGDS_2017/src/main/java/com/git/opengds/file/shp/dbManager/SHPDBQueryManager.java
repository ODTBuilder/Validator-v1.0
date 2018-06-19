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

/**
 * SHP 파일 관련된 DB Table Query를 생성하는 클래스
 * 
 * @author GIT
 *
 */
public class SHPDBQueryManager {

	/**
	 * SHP 파일 Collection (다수의 SHP 파일을 압축) Insert Query를 Map 형태로 반환
	 *
	 * @param collectionName
	 *            SHP 파일 Collection명
	 * @return HashMap<String, Object>
	 */
	public HashMap<String, Object> getInsertSHPLayerCollectionQuery(String collectionName) {

		String insertQuery = "insert into shp_layercollection(c_name) values('" + collectionName + "')";
		HashMap<String, Object> insertQueryMap = new HashMap<String, Object>();
		insertQueryMap.put("insertQuery", insertQuery);
		return insertQueryMap;
	}

	/**
	 * SHP 파일 레이어 Table 생성 Query를 Map 형태로 반환
	 * 
	 * @param type
	 *            파일 포맷
	 * @param collectionName
	 *            SHP 파일 Collection명
	 * @param shpLayer
	 *            SHP 파일 레이어명
	 * @param src
	 *            EPSG 코드 (ex. EPSG:4326 -> 4326)
	 * @return HashMap<String, Object>
	 */
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

	/**
	 * SHP 파일 레이어 Table Insert Query를 Map 형태로 반환
	 * 
	 * @param type
	 *            파일 포맷
	 * @param collectionName
	 *            SHP 파일 Collection명
	 * @param attributeColumn
	 *            SHP 속성 Column 목록
	 * @param shpLayer
	 *            SHP 파일 레이어
	 * @param src
	 *            EPSG 코드 (ex. EPSG:4326 -> 4326)
	 * @return List<HashMap<String, Object>>
	 */
	public List<HashMap<String, Object>> getSHPLayerInsertQuery(String type, String collectionName,
			List<String> attributeColumn, DTSHPLayer shpLayer, String src) {

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
			for (int i = 0; i < attributeColumn.size(); i++) {
				String attriKey = attributeColumn.get(i);
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

	/**
	 * SHP 파일 Metadata Table Insert Query를 Map 형태로 반환
	 * 
	 * @param type
	 *            파일 포맷
	 * @param collectionName
	 *            SHP 파일 Collection명
	 * @param shpLayer
	 *            SHP 파일 레이어
	 * @param cIdx
	 *            SHP 파일 Collection Table Index
	 * @return HashMap<String, Object>
	 */
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

	/**
	 * SimpleFeature 객체를 SHP 파일 레이어 Table Insert Query를 Map 형태로 반환
	 * 
	 * @param tableName
	 *            SHP 파일 레이어 Table명
	 * @param simpleFeature
	 *            SimpleFeature 객체
	 * @param src
	 *            EPSG 코드 (ex. EPSG:4326 -> 4326)
	 * @return HashMap<String, Object>
	 */
	public HashMap<String, Object> getInsertSHPFeatureQuery(String tableName, SimpleFeature simpleFeature, String src) {

		String insertColumns = "insert into \"" + tableName + "\"(";
		String insertValues = "values(";

		SimpleFeatureType sft = simpleFeature.getFeatureType();

		List<AttributeDescriptor> attriDescriptros = sft.getAttributeDescriptors();
		for (int j = 0; j < attriDescriptros.size(); j++) {
			AttributeDescriptor attriDescriptor = attriDescriptros.get(j);
			String attriName = attriDescriptor.getName().toString();
			if (attriName.equals("geom")) {
				insertColumns += "geom, ";
				Geometry geom = (Geometry) simpleFeature.getAttribute(attriName);
				insertValues += "ST_GeomFromText('" + geom.toString() + "', " + src + "), ";
			} else {
				insertColumns += attriName + ", ";
				Object attriValue = simpleFeature.getAttribute(attriName);
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

	/**
	 * SHP 파일 레이어 Table에서 featureId에 해당하는 f_idx(Table Index)값 Selete Query 생성
	 * 
	 * @param tableName
	 *            SHP 파일 레이어 Table명
	 * @param featureId
	 *            피처 Id
	 * @return HashMap<String, Object>
	 */
	public HashMap<String, Object> getSelectSHPFeatureIdxQuery(String tableName, String featureId) {

		HashMap<String, Object> selectQuery = new HashMap<String, Object>();
		String querytStr = "select f_idx from \"" + tableName + "\" where feature_id = '" + featureId + "'";
		selectQuery.put("selectQuery", querytStr);

		return selectQuery;

	}

	/**
	 * SHP 파일 레이어 Table에서 fIdx에 해당하는 행 Delete Query 생성
	 * 
	 * @param tableName
	 *            SHP 파일 레이어 Table명
	 * @param fIdx
	 *            피처 Index
	 * @return HashMap<String, Object>
	 */
	public HashMap<String, Object> getDeleteSHPFeatureQuery(String tableName, int fIdx) {

		HashMap<String, Object> deleteQuery = new HashMap<String, Object>();
		String queryStr = "delete from \"" + tableName + "\" where f_idx = '" + fIdx + "'";
		deleteQuery.put("deleteQuery", queryStr);

		return deleteQuery;
	}

	/**
	 * SHP 파일 레이어 Collection Table에서 collectionName에 해당하는 c_idx(Table Index) Selete
	 * Query 생성
	 * 
	 * @param collectionName
	 *            SHP 파일 레이어 Collection명
	 * @return HashMap<String, Object>
	 */
	public HashMap<String, Object> getSelectSHPLayerCollectionIdx(String collectionName) {

		String tableName = "\"" + "shp_layercollection" + "\"";
		String selectQuery = "select c_idx from " + tableName + " where c_name = '" + collectionName + "'";
		HashMap<String, Object> selectQueryMap = new HashMap<String, Object>();
		selectQueryMap.put("selectQuery", selectQuery);

		return selectQueryMap;
	}

	/**
	 * SHP 파일 레이어 Metadata Table에서 cIdx 및 layerName에 해당하는 lm_idx(Metadata Table
	 * Index) Selete Query 생성
	 * 
	 * @param cIdx
	 *            SHP 파일 레이어 Collection Index
	 * @param layerName
	 *            레이어명
	 * @return HashMap<String, Object>
	 */
	public HashMap<String, Object> getSelectSHPLayerMetaDataIdxQuery(Integer cIdx, String layerName) {

		HashMap<String, Object> selectQuery = new HashMap<String, Object>();
		String tableName = "\"" + "shp_layer_metadata" + "\"";
		String selectQueryStr = "select lm_idx from " + tableName + " where c_idx = " + cIdx + " and "
				+ "current_layer_name = '" + layerName + "'";
		selectQuery.put("selectQuery", selectQueryStr);
		return selectQuery;
	}

	/**
	 * SHP 파일 레이어 Metadata Table에서 mIdx(Metadata Table Index)에 해당하는 행 Delete Query
	 * 생성
	 * 
	 * @param mIdx
	 *            SHP 파일 레이어 Metadata Table Index
	 * @return HashMap<String, Object>
	 */
	public HashMap<String, Object> getDeleteSHPLayerMetaQuery(Integer mIdx) {

		HashMap<String, Object> deleteQuery = new HashMap<String, Object>();
		String tableName = "\"" + "shp_layer_metadata" + "\"";
		String deleteQueryStr = "delete from " + tableName + " where lm_idx = " + mIdx;
		deleteQuery.put("deleteQuery", deleteQueryStr);
		return deleteQuery;
	}

	/**
	 * SHP 파일 레이어 Table Drop Query 생성
	 * 
	 * @param type
	 *            파일포맷
	 * @param collectionName
	 *            SHP 파일 레이어 Collection명
	 * @param layerName
	 *            SHP 파일 레이어명
	 * @return HashMap<String, Object>
	 */
	public HashMap<String, Object> getDropSHPLayerQuery(String type, String collectionName, String layerName) {

		HashMap<String, Object> dropQueryMap = new HashMap<String, Object>();
		String layerTableName = "\"geo" + "_" + type + "_" + collectionName + "_" + layerName + "\"";
		String queryStr = "drop table " + layerTableName;
		dropQueryMap.put("dropQuery", queryStr);
		return dropQueryMap;
	}

	/**
	 * SHP 파일 레이어 Collection Table에서 cIdx(SHP 파일 레이어 Collection Table Index)에 해당하는 행
	 * Delete Query 생성
	 * 
	 * @param cIdx
	 *            SHP 파일 레이어 Collection Table Index
	 * @return HashMap<String, Object>
	 */
	public HashMap<String, Object> getDeleteSHPLayerCollectionQuery(Integer cIdx) {
		HashMap<String, Object> deleteQuery = new HashMap<String, Object>();
		String tableName = "\"" + "shp_layercollection" + "\"";
		String deleteQueryStr = "delete from " + tableName + " where c_idx = " + cIdx;
		deleteQuery.put("deleteQuery", deleteQueryStr);
		return deleteQuery;
	}

	/**
	 * SHP 파일 레이어 검수 진행 상태 Table에 검수 요청 값 Insert Query 생성
	 * 
	 * @param validateState
	 *            검수 진행 상태
	 * @param collectionName
	 *            SHP 파일 레이어 Collection명
	 * @param fileType
	 *            파일포맷
	 * @param cidx
	 *            SHP 파일 레이어 Collection Index
	 * @return HashMap<String, Object>
	 */
	public HashMap<String, Object> getInsertSHPRequestState(int validateState, String collectionName, String fileType,
			int cidx) {
		String tableName = "\"" + "shp_layercollection_qa_progress" + "\"";
		String insertQueryStr = " insert into " + tableName
				+ "(collection_name, file_type, state, request_time , c_idx) values ('" + collectionName + "'," + "'"
				+ fileType + "', " + validateState + ", " + "CURRENT_TIMESTAMP" + ", " + cidx + ")";
		HashMap<String, Object> insertQueryMap = new HashMap<String, Object>();
		insertQueryMap.put("insertQuery", insertQueryStr);
		return insertQueryMap;
	}

	/**
	 * SHP 파일 레이어 Collection Table에서 collectionName에 해당하는 cIdx(SHP 파일 레이어 Collection
	 * Table Index) Select Query 생성
	 * 
	 * @param collectionName
	 *            SHP 파일 레이어 Collection명
	 * @return HashMap<String, Object>
	 */
	public HashMap<String, Object> getSelectSHPLayerCollectionIdxQuery(String collectionName) {

		String tableName = "\"" + "shp_layercollection" + "\"";
		String selectQuery = "select c_idx from " + tableName + " where c_name = '" + collectionName + "'";
		HashMap<String, Object> selectQueryMap = new HashMap<String, Object>();
		selectQueryMap.put("selectQuery", selectQuery);

		return selectQueryMap;
	}

	/**
	 * SHP 파일 레이어 Table Columns List 생성
	 * 
	 * @param createLayer
	 *            DTSHPLayer 객체
	 * @return List<String>
	 */
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
