package com.git.gdsbuilder.type.shp.layer;

import org.geotools.data.simple.SimpleFeatureCollection;

public class DTSHPLayer {

	String layerName;
	String layerType;
	SimpleFeatureCollection simpleFeatureCollection;

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerId) {
		this.layerName = layerId;
	}

	public String getLayerType() {
		return layerType;
	}

	public void setLayerType(String layerType) {
		this.layerType = layerType;
	}

	public SimpleFeatureCollection getSimpleFeatureCollection() {
		return simpleFeatureCollection;
	}

	public void setSimpleFeatureCollection(SimpleFeatureCollection simpleFeatureCollection) {
		this.simpleFeatureCollection = simpleFeatureCollection;
	}

}
