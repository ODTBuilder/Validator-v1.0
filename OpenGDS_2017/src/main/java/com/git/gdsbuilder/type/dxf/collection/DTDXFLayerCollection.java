package com.git.gdsbuilder.type.dxf.collection;

import com.git.gdsbuilder.type.dxf.layer.DTDXFLayer;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayerList;
import com.git.gdsbuilder.type.dxf.structure.DTDXFBlocks;
import com.git.gdsbuilder.type.dxf.structure.DTDXFEntities;
import com.git.gdsbuilder.type.dxf.structure.DTDXFHeader;
import com.git.gdsbuilder.type.dxf.structure.DTDXFTables;

public class DTDXFLayerCollection {

	String id;
	String collectionName;
	DTDXFLayerList qa10Layers; // entities
	DTDXFHeader header; // header
	DTDXFBlocks blocks; // blocks
	DTDXFTables tables; // tables;
	DTDXFEntities entities;

	public DTDXFLayerCollection() {
		this.id = "";
		this.collectionName = "";
		this.qa10Layers = new DTDXFLayerList();
		this.header = new DTDXFHeader();
		this.tables = new DTDXFTables();
		this.entities = new DTDXFEntities();
	}

	public DTDXFLayerCollection(String id) {
		this.id = id;
		this.collectionName = "";
		this.qa10Layers = new DTDXFLayerList();
		this.header = new DTDXFHeader();
		this.blocks = new DTDXFBlocks();
		this.tables = new DTDXFTables();
	}

	public DTDXFLayerCollection(String id, String fileName, DTDXFLayerList qa10Layers) {
		super();
		this.id = id;
		this.collectionName = fileName;
		this.qa10Layers = qa10Layers;
	}

	public DTDXFLayerCollection(String id, String fileName) {
		super();
		this.id = id;
		this.collectionName = fileName;
		this.qa10Layers = new DTDXFLayerList();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DTDXFLayerList getQa10Layers() {
		return qa10Layers;
	}

	public void setQa10Layers(DTDXFLayerList qa10Layers) {
		this.qa10Layers = qa10Layers;
	}

	public DTDXFEntities getEntities() {
		return entities;
	}

	public void setEntities(DTDXFEntities entities) {
		this.entities = entities;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public void addQA10Layer(DTDXFLayer qa10Layer) {
		this.qa10Layers.add(qa10Layer);
	}

	public void addAllQA10Layers(DTDXFLayerList qa10Layers) {
		this.qa10Layers.addAll(qa10Layers);
	}

	public DTDXFHeader getHeader() {
		return header;
	}

	public void setHeader(DTDXFHeader header) {
		this.header = header;
	}

	public DTDXFBlocks getBlocks() {
		return blocks;
	}

	public void setBlocks(DTDXFBlocks blocks) {
		this.blocks = blocks;
	}

	public DTDXFTables getTables() {
		return tables;
	}

	public void setTables(DTDXFTables tables) {
		this.tables = tables;
	}

}
