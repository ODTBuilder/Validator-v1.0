package com.git.gdsbuilder.generalization.tmp;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.generalization.option.Simplification;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.simplify.TopologyPreservingSimplifier;

public class DTLineSimplification {

	SimpleFeatureCollection simpleFeatureCollection;
	DefaultFeatureCollection newFeatureCollection;

	double tolerance;

	int resultPoint;
	int resultEntity;
	int totalPoint;
	int totalEntity;

	public DTLineSimplification(SimpleFeatureCollection collection, Simplification option) {
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

	public double getTolerance() {
		return tolerance;
	}

	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}

	public DefaultFeatureCollection getNewFeatureCollection() {
		return newFeatureCollection;
	}

	public void setNewFeatureCollection(DefaultFeatureCollection newFeatureCollection) {
		this.newFeatureCollection = newFeatureCollection;
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

	public void simplify() {

		this.newFeatureCollection = new DefaultFeatureCollection();
		this.totalEntity = 0;
		this.totalPoint = 0;
		this.resultEntity = 0;
		this.resultPoint = 0;

		SimpleFeatureIterator iterator = this.simpleFeatureCollection.features();
		while (iterator.hasNext()) {
			SimpleFeature simpleFeautre = iterator.next();
			Geometry geom = (Geometry) simpleFeautre.getDefaultGeometry();
			totalPoint += geom.getCoordinates().length;
			totalEntity++;

			Geometry result = TopologyPreservingSimplifier.simplify(geom, this.tolerance);
			// Geometry result = DouglasPeuckerSimplifier.simplify(geom,
			// this.tolerance);

			if (result != null) {
				simpleFeautre.setDefaultGeometry(result);
				this.newFeatureCollection.add(simpleFeautre);
				resultPoint += result.getCoordinates().length;
				resultEntity++;
			}
		}
	}
}
