package com.git.opengds.validator.persistence;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.git.opengds.common.DataSourceFactory;
import com.git.opengds.user.domain.UserVO;

@Repository
public class ValidateProgressDAOImpl extends DataSourceFactory implements ValidateProgressDAO {

	private SqlSession sqlSession;

	private static final String shpNamespace = "com.git.mappers.shpMappers.SHPLayerCollectionProgressMapper";

	@Override
	public Integer insertSHPRequestState(UserVO userVO, HashMap<String, Object> insertQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(shpNamespace + ".insertSHPRequestState", insertQuery);
		return (Integer) insertQuery.get("p_idx");
	}


	@Override
	public Integer selectSHPValidateProgressPid(UserVO userVO, HashMap<String, Object> selectSHPValidateProgressPid) {
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> idxMap = sqlSession.selectOne(shpNamespace + ".selectSHPValidateProgressPid",
				selectSHPValidateProgressPid);
		if (idxMap != null) {
			return (Integer) idxMap.get("p_idx");
		} else {
			return null;
		}
	}

	@Override
	public void updateSHPProgressingState(UserVO userVO, HashMap<String, Object> insertProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(shpNamespace + ".updateSHPProgressingState", insertProgressingState);
	}

	@Override
	public void updateSHPValidateSuccessState(UserVO userVO, HashMap<String, Object> insertProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(shpNamespace + ".updateSHPValidateSuccessState", insertProgressingState);
	}

	@Override
	public void updateSHPValidateFailState(UserVO userVO, HashMap<String, Object> insertProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(shpNamespace + ".updateSHPValidateFailState", insertProgressingState);
	}

	@Override
	public void updateSHPValidateErrLayerSuccess(UserVO userVO, HashMap<String, Object> updateProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(shpNamespace + ".updateSHPValidateErrLayerSuccess", updateProgressingState);
	}

	@Override
	public void updateSHPValidateErrLayerFail(UserVO userVO, HashMap<String, Object> updateProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(shpNamespace + ".updateSHPValidateErrLayerFail", updateProgressingState);
	}

	@Override
	public void insertSHPResponseState(UserVO userVO, HashMap<String, Object> insertQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(shpNamespace + ".updateSHPResponseState", insertQuery);
	}

	@Override
	public void insertSHPErrorTableName(UserVO userVO, HashMap<String, Object> insertErrorTableName) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(shpNamespace + ".updateSHPErrorTableName", insertErrorTableName);
	}

	@Override
	public List<HashMap<String, Object>> selectAllSHPValidateProgress(UserVO userVO,
			Object selectAllSHPValidateProgress) {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectList(shpNamespace + ".selectAllSHPValidateProgress", selectAllSHPValidateProgress);
	}

	@Override
	public Long selectErrorLayerTbNamesCount(UserVO userVO, String fileType, HashMap<String, Object> selectIdxQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> countMap = null;
		if (fileType.equals("shp")) {
			countMap = sqlSession.selectOne(shpNamespace + ".selectSHPErrorLayerTbNamesCount", selectIdxQuery);
		}
		return (Long) countMap.get("count");
	}
}
