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

import com.vividsolutions.jts.geom.Geometry;

/**
 * ErrorFeature 정보를 담고 있는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:57:39
 */
public class ErrorFeature {

	/**
	 * 검수 파일 이름
	 */
	String collectionName;
	/**
	 * 검수 레이어 ID
	 */
	String layerID;
	/**
	 * 검수 레이어명
	 */
	String layerName;
	/**
	 * 오류 피처 ID
	 */
	String featureID;
	/**
	 * 오류 피처 Index
	 */
	String featureIdx;
	/**
	 * 오류 종류
	 */
	String errType;
	/**
	 * 오류명
	 */
	String errName;
	/**
	 * 오류 포인트
	 */
	Geometry errPoint;

	/**
	 * ErrorFeature 생성자
	 */
	public ErrorFeature() {
		super();
	}

	/**
	 * ErrorFeature 생성자
	 * 
	 * @param collectionName
	 *            검수 파일 이름
	 * @param layerID
	 *            검수 레이어 ID
	 * @param layerName
	 *            검수 레이어명
	 * @param featureID
	 *            오류 피처 ID
	 * @param featureIdx
	 *            오류 피처 Index
	 * @param errType
	 *            오류 종류
	 * @param errName
	 *            오류명
	 * @param errPoint
	 *            오류 포인트
	 */
	public ErrorFeature(String collectionName, String layerID, String layerName, String featureID, String featureIdx,
			String errType, String errName, Geometry errPoint) {
		super();
		this.collectionName = collectionName;
		this.layerID = layerID;
		this.layerName = layerName;
		this.featureID = featureID;
		this.featureIdx = featureIdx;
		this.errType = errType;
		this.errName = errName;
		this.errPoint = errPoint;
	}

	/**
	 * ErrorFeature 생성자
	 * 
	 * @param featureID
	 *            오류 피처 ID
	 * @param errType
	 *            오류 종류
	 * @param errName
	 *            오류명
	 * @param errPoint
	 *            오류 포인트
	 */
	public ErrorFeature(String featureId, String featureID, String errType, String errName, Geometry errPoint) {
		super();
		this.featureIdx = featureId;
		this.layerID = "";
		this.layerName = "";
		this.featureID = featureID;
		this.errType = errType;
		this.errName = errName;
		this.errPoint = errPoint;
	}

	/**
	 * 검수 파일 이름 반환
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
	 * 오류 피처 Index 반환
	 * 
	 * @return String
	 */
	public String getFeatureIdx() {
		return featureIdx;
	}

	/**
	 * 오류 피처 Index 설정
	 * 
	 * @param featureIdx
	 *            오류 피처 Index
	 */
	public void setFeatureIdx(String featureIdx) {
		this.featureIdx = featureIdx;
	}

	/**
	 * 검수 레이어 ID 반환
	 * 
	 * @return String
	 */
	public String getLayerID() {
		return layerID;
	}

	/**
	 * 검수 레이어 ID 설정
	 * 
	 * @param layerID
	 *            검수 레이어 ID
	 */
	public void setLayerID(String layerID) {
		this.layerID = layerID;
	}

	/**
	 * 검수 레이어명 반환
	 * 
	 * @return String
	 */
	public String getLayerName() {
		return layerName;
	}

	/**
	 * 검수 레이어명 설정
	 * 
	 * @param layerName
	 *            검수 레이어명
	 */
	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	/**
	 * 오류 피처 ID 반환
	 * 
	 * @return String
	 */
	public String getFeatureID() {
		return featureID;
	}

	/**
	 * 오류 피처 ID 설정
	 * 
	 * @param featureID
	 *            오류 피처 ID
	 */
	public void setFeatureID(String featureID) {
		this.featureID = featureID;
	}

	/**
	 * 오류 종류 반환
	 * 
	 * @return String
	 */
	public String getErrType() {
		return errType;
	}

	/**
	 * 오류 종류 설정
	 * 
	 * @param errType
	 *            오류 종류
	 */
	public void setErrType(String errType) {
		this.errType = errType;
	}

	/**
	 * 오류명 반환
	 * 
	 * @return String
	 */
	public String getErrName() {
		return errName;
	}

	/**
	 * 오류명 설정
	 * 
	 * @param errName
	 *            오류명
	 */
	public void setErrName(String errName) {
		this.errName = errName;
	}

	/**
	 * 오류 포인트 반환
	 * 
	 * @return Geometry
	 */
	public Geometry getErrPoint() {
		return errPoint;
	}

	/**
	 * 오류 포인트 설정
	 * 
	 * @param errPoint
	 *            오류 포인트
	 */
	public void setErrPoint(Geometry errPoint) {
		this.errPoint = errPoint;
	}

}
