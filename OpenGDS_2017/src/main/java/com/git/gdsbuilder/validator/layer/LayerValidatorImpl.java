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

package com.git.gdsbuilder.validator.layer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.SchemaException;
import org.json.simple.JSONObject;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import com.git.gdsbuilder.type.geoserver.layer.GeoLayer;
import com.git.gdsbuilder.type.validate.collection.close.ValidateCloseCollectionLayer;
import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.type.validate.error.ErrorLayer;
import com.git.gdsbuilder.type.validate.option.ValidatorOption;
import com.git.gdsbuilder.validator.collection.rule.MapSystemRule.MapSystemRuleType;
import com.git.gdsbuilder.validator.feature.FeatureAttributeValidator;
import com.git.gdsbuilder.validator.feature.FeatureAttributeValidatorImpl;
import com.git.gdsbuilder.validator.feature.FeatureCloseCollectionValidator;
import com.git.gdsbuilder.validator.feature.FeatureCloseCollectionValidatorImpl;
import com.git.gdsbuilder.validator.feature.FeatureGraphicValidator;
import com.git.gdsbuilder.validator.feature.FeatureGraphicValidatorImpl;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Polygon;

public class LayerValidatorImpl implements LayerValidator {

	GeoLayer validatorLayer;
	FeatureGraphicValidator graphicValidator = new FeatureGraphicValidatorImpl();
	FeatureAttributeValidator attributeValidator = new FeatureAttributeValidatorImpl();
	FeatureCloseCollectionValidator closeCollectionValidator = new FeatureCloseCollectionValidatorImpl();

	public LayerValidatorImpl() {

	}

	public LayerValidatorImpl(GeoLayer validatorLayer) {
		super();
		this.validatorLayer = validatorLayer;
	}

	/**
	 * @return the validatorLayer
	 */
	public GeoLayer getValidatorLayer() {
		return validatorLayer;
	}

	/**
	 * @param validatorLayer the validatorLayer to set
	 */
	public void setValidatorLayer(GeoLayer validatorLayer) {
		this.validatorLayer = validatorLayer;
	}

	
	public ErrorLayer validateConBreakLayers(GeoLayer neatLayer) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();

