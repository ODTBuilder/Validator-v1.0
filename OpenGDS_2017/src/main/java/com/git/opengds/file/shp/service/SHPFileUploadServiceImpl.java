package com.git.opengds.file.shp.service;

import java.io.File;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.gdsbuilder.FileRead.UnZipFile;
import com.git.gdsbuilder.FileRead.shp.SHPFileReader;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.shp.collection.DTSHPLayerCollection;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.upload.domain.FileMeta;
import com.git.opengds.user.domain.UserVO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class SHPFileUploadServiceImpl implements SHPFileUploadService {

	@Inject
	private SHPDBManagerService shpdbManagerService;

	@Inject
	private GeoserverService geoserverService;

	public FileMeta shpUpload(UserVO userVO, FileMeta fileMeta) throws Throwable {

		String filePath = fileMeta.getFilePath();
		String src = fileMeta.getOriginSrc();

		UnZipFile unZipFile = new UnZipFile();
		unZipFile.decompress(new File(filePath));
		String upZipFilePath = unZipFile.getFileDirectory();
		String entryName = unZipFile.getEntryName();
		SHPFileReader fileReader = new SHPFileReader();
		Map<String, Object> fileNameMap = unZipFile.getFileNames();
		DTSHPLayerCollection dtCollection = fileReader.read(upZipFilePath, entryName, fileNameMap);

		// create GeoLayerInfo
		GeoLayerInfo layerInfo = new GeoLayerInfo();
		layerInfo.setFilePath(filePath);
		layerInfo.setFileType(fileMeta.getFileType());
		layerInfo.setFileName(fileMeta.getFileName());
		layerInfo.setOriginSrc(src);
		layerInfo.setTransSrc("EPSG:3857");

		GeoLayerInfo geoLayerInfo = shpdbManagerService.insertSHPLayerCollection(userVO, dtCollection, layerInfo);
		fileMeta.setDbInsertFlag(geoLayerInfo.isDbInsertFlag());
		
		// publish Layer
		if (fileMeta.isDbInsertFlag()) {
			fileMeta.setUploadFlag(true);
			FileMeta geoserverFileMeta = geoserverService.dbLayerPublishGeoserver(userVO, geoLayerInfo);
			boolean isPublished = geoserverFileMeta.isServerPublishFlag();
			fileMeta.setServerPublishFlag(isPublished);
		}
		return fileMeta;
	}
}
