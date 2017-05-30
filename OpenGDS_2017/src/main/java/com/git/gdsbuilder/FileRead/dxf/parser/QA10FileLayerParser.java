package com.git.gdsbuilder.FileRead.dxf.parser;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.geotools.feature.SchemaException;
import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFLayer;

import com.git.gdsbuilder.type.qa10.en.EnDXFArc;
import com.git.gdsbuilder.type.qa10.en.EnDXFCircle;
import com.git.gdsbuilder.type.qa10.en.EnDXFCommon;
import com.git.gdsbuilder.type.qa10.en.EnDXFInsert;
import com.git.gdsbuilder.type.qa10.en.EnDXFLWPolyline;
import com.git.gdsbuilder.type.qa10.en.EnDXFPolyline;
import com.git.gdsbuilder.type.qa10.en.EnDXFText;
import com.git.gdsbuilder.type.qa10.feature.QA10Feature;
import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa10.layer.QA10LayerList;

public class QA10FileLayerParser {

	public static QA10LayerList parseDTLayer(DXFLayer dxfLayer) throws SchemaException {

		QA10LayerList dtlayers = new QA10LayerList();
		Iterator typeIterator = dxfLayer.getDXFEntityTypeIterator();
		while (typeIterator.hasNext()) {
			String type = (String) typeIterator.next();
			String layerId = dxfLayer.getName() + "_" + type;
			QA10Layer dtlayer = new QA10Layer(layerId);
			dtlayer.setLayerType(type);
			dtlayer.setLayerColumns(getLayerColumns(type));
			List<DXFEntity> dxfEntities = (List<DXFEntity>) dxfLayer.getDXFEntities(type);
			boolean typeValid = true;
			for (int i = 0; i < dxfEntities.size(); i++) {
				DXFEntity dxfEntity = dxfEntities.get(i);
				QA10Feature feature = new QA10Feature(dxfEntity.getID());
				if (type.equals("LINE")) {
					feature = QA10FileFeatureParser.parseDTLineFeaeture(dxfEntity);
					dtlayer.setLayerType("LineString");
				} else if (type.equals("POLYLINE")) {
					feature = QA10FileFeatureParser.parseDTPolylineFeature(dxfEntity);
					dtlayer.setLayerType("LineString");
					// dtlayer.addQA10Feature(feature);
				} else if (type.equals("LWPOLYLINE")) {
					feature = QA10FileFeatureParser.parseDTLWPolylineFeature(dxfEntity);
					dtlayer.setLayerType("LineString");
				} else if (type.equals("INSERT")) {
					feature = QA10FileFeatureParser.parseDTInsertFeature(dxfEntity);
					dtlayer.setLayerType("Point");
				} else if (type.equals("TEXT")) {
					feature = QA10FileFeatureParser.parseDTTextFeature(dxfEntity);
					dtlayer.setLayerType("Point");
				} else if (type.equals("SOLID")) {
					feature = QA10FileFeatureParser.parseDTSolidFeature(dxfEntity);
					dtlayer.setLayerType("Polygon");
				} else if (type.equals("CIRCLE")) {
					feature = QA10FileFeatureParser.parseDTCircleFeature(dxfEntity);
					dtlayer.setLayerType("Polygon");
				} else if (type.equals("ARC")) {
					feature = QA10FileFeatureParser.parseDTArcFeature(dxfEntity);
					dtlayer.setLayerType("LineString");
				} else {
					typeValid = false;
					continue;
				}
				dtlayer.addQA10Feature(feature);
			}
			if (typeValid) {
				dtlayers.add(dtlayer);
			} else {
				continue;
			}
		}
		return dtlayers;
	}

	private static Hashtable<String, Object> getLayerColumns(String type) {

		Hashtable<String, Object> columns = new Hashtable<String, Object>();

		if (type.equals("LINE")) {
		} else if (type.equals("POLYLINE")) {
			columns.putAll(EnDXFPolyline.getPolylineColumns());
		} else if (type.equals("LWPOLYLINE")) {
			columns.putAll(EnDXFLWPolyline.getLwPolylineColumns());
		} else if (type.equals("INSERT")) {
			columns.putAll(EnDXFInsert.getInsertColumns());
		} else if (type.equals("TEXT")) {
			columns.putAll(EnDXFText.getTextColumns());
		} else if (type.equals("SOLID")) {
		} else if (type.equals("CIRCLE")) {
			columns.putAll(EnDXFCircle.getCircleColumns());
		} else if (type.equals("ARC")) {
			columns.putAll(EnDXFArc.getArcColumns());
		}
		return columns;
	}

}
