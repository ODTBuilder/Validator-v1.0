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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.inject.Inject;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.gdsbuilder.FileRead.ngi.reader.QA20FileReader;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.qa20.collection.QA20LayerCollection;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.upload.domain.FileMeta;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class QA20FileUploadServiceImpl implements QA20FileUploadService {

	@Inject
	private QA20DBManagerService qa20dbManagerService;

	@Inject
	private GeoserverService geoserverService;

	public FileMeta ngiUpload(FileMeta fileMeta) throws Exception {

		String filePath = fileMeta.getFilePath();
		String src = fileMeta.getOriginSrc();

		// ngi & nda file read
		QA20FileReader fileReader = new QA20FileReader();
		QA20LayerCollection dtCollection = fileReader.read(filePath, "EPSG:" + src, "EUC-KR");
		dtCollection.setFileName(fileMeta.getFileName());

		// create GeoLayerInfo
		GeoLayerInfo layerInfo = new GeoLayerInfo();
		layerInfo.setFilePath(filePath);
		layerInfo.setFileType(fileMeta.getFileType());
		layerInfo.setFileName(fileMeta.getFileName());
		layerInfo.setOriginSrc(src);
		layerInfo.setTransSrc("EPSG:3857");

		// input DB layer
		GeoLayerInfo returnInfo = qa20dbManagerService.insertQA20LayerCollection(dtCollection, layerInfo);
		fileMeta.setDbInsertFlag(returnInfo.isDbInsertFlag());

		// publish Layer
		if (fileMeta.isDbInsertFlag()) {
			fileMeta.setUploadFlag(true);
			FileMeta geoserverFileMeta = geoserverService.dbLayerPublishGeoserver(returnInfo);
			boolean isPublished = geoserverFileMeta.isServerPublishFlag();
			fileMeta.setServerPublishFlag(isPublished);
			if (!isPublished) {
				// 다시 다 삭제
				GeoLayerInfo returnDropInfo = qa20dbManagerService.dropQA20LayerCollection(dtCollection, layerInfo);
				fileMeta.setUploadFlag(returnDropInfo.isUploadFlag());
			}
		}
		return fileMeta;
	}

	public void testReport() throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("D:\\test3.txt"));
		JSONObject jsonObject = (JSONObject) obj;

		// errorReportService.getISOReport("33611044", jsonObject);
		// errorReportService.getDetailsReport("33611044");
	}
}
