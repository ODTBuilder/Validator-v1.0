package com.git.opengds.validator.controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.git.opengds.validator.service.ErrorLayerExportService;

@Controller("layerFileExportController")
@RequestMapping("/fileExport")
public class LayerFileExportController {

	ErrorLayerExportService exportService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/fileExport.ajax")
	@ResponseBody
	public JSONObject exportLayerToFile(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String key = paramNames.nextElement().toString();
		}
		return null;
	}
}
