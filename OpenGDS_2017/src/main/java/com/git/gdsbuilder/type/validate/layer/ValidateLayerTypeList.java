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

package com.git.gdsbuilder.type.validate.layer;

import java.util.ArrayList;
import java.util.List;

import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollection;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayer;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerList;

/**
 * ValidateLayerTypeList 정보를 담고 있는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 3:02:56
 */
public class ValidateLayerTypeList extends ArrayList<ValidateLayerType> {

	/**
	 * 검수를 수행할 레이어 이름 목록
	 */
	List<String> layerIDList = new ArrayList<String>();

	/**
	 * 검수를 수행할 레이어 이름 목록 반환
	 * 
	 * @return List<String>
	 */
	public List<String> getLayerIDList() {
		return layerIDList;
	}

	/**
	 * 검수를 수행할 레이어 이름 목록 설정
	 * 
	 * @param layerIDList
	 *            검수를 수행할 레이어 이름 목록
	 */
	public void setLayerIDList(List<String> layerIDList) {
		this.layerIDList = layerIDList;
	}

	/**
	 * 검수를 수행할 레이어 이름 목록 추가
	 * 
	 * @param layerIDList
	 *            검수를 수행할 레이어 이름 목록
	 */
	public void addAllLayerIdList(List<String> layerIDList) {
		this.layerIDList.addAll(layerIDList);
	}

	/**
	 * 검수를 수행할 레이어 이름 추가
	 * 
	 * @param layerID
	 *            검수를 수행할 레이어 이름
	 */
	public void addLayerId(String layerID) {
		this.layerIDList.add(layerID);
	}

	/**
	 * GeoLayerCollection 중 typeName에 해당되는 GeoLayerList 반환
	 * 
	 * @param typeName
	 *            ValidateLayerType의 레이어 별칭
	 * @param layerCollection
	 *            GeoLayerCollection 객체
	 * @return GeoLayerList
	 */
	public GeoLayerList getTypeLayers(String typeName, GeoLayerCollection layerCollection) {
		GeoLayerList layers = new GeoLayerList();
		for (int j = 0; j < this.size(); j++) {
			ValidateLayerType type = this.get(j);
			if (type.getTypeName().equals(typeName)) {
				List<String> names = type.getLayerIDList();
				for (int i = 0; i < names.size(); i++) {
					String name = names.get(i);
					GeoLayer geoLayer = layerCollection.getLayer(name, layerCollection);
					if (geoLayer != null) {
						layers.add(geoLayer);
					} else {
						continue;
					}
				}
			}
		}
		return layers;
	}
}
