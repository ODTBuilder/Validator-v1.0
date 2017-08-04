package com.git.gdsbuilder.type.dxf.feature.style;

import org.apache.commons.lang.StringUtils;

public class DTDXFStyle {

	String layerID = StringUtils.EMPTY;
	String featureID = StringUtils.EMPTY;

	Integer visibile = 0;
	String lineType;

	Integer flags = 0;
	boolean block = false;
	double linetypeScaleFactor = 1.0;

	Integer color = 0;

	byte[] colorRGB = new byte[0];
	Integer lineWeight = 0;
	double transparency = 0.0;
	double thickness = 0.0;

	public DTDXFStyle() {
		super();
	}

	public String getLayerID() {
		return layerID;
	}

	public void setLayerID(String layerID) {
		this.layerID = layerID;
	}

	public String getFeatureID() {
		return featureID;
	}

	public void setFeatureID(String featureID) {
		this.featureID = featureID;
	}

	public Integer isVisibile() {
		return visibile;
	}

	public void setVisibile(Integer visibile) {
		this.visibile = visibile;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public boolean isBlock() {
		return block;
	}

	public void setBlock(boolean block) {
		this.block = block;
	}

	public double getLinetypeScaleFactor() {
		return linetypeScaleFactor;
	}

	public void setLinetypeScaleFactor(double linetypeScaleFactor) {
		this.linetypeScaleFactor = linetypeScaleFactor;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public byte[] getColorRGB() {
		return colorRGB;
	}

	public void setColorRGB(byte[] colorRGB) {
		this.colorRGB = colorRGB;
	}

	public int getLineWeight() {
		return lineWeight;
	}

	public void setLineWeight(int lineWeight) {
		this.lineWeight = lineWeight;
	}

	public double getTransparency() {
		return transparency;
	}

	public void setTransparency(double transparency) {
		this.transparency = transparency;
	}

	public double getThickness() {
		return thickness;
	}

	public void setThickness(double thickness) {
		this.thickness = thickness;
	}

}
