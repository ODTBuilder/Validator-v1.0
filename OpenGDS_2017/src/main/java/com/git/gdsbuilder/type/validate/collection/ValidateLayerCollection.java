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
 * ValidateLayerCollection 정보를 담고 있는 클래스
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:54:35
 * */
public class ValidateLayerCollection {

	GeoLayerCollectionList layerCollectionList;
	List<ValidateLayerType> typeList;
	
	/**
	 * ValidateLayerCollection 생성자
	 * @param layerCollectionList
	 * @param typeList
	 */
	public ValidateLayerCollection(GeoLayerCollectionList layerCollectionList, List<ValidateLayerType> typeList) {
		this.layerCollectionList = layerCollectionList;
		this.typeList = typeList;
	}

	/**
	 * layerCollectionList getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:54:53
	 * @return LayerCollectionList
	 * @throws
	 * */
	public GeoLayerCollectionList getLayerCollectionList() {
		return layerCollectionList;
	}

	/**
	 * layerCollectionList setter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:54:58
	 * @param layerCollectionList void
	 * @throws
	 * */
	public void setLayerCollectionList(GeoLayerCollectionList layerCollectionList) {
		this.layerCollectionList = layerCollectionList;
	}

	/**
	 * typeList getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:55:00
	 * @return List<ValidateLayerType>
	 * @throws
	 * */
	public List<ValidateLayerType> getTypeList() {
		return typeList;
	}

	/**
	 * typeList setter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:55:01
	 * @param typeList void
	 * @throws
	 * */
	public void setTypeList(List<ValidateLayerType> typeList) {
		this.typeList = typeList;
	}

	/**
	 * layerCollection에서 typeName을 가진 LayerList를 반환
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 2:55:02
	 * @param typeName
	 * @param layerCollection
	 * @return LayerList
	 * @throws
	 * */
	public GeoLayerList getTypeLayers(String typeName, GeoLayerCollection layerCollection) {

		GeoLayerList layers = new GeoLayerList();
		for (int j = 0; j < typeList.size(); j++) {
			ValidateLayerType type = typeList.get(j);
			if (type.getTypeName().equals(typeName)) {
				List<String> names = type.getLayerIDList();
				for (int i = 0; i < names.size(); i++) {
					String name = names.get(i);
					layers.add(layerCollection.getLayer(name, layerCollection));
				}
			}
		}
		return layers;
	}
}
