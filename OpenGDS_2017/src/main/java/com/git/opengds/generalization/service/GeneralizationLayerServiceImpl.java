package com.git.opengds.generalization.service;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.generalization.data.res.DTGeneralEAfLayer;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.opengds.file.shp.dbManager.SHPDBQueryManager;
import com.git.opengds.file.shp.persistence.SHPLayerCollectionDAO;
import com.git.opengds.generalization.dbManager.GenLayerDBQueryManager;
import com.git.opengds.generalization.dbManager.GenProgressQueryManager;
import com.git.opengds.generalization.persistance.GeneralizationLayerDAO;
import com.git.opengds.generalization.persistance.GeneralizationProgressDAO;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.user.domain.UserVO;

@Service
public class GeneralizationLayerServiceImpl implements GeneralizationLayerService {

	// @Inject
	// private DXFLayerCollectionDAO dxfDAO;
	//
	// @Inject
	// private NGILayerCollectionDAO ngiDAO;

	@Inject
	private SHPLayerCollectionDAO shpDAO;

	@Inject
	private GeneralizationLayerDAO genLayerDAO;

	@Inject
	private GeneralizationProgressDAO progressDAO;

	@Inject
	private GeoserverService geoserverService;

	@Override
	public String publishGenLayer(UserVO userVO, DTGeneralEAfLayer genLayer, String fileType, String fileName,
			String layerName, String layerType, String src) throws IllegalArgumentException, MalformedURLException {

		// create Table
		SimpleFeatureCollection afterGeoSfc = genLayer.getCollection();

		String genTableName = "";
		GenLayerDBQueryManager genDBManager = new GenLayerDBQueryManager();
		GenProgressQueryManager proDBManager = new GenProgressQueryManager();

		if (fileType.equals("shp")) {
			SHPDBQueryManager shpdbQueryManager = new SHPDBQueryManager();
			Integer cIdx = shpDAO.selectSHPLayerCollectionIdx(userVO,
					shpdbQueryManager.getSelectSHPLayerCollectionIdxQuery(fileName));
			HashMap<String, Object> selectTbCountQuery = proDBManager.selectGenLayerTbNamesCountQuery(fileType,
					fileName, cIdx);
			Long tbCount = progressDAO.selectGenLayerTablesCount(userVO, selectTbCountQuery);
			tbCount += 1;
			genTableName = "geo_" + fileType + "_" + fileName + "_" + layerName + "_gen" + tbCount + "_" + layerType;
			HashMap<String, Object> createTbMap = genDBManager.getCreateGenLayerTbQuery("\"" + genTableName + "\"",
					afterGeoSfc, layerType, src);

			HashMap<String, Object> createTbQuery = (HashMap<String, Object>) createTbMap.get("createQueryMap");
			List<String> attriKeyList = (List<String>) createTbMap.get("attriKeyList");
			genLayerDAO.createGenLayerTb(userVO, createTbQuery);
			SimpleFeatureIterator iterator = afterGeoSfc.features();
			while (iterator.hasNext()) {
				SimpleFeature simeFeature = iterator.next();
				HashMap<String, Object> insertQuery = genDBManager.getInsertGenLayerFeaturesQuery(
						"\"" + genTableName + "\"", fileName, layerName, attriKeyList, simeFeature, src);
				genLayerDAO.insertGenFeature(userVO, insertQuery);
			}
		}
		GeoLayerInfo layerInfo = new GeoLayerInfo();
		layerInfo.setFileName(genTableName);
		layerInfo.setOriginSrc("EPSG:" + src);
		layerInfo.setFileType(fileType);

		boolean isPublished = geoserverService.errLayerPublishGeoserver(userVO, layerInfo);
		if (isPublished) {
			return genTableName;
		} else {
			return null;
		}
	}
}
