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
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.git.opengds.common.DataSourceFactory;
import com.git.opengds.user.domain.UserVO;

@Repository
public class NGILayerCollectionDAOImpl extends DataSourceFactory implements NGILayerCollectionDAO  {

	private SqlSession sqlSession;
	
	/*public QA20LayerCollectionDAOImpl(UserVO user) {
		// TODO Auto-generated constructor stub
		sqlSession = super.getSqlSession(user.getId());
	}*/
	
	private static final String namespace = "com.git.mappers.qa20Mappers.QA20LayerCollectionMapper";

	// qa20_LayerCollection
	@Override
	public int insertQA20LayerCollection(UserVO userVO, Map<String, Object> collection) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertQA20LayerCollection", collection);
		return (Integer) collection.get("c_idx");
	}

	@Override
	public int selectQA20LayerCollectionIdx(UserVO userVO, HashMap<String, Object> selectLayerCollectionIdxQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> idxMap = sqlSession.selectOne(namespace + ".selectLayerCollectionIdx",
				selectLayerCollectionIdxQuery);
		return (Integer) idxMap.get("c_idx");
	}

	// qa20_Layer
	@Override
	public void createQA20LayerTb(UserVO userVO, HashMap<String, Object> hashMap) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(namespace + ".createQA20LayerTb", hashMap);
	}

	@Override
	public void insertQA20Layer(UserVO userVO, HashMap<String, Object> layer) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertQA20Layer", layer);
	}

	@Override
	public int dropLayer(UserVO userVO, HashMap<String, Object> dropQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.update(namespace + ".dropQA20Layer", dropQuery);
	}

	// qa20_layer_metadata
	@Override
	public int insertQA20LayerMetadata(UserVO userVO, HashMap<String, Object> metadata) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertQA20LayerMetadata", metadata);
		return (Integer) metadata.get("lm_idx");
	}

	// nda_aspatial_field_def
	@Override
	public void insertNdaAspatialFieldDefs(UserVO userVO, HashMap<String, Object> fieldDef) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertNdaAspatialFieldDefs", fieldDef);
	}

	// ngi_point_represent
	@Override
	public void insertPointRepresent(UserVO userVO, HashMap<String, Object> hashMap) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertPointRepresent", hashMap);
	}

	// ngi_linestring_represent
	@Override
	public void insertLineStringRepresent(UserVO userVO, HashMap<String, Object> hashMap) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertLineStringRepresent", hashMap);
	}

	// ngi_region_represents
	@Override
	public void insertRegionRepresent(UserVO userVO, HashMap<String, Object> hashMap) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertRegionRepresent", hashMap);
	}

	// ngi_text_represent
	@Override
	public void insertTextRepresent(UserVO userVO, HashMap<String, Object> hashMap) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertTextRepresent", hashMap);
	}

	@Override
	public HashMap<String, Object> selectCountAllQA20Features(UserVO userVO, HashMap<String, Object> countquery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectOne(namespace + ".selectCountAllFeatures", countquery);
	}

	@Override
	public HashMap<String, Object> selectQA20FeatureIdx(UserVO userVO, HashMap<String, Object> selectQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectOne(namespace + ".selectFeatureIdx", selectQuery);
	}

	@Override
	public int deleteQA20Feature(UserVO userVO, HashMap<String, Object> deleteQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.delete(namespace + ".deleteFeature", deleteQuery);
	}

	@Override
	public void insertQA20Feature(UserVO userVO, HashMap<String, Object> insertQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertFeature", insertQuery);
	}

	@Override
	public List<HashMap<String, Object>> selectQA20LayerMetadataIdxs(UserVO userVO, HashMap<String, Object> metadataIdxQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectList(namespace + ".selectQA20LayerMetadataIdxs", metadataIdxQuery);
	}

	@Override
	public HashMap<String, Object> selectQA20LayerTableName(UserVO userVO, HashMap<String, Object> layerTbNameQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectOne(namespace + ".selectQA20LayerTableName", layerTbNameQuery);
	}

	@Override
	public void deleteField(UserVO userVO, HashMap<String, Object> deleteHeaderQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.delete(namespace + ".deleteHeader", deleteHeaderQuery);
	}

	@Override
	public int selectQA20LayerMetadataIdx(UserVO userVO, HashMap<String, Object> metadataIdxQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> idxMap = sqlSession.selectOne(namespace + ".selectQA20LayerMetadataIdx",
				metadataIdxQuery);
		return (Integer) idxMap.get("lm_idx");
	}

	@Override
	public void updateQA20LayerMetadataLayerName(UserVO userVO, HashMap<String, Object> updateLayerNameQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(namespace + ".updateQA20LayerMetadataLayerName", updateLayerNameQuery);
	}

	@Override
	public void updateQA20LayerMetadataBoundary(UserVO userVO, HashMap<String, Object> updateBoundaryQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(namespace + ".updateQA20LayerMetadataBoundary", updateBoundaryQuery);
	}

	@Override
	public HashMap<String, Object> selectNdaAspatialFieldFidxs(UserVO userVO, HashMap<String, Object> selectNadFieldsQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectOne(namespace + ".selectNadField", selectNadFieldsQuery);
	}

	@Override
	public void updateNdaAspatialField(UserVO userVO, HashMap<String, Object> updateFieldQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(namespace + ".updateNdaAspatialField", updateFieldQuery);
	}

	@Override
	public HashMap<String, Object> selectQA20LayerMeata(UserVO userVO, HashMap<String, Object> selectAllMetaIdxQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectOne(namespace + ".selectAllLayerMeata", selectAllMetaIdxQuery);
	}

	@Override
	public List<HashMap<String, Object>> selectTextRepresent(UserVO userVO, HashMap<String, Object> selectTextRepresentQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectList(namespace + ".selectTextRepresent", selectTextRepresentQuery);
	}

	@Override
	public List<HashMap<String, Object>> selectResionRepresent(UserVO userVO, HashMap<String, Object> selectResionRepresentQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectList(namespace + ".selectResionRepresent", selectResionRepresentQuery);
	}

	@Override
	public List<HashMap<String, Object>> selectPointRepresent(UserVO userVO, HashMap<String, Object> selectPointRepresentQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectList(namespace + ".selectPointRepresent", selectPointRepresentQuery);
	}

	@Override
	public List<HashMap<String, Object>> selectLineStringRepresent(
			UserVO userVO, HashMap<String, Object> selectLineStringRepresentQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectList(namespace + ".selectLineStringRepresent", selectLineStringRepresentQuery);
	}

	@Override
	public List<HashMap<String, Object>> selectNdaAspatialField(UserVO userVO, HashMap<String, Object> selectNdaAspatialFieldQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectList(namespace + ".selectNdaAspatialField", selectNdaAspatialFieldQuery);
	}

	@Override
	public List<HashMap<String, Object>> selectAllQA20Features(UserVO userVO, HashMap<String, Object> selectAllFeaturesQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectList(namespace + ".selectAllfeatures", selectAllFeaturesQuery);
	}
}
