package com.git.opengds.user.service;

import java.util.HashMap;

import com.git.opengds.user.domain.UserVO;

/**
 * 사용자와 관련된 데이터를 처리한다.
 * @author SG.Lee
 * @Date 2017. 7. 17. 오후 2:34:41
 * */
public interface UserService {
	/**
	 *
	 * @author SG.Lee
	 * @Date 2017. 7. 17. 오후 2:34:01
	 * @param infoMap - 로그인정보
	 * @return
	 * @throws Exception UserVO
	 * */
	UserVO loginUserByInfo(HashMap<String, Object> infoMap);
}
