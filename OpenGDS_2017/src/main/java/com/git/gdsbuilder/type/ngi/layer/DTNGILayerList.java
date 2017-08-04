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

package com.git.gdsbuilder.type.ngi.layer;

import java.util.ArrayList;

/**
 * QA20LayerList 정보를 담고 있는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:33:30
 */
public class DTNGILayerList extends ArrayList<DTNGILayer> {

	/**
	 * layerID에 해당하는 QA20Layer 객체 반환 
	 * @author DY.Oh 
	 * @Date 2017. 5. 11. 오후 6:42:02 
	 * @param layerID 
	 * @return QA20Layer 
	 * @throws
	 */
	public DTNGILayer getQA20Layer(String layerID) {

		for (int i = 0; i < this.size(); i++) {
			DTNGILayer layer = this.get(i);
			if (layerID.equals(layer.getLayerID())) {
				return layer;
			}
		}
		return null;
	}

	/**
	 * layerID와 type에 해당하는 QA20Layer 객체의 유무 확인 
	 * @author DY.Oh 
	 * @Date 2017. 5. 11. 오후 6:42:14 
	 * @param layerName 
	 * @param type
	 * @return boolean 
	 * @throws
	 */
	public boolean isEqualsLayer(String id, String type) {
		
		for (int i = 0; i < this.size(); i++) {
			DTNGILayer layer = this.get(i);
			String layerID = layer.getLayerID();
			if (layerID.equals(id + "_" + type)) {
				return true;
			}
		}
		return false;
	}
}