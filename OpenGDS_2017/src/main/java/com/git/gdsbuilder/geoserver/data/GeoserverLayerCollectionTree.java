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

import com.git.gdsbuilder.FileRead.en.EnFileFormat;
import com.git.gdsbuilder.geosolutions.geoserver.rest.decoder.RESTFeatureTypeList;

/**
 * @ClassName: GeoserverLayerCollectionTree
 * @Description: Tree 형태의 GeoserverLayerCollection
 * @author JY.Kim
 * @date 2017. 4. 10. 오후 3:59:15
 */
@SuppressWarnings("serial")
public class GeoserverLayerCollectionTree extends JSONArray {

	public enum TreeType {
		ALL("all"),
		QA10("qa1.0"), 
		QA20("qa2.0"), 
		SHP("shp");


		String layerType;

		TreeType(String layerType) {
			this.layerType = layerType;
		}
		
		public String getTreeType() {
			return layerType;
		}

		public void setTreeType(String layerType) {
			this.layerType = layerType;
		}	};

	/**
	 * 생성자 생성
	 * 
	 * @param reader
	 *            - Geosolution Format
	 */
	public GeoserverLayerCollectionTree(RESTFeatureTypeList featureTypeList, TreeType treeType) {
		if(treeType!=null){
			if(treeType == TreeType.ALL){
				build(featureTypeList);
			}else{
				build(featureTypeList,treeType);
			}
		}
	}

