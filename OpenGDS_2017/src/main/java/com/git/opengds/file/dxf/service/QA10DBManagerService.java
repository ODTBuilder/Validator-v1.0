package com.git.opengds.file.dxf.service;

import org.postgresql.util.PSQLException;

import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.qa10.collection.QA10LayerCollection;

public interface QA10DBManagerService {

	public GeoLayerInfo insertQA10LayerCollection(QA10LayerCollection layerCollection, GeoLayerInfo layerInfo) throws PSQLException;

	public void dropQA10LayerCollection(QA10LayerCollection collection, GeoLayerInfo layerInfo);

}
