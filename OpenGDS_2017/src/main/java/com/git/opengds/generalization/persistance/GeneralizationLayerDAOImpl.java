package com.git.opengds.generalization.persistance;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.git.opengds.common.DataSourceFactory;
import com.git.opengds.user.domain.UserVO;

@Repository
public class GeneralizationLayerDAOImpl extends DataSourceFactory implements GeneralizationLayerDAO {

	private SqlSession sqlSession;

	private static final String namespace = "com.git.mappers.generalization.GenLayerMapper";

	@Override
	public void createGenLayerTb(UserVO userVO, HashMap<String, Object> createTbQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(namespace + ".createGenLayerTb", createTbQuery);
	}

	@Override
	public void insertGenFeature(UserVO userVO, HashMap<String, Object> insertQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertGenFeature", insertQuery);
	}

}
