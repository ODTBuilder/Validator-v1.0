package com.git.opengds.file.dxf.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.git.gdsbuilder.type.qa10.structure.QA10Blocks;
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


	public GeoLayerInfo insertQA10LayerCollection(QA10LayerCollection layerCollection, GeoLayerInfo layerInfo) {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);

		try {
			QA10DBQueryManager dbManager = new QA10DBQueryManager();

			String collectionName = layerCollection.getCollectionName();
			String type = layerInfo.getFileType();

			// collection
			HashMap<String, Object> insertCollectionQuery = dbManager.getInsertLayerCollection(collectionName);
			int cIdx = dao.insertQA10LayerCollection(insertCollectionQuery);

			// insertTables
			QA10Tables tables = layerCollection.getTables();
			Map<String, Object> tbLayers = tables.getLayers();
			HashMap<String, Object> tablesQuery = dbManager.getInsertTables(cIdx, tbLayers);
			int tbIdx = dao.insertQA10LayerCollectionTableCommon(tablesQuery);
			if (tables.isLayers()) {
				List<HashMap<String, Object>> layersQuery = dbManager.getInsertTablesLayers(tbIdx, tbLayers);
				for (int i = 0; i < layersQuery.size(); i++) {
					dao.insertQA10LayerCollectionTableLayers(layersQuery.get(i));
				}
			}

			// insertBlocks
			QA10Blocks qa10Blocks = layerCollection.getBlocks();
			List<LinkedHashMap<String, Object>> blocks = qa10Blocks.getBlocks();
			List<HashMap<String, Object>> blocksQuerys = dbManager.getInsertBlocks(cIdx, blocks);
			for(int i = 0; i < blocks.size(); i++) {
				HashMap<String, Object> blocksQuery = blocksQuerys.get(i);
				int bIdx = dao.insertQA10LayerCollectionBlocks(blocksQuery);
				
				// insertBlockEntities
				LinkedHashMap<String, Object> block = blocks.get(i);
				List<HashMap<String, Object>> entitiesQuerys = dbManager.getInsertBlockEntityQuery(bIdx, block);
				for(int j = 0; j < entitiesQuerys.size(); j++) {
					HashMap<String, Object> entitiesQuery = entitiesQuerys.get(j);
					dao.insertQA10LayercollectionBlockEntity(entitiesQuery);
				}
			}
			
			// qa10Layer
			String src = layerInfo.getOriginSrc();
			QA10LayerList createLayerList = layerCollection.getQa10Layers();
			for (int i = 0; i < createLayerList.size(); i++) {
				QA10Layer qa10Layer = createLayerList.get(i);
				String layerId = qa10Layer.getLayerID();

				// create QA10Layer
				HashMap<String, Object> createQuery = dbManager.qa10LayerTbCreateQuery(type, collectionName, qa10Layer, src);
				dao.createQA10LayerTb(createQuery);

				// insertQA20Layer
				List<HashMap<String, Object>> inertLayerQuerys = dbManager.qa10LayerTbInsertQuery(type, collectionName,
						qa10Layer, src);
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