	/**
	 * TreeType이 All인 Tree형태의 GeoserverLayerCollection json 객체들
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
		JSONObject geoserverLayers = new JSONObject();
		JSONObject validatorLayers = new JSONObject();
		JSONObject generalizationLayers = new JSONObject();
		JSONObject ngiGeoLayers = new JSONObject();
		JSONObject dxfGeoLayers = new JSONObject();
		JSONObject shpGeoLayers = new JSONObject();
		JSONObject ngiValLayers = new JSONObject();
		JSONObject dxfValLayers = new JSONObject();
		JSONObject shpValLayers = new JSONObject();
		JSONObject ngiGenLayers = new JSONObject();
		JSONObject dxfGenLayers = new JSONObject();
		JSONObject shpGenLayers = new JSONObject();

		geoserverLayers.put("id", "geoLayers");
		geoserverLayers.put("parent", "#");
		geoserverLayers.put("text", "GeoserverLayers");
		geoserverLayers.put("type", "normal");

		ngiGeoLayers.put("id", "n_ngi");
		ngiGeoLayers.put("parent", "geoLayers");
		ngiGeoLayers.put("text", "NGI");
		ngiGeoLayers.put("type", "n_ngi");

		dxfGeoLayers.put("id", "n_dxf");
		dxfGeoLayers.put("parent", "geoLayers");
		dxfGeoLayers.put("text", "DXF");
		dxfGeoLayers.put("type", "n_dxf");

		shpGeoLayers.put("id", "n_shp");
		shpGeoLayers.put("parent", "geoLayers");
		shpGeoLayers.put("text", "SHP");
		shpGeoLayers.put("type", "n_shp");

		validatorLayers.put("id", "valLayers");
		validatorLayers.put("parent", "#");
		validatorLayers.put("text", "ValidatorLayers");
		validatorLayers.put("type", "error");

		ngiValLayers.put("id", "e_ngi");
		ngiValLayers.put("parent", "valLayers");
		ngiValLayers.put("text", "NGI");
		ngiValLayers.put("type", "e_ngi");

		dxfValLayers.put("id", "e_dxf");
		dxfValLayers.put("parent", "valLayers");
		dxfValLayers.put("text", "DXF");
		dxfValLayers.put("type", "e_dxf");

		shpValLayers.put("id", "e_shp");
		shpValLayers.put("parent", "valLayers");
		shpValLayers.put("text", "SHP");
		shpValLayers.put("type", "e_shp");

		generalizationLayers.put("id", "genLayers");
		generalizationLayers.put("parent", "#");
		generalizationLayers.put("text", "GeneralizationLayers");
		generalizationLayers.put("type", "generalization");

		ngiGenLayers.put("id", "g_ngi");
		ngiGenLayers.put("parent", "genLayers");
		ngiGenLayers.put("text", "NGI");
		ngiGenLayers.put("type", "g_ngi");

		dxfGenLayers.put("id", "g_dxf");
		dxfGenLayers.put("parent", "genLayers");
		dxfGenLayers.put("text", "DXF");
		dxfGenLayers.put("type", "g_dxf");

		shpGenLayers.put("id", "g_shp");
		shpGenLayers.put("parent", "genLayers");
		shpGenLayers.put("text", "SHP");
		shpGenLayers.put("type", "g_shp");

		super.add(geoserverLayers);
		super.add(ngiGeoLayers);
		super.add(dxfGeoLayers);
		super.add(shpGeoLayers);
		super.add(validatorLayers);
		super.add(ngiValLayers);
		super.add(dxfValLayers);
		super.add(shpValLayers);
		super.add(generalizationLayers);
		super.add(ngiGenLayers);
		super.add(dxfGenLayers);
		super.add(shpGenLayers);

		if (featureTypeList == null) {
			return this;
		}

		if (featureTypeList.size() > 1) {
			List<String> layerNames = new ArrayList<String>(); // 레이어 이름 리스트
			layerNames = featureTypeList.getNames();
			List<String> ngiFileNames = new ArrayList<String>(); // 파일명 리스트(서로
																	// 중복된 파일명
																	// 없음)
			List<String> shpFileNames = new ArrayList<String>(); // 파일명 리스트(서로
																	// 중복된 파일명
																	// 없음)
			List<String> dxfFileNames = new ArrayList<String>(); // 파일명 리스트(서로
																	// 중복된 파일명
																	// 없음)

			for (String layerName : layerNames) {
				int con = layerName.indexOf("_");
				if (con != -1) {

					String preName = layerName.substring(0, con); // 구분코드
					String cutLayerName = layerName.substring(con + 1);

					// or
					// 레이어명(구분코드가
					// gen일 경우)

					JSONObject layerJson = new JSONObject();
					if (preName.equals("err")) { // 파일명이 eg 또는 el인 경우 -

						int dash = cutLayerName.indexOf("_");

						String fileType = cutLayerName.substring(0, dash);
						String lastName = cutLayerName.substring(dash + 1); // 파일명_레이어명

						if (fileType.toLowerCase().equals("ngi")) {
							layerJson.put("id", layerName);
							layerJson.put("parent", "e_ngi");
							layerJson.put("text", lastName);
							layerJson.put("type", "e_ngi_layer");
						} else if (fileType.toLowerCase().equals("dxf")) {
							layerJson.put("id", layerName);
							layerJson.put("parent", "e_dxf");
							layerJson.put("text", lastName);
							layerJson.put("type", "e_dxf_layer");
						} else if (fileType.toLowerCase().equals("shp")) {
							layerJson.put("id", layerName);
							layerJson.put("parent", "e_shp");
							layerJson.put("text", lastName);
							layerJson.put("type", "e_shp_layer");
						}
						super.add(layerJson);
					} else if (preName.equals("gen")) { // 파일명이 gen인 경우
						int dash = cutLayerName.indexOf("_");
						String fileType = cutLayerName.substring(0, dash);
						String lastName = cutLayerName.substring(dash + 1); // 파일명_레이어명

						if (fileType.toLowerCase().equals("ngi")) {
							layerJson.put("id", layerName);
							layerJson.put("parent", "g_ngi");
							layerJson.put("text", lastName);
							layerJson.put("type", "g_layer_ngi");
						} else if (fileType.toLowerCase().equals("dxf")) {
							layerJson.put("id", layerName);
							layerJson.put("parent", "g_dxf");
							layerJson.put("text", lastName);
							layerJson.put("type", "g_layer_dxf");
						} else if (fileType.toLowerCase().equals("shp")) {
							layerJson.put("id", layerName);
							layerJson.put("parent", "g_shp");
							layerJson.put("text", lastName);
							layerJson.put("type", "g_layer_shp");
						}
						super.add(layerJson);
					} else {
						int dash = cutLayerName.indexOf("_");
						String fileType = cutLayerName.substring(0, dash);
						if (fileType.equals(EnFileFormat.DXF.getStateName())
								|| fileType.equals(EnFileFormat.NGI.getStateName())
								|| fileType.equals(EnFileFormat.SHP.getStateName())) {

							String lastName = cutLayerName.substring(dash + 1); // 파일명_레이어명
							int div = lastName.indexOf("_");
							String fileName = lastName.substring(0, div);
							String lastLayerName = lastName.substring(div + 1);

							int layerTypeDash = lastLayerName.lastIndexOf("_");
							String exTypelayerName = lastLayerName.substring(0, layerTypeDash);
							String layerType = lastLayerName.substring(layerTypeDash + 1);
							String suLayerType = "";

							String groupName = "gro_" + fileType + "_" + fileName;

							JSONObject fileNameJson = new JSONObject();
							if (fileType.equals("ngi")) {
								if (ngiFileNames.contains(fileName)) {
									if (layerType.equals("POINT")) {
										suLayerType = "pt";
									} else if (layerType.equals("LINESTRING")) {
										suLayerType = "ln";
									} else if (layerType.equals("POLYGON")) {
										suLayerType = "pg";
									} else if (layerType.equals("TEXT")) {
										suLayerType = "txt";
									} else if (layerType.equals("MULTIPOINT")) {
										suLayerType = "mpt";
									} else if (layerType.equals("MULTILINESTRING")) {
										suLayerType = "mln";
									} else if (layerType.equals("MULTIPOLYGON")) {
										suLayerType = "mpg";
									} else if (layerType.equals("TEXT")) {
										suLayerType = "txt";
									} else {
										suLayerType = "defalut";
										layerJson.put("type", suLayerType);
									}

									layerJson.put("id", layerName);
									layerJson.put("parent", groupName);
									layerJson.put("text", exTypelayerName);
									if (!suLayerType.equals("defalut")) {
										layerJson.put("type", "n_" + fileType + "_layer_" + suLayerType);
									}
									super.add(layerJson);
								} else {
									if (layerType.equals("POINT")) {
										suLayerType = "pt";
									} else if (layerType.equals("LINESTRING")) {
										suLayerType = "ln";
									} else if (layerType.equals("POLYGON")) {
										suLayerType = "pg";
									} else if (layerType.equals("TEXT")) {
										suLayerType = "txt";
									} else if (layerType.equals("MULTIPOINT")) {
										suLayerType = "mpt";
									} else if (layerType.equals("MULTILINESTRING")) {
										suLayerType = "mln";
									} else if (layerType.equals("MULTIPOLYGON")) {
										suLayerType = "mpg";
									} else if (layerType.equals("TEXT")) {
										suLayerType = "txt";
									} else {
										suLayerType = "defalut";
										layerJson.put("type", suLayerType);
									}

									ngiFileNames.add(fileName);
									fileNameJson.put("id", groupName);
									fileNameJson.put("parent", "n_ngi");
									fileNameJson.put("text", fileName);
									fileNameJson.put("type", "n_" + fileType + "_group");
									layerJson.put("id", layerName);
									layerJson.put("parent", groupName);
									layerJson.put("text", exTypelayerName);
									if (!suLayerType.equals("defalut")) {
										layerJson.put("type", "n_" + fileType + "_layer_" + suLayerType);
									}
									super.add(fileNameJson);
									super.add(layerJson);
								}
							} else if (fileType.equals("dxf")) {
								if (dxfFileNames.contains(fileName)) {
									if (layerType.equals("ARC")) {
										suLayerType = "arc";
									} else if (layerType.equals("CIRCLE")) {
										suLayerType = "cir";
									} else if (layerType.equals("INSERT")) {
										suLayerType = "ins";
									} else if (layerType.equals("LWPOLYLINE")) {
										suLayerType = "lpl";
									} else if (layerType.equals("POLYLINE")) {
										suLayerType = "pl";
									} else if (layerType.equals("TEXT")) {
										suLayerType = "txt";
									} else {
										suLayerType = "defalut";
										layerJson.put("type", suLayerType);
									}

									layerJson.put("id", layerName);
									layerJson.put("parent", groupName);
									layerJson.put("text", exTypelayerName);
									if (!suLayerType.equals("defalut")) {
										layerJson.put("type", "n_" + fileType + "_layer_" + suLayerType);
									}
									super.add(layerJson);
								} else {
									if (layerType.equals("ARC")) {
										suLayerType = "arc";
									} else if (layerType.equals("CIRCLE")) {
										suLayerType = "cir";
									} else if (layerType.equals("INSERT")) {
										suLayerType = "ins";
									} else if (layerType.equals("LWPOLYLINE")) {
										suLayerType = "lpl";
									} else if (layerType.equals("POLYLINE")) {
										suLayerType = "pl";
									} else if (layerType.equals("TEXT")) {
										suLayerType = "txt";
									} else {
										suLayerType = "defalut";
										layerJson.put("type", suLayerType);
									}

									dxfFileNames.add(fileName);
									fileNameJson.put("id", groupName);
									fileNameJson.put("parent", "n_dxf");
									fileNameJson.put("text", fileName);
									fileNameJson.put("type", "n_" + fileType + "_group");
									layerJson.put("id", layerName);
									layerJson.put("parent", groupName);
									layerJson.put("text", exTypelayerName);
									if (!suLayerType.equals("defalut")) {
										layerJson.put("type", "n_" + fileType + "_layer_" + suLayerType);
									}
									super.add(fileNameJson);
									super.add(layerJson);
								}
							} else if (fileType.equals("shp")) {
								if (shpFileNames.contains(fileName)) {
									if (layerType.equals("POINT")) {
										suLayerType = "pt";
									} else if (layerType.equals("LINESTRING")) {
										suLayerType = "ln";
									} else if (layerType.equals("POLYGON")) {
										suLayerType = "pg";
									} else if (layerType.equals("MULTIPOINT")) {
										suLayerType = "mpt";
									} else if (layerType.equals("MULTILINESTRING")) {
										suLayerType = "mln";
									} else if (layerType.equals("MULTIPOLYGON")) {
										suLayerType = "mpg";
									} else {
										suLayerType = "defalut";
										layerJson.put("type", suLayerType);
									}

									layerJson.put("id", layerName);
									layerJson.put("parent", groupName);
									layerJson.put("text", exTypelayerName);
									if (!suLayerType.equals("defalut")) {
										layerJson.put("type", "n_" + fileType + "_layer_" + suLayerType);
									}
									super.add(layerJson);
								} else {
									if (layerType.equals("POINT")) {
										suLayerType = "pt";
									} else if (layerType.equals("LINESTRING")) {
										suLayerType = "ln";
									} else if (layerType.equals("POLYGON")) {
										suLayerType = "pg";
									} else if (layerType.equals("MULTIPOINT")) {
										suLayerType = "mpt";
									} else if (layerType.equals("MULTILINESTRING")) {
										suLayerType = "mln";
									} else if (layerType.equals("MULTIPOLYGON")) {
										suLayerType = "mpg";
									} else {
										suLayerType = "defalut";
										layerJson.put("type", suLayerType);
									}

									shpFileNames.add(fileName);
									fileNameJson.put("id", groupName);
									fileNameJson.put("parent", "n_shp");
									fileNameJson.put("text", fileName);
									fileNameJson.put("type", "n_" + fileType + "_group");
									layerJson.put("id", layerName);
									layerJson.put("parent", groupName);
									layerJson.put("text", exTypelayerName);
									if (!suLayerType.equals("defalut")) {
										layerJson.put("type", "n_" + fileType + "_layer_" + suLayerType);
									}
									super.add(fileNameJson);
									super.add(layerJson);
								}
							}
						}
					}
				}
			}
		}
		return this;
	}
	
	
	
	/**
	 * TreeType이 All인 Tree형태의 GeoserverLayerCollection json 객체들
	 * 
	 * @author JY.Kim
	 * @Date 2017. 4. 10. 오후 4:24:45
	 * @param reader
	 *            - Geosolution Format
	 * @return GeoserverLayerCollectionTree - Tree형태의 GeoserverLayerCollection
	 *         json 객체들
	 */
	@SuppressWarnings("unchecked")
	public GeoserverLayerCollectionTree build(RESTFeatureTypeList featureTypeList, TreeType treeType) {
		JSONObject geoserverLayers = new JSONObject();
		JSONObject ngiGeoLayers = new JSONObject();
		JSONObject dxfGeoLayers = new JSONObject();
		JSONObject shpGeoLayers = new JSONObject();

		
		
		geoserverLayers.put("id", "geoLayers");
		geoserverLayers.put("parent", "#");
		geoserverLayers.put("text", "GeoserverLayers");
		geoserverLayers.put("type", "normal");

		
		super.add(geoserverLayers);
		
		if(treeType == TreeType.QA20){
			ngiGeoLayers.put("id", "n_ngi");
			ngiGeoLayers.put("parent", "geoLayers");
			ngiGeoLayers.put("text", "NGI");
			ngiGeoLayers.put("type", "n_ngi");
			super.add(ngiGeoLayers);
		}
		if(treeType == TreeType.QA10){
			dxfGeoLayers.put("id", "n_dxf");
			dxfGeoLayers.put("parent", "geoLayers");
			dxfGeoLayers.put("text", "DXF");
			dxfGeoLayers.put("type", "n_dxf");
			super.add(dxfGeoLayers);
		}
		if(treeType==TreeType.QA20||treeType==TreeType.SHP){
			shpGeoLayers.put("id", "n_shp");
			shpGeoLayers.put("parent", "geoLayers");
			shpGeoLayers.put("text", "SHP");
			shpGeoLayers.put("type", "n_shp");
			super.add(shpGeoLayers);
		}

		if (featureTypeList == null) {
			return this;
		}

		if (featureTypeList.size() > 1) {
			List<String> layerNames = new ArrayList<String>(); // 레이어 이름 리스트
			layerNames = featureTypeList.getNames();
			List<String> ngiFileNames = new ArrayList<String>(); // 파일명 리스트(서로
																	// 중복된 파일명
																	// 없음)
			List<String> shpFileNames = new ArrayList<String>(); // 파일명 리스트(서로
																	// 중복된 파일명
																	// 없음)
			List<String> dxfFileNames = new ArrayList<String>(); // 파일명 리스트(서로
																	// 중복된 파일명
																	// 없음)

			for (String layerName : layerNames) {
				int con = layerName.indexOf("_");
				if (con != -1) {

					String preName = layerName.substring(0, con); // 구분코드
					String cutLayerName = layerName.substring(con + 1);

					// or
					// 레이어명(구분코드가
					// gen일 경우)

					JSONObject layerJson = new JSONObject();
					if (!preName.equals("err")&&!preName.equals("gen")) { // 파일명이 eg 또는 el 둘다 아닌경우
						int dash = cutLayerName.indexOf("_");
						String fileType = cutLayerName.substring(0, dash);
						if (fileType.equals(EnFileFormat.DXF.getStateName())
								|| fileType.equals(EnFileFormat.NGI.getStateName())
								|| fileType.equals(EnFileFormat.SHP.getStateName())) {

							String lastName = cutLayerName.substring(dash + 1); // 파일명_레이어명
							int div = lastName.indexOf("_");
							String fileName = lastName.substring(0, div);
							String lastLayerName = lastName.substring(div + 1);

							int layerTypeDash = lastLayerName.lastIndexOf("_");
							String exTypelayerName = lastLayerName.substring(0, layerTypeDash);
							String layerType = lastLayerName.substring(layerTypeDash + 1);
							String suLayerType = "";

							String groupName = "gro_" + fileType + "_" + fileName;

							JSONObject fileNameJson = new JSONObject();
							
							
							if(treeType ==TreeType.QA20){
								if (fileType.equals("ngi")) {
									if (ngiFileNames.contains(fileName)) {
										if (layerType.equals("POINT")) {
											suLayerType = "pt";
										} else if (layerType.equals("LINESTRING")) {
											suLayerType = "ln";
										} else if (layerType.equals("POLYGON")) {
											suLayerType = "pg";
										} else if (layerType.equals("TEXT")) {
											suLayerType = "txt";
										} else if (layerType.equals("MULTIPOINT")) {
											suLayerType = "mpt";
										} else if (layerType.equals("MULTILINESTRING")) {
											suLayerType = "mln";
										} else if (layerType.equals("MULTIPOLYGON")) {
											suLayerType = "mpg";
										} else if (layerType.equals("TEXT")) {
											suLayerType = "txt";
										} else {
											suLayerType = "defalut";
											layerJson.put("type", suLayerType);
										}

										layerJson.put("id", layerName);
										layerJson.put("parent", groupName);
										layerJson.put("text", exTypelayerName);
										if (!suLayerType.equals("defalut")) {
											layerJson.put("type", "n_" + fileType + "_layer_" + suLayerType);
										}
										super.add(layerJson);
									} else {
										if (layerType.equals("POINT")) {
											suLayerType = "pt";
										} else if (layerType.equals("LINESTRING")) {
											suLayerType = "ln";
										} else if (layerType.equals("POLYGON")) {
											suLayerType = "pg";
										} else if (layerType.equals("TEXT")) {
											suLayerType = "txt";
										} else if (layerType.equals("MULTIPOINT")) {
											suLayerType = "mpt";
										} else if (layerType.equals("MULTILINESTRING")) {
											suLayerType = "mln";
										} else if (layerType.equals("MULTIPOLYGON")) {
											suLayerType = "mpg";
										} else if (layerType.equals("TEXT")) {
											suLayerType = "txt";
										} else {
											suLayerType = "defalut";
											layerJson.put("type", suLayerType);
										}

										ngiFileNames.add(fileName);
										fileNameJson.put("id", groupName);
										fileNameJson.put("parent", "n_ngi");
										fileNameJson.put("text", fileName);
										fileNameJson.put("type", "n_" + fileType + "_group");
										layerJson.put("id", layerName);
										layerJson.put("parent", groupName);
										layerJson.put("text", exTypelayerName);
										if (!suLayerType.equals("defalut")) {
											layerJson.put("type", "n_" + fileType + "_layer_" + suLayerType);
										}
										super.add(fileNameJson);
										super.add(layerJson);
									}
								} 
							}
							
							
							
							if(treeType == TreeType.QA10){
								if (fileType.equals("dxf")) {
									if (dxfFileNames.contains(fileName)) {
										if (layerType.equals("ARC")) {
											suLayerType = "arc";
										} else if (layerType.equals("CIRCLE")) {
											suLayerType = "cir";
										} else if (layerType.equals("INSERT")) {
											suLayerType = "ins";
										} else if (layerType.equals("LWPOLYLINE")) {
											suLayerType = "lpl";
										} else if (layerType.equals("POLYLINE")) {
											suLayerType = "pl";
										} else if (layerType.equals("TEXT")) {
											suLayerType = "txt";
										} else {
											suLayerType = "defalut";
											layerJson.put("type", suLayerType);
										}

										layerJson.put("id", layerName);
										layerJson.put("parent", groupName);
										layerJson.put("text", exTypelayerName);
										if (!suLayerType.equals("defalut")) {
											layerJson.put("type", "n_" + fileType + "_layer_" + suLayerType);
										}
										super.add(layerJson);
									} else {
										if (layerType.equals("ARC")) {
											suLayerType = "arc";
										} else if (layerType.equals("CIRCLE")) {
											suLayerType = "cir";
										} else if (layerType.equals("INSERT")) {
											suLayerType = "ins";
										} else if (layerType.equals("LWPOLYLINE")) {
											suLayerType = "lpl";
										} else if (layerType.equals("POLYLINE")) {
											suLayerType = "pl";
										} else if (layerType.equals("TEXT")) {
											suLayerType = "txt";
										} else {
											suLayerType = "defalut";
											layerJson.put("type", suLayerType);
										}

										dxfFileNames.add(fileName);
										fileNameJson.put("id", groupName);
										fileNameJson.put("parent", "n_dxf");
										fileNameJson.put("text", fileName);
										fileNameJson.put("type", "n_" + fileType + "_group");
										layerJson.put("id", layerName);
										layerJson.put("parent", groupName);
										layerJson.put("text", exTypelayerName);
										if (!suLayerType.equals("defalut")) {
											layerJson.put("type", "n_" + fileType + "_layer_" + suLayerType);
										}
										super.add(fileNameJson);
										super.add(layerJson);
									}
								}
							}
							
							if(treeType==TreeType.QA20||treeType==TreeType.SHP){
								if (fileType.equals("shp")) {
									if (shpFileNames.contains(fileName)) {
										if (layerType.equals("POINT")) {
											suLayerType = "pt";
										} else if (layerType.equals("LINESTRING")) {
											suLayerType = "ln";
										} else if (layerType.equals("POLYGON")) {
											suLayerType = "pg";
										} else if (layerType.equals("MULTIPOINT")) {
											suLayerType = "mpt";
										} else if (layerType.equals("MULTILINESTRING")) {
											suLayerType = "mln";
										} else if (layerType.equals("MULTIPOLYGON")) {
											suLayerType = "mpg";
										} else {
											suLayerType = "defalut";
											layerJson.put("type", suLayerType);
										}

										layerJson.put("id", layerName);
										layerJson.put("parent", groupName);
										layerJson.put("text", exTypelayerName);
										if (!suLayerType.equals("defalut")) {
											layerJson.put("type", "n_" + fileType + "_layer_" + suLayerType);
										}
										super.add(layerJson);
									} else {
										if (layerType.equals("POINT")) {
											suLayerType = "pt";
										} else if (layerType.equals("LINESTRING")) {
											suLayerType = "ln";
										} else if (layerType.equals("POLYGON")) {
											suLayerType = "pg";
										} else if (layerType.equals("MULTIPOINT")) {
											suLayerType = "mpt";
										} else if (layerType.equals("MULTILINESTRING")) {
											suLayerType = "mln";
										} else if (layerType.equals("MULTIPOLYGON")) {
											suLayerType = "mpg";
										} else {
											suLayerType = "defalut";
											layerJson.put("type", suLayerType);
										}

										shpFileNames.add(fileName);
										fileNameJson.put("id", groupName);
										fileNameJson.put("parent", "n_shp");
										fileNameJson.put("text", fileName);
										fileNameJson.put("type", "n_" + fileType + "_group");
										layerJson.put("id", layerName);
										layerJson.put("parent", groupName);
										layerJson.put("text", exTypelayerName);
										if (!suLayerType.equals("defalut")) {
											layerJson.put("type", "n_" + fileType + "_layer_" + suLayerType);
										}
										super.add(fileNameJson);
										super.add(layerJson);
									}
								}
							}
						}
					}
				}
			}
		}
		return this;
	}
}
