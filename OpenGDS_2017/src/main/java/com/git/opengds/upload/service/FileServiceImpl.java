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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.git.opengds.file.dxf.service.QA10FileUploadService;
import com.git.opengds.file.ngi.service.QA20FileUploadService;
import com.git.opengds.upload.domain.FileMeta;
import com.git.opengds.upload.persistence.FileDAO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class FileServiceImpl implements FileService {

	private static final String dirPath = "D:\\files";
	private static final String id = "admin";

	@Autowired
	private QA20FileUploadService qa20FileService;

	@Autowired
	private QA10FileUploadService qa10FileService;

	@Inject
	private FileDAO fileDAO;

	public LinkedList<FileMeta> filesUpload(MultipartHttpServletRequest request, HttpServletResponse response) {
		String fullDirPath = this.dirPath + "\\" + this.id;
		File dir = new File(dirPath);
		File targetDir = new File(fullDirPath);

		// 최상위 디렉토리 생성
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		//사용자별 디렉토리 생성
		if(!targetDir.exists()){
			File ngiDir = new File(fullDirPath + "\\ngi");
			File shpDir = new File(fullDirPath + "\\shp");
			File dxfDir = new File(fullDirPath + "\\dxf");
			File otherDir = new File(fullDirPath + "\\other");

			targetDir.mkdirs();
			ngiDir.mkdirs();
			shpDir.mkdirs();
			dxfDir.mkdirs();
			otherDir.mkdirs();
		}

		LinkedList<FileMeta> files = new LinkedList<FileMeta>();

		// 1. build an iterator
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;

		// 2. get each file
		while (itr.hasNext()) {
			// 2.1 get next MultipartFile
			mpf = request.getFile(itr.next());

			System.out.println(mpf.getOriginalFilename() + " uploaded! " + files.size());

			// 2.2 if files > 10 remove the first from the list
			if (files.size() >= 10)
				files.pop();

			try {
				int pos = mpf.getOriginalFilename().lastIndexOf(".");
				String ext = mpf.getOriginalFilename().substring(pos + 1).toLowerCase();
				String epsg = request.getParameter("epsg");
				// 2.3 create new fileMeta
				FileMeta fileMeta = new FileMeta();
				fileMeta.setFileName(mpf.getOriginalFilename().substring(0, pos));
				fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
				fileMeta.setFileType(ext);
				fileMeta.setOriginSrc(epsg);
				fileMeta.setBytes(mpf.getBytes());

				String saveFilePath = "";

				if (ext.endsWith("dxf") || ext.endsWith("ngi") || ext.endsWith("nda") || ext.endsWith("shp")) {
					if (ext.endsWith("ngi") || ext.endsWith("nda")) {
						saveFilePath = fullDirPath + "\\ngi\\" + mpf.getOriginalFilename();
					} else
						saveFilePath = fullDirPath + "\\" + ext + "\\" + mpf.getOriginalFilename();
				} else
					saveFilePath = fullDirPath + "\\other\\" + mpf.getOriginalFilename();

				// copy file to local disk (make sure the path "e.g.
				// D:/temp/files" exists)
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(saveFilePath));
				fileMeta.setFilePath(saveFilePath);
				fileMeta.setUploadFlag(true);
				files.add(fileMeta);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 2.4 add to files
			files = this.filesPublish(files);
		}
		return files;
	}

	private LinkedList<FileMeta> filesPublish(LinkedList<FileMeta> fileMetaList) {
		LinkedList<FileMeta> fileMetas = fileMetaList;

		for (int i = 0; i < fileMetas.size(); i++) {
			FileMeta fileMeta = fileMetas.get(i);
			int pos = fileMeta.getFilePath().lastIndexOf(".");
			String ext = fileMeta.getFilePath().substring(pos + 1).toLowerCase();

			if (ext.endsWith("ngi") || ext.endsWith("shp") || ext.endsWith("dxf")) {
				FileMeta refileMeta = null;
				try {
					if(ext.equals("dxf")) {
						refileMeta = qa10FileService.dxfUpload(fileMeta);
					} else {
						refileMeta = qa20FileService.ngiUpload(fileMeta);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fileMetas.set(i, refileMeta);
			}
		}
		return fileMetas;
	}

	public boolean fileNameDupCheck(String fileFullName) {
		boolean dupFlag = false;

		if (fileFullName.length() != 0) {
			int Idx = fileFullName.lastIndexOf(".");

			String fileName = fileFullName.substring(0, Idx);
			String fileType = fileFullName.substring(Idx + 1);

			if (fileType.endsWith("dxf") || fileType.endsWith("ngi") || fileType.endsWith("nda")
					|| fileType.endsWith("shp")) {
				if (fileType.endsWith("ngi") || fileType.endsWith("nda")) {
					dupFlag = fileDAO.selectNGIDuplicateCheck(fileName);
				} else if (fileType.endsWith("dxf")) {
					dupFlag = fileDAO.selectDXFDuplicateCheck(fileName);
				}
			}
		}
		return dupFlag;
	}
}
