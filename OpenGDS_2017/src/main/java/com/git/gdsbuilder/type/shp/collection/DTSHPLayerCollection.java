package com.git.gdsbuilder.type.shp.collection;

import com.git.gdsbuilder.type.shp.layer.DTSHPLayerList;

public class DTSHPLayerCollection {

	String collectionName;
	DTSHPLayerList shpLayerList;

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public DTSHPLayerList getShpLayerList() {
		return shpLayerList;
	}

	public void setShpLayerList(DTSHPLayerList shpLayerList) {
		this.shpLayerList = shpLayerList;
	}

}
