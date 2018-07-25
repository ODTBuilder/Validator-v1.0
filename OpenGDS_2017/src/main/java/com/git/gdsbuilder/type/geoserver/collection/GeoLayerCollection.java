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
 * LayerCollection 정보를 저장하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오전 11:45:40
 */
public class GeoLayerCollection {

	String collectionName; // 도엽번호
	GeoLayer neatLine; // 도곽
	List<GeoLayer> layers; // 레이어
	EnFileFormat fileFormat;

	/**
	 * LayerCollection 생성자
	 */
	public GeoLayerCollection() {
		super();
		this.collectionName = "";
		this.neatLine = new GeoLayer();
		this.layers = new ArrayList<GeoLayer>();
		this.fileFormat = fileFormat;
	}

	/**
	 * LayerCollection 생성자
	 * 
	 * @param collectionName
	 */
	public GeoLayerCollection(String collectionName) {
		this.collectionName = collectionName;
		this.neatLine = new GeoLayer();
		this.layers = new ArrayList<GeoLayer>();
	}

	/**
	 * LayerCollection 생성자
	 * 
	 * @param collectionName
	 * @param neatLine
	 * @param layers
	 */
	public GeoLayerCollection(String collectionName, GeoLayer neatLine, List<GeoLayer> layers) {
		super();
		this.collectionName = collectionName;
		this.neatLine = neatLine;
		this.layers = layers;
	}

	/**
	 * collectionName getter @author DY.Oh @Date 2017. 3. 11. 오전
	 * 11:46:31 @return String @throws
	 */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * collectionName setter @author DY.Oh @Date 2017. 3. 11. 오전 11:46:33 @param
	 * collectionName void @throws
	 */
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public EnFileFormat getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(EnFileFormat fileFormat) {
		this.fileFormat = fileFormat;
	}

	/**
	 *
	 * @author DY.Oh @Date 2017. 3. 11. 오전 11:46:35 @return Layer @throws
	 */
	public GeoLayer getNeatLine() {
		return neatLine;
	}

	/**
	 * neatLine getter @author DY.Oh @Date 2017. 3. 11. 오전 11:46:38 @param
	 * neatLine void @throws
	 */
	public void setNeatLine(GeoLayer neatLine) {
		this.neatLine = neatLine;
	}

	/**
	 * layers getter @author DY.Oh @Date 2017. 3. 11. 오전 11:46:40 @return
	 * List<Layer> @throws
	 */
	public List<GeoLayer> getLayers() {
		return layers;
	}

	/**
	 * layers setter @author DY.Oh @Date 2017. 3. 11. 오전 11:46:42 @param layers
	 * void @throws
	 */
	public void setLayers(List<GeoLayer> layers) {
		this.layers = layers;
	}

	/**
	 * layers에 layer를 추가함 @author DY.Oh @Date 2017. 3. 11. 오전 11:46:45 @param
	 * layer void @throws
	 */
	public void addValidateLayer(GeoLayer layer) {
		layers.add(layer);
	}

	/**
	 * layerCollection에서 layerName에 해당하는 Layer 객체를 반환 
	 * @author DY.Oh 
	 * @Date 2017. 03. 11. 오전 11:48:22 @param layerName @param layerCollection @return
	 * Layer @throws
	 */
	public GeoLayer getLayer(String layerName, GeoLayerCollection layerCollection) {

		GeoLayer layer = null;
		List<GeoLayer> layers = layerCollection.getLayers();
		for (int i = 0; i < layers.size(); i++) {
			GeoLayer tmp = layers.get(i);
			String validateLayerName = tmp.getLayerName();
			if (validateLayerName.equalsIgnoreCase(layerName)) {
				layer = tmp;
				break;
			} else {
				continue;
			}
		}
		return layer;
	}

	public String getLayerCollectionType() {
		return this.fileFormat.getStateName();
	}
}
