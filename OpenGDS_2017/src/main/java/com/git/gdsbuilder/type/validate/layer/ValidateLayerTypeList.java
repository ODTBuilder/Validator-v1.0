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

	List<String> layerIDList = new ArrayList<String>();

	public List<String> getLayerIDList() {
		return layerIDList;
	}

	public void setLayerIDList(List<String> layerIDList) {
		this.layerIDList = layerIDList;
	}

	public void addAllLayerIdList(List<String> list) {
		this.layerIDList.addAll(list);
	}

	public void addLayerId(String layerID) {
		this.layerIDList.add(layerID);
	}
	
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
