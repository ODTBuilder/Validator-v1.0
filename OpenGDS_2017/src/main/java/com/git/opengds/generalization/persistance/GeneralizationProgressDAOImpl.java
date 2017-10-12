package com.git.opengds.generalization.persistance;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.git.opengds.common.DataSourceFactory;
import com.git.opengds.user.domain.UserVO;

@Repository
public class GeneralizationProgressDAOImpl extends DataSourceFactory implements GeneralizationProgressDAO {

	private SqlSession sqlSession;

	private static final String genNamespace = "com.git.mappers.generalization.GeneralizationProgressMapper";

	@Override
	public Long selectGenLayerTablesCount(UserVO userVO, HashMap<String, Object> selectTbCountQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> countMap = sqlSession.selectOne(genNamespace + ".selectGenTbNamesCount",
				selectTbCountQuery);
		return (Long) countMap.get("count");
	}

	@Override
	public Integer insertRequestState(UserVO userVO, HashMap<String, Object> insertQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(genNamespace + ".insertGenRequestState", insertQuery);
		return (Integer) insertQuery.get("p_idx");
	}

	@Override
	public void updateProgressingState(UserVO userVO, HashMap<String, Object> updateQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(genNamespace + ".updateGenProgressingState", updateQuery);
	}

	@Override
	public void insertGenResponseState(UserVO userVO, HashMap<String, Object> insertQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(genNamespace + ".updateGenResponseState", insertQuery);
	}
}
