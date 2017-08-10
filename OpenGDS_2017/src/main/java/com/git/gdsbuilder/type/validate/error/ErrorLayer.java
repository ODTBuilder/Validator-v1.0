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

	String layerName;
	String collectionName;
	List<ErrorFeature> errFeatureList;
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
	 */
	public ErrorLayer(List<ErrorFeature> errFeatureList) {
		super();
		this.collectionName = "";
		this.errFeatureList = errFeatureList;
		this.collectionType = "";
	}

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	/**
	 * collectionName getter @author DY.Oh @Date 2017. 3. 11. 오후 3:00:44 @return
	 * String @throws
	 */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * collectionName setter @author DY.Oh @Date 2017. 3. 11. 오후 3:00:45 @param
	 * collectionName void @throws
	 */
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	/**
	 * errFeatureList getter @author DY.Oh @Date 2017. 3. 11. 오후 3:00:48 @return
	 * List<ErrorFeature> @throws
	 */
	public List<ErrorFeature> getErrFeatureList() {
		return errFeatureList;
	}

	/**
	 * errFeatureList setter @author DY.Oh @Date 2017. 3. 11. 오후 3:00:50 @param
	 * errFeatureList void @throws
	 */
	public void setErrFeatureList(List<ErrorFeature> errFeatureList) {
		this.errFeatureList = errFeatureList;
	}

	/**
	 * errFeatureList에 errFeature를 더함 @author DY.Oh @Date 2017. 3. 11. 오후
	 * 3:00:51 @param errFeature void @throws
	 */
	public void addErrorFeature(ErrorFeature errFeature) {
		this.errFeatureList.add(errFeature);
	}

	/**
	 * errFeatureList에 errFeatures를 더함 @author DY.Oh @Date 2017. 3. 11. 오후
	 * 3:00:53 @param errFeatures void @throws
	 */
	public void addErrorFeatureCollection(List<ErrorFeature> errFeatures) {
		this.errFeatureList.addAll(errFeatures);
	}

	/**
	 * 두 ErrorLayer를 합침 @author DY.Oh @Date 2017. 3. 11. 오후 3:01:09 @param
	 * errLayer void @throws
	 */
	public void mergeErrorLayer(ErrorLayer errLayer) {
		this.errFeatureList.addAll(errLayer.getErrFeatureList());
	}

	public String getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}

}
