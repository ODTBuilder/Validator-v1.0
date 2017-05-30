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

package com.git.opengds.geoserver.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 프록시서버 요청을 수행한다.
 * @author SG.Lee
 * @Date 2016.05
 * */
@Controller("proxyServerController")
@RequestMapping("/geoserver")
public class ProxyServerController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * 요청 URL을 ProxyServer로 요청한다.
	 * 
	 * @author SG.LEE
	 * @data 2016.06
	 * @param request - 클라이언트의 요청과 관련된 정보와 동작을 가지고 있는 객체
	 * @param response - 클라이언트의 반응과 관련된 정보와 동작을 가지고 있는 객체
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/proxy.ajax")
	@ResponseBody
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String urlParam = request.getParameter("url");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		if (urlParam == null || urlParam.trim().length() == 0) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		boolean doPost = request.getMethod().equalsIgnoreCase("POST");
		URL url = new URL(urlParam);
		HttpURLConnection http = (HttpURLConnection) url.openConnection();
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			if (!key.equalsIgnoreCase("Host")) {
				http.setRequestProperty(key, request.getHeader(key));
			}
		}
		
		http.setDoInput(true);
		http.setDoOutput(doPost);

		byte[] buffer = new byte[8192];
		int read = -1;

		if (doPost) {
			OutputStream os = http.getOutputStream();
			ServletInputStream sis = request.getInputStream();
			while ((read = sis.read(buffer)) != -1) {
				os.write(buffer, 0, read);
			}
			os.close();
		}

		InputStream is = http.getInputStream();
		response.setStatus(http.getResponseCode());
		
		Map headerKeys = http.getHeaderFields();
		Set	keySet = headerKeys.keySet();
		Iterator iter = keySet.iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			String value = http.getHeaderField(key);
			if (key != null && value != null) {
				response.setHeader(key, value);
			}
		}
		
		ServletOutputStream sos = response.getOutputStream();
		response.resetBuffer();
		while ((read = is.read(buffer)) != -1) {
			sos.write(buffer, 0, read);
		}
		response.flushBuffer();
		sos.close();
	}
}