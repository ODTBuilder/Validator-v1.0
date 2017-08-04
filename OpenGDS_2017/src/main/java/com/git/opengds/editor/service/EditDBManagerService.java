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

import com.git.gdsbuilder.edit.dxf.EditDXFCollection;
import com.git.gdsbuilder.edit.ngi.EditNGICollection;
import com.git.gdsbuilder.type.dxf.feature.DTDXFFeature;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayer;
import com.git.gdsbuilder.type.ngi.feature.DTNGIFeature;
import com.git.gdsbuilder.type.ngi.layer.DTNGILayer;
import com.git.opengds.user.domain.UserVO;

public interface EditDBManagerService {

	public Integer checkQA20LayerCollectionName(UserVO userVO, String collectionName) throws RuntimeException;

	public Integer createQA20LayerCollection(UserVO userVO, String type, EditNGICollection editCollection) throws RuntimeException;

	public Integer createQA10LayerCollection(UserVO userVO, String type, EditDXFCollection editCollection) throws RuntimeException;

	public void deleteQA10LayerCollection(UserVO userVO, int cIdx) throws RuntimeException;
	
	public void deleteQA20LayerCollection(UserVO userVO, int cIdx) throws RuntimeException;
	
	public void insertQA20CreateFeature(UserVO userVO, String layerName, DTNGIFeature createFeature, String src) throws RuntimeException;

	public void updateQA20ModifyFeature(UserVO userVO, String layerName, DTNGIFeature modifyFeature, String src) throws RuntimeException;

	public void deleteQA20RemovedFeature(UserVO userVO, String layerName, String featureId) throws RuntimeException;

	public Integer checkQA10LayerCollectionName(UserVO userVO, String collectionName) throws RuntimeException;

	public void insertQA10CreateFeature(UserVO userVO, String layerName, DTDXFFeature createFeature) throws RuntimeException;

	public void updateQA10ModifyFeature(UserVO userVO, String layerName, DTDXFFeature modifyFeature) throws RuntimeException;

	public void deleteQA10RemovedFeature(UserVO userVO, String layerName, String featureId) throws RuntimeException;

	public boolean modifyQA20Layer(UserVO userVO, String type, Integer collectionIdx, String collectionName, DTNGILayer qa20Laye,
			Map<String, Object> geoLayer) throws RuntimeException;

	public List<HashMap<String, Object>> getQA20LayerMetadataIdx(UserVO userVO, Integer collectionIdx) throws RuntimeException;

	public boolean createQA20Layer(UserVO userVO, String type, Integer collectionIdx, String collectionName, DTNGILayer layer,
			String src) throws RuntimeException;

	public boolean dropQA20Layer(UserVO userVO, String type, Integer collectionIdx, String collectionName, DTNGILayer layer) throws RuntimeException;

	public boolean createQA10Layer(UserVO userVO, String type, Integer collectionIdx, String collectionName, DTDXFLayer createLayer,
			String src) throws RuntimeException;

	public boolean dropQA10Layer(UserVO userVO, String type, Integer collectionIdx, String collectionName, DTDXFLayer layer) throws RuntimeException;

	public boolean modifyQA10Layer(UserVO userVO, String type, Integer collectionIdx, String collectionName, DTDXFLayer modifiedLayer,
			Map<String, Object> geoLayer) throws RuntimeException;

	public void deleteQA10LayerCollectionTablesCommon(UserVO userVO, Integer collectionIdx) throws RuntimeException;

}
