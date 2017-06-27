package com.git.opengds.validator.service;

public interface ValidatorProgressService {

	public boolean setStateToRequest(String collectionName, String fileType, String requestTime);

	public boolean setStateToProgressing(String collectionName, String fileType);

}
