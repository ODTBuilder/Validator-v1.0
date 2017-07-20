package com.git.opengds.validator.controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.git.opengds.common.AbstractController;
import com.git.opengds.user.domain.UserVO;
import com.git.opengds.user.domain.UserVO.EnUserType;
import com.git.opengds.validator.service.ErrorLayerExportService;
import com.git.opengds.validator.service.ErrorLayerExportServiceImpl;

@Controller("layerFileExportController")
@RequestMapping("/fileExport")
public class LayerFileExportController extends AbstractController{

	@Autowired
	ErrorLayerExportService exportService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/fileExport.ajax")
	@ResponseBody
	public boolean exportLayerToFile(HttpServletRequest request, HttpServletResponse response) throws IOException {

		UserVO generalUser  = (UserVO) getSession(request,EnUserType.GENERAL.getTypeName());
		if(generalUser==null){
			return false;
		}
		String format = null;
		String type = null;
		String name = null;

		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String key = paramNames.nextElement().toString();
			if (key.equals("format")) {
				format = request.getParameter(key);
			} else if (key.equals("type")) {
				type = request.getParameter(key);
			} else if (key.equals("name")) {
				name = request.getParameter(key);
			}
		}
		return exportService.exportErrorLayer(generalUser, format, type, name, request, response);
	}
}
