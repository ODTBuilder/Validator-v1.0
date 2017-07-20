package com.git.opengds.file.dxf.service;

import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.qa10.collection.QA10LayerCollection;
import com.git.opengds.user.domain.UserVO;

public interface QA10DBManagerService {

	public GeoLayerInfo insertQA10LayerCollection(UserVO userVO, QA10LayerCollection layerCollection, GeoLayerInfo layerInfo) throws RuntimeException;

	public GeoLayerInfo dropQA10LayerCollection(UserVO userVO, QA10LayerCollection collection, GeoLayerInfo layerInfo) throws RuntimeException;

}
