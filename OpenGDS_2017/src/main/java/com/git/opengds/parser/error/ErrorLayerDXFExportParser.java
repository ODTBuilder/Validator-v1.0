package com.git.opengds.parser.error;

import java.util.HashMap;
import java.util.List;

import com.git.gdsbuilder.type.qa10.collection.QA10LayerCollection;
import com.git.gdsbuilder.type.qa10.feature.QA10Feature;
import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa10.layer.QA10LayerList;
import com.git.gdsbuilder.type.qa10.structure.QA10Entities;
import com.git.gdsbuilder.type.qa10.structure.QA10Header;
import com.git.gdsbuilder.type.qa10.structure.QA10Tables;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

public class ErrorLayerDXFExportParser {
	
	protected static String errIdx = "err_idx";
	protected static String featureId = "feature_id";
	protected static String coorX = "x_coordinate";
	protected static String coorY = "y_coordinate";
	
	protected static String defaultLayerType = "POINT";
	protected static String defaultFeatureType = "POINT";

	public static QA10LayerCollection parseQA10LayerCollection(String tableName, List<HashMap<String, Object>> errFeatureList) {

		QA10Layer qa10Layer = new QA10Layer();
		qa10Layer.setLayerID(tableName);
		qa10Layer.setLayerType(defaultLayerType);
		
		GeometryFactory gf = new GeometryFactory();
		for (int i = 0; i < errFeatureList.size(); i++) {
			HashMap<String, Object> errFeature = errFeatureList.get(i);
			String idx = String.valueOf(errFeature.get(errIdx));
			double x = (Double) errFeature.get(coorX);
			double y = (Double) errFeature.get(coorY);
			Geometry errGeom = gf.createPoint(new Coordinate(x, y));

			QA10Feature qa10Feature = new QA10Feature(idx, defaultFeatureType, errGeom);
			qa10Layer.addQA10Feature(qa10Feature);
		}
		QA10LayerCollection qa10LayerCollection = new QA10LayerCollection();
		qa10LayerCollection.setCollectionName(tableName);
		qa10LayerCollection.addQA10Layer(qa10Layer);
		qa10LayerCollection = setDXFFileSection(qa10LayerCollection);
		return qa10LayerCollection;
	}

	private static QA10LayerCollection setDXFFileSection(QA10LayerCollection qa10LayerCollection) {
		
		QA10LayerList layerList = qa10LayerCollection.getQa10Layers();
		
		QA10Header header = new QA10Header();
		header.setDefaultHeaderValues();
		QA10Tables tables = new QA10Tables();
		tables.setDefaultLineTypeValues();
		tables.setDefaultStyleValues();
		tables.setLayerValues(layerList);
		QA10Entities entities = new QA10Entities();
		entities.setPointValues(layerList);
		
		qa10LayerCollection.setHeader(header);
		qa10LayerCollection.setTables(tables);
		qa10LayerCollection.setEntities(entities);
		
		return qa10LayerCollection;
	}
}