		SimpleFeatureCollection neatLineSfc = neatLayer.getSimpleFeatureCollection();
		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();

		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			List<ErrorFeature> errFeatures = graphicValidator.validateConBreak(simpleFeature, neatLineSfc);
			if (errFeatures != null) {
				for (ErrorFeature errFeature : errFeatures) {
					errFeature.setLayerName(validatorLayer.getLayerName());
					errLayer.addErrorFeature(errFeature);
				}
			} else {
				continue;
			}
		}
		if (errLayer.getErrFeatureList().size() > 0) {
			return errLayer;
		} else {
			return null;
		}
	}

	public ErrorLayer validateConIntersected() throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();

		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		List<SimpleFeature> tmpsSimpleFeatures = new ArrayList<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			tmpsSimpleFeatures.add(simpleFeature);
		}

		int tmpsSimpleFeaturesSize = tmpsSimpleFeatures.size();
		for (int i = 0; i < tmpsSimpleFeaturesSize - 1; i++) {
			SimpleFeature tmpSimpleFeatureI = tmpsSimpleFeatures.get(i);
			for (int j = i + 1; j < tmpsSimpleFeaturesSize; j++) {
				SimpleFeature tmpSimpleFeatureJ = tmpsSimpleFeatures.get(j);
				List<ErrorFeature> errFeatures = graphicValidator.validateConIntersected(tmpSimpleFeatureI,
						tmpSimpleFeatureJ);
				if (errFeatures != null) {
					for (ErrorFeature errFeature : errFeatures) {
						errFeature.setLayerName(validatorLayer.getLayerName());
						errLayer.addErrorFeature(errFeature);
					}
				} else {
					continue;
				}
			}
		}
		if (errLayer.getErrFeatureList().size() > 0) {
			return errLayer;
		} else {
			return null;
		}
	}

	public ErrorLayer validateConOverDegree(double degree) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();

		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			List<ErrorFeature> errFeatures = graphicValidator.validateConOverDegree(simpleFeature, degree);
			if (errFeatures != null) {
				for (ErrorFeature errFeature : errFeatures) {
					errFeature.setLayerName(validatorLayer.getLayerName());
					errLayer.addErrorFeature(errFeature);
				}
			} else {
				continue;
			}
		}
		if (errLayer.getErrFeatureList().size() > 0) {
			return errLayer;
		} else {
			return null;
		}
	}

	public ErrorLayer validateZ_ValueAmbiguous(String attributeKey) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();

		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			ErrorFeature errFeature = attributeValidator.validateZvalueAmbiguous(simpleFeature, attributeKey);
			if (errFeature != null) {
				errFeature.setLayerName(validatorLayer.getLayerName());
				errLayer.addErrorFeature(errFeature);
			} else {
				continue;
			}
		}
		if (errLayer.getErrFeatureList().size() > 0) {
			return errLayer;
		} else {
			return null;
		}
	}

	public ErrorLayer validateSmallArea(double area) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();

		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			ErrorFeature errFeature = graphicValidator.validateSmallArea(simpleFeature, area);
			if (errFeature != null) {
				errFeature.setLayerName(validatorLayer.getLayerName());
				errLayer.addErrorFeature(errFeature);
			} else {
				continue;
			}
		}
		if (errLayer.getErrFeatureList().size() > 0) {
			return errLayer;
		} else {
			return null;
		}
	}

	public ErrorLayer validateSmallLength(double length) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();

		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			ErrorFeature errFeature = graphicValidator.validateSmallLength(simpleFeature, length);
			if (errFeature != null) {
				errFeature.setLayerName(validatorLayer.getLayerName());
				errLayer.addErrorFeature(errFeature);
			} else {
				continue;
			}
		}
		if (errLayer.getErrFeatureList().size() > 0) {
			return errLayer;
		} else {
			return null;
		}
	}

	public ErrorLayer validateOverShoot(GeoLayer neatLayer, double tolerence) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();

		SimpleFeatureCollection neatLineSfc = neatLayer.getSimpleFeatureCollection();
		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();

		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			List<ErrorFeature> errFeatures = graphicValidator.validateOverShoot(simpleFeature, neatLineSfc, tolerence);
			if (errFeatures != null) {
				for (ErrorFeature errFeature : errFeatures) {
					errFeature.setLayerName(validatorLayer.getLayerName());
					errLayer.addErrorFeature(errFeature);
				} 
			} else {
				continue;
			}
		}
		if (errLayer.getErrFeatureList().size() > 0) {
			return errLayer;
		} else {
			return null;
		}
	}

	public ErrorLayer validateUnderShoot(GeoLayer neatLayer, double tolerence) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();

		SimpleFeatureCollection neatLineSfc = neatLayer.getSimpleFeatureCollection();
		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();

		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			List<ErrorFeature> errFeatures = graphicValidator.validateUnderShoot(simpleFeature, neatLineSfc, tolerence);
			if (errFeatures != null) {
				for (ErrorFeature errFeature : errFeatures) {
					errFeature.setLayerName(validatorLayer.getLayerName());
					errLayer.addErrorFeature(errFeature);
				}
			} else {
				continue;
			}
		}
		if (errLayer.getErrFeatureList().size() > 0) {
			return errLayer;
		} else {
			return null;
		}
	}

	public ErrorLayer validateSelfEntity(List<GeoLayer> relationLayers) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();

		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		List<SimpleFeature> simpleFeatures = new ArrayList<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			simpleFeatures.add(simpleFeature);
		}
		// ErrorLayer selfErrorLayer = selfEntity(simpleFeatures);
		// if (selfErrorLayer != null) {
		// errLayer.mergeErrorLayer(selfErrorLayer);
		// }

		for (int i = 0; i < relationLayers.size(); i++) {
			GeoLayer relationLayer = relationLayers.get(i);

			if (relationLayer.getLayerName().equals("F0010000_LINESTRING")) {
				System.out.println("");

			}

			SimpleFeatureCollection relationSfc = relationLayer.getSimpleFeatureCollection();
			List<SimpleFeature> relationSimpleFeatures = new ArrayList<SimpleFeature>();
			SimpleFeatureIterator relationSimpleFeatureIterator = relationSfc.features();
			while (relationSimpleFeatureIterator.hasNext()) {
				SimpleFeature simpleFeature = relationSimpleFeatureIterator.next();
				relationSimpleFeatures.add(simpleFeature);
			}
			ErrorLayer relationErrorLayer = selfEntity(simpleFeatures, relationSimpleFeatures);
			if (relationErrorLayer != null) {
				errLayer.mergeErrorLayer(relationErrorLayer);
			}
		}
		return errLayer;
	}

	private ErrorLayer selfEntity(List<SimpleFeature> simpleFeatures, List<SimpleFeature> relationSimpleFeatures)
			throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();

		int tmpSizeI = simpleFeatures.size();
		int tmpSizeJ = relationSimpleFeatures.size();
		for (int i = 0; i < tmpSizeI; i++) {
			SimpleFeature simpleFeatureI = simpleFeatures.get(i);
			for (int j = 0; j < tmpSizeJ; j++) {
				SimpleFeature simpleFeatureJ = relationSimpleFeatures.get(j);
				List<ErrorFeature> errFeatures = graphicValidator.validateSelfEntity(simpleFeatureI, simpleFeatureJ);
				if (errFeatures != null) {
					for (ErrorFeature errFeature : errFeatures) {
						errFeature.setLayerName(validatorLayer.getLayerName());
						errLayer.addErrorFeature(errFeature);
					}
				}
			}
		}
		if (errLayer.getErrFeatureList().size() > 0) {
			return errLayer;
		} else {
			return null;
		}
	}

	private ErrorLayer selfEntity(List<SimpleFeature> simpleFeatures) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();

		int tmpSize = simpleFeatures.size();
		for (int i = 0; i < tmpSize - 1; i++) {
			SimpleFeature tmpSimpleFeatureI = simpleFeatures.get(i);
			for (int j = i + 1; j < tmpSize; j++) {
				SimpleFeature tmpSimpleFeatureJ = simpleFeatures.get(j);
				List<ErrorFeature> errFeatures = graphicValidator.validateSelfEntity(tmpSimpleFeatureI,
						tmpSimpleFeatureJ);
				if (errFeatures != null) {
					for (ErrorFeature errFeature : errFeatures) {
						errFeature.setLayerName(validatorLayer.getLayerName());
						errLayer.addErrorFeature(errFeature);
					}
				} else {
					continue;
				}
			}
		}
		if (errLayer.getErrFeatureList().size() > 0) {
			return errLayer;
		} else {
			return null;
		}
	}

	public ErrorLayer validateOutBoundary(List<GeoLayer> relationLayers) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();

		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		List<SimpleFeature> simpleFeatures = new ArrayList<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			simpleFeatures.add(simpleFeature);
		}

		for (int i = 0; i < relationLayers.size(); i++) {
			GeoLayer relationLayer = relationLayers.get(i);
			SimpleFeatureCollection relationSfc = relationLayer.getSimpleFeatureCollection();
			SimpleFeatureIterator relationSimpleFeatureIterator = relationSfc.features();
			while (relationSimpleFeatureIterator.hasNext()) {
				SimpleFeature relationSimpleFeature = relationSimpleFeatureIterator.next();
				for (int j = 0; j < simpleFeatures.size(); j++) {
					SimpleFeature simpleFeature = simpleFeatures.get(j);
					ErrorFeature errFeature = graphicValidator.validateOutBoundary(simpleFeature,
							relationSimpleFeature);
					if (errFeature != null) {
						errFeature.setLayerName(validatorLayer.getLayerName());
						errLayer.addErrorFeature(errFeature);
					} else {
						continue;
					}
				}
			}
		}
		if (errLayer.getErrFeatureList().size() > 0) {
			return errLayer;
		} else {
			return null;
		}
	}

	public ErrorLayer validateUselessPoint()
			throws NoSuchAuthorityCodeException, SchemaException, FactoryException, TransformException {

		ErrorLayer errLayer = new ErrorLayer();

		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			List<ErrorFeature> errFeatures = graphicValidator.validateUselessPoint(simpleFeature);
			if (errFeatures != null) {
				for (ErrorFeature errFeature : errFeatures) {
					errFeature.setLayerName(validatorLayer.getLayerName());
					errLayer.addErrorFeature(errFeature);
				}
			} else {
				continue;
			}
		}
		if (errLayer.getErrFeatureList().size() > 0) {
			return errLayer;
		} else {
			return null;
		}
	}

	public ErrorLayer validateEntityDuplicated() throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();

		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		List<SimpleFeature> tmpsSimpleFeatures = new ArrayList<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			tmpsSimpleFeatures.add(simpleFeature);
		}

		int tmpSize = tmpsSimpleFeatures.size();
		for (int i = 0; i < tmpSize - 1; i++) {
			SimpleFeature tmpSimpleFeatureI = tmpsSimpleFeatures.get(i);
			for (int j = i + 1; j < tmpSize; j++) {
				SimpleFeature tmpSimpleFeatureJ = tmpsSimpleFeatures.get(j);
				ErrorFeature errFeature = graphicValidator.validateEntityDuplicated(tmpSimpleFeatureI,
						tmpSimpleFeatureJ);
				if (errFeature != null) {
					errFeature.setLayerName(validatorLayer.getLayerName());
					errLayer.addErrorFeature(errFeature);
				} else {
					continue;
				}
			}
		}
		if (errLayer.getErrFeatureList().size() > 0) {
			return errLayer;
		} else {
			return null;
		}
	}

	/****************************************** 추가 **********************/
	public ErrorLayer validateUselessEntity() throws SchemaException{
		ErrorLayer errLayer = new ErrorLayer();
		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();

		SimpleFeatureIterator simpleFeatureIterator = sfc.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			ErrorFeature errFeature = graphicValidator.validateUselessEntity(simpleFeature);
			if (errFeature != null) {
				errFeature.setLayerName(validatorLayer.getLayerName());
				errLayer.addErrorFeature(errFeature);
			} else {
				continue;
			}
		}
		if (errLayer.getErrFeatureList().size() > 0) {
			return errLayer;
		} else {
			return null;
		}
	}

	public ErrorLayer validateBuildingOpen() throws SchemaException{
		ErrorLayer errorLayer = new ErrorLayer();
		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			ErrorFeature errFeature = graphicValidator.validateBuildingOpen(simpleFeature);
			if(errFeature != null){
				errFeature.setLayerName(validatorLayer.getLayerName());
				errorLayer.addErrorFeature(errFeature);
			}else {
				continue;
			}
		}
		if(errorLayer.getErrFeatureList().size() > 0){
			return errorLayer;
		}else{
			return null;
		}
	}

	public ErrorLayer validateWaterOpen() throws SchemaException{
		ErrorLayer errorLayer = new ErrorLayer();
		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			ErrorFeature errFeature = graphicValidator.validateWaterOpen(simpleFeature);
			if(errFeature != null){
				errFeature.setLayerName(validatorLayer.getLayerName());
				errorLayer.addErrorFeature(errFeature);
			}else{
				continue;
			}
		}
		if(errorLayer.getErrFeatureList().size() > 0){
			return errorLayer;
		}else{
			return null;
		}
	}

	public ErrorLayer validateLayerMiss(List<String> typeNames) throws SchemaException{
		ErrorLayer errorLayer = new ErrorLayer();
		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features(); 
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			ErrorFeature errorFeature = graphicValidator.validateLayerMiss(simpleFeature, typeNames);
			if(errorFeature != null){
				errorFeature.setLayerName(validatorLayer.getLayerName());
				errorLayer.addErrorFeature(errorFeature);
			}else{
				continue;
			}
		}
		if(errorLayer.getErrFeatureList().size() > 0){
			return errorLayer;
		}else{
			return null;
		}
	}

	public ErrorLayer vallidateB_SymbolOutSided(List<GeoLayer> relationLayers) throws SchemaException{
		ErrorLayer errorLayer = new ErrorLayer();

		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		List<SimpleFeature> simpleFeatures = new ArrayList<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();

		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			simpleFeatures.add(simpleFeature);
		}

		for (int i = 0; i < relationLayers.size(); i++) {
			GeoLayer relationLayer = relationLayers.get(i);
			SimpleFeatureCollection relationSfc = relationLayer.getSimpleFeatureCollection();
			SimpleFeatureIterator relationSimpleFeatureIterator = relationSfc.features();
			while (relationSimpleFeatureIterator.hasNext()) {
				SimpleFeature relationSimpleFeature = relationSimpleFeatureIterator.next();
				ErrorFeature errorFeature = graphicValidator.validateB_SymbolOutSided(simpleFeatures, relationSimpleFeature);
				if(errorFeature != null){
					errorFeature.setLayerName(validatorLayer.getLayerName());
					errorLayer.addErrorFeature(errorFeature);
				}else{
					continue;
				}
			}
		}
		return errorLayer;
	}

	public ErrorLayer validateCrossRoad(List<GeoLayer> relationLayers, String geomColumn, double tolerence) throws SchemaException{

		String geomCol = "";
		FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();

		if(geomColumn.equals("")){
			geomCol = "geom";
		}
		else{
			geomCol = geomColumn;
		}

		ErrorLayer errorLayer = new ErrorLayer();
		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();
		for (int i = 0; i < relationLayers.size(); i++) {
			GeoLayer relationLayer = relationLayers.get(i);
			SimpleFeatureCollection relationSfc = relationLayer.getSimpleFeatureCollection();

			while (simpleFeatureIterator.hasNext()) {
				SimpleFeature simpleFeature = simpleFeatureIterator.next();

				Polygon polygon = (Polygon) simpleFeature.getDefaultGeometry();

				List<SimpleFeature> relationSimplFeatureList = new ArrayList<SimpleFeature>();

				Filter contationFilter = ff.contains(ff.property(geomCol), ff.literal(polygon));
				Filter overlapsFilter = ff.overlaps(ff.property(geomCol), ff.literal(polygon));
				Filter filter = ff.or(contationFilter,overlapsFilter);

				SimpleFeatureCollection collection = relationSfc.subCollection(filter);
				SimpleFeatureIterator featureIterator = collection.features();

				while(featureIterator.hasNext()){
					SimpleFeature feature = featureIterator.next();
					relationSimplFeatureList.add(feature);
				}

				if (relationSimplFeatureList != null) {
					List<ErrorFeature> errorFeatures = graphicValidator.validateCrossRoad(simpleFeature, relationSimplFeatureList, tolerence);
					if (errorFeatures != null) {
						for (ErrorFeature errorFeature : errorFeatures) {
							errorFeature.setLayerName(validatorLayer.getLayerName());
							errorLayer.addErrorFeature(errorFeature);
						}
					}
				}
			}
		}
		return errorLayer;
	}

	public ErrorLayer validateBridgeName(List<GeoLayer> relationLayers) throws SchemaException{
		ErrorLayer errorLayer = new ErrorLayer();
		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();

		List<SimpleFeature> simpleFeatures = new ArrayList<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			simpleFeatures.add(simpleFeature);
		}
		for (int i = 0; i < relationLayers.size(); i++) {
			GeoLayer relationLayer = relationLayers.get(i);
			SimpleFeatureCollection relationSfc = relationLayer.getSimpleFeatureCollection();
			SimpleFeatureIterator relationSimpleFeatureIterator = relationSfc.features();
			while (relationSimpleFeatureIterator.hasNext()) {
				SimpleFeature relationSimpleFeature = relationSimpleFeatureIterator.next();
				for (int j = 0; j < simpleFeatures.size(); j++) {
					SimpleFeature simpleFeature = simpleFeatures.get(j);
					ErrorFeature errorFeature = attributeValidator.validateBridgeName(simpleFeature, relationSimpleFeature);
					if(errorFeature != null){
						errorFeature.setLayerName(validatorLayer.getLayerName());
						errorLayer.addErrorFeature(errorFeature);
					}else{
						continue;
					}
				}
			}
		}
		if(errorLayer.getErrFeatureList().size() > 0){
			return errorLayer;
		}else{
			return null;
		}
	}

	public ErrorLayer validateAdmin() throws SchemaException{
		ErrorLayer errorLayer = new ErrorLayer();
		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			ErrorFeature errorFeature = attributeValidator.validateAdmin(simpleFeature);
			if(errorFeature != null){
				errorFeature.setLayerName(validatorLayer.getLayerName());
				errorLayer.addErrorFeature(errorFeature);
			}else{
				continue;
			}
		}
		if(errorLayer.getErrFeatureList().size() > 0){
			return errorLayer;
		}else{
			return null;
		}
	}

	public ErrorLayer validateTwistedPolygon() throws SchemaException{
		ErrorLayer errorLayer = new ErrorLayer();
		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		DefaultFeatureCollection featureCollection = new DefaultFeatureCollection();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();

		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			ErrorFeature errorFeature = graphicValidator.validateTwistedPolygon(simpleFeature);
			if(errorFeature != null){
				errorFeature.setLayerName(validatorLayer.getLayerName());
				errorLayer.addErrorFeature(errorFeature);
			}else{
				featureCollection.add(simpleFeature);
				continue;
			}
		}
		validatorLayer.setSimpleFeatureCollection(featureCollection);
		if(errorLayer.getErrFeatureList().size() > 0){
			return errorLayer;
		}else{
			return null;
		}
	}

	public ErrorLayer validateAttributeFix(Map<String,List<String>> notNullAtt) throws SchemaException{
		ErrorLayer errorLayer = new ErrorLayer();
		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		DefaultFeatureCollection featureCollection = new DefaultFeatureCollection();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			ErrorFeature errorFeature = attributeValidator.validateAttributeFix(simpleFeature, notNullAtt);
			if(errorFeature != null){
				errorFeature.setLayerName(validatorLayer.getLayerName());
				errorLayer.addErrorFeature(errorFeature);
			}else{
				featureCollection.add(simpleFeature);
				continue;
			}
		}
		validatorLayer.setSimpleFeatureCollection(featureCollection);
		if(errorLayer.getErrFeatureList().size() > 0){
			return errorLayer;
		}else{
			return null;
		}
	}

	public ErrorLayer validateNodeMiss(List<GeoLayer> relationLayers, String geomColumn, double tolerence) throws SchemaException, IOException{
		String geomCol = "";
		FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();

		if(geomColumn.equals("")){
			geomCol = "geom";
		}
		else{
			geomCol = geomColumn;
		}

		ErrorLayer errorLayer = new ErrorLayer();
		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		List<SimpleFeature> simpleFeatures = new ArrayList<SimpleFeature>();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();
		/*while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			simpleFeatures.add(simpleFeature);
		}*/

		for (int i = 0; i < relationLayers.size(); i++) {

			GeoLayer relationLayer = relationLayers.get(i);
			SimpleFeatureCollection relationSfc = relationLayer.getSimpleFeatureCollection();
			SimpleFeatureSource featureSource = DataUtilities.source(relationSfc);

			/*SimpleFeatureIterator relationSimpleFeatureIterator = relationSfc.features();
			List<SimpleFeature> relationSimplFeatures = new ArrayList<SimpleFeature>();
			while (relationSimpleFeatureIterator.hasNext()) {
				SimpleFeature relationSimpleFeature = relationSimpleFeatureIterator.next();
				System.out.println(relationSimpleFeature.getAttribute("ID")+", ");
				relationSimplFeatures.add(relationSimpleFeature);
			}*/

			while (simpleFeatureIterator.hasNext()) {
				SimpleFeature simpleFeature = simpleFeatureIterator.next();

				Polygon polygon = (Polygon) simpleFeature.getDefaultGeometry();
				/* Geometry geometry = (Geometry)simpleFeature.getDefaultGeometry(); 
				  Coordinate[] coordinates = geometry.getCoordinates(); 
				  LinearRing ring = geometryFactory.createLinearRing(coordinates); 
				  LinearRing holes[] = null; 
				  Polygon polygon = geometryFactory.createPolygon(ring, holes);*/
				List<SimpleFeature> relationSimplFeatures2 = new ArrayList<SimpleFeature>();

				Filter filter = ff.intersects(ff.property(geomCol), ff.literal(polygon));
				/*Filter filter2 = ff.within(ff.property(geomCol), ff.literal(polygon));
				 * 
				Filter orFilter = ff.or(filter, filter2);*/
				SimpleFeatureCollection collection = relationSfc.subCollection(filter);
				SimpleFeatureIterator featureIterator = collection.features();

				while(featureIterator.hasNext()){
					SimpleFeature feature = featureIterator.next();
					relationSimplFeatures2.add(feature);
				}


				//				SimpleFeatureCollection relationSimplFeatures = featureSource.getFeatures(filter);
				if (relationSimplFeatures2 != null) {
					//List<ErrorFeature> errorFeatures = graphicValidator.validateNodeMiss(simpleFeature,relationSfc.subCollection(filter));
					List<ErrorFeature> errorFeatures = graphicValidator.validateNodeMiss(simpleFeature,relationSimplFeatures2, tolerence);
					if (errorFeatures != null) {
						for (ErrorFeature errorFeature : errorFeatures) {
							errorFeature.setLayerName(validatorLayer.getLayerName());
							errorLayer.addErrorFeature(errorFeature);
						}
					}
				}
			}
		}
		if(errorLayer.getErrFeatureList().size() > 0){
			return errorLayer;
		}else{
			return null;
		}
	}
	
	
	public ErrorLayer validateCloseCollection(ValidateCloseCollectionLayer closeCollectionLayer, String geomColunm){
		FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();
		
		ErrorLayer errorLayer = new ErrorLayer();
		
		if(closeCollectionLayer!=null){
			GeoLayer targetLayer = validatorLayer;
			Map<MapSystemRuleType, GeoLayer> collectionMap = closeCollectionLayer.getCollectionMap();
			Map<MapSystemRuleType, LineString> collectionBoundary = closeCollectionLayer.getCollectionBoundary();
			double tolorence = closeCollectionLayer.getTolorence();
			List<ValidatorOption> closeValidateOptions = closeCollectionLayer.getCloseValidateOptions();
			Map<MapSystemRuleType, Polygon> targetFeaturesGetBoundary = closeCollectionLayer.getTargetFeaturesGetBoundary();
			Map<MapSystemRuleType, Polygon> nearFeaturesGetBoundary = closeCollectionLayer.getNearFeaturesGetBoundary();
			
			//인접도엽 옵션객체 선언
			/*EntityNone entityNone = null;
			EdgeMatchMiss matchMiss = null;
			RefZValueMiss refZValueMiss = null;
			RefLayerMiss refLayerMiss = null;
			RefAttributeMiss refAttributeMiss = null;
			
			for(ValidatorOption validatorOption : closeValidateOptions){
				if (validatorOption instanceof EntityNone) {
					entityNone = (EntityNone) validatorOption;
				}
				if (validatorOption instanceof EdgeMatchMiss) {
					matchMiss = (EdgeMatchMiss) validatorOption;
				}
				if (validatorOption instanceof RefZValueMiss) {
					refZValueMiss = (RefZValueMiss) validatorOption;
				}
				if (validatorOption instanceof EntityNone) {
					refLayerMiss = (RefLayerMiss) validatorOption;
				}
				if (validatorOption instanceof EntityNone) {
					refAttributeMiss = (RefAttributeMiss) validatorOption;
				}
			}*/
			
			//도엽 라인 선언			
			LineString topLineString = null;
			LineString bottomLineString = null;
			LineString leftLineString = null;
			LineString rightLineString = null;
			if(collectionBoundary!=null){
				topLineString = collectionBoundary.get(MapSystemRuleType.TOP);
				bottomLineString = collectionBoundary.get(MapSystemRuleType.BOTTOM);
				leftLineString = collectionBoundary.get(MapSystemRuleType.LEFT);
				rightLineString = collectionBoundary.get(MapSystemRuleType.RIGHT);
			}
			else
				return null;
			
			
			
//			SimpleFeatureCollection targetCollection = targetLayer.getSimpleFeatureCollection();
			

			// 인접도엽 레이어 GET
			GeoLayer topGeoLayer = collectionMap.get(MapSystemRuleType.TOP);;
			GeoLayer bottomGeoLayer = collectionMap.get(MapSystemRuleType.BOTTOM);
			GeoLayer leftGeoLayer = collectionMap.get(MapSystemRuleType.LEFT);
			GeoLayer rightGeoLayer = collectionMap.get(MapSystemRuleType.RIGHT);
			
			
			// 대상도엽 Boundary GET
			Polygon topPolygon = targetFeaturesGetBoundary.get(MapSystemRuleType.TOP);
			Polygon bottomPolygon = targetFeaturesGetBoundary.get(MapSystemRuleType.BOTTOM);
			Polygon leftPolygon = targetFeaturesGetBoundary.get(MapSystemRuleType.LEFT);
			Polygon rightPolygon = targetFeaturesGetBoundary.get(MapSystemRuleType.RIGHT);
			
			
			// 인접도엽 Boundary GET
			Polygon nearTopPolygon = nearFeaturesGetBoundary.get(MapSystemRuleType.TOP);
			Polygon nearBottomPolygon = nearFeaturesGetBoundary.get(MapSystemRuleType.BOTTOM);
			Polygon nearLeftPolygon = nearFeaturesGetBoundary.get(MapSystemRuleType.LEFT);
			Polygon nearRightPolygon = nearFeaturesGetBoundary.get(MapSystemRuleType.RIGHT);
			
			
			// 대상도엽 Feature 리스트
			List<SimpleFeature> topFeatureList = new ArrayList<SimpleFeature>();
			List<SimpleFeature> bottomFeatureList = new ArrayList<SimpleFeature>();
			List<SimpleFeature> leftFeatureList = new ArrayList<SimpleFeature>();
			List<SimpleFeature> rightFeatureList = new ArrayList<SimpleFeature>();

			
			// 인접도엽 Feature 리스트
			List<SimpleFeature> nearTopFeatureList = new ArrayList<SimpleFeature>();
			List<SimpleFeature> nearBottomFeatureList = new ArrayList<SimpleFeature>();
			List<SimpleFeature> nearLeftFeatureList = new ArrayList<SimpleFeature>();
			List<SimpleFeature> nearRightFeatureList = new ArrayList<SimpleFeature>();

			
			// 대상도엽, 인접도엽 Tolorence 영역내 FeatureList GET
			if(topGeoLayer!=null){
				Filter topFilter = ff.intersects(ff.property(geomColunm), ff.literal(topPolygon));
				Filter nearTopFilter = ff.intersects(ff.property(geomColunm), ff.literal(nearTopPolygon));
				
				
				SimpleFeatureCollection topCollection = targetLayer.getSimpleFeatureCollection().subCollection(topFilter);
				SimpleFeatureCollection nearTopCollection = topGeoLayer.getSimpleFeatureCollection().subCollection(nearTopFilter);
				
				SimpleFeatureIterator topFeatureIterator = topCollection.features();
				SimpleFeatureIterator nearTopFeatureIterator = nearTopCollection.features();

				while (topFeatureIterator.hasNext()) {
					topFeatureList.add(topFeatureIterator.next());
				}

				while (nearTopFeatureIterator.hasNext()) {
					nearTopFeatureList.add(nearTopFeatureIterator.next());
				}
				
				List<ErrorFeature> errorFeatures = new ArrayList<ErrorFeature>();
				for(SimpleFeature targetFeature : topFeatureList){
					errorFeatures = closeCollectionValidator.ValidateCloseCollection(targetFeature, nearTopFeatureList, closeValidateOptions, topLineString, tolorence);	
				}
				
				for(ErrorFeature errorFeature : errorFeatures){
					errorFeature.setLayerName(validatorLayer.getLayerName());
					errorLayer.addErrorFeature(errorFeature);
				}
				
			}
			
			if(bottomGeoLayer!=null){
				Filter bottomFilter = ff.intersects(ff.property(geomColunm), ff.literal(bottomPolygon));
				Filter bottomTopFilter = ff.intersects(ff.property(geomColunm), ff.literal(nearBottomPolygon));
				
				
				SimpleFeatureCollection bottomCollection = targetLayer.getSimpleFeatureCollection().subCollection(bottomFilter);
				SimpleFeatureCollection nearBottomCollection = topGeoLayer.getSimpleFeatureCollection().subCollection(bottomTopFilter);
				
				SimpleFeatureIterator bottomFeatureIterator = bottomCollection.features();
				SimpleFeatureIterator nearBottomFeatureIterator = nearBottomCollection.features();

				while (bottomFeatureIterator.hasNext()) {
					bottomFeatureList.add(bottomFeatureIterator.next());
				}

				while (nearBottomFeatureIterator.hasNext()) {
					nearBottomFeatureList.add(nearBottomFeatureIterator.next());
				}
				
				List<ErrorFeature> errorFeatures = new ArrayList<ErrorFeature>();
				for(SimpleFeature targetFeature : bottomFeatureList){
					errorFeatures = closeCollectionValidator.ValidateCloseCollection(targetFeature, nearBottomFeatureList, closeValidateOptions, bottomLineString, tolorence);	
				}
				
				for(ErrorFeature errorFeature : errorFeatures){
					errorFeature.setLayerName(validatorLayer.getLayerName());
					errorLayer.addErrorFeature(errorFeature);
				}
			}
			
			if(leftGeoLayer!=null){
				Filter leftFilter = ff.intersects(ff.property(geomColunm), ff.literal(leftPolygon));
				Filter nearLeftFilter = ff.intersects(ff.property(geomColunm), ff.literal(nearLeftPolygon));
				
				
				SimpleFeatureCollection leftCollection = targetLayer.getSimpleFeatureCollection().subCollection(leftFilter);
				SimpleFeatureCollection nearLeftCollection = topGeoLayer.getSimpleFeatureCollection().subCollection(nearLeftFilter);
				
				SimpleFeatureIterator leftFeatureIterator = leftCollection.features();
				SimpleFeatureIterator nearLeftFeatureIterator = nearLeftCollection.features();

				while (leftFeatureIterator.hasNext()) {
					leftFeatureList.add(leftFeatureIterator.next());
				}

				while (nearLeftFeatureIterator.hasNext()) {
					nearLeftFeatureList.add(nearLeftFeatureIterator.next());
				}
				
				List<ErrorFeature> errorFeatures = new ArrayList<ErrorFeature>();
				for(SimpleFeature targetFeature : leftFeatureList){
					errorFeatures = closeCollectionValidator.ValidateCloseCollection(targetFeature, nearLeftFeatureList, closeValidateOptions, leftLineString, tolorence);	
				}
				
				for(ErrorFeature errorFeature : errorFeatures){
					errorFeature.setLayerName(validatorLayer.getLayerName());
					errorLayer.addErrorFeature(errorFeature);
				}
			}
			
			if(rightGeoLayer!=null){
				Filter rightFilter = ff.intersects(ff.property(geomColunm), ff.literal(rightPolygon));
				Filter nearRightFilter = ff.intersects(ff.property(geomColunm), ff.literal(nearRightPolygon));
				
				
				SimpleFeatureCollection rightCollection = targetLayer.getSimpleFeatureCollection().subCollection(rightFilter);
				SimpleFeatureCollection nearRightCollection = topGeoLayer.getSimpleFeatureCollection().subCollection(nearRightFilter);
				
				SimpleFeatureIterator rightFeatureIterator = rightCollection.features();
				SimpleFeatureIterator nearRightFeatureIterator = nearRightCollection.features();

				while (rightFeatureIterator.hasNext()) {
					rightFeatureList.add(rightFeatureIterator.next());
				}

				while (nearRightFeatureIterator.hasNext()) {
					nearRightFeatureList.add(nearRightFeatureIterator.next());
				}
				
				List<ErrorFeature> errorFeatures = new ArrayList<ErrorFeature>();
				for(SimpleFeature targetFeature : rightFeatureList){
					errorFeatures = closeCollectionValidator.ValidateCloseCollection(targetFeature, nearRightFeatureList, closeValidateOptions, rightLineString, tolorence);	
				}
				
				for(ErrorFeature errorFeature : errorFeatures){
					errorFeature.setLayerName(validatorLayer.getLayerName());
					errorLayer.addErrorFeature(errorFeature);
				}
			}
		}
		else
			return null;
		
		if(errorLayer.getErrFeatureList().size() > 0){
			return errorLayer;
		}else{
			return null;
		}
	}
}
