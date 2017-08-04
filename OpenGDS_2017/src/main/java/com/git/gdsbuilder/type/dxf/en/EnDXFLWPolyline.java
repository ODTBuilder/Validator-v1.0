package com.git.gdsbuilder.type.dxf.en;

import java.util.Hashtable;

import com.git.gdsbuilder.type.dxf.feature.style.DTDXFLWPolylineStyle;

public enum EnDXFLWPolyline {

	CONSTANTWIDTH("43", "constant_width", "double precision", "0"), ELEVATION("38", "elevation", "double precision",
			"0");

	String dxfCode;
	String dxfProperty;
	String type;
	String defaultDesc;

	EnDXFLWPolyline(String groupCode, String dtDesc, String type, String defaultDesc) {
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

	public static Hashtable<String, Object> getProperties(DTDXFLWPolylineStyle style) {

		Hashtable<String, Object> properties = new Hashtable<String, Object>();
		properties.putAll(EnDXFCommon.getProperties(style));
		properties.putAll(EnDXFPolyline.getProperties(style));
		properties.put(EnDXFLWPolyline.CONSTANTWIDTH.getDxfProperty(), style.getConstantwidth());
		properties.put(EnDXFLWPolyline.ELEVATION.getDxfProperty(), style.getElevation());

		return properties;

	}

	public static Hashtable<String, Object> getLwPolylineColumns() {

		Hashtable<String, Object> columns = new Hashtable<String, Object>();
		columns.putAll(EnDXFCommon.getCommonColumns());
		columns.putAll(EnDXFPolyline.getPolylineColumns());
		columns.put(EnDXFLWPolyline.CONSTANTWIDTH.getDxfProperty(), EnDXFLWPolyline.CONSTANTWIDTH.getType());
		columns.put(EnDXFLWPolyline.ELEVATION.getDxfProperty(), EnDXFLWPolyline.ELEVATION.getType());
		return columns;

	}

}
