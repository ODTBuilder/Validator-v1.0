package com.git.gdsbuilder.generalization.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.data.DataStoreFinder;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.FileDataStoreFactorySpi;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.SchemaException;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CRSAuthorityFactory;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import com.git.gdsbuilder.generalization.Simplification;
import com.git.gdsbuilder.generalization.data.res.DTGeneralEAfLayer;
import com.git.gdsbuilder.generalization.opt.SimplificationOption;
import com.git.gdsbuilder.generalization.rep.DTGeneralReport;
import com.git.gdsbuilder.generalization.rep.type.DTGeneralReportNums;
import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineSegment;
import com.vividsolutions.jts.geom.LineString;

public class SimplificationImpl implements Simplification {

	private SimplificationOption option;
	private SimpleFeatureCollection collection;

	public SimplificationImpl() {
	}

	public SimplificationImpl(SimpleFeatureCollection collection, SimplificationOption option) {
		this.collection = collection;
		this.option = option;
	}

	public SimpleFeatureCollection getCollection() {
		return collection;
	}

	public void setCollection(SimpleFeatureCollection collection) {
		this.collection = collection;
	}

	public SimplificationOption getOption() {
		return option;
	}

	public void setOption(SimplificationOption option) {
		this.option = option;
	}

	public DTGeneralEAfLayer getSimplification() {
		/*
		 * //일반화 결과 레이어 생성 및 초기화 //DTGeneralEAfLayer - 토폴로지 빌드를 하지 않은 결과레이어
		 * DTGeneralEAfLayer afLayer = new DTGeneralEAfLayer();
		 * 
		 * //Simplification 결과 -> DTGeneralEAfLayer 선언 및 초기화 DTGeneralReportNums
		 * entityNums = new DTGeneralReportNums(0,0); DTGeneralReportNums
		 * pointNums = new DTGeneralReportNums(0,0);
		 * 
		 * //결과레포트 생성 및 초기화 DTGeneralReport resultReport = new
		 * DTGeneralReport(entityNums, pointNums);
		 * 
		 * //SimpleFeatureCollection 생성 및 초기화 -> 일반화 결과 SimpleFeatureCollection
		 * DefaultFeatureCollection resultCollection = new
		 * DefaultFeatureCollection();
		 * 
		 * 
		 * if(collection!=null&&option!=null){ SimpleFeatureIterator
		 * featureIterator = collection.features();
		 * 
		 * //Simplification Report Nums 결과 int entityPreNum = collection.size();
		 * int entityAfNum = 0; int pointPreNum = 0; int pointAfNum = 0;
		 * 
		 * int testNum = 0;
		 * 
		 * while(featureIterator.hasNext()){ SimpleFeature feature =
		 * featureIterator.next(); Geometry geom = (Geometry)
		 * feature.getDefaultGeometry(); pointPreNum = pointPreNum +
		 * geom.getNumPoints();
		 * 
		 * Geometry newGeom = TopologyPreservingSimplifier.simplify(geom,
		 * option.getOption()); System.out.println(testNum + "번째 일반화 되는중이다");
		 * pointAfNum = pointAfNum + newGeom.getNumPoints();
		 * 
		 * feature.setDefaultGeometry(newGeom); resultCollection.add(feature);
		 * 
		 * testNum++; }
		 * 
		 * try { entityAfNum =resultCollection.getCount(); } catch (IOException
		 * e) { // TODO Auto-generated catch block return null; } //객체, 포인트수 SET
		 * entityNums.setPreNum(entityPreNum); entityNums.setAfNum(entityAfNum);
		 * pointNums.setPreNum(pointPreNum); pointNums.setAfNum(pointAfNum);
		 * 
		 * //결과레포트 SET resultReport.setEntityNum(entityNums);
		 * resultReport.setPointNum(pointNums);
		 * 
		 * //결과레이어 SET afLayer.setCollection(resultCollection);
		 * afLayer.setReport(resultReport); } else return null;
		 */

		// 임의

		File file = new File("C:\\Users\\GIT\\Desktop\\CoastlineOrigin", "CoastlineOrigin_multilinestring.shp");
		SimpleFeatureCollection collection = getShpObject(file);

		this.collection = collection;

		// 일반화 결과 레이어 생성 및 초기화
		// DTGeneralEAfLayer - 토폴로지 빌드를 하지 않은 결과레이어
		DTGeneralEAfLayer afLayer = new DTGeneralEAfLayer();

		// Simplification 결과 -> DTGeneralEAfLayer 선언 및 초기화
		DTGeneralReportNums entityNums = new DTGeneralReportNums(0, 0);
		DTGeneralReportNums pointNums = new DTGeneralReportNums(0, 0);

		// 결과레포트 생성 및 초기화
		DTGeneralReport resultReport = new DTGeneralReport(entityNums, pointNums);

		// SimpleFeatureCollection 생성 및 초기화 -> 일반화 결과 SimpleFeatureCollection
		DefaultFeatureCollection resultCollection = new DefaultFeatureCollection();

		if (collection != null) {
			SimpleFeatureIterator featureIterator = collection.features();

			// Simplification Report Nums 결과
			int entityPreNum = collection.size();
			int entityAfNum = 0;
			int pointPreNum = 0;
			int pointAfNum = 0;

			Property featuerIDPro = null;
			CRSAuthorityFactory factory = CRS.getAuthorityFactory(true);

			CoordinateReferenceSystem crs = null;
			try {
				crs = factory.createCoordinateReferenceSystem("EPSG:4326");
			} catch (NoSuchAuthorityCodeException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FactoryException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// GeometryFactory geometryFactory =
			// JTSFactoryFinder.getGeometryFactory();

			// 폐합 단일 라인객체 최소길이
			int minLength = 500;

			// 객체 라인 허용최대거리
			int maximalDistance = 1000;

			double rTolerance = 1000;
			double angle = 10;

			// 일반화 진행
			while (featureIterator.hasNext()) {
				SimpleFeature feature = featureIterator.next();

				String featureId = feature.getID();
				System.out.println(featureId);

				Geometry geometry = (Geometry) feature.getDefaultGeometry();
				Coordinate[] coordinates = geometry.getCoordinates();
				int size = coordinates.length;
				double allDistance = 0;
				boolean isLargeClosed = false;

				if (size > 0) {
					Coordinate chkStart = coordinates[0];
					Coordinate chkEnd = coordinates[size - 1];

					if (chkStart.equals2D(chkEnd)) {
						for (int i = 0; i < size - 1; i++) {
							double distance = 0;
							Coordinate start = coordinates[i];
							Coordinate end = coordinates[i + 1];
							try {

								distance = JTS.orthodromicDistance(start, end, crs);
							} catch (TransformException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							allDistance = allDistance + distance;
						}
						if (allDistance < minLength) {
							continue;
						} else if (allDistance > minLength) {
							isLargeClosed = true;
						}
					}
					// 폐합됐는데 길이 500 이상
					if (isLargeClosed) {
						// 임시 List에 넣기
						List<Coordinate> tmpCoos = new ArrayList<>();
						for (int i = 0; i < coordinates.length; i++) {
							tmpCoos.add(coordinates[i]);
						}

						int key = 0;
						int listSize = tmpCoos.size();

						for (int i = key; i < listSize - 3; i++) {
							Coordinate start = tmpCoos.get(key);
							Coordinate secnd = tmpCoos.get(key + 1);
							Coordinate third = tmpCoos.get(key + 2);
							try {
								if (Math.abs(180 - Angle.toDegrees(Angle.angleBetween(start, secnd, third))) < 58) {
									key++;
									continue;
								} else if (JTS.orthodromicDistance(start, third, crs) < rTolerance) {
									tmpCoos.remove(key + 1);
									// 동기화
									Collections.synchronizedCollection(tmpCoos);
								}
							} catch (TransformException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (tmpCoos.size() < 4) {
							continue;
						}
						Coordinate[] finCoors = new Coordinate[tmpCoos.size()];
						for (int i = 0; i < tmpCoos.size(); i++) {
							finCoors[i] = tmpCoos.get(i);
						}
						Geometry returnGeom = new GeometryFactory().createLineString(finCoors);
						feature.setDefaultGeometry(returnGeom);
						resultCollection.add(feature);
					} else {
						// 임시 List에 넣기
						List<Coordinate> tmpCoos = new ArrayList<>();
						for (int i = 0; i < coordinates.length; i++) {
							tmpCoos.add(coordinates[i]);
						}

						int key = 0;
						int listSize = tmpCoos.size();

						for (int i = key; i < listSize - 3; i++) {
							Coordinate start = tmpCoos.get(key);
							Coordinate secnd = tmpCoos.get(key + 1);
							Coordinate third = tmpCoos.get(key + 2);
							try {
								if (key + 2 < listSize && JTS.orthodromicDistance(start, third, crs) < rTolerance
										&& Math.abs(
												180 - Angle.toDegrees(Angle.angleBetween(start, secnd, third))) < 58) {
									tmpCoos.remove(key + 1);
									// 동기화
									Collections.synchronizedCollection(tmpCoos);
								} else {
									key++;
									continue;
								}
							} catch (TransformException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						Coordinate[] finCoors = new Coordinate[tmpCoos.size()];
						for (int i = 0; i < tmpCoos.size(); i++) {
							finCoors[i] = tmpCoos.get(i);
						}
						Geometry returnGeom = new GeometryFactory().createLineString(finCoors);
						feature.setDefaultGeometry(returnGeom);
						resultCollection.add(feature);
					}
				}
			}

			try {
				entityAfNum = resultCollection.getCount();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return null;
			}
			// 객체, 포인트수 SET
			entityNums.setPreNum(entityPreNum);
			entityNums.setAfNum(entityAfNum);
			pointNums.setPreNum(pointPreNum);
			pointNums.setAfNum(pointAfNum);

			// 결과레포트 SET
			resultReport.setEntityNum(entityNums);
			resultReport.setPointNum(pointNums);

			// 결과레이어 SET
			afLayer.setCollection(resultCollection);
			afLayer.setReport(resultReport);
		} else
			return null;

		try

		{
			this.writeSHP(resultCollection, "D:\\Generalization\\test.shp");
		} catch (NoSuchAuthorityCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SchemaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return afLayer;
	}

	public LineString refineLineString(LineString ls, double maximalDistance) {
		// list to store coordinates for resulting line string
		ArrayList<Coordinate> resultLineStringCoordinates = new ArrayList();
		// list of LineSegments from input LineString
		ArrayList<LineSegment> segmentList = new ArrayList();
		for (int i = 1; i < ls.getCoordinates().length; i++) {
			// create LineSegments from input LineString and add them to list
			segmentList.add(new LineSegment(ls.getCoordinates()[i - 1], ls.getCoordinates()[i]));
		}
		boolean isFirstSegment = true;
		// refine each LineSegment if necessary
		for (LineSegment currentSegment : segmentList) {
			// add startpoint from first segment only
			if (isFirstSegment) {
				resultLineStringCoordinates.add(new Coordinate(currentSegment.p0));
				isFirstSegment = false;
			}
			// refinement necessary
			if (currentSegment.getLength() > maximalDistance) {
				// calculate distance between coordinates as fraction (0-1)
				double distanceFraction = 1 / (currentSegment.getLength() / maximalDistance);
				for (double currentFraction = distanceFraction; currentFraction < 1; currentFraction +=

						distanceFraction) {
					// add coordinates from calculated fraction
					resultLineStringCoordinates.add(new Coordinate(currentSegment.pointAlong

					(currentFraction)));
				}
			}
			// add segment endpoint coordinate to result list
			resultLineStringCoordinates.add(new Coordinate(currentSegment.p1));
		}
		return new GeometryFactory().createLineString(
				resultLineStringCoordinates.toArray(new Coordinate[resultLineStringCoordinates.size()]));
	}

	public SimpleFeatureCollection getShpObject(File shpFile) {

		Map<String, Object> map = new HashMap<String, Object>();
		ShapefileDataStore dataStore;

		String typeName;
		SimpleFeatureCollection collection = null;

		try {
			map.put("url", shpFile.toURI().toURL());
			dataStore = (ShapefileDataStore) DataStoreFinder.getDataStore(map);

			typeName = dataStore.getTypeNames()[0];
			Charset UTF_8 = Charset.forName("EUC-KR");
			dataStore.setCharset(UTF_8);
			SimpleFeatureSource source = dataStore.getFeatureSource(typeName);
			Filter filter = Filter.INCLUDE;
			collection = source.getFeatures(filter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return collection;
	}

	public void writeSHP(SimpleFeatureCollection simpleFeatureCollection, String filePath)
			throws IOException, SchemaException, NoSuchAuthorityCodeException, FactoryException {

		FileDataStoreFactorySpi factory = new ShapefileDataStoreFactory();

		File file = new File(filePath);
		Map map = Collections.singletonMap("url", file.toURI().toURL());

		ShapefileDataStore myData = (ShapefileDataStore) factory.createNewDataStore(map);

		SimpleFeatureType featureType = simpleFeatureCollection.getSchema();
		myData.createSchema(featureType);

		Transaction transaction = new DefaultTransaction("create");

		String typeName = myData.getTypeNames()[0];
		SimpleFeatureSource featureSource = myData.getFeatureSource(typeName);

		if (featureSource instanceof SimpleFeatureStore) {
			SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
			featureStore.setTransaction(transaction);
			try {
				featureStore.addFeatures(simpleFeatureCollection);
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
			} finally {
				transaction.close();
			}
			System.out.println("Success!");
			System.exit(0);
		} else {
			System.out.println(typeName + " does not support read/write access");
			System.exit(1);
		}
	}
}