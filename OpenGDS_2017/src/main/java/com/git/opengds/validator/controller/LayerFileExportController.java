package com.git.opengds.validator.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("layerFileExportController")
@RequestMapping("/fileExport")
public class LayerFileExportController {

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/fileExport.ajax")
	@ResponseBody
	public JSONObject exportLayerToFile(HttpServletRequest request, @RequestBody String jsonStr) {

		return null;
	}
}
