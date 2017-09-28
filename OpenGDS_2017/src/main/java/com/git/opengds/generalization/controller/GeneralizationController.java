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

package com.git.opengds.generalization.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.git.opengds.common.AbstractController;
import com.git.opengds.generalization.service.GeneralizationService;
import com.git.opengds.user.domain.UserVO;
import com.git.opengds.user.domain.UserVO.EnUserType;
import com.git.opengds.validator.service.ValidatorService;
import com.git.opengds.validator.service.ValidatorServiceImpl;

@Controller("generalizationController")
@RequestMapping("/generalization")
public class GeneralizationController extends AbstractController {

	@Autowired
	GeneralizationService generalizationService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exeGeneralization.ajax")
	@ResponseBody
	public void exeGeneralization(HttpServletRequest request, @RequestBody String jsonObject) throws Exception {
		
		UserVO generalUser  = (UserVO) getSession(request,EnUserType.GENERAL.getTypeName());
		if(generalUser==null){
//			return null;
		}
		generalizationService.exeGeneralization(generalUser, jsonObject);
//		return 
	}
}
