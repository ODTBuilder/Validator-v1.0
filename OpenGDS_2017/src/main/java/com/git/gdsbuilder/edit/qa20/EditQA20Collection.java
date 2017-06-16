package com.git.gdsbuilder.edit.qa20;

import com.git.gdsbuilder.type.qa20.layer.QA20Layer;
import com.git.gdsbuilder.type.qa20.layer.QA20LayerList;

public class EditQA20Collection {

	String collectionName;
	QA20LayerList createLayerList;
	QA20LayerList modifiedLayerList;
	QA20LayerList deletedLayerList;

	boolean isCreated = false;
	boolean isModified = false;
	boolean isDeleted = false;

	boolean isDeleteAll = false;

	public EditQA20Collection() {
		this.collectionName = "";
		this.createLayerList = new QA20LayerList();
		this.modifiedLayerList = new QA20LayerList();
		this.deletedLayerList = new QA20LayerList();
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public boolean isDeleteAll() {
		return isDeleteAll;
	}

	public void setDeleteAll(boolean isDeleteAll) {
		this.isDeleteAll = isDeleteAll;
	}

	public QA20LayerList getCreateLayerList() {
		return createLayerList;
	}

	public void setCreateLayerList(QA20LayerList createLayerList) {
		this.createLayerList = createLayerList;
	}

	public QA20LayerList getModifiedLayerList() {
		return modifiedLayerList;
	}

	public void setModifiedLayerList(QA20LayerList modifiedLayerList) {
		this.modifiedLayerList = modifiedLayerList;
	}

	public QA20LayerList getDeletedLayerList() {
		return deletedLayerList;
	}

	public void setDeletedLayerList(QA20LayerList deletedLayerList) {
		this.deletedLayerList = deletedLayerList;
	}

	public void addCreateLayer(QA20Layer qa20Layer) {
		this.createLayerList.add(qa20Layer);
	}

	public void addAllCreateLayer(QA20LayerList createLayerList) {
		this.createLayerList.addAll(createLayerList);
	}

	public void addmodifiedLayer(QA20Layer qa20Layer) {
		this.modifiedLayerList.add(qa20Layer);
	}

	public void addAllmodifiedLayer(QA20LayerList modifiedLayerList) {
		this.modifiedLayerList.addAll(modifiedLayerList);
	}

	public void addDeleteLayer(QA20Layer qa20Layer) {
		this.deletedLayerList.add(qa20Layer);
	}

	public void addAllDeleteLayer(QA20LayerList deletedLayerList) {
		this.deletedLayerList.addAll(deletedLayerList);
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
