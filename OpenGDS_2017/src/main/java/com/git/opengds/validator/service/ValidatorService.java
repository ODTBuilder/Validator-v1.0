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

import org.json.simple.JSONObject;

import com.git.opengds.user.domain.UserVO;

/**
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 4:12:55
 * */
public interface ValidatorService {

	/**
	 *
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 4:12:57
	 * @param jsonObject
	 * @return
	 * @throws Exception JSONObject
	 * @throws
	 * */
	public JSONObject validate(UserVO userVO, String jsonObject) throws Exception;
}
