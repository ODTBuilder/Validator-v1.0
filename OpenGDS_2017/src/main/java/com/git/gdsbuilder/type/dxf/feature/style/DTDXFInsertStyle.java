package com.git.gdsbuilder.type.dxf.feature.style;

import org.apache.commons.lang.StringUtils;

public class DTDXFInsertStyle extends DTDXFStyle {

	private double scale_x = 1.0;
	private double scale_y = 1.0;
	private double scale_z = 1.0;
	private double rotate = 0;
	private int rows = 1;
	private int columns = 1;
	private double row_spacing = 0;
	private double column_spacing = 0;
	private String blockID = StringUtils.EMPTY;

	public DTDXFInsertStyle() {
		super();
	}

	public double getScale_x() {
		return scale_x;
	}

	public void setScale_x(double scale_x) {
		this.scale_x = scale_x;
	}

	public double getScale_y() {
		return scale_y;
	}

	public void setScale_y(double scale_y) {
		this.scale_y = scale_y;
	}

	public double getScale_z() {
		return scale_z;
	}

	public void setScale_z(double scale_z) {
		this.scale_z = scale_z;
	}

	public double getRotate() {
		return rotate;
	}

	public void setRotate(double rotate) {
		this.rotate = rotate;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public double getRow_spacing() {
		return row_spacing;
	}

	public void setRow_spacing(double row_spacing) {
		this.row_spacing = row_spacing;
	}

	public double getColumn_spacing() {
		return column_spacing;
	}

	public void setColumn_spacing(double column_spacing) {
		this.column_spacing = column_spacing;
	}

	public String getBlockID() {
		return blockID;
	}

	public void setBlockID(String blockID) {
		this.blockID = blockID;
	}

}
