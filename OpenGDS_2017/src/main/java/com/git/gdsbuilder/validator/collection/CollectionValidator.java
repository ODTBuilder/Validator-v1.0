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
 *    OpenGDS_2018
 *    Lesser General Public License for more details.
 */

package com.git.gdsbuilder.validator.collection;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.SchemaException;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.json.simple.JSONObject;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollection;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayer;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerList;
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
import com.git.gdsbuilder.type.validate.option.BuildingSite;
import com.git.gdsbuilder.type.validate.option.CemeterySite;
import com.git.gdsbuilder.type.validate.option.CenterLineMiss;
import com.git.gdsbuilder.type.validate.option.ConBreak;
import com.git.gdsbuilder.type.validate.option.ConIntersected;
import com.git.gdsbuilder.type.validate.option.ConOverDegree;
import com.git.gdsbuilder.type.validate.option.CrossRoad;
import com.git.gdsbuilder.type.validate.option.EdgeMatchMiss;
import com.git.gdsbuilder.type.validate.option.EntityDuplicated;
import com.git.gdsbuilder.type.validate.option.EntityInHole;
import com.git.gdsbuilder.type.validate.option.EntityNone;
import com.git.gdsbuilder.type.validate.option.HoleMisplacement;
import com.git.gdsbuilder.type.validate.option.HouseAttribute;
import com.git.gdsbuilder.type.validate.option.LayerMiss;
import com.git.gdsbuilder.type.validate.option.LinearDisconnection;
import com.git.gdsbuilder.type.validate.option.NeatLineAttribute;
import com.git.gdsbuilder.type.validate.option.NodeMiss;
import com.git.gdsbuilder.type.validate.option.NumericalValue;
import com.git.gdsbuilder.type.validate.option.OneAcre;
import com.git.gdsbuilder.type.validate.option.OneStage;
import com.git.gdsbuilder.type.validate.option.OutBoundary;
import com.git.gdsbuilder.type.validate.option.OverShoot;
import com.git.gdsbuilder.type.validate.option.PointDuplicated;
import com.git.gdsbuilder.type.validate.option.RefAttributeMiss;
import com.git.gdsbuilder.type.validate.option.RefZValueMiss;
import com.git.gdsbuilder.type.validate.option.RiverBoundaryMiss;
import com.git.gdsbuilder.type.validate.option.SelfEntity;
import com.git.gdsbuilder.type.validate.option.SmallArea;
import com.git.gdsbuilder.type.validate.option.SmallLength;
import com.git.gdsbuilder.type.validate.option.UFIDLength;
import com.git.gdsbuilder.type.validate.option.UFIDRule;
import com.git.gdsbuilder.type.validate.option.UnderShoot;
import com.git.gdsbuilder.type.validate.option.UselessEntity;
import com.git.gdsbuilder.type.validate.option.UselessPoint;
import com.git.gdsbuilder.type.validate.option.ValidatorOption;
import com.git.gdsbuilder.type.validate.option.WaterOpen;
import com.git.gdsbuilder.type.validate.option.ZValueAmbiguous;
import com.git.gdsbuilder.validator.collection.opt.ValCollectionOption;
import com.git.gdsbuilder.validator.collection.rule.MapSystemRule;
import com.git.gdsbuilder.validator.collection.rule.MapSystemRule.MapSystemRuleType;
import com.git.gdsbuilder.validator.layer.LayerValidator;
import com.git.gdsbuilder.validator.layer.LayerValidatorImpl;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Polygon;

/**
 * ValidateLayerCollectionList를 검수하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:30:17
 */
public class CollectionValidator {

	protected static double lineInvadedTolorence = 0.01; // 선형이 면형 객체 침범 (m)
	protected static double polygonInvadedTolorence = 0.01; // 면형이 면형 객체 침범
	// (m2)
	protected static double lineOverTolorence = 0.01; // 중심선이 경계면 초과 (m2)
	protected static double areaRatioTolorence = 0.1; // 지류계와 경지계 불일치 (%)
	protected static double spatialAccuracyTolorence = 0.01; // 공간분석 정밀도 설정 (m)
	protected static double underShootTolorence = 0.2;
	protected static double selfEntityLineTolerance = 0.01;

	// ValidateLayerCollectionList validateLayerCollectionList;
	ErrorLayerList errLayerList;
	// Map<String, Object> progress;
	// String collectionType;

