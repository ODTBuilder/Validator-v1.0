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

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollectionList;
import com.git.gdsbuilder.type.validate.collection.ValidateLayerCollectionList;
import com.git.gdsbuilder.type.validate.error.ErrorLayerList;
import com.git.gdsbuilder.type.validate.layer.ValidateLayerTypeList;
import com.git.gdsbuilder.validator.collection.CollectionValidator;
import com.git.opengds.editor.service.EditService;
import com.git.opengds.parser.json.BuilderJSONParser;

@Service
public class ValidatorServiceImpl implements ValidatorService {

	@Autowired
	private ErrorLayerService errorLayerService;
	
	@Autowired
	private EditService editService;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject validate(String jsonObject) throws Exception {
		
		
		//editService.editTest();
		
		
		try {
			// 파라미터 파싱
			BuilderJSONParser parserManager = new BuilderJSONParser();
			HashMap<String, Object> valdateObj = parserManager.parseValidateObj(jsonObject);
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
		//return null;
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
