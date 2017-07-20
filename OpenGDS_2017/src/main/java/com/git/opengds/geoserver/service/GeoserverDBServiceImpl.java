package com.git.opengds.geoserver.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.git.gdsbuilder.FileRead.en.EnFileFormat;
import com.git.opengds.geoserver.persistence.GeoserverDAO;
import com.git.opengds.geoserver.persistence.GeoserverDAOImpl;
import com.git.opengds.user.domain.UserVO;

@Service
public class GeoserverDBServiceImpl implements GeoserverDBService{

	@Inject
	private GeoserverDAO geoserverDAO;
	
	/*public GeoserverDBServiceImpl(UserVO userVO) {
		// TODO Auto-generated constructor stub
		geoserverDAO = new GeoserverDAOImpl(userVO);
	}
	*/
	@Override
	public boolean selectEditLayerDuplicateCheck(UserVO userVO, EnFileFormat fileFormat, String fileName, String layerName){
		boolean dupFlag = true;
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		if(fileFormat == EnFileFormat.DXF){
			paramsMap.put("fileCol", "collection_name");
			paramsMap.put("colTableName", "public.qa10_layercollection");
			paramsMap.put("metaTableName", "public.qa10_layer_metadata");
			paramsMap.put("fileName", fileName);
			paramsMap.put("nativeNameCol", "current_id");
			paramsMap.put("currentNameCol", "current_layer_id");
			paramsMap.put("layerName", layerName);
		}
		else if(fileFormat == EnFileFormat.NGI){
			paramsMap.put("fileCol", "file_name");
			paramsMap.put("colTableName", "public.qa20_layercollection");
			paramsMap.put("metaTableName", "public.qa20_layer_metadata");
			paramsMap.put("fileName", fileName);
			paramsMap.put("nativeNameCol", "layer_name");
			paramsMap.put("currentNameCol", "current_layer_name");
			paramsMap.put("layerName", layerName);
		}
		else if(fileFormat == EnFileFormat.SHP){
			/*parmasMap.put("tableName", "public.qa10_layer_metadata");
			parmasMap.put("tableName", "public.qa10_layer_metadata");
			parmasMap.put("fileName", fileName);
			parmasMap.put("nativeNameCol", "layer_id");
			parmasMap.put("currentNameCol", "current_layer_name");
			parmasMap.put("layerName", layerName);*/
		}
		else{
//			throw new IllegalArgumentException("null");
			return false;
		}
		
		dupFlag = geoserverDAO.selectEditLayerDuplicateCheck(userVO, paramsMap);
		
		return dupFlag;
	};
}
