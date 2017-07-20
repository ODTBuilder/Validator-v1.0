package com.git.opengds.validator.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
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

@Controller("validateProgressController")
@RequestMapping("/validateProgress")
public class ValidateProgressController extends AbstractController {

	@Autowired
	private ValidatorProgressService progressService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/validateProgress.ajax")
	@ResponseBody
	public JSONObject getValidateProgressList(HttpServletRequest request, @RequestBody  String atest) throws Exception {
		UserVO generalUser = (UserVO) getSession(request, EnUserType.GENERAL.getTypeName());
		if (generalUser == null) {
			return null;
		}

		ValidateProgressList ngiList = progressService.selectProgressOfCollection(generalUser, "ngi");
		ValidateProgressList dxfList = progressService.selectProgressOfCollection(generalUser, "dxf");

		JSONObject returnList = new JSONObject();
		returnList.put("QA20", ngiList);
		returnList.put("QA10", dxfList);

		return returnList;
	}
}
