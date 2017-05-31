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

import org.postgresql.util.PSQLException;

import com.git.gdsbuilder.edit.qa20.EditQA20Collection;
import com.git.gdsbuilder.edit.qa20.EditQA20LayerCollectionList;
import com.git.gdsbuilder.type.simple.collection.LayerCollectionList;

public interface EditDBManagerService {

	// public boolean updateFeatures(LayerCollectionList collecionList) throws
	// PSQLException;

	// public void editQA20Layer(EditQA20LayerCollectionList edtCollectionList);

	public Integer checkCollectionName(String collectionName);

	public void createQa20LayerCollection(String type, EditQA20Collection editCollection) throws Exception;

}
