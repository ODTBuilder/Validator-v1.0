package com.git.opengds.file.dxf.persistence;

import java.util.HashMap;

import org.postgresql.util.PSQLException;

public interface QA10LayerCollectionDAO {

	public void createQA10LayerTb(HashMap<String, Object> qa20Layertb) throws PSQLException;

	public void insertQA10Layer(HashMap<String, Object> qa10Layer);

	public int insertQA10LayerCollection(HashMap<String, Object> insertCollectionQuery);

	public void insertQA10LayerCollectionHeader(HashMap<String, Object> insertHeaderQuery);

	public int insertQA10LayerCollectionTables(HashMap<String, Object> tablesQuery);

	public void insertQA10LayerCollectionLineTypes(HashMap<String, Object> lineTypesQuery);

	public void insertQA10LayerCollectionLayers(HashMap<String, Object> layersQuery);

	public void insertQA10LayerCollecionStyles(HashMap<String, Object> stylesQuery);

	public void insertQA10LayerMetadata(HashMap<String, Object> insertQueryMap);

	public void insertQA10Feature(HashMap<String, Object> insertQuertMap);

	public HashMap<String, Object> selectQA10FeatureIdx(HashMap<String, Object> selectIdxqueryMap);

	public int deleteQA10Feature(HashMap<String, Object> deleteFeatureMap);

}
