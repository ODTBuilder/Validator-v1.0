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

package com.git.opengds.builder.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.git.opengds.common.AbstractController;
import com.git.opengds.user.domain.UserVO;
import com.git.opengds.user.domain.UserVO.EnUserType;

/**
 * Handles requests for the application home page.
 */
@Controller("treeBuilderController")
public class TreeBuilderController extends AbstractController{

	private static final Logger logger = LoggerFactory.getLogger(TreeBuilderController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/builder.do")
	public ModelAndView builderView(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		UserVO generalUser  = (UserVO) getSession(request,EnUserType.GENERAL.getTypeName());
		
		
		//세션이 없을경우 login 페이지호출
		if(generalUser==null){
			mav.setViewName("redirect:/user/loginView.do");
		}
		else{
			mav.setViewName("/map/tree_builder");
			mav.addObject("user", generalUser);
		}
		return mav;
	}
}
