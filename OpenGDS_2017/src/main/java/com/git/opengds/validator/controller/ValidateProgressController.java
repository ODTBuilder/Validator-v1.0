package com.git.opengds.validator.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.git.gdsbuilder.type.validate.collection.ValidateProgressList;
import com.git.opengds.common.AbstractController;
import com.git.opengds.user.domain.UserVO;
import com.git.opengds.user.domain.UserVO.EnUserType;
import com.git.opengds.validator.service.ValidatorProgressService;
import com.git.opengds.validator.service.ValidatorProgressServiceImpl;

@Controller("validateProgressController")
@RequestMapping("/validateProgress")
public class ValidateProgressController extends AbstractController {

	@Autowired
	private ValidatorProgressService progressService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/validateProgress.ajax")
	@ResponseBody
	public ValidateProgressList getValidateProgressList(HttpServletRequest request, @RequestBody String atest)
			throws Exception {
		UserVO generalUser  = (UserVO) getSession(request,EnUserType.GENERAL.getTypeName());
		if(generalUser==null){
			return null;
		}
		String type = "ngi";
		String collectionName = "35811033";

		return progressService.selectProgressOfCollection(generalUser, type, collectionName);
	}
}
