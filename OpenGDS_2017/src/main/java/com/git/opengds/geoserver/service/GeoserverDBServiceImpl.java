package com.git.opengds.geoserver.service;

import java.util.HashMap;
import java.util.Map;

import com.git.gdsbuilder.FileRead.en.EnFileFormat;

public class GeoserverDBServiceImpl {

	public boolean selectEditLayerDuplicateCheck(EnFileFormat fileFormat, String fileName, String nativeLayerName, String currentLayerName){
		boolean dupFlag = true;
		Map<String, Object> parmasMap = new HashMap<String, Object>();
		if(fileFormat == EnFileFormat.DXF){
			parmasMap.put("tableName", "public.qa10_layer_metadata");
			parmasMap.put("fileName", fileName);
			parmasMap.put("nativeNameCol", "");
			parmasMap.put("nativeNameVal", nativeLayerName);
			parmasMap.put("currentNameCol", "");
			parmasMap.put("currentNameVal", currentLayerName);
		}
		else if(fileFormat == EnFileFormat.NGI){
			parmasMap.put("tableName", "");
		}
		else if(fileFormat == EnFileFormat.SHP){
			parmasMap.put("tableName", "");
		}
		else{
//			throw new IllegalArgumentException("null");
			return false;
		}
		
		return false;
	};
}
