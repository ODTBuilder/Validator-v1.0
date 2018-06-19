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

package com.git.gdsbuilder.type.validate.collection;

import java.util.List;

import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollection;
import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollectionList;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerList;
import com.git.gdsbuilder.type.validate.layer.ValidateLayerType;

/**
 * ValidateLayerCollection 정보를 담고 있는 클래스. 검수 수행 시 필요한 레이어 정보 및 검수 옵션 정보를 가지고 있음.
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:54:35
 */
public class ValidateLayerCollection {

	/**
	 * 검수 대상 GeoLayerCollectionList
	 */
	GeoLayerCollectionList layerCollectionList;
	/**
	 * 검수 옵션 ValidateLayerType 목록
	 */
	List<ValidateLayerType> typeList;

	/**
	 * ValidateLayerCollection 생성자
	 * 
	 * @param layerCollectionList
	 *            검수 대상
	 * @param typeList
	 *            검수 옵션
	 */
	public ValidateLayerCollection(GeoLayerCollectionList layerCollectionList, List<ValidateLayerType> typeList) {
		this.layerCollectionList = layerCollectionList;
		this.typeList = typeList;
	}

	/**
	 * 검수 대상 GeoLayerCollectionList 반환
	 * 
	 * @return GeoLayerCollectionList
	 */
	public GeoLayerCollectionList getLayerCollectionList() {
		return layerCollectionList;
	}

	/**
	 * 검수 대상 GeoLayerCollectionList 설정
	 * 
	 * @param layerCollectionList
	 *            검수 대상 GeoLayerCollectionList
	 */
	public void setLayerCollectionList(GeoLayerCollectionList layerCollectionList) {
		this.layerCollectionList = layerCollectionList;
	}

	/**
	 * 검수 옵션 ValidateLayerType 목록 반환
	 * 
	 * @return List<ValidateLayerType>
	 */
	public List<ValidateLayerType> getTypeList() {
		return typeList;
	}

	/**
	 * 검수 옵션 ValidateLayerType 목록 설정
	 * 
	 * @param typeList
	 *            검수 옵션 ValidateLayerType 목록
	 */
	public void setTypeList(List<ValidateLayerType> typeList) {
		this.typeList = typeList;
	}

	/**
	 * GeoLayerCollection에서 typeName을 가진 GeoLayerList를 반환
	 * 
	 * @param typeName
	 *            레이어 별칭
	 * @param geoLayerCollection
	 *            GeoLayerCollection 객체
	 * @return GeoLayerList
	 */
	public GeoLayerList getTypeLayers(String typeName, GeoLayerCollection geoLayerCollection) {

		GeoLayerList layers = new GeoLayerList();
		for (int j = 0; j < typeList.size(); j++) {
			ValidateLayerType type = typeList.get(j);
			if (type.getTypeName().equals(typeName)) {
				List<String> names = type.getLayerIDList();
				for (int i = 0; i < names.size(); i++) {
					String name = names.get(i);
					layers.add(geoLayerCollection.getLayer(name, geoLayerCollection));
				}
			}
		}
		return layers;
	}
}
