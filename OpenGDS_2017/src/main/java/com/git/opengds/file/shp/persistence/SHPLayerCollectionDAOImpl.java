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
	public int createSHPLayerTb(UserVO userVO, HashMap<String, Object> createLayerQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.update(namespace + ".createSHPLayerTb", createLayerQuery);
	}

	@Override
	public int insertSHPLayer(UserVO userVO, HashMap<String, Object> insertLayerQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.insert(namespace + ".insertSHPLayer", insertLayerQuery);
	}

	@Override
	public int insertSHPLayerMetadata(UserVO userVO, HashMap<String, Object> insertLayerMeteQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.insert(namespace + ".insertSHPLayerMetadata", insertLayerMeteQuery);
	}

	@Override
	public int selectSHPFeatureIdx(UserVO userVO, HashMap<String, Object> selectIdxquery) {
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> idxMap = sqlSession.selectOne(namespace + ".selectSHPFeatureIdx", selectIdxquery);
		int idx = (Integer) idxMap.get("f_idx");
		return idx;
	}

	@Override
	public int deleteSHPFeature(UserVO userVO, HashMap<String, Object> deleteFeature) {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.delete(namespace + ".deleteSHPFeature", deleteFeature);
	}

	@Override
	public Integer selectSHPLayerCollectionIdx(UserVO userVO, HashMap<String, Object> selectLayerCollectionIdxQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> idxMap = sqlSession.selectOne(namespace + ".selectSHPLayerCollectionIdx",
				selectLayerCollectionIdxQuery);
		return (Integer) idxMap.get("c_idx");
	}

	@Override
	public Integer selectSHPLayerMetadataIdx(UserVO userVO, HashMap<String, Object> metadataIdxQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> idxMap = sqlSession.selectOne(namespace + ".selectSHPLayerMetadataIdx",
				metadataIdxQuery);
		return (Integer) idxMap.get("lm_idx");
	}

	@Override
	public int deleteSHPLayerMetadata(UserVO userVO, HashMap<String, Object> deleteLayerMetaQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.delete(namespace + ".deleteSHPLayerMetedata", deleteLayerMetaQuery);
	}

	@Override
	public int dropSHPLayer(UserVO userVO, HashMap<String, Object> dropQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.update(namespace + ".dropSHPLayer", dropQuery);
	}

	@Override
	public int deleteSHPLayerCollection(UserVO userVO, HashMap<String, Object> deleteLayerCollectionQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.delete(namespace + ".deleteSHPLayerCollection", deleteLayerCollectionQuery);
	}
}
