package com.git.opengds.parser.error;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.git.gdsbuilder.type.dxf.collection.DTDXFLayerCollection;
import com.git.gdsbuilder.type.dxf.feature.DTDXFFeature;
import com.git.gdsbuilder.type.dxf.feature.DTDXFFeatureList;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayer;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayerList;
import com.git.gdsbuilder.type.dxf.structure.DTDXFEntities;
import com.git.gdsbuilder.type.dxf.structure.DTDXFHeader;
import com.git.gdsbuilder.type.dxf.structure.DTDXFTables;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class DXFLayerExportParser {

	protected static String errIdx = "err_idx";
	protected static String featureId = "feature_id";
	protected static String coorX = "x_coordinate";
	protected static String coorY = "y_coordinate";

	protected static String defaultLayerType = "POINT";
	protected static String defaultFeatureType = "POINT";

	public static DTDXFLayer parseQA10ErrorLayer(String tableName, List<HashMap<String, Object>> errFeatureList) {

		DTDXFLayer qa10Layer = new DTDXFLayer();
		qa10Layer.setLayerID(tableName);
		qa10Layer.setLayerType(defaultLayerType);

		GeometryFactory gf = new GeometryFactory();
		for (int i = 0; i < errFeatureList.size(); i++) {
			HashMap<String, Object> errFeature = errFeatureList.get(i);
			String idx = String.valueOf(errFeature.get(errIdx));
			BigDecimal x = (BigDecimal) errFeature.get(coorX);
			BigDecimal y = (BigDecimal) errFeature.get(coorY);
			Geometry errGeom = gf.createPoint(new Coordinate(x.doubleValue(), y.doubleValue()));
			DTDXFFeature qa10Feature = new DTDXFFeature(idx, defaultFeatureType, errGeom);
			qa10Layer.addQA10Feature(qa10Feature);
		}
		return qa10Layer;
	}

	private static DTDXFLayerCollection setDefaultDXFFileSection(DTDXFLayerCollection qa10LayerCollection) {

		DTDXFLayerList layerList = qa10LayerCollection.getQa10Layers();

		DTDXFHeader header = new DTDXFHeader();
		header.setDefaultHeaderValues();
		DTDXFTables tables = new DTDXFTables();
		tables.setDefaultLineTypeValues();
		tables.setDefaultStyleValues();
		tables.setLayerValues(layerList);
		DTDXFEntities entities = new DTDXFEntities();
		entities.setPointValues(layerList);

		qa10LayerCollection.setHeader(header);
		qa10LayerCollection.setTables(tables);
		qa10LayerCollection.setEntities(entities);

		return qa10LayerCollection;
	}

	public static DTDXFLayer parseQA10Layer(String layerId, List<HashMap<String, Object>> featuresMapList)
			throws ParseException {

		DTDXFLayer qa10Layer = new DTDXFLayer();
		qa10Layer.setLayerID(layerId);
		DTDXFFeatureList featureList = new DTDXFFeatureList();
		for (int i = 0; i < featuresMapList.size(); i++) {
			HashMap<String, Object> featureMap = featuresMapList.get(i);
			String featureId = (String) featureMap.get("feature_id");
			String featureType = (String) featureMap.get("feature_type");
			String geomStr = String.valueOf(featureMap.get("geom"));
			WKTReader reader = new WKTReader();
			Geometry geom = reader.read(geomStr);
			DTDXFFeature feature = new DTDXFFeature(featureId, featureType, geom);
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
