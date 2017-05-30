package com.git.gdsbuilder.type.simple.layer;

import java.util.HashMap;

import com.git.gdsbuilder.type.simple.feature.FeatureList;

public class Layer {

	String type;
	String layerName;
	String layerType;
	HashMap<String, Object> layerColumns;
	FeatureList featureList;

	public Layer(String type, String layerName, String layerType, HashMap<String, Object> layerColumns,
			FeatureList featureList) {
		super();
		this.type = type;
		this.layerName = layerName;
		this.layerType = layerType;
		this.layerColumns = layerColumns;
		this.featureList = featureList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	public String getLayerType() {
		return layerType;
	}

	public void setLayerType(String layerType) {
		this.layerType = layerType;
	}

	public HashMap<String, Object> getLayerColumns() {
		return layerColumns;
	}

	public void setLayerColumns(HashMap<String, Object> layerColumns) {
		this.layerColumns = layerColumns;
	}

	public FeatureList getFeatureList() {
		return featureList;
	}

	public void setFeatureList(FeatureList featureList) {
		this.featureList = featureList;
	}

}
