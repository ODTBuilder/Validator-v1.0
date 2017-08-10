package com.git.opengds.validator.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.git.gdsbuilder.type.validate.collection.ValidateProgress;
import com.git.gdsbuilder.type.validate.collection.ValidateProgressList;
import com.git.opengds.file.dxf.dbManager.DXFDBQueryManager;
import com.git.opengds.file.dxf.persistence.DXFLayerCollectionDAO;
import com.git.opengds.file.ngi.dbManager.NGIDBQueryManager;
import com.git.opengds.file.ngi.persistence.NGILayerCollectionDAO;
import com.git.opengds.user.domain.UserVO;
import com.git.opengds.validator.dbManager.ValidateProgressDBQueryManager;
import com.git.opengds.validator.persistence.ValidateProgressDAO;

@Service
public class ValidatorProgressServiceImpl implements ValidatorProgressService {

	/*
	 * @Inject private DataSourceTransactionManager txManager;
	 */

	@Inject
	private DXFLayerCollectionDAO dxfDAO;

	@Inject
	private NGILayerCollectionDAO ngiDAO;

	@Inject
	private ValidateProgressDAO progressDAO;

	/*
	 * public ValidatorProgressServiceImpl(UserVO userVO) { // TODO
	 * Auto-generated constructor stub qa10DAO = new
	 * QA10LayerCollectionDAOImpl(userVO); qa20DAO = new
	 * QA20LayerCollectionDAOImpl(userVO); progressDAO = new
	 * ValidateProgressDAOImpl(userVO); }
	 */

	public Integer setStateToRequest(UserVO userVO, int validateStart, String collectionName, String fileType) {

		Integer pIdx = null;
		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		int cidx = 0;
		if (fileType.equals("ngi")) {
			NGIDBQueryManager qa20QueryManager = new NGIDBQueryManager();
			cidx = ngiDAO.selectNGILayerCollectionIdx(userVO,
					qa20QueryManager.getSelectNGILayerCollectionIdx(collectionName));
			HashMap<String, Object> insertQuery = queryManager.getInsertNGIRequestState(validateStart, collectionName,
					fileType, cidx);
			pIdx = progressDAO.insertNGIRequestState(userVO, insertQuery);
		} else if (fileType.equals("dxf")) {
			DXFDBQueryManager qa10QueryManager = new DXFDBQueryManager();
			cidx = dxfDAO.selectDXFLayerCollectionIdx(userVO,
					qa10QueryManager.getSelectDXFLayerCollectionIdxQuery(collectionName));
			HashMap<String, Object> insertQuery = queryManager.getInsertDXFRequestState(validateStart, collectionName,
					fileType, cidx);
			pIdx = progressDAO.insertDXFRequestState(userVO, insertQuery);
		}
		return pIdx;
	}

