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

		type = "shp";
		String layerName = shpLayer.getLayerName();
		String layerTypeStr = shpLayer.getLayerType();

		String tableName = "geo" + "_" + type + "_" + collectionName + "_" + layerName;
		String defalutCreateQuery = "create table " + tableName + "(f_idx serial primary key" + ","
				+ "feature_id varchar(100)" + "," + "feature_type varchar(100)" + "," + "geom geometry(" + layerTypeStr
				+ ", " + src + ")" + ", ";

		List<String> attriKeyList = new ArrayList<>();
		SimpleFeatureCollection featureCollection = shpLayer.getSimpleFeatureCollection();
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
				} else if (typeName.equals("int")) {
					defalutCreateQuery += " int, ";
				} else if (typeName.equals("Double")) {
					defalutCreateQuery += " double precision, ";
				} else if (typeName.equals("Long")) {
					defalutCreateQuery += " int, ";
				}
				attriKeyList.add(attriName);
			}
		}
		int lastIndext = defalutCreateQuery.lastIndexOf(",");
		String returnQuery = defalutCreateQuery.substring(0, lastIndext) + ")";
		HashMap<String, Object> createQuery = new HashMap<String, Object>();
		createQuery.put("createQuery", returnQuery);

		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("createQueryMap", createQuery);
		returnMap.put("attriKeyList", attriKeyList);
		return returnMap;
	}

	public List<HashMap<String, Object>> getSHPLayerInsertQuery(String type, String collectionName,
			List<String> attriKeyList, DTSHPLayer shpLayer, String src) {

		type = "shp";
		String layerName = shpLayer.getLayerName();

		String tableName = "geo" + "_" + type + "_" + collectionName + "_" + layerName;
		List<HashMap<String, Object>> insertQuerys = new ArrayList<HashMap<String, Object>>();
		SimpleFeatureCollection featureCollection = shpLayer.getSimpleFeatureCollection();
		SimpleFeatureIterator iterator = featureCollection.features();
		while (iterator.hasNext()) {
			SimpleFeature feature = iterator.next();
			Geometry geom = (Geometry) feature.getDefaultGeometry();
			String insertColumns = "insert into " + tableName + "(feature_id,  feature_type, geom, ";
			String insertValues = "values('" + feature.getID() + "', '" + geom.getGeometryType() + "', "
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

		type = "shp";
		String layerName = shpLayer.getLayerName();
		String tableName = "geo" + "_" + type + "_" + collectionName + "_" + layerName;

		String insertQueryColumn = "insert into shp_layer_metadata(layer_name, layer_t_name, c_idx)";
		String insertQueryValues = "values('" + layerName + "', " + "'" + tableName + "', " + cIdx + ")";

		HashMap<String, Object> insertQueryMap = new HashMap<String, Object>();
		insertQueryMap.put("insertQuery", insertQueryColumn + insertQueryValues);

		return insertQueryMap;
	}
}
