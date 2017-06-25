package com.git.opengds.geoserver.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.git.gdsbuilder.FileRead.en.EnFileFormat;

@Service
public class GeoserverDBServiceImpl implements GeoserverDBService{

	/*@Autowired
	private Geoserver*/
	
	@Override
	public boolean selectEditLayerDuplicateCheck(EnFileFormat fileFormat, String fileName, String layerName){
		boolean dupFlag = true;
		Map<String, Object> parmasMap = new HashMap<String, Object>();
		if(fileFormat == EnFileFormat.DXF){
			parmasMap.put("fileCol", "collection_name");
			parmasMap.put("colTableName", "public.qa10_layercollection");
			parmasMap.put("metaTableName", "public.qa10_layer_metadata");
			parmasMap.put("fileName", fileName);
			parmasMap.put("nativeNameCol", "layer_id");
			parmasMap.put("currentNameCol", "current_layer_name");
			parmasMap.put("layerName", layerName);
		}
		else if(fileFormat == EnFileFormat.NGI){
			parmasMap.put("fileCol", "file_name");
			parmasMap.put("colTableName", "public.qa20_layercollection");
			parmasMap.put("metaTableName", "public.qa20_layer_metadata");
			parmasMap.put("fileName", fileName);
			parmasMap.put("nativeNameCol", "layer_name");
			parmasMap.put("currentNameCol", "current_layer_name");
			parmasMap.put("layerName", layerName);
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
		
		
		return false;
	};
}
