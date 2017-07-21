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

package com.git.opengds.validator.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.git.opengds.common.AbstractController;
import com.git.opengds.user.domain.UserVO;
import com.git.opengds.user.domain.UserVO.EnUserType;
import com.git.opengds.validator.service.ErrorReportService;

@Controller("errorLayerController")
@RequestMapping("/errorLayer")
public class ErrorReportController extends AbstractController {

	@Autowired
	ErrorReportService errReportService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/errorReport.ajax")
	@ResponseBody
	public JSONObject seletErrorLayer(HttpServletRequest request, @RequestBody String geo) throws Exception {

		UserVO generalUser = (UserVO) getSession(request, EnUserType.GENERAL.getTypeName());
		if (generalUser == null) {
			return null;
		}
		
		JSONObject geoObj =  (JSONObject) JSONValue.parse(geo);
		JSONObject json = errReportService.getDetailsReport(generalUser, (String) geoObj.get("errorLayer"));
		return json;
	}
}
