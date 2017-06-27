package com.git.gdsbuilder.type.validate.collection;

public class ValidateProgress {

	String collectionName;
	String fileType;
	int state;
	String requestTime;
	String responseTime;
	String errLayerName;

	public ValidateProgress() {

	}

	public ValidateProgress(String collectionName, String fileType, int state, String requestTime, String responseTime,
			String errLayerName) {
		super();
		this.collectionName = collectionName;
		this.fileType = fileType;
		this.state = state;
		this.requestTime = requestTime;
		this.responseTime = responseTime;
		this.errLayerName = errLayerName;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public String getErrLayerName() {
		return errLayerName;
	}

	public void setErrLayerName(String errLayerName) {
		this.errLayerName = errLayerName;
	}

}
