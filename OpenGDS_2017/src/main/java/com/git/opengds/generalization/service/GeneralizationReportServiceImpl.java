package com.git.opengds.generalization.service;

import java.util.HashMap;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.git.gdsbuilder.generalization.rep.DTGeneralReport;
import com.git.opengds.generalization.dbManager.GenResultDBQueryManager;
import com.git.opengds.generalization.persistance.GeneralizationReportDAO;
import com.git.opengds.user.domain.UserVO;

@Service
public class GeneralizationReportServiceImpl implements GeneralizationReportService {

	@Inject
	GeneralizationReportDAO generalizationReportDAO;

	@Override
	public boolean insertGenralResult(UserVO userVO, String collectionName, String layerName, DTGeneralReport resultReport) {

		try {
			GenResultDBQueryManager queryManager = new GenResultDBQueryManager();
			HashMap<String, Object> insertQuery = queryManager.getInsertGenResultQuery(collectionName, layerName, resultReport);
			generalizationReportDAO.insertGenResult(userVO, insertQuery);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
