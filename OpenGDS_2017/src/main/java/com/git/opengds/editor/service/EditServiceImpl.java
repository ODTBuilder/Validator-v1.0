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

import javax.inject.Inject;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.opengds.user.domain.UserVO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class EditServiceImpl implements EditService {

	protected static final String none = "none";
	protected static final String isModified = "modify";
	protected static final String isCreated = "create";
	protected static final String isDeleted = "remove";

	protected static final String isShp = "shp";

	@Inject
	private EditDTLayerService editLayerService;

	@Inject
	private EditDTFeatureService editFeatureService;
	/*
	 * public EditServiceImpl(UserVO userVO) { // TODO Auto-generated constructor
	 * stub editLayerService = new EditLayerServiceImpl(userVO); editFeatureService
	 * = new EditFeatureServiceImpl(userVO); }
	 */

	@Override
	public boolean editLayerCollection(UserVO userVO, String editJSONStr) throws Exception {

		JSONParser jsonParser = new JSONParser();
		JSONObject editJSONObject = (JSONObject) jsonParser.parse(editJSONStr);

		boolean isSuccessed = false;

		// layerEdit
		Object layerEditObj = editJSONObject.get("layer");
		if (layerEditObj != null) {
			JSONObject layerEdit = (JSONObject) layerEditObj;
			isSuccessed = editLayerService.editLayer(userVO, layerEdit);
		}
		return isSuccessed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.git.opengds.editor.service.EditService#editFeature(com.git.opengds.user.
	 * domain.UserVO, java.lang.String)
	 */
	@Override
	public boolean editFeature(UserVO userVO, String editJSONStr) throws Exception {

		JSONParser jsonParser = new JSONParser();
		JSONObject editJSONObject = (JSONObject) jsonParser.parse(editJSONStr);

		boolean isSuccessed = false;

		// featureEdit
		Object featureEditObj = editJSONObject.get("feature");
		if (featureEditObj != null) {
			JSONObject featureEdit = (JSONObject) featureEditObj;
			isSuccessed = editFeatureService.editFeature(userVO, featureEdit);
		}
		return isSuccessed;
	}
}
