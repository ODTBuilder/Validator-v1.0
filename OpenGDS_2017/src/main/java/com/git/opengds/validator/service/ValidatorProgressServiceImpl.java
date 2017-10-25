package com.git.opengds.validator.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.git.gdsbuilder.type.validate.collection.ValidateProgress;
import com.git.gdsbuilder.type.validate.collection.ValidateProgressList;
import com.git.opengds.file.shp.dbManager.SHPDBQueryManager;
import com.git.opengds.file.shp.persistence.SHPLayerCollectionDAO;
import com.git.opengds.user.domain.UserVO;
import com.git.opengds.validator.dbManager.ValidateProgressDBQueryManager;
import com.git.opengds.validator.persistence.ValidateProgressDAO;

@Service
public class ValidatorProgressServiceImpl implements ValidatorProgressService {

	@Inject
	private SHPLayerCollectionDAO shpDAO;

	@Inject
	private ValidateProgressDAO progressDAO;

	public Integer setStateToRequest(UserVO userVO, int validateStart, String collectionName, String fileType) {

		Integer pIdx = null;
		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		int cidx = 0;
		if (fileType.equals("shp")) {
			SHPDBQueryManager shpdbQueryManager = new SHPDBQueryManager();
			cidx = shpDAO.selectSHPLayerCollectionIdx(userVO,
					shpdbQueryManager.getSelectSHPLayerCollectionIdx(collectionName));
			HashMap<String, Object> insertQuery = shpdbQueryManager.getInsertSHPRequestState(validateStart,
					collectionName, fileType, cidx);
			pIdx = progressDAO.insertSHPRequestState(userVO, insertQuery);
		}
		return pIdx;
	}

	public void setStateToProgressing(UserVO userVO, int validateStart, String fileType, int pIdx) {

		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("shp")) {
			progressDAO.updateSHPProgressingState(userVO,
					queryManager.getUpdateSHPProgressingState(pIdx, validateStart));
		}
	}

	@Override
	public void setStateToValidateSuccess(UserVO userVO, int validateSuccess, String fileType, int pIdx) {
		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("shp")) {
			progressDAO.updateSHPValidateSuccessState(userVO,
					queryManager.getUpdateSHPProgressingState(pIdx, validateSuccess));
		}
	}

	@Override
	public void setStateToValidateFail(UserVO userVO, int validateFail, String fileType, int pIdx) {
		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("shp")) {
			progressDAO.updateSHPValidateFailState(userVO,
					queryManager.getUpdateSHPProgressingState(pIdx, validateFail));
		}
	}

	@Override
	public void setStateToErrLayerSuccess(UserVO userVO, int errLayerSuccess, String fileType, int pIdx,
			String tableName) {
		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("shp")) {
			progressDAO.updateSHPValidateErrLayerSuccess(userVO,
					queryManager.getUpdateSHPProgressingState(pIdx, errLayerSuccess));
			progressDAO.insertSHPErrorTableName(userVO, queryManager.getInsertSHPErrorTableName(pIdx, tableName));
		}
	}

	@Override
	public void setStateToErrLayerFail(UserVO userVO, int errLayerFail, String fileType, int pIdx) {
		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("shp")) {
			progressDAO.updateSHPValidateErrLayerFail(userVO,
					queryManager.getUpdateSHPProgressingState(pIdx, errLayerFail));
		}
	}

	@Override
	public void setStateToResponse(UserVO userVO, String fileType, int pIdx) {

		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("shp")) {
			HashMap<String, Object> insertQuery = queryManager.getInsertSHPResponseState(pIdx);
			progressDAO.insertSHPResponseState(userVO, insertQuery);
		}
	}

	public ValidateProgressList selectProgressOfCollection(UserVO userVO, String type) {

		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		List<HashMap<String, Object>> progressListMap = null;
		if (type.equals("shp")) {
			progressListMap = progressDAO.selectAllSHPValidateProgress(userVO,
					queryManager.getSelectAllSHPValidateProgress());
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
