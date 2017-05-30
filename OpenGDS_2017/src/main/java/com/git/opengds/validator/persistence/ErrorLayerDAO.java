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

package com.git.opengds.validator.persistence;

import java.util.HashMap;
import java.util.List;

public interface ErrorLayerDAO {

	public void createErrorLayerTb(HashMap<String, Object> createQuery);

	public void insertErrorFeature(HashMap<String, Object> insertQuery);

	public List<HashMap<String, Object>> selectErrorFeatures(HashMap<String, Object> selectQuery);
	
	public List<HashMap<String, Object>> selectAllErrorFeatures(HashMap<String, Object> selectQuery);

}
