package com.git.gdsbuilder.type.qa10.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.git.gdsbuilder.type.qa10.feature.QA10Feature;
import com.git.gdsbuilder.type.qa10.feature.QA10FeatureList;
import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa10.layer.QA10LayerList;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

public class QA10Entities {

	String collectionName;
	Map<String, Object> values;

	public QA10Entities() {
		super();
		this.collectionName = "";
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
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

	public void getLWPolylineValues(QA10Layer qa10Layer) {

		String layerId = qa10Layer.getLayerID();
		QA10FeatureList qa10FeatureList = qa10Layer.getQa10FeatureList();
		for (int j = 0; j < qa10FeatureList.size(); j++) {
			QA10Feature qa10Feature = qa10FeatureList.get(j);
			HashMap<String, Object> entitiyMap = new HashMap<String, Object>();
			entitiyMap.put("0", "POLYLINE");
			entitiyMap.put("8", layerId);
			entitiyMap.put("330", qa10Feature.getFeatureID());
			entitiyMap.put("66", 1);
			entitiyMap.put("10", 0);
			entitiyMap.put("20", 0);
			entitiyMap.put("30", 0);

			List<HashMap<String, Object>> vertexMapList = new ArrayList<HashMap<String, Object>>();
			double elevation = qa10Feature.getElevation();
			Geometry geom = qa10Feature.getGeom();
			Coordinate[] coors = geom.getCoordinates();
			for (int i = 0; i < coors.length; i++) {
				Coordinate coor = coors[i];
				HashMap<String, Object> vertexMap = new HashMap<String, Object>();
				vertexMap.put("0", "VERTEX");
				vertexMap.put("8", layerId);
				vertexMap.put("10", coor.x);
				vertexMap.put("20", coor.y);
				vertexMap.put("30", elevation);
				vertexMapList.add(vertexMap);
			}
		}
	}

	public void setPolylineValues() {

	}

	public void setTextValues() {

	}

	public void setInsertValues() {

	}

	public void setEntitiesValue(QA10LayerList qa10LayerList) {

		for (int i = 0; i < qa10LayerList.size(); i++) {
			QA10Layer qa10Layer = qa10LayerList.get(i);
			String layerType = qa10Layer.getLayerType();
			if (layerType.equals("LWPOLYLINE")) {
				getLWPolylineValues(qa10Layer);
			}
		}
	}
}
