package com.git.opengds.validator.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
	public JSONObject exportLayerToFile(HttpServletRequest request, @RequestBody String jsonStr) throws IOException {
		exportService.exportErrorLayer(jsonStr);
		return null;
	}
}
