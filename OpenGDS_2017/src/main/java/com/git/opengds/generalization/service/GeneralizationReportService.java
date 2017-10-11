package com.git.opengds.generalization.service;

import com.git.gdsbuilder.generalization.rep.DTGeneralReport;
import com.git.opengds.user.domain.UserVO;

public interface GeneralizationReportService {

	boolean insertGenralResult(UserVO userVO, String collectionName, String layerName, DTGeneralReport resultReport);

}
