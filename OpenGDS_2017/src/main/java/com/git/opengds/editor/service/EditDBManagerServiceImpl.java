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

import java.net.MalformedURLException;
import java.util.ArrayList;
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
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.qa10.feature.QA10Feature;
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
	public boolean createQA20Layer(String type, Integer idx, String collectionName, QA20Layer qa20Layer)
			throws PSQLException {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);

		try {
			QA20DBQueryManager queryManager = new QA20DBQueryManager();

			// createQA20Layer
			HashMap<String, Object> createQuery = queryManager.getQA20LayerTbCreateQuery(type, collectionName,
					qa20Layer);
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
	public void insertQA20CreateFeature(String tableName, QA20Feature createFeature) {

		QA20DBQueryManager queryManager = new QA20DBQueryManager();
		HashMap<String, Object> insertQuertMap = queryManager.getInertQA20FeatureQuery(tableName, createFeature);
		qa20DAO.insertQA20Feature(insertQuertMap);
	}

	@Override
	public void updateQA20ModifyFeature(String tableName, QA20Feature modifyFeature) {

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
			HashMap<String, Object> insertFeatureMap = queryManager.getInertQA20FeatureQuery(tableName, modifyFeature);
			qa20DAO.insertQA20Feature(insertFeatureMap);
		} catch (Exception e) {
			txManager.rollback(status);
		}
		txManager.commit(status);
	}

	@Override
	public boolean modifyQA20Layer(String type, Integer collectionIdx, QA20Layer qa20Layer,
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
		boolean isSuccessed = geoserverService.updateFeatureType(originalName, name, title, summary, "", attChangeFlag);
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
			HashMap<String, Object> dropQuery = queryManager.getQA20DropLayerQuery(type, collectionName, layerName);
			qa20DAO.dropLayer(dropQuery);

			HashMap<String, Object> metadataIdxMap = queryManager.getSelectQA20LayerMetaDataIdxQuery(collectionIdx,
					layerName);
			Integer mIdx = (Integer) metadataIdxMap.get("lm_idx");

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
			HashMap<String, Object> deleteLayerMetaQuery = queryManager.getDeleteQA20LayerMetaQuery(collectionIdx);
			qa20DAO.deleteField(deleteLayerMetaQuery);
			HashMap<String, Object> deleteLayerCollectionQuery = queryManager
					.getDeleteQA20LayerCollectionQuery(collectionIdx);
			qa20DAO.deleteField(deleteLayerCollectionQuery);
		} catch (Exception e) {
			txManager.rollback(status);
			return false;
		}
		String layerTableName = "\"geo" + "_" + type + "_" + collectionName + "_" + layerName + "\"";
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
	public GeoLayerInfo createQA10LayerCollection(String type, EditQA10Collection editCollection) throws Exception {

		return null;
	}

	@Override
	public GeoLayerInfo createQA10Layers(String type, Integer collectionIdx, EditQA10Collection editCollection)
			throws PSQLException, IllegalArgumentException, MalformedURLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> dropQA10LayerCollection(String type, EditQA10Collection editCollection) {
		// TODO Auto-generated method stub
		return null;
	}

}
