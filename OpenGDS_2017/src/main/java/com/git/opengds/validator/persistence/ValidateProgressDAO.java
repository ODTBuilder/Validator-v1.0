package com.git.opengds.validator.persistence;

import java.util.HashMap;
import java.util.List;

import com.git.opengds.user.domain.UserVO;

public interface ValidateProgressDAO {

	public Integer insertQA20RequestState(UserVO userVO, HashMap<String, Object> insertQuery);

	public Integer insertQA10RequestState(UserVO userVO, HashMap<String, Object> insertQuery);

	public int selectQA20ValidateProgressPid(UserVO userVO, HashMap<String, Object> selectQA20ValidateProgressPid);

	public int selectQA10ValidateProgressPid(UserVO userVO, HashMap<String, Object> selectQA10ValidateProgressPid);

	public void updateQA20ProgressingState(UserVO userVO, HashMap<String, Object> insertProgressingState);

	public void updateQA20ValidateSuccessState(UserVO userVO, HashMap<String, Object> insertProgressingState);

	public void updateQA20ValidateFailState(UserVO userVO, HashMap<String, Object> insertProgressingState);

	public void updateQA20ValidateErrLayerSuccess(UserVO userVO, HashMap<String, Object> updateProgressingState);

	public void updateQA20ValidateErrLayerFail(UserVO userVO, HashMap<String, Object> updateProgressingState);

	public void insertQA20ResponseState(UserVO userVO, HashMap<String, Object> insertQuery);

	public void insertQA20ErrorTableName(UserVO userVO, HashMap<String, Object> insertErrorTableName);

	public void updateQA10ProgressingState(UserVO userVO, HashMap<String, Object> insertProgressingState);

	public void updateQA10ValidateSuccessState(UserVO userVO, HashMap<String, Object> insertProgressingState);

	public void updateQA10ValidateFailState(UserVO userVO, HashMap<String, Object> insertProgressingState);

	public void updateQA10ValidateErrLayerSuccess(UserVO userVO, HashMap<String, Object> updateProgressingState);

	public void updateQA10ValidateErrLayerFail(UserVO userVO, HashMap<String, Object> updateProgressingState);

	public void insertQA10ResponseState(UserVO userVO, HashMap<String, Object> insertQuery);

	public void insertQA10ErrorTableName(UserVO userVO, HashMap<String, Object> insertErrorTableName);

	public Long selectQA20ErrorLayerTbNamesCount(UserVO userVO, HashMap<String, Object> selectIdxQuery);

	public Long selectQA10ErrorLayerTbNamesCount(UserVO userVO, HashMap<String, Object> selectIdxQuery);

	public List<HashMap<String, Object>> selectAllQA10ValidateProgress(
			UserVO userVO, HashMap<String, Object> selectAllQA10ValidateProgress);

	public List<HashMap<String, Object>> selectAllQA20ValidateProgress(
			UserVO userVO, HashMap<String, Object> selectAllQA20ValidateProgress);

}
