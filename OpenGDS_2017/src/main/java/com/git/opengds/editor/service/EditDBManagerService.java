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

import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.edit.shp.EditSHPLayerCollection;
import com.git.gdsbuilder.type.shp.layer.DTSHPLayer;
import com.git.opengds.user.domain.UserVO;

public interface EditDBManagerService {

	public Integer selectSHPLayerCollectionIdx(UserVO userVO, String collectionName);

	public void insertSHPCreateFeature(UserVO userVO, String tableName, SimpleFeature createFeature, String src);

	public void deleteSHPRemovedFeature(UserVO userVO, String tableName, String featureId);

	public void updateSHPModifyFeature(UserVO userVO, String tableName, SimpleFeature modifiedFeature, String src);

	public boolean dropSHPLayer(UserVO userVO, String isdxf, Integer collectionIdx, String collectionName,
			DTSHPLayer layer);

	public void deleteSHPLayerCollection(UserVO userVO, Integer collectionIdx);

	public boolean createSHPLayer(UserVO userVO, String isdxf, Integer collectionIdx, String collectionName,
			DTSHPLayer createLayer, String src) throws MalformedURLException;

	public Integer createSHPLayerCollection(UserVO userVO, String isdxf, EditSHPLayerCollection editCollection);

}
