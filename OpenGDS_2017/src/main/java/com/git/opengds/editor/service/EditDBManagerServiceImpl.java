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

import org.postgresql.util.PSQLException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.git.gdsbuilder.edit.qa10.EditQA10Collection;
import com.git.gdsbuilder.edit.qa20.EditQA20Collection;
import com.git.gdsbuilder.type.qa10.feature.QA10Feature;
import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa10.structure.QA10Tables;
import com.git.gdsbuilder.type.qa20.collection.QA20LayerCollection;
import com.git.gdsbuilder.type.qa20.feature.QA20Feature;
import com.git.gdsbuilder.type.qa20.header.NDAField;
import com.git.gdsbuilder.type.qa20.header.NDAHeader;
import com.git.gdsbuilder.type.qa20.header.NGIHeader;
import com.git.gdsbuilder.type.qa20.layer.QA20Layer;
import com.git.gdsbuilder.type.qa20.layer.QA20LayerList;
import com.git.opengds.file.dxf.dbManager.QA10DBQueryManager;
import com.git.opengds.file.dxf.persistence.QA10LayerCollectionDAO;
import com.git.opengds.file.dxf.service.QA10DBManagerService;
import com.git.opengds.file.ngi.dbManager.QA20DBQueryManager;
import com.git.opengds.file.ngi.persistence.QA20LayerCollectionDAO;
import com.git.opengds.file.ngi.service.QA20DBManagerService;
import com.git.opengds.geoserver.service.GeoserverService;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class EditDBManagerServiceImpl implements EditDBManagerService {

	@Inject
	private DataSourceTransactionManager txManager;

	@Inject
	private QA20LayerCollectionDAO qa20DAO;

	@Inject
	private QA10LayerCollectionDAO qa10DAO;

	@Inject
	private QA20DBManagerService qa20DBManager;

	@Inject
	private QA10DBManagerService qa10DBManager;

	@Inject
	private GeoserverService geoserverService;

	public Integer checkQA20LayerCollectionName(String collectionName) {

		QA20DBQueryManager queryManager = new QA20DBQueryManager();
		HashMap<String, Object> queryMap = queryManager.getSelectQA20LayerCollectionIdx(collectionName);
		Integer cIdx = qa20DAO.selectQA20LayerCollectionIdx(queryMap);
		if (cIdx == null) {
			return null;
		} else {
			return cIdx;
		}
	}

	@Override
	public Integer createQA20LayerCollection(String type, EditQA20Collection editCollection) throws Exception {

		String collectionName = editCollection.getCollectionName();

		QA20LayerList createLayerList = editCollection.getCreateLayerList();
		QA20LayerCollection createCollection = new QA20LayerCollection();
		createCollection.setFileName(collectionName);
		createCollection.setQa20LayerList(createLayerList);

		QA20DBQueryManager queryManager = new QA20DBQueryManager();
		HashMap<String, Object> insertCollectionQuery = queryManager.getInsertQA20LayerCollectionQuery(collectionName);
		int cIdx = qa20DAO.insertQA20LayerCollection(insertCollectionQuery);

		return cIdx;
	}

	@Override
	public Integer createQA10LayerCollection(String type, EditQA10Collection editCollection) throws Exception {

		String collectionName = editCollection.getCollectionName();

		QA10DBQueryManager queryManager = new QA10DBQueryManager();
		HashMap<String, Object> insertCollectionQuery = queryManager.getInsertLayerCollection(collectionName);
		int cIdx = qa10DAO.insertQA10LayerCollection(insertCollectionQuery);

		return cIdx;
	}

	@Override
	public boolean createQA20Layer(String type, Integer idx, String collectionName, QA20Layer qa20Layer, String src)
			throws PSQLException {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);

		try {
			QA20DBQueryManager queryManager = new QA20DBQueryManager();

			// createQA20Layer
			HashMap<String, Object> createQuery = queryManager.getQA20LayerTbCreateQuery(type, collectionName,
					qa20Layer, src);
			qa20DAO.createQA20LayerTb(createQuery);

			// insertLayerMedata
			HashMap<String, Object> insertQueryMap = queryManager.getInsertQA20LayerMeataData(type, collectionName, idx,
					qa20Layer);
			int lmIdx = qa20DAO.insertQA20LayerMetadata(insertQueryMap);

			NDAHeader ndaHeader = qa20Layer.getNdaHeader();
			// aspatial_field_def
			List<HashMap<String, Object>> fieldDefs = queryManager.getAspatialFieldDefsInsertQuery(lmIdx, ndaHeader);
			if (fieldDefs != null) {
				for (int j = 0; j < fieldDefs.size(); j++) {
					qa20DAO.insertNdaAspatialFieldDefs(fieldDefs.get(j));
				}
			}
			NGIHeader ngiHeader = qa20Layer.getNgiHeader();
			// point_represent
			List<HashMap<String, Object>> ptReps = queryManager.getPtRepresentInsertQuery(lmIdx,
					ngiHeader.getPoint_represent());
			if (ptReps != null) {
				for (int j = 0; j < ptReps.size(); j++) {
					qa20DAO.insertPointRepresent(ptReps.get(j));
				}
			}
			// lineString_represent
			List<HashMap<String, Object>> lnReps = queryManager.getLnRepresentInsertQuery(lmIdx,
					ngiHeader.getLine_represent());
			if (lnReps != null) {
				for (int j = 0; j < lnReps.size(); j++) {
					qa20DAO.insertLineStringRepresent(lnReps.get(j));
				}
			}
			// region_represent
			List<HashMap<String, Object>> rgReps = queryManager.getRgRepresentInsertQuery(lmIdx,
					ngiHeader.getRegion_represent());
			if (rgReps != null) {
				for (int j = 0; j < rgReps.size(); j++) {
					qa20DAO.insertRegionRepresent(rgReps.get(j));
				}
			}
			// text_represent
			List<HashMap<String, Object>> txtReps = queryManager.getTxtRepresentInsertQuery(lmIdx,
					ngiHeader.getText_represent());
			if (txtReps != null) {
				for (int j = 0; j < txtReps.size(); j++) {
					qa20DAO.insertTextRepresent(txtReps.get(j));
				}
			}
		} catch (Exception e) {
			txManager.rollback(status);
			return false;
		}
		txManager.commit(status);
		return true;
	}

	@Override
	public void insertQA20CreateFeature(String tableName, QA20Feature createFeature, String src) {

		QA20DBQueryManager queryManager = new QA20DBQueryManager();
		HashMap<String, Object> insertQuertMap = queryManager.getInertQA20FeatureQuery(tableName, createFeature, src);
		qa20DAO.insertQA20Feature(insertQuertMap);
	}

	@Override
	public void updateQA20ModifyFeature(String tableName, QA20Feature modifyFeature, String src) {

		QA20DBQueryManager queryManager = new QA20DBQueryManager();

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);

		try {
			// 1. featureID 조회
			String featureID = modifyFeature.getFeatureID();
			HashMap<String, Object> selectIdxqueryMap = queryManager.getSelectQA20FeatureIdxQuery(tableName, featureID);
			HashMap<String, Object> idxMap = qa20DAO.selectQA20FeatureIdx(selectIdxqueryMap);
			int idx = (Integer) idxMap.get("f_idx");

			// 2. 해당 feature 삭제
			HashMap<String, Object> deleteFeatureMap = queryManager.getDeleteQA20FeatureQuery(tableName, idx);
			qa20DAO.deleteQA20Feature(deleteFeatureMap);

			// 3. 다시 insert
			HashMap<String, Object> insertFeatureMap = queryManager.getInertQA20FeatureQuery(tableName, modifyFeature,
					src);
			qa20DAO.insertQA20Feature(insertFeatureMap);
		} catch (Exception e) {
			txManager.rollback(status);
		}
		txManager.commit(status);
	}

	@Override
	public boolean modifyQA20Layer(String type, Integer collectionIdx, String collectionName, QA20Layer qa20Layer,
			Map<String, Object> geoLayer) throws PSQLException {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);

		QA20DBQueryManager queryManager = new QA20DBQueryManager();
		try {
			String orignName = qa20Layer.getOriginLayerName();
			HashMap<String, Object> queryMap = queryManager.getSelectQA20LayerMetaDataIdxQuery(collectionIdx,
					orignName);
			Integer lmIdx = qa20DAO.selectQA20LayerMetadataIdx(queryMap);

			// meta Tb - layerName update
			String currentName = qa20Layer.getLayerName();
			if (!currentName.equals(orignName)) {
				HashMap<String, Object> updateLayerNameQuery = queryManager.getUpdateQA20LayerMeataLayerNameQuery(lmIdx,
						currentName);
				qa20DAO.updateQA20LayerMetadataLayerName(updateLayerNameQuery);
			}

			NGIHeader ngiHeader = qa20Layer.getNgiHeader();
			// meta Tb - boundary update
			String boundary = ngiHeader.getBound();
			HashMap<String, Object> updateBoundaryQuery = queryManager.getUpdateQA20LayerMeataBoundaryQuery(lmIdx,
					boundary);
			qa20DAO.updateQA20LayerMetadataBoundary(updateBoundaryQuery);

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
				HashMap<String, Object> fIdxMap = qa20DAO.selectNdaAspatialFieldFidxs(selectNadFieldsQuery);
				if (fIdxMap != null) {
					// update
					int fIdx = (Integer) fIdxMap.get("f_idx");
					HashMap<String, Object> updateFieldQuery = queryManager.updateNdaAspatialFieldQuery(fIdx,
							modifiedfield);
					qa20DAO.updateNdaAspatialField(updateFieldQuery);
				} else {
					// insert
					HashMap<String, Object> insertFieldQuery = queryManager.getAspatialFieldDefsInsertQuery(lmIdx,
							modifiedfield);
					qa20DAO.insertNdaAspatialFieldDefs(insertFieldQuery);
				}
			}
		} catch (Exception e) {
			txManager.rollback(status);
			return false;
		}
		// update Geoserver
		String originalName = (String) geoLayer.get("orignalName");
		String name = (String) geoLayer.get("name");
		String title = (String) geoLayer.get("title");
		String summary = (String) geoLayer.get("summary");
		boolean attChangeFlag = (Boolean) geoLayer.get("attChangeFlag");
		String tableName = "geo_" + type + "_" + collectionName + "_" + originalName;
		boolean isSuccessed = geoserverService.updateFeatureType(tableName, name, title, summary, "", attChangeFlag);
		if (isSuccessed) {
			txManager.commit(status);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<HashMap<String, Object>> getQA20LayerMetadataIdx(Integer collectionIdx) {

		QA20DBQueryManager queryManager = new QA20DBQueryManager();
		HashMap<String, Object> queryMap = queryManager.getSelectQA20LayerMetaDataIdxQuery(collectionIdx);
		List<HashMap<String, Object>> cIdx = qa20DAO.selectQA20LayerMetadataIdxs(queryMap);
		if (cIdx == null) {
			return null;
		} else {
			return cIdx;
		}
	}

	@Override
	public void deleteQA20RemovedFeature(String tableName, String featureId) {

		QA20DBQueryManager queryManager = new QA20DBQueryManager();

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);
		try {
			HashMap<String, Object> selectIdxqueryMap = queryManager.getSelectQA20FeatureIdxQuery(tableName, featureId);
			HashMap<String, Object> idxMap = qa20DAO.selectQA20FeatureIdx(selectIdxqueryMap);
			int idx = (Integer) idxMap.get("f_idx");

			HashMap<String, Object> deleteFeatureMap = queryManager.getDeleteQA20FeatureQuery(tableName, idx);
			qa20DAO.deleteQA20Feature(deleteFeatureMap);
		} catch (Exception e) {
			txManager.rollback(status);
		}
		txManager.commit(status);
	}

	@Override
	public boolean dropQA20Layer(String type, Integer collectionIdx, String collectionName, QA20Layer layer) {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);

		String layerName = layer.getLayerName();
		QA20DBQueryManager queryManager = new QA20DBQueryManager();

		try {
			HashMap<String, Object> metadataIdxQuery = queryManager.getSelectQA20LayerMetaDataIdxQuery(collectionIdx,
					layerName);
			Integer mIdx = qa20DAO.selectQA20LayerMetadataIdx(metadataIdxQuery);

			// ngi_text_represent 삭제
			HashMap<String, Object> deleteTextRepQuery = queryManager.getDeleteTextRepresentQuery(mIdx);
			qa20DAO.deleteField(deleteTextRepQuery);
			// ngi_point_represent 삭제
			HashMap<String, Object> deletePointRepQuery = queryManager.getDeletePointRepresentQuery(mIdx);
			qa20DAO.deleteField(deletePointRepQuery);
			// ngi_lineString_represent 삭제
			HashMap<String, Object> deleteLineStringRepQuery = queryManager.getDeleteLineStringRepresentQuery(mIdx);
			qa20DAO.deleteField(deleteLineStringRepQuery);
			// ngi_polygon_represent 삭제
			HashMap<String, Object> deleteRegionRepQuery = queryManager.getDeleteRegionRepresentQuery(mIdx);
			qa20DAO.deleteField(deleteRegionRepQuery);
			// nda_aspatial_field_def 삭제
			HashMap<String, Object> deleteAspatialFieldQuery = queryManager.getDeleteAsptialFieldQuery(mIdx);
			qa20DAO.deleteField(deleteAspatialFieldQuery);
			// layerMetadata 삭제
			HashMap<String, Object> deleteLayerMetaQuery = queryManager.getDeleteQA20LayerMetaQuery(mIdx);
			qa20DAO.deleteField(deleteLayerMetaQuery);
			
			HashMap<String, Object> dropQuery = queryManager.getQA20DropLayerQuery(type, collectionName, layerName);
			qa20DAO.dropLayer(dropQuery);
			
//			HashMap<String, Object> deleteLayerCollectionQuery = queryManager
//					.getDeleteQA20LayerCollectionQuery(collectionIdx);
//			qa20DAO.deleteField(deleteLayerCollectionQuery);
		} catch (Exception e) {
			txManager.rollback(status);
			return false;
		}
		String layerTableName = "geo" + "_" + type + "_" + collectionName + "_" + layerName;
		String groupName = "gro" + "_" + type + "_" + collectionName;
		boolean isSuccessed = geoserverService.removeGeoserverLayer(layerTableName);
		if (isSuccessed) {
			txManager.commit(status);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void insertQA10CreateFeature(String tableName, QA10Feature createFeature) {

		QA10DBQueryManager queryManager = new QA10DBQueryManager();
		HashMap<String, Object> insertQuertMap = queryManager.getInertFeatureQuery(tableName, createFeature);
		qa10DAO.insertQA10Feature(insertQuertMap);
	}

	@Override
	public void updateQA10ModifyFeature(String tableName, QA10Feature modifyFeature) {

		QA10DBQueryManager queryManager = new QA10DBQueryManager();

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);

		try {
			// 1. featureID 조회
			String featureID = modifyFeature.getFeatureID();
			HashMap<String, Object> selectIdxqueryMap = queryManager.getSelectFeatureIdx(tableName, featureID);
			HashMap<String, Object> idxMap = qa10DAO.selectQA10FeatureIdx(selectIdxqueryMap);
			int idx = (Integer) idxMap.get("f_idx");

			// 2. 해당 feature 삭제
			HashMap<String, Object> deleteFeatureMap = queryManager.getDeleteFeature(tableName, idx);
			qa10DAO.deleteQA10Feature(deleteFeatureMap);

			// 3. 다시 insert
			HashMap<String, Object> insertFeatureMap = queryManager.getInertFeatureQuery(tableName, modifyFeature);
			qa10DAO.insertQA10Feature(insertFeatureMap);
		} catch (Exception e) {
			txManager.rollback(status);
		}
		txManager.commit(status);
	}

	@Override
	public void deleteQA10RemovedFeature(String tableName, String featureId) {

		QA10DBQueryManager queryManager = new QA10DBQueryManager();

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);
		try {
			HashMap<String, Object> selectIdxqueryMap = queryManager.getSelectFeatureIdx(tableName, featureId);
			HashMap<String, Object> idxMap = qa10DAO.selectQA10FeatureIdx(selectIdxqueryMap);
			int idx = (Integer) idxMap.get("f_idx");

			HashMap<String, Object> deleteFeatureMap = queryManager.getDeleteFeature(tableName, idx);
			qa10DAO.deleteQA10Feature(deleteFeatureMap);
		} catch (Exception e) {
			txManager.rollback(status);
		}
		txManager.commit(status);

	}

	@Override
	public Integer checkQA10LayerCollectionName(String collectionName) {

		QA10DBQueryManager queryManager = new QA10DBQueryManager();
		HashMap<String, Object> queryMap = queryManager.getSelectLayerCollectionIdx(collectionName);
		Integer cIdx = qa20DAO.selectQA20LayerCollectionIdx(queryMap);
		if (cIdx == null) {
			return null;
		} else {
			return cIdx;
		}
	}

	@Override
	public boolean createQA10Layer(String type, Integer collectionIdx, String collectionName, QA10Layer createLayer,
			String src) throws PSQLException {

		QA10DBQueryManager queryManager = new QA10DBQueryManager();

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);

		try {
			HashMap<String, Object> createQuery = queryManager.qa10LayerTbCreateQuery(type, collectionName, createLayer,
					src);
			qa10DAO.createQA10LayerTb(createQuery);

			// insertQA10Layer
			// List<HashMap<String, Object>> inertLayerQuerys =
			// queryManager.qa10LayerTbInsertQuery(type, collectionName,
			// createLayer, src);
			// for (int j = 0; j < inertLayerQuerys.size(); j++) {
			// HashMap<String, Object> insertLayerQuery =
			// inertLayerQuerys.get(j);
			// qa10DAO.insertQA10Layer(insertLayerQuery);
			// }

			// insertLayerMetadata
			HashMap<String, Object> insertQueryMap = queryManager.getInsertLayerMeataData(type, collectionName,
					collectionIdx, createLayer);
			qa10DAO.insertQA10LayerMetadata(insertQueryMap);

			// tablesLayer
			QA10Tables tables = new QA10Tables();
			Map<String, Object> tbLayers = tables.getLayerValues(createLayer);
			HashMap<String, Object> tablesQuery = queryManager.getInsertTables(collectionIdx, tbLayers);
			int tbIdx = qa10DAO.insertQA10LayerCollectionTableCommon(tablesQuery);
			if (tables.isLayers()) {
				List<HashMap<String, Object>> layersQuery = queryManager.getInsertTablesLayers(tbIdx, tbLayers);
				for (int i = 0; i < layersQuery.size(); i++) {
					qa10DAO.insertQA10LayerCollectionTableLayers(layersQuery.get(i));
				}
			}
		} catch (Exception e) {
			txManager.rollback(status);
			return false;
		}
		txManager.commit(status);
		return true;
	}

	@Override
	public boolean dropQA10Layer(String type, Integer collectionIdx, String collectionName, QA10Layer layer) {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);
		try {
			QA10DBQueryManager dbManager = new QA10DBQueryManager();
			HashMap<String, Object> selectLayerCollectionIdxQuery = dbManager
					.getSelectLayerCollectionIdx(collectionName);
			Integer cIdx = qa10DAO.selectQA10LayerCollectionIdx(selectLayerCollectionIdxQuery);
			if (cIdx != null) {
				HashMap<String, Object> metadataIdxQuery = dbManager.getSelectLayerMetaDataIdx(cIdx);
				List<HashMap<String, Object>> metadataIdxMapList = qa10DAO
						.selectQA10LayerMetadataIdxs(metadataIdxQuery);
				for (int i = 0; i < metadataIdxMapList.size(); i++) {
					HashMap<String, Object> metadataIdxMap = metadataIdxMapList.get(i);
					Integer mIdx = (Integer) metadataIdxMap.get("lm_idx");

					// get layerTb name
					HashMap<String, Object> layerTbNameQuery = dbManager.getSelectLayerTableNameQuery(mIdx);
					HashMap<String, Object> layerTbNameMap = qa10DAO.selectQA10LayerTableName(layerTbNameQuery);

					// layerTb drop
					String layerTbName = (String) layerTbNameMap.get("layer_t_name");
					HashMap<String, Object> dropLayerTbQuery = dbManager.getDropLayer(layerTbName);
					qa10DAO.dropLayer(dropLayerTbQuery);

					// layerMetadata 삭제
					HashMap<String, Object> deleteLayerMetaQuery = dbManager.getDeleteLayerMeta(cIdx);
					qa10DAO.deleteField(deleteLayerMetaQuery);

					// tables
					HashMap<String, Object> tableIdxQuery = dbManager.getSelectTableCommonIdx(cIdx);
					Integer tcIdx = qa10DAO.selectTableCommonIdx(tableIdxQuery);

					if (tcIdx != null) {
						// tables - layer
						HashMap<String, Object> deleteTableLayersQuery = dbManager.getDeleteTableLayers(tcIdx);
						qa10DAO.deleteField(deleteTableLayersQuery);
					}
				}
			}
		} catch (Exception e) {
			txManager.rollback(status);
			return false;
		}
		txManager.commit(status);
		return true;
	}

	@Override
	public boolean modifyQA10Layer(String type, Integer collectionIdx, String collectionName, QA10Layer qa10Layer,
			Map<String, Object> geoLayer) {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);

		QA10DBQueryManager queryManager = new QA10DBQueryManager();
	
		String orignId = qa10Layer.getOriginLayerID();
		String currentId = qa10Layer.getLayerID();
		try {
			HashMap<String, Object> queryMap = queryManager.getSelectQA10LayerMetaDataIdxQuery(collectionIdx, orignId);
			Integer lmIdx = qa10DAO.selectQA10LayerMetadataIdx(queryMap);

			// meta Tb - layerName update
			if (!currentId.equals(orignId)) {
				HashMap<String, Object> updateLayerNameQuery = queryManager.getUpdateQA10LayerMeataLayerIDQuery(lmIdx,
						currentId);
				qa10DAO.updateQA10LayerMetadataLayerID(updateLayerNameQuery);
			}

			// layerCollection_table_common
			HashMap<String, Object> selectTcIdxQuery = queryManager.getSelectTableCommonIdx(collectionIdx);
			int tcIdx = qa10DAO.selectTableCommonIdx(selectTcIdxQuery);

			// layerCollection_table_layer
			HashMap<String, Object> selectTlIdxQuery = queryManager.getSelectTableLayerIdx(tcIdx, orignId);
			int tlIdx = qa10DAO.selectTableLayerIdx(selectTlIdxQuery);

			// layerCollection_table_layer - layerId update
			HashMap<String, Object> updateTlIdQuery = queryManager.getUpdateTableLayerId(tlIdx, currentId);
			qa10DAO.updateTableLayerId(updateTlIdQuery);
		} catch (Exception e) {
			txManager.rollback(status);
			return false;
		}
		txManager.commit(status);
		// update Geoserver
		String originalName = (String) geoLayer.get("orignalName");
		String name = (String) geoLayer.get("name");
		String title = (String) geoLayer.get("title");
		String summary = (String) geoLayer.get("summary");
		boolean attChangeFlag = (Boolean) geoLayer.get("attChangeFlag");
		String tableName = "geo_" + type + "_" + collectionName + "_" + originalName;
		String tableNameCurrent = "geo_" + type + "_" + collectionName + "_" + currentId;
		boolean isSuccessed = geoserverService.updateFeatureType(tableName, tableNameCurrent, title, summary, "", attChangeFlag);
		if (isSuccessed) {
			txManager.commit(status);
			return true;
		} else {
			return false;
		}
		// return true;
	}
}
