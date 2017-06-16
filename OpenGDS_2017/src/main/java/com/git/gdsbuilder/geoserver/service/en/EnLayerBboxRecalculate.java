package com.git.gdsbuilder.geoserver.service.en;

public enum EnLayerBboxRecalculate {
	ALL("ALL", "all", "recalculate=nativebbox,latlonbbox"), 
	NATIVEBBOX("NATIVEBBOX", "nativebbox","recalculate=nativebbox"), 
	LATLONBBOX("LATLONBBOX", "latlonbbox", "recalculate=latlonbbox");
	String state;
	String stateName;
	String value;

	EnLayerBboxRecalculate(String state, String stateName, String value) {
		this.state = state;
		this.stateName = stateName;
		this.value= value;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
