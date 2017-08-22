package com.git.gdsbuilder.edit.shp;

import com.git.gdsbuilder.type.shp.feature.DTSHPFeatureList;

public class EditSHPLayer {

	String layerName;
	String orignName;
	String layerType;

	DTSHPFeatureList createFeatureList;
	DTSHPFeatureList modifiedFeatureList;
	DTSHPFeatureList deletedFeatureList;

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	public String getOrignName() {
		return orignName;
	}

	public void setOrignName(String orignName) {
		this.orignName = orignName;
	}

	public String getLayerType() {
		return layerType;
	}

	public void setLayerType(String layerType) {
		this.layerType = layerType;
	}

	public DTSHPFeatureList getCreateFeatureList() {
		return createFeatureList;
	}

	public void setCreateFeatureList(DTSHPFeatureList createFeatureList) {
		this.createFeatureList = createFeatureList;
	}

	public DTSHPFeatureList getModifiedFeatureList() {
		return modifiedFeatureList;
	}

	public void setModifiedFeatureList(DTSHPFeatureList modifiedFeatureList) {
		this.modifiedFeatureList = modifiedFeatureList;
	}

	public DTSHPFeatureList getDeletedFeatureList() {
		return deletedFeatureList;
	}

	public void setDeletedFeatureList(DTSHPFeatureList deletedFeatureList) {
		this.deletedFeatureList = deletedFeatureList;
	}

}
