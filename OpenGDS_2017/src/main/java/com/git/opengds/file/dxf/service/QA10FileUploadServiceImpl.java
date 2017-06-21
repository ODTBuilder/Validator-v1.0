package com.git.opengds.file.dxf.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.gdsbuilder.FileRead.dxf.reader.QA10FileReader;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.qa10.collection.QA10LayerCollection;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.upload.domain.FileMeta;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class QA10FileUploadServiceImpl implements QA10FileUploadService {

	@Inject
	QA10DBManagerService dbmanagerService;

	@Inject
	private GeoserverService geoserverService;

	@Override
	public FileMeta dxfUpload(FileMeta fileMeta) throws Exception {

		// dxf file read
		QA10FileReader fileReader = new QA10FileReader();
		QA10LayerCollection collection = fileReader.read(fileMeta);

		// create GeoLayerInfo
		GeoLayerInfo layerInfo = new GeoLayerInfo();
		layerInfo.setFilePath(fileMeta.getFilePath());
		layerInfo.setFileType(fileMeta.getFileType());
		layerInfo.setFileName(fileMeta.getFileName());
		layerInfo.setOriginSrc(fileMeta.getOriginSrc());
		layerInfo.setTransSrc("EPSG:3857");

		// input DB layer
		GeoLayerInfo returnInfo = dbmanagerService.insertQA10LayerCollection(collection, layerInfo);
		fileMeta.setDbInsertFlag(returnInfo.isDbInsertFlag());
		// publish Layer
		if (returnInfo != null) {
			fileMeta.setUploadFlag(true);
			FileMeta geoserverFileMeta = geoserverService.dbLayerPublishGeoserver(returnInfo);
			boolean isPublished = geoserverFileMeta.isServerPublishFlag();
			fileMeta.setServerPublishFlag(isPublished);
			if(!isPublished) {
				// 다시 다 삭제
			}
			System.out.println("서버성공");
		}
		return fileMeta;
	}

}
