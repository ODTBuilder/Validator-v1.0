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

/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2012, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */

package com.git.gdsbuilder.type.geoserver.layer;

import org.geotools.data.simple.SimpleFeatureCollection;

/**
 * GeoLayer 정보를 저장하는 클래스
 * 
 * @author DY.Oh
 */
public class GeoLayer {

	/**
	 * GeoLayer 타입
	 */
	String layerType;
	/**
	 * GeoLayer ID
	 */
	String layerID;
	/**
	 * GeoLayer명
	 */
	String layerName;
	/**
	 * GeoLayer에 해당하는 SimpleFeatureCollection 객체
	 */
	SimpleFeatureCollection simpleFeatureCollection;

	/**
	 * Layer 생성자
	 */
	public GeoLayer() {
		super();
	}

	/**
	 * Layer 생성자
	 * 
	 * @param layerType
	 *            GeoLayer 타입
	 * @param layerID
	 *            GeoLayer ID
	 * @param layerName
	 *            GeoLayer명
	 * @param simpleFeatureCollection
	 *            GeoLayer에 해당하는 SimpleFeatureCollection 객체
	 */
	public GeoLayer(String layerType, String layerID, String layerName,
			SimpleFeatureCollection simpleFeatureCollection) {
		super();
		this.layerID = layerID;
		this.layerType = layerType;
		this.layerName = layerName;
		this.simpleFeatureCollection = simpleFeatureCollection;
	}

	/**
	 * GeoLayer ID 반환
	 * 
	 * @return String
	 */
	public String getLayerID() {
		return layerID;
	}

	/**
	 * GeoLayer ID 설정
	 * 
	 * @param layerID
	 *            GeoLayer ID
	 */
	public void setLayerID(String layerID) {
		this.layerID = layerID;
	}

	/**
	 * GeoLayer 타입 반환
	 * 
	 * @return String
	 */
	public String getLayerType() {
		return layerType;
	}

	/**
	 * GeoLayer 타입 설정
	 * 
	 * @param layerType
	 *            GeoLayer 타입
	 */
	public void setLayerType(String layerType) {
		this.layerType = layerType;
	}

	/**
	 * GeoLayer명
	 * 
	 * @return String
	 */
	public String getLayerName() {
		return layerName;
	}

	/**
	 * GeoLayer명 설정
	 * 
	 * @param layerName
	 *            GeoLayer명
	 */
	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	/**
	 * GeoLayer에 해당하는 SimpleFeatureCollection 객체 반환
	 * 
	 * @return SimpleFeatureCollection
	 */
	public SimpleFeatureCollection getSimpleFeatureCollection() {
		return simpleFeatureCollection;
	}

	/**
	 * GeoLayer에 해당하는 SimpleFeatureCollection 객체 설정
	 * 
	 * @param simpleFeatureCollection
	 *            GeoLayer에 해당하는 SimpleFeatureCollection 객체
	 */
	public void setSimpleFeatureCollection(SimpleFeatureCollection simpleFeatureCollection) {
		this.simpleFeatureCollection = simpleFeatureCollection;
	}

}
