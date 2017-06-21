package com.git.gdsbuilder.type.qa10.structure;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QA10Header {

	String collectionName;
	Map<String, Object> defaultValues;

	public QA10Header() {
		super();
		this.collectionName = "";
		this.defaultValues = new LinkedHashMap<String, Object>();
	}

	public QA10Header(String collectionName, Map<String, Object> defaultValues) {
		super();
		this.collectionName = collectionName;
		this.defaultValues = defaultValues;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public Map<String, Object> getDefaultValues() {
		return defaultValues;
	}

	public void setDefaultValues(Map<String, Object> defaultValues) {
		this.defaultValues = defaultValues;
	}

	public void setDefaultHeaderValues() {

		Map<String, Object> headerMap = new LinkedHashMap<String, Object>();

		List<LinkedHashMap<String, Object>> variables = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> acadver = new LinkedHashMap<String, Object>();
		acadver.put("9", "$ACADVER");
		acadver.put("1", "AC1009");
		variables.add(acadver);

		LinkedHashMap<String, Object> insbase = new LinkedHashMap<String, Object>();
		insbase.put("9", "$INSBASE");
		insbase.put("10", "0.0");
		insbase.put("20", "0.0");
		insbase.put("30", "0.0");
		variables.add(insbase);

		LinkedHashMap<String, Object> extmin = new LinkedHashMap<String, Object>();
		extmin.put("9", "$EXTMIN");
		extmin.put("10", "0.0");
		extmin.put("20", "0.0");
		extmin.put("30", "0.0");
		variables.add(extmin);

		LinkedHashMap<String, Object> extmax = new LinkedHashMap<String, Object>();
		extmax.put("9", "$EXTMAX");
		extmax.put("10", "10000000.0");
		extmax.put("20", "10000000.0");
		extmax.put("30", "10000000.0");
		variables.add(extmax);

		// DXFVariable linmin = new DXFVariable("$LIMMIN");
		// linmin.setValue("9", "$LIMMIN");
		// linmin.setValue("10", "0.0");
		// linmin.setValue("20", "0.0");
		// variables.add(linmin);

		// DXFVariable linmax = new DXFVariable("$LIMMAX");
		// linmax.setValue("9", "$LIMMAX");
		// linmax.setValue("10", "10000000.0");
		// linmax.setValue("20", "10000000.0");
		// variables.add(linmax);

		this.defaultValues = headerMap;
	}
}
