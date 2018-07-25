package com.git.gdsbuilder.edit.shp;

import java.util.HashMap;
import java.util.Map;

import com.git.gdsbuilder.type.shp.layer.DTSHPLayer;
import com.git.gdsbuilder.type.shp.layer.DTSHPLayerList;

public class EditSHPLayerCollection {

	String collectionName;
	DTSHPLayerList createLayerList;
	DTSHPLayerList modifiedLayerList;
	DTSHPLayerList deletedLayerList;
	Map<String, Object> geoLayerList;

	boolean isCreated = false;
	boolean isModified = false;
	boolean isDeleted = false;
	boolean isDeleteAll = false;

	public EditSHPLayerCollection() {
		this.collectionName = "";
		this.createLayerList = new DTSHPLayerList();
		this.modifiedLayerList = new DTSHPLayerList();
		this.deletedLayerList = new DTSHPLayerList();
		this.geoLayerList = new HashMap<String, Object>();
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public DTSHPLayerList getCreateLayerList() {
		return createLayerList;
	}

	public void setCreateLayerList(DTSHPLayerList createLayerList) {
		this.createLayerList = createLayerList;
	}

	public DTSHPLayerList getModifiedLayerList() {
		return modifiedLayerList;
	}

	public void setModifiedLayerList(DTSHPLayerList modifiedLayerList) {
		this.modifiedLayerList = modifiedLayerList;
	}

	public DTSHPLayerList getDeletedLayerList() {
		return deletedLayerList;
	}

	public void setDeletedLayerList(DTSHPLayerList deletedLayerList) {
		this.deletedLayerList = deletedLayerList;
	}

	public Map<String, Object> getGeoLayerList() {
		return geoLayerList;
	}

	public void setGeoLayerList(Map<String, Object> geoLayerList) {
		this.geoLayerList = geoLayerList;
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

	public boolean isDeleteAll() {
		return isDeleteAll;
	}

	public void setDeleteAll(boolean isDeleteAll) {
		this.isDeleteAll = isDeleteAll;
	}

	public void addCreateLayer(DTSHPLayer qa10Layer) {
		this.createLayerList.add(qa10Layer);
	}

	public void addAllCreateLayer(DTSHPLayerList createLayerList) {
		this.createLayerList.addAll(createLayerList);
	}

	public void addmodifiedLayer(DTSHPLayer qa10Layer) {
		this.modifiedLayerList.add(qa10Layer);
	}

	public void addAllmodifiedLayer(DTSHPLayerList modifiedLayerList) {
		this.modifiedLayerList.addAll(modifiedLayerList);
	}

	public void addDeleteLayer(DTSHPLayer qa10Layer) {
		this.deletedLayerList.add(qa10Layer);
	}

	public void addAllDeleteLayer(DTSHPLayerList deletedLayerList) {
		this.deletedLayerList.addAll(deletedLayerList);
	}

}
