package com.git.opengds.validator.service;

public interface ValidatorProgressService {

	public Integer setStateToRequest(int validateStart, String collectionName, String fileType);

	public void setStateToProgressing(int validateStart, String fileType, int pIdx);

	public void setStateToValidateSuccess(int validateSuccess, String fileType, int pIdx);

	public void setStateToValidateFail(int validateSuccess, String fileType, int pIdx);

	public void setStateToErrLayerSuccess(int errLayerSuccess, String fileType, int pIdx, String tableName);

	public void setStateToErrLayerFail(int errLayerFail, String fileType, int pIdx);

	public void setStateToResponse(String fileType, int pIdx);

}
