package com.git.opengds.parser.error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.git.gdsbuilder.type.qa20.collection.QA20LayerCollection;
import com.git.gdsbuilder.type.qa20.feature.QA20Feature;
import com.git.gdsbuilder.type.qa20.header.NDAField;
import com.git.gdsbuilder.type.qa20.header.NDAHeader;
import com.git.gdsbuilder.type.qa20.header.NGIHeader;
import com.git.gdsbuilder.type.qa20.layer.QA20Layer;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

public class ErrorLayerNGIExportParser {
	
	
	protected static String layerID = "1";

	// ngi
	protected static String errIdx = "err_idx";
	protected static String featureId = "feature_id";
	protected static String coorX = "x_coordinate";
	protected static String coorY = "y_coordinate";

	// ngiDefaultHeader
	protected static String defaultVersion = "1";
	protected static String defaultGeometryic = "MASK(POINT,POINT)";
	protected static String defaultDim = "DIM(2)";
	protected static String defaultStyleValue = "1 SYMBOL(GM_0, 0.000000, 255)";
	protected static String defaultStyleID = "SYMBOLGATTR(1)";
	protected static String bound = "";

	protected static String defaultLayerType = "POINT";
	protected static String defaultFeatureType = "POINT";

	// nda
	protected static String collectionName = "collection_name";
	protected static String layerName = "layer_name";
	protected static String errType = "err_type";
	protected static String errName = "err_name";

	private static NGIHeader getDefaultErrorLayerNGIHeader() {

		List<String> defaultStyles = new ArrayList<String>();
		defaultStyles.add(defaultStyleValue);

		NGIHeader ngiHeader = new NGIHeader(defaultVersion, defaultGeometryic, defaultDim, bound, defaultStyles, null,
				null, null);

		return ngiHeader;
	}

	private static NDAHeader getDefaultErrorLayerNDAHeader() {

		List<NDAField> fields = new ArrayList<NDAField>();
		NDAField fieldC = new NDAField(collectionName, "STRING", "256", "0", false);
		NDAField fieldL = new NDAField(layerName, "STRING", "256", "0", false);
		NDAField fieldEt = new NDAField(errType, "STRING", "256", "0", false);
		NDAField fieldEn = new NDAField(errName, "STRING", "256", "0", false);
		fields.add(fieldC);
		fields.add(fieldL);
		fields.add(fieldEt);
		fields.add(fieldEn);

		NDAHeader ndaHeader = new NDAHeader(defaultVersion, fields);
		return ndaHeader;
	}

	public static QA20LayerCollection parseQA20LayerCollection(String tableName, List<HashMap<String, Object>> errFeatureList) {

		QA20Layer qa20Layer = new QA20Layer();
		qa20Layer.setLayerID(layerID);
		qa20Layer.setLayerName(tableName);
		qa20Layer.setLayerType(defaultLayerType);
		qa20Layer.setNgiHeader(getDefaultErrorLayerNGIHeader());
		qa20Layer.setNdaHeader(getDefaultErrorLayerNDAHeader());
		GeometryFactory gf = new GeometryFactory();
		for (int i = 0; i < errFeatureList.size(); i++) {
			HashMap<String, Object> errFeature = errFeatureList.get(i);
			String idx = String.valueOf(errFeature.get(errIdx));
			double x = (Double) errFeature.get(coorX);
			double y = (Double) errFeature.get(coorY);
			Geometry errGeom = gf.createPoint(new Coordinate(x, y));

			HashMap<String, Object> properties = new HashMap<String, Object>();
			properties.put(collectionName, errFeature.get(collectionName));
			properties.put(layerName, errFeature.get(layerName));
			properties.put(featureId, errFeature.get(featureId));
			properties.put(errType, errFeature.get(errType));
			properties.put(errName, errFeature.get(errName));

			QA20Feature qa20Feature = new QA20Feature(idx, defaultFeatureType, null, null, errGeom, defaultStyleID,
					properties);
			qa20Layer.add(qa20Feature);
		}
		QA20LayerCollection qa20LayerCollection = new QA20LayerCollection();
		qa20LayerCollection.setId(tableName);
		qa20LayerCollection.addQA20Layer(qa20Layer);

		return qa20LayerCollection;
	}

}
