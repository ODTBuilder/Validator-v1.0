package com.git.gdsbuilder.geoserver.service.en;

/**
 * GeoserverService 타입
 * @author SG.Lee
 * @Date 2017. 6. 5. 오후 5:45:47
 * */
public enum EnGeoserverService {
	WFS("WFS", "wfs"), 
	WMS("WMS", "wms"), 
	WCS("WCS", "wcs"),
	WMTS("WMTS", "wmts"),
	WPS("WPS", "wps"); 
	String state;
	String stateName;
	
	EnGeoserverService(String state, String stateName) {
		this.state = state;
		this.stateName = stateName;
	}
	
	public String getState() {
		return state;
		
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
