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

import javax.inject.Inject;

import org.postgresql.util.PSQLException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.git.gdsbuilder.type.simple.collection.LayerCollection;
import com.git.gdsbuilder.type.simple.collection.LayerCollectionList;
import com.git.gdsbuilder.type.simple.feature.Feature;
import com.git.gdsbuilder.type.simple.feature.FeatureList;
import com.git.gdsbuilder.type.simple.layer.Layer;
import com.git.gdsbuilder.type.simple.layer.LayerList;
import com.git.opengds.editor.dbManager.EditLayerDBQueryManager;
import com.git.opengds.file.ngi.persistence.QA20LayerCollectionDAO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class EditDBManagerServiceImpl implements EditDBManagerService {

	@Inject
	private DataSourceTransactionManager txManager;

	@Inject
	private QA20LayerCollectionDAO dao;

	@Override
	public boolean updateFeatures(LayerCollectionList collecionList) throws PSQLException {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);

		for (int i = 0; i < collecionList.size(); i++) {
			LayerCollection collection = collecionList.get(i);
			String collectionID = collection.getId();
			LayerList layerList = collection.getLayerList();
			for (int j = 0; j < layerList.size(); j++) {
				Layer layer = layerList.get(j);
				String layerName = layer.getLayerName();
				String tableName = "geo" + "_" + collectionID + "_" + layerName;
				EditLayerDBQueryManager dbManager = new EditLayerDBQueryManager(tableName);
				FeatureList featureList = layer.getFeatureList();
				for (int k = 0; k < featureList.size(); k++) {
					Feature feature = featureList.get(k);
					String featureID = feature.getFeatureID();
					try {
						// select
//						HashMap<String, Object> selectQuery = dbManager.selectFeatureQuery(tableName, featureID);
//						HashMap<String, Object> selectFID = dao.selectFeatureIdx(selectQuery);
//						int fID = (Integer) selectFID.get("f_idx");
//						// delete
//						HashMap<String, Object> deleteQuery = dbManager.deleteFeatureQuery(tableName, fID);
//						int successed = dao.deleteFeature(deleteQuery);
//						// insert
//						HashMap<String, Object> insertQuery = dbManager.insertFeatureQuery(tableName, fID, feature);
//						dao.insertFeature(insertQuery);
					} catch (Exception e) {
						txManager.rollback(status);
						return false;
					}
				}
			}
		}
		txManager.commit(status);
		return false;
	}
}
