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

import com.git.gdsbuilder.type.validate.layer.ValidateLayerType;
import com.git.gdsbuilder.validator.result.DetailsValidattionResultList;
import com.git.gdsbuilder.validator.result.ISOReportField;
import com.git.gdsbuilder.validator.result.ISOReportFieldList;
import com.git.opengds.file.ngi.dbManager.NGIDBQueryManager;
import com.git.opengds.file.ngi.persistence.NGILayerCollectionDAO;
import com.git.opengds.parser.error.ErrorReportParser;
import com.git.opengds.parser.validate.ValidateTypeParser;
import com.git.opengds.user.domain.UserVO;
import com.git.opengds.validator.dbManager.ErrorLayerDBQueryManager;
import com.git.opengds.validator.persistence.ErrorLayerDAO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class ErrorReportServiceImpl implements ErrorReportService {

	@Inject
	private ErrorLayerDAO errLayerDAO;

	@Inject
	private NGILayerCollectionDAO qa20LayerCollectionDAO;

/*	@Autowired
	private DataSourceTransactionManager txManager;*/
	
	/*public ErrorReportServiceImpl(UserVO userVO) {
		// TODO Auto-generated constructor stub
		errLayerDAO = new ErrorLayerDAOImpl(userVO);
		qa20LayerCollectionDAO = new QA20LayerCollectionDAOImpl(userVO);
	}*/

	public JSONObject getISOReport(UserVO userVO, String layerCollectionName, JSONObject jsonObject) {

		// 옵션
		JSONArray typeValidates = (JSONArray) jsonObject.get("typeValidate");
		if (typeValidates.size() != 0) {

			ISOReportFieldList isoFieldList = new ISOReportFieldList();
			NGIDBQueryManager qaLayerManager = new NGIDBQueryManager();
			ErrorLayerDBQueryManager errorLayerDBQueryManager = new ErrorLayerDBQueryManager();

			// transaction
		/*	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			TransactionStatus status = txManager.getTransaction(def);*/
			try {
				for (int j = 0; j < typeValidates.size(); j++) {
					JSONObject layerType = (JSONObject) typeValidates.get(j);
					ValidateLayerType type = ValidateTypeParser.typeParser(layerType);

					String typeName = type.getTypeName();
					List<String> layerIDList = type.getLayerIDList();
					double weight = type.getWeight();

					// get typeLayer's all feature count
					HashMap<String, Object> selectFeatureCountQuery = qaLayerManager
							.selectCountAllQA20FeaturesQuery(typeName, layerCollectionName, layerIDList);
					HashMap<String, Object> featureCount = qa20LayerCollectionDAO
							.selectCountAllQA20Features(userVO, selectFeatureCountQuery);

					// get typeLayer's error feature count
					HashMap<String, Object> selectErrorFeatureQuery = errorLayerDBQueryManager
							.selecctErrorFeaturesQuery(layerCollectionName, layerIDList);
					List<HashMap<String, Object>> errFeatures = errLayerDAO
							.selectErrorFeatures(userVO, selectErrorFeatureQuery);

					// get isoReportField
					ISOReportField isoField = ErrorReportParser.parseISOErrorReport(typeName, featureCount, errFeatures,
							weight);
					isoFieldList.add(isoField);
				}
//				txManager.commit(status);
				JSONObject isoReport = new JSONObject();
				JSONArray fields = isoFieldList.parseJSON();
				double totalAccuracy = isoFieldList.getTotalAccuracy();
				isoReport.put("fields", fields);
				isoReport.put("totalAccuracy", totalAccuracy);
				return isoReport;
			} catch (Exception e) {
//				txManager.rollback(status);
				return null;
			}
		} else {
			return null;
		}
	}

	public JSONObject getDetailsReport(UserVO userVO, String layerCollectionName) {
		ErrorLayerDBQueryManager errorLayerDBQueryManager = new ErrorLayerDBQueryManager();
		HashMap<String, Object> selectAllQuery = errorLayerDBQueryManager
				.selectAllErrorFeaturesQuery(layerCollectionName);

	/*	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);*/
		try {
			List<HashMap<String, Object>> errAllFeatures = errLayerDAO.selectAllErrorFeatures(userVO, selectAllQuery);
			DetailsValidattionResultList detailList = ErrorReportParser.parseDetailsErrorReport(errAllFeatures);
			if (errAllFeatures.size() != 0) {
				JSONObject detailReport = new JSONObject();
				JSONArray fields = detailList.parseJSON();
				detailReport.put("fields", fields);
//				txManager.commit(status);
				return detailReport;
			}
		} catch (Exception e) {
//			txManager.rollback(status);
			return null;
		}
		return null;
	}
}
