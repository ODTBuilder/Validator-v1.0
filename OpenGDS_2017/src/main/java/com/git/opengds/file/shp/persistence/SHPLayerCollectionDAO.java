package com.git.opengds.file.shp.persistence;

import java.util.HashMap;

import com.git.opengds.user.domain.UserVO;

public interface SHPLayerCollectionDAO {

	public int insertSHPLayerCollection(UserVO userVO, HashMap<String, Object> insertCollectionQuery);

	public void createSHPLayerTb(UserVO userVO, HashMap<String, Object> createLayerQuery);

	public void insertSHPLayer(UserVO userVO, HashMap<String, Object> insertLayerQuery);

	public void insertSHPLayerMetadata(UserVO userVO, HashMap<String, Object> insertLayerMeteQuery);

	public int selectSHPFeatureIdx(UserVO userVO, HashMap<String, Object> selectIdxqueryMap);

	public void deleteSHPFeature(UserVO userVO, HashMap<String, Object> deleteFeatureMap);

	public Integer selectSHPLayerCollectionIdx(UserVO userVO, HashMap<String, Object> queryMap);

	public Integer selectSHPLayerMetadataIdx(UserVO userVO, HashMap<String, Object> metadataIdxQuery);

	public void deleteSHPLayerMetadata(UserVO userVO, HashMap<String, Object> deleteLayerMetaQuery);

	public void dropSHPLayer(UserVO userVO, HashMap<String, Object> dropQuery);

	public void deleteSHPLayerCollection(UserVO userVO, HashMap<String, Object> deleteLayerCollectionQuery);

}
