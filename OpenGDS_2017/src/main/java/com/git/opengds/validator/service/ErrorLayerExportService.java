package com.git.opengds.validator.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.git.opengds.user.domain.UserVO;
import com.vividsolutions.jts.io.ParseException;

public interface ErrorLayerExportService {

	public boolean exportErrorLayer(UserVO userVO, String format, String type, String name, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException;
}
