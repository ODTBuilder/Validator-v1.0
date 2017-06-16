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

package com.git.gdsbuilder.validator.feature;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.SchemaException;
import org.json.simple.JSONArray;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.type.validate.error.ErrorLayer;
import com.git.gdsbuilder.type.validate.option.BridgeName;
import com.git.gdsbuilder.type.validate.option.CrossRoad;
import com.vividsolutions.jts.geom.Geometry;

public class FeatureAttributeValidatorImpl implements FeatureAttributeValidator {

	@Override
	public ErrorLayer validateCharacterAccuracy(SimpleFeatureCollection validatorLayer, String labelI,
			SimpleFeatureCollection relationLayer, String labelJ) throws SchemaException {
		return null;

		//		ErrorLayer errLayer = new ErrorLayer();
		//		DefaultFeatureCollection errSFC = new DefaultFeatureCollection();
		//		List<DetailsValidatorResult> dtReports = new ArrayList<DetailsValidatorResult>();
		//
		//		List<SimpleFeature> tmpSimpleFeaturesI = new ArrayList<SimpleFeature>();
		//		SimpleFeatureIterator simpleFeatureIteratorI = validatorLayer.features();
		//		while (simpleFeatureIteratorI.hasNext()) {
		//			SimpleFeature simpleFeature = simpleFeatureIteratorI.next();
		//			tmpSimpleFeaturesI.add(simpleFeature);
		//		}
		//
		//		List<SimpleFeature> tmpSimpleFeaturesJ = new ArrayList<SimpleFeature>();
		//		SimpleFeatureIterator simpleFeatureIteratorJ = relationLayer.features();
		//		while (simpleFeatureIteratorJ.hasNext()) {
		//			SimpleFeature simpleFeature = simpleFeatureIteratorJ.next();
		//			tmpSimpleFeaturesJ.add(simpleFeature);
		//		}
		//
		//		AttributeValidator attributeValidator = new AttributeValidatorImpl();
		//		int tmpSizeI = tmpSimpleFeaturesI.size();
		//		int tmpSizeJ = tmpSimpleFeaturesJ.size();
		//		for (int i = 0; i < tmpSizeI; i++) {
		//			SimpleFeature simpleFeatureI = tmpSimpleFeaturesI.get(i);
		//			boolean isTrue = false;
		//			int trueCount = 0;
		//			for (int j = 0; j < tmpSizeJ; j++) {
		//				SimpleFeature simpleFeatureJ = tmpSimpleFeaturesJ.get(j);
		//				isTrue = attributeValidator.isEqualsCharacter(simpleFeatureI, labelI, simpleFeatureJ, labelJ);
		//				if (isTrue) {
		//					trueCount++;
		//				}
		//			}
		//			if (trueCount == 0) {
		//				ErrorFeature errFeature = attributeValidator.characterAccuracy(simpleFeatureI);
		//				if (errFeature != null) {
		//					errSFC.add(errFeature.getErrFeature());
		//					dtReports.add(errFeature.getDtReport());
		//				} else {
		//					continue;
		//				}
		//			}
		//		}
		//		if (errSFC.size() > 0 && dtReports.size() > 0) {
		//			errLayer.setErrFeatureCollection(errSFC);
		//			errLayer.setDetailsValidatorReport(dtReports);
		//			return errLayer;
		//		} else {
		//			return null;
		//		}
	}

