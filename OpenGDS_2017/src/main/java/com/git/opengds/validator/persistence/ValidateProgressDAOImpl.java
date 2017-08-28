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

	private static final String dxfNamespace = "com.git.mappers.dxfMappers.DXFLayerCollectionProgressMapper";

	private static final String ngiNamespace = "com.git.mappers.ngiMappers.NGILayerCollectionProgressMapper";

	private static final String shpNamespace = "com.git.mappers.shpMappers.SHPLayerCollectionProgressMapper";

	/*
	 * public ValidateProgressDAOImpl(UserVO user) { // TODO Auto-generated
	 * constructor stub sqlSession = super.getSqlSession(user.getId()); }
	 */
	@Override
	public Integer insertNGIRequestState(UserVO userVO, HashMap<String, Object> insertQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(ngiNamespace + ".insertQA20RequestState", insertQuery);
		return (Integer) insertQuery.get("p_idx");
	}

	@Override
	public Integer insertDXFRequestState(UserVO userVO, HashMap<String, Object> insertQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(dxfNamespace + ".insertQA10RequestState", insertQuery);
		return (Integer) insertQuery.get("p_idx");
	}

	@Override
	public Integer insertSHPRequestState(UserVO userVO, HashMap<String, Object> insertQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(shpNamespace + ".insertSHPRequestState", insertQuery);
		return (Integer) insertQuery.get("p_idx");
	}

	@Override
	public int selectNGIValidateProgressPid(UserVO userVO, HashMap<String, Object> selectQA20ValidateProgressPid) {
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> idxMap = sqlSession.selectOne(ngiNamespace + ".selectQA20ValidateProgressPid",
				selectQA20ValidateProgressPid);
		return (Integer) idxMap.get("p_idx");
	}

	@Override
	public int selectDXFValidateProgressPid(UserVO userVO, HashMap<String, Object> selectQA10ValidateProgressPid) {
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> idxMap = sqlSession.selectOne(dxfNamespace + ".selectQA10ValidateProgressPid",
				selectQA10ValidateProgressPid);
		return (Integer) idxMap.get("p_idx");
	}

	@Override
	public int selectSHPValidateProgressPid(UserVO userVO, HashMap<String, Object> selectSHPValidateProgressPid) {
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> idxMap = sqlSession.selectOne(shpNamespace + ".selectSHPValidateProgressPid",
				selectSHPValidateProgressPid);
		return (Integer) idxMap.get("p_idx");
	}

	@Override
	public void updateNGIProgressingState(UserVO userVO, HashMap<String, Object> insertProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(ngiNamespace + ".updateQA20ProgressingState", insertProgressingState);
	}

	@Override
	public void updateNGIValidateSuccessState(UserVO userVO, HashMap<String, Object> insertProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(ngiNamespace + ".updateQA20ValidateSuccessState", insertProgressingState);
	}

	@Override
	public void updateNGIValidateFailState(UserVO userVO, HashMap<String, Object> insertProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(ngiNamespace + ".updateQA20ValidateFailState", insertProgressingState);
	}

	@Override
	public void updateNGIValidateErrLayerSuccess(UserVO userVO, HashMap<String, Object> updateProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(ngiNamespace + ".updateQA20ValidateErrLayerSuccess", updateProgressingState);
	}

	@Override
	public void updateNGIValidateErrLayerFail(UserVO userVO, HashMap<String, Object> updateProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(ngiNamespace + ".updateQA20ValidateErrLayerFail", updateProgressingState);
	}

	@Override
	public void insertNGIResponseState(UserVO userVO, HashMap<String, Object> insertQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(ngiNamespace + ".updateQA20ResponseState", insertQuery);
	}

	@Override
	public void insertNGIErrorTableName(UserVO userVO, HashMap<String, Object> insertErrorTableName) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(ngiNamespace + ".updateQA20ErrorTableName", insertErrorTableName);
	}

	@Override
	public void updateDXFProgressingState(UserVO userVO, HashMap<String, Object> insertProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(dxfNamespace + ".updateQA10ProgressingState", insertProgressingState);
	}

	@Override
	public void updateDXFValidateSuccessState(UserVO userVO, HashMap<String, Object> insertProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(dxfNamespace + ".updateQA10ValidateSuccessState", insertProgressingState);
	}

	@Override
	public void updateDXFValidateFailState(UserVO userVO, HashMap<String, Object> insertProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(dxfNamespace + ".updateQA10ValidateFailState", insertProgressingState);
	}

	@Override
	public void updateDXFValidateErrLayerSuccess(UserVO userVO, HashMap<String, Object> updateProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(dxfNamespace + ".updateQA10ValidateErrLayerSuccess", updateProgressingState);
	}

	@Override
	public void updateDXFValidateErrLayerFail(UserVO userVO, HashMap<String, Object> updateProgressingState) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(dxfNamespace + ".updateQA10ValidateErrLayerFail", updateProgressingState);
	}

	@Override
	public void insertDXFResponseState(UserVO userVO, HashMap<String, Object> insertQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(dxfNamespace + ".updateQA10ResponseState", insertQuery);
	}

	@Override
	public void insertDXFErrorTableName(UserVO userVO, HashMap<String, Object> insertErrorTableName) {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(dxfNamespace + ".updateQA10ErrorTableName", insertErrorTableName);
	}

	@Override
	public Long selectNGIErrorLayerTbNamesCount(UserVO userVO, HashMap<String, Object> selectIdxQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> countMap = sqlSession.selectOne(ngiNamespace + ".selectQA20ErrorLayerTbNamesCount",
				selectIdxQuery);
		return (Long) countMap.get("count");

	}

	@Override
	public Long selectDXFErrorLayerTbNamesCount(UserVO userVO, HashMap<String, Object> selectIdxQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> countMap = sqlSession.selectOne(dxfNamespace + ".selectQA10ErrorLayerTbNamesCount",
				selectIdxQuery);
		return (Long) countMap.get("count");
	}

	@Override
	public List<HashMap<String, Object>> selectAllDXFValidateProgress(UserVO userVO,
			HashMap<String, Object> selectAllQA10ValidateProgress) {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectList(dxfNamespace + ".selectAllQA10ValidateProgress", selectAllQA10ValidateProgress);
	}

	@Override
	public List<HashMap<String, Object>> selectAllNGIValidateProgress(UserVO userVO,
			HashMap<String, Object> selectAllQA20ValidateProgress) {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectList(ngiNamespace + ".selectAllQA20ValidateProgress", selectAllQA20ValidateProgress);
	}

	@Override
	public void deleteDXFProgress(HashMap<String, Object> deleteValidateProgressQuery) {
		sqlSession.delete(dxfNamespace + ".deleteQA10Progress", deleteValidateProgressQuery);
	}

	@Override
	public void deleteNGIProgress(HashMap<String, Object> deleteValidateProgressQuery) {
		sqlSession.delete(dxfNamespace + ".deleteQA20Progress", deleteValidateProgressQuery);
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
	public Long selectSHPErrorLayerTbNamesCount(UserVO userVO, HashMap<String, Object> selectIdxQuery) {
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> countMap = sqlSession.selectOne(shpNamespace + ".selectSHPErrorLayerTbNamesCount",
				selectIdxQuery);
		return (Long) countMap.get("count");
	}
}
