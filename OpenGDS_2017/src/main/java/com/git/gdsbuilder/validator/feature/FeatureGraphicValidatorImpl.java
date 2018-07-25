/*
 *    OpenGDS/Builder
 *    http://git.co.kr
 *
 *    (C) 2014-2017, GeoSpatial Information Technology(GIT)
 *    
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 3 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */

/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2012, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */

package com.git.gdsbuilder.validator.feature;

import java.util.ArrayList;
import java.util.List;

import javax.measure.quantity.Torque;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.SchemaException;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.type.validate.option.ConBreak;
import com.git.gdsbuilder.type.validate.option.ConIntersected;
import com.git.gdsbuilder.type.validate.option.ConOverDegree;
import com.git.gdsbuilder.type.validate.option.EntityDuplicated;
import com.git.gdsbuilder.type.validate.option.LayerMiss;
import com.git.gdsbuilder.type.validate.option.NodeMiss;
import com.git.gdsbuilder.type.validate.option.OutBoundary;
import com.git.gdsbuilder.type.validate.option.OverShoot;
import com.git.gdsbuilder.type.validate.option.PointDuplicated;
import com.git.gdsbuilder.type.validate.option.SelfEntity;
import com.git.gdsbuilder.type.validate.option.SmallArea;
import com.git.gdsbuilder.type.validate.option.SmallLength;
import com.git.gdsbuilder.type.validate.option.TwistedPolygon;
import com.git.gdsbuilder.type.validate.option.UselessPoint;
import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateArrays;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class FeatureGraphicValidatorImpl implements FeatureGraphicValidator {

	public ErrorFeature validateLayerMiss(SimpleFeature simpleFeature, List<String> typeNames) throws SchemaException {

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		String upperType = simpleFeature.getAttribute("feature_type").toString().toUpperCase();
		String upperMultiType = "MULTI" + upperType;
		Boolean flag = false;

		for (int i = 0; i < typeNames.size(); i++) {
			String typeName = typeNames.get(i);
			String upperTpyeName = typeName.toUpperCase();
			if (upperTpyeName.equals(upperType) || upperTpyeName.equals(upperMultiType)) {
				flag = true;
				break;
			} else {
				flag = false;
			}
		}
		if (flag == false) {
			String featureIdx = simpleFeature.getID();
			Property featuerIDPro = simpleFeature.getProperty("feature_id");
			String featureID = (String) featuerIDPro.getValue();
			ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID, LayerMiss.Type.LAYERMISS.errType(),
					LayerMiss.Type.LAYERMISS.errName(), geometry.getInteriorPoint());
			return errorFeature;
		} else {
			return null;
		}
	}

	@Override
	public List<ErrorFeature> validateConBreak(SimpleFeature simpleFeature, SimpleFeatureCollection aop,
			double tolerence) throws SchemaException {

		List<ErrorFeature> errFeatures = new ArrayList<>();

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Coordinate[] coordinates = geometry.getCoordinates();
		Coordinate start = coordinates[0];
		Coordinate end = coordinates[coordinates.length - 1];
		GeometryFactory geometryFactory = new GeometryFactory();

		if (!start.equals2D(end)) {
			SimpleFeatureIterator iterator = aop.features();
			while (iterator.hasNext()) {
				SimpleFeature aopSimpleFeature = iterator.next();
				Geometry aopGeom = (Geometry) aopSimpleFeature.getDefaultGeometry();
				if (!geometry.intersects(aopGeom)) {
					Coordinate[] temp = new Coordinate[] { start, end };
					int tempSize = temp.length;

					boolean isError = false;
					for (int i = 0; i < tempSize; i++) {
						Geometry returnGeom = geometryFactory.createPoint(temp[i]);
						if (Math.abs(returnGeom.distance(aopGeom)) > tolerence || returnGeom.crosses(aopGeom)) {
							isError = true;
						}
					}
					if (isError) {
						String featureIdx = simpleFeature.getID();
						Property featuerIDPro = simpleFeature.getProperty("feature_id");
						String featureID = (String) featuerIDPro.getValue();

						ErrorFeature errFeatureSt = new ErrorFeature(featureIdx, featureID,
								ConBreak.Type.CONBREAK.errType(), ConBreak.Type.CONBREAK.errName(),
								geometryFactory.createPoint(start));
						errFeatures.add(errFeatureSt);
						ErrorFeature errFeatureEd = new ErrorFeature(featureIdx, featureID,
								ConBreak.Type.CONBREAK.errType(), ConBreak.Type.CONBREAK.errName(),
								geometryFactory.createPoint(end));
						errFeatures.add(errFeatureEd);
					}
				}
			}
		}
		if (errFeatures.size() != 0) {
			return errFeatures;
		} else {
			return null;
		}
	}

	@Override
	public List<ErrorFeature> validateConIntersected(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ)
			throws SchemaException {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();
		GeometryFactory geometryFactory = new GeometryFactory();
		Geometry geometryI = (Geometry) simpleFeatureI.getDefaultGeometry();
		Geometry geometryJ = (Geometry) simpleFeatureJ.getDefaultGeometry();

		if (geometryI.intersects(geometryJ)) {
			Geometry returnGeom = geometryI.intersection(geometryJ);
			Coordinate[] coordinates = returnGeom.getCoordinates();
			for (int i = 0; i < coordinates.length; i++) {
				Coordinate coordinate = coordinates[i];
				Geometry intersectPoint = geometryFactory.createPoint(coordinate);
				String featureIdx = simpleFeatureI.getID();
				Property featuerIDPro = simpleFeatureI.getProperty("feature_id");
				String featureID = (String) featuerIDPro.getValue();
				ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
						ConIntersected.Type.CONINTERSECTED.errType(), ConIntersected.Type.CONINTERSECTED.errName(),
						intersectPoint);

				errFeatures.add(errFeature);
			}
			return errFeatures;
		} else {
			return null;
		}
	}

	@Override
	public List<ErrorFeature> validateConIntersected(SimpleFeature simpleFeature) {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();
		GeometryFactory geometryFactory = new GeometryFactory();
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		if (!geometry.isSimple()) {
			Coordinate[] coordinates = geometry.getCoordinates();
			for (int i = 0; i < coordinates.length - 1; i++) {
				Coordinate[] coordI = new Coordinate[] { new Coordinate(coordinates[i]),
						new Coordinate(coordinates[i + 1]) };
				LineString lineI = geometryFactory.createLineString(coordI);
				for (int j = 0; j < coordinates.length - 1; j++) {
					Coordinate[] coordJ = new Coordinate[] { new Coordinate(coordinates[j]),
							new Coordinate(coordinates[j + 1]) };
					LineString lineJ = geometryFactory.createLineString(coordJ);
					if (lineI.intersects(lineJ)) {
						Geometry intersectGeom = lineI.intersection(lineJ);
						Coordinate[] intersectCoors = intersectGeom.getCoordinates();
						for (int k = 0; k < intersectCoors.length; k++) {
							Coordinate interCoor = intersectCoors[k];
							Geometry errPoint = geometryFactory.createPoint(interCoor);
							Boolean flag = false;
							for (int l = 0; l < coordI.length; l++) {
								Coordinate coordPoint = coordI[l];
								if (interCoor.equals2D(coordPoint)) {
									flag = true;
									break;
								}
							}
							if (flag == false) {
								Property featuerIDPro = simpleFeature.getProperty("feature_id");
								String featureID = (String) featuerIDPro.getValue();
								String featureIdx = simpleFeature.getID();
								ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
										ConIntersected.Type.CONINTERSECTED.errType(),
										ConIntersected.Type.CONINTERSECTED.errName(), errPoint);
								errFeatures.add(errFeature);
							}
						}
					}
				}
			}
		}
		return errFeatures;
	}

	@Override
	public List<ErrorFeature> validateConOverDegree(SimpleFeature simpleFeature, double inputDegree)
			throws SchemaException {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Coordinate[] coordinates = geometry.getCoordinates();
		int coorSize = coordinates.length;
		for (int i = 0; i < coorSize - 2; i++) {
			Coordinate a = coordinates[i];
			Coordinate b = coordinates[i + 1];
			Coordinate c = coordinates[i + 2];
			if (!a.equals2D(b) && !b.equals2D(c) && !c.equals2D(a)) {
				double angle = Angle.toDegrees(Angle.angleBetween(a, b, c));
				if (angle < inputDegree) {
					GeometryFactory geometryFactory = new GeometryFactory();
					Point errPoint = geometryFactory.createPoint(b);
					String featureIdx = simpleFeature.getID();
					Property featuerIDPro = simpleFeature.getProperty("feature_id");
					String featureID = (String) featuerIDPro.getValue();
					ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
							ConOverDegree.Type.CONOVERDEGREE.errType(), ConOverDegree.Type.CONOVERDEGREE.errName(),
							errPoint);
					errFeatures.add(errFeature);
				}
			}
		}
		if (errFeatures.size() != 0) {
			return errFeatures;
		} else {
			return null;
		}
	}

	@Override
	public List<ErrorFeature> validateUselessPoint(SimpleFeature simpleFeature)
			throws SchemaException, NoSuchAuthorityCodeException, FactoryException, TransformException {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();

		String featureIdx = simpleFeature.getID();
		Property featuerIDPro = simpleFeature.getProperty("feature_id");
		String featureID = (String) featuerIDPro.getValue();

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Coordinate[] coors = geometry.getCoordinates();
		int coorsSize = coors.length;

		for (int i = 0; i < coorsSize - 1; i++) {
			Coordinate a = coors[i];
			Coordinate b = coors[i + 1];
			if (a.equals2D(b)) {
				continue;
			}
			boolean isAngError = false;
			if (i < coorsSize - 2) {
				// 각도 조건
				Coordinate c = coors[i + 2];
				if (!a.equals2D(b) && !b.equals2D(c) && !c.equals2D(a)) {
					double angle = Angle.toDegrees(Angle.angleBetween(a, b, c));
					if (180 - angle < 6) {
						isAngError = true;
					}
				}
			}
			boolean isDistError = false;
			if (isAngError) {
				// 길이 조건
				double tmpLength = a.distance(b);
				// double distance = JTS.orthodromicDistance(a, b, crs);

				if (tmpLength < 0.01) {
					isDistError = true;
				}
			}
			if (isDistError && isAngError) {
				GeometryFactory gFactory = new GeometryFactory();
				Geometry returnGeom = gFactory.createPoint(b);
				ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
						UselessPoint.Type.USELESSPOINT.errType(), UselessPoint.Type.USELESSPOINT.errName(), returnGeom);
				errFeatures.add(errFeature);
			}
		}
		if (errFeatures.size() != 0) {
			return errFeatures;
		} else {
			return null;
		}
	}

	@Override
	public ErrorFeature validateEntityDuplicated(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ)
			throws SchemaException {
		Geometry geometryI = (Geometry) simpleFeatureI.getDefaultGeometry();
		Geometry geometryJ = (Geometry) simpleFeatureJ.getDefaultGeometry();

		// geom 비교
		if (geometryI.equals(geometryJ)) {
			if (simpleFeatureI.getAttributeCount() != 0 && simpleFeatureJ.getAttributeCount() != 0) {
				FeatureAttributeValidator attributeValidator = new FeatureAttributeValidatorImpl();
				return attributeValidator.validateEntityDuplicated(simpleFeatureI, simpleFeatureJ);
			} else {
				String featureIdx = simpleFeatureI.getID();
				Property featuerIDPro = simpleFeatureI.getProperty("feature_id");
				String featureID = (String) featuerIDPro.getValue();
				ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
						EntityDuplicated.Type.ENTITYDUPLICATED.errType(),
						EntityDuplicated.Type.ENTITYDUPLICATED.errName(), geometryI.getInteriorPoint());
				return errFeature;
			}
		} else {
			return null;
		}
	}

	@Override
	public List<ErrorFeature> validateSelfEntity(SimpleFeature simpleFeatureI, double selfEntityLineTolerance,
			double polygonInvadedTolorence) {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();
		GeometryFactory geometryFactory = new GeometryFactory();
		Geometry geometry = (Geometry) simpleFeatureI.getDefaultGeometry();

		String featureIdx = simpleFeatureI.getID();
		Property featuerIDPro = simpleFeatureI.getProperty("feature_id");
		String featureID = (String) featuerIDPro.getValue();
		String geomIType = geometry.getGeometryType();
		if (geomIType.equals("LineString") || geomIType.equals("MultiLineString")) {
			if (!geometry.isSimple()) {
				Coordinate[] coordinates = geometry.getCoordinates();
				for (int i = 0; i < coordinates.length - 1; i++) {
					Coordinate[] coordI = new Coordinate[] { new Coordinate(coordinates[i]),
							new Coordinate(coordinates[i + 1]) };
					LineString lineI = geometryFactory.createLineString(coordI);
					for (int j = 0; j < coordinates.length - 1; j++) {
						Coordinate[] coordJ = new Coordinate[] { new Coordinate(coordinates[j]),
								new Coordinate(coordinates[j + 1]) };
						LineString lineJ = geometryFactory.createLineString(coordJ);
						if (lineI.intersects(lineJ)) {
							Geometry intersectGeom = lineI.intersection(lineJ);
							Coordinate[] intersectCoors = intersectGeom.getCoordinates();
							for (int k = 0; k < intersectCoors.length; k++) {
								Coordinate interCoor = intersectCoors[k];
								Geometry errPoint = geometryFactory.createPoint(interCoor);
								Boolean flag = false;
								for (int l = 0; l < coordI.length; l++) {
									Coordinate coordPoint = coordI[l];
									if (interCoor.equals2D(coordPoint)) {
										flag = true;
										break;
									}
								}
								if (flag == false) {
									ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
											ConIntersected.Type.CONINTERSECTED.errType(),
											ConIntersected.Type.CONINTERSECTED.errName(), errPoint);
									errFeatures.add(errFeature);
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public List<ErrorFeature> validateSelfEntity(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ,
			double selfEntityTolerance, double polygonInvadedTolorence) throws SchemaException {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();
		GeometryFactory geometryFactory = new GeometryFactory();
		Geometry geometryI = (Geometry) simpleFeatureI.getDefaultGeometry();
		Geometry geometryJ = (Geometry) simpleFeatureJ.getDefaultGeometry();

		String featureIdx = simpleFeatureI.getID();
		Property featuerIDPro = simpleFeatureI.getProperty("feature_id");
		String featureID = (String) featuerIDPro.getValue();

		String geomIType = geometryI.getGeometryType();
		Geometry returnGeom = null;
		if (geomIType.equals("Point") || geomIType.equals("MultiPoint")) {
			returnGeom = selfEntityPoint(geometryI, geometryJ);
		}
		if (geomIType.equals("LineString") || geomIType.equals("MultiLineString")) {
			returnGeom = selfEntityLineString(geometryI, geometryJ, selfEntityTolerance, polygonInvadedTolorence);
		}
		if (geomIType.equals("Polygon") || geomIType.equals("MultiPolygon")) {
			returnGeom = selfEntityPolygon(geometryI, geometryJ, selfEntityTolerance, polygonInvadedTolorence);
		}
		if (returnGeom != null) {
			String returnGeomType = returnGeom.getGeometryType().toUpperCase();
			if (returnGeomType.equals("LINESTRING")) {
				if (returnGeom.getLength() == 0.0 || returnGeom.getLength() == 0) {
					Coordinate[] coordinates = returnGeom.getCoordinates();
					Point startPoint = geometryFactory.createPoint(coordinates[0]);
					ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
							SelfEntity.Type.SELFENTITY.errType(), SelfEntity.Type.SELFENTITY.errName(), startPoint);
					errFeatures.add(errFeature);
				} else {
					ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
							SelfEntity.Type.SELFENTITY.errType(), SelfEntity.Type.SELFENTITY.errName(),
							returnGeom.getInteriorPoint());
					errFeatures.add(errFeature);
				}
			} else {
				for (int i = 0; i < returnGeom.getNumGeometries(); i++) {
					ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
							SelfEntity.Type.SELFENTITY.errType(), SelfEntity.Type.SELFENTITY.errName(),
							returnGeom.getGeometryN(i).getInteriorPoint());
					errFeatures.add(errFeature);
				}
			}

			return errFeatures;
		} else {
			return null;
		}
	}

	private Geometry selfEntityPoint(Geometry geometryI, Geometry geometryJ) {

		String typeJ = geometryJ.getGeometryType();
		Geometry returnGeom = null;
		if (typeJ.equals("Point") || typeJ.equals("MultiPoint")) {
			if (geometryI.intersects(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ);
			}
		}
		if (typeJ.equals("LineString") || typeJ.equals("MultiLineString")) {
			if (geometryI.intersects(geometryJ) || geometryI.touches(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ);
			}
		}
		if (typeJ.equals("Polygon") || typeJ.equals("MultiPolygon")) {
			if (geometryI.within(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ);
			}
		}
		return returnGeom;
	}

	private Geometry selfEntityLineString(Geometry geometryI, Geometry geometryJ, double selfEntityTolerance,
			double polygonInvadedTolorence) {
		GeometryFactory geometryFactory = new GeometryFactory();
		String typeJ = geometryJ.getGeometryType();
		Geometry returnGeom = null;
		if (typeJ.equals("Point") || typeJ.equals("MultiPoint")) {
			if (geometryI.equals(geometryJ)) {
				returnGeom = geometryI.intersection(geometryJ);
			}
		}

		if (typeJ.equals("LineString") || typeJ.equals("MultiLineString")) {
			if (geometryI.crosses(geometryJ)) {
				Geometry lineReturnGeom = null;
				lineReturnGeom = geometryI.intersection(geometryJ);
				String upperType = lineReturnGeom.getGeometryType().toString().toUpperCase();

				Coordinate[] coors = geometryI.getCoordinates();
				Coordinate firstCoor = coors[0];
				Coordinate lastCoor = coors[coors.length - 1];
				Point firstPoint = geometryFactory.createPoint(firstCoor);
				Point lastPoint = geometryFactory.createPoint(lastCoor);
				Coordinate[] coorsJ = geometryJ.getCoordinates();
				Coordinate firstCoorJ = coorsJ[0];
				Coordinate lastCoorJ = coorsJ[coorsJ.length - 1];
				Point firstPointJ = geometryFactory.createPoint(firstCoorJ);
				Point lastPointJ = geometryFactory.createPoint(lastCoorJ);

				if (upperType.equals("POINT")) {
					double firstDistance = firstPoint.distance(lineReturnGeom);
					double lastDistance = lastPoint.distance(lineReturnGeom);
					double firstDistanceJ = firstPointJ.distance(lineReturnGeom);
					double lastDistanceJ = lastPointJ.distance(lineReturnGeom);
					if (firstPoint.equals(lastPoint) && !firstPointJ.equals(lastPointJ)) {
						if (firstDistanceJ > selfEntityTolerance && lastDistanceJ > selfEntityTolerance) {
							returnGeom = lineReturnGeom;
						}
					} else if (!firstPoint.equals(lastPoint) && firstPointJ.equals(lastPointJ)) {
						if (firstDistance > selfEntityTolerance && lastDistance > selfEntityTolerance) {
							returnGeom = lineReturnGeom;
						}
					} else if (!firstPoint.equals(lastPoint) && !firstPointJ.equals(lastPointJ)) {
						if (firstDistance > selfEntityTolerance && lastDistance > selfEntityTolerance
								&& firstDistanceJ > selfEntityTolerance && lastDistanceJ > selfEntityTolerance) {
							returnGeom = lineReturnGeom;
						}
					}

				} else {
					if (firstPoint.equals(lastPoint) && firstPointJ.equals(lastPointJ)) {
						LinearRing ringI = geometryFactory.createLinearRing(coors);
						LinearRing holesI[] = null;
						Polygon polygonI = geometryFactory.createPolygon(ringI, holesI);
						LinearRing ringJ = geometryFactory.createLinearRing(coorsJ);
						LinearRing holesJ[] = null;
						Polygon polygonJ = geometryFactory.createPolygon(ringJ, holesJ);
						Geometry intersectPolygon = polygonI.intersection(polygonJ);
						if (intersectPolygon.getArea() > polygonInvadedTolorence) {
							returnGeom = lineReturnGeom;
						}
					} else if (firstPoint.equals(lastPoint) && !firstPointJ.equals(lastPointJ)) {
						List<Point> points = new ArrayList<Point>();
						Coordinate[] lineReturnCoor = lineReturnGeom.getCoordinates();
						for (int i = 0; i < lineReturnCoor.length; i++) {
							Point returnPoint = geometryFactory.createPoint(lineReturnCoor[i]);
							if (returnPoint.distance(firstPointJ) > selfEntityTolerance
									&& returnPoint.distance(lastPointJ) > selfEntityTolerance) {
								points.add(returnPoint);
							}
						}
						if (points.size() != 0) {
							Point[] pointList = new Point[points.size()];
							for (int j = 0; j < points.size(); j++) {
								pointList[j] = points.get(j);
							}
							returnGeom = geometryFactory.createMultiPoint(pointList);
						}

					} else if (!firstPoint.equals(lastPoint) && firstPointJ.equals(lastPointJ)) {
						List<Point> points = new ArrayList<Point>();
						Coordinate[] lineReturnCoor = lineReturnGeom.getCoordinates();
						for (int i = 0; i < lineReturnCoor.length; i++) {
							Point returnPoint = geometryFactory.createPoint(lineReturnCoor[i]);
							if (returnPoint.distance(firstPoint) > selfEntityTolerance
									&& returnPoint.distance(lastPoint) > selfEntityTolerance) {
								points.add(returnPoint);
							}
						}
						if (points.size() != 0) {
							Point[] pointList = new Point[points.size()];
							for (int j = 0; j < points.size(); j++) {
								pointList[j] = points.get(j);
							}
							returnGeom = geometryFactory.createMultiPoint(pointList);
						}
					}
				}
			}
		}
		if (typeJ.equals("Polygon") || typeJ.equals("MultiPolygon")) {

			if (geometryI.crosses(geometryJ.getBoundary()) || geometryI.within(geometryJ)) {
				Geometry geometry = geometryI.intersection(geometryJ);
				String upperType = geometry.getGeometryType().toUpperCase();
				if (upperType.equals("LINESTRING") || upperType.equals("MULTILINESTRING")) {
					if (geometryI.within(geometryJ)) {
						returnGeom = geometry;
					} else {
						if (geometry.getLength() > selfEntityTolerance) {
							returnGeom = geometryI.intersection(geometryJ.getBoundary());
						}
					}
				}
			}
		}
		return returnGeom;
	}

	private Geometry selfEntityPolygon(Geometry geometryI, Geometry geometryJ, double selfEntityTolerance,
			double polygonInvadedTolorence) {

		String typeJ = geometryJ.getGeometryType();
		Geometry returnGeom = null;
		try {
			if (typeJ.equals("Point") || typeJ.equals("MultiPoint")) {
				if (geometryI.within(geometryJ)) {
					returnGeom = geometryI.intersection(geometryJ);
				}
			}
			if (typeJ.equals("LineString") || typeJ.equals("MultiLineString")) {
				Geometry geom = geometryI.intersection(geometryJ);
				String upperType = geom.getGeometryType().toUpperCase();
				if (upperType.equals("LINESTRING") || upperType.equals("MULTILINESTRING")) {
					if (geom.getLength() > selfEntityTolerance) {
						if (geometryI.contains(geometryJ)) {
							returnGeom = geometryI.intersection(geometryJ);
						} else {
							returnGeom = geometryI.intersection(geometryJ.getBoundary());
						}
					}
				}
			}
			if (typeJ.equals("Polygon") || typeJ.equals("MultiPolygon")) {
				if (!geometryI.equals(geometryJ)) {
					if (geometryI.intersects(geometryJ) || geometryI.overlaps(geometryJ) || geometryI.within(geometryJ)
							|| geometryI.contains(geometryJ)) {
						Geometry geometry = geometryI.intersection(geometryJ);
						String upperType = geometry.getGeometryType().toUpperCase();
						if (upperType.equals("POLYGON") || upperType.equals("MULTIPOLYGON")) {
							if (geometry.getArea() > polygonInvadedTolorence) {
								returnGeom = geometry;
							}
						}
					}
				}
			}
			return returnGeom;
		} catch (Exception e) {
			return returnGeom;
		}
	}

	@Override
	public ErrorFeature validateSmallArea(SimpleFeature simpleFeature, double inputArea) throws SchemaException {

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		if (geometry.getGeometryType().equals("Polygon")) {
			double area = geometry.getArea();
			if (area < inputArea) {
				String featureIdx = simpleFeature.getID();
				Property featuerIDPro = simpleFeature.getProperty("feature_id");
				String featureID = (String) featuerIDPro.getValue();
				ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID, SmallArea.Type.SMALLAREA.errType(),
						SmallArea.Type.SMALLAREA.errName(), geometry.getInteriorPoint());
				return errFeature;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public ErrorFeature validateSmallLength(SimpleFeature simpleFeature, double inputLength) throws SchemaException {

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		GeometryFactory geometryFactory = new GeometryFactory();
		double length = geometry.getLength();
		if (length <= inputLength) {
			if (length == 0.0 || length == 0) {
				Coordinate[] coordinates = geometry.getCoordinates();
				Point errPoint = geometryFactory.createPoint(coordinates[0]);
				String featureIdx = simpleFeature.getID();
				Property featuerIDPro = simpleFeature.getProperty("feature_id");
				String featureID = (String) featuerIDPro.getValue();
				ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
						SmallLength.Type.SMALLLENGTH.errType(), SmallLength.Type.SMALLLENGTH.errName(), errPoint);
				return errFeature;
			} else {
				String featureIdx = simpleFeature.getID();
				Property featuerIDPro = simpleFeature.getProperty("feature_id");
				String featureID = (String) featuerIDPro.getValue();
				ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
						SmallLength.Type.SMALLLENGTH.errType(), SmallLength.Type.SMALLLENGTH.errName(),
						geometry.getInteriorPoint());
				return errFeature;
			}
		} else {
			return null;
		}
	}

	@Override
	public ErrorFeature validateOutBoundary(SimpleFeature simpleFeature, SimpleFeatureCollection relationSfc,
			double spatialAccuracyTolorence) throws SchemaException {

		// A007
		String featureIdx = simpleFeature.getID();
		Property featuerIDPro = simpleFeature.getProperty("feature_id");
		String featureID = (String) featuerIDPro.getValue();

		Geometry geom = (Geometry) simpleFeature.getDefaultGeometry();
		Coordinate[] geomCoors = geom.getCoordinates();
		int geomCoorsLength = geomCoors.length;
		boolean isErr = false;
		SimpleFeatureIterator iterator = relationSfc.features();
		while (iterator.hasNext()) {
			int trueCount = 0;
			// A001
			SimpleFeature relationSf = iterator.next();
			Geometry relationGeom = (Geometry) relationSf.getDefaultGeometry();
			if (geom.intersects(relationGeom)) {
				Coordinate[] rGeomCoors = relationGeom.getCoordinates();
				for (int i = 0; i < rGeomCoors.length - 1; i++) {
					Coordinate tmp1 = rGeomCoors[i];
					Coordinate tmp2 = rGeomCoors[i + 1];
					Coordinate[] tmpCoors = new Coordinate[] { tmp1, tmp2 };
					LineString tmpLn = new GeometryFactory().createLineString(tmpCoors);
					for (int j = 0; j < geomCoorsLength; j++) {
						Coordinate coor = geomCoors[j];
						double distance = tmpLn.distance(new GeometryFactory().createPoint(coor));
						if (distance < spatialAccuracyTolorence) {
							trueCount++;
						}
					}
				}
			}
			if (trueCount >= geomCoorsLength) {
				isErr = true;
				break;
			}
		}
		if (!isErr) {
			Geometry returnGome = geom.getCentroid();
			ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID, OutBoundary.Type.OUTBOUNDARY.errType(),
					OutBoundary.Type.OUTBOUNDARY.errName(), returnGome);
			return errFeature;
		} else {
			return null;
		}
	}

	@Override
	public List<ErrorFeature> validateOverShoot(SimpleFeature simpleFeature, SimpleFeatureCollection aop,
			double tolerence) throws SchemaException {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();

		Geometry geom = (Geometry) simpleFeature.getDefaultGeometry();
		SimpleFeatureIterator iterator = aop.features();
		while (iterator.hasNext()) {
			SimpleFeature aopSimpleFeature = iterator.next();
			Geometry aopGeom = (Geometry) aopSimpleFeature.getDefaultGeometry();
			Coordinate[] aopCoors = aopGeom.getCoordinates();
			LinearRing ring = new GeometryFactory().createLinearRing(aopCoors);
			Geometry aopPolygon = new GeometryFactory().createPolygon(ring, null);
			Coordinate[] geomCoors = geom.getCoordinates();
			for (int i = 0; i < geomCoors.length; i++) {
				Coordinate geomCoor = geomCoors[i];
				Geometry geomPt = new GeometryFactory().createPoint(geomCoor);
				if (!geomPt.within(aopPolygon)) {
					Geometry toleGeom = aopPolygon.buffer(tolerence);
					if (!geomPt.within(toleGeom)) {
						String featureIdx = simpleFeature.getID();
						Property featuerIDPro = simpleFeature.getProperty("feature_id");
						String featureID = (String) featuerIDPro.getValue();
						ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
								OverShoot.Type.OVERSHOOT.errType(), OverShoot.Type.OVERSHOOT.errName(), geomPt);
						errFeatures.add(errFeature);
					}
				}
			}
		}
		if (errFeatures.size() != 0) {
			return errFeatures;
		} else {
			return null;
		}
	}

	// 객체 꼬임 여부 검사
	public ErrorFeature validateTwistedPolygon(SimpleFeature simpleFeature) throws SchemaException {
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		if (!(geometry.isValid())) {
			GeometryFactory f = new GeometryFactory();
			Coordinate[] coordinates = geometry.getCoordinates();
			Geometry errGeometry = f.createPoint(coordinates[0]);
			String featureIdx = simpleFeature.getID();
			Property featuerIDPro = simpleFeature.getProperty("feature_id");
			String featureID = (String) featuerIDPro.getValue();
			ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
					TwistedPolygon.Type.TWISTEDPOLYGON.errType(), TwistedPolygon.Type.TWISTEDPOLYGON.errName(),
					errGeometry);
			return errorFeature;
		} else {
			return null;
		}
	}

	@Override
	public List<ErrorFeature> validatePointDuplicated(SimpleFeature simpleFeature) {

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		String geomType = geometry.getGeometryType();
		if (!geomType.equals("Point")) {
			Coordinate[] coors = geometry.getCoordinates();
			int coorLength = coors.length;
			if (coorLength == 0 || coorLength == 1) {
				return null;
			} else {
				List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();
				Property featuerIDPro = simpleFeature.getProperty("feature_id");
				String featureID = (String) featuerIDPro.getValue();

				if (coorLength == 2) {
					Coordinate coor0 = coors[0];
					Coordinate coor1 = coors[1];
					if (coor0.equals3D(coor1)) {
						// errFeature
						String featureIdx = simpleFeature.getID();
						Geometry errGeom = new GeometryFactory().createPoint(coor1);
						ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
								PointDuplicated.Type.POINTDUPLICATED.errType(),
								PointDuplicated.Type.POINTDUPLICATED.errName(), errGeom);

						errFeatures.add(errorFeature);
					}
				}
				if (coorLength > 3) {
					for (int i = 0; i < coorLength - 1; i++) {
						Coordinate coor0 = coors[i];
						Coordinate coor1 = coors[i + 1];
						if (coor0.equals3D(coor1)) {
							// errFeature
							String featureIdx = simpleFeature.getID();
							Geometry errGeom = new GeometryFactory().createPoint(coor1);
							ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
									PointDuplicated.Type.POINTDUPLICATED.errType(),
									PointDuplicated.Type.POINTDUPLICATED.errName(), errGeom);
							errFeatures.add(errorFeature);
						}
					}
				}
				return errFeatures;
			}
		}
		return null;
	}

	@Override
	public List<ErrorFeature> validateNodeMiss(SimpleFeature simpleFeature, SimpleFeatureCollection targetSfc,
			SimpleFeatureCollection relationSfc, double tolerence) throws SchemaException {

		List<ErrorFeature> errorFeatures = new ArrayList<ErrorFeature>();

		String featureIdx = simpleFeature.getID();
		Property featuerIDPro = simpleFeature.getProperty("feature_id");
		String featureID = (String) featuerIDPro.getValue();

		// a002
		Geometry tGeom = (Geometry) simpleFeature.getDefaultGeometry();
		Coordinate[] tCoors = tGeom.getCoordinates();
		Coordinate tFirCoor = tCoors[0];
		Coordinate tLasCoor = tCoors[tCoors.length - 1];

		GeometryFactory factory = new GeometryFactory();
		Geometry firPt = factory.createPoint(tFirCoor);
		Geometry lasPt = factory.createPoint(tLasCoor);

		boolean firTrue = false;
		boolean lasTrue = false;

		boolean firInter = true;
		boolean lasInter = true;

		boolean notInter = false;
		SimpleFeatureIterator rIterator = relationSfc.features();
		while (rIterator.hasNext()) {
			SimpleFeature rSf = rIterator.next();
			Geometry rGeom = (Geometry) rSf.getDefaultGeometry();
			if (!rGeom.intersects(tGeom)) {
				notInter = true;
			} else {
				notInter = false;
				Geometry boundary = rGeom.getBoundary();
				firInter = rGeom.intersects(firPt);
				if (boundary.intersects(firPt.buffer(0.2))) {
					firTrue = true;
				}
				lasInter = rGeom.intersects(lasPt);
				if (boundary.intersects(lasPt.buffer(0.2))) {
					lasTrue = true;
				}
				break;
			}
		}

		if (notInter) {
			return null;
		}

		if (firTrue && lasTrue) {
			return null;
		} else {
			boolean firErr = true;
			boolean lasErr = true;
			SimpleFeatureIterator tIterator = targetSfc.features();
			while (tIterator.hasNext()) {
				SimpleFeature tSimpleFeature = tIterator.next();
				if (featureIdx.equals(tSimpleFeature.getID())) {
					continue;
				}
				Geometry selfGeom = (Geometry) tSimpleFeature.getDefaultGeometry();
				if (!firTrue) {
					if (Math.abs(firPt.distance(selfGeom)) < tolerence) {
						firErr = false;
					}
				}
				if (!lasTrue) {
					if (Math.abs(lasPt.distance(selfGeom)) < tolerence) {
						lasErr = false;
					}
				}
			}
			if (!firTrue && firErr && firInter) {
				ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID, NodeMiss.Type.NODEMISS.errType(),
						NodeMiss.Type.NODEMISS.errName(), firPt);
				errorFeatures.add(errorFeature);
			}

			if (!lasTrue && lasErr && lasInter) {
				ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID, NodeMiss.Type.NODEMISS.errType(),
						NodeMiss.Type.NODEMISS.errName(), lasPt);
				errorFeatures.add(errorFeature);
			}
			if (errorFeatures.size() > 0) {
				return errorFeatures;
			} else {
				return null;
			}
		}
	}
}
