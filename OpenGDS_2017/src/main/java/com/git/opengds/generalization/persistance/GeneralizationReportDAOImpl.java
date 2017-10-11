package com.git.opengds.generalization.persistance;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.git.opengds.common.DataSourceFactory;
import com.git.opengds.user.domain.UserVO;

@Repository
public class GeneralizationReportDAOImpl extends DataSourceFactory implements GeneralizationReportDAO {

	private SqlSession sqlSession;

	private static final String namespace = "com.git.mappers.generalization.GeneralizationResult";

	@Override
	public void insertGenResult(UserVO userVO, HashMap<String, Object> insertQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertGenResult", insertQuery);
	}
}
