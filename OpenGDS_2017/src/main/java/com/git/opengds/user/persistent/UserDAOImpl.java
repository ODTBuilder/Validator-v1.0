/*
 *    OpenGDS/Builder
 *    http://git.co.kr
 *
 *    (C) 2014-2017, GeoSpatial Information Technology(GIT)
 *    
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 3 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */

package com.git.opengds.user.persistent;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.git.opengds.user.domain.UserVO;

/**
 * 파일에 대한 DB처리를 하는 클래스
 * @author SG.Lee
 * @Date 2017. 5. 12. 오전 2:24:03
 * */
@Repository
public class UserDAOImpl implements UserDAO {
	@Inject
	@Resource(name="userSqlSession")
	private SqlSession sqlSession;

	
	
	private static final String namespace = "com.git.mappers.userMappers.UserMapper";

	/**
	 * @since 2017. 4
	 * @author SG.Lee
	 * @param fileName
	 * @return
	 * @see com.git.opengds.upload.persistence.FileDAO#selectDuplicateCheck(java.lang.String)
	 */
	@Override
	public UserVO loginUserByInfo(HashMap<String, Object> infoMap){
		UserVO userVO = sqlSession.selectOne(namespace + ".loginUserByInfo", infoMap);
		
		return userVO;
	}
	
}
