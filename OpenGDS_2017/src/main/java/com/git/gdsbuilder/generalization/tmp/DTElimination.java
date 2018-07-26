package com.git.gdsbuilder.generalization.tmp;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.geometry.jts.JTS;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.generalization.option.Elimination;
import com.vividsolutions.jts.geom.Geometry;

public class DTElimination {

	SimpleFeatureCollection simpleFeatureCollection;
	DefaultFeatureCollection newFeatureCollection;

	double tolerance;

	int resultPoint;
	int resultEntity;
	int totalPoint;
	int totalEntity;

	public DTElimination(SimpleFeatureCollection collection, Elimination option) {
		super();
		this.simpleFeatureCollection = collection;
		this.tolerance = option.getTolerance();
	}

	public SimpleFeatureCollection getSimpleFeatureCollection() {
		return simpleFeatureCollection;
	}

	public void setSimpleFeatureCollection(SimpleFeatureCollection simpleFeatureCollection) {
		this.simpleFeatureCollection = simpleFeatureCollection;
	}

	public DefaultFeatureCollection getNewFeatureCollection() {
		return newFeatureCollection;
	}

	public void setNewFeatureCollection(DefaultFeatureCollection newFeatureCollection) {
		this.newFeatureCollection = newFeatureCollection;
	}

	public double getTolerance() {
		return tolerance;
	}

	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}

	public int getResultPoint() {
		return resultPoint;
	}

	public void setResultPoint(int resultPoint) {
		this.resultPoint = resultPoint;
	}

	public int getResultEntity() {
		return resultEntity;
	}

	public void setResultEntity(int resultEntity) {
		this.resultEntity = resultEntity;
	}

	public int getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}

	public int getTotalEntity() {
		return totalEntity;
	}

	public void setTotalEntity(int totalEntity) {
		this.totalEntity = totalEntity;
	}

	public void elimination() {

		SimpleFeatureIterator featureIterator = this.simpleFeatureCollection.features();
		this.newFeatureCollection = new DefaultFeatureCollection();

		while (featureIterator.hasNext()) {
			SimpleFeature feature = featureIterator.next();
			Geometry geom = (Geometry) feature.getDefaultGeometry();
			int coorsCount = geom.getCoordinates().length;

			totalEntity++;
			totalPoint += geom.getCoordinates().length;

			if (geom.getGeometryType().equals("LineString") || geom.getGeometryType().equals("MultiLineString")) {
				if (geom.getLength() * ((Math.PI / 180) * 6378137) > tolerance) {
					newFeatureCollection.add(feature);
					resultEntity++;
					resultPoint += coorsCount;
				}
			} else if (geom.getGeometryType().equals("Polygon") || geom.getGeometryType().equals("MultiPolygon")) {
				if (geom.getArea() * ((Math.PI / 180) * 6378137) > tolerance) {
					newFeatureCollection.add(feature);
					resultEntity++;
					resultPoint += coorsCount;
				}
			}
		}
	}
}
