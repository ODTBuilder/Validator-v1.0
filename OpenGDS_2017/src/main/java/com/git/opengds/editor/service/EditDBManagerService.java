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

import org.postgresql.util.PSQLException;

import com.git.gdsbuilder.edit.qa10.EditQA10Collection;
import com.git.gdsbuilder.edit.qa20.EditQA20Collection;
import com.git.gdsbuilder.type.qa10.feature.QA10Feature;
import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa20.feature.QA20Feature;
import com.git.gdsbuilder.type.qa20.layer.QA20Layer;
import com.git.opengds.user.domain.UserVO;

public interface EditDBManagerService {

	public Integer checkQA20LayerCollectionName(UserVO userVO, String collectionName);

	public Integer createQA20LayerCollection(UserVO userVO, String type, EditQA20Collection editCollection) throws Exception;

	public Integer createQA10LayerCollection(UserVO userVO, String type, EditQA10Collection editCollection) throws Exception;

	public void insertQA20CreateFeature(UserVO userVO, String layerName, QA20Feature createFeature, String src);

	public void updateQA20ModifyFeature(UserVO userVO, String layerName, QA20Feature modifyFeature, String src);

	public void deleteQA20RemovedFeature(UserVO userVO, String layerName, String featureId);

	public Integer checkQA10LayerCollectionName(UserVO userVO, String collectionName);

	public void insertQA10CreateFeature(UserVO userVO, String layerName, QA10Feature createFeature);

	public void updateQA10ModifyFeature(UserVO userVO, String layerName, QA10Feature modifyFeature);

	public void deleteQA10RemovedFeature(UserVO userVO, String layerName, String featureId);

	public boolean modifyQA20Layer(UserVO userVO, String type, Integer collectionIdx, String collectionName, QA20Layer qa20Laye,
			Map<String, Object> geoLayer) throws PSQLException;

	public List<HashMap<String, Object>> getQA20LayerMetadataIdx(UserVO userVO, Integer collectionIdx);

	public boolean createQA20Layer(UserVO userVO, String type, Integer collectionIdx, String collectionName, QA20Layer layer,
			String src) throws PSQLException;

	public boolean dropQA20Layer(UserVO userVO, String type, Integer collectionIdx, String collectionName, QA20Layer layer);

	public boolean createQA10Layer(UserVO userVO, String type, Integer collectionIdx, String collectionName, QA10Layer createLayer,
			String src) throws PSQLException;

	public boolean dropQA10Layer(UserVO userVO, String type, Integer collectionIdx, String collectionName, QA10Layer layer);

	public boolean modifyQA10Layer(UserVO userVO, String type, Integer collectionIdx, String collectionName, QA10Layer modifiedLayer,
			Map<String, Object> geoLayer);

}
