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
import java.util.Map;

import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.qa20.collection.QA20LayerCollection;
import com.git.gdsbuilder.type.qa20.feature.QA20Feature;
import com.git.gdsbuilder.type.qa20.feature.QA20FeatureList;
import com.git.gdsbuilder.type.qa20.header.NDAHeader;
import com.git.gdsbuilder.type.qa20.header.NGIField;
import com.git.gdsbuilder.type.qa20.header.NGIHeader;
import com.git.gdsbuilder.type.qa20.layer.QA20Layer;
import com.git.gdsbuilder.type.qa20.layer.QA20LayerList;

/**
 * QA20DBQuery 생성 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 4:00:14
 */
public class QA20DBQueryManager {

	QA20LayerCollection dtCollection;
	GeoLayerInfo layerInfo;

	/**
	 * QA20DBQueryManager 생성자
	 */
	public QA20DBQueryManager() {

	}

	/**
	 * QA20DBQueryManager 생성자
	 * 
	 * @param dtCollection
	 */
	public QA20DBQueryManager(QA20LayerCollection dtCollection, GeoLayerInfo layerInfo) {
		this.dtCollection = dtCollection;
		this.layerInfo = layerInfo;
	}

	public QA20LayerCollection getDtCollection() {
		return dtCollection;
	}

	public void setDtCollection(QA20LayerCollection dtCollection) {
		this.dtCollection = dtCollection;
	}

	/**
	 * qa20_layerCollection tb insert Query 생성 @author DY.Oh @Date 2017. 4. 18.
	 * 오후 4:00:49 @return Map<String,Object> @throws
	 */
	public Map<String, Object> qa20LayerCollection() {

		String fileName = dtCollection.getFileName();
		Map<String, Object> layerCollection = new HashMap<String, Object>();
		layerCollection.put("file_name", fileName);
		return layerCollection;
	}

