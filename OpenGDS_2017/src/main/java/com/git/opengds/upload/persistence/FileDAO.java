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

package com.git.opengds.upload.persistence;

public interface FileDAO {

	/**
	 * 파일 중복체크
	 * @author SG.Lee
	 * @Date 2017. 5. 12. 오전 2:24:29
	 * @param fileName
	 * @return boolean
	 * @throws
	 * */
	public boolean selectNGIDuplicateCheck(String fileName);
	
	public boolean selectDXFDuplicateCheck(String fileName);
}
