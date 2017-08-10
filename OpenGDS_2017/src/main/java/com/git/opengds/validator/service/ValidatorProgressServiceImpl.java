package com.git.opengds.validator.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.type.validate.collection.ValidateProgress;
import com.git.gdsbuilder.type.validate.collection.ValidateProgressList;
import com.git.opengds.file.dxf.dbManager.DXFDBQueryManager;
import com.git.opengds.file.dxf.persistence.DXFLayerCollectionDAO;
import com.git.opengds.file.dxf.persistence.DXFLayerCollectionDAOImpl;
import com.git.opengds.file.ngi.dbManager.NGIDBQueryManager;
import com.git.opengds.file.ngi.persistence.NGILayerCollectionDAO;
import com.git.opengds.file.ngi.persistence.NGILayerCollectionDAOImpl;
import com.git.opengds.user.domain.UserVO;
import com.git.opengds.validator.dbManager.ValidateProgressDBQueryManager;
import com.git.opengds.validator.persistence.ValidateProgressDAO;
import com.git.opengds.validator.persistence.ValidateProgressDAOImpl;

@Service
public class ValidatorProgressServiceImpl implements ValidatorProgressService {

	/*
	 * @Inject private DataSourceTransactionManager txManager;
	 */

	@Inject
	private DXFLayerCollectionDAO qa10DAO;

	@Inject
	private NGILayerCollectionDAO qa20DAO;

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
			cidx = qa20DAO.selectNGILayerCollectionIdx(userVO,
					qa20QueryManager.getSelectQA20LayerCollectionIdx(collectionName));
			HashMap<String, Object> insertQuery = queryManager.getInsertQA20RequestState(validateStart, collectionName,
					fileType, cidx);
			pIdx = progressDAO.insertQA20RequestState(userVO, insertQuery);
		} else if (fileType.equals("dxf")) {
			DXFDBQueryManager qa10QueryManager = new DXFDBQueryManager();
			cidx = qa10DAO.selectDXFLayerCollectionIdx(userVO,
					qa10QueryManager.getSelectDXFLayerCollectionIdxQuery(collectionName));
			HashMap<String, Object> insertQuery = queryManager.getInsertQA10RequestState(validateStart, collectionName,
					fileType, cidx);
			pIdx = progressDAO.insertQA10RequestState(userVO, insertQuery);
		}
		return pIdx;
	}

	public void setStateToProgressing(UserVO userVO, int validateStart, String fileType, int pIdx) {

		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("ngi")) {
			progressDAO.updateQA20ProgressingState(userVO,
					queryManager.getUpdateQA20ProgressingState(pIdx, validateStart));
		} else if (fileType.equals("dxf")) {
			progressDAO.updateQA10ProgressingState(userVO,
					queryManager.getUpdateQA10ProgressingState(pIdx, validateStart));
		}
	}

	@Override
	public void setStateToValidateSuccess(UserVO userVO, int validateSuccess, String fileType, int pIdx) {
		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("ngi")) {
			progressDAO.updateQA20ValidateSuccessState(userVO,
					queryManager.getUpdateQA20ProgressingState(pIdx, validateSuccess));
		} else if (fileType.equals("dxf")) {
			progressDAO.updateQA10ValidateSuccessState(userVO,
					queryManager.getUpdateQA10ProgressingState(pIdx, validateSuccess));
		}
	}

	@Override
	public void setStateToValidateFail(UserVO userVO, int validateFail, String fileType, int pIdx) {
		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("ngi")) {
			progressDAO.updateQA20ValidateFailState(userVO,
					queryManager.getUpdateQA20ProgressingState(pIdx, validateFail));
		} else if (fileType.equals("dxf")) {
			progressDAO.updateQA10ValidateFailState(userVO,
					queryManager.getUpdateQA10ProgressingState(pIdx, validateFail));
		}
	}

	@Override
	public void setStateToErrLayerSuccess(UserVO userVO, int errLayerSuccess, String fileType, int pIdx,
			String tableName) {
		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("ngi")) {
			progressDAO.updateQA20ValidateErrLayerSuccess(userVO,
					queryManager.getUpdateQA20ProgressingState(pIdx, errLayerSuccess));
			progressDAO.insertQA20ErrorTableName(userVO, queryManager.getInsertQA20ErrorTableName(pIdx, tableName));
		} else if (fileType.equals("dxf")) {
			progressDAO.updateQA10ValidateErrLayerSuccess(userVO,
					queryManager.getUpdateQA10ProgressingState(pIdx, errLayerSuccess));
			progressDAO.insertQA10ErrorTableName(userVO, queryManager.getInsertQA10ErrorTableName(pIdx, tableName));
		}
	}

	@Override
	public void setStateToErrLayerFail(UserVO userVO, int errLayerFail, String fileType, int pIdx) {
		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("ngi")) {
			progressDAO.updateQA20ValidateErrLayerFail(userVO,
					queryManager.getUpdateQA20ProgressingState(pIdx, errLayerFail));
		} else if (fileType.equals("dxf")) {
			progressDAO.updateQA10ValidateErrLayerFail(userVO,
					queryManager.getUpdateQA10ProgressingState(pIdx, errLayerFail));
		}
	}

	@Override
	public void setStateToResponse(UserVO userVO, String fileType, int pIdx) {

		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("ngi")) {
			HashMap<String, Object> insertQuery = queryManager.getInsertQA20ResponseState(pIdx);
			progressDAO.insertQA20ResponseState(userVO, insertQuery);
		} else if (fileType.equals("dxf")) {
			HashMap<String, Object> insertQuery = queryManager.getInsertQA10ResponseState(pIdx);
			progressDAO.insertQA10ResponseState(userVO, insertQuery);
		}
	}

	public ValidateProgressList selectProgressOfCollection(UserVO userVO, String type) {

		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		List<HashMap<String, Object>> progressListMap = null;
		if (type.equals("ngi")) {
			progressListMap = progressDAO.selectAllQA20ValidateProgress(userVO,
					queryManager.getSelectAllQA20ValidateProgress());
		} else if (type.equals("dxf")) {
			progressListMap = progressDAO.selectAllQA10ValidateProgress(userVO,
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
