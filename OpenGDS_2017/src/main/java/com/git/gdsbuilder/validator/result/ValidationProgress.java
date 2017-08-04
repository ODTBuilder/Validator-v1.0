package com.git.gdsbuilder.validator.result;

public class ValidationProgress {

	private String collectioName;
	private String fileType;
	private Integer state;
	private String requestTime;
	private String responseTime;
	private String errLayerName;

	public String getCollectioName() {
		return collectioName;
	}

	public void setCollectioName(String collectioName) {
		this.collectioName = collectioName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
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
