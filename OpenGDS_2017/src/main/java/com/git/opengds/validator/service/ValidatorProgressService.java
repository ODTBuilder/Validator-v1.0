package com.git.opengds.validator.service;

import com.git.gdsbuilder.type.validate.collection.ValidateProgressList;
import com.git.opengds.user.domain.UserVO;

public interface ValidatorProgressService {

	public Integer setStateToRequest(UserVO userVO, int validateStart, String collectionName, String fileType);

	public void setStateToProgressing(UserVO userVO, int validateStart, String fileType, int pIdx);

	public void setStateToValidateSuccess(UserVO userVO, int validateSuccess, String fileType, int pIdx);

	public void setStateToValidateFail(UserVO userVO, int validateSuccess, String fileType, int pIdx);

	public void setStateToErrLayerSuccess(UserVO userVO, int errLayerSuccess, String fileType, int pIdx, String tableName);

	public void setStateToErrLayerFail(UserVO userVO, int errLayerFail, String fileType, int pIdx);

	public void setStateToResponse(UserVO userVO, String fileType, int pIdx);
	
	public ValidateProgressList selectProgressOfCollection(UserVO userVO, String type);

}
