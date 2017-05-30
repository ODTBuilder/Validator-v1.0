package com.git.gdsbuilder.type.qa10.collection;

import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa10.layer.QA10LayerList;

public class QA10LayerCollection {

	String id;
	String fileName;
	QA10LayerList qa10Layers;

	public QA10LayerCollection(String id) {
		this.id = id;
		this.qa10Layers = new QA10LayerList();
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
}
