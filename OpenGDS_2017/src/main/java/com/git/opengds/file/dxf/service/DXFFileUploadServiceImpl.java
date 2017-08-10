package com.git.opengds.file.dxf.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.gdsbuilder.FileRead.dxf.reader.DXFFileReader;
import com.git.gdsbuilder.type.dxf.collection.DTDXFLayerCollection;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.upload.domain.FileMeta;
import com.git.opengds.user.domain.UserVO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class DXFFileUploadServiceImpl implements DXFFileUploadService {

	@Inject
	private DXFDBManagerService dbmanagerService;

	@Inject
	private GeoserverService geoserverService;
	
	
/*	public QA10FileUploadServiceImpl(UserVO userVO) {
		// TODO Auto-generated constructor stub
		dbmanagerService = new QA10DBManagerServiceImpl(userVO);
		geoserverService = new GeoserverServiceImpl(userVO);
	}
*/
	@Override
	public FileMeta dxfUpload(UserVO userVO, FileMeta fileMeta) throws Exception {

		// dxf file read
		DXFFileReader fileReader = new DXFFileReader();
		DTDXFLayerCollection collection = fileReader.read(fileMeta);

		// create GeoLayerInfo
		GeoLayerInfo layerInfo = new GeoLayerInfo();
		layerInfo.setFilePath(fileMeta.getFilePath());
		layerInfo.setFileType(fileMeta.getFileType());
		layerInfo.setFileName(fileMeta.getFileName());
		layerInfo.setOriginSrc(fileMeta.getOriginSrc());
		layerInfo.setTransSrc("EPSG:3857");

		// input DB layer
		GeoLayerInfo returnInfo = dbmanagerService.insertDXFLayerCollection(userVO, collection, layerInfo);
		fileMeta.setDbInsertFlag(returnInfo.isDbInsertFlag());
		// publish Layer
		if (fileMeta.isDbInsertFlag()) {
			fileMeta.setUploadFlag(true);
			FileMeta geoserverFileMeta = geoserverService.dbLayerPublishGeoserver(userVO,returnInfo);
			boolean isPublished = geoserverFileMeta.isServerPublishFlag();
			fileMeta.setServerPublishFlag(isPublished);
			if (!isPublished) {
				GeoLayerInfo returnDropInfo = dbmanagerService.dropDXFLayerCollection(userVO,collection, layerInfo);
				fileMeta.setUploadFlag(returnDropInfo.isUploadFlag());
			}
		}
		return fileMeta;
	}
}
