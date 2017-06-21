package com.git.gdsbuilder.type.qa10.feature;

import com.vividsolutions.jts.geom.Geometry;

public class QA10Feature {

	String featureID;
	String featureType;
	Geometry geom;
	String textValue;

	public QA10Feature(String featureID) {
		super();
		this.featureID = featureID;
		this.featureType = "";
		this.textValue = "";
	}

	public QA10Feature(String featureID, String featureType, Geometry geom) {
		super();
		this.featureID = featureID;
		this.featureType = featureType;
		this.geom = geom;
		this.textValue = "";
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

}
