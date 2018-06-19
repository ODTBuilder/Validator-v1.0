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
package com.git.gdsbuilder.edit.shp;

import com.git.gdsbuilder.type.shp.feature.DTSHPFeatureList;

/**
 * 편집된 shp file 정보를 가지고 있는 클래스
 * 
 * @author DY.Oh
 */
public class EditSHPLayer {

	/**
	 * 수정된 레이어명
	 */
	String layerName;
	/**
	 * 원본 레이어명
	 */
	String orignName;
	/**
	 * 레이어 타입
	 */
	String layerType;

	/**
	 * 생성된 피쳐 목록
	 */
	DTSHPFeatureList createFeatureList;
	/**
	 * 수정된 피쳐 목록
	 */
	DTSHPFeatureList modifiedFeatureList;
	/**
	 * 삭제된 피쳐 목록
	 */
	DTSHPFeatureList deletedFeatureList;

	/**
	 * 수정된 레이어명 반환
	 * 
	 * @return String
	 */
	public String getLayerName() {
		return layerName;
	}

	/**
	 * 수정된 레이어명 설정
	 * 
	 * @param layerName
	 *            수정된 레이어명
	 */
	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	/**
	 * 원본 레이어명 반환
	 * 
	 * @return String
	 */
	public String getOrignName() {
		return orignName;
	}

	/**
	 * 원본 레이어명 설정
	 * 
	 * @param orignName
	 *            원본 레이어명
	 */
	public void setOrignName(String orignName) {
		this.orignName = orignName;
	}

	/**
	 * 레이어 타입 반환
	 * 
	 * @return String
	 */
	public String getLayerType() {
		return layerType;
	}

	/**
	 * 레이어 타입 설정
	 * 
	 * @param layerType
	 *            레이어 타입
	 */
	public void setLayerType(String layerType) {
		this.layerType = layerType;
	}

	/**
	 * 생성된 피쳐 목록 반환
	 * 
	 * @return DTSHPFeatureList
	 */
	public DTSHPFeatureList getCreateFeatureList() {
		return createFeatureList;
	}

	/**
	 * 생성된 피쳐 목록 설정
	 * 
	 * @param createFeatureList
	 *            생성된 피쳐 목록
	 */
	public void setCreateFeatureList(DTSHPFeatureList createFeatureList) {
		this.createFeatureList = createFeatureList;
	}

	/**
	 * 수정된 피쳐 목록 반환
	 * 
	 * @return DTSHPFeatureList
	 */
	public DTSHPFeatureList getModifiedFeatureList() {
		return modifiedFeatureList;
	}

	/**
	 * 수정된 피쳐 목록 설정
	 * 
	 * @param modifiedFeatureList
	 *            수정된 피쳐 목록
	 */
	public void setModifiedFeatureList(DTSHPFeatureList modifiedFeatureList) {
		this.modifiedFeatureList = modifiedFeatureList;
	}

	/**
	 * 삭제된 피쳐 목록 반환
	 * 
	 * @return DTSHPFeatureList
	 */
	public DTSHPFeatureList getDeletedFeatureList() {
		return deletedFeatureList;
	}

	/**
	 * 삭제된 피쳐 목록 설정
	 * 
	 * @param deletedFeatureList
	 *            삭제된 피쳐 목록
	 */
	public void setDeletedFeatureList(DTSHPFeatureList deletedFeatureList) {
		this.deletedFeatureList = deletedFeatureList;
	}

}
