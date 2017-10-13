package com.git.opengds.generalization.dbManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.feature.type.AttributeType;

import com.vividsolutions.jts.geom.Geometry;

public class GenLayerDBQueryManager {

	public HashMap<String, Object> getCreateGenLayerTbQuery(String genTableName, SimpleFeatureCollection afterGeoSfc,
			String layerTypeStr, String src) {

		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		if (afterGeoSfc != null) {
			String defalutCreateQuery = "create table " + genTableName
					+ "(gen_idx serial primary key, collection_name varchar(100), layer_name varchar(100), ";
			List<String> attriKeyList = new ArrayList<>();
			SimpleFeatureType sft = afterGeoSfc.getSchema();
			List<AttributeDescriptor> attriDescriptros = sft.getAttributeDescriptors();
			for (int j = 0; j < attriDescriptros.size(); j++) {
				AttributeDescriptor attriDescriptor = attriDescriptros.get(j);
				String attriName = attriDescriptor.getName().toString();
				if (attriName.equals("geom")) {
					defalutCreateQuery += "geom geometry(" + layerTypeStr + ", " + src + ")" + ", ";
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
				}
				attriKeyList.add(attriName);
			}
			returnMap.put("attriKeyList", attriKeyList);
			int lastIndext = defalutCreateQuery.lastIndexOf(",");
			String returnQuery = defalutCreateQuery.substring(0, lastIndext) + ")";
			HashMap<String, Object> createQuery = new HashMap<String, Object>();
			createQuery.put("createQuery", returnQuery);
			returnMap.put("createQueryMap", createQuery);

			return returnMap;
		} else {
			return null;
		}
	}

	public HashMap<String, Object> getInsertGenLayerFeaturesQuery(String genTableName, String collectionName,
			String layerName, List<String> attriKeyList, SimpleFeature simeFeature, String src) {

		Geometry geom = (Geometry) simeFeature.getDefaultGeometry();
		String insertColumns = "insert into "  + genTableName + "(collection_name, layer_name, ";
		String insertValues = "values ('" + collectionName + "', " + "'" + layerName + "', ";

		for (int i = 0; i < attriKeyList.size(); i++) {
			String attriKey = attriKeyList.get(i);
			Object attriValue = simeFeature.getAttribute(attriKey);
			if (attriKey.equals("geom")) {
				insertColumns += attriKey + ", ";
				insertValues += "ST_GeomFromText('" + geom.toString() + "', " + src + "), ";
			} else {
				insertColumns += attriKey + ", ";
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
}
