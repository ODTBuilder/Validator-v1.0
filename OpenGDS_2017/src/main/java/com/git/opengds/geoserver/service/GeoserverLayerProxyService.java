/*
 *  GeoServer-Manager - Simple Manager Library for GeoServer
 *  
 *  Copyright (C) 2007,2011 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.git.opengds.geoserver.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.geosolutions.geoserver.rest.GeoServerRESTReader;

/**
 * 프록시서버 요청에 대한 요청을 처리하는 클래스
 * @author SG.Lee
 * @Date 2017. 5. 12. 오전 2:08:04
 * */
@Service
public class GeoserverLayerProxyService {

	private static final String serverURL;
	private static final String user;
	private static GeoServerRESTReader reader;

	static {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			properties.load(classLoader.getResourceAsStream("geoserver.properties"));
			reader = new GeoServerRESTReader(properties.getProperty("url"), properties.getProperty("id"), properties.getProperty("pw"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serverURL = properties.getProperty("url");
		user = properties.getProperty("user");
	}

	/**
	 * WMS레이어 로드 요청을 처리한다.
	 * @author SG.Lee
	 * @Date 2017. 4
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException void
	 * @throws
	 * */
	public void requestWMSLayer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String urlParam = this.createWMSURL(request);
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
		Set keySet = headerKeys.keySet();
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

	private String createWMSURL(HttpServletRequest request) {
		StringBuffer wmsURL = new StringBuffer();

		wmsURL.append(serverURL + "/" + user + "/wms?");

		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String key = paramNames.nextElement().toString();
			String value = request.getParameter(key);

			if (key.equals("LAYERS")) {
				wmsURL.append("&"+key+"="+user+":"+value);
			} 
			else if(key.equals("BBOX")){
				wmsURL.append("&"+key+"="+value);
			} 
			else if(key.equals("SRS")||key.equals("FORMAT_OPTIONS")){}
			else {
				wmsURL.append("&"+key+"="+value);
			}
		}
		return wmsURL.toString();
	}
	
	public void requestWFSLayer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String urlParam = this.createWFSURL(request);
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
		Set keySet = headerKeys.keySet();
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
	
	private String createWFSURL(HttpServletRequest request) {
		StringBuffer wfsURL = new StringBuffer();

		wfsURL.append(serverURL + "/" + user + "/wms?");

		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String key = paramNames.nextElement().toString();
			String value = request.getParameter(key);

			wfsURL.append("&"+key+"="+value);
		}
		return wfsURL.toString();
	}
}
