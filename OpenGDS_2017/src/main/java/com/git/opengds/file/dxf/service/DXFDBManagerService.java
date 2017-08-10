package com.git.opengds.file.dxf.service;

import com.git.gdsbuilder.type.dxf.collection.DTDXFLayerCollection;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.opengds.user.domain.UserVO;

public interface DXFDBManagerService {

	public GeoLayerInfo insertDXFLayerCollection(UserVO userVO, DTDXFLayerCollection layerCollection, GeoLayerInfo layerInfo) throws RuntimeException;

	public GeoLayerInfo dropDXFLayerCollection(UserVO userVO, DTDXFLayerCollection collection, GeoLayerInfo layerInfo) throws RuntimeException;

}