	@Override
	public ErrorFeature validateZvalueAmbiguous(SimpleFeature simpleFeature, String notNullAtt)
			throws SchemaException {

		boolean isError = false;
		// if (notNullAtt != null) {
		// int attributeCount = simpleFeature.getAttributeCount();
		// Iterator iterator = notNullAtt.iterator();
		// if (attributeCount > 1) {
		// while (iterator.hasNext()) {
		// String attribute = (String) iterator.next();
		// for (int i = 1; i < attributeCount; i++) {
		// String key =
		// simpleFeature.getFeatureType().getType(i).getName().toString();
		// if (key.equals(attribute)) {
		// Object value = simpleFeature.getAttribute(i);
		// if (value != null) {
		// if (value.toString().equals("") || value.toString().equals("0.0")) {
		// isError = true;
		// }
		// } else {
		// isError = true;
		// }
		// }
		// }
		// }
		// } else {
		// isError = true;
		// }
		// }
		// if (isError) {
		// DataConvertor convertService = new DataConvertor();
		// String errFeatureID = simpleFeature.getID();
		// Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		// Geometry returnGeo = geometry.getInteriorPoint();
		//
		// // SimpleFeature
		// SimpleFeature errSimpleFeature =
		// convertService.createErrSimpleFeature(errFeatureID, returnGeo,
		// Z_ValueAmbiguous.Type.Z_VALUEAMBIGUOUS.errName(),
		// Z_ValueAmbiguous.Type.Z_VALUEAMBIGUOUS.errType());
		//
		// // DetailReport
		// DetailsValidatorResult detailReport = new
		// DetailsValidatorResult(errFeatureID,
		// Z_ValueAmbiguous.Type.Z_VALUEAMBIGUOUS.errType(),
		// Z_ValueAmbiguous.Type.Z_VALUEAMBIGUOUS.errName(),
		// geometry.getCoordinate().x, geometry.getCoordinate().y);
		//
		// ErrorFeature errorFeature = new ErrorFeature(errSimpleFeature,
		// detailReport);
		//
		// return errorFeature;
		// } else {
		// return null;
		// }
		return null;
	}

	@Override
	public ErrorLayer validateAttributeFix(SimpleFeatureCollection validatorLayer, JSONArray notNullAtt)
			throws SchemaException {
		return null;

		//		ErrorLayer errLayer = new ErrorLayer();
		//		DefaultFeatureCollection errSFC = new DefaultFeatureCollection();
		//		List<DetailsValidatorResult> dtReports = new ArrayList<DetailsValidatorResult>();
		//
		//		AttributeValidator attributeValidator = new AttributeValidatorImpl();
		//		SimpleFeatureIterator simpleFeatureIterator = validatorLayer.features();
		//		while (simpleFeatureIterator.hasNext()) {
		//			SimpleFeature simpleFeature = simpleFeatureIterator.next();
		//			ErrorFeature errFeature = attributeValidator.attributeFix(simpleFeature, notNullAtt);
		//			if (errFeature != null) {
		//				errSFC.add(errFeature.getErrFeature());
		//				dtReports.add(errFeature.getDtReport());
		//			} else {
		//				continue;
		//			}
		//		}
		//		if (errSFC.size() > 0) {
		//			errLayer.setErrFeatureCollection(errSFC);
		//			errLayer.setDetailsValidatorReport(dtReports);
		//			return errLayer;
		//		} else {
		//			return null;
		//		}
	}

	public ErrorFeature validateBridgeName(SimpleFeature simpleFeature, SimpleFeature relationSimpleFeature) throws SchemaException{
		Geometry relGeometry = (Geometry) relationSimpleFeature.getDefaultGeometry();
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Geometry returnGeom = null; 
		System.out.println(simpleFeature.getAttribute("명칭").toString());
		if(geometry.intersects(relGeometry) || geometry.crosses(relGeometry) || geometry.overlaps(relGeometry)){
			Object simValue = simpleFeature.getAttribute("명칭");
			Object relValue = relationSimpleFeature.getAttribute("하천명");
			if(simValue == null || relValue == null){
				returnGeom = geometry.intersection(relGeometry);
				ErrorFeature errorFeature = new ErrorFeature(simpleFeature.getID(), BridgeName.Type.BRIDGENAME.errType(),
						BridgeName.Type.BRIDGENAME.errName(), returnGeom.getInteriorPoint());
				return errorFeature;
			}else{
				if(!(simValue.equals(relValue))){
					returnGeom = geometry.intersection(relGeometry);
					ErrorFeature errorFeature = new ErrorFeature(simpleFeature.getID(), BridgeName.Type.BRIDGENAME.errType(),
							BridgeName.Type.BRIDGENAME.errName(), returnGeom.getInteriorPoint());
					return errorFeature;
				}else{
					return null;
				}
			}
		}else{
			return null;
		}

	}

}
