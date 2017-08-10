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

	String collectionName;
	String layerID;
	String layerName;
	String featureID;
	String featureIdx;
	String errType;
	String errName;
	Geometry errPoint;

	/**
	 * ErrorFeature 생성자
	 */
	public ErrorFeature() {
		super();
		// TODO Auto-generated constructor stub
	}

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
	 * @param errType
	 * @param errName
	 * @param errPoint
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

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public String getFeatureIdx() {
		return featureIdx;
	}

	public void setFeatureIdx(String featureIdx) {
		this.featureIdx = featureIdx;
	}

	/**
	 * layerID getter @author DY.Oh @Date 2017. 3. 11. 오후 2:57:47 @return
	 * String @throws
	 */
	public String getLayerID() {
		return layerID;
	}

	/**
	 * layerID setter @author DY.Oh @Date 2017. 3. 11. 오후 2:57:48 @param layerID
	 * void @throws
	 */
	public void setLayerID(String layerID) {
		this.layerID = layerID;
	}

	/**
	 * layerName getter @author DY.Oh @Date 2017. 3. 11. 오후 2:57:53 @return
	 * String @throws
	 */
	public String getLayerName() {
		return layerName;
	}

	/**
	 * layerName setter @author DY.Oh @Date 2017. 3. 11. 오후 2:57:56 @param
	 * layerName void @throws
	 */
	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	/**
	 * featureID getter @author DY.Oh @Date 2017. 3. 11. 오후 2:57:58 @return
	 * String @throws
	 */
	public String getFeatureID() {
		return featureID;
	}

	/**
	 * featureID setter @author DY.Oh @Date 2017. 3. 11. 오후 2:58:01 @param
	 * featureID void @throws
	 */
	public void setFeatureID(String featureID) {
		this.featureID = featureID;
	}

	/**
	 * errType getter @author DY.Oh @Date 2017. 3. 11. 오후 2:58:03 @return
	 * String @throws
	 */
	public String getErrType() {
		return errType;
	}

	/**
	 * errType setter @author DY.Oh @Date 2017. 3. 11. 오후 2:58:05 @param errType
	 * void @throws
	 */
	public void setErrType(String errType) {
		this.errType = errType;
	}

	/**
	 * errName getter @author DY.Oh @Date 2017. 3. 11. 오후 2:58:07 @return
	 * String @throws
	 */
	public String getErrName() {
		return errName;
	}

	/**
	 * errName setter @author DY.Oh @Date 2017. 3. 11. 오후 2:58:10 @param errName
	 * void @throws
	 */
	public void setErrName(String errName) {
		this.errName = errName;
	}

	/**
	 * errPoint getter @author DY.Oh @Date 2017. 3. 11. 오후 2:58:13 @return
	 * Geometry @throws
	 */
	public Geometry getErrPoint() {
		return errPoint;
	}

	/**
	 * errPoint setter @author DY.Oh @Date 2017. 3. 11. 오후 2:58:15 @param
	 * errPoint void @throws
	 */
	public void setErrPoint(Geometry errPoint) {
		this.errPoint = errPoint;
	}

}
