package com.git.opengds.validator.persistence;

import java.util.HashMap;
import java.util.List;

import com.git.opengds.user.domain.UserVO;

public interface ValidateProgressDAO {

	public Integer insertSHPRequestState(UserVO userVO, HashMap<String, Object> insertQuery);

	public Integer selectSHPValidateProgressPid(UserVO userVO, HashMap<String, Object> selectQA10ValidateProgressPid);

	public void updateSHPProgressingState(UserVO userVO, HashMap<String, Object> insertProgressingState);

	public void updateSHPValidateSuccessState(UserVO userVO, HashMap<String, Object> insertProgressingState);

	public void updateSHPValidateFailState(UserVO userVO, HashMap<String, Object> insertProgressingState);

	public void updateSHPValidateErrLayerSuccess(UserVO userVO, HashMap<String, Object> updateProgressingState);

	public void updateSHPValidateErrLayerFail(UserVO userVO, HashMap<String, Object> updateProgressingState);

	public void insertSHPResponseState(UserVO userVO, HashMap<String, Object> insertQuery);

	public void insertSHPErrorTableName(UserVO userVO, HashMap<String, Object> insertErrorTableName);

	public List<HashMap<String, Object>> selectAllSHPValidateProgress(UserVO userVO,
			Object selectAllSHPValidateProgress);

	public Long selectErrorLayerTbNamesCount(UserVO userVO, String fileType, HashMap<String, Object> selectIdxQuery);

}
