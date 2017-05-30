package com.git.gdsbuilder.type.qa10.feature.style;

public class DTDXFPolylineStyle extends DTDXFStyle {

	protected static final double QUARTER_CIRCLE_ANGLE = Math.tan(0.39269908169872414D);
	protected double startWidth = 0.0;
	protected double endWidth = 0.0;
	protected int surefaceType = 0;
	protected int surefaceDensityRows = 0;
	protected int surefaceDensityColumns = 0;
	protected int rows = 0;
	protected int columns = 0;

	public DTDXFPolylineStyle() {
		// super();
	}

	public double getStartWidth() {
		return startWidth;
	}

	public void setStartWidth(double startWidth) {
		this.startWidth = startWidth;
	}

	public double getEndWidth() {
		return endWidth;
	}

	public void setEndWidth(double endWidth) {
		this.endWidth = endWidth;
	}

	public int getSurefaceType() {
		return surefaceType;
	}

	public void setSurefaceType(int surefaceType) {
		this.surefaceType = surefaceType;
	}

	public int getSurefaceDensityRows() {
		return surefaceDensityRows;
	}

	public void setSurefaceDensityRows(int surefaceDensityRows) {
		this.surefaceDensityRows = surefaceDensityRows;
	}

	public int getSurefaceDensityColumns() {
		return surefaceDensityColumns;
	}

	public void setSurefaceDensityColumns(int surefaceDensityColumns) {
		this.surefaceDensityColumns = surefaceDensityColumns;
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

	public static double getQuarterCircleAngle() {
		return QUARTER_CIRCLE_ANGLE;
	}
}
