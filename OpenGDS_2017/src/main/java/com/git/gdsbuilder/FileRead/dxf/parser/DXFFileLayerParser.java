package com.git.gdsbuilder.FileRead.dxf.parser;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.geotools.feature.SchemaException;
import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFLayer;

import com.git.gdsbuilder.type.dxf.en.EnDXFArc;
import com.git.gdsbuilder.type.dxf.en.EnDXFCircle;
import com.git.gdsbuilder.type.dxf.en.EnDXFInsert;
import com.git.gdsbuilder.type.dxf.en.EnDXFLWPolyline;
import com.git.gdsbuilder.type.dxf.en.EnDXFPolyline;
import com.git.gdsbuilder.type.dxf.en.EnDXFText;
import com.git.gdsbuilder.type.dxf.feature.DTDXFFeature;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayer;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayerList;

public class DXFFileLayerParser {

	public static DTDXFLayerList parseDTLayer(DXFLayer dxfLayer) throws SchemaException {

		DTDXFLayerList dtlayers = new DTDXFLayerList();
		Iterator typeIterator = dxfLayer.getDXFEntityTypeIterator();
		while (typeIterator.hasNext()) {
			String type = (String) typeIterator.next();
			String layerId = dxfLayer.getName() + "_" + type;
			DTDXFLayer dtlayer = new DTDXFLayer(layerId);
			dtlayer.setOriginLayerType(type);
			// dtlayer.setLayerColumns(getLayerColumns(type));
			List<DXFEntity> dxfEntities = (List<DXFEntity>) dxfLayer.getDXFEntities(type);
			boolean typeValid = true;
			for (int i = 0; i < dxfEntities.size(); i++) {
				DXFEntity dxfEntity = dxfEntities.get(i);
				DTDXFFeature feature = new DTDXFFeature(dxfEntity.getID());
				if (type.equals("LINE")) {
					feature = DXFFileFeatureParser.parseDTLineFeaeture(dxfEntity);
					dtlayer.setLayerType("LineString");
				} else if (type.equals("POLYLINE")) {
					feature = DXFFileFeatureParser.parseDTPolylineFeature(dxfEntity);
					dtlayer.setLayerType("LineString");
				} else if (type.equals("LWPOLYLINE")) {
					feature = DXFFileFeatureParser.parseDTLWPolylineFeature(dxfEntity);
					dtlayer.setLayerType("LineString");
				} else if (type.equals("INSERT")) {
					feature = DXFFileFeatureParser.parseDTInsertFeature(dxfEntity);
					dtlayer.setLayerType("Point");
				} else if (type.equals("TEXT")) {
					feature = DXFFileFeatureParser.parseDTTextFeature(dxfEntity);
					dtlayer.setLayerType("Point");
				} else if (type.equals("SOLID")) {
					feature = DXFFileFeatureParser.parseDTSolidFeature(dxfEntity);
					dtlayer.setLayerType("Polygon");
				} else if (type.equals("CIRCLE")) {
					feature = DXFFileFeatureParser.parseDTCircleFeature(dxfEntity);
					dtlayer.setLayerType("Polygon");
				} else if (type.equals("ARC")) {
					feature = DXFFileFeatureParser.parseDTArcFeature(dxfEntity);
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
