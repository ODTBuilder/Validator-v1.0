package com.git.gdsbuilder.type.qa10.structure;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kabeja.dxf.DXFVariable;

import com.git.gdsbuilder.type.qa10.feature.QA10Feature;
import com.git.gdsbuilder.type.qa10.feature.QA10FeatureList;
import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa10.layer.QA10LayerList;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

public class QA10Entities {

	String collectionName;
	DXFVariable entities;
	Map<String, Object> defaultValues;
	Map<String, Object> values;

	public QA10Entities() {
		super();
		this.collectionName = "";
		this.defaultValues = new LinkedHashMap<String, Object>();
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public DXFVariable getEntities() {
		return entities;
	}

	public void setEntities(DXFVariable entities) {
		this.entities = entities;
	}

	public Map<String, Object> getDefaultValues() {
		return defaultValues;
	}

	public void setDefaultValues(Map<String, Object> defaultValues) {
		this.defaultValues = defaultValues;
	}

	public Map<String, Object> getValues() {
		return values;
	}

	public void setValues(Map<String, Object> values) {
		this.values = values;
	}

	public void setPointValues(QA10LayerList qa10LayerList) {

		Map<String, Object> layerVariableMap = new LinkedHashMap<String, Object>();
		for (int i = 0; i < qa10LayerList.size(); i++) {
			QA10Layer qa10Layer = qa10LayerList.get(i);
			String layerId = qa10Layer.getLayerID();
			List<LinkedHashMap<String, Object>> featureVariables = new ArrayList<LinkedHashMap<String, Object>>();
			QA10FeatureList qa10FeatureList = qa10Layer.getQa10FeatureList();
			for (int j = 0; j < qa10FeatureList.size(); j++) {
				QA10Feature qa10Feature = qa10FeatureList.get(j);
				Geometry point = qa10Feature.getGeom();
				Coordinate coor = point.getCoordinate();
				LinkedHashMap<String, Object> featureVariable = new LinkedHashMap<String, Object>();
				featureVariable.put("0", "POINT");
				featureVariable.put("8", layerId);
				featureVariable.put("10", String.valueOf(coor.x));
				featureVariable.put("20", String.valueOf(coor.y));
				featureVariable.put("30", "0");
				featureVariables.add(featureVariable);
			}
			layerVariableMap.put(layerId, featureVariables);
		}
		this.values = layerVariableMap;
	}
}
