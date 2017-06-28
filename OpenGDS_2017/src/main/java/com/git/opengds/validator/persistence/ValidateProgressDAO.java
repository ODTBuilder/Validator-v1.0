package com.git.opengds.validator.persistence;

import java.util.HashMap;

public interface ValidateProgressDAO {

	public Integer insertQA20RequestState(HashMap<String, Object> insertQuery);

	public Integer insertQA10RequestState(HashMap<String, Object> insertQuery);

	public int selectQA20ValidateProgressPid(HashMap<String, Object> selectQA20ValidateProgressPid);

	public int selectQA10ValidateProgressPid(HashMap<String, Object> selectQA10ValidateProgressPid);

	public void updateQA20ProgressingState(HashMap<String, Object> insertProgressingState);

	public void updateQA20ValidateSuccessState(HashMap<String, Object> insertProgressingState);

	public void updateQA20ValidateFailState(HashMap<String, Object> insertProgressingState);

	public void updateQA20ValidateErrLayerSuccess(HashMap<String, Object> updateProgressingState);

	public void updateQA20ValidateErrLayerFail(HashMap<String, Object> updateProgressingState);

	public void insertQA20ResponseState(HashMap<String, Object> insertQuery);

	public void insertQA20ErrorTableName(HashMap<String, Object> insertErrorTableName);

	public void updateQA10ProgressingState(HashMap<String, Object> insertProgressingState);

	public void updateQA10ValidateSuccessState(HashMap<String, Object> insertProgressingState);

	public void updateQA10ValidateFailState(HashMap<String, Object> insertProgressingState);

	public void updateQA10ValidateErrLayerSuccess(HashMap<String, Object> updateProgressingState);

	public void updateQA10ValidateErrLayerFail(HashMap<String, Object> updateProgressingState);

	public void insertQA10ResponseState(HashMap<String, Object> insertQuery);

	public void insertQA10ErrorTableName(HashMap<String, Object> insertErrorTableName);

	public Long selectQA20ErrorLayerTbNamesCount(HashMap<String, Object> selectIdxQuery);

	public Long selectQA10ErrorLayerTbNamesCount(HashMap<String, Object> selectIdxQuery);

}
