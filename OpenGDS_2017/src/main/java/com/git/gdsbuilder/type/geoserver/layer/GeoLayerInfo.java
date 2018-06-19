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
 * Geoserver에 발행할 레이어 정보를 저장하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 5. 1. 오후 1:26:32
 */
public class GeoLayerInfo extends FileMeta {

	/**
	 * Geoserver에 발행할 레이어의 feature id로 사용될 DB Table 컬럼명
	 */
	private static final String fid = "f_idx";
	/**
	 * Geoserver에 발행할 레이어의 geometry로 사용될 DB Table 컬럼명
	 */
	private static final String geom = "geom";
	/**
	 * Geoserver에 발행할 레이어의 boundary로 사용될 DB Table 컬럼명
	 */
	private static final String boundary = "st_extent";
	/**
	 * 파일 포맷
	 */
	private String fileType;
	/**
	 * 레이어 좌표계 EPSG 코드
	 */
	private String transSrc;
	/**
	 * 레이어 명 목록
	 */
	private List<String> layerNames;
	/**
	 * 레이어 타입 목록
	 */
	private Map<String, String> layerTypes;
	/**
	 * 레이어 column 목록
	 */
	private Map<String, List<String>> layerColumns;
	/**
	 * 레이어 boundary 목록
	 */
	private Map<String, HashMap<String, Object>> boundarys;
	/**
	 * 
	 */
	// private Map<String, Boolean> isFeatureMap;

	/**
	 * GeoLayerInfo 생성자
	 */
	public GeoLayerInfo() {
		super();
		transSrc = "";
		fileType = "";
		layerNames = new ArrayList<String>();
		layerTypes = new HashMap<String, String>();
		layerColumns = new HashMap<String, List<String>>();
		boundarys = new HashMap<String, HashMap<String, Object>>();
		// isFeatureMap = new HashMap<String, Boolean>();
	}

	// public Map<String, Boolean> getIsFeatureMap() {
	// return isFeatureMap;
	// }
	//
	// public void setIsFeatureMap(Map<String, Boolean> isFeatureMap) {
	// this.isFeatureMap = isFeatureMap;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.git.opengds.upload.domain.FileMeta#getFileType()
	 */
	public String getFileType() {
		return fileType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.git.opengds.upload.domain.FileMeta#setFileType(java.lang.String)
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * Geoserver 레이어의 boundary로 사용될 DB Table 컬럼명 반환
	 * 
	 * @return String
	 */
	public static String getBoundary() {
		return boundary;
	}

	/**
	 * 레이어 좌표계 EPSG 코드 반환
	 * 
	 * @return String
	 */
	public String getTransSrc() {
		return transSrc;
	}

	/**
	 * * 레이어 좌표계 EPSG 코드 반환 설정
	 * 
	 * @param transSrc
	 *            레이어 좌표계 EPSG 코드
	 */
	public void setTransSrc(String transSrc) {
		this.transSrc = transSrc;
	}

	/**
	 * 레이어 타입 목록 반환
	 * 
	 * @return Map<String, String>
	 */
	public Map<String, String> getLayerTypes() {
		return layerTypes;
	}

	/**
	 * 레이어 타입 목록 설정
	 * 
	 * @param layerTypes
	 *            레이어 타입 목록
	 */
	public void setLayerTypes(Map<String, String> layerTypes) {
		this.layerTypes = layerTypes;
	}

	/**
	 * 레이어 column 목록 반환
	 * 
	 * @return Map<String, List<String>>
	 */
	public Map<String, List<String>> getLayerColumns() {
		return layerColumns;
	}

	/**
	 * 레이어 column 목록 설정
	 * 
	 * @param layerColumns
	 *            레이어 column 목록
	 */
	public void setLayerColumns(Map<String, List<String>> layerColumns) {
		this.layerColumns = layerColumns;
	}

	/**
	 * 레이어 ID, 레이어 타입을 레이어 타입 목록에 추가
	 * 
	 * @param layerID
	 *            레이어 ID
	 * @param layerType
	 *            레이어 타입
	 */
	public void putLayerType(String layerID, String layerType) {
		layerTypes.put(layerID, layerType);
	}

	/**
	 * 레이어 ID, 레이어 타입을 레이어 column 목록에 추가
	 * 
	 * @param layerID
	 *            레이어 ID
	 * @param columns
	 *            레이어 column 목록
	 */
	public void putLayerColumns(String layerID, List<String> columns) {
		layerColumns.put(layerID, columns);
	}

	/**
	 * 레이어 명 목록 반환
	 * 
	 * @return List<String>
	 */
	public List<String> getLayerNames() {
		return layerNames;
	}

	/**
	 * 레이어 명 목록 설정
	 * 
	 * @param layerNames
	 *            레이어 명 목록
	 */
	public void setLayerNames(List<String> layerNames) {
		this.layerNames = layerNames;
	}

	/**
	 * 레이어 명 목록에 레이어 명 추가
	 * 
	 * @param layerName
	 *            레이어 명
	 */
	public void putLayerName(String layerName) {
		layerNames.add(layerName);
	}

	/**
	 * Geoserver 레이어의 feature id로 사용될 DB Table 컬럼명 반환
	 * 
	 * @return String
	 */
	public static String getFid() {
		return fid;
	}

	/**
	 * Geoserver 레이어의 geometry로 사용될 DB Table 컬럼명
	 * 
	 * @return String
	 */
	public static String getGeom() {
		return geom;
	}

	/**
	 * 레이어 boundary 목록 반환
	 * 
	 * @return Map<String, HashMap<String, Object>>
	 */
	public Map<String, HashMap<String, Object>> getBoundarys() {
		return boundarys;
	}

	/**
	 * 레이어 boundary 목록 설정
	 * 
	 * @param boundary
	 *            레이어 boundary 목록
	 */
	public void setBoundarys(Map<String, HashMap<String, Object>> boundary) {
		this.boundarys = boundary;
	}

	/**
	 * 레이어 boundary 목록에 레이어명, 레이어 boundary 추가
	 * 
	 * @param layerName
	 *            레이어명
	 * @param boundary
	 *            레이어 boundary
	 */
	public void putLayerBoundary(String layerName, HashMap<String, Object> boundary) {

		String boundaryValue = (String) boundary.get(this.boundary);

		this.boundarys.put(layerName, boundary);
	}

}
