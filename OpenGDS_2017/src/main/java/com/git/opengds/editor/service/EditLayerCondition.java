package com.git.opengds.editor.service;

import java.util.Map;

public class EditLayerCondition {

	Map<String, Object> successedLayers;
	Map<String, Object> failedLayer;
	
	
	
	public void putSuccessedLayer(String collectionName, String layerName) {
		successedLayers.put(collectionName, layerName);
	}
	
	public void putFailedLayer(String collectionName, String layerName) {
		failedLayer.put(collectionName, layerName);
	}
	

	public Map<String, Object> getSuccessedLayers() {
		return successedLayers;
	}

	public void setSuccessedLayers(Map<String, Object> successedLayers) {
		this.successedLayers = successedLayers;
	}

	public Map<String, Object> getFailedLayer() {
		return failedLayer;
	}

	public void setFailedLayer(Map<String, Object> failedLayer) {
		this.failedLayer = failedLayer;
	}

}
