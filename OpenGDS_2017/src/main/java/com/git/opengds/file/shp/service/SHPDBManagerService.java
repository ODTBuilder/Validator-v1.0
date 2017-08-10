package com.git.opengds.file.shp.service;

import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.shp.collection.DTSHPLayerCollection;
import com.git.opengds.user.domain.UserVO;

public interface SHPDBManagerService {

	public GeoLayerInfo insertSHPLayerCollection(UserVO userVO, DTSHPLayerCollection dtCollection,
			GeoLayerInfo layerInfo) throws Exception;

}
