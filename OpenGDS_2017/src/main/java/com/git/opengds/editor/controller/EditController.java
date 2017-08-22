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

package com.git.opengds.editor.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.git.opengds.common.AbstractController;
import com.git.opengds.editor.service.EditService;
import com.git.opengds.user.domain.UserVO;
import com.git.opengds.user.domain.UserVO.EnUserType;

@Controller("editController")
@RequestMapping("/editLayerCollection")
public class EditController extends AbstractController {

	@Inject
	private EditService editService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editLayerCollection.ajax")
	@ResponseBody
	public void editLayerCollection(HttpServletRequest request, @RequestBody String geo) throws Exception {
		UserVO generalUser = (UserVO) getSession(request, EnUserType.GENERAL.getTypeName());
		editService.editLayerCollection(generalUser, geo);
	}
}
