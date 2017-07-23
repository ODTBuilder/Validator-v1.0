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

package com.git.opengds.parser.error;

import java.util.HashMap;
import java.util.List;

import com.git.gdsbuilder.validator.result.DetailsValidateResult;
import com.git.gdsbuilder.validator.result.DetailsValidateResultList;
import com.git.gdsbuilder.validator.result.ISOReportField;

/**
 * ErrorLayer를 조회하여 ErrorReport(검수결과) 객체로 파싱하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:18:26
 */
public class ErrorReportParser {

	/**
	 * ErrorLayer를 조회하여 DetailsValidateResultList 객체로 파싱 @author DY.Oh @Date
	 * 2017. 3. 11. 오후 2:18:29 @param errFeatures @return
	 * DetailsValidateResultList @throws
	 */
	public static DetailsValidateResultList parseDetailsErrorReport(List<HashMap<String, Object>> errFeatures) {

		DetailsValidateResultList dtValidateResultList = new DetailsValidateResultList();

		for (int i = 0; i < errFeatures.size(); i++) {
			HashMap<String, Object> errFeature = errFeatures.get(i);

			String collectionName = (String) errFeature.get("collection_name");
			String layerName = (String) errFeature.get("layer_name");
			String featureID = (String) errFeature.get("feature_id");
			String errorType = (String) errFeature.get("err_type");
			String errorName = (String) errFeature.get("err_name");
			double errorCoordinateX = (Double) errFeature.get("x_coordinate");
			double errorCoordinateY = (Double) errFeature.get("y_coordinate");

			DetailsValidateResult dtValidateResult = new DetailsValidateResult(collectionName, layerName, featureID,
					errorType, errorName, errorCoordinateX, errorCoordinateY);
			dtValidateResultList.add(dtValidateResult);
		}
		return dtValidateResultList;
	}

	/**
	 * ErrorLayer를 조회하여 ISOReportField 객체로 파싱 @author DY.Oh @Date 2017. 3. 11.
	 * 오후 2:18:32 @param typeName @param featureCount @param errFeatures @param
	 * weight @return ISOReportField @throws
	 */
	public static ISOReportField parseISOErrorReport(String typeName, HashMap<String, Object> featureCount,
			List<HashMap<String, Object>> errFeatures, double weight) {

		Long allFeatureCountLong = (Long) featureCount.get("all_feature_count");
		int allFeatureCount = allFeatureCountLong.intValue();
		int errFeatureCount = 0;
		String tmpErrFeatureID = "";
		for (int i = 0; i < errFeatures.size(); i++) {
			HashMap<String, Object> errFeature = errFeatures.get(i);
			String errFeatureID = (String) errFeature.get("feature_id");
			if (!tmpErrFeatureID.equals(errFeatureID)) {
				tmpErrFeatureID = errFeatureID;
				errFeatureCount++;
			} else {
				continue;
			}
		}
		ISOReportField isoField = new ISOReportField(typeName, allFeatureCount, errFeatureCount, weight);
		return isoField;
	}
}
