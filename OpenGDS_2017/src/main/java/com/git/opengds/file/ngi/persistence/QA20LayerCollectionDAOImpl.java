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

package com.git.opengds.file.ngi.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;

@Repository
public class QA20LayerCollectionDAOImpl implements QA20LayerCollectionDAO {

	@Inject
	private SqlSession sqlSession;

	private static final String namespace = "com.git.mappers.qa20Mappers.QA20LayerCollectionMapper";

	@Override
	public void insertQA20LayerCollection(Map<String, Object> collection) {
		sqlSession.insert(namespace + ".insertQA20LayerCollection", collection);
	}

	@Override
	public void insertQA20Layer(HashMap<String, Object> layer) throws PSQLException {
		sqlSession.insert(namespace + ".insertQA20Layer", layer);
	}

	@Override
	public void insertQA20LayerMetadatas(Map<String, Object> metadatas) throws PSQLException {
		sqlSession.insert(namespace + ".insertQA20LayerMetadata", metadatas);
	}

	@Override
	public void insertNdaAspatialFieldDefs(HashMap<String, Object> fieldDef) throws PSQLException {
		sqlSession.insert(namespace + ".insertNdaAspatialFieldDefs", fieldDef);
	}

	@Override
	public void insertTextRepresent(HashMap<String, Object> hashMap) throws PSQLException {
		sqlSession.insert(namespace + ".insertTextRepresent", hashMap);
	}

	@Override
	public void insertPointRepresent(HashMap<String, Object> hashMap) throws PSQLException {
		sqlSession.insert(namespace + ".insertPointRepresent", hashMap);

	}

	@Override
	public void insertLineStringRepresent(HashMap<String, Object> hashMap) throws PSQLException {
		sqlSession.insert(namespace + ".insertLineStringRepresent", hashMap);
	}

	@Override
	public void insertRegionRepresent(HashMap<String, Object> hashMap) throws PSQLException {
		sqlSession.insert(namespace + ".insertRegionRepresent", hashMap);
	}

	@Override
	public void createQA20LayerTb(HashMap<String, Object> hashMap) throws PSQLException {
		sqlSession.update(namespace + ".createQA20LayerTb", hashMap);
	}

	@Override
	public HashMap<String, Object> selectCountAllFeatures(HashMap<String, Object> countquery) {
		return sqlSession.selectOne(namespace + ".selectCountAllFeatures", countquery);
	}

	@Override
	public HashMap<String, Object> selectFeatureIdx(HashMap<String, Object> selectQuery) {
		return sqlSession.selectOne(namespace + ".selectFeatureIdx", selectQuery);
	}

	@Override
	public int deleteFeature(HashMap<String, Object> deleteQuery) {
		return sqlSession.delete(namespace + ".deleteFeature", deleteQuery);
	}

	@Override
	public void insertFeature(HashMap<String, Object> insertQuery) {
		sqlSession.insert(namespace + ".insertFeature", insertQuery);
	}

	@Override
	public HashMap<String, Object> selectQA20LayerBD(HashMap<String, Object> selectBDQuery) {
		return sqlSession.selectOne(namespace + ".selectLayerBD", selectBDQuery);
	}

	@Override
	public HashMap<String, Object> selectQA20LayerCollectionIdx(HashMap<String, Object> selectLayerCollectionIdxQuery) {
		return sqlSession.selectOne(namespace + ".selectLayerCollectionIdx", selectLayerCollectionIdxQuery);
	}
}
