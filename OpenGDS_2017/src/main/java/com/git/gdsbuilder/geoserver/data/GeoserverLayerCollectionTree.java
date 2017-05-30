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

/*
 *  GeoServer-Manager - Simple Manager Library for GeoServer
 *  
 *  Copyright (C) 2007,2011 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.git.gdsbuilder.geoserver.data;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.git.gdsbuilder.geosolutions.geoserver.rest.decoder.RESTFeatureTypeList;

/**
 * @ClassName: GeoserverLayerCollectionTree
 * @Description: Tree 형태의 GeoserverLayerCollection
 * @author JY.Kim
 * @date 2017. 4. 10. 오후 3:59:15
 */
@SuppressWarnings("serial")
public class GeoserverLayerCollectionTree extends JSONArray {

	/**
	 * 생성자 생성
	 * 
	 * @param reader
	 *            - Geosolution Format
	 */
	public GeoserverLayerCollectionTree(RESTFeatureTypeList featureTypeList) {
		build(featureTypeList);
	}

	/**
	 * Tree형태의 GeoserverLayerCollection json 객체들
	 * 
	 * @author JY.Kim
	 * @Date 2017. 4. 10. 오후 4:24:45
	 * @param reader
	 *            - Geosolution Format
	 * @return GeoserverLayerCollectionTree - Tree형태의 GeoserverLayerCollection
	 *         json 객체들
	 */
	@SuppressWarnings("unchecked")
	public GeoserverLayerCollectionTree build(RESTFeatureTypeList featureTypeList) {
		if (featureTypeList == null) {
			throw new IllegalArgumentException("RESTFeatureTypeList may not be null");
		}
		JSONObject geoserverLayers = new JSONObject();
		JSONObject validatorLayers = new JSONObject();
		JSONObject generalizationLayers = new JSONObject();
		JSONObject ngiLayers = new JSONObject();
		JSONObject dxfLayers = new JSONObject();
		JSONObject shpLayers = new JSONObject();

		geoserverLayers.put("id", "geoLayers");
		geoserverLayers.put("parent", "#");
		geoserverLayers.put("text", "GeoserverLayers");
		geoserverLayers.put("type", "normal");

		ngiLayers.put("id", "ngi");
		ngiLayers.put("parent", "geoLayers");
		ngiLayers.put("text", "NGI");
		ngiLayers.put("type", "ngi");

		dxfLayers.put("id", "dxf");
		dxfLayers.put("parent", "geoLayers");
		dxfLayers.put("text", "DXF");
		dxfLayers.put("type", "dxf");

		shpLayers.put("id", "shp");
		shpLayers.put("parent", "geoLayers");
		shpLayers.put("text", "SHP");
		shpLayers.put("type", "shp");

		validatorLayers.put("id", "valLayers");
		validatorLayers.put("parent", "#");
		validatorLayers.put("text", "ValidatorLayers");
		validatorLayers.put("type", "error");

		generalizationLayers.put("id", "genLayers");
		generalizationLayers.put("parent", "#");
		generalizationLayers.put("text", "GeneralizationLayers");
		generalizationLayers.put("type", "generalization");

		super.add(geoserverLayers);
		super.add(ngiLayers);
		super.add(dxfLayers);
		super.add(shpLayers);
		super.add(validatorLayers);
		super.add(generalizationLayers);

		if (featureTypeList.size() > 1) {
			List<String> layerNames = new ArrayList<String>(); // 레이어 이름 리스트
			layerNames = featureTypeList.getNames();
			List<String> fileNames = new ArrayList<String>(); // 파일명 리스트(서로 중복된
			// 파일명 없음)

			for (String layerName : layerNames) {
				int con = layerName.indexOf("_");
				if (con != -1) {

					String preName = layerName.substring(0, con); // 구분코드
					String cutLayerName = layerName.substring(con+1);
					
					// or
					// 레이어명(구분코드가
					// gen일 경우)

					JSONObject layerJson = new JSONObject();
					if (preName.equals("err")) { // 파일명이 eg 또는 el인 경우 -
						// validatorLayer
						layerJson.put("id", layerName);
						layerJson.put("parent", "valLayers");
						layerJson.put("text", cutLayerName);
						layerJson.put("type", "e_layer");
						super.add(layerJson);
					} else if (preName.equals("gen")) { // 파일명이 gen인 경우
						layerJson.put("id", layerName);
						layerJson.put("parent", "genLayers");
						layerJson.put("text", layerName);
						layerJson.put("type", "g_layer");
						super.add(layerJson);
					} else {
						
						int dash = cutLayerName.indexOf("_");
						String fileType = cutLayerName.substring(0, dash);
						String lastName = cutLayerName.substring(dash+1); // 파일명_레이어명
						int div = lastName.indexOf("_");
						String fileName = lastName.substring(0, div);
						String lastLayerName = lastName.substring(div + 1);
						String codeFileName = preName +"_"+ fileType +"_" + fileName;
						if (fileNames.contains(fileName)) {
							if(fileType.equals("ngi")){
								layerJson.put("id", layerName);
								layerJson.put("parent", codeFileName);
								layerJson.put("text", lastLayerName);
								layerJson.put("type", "n_"+fileType+"_layer");
								super.add(layerJson);
							}else if(fileType.equals("dxf")){
								layerJson.put("id", layerName);
								layerJson.put("parent", codeFileName);
								layerJson.put("text", lastLayerName);
								layerJson.put("type", "n_"+fileType+"_layer");
								super.add(layerJson);
							}else if(fileType.equals("shp")){
								layerJson.put("id", layerName);
								layerJson.put("parent", codeFileName);
								layerJson.put("text", lastLayerName);
								layerJson.put("type", "n_"+fileType+"_layer");
								super.add(layerJson);
							}
						} else {
							JSONObject fileNameJson = new JSONObject();
							if(fileType.equals("ngi")){
								fileNames.add(fileName);
								fileNameJson.put("id", codeFileName);
								fileNameJson.put("parent", "ngi");
								fileNameJson.put("text", fileName);
								fileNameJson.put("type", "n_"+fileType+"_group");
								layerJson.put("id", layerName);
								layerJson.put("parent", codeFileName);
								layerJson.put("text", lastLayerName);
								layerJson.put("type", "n_"+fileType+"_layer");
								super.add(fileNameJson);
								super.add(layerJson);
							}else if(fileType.equals("dxf")){
								fileNames.add(fileName);
								fileNameJson.put("id", codeFileName);
								fileNameJson.put("parent", "dxf");
								fileNameJson.put("text", fileName);
								fileNameJson.put("type", "n_"+fileType+"_group");
								layerJson.put("id", layerName);
								layerJson.put("parent", codeFileName);
								layerJson.put("text", lastLayerName);
								layerJson.put("type", "n_"+fileType+"_layer");
								super.add(fileNameJson);
								super.add(layerJson);
							}else if(fileType.equals("shp")){
								fileNames.add(fileName);
								fileNameJson.put("id", codeFileName);
								fileNameJson.put("parent", "shp");
								fileNameJson.put("text", fileName);
								fileNameJson.put("type", "n_"+fileType+"_group");
								layerJson.put("id", layerName);
								layerJson.put("parent", codeFileName);
								layerJson.put("text", lastLayerName);
								layerJson.put("type", "n_"+fileType+"_layer");
								super.add(fileNameJson);
								super.add(layerJson);
							}
						}
					}
				}
			}
		}
		return this;
	}
}
