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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.SchemaException;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.type.FeatureType;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import com.git.gdsbuilder.type.geoserver.layer.GeoLayer;
import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.type.validate.error.ErrorLayer;
import com.git.gdsbuilder.validator.feature.FeatureAttributeValidator;
import com.git.gdsbuilder.validator.feature.FeatureAttributeValidatorImpl;
import com.git.gdsbuilder.validator.feature.FeatureGraphicValidator;
import com.git.gdsbuilder.validator.feature.FeatureGraphicValidatorImpl;

public class LayerValidatorImpl implements LayerValidator {

	GeoLayer validatorLayer;
	FeatureGraphicValidator graphicValidator = new FeatureGraphicValidatorImpl();
	FeatureAttributeValidator attributeValidator = new FeatureAttributeValidatorImpl();

	public LayerValidatorImpl() {

	}

	public LayerValidatorImpl(GeoLayer validatorLayer) {
		super();
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

	public ErrorLayer validateZ_ValueAmbiguous(String key) throws SchemaException {

		ErrorLayer errLayer = new ErrorLayer();

		SimpleFeatureCollection sfc = validatorLayer.getSimpleFeatureCollection();
		SimpleFeatureIterator simpleFeatureIterator = sfc.features();
		while (simpleFeatureIterator.hasNext()) {
			SimpleFeature simpleFeature = simpleFeatureIterator.next();
			ErrorFeature errFeature = attributeValidator.validateZvalueAmbiguous(simpleFeature, key);
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
	
	public ErrorLayer validateCrossRoad(List<GeoLayer> relationLayers) throws SchemaException{
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
					ErrorFeature errorFeature = graphicValidator.validateCrossRoad(simpleFeature, relationSimpleFeature);
					if(errorFeature != null){
						errorFeature.setLayerName(validatorLayer.getLayerName());
						errorLayer.addErrorFeature(errorFeature);
					}else{
						continue;
					}
				}
			}
		}
		return errorLayer;
	}
	
	
}
