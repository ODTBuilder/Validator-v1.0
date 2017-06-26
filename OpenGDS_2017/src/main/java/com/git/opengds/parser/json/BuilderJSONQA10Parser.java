package com.git.opengds.parser.json;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONObject;

import com.git.gdsbuilder.edit.qa10.EditQA10LayerCollectionList;
import com.git.opengds.parser.edit.layer.EditLayerCollectionListParser;

public class BuilderJSONQA10Parser {
	
	public static Map<String, Object> parseEditLayerObj(JSONObject editLayerObj)
			throws FileNotFoundException, IOException, com.vividsolutions.jts.io.ParseException, SchemaException {

		// layer 편집
		Map<String, Object> editLayerListMap = new HashMap<String, Object>();
		Iterator layerIterator = editLayerObj.keySet().iterator();
		while (layerIterator.hasNext()) {
			String type = (String) layerIterator.next();
			JSONObject collectionListObj = (JSONObject) editLayerObj.get(type);
			EditLayerCollectionListParser editLayerCollectionListParser = new EditLayerCollectionListParser(type,
					collectionListObj);
			EditQA10LayerCollectionList edtCollectionList = editLayerCollectionListParser.getEdtQA10CollectionList();
			editLayerListMap.put(type, edtCollectionList);
		}
		return editLayerListMap;
	}
}
