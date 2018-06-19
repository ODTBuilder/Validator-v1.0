package com.git.gdsbuilder.type.shp.collection;

import com.git.gdsbuilder.type.shp.layer.DTSHPLayerList;

/**
 * 다수의 SHP 파일 레이어 정보를 저장하고 있는 클래스
 * 
 * @author DY.Oh
 *
 */
public class DTSHPLayerCollection {

	/**
	 * 다수의 SHP 파일 레이어 collection 이름
	 */
	String collectionName;
	/**
	 * 다수의 SHP 파일 레이어 정보 목록
	 */
	DTSHPLayerList shpLayerList;

	/**
	 * SHP 파일 레이어 collection 이름 반환
	 * 
	 * @return String
	 */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * SHP 파일 레이어 collection 이름 설정
	 * 
	 * @param collectionName
	 *            SHP 파일 레이어 collection 이름
	 */
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	/**
	 * 다수의 SHP 파일 레이어 정보 목록 반환
	 * 
	 * @return
	 */
	public DTSHPLayerList getShpLayerList() {
		return shpLayerList;
	}

	/**
	 * 다수의 SHP 파일 레이어 정보 목록 설정
	 * 
	 * @param shpLayerList
	 *            다수의 SHP 파일 레이어 정보 목록
	 */
	public void setShpLayerList(DTSHPLayerList shpLayerList) {
		this.shpLayerList = shpLayerList;
	}

}
