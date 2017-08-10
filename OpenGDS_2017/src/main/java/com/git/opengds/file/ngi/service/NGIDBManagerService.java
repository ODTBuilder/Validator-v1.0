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

package com.git.opengds.file.ngi.service;

import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.ngi.collection.DTNGILayerCollection;
import com.git.opengds.user.domain.UserVO;

public interface NGIDBManagerService {
	
	public GeoLayerInfo insertNGILayerCollection(UserVO userVO, DTNGILayerCollection dtCollection, GeoLayerInfo layerInfo) throws Exception;

	public GeoLayerInfo dropNGILayerCollection(UserVO userVO, DTNGILayerCollection dtCollection, GeoLayerInfo layerInfo);

}
