package com.git.gdsbuilder.geoserver.service.en;

/**
 * WFSOutputFormat 타입
 * @author SG.Lee
 * @Date 2017. 6. 5. 오후 5:45:47
 * */
public enum EnWFSOutputFormat {
	GML2("GML2", "gml2"), 
	GML3("GML3", "gml3"), 
	SHP("SHP", "shape-zip"),
	JSON("JSON", "application/json"),
	JSONP("JSONP", "text/javascript"),
	CSV("CSV", "csv");
	String state;
	String stateName;
	
	EnWFSOutputFormat(String state, String stateName) {
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
