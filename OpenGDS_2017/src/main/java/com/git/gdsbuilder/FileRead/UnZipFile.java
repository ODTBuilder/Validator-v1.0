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
package com.git.gdsbuilder.FileRead;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;

/**
 * ZIP 파일을 지정된 경로에 압축해제하는 기능을 제공
 * 
 * @author DY.Oh
 *
 */
public class UnZipFile {

	/**
	 * 압축해제 경로
	 */
	public final String OUTPUT_DIR = "d:\\";
	/**
	 * 압축폴더명
	 */
	public String entryName;
	/**
	 * 압축파일 경로
	 */
	public String fileDirectory;
	/**
	 * 파일 포맷별 목록
	 */
	public Map<String, Object> fileNames;

	/**
	 * 압축폴더명 반환
	 * 
	 * @return String
	 */
	public String getEntryName() {
		return entryName;
	}

	/**
	 * 압축폴더명 설정
	 * 
	 * @param entryName
	 *            압축폴더명
	 */
	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}

	/**
	 * 파일 포맷별 목록 반환
	 * 
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getFileNames() {
		return fileNames;
	}

	/**
	 * 압축파일 경로 반환
	 * 
	 * @return String
	 */
	public String getFileDirectory() {
		return fileDirectory;
	}

	/**
	 * 압축파일 경로 설정
	 * 
	 * @param fileDirectory
	 *            압축파일 경로
	 */
	public void setFileDirectory(String fileDirectory) {
		this.fileDirectory = fileDirectory;
	}

	/**
	 * 파일 포맷별 목록 설정
	 * 
	 * @param fileNames
	 *            파일 포맷별 목록
	 */
	public void setFileNames(Map<String, Object> fileNames) {
		this.fileNames = fileNames;
	}

	/**
	 * 압축해제 경로 반환
	 * 
	 * @return String
	 */
	public String getOUTPUT_DIR() {
		return OUTPUT_DIR;
	}

	/**
	 * zip 파일의 압축 해제 후 지정된 경로에 압축 해제
	 * 
	 * @param zipFile
	 *            zip 파일 객체
	 * @throws Throwable
	 */
	public void decompress(File zipFile) throws Throwable {

		FileInputStream fis = null;
		ZipInputStream zis = null;
		ZipEntry zipentry = null;
		try {
			// 디렉토리생성
			String zipFileName = zipFile.getName(); // 파일 이름.확장자
			int comma = zipFileName.lastIndexOf(".");
			this.entryName = zipFileName.substring(0, comma); // 파일 이름
			this.fileDirectory = OUTPUT_DIR + entryName;
			FileUtils.forceMkdir(new File(OUTPUT_DIR, entryName));
			// 파일 스트림
			fis = new FileInputStream(zipFile);
			// Zip 파일 스트림
			zis = new ZipInputStream(fis);

			List<String> shpFileNames = new ArrayList<>();
			List<String> shxFileNames = new ArrayList<>();
			List<String> dbfFileNames = new ArrayList<>();

			// Fentry가 없을때까지 뽑기
			while ((zipentry = zis.getNextEntry()) != null) {
				String zipentryName = zipentry.getName();
				File file = new File(OUTPUT_DIR, zipentryName);
				// entiry가 폴더면 폴더 생성
				if (zipentry.isDirectory()) {
					file.mkdirs();
				} else {
					// 파일이면 파일 만들기
					String fileName = file.getName();
					if (fileName.endsWith(".shp")) {
						shpFileNames.add(fileName);
					} else if (fileName.endsWith(".shx")) {
						shxFileNames.add(fileName);
					} else if (fileName.endsWith(".dbf")) {
						dbfFileNames.add(fileName);
					}
					createFile(file, zis);
				}
			}
			this.fileNames = new HashMap<>();
			this.fileNames.put("shp", shpFileNames);
			this.fileNames.put("shx", shxFileNames);
			this.fileNames.put("dbf", dbfFileNames);
		} catch (Throwable e) {
			throw e;
		} finally {
			if (zis != null)
				zis.close();
			if (fis != null)
				fis.close();
		}
	}

	private void createFile(File file, ZipInputStream zis) throws Throwable {
		// 디렉토리 확인
		File parentDir = new File(file.getParent());
		// 디렉토리가 없으면 생성하자
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
		// 파일 스트림 선언
		try (FileOutputStream fos = new FileOutputStream(file)) {
			byte[] buffer = new byte[256];
			int size = 0;
			// Zip스트림으로부터 byte뽑아내기
			while ((size = zis.read(buffer)) > 0) {
				// byte로 파일 만들기
				fos.write(buffer, 0, size);
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