	public void setStateToProgressing(UserVO userVO, int validateStart, String fileType, int pIdx) {

		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("ngi")) {
			progressDAO.updateNGIProgressingState(userVO,
					queryManager.getUpdateNGIProgressingState(pIdx, validateStart));
		} else if (fileType.equals("dxf")) {
			progressDAO.updateDXFProgressingState(userVO,
					queryManager.getUpdateDXFProgressingState(pIdx, validateStart));
		}
	}

	@Override
	public void setStateToValidateSuccess(UserVO userVO, int validateSuccess, String fileType, int pIdx) {
		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("ngi")) {
			progressDAO.updateNGIValidateSuccessState(userVO,
					queryManager.getUpdateNGIProgressingState(pIdx, validateSuccess));
		} else if (fileType.equals("dxf")) {
			progressDAO.updateDXFValidateSuccessState(userVO,
					queryManager.getUpdateDXFProgressingState(pIdx, validateSuccess));
		}
	}

	@Override
	public void setStateToValidateFail(UserVO userVO, int validateFail, String fileType, int pIdx) {
		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("ngi")) {
			progressDAO.updateNGIValidateFailState(userVO,
					queryManager.getUpdateNGIProgressingState(pIdx, validateFail));
		} else if (fileType.equals("dxf")) {
			progressDAO.updateDXFValidateFailState(userVO,
					queryManager.getUpdateDXFProgressingState(pIdx, validateFail));
		}
	}

	@Override
	public void setStateToErrLayerSuccess(UserVO userVO, int errLayerSuccess, String fileType, int pIdx,
			String tableName) {
		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("ngi")) {
			progressDAO.updateNGIValidateErrLayerSuccess(userVO,
					queryManager.getUpdateNGIProgressingState(pIdx, errLayerSuccess));
			progressDAO.insertNGIErrorTableName(userVO, queryManager.getInsertNGIErrorTableName(pIdx, tableName));
		} else if (fileType.equals("dxf")) {
			progressDAO.updateDXFValidateErrLayerSuccess(userVO,
					queryManager.getUpdateDXFProgressingState(pIdx, errLayerSuccess));
			progressDAO.insertDXFErrorTableName(userVO, queryManager.getInsertQA10ErrorTableName(pIdx, tableName));
		}
	}

	@Override
	public void setStateToErrLayerFail(UserVO userVO, int errLayerFail, String fileType, int pIdx) {
		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("ngi")) {
			progressDAO.updateNGIValidateErrLayerFail(userVO,
					queryManager.getUpdateNGIProgressingState(pIdx, errLayerFail));
		} else if (fileType.equals("dxf")) {
			progressDAO.updateDXFValidateErrLayerFail(userVO,
					queryManager.getUpdateDXFProgressingState(pIdx, errLayerFail));
		}
	}

	@Override
	public void setStateToResponse(UserVO userVO, String fileType, int pIdx) {

		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("ngi")) {
			HashMap<String, Object> insertQuery = queryManager.getInsertNGIResponseState(pIdx);
			progressDAO.insertNGIResponseState(userVO, insertQuery);
		} else if (fileType.equals("dxf")) {
			HashMap<String, Object> insertQuery = queryManager.getInsertQA10ResponseState(pIdx);
			progressDAO.insertDXFResponseState(userVO, insertQuery);
		}
	}

	public ValidateProgressList selectProgressOfCollection(UserVO userVO, String type) {

		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		List<HashMap<String, Object>> progressListMap = null;
		if (type.equals("ngi")) {
			progressListMap = progressDAO.selectAllNGIValidateProgress(userVO,
					queryManager.getSelectAllQA20ValidateProgress());
		} else if (type.equals("dxf")) {
			progressListMap = progressDAO.selectAllDXFValidateProgress(userVO,
					queryManager.getSelectAllQA10ValidateProgress());
		}
		ValidateProgressList progressList = new ValidateProgressList();
		for (int i = 0; i < progressListMap.size(); i++) {
			ValidateProgress progress = new ValidateProgress();
			HashMap<String, Object> progressMap = progressListMap.get(i);
			progress.setpIdx((Integer) progressMap.get("p_idx"));
			progress.setCollectionName((String) progressMap.get("collection_name"));
			progress.setFileType((String) progressMap.get("file_type"));
			progress.setState((Integer) progressMap.get("state"));

			Object requestTime = progressMap.get("request_time");
			if (requestTime != null) {
				progress.setRequestTime(progressMap.get("request_time").toString());
			} else {
				progress.setRequestTime("");
			}
			Object responseTime = progressMap.get("response_time");
			if (responseTime != null) {
				progress.setResponseTime(progressMap.get("response_time").toString());
			} else {
				progress.setResponseTime("");
			}
			Object errLayerName = progressMap.get("err_layer_name");
			if (errLayerName != null) {
				progress.setErrLayerName(progressMap.get("err_layer_name").toString());
			} else {
				progress.setErrLayerName("");
			}
			progressList.add(progress);
		}
		return progressList;
	}
}
