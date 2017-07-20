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

package com.git.opengds.upload.service;

import java.util.LinkedList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.git.opengds.upload.domain.FileMeta;
import com.git.opengds.user.domain.UserVO;

public interface FileService {

	public LinkedList<FileMeta> filesUpload(UserVO userVO, MultipartHttpServletRequest request, HttpServletResponse response);

	public boolean fileNameDupCheck(UserVO userVO, String fileName);
}
