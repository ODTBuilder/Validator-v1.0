package com.git.opengds.generalization.service;

import java.net.MalformedURLException;

import com.git.gdsbuilder.generalization.data.res.DTGeneralEAfLayer;
import com.git.opengds.user.domain.UserVO;

public interface GeneralizationLayerService {

	public String publishGenLayer(UserVO userVO, DTGeneralEAfLayer genLayer, String fileType, String fileName,
			String layerName, String layerType, String src) throws IllegalArgumentException, MalformedURLException;

}
