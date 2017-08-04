package com.git.gdsbuilder.type.dxf.structure;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.git.gdsbuilder.type.dxf.feature.DTDXFFeature;
import com.git.gdsbuilder.type.dxf.feature.DTDXFFeatureList;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayer;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayerList;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

public class DTDXFEntities {

	String collectionName;
	Map<String, Object> values;

	public DTDXFEntities() {
		super();
		this.collectionName = "";
		this.values = new LinkedHashMap<String, Object>();
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

	public void setPointValues(DTDXFLayerList qa10LayerList) {

		Map<String, Object> layerVariableMap = new LinkedHashMap<String, Object>();
		for (int i = 0; i < qa10LayerList.size(); i++) {
			DTDXFLayer qa10Layer = qa10LayerList.get(i);
			String layerId = qa10Layer.getLayerID();
			String[] typeSplit = layerId.split("_");
			String id = typeSplit[0];
			List<LinkedHashMap<String, Object>> featureVariables = new ArrayList<LinkedHashMap<String, Object>>();
			DTDXFFeatureList qa10FeatureList = qa10Layer.getQa10FeatureList();
			for (int j = 0; j < qa10FeatureList.size(); j++) {
				DTDXFFeature qa10Feature = qa10FeatureList.get(j);
				Geometry point = qa10Feature.getGeom();
				Coordinate coor = point.getCoordinate();
				LinkedHashMap<String, Object> featureVariable = new LinkedHashMap<String, Object>();
				featureVariable.put("0", "POINT");
				featureVariable.put("8", id);
				featureVariable.put("10", String.valueOf(coor.x));
				featureVariable.put("20", String.valueOf(coor.y));
				featureVariable.put("30", "0");
				featureVariables.add(featureVariable);
			}
			layerVariableMap.put(layerId, featureVariables);
		}
		this.values = layerVariableMap;
	}

	public void setErrTextValues(DTDXFLayer qa10Layer) {

		List<LinkedHashMap<String, Object>> entitiyMapList = new ArrayList<LinkedHashMap<String, Object>>();
		String layerId = qa10Layer.getLayerID();
		DTDXFFeatureList qa10FeatureList = qa10Layer.getQa10FeatureList();
		for (int i = 0; i < qa10FeatureList.size(); i++) {
			DTDXFFeature qa10Feature = qa10FeatureList.get(i);
			LinkedHashMap<String, Object> entitiyMap = new LinkedHashMap<String, Object>();
			Geometry geom = qa10Feature.getGeom();
			Coordinate coor = geom.getCoordinate();
			entitiyMap.put("0", "TEXT");
			entitiyMap.put("8", layerId);
			entitiyMap.put("10", String.valueOf(coor.x));
			entitiyMap.put("20", String.valueOf(coor.y));
			entitiyMap.put("30", "0");
			entitiyMap.put("40", "16");
			entitiyMap.put("1", "0");
			entitiyMapList.add(entitiyMap);
		}
		this.values.put(layerId, entitiyMapList);
	}

	public void getPolylineValues(DTDXFLayer qa10Layer) {

		List<LinkedHashMap<String, Object>> entitiyMapList = new ArrayList<LinkedHashMap<String, Object>>();
		String layerId = qa10Layer.getLayerID();
		String[] typeSplit = layerId.split("_");
		String id = typeSplit[0];
		DTDXFFeatureList qa10FeatureList = qa10Layer.getQa10FeatureList();
		for (int j = 0; j < qa10FeatureList.size(); j++) {
			DTDXFFeature qa10Feature = qa10FeatureList.get(j);
			LinkedHashMap<String, Object> entitiyMap = new LinkedHashMap<String, Object>();
			entitiyMap.put("0", "POLYLINE");
			entitiyMap.put("8", id);
			// entitiyMap.put("330", qa10Feature.getFeatureID());
			entitiyMap.put("66", "1");
			entitiyMap.put("10", "0");
			entitiyMap.put("20", "0");
			entitiyMap.put("30", "0");
			entitiyMap.put("70", String.valueOf(qa10Feature.getFlag()));

			List<LinkedHashMap<String, Object>> vertexMapList = new ArrayList<LinkedHashMap<String, Object>>();
			double elevation = qa10Feature.getElevation();
			Geometry geom = qa10Feature.getGeom();
			Coordinate[] coors = geom.getCoordinates();
			for (int i = 0; i < coors.length; i++) {
				Coordinate coor = coors[i];
				LinkedHashMap<String, Object> vertexMap = new LinkedHashMap<String, Object>();
				vertexMap.put("0", "VERTEX");
				vertexMap.put("8", id);
				vertexMap.put("10", String.valueOf(coor.x));
				vertexMap.put("20", String.valueOf(coor.y));
				vertexMap.put("30", String.valueOf(elevation));
				vertexMap.put("70", "32");
				vertexMapList.add(vertexMap);
			}
			entitiyMap.put("vertexs", vertexMapList);
			entitiyMapList.add(entitiyMap);
		}
		this.values.put(layerId, entitiyMapList);
	}

	public void getTextValues(DTDXFLayer qa10Layer) {

		List<LinkedHashMap<String, Object>> entitiyMapList = new ArrayList<LinkedHashMap<String, Object>>();
		String layerId = qa10Layer.getLayerID();
		String[] typeSplit = layerId.split("_");
		String id = typeSplit[0];
		DTDXFFeatureList qa10FeatureList = qa10Layer.getQa10FeatureList();
		for (int i = 0; i < qa10FeatureList.size(); i++) {
			DTDXFFeature qa10Feature = qa10FeatureList.get(i);
			LinkedHashMap<String, Object> entitiyMap = new LinkedHashMap<String, Object>();
			Geometry geom = qa10Feature.getGeom();
			Coordinate coor = geom.getCoordinate();
			entitiyMap.put("0", "TEXT");
			entitiyMap.put("8", id);
			// entitiyMap.put("330", qa10Feature.getFeatureID());
			entitiyMap.put("10", String.valueOf(coor.x));
			entitiyMap.put("20", String.valueOf(coor.y));
			entitiyMap.put("30", "0");
			double height = qa10Feature.getHeight();
			if (height != 0) {
				entitiyMap.put("40", String.valueOf(qa10Feature.getHeight()));
			}
			String textValue = qa10Feature.getTextValue();
			if (!textValue.equals("")) {
				entitiyMap.put("1", String.valueOf(qa10Feature.getTextValue()));
			}
			double rotate = qa10Feature.getRotate();
			if (rotate != 0) {
				entitiyMap.put("50", String.valueOf(qa10Feature.getRotate()));
			}
			entitiyMap.put("7", "STANDARD");
			entitiyMapList.add(entitiyMap);
		}
		this.values.put(layerId, entitiyMapList);
	}

	public void getInsertValues(DTDXFLayer qa10Layer) {

		List<LinkedHashMap<String, Object>> entitiyMapList = new ArrayList<LinkedHashMap<String, Object>>();
		String layerId = qa10Layer.getLayerID();

		String[] typeSplit = layerId.split("_");
		String id = typeSplit[0];

		DTDXFFeatureList qa10FeatureList = qa10Layer.getQa10FeatureList();
		for (int i = 0; i < qa10FeatureList.size(); i++) {
			DTDXFFeature qa10Feature = qa10FeatureList.get(i);
			LinkedHashMap<String, Object> entitiyMap = new LinkedHashMap<String, Object>();
			Geometry geom = qa10Feature.getGeom();
			Coordinate coor = geom.getCoordinate();
			entitiyMap.put("0", "INSERT");
			entitiyMap.put("8", id);
			entitiyMap.put("2", id);
			// entitiyMap.put("330", qa10Feature.getFeatureID());
			entitiyMap.put("10", String.valueOf(coor.x));
			entitiyMap.put("20", String.valueOf(coor.y));
			entitiyMap.put("30", "0");
			entitiyMap.put("41", "1");
			entitiyMap.put("42", "1");
			entitiyMap.put("50", String.valueOf(qa10Feature.getRotate()));
			entitiyMap.put("43", "1");
			entitiyMapList.add(entitiyMap);
		}
		this.values.put(layerId, entitiyMapList);
	}

	public void setEntitiesValue(DTDXFLayerList qa10LayerList) {

		for (int i = 0; i < qa10LayerList.size(); i++) {
			DTDXFLayer qa10Layer = qa10LayerList.get(i);
			String layerType = qa10Layer.getLayerType();
			if (layerType.equals("LWPOLYLINE") || layerType.equals("POLYLINE")) {
				getPolylineValues(qa10Layer);
			} else if (layerType.equals("TEXT")) {
				getTextValues(qa10Layer);
			} else if (layerType.equals("INSERT")) {
				getInsertValues(qa10Layer);
			} else if (layerType.equals("POINT")) {
				getPolylineValues(qa10Layer);
			}
		}
	}
}
