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

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollection;
import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollectionList;
import com.git.gdsbuilder.type.validate.collection.ValidateLayerCollectionList;
import com.git.gdsbuilder.type.validate.error.ErrorLayer;
import com.git.gdsbuilder.type.validate.error.ErrorLayerList;
import com.git.gdsbuilder.type.validate.layer.ValidateLayerTypeList;
import com.git.gdsbuilder.validator.collection.CollectionValidator;
import com.git.gdsbuilder.validator.collection.rule.MapSystemRule;
import com.git.gdsbuilder.validator.collection.rule.MapSystemRule.MapSystemRuleType;
import com.git.opengds.parser.json.BuilderJSONParser;
import com.git.opengds.user.domain.UserVO;

@Service
public class ValidatorServiceImpl implements ValidatorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorServiceImpl.class);

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

	/*
	 * public ValidatorServiceImpl(UserVO generalUser) { // TODO Auto-generated
	 * constructor stub errorLayerService = new
	 * ErrorLayerServiceImpl(generalUser); }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public void validate(final UserVO userVO, String jsonObject) throws Exception {

		System.out.println(jsonObject);

		JSONParser parser = new JSONParser();
		JSONObject jsonObj = (JSONObject) parser.parse(jsonObject);

		JSONObject layerCollections = (JSONObject) jsonObj.get("layerCollections");
		JSONArray layerCollectionNames = (JSONArray) layerCollections.get("collectionName");
		final String fileType = (String) layerCollections.get("fileType");

		final Map<String, Object> progressMap = new HashMap<String, Object>();
		// progress : 0 -> 검수 요청
		for (int i = 0; i < layerCollectionNames.size(); i++) {
			String collectionName = (String) layerCollectionNames.get(i);
			Integer pIdx = progressService.setStateToRequest(userVO, requestSuccess, collectionName, fileType);
			progressMap.put(collectionName, pIdx);
		}
		// progress : 1 -> 검수 수행 중
		for (int i = 0; i < layerCollectionNames.size(); i++) {
			String collectionName = (String) layerCollectionNames.get(i);
			Integer pIdx = (Integer) progressMap.get(collectionName);
			progressService.setStateToProgressing(userVO, validateProgresing, fileType, pIdx);
		}
		// 파라미터 파싱
		BuilderJSONParser parserManager = new BuilderJSONParser();
		HashMap<String, Object> valdateObj = parserManager.parseValidateObj(jsonObj, userVO);
		ValidateLayerTypeList validateLayerTypeList = (ValidateLayerTypeList) valdateObj.get("typeValidate");
		GeoLayerCollectionList collectionList = (GeoLayerCollectionList) valdateObj.get("collectionList");

		// 검수수행
		ValidateLayerCollectionList validateLayerCollection = new ValidateLayerCollectionList(collectionList,
				validateLayerTypeList);
		GeoLayerCollectionList geoLayerCollectionList = validateLayerCollection.getLayerCollectionList();

		final ValidateLayerTypeList layerTypeList = validateLayerCollection.getValidateLayerTypeList();
		final MapSystemRule mapSystemRule = new MapSystemRule(-10, 10, -1, 1); // 도곽설정

		final List<GeoLayerCollection> collections = new ArrayList<GeoLayerCollection>();

		// 도엽별 검수 쓰레드 생성
		ExecutorService execService = Executors.newCachedThreadPool();

		for (final GeoLayerCollection collection : geoLayerCollectionList) {
			String collectionName = collection.getCollectionName();
			try {
				// 인접도엽 GET
				int collectionNum = Integer.parseInt(collectionName); // 대상도엽번호

				int topColltionNum = collectionNum + mapSystemRule.getMapSystemlRule(MapSystemRuleType.TOP);
				int bottomColltionNum = collectionNum + mapSystemRule.getMapSystemlRule(MapSystemRuleType.BOTTOM);
				int leftColltionNum = collectionNum + mapSystemRule.getMapSystemlRule(MapSystemRuleType.LEFT);
				int rightColltionNum = collectionNum + mapSystemRule.getMapSystemlRule(MapSystemRuleType.RIGHT);

				for (GeoLayerCollection nearCollection : collectionList) {
					try {
						String nearCollectionName = nearCollection.getCollectionName();
						int nearCollectionNum = Integer.parseInt(nearCollectionName);

						if (collectionName.endsWith("6")) {
							if (topColltionNum == nearCollectionNum) {
								collections.add(nearCollection);
							}
							if (bottomColltionNum == nearCollectionNum) {
								collections.add(nearCollection);
							}
							if (rightColltionNum == nearCollectionNum) {
								collections.add(nearCollection);
							}
						} else if (collectionName.endsWith("0")) {
							if (topColltionNum == nearCollectionNum) {
								collections.add(nearCollection);
							}
							if (bottomColltionNum == nearCollectionNum) {
								collections.add(nearCollection);
							}
							if (leftColltionNum == nearCollectionNum) {
								collections.add(nearCollection);
							}
						} else {
							if (topColltionNum == nearCollectionNum) {
								collections.add(nearCollection);
							}
							if (bottomColltionNum == nearCollectionNum) {
								collections.add(nearCollection);
							}
							if (leftColltionNum == nearCollectionNum) {
								collections.add(nearCollection);
							}
							if (rightColltionNum == nearCollectionNum) {
								collections.add(nearCollection);
							}
						}
					} catch (NumberFormatException e) {
						// TODO: handle exception
						LOGGER.info("인접도엽이름 정수아님");
					}
				}
			} catch (NumberFormatException e) {
				LOGGER.info("대상도엽 숫자아님");
			}

			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					CollectionValidator validator = null;
					try {
						validator = new CollectionValidator(collection, collections, layerTypeList, mapSystemRule,
								fileType);
					} catch (SchemaException | FactoryException | TransformException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					execValidator(userVO, fileType, validator, progressMap);
				}
			};
			execService.execute(runnable);
		}
		execService.shutdown();
	}

	private void execValidator(UserVO userVO, String fileType, CollectionValidator validator,
			Map<String, Object> progressMap) {
		// progress : 2 / 3 -> 2 : 검수 성공, 3 : 검수 실패d
		try {
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
			// JSONObject returnJSON = new JSONObject();
			// returnJSON.put("ErrorLayer", isErrorLayer);
			// returnJSON.put("Publising ErrorLayer", isAllSuccessPublish);
			System.out.println("완료");
			// return returnJSON;
		} catch (Exception e) {
			System.out.println("실패");
			// e.printStackTrace();
			// JSONObject returnJSON = new JSONObject();
			// returnJSON.put("ErrorLayer", false);
			// returnJSON.put("Publising ErrorLayer", false);
			// System.out.println("완료");
			// return returnJSON;
		}
	}

	private Map<String, Object> publishErrorLayer(UserVO userVO, ErrorLayer errorLayer)
			throws IllegalArgumentException, MalformedURLException {
		if (errorLayer != null) {
			return errorLayerService.publishErrorLayer(userVO, errorLayer);
		}
		return null;
	}
}
