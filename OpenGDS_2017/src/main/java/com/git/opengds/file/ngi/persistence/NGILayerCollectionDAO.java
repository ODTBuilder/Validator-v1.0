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

import com.git.opengds.user.domain.UserVO;

public interface NGILayerCollectionDAO {

	public int insertNGILayerCollection(UserVO userVO, Map<String, Object> collection) throws RuntimeException;

	public int selectNGILayerCollectionIdx(UserVO userVO, HashMap<String, Object> selectLayerCollectionIdxQuery) throws RuntimeException;

	public void createNGILayerTb(UserVO userVO, HashMap<String, Object> hashMap) throws RuntimeException;

	public void insertNGILayer(UserVO userVO, HashMap<String, Object> layer) throws RuntimeException;

	public int dropLayer(UserVO userVO, HashMap<String, Object> dropQuery) throws RuntimeException;

	public int insertNGILayerMetadata(UserVO userVO, HashMap<String, Object> metadata) throws RuntimeException;

	public void insertNdaAspatialFieldDefs(UserVO userVO, HashMap<String, Object> fieldDef) throws RuntimeException;

	public void insertPointRepresent(UserVO userVO, HashMap<String, Object> hashMap) throws RuntimeException;

	public void insertLineStringRepresent(UserVO userVO, HashMap<String, Object> hashMap) throws RuntimeException;

	public void insertRegionRepresent(UserVO userVO, HashMap<String, Object> hashMap) throws RuntimeException;

	public void insertTextRepresent(UserVO userVO, HashMap<String, Object> hashMap) throws RuntimeException;

	public HashMap<String, Object> selectCountAllNGIFeatures(UserVO userVO, HashMap<String, Object> countquery)throws RuntimeException;

	public HashMap<String, Object> selectNGIFeatureIdx(UserVO userVO, HashMap<String, Object> selectQuery)throws RuntimeException;

	public int deleteNGIFeature(UserVO userVO, HashMap<String, Object> deleteQuery) throws RuntimeException;

	public void insertNGIFeature(UserVO userVO, HashMap<String, Object> insertQuery) throws RuntimeException;

	public List<HashMap<String, Object>> selectNGILayerMetadataIdxs(UserVO userVO, HashMap<String, Object> metadataIdxQuery) throws RuntimeException;

	public int selectNGILayerMetadataIdx(UserVO userVO, HashMap<String, Object> metadataIdxQuery) throws RuntimeException;

	public HashMap<String, Object> selectNGILayerTableName(UserVO userVO, HashMap<String, Object> layerTbNameQuery) throws RuntimeException;

	public void deleteField(UserVO userVO, HashMap<String, Object> deleteTextRepQuery) throws RuntimeException;

	public void updateNGILayerMetadataLayerName(UserVO userVO, HashMap<String, Object> updateLayerNameQuery) throws RuntimeException;

	public void updateNGILayerMetadataBoundary(UserVO userVO, HashMap<String, Object> updateBoundaryQuery) throws RuntimeException;

	public HashMap<String, Object> selectNdaAspatialFieldFidxs(UserVO userVO, HashMap<String, Object> selectNadFieldsQuery) throws RuntimeException;

	public void updateNdaAspatialField(UserVO userVO, HashMap<String, Object> updateFieldQuery) throws RuntimeException;

	public HashMap<String, Object> selectNGILayerMeata(UserVO userVO, HashMap<String, Object> selectAllMetaIdxQuery);

	public List<HashMap<String, Object>> selectTextRepresent(UserVO userVO, HashMap<String, Object> selectTextRepresentQuery) throws RuntimeException;

	public List<HashMap<String, Object>> selectResionRepresent(UserVO userVO, HashMap<String, Object> selectResionRepresentQuery) throws RuntimeException;

	public List<HashMap<String, Object>> selectPointRepresent(UserVO userVO, HashMap<String, Object> selectPointRepresentQuery) throws RuntimeException;

	public List<HashMap<String, Object>> selectLineStringRepresent(
			UserVO userVO, HashMap<String, Object> selectLineStringRepresentQuery) throws RuntimeException;

	public List<HashMap<String, Object>> selectNdaAspatialField(UserVO userVO, HashMap<String, Object> selectNdaAspatialFieldQuery) throws RuntimeException;

	public List<HashMap<String, Object>> selectAllNGIFeatures(UserVO userVO, HashMap<String, Object> selectAllFeaturesQuery) throws RuntimeException;

}
