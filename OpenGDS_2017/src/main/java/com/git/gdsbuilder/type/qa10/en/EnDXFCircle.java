package com.git.gdsbuilder.type.qa10.en;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.git.gdsbuilder.type.qa10.feature.style.DTDXFCircleStyle;

public enum EnDXFCircle {

	// Enum
	RADIUS("40", "radius", "double precision", "");

	String dxfCode;
	String dxfProperty;
	String type;
	String defaultDesc;

	EnDXFCircle(String groupCode, String dtDesc, String type, String defaultDesc) {
		this.dxfCode = groupCode;
		this.dxfProperty = dtDesc;
		this.type = type;
		this.defaultDesc = defaultDesc;
	}

	public String getDxfCode() {
		return dxfCode;
	}

	public void setDxfCode(String dxfCode) {
		this.dxfCode = dxfCode;
	}

	public String getDxfProperty() {
		return dxfProperty;
	}

	public void setDxfProperty(String dxfProperty) {
		this.dxfProperty = dxfProperty;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDefaultDesc() {
		return defaultDesc;
	}

	public void setDefaultDesc(String defaultDesc) {
		this.defaultDesc = defaultDesc;
	}

	public static Hashtable<String, Object> getProperties(DTDXFCircleStyle style) {

		Hashtable<String, Object> properties = new Hashtable<String, Object>();
		properties.putAll(EnDXFCommon.getProperties(style));
		properties.put(EnDXFCircle.RADIUS.getDxfProperty(), style.getRadius());

		return properties;
	}

	public static Hashtable<String, Object> getCircleColumns() {

		Hashtable<String, Object> columns = new Hashtable<String, Object>();
		columns.putAll(EnDXFCommon.getCommonColumns());
		columns.put(EnDXFCircle.RADIUS.getDxfProperty(), EnDXFCircle.RADIUS.getType());
		return columns;

	}
}
