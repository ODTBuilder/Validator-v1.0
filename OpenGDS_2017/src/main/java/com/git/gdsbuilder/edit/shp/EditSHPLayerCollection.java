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

import java.util.HashMap;
import java.util.Map;

import com.git.gdsbuilder.type.shp.layer.DTSHPLayer;
import com.git.gdsbuilder.type.shp.layer.DTSHPLayerList;

/**
 * 편집된 shp file collection 정보를 가지고 있는 클래스
 * 
 * @author DY.Oh
 */
public class EditSHPLayerCollection {

	/**
	 * 편집된 shp file collection 이름
	 */
	String collectionName;
	/**
	 * 생성된 shp 레이어 목록
	 */
	DTSHPLayerList createLayerList;
	/**
	 * 수정된 shp 레이어 목록
	 */
	DTSHPLayerList modifiedLayerList;
	/**
	 * 삭제된 shp 레이어 목록
	 */
	DTSHPLayerList deletedLayerList;
	/**
	 * Geoserver에 저장된 레이어 목록
	 */
	Map<String, Object> geoLayerList;

	/**
	 * shp 레이어 생성 여부
	 */
	boolean isCreated = false;
	/**
	 * shp 레이어 수정 여부
	 */
	boolean isModified = false;
	/**
	 * shp 레이어 삭제 여부
	 */
	boolean isDeleted = false;
	/**
	 * shp 레이어 모두 삭제 여부
	 */
	boolean isDeleteAll = false;

	/**
	 * EditSHPLayerCollection 생성자
	 */
	public EditSHPLayerCollection() {
		this.collectionName = "";
		this.createLayerList = new DTSHPLayerList();
		this.modifiedLayerList = new DTSHPLayerList();
		this.deletedLayerList = new DTSHPLayerList();
		this.geoLayerList = new HashMap<String, Object>();
	}

	/**
	 * 편집된 shp file collection 이름 반환
	 * 
	 * @return String
	 */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * 편집된 shp file collection 이름 설정
	 * 
	 * @param collectionName
	 *            편집된 shp file collection 이름
	 */
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	/**
	 * 생성된 shp 레이어 목록 반환
	 * 
	 * @return DTSHPLayerList
	 */
	public DTSHPLayerList getCreateLayerList() {
		return createLayerList;
	}

	/**
	 * 생성된 shp 레이어 목록 설정
	 * 
	 * @param createLayerList
	 *            생성된 shp 레이어 목록
	 */
	public void setCreateLayerList(DTSHPLayerList createLayerList) {
		this.createLayerList = createLayerList;
	}

	/**
	 * 수정된 shp 레이어 목록 반환
	 * 
	 * @return DTSHPLayerList
	 */
	public DTSHPLayerList getModifiedLayerList() {
		return modifiedLayerList;
	}

	/**
	 * 수정된 shp 레이어 목록 설정
	 * 
	 * @param modifiedLayerList
	 *            수정된 shp 레이어 목록
	 */
	public void setModifiedLayerList(DTSHPLayerList modifiedLayerList) {
		this.modifiedLayerList = modifiedLayerList;
	}

	/**
	 * 삭제된 shp 레이어 목록 반환
	 * 
	 * @return DTSHPLayerList
	 */
	public DTSHPLayerList getDeletedLayerList() {
		return deletedLayerList;
	}

	/**
	 * 삭제된 shp 레이어 목록 설정
	 * 
	 * @param deletedLayerList
	 *            삭제된 shp 레이어 목록
	 */
	public void setDeletedLayerList(DTSHPLayerList deletedLayerList) {
		this.deletedLayerList = deletedLayerList;
	}

	/**
	 * Geoserver에 저장된 레이어 목록 반환
	 * 
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getGeoLayerList() {
		return geoLayerList;
	}

	/**
	 * Geoserver에 저장된 레이어 목록 설정
	 * 
	 * @param geoLayerList
	 *            Geoserver에 저장된 레이어 목록
	 */
	public void setGeoLayerList(Map<String, Object> geoLayerList) {
		this.geoLayerList = geoLayerList;
	}

	/**
	 * shp 레이어 생성 여부 반환
	 * 
	 * @return boolean
	 */
	public boolean isCreated() {
		return isCreated;
	}

	/**
	 * shp 레이어 생성 여부 설정
	 * 
	 * @param isCreated
	 *            shp 레이어 생성 여부
	 */
	public void setCreated(boolean isCreated) {
		this.isCreated = isCreated;
	}

	/**
	 * shp 레이어 수정 여부 반환
	 * 
	 * @return boolean
	 */
	public boolean isModified() {
		return isModified;
	}

	/**
	 * shp 레이어 수정 여부 설정
	 * 
	 * @param isModified
	 *            shp 레이어 수정 여부
	 */
	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}

	/**
	 * shp 레이어 삭제 여부
	 * 
	 * @return boolean
	 */
	public boolean isDeleted() {
		return isDeleted;
	}

	/**
	 * shp 레이어 삭제 여부 설정
	 * 
	 * @param isDeleted
	 *            shp 레이어 삭제 여부
	 */
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * shp 레이어 모두 삭제 여부 반환
	 * 
	 * @return boolean
	 */
	public boolean isDeleteAll() {
		return isDeleteAll;
	}

	/**
	 * shp 레이어 모두 삭제 여부 설정
	 * 
	 * @param isDeleteAll
	 *            shp 레이어 모두 삭제 여부
	 */
	public void setDeleteAll(boolean isDeleteAll) {
		this.isDeleteAll = isDeleteAll;
	}

	/**
	 * 생성된 shp 레이어 목록에 생성된 shp 레이어 정보 추가
	 * 
	 * @param createLayer
	 *            생성된 shp 레이어 정보
	 */
	public void addCreateLayer(DTSHPLayer createLayer) {
		this.createLayerList.add(createLayer);
	}

	/**
	 * 생성된 shp 레이어 목록에 생성된 shp 레이어 정보 목록 추가
	 * 
	 * @param createLayerList
	 *            생성된 shp 레이어 정보 목록
	 */
	public void addAllCreateLayer(DTSHPLayerList createLayerList) {
		this.createLayerList.addAll(createLayerList);
	}

	/**
	 * 수정된 shp 레이어 목록에 수정된 shp 레이어 정보 추가
	 * 
	 * @param modifyedLayer
	 *            수정된 shp 레이어 정보
	 */
	public void addmodifiedLayer(DTSHPLayer modifyedLayer) {
		this.modifiedLayerList.add(modifyedLayer);
	}

	/**
	 * 수정된 shp 레이어 목록에 수정된 shp 레이어 정보 목록 추가
	 * 
	 * @param modifiedLayerList
	 *            수정된 shp 레이어 정보 목록
	 */
	public void addAllmodifiedLayer(DTSHPLayerList modifiedLayerList) {
		this.modifiedLayerList.addAll(modifiedLayerList);
	}

	/**
	 * 삭제된 shp 레이어 목록에 삭제된 shp 레이어 정보 추가
	 * 
	 * @param deletedLayer
	 *            삭제된 shp 레이어 정보
	 */
	public void addDeleteLayer(DTSHPLayer deletedLayer) {
		this.deletedLayerList.add(deletedLayer);
	}

	/**
	 * 삭제된 shp 레이어 목록에 삭제된 shp 레이어 정보 목록 추가
	 * 
	 * @param deletedLayerList
	 *            삭제된 shp 레이어 정보 목록
	 */
	public void addAllDeleteLayer(DTSHPLayerList deletedLayerList) {
		this.deletedLayerList.addAll(deletedLayerList);
	}

}
