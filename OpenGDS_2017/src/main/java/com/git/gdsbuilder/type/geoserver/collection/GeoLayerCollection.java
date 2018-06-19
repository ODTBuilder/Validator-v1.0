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

package com.git.gdsbuilder.type.geoserver.collection;

import java.util.ArrayList;
import java.util.List;

import com.git.gdsbuilder.FileRead.en.EnFileFormat;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayer;

/**
 * GeoLayerCollection 객체 정보를 담고있는 클래스. 검수 대상 레이어 목록, 검수 대상영역 등의 정보가 포함됨.
 * 
 * @author DY.Oh
 *
 */
public class GeoLayerCollection {

	/**
	 * GeoLayerCollection명, 파일명
	 */
	String collectionName;
	/**
	 * 검수 영역 GeoLayer
	 */
	GeoLayer neatLine;
	/**
	 * GeoLayer 목록
	 */
	List<GeoLayer> layers;
	/**
	 * GeoLayerCollection 파일 포맷
	 */
	EnFileFormat fileFormat;

	/**
	 * GeoLayerCollection 생성자
	 */
	public GeoLayerCollection() {
		super();
		this.collectionName = "";
		this.neatLine = new GeoLayer();
		this.layers = new ArrayList<GeoLayer>();
		this.fileFormat = fileFormat;
	}

	/**
	 * GeoLayerCollection 생성자
	 * 
	 * @param collectionName
	 *            GeoLayerCollection명
	 */
	public GeoLayerCollection(String collectionName) {
		this.collectionName = collectionName;
		this.neatLine = new GeoLayer();
		this.layers = new ArrayList<GeoLayer>();
	}

	/**
	 * GeoLayerCollection 생성자
	 * 
	 * @param collectionName
	 *            GeoLayerCollection명
	 * @param neatLine
	 *            검수 영역 GeoLayer
	 * @param layers
	 *            GeoLayer 목록
	 */
	public GeoLayerCollection(String collectionName, GeoLayer neatLine, List<GeoLayer> layers) {
		super();
		this.collectionName = collectionName;
		this.neatLine = neatLine;
		this.layers = layers;
	}

	/**
	 * GeoLayerCollection명 반환
	 * 
	 * @return String
	 */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * GeoLayerCollection명 설정
	 * 
	 * @param collectionName
	 *            GeoLayerCollection명
	 */
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	/**
	 * GeoLayerCollection 파일 포맷 반환
	 * 
	 * @return EnFileFormat
	 */
	public EnFileFormat getFileFormat() {
		return fileFormat;
	}

	/**
	 * GeoLayerCollection 파일 포맷 설정
	 * 
	 * @param fileFormat
	 *            GeoLayerCollection 파일 포맷
	 */
	public void setFileFormat(EnFileFormat fileFormat) {
		this.fileFormat = fileFormat;
	}

	/**
	 * 검수 영역 GeoLayer
	 * 
	 * @return GeoLayer
	 */
	public GeoLayer getNeatLine() {
		return neatLine;
	}

	/**
	 * 검수 영역 GeoLayer 설정
	 * 
	 * @param neatLine
	 *            검수 영역 GeoLayer
	 */
	public void setNeatLine(GeoLayer neatLine) {
		this.neatLine = neatLine;
	}

	/**
	 * GeoLayer 목록 반환
	 * 
	 * @return List<GeoLayer>
	 */
	public List<GeoLayer> getLayers() {
		return layers;
	}

	/**
	 * GeoLayer 목록 설정
	 * 
	 * @param layers
	 *            GeoLayer 목록
	 */
	public void setLayers(List<GeoLayer> layers) {
		this.layers = layers;
	}

	/**
	 * GeoLayer 목록에 GeoLayer 추가
	 * 
	 * @param layer
	 *            GeoLayer
	 */
	public void addValidateLayer(GeoLayer layer) {
		layers.add(layer);
	}

	/**
	 * geoLayerCollection에서 geoLayerName에 해당하는 GeoLayer 객체를 반환
	 * 
	 * @param geoLayerName
	 *            GeoLayer명
	 * @param geoLayerCollection
	 *            GeoLayerCollection
	 * @return GeoLayer
	 */
	public GeoLayer getLayer(String geoLayerName, GeoLayerCollection geoLayerCollection) {

		GeoLayer layer = null;
		List<GeoLayer> layers = geoLayerCollection.getLayers();
		for (int i = 0; i < layers.size(); i++) {
			GeoLayer tmp = layers.get(i);
			String validateLayerName = tmp.getLayerName();
			if (validateLayerName.equalsIgnoreCase(geoLayerName)) {
				layer = tmp;
				break;
			} else {
				continue;
			}
		}
		return layer;
	}

	/**
	 * GeoLayerCollection의 파일 포맷 반환
	 * 
	 * @return String
	 */
	public String getLayerCollectionType() {
		return this.fileFormat.getStateName();
	}
}
