package com.git.gdsbuilder.type.qa10.structure;

import java.util.ArrayList;
import java.util.List;

import org.kabeja.dxf.DXFDimensionStyle;
import org.kabeja.dxf.DXFLineType;
import org.kabeja.dxf.DXFStyle;
import org.kabeja.dxf.DXFView;
import org.kabeja.dxf.DXFViewport;

public class QA10Tables {

	String collectionName;
	List<DXFLineType> lineType; // DXFLineType
	List<DXFDimensionStyle> dimStyle; // DXFDimensionStyle
	List<DXFView> view; // DXFView
	List<DXFStyle> style; // DXFStyle
	List<DXFViewport> viewport; // DXFViewport
	// HashMap ucs; // ?

	public QA10Tables() {
		this.collectionName = "";
		this.lineType = new ArrayList<DXFLineType>();
		this.dimStyle = new ArrayList<DXFDimensionStyle>();
		this.view = new ArrayList<DXFView>();
		this.style = new ArrayList<DXFStyle>();
		this.viewport = new ArrayList<DXFViewport>();
	}

	public QA10Tables(String collectionName, List<DXFLineType> lineType, List<DXFDimensionStyle> dimStyle,
			List<DXFView> view, List<DXFStyle> style, List<DXFViewport> viewport) {
		this.collectionName = collectionName;
		this.lineType = lineType;
		this.dimStyle = dimStyle;
		this.view = view;
		this.style = style;
		this.viewport = viewport;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public List<DXFLineType> getLineType() {
		return lineType;
	}

	public void setLineType(List<DXFLineType> lineType) {
		this.lineType = lineType;
	}

	public List<DXFDimensionStyle> getDimStyle() {
		return dimStyle;
	}

	public void setDimStyle(List<DXFDimensionStyle> dimStyle) {
		this.dimStyle = dimStyle;
	}

	public List<DXFView> getView() {
		return view;
	}

	public void setView(List<DXFView> view) {
		this.view = view;
	}

	public List<DXFStyle> getStyle() {
		return style;
	}

	public void setStyle(List<DXFStyle> style) {
		this.style = style;
	}

	public List<DXFViewport> getViewport() {
		return viewport;
	}

	public void setViewport(List<DXFViewport> viewport) {
		this.viewport = viewport;
	}

}
