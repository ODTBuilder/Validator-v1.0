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

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.gdsbuilder.validator.result.DetailsValidattionResultList;
import com.git.opengds.parser.error.ErrorReportParser;
import com.git.opengds.user.domain.UserVO;
import com.git.opengds.validator.dbManager.ErrorLayerDBQueryManager;
import com.git.opengds.validator.persistence.ErrorLayerDAO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class ErrorReportServiceImpl implements ErrorReportService {

	@Inject
	private ErrorLayerDAO errLayerDAO;

	public JSONObject getDetailsReport(UserVO userVO, String layerCollectionName) {
		ErrorLayerDBQueryManager errorLayerDBQueryManager = new ErrorLayerDBQueryManager();
		HashMap<String, Object> selectAllQuery = errorLayerDBQueryManager
				.selectAllErrorFeaturesQuery(layerCollectionName);
		try {
			List<HashMap<String, Object>> errAllFeatures = errLayerDAO.selectAllErrorFeatures(userVO, selectAllQuery);
			DetailsValidattionResultList detailList = ErrorReportParser.parseDetailsErrorReport(errAllFeatures);
			if (errAllFeatures.size() != 0) {
				JSONObject detailReport = new JSONObject();
				JSONArray fields = detailList.parseJSON();
				detailReport.put("fields", fields);
				return detailReport;
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}
}
