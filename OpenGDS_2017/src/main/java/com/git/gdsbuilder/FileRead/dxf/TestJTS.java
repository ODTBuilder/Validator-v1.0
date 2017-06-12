package com.git.gdsbuilder.FileRead.dxf;

import java.util.Hashtable;

import org.json.simple.parser.ParseException;

import com.git.gdsbuilder.type.qa10.collection.QA10LayerCollection;
import com.git.gdsbuilder.type.qa10.feature.QA10Feature;
import com.git.gdsbuilder.type.qa10.feature.QA10FeatureList;
import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa10.layer.QA10LayerList;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;

public class TestJTS {

	public QA10LayerCollection testJTS(QA10LayerCollection collection) throws ParseException {

		QA10LayerList list = collection.getQa10Layers();
		QA10LayerList newLayerList = new QA10LayerList();
		for (int i = 0; i < list.size(); i++) {
			QA10Layer layer = list.get(i);
			QA10FeatureList featureList = layer.getQa10FeatureList();
			QA10FeatureList newFeatureList = new QA10FeatureList();
			Geometry newGeom = null;
			Hashtable<String, Object> tmpProperties = null;
			GeometryFactory factroy = new GeometryFactory();
			for (int j = 0; j < featureList.size(); j++) {
				QA10Feature feature = featureList.get(j);
				Geometry tmpGeom = feature.getGeom();
				Geometry ring = factroy.createLinearRing(tmpGeom.getCoordinates());
				Geometry polygon = factroy.createPolygon((LinearRing) ring, null);
				if (j == 0) {
					newGeom = polygon;
					tmpProperties = feature.getProperties();
				} else {
					newGeom = newGeom.union(polygon);
				}
				System.out.println("");
			}
			Geometry returnGeom = factroy.createLineString(newGeom.getCoordinates());
			QA10Feature newFeature = new QA10Feature("featureID", returnGeom.getGeometryType(), "newLayer", returnGeom,
					tmpProperties);
			newFeatureList.add(newFeature);
			QA10Layer newLayer = new QA10Layer("newLayer");
			newLayer.setLayerType(returnGeom.getGeometryType());
			newLayer.addQA10Feature(newFeature);
			newLayer.setLayerColumns(layer.getLayerColumns());
			newLayerList.add(newLayer);
		}
		QA10LayerCollection newCollection = new QA10LayerCollection("newCollection", collection.getFileName(),
				newLayerList);
		return newCollection;
	}
}
