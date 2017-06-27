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

package com.git.opengds.editor.service;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditServiceImpl implements EditService {

	protected static final String none = "none";
	protected static final String isModified = "modify";
	protected static final String isCreated = "create";
	protected static final String isDeleted = "remove";

	protected static final String isNgi = "ngi";
	protected static final String isDxf = "dxf";
	protected static final String isShp = "shp";

	@Autowired
	EditLayerService editLayerService;

	@Autowired
	EditFeatureService editFeatureService;

	@Override
	public void editLayerCollection(String editJSONStr) throws Exception {

		// 옵션 넘겨 받음
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(new FileReader("D:\\editDXFTest.txt"));
		JSONObject editJSONObject = (JSONObject) obj;

		// JSONParser jsonParser = new JSONParser();
		// JSONObject editJSONObject = (JSONObject)
		// jsonParser.parse(editJSONStr);

		// layerEdit
		JSONObject layerEditObj = (JSONObject) editJSONObject.get("layer");
		editLayerService.editLayer(layerEditObj);

		// featureEdit
		JSONObject featureEditObj = (JSONObject) editJSONObject.get("feature");
		editFeatureService.editFeature(featureEditObj);
	}

}
