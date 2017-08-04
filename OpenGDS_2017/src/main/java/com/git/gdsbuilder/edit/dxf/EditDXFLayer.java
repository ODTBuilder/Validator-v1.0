package com.git.gdsbuilder.edit.dxf;

import java.util.Map;

import com.git.gdsbuilder.type.dxf.feature.DTDXFFeature;
import com.git.gdsbuilder.type.dxf.feature.DTDXFFeatureList;

public class EditDXFLayer {

	String layerName;
	String orignName;
	String layerType;
	String originLayerType;
	DTDXFFeatureList createFeatureList;
	DTDXFFeatureList modifiedFeatureList;
	DTDXFFeatureList deletedFeatureList;

	Map<String, Object> geoServerLayer;

	boolean isCreated = false;
	boolean isModified = false;
	boolean isDeleted = false;

	public String getOriginLayerType() {
		return originLayerType;
	}

	public void setOriginLayerType(String originLayerType) {
		this.originLayerType = originLayerType;
	}

	public Map<String, Object> getGeoServerLayer() {
		return geoServerLayer;
	}

	public void setGeoServerLayer(Map<String, Object> geoServerLayer) {
		this.geoServerLayer = geoServerLayer;
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

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	public DTDXFFeatureList getCreateFeatureList() {
		return createFeatureList;
	}

	public void setCreateFeatureList(DTDXFFeatureList createFeatureList) {
		this.createFeatureList = createFeatureList;
	}

	public DTDXFFeatureList getModifiedFeatureList() {
		return modifiedFeatureList;
	}

	public void setModifiedFeatureList(DTDXFFeatureList modifiedFeatureList) {
		this.modifiedFeatureList = modifiedFeatureList;
	}

	public DTDXFFeatureList getDeletedFeatureList() {
		return deletedFeatureList;
	}

	public void setDeletedFeatureList(DTDXFFeatureList deletedFeatureList) {
		this.deletedFeatureList = deletedFeatureList;
	}

	public void addCreateFeature(DTDXFFeature qa20Feature) {
		this.createFeatureList.add(qa20Feature);
	}

	public void addAllCreateFeature(DTDXFFeatureList createFeatureList) {
		this.createFeatureList.addAll(createFeatureList);
	}

	public void addmodifiedFeature(DTDXFFeature qa20Feature) {
		this.modifiedFeatureList.add(qa20Feature);
	}

	public void addAllmodifiedFeature(DTDXFFeatureList modifiedFeatureList) {
		this.modifiedFeatureList.addAll(modifiedFeatureList);
	}

	public void addDeleteFeature(DTDXFFeature qa20Feature) {
		this.deletedFeatureList.add(qa20Feature);
	}

	public void addAllDeleteFeature(DTDXFFeatureList deletedFeatureList) {
		this.deletedFeatureList.addAll(deletedFeatureList);
	}

	public boolean isCreated() {
		return isCreated;
	}

	public void setCreated(boolean isCreated) {
		this.isCreated = isCreated;
	}

	public boolean isModified() {
		return isModified;
	}

	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
