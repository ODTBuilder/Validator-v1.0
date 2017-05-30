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

import org.springframework.stereotype.Service;

import com.git.gdsbuilder.geoserver.service.GeoserverServiceManager;
import com.git.gdsbuilder.geoserver.service.impl.GeoserverServiceManagerImpl;
import com.git.gdsbuilder.geoserver.service.wfs.WFSGetFeature;
import com.git.gdsbuilder.geoserver.service.wms.WMSGetMap;

/**
 * 프록시서버 요청에 대한 요청을 처리하는 클래스
 * @author SG.Lee
 * @Date 2017. 5. 12. 오전 2:08:04
 * */
@Service
public class GeoserverLayerProxyServiceImpl implements GeoserverLayerProxyService {
	private static final String serverURL;
	private static final String user;
//	private static GeoServerRESTReader reader;

	static {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			properties.load(classLoader.getResourceAsStream("geoserver.properties"));
//			reader = new GeoServerRESTReader(properties.getProperty("url"), properties.getProperty("id"), properties.getProperty("pw"));
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
	 * @param request +
	 * @param response
	 * @throws ServletException
	 * @throws IOException void
	 * @throws
	 * */
	/*public void requestWMSLayer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String urlParam = this.createWMSURL(request);
		this.requestProxyService(request, response, urlParam);
	}*/
	

	/*private String createWMSURL(HttpServletRequest request) {
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
	}*/
	
	public void requestWMSLayer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		WMSGetMap wmsGetMap = this.createWMSGetMap(request);
		GeoserverServiceManager geoserverService = new GeoserverServiceManagerImpl(request,response);
		geoserverService.requestWMSGetMap(wmsGetMap);
	}
	
	private WMSGetMap createWMSGetMap(HttpServletRequest request){
		 String serverURL = this.serverURL + "/" + user + "/wms";
		 String version="";
		 String format="";
		 String layers="";
		 boolean tiled=false;
		 boolean transparent=false;
		 String bgcolor="";
		 String crs="";
		 String bbox="";
		 int width=0;
		 int height=0;
		 String styles="";
		 String exceptions = "application/vnd.ogc.se_xml"; 
		 String time="";
		 String sld="";
		 String sld_body="";
		
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String key = paramNames.nextElement().toString();
			String value = request.getParameter(key);

			if (key.toLowerCase().equals("layers")) {
				layers=value;
			}
			else if(key.toLowerCase().equals("version")){
				bbox=value;
			} 
			else if(key.toLowerCase().equals("bbox")){
				bbox=value;
			} 
			else if(key.toLowerCase().equals("crs")){
				crs=value;
			}
			else if(key.toLowerCase().equals("format")){
				format=value;
			}
			else if(key.toLowerCase().equals("layers")){
				layers=value;				
			}
			else if(key.toLowerCase().equals("tiled")){
				tiled=Boolean.valueOf(value);
			}
			else if(key.toLowerCase().equals("transparent")){
				transparent=Boolean.valueOf(value);
			}
			else if(key.toLowerCase().equals("bgcolor")){
				bgcolor=value;
			}
			else if(key.toLowerCase().equals("crs")){
				crs=value;
			}
			else if(key.toLowerCase().equals("bbox")){
				bbox=value;
			}
			else if(key.toLowerCase().equals("width")){
				width=Integer.parseInt(value);
			}
			else if(key.toLowerCase().equals("height")){
				height=Integer.parseInt(value);
			}
			else if(key.toLowerCase().equals("styles")){
				styles=value;
			}
			else if(key.toLowerCase().equals("exceptions")){
				exceptions=value;
			}
			else if(key.toLowerCase().equals("time")){
				time=value;
			}
			else if(key.toLowerCase().equals("sld")){
				sld=value;
			}
			else if(key.toLowerCase().equals("sld_body")){
				sld_body=value;
			}
		}
		return new WMSGetMap(serverURL, version, format, layers, tiled, transparent, bgcolor, crs, bbox, width, height, styles, exceptions, time, sld, sld_body);
	}
	
	public void requestWFSLayer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String urlParam = this.createWFSURL(request);
//		this.requestProxyService(request, response, urlParam);
	}
	
	private String createWFSURL(HttpServletRequest request){
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
	
	
	
	
	public void requestGetFeature(HttpServletRequest request, HttpServletResponse response){
//		String urlParam = this.createGetFeatureInfoURL(request);
		WFSGetFeature wfsGetFeature = this.createWFSGetFeature(request);
		GeoserverServiceManager geoserverService = new GeoserverServiceManagerImpl(request,response);
		geoserverService.requestWFSGetFeature(wfsGetFeature);
	};
	
	@SuppressWarnings("unused")
	private WFSGetFeature createWFSGetFeature(HttpServletRequest request) {
		String serverURL = this.serverURL + "/" + user + "/wfs"; 
		String version = "";
		String typeName = "";
		String bbox = "";
		String outputformat = "";
		String format_options= "";

		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String key = paramNames.nextElement().toString();
			String value = request.getParameter(key);

			if (key.equals("version")) {
				version=value;
			} else if (key.equals("typeName")) {
				typeName=value;
			} else if (key.equals("bbox")) {
				bbox=value;
			} else if (key.equals("outputformat")) {
				outputformat=value;
			} else if (key.equals("format_options")) {
				format_options=value;
			}
		}
		return new WFSGetFeature(serverURL, version, typeName, outputformat, bbox, format_options);
	}
	
	
	
	
	
	
	/*
	private void requestProxyService(HttpServletRequest request, HttpServletResponse response, String urlParam) throws ServletException, IOException {
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
	}*/

}
