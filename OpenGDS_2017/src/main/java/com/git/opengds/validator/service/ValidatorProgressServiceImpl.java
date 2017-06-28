package com.git.opengds.validator.service;

import java.util.HashMap;

import javax.inject.Inject;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import com.git.opengds.file.dxf.dbManager.QA10DBQueryManager;
import com.git.opengds.file.dxf.persistence.QA10LayerCollectionDAO;
import com.git.opengds.file.ngi.dbManager.QA20DBQueryManager;
import com.git.opengds.file.ngi.persistence.QA20LayerCollectionDAO;
import com.git.opengds.validator.dbManager.ValidateProgressDBQueryManager;
import com.git.opengds.validator.persistence.ValidateProgressDAO;

@Service
public class ValidatorProgressServiceImpl implements ValidatorProgressService {

	@Inject
	private DataSourceTransactionManager txManager;

	@Inject
	private QA10LayerCollectionDAO qa10DAO;

	@Inject
	private QA20LayerCollectionDAO qa20DAO;

	@Inject
	private ValidateProgressDAO progressDAO;

	public Integer setStateToRequest(int validateStart, String collectionName, String fileType) {

		Integer pIdx = null;
		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		int cidx = 0;
		if (fileType.equals("ngi")) {
			QA20DBQueryManager qa20QueryManager = new QA20DBQueryManager();
			cidx = qa20DAO
					.selectQA20LayerCollectionIdx(qa20QueryManager.getSelectQA20LayerCollectionIdx(collectionName));
			HashMap<String, Object> insertQuery = queryManager.getInsertQA20RequestState(validateStart, collectionName,
					fileType, cidx);
			pIdx = progressDAO.insertQA20RequestState(insertQuery);
		} else if (fileType.equals("dxf")) {
			QA10DBQueryManager qa10QueryManager = new QA10DBQueryManager();
			cidx = qa10DAO.selectQA10LayerCollectionIdx(qa10QueryManager.getSelectLayerCollectionIdx(collectionName));
			HashMap<String, Object> insertQuery = queryManager.getInsertQA10RequestState(validateStart, collectionName,
					fileType, cidx);
			pIdx = progressDAO.insertQA10RequestState(insertQuery);
		}
		return pIdx;
	}

	public void setStateToProgressing(int validateStart, String fileType, int pIdx) {

		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("ngi")) {
			progressDAO.updateQA20ProgressingState(queryManager.getUpdateQA20ProgressingState(pIdx, validateStart));
		} else if (fileType.equals("dxf")) {
			progressDAO.updateQA10ProgressingState(queryManager.getUpdateQA10ProgressingState(pIdx, validateStart));
		}
	}

	@Override
	public void setStateToValidateSuccess(int validateSuccess, String fileType, int pIdx) {
		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("ngi")) {
			progressDAO
					.updateQA20ValidateSuccessState(queryManager.getUpdateQA20ProgressingState(pIdx, validateSuccess));
		} else if (fileType.equals("dxf")) {
			progressDAO
					.updateQA10ValidateSuccessState(queryManager.getUpdateQA10ProgressingState(pIdx, validateSuccess));
		}
	}

	@Override
	public void setStateToValidateFail(int validateFail, String fileType, int pIdx) {
		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("ngi")) {
			progressDAO.updateQA20ValidateFailState(queryManager.getUpdateQA20ProgressingState(pIdx, validateFail));
		} else if (fileType.equals("dxf")) {
			progressDAO.updateQA10ValidateFailState(queryManager.getUpdateQA10ProgressingState(pIdx, validateFail));
		}
	}

	@Override
	public void setStateToErrLayerSuccess(int errLayerSuccess, String fileType, int pIdx, String tableName) {
		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("ngi")) {
			progressDAO.updateQA20ValidateErrLayerSuccess(
					queryManager.getUpdateQA20ProgressingState(pIdx, errLayerSuccess));
			progressDAO.insertQA20ErrorTableName(queryManager.getInsertQA20ErrorTableName(pIdx, tableName));
		} else if (fileType.equals("dxf")) {
			progressDAO.updateQA10ValidateErrLayerSuccess(
					queryManager.getUpdateQA10ProgressingState(pIdx, errLayerSuccess));
			progressDAO.insertQA10ErrorTableName(queryManager.getInsertQA10ErrorTableName(pIdx, tableName));
		}
	}

	@Override
	public void setStateToErrLayerFail(int errLayerFail, String fileType, int pIdx) {
		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("ngi")) {
			progressDAO.updateQA20ValidateErrLayerFail(queryManager.getUpdateQA20ProgressingState(pIdx, errLayerFail));
		} else if (fileType.equals("dxf")) {
			progressDAO.updateQA10ValidateErrLayerFail(queryManager.getUpdateQA10ProgressingState(pIdx, errLayerFail));
		}
	}

	@Override
	public void setStateToResponse(String fileType, int pIdx) {

		ValidateProgressDBQueryManager queryManager = new ValidateProgressDBQueryManager();
		if (fileType.equals("ngi")) {
			HashMap<String, Object> insertQuery = queryManager.getInsertQA20ResponseState(pIdx);
			progressDAO.insertQA20ResponseState(insertQuery);
		} else if (fileType.equals("dxf")) {
			HashMap<String, Object> insertQuery = queryManager.getInsertQA10ResponseState(pIdx);
			progressDAO.insertQA10ResponseState(insertQuery);
		}
	}
}
