package com.git.opengds.generalization.service;

import java.util.HashMap;

import javax.inject.Inject;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;

import com.git.opengds.file.shp.dbManager.SHPDBQueryManager;
import com.git.opengds.file.shp.persistence.SHPLayerCollectionDAO;
import com.git.opengds.generalization.dbManager.GenProgressQueryManager;
import com.git.opengds.generalization.persistance.GeneralizationProgressDAO;
import com.git.opengds.user.domain.UserVO;

@Service
public class GeneralizationProgressServiceImpl implements GeneralizationProgressService {

	@Inject
	private SHPLayerCollectionDAO shpDAO;

	@Inject
	private GeneralizationProgressDAO progressDAO;

	@Override
	public Integer setStateToRequest(UserVO userVO, int validateStart, String collectionName, String type,
			JSONArray layersArray) {

		return null;
	}

	@Override
	public Integer setStateToRequest(UserVO userVO, int validateStart, String collectionName, String type,
			String layerName) {

		GenProgressQueryManager queryManager = new GenProgressQueryManager();

		SHPDBQueryManager shpdbQueryManager = new SHPDBQueryManager();
		int cidx = shpDAO.selectSHPLayerCollectionIdx(userVO,
				shpdbQueryManager.getSelectSHPLayerCollectionIdx(collectionName));
		HashMap<String, Object> insertQuery = queryManager.getInsertRequestState(validateStart, collectionName, type,
				layerName, cidx);
		Integer pIdx = progressDAO.insertRequestState(userVO, insertQuery);
		return pIdx;
	}

	@Override
	public void setStateToProgressing(UserVO userVO, int state, String collectionName, String type, String layerName,
			Integer pIdx) {
		GenProgressQueryManager queryManager = new GenProgressQueryManager();
		HashMap<String, Object> insertQuery = queryManager.getUpdateProgressingState(pIdx, type, state);
		progressDAO.getUpdateProgressingState(userVO, insertQuery);
	}

}
