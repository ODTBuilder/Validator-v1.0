package com.git.gdsbuilder.type.qa10.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kabeja.dxf.DXFVariable;

public class QA10Header {

	String collectionName;
	DXFVariable header;
	Map<String, Object> defaultValues;

	public QA10Header() {
		super();
		this.collectionName = "";
		initDefaultHeader();
		this.defaultValues = new HashMap<String, Object>();
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

	public DXFVariable getHeader() {
		return header;
	}

	public void setHeader(DXFVariable header) {
		this.header = header;
	}

	public Map<String, Object> getDefaultValues() {
		return defaultValues;
	}

	public void setDefaultValues(Map<String, Object> defaultValues) {
		this.defaultValues = defaultValues;
	}

	private void initDefaultHeader() {
		DXFVariable header = new DXFVariable("header");
		header.setValue("2", "HEADER");
		this.header = header;
	}

	public void setDefaultHeaderValues() {

		Map<String, Object> headerMap = new HashMap<String, Object>();

		List<DXFVariable> variables = new ArrayList<DXFVariable>();
		DXFVariable acadver = new DXFVariable("$ACADVER");
		acadver.setValue("$ACADVER_9", "$ACADVER");
		acadver.setValue("$ACADVER_1", "AC1014");
		variables.add(acadver);

		DXFVariable insbase = new DXFVariable("$INSBASE");
		insbase.setValue("$INSBASE_9", "$INSBASE");
		insbase.setValue("$INSBASE_10", "0.0");
		insbase.setValue("$INSBASE_20", "0.0");
		insbase.setValue("$INSBASE_30", "0.0");
		variables.add(insbase);

		DXFVariable extmin = new DXFVariable("$EXTMIN");
		extmin.setValue("$EXTMIN_9", "$EXTMIN");
		extmin.setValue("$EXTMIN_10", "0.0");
		extmin.setValue("$EXTMIN_20", "0.0");
		variables.add(extmin);

		DXFVariable extmax = new DXFVariable("$EXTMAX");
		extmax.setValue("$EXTMAX_9", "$EXTMAX");
		extmax.setValue("$EXTMAX_10", "10000000.0");
		extmax.setValue("$EXTMAX_20", "10000000.0");
		variables.add(extmax);

		DXFVariable linmin = new DXFVariable("$LIMMIN");
		linmin.setValue("$LIMMIN_9", "$LIMMIN");
		linmin.setValue("$LIMMIN_10", "0.0");
		linmin.setValue("$LIMMIN_20", "0.0");
		variables.add(linmin);

		DXFVariable linmax = new DXFVariable("$LIMMAX");
		linmax.setValue("$LIMMAX_9", "$LIMMAX");
		linmax.setValue("$LIMMAX_10", "10000000.0");
		linmax.setValue("$LIMMAX_20", "10000000.0");
		variables.add(linmax);

		headerMap.put("defualtHeader", variables);
		this.defaultValues = headerMap;
	}
}
