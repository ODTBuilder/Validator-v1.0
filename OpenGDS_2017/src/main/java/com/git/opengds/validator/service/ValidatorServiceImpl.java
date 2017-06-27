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

package com.git.opengds.validator.service;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollectionList;
import com.git.gdsbuilder.type.validate.collection.ValidateLayerCollectionList;
import com.git.gdsbuilder.type.validate.error.ErrorLayerList;
import com.git.gdsbuilder.type.validate.layer.ValidateLayerTypeList;
import com.git.gdsbuilder.validator.collection.CollectionValidator;
import com.git.opengds.parser.json.BuilderJSONQA20Parser;

@Service
public class ValidatorServiceImpl implements ValidatorService {

	@Autowired
	private ErrorLayerService errorLayerService;

	// @Autowired
	// private ErrorLayerExportService errorLayerExportService;

	@Autowired
	private ValidatorProgressService progressService;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject validate(String jsonObject) throws Exception {

		JSONParser parser = new JSONParser();
		JSONObject jsonObj = (JSONObject) parser.parse(jsonObject);

		JSONObject layerCollections = (JSONObject) jsonObj.get("layerCollections");
		JSONArray layerCollectionNames = (JSONArray) layerCollections.get("collectionName");
		String fileType = (String) layerCollections.get("fileType");

		// progress : 0 -> 검수 요청
		for (int i = 0; i < layerCollectionNames.size(); i++) {
			String collectionName = (String) layerCollectionNames.get(i);
			String timeFormat = "YYYY. MM. DD HH:mm:ss";
			progressService.setStateToRequest(collectionName, fileType,
					new SimpleDateFormat(timeFormat).format(System.currentTimeMillis()));
		}

		try {
			// progress : 1 -> 검수 수행 중
			for (int i = 0; i < layerCollectionNames.size(); i++) {
				String collectionName = (String) layerCollectionNames.get(i);
				progressService.setStateToProgressing(collectionName, fileType);
			}

			// 파라미터 파싱
			BuilderJSONQA20Parser parserManager = new BuilderJSONQA20Parser();
			HashMap<String, Object> valdateObj = parserManager.parseValidateObj(jsonObj);
			ValidateLayerTypeList validateLayerTypeList = (ValidateLayerTypeList) valdateObj.get("typeValidate");
			GeoLayerCollectionList collectionList = (GeoLayerCollectionList) valdateObj.get("collectionList");

			// 검수수행
			ValidateLayerCollectionList validateLayerCollection = new ValidateLayerCollectionList(collectionList,
					validateLayerTypeList);
			CollectionValidator validator = new CollectionValidator(validateLayerCollection);
			ErrorLayerList errorLayerList = validator.getErrLayerList();

			// 오류레이어 발행
			boolean isSuccessPublish = publishErrorLayer(errorLayerList);
			boolean isErrorLayer = false;
			if (errorLayerList.size() > 0) {
				isErrorLayer = true;
			}
			JSONObject returnJSON = new JSONObject();
			returnJSON.put("ErrorLayer", isErrorLayer);
			returnJSON.put("Publising ErrorLayer", isSuccessPublish);
			System.out.println("완료");
			return returnJSON;
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject returnJSON = new JSONObject();
			returnJSON.put("ErrorLayer", false);
			returnJSON.put("Publising ErrorLayer", false);
			System.out.println("완료");
			return returnJSON;
		}

	}

	public boolean publishErrorLayer(ErrorLayerList errorLayerList)
			throws IllegalArgumentException, MalformedURLException {
		boolean isSuccess = false;
		if (errorLayerList != null) {
			isSuccess = errorLayerService.publishErrorLayer(errorLayerList);
		}
		return isSuccess;
	}
}
