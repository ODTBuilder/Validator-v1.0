package com.git.gdsbuilder.edit.qa10;

import java.util.HashMap;
import java.util.Map;

import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa10.layer.QA10LayerList;

public class EditQA10Collection {

	String collectionName;
	QA10LayerList createLayerList;
	QA10LayerList modifiedLayerList;
	QA10LayerList deletedLayerList;
	Map<String, Object> geoLayerList;

	boolean isCreated = false;
	boolean isModified = false;
	boolean isDeleted = false;

	boolean isDeleteAll = false;

	public EditQA10Collection() {
		this.collectionName = "";
		this.createLayerList = new QA10LayerList();
		this.modifiedLayerList = new QA10LayerList();
		this.deletedLayerList = new QA10LayerList();
		this.geoLayerList = new HashMap<String, Object>();
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

	public QA10LayerList getCreateLayerList() {
		return createLayerList;
	}

	public void setCreateLayerList(QA10LayerList createLayerList) {
		this.createLayerList = createLayerList;
	}

	public QA10LayerList getModifiedLayerList() {
		return modifiedLayerList;
	}

	public void setModifiedLayerList(QA10LayerList modifiedLayerList) {
		this.modifiedLayerList = modifiedLayerList;
	}

	public QA10LayerList getDeletedLayerList() {
		return deletedLayerList;
	}

	public void setDeletedLayerList(QA10LayerList deletedLayerList) {
		this.deletedLayerList = deletedLayerList;
	}

	public void addCreateLayer(QA10Layer qa10Layer) {
		this.createLayerList.add(qa10Layer);
	}

	public void addAllCreateLayer(QA10LayerList createLayerList) {
		this.createLayerList.addAll(createLayerList);
	}

	public void addmodifiedLayer(QA10Layer qa10Layer) {
		this.modifiedLayerList.add(qa10Layer);
	}

	public void addAllmodifiedLayer(QA10LayerList modifiedLayerList) {
		this.modifiedLayerList.addAll(modifiedLayerList);
	}

	public void addDeleteLayer(QA10Layer qa10Layer) {
		this.deletedLayerList.add(qa10Layer);
	}

	public void addAllDeleteLayer(QA10LayerList deletedLayerList) {
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

	public void putGeoLayer(Map<String, Object> geoLayerLayer) {
		geoLayerList.putAll(geoLayerLayer);
	}

	public Map<String, Object> getGeoLayerList() {
		return geoLayerList;
	}

	public void setGeoLayerList(Map<String, Object> geoLayerList) {
		this.geoLayerList = geoLayerList;
	}

}
