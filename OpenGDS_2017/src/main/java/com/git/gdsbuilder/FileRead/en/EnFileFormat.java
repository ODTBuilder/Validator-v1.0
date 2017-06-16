package com.git.gdsbuilder.FileRead.en;

/**
 * EnFileFormat 타입
 * @author SG.Lee
 * @Date 2017. 6. 5. 오후 5:45:47
 * */
public enum EnFileFormat {
	DXF("DXF", "dxf"), 
	NGI("NGI", "ngi"), 
	SHP("SHP", "shp");
	String state;
	String stateName;
	
	EnFileFormat(String state, String stateName) {
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