	GeoLayerCollection collection;
	List<GeoLayerCollection> nearCollections;
	ValidateLayerTypeList types;
	MapSystemRule mapSystemRule;
	Map<String, Object> progress;
	String collectionType;

	private static final Logger LOGGER = LoggerFactory.getLogger(CollectionValidator.class);

	/**
	 * CollectionValidator 생성자
	 * 
	 * @param validateLayerCollectionList
	 * @param fileType
	 * @throws NoSuchAuthorityCodeException
	 * @throws SchemaException
	 * @throws FactoryException
	 * @throws TransformException
	 * @throws IOException
	 */
	/*
	 * public CollectionValidator(ValidateLayerCollectionList
	 * validateLayerCollectionList, String fileType) throws
	 * NoSuchAuthorityCodeException, SchemaException, FactoryException,
	 * TransformException, IOException { this.validateLayerCollectionList =
	 * validateLayerCollectionList; this.progress = new HashMap<String,
	 * Object>(); this.collectionType = fileType; collectionValidate(); }
	 */

	public CollectionValidator(GeoLayerCollection collection, List<GeoLayerCollection> nearCollections,
			ValidateLayerTypeList types, MapSystemRule mapSystemRule, String fileType)
			throws NoSuchAuthorityCodeException, SchemaException, FactoryException, TransformException, IOException {

		this.collection = collection;
		this.nearCollections = nearCollections;
		this.types = types;
		this.mapSystemRule = mapSystemRule;
		this.collectionType = fileType;
		this.errLayerList = new ErrorLayerList();
		this.progress = new HashMap<String, Object>();
		collectionValidate();
	}

