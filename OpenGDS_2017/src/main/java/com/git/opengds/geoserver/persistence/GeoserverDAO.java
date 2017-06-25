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

package com.git.opengds.geoserver.persistence;

import java.util.HashMap;

public interface GeoserverDAO {

	/**
	 * GeoLayer 중복체크
	 * @author SG.Lee
	 * @Date 2017. 6. 25. 오후 11:16:16
	 * @param infoMap
	 * @return boolean
	 * */
	public boolean selectEditLayerDuplicateCheck(HashMap<String,Object> infoMap);
	
}
