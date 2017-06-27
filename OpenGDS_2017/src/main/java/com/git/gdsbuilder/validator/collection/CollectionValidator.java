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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONObject;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollection;
import com.git.gdsbuilder.type.geoserver.collection.GeoLayerCollectionList;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayer;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerList;
import com.git.gdsbuilder.type.validate.collection.ValidateLayerCollectionList;
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
import com.git.gdsbuilder.type.validate.option.EntityDuplicated;
import com.git.gdsbuilder.type.validate.option.LayerMiss;
import com.git.gdsbuilder.type.validate.option.NodeMiss;
import com.git.gdsbuilder.type.validate.option.OutBoundary;
import com.git.gdsbuilder.type.validate.option.OverShoot;
import com.git.gdsbuilder.type.validate.option.SelfEntity;
import com.git.gdsbuilder.type.validate.option.SmallArea;
import com.git.gdsbuilder.type.validate.option.SmallLength;
import com.git.gdsbuilder.type.validate.option.UnderShoot;
import com.git.gdsbuilder.type.validate.option.UselessEntity;
import com.git.gdsbuilder.type.validate.option.UselessPoint;
import com.git.gdsbuilder.type.validate.option.ValidatorOption;
import com.git.gdsbuilder.type.validate.option.WaterOpen;
import com.git.gdsbuilder.type.validate.option.Z_ValueAmbiguous;
import com.git.gdsbuilder.validator.layer.LayerValidatorImpl;

/**
 * ValidateLayerCollectionList를 검수하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:30:17
 */
public class CollectionValidator {

	ValidateLayerCollectionList validateLayerCollectionList;
	ErrorLayerList errLayerList;
	Map<String, Object> progress;

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

		for (int i = 0; i < layerCollections.size(); i++) {

			GeoLayerCollection collection = layerCollections.get(i);

			// layerMiss 검수
			layerMissValidate(types, collection);

			// geometric 검수
			geometricValidate(types, collection);

			// attribute 검수
			attributeValidate(types, collection);

			// 인접도엽 검수
			closeCollectionValidate(types, collection);
		}
	}
	// closeValidate

	private void closeCollectionValidate(ValidateLayerTypeList types, GeoLayerCollection layerCollection) {
		// TODO Auto-generated method stub
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
							HashMap<String, Object> attributeNames = ((AttributeFix) option).getRelationType();
							String typeLayerName = typeLayer.getLayerName();
							int index = typeLayerName.indexOf("_");
							String layerCode = typeLayerName.substring(0, index);
							JSONObject attrJson = (JSONObject) attributeNames.get(layerCode);
							typeErrorLayer = layerValidator.validateAttributeFix(attrJson);
						}
						if (option instanceof Z_ValueAmbiguous) {
							String attributeKey = ((Z_ValueAmbiguous) option).getAttributeKey();
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
						if (option instanceof Z_ValueAmbiguous) {
							String key = ((Z_ValueAmbiguous) option).getAttributeKey();
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
<<<<<<< HEAD
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
=======
							
							
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
							if (option instanceof Z_ValueAmbiguous) {
								String key = ((Z_ValueAmbiguous) option).getAttributeKey();
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
											.getTypeLayers(relationNames.get(r), collection));
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
											.getTypeLayers(relationNames.get(r), collection));
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
>>>>>>> 50bbe3d790e7e08f193e6b2d79751c92375803bd
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
										.getTypeLayers(relationNames.get(l), layerCollection));
							}
							if (typeErrorLayer != null) {
								errLayer.mergeErrorLayer(typeErrorLayer);
							}
						}
						if (option instanceof NodeMiss) {
							List<String> relationNames = ((NodeMiss) option).getRelationType();
							for (int l = 0; l < relationNames.size(); l++) {
								typeErrorLayer = layerValidator.validateNodeMiss(validateLayerCollectionList
										.getTypeLayers(relationNames.get(l), layerCollection), "", 0.001);
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
<<<<<<< HEAD
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
=======
	private void layerMissValidate(ValidateLayerTypeList types, GeoLayerCollectionList layerCollections) throws SchemaException {
		
		// TODO Auto-generated method stub
		for (int i = 0; i < layerCollections.size(); i++) {
			GeoLayerCollection collection = layerCollections.get(i);
			List<GeoLayer> collectionList = collection.getLayers();
			ErrorLayer errLayer = new ErrorLayer();
			for(int j=0; j < types.size(); j++){
				ValidateLayerType type = types.get(j);
				GeoLayerList typeLayers = validateLayerCollectionList.getTypeLayers(type.getTypeName(), collection);
				List<ValidatorOption> options = type.getOptionList();
				if(options != null){
					ErrorLayer typeErrorLayer = null; 
					for(int k = 0; k<options.size(); k++){
						ValidatorOption option = options.get(k);
						for (int l = 0; l < typeLayers.size(); l++) {
							GeoLayer typeLayer = typeLayers.get(l);
							if(typeLayer == null){
								continue;
							}
							LayerValidatorImpl layerValidator = new LayerValidatorImpl(typeLayer);
							if (option instanceof LayerMiss){
								List<String> typeNames = ((LayerMiss) option).getLayerType();
								typeErrorLayer = layerValidator.validateLayerMiss(typeNames);
								if (typeErrorLayer != null) {
									errLayer.mergeErrorLayer(typeErrorLayer);
								}
								collectionList.remove(typeLayer);
>>>>>>> 50bbe3d790e7e08f193e6b2d79751c92375803bd
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
