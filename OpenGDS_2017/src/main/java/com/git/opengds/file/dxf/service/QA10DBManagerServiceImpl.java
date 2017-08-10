package com.git.opengds.file.dxf.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.qa10.collection.QA10LayerCollection;
import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa10.layer.QA10LayerList;
import com.git.gdsbuilder.type.qa10.structure.QA10Blocks;
import com.git.gdsbuilder.type.qa10.structure.QA10Tables;
import com.git.opengds.file.dxf.dbManager.QA10DBQueryManager;
import com.git.opengds.file.dxf.persistence.QA10LayerCollectionDAO;
import com.git.opengds.user.domain.UserVO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class QA10DBManagerServiceImpl implements QA10DBManagerService {

	/*
	 * @Inject private DataSourceTransactionManager txManager;
	 */

	@Inject
	private QA10LayerCollectionDAO dao;

	/*
	 * public QA10DBManagerServiceImpl(UserVO userVO){ dao = new
	 * QA10LayerCollectionDAOImpl(userVO); }
	 */
	@Override
	// @Transactional
	public GeoLayerInfo insertQA10LayerCollection(UserVO userVO, QA10LayerCollection layerCollection,
			GeoLayerInfo layerInfo) throws RuntimeException {
		try {
			QA10DBQueryManager dbManager = new QA10DBQueryManager();

			String collectionName = layerCollection.getCollectionName();
			String type = layerInfo.getFileType();

			// collection
			HashMap<String, Object> insertCollectionQuery = dbManager.getInsertLayerCollection(collectionName);
			int cIdx = dao.insertQA10LayerCollection(userVO, insertCollectionQuery);

			// insertTables
			QA10Tables tables = layerCollection.getTables();
			Map<String, Object> tbLayers = tables.getLayers();
			HashMap<String, Object> tablesQuery = dbManager.getInsertTables(cIdx, tbLayers);
			int tbIdx = dao.insertQA10LayerCollectionTableCommon(userVO, tablesQuery);
			if (tables.isLayers()) {
				List<HashMap<String, Object>> layersQuery = dbManager.getInsertTablesLayers(tbIdx, tbLayers);
				for (int i = 0; i < layersQuery.size(); i++) {
					dao.insertQA10LayerCollectionTableLayers(userVO, layersQuery.get(i));
				}
			}
			// insertBlocks
			QA10Blocks qa10Blocks = layerCollection.getBlocks();
			List<LinkedHashMap<String, Object>> blocks = qa10Blocks.getBlocks();
			List<HashMap<String, Object>> blocksCommonQuerys = dbManager.getInsertBlocksCommon(cIdx, blocks);
			for (int i = 0; i < blocks.size(); i++) {
				HashMap<String, Object> blocksQuery = blocksCommonQuerys.get(i);
				int bIdx = dao.insertQA10LayerCollectionBlocksCommon(userVO, blocksQuery);

				// insertBlockEntities
				LinkedHashMap<String, Object> block = blocks.get(i);
				List<LinkedHashMap<String, Object>> entities = (List<LinkedHashMap<String, Object>>) block
						.get("entities");
				for (int j = 0; j < entities.size(); j++) {
					LinkedHashMap<String, Object> entity = entities.get(j);
					String entityType = (String) entity.get("0");
					if (entityType.equals("POLYLINE")) {
						HashMap<String, Object> polylineQuery = dbManager.getInsertBlockPolylineQuery(bIdx, entity);
						int dpIdx = dao.insertQA10LayercollectionBlockPolyline(userVO, polylineQuery);
						List<LinkedHashMap<String, Object>> vertexMaps = (List<LinkedHashMap<String, Object>>) entity
								.get("vertexs");
						for (int k = 0; k < vertexMaps.size(); k++) {
							LinkedHashMap<String, Object> vertexMap = vertexMaps.get(k);
							HashMap<String, Object> vertextInsertQuery = dbManager.getInsertBlockVertexQuery(bIdx,
									dpIdx, vertexMap);
							dao.insertQA10LayercollectionBlockVertex(userVO, vertextInsertQuery);
						}
					} else if (entityType.equals("ARC")) {
						HashMap<String, Object> arcQuery = dbManager.getInsertBlockArcQuery(bIdx, entity);
						dao.insertQA10LayercollectionBlockEntity(userVO, arcQuery);
					} else if (entityType.equals("VERTEX")) {
						HashMap<String, Object> vertexQuery = dbManager.getInsertBlockVertexQuery(bIdx, entity);
						dao.insertQA10LayercollectionBlockEntity(userVO, vertexQuery);
					} else if (entityType.equals("CIRCLE")) {
						HashMap<String, Object> circleQuery = dbManager.getInsertBlockCircleQuery(bIdx, entity);
						dao.insertQA10LayercollectionBlockEntity(userVO, circleQuery);
					} else if (entityType.equals("TEXT")) {
						HashMap<String, Object> textQuery = dbManager.getInsertBlockTextQuery(bIdx, entity);
						dao.insertQA10LayercollectionBlockEntity(userVO, textQuery);
					} else if (entityType.equals("LINE")) {
						HashMap<String, Object> textQuery = dbManager.getInsertBlockLineQuery(bIdx, entity);
						dao.insertQA10LayercollectionBlockEntity(userVO, textQuery);
					} else if (entityType.equals("LWPOLYLINE")) {
						HashMap<String, Object> lwpolylineQuery = dbManager.getInsertBlockLWPolylineQuery(bIdx, entity);
						int dlpIdx = dao.insertQA10LayercollectionBlockLWPolyline(userVO, lwpolylineQuery);
						List<LinkedHashMap<String, Object>> vertexMaps = (List<LinkedHashMap<String, Object>>) entity
								.get("vertexs");
						for (int k = 0; k < vertexMaps.size(); k++) {
							LinkedHashMap<String, Object> vertexMap = vertexMaps.get(k);
							HashMap<String, Object> vertextInsertQuery = dbManager.getInsertBlockLWVertexQuery(bIdx,
									dlpIdx, vertexMap);
							dao.insertQA10LayercollectionBlockVertex(userVO, vertextInsertQuery);
						}
						int isClosed = (Integer) entity.get("70");
						if (isClosed == 1) {
							LinkedHashMap<String, Object> vertexMap = vertexMaps.get(0);
							HashMap<String, Object> vertextInsertQuery = dbManager.getInsertBlockLWVertexQuery(bIdx,
									dlpIdx, vertexMap);
							dao.insertQA10LayercollectionBlockVertex(userVO, vertextInsertQuery);
						}
					}
				}
			}

			// qa10Layer
			String src = layerInfo.getOriginSrc();
			Map<String, Boolean> isFeaturesMap = new HashMap<String, Boolean>();
			QA10LayerList createLayerList = layerCollection.getQa10Layers();

			for (int i = 0; i < createLayerList.size(); i++) {
				QA10Layer qa10Layer = createLayerList.get(i);
				String layerId = qa10Layer.getLayerID();
				
				if(layerId.equals("A0023119_LWPOLYLINE")) {
					System.out.println("");
				}
				

				// isFeature
				if (qa10Layer.getQa10FeatureList().size() == 0) {
					isFeaturesMap.put(layerId, false);
					continue;
				} else {
					isFeaturesMap.put(layerId, true);
					// create QA10Layer
					HashMap<String, Object> createQuery = dbManager.qa10LayerTbCreateQuery(type, collectionName,
							qa10Layer, src);
					dao.createQA10LayerTb(userVO, createQuery);
					// insertQA10Layer
					List<HashMap<String, Object>> inertLayerQuerys = dbManager.qa10LayerTbInsertQuery(type,
							collectionName, qa10Layer, src);
					for (int j = 0; j < inertLayerQuerys.size(); j++) {
						HashMap<String, Object> insertLayerQuery = inertLayerQuerys.get(j);
						dao.insertQA10Layer(userVO, insertLayerQuery);
					}
					// insertLayerMetadata
					HashMap<String, Object> insertQueryMap = dbManager.getInsertLayerMeataData(type, collectionName,
							cIdx, qa10Layer);
					dao.insertQA10LayerMetadata(userVO, insertQueryMap);
					// geoLayerInfo
					layerInfo.putLayerName(layerId);
					String layerType = qa10Layer.getLayerType();
					layerInfo.putLayerType(layerId, layerType);
					List<String> columns = dbManager.getLayerColumns();
					layerInfo.putLayerColumns(layerId, columns);
				}
			}
			layerInfo.setIsFeatureMap(isFeaturesMap);
			layerInfo.setDbInsertFlag(true);
		} catch (RuntimeException e) {
			throw new RuntimeException();
		}
		return layerInfo;
	}

	@Override
	// @Transactional(rollbackFor=RuntimeException.class)
	public GeoLayerInfo dropQA10LayerCollection(UserVO userVO, QA10LayerCollection collection, GeoLayerInfo layerInfo)
			throws RuntimeException {

		try {
			QA10DBQueryManager dbManager = new QA10DBQueryManager();
			String collectionName = collection.getCollectionName();

			HashMap<String, Object> selectLayerCollectionIdxQuery = dbManager
					.getSelectLayerCollectionIdx(collectionName);
			Integer cIdx = dao.selectQA10LayerCollectionIdx(userVO, selectLayerCollectionIdxQuery);
			if (cIdx != null) {
				HashMap<String, Object> metadataIdxQuery = dbManager.getSelectLayerMetaDataIdx(cIdx);
				List<HashMap<String, Object>> metadataIdxMapList = dao.selectQA10LayerMetadataIdxs(userVO,
						metadataIdxQuery);
				for (int i = 0; i < metadataIdxMapList.size(); i++) {
					HashMap<String, Object> metadataIdxMap = metadataIdxMapList.get(i);
					Integer mIdx = (Integer) metadataIdxMap.get("lm_idx");
					String layerId = (String) metadataIdxMap.get("layer_id");

					// get layerTb name
					HashMap<String, Object> layerTbNameQuery = dbManager.getSelectLayerTableNameQuery(mIdx);
					HashMap<String, Object> layerTbNameMap = dao.selectQA10LayerTableName(userVO, layerTbNameQuery);
					// layerTb drop
					String layerTbName = (String) layerTbNameMap.get("layer_t_name");
					HashMap<String, Object> dropLayerTbQuery = dbManager.getDropLayer(layerTbName);
					dao.dropLayer(userVO, dropLayerTbQuery);
					// tables
					HashMap<String, Object> tableIdxQuery = dbManager.getSelectTableCommonIdx(cIdx);
					Integer tcIdx = dao.selectTableCommonIdx(userVO, tableIdxQuery);
					if (tcIdx != null) {
						// tables - layer
						HashMap<String, Object> deleteTableLayersQuery = dbManager.getDeleteTableLayers(tcIdx, layerId);
						dao.deleteField(userVO, deleteTableLayersQuery);
						// blocks - commonIdx
						HashMap<String, Object> blocksIdxQuery = dbManager.getSelectBlockCommonIdx(cIdx, layerId);
						Integer bcIdx = dao.selectBlockCommonIdx(userVO, blocksIdxQuery);
						if (bcIdx != null) {
							// blocks - vertex
							HashMap<String, Object> deleteBlocksVertexQuery = dbManager.getDeleteBlockVertex(bcIdx);
							dao.deleteField(userVO, deleteBlocksVertexQuery);
							// blocks - polyline
							HashMap<String, Object> deleteBlocksPolylineQuery = dbManager.getDeleteBlockPolyline(bcIdx);
							dao.deleteField(userVO, deleteBlocksPolylineQuery);
							// blocks - text
							HashMap<String, Object> deleteBlocksTextQuery = dbManager.getDeleteBlockText(bcIdx);
							dao.deleteField(userVO, deleteBlocksTextQuery);
							// blocks - circle
							HashMap<String, Object> deleteBlocksCircleQuery = dbManager.getDeleteBlockCircle(bcIdx);
							dao.deleteField(userVO, deleteBlocksCircleQuery);
							// blocks - arc
							HashMap<String, Object> deleteBlocksArcQuery = dbManager.getDeleteBlockArc(bcIdx);
							dao.deleteField(userVO, deleteBlocksArcQuery);
							// blocks - commons
							HashMap<String, Object> deleteBlocksCommonsQuery = dbManager.getDeleteBlocks(bcIdx);
							dao.deleteField(userVO, deleteBlocksCommonsQuery);
						}
					}
					// layerMetadata 삭제
					HashMap<String, Object> deleteLayerMetaQuery = dbManager.getDeleteLayerMeta(mIdx);
					dao.deleteField(userVO, deleteLayerMetaQuery);
				}
			}
			// collection
			HashMap<String, Object> deleteLayerCollectionQuery = dbManager.getDeleteLayerCollection(cIdx);
			dao.deleteField(userVO, deleteLayerCollectionQuery);
		} catch (RuntimeException e) {
			throw new RuntimeException();
		}
		layerInfo.setDbInsertFlag(true);
		return layerInfo;
	}

}
