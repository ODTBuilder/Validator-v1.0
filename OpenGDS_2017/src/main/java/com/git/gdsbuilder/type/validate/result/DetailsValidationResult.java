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

package com.git.gdsbuilder.type.validate.result;

import org.json.JSONObject;

/**
 * DetailsValidateResult 정보를 담고 있는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:54:02
 */
public class DetailsValidationResult {

	/**
	 * 검수 파일명
	 */
	String collectionName;
	/**
	 * 오류 레이어명
	 */
	String layerName;
	/**
	 * 오류 피처 ID
	 */
	String featureID;
	/**
	 * 오류 타입
	 */
	String errorType;
	/**
	 * 오류명
	 */
	String errorName;
	/**
	 * 오류 포인트 x 좌표
	 */
	double errorCoordinateX;
	/**
	 * 오류 포인트 y 좌표
	 */
	double errorCoordinateY;

	/**
	 * DetailsValidateResult 생성자
	 * 
	 * @param collectionName
	 *            검수 파일명
	 * @param layerName
	 *            오류 레이어명
	 * @param featureID
	 *            오류 피처 ID
	 * @param errorType
	 *            오류 타입
	 * @param errorName
	 *            오류명
	 * @param errorCoordinateX
	 *            오류 포인트 x 좌표
	 * @param errorCoordinateY
	 *            오류 포인트 y 좌표
	 */
	public DetailsValidationResult(String collectionName, String layerName, String featureID, String errorType,
			String errorName, double errorCoordinateX, double errorCoordinateY) {
		super();
		this.collectionName = collectionName;
		this.layerName = layerName;
		this.featureID = featureID;
		this.errorType = errorType;
		this.errorName = errorName;
		this.errorCoordinateX = errorCoordinateX;
		this.errorCoordinateY = errorCoordinateY;
	}

	/**
	 * 검수 파일명 반환
	 * 
	 * @return String
	 */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * 검수 파일명 설정
	 * 
	 * @param collectionName
	 *            검수 파일명
	 */
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
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
	 * 오류 타입 반환
	 * 
	 * @return String
	 */
	public String getErrorType() {
		return errorType;
	}

	/**
	 * 오류 타입 설정
	 * 
	 * @param errorType
	 *            오류 타입
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	/**
	 * 오류명 반환
	 * 
	 * @return String
	 */
	public String getErrorName() {
		return errorName;
	}

	/**
	 * 오류명 설정
	 * 
	 * @param errorName
	 *            오류명
	 */
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}

	/**
	 * 오류 포인트 x 좌표 반환
	 * 
	 * @return double
	 */
	public double getErrorCoordinateX() {
		return errorCoordinateX;
	}

	/**
	 * 오류 포인트 x 좌표 설정
	 * 
	 * @param errorCoordinateX
	 *            오류 포인트 x
	 */
	public void setErrorCoordinateX(double errorCoordinateX) {
		this.errorCoordinateX = errorCoordinateX;
	}

	/**
	 * 오류 포인트 y 좌표 반환
	 * 
	 * @return double
	 */
	public double getErrorCoordinateY() {
		return errorCoordinateY;
	}

	/**
	 * 오류 포인트 y 좌표 설정
	 * 
	 * @param errorCoordinateY
	 *            오류 포인트 y 좌표
	 */
	public void setErrorCoordinateY(double errorCoordinateY) {
		this.errorCoordinateY = errorCoordinateY;
	}

	/**
	 * DetailsValidateResult를 JSONObject로 파싱
	 * 
	 * @return JSONObject
	 */
	public JSONObject parseJSON() {

		JSONObject resultObj = new JSONObject();

		resultObj.put("collectionName", collectionName);
		resultObj.put("layerName", layerName);
		resultObj.put("featureID", featureID);
		resultObj.put("errorType", errorType);
		resultObj.put("errorName", errorName);
		resultObj.put("errorCoordinateX", errorCoordinateX);
		resultObj.put("errorCoordinateY", errorCoordinateY);

		return resultObj;
	}

}
