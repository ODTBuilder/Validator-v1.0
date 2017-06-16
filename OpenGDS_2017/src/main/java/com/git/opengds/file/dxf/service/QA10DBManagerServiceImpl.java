package com.git.opengds.file.dxf.service;

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
import com.git.gdsbuilder.type.qa10.collection.QA10LayerCollection;
import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa10.layer.QA10LayerList;
import com.git.gdsbuilder.type.qa10.structure.QA10Header;
import com.git.gdsbuilder.type.qa10.structure.QA10Tables;
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

		// DefaultTransactionDefinition def = new
		// DefaultTransactionDefinition();
		// def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		// TransactionStatus status = txManager.getTransaction(def);
		//
		// try {
		// QA10DBQueryManager dbManager = new
		// QA10DBQueryManager(layerCollection, layerInfo);
		// List<HashMap<String, Object>> qa10Metadatas =
		// dbManager.qa10LayerMetadata();
		//
		// for (int i = 0; i < qa10Metadatas.size(); i++) {
		// HashMap<String, Object> metadata = qa10Metadatas.get(i);
		// String layerID = (String) metadata.get("layerID");
		//
		// // create layer tb
		// HashMap<String, Object> createQuery =
		// dbManager.qa10LayerTbCreateQuery(layerID);
		// dao.createQA10LayerTb(createQuery);
		//
		// // insert layer tb
		// List<HashMap<String, Object>> qa10Layers =
		// dbManager.qa10LayerTbInsertQuery(layerID);
		// for (int j = 0; j < qa10Layers.size(); j++) {
		// dao.insertQA10Layer(qa10Layers.get(j));
		// }
		//
		// // geoLayerInfo
		// layerInfo.putLayerName(layerID);
		// String layerType = dbManager.getLayerType(layerID);
		// layerInfo.putLayerType(layerID, layerType);
		// List<String> columns = dbManager.getLayerColumns(layerID);
		// layerInfo.putLayerColumns(layerID, columns);
		// }
		// } catch (Exception e) {
		// txManager.rollback(status);
		// return null;
		// }
		// txManager.commit(status);
		// return layerInfo;

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

			// collection
			HashMap<String, Object> insertCollectionQuery = dbManager.getInsertLayerCollection(collectionName);
			int cIdx = dao.insertQA10LayerCollection(insertCollectionQuery);

			// insertHeader
			QA10Header header = layerCollection.getHeader();
			HashMap<String, Object> insertHeaderQuery = dbManager.getInsertHeader(cIdx, header);
			dao.insertQA10LayerCollectionHeader(insertHeaderQuery);

			// insertTables
			QA10Tables tables = layerCollection.getTables();
			HashMap<String, Object> tablesQuery = dbManager.getInsertTables(cIdx, tables);
			int tbIdx = dao.insertQA10LayerCollectionTables(tablesQuery);

			// insertTablesEntities
			if (tables.isLineTypes()) {
				Map<String, Object> lineTypes = tables.getLineTypes();
				List<HashMap<String, Object>> lineTypesQuery = dbManager.getInsertTablesLineTypes(tbIdx, lineTypes);
				for (int i = 0; i < lineTypesQuery.size(); i++) {
					dao.insertQA10LayerCollectionLineTypes(lineTypesQuery.get(i));
				}
			}
			if (tables.isLayers()) {
				Map<String, Object> layers = tables.getLayers();
				List<HashMap<String, Object>> layersQuery = dbManager.getInsertTablesLayers(tbIdx, layers);
				for (int i = 0; i < layersQuery.size(); i++) {
					dao.insertQA10LayerCollectionLayers(layersQuery.get(i));
				}
			}
			if (tables.isStyles()) {
				Map<String, Object> styles = tables.getStyles();
				List<HashMap<String, Object>> stylesQuery = dbManager.getInsertTablesStyes(tbIdx, styles);
				for (int i = 0; i < stylesQuery.size(); i++) {
					dao.insertQA10LayerCollecionStyles(stylesQuery.get(i));
				}
			}

			// qa10Layer
			QA10LayerList createLayerList = layerCollection.getQa10Layers();
			for (int i = 0; i < createLayerList.size(); i++) {
				QA10Layer qa10Layer = createLayerList.get(i);
				String layerId = qa10Layer.getLayerID();

				// create QA10Layer
				HashMap<String, Object> createQuery = dbManager.qa10LayerTbCreateQuery(type, collectionName, qa10Layer);
				dao.createQA10LayerTb(createQuery);

				// insertQA20Layer
				List<HashMap<String, Object>> inertLayerQuerys = dbManager.qa10LayerTbInsertQuery(type, collectionName,
						qa10Layer);
				for (int j = 0; j < inertLayerQuerys.size(); j++) {
					HashMap<String, Object> insertLayerQuery = inertLayerQuerys.get(j);
					dao.insertQA10Layer(insertLayerQuery);
				}

				// insertLayerMetadata
				HashMap<String, Object> insertQueryMap = dbManager.getInsertLayerMeataData(type, collectionName, cIdx,
						qa10Layer);
				dao.insertQA10LayerMetadata(insertQueryMap);

				// geoLayerInfo
				layerInfo.putLayerName(layerId);
				String layerType = qa10Layer.getLayerType();
				layerInfo.putLayerType(layerId, layerType);
				List<String> columns = dbManager.getLayerColumns();
				layerInfo.putLayerColumns(layerId, columns);
			}

		} catch (Exception e) {
			txManager.rollback(status);
			return null;
		}
		txManager.commit(status);
		return layerInfo;
	}
}
