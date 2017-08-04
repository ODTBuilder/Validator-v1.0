package com.git.gdsbuilder.type.dxf.en;

import java.util.Hashtable;

import com.git.gdsbuilder.type.dxf.feature.style.DTDXFInsertStyle;

public enum EnDXFInsert {

	// Enum
	SCALE_X("41", "scale_x", "double precision", "1.0"), SCALE_Y("42", "scale_y", "double precision", "1.0"), SCALE_Z(
			"43", "scale_z", "double precision", "1.0"), ROTATE("50", "rotate", "double precision", "0"), ROWS("71",
					"row_count", "integer", "1"), COLUMNS("70", "columns_count", "integer", "1"), ROW_SPACING("45",
							"row_spacing", "double precision", "0"), COLUMN_SPACING("44", "column_spacing",
									"double precision", "0"), BLOCKID("2", "block_name", "varchar(100)", "");

	String dxfCode;
	String dxfProperty;
	String type;
	String defaultDesc;

	EnDXFInsert(String groupCode, String dtDesc, String type, String defaultDesc) {
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

	public static Hashtable<String, Object> getProperties(DTDXFInsertStyle style) {

		Hashtable<String, Object> properties = new Hashtable<String, Object>();
		properties.putAll(EnDXFCommon.getProperties(style));
		properties.put(EnDXFInsert.SCALE_X.getDxfProperty(), style.getScale_x());
		properties.put(EnDXFInsert.SCALE_Y.getDxfProperty(), style.getScale_y());
		properties.put(EnDXFInsert.SCALE_Z.getDxfProperty(), style.getScale_z());
		properties.put(EnDXFInsert.ROTATE.getDxfProperty(), style.getRotate());
		properties.put(EnDXFInsert.ROWS.getDxfProperty(), style.getRows());
		properties.put(EnDXFInsert.COLUMNS.getDxfProperty(), style.getColumns());
		properties.put(EnDXFInsert.ROW_SPACING.getDxfProperty(), style.getRow_spacing());
		properties.put(EnDXFInsert.COLUMN_SPACING.getDxfProperty(), style.getColumn_spacing());
		properties.put(EnDXFInsert.BLOCKID.getDxfProperty(), style.getBlockID());

		return properties;
	}

	public static Hashtable<String, Object> getInsertColumns() {

		Hashtable<String, Object> columns = new Hashtable<String, Object>();
		columns.putAll(EnDXFCommon.getCommonColumns());
		columns.put(EnDXFInsert.SCALE_X.getDxfProperty(), EnDXFInsert.SCALE_X.getType());
		columns.put(EnDXFInsert.SCALE_Y.getDxfProperty(), EnDXFInsert.SCALE_Y.getType());
		columns.put(EnDXFInsert.SCALE_Z.getDxfProperty(), EnDXFInsert.SCALE_Z.getType());
		columns.put(EnDXFInsert.ROTATE.getDxfProperty(), EnDXFInsert.ROTATE.getType());
		columns.put(EnDXFInsert.ROWS.getDxfProperty(), EnDXFInsert.ROWS.getType());
		columns.put(EnDXFInsert.COLUMNS.getDxfProperty(), EnDXFInsert.COLUMNS.getType());
		columns.put(EnDXFInsert.ROW_SPACING.getDxfProperty(), EnDXFInsert.ROW_SPACING.getType());
		columns.put(EnDXFInsert.COLUMN_SPACING.getDxfProperty(), EnDXFInsert.COLUMN_SPACING.getType());
		columns.put(EnDXFInsert.BLOCKID.getDxfProperty(), EnDXFInsert.BLOCKID.getType());
		return columns;

	}
}
