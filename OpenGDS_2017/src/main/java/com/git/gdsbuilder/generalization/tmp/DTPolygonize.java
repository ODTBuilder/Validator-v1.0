package com.git.gdsbuilder.generalization.tmp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

public class DTPolygonize {

	SimpleFeatureCollection feataureCollection;
	DefaultFeatureCollection newFeatureCollection;

	public DTPolygonize(SimpleFeatureCollection feataureCollection) {
		this.feataureCollection = feataureCollection;
	}

	public SimpleFeatureCollection getFeataureCollection() {
		return feataureCollection;
	}

	public void setFeataureCollection(SimpleFeatureCollection feataureCollection) {
		this.feataureCollection = feataureCollection;
	}

	public DefaultFeatureCollection getNewFeatureCollection() {
		return newFeatureCollection;
	}

	public void setNewFeatureCollection(DefaultFeatureCollection newFeatureCollection) {
		this.newFeatureCollection = newFeatureCollection;
	}

	public void build() throws SchemaException {

		this.newFeatureCollection = new DefaultFeatureCollection();

		List<SimpleFeature> simpleFeatuares = new ArrayList<>();
		SimpleFeatureIterator iterator = feataureCollection.features();
		while (iterator.hasNext()) {
			SimpleFeature simpleFeature = iterator.next();
			simpleFeatuares.add(simpleFeature);
		}
		int newCount = 0;
		int featureSize = simpleFeatuares.size();

		GeometryFactory factory = new GeometryFactory();
		Map<String, Object> trueIds = new HashedMap();
		for (int i = 0; i < featureSize; i++) {
			SimpleFeature featureI = simpleFeatuares.get(i);
			String iId = (String) featureI.getAttribute("osm_id");

			if (trueIds.get(iId) != null) {
				continue;
			}
			Geometry tarGeom = (Geometry) featureI.getDefaultGeometry();
			Coordinate[] tarCoors = tarGeom.getCoordinates();
			int tarSize = tarCoors.length;
			Coordinate tarEnd = tarCoors[tarSize - 1];

			// 새로운 Coor 배열
			List<Coordinate> coorList = new ArrayList<>();
			for (int q = 0; q < tarSize; q++) {
				coorList.add(tarCoors[q]);
			}
			int trueCount = 0;
			for (int j = 0; j < featureSize;) {
				SimpleFeature featureJ = simpleFeatuares.get(j);
				String jId = (String) featureJ.getAttribute("osm_id");

				if (iId.equals(jId)) {
					j++;
					continue;
				}
				if (trueIds.get(jId) != null) {
					j++;
					continue;
				}

				Geometry tmpGeom = (Geometry) featureJ.getDefaultGeometry();
				Coordinate[] tmpCoors = tmpGeom.getCoordinates();
				int tmpSize = tmpCoors.length;
				Coordinate tmpFirst = tmpCoors[0];
				Coordinate tmpEnd = tmpCoors[tmpSize - 1];
				if (tarEnd.equals(tmpFirst) || tarEnd.distance(tmpFirst) < 0.0001) {
					for (int k = 1; k < tmpSize; k++) { // tmp 첫점 제외하고 add
						// 새로운 Coor 배열에 넣고
						coorList.add(tmpCoors[k]);
					}
					trueCount++;
					trueIds.put(iId, trueCount);
					trueIds.put(jId, trueCount);
					tarEnd = coorList.get(coorList.size() - 1);
					j = 0;
				} else if (tarEnd.equals(tmpEnd) || tarEnd.distance(tmpEnd) < 0.0001) {
					for (int k = tmpSize - 2; k >= 0; k--) { // tmp 첫점 제외하고 add
						// 새로운 Coor 배열에 넣고
						coorList.add(tmpCoors[k]);
					}
					trueCount++;
					trueIds.put(iId, trueCount);
					trueIds.put(jId, trueCount);
					tarEnd = coorList.get(coorList.size() - 1);
					j = 0;
				} else {
					j++;
				}
			}
			int coorSize = coorList.size();
			Coordinate[] newCoors = new Coordinate[coorSize];
			for (int q = 0; q < coorSize; q++) {
				newCoors[q] = coorList.get(q);
			}

			Geometry newGeom = null;
			// 폐합여부확인
			int newCoorsSize = newCoors.length;
			Coordinate coorListEnd = newCoors[newCoorsSize - 1];
			Coordinate coorListFir = newCoors[0];

			if (newCoorsSize == 1) {
				// Point
				newGeom = factory.createPoint(newCoors[0]);
			} else if (newCoorsSize > 3) {
				// if (coorListFir.equals(coorListEnd)) {
				// // Polygon
				// newGeom =
				// factory.createPolygon(factory.createLinearRing(newCoors),
				// null);
				// } else
				if (coorListFir.distance(coorListEnd) < 0.0001) {
					// Polygon
					newCoors[newCoorsSize - 1] = newCoors[0];
					newGeom = factory.createLineString(newCoors);
				//	newGeom = factory.createPolygon(factory.createLinearRing(newCoors), null);
				} else {
					// LineString
					newGeom = factory.createLineString(newCoors);
				}
			} else {
				// LineString
				newGeom = factory.createLineString(newCoors);
			}
			// polygonize & create new Geom
			SimpleFeatureType newSimpleFeatureType = DataUtilities.createType("newFeature" + newCount,
					"the_geom:" + newGeom.getGeometryType());
			SimpleFeature newSimpleFeature = SimpleFeatureBuilder.build(newSimpleFeatureType, new Object[] { newGeom },
					"newFeature" + newCount);
			newFeatureCollection.add(newSimpleFeature);
			newCount++;
		}
	}

}
