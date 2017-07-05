package com.git.opengds.parser.json;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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

public class BuilderJSONParser {

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
	public static HashMap<String, Object> parseValidateObj(JSONObject jsonObj)
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

		if (fileType.equals("ngi")) {
			enFileFormat = EnFileFormat.NGI;
		} else if (fileType.equals("dxf")) {
			enFileFormat = EnFileFormat.DXF;
		} else if (fileType.equals("shp")) {
			enFileFormat = EnFileFormat.SHP;
		} else
			throw new IllegalArgumentException("올바르지 않은 파일 타입");

		String getCapabilities = URL + "/wfs?REQUEST=GetCapabilities&version=1.0.0";

		GeoLayerCollectionParser collectionParser = new GeoLayerCollectionParser(layerCollections, ID, getCapabilities,
				enFileFormat, validateLayerList);
		GeoLayerCollectionList collectionList = collectionParser.getLayerCollections();
		if (collectionList.size() == 0 && validateLayerTypeList.size() == 0) {
			return null;
		} else {
			validateMap.put("typeValidate", validateLayerTypeList);
			validateMap.put("collectionList", collectionList);
			return validateMap;
		}
	}
}
