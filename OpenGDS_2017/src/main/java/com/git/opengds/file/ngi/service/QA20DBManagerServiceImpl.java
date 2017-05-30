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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.qa20.collection.QA20LayerCollection;
import com.git.opengds.file.ngi.dbManager.QA20DBQueryManager;
import com.git.opengds.file.ngi.persistence.QA20LayerCollectionDAO;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.upload.domain.FileMeta;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class QA20DBManagerServiceImpl implements QA20DBManagerService {

	@Inject
	private DataSourceTransactionManager txManager;

	@Inject
	private QA20LayerCollectionDAO dao;
	
	@Inject
	private GeoserverService geoserverService;

	public GeoLayerInfo insertQA20LayerCollection(QA20LayerCollection dtCollection, GeoLayerInfo layerInfo) throws Exception {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);

		try {
			QA20DBQueryManager dbManager = new QA20DBQueryManager(dtCollection, layerInfo);
			Map<String, Object> qa20Collection = dbManager.qa20LayerCollection();
			dao.insertQA20LayerCollection(qa20Collection);

			List<HashMap<String, Object>> qa20Metadatas = dbManager.qa20LayerMetadata();
			for (int i = 0; i < qa20Metadatas.size(); i++) {

				// insertMetadata
				HashMap<String, Object> metadata = qa20Metadatas.get(i);
				dao.insertQA20LayerMetadatas(metadata);

				// createQA20Layer
				String layerID = (String) metadata.get("layerID");
				HashMap<String, Object> createQuery = dbManager.qa20LayerTbCreateQuery(layerID);
				dao.createQA20LayerTb(createQuery);

				// insertQA20Layer
				List<HashMap<String, Object>> qa20Layers = dbManager.qa20LayerTbInsertQuery(layerID);
				for (int j = 0; j < qa20Layers.size(); j++) {
					dao.insertQA20Layer(qa20Layers.get(j));
				}
				
				// select Boundary
//				HashMap<String, Object> selectBDQuery = dbManager.qa20LayerTbBoundaryQuery(layerID);
//				HashMap<String, Object> boundryMap = dao.selectQA20LayerBD(selectBDQuery);
				
				// aspatial_field_def
				List<HashMap<String, Object>> fieldDefs = dbManager.aspatialFieldDefs(layerID);
				if (fieldDefs != null) {
					for (int j = 0; j < fieldDefs.size(); j++) {
						dao.insertNdaAspatialFieldDefs(fieldDefs.get(j));
					}
				}

				// point_represent
				if ((Boolean) metadata.get("ptRepresent")) {
					List<HashMap<String, Object>> txtReps = dbManager.ptRepresent(layerID);
					for (int j = 0; j < txtReps.size(); j++) {
						dao.insertPointRepresent(txtReps.get(j));
					}
				}

				// lineString_represent
				if ((Boolean) metadata.get("lnRepresent")) {
					List<HashMap<String, Object>> txtReps = dbManager.lnRepresent(layerID);
					for (int j = 0; j < txtReps.size(); j++) {
						dao.insertLineStringRepresent(txtReps.get(j));
					}
				}

				// region_represent
				if ((Boolean) metadata.get("rgRepresent")) {
					List<HashMap<String, Object>> txtReps = dbManager.rgRepresent(layerID);
					for (int j = 0; j < txtReps.size(); j++) {
						dao.insertRegionRepresent(txtReps.get(j));
					}
				}

				// text_represent
				if ((Boolean) metadata.get("txRepresent")) {
					List<HashMap<String, Object>> txtReps = dbManager.txtRepresent(layerID);
					for (int j = 0; j < txtReps.size(); j++) {
						dao.insertTextRepresent(txtReps.get(j));
					}
				}

				// geoLayerInfo
				String layerName = (String) metadata.get("layerName");
				layerInfo.putLayerName(layerName);
				String layerType = dbManager.getLayerType(layerID);
				layerInfo.putLayerType(layerName, layerType);
				List<String> columns = dbManager.getLayerCoulmns(layerID);
				layerInfo.putLayerColumns(layerName, columns);
				//layerInfo.putLayerBoundary(layerName, boundryMap);
			}
		} catch (Exception e) {
			txManager.rollback(status);
			return null;
		}
		if (layerInfo != null) {
			txManager.commit(status);
			FileMeta geoserverFileMeta = geoserverService.dbLayerPublishGeoserver(layerInfo);
			System.out.println("서버성공");
		}
		return layerInfo;
	}
}
