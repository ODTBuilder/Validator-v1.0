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

package com.git.gdsbuilder.type.geoserver.layer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.opengds.upload.domain.FileMeta;

/**
 * GeoserverLayer 정보를 저장하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 5. 1. 오후 1:26:32
 */
public class GeoLayerInfo extends FileMeta {

	private static final String fid = "f_idx";
	private static final String geom = "geom";
	private static final String boundary = "st_extent";
	private String fileType;
	private String transSrc;
	private List<String> layerNames;
	private Map<String, String> layerTypes;
	private Map<String, List<String>> layerColumns;
	private Map<String, HashMap<String, Object>> boundarys;
	private Map<String, Boolean> isFeatureMap;

	/**
	 * LayerInfo 생성자
	 */
	public GeoLayerInfo() {
		super();
		transSrc = "";
		fileType = "";
		layerNames = new ArrayList<String>();
		layerTypes = new HashMap<String, String>();
		layerColumns = new HashMap<String, List<String>>();
		boundarys = new HashMap<String, HashMap<String, Object>>();
		isFeatureMap = new HashMap<String, Boolean>();
	}

	public Map<String, Boolean> getIsFeatureMap() {
		return isFeatureMap;
	}

	public void setIsFeatureMap(Map<String, Boolean> isFeatureMap) {
		this.isFeatureMap = isFeatureMap;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public static String getBoundary() {
		return boundary;
	}

	/**
	 * transSrc getter @author DY.Oh @Date 2017. 5. 1. 오후 1:25:38 @return
	 * String @throws
	 */
	public String getTransSrc() {
		return transSrc;
	}

	/**
	 * transSrc setter @author DY.Oh @Date 2017. 5. 1. 오후 1:25:54 @param
	 * transSrc void @throws
	 */
	public void setTransSrc(String transSrc) {
		this.transSrc = transSrc;
	}

	/**
	 * layerTypes getter @author DY.Oh @Date 2017. 5. 1. 오후 1:26:04 @return
	 * Map<String,String> @throws
	 */
	public Map<String, String> getLayerTypes() {
		return layerTypes;
	}

	/**
	 * layerTypes setter @author DY.Oh @Date 2017. 5. 1. 오후 1:26:12 @param
	 * layerTypes void @throws
	 */
	public void setLayerTypes(Map<String, String> layerTypes) {
		this.layerTypes = layerTypes;
	}

	/**
	 * layerColumns getter @author DY.Oh @Date 2017. 5. 1. 오후 1:26:19 @return
	 * Map<String,List<String>> @throws
	 */
	public Map<String, List<String>> getLayerColumns() {
		return layerColumns;
	}

	/**
	 * layerColumns setter @author DY.Oh @Date 2017. 5. 1. 오후 1:26:21 @param
	 * layerColumns void @throws
	 */
	public void setLayerColumns(Map<String, List<String>> layerColumns) {
		this.layerColumns = layerColumns;
	}

	/**
	 * layerTypes에 layerType을 추가 @author DY.Oh @Date 2017. 5. 1. 오후
	 * 1:26:24 @param layerID @param layerType void @throws
	 */
	public void putLayerType(String layerID, String layerType) {
		layerTypes.put(layerID, layerType);
	}

	/**
	 * layerColumns layerColum 리스트를 추가 @author DY.Oh @Date 2017. 5. 1. 오후
	 * 1:26:26 @param layerID @param columns void @throws
	 */
	public void putLayerColumns(String layerID, List<String> columns) {
		layerColumns.put(layerID, columns);
	}

	/**
	 * layerNames getter @author DY.Oh @Date 2017. 5. 1. 오후 1:26:44 @return
	 * List<String> @throws
	 */
	public List<String> getLayerNames() {
		return layerNames;
	}

	/**
	 * layerNames setter @author DY.Oh @Date 2017. 5. 1. 오후 1:26:42 @param
	 * layerNames void @throws
	 */
	public void setLayerNames(List<String> layerNames) {
		this.layerNames = layerNames;
	}

	/**
	 * layerNames layerName 추가 @author DY.Oh @Date 2017. 5. 1. 오후 1:26:40 @param
	 * layerName void @throws
	 */
	public void putLayerName(String layerName) {
		layerNames.add(layerName);
	}

	/**
	 * fid getter @author DY.Oh @Date 2017. 5. 1. 오후 1:26:37 @return
	 * String @throws
	 */
	public static String getFid() {
		return fid;
	}

	/**
	 * geom getter @author DY.Oh @Date 2017. 5. 1. 오후 1:26:29 @return
	 * String @throws
	 */
	public static String getGeom() {
		return geom;
	}

	public Map<String, HashMap<String, Object>> getBoundarys() {
		return boundarys;
	}

	public void setBoundarys(Map<String, HashMap<String, Object>> boundary) {
		this.boundarys = boundary;
	}

	public void putLayerBoundary(String layerName, HashMap<String, Object> boundary) {

		String boundaryValue = (String) boundary.get(this.boundary);

		this.boundarys.put(layerName, boundary);
	}

}
