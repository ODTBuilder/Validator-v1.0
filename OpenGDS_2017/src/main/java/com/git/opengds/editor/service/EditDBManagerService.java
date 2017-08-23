/*
 *    OpenGDS/Builder
 *    http://git.co.kr
 *
 *    (C) 2014-2017, GeoSpatial Information Technology(GIT)
 *    
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 3 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */

package com.git.opengds.editor.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.edit.dxf.EditDXFLayerCollection;
import com.git.gdsbuilder.edit.ngi.EditNGILayerCollection;
import com.git.gdsbuilder.edit.shp.EditSHPLayerCollection;
import com.git.gdsbuilder.type.dxf.feature.DTDXFFeature;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayer;
import com.git.gdsbuilder.type.ngi.feature.DTNGIFeature;
import com.git.gdsbuilder.type.ngi.layer.DTNGILayer;
import com.git.gdsbuilder.type.shp.layer.DTSHPLayer;
import com.git.opengds.user.domain.UserVO;

public interface EditDBManagerService {

	public Integer selectSHPLayerCollectionIdx(UserVO userVO, String collectionName);

	public Integer selectNGILayerCollectionIdx(UserVO userVO, String collectionName) throws RuntimeException;

	public Integer createNGILayerCollection(UserVO userVO, String type, EditNGILayerCollection editCollection)
			throws RuntimeException;

	public Integer createDXFLayerCollection(UserVO userVO, String type, EditDXFLayerCollection editCollection)
			throws RuntimeException;

	public void deleteDXFLayerCollection(UserVO userVO, int cIdx) throws RuntimeException;

	public void deleteNGILayerCollection(UserVO userVO, int cIdx) throws RuntimeException;

	public void insertNGICreateFeature(UserVO userVO, String layerName, DTNGIFeature createFeature, String src)
			throws RuntimeException;

	public void updateNGIModifyFeature(UserVO userVO, String layerName, DTNGIFeature modifyFeature, String src)
			throws RuntimeException;

	public void deleteNGIRemovedFeature(UserVO userVO, String layerName, String featureId) throws RuntimeException;

	public Integer selectDXFLayerCollectionIdx(UserVO userVO, String collectionName) throws RuntimeException;

	public void insertDXFCreateFeature(UserVO userVO, String layerName, DTDXFFeature createFeature)
			throws RuntimeException;

	public void updateDXFModifyFeature(UserVO userVO, String layerName, DTDXFFeature modifyFeature)
			throws RuntimeException;

	public void deleteDXFRemovedFeature(UserVO userVO, String layerName, String featureId) throws RuntimeException;

	public boolean modifyNGILayer(UserVO userVO, String type, Integer collectionIdx, String collectionName,
			DTNGILayer qa20Laye, Map<String, Object> geoLayer) throws RuntimeException;

	public List<HashMap<String, Object>> getNGILayerMetadataIdx(UserVO userVO, Integer collectionIdx)
			throws RuntimeException;

	public boolean createNGILayer(UserVO userVO, String type, Integer collectionIdx, String collectionName,
			DTNGILayer layer, String src) throws RuntimeException;

	public boolean dropNGILayer(UserVO userVO, String type, Integer collectionIdx, String collectionName,
			DTNGILayer layer) throws RuntimeException;

	public boolean createDXFLayer(UserVO userVO, String type, Integer collectionIdx, String collectionName,
			DTDXFLayer createLayer, String src) throws RuntimeException;

	public boolean dropDXFLayer(UserVO userVO, String type, Integer collectionIdx, String collectionName,
			DTDXFLayer layer) throws RuntimeException;

	public boolean modifyDXFLayer(UserVO userVO, String type, Integer collectionIdx, String collectionName,
			DTDXFLayer modifiedLayer, Map<String, Object> geoLayer) throws RuntimeException;

	public void deleteDXFLayerCollectionTablesCommon(UserVO userVO, Integer collectionIdx) throws RuntimeException;

	public void insertSHPCreateFeature(UserVO userVO, String tableName, SimpleFeature createFeature, String src);

	public void deleteSHPRemovedFeature(UserVO userVO, String tableName, String featureId);

	public void updateSHPModifyFeature(UserVO userVO, String tableName, SimpleFeature modifiedFeature, String src);

	public boolean dropSHPLayer(UserVO userVO, String isdxf, Integer collectionIdx, String collectionName,
			DTSHPLayer layer);

	public void deleteSHPLayerCollection(UserVO userVO, Integer collectionIdx);

	public boolean createSHPLayer(UserVO userVO, String isdxf, Integer collectionIdx, String collectionName,
			DTSHPLayer createLayer, String src);

	public Integer createSHPLayerCollection(UserVO userVO, String isdxf, EditSHPLayerCollection editCollection);

}
