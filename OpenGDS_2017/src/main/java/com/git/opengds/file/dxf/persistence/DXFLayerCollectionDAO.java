package com.git.opengds.file.dxf.persistence;

import java.util.HashMap;
import java.util.List;

import com.git.opengds.user.domain.UserVO;

public interface DXFLayerCollectionDAO {

	public void createDXFLayerTb(UserVO userVO, HashMap<String, Object> qa20Layertb) throws RuntimeException;

	public void insertDXFLayer(UserVO userVO, HashMap<String, Object> qa10Layer) throws RuntimeException;

	public int insertDXFLayerCollection(UserVO userVO, HashMap<String, Object> insertCollectionQuery) throws RuntimeException;

	public void insertDXFLayerMetadata(UserVO userVO, HashMap<String, Object> insertQueryMap) throws RuntimeException;

	public void insertDXFFeature(UserVO userVO, HashMap<String, Object> insertQuertMap) throws RuntimeException;

	public HashMap<String, Object> selectDXFFeatureIdx(UserVO userVO, HashMap<String, Object> selectIdxqueryMap) throws RuntimeException;

	public int deleteDXFFeature(UserVO userVO, HashMap<String, Object> deleteFeatureMap) throws RuntimeException;

	public int insertDXFLayerCollectionBlocksCommon(UserVO userVO, HashMap<String, Object> blocksQuery) throws RuntimeException;

	public void insertDXFLayercollectionBlockEntity(UserVO userVO, HashMap<String, Object> entitiesQuery) throws RuntimeException;

	public int insertDXFLayerCollectionTableCommon(UserVO userVO, HashMap<String, Object> tablesQuery) throws RuntimeException;

	public void insertDXFLayerCollectionTableLayers(UserVO userVO, HashMap<String, Object> hashMap) throws RuntimeException;

	public Integer selectDXFLayerCollectionIdx(UserVO userVO, HashMap<String, Object> selectLayerCollectionIdxQuery) throws RuntimeException;

	public List<HashMap<String, Object>> selectDXFLayerMetadataIdxs(UserVO userVO, HashMap<String, Object> metadataIdxQuery) throws RuntimeException;

	public int selectDXFLayerMetadataIdx(UserVO userVO, HashMap<String, Object> metadataIdxQuery) throws RuntimeException;

	public HashMap<String, Object> selectDXFLayerTableName(UserVO userVO, HashMap<String, Object> layerTbNameQuery) throws RuntimeException;

	public int dropDXFLayer(UserVO userVO, HashMap<String, Object> dropLayerTbQuery) throws RuntimeException;

	public int deleteField(UserVO userVO, HashMap<String, Object> deleteLayerMetaQuery) throws RuntimeException;

	public Integer selectTableCommonIdx(UserVO userVO, HashMap<String, Object> tableIdxQuery) throws RuntimeException;

	public Integer selectBlockCommonIdx(UserVO userVO, HashMap<String, Object> tableIdxQuery) throws RuntimeException;

	public List<HashMap<String, Object>> selectBlockCommonIdxs(UserVO userVO, HashMap<String, Object> tableIdxQuery) throws RuntimeException;

	public void updateDXFLayerMetadataLayerID(UserVO userVO, HashMap<String, Object> updateLayerNameQuery) throws RuntimeException;

	public int selectTableLayerIdx(UserVO userVO, HashMap<String, Object> selectTlIdxQuery) throws RuntimeException;

	public void updateTableLayerId(UserVO userVO, HashMap<String, Object> updateTlIdQuery) throws RuntimeException;

	public HashMap<String, Object> selectDXFLayerMeata(UserVO userVO, HashMap<String, Object> selectAllMetaQuery) throws RuntimeException;

	public List<HashMap<String, Object>> selectDXFFeatures(UserVO userVO, HashMap<String, Object> selectFeaturesQuery) throws RuntimeException;

	public HashMap<String, Object> selectTablesCommon(UserVO userVO, HashMap<String, Object> selectTablesCommonsQuery) throws RuntimeException;

	public List<HashMap<String, Object>> selectTablesLayer(UserVO userVO, HashMap<String, Object> selectTablesLayerQuery) throws RuntimeException;

	public int insertDXFLayercollectionBlockPolyline(UserVO userVO, HashMap<String, Object> polylineQuery) throws RuntimeException;

	public void insertDXFLayercollectionBlockVertex(UserVO userVO, HashMap<String, Object> vertextInsertQuery) throws RuntimeException;

	public HashMap<String, Object> selectDXFlayerBlocksCommon(UserVO userVO, HashMap<String, Object> selectBlockCommonQuery) throws RuntimeException;

	public List<HashMap<String, Object>> selectBlockEntities(UserVO userVO, HashMap<String, Object> selectBlockArcList) throws RuntimeException;

	public int insertDXFLayercollectionBlockLWPolyline(UserVO userVO, HashMap<String, Object> polylineQuery) throws RuntimeException;

}
