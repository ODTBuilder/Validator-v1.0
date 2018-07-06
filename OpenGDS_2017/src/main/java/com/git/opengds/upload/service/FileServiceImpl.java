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
import javax.swing.filechooser.FileSystemView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.git.opengds.file.shp.service.SHPFileUploadService;
import com.git.opengds.upload.domain.FileMeta;
import com.git.opengds.upload.persistence.FileDAO;
import com.git.opengds.user.domain.UserVO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class FileServiceImpl implements FileService {

	// windows
	// private static final String dirPath = "D:" + File.separator + "files";
	// linux
	private static final String dirPath = FileSystemView.getFileSystemView().getHomeDirectory() + File.separator
			+ "files";

	@Autowired
	private SHPFileUploadService shpFileService;

	@Inject
	private FileDAO fileDAO;

	/*
	 * public FileServiceImpl(UserVO userVO) { // TODO Auto-generated
	 * constructor stub id = userVO.getId(); qa20FileService = new
	 * QA20FileUploadServiceImpl(userVO); qa10FileService = new
	 * QA10FileUploadServiceImpl(userVO); fileDAO = new FileDAOImpl(userVO); }
	 */

	public LinkedList<FileMeta> filesUpload(UserVO userVO, MultipartHttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		String fullDirPath = dirPath + File.separator + userVO.getId();
		File dir = new File(dirPath);
		File targetDir = new File(fullDirPath);

		// 최상위 디렉토리 생성
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// 사용자별 디렉토리 생성
		if (!targetDir.exists()) {
			File shpDir = new File(fullDirPath + File.separator + "shp");
			File otherDir = new File(fullDirPath + File.separator + "other");

			targetDir.mkdirs();
			shpDir.mkdirs();
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

				if (ext.endsWith("zip")) {
					if (ext.endsWith("zip")) {
						saveFilePath = fullDirPath + File.separator + "shp" + File.separator
								+ mpf.getOriginalFilename();
					}
				} else
					saveFilePath = fullDirPath + File.separator + "other" + File.separator + mpf.getOriginalFilename();

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
			files = this.filesPublish(userVO, files);
		}
		return files;
	}

	private LinkedList<FileMeta> filesPublish(UserVO userVO, LinkedList<FileMeta> fileMetaList) throws Throwable {
		LinkedList<FileMeta> fileMetas = fileMetaList;

		// int a = fileMetaList.size();
		for (int i = 0; i < fileMetas.size(); i++) {
			FileMeta fileMeta = fileMetas.get(i);
			int pos = fileMeta.getFilePath().lastIndexOf(".");
			String ext = fileMeta.getFilePath().substring(pos + 1).toLowerCase();

			if (ext.endsWith("zip")) {
				FileMeta refileMeta = null;
				try {
					if (ext.equals("zip")) {
						fileMeta.setFileType("shp");
						refileMeta = shpFileService.shpUpload(userVO, fileMeta);
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

	// private

	public boolean fileNameDupCheck(UserVO userVO, String fileFullName) {
		boolean dupFlag = false;

		if (fileFullName.length() != 0) {
			int Idx = fileFullName.lastIndexOf(".");

			String fileName = fileFullName.substring(0, Idx);
			String fileType = fileFullName.substring(Idx + 1);

			if (fileType.endsWith("zip")) {
				if (fileType.endsWith("zip")) {
					dupFlag = fileDAO.selectSHPDuplicateCheck(userVO, fileName);
				}
			}
		}
		return dupFlag;
	}
}
