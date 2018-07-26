package com.git.gdsbuilder.generalization.tmp;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.opengis.feature.simple.SimpleFeature;

import com.vividsolutions.jts.geom.Geometry;

public class DTCountCalculator {

	SimpleFeatureCollection collection;

	int featureCount;
	int pointCount;

	public DTCountCalculator(SimpleFeatureCollection collection) {
		this.collection = collection;
	}

	public SimpleFeatureCollection getCollection() {
		return collection;
	}

	public void setCollection(SimpleFeatureCollection collection) {
		this.collection = collection;
	}

	public int getFeatureCount() {
		return featureCount;
	}

	public void setFeatureCount(int featureCount) {
		this.featureCount = featureCount;
	}

	public int getPointCount() {
		return pointCount;
	}

	public void setPointCount(int pointCount) {
		this.pointCount = pointCount;
	}

	public void getCount() {

		int totalFeature = 0;
		int totalPoint = 0;

		SimpleFeatureIterator featureIterator = this.collection.features();
		while (featureIterator.hasNext()) {
			SimpleFeature feature = featureIterator.next();
			Geometry geom = (Geometry) feature.getDefaultGeometry();
			totalFeature++;
			totalPoint += geom.getCoordinates().length;
		}
		this.pointCount = totalPoint;
		this.featureCount = totalFeature;
	}
}
