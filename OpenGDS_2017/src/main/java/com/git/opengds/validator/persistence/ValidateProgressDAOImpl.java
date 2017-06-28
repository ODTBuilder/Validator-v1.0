package com.git.opengds.validator.persistence;

import java.util.HashMap;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class ValidateProgressDAOImpl implements ValidateProgressDAO {

	@Inject
	private SqlSession sqlSession;

	private static final String qa10Namespace = "com.git.mappers.qa10Mappers.QA10LayerCollectionProgressMapper";

	private static final String qa20Namespace = "com.git.mappers.qa20Mappers.QA20LayerCollectionProgressMapper";

	@Override
	public Integer insertQA20RequestState(HashMap<String, Object> insertQuery) {
		sqlSession.insert(qa20Namespace + ".insertQA20RequestState", insertQuery);
		return (Integer) insertQuery.get("p_idx");
	}

	@Override
	public Integer insertQA10RequestState(HashMap<String, Object> insertQuery) {
		sqlSession.insert(qa10Namespace + ".insertQA10RequestState", insertQuery);
		return (Integer) insertQuery.get("p_idx");
	}

	@Override
	public int selectQA20ValidateProgressPid(HashMap<String, Object> selectQA20ValidateProgressPid) {
		HashMap<String, Object> idxMap = sqlSession.selectOne(qa20Namespace + ".selectQA20ValidateProgressPid",
				selectQA20ValidateProgressPid);
		return (Integer) idxMap.get("p_idx");
	}

	@Override
	public int selectQA10ValidateProgressPid(HashMap<String, Object> selectQA10ValidateProgressPid) {
		HashMap<String, Object> idxMap = sqlSession.selectOne(qa10Namespace + ".selectQA10ValidateProgressPid",
				selectQA10ValidateProgressPid);
		return (Integer) idxMap.get("p_idx");
	}

	@Override
	public void updateQA20ProgressingState(HashMap<String, Object> insertProgressingState) {
		sqlSession.update(qa20Namespace + ".updateQA20ProgressingState", insertProgressingState);
	}

	@Override
	public void updateQA20ValidateSuccessState(HashMap<String, Object> insertProgressingState) {
		sqlSession.update(qa20Namespace + ".updateQA20ValidateSuccessState", insertProgressingState);
	}

	@Override
	public void updateQA20ValidateFailState(HashMap<String, Object> insertProgressingState) {
		sqlSession.update(qa20Namespace + ".updateQA20ValidateFailState", insertProgressingState);
	}

	@Override
	public void updateQA20ValidateErrLayerSuccess(HashMap<String, Object> updateProgressingState) {
		sqlSession.update(qa20Namespace + ".updateQA20ValidateErrLayerSuccess", updateProgressingState);
	}

	@Override
	public void updateQA20ValidateErrLayerFail(HashMap<String, Object> updateProgressingState) {
		sqlSession.update(qa20Namespace + ".updateQA20ValidateErrLayerFail", updateProgressingState);
	}

	@Override
	public void insertQA20ResponseState(HashMap<String, Object> insertQuery) {
		sqlSession.insert(qa20Namespace + ".updateQA20ResponseState", insertQuery);
	}

	@Override
	public void insertQA20ErrorTableName(HashMap<String, Object> insertErrorTableName) {
		sqlSession.insert(qa20Namespace + ".updateQA20ErrorTableName", insertErrorTableName);
	}

	@Override
	public void updateQA10ProgressingState(HashMap<String, Object> insertProgressingState) {
		sqlSession.update(qa10Namespace + ".updateQA10ProgressingState", insertProgressingState);
	}

	@Override
	public void updateQA10ValidateSuccessState(HashMap<String, Object> insertProgressingState) {
		sqlSession.update(qa10Namespace + ".updateQA10ValidateSuccessState", insertProgressingState);
	}

	@Override
	public void updateQA10ValidateFailState(HashMap<String, Object> insertProgressingState) {
		sqlSession.update(qa10Namespace + ".updateQA10ValidateFailState", insertProgressingState);
	}

	@Override
	public void updateQA10ValidateErrLayerSuccess(HashMap<String, Object> updateProgressingState) {
		sqlSession.update(qa10Namespace + ".updateQA10ValidateErrLayerSuccess", updateProgressingState);
	}

	@Override
	public void updateQA10ValidateErrLayerFail(HashMap<String, Object> updateProgressingState) {
		sqlSession.update(qa10Namespace + ".updateQA10ValidateErrLayerFail", updateProgressingState);
	}

	@Override
	public void insertQA10ResponseState(HashMap<String, Object> insertQuery) {
		sqlSession.insert(qa10Namespace + ".updateQA10ResponseState", insertQuery);
	}

	@Override
	public void insertQA10ErrorTableName(HashMap<String, Object> insertErrorTableName) {
		sqlSession.insert(qa10Namespace + ".updateQA10ErrorTableName", insertErrorTableName);
	}

	@Override
	public Long selectQA20ErrorLayerTbNamesCount(HashMap<String, Object> selectIdxQuery) {
		HashMap<String, Object> countMap = sqlSession.selectOne(qa20Namespace + ".selectQA20ErrorLayerTbNamesCount",
				selectIdxQuery);
		return (Long) countMap.get("count");

	}

	@Override
	public Long selectQA10ErrorLayerTbNamesCount(HashMap<String, Object> selectIdxQuery) {
		HashMap<String, Object> countMap = sqlSession.selectOne(qa10Namespace + ".selectQA10ErrorLayerTbNamesCount",
				selectIdxQuery);
		return (Long) countMap.get("count");
	}
}
