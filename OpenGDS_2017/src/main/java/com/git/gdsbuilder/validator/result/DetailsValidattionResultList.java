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
 * DetailsValidateResultList 정보를 담고 있는 클래스
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:56:14
 * */
public class DetailsValidattionResultList extends ArrayList<DetailsValidationResult> {

	/**
	 * DetailsValidateResultList를 JSONArray로 파싱
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:56:27
	 * @return JSONArray
	 * @throws
	 * */
	public JSONArray parseJSON() {

		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < this.size(); i++) {
			DetailsValidationResult dtResult = this.get(i);
			JSONObject dtReport = dtResult.parseJSON();
			jsonArray.add(dtReport);
		}
		return jsonArray;
	}

}
