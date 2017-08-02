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

package com.git.gdsbuilder.type.qa20.layer;

import java.util.ArrayList;
import java.util.List;

import com.git.gdsbuilder.type.qa20.feature.QA20Feature;
import com.git.gdsbuilder.type.qa20.feature.QA20FeatureList;
import com.git.gdsbuilder.type.qa20.header.NDAHeader;
import com.git.gdsbuilder.type.qa20.header.NGIHeader;

/**
 * QA20Layer 정보를 담고 있는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:33:30
 */
public class QA20Layer {

	String layerID;
	String layerName;
	String originLayerName;
	NGIHeader ngiHeader;
	NDAHeader ndaHeader;
	String layerType;
	QA20FeatureList features;
	List<String> originFieldNams; 

	/**
	 * QA20Layer 생성자
	 */
	public QA20Layer() {
		super();
		this.layerID = "";
		this.layerName = "";
		this.originLayerName = "";
		this.layerType = "";
		this.ngiHeader = new NGIHeader();
		this.ndaHeader = new NDAHeader();
		this.features = new QA20FeatureList();
		this.originFieldNams = new ArrayList<String>();
	}

	/**
	 * QA20Layer 생성자
	 * 
	 * @param layerID
	 * @param isNDA
	 */
	public QA20Layer(String layerID, boolean isNDA) {
		super();
		this.layerID = layerID;
		this.layerName = "";
		this.originLayerName = "";
		this.layerType = "";
		if (isNDA) {
			this.ndaHeader = new NDAHeader();
		}
		this.ngiHeader = new NGIHeader();
		this.features = new QA20FeatureList();
	}

	/**
	 * QA20Layer 생성자
	 * 
	 * @param layerID
	 * @param layerName
	 * @param ngiHeader
	 * @param ndaHeader
	 * @param layerType
	 * @param features
	 */
	public QA20Layer(String layerID, String layerName, NGIHeader ngiHeader, NDAHeader ndaHeader, String layerType,
			QA20FeatureList features) {
		super();
		this.layerID = layerID;
		this.layerName = layerName;
		this.ngiHeader = ngiHeader;
		this.ndaHeader = ndaHeader;
		this.layerType = layerType;
		this.features = features;
	}

	/**
	 * layerID getter @author DY.Oh @Date 2017. 3. 11. 오후 2:50:52 @return
	 * String @throws
	 */
	public String getLayerID() {
		return layerID;
	}

	/**
	 * layerID setter @author DY.Oh @Date 2017. 3. 11. 오후 2:50:54 @param layerID
	 * void @throws
	 */
	public void setLayerID(String layerID) {
		this.layerID = layerID;
	}

	/**
	 * layerName getter @author DY.Oh @Date 2017. 3. 11. 오후 2:50:58 @return
	 * String @throws
	 */
	public String getLayerName() {
		return layerName;
	}

	public String getOriginLayerName() {
		return originLayerName;
	}

	public void setOriginLayerName(String originLayerName) {
		this.originLayerName = originLayerName;
	}

	/**
	 * layerName setter @author DY.Oh @Date 2017. 3. 11. 오후 2:51:00 @param
	 * layerName void @throws
	 */
	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	/**
	 * ngiHeader getter @author DY.Oh @Date 2017. 3. 11. 오후 2:51:04 @return
	 * NGIHeader @throws
	 */
	public NGIHeader getNgiHeader() {
		return ngiHeader;
	}

	/**
	 * ngiHeader setter @author DY.Oh @Date 2017. 3. 11. 오후 2:51:06 @param
	 * ngiHeader void @throws
	 */
	public void setNgiHeader(NGIHeader ngiHeader) {
		this.ngiHeader = ngiHeader;
	}

	/**
	 * ndaHeader getter @author DY.Oh @Date 2017. 3. 11. 오후 2:51:07 @return
	 * NDAHeader @throws
	 */
	public NDAHeader getNdaHeader() {
		return ndaHeader;
	}

	/**
	 * ndaHeader setter @author DY.Oh @Date 2017. 3. 11. 오후 2:51:09 @param
	 * ndaHeader void @throws
	 */
	public void setNdaHeader(NDAHeader ndaHeader) {
		this.ndaHeader = ndaHeader;
	}

	/**
	 * layerType getter @author DY.Oh @Date 2017. 3. 11. 오후 2:51:11 @return
	 * String @throws
	 */
	public String getLayerType() {
		return layerType;
	}

	/**
	 * layerType setter @author DY.Oh @Date 2017. 3. 11. 오후 2:51:13 @param
	 * layerType void @throws
	 */
	public void setLayerType(String layerType) {
		this.layerType = layerType;
	}

	/**
	 * features getter @author DY.Oh @Date 2017. 3. 11. 오후 2:51:15 @return
	 * QA20FeatureList @throws
	 */
	public QA20FeatureList getFeatures() {
		return features;
	}

	/**
	 * features setter @author DY.Oh @Date 2017. 3. 11. 오후 2:51:17 @param
	 * features void @throws
	 */
	public void setFeatures(QA20FeatureList features) {
		this.features = features;
	}

	/**
	 * features에 feature를 더함 @author DY.Oh @Date 2017. 3. 11. 오후 2:51:18 @param
	 * feature void @throws
	 */
	public void add(QA20Feature feature) {
		features.add(feature);
	}
}
