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

package com.git.gdsbuilder.type.ngi.feature;

import java.util.HashMap;
import java.util.Hashtable;

import com.vividsolutions.jts.geom.Geometry;

/**
 * QA20Feature 정보를 담고 있는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:33:30
 */
public class DTNGIFeature {

	String featureID;
	String featureType;
	String numparts;
	String coordinateSize;
	Geometry geom;
	String styleID;
	String text;
	HashMap<String, Object> properties;

	public DTNGIFeature(String featureID, String featureType, String numparts, String coordinateSize, Geometry geom,
			String styleID, String text, HashMap<String, Object> properties) {
		super();
		this.featureID = featureID;
		this.featureType = featureType;
		this.numparts = numparts;
		this.coordinateSize = coordinateSize;
		this.geom = geom;
		this.styleID = styleID;
		this.text = text;
		this.properties = properties;
	}

	/**
	 * QA20Feature 생성자
	 * 
	 * @param featureID
	 * @param featureType
	 * @param numparts
	 * @param coordinateSize
	 * @param geom
	 * @param styleID
	 */
	public DTNGIFeature(String featureID, String featureType, String numparts, String coordinateSize, Geometry geom,
			String styleID) {
		super();
		this.featureID = featureID;
		this.featureType = featureType;
		this.numparts = numparts;
		this.coordinateSize = coordinateSize;
		this.geom = geom;
		this.styleID = styleID;
		this.properties = new HashMap<String, Object>();
	}

	/**
	 * QA20Feature 생성자
	 * 
	 * @param featureID
	 * @param featureType
	 * @param numparts
	 * @param coordinateSize
	 * @param geom
	 * @param styleID
	 * @param properties
	 */
	public DTNGIFeature(String featureID, String featureType, String numparts, String coordinateSize, Geometry geom,
			String styleID, HashMap<String, Object> properties) {
		super();
		this.featureID = featureID;
		this.featureType = featureType;
		this.numparts = numparts;
		this.coordinateSize = coordinateSize;
		this.geom = geom;
		this.styleID = styleID;
		this.properties = properties;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	/**
	 * featureID getter @author DY.Oh @Date 2017. 3. 11. 오후 2:33:37 @return
	 * String @throws
	 */
	public String getFeatureID() {
		return featureID;
	}

	/**
	 * featureID setter @author DY.Oh @Date 2017. 3. 11. 오후 2:33:40 @param
	 * featureID void @throws
	 */
	public void setFeatureID(String featureID) {
		this.featureID = featureID;
	}

	/**
	 * featureType getter @author DY.Oh @Date 2017. 3. 11. 오후 2:33:42 @return
	 * String @throws
	 */
	public String getFeatureType() {
		return featureType;
	}

	/**
	 * featureType setter @author DY.Oh @Date 2017. 3. 11. 오후 2:33:43 @param
	 * featureType void @throws
	 */
	public void setFeatureType(String featureType) {
		this.featureType = featureType;
	}

	/**
	 * geom getter @author DY.Oh @Date 2017. 3. 11. 오후 2:33:46 @return
	 * Geometry @throws
	 */
	public Geometry getGeom() {
		return geom;
	}

	/**
	 * geom setter @author DY.Oh @Date 2017. 3. 11. 오후 2:33:48 @param geom
	 * void @throws
	 */
	public void setGeom(Geometry geom) {
		this.geom = geom;
	}

	/**
	 * properties getter @author DY.Oh @Date 2017. 3. 11. 오후 2:33:50 @return
	 * Hashtable<String,Object> @throws
	 */
	public HashMap<String, Object> getProperties() {
		return properties;
	}

	/**
	 * properties setter @author DY.Oh @Date 2017. 3. 11. 오후 2:33:52 @param
	 * properties void @throws
	 */
	public void setProperties(HashMap<String, Object> properties) {
		this.properties = properties;
	}

	/**
	 * numparts getter @author DY.Oh @Date 2017. 3. 11. 오후 2:33:54 @return
	 * String @throws
	 */
	public String getNumparts() {
		return numparts;
	}

	/**
	 * numparts setter @author DY.Oh @Date 2017. 3. 11. 오후 2:33:56 @param
	 * numparts void @throws
	 */
	public void setNumparts(String numparts) {
		this.numparts = numparts;
	}

	/**
	 * styleID getter @author DY.Oh @Date 2017. 3. 11. 오후 2:33:58 @return
	 * String @throws
	 */
	public String getStyleID() {
		return styleID;
	}

	/**
	 * styleID setter @author DY.Oh @Date 2017. 3. 11. 오후 2:34:00 @param styleID
	 * void @throws
	 */
	public void setStyleID(String styleID) {
		this.styleID = styleID;
	}

	/**
	 * coordinateSize getter @author DY.Oh @Date 2017. 3. 11. 오후 2:34:02 @return
	 * String @throws
	 */
	public String getCoordinateSize() {
		return coordinateSize;
	}

	/**
	 * coordinateSize setter @author DY.Oh @Date 2017. 3. 11. 오후 2:34:04 @param
	 * coordinateSize void @throws
	 */
	public void setCoordinateSize(String coordinateSize) {
		this.coordinateSize = coordinateSize;
	}

	/**
	 * properties에 value를 더함 @author DY.Oh @Date 2017. 3. 11. 오후 2:34:06 @param
	 * key @param value void @throws
	 */
	public void putProperty(String key, Object value) {
		properties.put(key, value);
	}

	/**
	 * properties에 properties를 더함 @author DY.Oh @Date 2017. 3. 11. 오후
	 * 2:34:09 @param properties void @throws
	 */
	public void putAllProperty(Hashtable<String, Object> properties) {
		properties.putAll(properties);
	}

}
