package com.git.gdsbuilder.validator.feature;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.type.validate.option.EdgeMatchMiss;
import com.git.gdsbuilder.type.validate.option.EntityNone;
import com.git.gdsbuilder.type.validate.option.RefAttributeMiss;
import com.git.gdsbuilder.type.validate.option.RefZValueMiss;
import com.git.gdsbuilder.validator.collection.opt.ValCollectionOption;
import com.git.gdsbuilder.validator.collection.opt.ValCollectionOption.ValCollectionOptionType;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

public class FeatureCloseCollectionValidatorImpl implements FeatureCloseCollectionValidator {

	public FeatureCloseCollectionValidatorImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<ErrorFeature> ValidateCloseCollectionTarget(SimpleFeature simpleFeature,
			List<SimpleFeature> relationSimpleFeatures, ValCollectionOption closeValidateOptions, LineString nearLine,
			double tolerence) {

		List<ErrorFeature> collectionErrors = new ArrayList<ErrorFeature>();
		GeometryFactory geometryFactory = new GeometryFactory();

		String featureIdx = simpleFeature.getID();
		Property featuerIDPro = simpleFeature.getProperty("feature_id");
		String featureID = (String) featuerIDPro.getValue();

		// if (featureID.equals("RECORD 2394")) {
		// System.out.println("");
		// }

		// 인접도엽 옵션객체 선언
		boolean matchMiss = false;
		boolean refAttributeMiss = false;
		boolean refZvalueMiss = false;
		boolean entityNon = false;
		if (closeValidateOptions != null) {
			Iterator<ValCollectionOptionType> typeItr = closeValidateOptions.keySet().iterator();
			while (typeItr.hasNext()) {
				String iteratorVal = typeItr.next().getTypeName();
				if (iteratorVal.equals(ValCollectionOptionType.EDGEMATCHMISS.getTypeName())) {
					matchMiss = true;
				}
				if (iteratorVal.equals(ValCollectionOptionType.REFATTRIBUTEMISS.getTypeName())) {
					refAttributeMiss = true;
				}
				if (iteratorVal.equals(ValCollectionOptionType.REFZVALUEMISS.getTypeName())) {
					refZvalueMiss = true;
				}
				if (iteratorVal.equals(ValCollectionOptionType.ENTITYNONE.getTypeName())) {
					entityNon = true;
				}
			}
		} else
			return null;

		Geometry targetGeometry = (Geometry) simpleFeature.getDefaultGeometry();
		if (entityNon) {
			List<SimpleFeature> validRelationSimpleFeatures = new ArrayList<SimpleFeature>();
			Geometry targetGeomBuuer = targetGeometry.buffer(tolerence * 2);
			for (SimpleFeature relationSimpleFeature : relationSimpleFeatures) {
				Geometry relationGeometry = (Geometry) relationSimpleFeature.getDefaultGeometry();
				if (!relationGeometry.intersects(targetGeomBuuer)) {
					Coordinate[] targetCoors = targetGeometry.getCoordinates();
					innerFor: for (int j = 0; j < targetCoors.length; j++) {
						Point tmpPt = geometryFactory.createPoint(targetCoors[j]);
						double reDist = nearLine.distance(tmpPt);
						if (reDist >= 0 && reDist <= tolerence) {
							ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
									EntityNone.Type.ENTITYNONE.errType(), EntityNone.Type.ENTITYNONE.errName(), tmpPt);
							collectionErrors.add(errFeature);
							break innerFor;
						}
					}
				} else {
					validRelationSimpleFeatures.add(relationSimpleFeature);
				}
			}
			relationSimpleFeatures = validRelationSimpleFeatures;
		}

