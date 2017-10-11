package com.git.opengds.generalization.service;

import org.json.simple.JSONArray;

import com.git.opengds.user.domain.UserVO;

public interface GeneralizationProgressService {

	Integer setStateToRequest(UserVO userVO, int validateStart, String collectionName, String type,
			JSONArray layersArray);

	Integer setStateToRequest(UserVO userVO, int validateStart, String collectionName, String type, String layerName);

	void setStateToProgressing(UserVO userVO, int validateStart, String collectionName, String type, String layerName,
			Integer pIdx);

}
