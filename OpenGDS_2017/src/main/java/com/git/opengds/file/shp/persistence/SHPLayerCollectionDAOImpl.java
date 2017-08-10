package com.git.opengds.file.shp.persistence;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.git.opengds.common.DataSourceFactory;
import com.git.opengds.user.domain.UserVO;

@Repository
public class SHPLayerCollectionDAOImpl extends DataSourceFactory implements SHPLayerCollectionDAO {

	private SqlSession sqlSession;

	private static final String namespace = "com.git.mappers.shpMappers.SHPLayerCollectionMapper";

	@Override
	public int insertSHPLayerCollection(UserVO userVO, HashMap<String, Object> insertCollectionQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertSHPLayerCollection", insertCollectionQuery);
		return (Integer) insertCollectionQuery.get("c_idx");
	}

	@Override
	public void createSHPLayerTb(UserVO userVO, HashMap<String, Object> createLayerQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(namespace + ".createSHPLayerTb", createLayerQuery);
	}

	@Override
	public void insertSHPLayer(UserVO userVO, HashMap<String, Object> insertLayerQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertSHPLayer", insertLayerQuery);
	}

	@Override
	public void insertSHPLayerMetadata(UserVO userVO, HashMap<String, Object> insertLayerMeteQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertSHPLayerMetadata", insertLayerMeteQuery);
	}
}
