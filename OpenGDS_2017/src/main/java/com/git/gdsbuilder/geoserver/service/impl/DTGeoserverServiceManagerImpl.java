package com.git.gdsbuilder.geoserver.service.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.git.gdsbuilder.geoserver.service.DTGeoserverServiceManager;
import com.git.gdsbuilder.geoserver.service.wfs.WFSGetFeature;
import com.git.gdsbuilder.geoserver.service.wms.WMSGetFeatureInfo;
import com.git.gdsbuilder.geoserver.service.wms.WMSGetMap;
import com.git.gdsbuilder.net.impl.ProxyServerImpl;

public class DTGeoserverServiceManagerImpl implements DTGeoserverServiceManager {
	private HttpServletRequest request=null;
	private HttpServletResponse response=null;
	
	public DTGeoserverServiceManagerImpl(HttpServletRequest request, HttpServletResponse response){
		this.request=request;
		this.response=response;
	}
	
	@Override
	public void requestWFSGetFeature(WFSGetFeature feature) {
		String url = feature.getWFSGetFeatureURL();
		this.requestProxyService(url);
	};
	
	@Override
	public void requestWMSGetFeatureInfo(WMSGetFeatureInfo featureInfo) {
		String url = featureInfo.getWMSGetFeatureInfoURL();
		this.requestProxyService(url);
	};

	@Override
	public void requestWMSGetMap(WMSGetMap map) {
		String url = map.getWMSGetMapURL();
		this.requestProxyService(url);
	};
	
	
	private void requestProxyService(String url) {
		try {
			new ProxyServerImpl(request, response, url).requestProxyService();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
