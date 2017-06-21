package com.git.gdsbuilder.type.qa10.collection;

import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa10.layer.QA10LayerList;
import com.git.gdsbuilder.type.qa10.structure.QA10Blocks;
import com.git.gdsbuilder.type.qa10.structure.QA10Entities;
import com.git.gdsbuilder.type.qa10.structure.QA10Header;
import com.git.gdsbuilder.type.qa10.structure.QA10Tables;

public class QA10LayerCollection {

	String id;
	String collectionName;
	QA10LayerList qa10Layers; // entities
	QA10Header header; // header
	QA10Blocks blocks; // blocks
	QA10Tables tables; // tables;
	QA10Entities entities;

	public QA10LayerCollection() {
		this.id = "";
		this.collectionName = "";
		this.qa10Layers = new QA10LayerList();
		this.header = new QA10Header();
		this.tables = new QA10Tables();
		this.entities = new QA10Entities();
	}

	public QA10LayerCollection(String id) {
		this.id = id;
		this.collectionName = "";
		this.qa10Layers = new QA10LayerList();
		this.header = new QA10Header();
		this.blocks = new QA10Blocks();
		this.tables = new QA10Tables();
	}

	public QA10LayerCollection(String id, String fileName, QA10LayerList qa10Layers) {
		super();
		this.id = id;
		this.collectionName = fileName;
		this.qa10Layers = qa10Layers;
	}

	public QA10LayerCollection(String id, String fileName) {
		super();
		this.id = id;
		this.collectionName = fileName;
		this.qa10Layers = new QA10LayerList();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public QA10LayerList getQa10Layers() {
		return qa10Layers;
	}

	public void setQa10Layers(QA10LayerList qa10Layers) {
		this.qa10Layers = qa10Layers;
	}

	public QA10Entities getEntities() {
		return entities;
	}

	public void setEntities(QA10Entities entities) {
		this.entities = entities;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public void addQA10Layer(QA10Layer qa10Layer) {
		this.qa10Layers.add(qa10Layer);
	}

	public void addAllQA10Layers(QA10LayerList qa10Layers) {
		this.qa10Layers.addAll(qa10Layers);
	}

	public QA10Header getHeader() {
		return header;
	}

	public void setHeader(QA10Header header) {
		this.header = header;
	}

	public QA10Blocks getBlocks() {
		return blocks;
	}

	public void setBlocks(QA10Blocks blocks) {
		this.blocks = blocks;
	}

	public QA10Tables getTables() {
		return tables;
	}

	public void setTables(QA10Tables tables) {
		this.tables = tables;
	}

}
