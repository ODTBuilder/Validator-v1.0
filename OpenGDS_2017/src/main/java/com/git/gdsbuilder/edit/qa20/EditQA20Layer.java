package com.git.gdsbuilder.edit.qa20;

import com.git.gdsbuilder.type.qa20.feature.QA20Feature;
import com.git.gdsbuilder.type.qa20.feature.QA20FeatureList;

public class EditQA20Layer {

	String layerName;
	QA20FeatureList createFeatureList;
	QA20FeatureList modifiedFeatureList;
	QA20FeatureList deletedFeatureList;

	boolean isCreated = false;
	boolean isModified = false;
	boolean isDeleted = false;

	public EditQA20Layer(String layerName) {
		this.layerName = layerName;
		createFeatureList = new QA20FeatureList();
		modifiedFeatureList = new QA20FeatureList();
		deletedFeatureList = new QA20FeatureList();
	}

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
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

	public void addCreateFeature(QA20Feature qa20Feature) {
		this.createFeatureList.add(qa20Feature);
	}

	public void addAllCreateFeature(QA20FeatureList createFeatureList) {
		this.createFeatureList.addAll(createFeatureList);
	}

	public void addmodifiedFeature(QA20Feature qa20Feature) {
		this.modifiedFeatureList.add(qa20Feature);
	}

	public void addAllmodifiedFeature(QA20FeatureList modifiedFeatureList) {
		this.modifiedFeatureList.addAll(modifiedFeatureList);
	}

	public void addDeleteFeature(QA20Feature qa20Feature) {
		this.deletedFeatureList.add(qa20Feature);
	}

	public void addAllDeleteFeature(QA20FeatureList deletedFeatureList) {
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
