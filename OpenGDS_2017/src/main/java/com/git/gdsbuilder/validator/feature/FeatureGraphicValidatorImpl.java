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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.SchemaException;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.json.simple.JSONArray;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CRSAuthorityFactory;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.type.validate.option.B_SymbolOutSided;
import com.git.gdsbuilder.type.validate.option.BuildingOpen;
import com.git.gdsbuilder.type.validate.option.BuildingSite;
import com.git.gdsbuilder.type.validate.option.CemeterySite;
import com.git.gdsbuilder.type.validate.option.CenterLineMiss;
import com.git.gdsbuilder.type.validate.option.ConBreak;
import com.git.gdsbuilder.type.validate.option.ConIntersected;
import com.git.gdsbuilder.type.validate.option.ConOverDegree;
import com.git.gdsbuilder.type.validate.option.CrossRoad;
import com.git.gdsbuilder.type.validate.option.EntityDuplicated;
import com.git.gdsbuilder.type.validate.option.EntityInHole;
import com.git.gdsbuilder.type.validate.option.HoleMisplacement;
import com.git.gdsbuilder.type.validate.option.LayerMiss;
import com.git.gdsbuilder.type.validate.option.LinearDisconnection;
import com.git.gdsbuilder.type.validate.option.NodeMiss;
import com.git.gdsbuilder.type.validate.option.OneAcre;
import com.git.gdsbuilder.type.validate.option.OneStage;
import com.git.gdsbuilder.type.validate.option.OutBoundary;
import com.git.gdsbuilder.type.validate.option.OverShoot;
import com.git.gdsbuilder.type.validate.option.PointDuplicated;
import com.git.gdsbuilder.type.validate.option.RiverBoundaryMiss;
import com.git.gdsbuilder.type.validate.option.SelfEntity;
import com.git.gdsbuilder.type.validate.option.SmallArea;
import com.git.gdsbuilder.type.validate.option.SmallLength;
import com.git.gdsbuilder.type.validate.option.TwistedPolygon;
import com.git.gdsbuilder.type.validate.option.UnderShoot;
import com.git.gdsbuilder.type.validate.option.UselessEntity;
import com.git.gdsbuilder.type.validate.option.UselessPoint;
import com.git.gdsbuilder.type.validate.option.WaterOpen;
import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.algorithm.CentroidPoint;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class FeatureGraphicValidatorImpl implements FeatureGraphicValidator {

	@Override
	public List<ErrorFeature> validateConBreak(SimpleFeature simpleFeature, SimpleFeatureCollection aop,
			double tolerence) throws SchemaException {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();

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
				if (geometry.intersection(aopGeom) != null) {
					Coordinate[] temp = new Coordinate[] { start, end };
					int tempSize = temp.length;
					for (int i = 0; i < tempSize; i++) {
						Geometry returnGeom = geometryFactory.createPoint(temp[i]);
						if (Math.abs(returnGeom.distance(aopGeom)) > tolerence || returnGeom.crosses(aopGeom)) {
							String featureIdx = simpleFeature.getID();
							Property featuerIDPro = simpleFeature.getProperty("feature_id");
							String featureID = (String) featuerIDPro.getValue();
							ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
									ConBreak.Type.CONBREAK.errType(), ConBreak.Type.CONBREAK.errName(), returnGeom);
							errFeatures.add(errFeature);
						}
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

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Coordinate[] coors = geometry.getCoordinates();
		int coorsSize = coors.length;

		CRSAuthorityFactory factory = CRS.getAuthorityFactory(true);
		CoordinateReferenceSystem crs = factory.createCoordinateReferenceSystem("EPSG:3857");
		for (int i = 0; i < coorsSize - 1; i++) {
			Coordinate a = coors[i];
			Coordinate b = coors[i + 1];

			// 길이 조건
			double tmpLength = a.distance(b);
			double distance = JTS.orthodromicDistance(a, b, crs);

			if (tmpLength == 0) {
				CentroidPoint cPoint = new CentroidPoint();
				cPoint.add(a);
				cPoint.add(b);

				Coordinate coordinate = cPoint.getCentroid();
				GeometryFactory gFactory = new GeometryFactory();
				Geometry returnGeom = gFactory.createPoint(coordinate);

				Property featuerIDPro = simpleFeature.getProperty("feature_id");
				String featureID = (String) featuerIDPro.getValue();
				String featureIdx = simpleFeature.getID();
				ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
						UselessPoint.Type.USELESSPOINT.errType(), UselessPoint.Type.USELESSPOINT.errName(),
						returnGeom.getGeometryN(i));
				errFeatures.add(errFeature);
			}
			if (i < coorsSize - 2) {
				// 각도 조건
				Coordinate c = coors[i + 2];
				if (!a.equals2D(b) && !b.equals2D(c) && !c.equals2D(a)) {
					double tmpAngle = Angle.toDegrees(Angle.angleBetween(a, b, c));
					if (tmpAngle < 6) {
						GeometryFactory gFactory = new GeometryFactory();
						Geometry returnGeom = gFactory.createPoint(b);
						String featureIdx = simpleFeature.getID();
						Property featuerIDPro = simpleFeature.getProperty("feature_id");
						String featureID = (String) featuerIDPro.getValue();
						ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
								UselessPoint.Type.USELESSPOINT.errType(), UselessPoint.Type.USELESSPOINT.errName(),
								returnGeom.getGeometryN(i));
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
	public List<ErrorFeature> validateSelfEntity(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ,
			double selfEntityTolerance, double polygonInvadedTolorence) throws SchemaException {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();
		GeometryFactory geometryFactory = new GeometryFactory();
		Geometry geometryI = (Geometry) simpleFeatureI.getDefaultGeometry();
		Geometry geometryJ = (Geometry) simpleFeatureJ.getDefaultGeometry();

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
					String featureIdx = simpleFeatureI.getID();
					Property featureIDPro = simpleFeatureI.getProperty("feature_id");
					String featureID = (String) featureIDPro.getValue();
					ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
							SelfEntity.Type.SELFENTITY.errType(), SelfEntity.Type.SELFENTITY.errName(), startPoint);
					errFeatures.add(errFeature);
				} else {
					String featureIdx = simpleFeatureI.getID();
					Property featureIDPro = simpleFeatureI.getProperty("feature_id");
					String featureID = (String) featureIDPro.getValue();
					ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
							SelfEntity.Type.SELFENTITY.errType(), SelfEntity.Type.SELFENTITY.errName(),
							returnGeom.getInteriorPoint());
					errFeatures.add(errFeature);
				}
			} else {
				for (int i = 0; i < returnGeom.getNumGeometries(); i++) {
					String featureIdx = simpleFeatureI.getID();
					Property featuerIDPro = simpleFeatureI.getProperty("feature_id");
					String featureID = (String) featuerIDPro.getValue();
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
			if (geometryI.crosses(geometryJ) && !geometryI.equals(geometryJ)) {
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
					if (geometryI.overlaps(geometryJ) || geometryI.within(geometryJ) || geometryI.contains(geometryJ)) {
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
	public ErrorFeature validateOutBoundary(SimpleFeature simpleFeature, SimpleFeature relationSimpleFeature,
			double spatialAccuracyTolorence) throws SchemaException {

		Geometry geometryI = (Geometry) simpleFeature.getDefaultGeometry();
		Geometry geometryJ = (Geometry) relationSimpleFeature.getDefaultGeometry();

		ErrorFeature errFeature = null;

		if (geometryI.overlaps(geometryJ)) {
			if (!geometryI.within(geometryJ)) {
				Coordinate[] geomJCoor = geometryJ.getCoordinates();
				for (int i = 0; i < geomJCoor.length; i++) {
					Coordinate j = geomJCoor[i];
					double distance = geometryI.distance(new GeometryFactory().createPoint(j));
					if (distance > spatialAccuracyTolorence) {
						Geometry returnGome = geometryJ.getCentroid();
						String featureIdx = relationSimpleFeature.getID();
						Property featuerIDPro = relationSimpleFeature.getProperty("feature_id");
						String featureID = (String) featuerIDPro.getValue();
						errFeature = new ErrorFeature(featureIdx, featureID, OutBoundary.Type.OUTBOUNDARY.errType(),
								OutBoundary.Type.OUTBOUNDARY.errName(), returnGome);
						return errFeature;
					}
				}
			}
		}
		return null;
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

	@Override
	public List<ErrorFeature> validateUnderShoot(SimpleFeature simpleFeature, SimpleFeatureCollection aop,
			double tolerence) throws SchemaException {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry(); // feature
		SimpleFeatureIterator iterator = aop.features();
		while (iterator.hasNext()) {
			SimpleFeature relationSimpleFeature = iterator.next();
			Geometry geometryRelation = (Geometry) relationSimpleFeature.getDefaultGeometry(); // aop
			Geometry aopBuffer = geometryRelation.buffer(tolerence); // tolerence

			Polygon aopBufferPg = (Polygon) aopBuffer;
			LineString ring = aopBufferPg.getInteriorRingN(0);
			Coordinate[] coors = ring.getCoordinates();
			GeometryFactory factory = new GeometryFactory();
			for (int i = 0; i < coors.length; i++) {
				
			}
			System.out.println("");

			// : 사용자
			// 입력 파라미터
			Geometry envelope = geometryRelation.getEnvelope();

			if (envelope.contains(geometry)) {
				Coordinate[] featureCoor = geometry.getCoordinates();
				Geometry featurePt1 = factory.createPoint(featureCoor[0]);
				Geometry featurePt2 = factory.createPoint(featureCoor[featureCoor.length - 1]);
				if (!featurePt1.equals(featurePt2)) {
					if (!aopBuffer.contains(featurePt1)) {
						String featureIdx = simpleFeature.getID();
						Property featuerIDPro = simpleFeature.getProperty("feature_id");
						String featureID = (String) featuerIDPro.getValue();
						ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
								UnderShoot.Type.UNDERSHOOT.errType(), UnderShoot.Type.UNDERSHOOT.errName(), featurePt1);
						errFeatures.add(errFeature);
					}
					if (!aopBuffer.contains(featurePt2)) {
						String featureIdx = simpleFeature.getID();
						Property featuerIDPro = simpleFeature.getProperty("feature_id");
						String featureID = (String) featuerIDPro.getValue();
						ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
								UnderShoot.Type.UNDERSHOOT.errType(), UnderShoot.Type.UNDERSHOOT.errName(), featurePt2);
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

	public ErrorFeature validateUselessEntity(SimpleFeature simpleFeature) throws SchemaException {
		String upperType = simpleFeature.getAttribute("feature_type").toString().toUpperCase();
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();

		if (!(upperType.equals("POINT") || upperType.equals("LINESTRING") || upperType.equals("POLYGON")
				|| upperType.equals("MULTIPOINT") || upperType.equals("MULTILINESTRING")
				|| upperType.equals("MULTIPOLYGON") || upperType.equals("TEXT") || upperType.equals("LINE")
				|| upperType.equals("INSERT") || upperType.equals("LWPOLYLINE") || upperType.equals("POLYLINE"))) {

			String featureIdx = simpleFeature.getID();
			Property featuerIDPro = simpleFeature.getProperty("feature_id");
			String featureID = (String) featuerIDPro.getValue();
			ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
					UselessEntity.Type.USELESSENTITY.errType(), UselessEntity.Type.USELESSENTITY.errName(),
					geometry.getInteriorPoint());
			return errorFeature;
		} else {
			return null;
		}
	}

	public ErrorFeature validateBuildingOpen(SimpleFeature simpleFeature, SimpleFeatureCollection aop, double tolerence)
			throws SchemaException {
		GeometryFactory geometryFactory = new GeometryFactory();
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Coordinate[] coordinates = geometry.getCoordinates();
		int coorSize = coordinates.length;
		Coordinate start = coordinates[0];
		Coordinate end = coordinates[coorSize - 1];
		Geometry startGeom = geometryFactory.createPoint(start);
		Geometry endGeom = geometryFactory.createPoint(end);
		if (coorSize > 3) {
			if (!(start.equals2D(end))) {
				SimpleFeatureIterator iterator = aop.features();
				while (iterator.hasNext()) {
					SimpleFeature aopSimpleFeature = iterator.next();
					Geometry aopGeom = (Geometry) aopSimpleFeature.getDefaultGeometry();
					if (Math.abs(aopGeom.distance(startGeom)) > tolerence
							|| Math.abs(aopGeom.distance(endGeom)) > tolerence) {
						String featureIdx = simpleFeature.getID();
						Property featuerIDPro = simpleFeature.getProperty("feature_id");
						String featureID = (String) featuerIDPro.getValue();
						ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
								BuildingOpen.Type.BUILDINGOPEN.errType(), BuildingOpen.Type.BUILDINGOPEN.errName(),
								geometry.getInteriorPoint());
						return errorFeature;
					}
				}
			} else {
				return null;
			}
		} else {
			SimpleFeatureIterator iterator = aop.features();
			while (iterator.hasNext()) {
				SimpleFeature aopSimpleFeature = iterator.next();
				Geometry aopGeom = (Geometry) aopSimpleFeature.getDefaultGeometry();
				if (Math.abs(aopGeom.distance(startGeom)) > tolerence
						|| Math.abs(aopGeom.distance(endGeom)) > tolerence) {
					String featureIdx = simpleFeature.getID();
					Property featuerIDPro = simpleFeature.getProperty("feature_id");
					String featureID = (String) featuerIDPro.getValue();
					ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
							BuildingOpen.Type.BUILDINGOPEN.errType(), BuildingOpen.Type.BUILDINGOPEN.errName(),
							geometry.getInteriorPoint());
					return errorFeature;
				}
			}
		}
		return null;
	}

	public ErrorFeature validateWaterOpen(SimpleFeature simpleFeature, SimpleFeatureCollection aop, double tolerence)
			throws SchemaException {
		GeometryFactory geometryFactory = new GeometryFactory();
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Coordinate[] coordinates = geometry.getCoordinates();
		int coorSize = coordinates.length;
		Coordinate start = coordinates[0];
		Coordinate end = coordinates[coorSize - 1];
		Geometry startGeom = geometryFactory.createPoint(start);
		Geometry endGeom = geometryFactory.createPoint(end);
		if (coorSize > 3) {
			if (!(start.equals2D(end))) {
				SimpleFeatureIterator iterator = aop.features();
				while (iterator.hasNext()) {
					SimpleFeature aopSimpleFeature = iterator.next();
					Geometry aopGeom = (Geometry) aopSimpleFeature.getDefaultGeometry();
					if (Math.abs(aopGeom.distance(startGeom)) > tolerence
							|| Math.abs(aopGeom.distance(endGeom)) > tolerence) {
						String featureIdx = simpleFeature.getID();
						Property featuerIDPro = simpleFeature.getProperty("feature_id");
						String featureID = (String) featuerIDPro.getValue();
						ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
								WaterOpen.Type.WATEROPEN.errType(), WaterOpen.Type.WATEROPEN.errName(),
								geometry.getInteriorPoint());
						return errorFeature;
					}
				}
			} else {
				return null;
			}
		} else {
			SimpleFeatureIterator iterator = aop.features();
			while (iterator.hasNext()) {
				SimpleFeature aopSimpleFeature = iterator.next();
				Geometry aopGeom = (Geometry) aopSimpleFeature.getDefaultGeometry();
				if (Math.abs(aopGeom.distance(startGeom)) > tolerence
						|| Math.abs(aopGeom.distance(endGeom)) > tolerence) {
					String featureIdx = simpleFeature.getID();
					Property featuerIDPro = simpleFeature.getProperty("feature_id");
					String featureID = (String) featuerIDPro.getValue();
					ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
							WaterOpen.Type.WATEROPEN.errType(), WaterOpen.Type.WATEROPEN.errName(),
							geometry.getInteriorPoint());
					return errorFeature;
				}
			}
		}
		return null;
	}

	public ErrorFeature validateLayerMiss(SimpleFeature simpleFeature, List<String> typeNames) throws SchemaException {
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		String upperType = simpleFeature.getAttribute("feature_type").toString().toUpperCase();
		Boolean flag = false;

		for (int i = 0; i < typeNames.size(); i++) {
			String typeName = typeNames.get(i);
			String upperTpyeName = typeName.toUpperCase();
			if (upperTpyeName.equals(upperType)) {
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

	public ErrorFeature validateB_SymbolOutSided(List<SimpleFeature> simpleFeatures,
			SimpleFeature relationSimpleFeature) throws SchemaException {

		Geometry relationGeometry = (Geometry) relationSimpleFeature.getDefaultGeometry();
		Boolean flag = false;

		for (int i = 0; i < simpleFeatures.size(); i++) {
			SimpleFeature simpleFeature = simpleFeatures.get(i);
			Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
			String upperType = simpleFeature.getAttribute("feature_type").toString().toUpperCase();

			if (upperType.equals("POINT") || upperType.equals("TEXT")) {
				if ((geometry.equals(relationGeometry))) {
					flag = true;
					break;
				}
			}
			if (upperType.equals("LINESTRING") || upperType.equals("LINE")) {
				GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
				Coordinate[] coordinates = geometry.getCoordinates();
				Coordinate start = coordinates[0];
				Coordinate end = coordinates[coordinates.length - 1];
				if (start.equals2D(end)) {
					LinearRing ring = geometryFactory.createLinearRing(coordinates);
					LinearRing holes[] = null;
					Polygon polygon = geometryFactory.createPolygon(ring, holes);
					if (polygon.contains(relationGeometry)) {
						flag = true;
						break;
					}
				}

			}
			if (upperType.equals("LWPOLYLINE") || upperType.equals("POLYLINE")) {

				// create Polygon
				GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
				List<Coordinate> list = new ArrayList<Coordinate>();
				Coordinate[] coordinates = geometry.getCoordinates();
				Coordinate start = coordinates[0];
				Coordinate end = coordinates[coordinates.length - 1];
				if (start.equals2D(end)) {
					LinearRing ring = geometryFactory.createLinearRing(coordinates);
					LinearRing holes[] = null;
					Polygon polygon = geometryFactory.createPolygon(ring, holes);
					if (polygon.contains(relationGeometry)) {
						flag = true;
						break;
					}
				} else {
					for (int j = 0; j < coordinates.length; j++) {
						Coordinate coordinate = coordinates[j];
						list.add(coordinate);
					}
					list.add(start);
					Coordinate[] coordinates2 = new Coordinate[list.size()];
					list.toArray(coordinates2);
					LinearRing ring = geometryFactory.createLinearRing(coordinates2);
					LinearRing holes[] = null;
					Polygon polygon = geometryFactory.createPolygon(ring, holes);
					if (polygon.contains(relationGeometry)) {
						flag = true;
						break;
					}
				}
			}
		}

		if (flag == false) {
			String featureIdx = relationSimpleFeature.getID();
			Property featuerIDPro = relationSimpleFeature.getProperty("feature_id");
			String featureID = (String) featuerIDPro.getValue();
			ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
					B_SymbolOutSided.Type.B_SYMBOLOUTSIDED.errType(), B_SymbolOutSided.Type.B_SYMBOLOUTSIDED.errName(),
					relationGeometry.getInteriorPoint());
			return errorFeature;
		} else {
			return null;
		}
	}

	public List<ErrorFeature> validateCrossRoad(SimpleFeature simpleFeature, List<SimpleFeature> relationSimpleFeatures,
			double tolerence) throws SchemaException {
		List<ErrorFeature> errorFeatures = new ArrayList<ErrorFeature>();
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		GeometryFactory geometryFactory = new GeometryFactory();
		Coordinate[] coordinates = geometry.getCoordinates();

		for (int i = 0; i < relationSimpleFeatures.size(); i++) {
			SimpleFeature relationSimpleFeature = relationSimpleFeatures.get(i);
			Geometry relationGeom = (Geometry) relationSimpleFeature.getDefaultGeometry();
			Coordinate[] relCoordinates = relationGeom.getCoordinates();
			LineString lineString = geometryFactory.createLineString(relCoordinates);
			for (int j = 0; j < coordinates.length; j++) {
				Coordinate coordinate = coordinates[j];
				Geometry point = geometryFactory.createPoint(coordinate);
				if (Math.abs(lineString.distance(point)) > tolerence) {
					String featureIdx = relationSimpleFeature.getID();
					Property featuerIDPro = relationSimpleFeature.getProperty("feature_id");
					String featureID = (String) featuerIDPro.getValue();
					ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
							CrossRoad.Type.CROSSROAD.errType(), CrossRoad.Type.CROSSROAD.errName(), point);
					errorFeatures.add(errorFeature);
				}
			}
		}
		return errorFeatures;
	}

	public List<ErrorFeature> validateNodeMiss(SimpleFeature simpleFeature, List<SimpleFeature> relationSimpleFeatures,
			double tolerence) throws SchemaException {
		List<ErrorFeature> errorFeatures = new ArrayList<ErrorFeature>();
		GeometryFactory geometryFactory = new GeometryFactory();
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Coordinate[] coordinates = geometry.getCoordinates();
		LineString line = geometryFactory.createLineString(coordinates);

		for (int i = 0; i < relationSimpleFeatures.size(); i++) {
			SimpleFeature lineSimpleFeature = relationSimpleFeatures.get(i);
			Geometry lineGeometry = (Geometry) lineSimpleFeature.getDefaultGeometry();
			if (line.intersects(lineGeometry)) {
				Boolean flag = false;
				Geometry intersectGeom = line.intersection(lineGeometry);
				Coordinate[] intersectCoor = intersectGeom.getCoordinates();
				Coordinate[] lineCoordinates = lineGeometry.getCoordinates();
				for (int j = 0; j < intersectCoor.length; j++) {
					Coordinate interCoordinateValue = intersectCoor[j];
					Geometry interPoint = geometryFactory.createPoint(interCoordinateValue);

					for (int k = 0; k < lineCoordinates.length; k++) {
						Coordinate lineCoordinate = lineCoordinates[k];
						Geometry linePoint = geometryFactory.createPoint(lineCoordinate);
						if (Math.abs(linePoint.distance(interPoint)) < tolerence) {
							flag = true;
							break;
						}
					}
					if (flag == false) {
						String featureIdx = lineSimpleFeature.getID();
						Property featuerIDPro = lineSimpleFeature.getProperty("feature_id");
						String featureID = (String) featuerIDPro.getValue();
						ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
								NodeMiss.Type.NODEMISS.errType(), NodeMiss.Type.NODEMISS.errName(), interPoint);
						errorFeatures.add(errorFeature);
					}
				}
			}
		}
		return errorFeatures;
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
	public ErrorFeature validateOneAcre(SimpleFeature simpleFeature, SimpleFeatureCollection relationSfc) {

		int withinCount = 0;
		SimpleFeature returnSf = null;
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry(); // D002
		SimpleFeatureIterator sfIterator = relationSfc.features();
		while (sfIterator.hasNext()) {
			SimpleFeature relationSf = sfIterator.next(); // D001
			Geometry relationGeom = (Geometry) relationSf.getDefaultGeometry();
			if (relationGeom.within(geometry)) {
				withinCount++;
				if (withinCount > 1) {
					returnSf = null;
					return null;
				}
				if (withinCount == 1) {
					returnSf = relationSf;
				}
			}
		}
		if (withinCount == 1) {
			Property featuerIDPro = returnSf.getProperty("feature_id");
			String featureID = (String) featuerIDPro.getValue();
			String featureIdx = returnSf.getID();
			Geometry returnGeom = ((Geometry) returnSf.getDefaultGeometry()).getCentroid();
			ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID, OneAcre.Type.ONEACRE.errType(),
					OneAcre.Type.ONEACRE.errName(), returnGeom);
			return errFeature;
		} else {
			return null;
		}
	}

	@Override
	public List<ErrorFeature> validateOneStage(SimpleFeatureCollection simpleFeatureCollection,
			SimpleFeatureCollection relaSimpleFeatureCollection) {

		List<ErrorFeature> errFeatures = new ArrayList<ErrorFeature>();
		SimpleFeatureIterator sfIter = simpleFeatureCollection.features();
		while (sfIter.hasNext()) {
			// D001
			SimpleFeature simpleFeature = sfIter.next();
			Geometry geom = (Geometry) simpleFeature.getDefaultGeometry();
			boolean isTrue = false;
			SimpleFeatureIterator reSfIter = relaSimpleFeatureCollection.features();
			intterWhile: while (reSfIter.hasNext()) {
				// D002
				SimpleFeature reSimpleFeature = reSfIter.next();
				Geometry reGeom = (Geometry) reSimpleFeature.getDefaultGeometry();
				if (geom.within(reGeom) || geom.overlaps(reGeom)) {
					isTrue = true;
					break intterWhile;
				}
			}
			if (!isTrue) {
				Property featuerIDPro = simpleFeature.getProperty("feature_id");
				String featureID = (String) featuerIDPro.getValue();
				String featureIdx = simpleFeature.getID();
				Geometry returnGeom = ((Geometry) simpleFeature.getDefaultGeometry()).getCentroid();
				ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID, OneStage.Type.ONESTAGE.errType(),
						OneStage.Type.ONESTAGE.errName(), returnGeom);
				errFeatures.add(errFeature);
			}
		}
		if (errFeatures.size() > 0) {
			return errFeatures;
		} else {
			return null;
		}
	}

	public ErrorFeature validateCemeterySite(SimpleFeature simpleFeature, SimpleFeatureCollection relationSfc) {
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();

		SimpleFeatureIterator relationSimpleFeatureIterator = relationSfc.features();
		Boolean isTrue = false;
		Geometry errPoint = null;
		while (relationSimpleFeatureIterator.hasNext()) {
			SimpleFeature relationSimpleFeature = relationSimpleFeatureIterator.next();
			Geometry relationGeometry = (Geometry) relationSimpleFeature.getDefaultGeometry();
			if (geometry.intersects(relationGeometry) || geometry.contains(relationGeometry)) {
				isTrue = true;
				Geometry errorPoint = geometry.intersection(relationGeometry);
				errPoint = errorPoint;
				break;
			}
		}

		if (isTrue) {
			Property featuerIDPro = simpleFeature.getProperty("feature_id");
			String featureID = (String) featuerIDPro.getValue();
			String featureIdx = simpleFeature.getID();

			ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
					CemeterySite.Type.CEMETERYSITE.errType(), CemeterySite.Type.CEMETERYSITE.errName(), errPoint);
			return errorFeature;
		} else {
			return null;
		}
	}

	public ErrorFeature validateBuildingSite(SimpleFeature simpleFeature, SimpleFeatureCollection relationSfc,
			Map<String, List<String>> attributes) {
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		SimpleFeatureIterator simpleFeatureIterator = relationSfc.features();

		if (attributes != null) {
			Iterator iterator = attributes.keySet().iterator();
			while (iterator.hasNext()) {
				String attributeKey = (String) iterator.next();
				Object attributeObj = simpleFeature.getAttribute(attributeKey);
				JSONArray attributeArray = (JSONArray) attributes.get(attributeKey);

				if (attributeObj != null) {
					String attribute = attributeObj.toString();
					Iterator iteratorArray = attributeArray.iterator();
					Boolean flag = false;
					while (iteratorArray.hasNext()) {
						String attributeValue = (String) iteratorArray.next();
						if (attribute.equals(attributeValue)) {
							flag = true;
							break;
						}
					}
					if (flag == true) {
						Boolean isTrue = false;
						while (simpleFeatureIterator.hasNext()) {
							SimpleFeature relationSimpleFeature = simpleFeatureIterator.next();
							Geometry relationGeometry = (Geometry) relationSimpleFeature.getDefaultGeometry();
							if (relationGeometry.contains(geometry) || geometry.within(relationGeometry)) {
								isTrue = true;
								break;
							}
						}
						if (isTrue == false) {
							String featureIdx = simpleFeature.getID();
							Property featuerIDPro = simpleFeature.getProperty("feature_id");
							String featureID = (String) featuerIDPro.getValue();
							ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
									BuildingSite.Type.BUILDINGSITE.errType(), BuildingSite.Type.BUILDINGSITE.errName(),
									geometry.getInteriorPoint());
							return errorFeature;
						}
					}
				}
			}
		}
		return null;
	}

	public ErrorFeature validateRiverBoundaryMiss(SimpleFeature simpleFeature, SimpleFeatureCollection relationSfc) {
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		String ufid = simpleFeature.getAttribute("feature_id").toString();
		if (ufid.equals("RECORD 2201")) {
			System.out.println();
		}
		SimpleFeatureIterator relationSimpleFeatureIterator = relationSfc.features();
		String featureIdx = simpleFeature.getID();
		Property featuerIDPro = simpleFeature.getProperty("feature_id");
		String featureID = (String) featuerIDPro.getValue();

		List<SimpleFeature> relationSimpleFeatureList = new ArrayList<SimpleFeature>();
		while (relationSimpleFeatureIterator.hasNext()) {
			SimpleFeature relationSimpleFeature = relationSimpleFeatureIterator.next();
			Geometry relationGeometry = (Geometry) relationSimpleFeature.getDefaultGeometry();
			if (geometry.intersects(relationGeometry)) {
				relationSimpleFeatureList.add(relationSimpleFeature);
			}
		}

		if (relationSimpleFeatureList.size() == 0) {
			// error
			ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
					RiverBoundaryMiss.Type.RIVERBOUNDARYMISS.errType(),
					RiverBoundaryMiss.Type.RIVERBOUNDARYMISS.errName(), geometry.getInteriorPoint());
			return errorFeature;
		} else if (relationSimpleFeatureList.size() == 1) {
			for (int i = 0; i < relationSimpleFeatureList.size(); i++) {
				SimpleFeature relationSimplfeature = relationSimpleFeatureList.get(i);
				Geometry relationGeometry = (Geometry) relationSimplfeature.getDefaultGeometry();
				if (!geometry.contains(relationGeometry) || !relationGeometry.within(geometry)) {
					// error
					ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
							RiverBoundaryMiss.Type.RIVERBOUNDARYMISS.errType(),
							RiverBoundaryMiss.Type.RIVERBOUNDARYMISS.errName(), geometry.getInteriorPoint());
					return errorFeature;
				}
			}
		} else {
			SimpleFeature relationSimFeature = relationSimpleFeatureList.get(0);
			Geometry relationGeom = (Geometry) relationSimFeature.getDefaultGeometry();
			for (int i = 1; i < relationSimpleFeatureList.size(); i++) {
				SimpleFeature relationSimpleFeature = relationSimpleFeatureList.get(i);
				Geometry relationGeometry = (Geometry) relationSimpleFeature.getDefaultGeometry();
				Geometry union = relationGeom.union(relationGeometry);
				relationGeom = union;
			}

			Geometry buffer = relationGeom.buffer(1);
			Geometry intersection = buffer.intersection(geometry);
			System.out.println(geometry.equals(intersection));

			if (!buffer.within(geometry)) {
				// error
				ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
						RiverBoundaryMiss.Type.RIVERBOUNDARYMISS.errType(),
						RiverBoundaryMiss.Type.RIVERBOUNDARYMISS.errName(), geometry.getInteriorPoint());
				return errorFeature;
			}
		}

		return null;
	}

	public ErrorFeature validateCenterLineMiss(SimpleFeature simpleFeature, SimpleFeatureCollection relationSfc,
			double lineInvadedTolorence) {
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		String featureIdx = simpleFeature.getID();
		Property featureIDPro = simpleFeature.getProperty("feature_id");
		String featureID = (String) featureIDPro.getValue();
		SimpleFeatureIterator relationSfcIterator = relationSfc.features();

		Geometry bufferGeom = geometry.buffer(lineInvadedTolorence);

		boolean isTrue = false;
		while (relationSfcIterator.hasNext()) {
			SimpleFeature relationSimpleFeature = relationSfcIterator.next();
			Geometry relationGeometry = (Geometry) relationSimpleFeature.getDefaultGeometry();
			if (geometry.intersects(relationGeometry)) {
				isTrue = true;
				if (bufferGeom.contains(relationGeometry) || relationGeometry.within(bufferGeom)) {
					break;
				} else {
					// error
					ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
							CenterLineMiss.Type.CENTERLINEMISS.errType(), CenterLineMiss.Type.CENTERLINEMISS.errName(),
							geometry.getInteriorPoint());
					return errorFeature;
				}
			}
		}

		if (!isTrue) {
			// error
			ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
					CenterLineMiss.Type.CENTERLINEMISS.errType(), CenterLineMiss.Type.CENTERLINEMISS.errName(),
					geometry.getInteriorPoint());
			return errorFeature;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unused")
	public List<ErrorFeature> validateHoleMisplacement(SimpleFeature simpleFeature) {

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		String geomType = geometry.getGeometryType().toUpperCase();
		String featureIdx = simpleFeature.getID();
		Property featureIDPro = simpleFeature.getProperty("feature_id");
		String featureID = (String) featureIDPro.getValue();
		List<ErrorFeature> errorFeatures = new ArrayList<>();

		if (geomType.equals("POLYGON")) {
			Polygon polygon = (Polygon) geometry;
			int holeNum = polygon.getNumInteriorRing();
			boolean hasHoles = holeNum > 0;
			if (hasHoles) {
				for (int i = 0; i < holeNum; i++) {
					LineString lineString = polygon.getInteriorRingN(i);
					ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
							HoleMisplacement.Type.HOLEMISPLACEMENT.errType(),
							HoleMisplacement.Type.HOLEMISPLACEMENT.errName(), lineString.getInteriorPoint());
					errorFeatures.add(errorFeature);
					return errorFeatures;
				}
			}
		}
		return null;
	}
	
	public List<ErrorFeature> validateEntityInHole(SimpleFeature simpleFeature, SimpleFeatureCollection relationSfc){

		List<ErrorFeature> errorFeatures = new ArrayList<ErrorFeature>();
		GeometryFactory geometryFactory = new GeometryFactory();
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		String geomType = geometry.getGeometryType().toUpperCase();
		SimpleFeatureIterator relationSfcIterator = relationSfc.features();

		String featureIdx = simpleFeature.getID();
		Property featureIDPro = simpleFeature.getProperty("feature_id");
		String featureID = (String) featureIDPro.getValue();

		if (geomType.equals("POLYGON")) {
			Polygon polygon = (Polygon) geometry;
			int holeNum = polygon.getNumInteriorRing();
			if (holeNum > 0) {
				for (int i = 0; i < holeNum; i++) {
					LinearRing interiorRing = (LinearRing) polygon.getInteriorRingN(i);
					LinearRing holes[] = null;
					Polygon interiorPolygon = geometryFactory.createPolygon(interiorRing, holes);

					while (relationSfcIterator.hasNext()) {
						SimpleFeature relationSimpleFeature = relationSfcIterator.next();
						Geometry relationGeometry = (Geometry) relationSimpleFeature.getDefaultGeometry();
						if (interiorPolygon.intersects(relationGeometry)) {
							if (interiorPolygon.equals(relationGeometry)) {
								// error
								ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
										EntityInHole.Type.ENTITYINHOLE.errType(),
										EntityInHole.Type.ENTITYINHOLE.errName(), interiorPolygon.getInteriorPoint());
								errorFeatures.add(errorFeature);
							}else{
								if(relationGeometry.contains(interiorPolygon) || interiorPolygon.within(relationGeometry)){
									//error
									ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID, 
											EntityInHole.Type.ENTITYINHOLE.errType(), EntityInHole.Type.ENTITYINHOLE.errName(),

											interiorPolygon.getInteriorPoint());
									errorFeatures.add(errorFeature);
								}
							}
						}

					}
				}
			}
		}
		return errorFeatures;
	}
	
	public List<ErrorFeature> validateLinearDisconnection(SimpleFeature simpleFeature, SimpleFeatureCollection relationSfc){

		GeometryFactory geometryFactory = new GeometryFactory();
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		SimpleFeatureIterator simpleFeatureIterator = relationSfc.features();
		String featureIdx = simpleFeature.getID();
		Property featuerIDPro = simpleFeature.getProperty("feature_id");
		String featureID = (String) featuerIDPro.getValue();
		if(featureID.equals("RECORD 912")){
			System.out.println();
		}
		List<SimpleFeature> relationSimpleFeatures = new ArrayList<>();
		List<Geometry> geometries = new ArrayList<>();
		List<ErrorFeature> errorFeatures = new ArrayList<>();
	
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature relationSimpleFeatrue = simpleFeatureIterator.next();
			Geometry relationGeometry = (Geometry) relationSimpleFeatrue.getDefaultGeometry();
			Property featuerIDPro2 = simpleFeature.getProperty("feature_id");
			String featureID2 = (String) featuerIDPro2.getValue();
			if(featureID2.equals("RECORD 250")){
				System.out.println();
			}
			if(geometry.intersects(relationGeometry)){
				Geometry intersection = geometry.intersection(relationGeometry);
				String intersectionType = intersection.getGeometryType().toUpperCase();
				if(!intersectionType.equals("POINT")|| !intersectionType.equals("NULTIPOINT")){
					if(intersectionType.equals("LINESTRING")){
						double length = intersection.getLength();
						if(length > 0.01){
							relationSimpleFeatures.add(relationSimpleFeatrue);
						}
					}
				}
			}
		}

		if(relationSimpleFeatures.size() > 1){
			for (int i = 0; i < relationSimpleFeatures.size(); i++) {
				SimpleFeature relationSimpleFeature = relationSimpleFeatures.get(i);
				Geometry relationGeometry = (Geometry) relationSimpleFeature.getDefaultGeometry();
				Geometry intersection = geometry.intersection(relationGeometry.getBoundary());
				String intersectionType = intersection.getGeometryType().toUpperCase();
				if(intersectionType.equals("MULTIPOINT") || intersectionType.equals("POINT")){
					Coordinate[] coordinates = intersection.getCoordinates();
					for (int j = 0; j < coordinates.length; j++) {
						Coordinate coordinate = coordinates[j];
						Point point = geometryFactory.createPoint(coordinate);
						geometries.add(point);
					}
				}
			}
		}
		
		HashSet<Geometry> distinctData = new HashSet<>(geometries);
		geometries.clear();
		geometries.addAll(distinctData);
		for (int i = 0; i < geometries.size(); i++) {
			Geometry point = geometries.get(i);
			ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
					LinearDisconnection.Type.LINEARDISCONNECTION.errType(),
					LinearDisconnection.Type.LINEARDISCONNECTION.errName(), point);
			errorFeatures.add(errorFeature);
		}
		
		return errorFeatures;
	}

}
