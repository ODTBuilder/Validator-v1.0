package com.git.gdsbuilder.type.qa10.feature;

import com.vividsolutions.jts.geom.Geometry;

public class QA10Feature {

	String featureID;
	String featureType;
	Geometry geom;
	String textValue;
	double elevation;
	double rotate;
	double width;
	double height;

	public QA10Feature() {
		super();
		this.featureID = "";
		this.featureType = "";
		this.textValue = "";
		this.elevation = 0;
		this.rotate = 0;
		this.width = 0;
		this.height = 0;
	}

	public QA10Feature(String featureID) {
		super();
		this.featureID = featureID;
		this.featureType = "";
		this.textValue = "";
		this.elevation = 0;
		this.rotate = 0;
	}

	public QA10Feature(String featureID, String featureType, Geometry geom) {
		super();
		this.featureID = featureID;
		this.featureType = featureType;
		this.geom = geom;
		this.textValue = "";
		this.elevation = 0;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getElevation() {
		return elevation;
	}

	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

	public String getFeatureID() {
		return featureID;
	}

	public void setFeatureID(String featureID) {
		this.featureID = featureID;
	}

	public String getFeatureType() {
		return featureType;
	}

	public void setFeatureType(String featureType) {
		this.featureType = featureType;
	}

	public Geometry getGeom() {
		return geom;
	}

	public void setGeom(Geometry geom) {
		this.geom = geom;
	}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}

	public double getRotate() {
		return rotate;
	}

	public void setRotate(double rotate) {
		this.rotate = rotate;
	}

	/**
	 *
	 * @author JY.Kim
	 * @Date 2017. 8. 10. 오후 4:47:45
	 * @param flag void
	 * @throws
	 * */
	public void setFlag(int flag) {
		// TODO Auto-generated method stub
		
	}

}
