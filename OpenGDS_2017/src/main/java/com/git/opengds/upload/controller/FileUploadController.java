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

package com.git.opengds.upload.controller;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.git.opengds.common.AbstractController;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.upload.domain.FileMeta;
import com.git.opengds.upload.service.FileService;
import com.git.opengds.upload.service.FileServiceImpl;
import com.git.opengds.user.domain.UserVO;
import com.git.opengds.user.domain.UserVO.EnUserType;

/**
 * 파일 업로드와 관련된 요청을 수행한다.
 * 
 * @author SG.Lee
 * @Date 2017.04.11
 */
@Controller
@RequestMapping("/file")
public class FileUploadController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	@Autowired
	private FileService fileService;

	/**
	 * @throws Throwable
	 *             파일업로드 @author SG.Lee @Date 2017. 4 @param request @param
	 *             response @return @throws Exception
	 *             LinkedList<FileMeta> @throws
	 */
	@RequestMapping(value = "/fileUpload.do", method = RequestMethod.POST)
	public @ResponseBody LinkedList<FileMeta> fileUploadRequest(MultipartHttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		UserVO generalUser = (UserVO) getSession(request, EnUserType.GENERAL.getTypeName());
		LinkedList<FileMeta> files = new LinkedList<FileMeta>();
		long start = System.currentTimeMillis() ; 

		
		
		files = fileService.filesUpload(generalUser, request, response);
		/*
		 * geoserverService.groupPublish();
		 * 
		 * geoserverService.updateDBLayer("admin", "admin",
		 * "geo_ngi_00000738000124_E0052114_POLYGON", null, null);
		 */

		long end = System.currentTimeMillis(); 
		System.out.println((end-start)/1000 +" 초 걸림");

		return files;
	}

	/**
	 * 파일명 중복체크 @author SG.Lee @Date 2017. 4 @param fileName @return
	 * boolean @throws
	 */
	@RequestMapping(value = "/fileNameDupCheckAjax.ajax", method = RequestMethod.GET)
	public @ResponseBody boolean fileNameDupCheck(HttpServletRequest request,
			@RequestParam(value = "fileName", required = true) String fileName) {
		UserVO generalUser = (UserVO) getSession(request, EnUserType.GENERAL.getTypeName());
		if (generalUser == null) {
			return false;
		}
		boolean dupFlag = false;
		dupFlag = fileService.fileNameDupCheck(generalUser, fileName);
		return dupFlag;
	}
}
