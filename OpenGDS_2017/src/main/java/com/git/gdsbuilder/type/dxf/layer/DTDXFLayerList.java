package com.git.gdsbuilder.type.dxf.layer;

import java.util.ArrayList;

public class DTDXFLayerList extends ArrayList<DTDXFLayer> {

	public DTDXFLayer getQA10Layer(String layerID) {

		for (int i = 0; i < this.size(); i++) {
			DTDXFLayer layer = this.get(i);
			if (layerID.equals(layer.getLayerID())) {
				return layer;
			}
		}
		return null;
	}

}
