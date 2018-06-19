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

package com.git.gdsbuilder.type.validate.error;

import java.util.ArrayList;
import java.util.List;

/**
 * ErrorLayer 정보를 담고 있는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:57:39
 */
public class ErrorLayer {

	/**
	 * 오류 레이어명
	 */
	String layerName;
	/**
	 * 검수 파일 이름
	 */
	String collectionName;
	/**
	 * 오류 Feature 목록
	 */
	List<ErrorFeature> errFeatureList;
	/**
	 * 검수 파일 포맷
	 */
	String collectionType;

	/**
	 * ErrorLayer 생성자
	 */
	public ErrorLayer() {
		super();
		this.layerName = "";
		this.collectionName = "";
		this.errFeatureList = new ArrayList<ErrorFeature>();
		this.collectionType = "";
	}

	/**
	 * ErrorLayer 생성자
	 * 
	 * @param errFeatureList
	 *            오류 Feature 목록
	 */
	public ErrorLayer(List<ErrorFeature> errFeatureList) {
		super();
		this.collectionName = "";
		this.errFeatureList = errFeatureList;
		this.collectionType = "";
	}

	/**
	 * 오류 레이어명 반환
	 * 
	 * @return String
	 */
	public String getLayerName() {
		return layerName;
	}

	/**
	 * 오류 레이어명 설정
	 * 
	 * @param layerName
	 *            오류 레이어명
	 */
	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	/**
	 * 검수 파일 이름
	 * 
	 * @return String
	 */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * 검수 파일 이름 설정
	 * 
	 * @param collectionName
	 *            검수 파일 이름
	 */
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	/**
	 * 오류 Feature 목록 반환
	 * 
	 * @return
	 */
	public List<ErrorFeature> getErrFeatureList() {
		return errFeatureList;
	}

	/**
	 * 오류 Feature 목록 설정
	 * 
	 * @param errFeatureList
	 *            오류 Feature 목록
	 */
	public void setErrFeatureList(List<ErrorFeature> errFeatureList) {
		this.errFeatureList = errFeatureList;
	}

	/**
	 * 오류 Feature 목록에 ErrorFeature를 더함
	 * 
	 * @param errFeature
	 *            오류 Feature
	 */
	public void addErrorFeature(ErrorFeature errFeature) {
		this.errFeatureList.add(errFeature);
	}

	/**
	 * 오류 Feature 목록에 List<ErrorFeature>를 더함
	 * 
	 * @param errFeatures
	 *            오류 Feature 목록
	 */
	public void addErrorFeatureCollection(List<ErrorFeature> errFeatures) {
		this.errFeatureList.addAll(errFeatures);
	}

	/**
	 * 두 ErrorLayer를 합침
	 * 
	 * @param errLayer
	 *            오류 레이어
	 */
	public void mergeErrorLayer(ErrorLayer errLayer) {
		this.errFeatureList.addAll(errLayer.getErrFeatureList());
	}

	/**
	 * 검수 파일 포맷 반환
	 * 
	 * @return String
	 */
	public String getCollectionType() {
		return collectionType;
	}

	/**
	 * 검수 파일 포맷 설정
	 * 
	 * @param collectionType
	 *            검수 파일 포맷
	 */
	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}

}
