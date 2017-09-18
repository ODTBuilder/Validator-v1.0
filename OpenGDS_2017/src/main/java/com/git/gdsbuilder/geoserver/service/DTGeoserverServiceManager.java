package com.git.gdsbuilder.geoserver.service;

import com.git.gdsbuilder.geoserver.service.wfs.WFSGetFeature;
import com.git.gdsbuilder.geoserver.service.wms.WMSGetFeatureInfo;
import com.git.gdsbuilder.geoserver.service.wms.WMSGetMap;

public interface DTGeoserverServiceManager {
	public void requestWFSGetFeature(WFSGetFeature feature);
	public void requestWMSGetFeatureInfo(WMSGetFeatureInfo feature);
	public void requestWMSGetMap(WMSGetMap feature);
}
