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

package com.git.opengds.validator.service;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfoList;
import com.git.gdsbuilder.type.validate.error.ErrorLayer;
import com.git.gdsbuilder.type.validate.error.ErrorLayerList;
import com.git.opengds.file.dxf.dbManager.DXFDBQueryManager;
import com.git.opengds.file.dxf.persistence.DXFLayerCollectionDAO;
import com.git.opengds.file.ngi.dbManager.NGIDBQueryManager;
import com.git.opengds.file.ngi.persistence.NGILayerCollectionDAO;
import com.git.opengds.file.shp.dbManager.SHPDBQueryManager;
import com.git.opengds.file.shp.persistence.SHPLayerCollectionDAO;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.user.domain.UserVO;
import com.git.opengds.validator.dbManager.ErrorLayerDBQueryManager;
import com.git.opengds.validator.persistence.ErrorLayerDAO;
import com.git.opengds.validator.persistence.ValidateProgressDAO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class ErrorLayerServiceImpl implements ErrorLayerService {

	@Inject
	private ErrorLayerDAO errLayerDAO;

	/*
	 * @Autowired private DataSourceTransactionManager txManager;
	 */
	@Inject
	private DXFLayerCollectionDAO qa10DAO;

	@Inject
	private NGILayerCollectionDAO qa20DAO;

	@Inject
	private SHPLayerCollectionDAO shpDAO;

	@Inject
	private ValidateProgressDAO progressDAO;

	@Autowired
	private GeoserverService geoserverService;

	/*
	 * public ErrorLayerServiceImpl(UserVO userVO) { // TODO Auto-generated
	 * constructor stub errLayerDAO = new ErrorLayerDAOImpl(userVO); qa10DAO =
	 * new QA10LayerCollectionDAOImpl(userVO); qa20DAO = new
	 * QA20LayerCollectionDAOImpl(userVO); progressDAO = new
	 * ValidateProgressDAOImpl(userVO); geoserverService = new
	 * GeoserverServiceImpl(userVO); errorReportService = new
	 * ErrorReportServiceImpl(userVO); }
	 */

	public boolean publishErrorLayerList(UserVO userVO, ErrorLayerList errLayers)
			throws IllegalArgumentException, MalformedURLException {

		/*
		 * DefaultTransactionDefinition def = new
		 * DefaultTransactionDefinition();
		 * def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED
		 * ); TransactionStatus status = txManager.getTransaction(def);
		 */
		GeoLayerInfoList geoLayerInfoList = new GeoLayerInfoList();

		try {
			for (int i = 0; i < errLayers.size(); i++) {

				ErrorLayer errLayer = errLayers.get(i);
				String fileType = errLayer.getCollectionType();
				String collectionName = errLayer.getCollectionName();
				ErrorLayerDBQueryManager queryManager = new ErrorLayerDBQueryManager(errLayer);

				String errTableName = "\"" + "err_" + fileType + "_";
				Integer cIdx = null;
				Long errTbCount = null;
				if (fileType.equals("ngi")) {
					NGIDBQueryManager qa20dbQueryManager = new NGIDBQueryManager();
					cIdx = qa20DAO.selectNGILayerCollectionIdx(userVO,
							qa20dbQueryManager.getSelectNGILayerCollectionIdx(collectionName));
				}
				if (fileType.equals("dxf")) {
					DXFDBQueryManager qa10dbQueryManager = new DXFDBQueryManager();
					cIdx = qa10DAO.selectDXFLayerCollectionIdx(userVO,
							qa10dbQueryManager.getSelectDXFLayerCollectionIdxQuery(collectionName));
				}
				if (fileType.equals("shp")) {
					SHPDBQueryManager shpdbQueryManager = new SHPDBQueryManager();
					cIdx = shpDAO.selectSHPLayerCollectionIdx(userVO,
							shpdbQueryManager.getSelectSHPLayerCollectionIdxQuery(collectionName));
				}
				HashMap<String, Object> selectIdxQuery = queryManager.selectErrorLayerTbNamesCountQuery(fileType,
						collectionName, cIdx);
				errTbCount = progressDAO.selectErrorLayerTbNamesCount(userVO, selectIdxQuery);
				if (errTbCount == null) {
					errTableName += collectionName + "\"";
				} else {
					Long count = errTbCount + 1;
					errTableName += collectionName + "_" + count + "\"";
				}

				// create
				HashMap<String, Object> createQuery = queryManager.createErrorLayerTbQuery(errTableName);
				errLayerDAO.createErrorLayerTb(userVO, createQuery);
				// insert
				List<HashMap<String, Object>> insertQuerys = queryManager.insertErrorLayerQuery(errTableName);
				for (int j = 0; j < insertQuerys.size(); j++) {
					HashMap<String, Object> insertQuery = insertQuerys.get(j);
					errLayerDAO.insertErrorFeature(userVO, insertQuery);
				}
				GeoLayerInfo layerInfo = new GeoLayerInfo();
				layerInfo.setFileName(errTableName);
				layerInfo.setOriginSrc("EPSG:5186");
				layerInfo.setTransSrc("EPSG:3857");
				layerInfo.setFileType(fileType);
				geoLayerInfoList.add(layerInfo);
			}
		} catch (Exception e) {
			// txManager.rollback(status);
			return false;
		}
		// txManager.commit(status);
		boolean isSuccessed = geoserverService.errLayerListPublishGeoserver(geoLayerInfoList);
		if (isSuccessed) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Map<String, Object> publishErrorLayer(UserVO userVO, ErrorLayer errLayer)
			throws IllegalArgumentException, MalformedURLException {

		/*
		 * DefaultTransactionDefinition def = new
		 * DefaultTransactionDefinition();
		 * def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED
		 * ); TransactionStatus status = txManager.getTransaction(def);
		 */
		GeoLayerInfo layerInfo = new GeoLayerInfo();
		ErrorLayerDBQueryManager queryManager = new ErrorLayerDBQueryManager(errLayer);
		Map<String, Object> returnMap = new HashMap<String, Object>();

		try {
			String fileType = errLayer.getCollectionType();
			String collectionName = errLayer.getCollectionName();
			String errTableName = "err_" + fileType + "_";
			Integer cIdx = null;
			Long errTbCount = null;
			if (fileType.equals("ngi")) {
				NGIDBQueryManager qa20dbQueryManager = new NGIDBQueryManager();
				cIdx = qa20DAO.selectNGILayerCollectionIdx(userVO,
						qa20dbQueryManager.getSelectNGILayerCollectionIdx(collectionName));
			}
			if (fileType.equals("dxf")) {
				DXFDBQueryManager qa10dbQueryManager = new DXFDBQueryManager();
				cIdx = qa10DAO.selectDXFLayerCollectionIdx(userVO,
						qa10dbQueryManager.getSelectDXFLayerCollectionIdxQuery(collectionName));
			}
			if (fileType.equals("shp")) {
				SHPDBQueryManager shpdbQueryManager = new SHPDBQueryManager();
				cIdx = shpDAO.selectSHPLayerCollectionIdx(userVO,
						shpdbQueryManager.getSelectSHPLayerCollectionIdxQuery(collectionName));
			}
			HashMap<String, Object> selectIdxQuery = queryManager.selectErrorLayerTbNamesCountQuery(fileType,
					collectionName, cIdx);
			errTbCount = progressDAO.selectErrorLayerTbNamesCount(userVO, selectIdxQuery);
			if (errTbCount == null) {
				errTableName += collectionName + "\"";
			} else {
				Long count = errTbCount + 1;
				errTableName += collectionName + "_" + count;
			}
			// create
			HashMap<String, Object> createQuery = queryManager.createErrorLayerTbQuery(errTableName);
			errLayerDAO.createErrorLayerTb(userVO, createQuery);
			// insert
			List<HashMap<String, Object>> insertQuerys = queryManager.insertErrorLayerQuery(errTableName);
			for (int j = 0; j < insertQuerys.size(); j++) {
				HashMap<String, Object> insertQuery = insertQuerys.get(j);
				errLayerDAO.insertErrorFeature(userVO, insertQuery);
			}
			layerInfo.setFileName(errTableName);
			layerInfo.setOriginSrc("EPSG:5186");
			layerInfo.setTransSrc("EPSG:3857");
			layerInfo.setFileType(fileType);
		} catch (

		Exception e) {
			// txManager.rollback(status);
			returnMap.put("flag", false);
			return returnMap;
		}
		// txManager.commit(status);
		boolean isSuccessed = geoserverService.errLayerPublishGeoserver(userVO, layerInfo);
		String errTableName = layerInfo.getFileName();
		if (isSuccessed) {
			returnMap.put("flag", true);
			returnMap.put("errTbName", errTableName);
			return returnMap;
		} else {
			HashMap<String, Object> dropQuery = queryManager.dropjErrorLayerTbQuery(errTableName);
			errLayerDAO.dropErrorLayerTb(userVO, dropQuery);
			returnMap.put("flag", false);
			return returnMap;
		}
	}
}
