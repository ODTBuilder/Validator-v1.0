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
import com.git.gdsbuilder.type.geoserver.layer.GeoLayer;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerList;
import com.git.gdsbuilder.type.validate.layer.ValidateLayerType;
import com.git.gdsbuilder.type.validate.layer.ValidateLayerTypeList;

/**
 * ValidateLayerCollectionList 정보를 담고 있는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:55:59
 */
public class ValidateLayerCollectionList {

	/**
	 * 검수 대상 GeoLayerCollectionList
	 */
	GeoLayerCollectionList layerCollectionList;
	/**
	 * 검수 옵션 ValidateLayerTypeList
	 */
	ValidateLayerTypeList validateLayerTypeList;

	/**
	 * ValidateLayerCollectionList 생성자
	 * 
	 * @param layerCollectionList
	 *            검수 대상 GeoLayerCollectionList
	 * @param validateLayerTypeList
	 *            검수 옵션 ValidateLayerTypeList
	 */
	public ValidateLayerCollectionList(GeoLayerCollectionList layerCollectionList,
			ValidateLayerTypeList validateLayerTypeList) {
		this.layerCollectionList = layerCollectionList;
		this.validateLayerTypeList = validateLayerTypeList;
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
	 * 검수 옵션 ValidateLayerTypeList 반환
	 * 
	 * @return ValidateLayerTypeList
	 */
	public ValidateLayerTypeList getValidateLayerTypeList() {
		return validateLayerTypeList;
	}

	/**
	 * 검수 옵션 ValidateLayerTypeList 설정
	 * 
	 * @param validateLayerTypeList
	 *            검수 옵션 ValidateLayerTypeList
	 */
	public void setValidateLayerTypeList(ValidateLayerTypeList validateLayerTypeList) {
		this.validateLayerTypeList = validateLayerTypeList;
	}

	/**
	 * layerCollection에서 typeName을 가진 LayerList를 반환
	 * 
	 * @param typeName
	 *            레이어 별칭
	 * @param layerCollection
	 *            GeoLayerCollection 객체
	 * @return GeoLayerList
	 */
	public GeoLayerList getTypeLayers(String typeName, GeoLayerCollection layerCollection) {

		GeoLayerList layers = new GeoLayerList();
		for (int j = 0; j < validateLayerTypeList.size(); j++) {
			ValidateLayerType type = validateLayerTypeList.get(j);
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
