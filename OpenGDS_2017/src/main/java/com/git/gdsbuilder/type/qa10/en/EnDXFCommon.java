package com.git.gdsbuilder.type.qa10.en;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.git.gdsbuilder.type.qa10.feature.style.DTDXFStyle;

public enum EnDXFCommon {

	// Enum
	LAYERID("8", "layer_id", "varchar(100)", null), FEATUREID("5", "feature_id", "varchar(100)", null), VISIBILE("60",
			"visibile", "integer", "0"), LINETYPE("6", "line_type", "varchar(50)", "BYLAYER"), BLOCK("", "block",
					"varchar(50)", "false"), LINETYPESCALE("48", "line_type_scale", "double precision",
							"1.0"), COLOR("62", "color", "varchar(50)", "BYLAYER"),
	// COLORRGB???
	LINEWEIGHT("", "line_weight", "double precision", null), TRANSPARENCY("", "transparency", "varchar(50)",
			"ByLayer"), THICKNESS("", "thickness", "double precision",
					"0.0"), RGB("", "rgb", "varchar(50)", ""), FLAG("", "flag", "varchar(50)", "");

	String dxfCode;
	String dxfProperty;
	String type;
	String defaultDesc;

	EnDXFCommon(String groupCode, String dtDesc, String type, String defaultDesc) {
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

	public static Hashtable<String, Object> getProperties(DTDXFStyle style) {

		Hashtable<String, Object> properties = new Hashtable<String, Object>();

		properties.put(EnDXFCommon.LAYERID.dxfProperty, style.getLayerID());
		properties.put(EnDXFCommon.FEATUREID.dxfProperty, style.getFeatureID());
		properties.put(EnDXFCommon.VISIBILE.dxfProperty, style.isVisibile());
		properties.put(EnDXFCommon.LINETYPE.dxfProperty, style.getLineType());
		properties.put(EnDXFCommon.BLOCK.dxfProperty, style.isBlock());
		properties.put(EnDXFCommon.LINETYPESCALE.dxfProperty, style.getLinetypeScaleFactor());
		properties.put(EnDXFCommon.COLOR.dxfProperty, style.getColor());
		properties.put(EnDXFCommon.LINEWEIGHT.dxfProperty, style.getLineWeight());
		properties.put(EnDXFCommon.TRANSPARENCY.dxfProperty, style.getTransparency());
		properties.put(EnDXFCommon.THICKNESS.dxfProperty, style.getThickness());
		properties.put(EnDXFCommon.FLAG.dxfProperty, style.getFlags());
		//properties.put(EnDXFCommon.RGB.dxfProperty, style.getColorRGB());

		return properties;

	}

	public static Hashtable<String, Object> getCommonColumns() {

		Hashtable<String, Object> columns = new Hashtable<String, Object>();
		columns.put(EnDXFCommon.LAYERID.getDxfProperty(), EnDXFCommon.LAYERID.getType());
		columns.put(EnDXFCommon.FEATUREID.getDxfProperty(), EnDXFCommon.FEATUREID.getType());
		columns.put(EnDXFCommon.VISIBILE.getDxfProperty(), EnDXFCommon.VISIBILE.getType());
		columns.put(EnDXFCommon.LINETYPE.getDxfProperty(), EnDXFCommon.LINETYPE.getType());
		columns.put(EnDXFCommon.BLOCK.getDxfProperty(), EnDXFCommon.BLOCK.getType());
		columns.put(EnDXFCommon.LINETYPESCALE.getDxfProperty(), EnDXFCommon.LINETYPESCALE.getType());
		columns.put(EnDXFCommon.COLOR.getDxfProperty(), EnDXFCommon.COLOR.getType());
		columns.put(EnDXFCommon.LINEWEIGHT.getDxfProperty(), EnDXFCommon.LINEWEIGHT.getType());
		columns.put(EnDXFCommon.TRANSPARENCY.getDxfProperty(), EnDXFCommon.TRANSPARENCY.getType());
		columns.put(EnDXFCommon.THICKNESS.getDxfProperty(), EnDXFCommon.THICKNESS.getType());
		columns.put(EnDXFCommon.FLAG.getDxfProperty(), EnDXFCommon.FLAG.getType());
		columns.put(EnDXFCommon.RGB.getDxfProperty(), EnDXFCommon.RGB.getType());
		return columns;

	}

};
