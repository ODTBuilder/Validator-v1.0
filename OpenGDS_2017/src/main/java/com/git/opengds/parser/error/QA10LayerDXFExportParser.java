package com.git.opengds.parser.error;

import java.util.HashMap;
import java.util.List;

import com.git.gdsbuilder.type.qa10.collection.QA10LayerCollection;
import com.git.gdsbuilder.type.qa10.feature.QA10Feature;
import com.git.gdsbuilder.type.qa10.feature.QA10FeatureList;
import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa10.layer.QA10LayerList;
import com.git.gdsbuilder.type.qa10.structure.QA10Entities;
import com.git.gdsbuilder.type.qa10.structure.QA10Header;
import com.git.gdsbuilder.type.qa10.structure.QA10Tables;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class QA10LayerDXFExportParser {

	protected static String errIdx = "err_idx";
	protected static String featureId = "feature_id";
	protected static String coorX = "x_coordinate";
	protected static String coorY = "y_coordinate";

	protected static String defaultLayerType = "POINT";
	protected static String defaultFeatureType = "POINT";

	public static QA10Layer parseQA10ErrorLayer(String tableName, List<HashMap<String, Object>> errFeatureList) {

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
		return qa10Layer;
	}

	private static QA10LayerCollection setDefaultDXFFileSection(QA10LayerCollection qa10LayerCollection) {

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

	public static QA10Layer parseQA10Layer(String layerId, List<HashMap<String, Object>> featuresMapList)
			throws ParseException {

		QA10Layer qa10Layer = new QA10Layer();
		qa10Layer.setLayerID(layerId);
		QA10FeatureList featureList = new QA10FeatureList();
		for (int i = 0; i < featuresMapList.size(); i++) {
			HashMap<String, Object> featureMap = featuresMapList.get(i);
			String featureId = (String) featureMap.get("feature_id");
			String featureType = (String) featureMap.get("feature_type");
			String geomStr = String.valueOf(featureMap.get("geom"));
			WKTReader reader = new WKTReader();
			Geometry geom = reader.read(geomStr);
			QA10Feature feature = new QA10Feature(featureId, featureType, geom);
			if (featureType.equals("TEXT")) {
				String textValue = (String) featureMap.get("text_value");
				feature.setTextValue(textValue);
				double height = (double) featureMap.get("height");
				feature.setHeight(height);
				double rotate = (double) featureMap.get("rotate");
				feature.setRotate(rotate);
			}
			if (featureType.equals("LWPOLYLINE") || featureType.equals("LWPOLYLINE")) {
				double elevation = (Double) featureMap.get("elevation");
				feature.setElevation(elevation);
				int flag = (int) featureMap.get("flag");
				if (flag == 0) {
					feature.setFlag(flag);
				} else if (flag == 1 || flag == 129) {
					feature.setFlag(1);
				} else if (flag == 128) {
					feature.setFlag(8);
				}
			}
			if (featureType.equals("INSERT")) {
				double rotate = (Double) featureMap.get("rotate");
				feature.setRotate(rotate);
			}
			featureList.add(feature);
		}
		qa10Layer.setQa10FeatureList(featureList);
		return qa10Layer;
	}
}
