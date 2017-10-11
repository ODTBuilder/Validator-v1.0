package com.git.opengds.generalization.persistance;

import java.util.HashMap;

import com.git.opengds.user.domain.UserVO;

public interface GeneralizationProgressDAO {

	public Long selectGenLayerTablesCount(UserVO userVO, HashMap<String, Object> selectTbCountQuery);

	public Integer insertRequestState(UserVO userVO, HashMap<String, Object> insertQuery);

	public void getUpdateProgressingState(UserVO userVO, HashMap<String, Object> insertQuery);

	public void insertGenTableName(UserVO userVO, Object insertGenTableName);

}
