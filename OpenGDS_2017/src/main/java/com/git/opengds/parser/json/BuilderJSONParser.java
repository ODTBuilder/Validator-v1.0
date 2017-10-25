package com.git.opengds.parser.json;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.git.gdsbuilder.FileRead.en.EnFileFormat;
import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollectionList;
import com.git.gdsbuilder.type.geoserver.parser.GeoLayerCollectionParser;
import com.git.gdsbuilder.type.validate.layer.ValidateLayerTypeList;
import com.git.opengds.parser.validate.ValidateTypeParser;
import com.git.opengds.user.domain.UserVO;

public class BuilderJSONParser {

	protected static final String isShp = "shp";

	private static final String URL;
	private static final String ID;

	static {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			properties.load(classLoader.getResourceAsStream("geoserver.properties"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		URL = properties.getProperty("url");
		ID = properties.getProperty("id");
	}

	/**
	 * JSONObject를 ValidateLayerTypeList, LayerCollectionList로 파싱 @author
	 * DY.Oh @Date 2017. 4. 18. 오후 4:08:26 @param j @return
	 * HashMap<String,Object> @throws FileNotFoundException @throws
	 * IOException @throws ParseException @throws SchemaException @throws
	 */
	public static HashMap<String, Object> parseValidateObj(JSONObject jsonObj, UserVO userVO)
			throws FileNotFoundException, IOException, ParseException, SchemaException {

		HashMap<String, Object> validateMap = new HashMap<String, Object>();

		// 타입검수 파싱
		JSONArray typeValidates = (JSONArray) jsonObj.get("typeValidate");
		ValidateTypeParser validateTypeParser = new ValidateTypeParser(typeValidates);
		ValidateLayerTypeList validateLayerTypeList = validateTypeParser.getValidateLayerTypeList();
		List<String> validateLayerList = validateLayerTypeList.getLayerIDList();

		// 도엽들 파싱
		JSONObject layerCollections = (JSONObject) jsonObj.get("layerCollections");
		String fileType = (String) layerCollections.get("fileType");
		EnFileFormat enFileFormat;

		if (fileType.equals("shp")) {
			enFileFormat = EnFileFormat.SHP;
		} else
			throw new IllegalArgumentException("올바르지 않은 파일 타입");

		String getCapabilities = URL + "/wfs?REQUEST=GetCapabilities&version=1.0.0";

		GeoLayerCollectionParser collectionParser = new GeoLayerCollectionParser(layerCollections, userVO.getId(),
				getCapabilities, enFileFormat, validateLayerList);
		GeoLayerCollectionList collectionList = collectionParser.getLayerCollections();
		if (collectionList.size() == 0 && validateLayerTypeList.size() == 0) {
			return null;
		} else {
			validateMap.put("typeValidate", validateLayerTypeList);
			validateMap.put("collectionList", collectionList);
			return validateMap;
		}
	}

	// public static void parseEditLayerObj(JSONObject editLayerObj)
	// throws FileNotFoundException, IOException,
	// com.vividsolutions.jts.io.ParseException, SchemaException {
	//
	// Iterator layerIterator = editLayerObj.keySet().iterator();
	// while (layerIterator.hasNext()) {
	// String type = (String) layerIterator.next();
	// if (type.equals(isNgi)) {
	// EditQA20LayerCollectionList edtQA20CollectionListObj =
	// BuilderJSONQA20Parser
	// .parseEditLayerObj(editLayerObj, type);
	// } else if (type.equals(isDxf)) {
	// EditQA10LayerCollectionList edtCollectionList =
	// BuilderJSONQA10Parser.parseEditLayerObj(editLayerObj,
	// type);
	// }
	// }
	// }

	public static Map<String, Object> parseEditFeatureObj(JSONObject editFeatureObj)
			throws com.vividsolutions.jts.io.ParseException, ParseException, SchemaException {

		// feature 편집
		Map<String, Object> editFeatureListMap = new HashMap<String, Object>();
		Iterator featureIterator = editFeatureObj.keySet().iterator();
		while (featureIterator.hasNext()) {
			String tableName = (String) featureIterator.next();
			String collectionType = getCollectionType(tableName);
			String layerType = getLayerType(tableName);
			JSONObject stateObj = (JSONObject) editFeatureObj.get(tableName);
			Map<String, Object> editFeatureMap = new HashMap<String, Object>();
			if (collectionType.equals(isShp)) {
				editFeatureMap = BuilderJSONSHPParser.parseSHPFeature(stateObj, layerType);
			}
			editFeatureListMap.put(tableName, editFeatureMap);
		}
		return editFeatureListMap;
	}

	public static String getCollectionType(String layerName) {

		int firstIndex = layerName.indexOf("_");
		String tempStr = layerName.substring(firstIndex + 1);
		int lastIndex = tempStr.indexOf("_");
		String layerType = tempStr.substring(0, lastIndex);

		return layerType;
	}

	public static String getLayerType(String layerName) {

		int firstIndex = layerName.lastIndexOf("_");
		String layerType = layerName.substring(firstIndex + 1, layerName.length() - 1);

		return layerType;
	}
}
