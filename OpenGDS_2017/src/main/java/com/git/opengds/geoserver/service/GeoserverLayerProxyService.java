package com.git.opengds.geoserver.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.git.opengds.user.domain.UserVO;

public interface GeoserverLayerProxyService {
	public void requestWMSLayer(UserVO userVO, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	public void requestGetFeature(UserVO userVO, HttpServletRequest request, HttpServletResponse response);
	public void requestGetFeatureInfo(UserVO userVO, HttpServletRequest request, HttpServletResponse response);
	public void requestWMSGetLegendGraphic(UserVO userVO, HttpServletRequest request, HttpServletResponse response);
	public void requestGeoserverDataOutput(UserVO userVO, HttpServletRequest request, HttpServletResponse response);
}
