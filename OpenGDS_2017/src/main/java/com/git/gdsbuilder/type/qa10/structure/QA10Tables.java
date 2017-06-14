package com.git.gdsbuilder.type.qa10.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFLineType;
import org.kabeja.dxf.DXFStyle;
import org.kabeja.dxf.DXFVariable;

public class QA10Tables {

	String collectionName;
	DXFVariable table;

	boolean isLineTypes = false;
	boolean isLayers = false;
	boolean isStyles = false;

	Map<String, Object> lineTypes;
	Map<String, Object> layers;
	Map<String, Object> styles;

	public QA10Tables() {
		this.collectionName = "";
		initDefaultTable();
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

	public DXFVariable getTable() {
		return table;
	}

	public void setTable(DXFVariable table) {
		this.table = table;
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

	private void initDefaultTable() {
		DXFVariable table = new DXFVariable("table");
		table.setValue("0_TABLE", "TABLE");
		table.setValue("0_ENDTAB", "ENDTAB");
		this.table = table;
	}

	public void setLineTypeValues(Iterator lineTypeIterator) {

		Map<String, Object> variabliesMap = new HashMap<String, Object>();

		DXFVariable commons = new DXFVariable("commonLTYPE");
		commons.setValue("2_object", "STYLE");
		commons.setValue("70_object", "2");
		variabliesMap.put("common", commons);

		List<DXFVariable> lineTypes = new ArrayList<DXFVariable>();
		while (lineTypeIterator.hasNext()) {
			DXFLineType lineType = (DXFLineType) lineTypeIterator.next();
			String lineTypeName = lineType.getName();

			DXFVariable lineTypeValue = new DXFVariable(lineTypeName);
			lineTypeValue.setValue("0_entity", "LTYPE");
			lineTypeValue.setValue("2_entity", lineTypeName);
			lineTypeValue.setValue("70_entity", "64");
			lineTypeValue.setValue("3_entity", lineType.getDescritpion());
			lineTypeValue.setValue("72_entity", String.valueOf(lineType.getAlignment()));
			lineTypeValue.setValue("73_entity", String.valueOf(lineType.getSegmentCount()));
			lineTypeValue.setValue("40_entity", String.valueOf(lineType.getPatternLength()));
			lineTypes.add(lineTypeValue);
		}
		variabliesMap.put("lineTypes", lineTypes);
		this.lineTypes = variabliesMap;
	}

	public void setLayerValues(Iterator layerIterator) {

		Map<String, Object> variabliesMap = new HashMap<String, Object>();

		DXFVariable commons = new DXFVariable("commonLAYER");
		commons.setValue("2_object", "LAYER");
		commons.setValue("70_object", "99");
		variabliesMap.put("common", commons);

		List<DXFVariable> layers = new ArrayList<DXFVariable>();
		while (layerIterator.hasNext()) {
			DXFLayer dxfLayer = (DXFLayer) layerIterator.next();
			String layerName = dxfLayer.getName();
			DXFVariable layer = new DXFVariable(layerName);
			layer.setValue("0_entity", "LAYER");
			layer.setValue("2_entity", layerName);
			layer.setValue("70_entity", "64");
			layer.setValue("62_entity", String.valueOf(dxfLayer.getColor()));
			layer.setValue("6_entity", dxfLayer.getLineType());
			layers.add(layer);
		}
		variabliesMap.put("layers", layers);
		this.layers = variabliesMap;
	}

	public void setStyleValues(Iterator styleIterator) {

		Map<String, Object> variabliesMap = new HashMap<String, Object>();

		DXFVariable commons = new DXFVariable("commonSTYLE");
		commons.setValue("2_object", "STYLE");
		commons.setValue("70_object", "2");
		variabliesMap.put("common", commons);

		List<DXFVariable> styles = new ArrayList<DXFVariable>();
		while (styleIterator.hasNext()) {
			DXFStyle style = (DXFStyle) styleIterator.next();
			String styleName = style.getName();
			DXFVariable layerVariable = new DXFVariable(styleName);
			layerVariable.setValue("0_entity", "STYLE");
			layerVariable.setValue("2_entity", styleName);
			layerVariable.setValue("70_entity", String.valueOf(style.getFlags()));
			layerVariable.setValue("40_entity", String.valueOf(style.getTextHeight()));
			layerVariable.setValue("41_entity", String.valueOf(style.getWidthFactor()));
			layerVariable.setValue("50_entity", String.valueOf(style.getObliqueAngle()));
			layerVariable.setValue("71_entity", String.valueOf(style.getTextGenerationFlag()));
			layerVariable.setValue("42_entity", String.valueOf(style.getLastHeight()));
			layerVariable.setValue("3_entity", style.getFontFile());
			layerVariable.setValue("4_entity", style.getBigFontFile());
			styles.add(layerVariable);
		}
		variabliesMap.put("styles", styles);
		this.styles = variabliesMap;
	}
}
