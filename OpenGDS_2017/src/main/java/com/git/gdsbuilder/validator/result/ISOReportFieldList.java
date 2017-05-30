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

package com.git.gdsbuilder.validator.result;

import java.util.ArrayList;

import org.json.JSONObject;
import org.json.simple.JSONArray;

/**
 * ISOReportFieldList 정보를 담고 있는 클래스
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:58:22
 * */
public class ISOReportFieldList extends ArrayList<ISOReportField> {

	/**
	 * totalAccuracy 계산
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:58:30
	 * @return double
	 * @throws
	 * */
	public double getTotalAccuracy() {

		double totalAccuracy = 0;

		for (int i = 0; i < this.size(); i++) {
			ISOReportField isoField = this.get(i);
			double accuracy = isoField.getAccuracyValue();
			totalAccuracy += accuracy;
		}
		return Double.parseDouble(String.format("%.3f", totalAccuracy * 100));
	}

	/**
	 * ISOReportFieldList를 JSONArray로 파싱
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:58:32
	 * @return JSONArray
	 * @throws
	 * */
	public JSONArray parseJSON() {

		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < this.size(); i++) {
			ISOReportField isoField = this.get(i);
			JSONObject isoFieldObj = isoField.parseJSON();
			jsonArray.add(isoFieldObj);
		}
		return jsonArray;
	}

}
