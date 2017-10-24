package com.git.opengds.validator.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
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

	@Inject
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
		ValidateProgressList shpList = progressService.selectProgressOfCollection(generalUser, "shp");

		JSONObject returnList = new JSONObject();
		returnList.put("ngi", ngiList);
		returnList.put("dxf", dxfList);
		returnList.put("shp", shpList);

		return returnList;
	}
}
