package com.git.opengds.generalization.persistance;

import java.util.HashMap;

import com.git.opengds.user.domain.UserVO;

public interface GeneralizationReportDAO {

	void insertGenResult(UserVO userVO, HashMap<String, Object> insertQuery);

}
