package com.git.gdsbuilder.type.qa10.structure;

import java.util.ArrayList;
import java.util.List;

import org.kabeja.dxf.DXFVariable;

public class QA10Header {

	String collectionName;
	List<DXFVariable> values;

	public QA10Header() {
		super();
		this.collectionName = "";
		this.values = new ArrayList<DXFVariable>();
	}

	public QA10Header(String collectionName, List<DXFVariable> values) {
		super();
		this.collectionName = collectionName;
		this.values = values;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public List<DXFVariable> getValues() {
		return values;
	}

	public void setValues(List<DXFVariable> values) {
		this.values = values;
	}

}
