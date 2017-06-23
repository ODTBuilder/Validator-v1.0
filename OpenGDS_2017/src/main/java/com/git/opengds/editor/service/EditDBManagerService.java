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

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.postgresql.util.PSQLException;

import com.git.gdsbuilder.edit.qa10.EditQA10Collection;
import com.git.gdsbuilder.edit.qa20.EditQA20Collection;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.qa10.feature.QA10Feature;
import com.git.gdsbuilder.type.qa20.feature.QA20Feature;
import com.git.gdsbuilder.type.qa20.layer.QA20Layer;

public interface EditDBManagerService {

	public Integer checkQA20LayerCollectionName(String collectionName);

	public Integer createQA20LayerCollection(String type, EditQA20Collection editCollection) throws Exception;

	public void insertQA20CreateFeature(String layerName, QA20Feature createFeature);

	public void updateQA20ModifyFeature(String layerName, QA20Feature modifyFeature);

	public void deleteQA20RemovedFeature(String layerName, String featureId);

	public Integer checkQA10LayerCollectionName(String collectionName);

	public GeoLayerInfo createQA10LayerCollection(String type, EditQA10Collection editCollection) throws Exception;

	public GeoLayerInfo createQA10Layers(String type, Integer collectionIdx, EditQA10Collection editCollection)
			throws PSQLException, IllegalArgumentException, MalformedURLException;

	public void insertQA10CreateFeature(String layerName, QA10Feature createFeature);

	public void updateQA10ModifyFeature(String layerName, QA10Feature modifyFeature);

	public void deleteQA10RemovedFeature(String layerName, String featureId);

	public List<String> dropQA10LayerCollection(String type, EditQA10Collection editCollection);

	public boolean modifyQA20Layer(String type, Integer collectionIdx, QA20Layer qa20Laye, Map<String, Object> geoLayer)
			throws PSQLException;

	public List<HashMap<String, Object>> getQA20LayerMetadataIdx(Integer collectionIdx);

	public boolean createQA20Layer(String type, Integer collectionIdx, String collectionName, QA20Layer layer)
			throws PSQLException;

	public boolean dropQA20Layer(String type, Integer collectionIdx, String collectionName, QA20Layer layer);

}
