package com.git.gdsbuilder.type.dxf.en;

import java.util.Hashtable;

import com.git.gdsbuilder.type.dxf.feature.style.DTDXFTextSyle;

public enum EnDXFText {

	// Enum
	TEXTHEIGHT("40", "text_height", "double precision", "0"), TEXTROTATION("50", "text_rotation", "double precision",
			"0"), XSCALE("41", "x_scale", "double precision", "1"), ANGLE("51", "angle", "double precision",
					"0"), TEXTSTYLE("7", "text_style", "varchar(50)", "STANDARD"), TEXTGENERATIONFLAGS("71",
							"text_generation_flags", "integer",
							"0"), HORIZONTALTYPE("72", "horizontal_type", "integer", "0"), VERTICALTYPE("73",
									"vertical_type", "integer", "0"), TEXT("1", "text_value", "varchar(100)", null);

	String dxfCode;
	String dxfProperty;
	String type;
	String defaultDesc;

	EnDXFText(String groupCode, String dtDesc, String type, String defaultDesc) {
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

	public static Hashtable<String, Object> getProperties(DTDXFTextSyle style) {

		Hashtable<String, Object> properties = new Hashtable<String, Object>();
		properties.putAll(EnDXFCommon.getProperties(style));
		properties.put(EnDXFText.TEXTHEIGHT.getDxfProperty(), style.getTextHeight());
		properties.put(EnDXFText.TEXTROTATION.getDxfProperty(), style.getTextRotation());
		properties.put(EnDXFText.XSCALE.getDxfProperty(), style.getxScale());
		properties.put(EnDXFText.ANGLE.getDxfProperty(), style.getAngle());
		properties.put(EnDXFText.TEXTSTYLE.getDxfProperty(), style.getTextStyle());
		properties.put(EnDXFText.TEXTGENERATIONFLAGS.getDxfProperty(), style.getFlag());
		properties.put(EnDXFText.HORIZONTALTYPE.getDxfProperty(), style.getHorizontalType());
		properties.put(EnDXFText.VERTICALTYPE.getDxfProperty(), style.getVerticalType());
		properties.put(EnDXFText.TEXT.getDxfProperty(), style.getText());

		return properties;

	}

	public static Hashtable<String, Object> getTextColumns() {

		Hashtable<String, Object> columns = new Hashtable<String, Object>();
		columns.putAll(EnDXFCommon.getCommonColumns());
		columns.put(EnDXFText.TEXTHEIGHT.getDxfProperty(), EnDXFText.TEXTHEIGHT.getType());
		columns.put(EnDXFText.TEXTROTATION.getDxfProperty(), EnDXFText.TEXTROTATION.getType());
		columns.put(EnDXFText.XSCALE.getDxfProperty(), EnDXFText.XSCALE.getType());
		columns.put(EnDXFText.ANGLE.getDxfProperty(), EnDXFText.ANGLE.getType());
		columns.put(EnDXFText.TEXTSTYLE.getDxfProperty(), EnDXFText.TEXTSTYLE.getType());
		columns.put(EnDXFText.TEXTGENERATIONFLAGS.getDxfProperty(), EnDXFText.TEXTGENERATIONFLAGS.getType());
		columns.put(EnDXFText.HORIZONTALTYPE.getDxfProperty(), EnDXFText.HORIZONTALTYPE.getType());
		columns.put(EnDXFText.VERTICALTYPE.getDxfProperty(), EnDXFText.VERTICALTYPE.getType());
		columns.put(EnDXFText.TEXT.getDxfProperty(), EnDXFText.TEXT.getType());

		return columns;

	}
}
