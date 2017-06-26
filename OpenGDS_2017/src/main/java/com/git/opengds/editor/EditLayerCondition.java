package com.git.opengds.editor;

import java.util.HashMap;
import java.util.Map;

public class EditLayerCondition {

	Map<String, Object> successedLayers;
	Map<String, Object> failedLayers;

	public EditLayerCondition() {
		successedLayers = new HashMap<String, Object>();
		failedLayers = new HashMap<String, Object>();
	}

	public void putSuccessedLayers(String collectionName, String layerName) {
		successedLayers.put(collectionName, layerName);
	}

	public void putFailedLayers(String collectionName, String layerName) {
		failedLayers.put(collectionName, layerName);
	}

	public Map<String, Object> getSuccessedLayers() {
		return successedLayers;
	}

	public void setSuccessedLayers(Map<String, Object> successedLayers) {
		this.successedLayers = successedLayers;
	}

	public Map<String, Object> getFailedLayer() {
		return failedLayers;
	}

	public void setFailedLayer(Map<String, Object> failedLayer) {
		this.failedLayers = failedLayer;
	}

}
