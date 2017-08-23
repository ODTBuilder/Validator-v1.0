package com.git.opengds.file.shp.persistence;

import java.util.HashMap;

import com.git.opengds.user.domain.UserVO;

public interface SHPLayerCollectionDAO {

	public int insertSHPLayerCollection(UserVO userVO, HashMap<String, Object> insertCollectionQuery);

	public int createSHPLayerTb(UserVO userVO, HashMap<String, Object> createLayerQuery);

	public int insertSHPLayer(UserVO userVO, HashMap<String, Object> insertLayerQuery);

	public int insertSHPLayerMetadata(UserVO userVO, HashMap<String, Object> insertLayerMeteQuery);

	public int selectSHPFeatureIdx(UserVO userVO, HashMap<String, Object> selectIdxqueryMap);

	public int deleteSHPFeature(UserVO userVO, HashMap<String, Object> deleteFeatureMap);

	public Integer selectSHPLayerCollectionIdx(UserVO userVO, HashMap<String, Object> queryMap);

	public Integer selectSHPLayerMetadataIdx(UserVO userVO, HashMap<String, Object> metadataIdxQuery);

	public int deleteSHPLayerMetadata(UserVO userVO, HashMap<String, Object> deleteLayerMetaQuery);

	public int dropSHPLayer(UserVO userVO, HashMap<String, Object> dropQuery);

	public int deleteSHPLayerCollection(UserVO userVO, HashMap<String, Object> deleteLayerCollectionQuery);

}
