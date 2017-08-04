package com.git.gdsbuilder.type.dxf.en;

import java.util.Hashtable;

import com.git.gdsbuilder.type.dxf.feature.style.DTDXFArcStyle;

public enum EnDXFArc {

	// Enum
	RADIUS("40", "radius", "double precision", "0"), START_ANGLE("50", "start_angle", "double precision",
			""), END_ANGLE("51", "end_angle", "double precision",
					""), COUNTERCLOCKWISE("51", "counterclockwise", "integer", "0");
	String dxfCode;
	String dxfProperty;
	String type;
	String defaultDesc;

	EnDXFArc(String groupCode, String dtDesc, String type, String defaultDesc) {
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

	public static Hashtable<String, Object> getProperties(DTDXFArcStyle style) {

		Hashtable<String, Object> properties = new Hashtable<String, Object>();

		properties.putAll(EnDXFCommon.getProperties(style));
		properties.put(EnDXFArc.RADIUS.dxfProperty, style.getRadius());
		properties.put(EnDXFArc.START_ANGLE.dxfProperty, style.getStart_angle());
		properties.put(EnDXFArc.END_ANGLE.dxfProperty, style.getEnd_angle());
		properties.put(EnDXFArc.COUNTERCLOCKWISE.dxfProperty, style.isCounterclockwise());

		return properties;

	}

	public static Hashtable<String, Object> getArcColumns() {

		Hashtable<String, Object> columns = new Hashtable<String, Object>();
		columns.putAll(EnDXFCommon.getCommonColumns());
		columns.put(EnDXFArc.RADIUS.getDxfProperty(), EnDXFArc.RADIUS.getType());
		columns.put(EnDXFArc.START_ANGLE.getDxfProperty(), EnDXFArc.START_ANGLE.getType());
		columns.put(EnDXFArc.END_ANGLE.getDxfProperty(), EnDXFArc.END_ANGLE.getType());
		columns.put(EnDXFArc.COUNTERCLOCKWISE.getDxfProperty(), EnDXFArc.COUNTERCLOCKWISE.getType());
		return columns;

	}

}
