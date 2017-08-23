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

package com.git.opengds.editor.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.opengis.feature.simple.SimpleFeature;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.gdsbuilder.edit.dxf.EditDXFLayerCollection;
import com.git.gdsbuilder.edit.ngi.EditNGILayerCollection;
import com.git.gdsbuilder.edit.shp.EditSHPLayerCollection;
import com.git.gdsbuilder.type.dxf.feature.DTDXFFeature;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayer;
import com.git.gdsbuilder.type.dxf.structure.DTDXFTables;
import com.git.gdsbuilder.type.ngi.collection.DTNGILayerCollection;
import com.git.gdsbuilder.type.ngi.feature.DTNGIFeature;
import com.git.gdsbuilder.type.ngi.header.NDAField;
import com.git.gdsbuilder.type.ngi.header.NDAHeader;
import com.git.gdsbuilder.type.ngi.header.NGIHeader;
import com.git.gdsbuilder.type.ngi.layer.DTNGILayer;
import com.git.gdsbuilder.type.ngi.layer.DTNGILayerList;
import com.git.gdsbuilder.type.shp.collection.DTSHPLayerCollection;
import com.git.gdsbuilder.type.shp.layer.DTSHPLayer;
import com.git.gdsbuilder.type.shp.layer.DTSHPLayerList;
import com.git.opengds.file.dxf.dbManager.DXFDBQueryManager;
import com.git.opengds.file.dxf.persistence.DXFLayerCollectionDAO;
import com.git.opengds.file.ngi.dbManager.NGIDBQueryManager;
import com.git.opengds.file.ngi.persistence.NGILayerCollectionDAO;
import com.git.opengds.file.shp.dbManager.SHPDBQueryManager;
import com.git.opengds.file.shp.persistence.SHPLayerCollectionDAO;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.user.domain.UserVO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class EditDBManagerServiceImpl implements EditDBManagerService {

	/*
	 * @Inject private DataSourceTransactionManager txManager;
	 */

	@Inject
	private NGILayerCollectionDAO ngiDAO;

	@Inject
	private DXFLayerCollectionDAO dxfDAO;

	@Inject
	private SHPLayerCollectionDAO shpDAO;

	@Inject
	private GeoserverService geoserverService;

	public Integer selectNGILayerCollectionIdx(UserVO userVO, String collectionName) throws RuntimeException {

		NGIDBQueryManager queryManager = new NGIDBQueryManager();
		HashMap<String, Object> queryMap = queryManager.getSelectNGILayerCollectionIdx(collectionName);
		Integer cIdx = ngiDAO.selectNGILayerCollectionIdx(userVO, queryMap);
		if (cIdx == null) {
			return null;
		} else {
			return cIdx;
		}
	}

	@Override
	public Integer createNGILayerCollection(UserVO userVO, String type, EditNGILayerCollection editCollection)
			throws RuntimeException {

		String collectionName = editCollection.getCollectionName();

		DTNGILayerList createLayerList = editCollection.getCreateLayerList();
		DTNGILayerCollection createCollection = new DTNGILayerCollection();
		createCollection.setFileName(collectionName);
		createCollection.setNGILayerList(createLayerList);

		NGIDBQueryManager queryManager = new NGIDBQueryManager();
		HashMap<String, Object> insertCollectionQuery = queryManager.getInsertNGILayerCollectionQuery(collectionName);
		int cIdx = ngiDAO.insertNGILayerCollection(userVO, insertCollectionQuery);

		return cIdx;
	}

	@Override
	public Integer createDXFLayerCollection(UserVO userVO, String type, EditDXFLayerCollection editCollection)
			throws RuntimeException {

		String collectionName = editCollection.getCollectionName();

		DXFDBQueryManager queryManager = new DXFDBQueryManager();
		HashMap<String, Object> insertCollectionQuery = queryManager.getInsertDXFLayerCollection(collectionName);
		int cIdx = dxfDAO.insertDXFLayerCollection(userVO, insertCollectionQuery);

		return cIdx;
	}

	public void deleteDXFLayerCollection(UserVO userVO, int cIdx) throws RuntimeException {

		DXFDBQueryManager queryManager = new DXFDBQueryManager();

		HashMap<String, Object> deleteTableCommonsQuery = queryManager.getDeleteTablesQuery(cIdx);
		dxfDAO.deleteField(userVO, deleteTableCommonsQuery);

		// HashMap<String, Object> deleteValidateProgressQuery =
		// queryManager.getDeleteQA10ProgressQuery(cIdx);
		// progressDAO.deleteQA10Progress(deleteValidateProgressQuery);

		HashMap<String, Object> deleteLayerCollectionQuery = queryManager.getDeleteDXFLayerCollectionQuery(cIdx);
		dxfDAO.deleteField(userVO, deleteLayerCollectionQuery);
	}

	public void deleteNGILayerCollection(UserVO userVO, int cIdx) throws RuntimeException {

		NGIDBQueryManager queryManager = new NGIDBQueryManager();

		// HashMap<String, Object> deleteValidateProgressQuery =
		// queryManager.getDeleteQA10ProgressQuery(cIdx);
		// progressDAO.deleteQA20Progress(deleteValidateProgressQuery);

		HashMap<String, Object> deleteLayerCollectionQuery = queryManager.getDeleteNGILayerCollection(cIdx);
		ngiDAO.deleteField(userVO, deleteLayerCollectionQuery);
	}

	@Override
	public boolean createNGILayer(UserVO userVO, String type, Integer idx, String collectionName, DTNGILayer qa20Layer,
			String src) throws RuntimeException {

		/*
		 * DefaultTransactionDefinition def = new
		 * DefaultTransactionDefinition();
		 * def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED
		 * ); TransactionStatus status = txManager.getTransaction(def);
		 */

		try {
			NGIDBQueryManager queryManager = new NGIDBQueryManager();

			// createQA20Layer
			HashMap<String, Object> createQuery = queryManager.getNGILayerTbCreateQuery(type, collectionName, qa20Layer,
					src);
			ngiDAO.createNGILayerTb(userVO, createQuery);

			// insertLayerMedata
			HashMap<String, Object> insertQueryMap = queryManager.getInsertNGILayerMeataData(type, collectionName, idx,
					qa20Layer);
			int lmIdx = ngiDAO.insertNGILayerMetadata(userVO, insertQueryMap);

			NDAHeader ndaHeader = qa20Layer.getNdaHeader();
			// aspatial_field_def
			List<HashMap<String, Object>> fieldDefs = queryManager.getAspatialFieldDefsInsertQuery(lmIdx, ndaHeader);
			if (fieldDefs != null) {
				for (int j = 0; j < fieldDefs.size(); j++) {
					ngiDAO.insertNdaAspatialFieldDefs(userVO, fieldDefs.get(j));
				}
			}
			NGIHeader ngiHeader = qa20Layer.getNgiHeader();
			// point_represent
			List<HashMap<String, Object>> ptReps = queryManager.getPtRepresentInsertQuery(lmIdx,
					ngiHeader.getPoint_represent());
			if (ptReps != null) {
				for (int j = 0; j < ptReps.size(); j++) {
					ngiDAO.insertPointRepresent(userVO, ptReps.get(j));
				}
			}
			// lineString_represent
			List<HashMap<String, Object>> lnReps = queryManager.getLnRepresentInsertQuery(lmIdx,
					ngiHeader.getLine_represent());
			if (lnReps != null) {
				for (int j = 0; j < lnReps.size(); j++) {
					ngiDAO.insertLineStringRepresent(userVO, lnReps.get(j));
				}
			}
			// region_represent
			List<HashMap<String, Object>> rgReps = queryManager.getRgRepresentInsertQuery(lmIdx,
					ngiHeader.getRegion_represent());
			if (rgReps != null) {
				for (int j = 0; j < rgReps.size(); j++) {
					ngiDAO.insertRegionRepresent(userVO, rgReps.get(j));
				}
			}
			// text_represent
			List<HashMap<String, Object>> txtReps = queryManager.getTxtRepresentInsertQuery(lmIdx,
					ngiHeader.getText_represent());
			if (txtReps != null) {
				for (int j = 0; j < txtReps.size(); j++) {
					ngiDAO.insertTextRepresent(userVO, txtReps.get(j));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return true;
	}

	@Override
	public void insertNGICreateFeature(UserVO userVO, String tableName, DTNGIFeature createFeature, String src)
			throws RuntimeException {

		NGIDBQueryManager queryManager = new NGIDBQueryManager();
		HashMap<String, Object> insertQuertMap = queryManager.getInertNGIFeatureQuery(tableName, createFeature, src);
		ngiDAO.insertNGIFeature(userVO, insertQuertMap);
	}

	@Override
	public void updateNGIModifyFeature(UserVO userVO, String tableName, DTNGIFeature modifyFeature, String src)
			throws RuntimeException {

		NGIDBQueryManager queryManager = new NGIDBQueryManager();

		/*
		 * DefaultTransactionDefinition def = new
		 * DefaultTransactionDefinition();
		 * def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED
		 * ); TransactionStatus status = txManager.getTransaction(def);
		 */

		try {
			// 1. featureID 조회
			String featureID = modifyFeature.getFeatureID();
			HashMap<String, Object> selectIdxqueryMap = queryManager.getSelectNGIFeatureIdxQuery(tableName, featureID);
			HashMap<String, Object> idxMap = ngiDAO.selectNGIFeatureIdx(userVO, selectIdxqueryMap);
			int idx = (Integer) idxMap.get("f_idx");

			// 2. 해당 feature 삭제
			HashMap<String, Object> deleteFeatureMap = queryManager.getDeleteNGIFeatureQuery(tableName, idx);
			ngiDAO.deleteNGIFeature(userVO, deleteFeatureMap);

			// 3. 다시 insert
			HashMap<String, Object> insertFeatureMap = queryManager.getInertNGIFeatureQuery(tableName, modifyFeature,
					src);
			ngiDAO.insertNGIFeature(userVO, insertFeatureMap);
		} catch (Exception e) {
			// txManager.rollback(status);
		}
		// txManager.commit(status);
	}

	@Override
	public boolean modifyNGILayer(UserVO userVO, String type, Integer collectionIdx, String collectionName,
			DTNGILayer qa20Layer, Map<String, Object> geoLayer) throws RuntimeException {

		/*
		 * DefaultTransactionDefinition def = new
		 * DefaultTransactionDefinition();
		 * def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED
		 * ); TransactionStatus status = txManager.getTransaction(def);
		 */

		NGIDBQueryManager queryManager = new NGIDBQueryManager();
		try {
			String orignName = qa20Layer.getOriginLayerName();
			HashMap<String, Object> queryMap = queryManager.getSelectNGILayerMetaDataIdxQuery(collectionIdx, orignName);
			Integer lmIdx = ngiDAO.selectNGILayerMetadataIdx(userVO, queryMap);

			// meta Tb - layerName update
			String currentName = qa20Layer.getLayerName();
			if (!currentName.equals(orignName)) {
				HashMap<String, Object> updateLayerNameQuery = queryManager.getUpdateNGILayerMeataLayerNameQuery(lmIdx,
						currentName);
				ngiDAO.updateNGILayerMetadataLayerName(userVO, updateLayerNameQuery);
			}

			NGIHeader ngiHeader = qa20Layer.getNgiHeader();
			// meta Tb - boundary update
			// String boundary = ngiHeader.getBound();
			// HashMap<String, Object> updateBoundaryQuery =
			// queryManager.getUpdateQA20LayerMeataBoundaryQuery(lmIdx,
			// boundary);
			// qa20DAO.updateQA20LayerMetadataBoundary(userVO,updateBoundaryQuery);

			// ngi_point_rep Tb update

			// ngi_linestring_rep Tb update

			// ngi_region_rep Tb update

			// ngi_text_rep Tb update

			// nda_aspatial Tb update
			NDAHeader ndaHeader = qa20Layer.getNdaHeader();
			List<NDAField> fields = ndaHeader.getAspatial_field_def();
			for (int j = 0; j < fields.size(); j++) {
				// updated
				NDAField modifiedfield = fields.get(j);
				String originFieldName = modifiedfield.getOriginFieldName();

				// origin
				HashMap<String, Object> selectNadFieldsQuery = queryManager.getNdaAspatialFieldFidxQuery(lmIdx,
						originFieldName);
				HashMap<String, Object> fIdxMap = ngiDAO.selectNdaAspatialFieldFidxs(userVO, selectNadFieldsQuery);
				if (fIdxMap != null) {
					// update
					int fIdx = (Integer) fIdxMap.get("f_idx");
					HashMap<String, Object> updateFieldQuery = queryManager.updateNdaAspatialFieldQuery(fIdx,
							modifiedfield);
					ngiDAO.updateNdaAspatialField(userVO, updateFieldQuery);
				} else {
					// insert
					HashMap<String, Object> insertFieldQuery = queryManager.getAspatialFieldDefsInsertQuery(lmIdx,
							modifiedfield);
					ngiDAO.insertNdaAspatialFieldDefs(userVO, insertFieldQuery);
				}
			}
		} catch (Exception e) {
			// txManager.rollback(status);
			return false;
		}
		// update Geoserver
		String originalName = (String) geoLayer.get("orignalName");
		String name = (String) geoLayer.get("name");
		String title = (String) geoLayer.get("title");
		String summary = (String) geoLayer.get("summary");
		boolean attChangeFlag = (Boolean) geoLayer.get("attChangeFlag");
		String tableName = "geo_" + type + "_" + collectionName + "_" + originalName;
		boolean isSuccessed = geoserverService.updateFeatureType(userVO, tableName, name, title, summary, "",
				attChangeFlag);
		if (isSuccessed) {
			// txManager.commit(status);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<HashMap<String, Object>> getNGILayerMetadataIdx(UserVO userVO, Integer collectionIdx)
			throws RuntimeException {

		NGIDBQueryManager queryManager = new NGIDBQueryManager();
		HashMap<String, Object> queryMap = queryManager.getSelectNGILayerMetaDataIdxQuery(collectionIdx);
		List<HashMap<String, Object>> cIdx = ngiDAO.selectNGILayerMetadataIdxs(userVO, queryMap);
		if (cIdx == null) {
			return null;
		} else {
			return cIdx;
		}
	}

	@Override
	public void deleteNGIRemovedFeature(UserVO userVO, String tableName, String featureId) throws RuntimeException {

		NGIDBQueryManager queryManager = new NGIDBQueryManager();

		/*
		 * DefaultTransactionDefinition def = new
		 * DefaultTransactionDefinition();
		 * def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED
		 * ); TransactionStatus status = txManager.getTransaction(def);
		 */
		try {
			HashMap<String, Object> selectIdxqueryMap = queryManager.getSelectNGIFeatureIdxQuery(tableName, featureId);
			HashMap<String, Object> idxMap = ngiDAO.selectNGIFeatureIdx(userVO, selectIdxqueryMap);
			int idx = (Integer) idxMap.get("f_idx");

			HashMap<String, Object> deleteFeatureMap = queryManager.getDeleteNGIFeatureQuery(tableName, idx);
			ngiDAO.deleteNGIFeature(userVO, deleteFeatureMap);
		} catch (Exception e) {
			// txManager.rollback(status);
		}
		// txManager.commit(status);
	}

	@Override
	public boolean dropNGILayer(UserVO userVO, String type, Integer collectionIdx, String collectionName,
			DTNGILayer layer) throws RuntimeException {

		/*
		 * DefaultTransactionDefinition def = new
		 * DefaultTransactionDefinition();
		 * def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED
		 * ); TransactionStatus status = txManager.getTransaction(def);
		 */

		boolean isSuccessed = false;

		String layerName = layer.getLayerName();
		NGIDBQueryManager queryManager = new NGIDBQueryManager();

		try {
			HashMap<String, Object> metadataIdxQuery = queryManager.getSelectNGILayerMetaDataIdxQuery(collectionIdx,
					layerName);
			Integer mIdx = ngiDAO.selectNGILayerMetadataIdx(userVO, metadataIdxQuery);

			// ngi_text_represent 삭제
			HashMap<String, Object> deleteTextRepQuery = queryManager.getDeleteTextRepresentQuery(mIdx);
			ngiDAO.deleteField(userVO, deleteTextRepQuery);
			// ngi_point_represent 삭제
			HashMap<String, Object> deletePointRepQuery = queryManager.getDeletePointRepresentQuery(mIdx);
			ngiDAO.deleteField(userVO, deletePointRepQuery);
			// ngi_lineString_represent 삭제
			HashMap<String, Object> deleteLineStringRepQuery = queryManager.getDeleteLineStringRepresentQuery(mIdx);
			ngiDAO.deleteField(userVO, deleteLineStringRepQuery);
			// ngi_polygon_represent 삭제
			HashMap<String, Object> deleteRegionRepQuery = queryManager.getDeleteRegionRepresentQuery(mIdx);
			ngiDAO.deleteField(userVO, deleteRegionRepQuery);
			// nda_aspatial_field_def 삭제
			HashMap<String, Object> deleteAspatialFieldQuery = queryManager.getDeleteAsptialFieldQuery(mIdx);
			ngiDAO.deleteField(userVO, deleteAspatialFieldQuery);
			// layerMetadata 삭제
			HashMap<String, Object> deleteLayerMetaQuery = queryManager.getDeleteNGILayerMetaQuery(mIdx);
			ngiDAO.deleteField(userVO, deleteLayerMetaQuery);

			HashMap<String, Object> dropQuery = queryManager.getDropNGILayerQuery(type, collectionName, layerName);
			ngiDAO.dropLayer(userVO, dropQuery);

			String layerTableName = "geo" + "_" + type + "_" + collectionName + "_" + layerName;
			String groupName = "gro" + "_" + type + "_" + collectionName;
			isSuccessed = geoserverService.removeGeoserverLayer(userVO, groupName, layerTableName);
			// HashMap<String, Object> deleteLayerCollectionQuery = queryManager
			// .getDeleteQA20LayerCollectionQuery(collectionIdx);
			// qa20DAO.deleteField(deleteLayerCollectionQuery);
			if (isSuccessed) {
				return true;
			} else {
				throw new RuntimeException();
			}
		} catch (RuntimeException e) {
			// txManager.rollback(status);
			throw new RuntimeException();
		}
	}

	@Override
	public void insertDXFCreateFeature(UserVO userVO, String tableName, DTDXFFeature createFeature)
			throws RuntimeException {

		DXFDBQueryManager queryManager = new DXFDBQueryManager();
		HashMap<String, Object> insertQuertMap = queryManager.getInertDXFFeatureQuery(tableName, createFeature);
		dxfDAO.insertDXFFeature(userVO, insertQuertMap);
	}

	@Override
	public void updateDXFModifyFeature(UserVO userVO, String tableName, DTDXFFeature modifyFeature)
			throws RuntimeException {

		DXFDBQueryManager queryManager = new DXFDBQueryManager();

		/*
		 * DefaultTransactionDefinition def = new
		 * DefaultTransactionDefinition();
		 * def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED
		 * ); TransactionStatus status = txManager.getTransaction(def);
		 */
		try {
			// 1. featureID 조회
			String featureID = modifyFeature.getFeatureID();
			HashMap<String, Object> selectIdxqueryMap = queryManager.getSelectDXFFeatureIdxQuery(tableName, featureID);
			HashMap<String, Object> idxMap = dxfDAO.selectDXFFeatureIdx(userVO, selectIdxqueryMap);
			int idx = (Integer) idxMap.get("f_idx");

			// 2. 해당 feature 삭제
			HashMap<String, Object> deleteFeatureMap = queryManager.getDeleteDXFFeatureQuery(tableName, idx);
			dxfDAO.deleteDXFFeature(userVO, deleteFeatureMap);

			// 3. 다시 insert
			HashMap<String, Object> insertFeatureMap = queryManager.getInertDXFFeatureQuery(tableName, modifyFeature);
			dxfDAO.insertDXFFeature(userVO, insertFeatureMap);
		} catch (Exception e) {
			// txManager.rollback(status);
		}
		// txManager.commit(status);
	}

	@Override
	public void deleteDXFRemovedFeature(UserVO userVO, String tableName, String featureId) throws RuntimeException {

		DXFDBQueryManager queryManager = new DXFDBQueryManager();

		/*
		 * DefaultTransactionDefinition def = new
		 * DefaultTransactionDefinition();
		 * def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED
		 * ); TransactionStatus status = txManager.getTransaction(def);
		 */
		try {
			HashMap<String, Object> selectIdxqueryMap = queryManager.getSelectDXFFeatureIdxQuery(tableName, featureId);
			HashMap<String, Object> idxMap = dxfDAO.selectDXFFeatureIdx(userVO, selectIdxqueryMap);
			int idx = (Integer) idxMap.get("f_idx");

			HashMap<String, Object> deleteFeatureMap = queryManager.getDeleteDXFFeatureQuery(tableName, idx);
			dxfDAO.deleteDXFFeature(userVO, deleteFeatureMap);
		} catch (Exception e) {
			// txManager.rollback(status);
		}
		// txManager.commit(status);

	}

	@Override
	public Integer selectDXFLayerCollectionIdx(UserVO userVO, String collectionName) throws RuntimeException {

		DXFDBQueryManager queryManager = new DXFDBQueryManager();
		HashMap<String, Object> queryMap = queryManager.getSelectDXFLayerCollectionIdxQuery(collectionName);
		Integer cIdx = ngiDAO.selectNGILayerCollectionIdx(userVO, queryMap);
		if (cIdx == null) {
			return null;
		} else {
			return cIdx;
		}
	}

	@Override
	public boolean createDXFLayer(UserVO userVO, String type, Integer collectionIdx, String collectionName,
			DTDXFLayer createLayer, String src) throws RuntimeException {

		DXFDBQueryManager queryManager = new DXFDBQueryManager();

		/*
		 * DefaultTransactionDefinition def = new
		 * DefaultTransactionDefinition();
		 * def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED
		 * ); TransactionStatus status = txManager.getTransaction(def);
		 */

		try {
			HashMap<String, Object> createQuery = queryManager.getDXFLayerTbCreateQuery(type, collectionName,
					createLayer, src);
			dxfDAO.createDXFLayerTb(userVO, createQuery);

			// insertQA10Layer
			/*
			 * List<HashMap<String, Object>> inertLayerQuerys =
			 * queryManager.qa10LayerTbInsertQuery(type, collectionName,
			 * createLayer, src); for (int j = 0; j < inertLayerQuerys.size();
			 * j++) { HashMap<String, Object> insertLayerQuery =
			 * inertLayerQuerys.get(j);
			 * qa10DAO.insertQA10Layer(insertLayerQuery); }
			 */

			// insertLayerMetadata
			HashMap<String, Object> insertQueryMap = queryManager.getInsertDXFLayerMeataDataQuery(type, collectionName,
					collectionIdx, createLayer);
			dxfDAO.insertDXFLayerMetadata(userVO, insertQueryMap);

			// tablesLayer
			DTDXFTables tables = new DTDXFTables();
			Map<String, Object> tbLayers = tables.getLayerValues(createLayer);
			HashMap<String, Object> tablesQuery = queryManager.getInsertTablesQuery(collectionIdx, tbLayers);
			int tbIdx = dxfDAO.insertDXFLayerCollectionTableCommon(userVO, tablesQuery);
			if (tables.isLayers()) {
				List<HashMap<String, Object>> layersQuery = queryManager.getInsertTablesLayersQuery(tbIdx, tbLayers);
				for (int i = 0; i < layersQuery.size(); i++) {
					dxfDAO.insertDXFLayerCollectionTableLayers(userVO, layersQuery.get(i));
				}
			}
		} catch (Exception e) {
			// txManager.rollback(status);
			return false;
		}
		// txManager.commit(status);
		return true;
	}

	@Override
	public boolean dropDXFLayer(UserVO userVO, String type, Integer collectionIdx, String collectionName,
			DTDXFLayer layer) throws RuntimeException {

		/*
		 * DefaultTransactionDefinition def = new
		 * DefaultTransactionDefinition();
		 * def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED
		 * ); TransactionStatus status = txManager.getTransaction(def);
		 */

		String layerId = layer.getLayerID();
		String[] typeSplit = layerId.split("_");
		// String id = typeSplit[0] + "_" + typeSplit[1];
		String id = typeSplit[0];

		boolean isSuccessed = false;

		try {
			DXFDBQueryManager dbManager = new DXFDBQueryManager();
			HashMap<String, Object> selectLayerCollectionIdxQuery = dbManager
					.getSelectDXFLayerCollectionIdxQuery(collectionName);
			Integer cIdx = dxfDAO.selectDXFLayerCollectionIdx(userVO, selectLayerCollectionIdxQuery);
			if (cIdx != null) {
				HashMap<String, Object> metadataIdxQuery = dbManager.getSelectQA10LayerMetaDataIdxQuery(cIdx, layerId);
				int mIdx = dxfDAO.selectDXFLayerMetadataIdx(userVO, metadataIdxQuery);

				// get layerTb name
				HashMap<String, Object> layerTbNameQuery = dbManager.getSelectLayerTableNameQuery(mIdx);
				HashMap<String, Object> layerTbNameMap = dxfDAO.selectDXFLayerTableName(userVO, layerTbNameQuery);

				// layerTb drop
				String layerTbName = (String) layerTbNameMap.get("layer_t_name");
				HashMap<String, Object> dropLayerTbQuery = dbManager.getDropDXFLayerQuery(layerTbName);
				int dropLayer = dxfDAO.dropDXFLayer(userVO, dropLayerTbQuery);

				// tables
				HashMap<String, Object> tableIdxQuery = dbManager.getSelectTableCommonIdxQuery(cIdx);
				Integer tcIdx = dxfDAO.selectTableCommonIdx(userVO, tableIdxQuery);
				if (tcIdx != null) {
					// tables - layer
					HashMap<String, Object> deleteTableLayersQuery = dbManager.getDeleteTableLayersQuery(tcIdx, id);
					dxfDAO.deleteField(userVO, deleteTableLayersQuery);

					// blocks - commonIdx
					HashMap<String, Object> blocksIdxQuery = dbManager.getSelectBlockCommonIdxQuery(cIdx, id);
					Integer bcIdx = dxfDAO.selectBlockCommonIdx(userVO, blocksIdxQuery);
					if (bcIdx != null) {
						// blocks - vertex
						HashMap<String, Object> deleteBlocksVertexQuery = dbManager.getDeleteBlockVertexQuery(bcIdx);
						int vertex = dxfDAO.deleteField(userVO, deleteBlocksVertexQuery);
						// blocks - polyline
						HashMap<String, Object> deleteBlocksPolylineQuery = dbManager
								.getDeleteBlockPolylineQuery(bcIdx);
						int polyline = dxfDAO.deleteField(userVO, deleteBlocksPolylineQuery);
						// blocks - text
						HashMap<String, Object> deleteBlocksTextQuery = dbManager.getDeleteBlockTextQuery(bcIdx);
						int text = dxfDAO.deleteField(userVO, deleteBlocksTextQuery);
						// blocks - circle
						HashMap<String, Object> deleteBlocksCircleQuery = dbManager.getDeleteBlockCircleQuery(bcIdx);
						int circle = dxfDAO.deleteField(userVO, deleteBlocksCircleQuery);
						// blocks - arc
						HashMap<String, Object> deleteBlocksArcQuery = dbManager.getDeleteBlockArcQuery(bcIdx);
						int arc = dxfDAO.deleteField(userVO, deleteBlocksArcQuery);
						// blocks - line
						HashMap<String, Object> deleteBlocksLineQuery = dbManager.getDeleteBlockLineQuery(bcIdx);
						int line = dxfDAO.deleteField(userVO, deleteBlocksLineQuery);
						// blocks - lwpolyline
						HashMap<String, Object> deleteBlocksLWPolylineQuery = dbManager
								.getDeleteBlockLWPolylineQuery(bcIdx);
						int lwpolyline = dxfDAO.deleteField(userVO, deleteBlocksLWPolylineQuery);
						// blocks - commons
						HashMap<String, Object> deleteBlocksCommonsQuery = dbManager.getDeleteBlocksQuery(bcIdx);
						int commons = dxfDAO.deleteField(userVO, deleteBlocksCommonsQuery);
					}
				}
				// layerMetadata 삭제
				HashMap<String, Object> deleteLayerMetaQuery = dbManager.getDeleteDXFLayerMetaQuery(mIdx);
				int layerMetadata = dxfDAO.deleteField(userVO, deleteLayerMetaQuery);
			}
			String layerTableName = "geo" + "_" + type + "_" + collectionName + "_" + layerId;
			String groupName = "gro" + "_" + type + "_" + collectionName;
			isSuccessed = geoserverService.removeGeoserverLayer(userVO, groupName, layerTableName);
			// HashMap<String, Object> deleteLayerCollectionQuery = queryManager
			// .getDeleteQA20LayerCollectionQuery(collectionIdx);
			// qa20DAO.deleteField(deleteLayerCollectionQuery);
			if (isSuccessed) {
				return true;
			} else {
				throw new RuntimeException();
			}
		} catch (RuntimeException e) {
			// txManager.rollback(status);
			throw new RuntimeException();
		}
	}

	@Override
	public boolean modifyDXFLayer(UserVO userVO, String type, Integer collectionIdx, String collectionName,
			DTDXFLayer qa10Layer, Map<String, Object> geoLayer) throws RuntimeException {

		/*
		 * DefaultTransactionDefinition def = new
		 * DefaultTransactionDefinition();
		 * def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED
		 * ); TransactionStatus status = txManager.getTransaction(def);
		 */

		DXFDBQueryManager queryManager = new DXFDBQueryManager();

		String orignId = qa10Layer.getOriginLayerID();
		String currentId = qa10Layer.getLayerID();
		try {
			HashMap<String, Object> queryMap = queryManager.getSelectQA10LayerMetaDataIdxQuery(collectionIdx, orignId);
			Integer lmIdx = dxfDAO.selectDXFLayerMetadataIdx(userVO, queryMap);

			// meta Tb - layerName update
			if (!currentId.equals(orignId)) {
				HashMap<String, Object> updateLayerNameQuery = queryManager.getUpdateQA10LayerMeataLayerIDQuery(lmIdx,
						currentId);
				dxfDAO.updateDXFLayerMetadataLayerID(userVO, updateLayerNameQuery);
			}

			// layerCollection_table_common
			HashMap<String, Object> selectTcIdxQuery = queryManager.getSelectTableCommonIdxQuery(collectionIdx);
			int tcIdx = dxfDAO.selectTableCommonIdx(userVO, selectTcIdxQuery);

			// layerCollection_table_layer
			HashMap<String, Object> selectTlIdxQuery = queryManager.getSelectTableLayerIdxQuery(tcIdx, orignId);
			int tlIdx = dxfDAO.selectTableLayerIdx(userVO, selectTlIdxQuery);

			// layerCollection_table_layer - layerId update
			HashMap<String, Object> updateTlIdQuery = queryManager.getUpdateTableLayerIdQuery(tlIdx, currentId);
			dxfDAO.updateTableLayerId(userVO, updateTlIdQuery);
		} catch (Exception e) {
			// txManager.rollback(status);
			return false;
		}
		// txManager.commit(status);
		// update Geoserver
		String originalName = (String) geoLayer.get("orignalName");
		String name = (String) geoLayer.get("name");
		String title = (String) geoLayer.get("title");
		String summary = (String) geoLayer.get("summary");
		boolean attChangeFlag = (Boolean) geoLayer.get("attChangeFlag");
		String tableName = "geo_" + type + "_" + collectionName + "_" + originalName;
		String tableNameCurrent = "geo_" + type + "_" + collectionName + "_" + currentId;
		boolean isSuccessed = geoserverService.updateFeatureType(userVO, tableName, tableNameCurrent, title, summary,
				"", attChangeFlag);
		if (isSuccessed) {
			// txManager.commit(status);
			return true;
		} else {
			return false;
		}
		// return true;
	}

	@Override
	public void deleteDXFLayerCollectionTablesCommon(UserVO userVO, Integer cIdx) throws RuntimeException {

		DXFDBQueryManager queryManager = new DXFDBQueryManager();
		HashMap<String, Object> deleteQuery = queryManager.getDeleteTablesQuery(cIdx);
		dxfDAO.deleteField(userVO, deleteQuery);
	}

	@Override
	public void insertSHPCreateFeature(UserVO userVO, String tableName, SimpleFeature createFeature, String src) {
		SHPDBQueryManager queryManager = new SHPDBQueryManager();
		HashMap<String, Object> insertLayerQuery = queryManager.getInsertSHPFeatureQuery(tableName, createFeature, src);
		shpDAO.insertSHPLayer(userVO, insertLayerQuery);
	}

	@Override
	public void deleteSHPRemovedFeature(UserVO userVO, String tableName, String featureId) {

		SHPDBQueryManager queryManager = new SHPDBQueryManager();
		HashMap<String, Object> selectIdxqueryMap = queryManager.getSelectSHPFeatureIdxQuery(tableName, featureId);
		int fIdx = shpDAO.selectSHPFeatureIdx(userVO, selectIdxqueryMap);
		HashMap<String, Object> deleteFeatureMap = queryManager.getDeleteSHPFeatureQuery(tableName, fIdx);
		shpDAO.deleteSHPFeature(userVO, deleteFeatureMap);
	}

	@Override
	public void updateSHPModifyFeature(UserVO userVO, String tableName, SimpleFeature modifiedFeature, String src) {

		SHPDBQueryManager queryManager = new SHPDBQueryManager();
		// 1. featureID 조회
		String featureID = modifiedFeature.getID();
		HashMap<String, Object> selectIdxqueryMap = queryManager.getSelectSHPFeatureIdxQuery(tableName, featureID);
		int fIdx = shpDAO.selectSHPFeatureIdx(userVO, selectIdxqueryMap);

		// 2. 해당 feature 삭제
		HashMap<String, Object> deleteFeatureMap = queryManager.getDeleteSHPFeatureQuery(tableName, fIdx);
		shpDAO.deleteSHPFeature(userVO, deleteFeatureMap);

		// 3. 다시 insert
		HashMap<String, Object> insertFeatureMap = queryManager.getInsertSHPFeatureQuery(tableName, modifiedFeature,
				src);
		shpDAO.insertSHPLayer(userVO, insertFeatureMap);
	}

	@Override
	public Integer selectSHPLayerCollectionIdx(UserVO userVO, String collectionName) {
		SHPDBQueryManager queryManager = new SHPDBQueryManager();
		HashMap<String, Object> queryMap = queryManager.getSelectSHPLayerCollectionIdx(collectionName);
		Integer cIdx = shpDAO.selectSHPLayerCollectionIdx(userVO, queryMap);
		if (cIdx == null) {
			return null;
		} else {
			return cIdx;
		}
	}

	@Override
	public boolean dropSHPLayer(UserVO userVO, String type, Integer collectionIdx, String collectionName,
			DTSHPLayer layer) {

		try {
			boolean isSuccessed = false;

			SHPDBQueryManager queryManager = new SHPDBQueryManager();
			String layerName = layer.getLayerName();

			HashMap<String, Object> metadataIdxQuery = queryManager.getSelectSHPLayerMetaDataIdxQuery(collectionIdx,
					layerName);
			Integer mIdx = shpDAO.selectSHPLayerMetadataIdx(userVO, metadataIdxQuery);

			// layerMetadata 삭제
			HashMap<String, Object> deleteLayerMetaQuery = queryManager.getDeleteSHPLayerMetaQuery(mIdx);
			shpDAO.deleteSHPLayerMetadata(userVO, deleteLayerMetaQuery);

			HashMap<String, Object> dropQuery = queryManager.getDropSHPLayerQuery(type, collectionName, layerName);
			shpDAO.dropSHPLayer(userVO, dropQuery);

			String layerTableName = "geo" + "_" + type + "_" + collectionName + "_" + layerName;
			String groupName = "gro" + "_" + type + "_" + collectionName;
			isSuccessed = geoserverService.removeGeoserverLayer(userVO, groupName, layerTableName);
			if (isSuccessed) {
				return true;
			} else {
				throw new RuntimeException();
			}
		} catch (RuntimeException e) {
			// txManager.rollback(status);
			throw new RuntimeException();
		}
	}

	@Override
	public void deleteSHPLayerCollection(UserVO userVO, Integer cIdx) {

		SHPDBQueryManager queryManager = new SHPDBQueryManager();
		HashMap<String, Object> deleteLayerCollectionQuery = queryManager.getDeleteSHPLayerCollectionQuery(cIdx);
		shpDAO.deleteSHPLayerCollection(userVO, deleteLayerCollectionQuery);
	}

	@Override
	public boolean createSHPLayer(UserVO userVO, String type, Integer cIdx, String collectionName,
			DTSHPLayer createLayer, String src) {

		try {
			boolean isSuccessed = false;
			SHPDBQueryManager queryManager = new SHPDBQueryManager();

			// createQA20Layer
			HashMap<String, Object> createQuery = queryManager.getSHPLayerTbCreateQuery(type, collectionName,
					createLayer, src);
			shpDAO.createSHPLayerTb(userVO, createQuery);

			// insertLayerMedata
			HashMap<String, Object> insertQueryMap = queryManager.getSHPLayerMetaInertQuery(type, collectionName,
					createLayer, cIdx);
			shpDAO.insertSHPLayerMetadata(userVO, insertQueryMap);
			if (isSuccessed) {
				return true;
			} else {
				throw new RuntimeException();
			}
		} catch (RuntimeException e) {
			// txManager.rollback(status);
			throw new RuntimeException();
		}
	}

	@Override
	public Integer createSHPLayerCollection(UserVO userVO, String isdxf, EditSHPLayerCollection editCollection) {

		String collectionName = editCollection.getCollectionName();

		DTSHPLayerList createLayerList = editCollection.getCreateLayerList();
		DTSHPLayerCollection createCollection = new DTSHPLayerCollection();
		createCollection.setCollectionName(collectionName);
		createCollection.setShpLayerList(createLayerList);

		SHPDBQueryManager queryManager = new SHPDBQueryManager();
		HashMap<String, Object> insertCollectionQuery = queryManager.getInsertSHPLayerCollectionQuery(collectionName);
		int cIdx = shpDAO.insertSHPLayerCollection(userVO, insertCollectionQuery);
		return cIdx;
	}
}
