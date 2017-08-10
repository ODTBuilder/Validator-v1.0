package com.git.opengds.file.dxf.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.gdsbuilder.type.dxf.collection.DTDXFLayerCollection;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayer;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayerList;
import com.git.gdsbuilder.type.dxf.structure.DTDXFBlocks;
import com.git.gdsbuilder.type.dxf.structure.DTDXFTables;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.opengds.file.dxf.dbManager.DXFDBQueryManager;
import com.git.opengds.file.dxf.persistence.DXFLayerCollectionDAO;
import com.git.opengds.user.domain.UserVO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class DXFDBManagerServiceImpl implements DXFDBManagerService {

	/*
	 * @Inject private DataSourceTransactionManager txManager;
	 */

	@Inject
	private DXFLayerCollectionDAO dao;

	/*
	 * public QA10DBManagerServiceImpl(UserVO userVO){ dao = new
	 * QA10LayerCollectionDAOImpl(userVO); }
	 */
	@Override
	// @Transactional
	public GeoLayerInfo insertDXFLayerCollection(UserVO userVO, DTDXFLayerCollection layerCollection,
			GeoLayerInfo layerInfo) throws RuntimeException {
		try {
			DXFDBQueryManager dbManager = new DXFDBQueryManager();

			String collectionName = layerCollection.getCollectionName();
			String type = layerInfo.getFileType();

			// collection
			HashMap<String, Object> insertCollectionQuery = dbManager.getInsertDXFLayerCollection(collectionName);
			int cIdx = dao.insertDXFLayerCollection(userVO, insertCollectionQuery);

			// insertTables
			DTDXFTables tables = layerCollection.getTables();
			Map<String, Object> tbLayers = tables.getLayers();
			HashMap<String, Object> tablesQuery = dbManager.getInsertTablesQuery(cIdx, tbLayers);
			int tbIdx = dao.insertDXFLayerCollectionTableCommon(userVO, tablesQuery);
			if (tables.isLayers()) {
				List<HashMap<String, Object>> layersQuery = dbManager.getInsertTablesLayersQuery(tbIdx, tbLayers);
				for (int i = 0; i < layersQuery.size(); i++) {
					dao.insertDXFLayerCollectionTableLayers(userVO, layersQuery.get(i));
				}
			}
			// insertBlocks
			DTDXFBlocks qa10Blocks = layerCollection.getBlocks();
			List<LinkedHashMap<String, Object>> blocks = qa10Blocks.getBlocks();
			List<HashMap<String, Object>> blocksCommonQuerys = dbManager.getInsertBlocksCommonQuery(cIdx, blocks);
			for (int i = 0; i < blocks.size(); i++) {
				HashMap<String, Object> blocksQuery = blocksCommonQuerys.get(i);
				int bIdx = dao.insertDXFLayerCollectionBlocksCommon(userVO, blocksQuery);

				// insertBlockEntities
				LinkedHashMap<String, Object> block = blocks.get(i);
				List<LinkedHashMap<String, Object>> entities = (List<LinkedHashMap<String, Object>>) block
						.get("entities");
				for (int j = 0; j < entities.size(); j++) {
					LinkedHashMap<String, Object> entity = entities.get(j);
					String entityType = (String) entity.get("0");
					if (entityType.equals("POLYLINE")) {
						HashMap<String, Object> polylineQuery = dbManager.getInsertBlockPolylineQuery(bIdx, entity);
						int dpIdx = dao.insertDXFLayercollectionBlockPolyline(userVO, polylineQuery);
						List<LinkedHashMap<String, Object>> vertexMaps = (List<LinkedHashMap<String, Object>>) entity
								.get("vertexs");
						for (int k = 0; k < vertexMaps.size(); k++) {
							LinkedHashMap<String, Object> vertexMap = vertexMaps.get(k);
							HashMap<String, Object> vertextInsertQuery = dbManager.getInsertBlockVertexQuery(bIdx,
									dpIdx, vertexMap);
							dao.insertDXFLayercollectionBlockVertex(userVO, vertextInsertQuery);
						}
					} else if (entityType.equals("ARC")) {
						HashMap<String, Object> arcQuery = dbManager.getInsertBlockArcQuery(bIdx, entity);
						dao.insertDXFLayercollectionBlockEntity(userVO, arcQuery);
					} else if (entityType.equals("VERTEX")) {
						HashMap<String, Object> vertexQuery = dbManager.getInsertBlockVertexQuery(bIdx, entity);
						dao.insertDXFLayercollectionBlockEntity(userVO, vertexQuery);
					} else if (entityType.equals("CIRCLE")) {
						HashMap<String, Object> circleQuery = dbManager.getInsertBlockCircleQuery(bIdx, entity);
						dao.insertDXFLayercollectionBlockEntity(userVO, circleQuery);
					} else if (entityType.equals("TEXT")) {
						HashMap<String, Object> textQuery = dbManager.getInsertBlockTextQuery(bIdx, entity);
						dao.insertDXFLayercollectionBlockEntity(userVO, textQuery);
					} else if (entityType.equals("LINE")) {
						HashMap<String, Object> textQuery = dbManager.getInsertBlockLineQuery(bIdx, entity);
						dao.insertDXFLayercollectionBlockEntity(userVO, textQuery);
					} else if (entityType.equals("LWPOLYLINE")) {
						HashMap<String, Object> lwpolylineQuery = dbManager.getInsertBlockLWPolylineQuery(bIdx, entity);
						int dlpIdx = dao.insertDXFLayercollectionBlockLWPolyline(userVO, lwpolylineQuery);
						List<LinkedHashMap<String, Object>> vertexMaps = (List<LinkedHashMap<String, Object>>) entity
								.get("vertexs");
						for (int k = 0; k < vertexMaps.size(); k++) {
							LinkedHashMap<String, Object> vertexMap = vertexMaps.get(k);
							HashMap<String, Object> vertextInsertQuery = dbManager.getInsertBlockLWVertexQuery(bIdx,
									dlpIdx, vertexMap);
							dao.insertDXFLayercollectionBlockVertex(userVO, vertextInsertQuery);
						}
						int isClosed = (Integer) entity.get("70");
						if (isClosed == 1) {
							LinkedHashMap<String, Object> vertexMap = vertexMaps.get(0);
							HashMap<String, Object> vertextInsertQuery = dbManager.getInsertBlockLWVertexQuery(bIdx,
									dlpIdx, vertexMap);
							dao.insertDXFLayercollectionBlockVertex(userVO, vertextInsertQuery);
						}
					}
				}
			}

			// qa10Layer
			String src = layerInfo.getOriginSrc();
			Map<String, Boolean> isFeaturesMap = new HashMap<String, Boolean>();
			DTDXFLayerList createLayerList = layerCollection.getQa10Layers();

			for (int i = 0; i < createLayerList.size(); i++) {
				DTDXFLayer qa10Layer = createLayerList.get(i);
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
					HashMap<String, Object> createQuery = dbManager.getDXFLayerTbCreateQuery(type, collectionName,
							qa10Layer, src);
					dao.createDXFLayerTb(userVO, createQuery);
					// insertQA10Layer
					List<HashMap<String, Object>> inertLayerQuerys = dbManager.getDXFLayerTbInsertQuery(type,
							collectionName, qa10Layer, src);
					for (int j = 0; j < inertLayerQuerys.size(); j++) {
						HashMap<String, Object> insertLayerQuery = inertLayerQuerys.get(j);
						dao.insertDXFLayer(userVO, insertLayerQuery);
					}
					// insertLayerMetadata
					HashMap<String, Object> insertQueryMap = dbManager.getInsertDXFLayerMeataDataQuery(type, collectionName,
							cIdx, qa10Layer);
					dao.insertDXFLayerMetadata(userVO, insertQueryMap);
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
	public GeoLayerInfo dropDXFLayerCollection(UserVO userVO, DTDXFLayerCollection collection, GeoLayerInfo layerInfo)
			throws RuntimeException {

		try {
			DXFDBQueryManager dbManager = new DXFDBQueryManager();
			String collectionName = collection.getCollectionName();

			HashMap<String, Object> selectLayerCollectionIdxQuery = dbManager
					.getSelectDXFLayerCollectionIdxQuery(collectionName);
			Integer cIdx = dao.selectDXFLayerCollectionIdx(userVO, selectLayerCollectionIdxQuery);
			if (cIdx != null) {
				HashMap<String, Object> metadataIdxQuery = dbManager.getSelectLayerMetaDataIdxQuery(cIdx);
				List<HashMap<String, Object>> metadataIdxMapList = dao.selectDXFLayerMetadataIdxs(userVO,
						metadataIdxQuery);
				for (int i = 0; i < metadataIdxMapList.size(); i++) {
					HashMap<String, Object> metadataIdxMap = metadataIdxMapList.get(i);
					Integer mIdx = (Integer) metadataIdxMap.get("lm_idx");
					String layerId = (String) metadataIdxMap.get("layer_id");

					// get layerTb name
					HashMap<String, Object> layerTbNameQuery = dbManager.getSelectLayerTableNameQuery(mIdx);
					HashMap<String, Object> layerTbNameMap = dao.selectDXFLayerTableName(userVO, layerTbNameQuery);
					// layerTb drop
					String layerTbName = (String) layerTbNameMap.get("layer_t_name");
					HashMap<String, Object> dropLayerTbQuery = dbManager.getDropDXFLayerQuery(layerTbName);
					dao.dropDXFLayer(userVO, dropLayerTbQuery);
					// tables
					HashMap<String, Object> tableIdxQuery = dbManager.getSelectTableCommonIdxQuery(cIdx);
					Integer tcIdx = dao.selectTableCommonIdx(userVO, tableIdxQuery);
					if (tcIdx != null) {
						// tables - layer
						HashMap<String, Object> deleteTableLayersQuery = dbManager.getDeleteTableLayersQuery(tcIdx, layerId);
						dao.deleteField(userVO, deleteTableLayersQuery);
						// blocks - commonIdx
						HashMap<String, Object> blocksIdxQuery = dbManager.getSelectBlockCommonIdxQuery(cIdx, layerId);
						Integer bcIdx = dao.selectBlockCommonIdx(userVO, blocksIdxQuery);
						if (bcIdx != null) {
							// blocks - vertex
							HashMap<String, Object> deleteBlocksVertexQuery = dbManager.getDeleteBlockVertexQuery(bcIdx);
							dao.deleteField(userVO, deleteBlocksVertexQuery);
							// blocks - polyline
							HashMap<String, Object> deleteBlocksPolylineQuery = dbManager.getDeleteBlockPolylineQuery(bcIdx);
							dao.deleteField(userVO, deleteBlocksPolylineQuery);
							// blocks - text
							HashMap<String, Object> deleteBlocksTextQuery = dbManager.getDeleteBlockTextQuery(bcIdx);
							dao.deleteField(userVO, deleteBlocksTextQuery);
							// blocks - circle
							HashMap<String, Object> deleteBlocksCircleQuery = dbManager.getDeleteBlockCircleQuery(bcIdx);
							dao.deleteField(userVO, deleteBlocksCircleQuery);
							// blocks - arc
							HashMap<String, Object> deleteBlocksArcQuery = dbManager.getDeleteBlockArcQuery(bcIdx);
							dao.deleteField(userVO, deleteBlocksArcQuery);
							// blocks - commons
							HashMap<String, Object> deleteBlocksCommonsQuery = dbManager.getDeleteBlocksQuery(bcIdx);
							dao.deleteField(userVO, deleteBlocksCommonsQuery);
						}
					}
					// layerMetadata 삭제
					HashMap<String, Object> deleteLayerMetaQuery = dbManager.getDeleteDXFLayerMetaQuery(mIdx);
					dao.deleteField(userVO, deleteLayerMetaQuery);
				}
			}
			// collection
			HashMap<String, Object> deleteLayerCollectionQuery = dbManager.getDeleteDXFLayerCollectionQuery(cIdx);
			dao.deleteField(userVO, deleteLayerCollectionQuery);
		} catch (RuntimeException e) {
			throw new RuntimeException();
		}
		layerInfo.setDbInsertFlag(true);
		return layerInfo;
	}

}
