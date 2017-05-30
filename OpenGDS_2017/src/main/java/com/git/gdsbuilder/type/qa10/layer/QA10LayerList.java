package com.git.gdsbuilder.type.qa10.layer;

import java.util.ArrayList;

public class QA10LayerList extends ArrayList<QA10Layer> {

	public QA10Layer getQA10Layer(String layerID) {

		for (int i = 0; i < this.size(); i++) {
			QA10Layer layer = this.get(i);
			if (layerID.equals(layer.getLayerID())) {
				return layer;
			}
		}
		return null;
	}

}
