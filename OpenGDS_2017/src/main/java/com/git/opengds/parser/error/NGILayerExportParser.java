package com.git.opengds.parser.error;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.git.gdsbuilder.type.ngi.feature.DTNGIFeature;
import com.git.gdsbuilder.type.ngi.feature.DTNGIFeatureList;
import com.git.gdsbuilder.type.ngi.header.NDAField;
import com.git.gdsbuilder.type.ngi.header.NDAHeader;
import com.git.gdsbuilder.type.ngi.header.NGIHeader;
import com.git.gdsbuilder.type.ngi.layer.DTNGILayer;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class NGILayerExportParser {

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

	public static DTNGILayer parseQA20ErrorLayer(String tableName, List<HashMap<String, Object>> errFeatureList) {

		DTNGILayer qa20Layer = new DTNGILayer();
		qa20Layer.setLayerID(layerID);
		qa20Layer.setLayerName(tableName);
		qa20Layer.setLayerType(defaultLayerType);
		qa20Layer.setNgiHeader(getDefaultErrorLayerNGIHeader());
		qa20Layer.setNdaHeader(getDefaultErrorLayerNDAHeader());
		GeometryFactory gf = new GeometryFactory();
		for (int i = 0; i < errFeatureList.size(); i++) {
			HashMap<String, Object> errFeature = errFeatureList.get(i);
			String idx = String.valueOf(errFeature.get(errIdx));
			BigDecimal x = (BigDecimal) errFeature.get(coorX);
			BigDecimal y = (BigDecimal) errFeature.get(coorY);
			Geometry errGeom = gf.createPoint(new Coordinate(x.doubleValue(), y.doubleValue()));

			HashMap<String, Object> properties = new HashMap<String, Object>();
			properties.put(collectionName, errFeature.get(collectionName));
			properties.put(layerName, errFeature.get(layerName));
			properties.put(featureId, errFeature.get(featureId));
			properties.put(errType, errFeature.get(errType));
			properties.put(errName, errFeature.get(errName));

			DTNGIFeature qa20Feature = new DTNGIFeature(idx, defaultFeatureType, null, null, errGeom, defaultStyleID,
					properties);
			qa20Layer.add(qa20Feature);
		}
		return qa20Layer;
	}

	public static DTNGILayer parseQA20Layer(HashMap<String, Object> metaMap,
			List<HashMap<String, Object>> featuresMapList, List<HashMap<String, Object>> pointRepresenets,
			List<HashMap<String, Object>> lineRepresenets, List<HashMap<String, Object>> regionRepresenets,
			List<HashMap<String, Object>> textRepresenets, List<HashMap<String, Object>> aspatialField)
			throws ParseException {

		if (featuresMapList.size() > 0) {

			DTNGILayer qa20Layer = new DTNGILayer();

			// layerID
			String layerID = (String) metaMap.get("layer_id");
			qa20Layer.setLayerID(layerID);

			// layerName
			String layerName = (String) metaMap.get("current_layer_name");
			qa20Layer.setLayerName(layerName);

			// ngiHeader
			NGIHeader ngiHeader = parserQA20NGIHeader(metaMap, pointRepresenets, lineRepresenets, regionRepresenets,
					textRepresenets);
			qa20Layer.setNgiHeader(ngiHeader);

			// ndaHeader
			String fileVerseion = (String) metaMap.get("file_version");
			NDAHeader ndaHeader = parseQA20NDAHeader(fileVerseion, aspatialField);
			qa20Layer.setNdaHeader(ndaHeader);

			// featureList
			DTNGIFeatureList featureList = new DTNGIFeatureList();
			for (int i = 0; i < featuresMapList.size(); i++) {
				HashMap<String, Object> featureMap = featuresMapList.get(i);
				DTNGIFeature qa20Feature = parserQA20Feature(featureMap);
				featureList.add(qa20Feature);
			}
			qa20Layer.setFeatures(featureList);
			return qa20Layer;
		} else {
			return null;
		}
	}

	private static DTNGIFeature parserQA20Feature(HashMap<String, Object> featureMap) throws ParseException {

		String featureID = null;
		String featureType = null;
		String numparts = null;
		String coordinateSize = null;
		Geometry geom = null;
		String styleId = null;
		String text = null;
		HashMap<String, Object> properties = new HashMap<String, Object>();

		Iterator featureIt = featureMap.keySet().iterator();
		while (featureIt.hasNext()) {
			String featureKey = (String) featureIt.next();
			Object value = featureMap.get(featureKey);
			if (featureKey.equals("feature_id")) {
				featureID = (String) value;
			} else if (featureKey.equals("feature_type")) {
				featureType = (String) value;
			} else if (featureKey.equals("num_rings")) {
				numparts = String.valueOf(value);
			} else if (featureKey.equals("num_vertexes")) {
				coordinateSize = String.valueOf(value);
			} else if (featureKey.equals("geom")) {
				WKTReader reader = new WKTReader();
				geom = reader.read(String.valueOf(value));
			} else if (featureKey.equals("style_id")) {
				styleId = (String) value;
			} else if (featureKey.equals("text")) {
				text = (String) value;
			} else {
				properties.put(featureKey, value);
			}
		}
		DTNGIFeature qa20Feature = new DTNGIFeature(featureID, featureType, numparts, coordinateSize, geom, styleId, text,
				properties);

		return qa20Feature;
	}

	private static NGIHeader parserQA20NGIHeader(HashMap<String, Object> metaMap,
			List<HashMap<String, Object>> pointRepresenets, List<HashMap<String, Object>> lineRepresenets,
			List<HashMap<String, Object>> regionRepresenets, List<HashMap<String, Object>> textRepresenets) {

		NGIHeader ngiHeader = new NGIHeader();
		String fileVerseion = (String) metaMap.get("file_version");
		ngiHeader.setVersion(fileVerseion);
		String dim = (String) metaMap.get("ngi_dim");
		ngiHeader.setDim(dim);
		String bound = (String) metaMap.get("ngi_bound");
		ngiHeader.setBound(bound);

		List<String> typeList = new ArrayList<String>();
		if (pointRepresenets != null) {
			List<String> pointReps = new ArrayList<String>();
			for (int i = 0; i < pointRepresenets.size(); i++) {
				HashMap<String, Object> pointRepMap = pointRepresenets.get(i);
				String pointRep = (String) pointRepMap.get("p_rep_value");
				pointReps.add(pointRep);
			}
			if (pointReps.size() > 0) {
				ngiHeader.setPoint_represent(pointReps);
				typeList.add("POINT");
			}
		}
		if (lineRepresenets != null) {
			List<String> lineReps = new ArrayList<String>();
			for (int i = 0; i < lineRepresenets.size(); i++) {
				HashMap<String, Object> lineRepMap = lineRepresenets.get(i);
				String pointRep = (String) lineRepMap.get("l_rep_value");
				lineReps.add(pointRep);
			}
			if (lineReps.size() > 0) {
				ngiHeader.setLine_represent(lineReps);
				typeList.add("LINESTRING");
			}
		}
		if (regionRepresenets != null) {
			List<String> regionReps = new ArrayList<String>();
			for (int i = 0; i < regionRepresenets.size(); i++) {
				HashMap<String, Object> regionRepMap = regionRepresenets.get(i);
				String regionRep = (String) regionRepMap.get("r_rep_value");
				regionReps.add(regionRep);
			}
			if (regionReps.size() > 0) {
				ngiHeader.setRegion_represent(regionReps);
				typeList.add("POLYGON");
			}
		}

		if (textRepresenets != null) {
			List<String> textReps = new ArrayList<String>();
			for (int i = 0; i < textRepresenets.size(); i++) {
				HashMap<String, Object> textRepMap = textRepresenets.get(i);
				String textRep = (String) textRepMap.get("t_rep_value");
				textReps.add(textRep);
			}
			if (textReps.size() > 0) {
				ngiHeader.setText_represent(textReps);
				typeList.add("TEXT");
			}

		}

		int size = typeList.size();
		if (size > 0) {
			String geometricMetadata = "MASK(";
			for (int i = 0; i < typeList.size(); i++) {
				String type = typeList.get(i);
				geometricMetadata += type;
				if (i == size - 1) {
					geometricMetadata += ")";
				} else {
					geometricMetadata += ",";
				}
			}
			ngiHeader.setGeometric_metadata(geometricMetadata);
		}
		return ngiHeader;
	}

	public static NDAHeader parseQA20NDAHeader(String version, List<HashMap<String, Object>> aspatialField) {

		if (aspatialField != null) {
			List<NDAField> fields = new ArrayList<NDAField>();
			for (int i = 0; i < aspatialField.size(); i++) {
				HashMap<String, Object> fieldMap = aspatialField.get(i);
				NDAField field = new NDAField();
				field.setFieldName((String) fieldMap.get("f_name"));
				field.setType((String) fieldMap.get("f_type"));
				field.setSize(String.valueOf(fieldMap.get("f_size")));
				field.setDecimal((String) fieldMap.get("f_decimal"));
				field.setUnique((Boolean) fieldMap.get("f_isunique"));
				fields.add(field);
			}
			NDAHeader ndaHeader = new NDAHeader();
			ndaHeader.setVersion(version);
			ndaHeader.setAspatial_field_def(fields);
			return ndaHeader;
		} else {
			return null;
		}
	}
}
