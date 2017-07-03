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

package com.git.gdsbuilder.validator.collection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.SchemaException;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.json.simple.JSONObject;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.FilterFactory2;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollection;
import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollectionList;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayer;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerList;
import com.git.gdsbuilder.type.validate.collection.ValidateLayerCollectionList;
import com.git.gdsbuilder.type.validate.collection.close.ValidateCloseCollectionLayer;
import com.git.gdsbuilder.type.validate.error.ErrorLayer;
import com.git.gdsbuilder.type.validate.error.ErrorLayerList;
import com.git.gdsbuilder.type.validate.layer.ValidateLayerType;
import com.git.gdsbuilder.type.validate.layer.ValidateLayerTypeList;
import com.git.gdsbuilder.type.validate.option.Admin;
import com.git.gdsbuilder.type.validate.option.AttributeFix;
import com.git.gdsbuilder.type.validate.option.B_SymbolOutSided;
import com.git.gdsbuilder.type.validate.option.BridgeName;
import com.git.gdsbuilder.type.validate.option.BuildingOpen;
import com.git.gdsbuilder.type.validate.option.ConBreak;
import com.git.gdsbuilder.type.validate.option.ConIntersected;
import com.git.gdsbuilder.type.validate.option.ConOverDegree;
import com.git.gdsbuilder.type.validate.option.CrossRoad;
import com.git.gdsbuilder.type.validate.option.EdgeMatchMiss;
import com.git.gdsbuilder.type.validate.option.EntityDuplicated;
import com.git.gdsbuilder.type.validate.option.EntityNone;
import com.git.gdsbuilder.type.validate.option.LayerMiss;
import com.git.gdsbuilder.type.validate.option.NodeMiss;
import com.git.gdsbuilder.type.validate.option.OutBoundary;
import com.git.gdsbuilder.type.validate.option.OverShoot;
import com.git.gdsbuilder.type.validate.option.RefAttributeMiss;
import com.git.gdsbuilder.type.validate.option.RefZValueMiss;
import com.git.gdsbuilder.type.validate.option.SelfEntity;
import com.git.gdsbuilder.type.validate.option.SmallArea;
import com.git.gdsbuilder.type.validate.option.SmallLength;
import com.git.gdsbuilder.type.validate.option.UnderShoot;
import com.git.gdsbuilder.type.validate.option.UselessEntity;
import com.git.gdsbuilder.type.validate.option.UselessPoint;
import com.git.gdsbuilder.type.validate.option.ValidatorOption;
import com.git.gdsbuilder.type.validate.option.WaterOpen;
import com.git.gdsbuilder.type.validate.option.ZValueAmbiguous;
import com.git.gdsbuilder.validator.collection.rule.MapSystemRule;
import com.git.gdsbuilder.validator.collection.rule.MapSystemRule.MapSystemRuleType;
import com.git.gdsbuilder.validator.layer.LayerValidatorImpl;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;

/**
 * ValidateLayerCollectionList를 검수하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:30:17
 */
public class CollectionValidator {

	ValidateLayerCollectionList validateLayerCollectionList;
	double tolorence = 0.001;
	ErrorLayerList errLayerList;
	Map<String, Object> progress;

	private static final Logger LOGGER = LoggerFactory.getLogger(CollectionValidator.class);

	/**
	 * CollectionValidator 생성자
	 * 
	 * @param validateLayerCollectionList
	 * @throws NoSuchAuthorityCodeException
	 * @throws SchemaException
	 * @throws FactoryException
	 * @throws TransformException
	 * @throws IOException
	 */
	public CollectionValidator(ValidateLayerCollectionList validateLayerCollectionList)
			throws NoSuchAuthorityCodeException, SchemaException, FactoryException, TransformException, IOException {
		this.validateLayerCollectionList = validateLayerCollectionList;
		this.progress = new HashMap<String, Object>();
		collectionValidate();
	}

