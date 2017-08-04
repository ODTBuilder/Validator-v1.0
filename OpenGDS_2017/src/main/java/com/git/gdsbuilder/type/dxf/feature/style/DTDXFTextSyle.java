package com.git.gdsbuilder.type.dxf.feature.style;

public class DTDXFTextSyle extends DTDXFStyle {

	String text;
	double textHeight = 0.0; // 40
	double textRotation = 0.0; // 50
	double xScale = 1.0; // 41
	double angle = 0.0; // 51
	String textStyle = "STANDARD"; // 7
	int flag = 0; // 71
	int horizontalType = 0; // 72
	int verticalType = 0; // 73

	public DTDXFTextSyle() {
		super();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getTextHeight() {
		return textHeight;
	}

	public void setTextHeight(double textHeight) {
		this.textHeight = textHeight;
	}

	public double getTextRotation() {
		return textRotation;
	}

	public void setTextRotation(double textRotation) {
		this.textRotation = textRotation;
	}

	public double getxScale() {
		return xScale;
	}

	public void setxScale(double xScale) {
		this.xScale = xScale;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public String getTextStyle() {
		return textStyle;
	}

	public void setTextStyle(String textStyle) {
		this.textStyle = textStyle;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getHorizontalType() {
		return horizontalType;
	}

	public void setHorizontalType(int horizontalType) {
		this.horizontalType = horizontalType;
	}

	public int getVerticalType() {
		return verticalType;
	}

	public void setVerticalType(int verticalType) {
		this.verticalType = verticalType;
	}

}
