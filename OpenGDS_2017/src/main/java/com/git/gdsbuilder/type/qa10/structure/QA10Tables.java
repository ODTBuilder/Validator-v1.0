package com.git.gdsbuilder.type.qa10.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFLineType;
import org.kabeja.dxf.DXFStyle;

import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa10.layer.QA10LayerList;

public class QA10Tables {

	String collectionName;

	boolean isLineTypes = false;
	boolean isLayers = false;
	boolean isStyles = false;

	Map<String, Object> lineTypes;
	Map<String, Object> layers;
	Map<String, Object> styles;

	public QA10Tables() {
		this.collectionName = "";
		this.lineTypes = new HashMap<String, Object>();
		this.layers = new HashMap<String, Object>();
		this.styles = new HashMap<String, Object>();
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public boolean isLineTypes() {
		return isLineTypes;
	}

	public void setLineTypes(boolean isLineTypes) {
		this.isLineTypes = isLineTypes;
	}

	public boolean isLayers() {
		return isLayers;
	}

	public void setLayers(boolean isLayers) {
		this.isLayers = isLayers;
	}

	public boolean isStyles() {
		return isStyles;
	}

	public void setStyles(boolean isStyles) {
		this.isStyles = isStyles;
	}

	public Map<String, Object> getLineTypes() {
		return lineTypes;
	}

	public void setLineTypes(Map<String, Object> lineTypes) {
		this.lineTypes = lineTypes;
	}

	public Map<String, Object> getLayers() {
		return layers;
	}

	public void setLayers(Map<String, Object> layers) {
		this.layers = layers;
	}

	public Map<String, Object> getStyles() {
		return styles;
	}

	public void setStyles(Map<String, Object> styles) {
		this.styles = styles;
	}

	public void setLineTypeValues(Iterator lineTypeIterator) {

		Map<String, Object> variabliesMap = new LinkedHashMap<String, Object>();

		LinkedHashMap<String, Object> commons = new LinkedHashMap<String, Object>();
		commons.put("2", "LTYPE");
		commons.put("70", "5");
		variabliesMap.put("common", commons);

		List<LinkedHashMap<String, Object>> lineTypes = new ArrayList<LinkedHashMap<String, Object>>();
		while (lineTypeIterator.hasNext()) {
			DXFLineType lineType = (DXFLineType) lineTypeIterator.next();
			String lineTypeName = lineType.getName();

			LinkedHashMap<String, Object> lineTypeValue = new LinkedHashMap<String, Object>();
			lineTypeValue.put("0", "LTYPE");
			lineTypeValue.put("2", lineTypeName);
			lineTypeValue.put("70", "64");
			lineTypeValue.put("3", lineType.getDescritpion());
			lineTypeValue.put("72", String.valueOf(lineType.getAlignment()));
			lineTypeValue.put("73", String.valueOf(lineType.getSegmentCount()));
			lineTypeValue.put("40", String.valueOf(lineType.getPatternLength()));
			lineTypes.add(lineTypeValue);
		}
		variabliesMap.put("lineTypes", lineTypes);
		this.lineTypes = variabliesMap;
	}

	public void setLayerValues(Iterator layerIterator) {

		Map<String, Object> variabliesMap = new LinkedHashMap<String, Object>();

		LinkedHashMap<String, Object> commons = new LinkedHashMap<String, Object>();
		commons.put("2", "LAYER");
		commons.put("70", "99");
		variabliesMap.put("common", commons);

		List<LinkedHashMap<String, Object>> layers = new ArrayList<LinkedHashMap<String, Object>>();
		while (layerIterator.hasNext()) {
			DXFLayer dxfLayer = (DXFLayer) layerIterator.next();
			String layerName = dxfLayer.getName();
			LinkedHashMap<String, Object> layer = new LinkedHashMap<String, Object>();
			layer.put("0", "LAYER");
			layer.put("2", layerName);
			layer.put("70", "64");
			layer.put("62", String.valueOf(dxfLayer.getColor()));
			layer.put("6", dxfLayer.getLineType());
			layers.add(layer);
		}
		variabliesMap.put("layers", layers);
		this.layers = variabliesMap;
	}

	public Map<String, Object> getLayerValues(QA10Layer qa10Layer) {

		Map<String, Object> variabliesMap = new LinkedHashMap<String, Object>();

		LinkedHashMap<String, Object> commons = new LinkedHashMap<String, Object>();
		commons.put("2", "LAYER");
		commons.put("70", "99");
		variabliesMap.put("common", commons);

		List<LinkedHashMap<String, Object>> layers = new ArrayList<LinkedHashMap<String, Object>>();
		String layerName = qa10Layer.getLayerID();
		LinkedHashMap<String, Object> layer = new LinkedHashMap<String, Object>();
		layer.put("0", "LAYER");
		layer.put("2", layerName);
		layer.put("70", "64");
		layer.put("62", "7");
		layer.put("6", "COUNTINUOUS");
		layers.add(layer);
		variabliesMap.put("layers", layers);

		return variabliesMap;
	}

	public void setStyleValues(Iterator styleIterator) {

		Map<String, Object> variabliesMap = new LinkedHashMap<String, Object>();

		LinkedHashMap<String, Object> commons = new LinkedHashMap<String, Object>();
		commons.put("2", "STYLE");
		commons.put("70", "2");
		variabliesMap.put("common", commons);

		List<LinkedHashMap<String, Object>> styles = new ArrayList<LinkedHashMap<String, Object>>();
		while (styleIterator.hasNext()) {
			DXFStyle style = (DXFStyle) styleIterator.next();
			String styleName = style.getName();
			LinkedHashMap<String, Object> layerVariable = new LinkedHashMap<String, Object>();
			layerVariable.put("0", "STYLE");
			layerVariable.put("2", styleName);
			layerVariable.put("70", String.valueOf(style.getFlags()));
			layerVariable.put("40", String.valueOf(style.getTextHeight()));
			layerVariable.put("41", String.valueOf(style.getWidthFactor()));
			layerVariable.put("50", String.valueOf(style.getObliqueAngle()));
			layerVariable.put("71", String.valueOf(style.getTextGenerationFlag()));
			layerVariable.put("42", String.valueOf(style.getLastHeight()));
			layerVariable.put("3", style.getFontFile());
			layerVariable.put("4", style.getBigFontFile());
			styles.add(layerVariable);
		}
		variabliesMap.put("styles", styles);
		this.styles = variabliesMap;
	}

	public void setDefaultLineTypeValues() {

		Map<String, Object> variabliesMap = new LinkedHashMap<String, Object>();

		LinkedHashMap<String, Object> commons = new LinkedHashMap<String, Object>();
		commons.put("2", "LTYPE");
		commons.put("70", "5");
		variabliesMap.put("common", commons);

		LinkedHashMap<String, Object> lineTypeValue = new LinkedHashMap<String, Object>();
		lineTypeValue.put("0", "LTYPE");
		lineTypeValue.put("2", "CONTINUOUS");
		lineTypeValue.put("70", "0");
		lineTypeValue.put("3", "Solid line");
		lineTypeValue.put("72", "65");
		lineTypeValue.put("73", "0");
		lineTypeValue.put("40", "0");
		variabliesMap.put("lineTypes", lineTypeValue);
		this.lineTypes = variabliesMap;
	}

	public void setLayerValues(QA10LayerList qa10LayerList) {

		Map<String, Object> variabliesMap = new LinkedHashMap<String, Object>();

		LinkedHashMap<String, Object> commons = new LinkedHashMap<String, Object>();
		commons.put("2", "LAYER");
		commons.put("70", "99");
		variabliesMap.put("common", commons);

		List<LinkedHashMap<String, Object>> layers = new ArrayList<LinkedHashMap<String, Object>>();
		for (int i = 0; i < qa10LayerList.size(); i++) {
			QA10Layer qa10Layer = qa10LayerList.get(i);
			String layerId = qa10Layer.getLayerID();
			LinkedHashMap<String, Object> layer = new LinkedHashMap<String, Object>();
			layer.put("0", "LAYER");
			layer.put("2", layerId);
			layer.put("70", "64");
			layer.put("62", "1");
			layer.put("6", "CONTINUOUS");
			layers.add(layer);
		}
		variabliesMap.put("layers", layers);
		this.layers = variabliesMap;
	}

	public void setDefaultStyleValues() {

		Map<String, Object> variabliesMap = new LinkedHashMap<String, Object>();

		LinkedHashMap<String, Object> commons = new LinkedHashMap<String, Object>();
		commons.put("2", "STYLE");
		commons.put("70", "2");
		variabliesMap.put("common", commons);

		List<LinkedHashMap<String, Object>> styles = new ArrayList<LinkedHashMap<String, Object>>();
		styles.add(getStandardStyle());
		styles.add(getGHSStyle());
		variabliesMap.put("styles", styles);
		this.styles = variabliesMap;
	}

	private LinkedHashMap<String, Object> getStandardStyle() {

		String styleName = "STANDARD";
		LinkedHashMap<String, Object> styleVariable = new LinkedHashMap<String, Object>();
		styleVariable.put("0", "STYLE");
		styleVariable.put("2", styleName);
		styleVariable.put("70", "0");
		styleVariable.put("40", "0");
		styleVariable.put("41", "1");
		styleVariable.put("50", "0");
		styleVariable.put("71", "0");
		styleVariable.put("42", "5");
		styleVariable.put("3", "romans");
		styleVariable.put("4", "ghs");

		return styleVariable;
	}

	private LinkedHashMap<String, Object> getGHSStyle() {

		String styleName = "GHS";
		LinkedHashMap<String, Object> styleVariable = new LinkedHashMap<String, Object>();
		styleVariable.put("0", "STYLE");
		styleVariable.put("2", styleName);
		styleVariable.put("70", "64");
		styleVariable.put("40", "0");
		styleVariable.put("41", "1");
		styleVariable.put("50", "0");
		styleVariable.put("71", "0");
		styleVariable.put("42", "5.377037");
		styleVariable.put("3", "romans");
		styleVariable.put("4", "ghs");

		return styleVariable;

	}

	public void setLayerValues(HashMap<String, Object> tablesCommonMap,
			List<HashMap<String, Object>> tablesLayerMapList) {

		Map<String, Object> variabliesMap = new LinkedHashMap<String, Object>();

		LinkedHashMap<String, Object> commons = new LinkedHashMap<String, Object>();
		commons.put("2", tablesCommonMap.get("2"));
		commons.put("70", tablesCommonMap.get("70"));
		variabliesMap.put("common", commons);

		List<LinkedHashMap<String, Object>> layers = new ArrayList<LinkedHashMap<String, Object>>();
		for (int i = 0; i < tablesLayerMapList.size(); i++) {
			HashMap<String, Object> tableLayerMap = tablesLayerMapList.get(i);
			LinkedHashMap<String, Object> layer = new LinkedHashMap<String, Object>();
			layer.put("0", tableLayerMap.get("0"));
			layer.put("2", tableLayerMap.get("2"));
			layer.put("70", tableLayerMap.get("70"));
			layer.put("62", tableLayerMap.get("62"));
			layer.put("6", tableLayerMap.get("6"));
			layers.add(layer);
		}
		variabliesMap.put("layers", layers);
		this.layers = variabliesMap;
	}
}
