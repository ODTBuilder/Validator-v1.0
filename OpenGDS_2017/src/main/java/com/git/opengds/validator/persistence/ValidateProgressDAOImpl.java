package com.git.opengds.validator.persistence;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.git.opengds.common.DataSourceFactory;
import com.git.opengds.user.domain.UserVO;

@Repository
public class ValidateProgressDAOImpl extends DataSourceFactory implements ValidateProgressDAO {

	private SqlSession sqlSession;

	private static final String qa10Namespace = "com.git.mappers.qa10Mappers.QA10LayerCollectionProgressMapper";

	private static final String qa20Namespace = "com.git.mappers.qa20Mappers.QA20LayerCollectionProgressMapper";

	/*public ValidateProgressDAOImpl(UserVO user) {
		// TODO Auto-generated constructor stub
		sqlSession = super.getSqlSession(user.getId());
	}
	*/
	@Override
	public Integer insertNGIRequestState(UserVO userVO, HashMap<String, Object> insertQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(qa20Namespace + ".insertQA20RequestState", insertQuery);
		return (Integer) insertQuery.get("p_idx");
	}

	@Override
	public Integer insertDXFRequestState(UserVO userVO, HashMap<String, Object> insertQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(qa10Namespace + ".insertQA10RequestState", insertQuery);
		return (Integer) insertQuery.get("p_idx");
	}

	@Override
	public int selectNGIValidateProgressPid(UserVO userVO, HashMap<String, Object> selectQA20ValidateProgressPid) {
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> idxMap = sqlSession.selectOne(qa20Namespace + ".selectQA20ValidateProgressPid",
				selectQA20ValidateProgressPid);
		return (Integer) idxMap.get("p_idx");
	}

	@Override
	public int selectDXFValidateProgressPid(UserVO userVO, HashMap<String, Object> selectQA10ValidateProgressPid) {
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> idxMap = sqlSession.selectOne(qa10Namespace + ".selectQA10ValidateProgressPid",
				selectQA10ValidateProgressPid);
		return (Integer) idxMap.get("p_idx");
	}

	@Override
	public void updateNGIProgressingState(UserVO userVO, HashMap<String, Object> insertProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(qa20Namespace + ".updateQA20ProgressingState", insertProgressingState);
	}

	@Override
	public void updateNGIValidateSuccessState(UserVO userVO, HashMap<String, Object> insertProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(qa20Namespace + ".updateQA20ValidateSuccessState", insertProgressingState);
	}

	@Override
	public void updateNGIValidateFailState(UserVO userVO, HashMap<String, Object> insertProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(qa20Namespace + ".updateQA20ValidateFailState", insertProgressingState);
	}

	@Override
	public void updateNGIValidateErrLayerSuccess(UserVO userVO, HashMap<String, Object> updateProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(qa20Namespace + ".updateQA20ValidateErrLayerSuccess", updateProgressingState);
	}

	@Override
	public void updateNGIValidateErrLayerFail(UserVO userVO, HashMap<String, Object> updateProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(qa20Namespace + ".updateQA20ValidateErrLayerFail", updateProgressingState);
	}

	@Override
	public void insertNGIResponseState(UserVO userVO, HashMap<String, Object> insertQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(qa20Namespace + ".updateQA20ResponseState", insertQuery);
	}

	@Override
	public void insertNGIErrorTableName(UserVO userVO, HashMap<String, Object> insertErrorTableName) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(qa20Namespace + ".updateQA20ErrorTableName", insertErrorTableName);
	}

	@Override
	public void updateDXFProgressingState(UserVO userVO, HashMap<String, Object> insertProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(qa10Namespace + ".updateQA10ProgressingState", insertProgressingState);
	}

	@Override
	public void updateDXFValidateSuccessState(UserVO userVO, HashMap<String, Object> insertProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(qa10Namespace + ".updateQA10ValidateSuccessState", insertProgressingState);
	}

	@Override
	public void updateDXFValidateFailState(UserVO userVO, HashMap<String, Object> insertProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(qa10Namespace + ".updateQA10ValidateFailState", insertProgressingState);
	}

	@Override
	public void updateDXFValidateErrLayerSuccess(UserVO userVO, HashMap<String, Object> updateProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(qa10Namespace + ".updateQA10ValidateErrLayerSuccess", updateProgressingState);
	}

	@Override
	public void updateDXFValidateErrLayerFail(UserVO userVO, HashMap<String, Object> updateProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(qa10Namespace + ".updateQA10ValidateErrLayerFail", updateProgressingState);
	}

	@Override
	public void insertDXFResponseState(UserVO userVO, HashMap<String, Object> insertQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(qa10Namespace + ".updateQA10ResponseState", insertQuery);
	}

	@Override
	public void insertDXFErrorTableName(UserVO userVO, HashMap<String, Object> insertErrorTableName) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(qa10Namespace + ".updateQA10ErrorTableName", insertErrorTableName);
	}

	@Override
	public Long selectNGIErrorLayerTbNamesCount(UserVO userVO, HashMap<String, Object> selectIdxQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> countMap = sqlSession.selectOne(qa20Namespace + ".selectQA20ErrorLayerTbNamesCount",
				selectIdxQuery);
		return (Long) countMap.get("count");

	}

	@Override
	public Long selectDXFErrorLayerTbNamesCount(UserVO userVO, HashMap<String, Object> selectIdxQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> countMap = sqlSession.selectOne(qa10Namespace + ".selectQA10ErrorLayerTbNamesCount",
				selectIdxQuery);
		return (Long) countMap.get("count");
	}

	@Override
	public List<HashMap<String, Object>> selectAllDXFValidateProgress(
			UserVO userVO, HashMap<String, Object> selectAllQA10ValidateProgress) {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectList(qa10Namespace + ".selectAllQA10ValidateProgress", selectAllQA10ValidateProgress);
	}

	@Override
	public List<HashMap<String, Object>> selectAllNGIValidateProgress(
			UserVO userVO, HashMap<String, Object> selectAllQA20ValidateProgress) {
		sqlSession = super.getSqlSession(userVO.getId());
		// TODO Auto-generated method stub
		return sqlSession.selectList(qa20Namespace + ".selectAllQA20ValidateProgress", selectAllQA20ValidateProgress);
	}

	@Override
	public void deleteDXFProgress(HashMap<String, Object> deleteValidateProgressQuery) {
		sqlSession.delete(qa10Namespace + ".deleteQA10Progress", deleteValidateProgressQuery);
	}

	@Override
	public void deleteNGIProgress(HashMap<String, Object> deleteValidateProgressQuery) {
		sqlSession.delete(qa10Namespace + ".deleteQA20Progress", deleteValidateProgressQuery);
	}
}
