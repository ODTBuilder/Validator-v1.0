package com.git.gdsbuilder.type.qa10.layer;

import java.util.Hashtable;

import com.git.gdsbuilder.type.qa10.feature.QA10Feature;
import com.git.gdsbuilder.type.qa10.feature.QA10FeatureList;

public class QA10Layer {

	String layerID;
	String layerType;
	Hashtable<String, Object> layerColumns;
	QA10FeatureList qa10FeatureList;

	public QA10Layer() {
		this.layerID = "";
		this.layerType = "";
		this.layerColumns = new Hashtable<String, Object>();
		this.qa10FeatureList = new QA10FeatureList();
	}
	
	public QA10Layer(String layerID) {
		this.layerID = layerID;
		this.layerType = "";
		this.layerColumns = new Hashtable<String, Object>();
		this.qa10FeatureList = new QA10FeatureList();
	}

	public String getLayerID() {
		return layerID;
	}

	public void setLayerID(String layerID) {
		this.layerID = layerID;
	}

	public String getLayerType() {
		return layerType;
	}

	public void setLayerType(String layerType) {
		this.layerType = layerType;
	}

	public Hashtable<String, Object> getLayerColumns() {
		return layerColumns;
	}

	public void setLayerColumns(Hashtable<String, Object> layerColumns) {
		this.layerColumns = layerColumns;
	}

	public QA10FeatureList getQa10FeatureList() {
		return qa10FeatureList;
	}

	public void setQa10FeatureList(QA10FeatureList qa10FeatureList) {
		this.qa10FeatureList = qa10FeatureList;
	}

	public void addQA10Feature(QA10Feature feature) {
		this.qa10FeatureList.add(feature);
	}

}
