package com.git.opengds.file.dxf.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.qa10.collection.QA10LayerCollection;
import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa10.layer.QA10LayerList;
import com.git.opengds.file.dxf.dbManager.QA10DBQueryManager;
import com.git.opengds.file.dxf.persistence.QA10LayerCollectionDAO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class QA10DBManagerServiceImpl implements QA10DBManagerService {

	@Inject
	private DataSourceTransactionManager txManager;

	@Inject
	private QA10LayerCollectionDAO dao;

	@Override
	public GeoLayerInfo insertQA10LayerCollection(QA10LayerCollection layerCollection, GeoLayerInfo layerInfo) {

//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		TransactionStatus status = txManager.getTransaction(def);
//
//		try {
//			QA10DBQueryManager dbManager = new QA10DBQueryManager(layerCollection, layerInfo);
//			List<HashMap<String, Object>> qa10Metadatas = dbManager.qa10LayerMetadata();
//
//			for (int i = 0; i < qa10Metadatas.size(); i++) {
//				HashMap<String, Object> metadata = qa10Metadatas.get(i);
//				String layerID = (String) metadata.get("layerID");
//
//				// create layer tb
//				HashMap<String, Object> createQuery = dbManager.qa10LayerTbCreateQuery(layerID);
//				dao.createQA10LayerTb(createQuery);
//
//				// insert layer tb
//				List<HashMap<String, Object>> qa10Layers = dbManager.qa10LayerTbInsertQuery(layerID);
//				for (int j = 0; j < qa10Layers.size(); j++) {
//					dao.insertQA10Layer(qa10Layers.get(j));
//				}
//
//				// geoLayerInfo
//				layerInfo.putLayerName(layerID);
//				String layerType = dbManager.getLayerType(layerID);
//				layerInfo.putLayerType(layerID, layerType);
//				List<String> columns = dbManager.getLayerColumns(layerID);
//				layerInfo.putLayerColumns(layerID, columns);
//			}
//		} catch (Exception e) {
//			txManager.rollback(status);
//			return null;
//		}
//		txManager.commit(status);
//		return layerInfo;
		
		return null;
	}

	public GeoLayerInfo insertQA10LayerCollectiontest(QA10LayerCollection layerCollection, GeoLayerInfo layerInfo) {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);

		try {
			QA10DBQueryManager dbManager = new QA10DBQueryManager();

			String collectionName = layerCollection.getFileName();
			String type = layerInfo.getFileType();

			HashMap<String, Object> insertCollectionQuery = dbManager.getInsertLayerCollection(collectionName);
			// collection insert dao
			int cIdx = 0;

			QA10LayerList createLayerList = layerCollection.getQa10Layers();
			for (int i = 0; i < createLayerList.size(); i++) {
				QA10Layer qa10Layer = createLayerList.get(i);
				String layerId = qa10Layer.getLayerID();

				// create QA10Layer
				HashMap<String, Object> createQuery = dbManager.qa10LayerTbCreateQuery(type, collectionName, qa10Layer);
				// create layer dao

				// insertQA20Layer
				List<HashMap<String, Object>> inertLayerQuerys = dbManager.qa10LayerTbInsertQuery(type, collectionName, qa10Layer);
				for(int j = 0; j < inertLayerQuerys.size(); j++) {
					HashMap<String, Object> insertLayerQuery = inertLayerQuerys.get(j);
				//	dao.insertQA10Layer(insertLayerQuery);
				}
				
				// insertLayerMetadata
				HashMap<String, Object> insertQueryMap = dbManager.getInsertLayerMeataData(type, collectionName, cIdx,
						qa10Layer);
				// insert metadata dao
				
				// insertHeader
				
				// insertBlocks
				
				// insertObjects
				
				// insertTables
			}

			// geoLayerInfo
			// layerInfo.putLayerName(layerID);
			// String layerType = dbManager.getLayerType(layerID);
			// layerInfo.putLayerType(layerID, layerType);
			// List<String> columns = dbManager.getLayerColumns(layerID);
			// layerInfo.putLayerColumns(layerID, columns);

		} catch (Exception e) {
			txManager.rollback(status);
			return null;
		}
		txManager.commit(status);
		return layerInfo;
	}
}
