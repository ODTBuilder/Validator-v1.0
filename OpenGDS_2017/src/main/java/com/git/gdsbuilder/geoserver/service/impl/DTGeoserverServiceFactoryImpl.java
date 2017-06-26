package com.git.gdsbuilder.geoserver.service.impl;

import com.git.gdsbuilder.geoserver.service.factory.DTGeoserverServiceFactory;
import com.git.gdsbuilder.geoserver.service.wfs.WFSGetFeature;
import com.git.gdsbuilder.geoserver.service.wms.WMSGetMap;

public class DTGeoserverServiceFactoryImpl implements DTGeoserverServiceFactory {
	
	@Override
	public WFSGetFeature createWFSGetFeature(String serverURL, String version, String typeName, String outputformat, int maxFeatures, String bbox,
			String format_options, String featureID){
		return new WFSGetFeature(serverURL, version, typeName, outputformat, maxFeatures, bbox, format_options, featureID);
	};
	
	@Override
	public WFSGetFeature createWFSGetFeature(String serverURL, String version, String typeName, String bbox){
		return new WFSGetFeature(serverURL, version, typeName, bbox);
	};
	
	@Override
	public WMSGetMap createWMSGetMap(String serverURL, String version, String format, String layers, String tiled, String transparent,
			String bgcolor, String crs, String bbox, int width, int height, String styles, String exceptions,
			String time, String sld, String sld_body){
		return new WMSGetMap(serverURL, version, format, layers, tiled, transparent, bgcolor, crs, bbox, width, height, styles, exceptions, time, sld, sld_body);
	};
	
	@Override
	public WMSGetMap createWMSGetMap(String serverURL, String version, String format, String layers, String tiled, String crs, String bbox, int width, int height, String styles){
		return new WMSGetMap(serverURL, version, format, layers, tiled, crs, bbox, width, height, styles);
	};
}