	/**
	 * validateLayerCollectionList getter @author DY.Oh @Date 2017. 4. 18. 오후
	 * 3:30:23 @return ValidateLayerCollectionList @throws
	 */
	public ValidateLayerCollectionList getValidateLayerCollectionList() {
		return validateLayerCollectionList;
	}

	/**
	 * validateLayerCollectionList setter @author DY.Oh @Date 2017. 4. 18. 오후
	 * 3:30:24 @param validateLayerCollectionList void @throws
	 */
	public void setValidateLayerCollectionList(ValidateLayerCollectionList validateLayerCollectionList) {
		this.validateLayerCollectionList = validateLayerCollectionList;
	}

	/**
	 * errLayerList getter @author DY.Oh @Date 2017. 4. 18. 오후 3:30:26 @return
	 * ErrorLayerList @throws
	 */
	public ErrorLayerList getErrLayerList() {
		return errLayerList;
	}

	/**
	 * errLayerList setter @author DY.Oh @Date 2017. 4. 18. 오후 3:30:30 @param
	 * errLayerList void @throws
	 */
	public void setErrLayerList(ErrorLayerList errLayerList) {
		this.errLayerList = errLayerList;
	}

	public Map<String, Object> getProgress() {
		return progress;
	}

	public void setProgress(Map<String, Object> progress) {
		this.progress = progress;
	}

	/**
	 * validateLayerCollectionList를 검수 @author DY.Oh @Date 2017. 4. 18. 오후
	 * 3:30:31 @throws SchemaException @throws
	 * NoSuchAuthorityCodeException @throws FactoryException @throws
	 * TransformException void @throws
	 * 
	 * @throws IOException
	 */
	public void collectionValidate()
			throws SchemaException, NoSuchAuthorityCodeException, FactoryException, TransformException, IOException {

		this.errLayerList = new ErrorLayerList();
		ValidateLayerTypeList types = validateLayerCollectionList.getValidateLayerTypeList();
		GeoLayerCollectionList layerCollections = validateLayerCollectionList.getLayerCollectionList();

		MapSystemRule mapSystemRule = new MapSystemRule(-10, 10, -1, 1);

		for (int i = 0; i < layerCollections.size(); i++) {

			GeoLayerCollection collection = layerCollections.get(i);

			// layerMiss 검수
			layerMissValidate(types, collection);

			// geometric 검수
			geometricValidate(types, collection);

			// attribute 검수
			attributeValidate(types, collection);

			// 인접도엽 검수
			closeCollectionValidate(types, mapSystemRule, collection,"");
		}
	}

	private void attributeValidate(ValidateLayerTypeList types, GeoLayerCollection layerCollection)
			throws SchemaException {
		ErrorLayerList geoErrorList = new ErrorLayerList();
		String collectionName = layerCollection.getCollectionName();
		ErrorLayer errLayer = new ErrorLayer();
		for (int j = 0; j < types.size(); j++) {
			ValidateLayerType type = types.get(j);
			GeoLayerList typeLayers = validateLayerCollectionList.getTypeLayers(type.getTypeName(), layerCollection);
			List<ValidatorOption> options = type.getOptionList();
			if (options != null) {
				ErrorLayer typeErrorLayer = null;
				for (int k = 0; k < options.size(); k++) {
					ValidatorOption option = options.get(k);
					for (int a = 0; a < typeLayers.size(); a++) {
						GeoLayer typeLayer = typeLayers.get(a);
						if (typeLayer == null) {
							continue;
						}
						LayerValidatorImpl layerValidator = new LayerValidatorImpl(typeLayer);
						if (option instanceof BridgeName) {
							List<String> relationNames = ((BridgeName) option).getRelationType();
							for (int l = 0; l < relationNames.size(); l++) {
								typeErrorLayer = layerValidator.validateBridgeName(validateLayerCollectionList
										.getTypeLayers(relationNames.get(l), layerCollection));
							}
						}
						if (option instanceof Admin) {
							typeErrorLayer = layerValidator.validateAdmin();
						}
						if (option instanceof AttributeFix) {
							HashMap<String, List<String>> attributeNames = ((AttributeFix) option).getAttributeFixList();
							typeErrorLayer = layerValidator.validateAttributeFix(attributeNames);
						}
						if (option instanceof ZValueAmbiguous) {
							String attributeKey = ((ZValueAmbiguous) option).getAttributeKey();
							typeErrorLayer = layerValidator.validateZ_ValueAmbiguous(attributeKey);
						}
						if (typeErrorLayer != null) {
							errLayer.mergeErrorLayer(typeErrorLayer);
						}
					}
				}
			}
		}
		errLayer.setCollectionName(collectionName);
		geoErrorList.add(errLayer);
		this.errLayerMerge(geoErrorList);
	}

