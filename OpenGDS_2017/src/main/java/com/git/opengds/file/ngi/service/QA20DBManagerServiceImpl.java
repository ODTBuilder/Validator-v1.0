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

import javax.inject.Inject;

import org.apache.log4j.lf5.viewer.TrackingAdjustmentListener;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.qa20.collection.QA20LayerCollection;
import com.git.gdsbuilder.type.qa20.header.NDAHeader;
import com.git.gdsbuilder.type.qa20.header.NGIHeader;
import com.git.gdsbuilder.type.qa20.layer.QA20Layer;
import com.git.gdsbuilder.type.qa20.layer.QA20LayerList;
import com.git.opengds.file.ngi.dbManager.QA20DBQueryManager;
import com.git.opengds.file.ngi.persistence.QA20LayerCollectionDAO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class QA20DBManagerServiceImpl implements QA20DBManagerService {

	@Inject
	private DataSourceTransactionManager txManager;

	@Inject
	private QA20LayerCollectionDAO dao;

	public GeoLayerInfo insertQA20LayerCollection(QA20LayerCollection dtCollection, GeoLayerInfo layerInfo)
			throws Exception {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);

		try {
			QA20DBQueryManager dbManager = new QA20DBQueryManager();
			// EditLayerDBQueryManager queryManager = new
			// EditLayerDBQueryManager();

			String collectionName = dtCollection.getFileName();
			String type = layerInfo.getFileType();

			HashMap<String, Object> insertCollectionQuery = dbManager.getInsertLayerCollection(collectionName);
			int cIdx = dao.insertQA20LayerCollection(insertCollectionQuery);

			QA20LayerList createLayerList = dtCollection.getQa20LayerList();
			for (int i = 0; i < createLayerList.size(); i++) {
				QA20Layer qa20Layer = createLayerList.get(i);
				String layerName = qa20Layer.getLayerName();

				// createQA20Layer
				HashMap<String, Object> createQuery = dbManager.qa20LayerTbCreateQuery(type, collectionName, qa20Layer);
				dao.createQA20LayerTb(createQuery);

				// insertQA20Layer
				List<HashMap<String, Object>> inertLayerQuerys = dbManager.qa20LayerInsertQuery(type, collectionName,
						qa20Layer);
				for (int j = 0; j < inertLayerQuerys.size(); j++) {
					HashMap<String, Object> insertLayerQuery = inertLayerQuerys.get(j);
					dao.insertQA20Layer(insertLayerQuery);
				}

				// insertLayerMedata
				HashMap<String, Object> insertQueryMap = dbManager.getInsertLayerMeataData(type, collectionName, cIdx,
						qa20Layer);
				int lmIdx = dao.insertQA20LayerMetadata(insertQueryMap);

				NDAHeader ndaHeader = qa20Layer.getNdaHeader();
				// aspatial_field_def
				List<HashMap<String, Object>> fieldDefs = dbManager.getAspatialFieldDefs(lmIdx, ndaHeader);
				if (fieldDefs != null) {
					for (int j = 0; j < fieldDefs.size(); j++) {
						dao.insertNdaAspatialFieldDefs(fieldDefs.get(j));
					}
				}
				NGIHeader ngiHeader = qa20Layer.getNgiHeader();
				// point_represent
				List<HashMap<String, Object>> ptReps = dbManager.getPtRepresent(lmIdx, ngiHeader.getPoint_represent());
				if (ptReps != null) {
					for (int j = 0; j < ptReps.size(); j++) {
						dao.insertPointRepresent(ptReps.get(j));
					}
				}
				// lineString_represent
				List<HashMap<String, Object>> lnReps = dbManager.getLnRepresent(lmIdx, ngiHeader.getLine_represent());
				if (lnReps != null) {
					for (int j = 0; j < lnReps.size(); j++) {
						dao.insertLineStringRepresent(lnReps.get(j));
					}
				}
				// region_represent
				List<HashMap<String, Object>> rgReps = dbManager.getRgRepresent(lmIdx, ngiHeader.getRegion_represent());
				if (rgReps != null) {
					for (int j = 0; j < rgReps.size(); j++) {
						dao.insertRegionRepresent(rgReps.get(j));
					}
				}
				// text_represent
				List<HashMap<String, Object>> txtReps = dbManager.getTxtRepresent(lmIdx, ngiHeader.getText_represent());
				if (txtReps != null) {
					for (int j = 0; j < txtReps.size(); j++) {
						dao.insertTextRepresent(txtReps.get(j));
					}
				}
				// geoLayerInfo
				layerInfo.putLayerName(layerName);
				String layerTypeStr = qa20Layer.getLayerType();
				String layerType = dbManager.getLayerType(layerTypeStr);
				layerInfo.putLayerType(layerName, layerType);
				List<String> columns = dbManager.getLayerCoulmns(qa20Layer);
				layerInfo.putLayerColumns(layerName, columns);
				// layerInfo.putLayerBoundary(layerName, boundryMap);
			}
		} catch (Exception e) {
			txManager.rollback(status);
			layerInfo.setDbInsertFlag(false);
			return layerInfo;
		}
		if (layerInfo != null) {
			txManager.commit(status);
			layerInfo.setDbInsertFlag(true);
		}
		return layerInfo;
	}

	public void dropQA20LayerCollection(QA20LayerCollection dtCollection, GeoLayerInfo layerInfo) {

		QA20DBQueryManager dbManager = new QA20DBQueryManager();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);
		try {
			String collectionName = dtCollection.getFileName();
			HashMap<String, Object> selectLayerCollectionIdxQuery = dbManager
					.getSelectLayerCollectionIdx(collectionName);
			Integer cIdx = dao.selectQA20LayerCollectionIdx(selectLayerCollectionIdxQuery);
			if (cIdx != null) {
				HashMap<String, Object> metadataIdxQuery = dbManager.getSelectLayerMetaDataIdx(cIdx);
				List<HashMap<String, Object>> metadataIdxMapList = dao.selectQA20LayerMetadataIdx(metadataIdxQuery);
				for (int i = 0; i < metadataIdxMapList.size(); i++) {
					HashMap<String, Object> metadataIdxMap = metadataIdxMapList.get(i);
					Integer mIdx = (Integer) metadataIdxMap.get("lm_idx");

					// get layerTb name
					HashMap<String, Object> layerTbNameQuery = dbManager.getSelectLayerTableNameQuery(mIdx);
					HashMap<String, Object> layerTbNameMap = dao.selectQA20LayerTableName(layerTbNameQuery);

					// layerTb drop
					String layerTbName = (String) layerTbNameMap.get("layer_t_name");
					HashMap<String, Object> dropLayerTbQuery = dbManager.getDropLayer(layerTbName);
					dao.dropLayer(dropLayerTbQuery);

					// ngi_text_represent 삭제
					HashMap<String, Object> deleteTextRepQuery = dbManager.getDeleteTextRepresent(mIdx);
					dao.deleteField(deleteTextRepQuery);
					// ngi_point_represent 삭제
					HashMap<String, Object> deletePointRepQuery = dbManager.getDeletePointRepresent(mIdx);
					dao.deleteField(deletePointRepQuery);
					// ngi_lineString_represent 삭제
					HashMap<String, Object> deleteLineStringRepQuery = dbManager.getDeleteLineStringRepresent(mIdx);
					dao.deleteField(deleteLineStringRepQuery);
					// ngi_polygon_represent 삭제
					HashMap<String, Object> deleteRegionRepQuery = dbManager.getDeleteRegionRepresent(mIdx);
					dao.deleteField(deleteRegionRepQuery);
					// nda_aspatial_field_def 삭제
					HashMap<String, Object> deleteAspatialFieldQuery = dbManager.getDeleteAsptialField(mIdx);
					dao.deleteField(deleteAspatialFieldQuery);
				}
			}
			// layerMetadata 삭제
			HashMap<String, Object> deleteLayerMetaQuery = dbManager.getDeleteLayerMeta(cIdx);
			dao.deleteField(deleteLayerMetaQuery);
			HashMap<String, Object> deleteLayerCollectionQuery = dbManager.getDeleteLayerCollection(cIdx);
			dao.deleteField(deleteLayerCollectionQuery);
		} catch (Exception e) {
			txManager.rollback(status);
		}
		txManager.commit(status);
	}

}
