package com.git.opengds.file.dxf.persistence;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.git.opengds.common.DataSourceFactory;
import com.git.opengds.user.domain.UserVO;

@Repository
public class DXFLayerCollectionDAOImpl extends DataSourceFactory implements DXFLayerCollectionDAO {

	private SqlSession sqlSession;

	private static final String namespace = "com.git.mappers.qa10Mappers.QA10LayerCollectionMapper";

	/*
	 * public QA10LayerCollectionDAOImpl(UserVO user) throws RuntimeException{
	 * // TODO Auto-generated constructor stub sqlSession =
	 * super.getSqlSession(user.getId()); }
	 */

	@Override
	public void createDXFLayerTb(UserVO userVO, HashMap<String, Object> qa10Layertb) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(namespace + ".createDXFLayerTb", qa10Layertb);
	}

	@Override
	public void insertDXFLayer(UserVO userVO, HashMap<String, Object> qa10Layer) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertDXFLayer", qa10Layer);
	}

	@Override
	public int insertDXFLayerCollection(UserVO userVO, HashMap<String, Object> insertCollectionQuery)
			throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertDXFLayerCollection", insertCollectionQuery);
		return (Integer) insertCollectionQuery.get("c_idx");
	}

	@Override
	public void insertDXFLayerMetadata(UserVO userVO, HashMap<String, Object> insertQueryMap) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertDXFLayerMetadata", insertQueryMap);
	}

	@Override
	public void insertDXFFeature(UserVO userVO, HashMap<String, Object> insertQuertMap) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertDXFFeature", insertQuertMap);
	}

	@Override
	public HashMap<String, Object> selectDXFFeatureIdx(UserVO userVO, HashMap<String, Object> selectIdxqueryMap)
			throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectOne(namespace + ".selectDXFFeatureIdx", selectIdxqueryMap);
	}

	@Override
	public int deleteDXFFeature(UserVO userVO, HashMap<String, Object> deleteQuery) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.delete(namespace + ".deleteDXFFeature", deleteQuery);
	}

	@Override
	public int insertDXFLayerCollectionBlocksCommon(UserVO userVO, HashMap<String, Object> blocksQuery)
			throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertDXFLayerCollectionBlockCommon", blocksQuery);
		return (Integer) blocksQuery.get("bc_idx");
	}

	@Override
	public void insertDXFLayercollectionBlockEntity(UserVO userVO, HashMap<String, Object> entitiesQuery)
			throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertDXFLayerCollectionBlockEntity", entitiesQuery);
	}

	@Override
	public int insertDXFLayerCollectionTableCommon(UserVO userVO, HashMap<String, Object> tablesQuery)
			throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertDXFLayerCollectionTableCommon", tablesQuery);
		return (Integer) tablesQuery.get("tc_idx");
	}

	@Override
	public void insertDXFLayerCollectionTableLayers(UserVO userVO, HashMap<String, Object> layersQuery)
			throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertDXFLayerCollectionTableLayer", layersQuery);
	}

	@Override
	public Integer selectDXFLayerCollectionIdx(UserVO userVO, HashMap<String, Object> selectLayerCollectionIdxQuery)
			throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> idxMap = sqlSession.selectOne(namespace + ".selectDXFLayerCollectionIdx",
				selectLayerCollectionIdxQuery);
		return (Integer) idxMap.get("c_idx");
	}

	@Override
	public List<HashMap<String, Object>> selectDXFLayerMetadataIdxs(UserVO userVO,
			HashMap<String, Object> metadataIdxQuery) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectList(namespace + ".selectDXFLayerMetadataIdxs", metadataIdxQuery);
	}

	@Override
	public HashMap<String, Object> selectDXFLayerTableName(UserVO userVO, HashMap<String, Object> layerTbNameQuery)
			throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectOne(namespace + ".selectDXFLayerTableName", layerTbNameQuery);
	}

	@Override
	public int dropDXFLayer(UserVO userVO, HashMap<String, Object> dropLayerTbQuery) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.update(namespace + ".dropDXFLayer", dropLayerTbQuery);
	}

	@Override
	public int deleteField(UserVO userVO, HashMap<String, Object> deleteFieldQuery) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.delete(namespace + ".deleteField", deleteFieldQuery);
	}

	@Override
	public Integer selectTableCommonIdx(UserVO userVO, HashMap<String, Object> tableIdxQuery) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> idxMap = sqlSession.selectOne(namespace + ".selectTableCommonIdx", tableIdxQuery);
		if (idxMap == null) {
			return null;
		} else {
			return (Integer) idxMap.get("tc_idx");
		}
	}

	@Override
	public Integer selectBlockCommonIdx(UserVO userVO, HashMap<String, Object> blockIdxQuery) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> idxMap = sqlSession.selectOne(namespace + ".selectBlockCommonIdx", blockIdxQuery);
		if (idxMap != null) {
			return (Integer) idxMap.get("bc_idx");
		} else {
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> selectBlockCommonIdxs(UserVO userVO, HashMap<String, Object> blockIdxQuery)
			throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectList(namespace + ".selectBlockCommonIdxs", blockIdxQuery);
	}

	@Override
	public int selectDXFLayerMetadataIdx(UserVO userVO, HashMap<String, Object> metadataIdxQuery)
			throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> idxMap = sqlSession.selectOne(namespace + ".selectDXFLayerMetadataIdx",
				metadataIdxQuery);
		return (Integer) idxMap.get("lm_idx");
	}

	@Override
	public void updateDXFLayerMetadataLayerID(UserVO userVO, HashMap<String, Object> updateLayerNameQuery)
			throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(namespace + ".updateDXFLayerMetadataLayerID", updateLayerNameQuery);
	}

	@Override
	public int selectTableLayerIdx(UserVO userVO, HashMap<String, Object> selectTlIdxQuery) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> idxMap = sqlSession.selectOne(namespace + ".selectTableLayerIdx", selectTlIdxQuery);
		return (Integer) idxMap.get("tl_idx");
	}

	@Override
	public void updateTableLayerId(UserVO userVO, HashMap<String, Object> updateTlIdQuery) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(namespace + ".updateTableLayerID", updateTlIdQuery);
	}

	@Override
	public HashMap<String, Object> selectDXFLayerMeata(UserVO userVO, HashMap<String, Object> selectMetaQuery)
			throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectOne(namespace + ".selectDXFLayerMeata", selectMetaQuery);
	}

	@Override
	public List<HashMap<String, Object>> selectDXFFeatures(UserVO userVO, HashMap<String, Object> selectFeaturesQuery)
			throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectList(namespace + ".selectDXFFeatures", selectFeaturesQuery);
	}

	@Override
	public HashMap<String, Object> selectTablesCommon(UserVO userVO, HashMap<String, Object> selectTablesCommonsQuery)
			throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectOne(namespace + ".selectTablesCommon", selectTablesCommonsQuery);
	}

	@Override
	public List<HashMap<String, Object>> selectTablesLayer(UserVO userVO,
			HashMap<String, Object> selectTablesLayerQuery) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectList(namespace + ".selectTablesLayer", selectTablesLayerQuery);
	}

	@Override
	public int insertDXFLayercollectionBlockPolyline(UserVO userVO, HashMap<String, Object> polylineQuery)
			throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertDXFLayercollectionBlockPolyline", polylineQuery);
		return (Integer) polylineQuery.get("bp_idx");
	}

	@Override
	public void insertDXFLayercollectionBlockVertex(UserVO userVO, HashMap<String, Object> vertextInsertQuery)
			throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertDXFLayercollectionBlockVertex", vertextInsertQuery);
	}

	@Override
	public HashMap<String, Object> selectDXFlayerBlocksCommon(UserVO userVO,
			HashMap<String, Object> selectBlockCommonQuery) throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectOne(namespace + ".selectDXFlayerBlocksCommon", selectBlockCommonQuery);
	}

	@Override
	public List<HashMap<String, Object>> selectBlockEntities(UserVO userVO, HashMap<String, Object> selectBlockArcList)
			throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		List<HashMap<String, Object>> selectList = sqlSession.selectList(namespace + ".selectBlockEntities",
				selectBlockArcList);
		if (selectList.size() > 0) {
			return selectList;
		} else {
			return null;
		}
	}

	@Override
	public int insertDXFLayercollectionBlockLWPolyline(UserVO userVO, HashMap<String, Object> polylineQuery)
			throws RuntimeException {
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertDXFLayercollectionBlockLWPolyline", polylineQuery);
		return (Integer) polylineQuery.get("blp_idx");
	}
}
