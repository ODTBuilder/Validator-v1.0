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
package com.git.gdsbuilder.FileRead.shp;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.data.DataStoreFinder;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.GeometryType;
import org.opengis.filter.Filter;

import com.git.gdsbuilder.type.shp.collection.DTSHPLayerCollection;
import com.git.gdsbuilder.type.shp.layer.DTSHPLayer;
import com.git.gdsbuilder.type.shp.layer.DTSHPLayerList;

/**
 * SHP 파일을 읽어 DTSHPLayerCollection 객체로 변환하는 기능을 제공
 * 
 * @author DY.Oh
 *
 */
public class SHPFileReader {

	/**
	 * zip 파일 압축 해제 경로의 shp 파일을 읽어 DTSHPLayerCollection 객체로 변환하여 반환
	 * 
	 * @param upZipFilePath
	 *            zip 파일 압축 해제 경로
	 * @param entryName
	 *            zip 파일 내 폴더명
	 * @param fileNameMap
	 *            shp, shx, dbf 파일명
	 * @return DTSHPLayerCollection
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public DTSHPLayerCollection read(String upZipFilePath, String entryName, Map<String, Object> fileNameMap)
			throws Exception {
		List<String> shpFileNames = (List<String>) fileNameMap.get("shp");
		List<String> shxFileNames = (List<String>) fileNameMap.get("shx");
		List<String> dbfFileNames = (List<String>) fileNameMap.get("dbf");

		DTSHPLayerList dtLayerList = new DTSHPLayerList();
		for (int i = 0; i < shpFileNames.size(); i++) {
			String shpFile = shpFileNames.get(i);
			int comma = shpFile.lastIndexOf(".");
			String shpName = shpFile.substring(0, comma);
			boolean validShx = isValidFile(shpName, shxFileNames);
			boolean validDbf = isValidFile(shpName, dbfFileNames);
			if (validShx && validDbf) {
				File file = new File(upZipFilePath, shpFile);
				SimpleFeatureCollection collection = getShpObject(file);
				if (collection != null) {
					DTSHPLayer dtLayer = new DTSHPLayer();
					dtLayer.setSimpleFeatureCollection(collection);
					SimpleFeatureType featureType = collection.getSchema();
					GeometryType geometryType = featureType.getGeometryDescriptor().getType();
					String geomType = geometryType.getName().toString();
					// if (geomType.startsWith("Multi")) {
					// DefaultFeatureCollection newCollection = new
					// DefaultFeatureCollection();
					// SimpleFeatureIterator it = collection.features();
					// while (it.hasNext()) {
					// SimpleFeature sf = it.next();
					// Geometry geom = (Geometry) sf.getDefaultGeometry();
					// int geomNum = geom.getNumGeometries();
					// if (geomNum == 1) {
					// String featureID = sf.getID();
					// Geometry singleGeom = geom.getGeometryN(0);
					// int size = sf.getAttributeCount();
					// Object[] propertyObj = new Object[size];
					// propertyObj[0] = singleGeom;
					// String temp = "";
					// List<Property> properties = (List) sf.getProperties();
					// for (int j = 0; j < properties.size(); j++) {
					// Property property = properties.get(j);
					// String propertyName = property.getName().toString();
					// if (propertyName.equals("the_geom")) {
					// continue;
					// } else {
					// Object value = property.getValue();
					// String valueType =
					// property.getValue().getClass().getSimpleName();
					// if (valueType.equals("Long")) {
					// valueType = "String";
					// propertyObj[j] = value.toString();
					// temp += propertyName + ":" + valueType + ",";
					// } else {
					// propertyObj[j] = value;
					// temp += propertyName + ":" + valueType + ",";
					// }
					// }
					// }
					// SimpleFeatureType simpleFeatureType =
					// DataUtilities.createType(featureID.toString(),
					// "the_geom:" + singleGeom.getGeometryType() + "," +
					// temp.substring(0, temp.length() - 1));
					// SimpleFeature newSimpleFeature =
					// SimpleFeatureBuilder.build(simpleFeatureType,
					// propertyObj, featureID);
					// newCollection.add(newSimpleFeature);
					//
					// geomType = geomType.replaceAll("Multi", "");
					// newCollection.add(sf);
					// }
					// }
					// dtLayer.setLayerType(geomType.toUpperCase());
					// dtLayer.setSimpleFeatureCollection(newCollection);
					// dtLayer.setLayerName(shpName.toUpperCase());
					// dtLayerList.add(dtLayer);
					// } else {
					dtLayer.setLayerType(geomType.toUpperCase());
					dtLayer.setLayerName(shpName.toUpperCase() + "_" + geomType.toUpperCase());
					dtLayerList.add(dtLayer);
					// }
				}
			}
		}
		DTSHPLayerCollection dtCollectoin = new DTSHPLayerCollection();
		dtCollectoin.setShpLayerList(dtLayerList);
		dtCollectoin.setCollectionName(entryName);

		return dtCollectoin;
	}

	private boolean isValidFile(String fileName, List<String> otherFileNames) {

		boolean isValid = false;

		for (int i = 0; i < otherFileNames.size(); i++) {
			String otherFileName = otherFileNames.get(i);
			int otherComma = otherFileName.lastIndexOf(".");
			String otherName = otherFileName.substring(0, otherComma);
			if (fileName.toUpperCase().equals(otherName.toUpperCase())) {
				isValid = true;
			}
		}
		return isValid;
	}

	/**
	 * shp 파일 객체를 SimpleFeatureCollection으로 변환하여 반환
	 * 
	 * @param shpFile
	 *            shp 파일 객체
	 * @return SimpleFeatureCollection
	 */
	public SimpleFeatureCollection getShpObject(File shpFile) {

		Map<String, Object> map = new HashMap<String, Object>();
		ShapefileDataStore dataStore;

		String typeName;
		SimpleFeatureCollection collection = null;

		try {
			map.put("url", shpFile.toURI().toURL());
			dataStore = (ShapefileDataStore) DataStoreFinder.getDataStore(map);

			typeName = dataStore.getTypeNames()[0];
			Charset UTF_8 = Charset.forName("EUC-KR");
			dataStore.setCharset(UTF_8);
			SimpleFeatureSource source = dataStore.getFeatureSource(typeName);
			Filter filter = Filter.INCLUDE;
			collection = source.getFeatures(filter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return collection;
	}

}
