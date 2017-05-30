package com.git.gdsbuilder.type.simple.collection;

import org.apache.commons.lang.StringUtils;

import com.git.gdsbuilder.type.simple.layer.LayerList;

public class LayerCollection {

	String id = StringUtils.EMPTY;
	LayerList layerList = new LayerList();

	public LayerCollection() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LayerCollection(String id) {
		super();
		this.id = id;
	}

	public LayerCollection(String id, LayerList layerList) {
		super();
		this.id = id;
		this.layerList = layerList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LayerList getLayerList() {
		return layerList;
	}

	public void setLayerList(LayerList layerList) {
		this.layerList = layerList;
	}

}
