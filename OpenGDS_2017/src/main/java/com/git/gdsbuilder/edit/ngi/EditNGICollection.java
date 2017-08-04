package com.git.gdsbuilder.edit.ngi;

import java.util.HashMap;
import java.util.Map;

import com.git.gdsbuilder.type.ngi.layer.DTNGILayer;
import com.git.gdsbuilder.type.ngi.layer.DTNGILayerList;

public class EditNGICollection {

	String collectionName;
	DTNGILayerList createLayerList;
	DTNGILayerList modifiedLayerList;
	DTNGILayerList deletedLayerList;
	Map<String, Object> geoLayerList;

	boolean isCreated = false;
	boolean isModified = false;
	boolean isDeleted = false;

	boolean isDeleteAll = false;

	public EditNGICollection() {
		this.collectionName = "";
		this.createLayerList = new DTNGILayerList();
		this.modifiedLayerList = new DTNGILayerList();
		this.deletedLayerList = new DTNGILayerList();
		this.geoLayerList = new HashMap<String, Object>();
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

	public DTNGILayerList getCreateLayerList() {
		return createLayerList;
	}

	public void setCreateLayerList(DTNGILayerList createLayerList) {
		this.createLayerList = createLayerList;
	}

	public DTNGILayerList getModifiedLayerList() {
		return modifiedLayerList;
	}

	public void setModifiedLayerList(DTNGILayerList modifiedLayerList) {
		this.modifiedLayerList = modifiedLayerList;
	}

	public DTNGILayerList getDeletedLayerList() {
		return deletedLayerList;
	}

	public void setDeletedLayerList(DTNGILayerList deletedLayerList) {
		this.deletedLayerList = deletedLayerList;
	}

	public void addCreateLayer(DTNGILayer qa20Layer) {
		this.createLayerList.add(qa20Layer);
	}

	public void addAllCreateLayer(DTNGILayerList createLayerList) {
		this.createLayerList.addAll(createLayerList);
	}

	public void addmodifiedLayer(DTNGILayer qa20Layer) {
		this.modifiedLayerList.add(qa20Layer);
	}

	public void addAllmodifiedLayer(DTNGILayerList modifiedLayerList) {
		this.modifiedLayerList.addAll(modifiedLayerList);
	}

	public void addDeleteLayer(DTNGILayer qa20Layer) {
		this.deletedLayerList.add(qa20Layer);
	}

	public void addAllDeleteLayer(DTNGILayerList deletedLayerList) {
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
