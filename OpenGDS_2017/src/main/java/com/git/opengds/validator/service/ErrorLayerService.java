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

package com.git.opengds.validator.service;

import java.net.MalformedURLException;
import java.util.Map;

import com.git.gdsbuilder.type.validate.error.ErrorLayer;
import com.git.gdsbuilder.type.validate.error.ErrorLayerList;
import com.git.opengds.user.domain.UserVO;

public interface ErrorLayerService {

	public boolean publishErrorLayerList(UserVO userVO, ErrorLayerList errLayerList)
			throws IllegalArgumentException, MalformedURLException;

	public Map<String, Object> publishErrorLayer(UserVO userVO, ErrorLayer errLayer)
			throws IllegalArgumentException, MalformedURLException;
}
