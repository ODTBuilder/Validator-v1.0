package com.git.gdsbuilder.edit.ngi;

import java.util.Map;

import com.git.gdsbuilder.type.ngi.feature.DTNGIFeatureList;
import com.git.gdsbuilder.type.ngi.header.NDAHeader;
import com.git.gdsbuilder.type.ngi.header.NGIHeader;

public class EditNGILayer {

	String layerName;
	String orignName;
	String layerType;
	NGIHeader ngiHeader;
	NDAHeader ndaHeader;
	DTNGIFeatureList createFeatureList;
	DTNGIFeatureList modifiedFeatureList;
	DTNGIFeatureList deletedFeatureList;

	Map<String, Object> geoServerLayer;

	boolean isCreated = false;
	boolean isModified = false;
	boolean isDeleted = false;

	public EditNGILayer() {

	}

	public Map<String, Object> getGeoServerLayer() {
		return geoServerLayer;
	}

	public void setGeoServerLayer(Map<String, Object> geoServerLayer) {
		this.geoServerLayer = geoServerLayer;
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

	public String getOrignName() {
		return orignName;
	}

	public void setOrignName(String orignName) {
		this.orignName = orignName;
	}

	public NGIHeader getNgiHeader() {
		return ngiHeader;
	}

	public void setNgiHeader(NGIHeader ngiHeader) {
		this.ngiHeader = ngiHeader;
	}

	public NDAHeader getNdaHeader() {
		return ndaHeader;
	}

	public void setNdaHeader(NDAHeader ndaHeader) {
		this.ndaHeader = ndaHeader;
	}

	public DTNGIFeatureList getCreateFeatureList() {
		return createFeatureList;
	}

	public void setCreateFeatureList(DTNGIFeatureList createFeatureList) {
		this.createFeatureList = createFeatureList;
	}

	public DTNGIFeatureList getModifiedFeatureList() {
		return modifiedFeatureList;
	}

	public void setModifiedFeatureList(DTNGIFeatureList modifiedFeatureList) {
		this.modifiedFeatureList = modifiedFeatureList;
	}

	public DTNGIFeatureList getDeletedFeatureList() {
		return deletedFeatureList;
	}

	public void setDeletedFeatureList(DTNGIFeatureList deletedFeatureList) {
		this.deletedFeatureList = deletedFeatureList;
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
