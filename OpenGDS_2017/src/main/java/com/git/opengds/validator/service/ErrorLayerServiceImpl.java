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

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfoList;
import com.git.gdsbuilder.type.validate.error.ErrorLayer;
import com.git.gdsbuilder.type.validate.error.ErrorLayerList;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.validator.dbManager.ErrorLayerDBQueryManager;
import com.git.opengds.validator.persistence.ErrorLayerDAO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class ErrorLayerServiceImpl implements ErrorLayerService {

	@Inject
	private ErrorLayerDAO errLayerDAO;

	@Autowired
	private DataSourceTransactionManager txManager;

	@Autowired
	private GeoserverService geoserverService;

	@Autowired
	private ErrorReportService errorReportService;

	public boolean publishErrorLayer(ErrorLayerList errLayers) throws IllegalArgumentException, MalformedURLException {

		GeoLayerInfoList geoLayerInfoList = new GeoLayerInfoList();

		for (int i = 0; i < errLayers.size(); i++) {
			ErrorLayer errLayer = errLayers.get(i);
			String fileType = errLayer.getCollectionType();
			ErrorLayerDBQueryManager converter = new ErrorLayerDBQueryManager(errLayer);
			// create
			HashMap<String, Object> createQuery = converter.createErrorLayerTbQuery();
			errLayerDAO.createErrorLayerTb(createQuery);
			// insert
			List<HashMap<String, Object>> insertQuerys = converter.insertErrorLayerQuery();
			for (int j = 0; j < insertQuerys.size(); j++) {
				HashMap<String, Object> insertQuery = insertQuerys.get(j);
				errLayerDAO.insertErrorFeature(insertQuery);
			}
			GeoLayerInfo layerInfo = new GeoLayerInfo();
			layerInfo.setFileName(errLayer.getCollectionName());
			layerInfo.setOriginSrc("EPSG:5186");
			layerInfo.setTransSrc("EPSG:3857");
			layerInfo.setFileType(fileType);
			geoLayerInfoList.add(layerInfo);
		}
		geoserverService.errLayerPublishGeoserver(geoLayerInfoList);
		return true;
	}
}
