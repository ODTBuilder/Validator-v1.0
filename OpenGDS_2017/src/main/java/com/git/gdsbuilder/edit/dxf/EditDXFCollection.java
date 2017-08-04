package com.git.gdsbuilder.edit.dxf;

import java.util.HashMap;
import java.util.Map;

import com.git.gdsbuilder.type.dxf.layer.DTDXFLayer;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayerList;

public class EditDXFCollection {

	String collectionName;
	DTDXFLayerList createLayerList;
	DTDXFLayerList modifiedLayerList;
	DTDXFLayerList deletedLayerList;
	Map<String, Object> geoLayerList;

	boolean isCreated = false;
	boolean isModified = false;
	boolean isDeleted = false;

	boolean isDeleteAll = false;

	public EditDXFCollection() {
		this.collectionName = "";
		this.createLayerList = new DTDXFLayerList();
		this.modifiedLayerList = new DTDXFLayerList();
		this.deletedLayerList = new DTDXFLayerList();
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

	public DTDXFLayerList getCreateLayerList() {
		return createLayerList;
	}

	public void setCreateLayerList(DTDXFLayerList createLayerList) {
		this.createLayerList = createLayerList;
	}

	public DTDXFLayerList getModifiedLayerList() {
		return modifiedLayerList;
	}

	public void setModifiedLayerList(DTDXFLayerList modifiedLayerList) {
		this.modifiedLayerList = modifiedLayerList;
	}

	public DTDXFLayerList getDeletedLayerList() {
		return deletedLayerList;
	}

	public void setDeletedLayerList(DTDXFLayerList deletedLayerList) {
		this.deletedLayerList = deletedLayerList;
	}

	public void addCreateLayer(DTDXFLayer qa10Layer) {
		this.createLayerList.add(qa10Layer);
	}

	public void addAllCreateLayer(DTDXFLayerList createLayerList) {
		this.createLayerList.addAll(createLayerList);
	}

	public void addmodifiedLayer(DTDXFLayer qa10Layer) {
		this.modifiedLayerList.add(qa10Layer);
	}

	public void addAllmodifiedLayer(DTDXFLayerList modifiedLayerList) {
		this.modifiedLayerList.addAll(modifiedLayerList);
	}

	public void addDeleteLayer(DTDXFLayer qa10Layer) {
		this.deletedLayerList.add(qa10Layer);
	}

	public void addAllDeleteLayer(DTDXFLayerList deletedLayerList) {
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
