package com.git.gdsbuilder.type.dxf.structure;

import java.util.ArrayList;
import java.util.List;

import org.kabeja.dxf.objects.DXFDictionary;

public class DTDXFObjects {

	String collectionName;
	List<DXFDictionary> objects;

	public DTDXFObjects() {
		super();
		this.collectionName = "";
		this.objects = new ArrayList<DXFDictionary>();
	}

	public DTDXFObjects(String collectionName, List<DXFDictionary> objects) {
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
