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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfoList;
import com.git.gdsbuilder.type.validate.error.ErrorLayer;
import com.git.gdsbuilder.type.validate.error.ErrorLayerList;
import com.git.opengds.file.dxf.dbManager.QA10DBQueryManager;
import com.git.opengds.file.dxf.persistence.QA10LayerCollectionDAO;
import com.git.opengds.file.ngi.dbManager.QA20DBQueryManager;
import com.git.opengds.file.ngi.persistence.QA20LayerCollectionDAO;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.validator.dbManager.ErrorLayerDBQueryManager;
import com.git.opengds.validator.persistence.ErrorLayerDAO;
import com.git.opengds.validator.persistence.ValidateProgressDAO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class ErrorLayerServiceImpl implements ErrorLayerService {

	@Inject
	private ErrorLayerDAO errLayerDAO;

	@Autowired
	private DataSourceTransactionManager txManager;

	@Inject
	private QA10LayerCollectionDAO qa10DAO;

	@Inject
	private QA20LayerCollectionDAO qa20DAO;

	@Inject
	private ValidateProgressDAO progressDAO;

	@Autowired
	private GeoserverService geoserverService;

	@Autowired
	private ErrorReportService errorReportService;

	public boolean publishErrorLayerList(ErrorLayerList errLayers)
			throws IllegalArgumentException, MalformedURLException {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);
		GeoLayerInfoList geoLayerInfoList = new GeoLayerInfoList();

		try {
			for (int i = 0; i < errLayers.size(); i++) {

				ErrorLayer errLayer = errLayers.get(i);
				String fileType = errLayer.getCollectionType();
				String collectionName = errLayer.getCollectionName();
				ErrorLayerDBQueryManager queryManager = new ErrorLayerDBQueryManager(errLayer);

				String errTableName = "\"" + "err_" + fileType + "_";
				Integer cIdx = null;
				if (fileType.equals("ngi")) {
					QA20DBQueryManager qa20dbQueryManager = new QA20DBQueryManager();
					cIdx = qa20DAO.selectQA20LayerCollectionIdx(
							qa20dbQueryManager.getSelectQA20LayerCollectionIdx(collectionName));
					HashMap<String, Object> selectIdxQuery = queryManager
							.selectQA20ErrorLayerTbNamesCountQuery(fileType, collectionName, cIdx);
					Long errTbCount = progressDAO.selectQA20ErrorLayerTbNamesCount(selectIdxQuery);
					if (errTbCount == null) {
						errTableName += collectionName;
					} else {
						Long count = errTbCount + 1;
						errTableName += collectionName + "_" + count;
					}
				}
				if (fileType.equals("dxf")) {
					QA10DBQueryManager qa10dbQueryManager = new QA10DBQueryManager();
					cIdx = qa10DAO.selectQA10LayerCollectionIdx(
							qa10dbQueryManager.getSelectLayerCollectionIdx(collectionName));
					HashMap<String, Object> selectIdxQuery = queryManager
							.selectQA10ErrorLayerTbNamesCountQuery(fileType, collectionName, cIdx);
					Long errTbCount = progressDAO.selectQA10ErrorLayerTbNamesCount(selectIdxQuery);
					if (errTbCount == null) {
						errTableName += collectionName;
					} else {
						Long count = errTbCount + 1;
						errTableName += collectionName + "_" + count;
					}
				}
				errTableName += "\"";

				// create
				HashMap<String, Object> createQuery = queryManager.createErrorLayerTbQuery(errTableName);
				errLayerDAO.createErrorLayerTb(createQuery);
				// insert
				List<HashMap<String, Object>> insertQuerys = queryManager.insertErrorLayerQuery(errTableName);
				for (int j = 0; j < insertQuerys.size(); j++) {
					HashMap<String, Object> insertQuery = insertQuerys.get(j);
					errLayerDAO.insertErrorFeature(insertQuery);
				}
				GeoLayerInfo layerInfo = new GeoLayerInfo();
				layerInfo.setFileName(errTableName);
				layerInfo.setOriginSrc("EPSG:5186");
				layerInfo.setTransSrc("EPSG:3857");
				layerInfo.setFileType(fileType);
				geoLayerInfoList.add(layerInfo);
			}
		} catch (Exception e) {
			txManager.rollback(status);
			return false;
		}
		txManager.commit(status);
		boolean isSuccessed = geoserverService.errLayerListPublishGeoserver(geoLayerInfoList);
		if (isSuccessed) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Map<String, Object> publishErrorLayer(ErrorLayer errLayer)
			throws IllegalArgumentException, MalformedURLException {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);
		GeoLayerInfo layerInfo = new GeoLayerInfo();
		ErrorLayerDBQueryManager queryManager = new ErrorLayerDBQueryManager(errLayer);
		Map<String, Object> returnMap = new HashMap<String, Object>();

		try {
			String fileType = errLayer.getCollectionType();
			String collectionName = errLayer.getCollectionName();
			String errTableName = "err_" + fileType + "_";
			Integer cIdx = null;
			if (fileType.equals("ngi")) {
				QA20DBQueryManager qa20dbQueryManager = new QA20DBQueryManager();
				cIdx = qa20DAO.selectQA20LayerCollectionIdx(
						qa20dbQueryManager.getSelectQA20LayerCollectionIdx(collectionName));
				HashMap<String, Object> selectIdxQuery = queryManager.selectQA20ErrorLayerTbNamesCountQuery(fileType,
						collectionName, cIdx);
				Long errTbCount = progressDAO.selectQA20ErrorLayerTbNamesCount(selectIdxQuery);
				if (errTbCount == null) {
					errTableName += collectionName;
				} else {
					errTableName += collectionName + "_" + errTbCount;

				}
			}
			if (fileType.equals("dxf")) {
				QA10DBQueryManager qa10dbQueryManager = new QA10DBQueryManager();
				cIdx = qa10DAO
						.selectQA10LayerCollectionIdx(qa10dbQueryManager.getSelectLayerCollectionIdx(collectionName));
				HashMap<String, Object> selectIdxQuery = queryManager.selectQA10ErrorLayerTbNamesCountQuery(fileType,
						collectionName, cIdx);
				Long errTbCount = progressDAO.selectQA10ErrorLayerTbNamesCount(selectIdxQuery);
				if (errTbCount == null) {
					errTableName += collectionName;
				} else {
					errTableName += collectionName +  "_" + errTbCount;
				}
			}

			// create
			HashMap<String, Object> createQuery = queryManager.createErrorLayerTbQuery(errTableName);
			errLayerDAO.createErrorLayerTb(createQuery);
			// insert
			List<HashMap<String, Object>> insertQuerys = queryManager.insertErrorLayerQuery(errTableName);
			for (int j = 0; j < insertQuerys.size(); j++) {
				HashMap<String, Object> insertQuery = insertQuerys.get(j);
				errLayerDAO.insertErrorFeature(insertQuery);
			}
			layerInfo.setFileName(errTableName);
			layerInfo.setOriginSrc("EPSG:5186");
			layerInfo.setTransSrc("EPSG:3857");
			layerInfo.setFileType(fileType);
		} catch (Exception e) {
			txManager.rollback(status);
			returnMap.put("flag", false);
			return returnMap;
		}
		txManager.commit(status);
		boolean isSuccessed = geoserverService.errLayerPublishGeoserver(layerInfo);
		String errTableName = layerInfo.getFileName();
		if (isSuccessed) {
			returnMap.put("flag", true);
			returnMap.put("errTbName", errTableName);
			return returnMap;
		} else {
			HashMap<String, Object> dropQuery = queryManager.dropjErrorLayerTbQuery(errTableName);
			errLayerDAO.dropErrorLayerTb(dropQuery);
			returnMap.put("flag", false);
			return returnMap;
		}
	}
}
