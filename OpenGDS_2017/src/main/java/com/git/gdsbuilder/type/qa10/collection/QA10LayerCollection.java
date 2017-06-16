package com.git.gdsbuilder.type.qa10.collection;

import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa10.layer.QA10LayerList;
import com.git.gdsbuilder.type.qa10.structure.QA10Blocks;
import com.git.gdsbuilder.type.qa10.structure.QA10Classes;
import com.git.gdsbuilder.type.qa10.structure.QA10Header;
import com.git.gdsbuilder.type.qa10.structure.QA10Objects;
import com.git.gdsbuilder.type.qa10.structure.QA10Tables;

public class QA10LayerCollection {

	String id;
	String fileName;
	QA10LayerList qa10Layers; // entities
	QA10Header header; // header
	QA10Classes classes; // classes
	QA10Blocks blocks; // blocks
	QA10Objects objects; // objects
	QA10Tables tables; // tables;

	public QA10LayerCollection(String id) {
		this.id = id;
		this.fileName = "";
		this.qa10Layers = new QA10LayerList(); 
		this.header = new QA10Header();
		this.classes = new QA10Classes();
		this.blocks = new QA10Blocks();
		this.objects = new QA10Objects();
		this.tables = new QA10Tables();
	}

	public QA10LayerCollection(String id, String fileName, QA10LayerList qa10Layers) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.qa10Layers = qa10Layers;
	}

	public QA10LayerCollection(String id, String fileName) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.qa10Layers = new QA10LayerList();
	}

	public QA10LayerCollection(String id, String fileName, QA10LayerList qa10Layers, QA10Header header,
			QA10Classes classes, QA10Blocks blocks, QA10Objects objects, QA10Tables tables) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.qa10Layers = qa10Layers;
		this.header = header;
		this.classes = classes;
		this.blocks = blocks;
		this.objects = objects;
		this.tables = tables;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public QA10Classes getClasses() {
		return classes;
	}

	public void setClasses(QA10Classes classes) {
		this.classes = classes;
	}

	public QA10Blocks getBlocks() {
		return blocks;
	}

	public void setBlocks(QA10Blocks blocks) {
		this.blocks = blocks;
	}

	public QA10Objects getObjects() {
		return objects;
	}

	public void setObjects(QA10Objects objects) {
		this.objects = objects;
	}

	public QA10Tables getTables() {
		return tables;
	}

	public void setTables(QA10Tables tables) {
		this.tables = tables;
	}

}
