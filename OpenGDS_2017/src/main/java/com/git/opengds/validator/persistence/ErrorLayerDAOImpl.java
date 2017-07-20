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

package com.git.opengds.validator.persistence;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.git.opengds.common.DataSourceFactory;
import com.git.opengds.user.domain.UserVO;

@Repository
public class ErrorLayerDAOImpl extends DataSourceFactory implements ErrorLayerDAO {

	private SqlSession sqlSession;

	private static final String namespace = "com.git.mappers.errLayerMapper.ErrorLayerMapper";

/*	public ErrorLayerDAOImpl(UserVO user) {
		// TODO Auto-generated constructor stub
		sqlSession = super.getSqlSession(user.getId());
	}*/
	
	
	@Override
	public void createErrorLayerTb(UserVO userVO, HashMap<String, Object> createQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(namespace + ".createErrorLayerTb", createQuery);
	}

	@Override
	public void insertErrorFeature(UserVO userVO, HashMap<String, Object> insertQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertErrorFeature", insertQuery);
	}

	@Override
	public List<HashMap<String, Object>> selectErrorFeatures(UserVO userVO, HashMap<String, Object> selectQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectList(namespace + ".selectErrorFeatures", selectQuery);
	}

	@Override
	public List<HashMap<String, Object>> selectAllErrorFeatures(UserVO userVO, HashMap<String, Object> selectAllQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectList(namespace + ".selectAllErrorFeatures", selectAllQuery);
	}

	@Override
	public void dropErrorLayerTb(UserVO userVO, HashMap<String, Object> dropQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(namespace + ".dropErrorLayerTb", dropQuery);
	}

}
