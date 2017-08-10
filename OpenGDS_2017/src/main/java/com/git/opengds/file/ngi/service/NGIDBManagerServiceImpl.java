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

import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.ngi.collection.DTNGILayerCollection;
import com.git.gdsbuilder.type.ngi.header.NDAHeader;
import com.git.gdsbuilder.type.ngi.header.NGIHeader;
import com.git.gdsbuilder.type.ngi.layer.DTNGILayer;
import com.git.gdsbuilder.type.ngi.layer.DTNGILayerList;
import com.git.opengds.file.ngi.dbManager.NGIDBQueryManager;
import com.git.opengds.file.ngi.persistence.NGILayerCollectionDAO;
import com.git.opengds.user.domain.UserVO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class NGIDBManagerServiceImpl implements NGIDBManagerService {

	/*
	 * @Inject private DataSourceTransactionManager txManager;
	 */

	@Inject
	private NGILayerCollectionDAO dao;

	/*
	 * public QA20DBManagerServiceImpl(UserVO userVO){ dao = new
	 * QA20LayerCollectionDAOImpl(userVO); }
	 */
	public GeoLayerInfo insertNGILayerCollection(UserVO userVO, DTNGILayerCollection dtCollection,
			GeoLayerInfo layerInfo) throws RuntimeException {
		/*
		 * DefaultTransactionDefinition def = new
		 * DefaultTransactionDefinition();
		 * def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED
		 * ); TransactionStatus status = txManager.getTransaction(def);
		 */

		try {
			NGIDBQueryManager dbManager = new NGIDBQueryManager();
			// EditLayerDBQueryManager queryManager = new
			// EditLayerDBQueryManager();

			String collectionName = dtCollection.getFileName();
			String type = layerInfo.getFileType();

			HashMap<String, Object> insertCollectionQuery = dbManager.getInsertNGILayerCollectionQuery(collectionName);
			int cIdx = dao.insertNGILayerCollection(userVO, insertCollectionQuery);

			Map<String, Boolean> isFeaturesMap = new HashMap<String, Boolean>();

			String src = layerInfo.getOriginSrc();
			DTNGILayerList createLayerList = dtCollection.getNGILayerList();
			for (int i = 0; i < createLayerList.size(); i++) {
				DTNGILayer ngiLayer = createLayerList.get(i);
				String layerName = ngiLayer.getLayerName();

				// isFeature
				if (ngiLayer.getFeatures().size() == 0) {
					isFeaturesMap.put(layerName, false);
					continue;
				} else {
					isFeaturesMap.put(layerName, true);

					// createQA20Layer
					HashMap<String, Object> createQuery = dbManager.getNGILayerTbCreateQuery(type, collectionName,
							ngiLayer, src);
					dao.createNGILayerTb(userVO, createQuery);

					// insertQA20Layer
					List<HashMap<String, Object>> inertLayerQuerys = dbManager.getNGILayerInsertQuery(type,
							collectionName, ngiLayer, src);
					for (int j = 0; j < inertLayerQuerys.size(); j++) {
						HashMap<String, Object> insertLayerQuery = inertLayerQuerys.get(j);
						dao.insertNGILayer(userVO, insertLayerQuery);
					}

					// insertLayerMedata
					HashMap<String, Object> insertQueryMap = dbManager.getInsertNGILayerMeataData(type, collectionName,
							cIdx, ngiLayer);
					int lmIdx = dao.insertNGILayerMetadata(userVO, insertQueryMap);

					NDAHeader ndaHeader = ngiLayer.getNdaHeader();
					// aspatial_field_def
					List<HashMap<String, Object>> fieldDefs = dbManager.getAspatialFieldDefsInsertQuery(lmIdx,
							ndaHeader);
					if (fieldDefs != null) {
						for (int j = 0; j < fieldDefs.size(); j++) {
							dao.insertNdaAspatialFieldDefs(userVO, fieldDefs.get(j));
						}
					}
					NGIHeader ngiHeader = ngiLayer.getNgiHeader();
					// point_represent
					List<HashMap<String, Object>> ptReps = dbManager.getPtRepresentInsertQuery(lmIdx,
							ngiHeader.getPoint_represent());
					if (ptReps != null) {
						for (int j = 0; j < ptReps.size(); j++) {
							dao.insertPointRepresent(userVO, ptReps.get(j));
						}
					}
					// lineString_represent
					List<HashMap<String, Object>> lnReps = dbManager.getLnRepresentInsertQuery(lmIdx,
							ngiHeader.getLine_represent());
					if (lnReps != null) {
						for (int j = 0; j < lnReps.size(); j++) {
							dao.insertLineStringRepresent(userVO, lnReps.get(j));
						}
					}
					// region_represent
					List<HashMap<String, Object>> rgReps = dbManager.getRgRepresentInsertQuery(lmIdx,
							ngiHeader.getRegion_represent());
					if (rgReps != null) {
						for (int j = 0; j < rgReps.size(); j++) {
							dao.insertRegionRepresent(userVO, rgReps.get(j));
						}
					}
					// text_represent
					List<HashMap<String, Object>> txtReps = dbManager.getTxtRepresentInsertQuery(lmIdx,
							ngiHeader.getText_represent());
					if (txtReps != null) {
						for (int j = 0; j < txtReps.size(); j++) {
							dao.insertTextRepresent(userVO, txtReps.get(j));
						}
					}
					// geoLayerInfo
					layerInfo.putLayerName(layerName);
					String layerTypeStr = ngiLayer.getLayerType();
					String layerType = dbManager.getLayerType(layerTypeStr);
					layerInfo.putLayerType(layerName, layerType);
					List<String> columns = dbManager.getLayerCoulmns(ngiLayer);
					layerInfo.putLayerColumns(layerName, columns);
					// layerInfo.putLayerBoundary(layerName, boundryMap);
				}
			}
			layerInfo.setIsFeatureMap(isFeaturesMap);
		} catch (RuntimeException e) {
			// txManager.rollback(status);
			throw new RuntimeException();
		}
		if (layerInfo != null) {
			// txManager.commit(status);
			layerInfo.setDbInsertFlag(true);
		}
		return layerInfo;
	}

	public GeoLayerInfo dropNGILayerCollection(UserVO userVO, DTNGILayerCollection dtCollection, GeoLayerInfo layerInfo)
			throws RuntimeException {

		NGIDBQueryManager dbManager = new NGIDBQueryManager();
		/*
		 * DefaultTransactionDefinition def = new
		 * DefaultTransactionDefinition();
		 * def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED
		 * ); TransactionStatus status = txManager.getTransaction(def);
		 */
		try {
			String collectionName = dtCollection.getFileName();
			HashMap<String, Object> selectLayerCollectionIdxQuery = dbManager
					.getSelectNGILayerCollectionIdx(collectionName);
			Integer cIdx = dao.selectNGILayerCollectionIdx(userVO, selectLayerCollectionIdxQuery);
			if (cIdx != null) {
				HashMap<String, Object> metadataIdxQuery = dbManager.getSelectNGILayerMetaDataIdxQuery(cIdx);
				List<HashMap<String, Object>> metadataIdxMapList = dao.selectNGILayerMetadataIdxs(userVO,
						metadataIdxQuery);
				for (int i = 0; i < metadataIdxMapList.size(); i++) {
					HashMap<String, Object> metadataIdxMap = metadataIdxMapList.get(i);
					Integer mIdx = (Integer) metadataIdxMap.get("lm_idx");

					// get layerTb name
					HashMap<String, Object> layerTbNameQuery = dbManager.getSelectNGILayerTableNameQuery(mIdx);
					HashMap<String, Object> layerTbNameMap = dao.selectNGILayerTableName(userVO, layerTbNameQuery);

					// layerTb drop
					String layerTbName = (String) layerTbNameMap.get("layer_t_name");
					HashMap<String, Object> dropLayerTbQuery = dbManager.getDropNGILayerQuery(layerTbName);
					dao.dropLayer(userVO, dropLayerTbQuery);

					// ngi_text_represent 삭제
					HashMap<String, Object> deleteTextRepQuery = dbManager.getDeleteTextRepresentQuery(mIdx);
					dao.deleteField(userVO, deleteTextRepQuery);
					// ngi_point_represent 삭제
					HashMap<String, Object> deletePointRepQuery = dbManager.getDeletePointRepresentQuery(mIdx);
					dao.deleteField(userVO, deletePointRepQuery);
					// ngi_lineString_represent 삭제
					HashMap<String, Object> deleteLineStringRepQuery = dbManager
							.getDeleteLineStringRepresentQuery(mIdx);
					dao.deleteField(userVO, deleteLineStringRepQuery);
					// ngi_polygon_represent 삭제
					HashMap<String, Object> deleteRegionRepQuery = dbManager.getDeleteRegionRepresentQuery(mIdx);
					dao.deleteField(userVO, deleteRegionRepQuery);
					// nda_aspatial_field_def 삭제
					HashMap<String, Object> deleteAspatialFieldQuery = dbManager.getDeleteAsptialFieldQuery(mIdx);
					dao.deleteField(userVO, deleteAspatialFieldQuery);
				}
			}
			// layerMetadata 삭제
			HashMap<String, Object> deleteLayerMetaQuery = dbManager.getDeleteNGILayerMetaQuery(cIdx);
			dao.deleteField(userVO, deleteLayerMetaQuery);
			HashMap<String, Object> deleteLayerCollectionQuery = dbManager.getDeleteNGILayerCollectionQuery(cIdx);
			dao.deleteField(userVO, deleteLayerCollectionQuery);
		} catch (RuntimeException e) {
			// txManager.rollback(status);
			throw new RuntimeException();
		}
		// txManager.commit(status);
		layerInfo.setDbInsertFlag(true);
		return layerInfo;
	}
}
