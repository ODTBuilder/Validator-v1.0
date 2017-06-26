package com.git.gdsbuilder.geoserver.service.factory;

import com.git.gdsbuilder.geoserver.service.wfs.WFSGetFeature;
import com.git.gdsbuilder.geoserver.service.wms.WMSGetMap;

public interface DTGeoserverServiceFactory {
	public WFSGetFeature createWFSGetFeature(String serverURL, String version, String typeName, String outputformat, int maxFeatures, String bbox,
			String format_options, String featureID);
	
	public WFSGetFeature createWFSGetFeature(String serverURL, String version, String typeName, String bbox);
	
	public WMSGetMap createWMSGetMap(String serverURL, String version, String format, String layers, String tiled, String transparent,
			String bgcolor, String crs, String bbox, int width, int height, String styles, String exceptions,
			String time, String sld, String sld_body);
	
	public WMSGetMap createWMSGetMap(String serverURL, String version, String format, String layers, String tiled, String crs, String bbox, int width, int height, String styles);
}
