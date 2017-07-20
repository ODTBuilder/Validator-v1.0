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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollectionList;
import com.git.gdsbuilder.type.validate.collection.ValidateLayerCollectionList;
import com.git.gdsbuilder.type.validate.error.ErrorLayer;
import com.git.gdsbuilder.type.validate.error.ErrorLayerList;
import com.git.gdsbuilder.type.validate.layer.ValidateLayerTypeList;
import com.git.gdsbuilder.validator.collection.CollectionValidator;
import com.git.opengds.parser.json.BuilderJSONParser;
import com.git.opengds.user.domain.UserVO;

@Service
public class ValidatorServiceImpl implements ValidatorService {

	protected static int requestSuccess = 0;
	protected static int validateProgresing = 1;
	protected static int validateSuccess = 2;
	protected static int validateFail = 3;
	protected static int errLayerSuccess = 4;
	protected static int errLayerFail = 5;

	@Autowired
	private ErrorLayerService errorLayerService;

	// @Autowired
	// private ErrorLayerExportService errorLayerExportService;

	@Autowired
	private ValidatorProgressService progressService;

	/*public ValidatorServiceImpl(UserVO generalUser) {
		// TODO Auto-generated constructor stub
		errorLayerService = new ErrorLayerServiceImpl(generalUser);
	}*/

	@SuppressWarnings("unchecked")
	@Async
	@Override
	public JSONObject validate(UserVO userVO, String jsonObject) throws Exception {

		JSONParser parser = new JSONParser();
		JSONObject jsonObj = (JSONObject) parser.parse(jsonObject);

		JSONObject layerCollections = (JSONObject) jsonObj.get("layerCollections");
		JSONArray layerCollectionNames = (JSONArray) layerCollections.get("collectionName");
		String fileType = (String) layerCollections.get("fileType");

		Map<String, Object> progressMap = new HashMap<String, Object>();
		// progress : 0 -> 검수 요청
		for (int i = 0; i < layerCollectionNames.size(); i++) {
			String collectionName = (String) layerCollectionNames.get(i);
			Integer pIdx = progressService.setStateToRequest(userVO, requestSuccess, collectionName, fileType);
			progressMap.put(collectionName, pIdx);
		}
		try {
			// progress : 1 -> 검수 수행 중
			for (int i = 0; i < layerCollectionNames.size(); i++) {
				String collectionName = (String) layerCollectionNames.get(i);
				Integer pIdx = (Integer) progressMap.get(collectionName);
				progressService.setStateToProgressing(userVO, validateProgresing, fileType, pIdx);
			}
			// 파라미터 파싱
			BuilderJSONParser parserManager = new BuilderJSONParser();
			HashMap<String, Object> valdateObj = parserManager.parseValidateObj(jsonObj);
			ValidateLayerTypeList validateLayerTypeList = (ValidateLayerTypeList) valdateObj.get("typeValidate");
			GeoLayerCollectionList collectionList = (GeoLayerCollectionList) valdateObj.get("collectionList");

			// 검수수행
			ValidateLayerCollectionList validateLayerCollection = new ValidateLayerCollectionList(collectionList,
					validateLayerTypeList);
			CollectionValidator validator = new CollectionValidator(validateLayerCollection, fileType);

			// progress : 2 / 3 -> 2 : 검수 성공, 3 : 검수 실패
			Map<String, Object> validateProgressMap = validator.getProgress();
			Iterator prgressMapIt = validateProgressMap.keySet().iterator();
			while (prgressMapIt.hasNext()) {
				String collectionName = (String) prgressMapIt.next();
				Integer pIdx = (Integer) progressMap.get(collectionName);
				int state = (Integer) validateProgressMap.get(collectionName);
				if (state == validateSuccess) {
					progressService.setStateToValidateSuccess(userVO, validateSuccess, fileType, pIdx);
				} else if (state == validateFail) {
					progressService.setStateToValidateFail(userVO, validateFail, fileType, pIdx);
				}
			}

			// 오류레이어 발행
			boolean isAllSuccessPublish = true;
			ErrorLayerList errorLayerList = validator.getErrLayerList();
			for (int i = 0; i < errorLayerList.size(); i++) {
				ErrorLayer errLayer = errorLayerList.get(i);
				String collectionName = errLayer.getCollectionName();
				Integer pIdx = (Integer) progressMap.get(collectionName);
				String collectionType = errLayer.getCollectionType();
				Map<String, Object> isSuccessPublish = publishErrorLayer(userVO, errLayer);
				if (isSuccessPublish != null) {
					// progress : 4 오류레이어 발행 완료 / 검수 끝
					String tableName = (String) isSuccessPublish.get("errTbName");
					progressService.setStateToErrLayerSuccess(userVO, errLayerSuccess, collectionType, pIdx, tableName);
					progressService.setStateToResponse(userVO, collectionType, pIdx);
				} else {
					// progress : 5 오류레이어 발행 실패 / 검수 끝
					progressService.setStateToErrLayerFail(userVO, errLayerFail, collectionType, pIdx);
					progressService.setStateToResponse(userVO, collectionType, pIdx);
					isAllSuccessPublish = false;
				}
			}
			boolean isErrorLayer = false;
			if (errorLayerList.size() > 0) {
				isErrorLayer = true;
			}
			JSONObject returnJSON = new JSONObject();
			returnJSON.put("ErrorLayer", isErrorLayer);
			returnJSON.put("Publising ErrorLayer", isAllSuccessPublish);
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

	public Map<String, Object> publishErrorLayer(UserVO userVO, ErrorLayer errorLayer)
			throws IllegalArgumentException, MalformedURLException {
		if (errorLayer != null) {
			return errorLayerService.publishErrorLayer(userVO, errorLayer);
		}
		return null;
	}
}