		Coordinate[] tarCoors = targetGeometry.getCoordinates();
		int tarCoorsSize = tarCoors.length;
		if (targetGeometry.getGeometryType().equals("Polygon")) {
			tarCoorsSize = tarCoorsSize - 1;
		}
		List<SimpleFeature> reSimpleFeatures = new ArrayList<SimpleFeature>();
		for (int i = 0; i < tarCoorsSize; i++) {
			Point tmpPt = geometryFactory.createPoint(tarCoors[i]);
			double tarDist = nearLine.distance(tmpPt);
			if (tarDist < tolerence || tarDist == tolerence) {
				Geometry tarBuffer = tmpPt.buffer(tolerence * 2);
				boolean isError = true;
				innerFor: for (SimpleFeature relationSimpleFeature : relationSimpleFeatures) {
					Geometry relationGeometry = (Geometry) relationSimpleFeature.getDefaultGeometry();
					if (relationGeometry.intersects(tarBuffer)) {
						reSimpleFeatures.add(relationSimpleFeature);
						Coordinate[] relationCoors = relationGeometry.getCoordinates();
						int relCoorsSize = relationCoors.length;
						if (relationGeometry.getGeometryType().equals("Polygon")) {
							relCoorsSize = relCoorsSize - 1;
						}
						for (int j = 0; j < relCoorsSize; j++) {
							Point tmpRePt = geometryFactory.createPoint(relationCoors[j]);
							double reDist = nearLine.distance(tmpRePt);
							if (reDist >= 0 && reDist <= tolerence) {
								if (tmpRePt.distance(tmpPt) <= tolerence * 2) {
									isError = false;
									reSimpleFeatures.add(relationSimpleFeature);
									break innerFor;
								}
							}
						}
					}
				}
				if (matchMiss && isError) {
					ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
							EdgeMatchMiss.Type.EDGEMATCHMISS.errType(), EdgeMatchMiss.Type.EDGEMATCHMISS.errName(),
							tmpPt);
					collectionErrors.add(errFeature);
				}
			}
		}

		if (reSimpleFeatures.size() > 0) {
			if (refAttributeMiss) {
				JSONArray attributesColumns = (JSONArray) closeValidateOptions
						.get(ValCollectionOptionType.REFATTRIBUTEMISS);
				boolean isMiss = false;
				outerFor: for (int i = 0; i < reSimpleFeatures.size(); i++) {
					SimpleFeature reSimpleFeature = reSimpleFeatures.get(i);
					for (int j = 0; j < attributesColumns.size(); j++) {
						String attriKey = (String) attributesColumns.get(j);
						Property relationProperty = reSimpleFeature.getProperty(attriKey);
						Property targetProperty = simpleFeature.getProperty(attriKey);
						if (targetProperty == null || relationProperty == null) {
							isMiss = true;
							break outerFor;
						} else {
							Object rePropertyValue = relationProperty.getValue();
							Object tarPropertyValue = targetProperty.getValue();
							if (rePropertyValue == null || tarPropertyValue == null) {
								isMiss = true;
								break outerFor;
							} else {
								String reValueStr = rePropertyValue.toString();
								String tarValueStr = tarPropertyValue.toString();
								if (!reValueStr.equals(tarValueStr)) {
									isMiss = true;
									break outerFor;
								}
							}
						}
					}
					if (!isMiss) {
						break outerFor;
					}
				}
				if (isMiss) {
					Point minDistPt = null;
					for (int i = 0; i < tarCoors.length; i++) {
						Point tmpPt = geometryFactory.createPoint(tarCoors[i]);
						double tarDist = nearLine.distance(tmpPt);
						if (tarDist >= 0 && tarDist <= tolerence) {
							minDistPt = tmpPt;
							break;
						}
					}
					ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
							RefAttributeMiss.Type.RefAttributeMiss.errType(),
							RefAttributeMiss.Type.RefAttributeMiss.errName(), minDistPt);
					collectionErrors.add(errFeature);
				}
			}
			if (refZvalueMiss) {
				JSONArray attributesColumns = (JSONArray) closeValidateOptions
						.get(ValCollectionOptionType.REFZVALUEMISS);
				boolean isMiss = false;
				outerFor: for (int i = 0; i < reSimpleFeatures.size(); i++) {
					SimpleFeature reSimpleFeature = reSimpleFeatures.get(i);
					for (int j = 0; j < attributesColumns.size(); j++) {
						String attriKey = (String) attributesColumns.get(j);
						Property relationProperty = reSimpleFeature.getProperty(attriKey);
						Property targetProperty = simpleFeature.getProperty(attriKey);
						if (targetProperty == null || relationProperty == null) {
							isMiss = true;
							break outerFor;
						} else {
							Object rePropertyValue = relationProperty.getValue();
							Object tarPropertyValue = targetProperty.getValue();
							if (rePropertyValue == null || tarPropertyValue == null) {
								isMiss = true;
								break outerFor;
							} else {
								String reValueStr = rePropertyValue.toString();
								String tarValueStr = tarPropertyValue.toString();
								if (!reValueStr.equals(tarValueStr)) {
									isMiss = true;
									break outerFor;
								}
							}
						}
					}
					if (!isMiss) {
						break outerFor;
					}
				}
				if (isMiss) {
					Point minDistPt = null;
					for (int i = 0; i < tarCoors.length; i++) {
						Point tmpPt = geometryFactory.createPoint(tarCoors[i]);
						double tarDist = nearLine.distance(tmpPt);
						if (tarDist >= 0 && tarDist <= tolerence) {
							minDistPt = tmpPt;
							break;
						}
					}
					ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
							RefZValueMiss.Type.REFZVALUEMISS.errType(), RefZValueMiss.Type.REFZVALUEMISS.errName(),
							minDistPt);
					collectionErrors.add(errFeature);
				}
			}
		}
		return collectionErrors;
	}

	@Override
	public List<ErrorFeature> ValidateCloseCollectionRelation(SimpleFeature nearFeature,
			List<SimpleFeature> targetFeatureList, LineString nearLine, double tolorence) {
		// TODO Auto-generated method stub

		// Coordinate[] coords = new Coordinate[] {new Coordinate(273915.020 ,
		// 542057.470 ), new Coordinate(273914.130,542055.780), new
		// Coordinate(8, 6) };

		List<ErrorFeature> collectionErrs = new ArrayList<ErrorFeature>();
		GeometryFactory geometryFactory = new GeometryFactory();
		String featureIdx = nearFeature.getID();
		Property featureIDPro = nearFeature.getProperty("feature_id");
		String featureID = (String) featureIDPro.getValue();
		if (featureID.equals("6C310")) {
			System.out.println();
		}

		Geometry nearGeometry = (Geometry) nearFeature.getDefaultGeometry();
		Coordinate[] nearCoors = nearGeometry.getCoordinates();
		for (int i = 0; i < nearCoors.length; i++) {
			Point nearPoint = geometryFactory.createPoint(nearCoors[i]);
			double nearDist = nearLine.distance(nearPoint);
			if (nearDist < tolorence || nearDist == tolorence) {
				Geometry nearBuffer = nearPoint.buffer(tolorence * 2);
				boolean isTrue = false;
				innerFor: for (SimpleFeature targetSimpleFeature : targetFeatureList) {

					Property featureIDPro2 = targetSimpleFeature.getProperty("feature_id");
					String featureID2 = (String) featureIDPro2.getValue();
					if (featureID2.equals("6E9EF")) {
						System.out.println();
					}

					Geometry targetGeometry = (Geometry) targetSimpleFeature.getDefaultGeometry();
					if (targetGeometry.intersects(nearBuffer)) {
						Coordinate[] targetCoors = targetGeometry.getCoordinates();
						int tarCoorSize = targetCoors.length;
						if (targetGeometry.getGeometryType().equals("Polygon")) {
							tarCoorSize = tarCoorSize - 1;
						}
						for (int j = 0; j < tarCoorSize; j++) {
							Point tmpRePt = geometryFactory.createPoint(targetCoors[j]);
							double reDist = nearLine.distance(tmpRePt);
							if (reDist >= 0 && reDist <= tolorence) {
								if (tmpRePt.distance(nearPoint) <= tolorence * 2) {
									isTrue = true;
									break innerFor;
								}
							}
						}
					}
				}

				if (!isTrue) {
					ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
							EntityNone.Type.ENTITYNONE.errType(), EntityNone.Type.ENTITYNONE.errName(), nearPoint);
					collectionErrs.add(errFeature);
				}
			}
		}
		return collectionErrs;
	}
}
