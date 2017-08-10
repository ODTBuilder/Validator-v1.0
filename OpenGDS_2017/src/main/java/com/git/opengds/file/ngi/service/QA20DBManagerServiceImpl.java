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
import com.git.gdsbuilder.type.qa20.collection.QA20LayerCollection;
import com.git.gdsbuilder.type.qa20.header.NDAHeader;
import com.git.gdsbuilder.type.qa20.header.NGIHeader;
import com.git.gdsbuilder.type.qa20.layer.QA20Layer;
import com.git.gdsbuilder.type.qa20.layer.QA20LayerList;
import com.git.opengds.file.ngi.dbManager.QA20DBQueryManager;
import com.git.opengds.file.ngi.persistence.QA20LayerCollectionDAO;
import com.git.opengds.user.domain.UserVO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class QA20DBManagerServiceImpl implements QA20DBManagerService {

/*	@Inject
	private DataSourceTransactionManager txManager;*/

	@Inject
	private QA20LayerCollectionDAO dao;
	
/*	public QA20DBManagerServiceImpl(UserVO userVO){
		dao = new QA20LayerCollectionDAOImpl(userVO);
	}
*/
	public GeoLayerInfo insertQA20LayerCollection(UserVO userVO, QA20LayerCollection dtCollection, GeoLayerInfo layerInfo)
			 throws RuntimeException{
		/*DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);*/

		try {
			QA20DBQueryManager dbManager = new QA20DBQueryManager();
			// EditLayerDBQueryManager queryManager = new
			// EditLayerDBQueryManager();

			String collectionName = dtCollection.getFileName();
			String type = layerInfo.getFileType();

			HashMap<String, Object> insertCollectionQuery = dbManager.getInsertQA20LayerCollectionQuery(collectionName);
			int cIdx = dao.insertQA20LayerCollection(userVO, insertCollectionQuery);

			Map<String, Boolean> isFeaturesMap = new HashMap<String, Boolean>();

			String src = layerInfo.getOriginSrc();
			QA20LayerList createLayerList = dtCollection.getQa20LayerList();
			for (int i = 0; i < createLayerList.size(); i++) {
				QA20Layer qa20Layer = createLayerList.get(i);
				String layerName = qa20Layer.getLayerName();

				// isFeature
				if (qa20Layer.getFeatures().size() == 0) {
					isFeaturesMap.put(layerName, false);
					continue;
				} else {
					isFeaturesMap.put(layerName, true);

					// createQA20Layer
					HashMap<String, Object> createQuery = dbManager.getQA20LayerTbCreateQuery(type, collectionName,
							qa20Layer, src);
					dao.createQA20LayerTb(userVO, createQuery);

					// insertQA20Layer
					List<HashMap<String, Object>> inertLayerQuerys = dbManager.getQA20LayerInsertQuery(type,
							collectionName, qa20Layer, src);
					for (int j = 0; j < inertLayerQuerys.size(); j++) {
						HashMap<String, Object> insertLayerQuery = inertLayerQuerys.get(j);
						dao.insertQA20Layer(userVO, insertLayerQuery);
					}

					// insertLayerMedata
					HashMap<String, Object> insertQueryMap = dbManager.getInsertQA20LayerMeataData(type, collectionName,
							cIdx, qa20Layer);
					int lmIdx = dao.insertQA20LayerMetadata(userVO, insertQueryMap);

					NDAHeader ndaHeader = qa20Layer.getNdaHeader();
					// aspatial_field_def
					List<HashMap<String, Object>> fieldDefs = dbManager.getAspatialFieldDefsInsertQuery(lmIdx,
							ndaHeader);
					if (fieldDefs != null) {
						for (int j = 0; j < fieldDefs.size(); j++) {
							dao.insertNdaAspatialFieldDefs(userVO, fieldDefs.get(j));
						}
					}
					NGIHeader ngiHeader = qa20Layer.getNgiHeader();
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
					String layerTypeStr = qa20Layer.getLayerType();
					String layerType = dbManager.getLayerType(layerTypeStr);
					layerInfo.putLayerType(layerName, layerType);
					List<String> columns = dbManager.getLayerCoulmns(qa20Layer);
					layerInfo.putLayerColumns(layerName, columns);
					// layerInfo.putLayerBoundary(layerName, boundryMap);
				}
			}
			layerInfo.setIsFeatureMap(isFeaturesMap);
		} catch (RuntimeException e) {
//			txManager.rollback(status);
			throw new RuntimeException();
		}
		if (layerInfo != null) {
//			txManager.commit(status);
			layerInfo.setDbInsertFlag(true);
		}
		return layerInfo;
	}

	public GeoLayerInfo dropQA20LayerCollection(UserVO userVO, QA20LayerCollection dtCollection, GeoLayerInfo layerInfo)  throws RuntimeException{

		QA20DBQueryManager dbManager = new QA20DBQueryManager();
		/*DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);*/
		try {
			String collectionName = dtCollection.getFileName();
			HashMap<String, Object> selectLayerCollectionIdxQuery = dbManager
					.getSelectQA20LayerCollectionIdx(collectionName);
			Integer cIdx = dao.selectQA20LayerCollectionIdx(userVO, selectLayerCollectionIdxQuery);
			if (cIdx != null) {
				HashMap<String, Object> metadataIdxQuery = dbManager.getSelectQA20LayerMetaDataIdxQuery(cIdx);
				List<HashMap<String, Object>> metadataIdxMapList = dao.selectQA20LayerMetadataIdxs(userVO, metadataIdxQuery);
				for (int i = 0; i < metadataIdxMapList.size(); i++) {
					HashMap<String, Object> metadataIdxMap = metadataIdxMapList.get(i);
					Integer mIdx = (Integer) metadataIdxMap.get("lm_idx");

					// get layerTb name
					HashMap<String, Object> layerTbNameQuery = dbManager.getSelectQA20LayerTableNameQuery(mIdx);
					HashMap<String, Object> layerTbNameMap = dao.selectQA20LayerTableName(userVO, layerTbNameQuery);

					// layerTb drop
					String layerTbName = (String) layerTbNameMap.get("layer_t_name");
					HashMap<String, Object> dropLayerTbQuery = dbManager.getQA20DropLayerQuery(layerTbName);
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
			HashMap<String, Object> deleteLayerMetaQuery = dbManager.getDeleteQA20LayerMetaQuery(cIdx);
			dao.deleteField(userVO, deleteLayerMetaQuery);
			HashMap<String, Object> deleteLayerCollectionQuery = dbManager.getDeleteQA20LayerCollectionQuery(cIdx);
			dao.deleteField(userVO, deleteLayerCollectionQuery);
		} catch (RuntimeException e) {
//			txManager.rollback(status);
			throw new RuntimeException();
		}
//		txManager.commit(status);
		layerInfo.setDbInsertFlag(true);
		return layerInfo;
	}
}
