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

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.gdsbuilder.FileRead.ngi.reader.NGIFileReader;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.ngi.collection.DTNGILayerCollection;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.upload.domain.FileMeta;
import com.git.opengds.user.domain.UserVO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class NGIFileUploadServiceImpl implements NGIFileUploadService {

	@Inject
	private NGIDBManagerService ngidbManagerService;

	@Inject
	private GeoserverService geoserverService;

	/*
	 * public QA20FileUploadServiceImpl(UserVO userVO) { // TODO Auto-generated
	 * constructor stub qa20dbManagerService = new
	 * QA20DBManagerServiceImpl(userVO); geoserverService = new
	 * GeoserverServiceImpl(userVO); }
	 */

	public FileMeta ngiUpload(UserVO userVO, FileMeta fileMeta) throws Exception {

		String filePath = fileMeta.getFilePath();
		String src = fileMeta.getOriginSrc();

		// ngi & nda file read
		NGIFileReader fileReader = new NGIFileReader();
		DTNGILayerCollection dtCollection = fileReader.read(filePath, "EPSG:" + src, "EUC-KR");
		dtCollection.setFileName(fileMeta.getFileName());

		// create GeoLayerInfo
		GeoLayerInfo layerInfo = new GeoLayerInfo();
		layerInfo.setFilePath(filePath);
		layerInfo.setFileType(fileMeta.getFileType());
		layerInfo.setFileName(fileMeta.getFileName());
		layerInfo.setOriginSrc(src);
		layerInfo.setTransSrc("EPSG:3857");

		// input DB layer
		GeoLayerInfo returnInfo = ngidbManagerService.insertNGILayerCollection(userVO, dtCollection, layerInfo);
		fileMeta.setDbInsertFlag(returnInfo.isDbInsertFlag());

		// publish Layer
		if (fileMeta.isDbInsertFlag()) {
			fileMeta.setUploadFlag(true);
			FileMeta geoserverFileMeta = geoserverService.dbLayerPublishGeoserver(userVO, returnInfo);
			boolean isPublished = geoserverFileMeta.isServerPublishFlag();
			fileMeta.setServerPublishFlag(isPublished);
			if (!isPublished) {
				// 다시 다 삭제
				GeoLayerInfo returnDropInfo = ngidbManagerService.dropNGILayerCollection(userVO, dtCollection,
						layerInfo);
				fileMeta.setUploadFlag(returnDropInfo.isUploadFlag());
			}
		}
		return fileMeta;
	}
}
