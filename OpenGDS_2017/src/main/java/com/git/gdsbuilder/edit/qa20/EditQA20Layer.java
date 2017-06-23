package com.git.gdsbuilder.edit.qa20;

import java.util.Map;

import com.git.gdsbuilder.type.qa20.feature.QA20FeatureList;
import com.git.gdsbuilder.type.qa20.header.NDAHeader;
import com.git.gdsbuilder.type.qa20.header.NGIHeader;

public class EditQA20Layer {

	String layerName;
	String orignName;
	String layerType;
	NGIHeader ngiHeader;
	NDAHeader ndaHeader;
	QA20FeatureList createFeatureList;
	QA20FeatureList modifiedFeatureList;
	QA20FeatureList deletedFeatureList;

	Map<String, Object> geoServerLayer;

	boolean isCreated = false;
	boolean isModified = false;
	boolean isDeleted = false;

	public EditQA20Layer() {

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

	public QA20FeatureList getCreateFeatureList() {
		return createFeatureList;
	}

	public void setCreateFeatureList(QA20FeatureList createFeatureList) {
		this.createFeatureList = createFeatureList;
	}

	public QA20FeatureList getModifiedFeatureList() {
		return modifiedFeatureList;
	}

	public void setModifiedFeatureList(QA20FeatureList modifiedFeatureList) {
		this.modifiedFeatureList = modifiedFeatureList;
	}

	public QA20FeatureList getDeletedFeatureList() {
		return deletedFeatureList;
	}

	public void setDeletedFeatureList(QA20FeatureList deletedFeatureList) {
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