	private void geometricValidate(ValidateLayerTypeList types, GeoLayerCollection layerCollection)
			throws SchemaException, NoSuchAuthorityCodeException, FactoryException, TransformException, IOException {

		ErrorLayerList geoErrorList = new ErrorLayerList();
		GeoLayer neatLayer = layerCollection.getNeatLine();
		ErrorLayer errLayer = new ErrorLayer();

		for (int i = 0; i < types.size(); i++) {
			// getType
			ValidateLayerType type = types.get(i);
			// getTypeLayers
			GeoLayerList typeLayers = validateLayerCollectionList.getTypeLayers(type.getTypeName(), layerCollection);
			// getTypeOption
			List<ValidatorOption> options = type.getOptionList();
			if (options != null) {
				// typeValidate
				ErrorLayer typeErrorLayer = null;
				for (int k = 0; k < options.size(); k++) {
					ValidatorOption option = options.get(k);
					// typeLayerValidate
					for (int a = 0; a < typeLayers.size(); a++) {
						GeoLayer typeLayer = typeLayers.get(a);
						if (typeLayer == null) {
							continue;
						}
						LayerValidatorImpl layerValidator = new LayerValidatorImpl(typeLayer);

						typeErrorLayer = layerValidator.validateTwistedPolygon();

						if (typeErrorLayer != null) {
							errLayer.mergeErrorLayer(typeErrorLayer);
						}

						if (option instanceof ConBreak) {
							typeErrorLayer = layerValidator.validateConBreakLayers(neatLayer);
						}
						if (option instanceof ConIntersected) {
							typeErrorLayer = layerValidator.validateConIntersected();
						}
						if (option instanceof ConOverDegree) {
							double degree = ((ConOverDegree) option).getDegree();
							typeErrorLayer = layerValidator.validateConOverDegree(degree);
						}
						if (option instanceof ZValueAmbiguous) {
							String key = ((ZValueAmbiguous) option).getAttributeKey();
							typeErrorLayer = layerValidator.validateZ_ValueAmbiguous(key);
						}
						if (option instanceof UselessPoint) {
							typeErrorLayer = layerValidator.validateUselessPoint();
						}
						if (option instanceof EntityDuplicated) {
							typeErrorLayer = layerValidator.validateEntityDuplicated();
						}
						if (option instanceof OutBoundary) {
							List<String> relationNames = ((OutBoundary) option).getRelationType();
							for (int r = 0; r < relationNames.size(); r++) {
								typeErrorLayer = layerValidator.validateOutBoundary(validateLayerCollectionList
										.getTypeLayers(relationNames.get(r), layerCollection));
							}
							if (typeErrorLayer != null) {
								errLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if (option instanceof SmallArea) {
							double area = ((SmallArea) option).getArea();
							typeErrorLayer = layerValidator.validateSmallArea(area);
						}
						if (option instanceof SmallLength) {
							double length = ((SmallLength) option).getLength();
							typeErrorLayer = layerValidator.validateSmallLength(length);
						}
						if (option instanceof SelfEntity) {
							List<String> relationNames = ((SelfEntity) option).getRelationType();
							for (int r = 0; r < relationNames.size(); r++) {
								typeErrorLayer = layerValidator.validateSelfEntity(validateLayerCollectionList
										.getTypeLayers(relationNames.get(r), layerCollection));
							}
							if (typeErrorLayer != null) {
								errLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if (option instanceof OverShoot) {
							double tolerence = ((OverShoot) option).getTolerence();
							typeErrorLayer = layerValidator.validateOverShoot(neatLayer, tolerence);
						}
						if (option instanceof UnderShoot) {
							double tolerence = ((UnderShoot) option).getTolerence();
							typeErrorLayer = layerValidator.validateUnderShoot(neatLayer, tolerence);
						}
						if (option instanceof UselessEntity) {
							typeErrorLayer = layerValidator.validateUselessEntity();
						}
						if (option instanceof BuildingOpen) {
							typeErrorLayer = layerValidator.validateBuildingOpen();
						}
						if (option instanceof WaterOpen) {
							typeErrorLayer = layerValidator.validateWaterOpen();
						}
						if (option instanceof B_SymbolOutSided) {
							List<String> relationNames = ((B_SymbolOutSided) option).getRelationType();
							for (int l = 0; l < relationNames.size(); l++) {
								typeErrorLayer = layerValidator.vallidateB_SymbolOutSided(validateLayerCollectionList
										.getTypeLayers(relationNames.get(l), layerCollection));
							}
							if (typeErrorLayer != null) {
								errLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if (option instanceof CrossRoad) {
							List<String> relationNames = ((CrossRoad) option).getRelationType();
							for (int l = 0; l < relationNames.size(); l++) {
								typeErrorLayer = layerValidator.validateCrossRoad(validateLayerCollectionList
										.getTypeLayers(relationNames.get(l), layerCollection), "",tolorence);
							}
							if (typeErrorLayer != null) {
								errLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if (option instanceof NodeMiss) {
							List<String> relationNames = ((NodeMiss) option).getRelationType();
							for (int l = 0; l < relationNames.size(); l++) {
								typeErrorLayer = layerValidator.validateNodeMiss(validateLayerCollectionList
										.getTypeLayers(relationNames.get(l), layerCollection), "",tolorence);
							}
							if (typeErrorLayer != null) {
								errLayer.mergeErrorLayer(typeErrorLayer);
							}
						}

						if (typeErrorLayer != null) {
							errLayer.mergeErrorLayer(typeErrorLayer);
						}
					}
				}
			} else {
				continue;
			}
		}
		errLayer.setCollectionName(layerCollection.getCollectionName());
		geoErrorList.add(errLayer);
		this.errLayerMerge(geoErrorList);
		// errLayerList.add(errLayer);
	}

	@SuppressWarnings("unused")
	private void layerMissValidate(ValidateLayerTypeList types, GeoLayerCollection layerCollection)
			throws SchemaException {
		// TODO Auto-generated method stub

		List<GeoLayer> collectionList = layerCollection.getLayers();
		ErrorLayer errLayer = new ErrorLayer();
		for (int j = 0; j < types.size(); j++) {
			ValidateLayerType type = types.get(j);
			GeoLayerList typeLayers = validateLayerCollectionList.getTypeLayers(type.getTypeName(), layerCollection);
			List<ValidatorOption> options = type.getOptionList();
			if (options != null) {
				ErrorLayer typeErrorLayer = null;
				for (int k = 0; k < options.size(); k++) {
					ValidatorOption option = options.get(k);
					for (int l = 0; l < typeLayers.size(); l++) {
						GeoLayer typeLayer = typeLayers.get(l);
						if (typeLayer == null) {
							continue;
						}
						LayerValidatorImpl layerValidator = new LayerValidatorImpl(typeLayer);
						if (option instanceof LayerMiss) {
							List<String> typeNames = ((LayerMiss) option).getLayerType();
							typeErrorLayer = layerValidator.validateLayerMiss(typeNames);
							if (typeErrorLayer != null) {
								errLayer.mergeErrorLayer(typeErrorLayer);
							}
							collectionList.remove(typeLayer);
						}
					}
				}
			}
		}
		if (errLayer != null) {
			errLayer.setCollectionName(layerCollection.getCollectionName());
			errLayer.setCollectionType(layerCollection.getLayerCollectionType());
			errLayerList.add(errLayer);
		}
	}

	// closeValidate

	@SuppressWarnings("unused")
	private void closeCollectionValidate(ValidateLayerTypeList types, MapSystemRule mapSystemRule,
			GeoLayerCollection layerCollection, String geomColunm) throws SchemaException {
		// TODO Auto-generated method stub
		FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();
		
		String geomCol = "geom"; //임시선언 geometry 컬럼
		
		
		LayerValidatorImpl layerValidator = new LayerValidatorImpl();

		if (layerCollection != null) {
			String neatLineName = layerCollection.getNeatLine().getLayerName();
			SimpleFeatureCollection neatLineCollection = layerCollection.getNeatLine().getSimpleFeatureCollection();
			SimpleFeatureIterator featureIterator = neatLineCollection.features();

			// 인접도엽 변수선언
			GeoLayerCollection topGeoCollection = null;
			GeoLayerCollection bottomGeoCollection = null;
			GeoLayerCollection leftGeoCollection = null;
			GeoLayerCollection rightGeoCollection = null;

			GeoLayerCollectionList collectionList = this.validateLayerCollectionList.getLayerCollectionList();

			if (collectionList != null) {
				String collectionName = layerCollection.getCollectionName();
				try {
					// 인접도엽 GET
					int collectionNum = Integer.parseInt(collectionName); // 대상도엽번호

					int topColltionNum = collectionNum + mapSystemRule.getMapSystemlRule(MapSystemRuleType.TOP);
					int bottomColltionNum = collectionNum + mapSystemRule.getMapSystemlRule(MapSystemRuleType.BOTTOM);
					int leftColltionNum = collectionNum + mapSystemRule.getMapSystemlRule(MapSystemRuleType.LEFT);
					int rightColltionNum = collectionNum + mapSystemRule.getMapSystemlRule(MapSystemRuleType.RIGHT);

					for (GeoLayerCollection nearCollection : collectionList) {
						try {
							String nearCollectionName = nearCollection.getCollectionName();
							int nearCollectionNum = Integer.parseInt(nearCollectionName);

							if (topColltionNum == nearCollectionNum) {
								topGeoCollection = nearCollection;
							}
							if (bottomColltionNum == nearCollectionNum) {
								bottomGeoCollection = nearCollection;
							}
							if (leftColltionNum == nearCollectionNum) {
								leftGeoCollection = nearCollection;
							}
							if (rightColltionNum == nearCollectionNum) {
								rightGeoCollection = nearCollection;
							}
						} catch (NumberFormatException e) {
							// TODO: handle exception
							LOGGER.info("인접도엽이름 정수아님");
						}

					}
				} catch (NumberFormatException e) {
					LOGGER.info("대상도엽 숫자아님");
					return;
				}
			} else {
				LOGGER.info("도엽리스트 Null");
				return;
			}

			// 도곽 좌표 추출
			double minx = 0.0;
			double miny = 0.0;
			double maxx = 0.0;
			double maxy = 0.0;

			int i = 0;
			while (featureIterator.hasNext()) {
				if (i == 0) {
					SimpleFeature feature = featureIterator.next();
					Geometry geometry = (Geometry) feature.getDefaultGeometry();
					Coordinate[] coordinateArray = geometry.getEnvelope().getCoordinates();
					Coordinate minCoordinate = new Coordinate();
					Coordinate maxCoordinate = new Coordinate();

					minCoordinate = coordinateArray[0];
					maxCoordinate = coordinateArray[2];

					minx = minCoordinate.x;
					miny = minCoordinate.y;
					maxx = maxCoordinate.x;
					maxy = maxCoordinate.y;
					i++;
				} else {
					LOGGER.info("도곽레이어 객체 1개이상");
					return;
				}
			}

			GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
			
			// 도엽라인객체 생성
			Coordinate[] topLineCoords = new Coordinate[] { new Coordinate(minx, miny), new Coordinate(maxx, miny) };
			Coordinate[] bottomLineCoords = new Coordinate[] { new Coordinate(minx, maxy), new Coordinate(maxx, maxy) };
			Coordinate[] leftLineCoords = new Coordinate[] { new Coordinate(minx, miny), new Coordinate(minx, maxy) };
			Coordinate[] rightLineCoords = new Coordinate[] { new Coordinate(maxx, miny), new Coordinate(maxx, maxy) };
			
			LineString topLineString = geometryFactory.createLineString(topLineCoords);
			LineString bottomLineString = geometryFactory.createLineString(bottomLineCoords);
			LineString leftLineString = geometryFactory.createLineString(leftLineCoords);
			LineString rightLineString = geometryFactory.createLineString(rightLineCoords);
			
			Map<MapSystemRuleType, LineString> collectionBoundary = new HashMap<MapSystemRule.MapSystemRuleType, LineString>();
			
			collectionBoundary.put(MapSystemRuleType.TOP, topLineString);
			collectionBoundary.put(MapSystemRuleType.BOTTOM, bottomLineString);
			collectionBoundary.put(MapSystemRuleType.LEFT, leftLineString);
			collectionBoundary.put(MapSystemRuleType.RIGHT, rightLineString);
			

			// 대상도엽 객체 GET 폴리곤 생성
			Coordinate[] topCoords = new Coordinate[] { new Coordinate(minx, miny),
					new Coordinate(maxx, miny), new Coordinate(maxx, maxy+tolorence), new Coordinate(minx, maxy+tolorence),
					new Coordinate(minx, miny) };
			Coordinate[] bottomCoords = new Coordinate[] { new Coordinate(minx, maxy), new Coordinate(maxx, maxy),
					new Coordinate(maxx, maxy - tolorence), new Coordinate(minx, maxy - tolorence),
					new Coordinate(minx, maxy) };
			Coordinate[] leftCoords = new Coordinate[] { new Coordinate(minx, miny),
					new Coordinate(minx+tolorence, miny), new Coordinate(minx+tolorence, maxy), new Coordinate(minx, maxy),
					new Coordinate(minx, miny) };
			Coordinate[] rightCoords = new Coordinate[] { new Coordinate(maxx, miny),
					new Coordinate(maxx - tolorence, miny), new Coordinate(maxx - tolorence, maxy),
					new Coordinate(maxx, maxy), new Coordinate(maxx, miny) };

			LinearRing topRing = geometryFactory.createLinearRing(topCoords);
			LinearRing bottomRing = geometryFactory.createLinearRing(bottomCoords);
			LinearRing leftRing = geometryFactory.createLinearRing(leftCoords);
			LinearRing rightRing = geometryFactory.createLinearRing(rightCoords);

			LinearRing holes[] = null; // use LinearRing[] to represent holes
			Polygon topPolygon = geometryFactory.createPolygon(topRing, holes);
			Polygon bottomPolygon = geometryFactory.createPolygon(bottomRing, holes);
			Polygon leftPolygon = geometryFactory.createPolygon(leftRing, holes);
			Polygon rightPolygon = geometryFactory.createPolygon(rightRing, holes);

			// 인접도엽 top, bottom, left, right 객체로드를 위한 Map 생성
			  Map<MapSystemRuleType, Polygon> targetFeaturesGetBoundary = new HashMap<MapSystemRuleType, Polygon>();
			  targetFeaturesGetBoundary.put(MapSystemRuleType.TOP, topPolygon);
			  targetFeaturesGetBoundary.put(MapSystemRuleType.BOTTOM,bottomPolygon);
			  targetFeaturesGetBoundary.put(MapSystemRuleType.LEFT, leftPolygon);
			  targetFeaturesGetBoundary.put(MapSystemRuleType.RIGHT,rightPolygon);
			 

			// 인접도엽 객체 GET 폴리곤생성
			Coordinate[] nearTopCoords = new Coordinate[] { new Coordinate(minx, miny - tolorence),
					new Coordinate(maxx, miny - tolorence), new Coordinate(maxx, maxy), new Coordinate(minx, maxy),
					new Coordinate(minx, miny - tolorence) };
			Coordinate[] nearBottomCoords = new Coordinate[] { new Coordinate(minx, maxy), new Coordinate(maxx, maxy),
					new Coordinate(maxx, maxy + tolorence), new Coordinate(minx, maxy + tolorence),
					new Coordinate(minx, maxy) };
			Coordinate[] nearLeftCoords = new Coordinate[] { new Coordinate(minx - tolorence, miny),
					new Coordinate(minx, miny), new Coordinate(minx, maxy), new Coordinate(minx - tolorence, maxy),
					new Coordinate(minx - tolorence, miny) };
			Coordinate[] nearRightCoords = new Coordinate[] { new Coordinate(maxx, miny),
					new Coordinate(maxx + tolorence, miny), new Coordinate(maxx + tolorence, maxy),
					new Coordinate(maxx, maxy), new Coordinate(maxx, miny) };

			LinearRing nearTopRing = geometryFactory.createLinearRing(nearTopCoords);
			LinearRing nearBottomRing = geometryFactory.createLinearRing(nearBottomCoords);
			LinearRing nearLeftRing = geometryFactory.createLinearRing(nearLeftCoords);
			LinearRing nearRightRing = geometryFactory.createLinearRing(nearRightCoords);

			LinearRing nearHoles[] = null; // use LinearRing[] to represent
											// holes
			Polygon nearTopPolygon = geometryFactory.createPolygon(nearTopRing, holes);
			Polygon nearBottomPolygon = geometryFactory.createPolygon(nearBottomRing, holes);
			Polygon nearLeftPolygon = geometryFactory.createPolygon(nearLeftRing, holes);
			Polygon nearRightPolygon = geometryFactory.createPolygon(nearRightRing, holes);

			// 인접도엽 top, bottom, left, right 객체로드를 위한 Map 생성
			Map<MapSystemRuleType, Polygon> nearFeaturesGetBoundary = new HashMap<MapSystemRuleType, Polygon>();
			nearFeaturesGetBoundary.put(MapSystemRuleType.TOP, topPolygon);
			nearFeaturesGetBoundary.put(MapSystemRuleType.BOTTOM, bottomPolygon);
			nearFeaturesGetBoundary.put(MapSystemRuleType.LEFT, leftPolygon);
			nearFeaturesGetBoundary.put(MapSystemRuleType.RIGHT, rightPolygon);

			
			ErrorLayer errLayer = new ErrorLayer();
			
			
			for (ValidateLayerType layerType : types) {
				GeoLayerList typeLayers = validateLayerCollectionList.getTypeLayers(layerType.getTypeName(),layerCollection);
				List<ValidatorOption> options = layerType.getOptionList();
				if (options != null) {
					ErrorLayer typeErrorLayer = null;

					// 인접관련 옵션 객체선언
					EntityNone entityNone = null;
					EdgeMatchMiss matchMiss = null;
					RefZValueMiss refZValueMiss = null;
					RefAttributeMiss refAttributeMiss = null;
					
					List<ValidatorOption> optionList = new ArrayList<ValidatorOption>();
					for (ValidatorOption option : options) {
						if (option instanceof EntityNone || option instanceof RefAttributeMiss || option instanceof EdgeMatchMiss || option instanceof RefZValueMiss) {
							optionList.add(option);
						}
					}
					
					
					for (GeoLayer geoLayer : typeLayers) {
						Map<String, GeoLayer> nearGeoLayers = new HashMap<String, GeoLayer>();
						layerValidator.setValidatorLayer(geoLayer);
																																																			// 선언
						
						Map<MapSystemRuleType, GeoLayer> collctionMap = new HashMap<MapSystemRule.MapSystemRuleType, GeoLayer>();
						GeoLayer topLayer = null;
						GeoLayer bottomLayer = null;
						GeoLayer leftLayer = null;
						GeoLayer rightLayer = null;
						
						
						if (geoLayer != null) {
							if (topGeoCollection != null) {
								topLayer = topGeoCollection.getLayer(geoLayer.getLayerName(),topGeoCollection);
								collctionMap.put(MapSystemRuleType.TOP, topLayer);
							}
							if (bottomGeoCollection != null) {
								bottomLayer = bottomGeoCollection.getLayer(geoLayer.getLayerName(),bottomGeoCollection);
								collctionMap.put(MapSystemRuleType.BOTTOM, topLayer);
							}
							if (leftGeoCollection != null) {
								leftLayer = leftGeoCollection.getLayer(geoLayer.getLayerName(),leftGeoCollection);
								collctionMap.put(MapSystemRuleType.LEFT, topLayer);
							}
							if (rightGeoCollection != null) {
								rightLayer = rightGeoCollection.getLayer(geoLayer.getLayerName(),rightGeoCollection);
								collctionMap.put(MapSystemRuleType.RIGHT, topLayer);
							}
							
							
							ValidateCloseCollectionLayer closeCollectionLayer = new ValidateCloseCollectionLayer(
									geoLayer, collctionMap, tolorence, optionList, collectionBoundary, targetFeaturesGetBoundary, nearFeaturesGetBoundary);
							
							
							typeErrorLayer = layerValidator.validateCloseCollection(closeCollectionLayer, geomCol);
							
							if(typeErrorLayer !=null){
								errLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
					}
				}
			}
			if (errLayer != null) {
				errLayer.setCollectionName(layerCollection.getCollectionName());
				errLayer.setCollectionType(layerCollection.getLayerCollectionType());
				errLayerList.add(errLayer);
			}
		}

		/*for (int i = 0; i < layerCollections.size(); i++) {
			GeoLayerCollection collection = layerCollections.get(i);
			List<GeoLayer> collectionList = collection.getLayers();
			ErrorLayer errLayer = new ErrorLayer();
			for (int j = 0; j < types.size(); j++) {
				ValidateLayerType type = types.get(j);
				GeoLayerList typeLayers = validateLayerCollectionList.getTypeLayers(type.getTypeName(), collection);
				List<ValidatorOption> options = type.getOptionList();
				if (options != null) {
					ErrorLayer typeErrorLayer = null;
					for (int k = 0; k < options.size(); k++) {
						ValidatorOption option = options.get(k);
						for (int l = 0; l < typeLayers.size(); l++) {
							GeoLayer typeLayer = typeLayers.get(l);
							if (typeLayer == null) {
								continue;
							}
							if (option instanceof LayerMiss) {
								List<String> typeNames = ((LayerMiss) option).getLayerType();
								typeErrorLayer = layerValidator.validateLayerMiss(typeNames);
								if (typeErrorLayer != null) {
									errLayer.mergeErrorLayer(typeErrorLayer);
								}
								collectionList.remove(typeLayer);
							}
						}
					}
				}
			}
			if (errLayer != null) {
				errLayer.setCollectionName(collection.getCollectionName());
				errLayer.setCollectionType(collection.getLayerCollectionType());
				errLayerList.add(errLayer);
			}
		}*/
	}

	private void errLayerMerge(ErrorLayerList geoErrorList) {
		for (int i = 0; i < errLayerList.size(); i++) {
			ErrorLayer errorLayer = errLayerList.get(i);
			String errorLayerName = errorLayer.getCollectionName();
			for (int j = 0; j < geoErrorList.size(); j++) {
				ErrorLayer geoErrLayer = geoErrorList.get(j);
				String geoErrLayerName = geoErrLayer.getCollectionName();
				if (errorLayerName.equals(geoErrLayerName)) {
					errorLayer.mergeErrorLayer(geoErrLayer);
				}
			}
		}
	}
}
