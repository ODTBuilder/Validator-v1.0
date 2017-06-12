package com.git.gdsbuilder.type.qa10.structure;

import java.util.ArrayList;
import java.util.List;

import org.kabeja.dxf.objects.DXFDictionary;

public class QA10Objects {

	String collectionName;
	List<DXFDictionary> objects;

	public QA10Objects() {
		super();
		this.collectionName = "";
		this.objects = new ArrayList<DXFDictionary>();
	}

	public QA10Objects(String collectionName, List<DXFDictionary> objects) {
		super();
		this.collectionName = collectionName;
		this.objects = objects;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public List<DXFDictionary> getObjects() {
		return objects;
	}

	public void setObjects(List<DXFDictionary> objects) {
		this.objects = objects;
	}

}
