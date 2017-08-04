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

package com.git.gdsbuilder.validator.result;

import org.json.JSONObject;

/**
 * DetailsValidateResult 정보를 담고 있는 클래스
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:54:02
 * */
public class DetailsValidationResult {

	String collectionName;
	String layerName;
	String featureID;
	String errorType;
	String errorName;
	double errorCoordinateX;
	double errorCoordinateY;
	
	/**
	 * DetailsValidateResult 생성자
	 * @param collectionName
	 * @param layerName
	 * @param featureID
	 * @param errorType
	 * @param errorName
	 * @param errorCoordinateX
	 * @param errorCoordinateY
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
	 * collectionName getter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:54:16
	 * @return String
	 * @throws
	 * */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * collectionName setter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:54:17
	 * @param collectionName void
	 * @throws
	 * */
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	/**
	 * layerName getter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:54:18
	 * @return String
	 * @throws
	 * */
	public String getLayerName() {
		return layerName;
	}

	/**
	 * layerName setter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:54:19
	 * @param layerName void
	 * @throws
	 * */
	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	/**
	 * featureID getter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:54:21
	 * @return String
	 * @throws
	 * */
	public String getFeatureID() {
		return featureID;
	}

	/**
	 * featureID setter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:54:22
	 * @param featureID void
	 * @throws
	 * */
	public void setFeatureID(String featureID) {
		this.featureID = featureID;
	}

	/**
	 * errorType getter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:54:23
	 * @return String
	 * @throws
	 * */
	public String getErrorType() {
		return errorType;
	}

	/**
	 * errorType setter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:54:24
	 * @param errorType void
	 * @throws
	 * */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	/**
	 * errorName getter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:54:25
	 * @return String
	 * @throws
	 * */
	public String getErrorName() {
		return errorName;
	}

	/**
	 * errorName setter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:54:26
	 * @param errorName void
	 * @throws
	 * */
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}

	/**
	 * errorCoordinateX getter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:54:28
	 * @return double
	 * @throws
	 * */
	public double getErrorCoordinateX() {
		return errorCoordinateX;
	}

	/**
	 * errorCoordinateX setter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:54:29
	 * @param errorCoordinateX void
	 * @throws
	 * */
	public void setErrorCoordinateX(double errorCoordinateX) {
		this.errorCoordinateX = errorCoordinateX;
	}

	/**
	 * errorCoordinateY getter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:54:30
	 * @return double
	 * @throws
	 * */
	public double getErrorCoordinateY() {
		return errorCoordinateY;
	}

	/**
	 * errorCoordinateY setter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:54:31
	 * @param errorCoordinateY void
	 * @throws
	 * */
	public void setErrorCoordinateY(double errorCoordinateY) {
		this.errorCoordinateY = errorCoordinateY;
	}

	/**
	 * DetailsValidateResult를 JSONObject로 파싱
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:54:33
	 * @return JSONObject
	 * @throws
	 * */
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
