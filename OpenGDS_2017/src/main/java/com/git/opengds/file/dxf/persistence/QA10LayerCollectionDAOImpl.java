package com.git.opengds.file.dxf.persistence;

import java.util.HashMap;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;

@Repository
public class QA10LayerCollectionDAOImpl implements QA10LayerCollectionDAO {

	@Inject
	private SqlSession sqlSession;

	private static final String namespace = "com.git.mappers.qa10Mappers.QA10LayerCollectionMapper";

	@Override
	public void createQA10LayerTb(HashMap<String, Object> qa10Layertb) throws PSQLException {
		sqlSession.update(namespace + ".createQA10LayerTb", qa10Layertb);
	}

	@Override
	public void insertQA10Layer(HashMap<String, Object> qa10Layer) {
		sqlSession.insert(namespace + ".insertQA10Layer", qa10Layer);
	}
}
