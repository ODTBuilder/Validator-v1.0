package com.git.gdsbuilder.generalization.tmp;

import org.geotools.data.simple.SimpleFeatureCollection;

public class DTGeneralizationResult {

	String tbName;
	String collectionName;
	String layerName;
	String genType;

	SimpleFeatureCollection resultSfc;

	int beforeFeautres;
	int resultFeautres;
	int beforePoints;
	int resultPoints;

	public DTGeneralizationResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DTGeneralizationResult(String tbName, String collectionName, String layerName,
			SimpleFeatureCollection resultSfc, int beforeFeautres, int resultFeautres, int beforePoints,
			int resultPoints) {
		super();
		this.tbName = tbName;
		this.collectionName = collectionName;
		this.layerName = layerName;
		this.resultSfc = resultSfc;
		this.beforeFeautres = beforeFeautres;
		this.resultFeautres = resultFeautres;
		this.beforePoints = beforePoints;
		this.resultPoints = resultPoints;
	}

	public String getGenType() {
		return genType;
	}

	public void setGenType(String genType) {
		this.genType = genType;
	}

	public SimpleFeatureCollection getResultSfc() {
		return resultSfc;
	}

	public void setResultSfc(SimpleFeatureCollection resultSfc) {
		this.resultSfc = resultSfc;
	}

	public String getTbName() {
		return tbName;
	}

	public void setTbName(String tbName) {
		this.tbName = tbName;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	public int getBeforeFeautres() {
		return beforeFeautres;
	}

	public void setBeforeFeautres(int beforeFeautres) {
		this.beforeFeautres = beforeFeautres;
	}

	public int getResultFeautres() {
		return resultFeautres;
	}

	public void setResultFeautres(int resultFeautres) {
		this.resultFeautres = resultFeautres;
	}

	public int getBeforePoints() {
		return beforePoints;
	}

	public void setBeforePoints(int beforePoints) {
		this.beforePoints = beforePoints;
	}

	public int getResultPoints() {
		return resultPoints;
	}

	public void setResultPoints(int resultPoints) {
		this.resultPoints = resultPoints;
	}

}
