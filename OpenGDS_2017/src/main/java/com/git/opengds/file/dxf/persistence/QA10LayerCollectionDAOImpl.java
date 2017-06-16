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

	@Override
	public int insertQA10LayerCollection(HashMap<String, Object> insertCollectionQuery) {
		sqlSession.insert(namespace + ".insertQA10LayerCollection", insertCollectionQuery);
		return (Integer) insertCollectionQuery.get("c_idx");
	}

	@Override
	public void insertQA10LayerCollectionHeader(HashMap<String, Object> insertHeaderQuery) {
		sqlSession.insert(namespace + ".insertQA10LayerCollectionHeader", insertHeaderQuery);
	}

	@Override
	public int insertQA10LayerCollectionTables(HashMap<String, Object> tablesQuery) {
		sqlSession.insert(namespace + ".insertQA10LayerCollectionTables", tablesQuery);
		return (Integer) tablesQuery.get("tb_idx");
	}

	@Override
	public void insertQA10LayerCollectionLineTypes(HashMap<String, Object> lineTypesQuery) {
		sqlSession.insert(namespace + ".insertQA10LayerCollectionLineTypes", lineTypesQuery);
	}

	@Override
	public void insertQA10LayerCollectionLayers(HashMap<String, Object> layersQuery) {
		sqlSession.insert(namespace + ".insertQA10LayerCollectionLayers", layersQuery);
	}

	@Override
	public void insertQA10LayerCollecionStyles(HashMap<String, Object> stylesQuery) {
		sqlSession.insert(namespace + ".insertQA10LayerCollectionStyles", stylesQuery);
	}

	@Override
	public void insertQA10LayerMetadata(HashMap<String, Object> insertQueryMap) {
		sqlSession.insert(namespace + ".insertQA10LayerMetadata", insertQueryMap);
	}

	@Override
	public void insertQA10Feature(HashMap<String, Object> insertQuertMap) {
		sqlSession.insert(namespace + ".insertQA10Feature", insertQuertMap);
	}

	@Override
	public HashMap<String, Object> selectQA10FeatureIdx(HashMap<String, Object> selectIdxqueryMap) {
		return sqlSession.selectOne(namespace + ".selectFeatureIdx", selectIdxqueryMap);
	}

	@Override
	public int deleteQA10Feature(HashMap<String, Object> deleteQuery) {
		return sqlSession.delete(namespace + ".deleteFeature", deleteQuery);
	}
}
