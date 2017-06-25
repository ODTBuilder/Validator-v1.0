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

import java.util.Iterator;
import java.util.List;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.SchemaException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.type.validate.error.ErrorLayer;
import com.git.gdsbuilder.type.validate.option.Admin;
import com.git.gdsbuilder.type.validate.option.AttributeFix;
import com.git.gdsbuilder.type.validate.option.BridgeName;
import com.git.gdsbuilder.type.validate.option.CrossRoad;
import com.git.gdsbuilder.type.validate.option.Z_ValueAmbiguous;
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
	public ErrorFeature validateZvalueAmbiguous(SimpleFeature simpleFeature, String attributeKey)
			throws SchemaException {
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		
		Object attributeValue = simpleFeature.getAttribute(attributeKey);
		
		if(attributeValue.equals("null") || attributeValue.equals("0.0")){
			ErrorFeature errorFeature = new ErrorFeature(simpleFeature.getID(), Z_ValueAmbiguous.Type.Z_VALUEAMBIGUOUS.errType(),
					Z_ValueAmbiguous.Type.Z_VALUEAMBIGUOUS.errName(), geometry.getInteriorPoint());
			return errorFeature;
		}else{
		return null;
		}
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
	
	}

	@Override
	public ErrorFeature validateAttributeFix(SimpleFeature simpleFeature, JSONObject notNullAtt)
			throws SchemaException {
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		boolean flag = true;
		if(notNullAtt != null){
			Iterator iterator = notNullAtt.keySet().iterator();
			while(iterator.hasNext()){

				String attributeKey = (String) iterator.next(); 
				String attribute = (String) simpleFeature.getAttribute(attributeKey); 
				JSONArray attributeArray = (JSONArray) notNullAtt.get(attributeKey); 
			
				if(!(attributeArray.isEmpty())){
					if(attribute != null){
						Iterator attrIterator = attributeArray.iterator();
						while (attrIterator.hasNext()) {
							String attributeValue = (String) attrIterator.next();
							if(!(attributeValue.equals(attribute))){
								flag = false;
							}else{
								flag = true;
								break;
							}
						}
					}else{
						flag = false;
						break;
					}
				}else{
					if(attribute.equals("null")){
						flag = false;
						break;
					}
				}
			}
		}
		
		if(flag == false){
			ErrorFeature errorFeature = new ErrorFeature(simpleFeature.getID(), AttributeFix.Type.ATTRIBUTEFIX.errType(),
					AttributeFix.Type.ATTRIBUTEFIX.errName(), geometry.getInteriorPoint());
			return errorFeature;
		}else{
			return null;
		}
	}

	public ErrorFeature validateBridgeName(SimpleFeature simpleFeature, SimpleFeature relationSimpleFeature) throws SchemaException{
		Geometry relGeometry = (Geometry) relationSimpleFeature.getDefaultGeometry();
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Geometry returnGeom = null; 
		if(geometry.intersects(relGeometry) || geometry.crosses(relGeometry) || geometry.overlaps(relGeometry)){
			Object simValue = simpleFeature.getAttribute("명칭");
			Object relValue = relationSimpleFeature.getAttribute("하천명");
			if(simValue.equals("null") || relValue.equals("null")){
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

	public ErrorFeature validateAdmin(SimpleFeature simpleFeature) throws SchemaException{
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		String title = simpleFeature.getAttribute("명칭").toString();
		int titleLength = title.length();
		String division = simpleFeature.getAttribute("구분").toString();
		int divisionLength = division.length();

		if(title == null || division == null){
			ErrorFeature errorFeature = new ErrorFeature(simpleFeature.getID(), Admin.Type.ADMIN.errType(), 
					Admin.Type.ADMIN.errName(), geometry.getInteriorPoint());
			return errorFeature;
		}else{
			if(titleLength < 2){
				ErrorFeature errorFeature = new ErrorFeature(simpleFeature.getID(), Admin.Type.ADMIN.errType(), 
						Admin.Type.ADMIN.errName(), geometry.getInteriorPoint());
				return errorFeature;
			}else{
				if(titleLength > divisionLength){
					int length = titleLength - divisionLength;
					String title_end = title.substring(length);
					if(!(title_end.equals(division))){
						ErrorFeature errorFeature = new ErrorFeature(simpleFeature.getID(), Admin.Type.ADMIN.errType(), 
								Admin.Type.ADMIN.errName(), geometry.getInteriorPoint());
						return errorFeature;
					}else{
						return null;
					}
				}else{
					ErrorFeature errorFeature = new ErrorFeature(simpleFeature.getID(), Admin.Type.ADMIN.errType(), 
							Admin.Type.ADMIN.errName(), geometry.getInteriorPoint());
					return errorFeature;
				}
			}
		}
	}
	
}
