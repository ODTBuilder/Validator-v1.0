package com.git.gdsbuilder.type.dxf.layer;

import java.util.Hashtable;

import com.git.gdsbuilder.type.dxf.feature.DTDXFFeature;
import com.git.gdsbuilder.type.dxf.feature.DTDXFFeatureList;

public class DTDXFLayer {

	String layerID;
	String originLayerID;
	String layerType;
	String originLayerType;
	Hashtable<String, Object> layerColumns;
	DTDXFFeatureList qa10FeatureList;

	public DTDXFLayer() {
		this.layerID = "";
		this.layerType = "";
		this.originLayerType = "";
		this.layerColumns = new Hashtable<String, Object>();
		this.qa10FeatureList = new DTDXFFeatureList();
	}

	public DTDXFLayer(String layerID) {
		this.layerID = layerID;
		this.layerType = "";
		this.originLayerType = "";
		this.layerColumns = new Hashtable<String, Object>();
		this.qa10FeatureList = new DTDXFFeatureList();
	}

	public DTDXFLayer(String layerID, String originLayerID, String layerType, String originLayerType) {
		this.layerID = layerID;
		this.originLayerID = originLayerID;
		this.layerType = layerType;
		this.originLayerType = originLayerType;
	}

	public DTDXFLayer(String layerID, String originLayerID) {
		this.layerID = layerID;
		this.originLayerID = originLayerID;
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

	public DTDXFFeatureList getQa10FeatureList() {
		return qa10FeatureList;
	}

	public void setQa10FeatureList(DTDXFFeatureList qa10FeatureList) {
		this.qa10FeatureList = qa10FeatureList;
	}

	public String getOriginLayerType() {
		return originLayerType;
	}

	public void setOriginLayerType(String originLayerType) {
		this.originLayerType = originLayerType;
	}

	public void addQA10Feature(DTDXFFeature feature) {
		this.qa10FeatureList.add(feature);
	}

	public String getOriginLayerID() {
		return originLayerID;
	}

	public void setOriginLayerID(String originLayerID) {
		this.originLayerID = originLayerID;
	}

}
