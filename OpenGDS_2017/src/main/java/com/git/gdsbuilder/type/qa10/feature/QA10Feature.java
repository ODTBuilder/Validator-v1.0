package com.git.gdsbuilder.type.qa10.feature;

import java.util.Hashtable;

import com.vividsolutions.jts.geom.Geometry;

public class QA10Feature {

	String featureID;
	String featureType;
	String layerID;
	Geometry geom;
	Hashtable<String, Object> properties;

	public QA10Feature(String featureID) {
		super();
		this.featureID = featureID;
		this.featureType = "";
		this.layerID = "";
		this.properties = new Hashtable<String, Object>();
	}

	public QA10Feature(String featureID, String featureType, String layerID, Geometry geom,
			Hashtable<String, Object> properties) {
		super();
		this.properties = properties;
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

	public String getLayerID() {
		return layerID;
	}

	public void setLayerID(String layerID) {
		this.layerID = layerID;
	}

	public Geometry getGeom() {
		return geom;
	}

	public void setGeom(Geometry geom) {
		this.geom = geom;
	}

	public Hashtable<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Hashtable<String, Object> properties) {
		this.properties = properties;
	}

	public void putAllProperties(Hashtable<String, Object> properties) {
		this.properties.putAll(properties);
	}

	public void putProperty(String key, Object value) {
		this.properties.put(key, value);
	}

}
