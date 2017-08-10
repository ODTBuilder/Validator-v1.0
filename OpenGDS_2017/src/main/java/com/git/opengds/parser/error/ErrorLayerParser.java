package com.git.opengds.parser.error;

import java.util.HashMap;
import java.util.List;

import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.type.validate.error.ErrorLayer;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

public class ErrorLayerParser {

	protected static String errIdx = "err_idx";
	protected static String featureId = "feature_id";
	protected static String coorX = "x_coordinate";
	protected static String coorY = "y_coordinate";

	protected static String layerName = "layer_name";
	protected static String errType = "err_type";
	protected static String errName = "err_name";

	public static ErrorLayer parseErrorLayer(String tableName, List<HashMap<String, Object>> errFeatureList) {

		ErrorLayer errLayer = new ErrorLayer();
		GeometryFactory gf = new GeometryFactory();
		for (int i = 0; i < errFeatureList.size(); i++) {
			HashMap<String, Object> errFeatureMap = errFeatureList.get(i);
			ErrorFeature errFeature = new ErrorFeature();
			String idx = String.valueOf(errFeatureMap.get(errIdx));
			errFeature.setFeatureID(idx);

			double x = (Double) errFeatureMap.get(coorX);
			double y = (Double) errFeatureMap.get(coorY);
			Geometry errGeom = gf.createPoint(new Coordinate(x, y));
			errFeature.setErrPoint(errGeom);

			String errorType = (String) errFeatureMap.get(errType);
			String errorName = (String) errFeatureMap.get(errName);

			errFeature.setErrType(errorType);
			errFeature.setErrName(errorName);
			errLayer.addErrorFeature(errFeature);
		}
		return errLayer;
	}
}
