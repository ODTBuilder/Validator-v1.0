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

package com.git.opengds.validator.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.git.opengds.common.AbstractController;
import com.git.opengds.validator.service.ValidatorService;

@Controller("validatorController")
@RequestMapping("/validator")
public class ValidatorController extends AbstractController {

	@Autowired
	ValidatorService validatorService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/validate.ajax")
	@ResponseBody
	public JSONObject geoserverAddLoadAjax(HttpServletRequest request, @RequestBody String geo) throws Exception {

	/*	JSONParser parser = new JSONParser();
			try {

				Object obj = parser.parse(new FileReader("C:\\Users\\GIT\\Desktop\\옵션txt\\NGI_BRIDGENAME.txt"));
				JSONObject jsonObject = (JSONObject) obj;
				String jsonStr = jsonObject.toString();
				//operatorService.autoOperation(jsonObject);
				return validatorService.validate(jsonStr);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null*/;
		return validatorService.validate(geo);
	}
}