	public String getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}

	/**
	 * validateLayerCollectionList getter @author DY.Oh @Date 2017. 4. 18. 오후
	 * 3:30:23 @return ValidateLayerCollectionList @throws
	 */
	/*
	 * public ValidateLayerCollectionList getValidateLayerCollectionList() {
	 * return validateLayerCollectionList; }
	 * 
	 *//**
		 * validateLayerCollectionList setter @author DY.Oh @Date 2017. 4. 18.
		 * 오후 3:30:24 @param validateLayerCollectionList void @throws
		 *//*
		 * public void
		 * setValidateLayerCollectionList(ValidateLayerCollectionList
		 * validateLayerCollectionList) { this.validateLayerCollectionList =
		 * validateLayerCollectionList; }
		 */

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
	public void collectionValidate(){

		this.errLayerList = new ErrorLayerList();

		GeoLayerCollection collection = this.collection;
		ValidateLayerTypeList types = this.types;
		MapSystemRule mapSystemRule = this.mapSystemRule;

		try {
			ErrorLayer errorLayer = new ErrorLayer();
			errorLayer.setCollectionName(collection.getCollectionName());

			errorLayer.setCollectionType(this.collectionType);

			// layerMiss 검수

			layerMissValidate(types, collection, errorLayer);

			// geometric 검수
			geometricValidate(types, collection, errorLayer);

			// attribute 검수
			attributeValidate(types, collection, errorLayer);

			// 인접도엽 검수
			if (nearCollections.size() != 0) {
				closeCollectionValidate(types, mapSystemRule, collection, "", errorLayer);
			}
			errLayerList.add(errorLayer);
			progress.put(collection.getCollectionName(), 2);

		} catch (Exception e) {
			e.printStackTrace();
			progress.put(collection.getCollectionName(), 3);

		}
		System.out.println("검수완료");
		// execService.shutdown();
	}

	/*
	 * private void testtread(GeoLayerCollection collection,
	 * ValidateLayerTypeList types,MapSystemRule mapSystemRule,int j) throws
	 * SchemaException, NoSuchAuthorityCodeException, FactoryException,
	 * TransformException, IOException{ System.out.println("쓰레드" + j); String
	 * collectionName = collection.getCollectionName(); // try { ErrorLayer
	 * errorLayer = new ErrorLayer();
	 * errorLayer.setCollectionName(collectionName);
	 * errorLayer.setCollectionType(this.collectionType);
	 * 
	 * // layerMiss 검수 layerMissValidate(types, collection, errorLayer);
	 * 
	 * // geometric 검수 geometricValidate(types, collection, errorLayer);
	 * 
	 * // attribute 검수 attributeValidate(types, collection, errorLayer);
	 * 
	 * // 인접도엽 검수 closeCollectionValidate(types, mapSystemRule, collection, "",
	 * errorLayer); errLayerList.add(errorLayer);
	 * progress.put(collection.getCollectionName(), 2); }
	 */

	@SuppressWarnings("unused")
	private void attributeValidate(ValidateLayerTypeList types, GeoLayerCollection layerCollection,
			ErrorLayer errorLayer) throws SchemaException {

		String collectionName = layerCollection.getCollectionName();
		for (int j = 0; j < types.size(); j++) {
			ValidateLayerType type = types.get(j);
			GeoLayerList typeLayers = types.getTypeLayers(type.getTypeName(), layerCollection);
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
								typeErrorLayer = layerValidator
										.validateBridgeName(types.getTypeLayers(relationNames.get(l), layerCollection));
								if (typeErrorLayer != null) {
									errorLayer.mergeErrorLayer(typeErrorLayer);
								}
							}
						}
						if (option instanceof Admin) {
							typeErrorLayer = layerValidator.validateAdmin();
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if (option instanceof AttributeFix) {
							HashMap<String, Object> attributeNames = ((AttributeFix) option).getRelationType();
							String typeLayerName = typeLayer.getLayerName();
							JSONObject attrJson = (JSONObject) attributeNames.get(typeLayerName);
							typeErrorLayer = layerValidator.validateAttributeFix(attrJson);
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if (option instanceof ZValueAmbiguous) {
							HashMap<String, Object> hashMap = ((ZValueAmbiguous) option).getRelationType();
							String typeLayerName = typeLayer.getLayerName();
							JSONObject zValue = (JSONObject) hashMap.get(typeLayerName);
							typeErrorLayer = layerValidator.validateZValueAmbiguous(zValue);
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if(option instanceof HouseAttribute){
							typeErrorLayer = layerValidator.valildateHouseAttribute();
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if(option instanceof UFIDLength){
							double length = ((UFIDLength) option).getLength();
							typeErrorLayer = layerValidator.validateUFIDLength(length);
							if(typeErrorLayer != null){
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						
						if(option instanceof NeatLineAttribute){
							typeErrorLayer = layerValidator.validateNeatLineAttribute();
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						
						if(option instanceof NumericalValue){
							String attribute = ((NumericalValue) option).getAttribute(); 
							String condition = ((NumericalValue) option).getCondition();
							double figure = ((NumericalValue) option).getFigure();
							typeErrorLayer = layerValidator.validateNumericalValue(attribute, condition, figure);
							if(typeErrorLayer != null){
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						
						if(option instanceof UFIDRule){
							typeErrorLayer = layerValidator.validateUFIDRule();
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						
					}
				}
			}
		}
	}

	private void geometricValidate(ValidateLayerTypeList types, GeoLayerCollection layerCollection,
			ErrorLayer errorLayer)
			throws SchemaException, NoSuchAuthorityCodeException, FactoryException, TransformException, IOException {

		GeoLayer neatLayer = layerCollection.getNeatLine();
		
		
		for (int i = 0; i < types.size(); i++) {
			// getType
			ValidateLayerType type = types.get(i);
			// getTypeLayers
			GeoLayerList typeLayers = types.getTypeLayers(type.getTypeName(), layerCollection);
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
						String layerFullName = typeLayer.getLayerName();
						int dash = layerFullName.indexOf("_");
						String layerType = layerFullName.substring(dash + 1);
						String upperLayerType = layerType.toUpperCase();
						LayerValidator layerValidator = new LayerValidatorImpl(typeLayer);
						if (upperLayerType.equals("POLYGON")) {
							// twistedFeature
							typeErrorLayer = layerValidator.validateTwistedPolygon();
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}

						if (option instanceof OneAcre) {
							List<String> relationNames = ((OneAcre) option).getRelationType();
							for (int r = 0; r < relationNames.size(); r++) {
								typeErrorLayer = layerValidator.validateOneAcre(
										types.getTypeLayers(relationNames.get(r), layerCollection),
										spatialAccuracyTolorence);
								if (typeErrorLayer != null) {
									errorLayer.mergeErrorLayer(typeErrorLayer);
								}
							}
						}

						if (option instanceof OneStage) {
							List<String> relationNames = ((OneStage) option).getRelationType();
							for (int r = 0; r < relationNames.size(); r++) {
								typeErrorLayer = layerValidator.validateOneStage(
										types.getTypeLayers(relationNames.get(r), layerCollection),
										spatialAccuracyTolorence);
								if (typeErrorLayer != null) {
									errorLayer.mergeErrorLayer(typeErrorLayer);
								}
							}
						}

						if (option instanceof ConBreak) {
							typeErrorLayer = layerValidator.validateConBreakLayers(neatLayer, spatialAccuracyTolorence);
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if (option instanceof ConIntersected) {
							typeErrorLayer = layerValidator.validateConIntersected();
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if (option instanceof ConOverDegree) {
							double degree = ((ConOverDegree) option).getDegree();
							typeErrorLayer = layerValidator.validateConOverDegree(degree);
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if (option instanceof UselessPoint) {
							typeErrorLayer = layerValidator.validateUselessPoint();
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if (option instanceof EntityDuplicated) {
							typeErrorLayer = layerValidator.validateEntityDuplicated();
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if (option instanceof OutBoundary) {
							List<String> relationNames = ((OutBoundary) option).getRelationType();
							for (int r = 0; r < relationNames.size(); r++) {
								typeErrorLayer = layerValidator.validateOutBoundary(
										types.getTypeLayers(relationNames.get(r), layerCollection),
										spatialAccuracyTolorence);
								if (typeErrorLayer != null) {
									errorLayer.mergeErrorLayer(typeErrorLayer);
								}
							}
						}
						if (option instanceof SmallArea) {
							double area = ((SmallArea) option).getArea();
							typeErrorLayer = layerValidator.validateSmallArea(area);
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if (option instanceof SmallLength) {
							double length = ((SmallLength) option).getLength();
							typeErrorLayer = layerValidator.validateSmallLength(length);
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if (option instanceof SelfEntity) {
							List<String> relationNames = ((SelfEntity) option).getRelationType();
							for (int r = 0; r < relationNames.size(); r++) {
								typeErrorLayer = layerValidator.validateSelfEntity(
										types.getTypeLayers(relationNames.get(r), layerCollection),
										selfEntityLineTolerance, polygonInvadedTolorence);

								if (typeErrorLayer != null) {
									errorLayer.mergeErrorLayer(typeErrorLayer);
								}
							}
						}
						if (option instanceof OverShoot) {
							double tolerence = ((OverShoot) option).getTolerence();
							typeErrorLayer = layerValidator.validateOverShoot(neatLayer, tolerence);
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if (option instanceof UnderShoot) {
							double tolerence = ((UnderShoot) option).getTolerence();
							typeErrorLayer = layerValidator.validateUnderShoot(neatLayer, tolerence);
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if (option instanceof UselessEntity) {
							typeErrorLayer = layerValidator.validateUselessEntity();
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if (option instanceof BuildingOpen) {
							typeErrorLayer = layerValidator.validateBuildingOpen(neatLayer, spatialAccuracyTolorence);
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if (option instanceof WaterOpen) {
							typeErrorLayer = layerValidator.validateWaterOpen(neatLayer, spatialAccuracyTolorence);
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if (option instanceof B_SymbolOutSided) {
							List<String> relationNames = ((B_SymbolOutSided) option).getRelationType();
							for (int l = 0; l < relationNames.size(); l++) {
								System.out.println();
								typeErrorLayer = layerValidator.vallidateB_SymbolOutSided(
										types.getTypeLayers(relationNames.get(l), layerCollection));
								if (typeErrorLayer != null) {
									errorLayer.mergeErrorLayer(typeErrorLayer);
								}
							}
						}
						if (option instanceof CrossRoad) {
							List<String> relationNames = ((CrossRoad) option).getRelationType();
							for (int l = 0; l < relationNames.size(); l++) {
								typeErrorLayer = layerValidator.validateCrossRoad(
										types.getTypeLayers(relationNames.get(l), layerCollection), "",
										spatialAccuracyTolorence);
								if (typeErrorLayer != null) {
									errorLayer.mergeErrorLayer(typeErrorLayer);
								}
							}
						}
						if (option instanceof NodeMiss) {
							List<String> relationNames = ((NodeMiss) option).getRelationType();
							for (int l = 0; l < relationNames.size(); l++) {
								typeErrorLayer = layerValidator.validateNodeMiss(
										types.getTypeLayers(relationNames.get(l), layerCollection), "",
										spatialAccuracyTolorence);
								if (typeErrorLayer != null) {
									errorLayer.mergeErrorLayer(typeErrorLayer);
								}
							}
						}
						if (option instanceof PointDuplicated) {
							typeErrorLayer = layerValidator.validatePointDuplicated();
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if(option instanceof CemeterySite){
							List<String> relationNames = ((CemeterySite) option).getRelationType();
							for (int j = 0; j < relationNames.size(); j++) {
								typeErrorLayer = layerValidator.validateCemeterySite(
										types.getTypeLayers(relationNames.get(j), layerCollection));
								if(typeErrorLayer != null){
									errorLayer.mergeErrorLayer(typeErrorLayer);
								}
							}
						}
						
						if(option instanceof BuildingSite){
							HashMap<String, Object> attributeNames = ((BuildingSite) option).getAttributeKey();
							List<String> relationNames = ((BuildingSite) option).getRelationType();
							String typeLayerName = typeLayer.getLayerName();
							JSONObject attrJson = (JSONObject) attributeNames.get(typeLayerName);
							
							for (int j = 0; j < relationNames.size(); j++) {
								List<GeoLayer> relationName = types.getTypeLayers(relationNames.get(j), layerCollection);
								typeErrorLayer = layerValidator.validateBuildingSite(attrJson, relationName);
							}
							if(typeErrorLayer != null){
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						
						if(option instanceof RiverBoundaryMiss){
							List<String> relationNames = ((RiverBoundaryMiss) option).getRelationType();
							for (int r = 0; r < relationNames.size(); r++) {
								typeErrorLayer = layerValidator.validateRiverBoundaryMiss
										(types.getTypeLayers(relationNames.get(r), layerCollection));
								if (typeErrorLayer != null) {
									errorLayer.mergeErrorLayer(typeErrorLayer);
								}
							}
						}
						
						if(option instanceof CenterLineMiss){
							List<String> relationNames = ((CenterLineMiss) option).getRelationType();
							for (int j = 0; j < relationNames.size(); j++) {
								typeErrorLayer = layerValidator.validateCenterLineMiss
										(types.getTypeLayers(relationNames.get(j), layerCollection), lineInvadedTolorence);
								if(typeErrorLayer != null){
									errorLayer.mergeErrorLayer(typeErrorLayer);
								}
							}
						}
						
						if(option instanceof HoleMisplacement){
							typeErrorLayer = layerValidator.validateHoleMisplacement();
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						
						if(option instanceof EntityInHole){
							List<String> relationNames = ((EntityInHole) option).getRelationType();
							for (int j = 0; j < relationNames.size(); j++) {
								typeErrorLayer = layerValidator.validateEntityInHole(types.getTypeLayers(relationNames.get(j), layerCollection));
								if(typeErrorLayer != null){
									errorLayer.mergeErrorLayer(typeErrorLayer);
								}
							}
						}
						
						if(option instanceof LinearDisconnection){
							List<String> relationNames = ((LinearDisconnection) option).getRelationType();
							for (int j = 0; j < relationNames.size(); j++) {
								typeErrorLayer = layerValidator.valildateLinearDisconnection(types.getTypeLayers(relationNames.get(j), layerCollection));
								if(typeErrorLayer != null){
									errorLayer.mergeErrorLayer(typeErrorLayer);
								}
							}
						}
						
							
					}
				}
			} else {
				continue;
			}
		}
	}

	@SuppressWarnings("unused")
	private void layerMissValidate(ValidateLayerTypeList types, GeoLayerCollection layerCollection,
			ErrorLayer errorLayer) throws SchemaException {
		// TODO Auto-generated method stub

		List<GeoLayer> collectionList = layerCollection.getLayers();
		for (int j = 0; j < types.size(); j++) {
			ValidateLayerType type = types.get(j);
			GeoLayerList typeLayers = types.getTypeLayers(type.getTypeName(), layerCollection);
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
								errorLayer.mergeErrorLayer(typeErrorLayer);
								collectionList.remove(typeLayer);
							}

						}
					}
				}
			}
		}
	}

	// closeValidate

	private void closeCollectionValidate(ValidateLayerTypeList types, MapSystemRule mapSystemRule,
			GeoLayerCollection layerCollection, String geomColumn, ErrorLayer errorLayer) throws SchemaException {
		// TODO Auto-generated method stub
		String geomCol = "geom"; // 임시선언 geometry 컬럼

		LayerValidatorImpl layerValidator = new LayerValidatorImpl();

		if (layerCollection != null) {
			SimpleFeatureCollection neatLineCollection = layerCollection.getNeatLine().getSimpleFeatureCollection();
			SimpleFeatureIterator featureIterator = neatLineCollection.features();

			// 인접도엽 변수선언
			GeoLayerCollection topGeoCollection = null;
			GeoLayerCollection bottomGeoCollection = null;
			GeoLayerCollection leftGeoCollection = null;
			GeoLayerCollection rightGeoCollection = null;

			// GeoLayerCollectionList collectionList =
			// this.getLayerCollectionList();
			List<GeoLayerCollection> collectionList = this.nearCollections;

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

			Coordinate firstPoint = null;
			Coordinate secondPoint = null;
			Coordinate thirdPoint = null;
			Coordinate fourthPoint = null;

			int i = 0;
			while (featureIterator.hasNext()) {
				if (i == 0) {
					SimpleFeature feature = featureIterator.next();
					Geometry geometry = (Geometry) feature.getDefaultGeometry();

					Coordinate[] coordinateArray = this.getSortCoordinate(geometry.getCoordinates());

					firstPoint = coordinateArray[0];
					secondPoint = coordinateArray[1];
					thirdPoint = coordinateArray[2];
					fourthPoint = coordinateArray[3];

					i++;
				} else {
					LOGGER.info("도곽레이어 객체 1개이상");
					return;
				}
			}

			GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

			// 도엽라인객체 생성
			Coordinate[] topLineCoords = new Coordinate[] { firstPoint, secondPoint };
			Coordinate[] bottomLineCoords = new Coordinate[] { thirdPoint, fourthPoint };
			Coordinate[] leftLineCoords = new Coordinate[] { firstPoint, fourthPoint };
			Coordinate[] rightLineCoords = new Coordinate[] { secondPoint, thirdPoint };

			LineString topLineString = geometryFactory.createLineString(topLineCoords);
			LineString bottomLineString = geometryFactory.createLineString(bottomLineCoords);
			LineString leftLineString = geometryFactory.createLineString(leftLineCoords);
			LineString rightLineString = geometryFactory.createLineString(rightLineCoords);
			Polygon topBuffer = (Polygon) topLineString.buffer(underShootTolorence);
			Polygon bottomBuffer = (Polygon) bottomLineString.buffer(underShootTolorence);
			Polygon leftBuffer = (Polygon) leftLineString.buffer(underShootTolorence);
			Polygon rightBuffer = (Polygon) rightLineString.buffer(underShootTolorence);

			Map<MapSystemRuleType, LineString> collectionBoundary = new HashMap<MapSystemRule.MapSystemRuleType, LineString>();

			collectionBoundary.put(MapSystemRuleType.TOP, topLineString);
			collectionBoundary.put(MapSystemRuleType.BOTTOM, bottomLineString);
			collectionBoundary.put(MapSystemRuleType.LEFT, leftLineString);
			collectionBoundary.put(MapSystemRuleType.RIGHT, rightLineString);

			// 인접도엽 top, bottom, left, right 객체로드를 위한 Map 생성
			Map<MapSystemRuleType, Polygon> targetFeaturesGetBoundary = new HashMap<MapSystemRuleType, Polygon>();
			targetFeaturesGetBoundary.put(MapSystemRuleType.TOP, topBuffer);
			targetFeaturesGetBoundary.put(MapSystemRuleType.BOTTOM, bottomBuffer);
			targetFeaturesGetBoundary.put(MapSystemRuleType.LEFT, leftBuffer);
			targetFeaturesGetBoundary.put(MapSystemRuleType.RIGHT, rightBuffer);

			// 인접도엽 top, bottom, left, right 객체로드를 위한 Map 생성
			Map<MapSystemRuleType, Polygon> nearFeaturesGetBoundary = new HashMap<MapSystemRuleType, Polygon>();
			nearFeaturesGetBoundary.put(MapSystemRuleType.TOP, topBuffer);
			nearFeaturesGetBoundary.put(MapSystemRuleType.BOTTOM, bottomBuffer);
			nearFeaturesGetBoundary.put(MapSystemRuleType.LEFT, leftBuffer);
			nearFeaturesGetBoundary.put(MapSystemRuleType.RIGHT, rightBuffer);

			for (ValidateLayerType layerType : types) {
				GeoLayerList typeLayers = types.getTypeLayers(layerType.getTypeName(), layerCollection);
				List<ValidatorOption> options = layerType.getOptionList();
				if (options != null) {
					ErrorLayer typeErrorLayer = null;
					for (GeoLayer geoLayer : typeLayers) {
						layerValidator.setValidatorLayer(geoLayer);

						Map<MapSystemRuleType, GeoLayer> collctionMap = new HashMap<MapSystemRule.MapSystemRuleType, GeoLayer>();
						GeoLayer topLayer = null;
						GeoLayer bottomLayer = null;
						GeoLayer leftLayer = null;
						GeoLayer rightLayer = null;

						if (geoLayer != null) {
							String layerName = geoLayer.getLayerName();

							// 인접도엽 검수옵션 생성 - 레이어단위
							ValCollectionOption collectionOptions = new ValCollectionOption();
							for (ValidatorOption option : options) {
								if (option instanceof EntityNone) {
									collectionOptions.putEntityNoneOption(true);
								}
								if (option instanceof RefAttributeMiss) {
									List<String> colunms = (List<String>) ((RefAttributeMiss) option)
											.getRefAttributeMaissOpt(layerName);
									collectionOptions.putRefAttributeMissOption(colunms);
								}
								if (option instanceof RefZValueMiss) {
									HashMap<String, List<String>> opts = ((RefZValueMiss) option)
											.getRefZValueMissOpts();
									List<String> colunms = opts.get(layerName);
									collectionOptions.putRefZValueMissOption(colunms);
								}
								if (option instanceof EdgeMatchMiss) {
									collectionOptions.putEdgeMatchMissOption(true);
								}
								if (option instanceof UnderShoot) {
									double tolerence = ((UnderShoot) option).getTolerence();
									collectionOptions.putUnderShootOption(tolerence);
								}
							}

							if (topGeoCollection != null) {
								topLayer = topGeoCollection.getLayer(layerName, topGeoCollection);
								collctionMap.put(MapSystemRuleType.TOP, topLayer);
							}
							if (bottomGeoCollection != null) {
								bottomLayer = bottomGeoCollection.getLayer(layerName, bottomGeoCollection);
								collctionMap.put(MapSystemRuleType.BOTTOM, bottomLayer);
							}
							if (leftGeoCollection != null) {
								leftLayer = leftGeoCollection.getLayer(layerName, leftGeoCollection);
								collctionMap.put(MapSystemRuleType.LEFT, leftLayer);
							}
							if (rightGeoCollection != null) {
								rightLayer = rightGeoCollection.getLayer(layerName, rightGeoCollection);
								collctionMap.put(MapSystemRuleType.RIGHT, rightLayer);
							}

							ValidateCloseCollectionLayer closeCollectionLayer = new ValidateCloseCollectionLayer(
									geoLayer, collctionMap, spatialAccuracyTolorence, collectionOptions,
									collectionBoundary, targetFeaturesGetBoundary, nearFeaturesGetBoundary);

							typeErrorLayer = layerValidator.validateCloseCollection(closeCollectionLayer, geomCol);
							if (typeErrorLayer != null) {
								errorLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
					}
				}
			}
		}
	}

	private Coordinate[] getSortCoordinate(Coordinate[] coordinates) {
		Coordinate[] returncoordinate = coordinates;
		if (coordinates.length == 5) {
			double fPointY = 0.0;
			double sPointY = 0.0;

			for (int a = 0; a < returncoordinate.length - 2; a++) {
				for (int j = 0; j < returncoordinate.length - 2; j++) {
					fPointY = returncoordinate[j].y;
					sPointY = returncoordinate[j + 1].y;

					Coordinate jCoordinate = returncoordinate[j];
					Coordinate kCoordinate = returncoordinate[j + 1];

					if (fPointY < sPointY) {
						returncoordinate[j + 1] = jCoordinate;
						returncoordinate[j] = kCoordinate;
					}
				}
			}

			Coordinate firstPoint = returncoordinate[0];
			Coordinate secondPoint = returncoordinate[1];
			Coordinate thirdPoint = returncoordinate[2];
			Coordinate fourthPoint = returncoordinate[3];

			if (firstPoint.x > secondPoint.x) {
				returncoordinate[0] = secondPoint;
				returncoordinate[1] = firstPoint;
			}

			if (thirdPoint.x < fourthPoint.x) {
				returncoordinate[2] = fourthPoint;
				returncoordinate[3] = thirdPoint;
			}

			returncoordinate[4] = returncoordinate[0];
		} else
			return null;

		return returncoordinate;
	}
}
