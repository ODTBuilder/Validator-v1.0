package com.git.opengds.validator.persistence;

import java.util.HashMap;
import java.util.List;

import com.git.opengds.user.domain.UserVO;

public interface ValidateProgressDAO {

	public Integer insertNGIRequestState(UserVO userVO, HashMap<String, Object> insertQuery);

	public Integer insertDXFRequestState(UserVO userVO, HashMap<String, Object> insertQuery);

	public Integer insertSHPRequestState(UserVO userVO, HashMap<String, Object> insertQuery);

	public List<HashMap<String, Object>> selectNGIValidateProgressPid(UserVO userVO,
			HashMap<String, Object> selectQA20ValidateProgressPid);

	public List<HashMap<String, Object>> selectDXFValidateProgressPid(UserVO userVO,
			HashMap<String, Object> selectQA10ValidateProgressPid);

	public Integer selectSHPValidateProgressPid(UserVO userVO, HashMap<String, Object> selectQA10ValidateProgressPid);

	public void updateNGIProgressingState(UserVO userVO, HashMap<String, Object> insertProgressingState);

	public void updateNGIValidateSuccessState(UserVO userVO, HashMap<String, Object> insertProgressingState);

	public void updateNGIValidateFailState(UserVO userVO, HashMap<String, Object> insertProgressingState);

	public void updateNGIValidateErrLayerSuccess(UserVO userVO, HashMap<String, Object> updateProgressingState);

	public void updateNGIValidateErrLayerFail(UserVO userVO, HashMap<String, Object> updateProgressingState);

	public void insertNGIResponseState(UserVO userVO, HashMap<String, Object> insertQuery);

	public void insertNGIErrorTableName(UserVO userVO, HashMap<String, Object> insertErrorTableName);

	public void updateDXFProgressingState(UserVO userVO, HashMap<String, Object> insertProgressingState);

	public void updateDXFValidateSuccessState(UserVO userVO, HashMap<String, Object> insertProgressingState);

	public void updateDXFValidateFailState(UserVO userVO, HashMap<String, Object> insertProgressingState);

	public void updateDXFValidateErrLayerSuccess(UserVO userVO, HashMap<String, Object> updateProgressingState);

	public void updateDXFValidateErrLayerFail(UserVO userVO, HashMap<String, Object> updateProgressingState);

	public void insertDXFResponseState(UserVO userVO, HashMap<String, Object> insertQuery);

	public void insertDXFErrorTableName(UserVO userVO, HashMap<String, Object> insertErrorTableName);

	public void updateSHPProgressingState(UserVO userVO, HashMap<String, Object> insertProgressingState);

	public void updateSHPValidateSuccessState(UserVO userVO, HashMap<String, Object> insertProgressingState);

	public void updateSHPValidateFailState(UserVO userVO, HashMap<String, Object> insertProgressingState);

	public void updateSHPValidateErrLayerSuccess(UserVO userVO, HashMap<String, Object> updateProgressingState);

	public void updateSHPValidateErrLayerFail(UserVO userVO, HashMap<String, Object> updateProgressingState);

	public void insertSHPResponseState(UserVO userVO, HashMap<String, Object> insertQuery);

	public void insertSHPErrorTableName(UserVO userVO, HashMap<String, Object> insertErrorTableName);

	public List<HashMap<String, Object>> selectAllDXFValidateProgress(UserVO userVO,
			HashMap<String, Object> selectAllQA10ValidateProgress);

	public List<HashMap<String, Object>> selectAllNGIValidateProgress(UserVO userVO,
			HashMap<String, Object> selectAllQA20ValidateProgress);

	public void deleteDXFProgress(HashMap<String, Object> deleteValidateProgressQuery);

	public void deleteNGIProgress(HashMap<String, Object> deleteValidateProgressQuery);

	public List<HashMap<String, Object>> selectAllSHPValidateProgress(UserVO userVO,
			Object selectAllSHPValidateProgress);

	public Long selectErrorLayerTbNamesCount(UserVO userVO, HashMap<String, Object> selectIdxQuery);

}
