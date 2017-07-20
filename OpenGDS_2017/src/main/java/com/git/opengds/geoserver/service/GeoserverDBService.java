package com.git.opengds.geoserver.service;

import com.git.gdsbuilder.FileRead.en.EnFileFormat;
import com.git.opengds.user.domain.UserVO;

public interface GeoserverDBService {

	public boolean selectEditLayerDuplicateCheck(UserVO userVO, EnFileFormat fileFormat, String fileName, String layerName);
}
