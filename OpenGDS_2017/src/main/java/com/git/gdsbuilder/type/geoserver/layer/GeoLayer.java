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
 * layer 정보를 저장하는 클래스
 * @author DY.Oh
 * @Date 2017. 3. 11. 오전 11:49:01
 * */
public class GeoLayer {

	String layerType;
	String layerID;
	String layerName;
	SimpleFeatureCollection simpleFeatureCollection;
	
	/**
	 * Layer 생성자
	 */
	public GeoLayer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Layer 생성자
	 * @param layerType
	 * @param layerID
	 * @param layerName
	 * @param simpleFeatureCollection
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
	 * layerID getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오전 11:49:58
	 * @return String
	 * @throws
	 * */
	public String getLayerID() {
		return layerID;
	}

	/**
	 * layerID setter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오전 11:50:00
	 * @param layerID void
	 * @throws
	 * */
	public void setLayerID(String layerID) {
		this.layerID = layerID;
	}

	/**
	 * layerType getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오전 11:50:02
	 * @return String
	 * @throws
	 * */
	public String getLayerType() {
		return layerType;
	}

	/**
	 * layerType setter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오전 11:50:08
	 * @param layerType void
	 * @throws
	 * */
	public void setLayerType(String layerType) {
		this.layerType = layerType;
	}

	/**
	 * layerName getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오전 11:50:10
	 * @return String
	 * @throws
	 * */
	public String getLayerName() {
		return layerName;
	}

	/**
	 * layerName setter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오전 11:50:12
	 * @param layerName void
	 * @throws
	 * */
	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	/**
	 * simpleFeatureCollection getter
	 * @author DY.Oh
	 * @Date 2017. 5. 1. 오후 1:24:58
	 * @return SimpleFeatureCollection
	 * @throws
	 * */
	public SimpleFeatureCollection getSimpleFeatureCollection() {
		return simpleFeatureCollection;
	}

	/**
	 * simpleFeatureCollection setter
	 * @author DY.Oh
	 * @Date 2017. 5. 1. 오후 1:25:09
	 * @param simpleFeatureCollection void
	 * @throws
	 * */
	public void setSimpleFeatureCollection(SimpleFeatureCollection simpleFeatureCollection) {
		this.simpleFeatureCollection = simpleFeatureCollection;
	}

}
