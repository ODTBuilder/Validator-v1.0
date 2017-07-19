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

import org.postgresql.util.PSQLException;

public interface QA20LayerCollectionDAO {

	public int insertQA20LayerCollection(Map<String, Object> collection);

	public int selectQA20LayerCollectionIdx(HashMap<String, Object> selectLayerCollectionIdxQuery);

	public void createQA20LayerTb(HashMap<String, Object> hashMap) throws PSQLException;

	public void insertQA20Layer(HashMap<String, Object> layer) throws PSQLException;

	public int dropLayer(HashMap<String, Object> dropQuery);

	public int insertQA20LayerMetadata(HashMap<String, Object> metadata) throws PSQLException;

	public void insertNdaAspatialFieldDefs(HashMap<String, Object> fieldDef) throws PSQLException;

	public void insertPointRepresent(HashMap<String, Object> hashMap) throws PSQLException;

	public void insertLineStringRepresent(HashMap<String, Object> hashMap) throws PSQLException;

	public void insertRegionRepresent(HashMap<String, Object> hashMap) throws PSQLException;

	public void insertTextRepresent(HashMap<String, Object> hashMap) throws PSQLException;

	public HashMap<String, Object> selectCountAllQA20Features(HashMap<String, Object> countquery);

	public HashMap<String, Object> selectQA20FeatureIdx(HashMap<String, Object> selectQuery);

	public int deleteQA20Feature(HashMap<String, Object> deleteQuery);

	public void insertQA20Feature(HashMap<String, Object> insertQuery);

	public List<HashMap<String, Object>> selectQA20LayerMetadataIdxs(HashMap<String, Object> metadataIdxQuery);

	public int selectQA20LayerMetadataIdx(HashMap<String, Object> metadataIdxQuery);

	public HashMap<String, Object> selectQA20LayerTableName(HashMap<String, Object> layerTbNameQuery);

	public void deleteField(HashMap<String, Object> deleteTextRepQuery);

	public void updateQA20LayerMetadataLayerName(HashMap<String, Object> updateLayerNameQuery);

	public void updateQA20LayerMetadataBoundary(HashMap<String, Object> updateBoundaryQuery);

	public HashMap<String, Object> selectNdaAspatialFieldFidxs(HashMap<String, Object> selectNadFieldsQuery);

	public void updateNdaAspatialField(HashMap<String, Object> updateFieldQuery);

	public HashMap<String, Object> selectQA20LayerMeata(HashMap<String, Object> selectAllMetaIdxQuery);

	public List<HashMap<String, Object>> selectTextRepresent(HashMap<String, Object> selectTextRepresentQuery);

	public List<HashMap<String, Object>> selectResionRepresent(HashMap<String, Object> selectResionRepresentQuery);

	public List<HashMap<String, Object>> selectPointRepresent(HashMap<String, Object> selectPointRepresentQuery);

	public List<HashMap<String, Object>> selectLineStringRepresent(
			HashMap<String, Object> selectLineStringRepresentQuery);

	public List<HashMap<String, Object>> selectNdaAspatialField(HashMap<String, Object> selectNdaAspatialFieldQuery);

	public List<HashMap<String, Object>> selectAllQA20Features(HashMap<String, Object> selectAllFeaturesQuery);

}