	/**
	 * qa20_layer_metadata tb insert Query 생성 @author DY.Oh @Date 2017. 4. 18.
	 * 오후 4:01:05 @return List<HashMap<String,Object>> @throws
	 */
	public List<HashMap<String, Object>> qa20LayerMetadata() {

		QA20LayerList dtLayers = dtCollection.getQa20LayerList();

		// hashtable 1개가 1 tuple
		List<HashMap<String, Object>> dblayers = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i < dtLayers.size(); i++) {

			HashMap<String, Object> dbLayer = new HashMap<String, Object>();
			QA20Layer layer = dtLayers.get(i);

			String layerID = layer.getLayerID(); // layer_id
			String layerName = layer.getLayerName(); // layer_name

			NGIHeader ngiHeader = layer.getNgiHeader();

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
			String fileType = layerInfo.getFileType();
			String fileName = dtCollection.getFileName();
			String tableName = "\"geo" + "_" + fileType + "_" + fileName + "_" + layer.getLayerName() + "\"";

			dbLayer.put("layerID", layerID);
			dbLayer.put("layerName", layerName);
			dbLayer.put("tableName", tableName);
			dbLayer.put("version", version);
			dbLayer.put("ngiDim", ngiDim);
			dbLayer.put("ngiBound", ngiBound);
			dbLayer.put("ptRepresent", ptRepresent);
			dbLayer.put("lnRepresent", lnRepresent);
			dbLayer.put("rgRepresent", rgRepresent);
			dbLayer.put("txRepresent", txRepresent);
			dblayers.add(dbLayer);
		}
		return dblayers;
	}

	/**
	 * qa20_layer tb create Query 생성 @author DY.Oh @Date 2017. 4. 18. 오후
	 * 4:01:55 @param layerID @return HashMap<String,Object> @throws
	 */
	public HashMap<String, Object> qa20LayerTbCreateQuery(String layerID) {

		String fileName = dtCollection.getFileName();
		QA20LayerList dtLayers = dtCollection.getQa20LayerList();
		QA20Layer layer = dtLayers.getQA20Layer(layerID);
		String layerTypeStr = layer.getLayerType();
		boolean isTextLayer = false;
		if (layerTypeStr.equals("TEXT")) {
			layerTypeStr = "POINT";
			isTextLayer = true;
		}
		String fileType = layerInfo.getFileType();
		String tableName = "\"geo" + "_" + fileType + "_" + fileName + "_" + layer.getLayerName() + "\"";
		String defalutCreateQuery = "create table " + tableName + " (" + "f_idx serial primary key" + ","
				+ "feature_id varchar(100)" + "," + "feature_type varchar(100)" + "," + "geom geometry(" + layerTypeStr
				+ ", 5186)" + "," + "num_rings numeric" + "," + "num_vertexes numeric" + ",";

		if (isTextLayer) {
			defalutCreateQuery += "TEXT varchar(100),";
		}

		NDAHeader ndaHeader = layer.getNdaHeader();
		if (ndaHeader != null) {
			List<NGIField> fields = ndaHeader.getAspatial_field_def();
			for (NGIField field : fields) {
				String key = "\"" + field.getFieldName() + "\"";
				String typeStr = field.getType();
				String type = "";
				if (typeStr.equals("string") || typeStr.equals("STRING") || typeStr.equals("String")) {
					type = "varchar(100)";
				} else {
					type = typeStr;
				}
				defalutCreateQuery += key + " " + type + ", ";
			}
		}

		int lastIndext = defalutCreateQuery.lastIndexOf(",");
		String returnQuery = defalutCreateQuery.substring(0, lastIndext) + ")";
		HashMap<String, Object> query = new HashMap<String, Object>();
		query.put("createQuery", returnQuery);
		return query;
	}

	/**
	 * qa20_layer tb insert Query 생성 @author DY.Oh @Date 2017. 4. 18. 오후
	 * 4:02:16 @param layerID @return List<HashMap<String,Object>> @throws
	 */
	public List<HashMap<String, Object>> qa20LayerTbInsertQuery(String layerID) {

		String fileName = dtCollection.getFileName();
		QA20LayerList dtLayers = dtCollection.getQa20LayerList();
		QA20Layer layer = dtLayers.getQA20Layer(layerID);

		String fileType = layerInfo.getFileType();
		String tableName = "\"geo" + "_" + fileType + "_" + fileName + "_" + layer.getLayerName() + "\"";

		List<HashMap<String, Object>> dbLayers = new ArrayList<HashMap<String, Object>>();
		QA20FeatureList features = layer.getFeatures();
		for (int i = 0; i < features.size(); i++) {
			QA20Feature feature = features.get(i);

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
					+ "(feature_id, feature_type, geom, num_rings, num_vertexes, ";
			String insertDefaultValues = " values('" + feature.getFeatureID() + "'," + "'" + featureType + "',"
					+ "ST_GeomFromText('" + feature.getGeom().toString() + "', 5186)" + "," + numparts + ","
					+ numVertexts + ",";

			// properties
			HashMap<String, Object> properties = feature.getProperties();
			int propertiesSize = properties.size();
			if (propertiesSize != 0) {
				Iterator keys = properties.keySet().iterator();
				while (keys.hasNext()) {
					String tmpKey = (String) keys.next();
					String key = "\"" + tmpKey + "\"";
					Object value = properties.get(key);
					insertDefaultQuery += key + ", ";
					if (value instanceof String && value != null) {
						insertDefaultValues += "'" + value + "', ";
					} else {
						insertDefaultValues += value + ",";
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

	/**
	 * point_represent tb insert Query 생성 @author DY.Oh @Date 2017. 4. 18. 오후
	 * 4:02:30 @param layerID @return List<HashMap<String,Object>> @throws
	 */
	public List<HashMap<String, Object>> ptRepresent(String layerID) {

		QA20LayerList dtLayers = dtCollection.getQa20LayerList();
		QA20Layer layer = dtLayers.getQA20Layer(layerID);

		List<HashMap<String, Object>> dbTextReps = new ArrayList<HashMap<String, Object>>();
		if (layer.getLayerType().equalsIgnoreCase("POINT")) {
			NGIHeader ngiHeader = layer.getNgiHeader();
			List<String> textRepresents = ngiHeader.getPoint_represent();
			for (int i = 0; i < textRepresents.size(); i++) {
				HashMap<String, Object> dbTextRep = new HashMap<String, Object>();
				String textReStr = textRepresents.get(i);

				int index = textReStr.indexOf(" ");
				String no = textReStr.substring(0, index);
				String value = textReStr.substring(index + 1);

				dbTextRep.put("pRepNo", Integer.parseInt(no));
				dbTextRep.put("pRepValue", value);
				dbTextReps.add(dbTextRep);
			}
		}
		return dbTextReps;
	}

	/**
	 * lineString_represent tb insert Query 생성 @author DY.Oh @Date 2017. 4. 18.
	 * 오후 4:02:41 @param layerID @return List<HashMap<String,Object>> @throws
	 */
	public List<HashMap<String, Object>> lnRepresent(String layerID) {

		QA20LayerList dtLayers = dtCollection.getQa20LayerList();
		QA20Layer layer = dtLayers.getQA20Layer(layerID);

		List<HashMap<String, Object>> dbTextReps = new ArrayList<HashMap<String, Object>>();
		if (layer.getLayerType().equalsIgnoreCase("LINESTRING")) {
			NGIHeader ngiHeader = layer.getNgiHeader();
			List<String> textRepresents = ngiHeader.getLine_represent();
			for (int i = 0; i < textRepresents.size(); i++) {
				HashMap<String, Object> dbTextRep = new HashMap<String, Object>();
				String textReStr = textRepresents.get(i);

				int index = textReStr.indexOf(" ");
				String no = textReStr.substring(0, index);
				String value = textReStr.substring(index + 1);

				dbTextRep.put("lRepNo", Integer.parseInt(no));
				dbTextRep.put("lRepValue", value);
				dbTextReps.add(dbTextRep);
			}
		}
		return dbTextReps;
	}

	/**
	 * region_represent tb insert Query 생성 @author DY.Oh @Date 2017. 4. 18. 오후
	 * 4:02:50 @param layerID @return List<HashMap<String,Object>> @throws
	 */
	public List<HashMap<String, Object>> rgRepresent(String layerID) {

		QA20LayerList dtLayers = dtCollection.getQa20LayerList();
		QA20Layer layer = dtLayers.getQA20Layer(layerID);

		List<HashMap<String, Object>> dbTextReps = new ArrayList<HashMap<String, Object>>();
		if (layer.getLayerType().equalsIgnoreCase("POLYGON")) {
			NGIHeader ngiHeader = layer.getNgiHeader();
			List<String> textRepresents = ngiHeader.getRegion_represent();
			for (int i = 0; i < textRepresents.size(); i++) {
				HashMap<String, Object> dbTextRep = new HashMap<String, Object>();
				String textReStr = textRepresents.get(i);

				int index = textReStr.indexOf(" ");
				String no = textReStr.substring(0, index);
				String value = textReStr.substring(index + 1);

				dbTextRep.put("rRepNo", Integer.parseInt(no));
				dbTextRep.put("rRepValue", value);
				dbTextReps.add(dbTextRep);
			}
		}
		return dbTextReps;
	}

	/**
	 * text_represent tb insert Query 생성 @author DY.Oh @Date 2017. 4. 18. 오후
	 * 4:03:06 @param layerID @return List<HashMap<String,Object>> @throws
	 */
	public List<HashMap<String, Object>> txtRepresent(String layerID) {

		QA20LayerList dtLayers = dtCollection.getQa20LayerList();
		QA20Layer layer = dtLayers.getQA20Layer(layerID);
		List<HashMap<String, Object>> dbTextReps = new ArrayList<HashMap<String, Object>>();
		if (layer.getLayerType().equalsIgnoreCase("TEXT")) {
			NGIHeader ngiHeader = layer.getNgiHeader();
			List<String> textRepresents = ngiHeader.getText_represent();
			for (int i = 0; i < textRepresents.size(); i++) {
				HashMap<String, Object> dbTextRep = new HashMap<String, Object>();
				String textReStr = textRepresents.get(i);

				int index = textReStr.indexOf(" ");
				String no = textReStr.substring(0, index);
				String value = textReStr.substring(index + 1);

				dbTextRep.put("tRepNo", Integer.parseInt(no));
				dbTextRep.put("tRepValue", value);
				dbTextReps.add(dbTextRep);
			}
		}
		return dbTextReps;
	}

	/**
	 * aspatial_field_def tb insert Query 생성 @author DY.Oh @Date 2017. 4. 18. 오후
	 * 4:05:57 @param layerID @return List<HashMap<String,Object>> @throws
	 */
	public List<HashMap<String, Object>> aspatialFieldDefs(String layerID) {

		QA20LayerList dtLayers = dtCollection.getQa20LayerList();
		List<HashMap<String, Object>> fieldDefs = new ArrayList<HashMap<String, Object>>();

		// hashtable 1개가 1 tuple
		QA20Layer layer = dtLayers.getQA20Layer(layerID);
		NDAHeader ndaHeader = layer.getNdaHeader();

		if (ndaHeader == null) {
			return null;
		} else {
			List<NGIField> fields = ndaHeader.getAspatial_field_def();
			for (int i = 0; i < fields.size(); i++) {
				HashMap<String, Object> field = new HashMap<String, Object>();
				NGIField dtField = fields.get(i);
				field.put("name", dtField.getFieldName());
				field.put("type", dtField.getType());
				field.put("size", Integer.parseInt(dtField.getSize()));
				field.put("decimal", dtField.getDecimal());
				field.put("isunique", dtField.isUnique());
				fieldDefs.add(field);
			}
			return fieldDefs;
		}
	}

	/**
	 * GeoLayerInfo 정보 반환 @author DY.Oh @Date 2017. 4. 18. 오후 4:05:29 @param
	 * layerID @return String @throws
	 */
	public String getLayerType(String layerID) {

		QA20LayerList dtLayers = dtCollection.getQa20LayerList();
		QA20Layer layer = dtLayers.getQA20Layer(layerID);
		String layerType = layer.getLayerType();
		if (layerType.equals("POINT")) {
			layerType = "Point";
		} else if (layerType.equals("LINESTRING")) {
			layerType = "LineString";
		} else if (layerType.equals("POLYGON")) {
			layerType = "Polygon";
		}
		return layerType;
	}

	/**
	 * qa20_layer tb의 컬럼 명 리스트 반환 @author DY.Oh @Date 2017. 4. 18. 오후
	 * 4:04:49 @param layerID @return List<String> @throws
	 */
	public List<String> getLayerCoulmns(String layerID) {

		List<String> columns = new ArrayList<String>();
		QA20LayerList dtLayers = dtCollection.getQa20LayerList();
		QA20Layer layer = dtLayers.getQA20Layer(layerID);
		NDAHeader header = layer.getNdaHeader();
		if (header == null) {
			return null;
		}
		List<NGIField> fields = header.getAspatial_field_def();
		for (int i = 0; i < fields.size(); i++) {
			NGIField dtField = fields.get(i);
			columns.add(dtField.getFieldName());
		}
		return columns;
	}

	/**
	 * qa20_layer tb의 모든 Feature 수 반환 @author DY.Oh @Date 2017. 4. 18. 오후
	 * 4:04:05 @param layerCollectionName @param layerIDList @return
	 * HashMap<String,Object> @throws
	 */
	public HashMap<String, Object> selectCountAllFeaturesQuery(String layerCollectionName, List<String> layerIDList) {

		String countQueryStr = "select ";
		String fileType = layerInfo.getFileType();
		for (int i = 0; i < layerIDList.size(); i++) {
			if (i > 0) {
				countQueryStr += " + ";
			}
			String layerID = layerIDList.get(i);
			String tableName = "\"geo" + "_" + fileType + "_" + layerCollectionName + "_" + layerID + "\"";
			countQueryStr += "(select count (*) from " + tableName + ")";
		}
		countQueryStr += " as all_feature_count";
		HashMap<String, Object> countQueryMap = new HashMap<String, Object>();
		countQueryMap.put("countQuery", countQueryStr);
		return countQueryMap;
	}

	public HashMap<String, Object> qa20LayerTbBoundaryQuery(String layerID) {

		String fileType = layerInfo.getFileType();
		String fileName = dtCollection.getFileName();
		QA20LayerList dtLayers = dtCollection.getQa20LayerList();
		QA20Layer layer = dtLayers.getQA20Layer(layerID);
		String tableName = "\"geo" + "_" + fileType + "_" + fileName + "_" + layer.getLayerName() + "\"";

		String selectBDQueryStr = "select ST_Extent(geom) from " + tableName;
		HashMap<String, Object> selectBDQuery = new HashMap<String, Object>();
		selectBDQuery.put("selectBDQuery", selectBDQueryStr);
		return selectBDQuery;
	}
}
