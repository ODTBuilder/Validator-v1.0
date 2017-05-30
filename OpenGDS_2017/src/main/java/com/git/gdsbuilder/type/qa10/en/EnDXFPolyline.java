package com.git.gdsbuilder.type.qa10.en;

import java.util.Hashtable;

import com.git.gdsbuilder.type.qa10.feature.style.DTDXFPolylineStyle;

public enum EnDXFPolyline {

	// Enum
	STARTWIDTH("40", "start_width", "double precision", "0"), ENDWIDTH("41", "end_width", "double precision",
			"0"), SURFACETYPE("75", "surface_type", "integer",
					"0"), SURFACEMDENSITY("73", "surface_m_density", "integer", "0"), SURFACENDENSITY("74",
							"surface_n_density", "integer",
							"0"), ROWS("", "rows", "integer", "0"), COLUMNS("", "columns", "integer", "0");

	String dxfCode;
	String dxfProperty;
	String type;
	String defaultDesc;

	EnDXFPolyline(String groupCode, String dtDesc, String type, String defaultDesc) {
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

	public static Hashtable<String, Object> getProperties(DTDXFPolylineStyle style) {

		Hashtable<String, Object> properties = new Hashtable<String, Object>();
		properties.putAll(EnDXFCommon.getProperties(style));
		properties.put(EnDXFPolyline.STARTWIDTH.getDxfProperty(), style.getStartWidth());
		properties.put(EnDXFPolyline.ENDWIDTH.getDxfProperty(), style.getEndWidth());
		properties.put(EnDXFPolyline.SURFACETYPE.getDxfProperty(), style.getSurefaceType());
		properties.put(EnDXFPolyline.SURFACEMDENSITY.getDxfProperty(), style.getSurefaceDensityColumns());
		properties.put(EnDXFPolyline.SURFACENDENSITY.getDxfProperty(), style.getSurefaceDensityRows());
		properties.put(EnDXFPolyline.ROWS.getDxfProperty(), style.getRows());
		properties.put(EnDXFPolyline.COLUMNS.getDxfProperty(), style.getColumns());

		return properties;

	}

	public static Hashtable<String, Object> getPolylineColumns() {

		Hashtable<String, Object> columns = new Hashtable<String, Object>();
		columns.putAll(EnDXFCommon.getCommonColumns());
		columns.put(EnDXFPolyline.STARTWIDTH.getDxfProperty(), EnDXFPolyline.STARTWIDTH.getType());
		columns.put(EnDXFPolyline.ENDWIDTH.getDxfProperty(), EnDXFPolyline.ENDWIDTH.getType());
		columns.put(EnDXFPolyline.SURFACETYPE.getDxfProperty(), EnDXFPolyline.SURFACETYPE.getType());
		columns.put(EnDXFPolyline.SURFACEMDENSITY.getDxfProperty(), EnDXFPolyline.SURFACEMDENSITY.getType());
		columns.put(EnDXFPolyline.SURFACENDENSITY.getDxfProperty(), EnDXFPolyline.SURFACENDENSITY.getType());
		columns.put(EnDXFPolyline.ROWS.getDxfProperty(), EnDXFPolyline.ROWS.getType());
		columns.put(EnDXFPolyline.COLUMNS.getDxfProperty(), EnDXFPolyline.COLUMNS.getType());
		return columns;

	}
}
