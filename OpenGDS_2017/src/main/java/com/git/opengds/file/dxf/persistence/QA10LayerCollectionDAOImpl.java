package com.git.opengds.file.dxf.persistence;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;

import com.git.opengds.common.DataSourceFactory;
import com.git.opengds.user.domain.UserVO;

@Repository
public class QA10LayerCollectionDAOImpl extends DataSourceFactory implements QA10LayerCollectionDAO{

	private SqlSession sqlSession;

	private static final String namespace = "com.git.mappers.qa10Mappers.QA10LayerCollectionMapper";

/*	public QA10LayerCollectionDAOImpl(UserVO user) throws RuntimeException{
		// TODO Auto-generated constructor stub
		sqlSession = super.getSqlSession(user.getId());
	}*/
	
	@Override
	public void createQA10LayerTb(UserVO userVO, HashMap<String, Object> qa10Layertb) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(namespace + ".createQA10LayerTb", qa10Layertb);
	}

	@Override
	public void insertQA10Layer(UserVO userVO, HashMap<String, Object> qa10Layer)throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertQA10Layer", qa10Layer);
	}

	@Override
	public int insertQA10LayerCollection(UserVO userVO, HashMap<String, Object> insertCollectionQuery)throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertQA10LayerCollection", insertCollectionQuery);
		return (Integer) insertCollectionQuery.get("c_idx");
	}

	@Override
	public void insertQA10LayerMetadata(UserVO userVO, HashMap<String, Object> insertQueryMap)throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertQA10LayerMetadata", insertQueryMap);
	}

	@Override
	public void insertQA10Feature(UserVO userVO, HashMap<String, Object> insertQuertMap)throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertQA10Feature", insertQuertMap);
	}

	@Override
	public HashMap<String, Object> selectQA10FeatureIdx(UserVO userVO, HashMap<String, Object> selectIdxqueryMap)throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectOne(namespace + ".selectFeatureIdx", selectIdxqueryMap);
	}

	@Override
	public int deleteQA10Feature(UserVO userVO, HashMap<String, Object> deleteQuery)throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.delete(namespace + ".deleteFeature", deleteQuery);
	}

	@Override
	public int insertQA10LayerCollectionBlocksCommon(UserVO userVO, HashMap<String, Object> blocksQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertQA10LayerCollectionBlockCommon", blocksQuery);
		return (Integer) blocksQuery.get("bc_idx");
	}

	@Override
	public void insertQA10LayercollectionBlockEntity(UserVO userVO, HashMap<String, Object> entitiesQuery)throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertQA10LayerCollectionBlockEntity", entitiesQuery);
	}

	@Override
	public int insertQA10LayerCollectionTableCommon(UserVO userVO, HashMap<String, Object> tablesQuery)throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertQA10LayerCollectionTableCommon", tablesQuery);
		return (Integer) tablesQuery.get("tc_idx");
	}

	@Override
	public void insertQA10LayerCollectionTableLayers(UserVO userVO, HashMap<String, Object> layersQuery)throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertQA10LayerCollectionTableLayer", layersQuery);
	}

	@Override
	public Integer selectQA10LayerCollectionIdx(UserVO userVO, HashMap<String, Object> selectLayerCollectionIdxQuery)throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> idxMap = sqlSession.selectOne(namespace + ".selectLayerCollectionIdx",
				selectLayerCollectionIdxQuery);
		return (Integer) idxMap.get("c_idx");
	}

	@Override
	public List<HashMap<String, Object>> selectQA10LayerMetadataIdxs(UserVO userVO, HashMap<String, Object> metadataIdxQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectList(namespace + ".selectQA10LayerMetadataIdxs", metadataIdxQuery);
	}

	@Override
	public HashMap<String, Object> selectQA10LayerTableName(UserVO userVO, HashMap<String, Object> layerTbNameQuery)throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectOne(namespace + ".selectQA10LayerTableName", layerTbNameQuery);
	}

	@Override
	public int dropLayer(UserVO userVO, HashMap<String, Object> dropLayerTbQuery)throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.update(namespace + ".dropQA10Layer", dropLayerTbQuery);
	}

	@Override
	public void deleteField(UserVO userVO, HashMap<String, Object> deleteFieldQuery)throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.delete(namespace + ".deleteField", deleteFieldQuery);
	}

	@Override
	public Integer selectTableCommonIdx(UserVO userVO, HashMap<String, Object> tableIdxQuery)throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> idxMap = sqlSession.selectOne(namespace + ".selectTableCommonIdx", tableIdxQuery);
		return (Integer) idxMap.get("tc_idx");
	}

	@Override
	public Integer selectBlockCommonIdx(UserVO userVO, HashMap<String, Object> blockIdxQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> idxMap = sqlSession.selectOne(namespace + ".selectBlockCommonIdx", blockIdxQuery);
		if (idxMap != null){
			return (Integer) idxMap.get("bc_idx");
		} else{
			return null;
		}
	}

	@Override
	public List<HashMap<String, Object>> selectBlockCommonIdxs(UserVO userVO, HashMap<String, Object> blockIdxQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectList(namespace + ".selectBlockCommonIdxs", blockIdxQuery);
	}

	@Override
	public int selectQA10LayerMetadataIdx(UserVO userVO, HashMap<String, Object> metadataIdxQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> idxMap = sqlSession.selectOne(namespace + ".selectQA10LayerMetadataIdx",
				metadataIdxQuery);
		return (Integer) idxMap.get("lm_idx");
	}

	@Override
	public void updateQA10LayerMetadataLayerID(UserVO userVO, HashMap<String, Object> updateLayerNameQuery)throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(namespace + ".updateQA10LayerMetadataLayerID", updateLayerNameQuery);
	}

	@Override
	public int selectTableLayerIdx(UserVO userVO, HashMap<String, Object> selectTlIdxQuery)throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		HashMap<String, Object> idxMap = sqlSession.selectOne(namespace + ".selectTableLayerIdx", selectTlIdxQuery);
		return (Integer) idxMap.get("tl_idx");
	}

	@Override
	public void updateTableLayerId(UserVO userVO, HashMap<String, Object> updateTlIdQuery)throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.update(namespace + ".updateTableLayerID", updateTlIdQuery);
	}

	@Override
	public HashMap<String, Object> selectQA10LayerMeata(UserVO userVO, HashMap<String, Object> selectMetaQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectOne(namespace + ".selectQA10LayerMeata", selectMetaQuery);
	}

	@Override
	public List<HashMap<String, Object>> selectQA10Features(UserVO userVO, HashMap<String, Object> selectFeaturesQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectList(namespace + ".selectQA10Features", selectFeaturesQuery);
	}

	@Override
	public HashMap<String, Object> selectTablesCommon(UserVO userVO, HashMap<String, Object> selectTablesCommonsQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectOne(namespace + ".selectTablesCommon", selectTablesCommonsQuery);
	}

	@Override
	public List<HashMap<String, Object>> selectTablesLayer(UserVO userVO, HashMap<String, Object> selectTablesLayerQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectList(namespace + ".selectTablesLayer", selectTablesLayerQuery);
	}

	@Override
	public int insertQA10LayercollectionBlockPolyline(UserVO userVO, HashMap<String, Object> polylineQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertQA10LayercollectionBlockPolyline", polylineQuery);
		return (Integer) polylineQuery.get("bp_idx");
	}

	@Override
	public void insertQA10LayercollectionBlockVertex(UserVO userVO, HashMap<String, Object> vertextInsertQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertQA10LayercollectionBlockVertex", vertextInsertQuery);
	}

	@Override
	public HashMap<String, Object> selectQA10layerBlocksCommon(UserVO userVO, HashMap<String, Object> selectBlockCommonQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		return sqlSession.selectOne(namespace + ".selectQA10layerBlocksCommon", selectBlockCommonQuery);
	}

	@Override
	public List<HashMap<String, Object>> selectBlockEntities(UserVO userVO, HashMap<String, Object> selectBlockArcList) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		List<HashMap<String, Object>> selectList = sqlSession.selectList(namespace + ".selectBlockEntities",
				selectBlockArcList);
		if (selectList.size() > 0){
			return selectList;
		} else{
			return null;
		}
	}

	@Override
	public int insertQA10LayercollectionBlockLWPolyline(UserVO userVO, HashMap<String, Object> polylineQuery) throws RuntimeException{
		sqlSession = super.getSqlSession(userVO.getId());
		sqlSession.insert(namespace + ".insertQA10LayercollectionBlockLWPolyline", polylineQuery);
		return (Integer) polylineQuery.get("blp_idx");
	}
}
