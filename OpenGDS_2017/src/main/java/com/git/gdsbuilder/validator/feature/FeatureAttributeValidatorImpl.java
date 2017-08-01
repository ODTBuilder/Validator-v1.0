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
import java.util.Map;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONArray;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.type.validate.option.Admin;
import com.git.gdsbuilder.type.validate.option.AttributeFix;
import com.git.gdsbuilder.type.validate.option.BridgeName;
import com.git.gdsbuilder.type.validate.option.EntityDuplicated;
import com.git.gdsbuilder.type.validate.option.HouseAttribute;
import com.git.gdsbuilder.type.validate.option.ZValueAmbiguous;
import com.vividsolutions.jts.geom.Geometry;

public class FeatureAttributeValidatorImpl implements FeatureAttributeValidator {

	@SuppressWarnings("unused")
	@Override
	public ErrorFeature validateZvalueAmbiguous(SimpleFeature simpleFeature, Map<String, List<String>> attributeKey) {
		ErrorFeature errorFeature = null;
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		String featureIdx = simpleFeature.getID();
		Property featuerIDPro = simpleFeature.getProperty("feature_id");
		String featureID = (String) featuerIDPro.getValue();
		if (attributeKey != null) {
			Iterator iterator = attributeKey.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Object attributeValue = simpleFeature.getAttribute(key);
				if (attributeValue != null) {
					if (attributeValue.toString().equals("") || attributeValue.toString().equals("0.0")) {
						errorFeature = new ErrorFeature(featureIdx, featureID,
								ZValueAmbiguous.Type.ZVALUEAMBIGUOUS.errType(),
								ZValueAmbiguous.Type.ZVALUEAMBIGUOUS.errName(), geometry.getInteriorPoint());
					} else {
						String attributeStr = attributeValue.toString();
						Double num = new Double(Double.parseDouble(attributeStr));
						int intNum = num.intValue();
						if (!(num == intNum)) {
							errorFeature = new ErrorFeature(featureIdx, featureID,
									ZValueAmbiguous.Type.ZVALUEAMBIGUOUS.errType(),
									ZValueAmbiguous.Type.ZVALUEAMBIGUOUS.errName(), geometry.getInteriorPoint());
						} else {
							JSONArray zValueArray = (JSONArray) attributeKey.get(key);
							String tolerence = zValueArray.get(0).toString();
							Double tolerenceDou = new Double(Double.parseDouble(tolerence));
							Double result = num % tolerenceDou;

							if (!(result == 0.0)) {
								errorFeature = new ErrorFeature(featureIdx, featureID,
										ZValueAmbiguous.Type.ZVALUEAMBIGUOUS.errType(),
										ZValueAmbiguous.Type.ZVALUEAMBIGUOUS.errName(), geometry.getInteriorPoint());
							}
						}
					}
				}
			}
		} else {
			errorFeature = new ErrorFeature(featureIdx, featureID, ZValueAmbiguous.Type.ZVALUEAMBIGUOUS.errType(),
					ZValueAmbiguous.Type.ZVALUEAMBIGUOUS.errName(), geometry.getInteriorPoint());
		}

		return errorFeature;
	}

	@Override
	public ErrorFeature validateAttributeFix(SimpleFeature simpleFeature, Map<String, List<String>> notNullAtt)
			throws SchemaException {

		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		boolean flag = true;
		if (notNullAtt != null) {
			Iterator iterator = notNullAtt.keySet().iterator();
			while (iterator.hasNext()) {
				String attributeKey = (String) iterator.next();
				Object attribute = simpleFeature.getAttribute(attributeKey); // value
				JSONArray attributeArray = (JSONArray) notNullAtt.get(attributeKey);
				if (attributeArray.get(0).equals("") || attributeArray.isEmpty()) {
					//
					if (attribute == null) {
						flag = false;
						break;
					}
				} else {
					//
					if (attribute != null) {
						String attributeStr = attribute.toString();
						Iterator attrIterator = attributeArray.iterator();
						while (attrIterator.hasNext()) {
							String attributeValue = (String) attrIterator.next();
							if (attributeValue.equals("") && !attributeStr.equals("")) {
								flag = true;
								break;
							}
							if (!(attributeValue.equals(attributeStr))) {
								flag = false;
							} else {
								flag = true;
								break;
							}
						}
					}else{
						
						flag = false;
						break;
					}
				}
			}
		}
		if (flag == false) {
			String featureIdx = simpleFeature.getID();
			Property featuerIDPro = simpleFeature.getProperty("feature_id");
			String featureID = (String) featuerIDPro.getValue();
			ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
					AttributeFix.Type.ATTRIBUTEFIX.errType(), AttributeFix.Type.ATTRIBUTEFIX.errName(),
					geometry.getInteriorPoint());
			return errorFeature;
		} else {
			return null;
		}
	}

	public ErrorFeature validateBridgeName(SimpleFeature simpleFeature, SimpleFeature relationSimpleFeature)
			throws SchemaException {
		Geometry relGeometry = (Geometry) relationSimpleFeature.getDefaultGeometry();
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Geometry returnGeom = null;
		if (geometry.intersects(relGeometry) || geometry.crosses(relGeometry) || geometry.overlaps(relGeometry)) {
			Object simValue = simpleFeature.getAttribute("하천명");
			Object relValue = relationSimpleFeature.getAttribute("명칭");
			if (simValue.equals("null") || relValue.equals("null")) {
				returnGeom = geometry.intersection(relGeometry);
				String featureIdx = simpleFeature.getID();
				Property featuerIDPro = simpleFeature.getProperty("feature_id");
				String featureID = (String) featuerIDPro.getValue();
				ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
						BridgeName.Type.BRIDGENAME.errType(), BridgeName.Type.BRIDGENAME.errName(),
						returnGeom.getInteriorPoint());
				return errorFeature;
			} else {
				if (!(simValue.equals(relValue))) {
					returnGeom = geometry.intersection(relGeometry);
					String featureIdx = simpleFeature.getID();
					Property featuerIDPro = simpleFeature.getProperty("feature_id");
					String featureID = (String) featuerIDPro.getValue();
					ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID,
							BridgeName.Type.BRIDGENAME.errType(), BridgeName.Type.BRIDGENAME.errName(),
							returnGeom.getInteriorPoint());
					return errorFeature;
				} else {
					return null;
				}
			}
		} else {
			return null;
		}
	}

	public ErrorFeature validateAdmin(SimpleFeature simpleFeature) throws SchemaException {
		
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		String title = simpleFeature.getAttribute("명칭").toString();
		int titleLength = title.length();
		String division = simpleFeature.getAttribute("구분").toString();
		int divisionLength = division.length();

		String featureIdx = simpleFeature.getID();
		Property featuerIDPro = simpleFeature.getProperty("feature_id");
		String featureID = (String) featuerIDPro.getValue();

		if (title == null || division == null) {
			ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID, Admin.Type.ADMIN.errType(),
					Admin.Type.ADMIN.errName(), geometry.getInteriorPoint());
			return errorFeature;
		} else {
			if (titleLength < 2) {
				ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID, Admin.Type.ADMIN.errType(),
						Admin.Type.ADMIN.errName(), geometry.getInteriorPoint());
				return errorFeature;
			} else {
				if (titleLength > divisionLength) {
					int length = titleLength - divisionLength;
					String title_end = title.substring(length);
					if (!(title_end.equals(division))) {
						ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID, Admin.Type.ADMIN.errType(),
								Admin.Type.ADMIN.errName(), geometry.getInteriorPoint());
						return errorFeature;
					} else {
						return null;
					}
				} else {
					ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID, Admin.Type.ADMIN.errType(),
							Admin.Type.ADMIN.errName(), geometry.getInteriorPoint());
					return errorFeature;
				}
			}
		}
	}
	
	public ErrorFeature validateHouseAttribute(SimpleFeature simpleFeature){
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		String notNullAttr = simpleFeature.getAttribute("주기").toString();

		String featureIdx = simpleFeature.getID();
		Property featuerIDPro = simpleFeature.getProperty("feature_id");
		String featureID = (String) featuerIDPro.getValue();
		
		if (notNullAttr != null) {
			ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID, HouseAttribute.Type.HOUSEATTRIBUTE.errType(),
					HouseAttribute.Type.HOUSEATTRIBUTE.errName(), geometry.getInteriorPoint());
			return errorFeature;
		}else{
			return null;
		}
	}
	
	
	
	


	public ErrorFeature validateEntityDuplicated(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ) {

		List attrIList = simpleFeatureI.getAttributes();
		List attrJList = simpleFeatureJ.getAttributes();

		int equalsCount = 0;
		for (int i = 1; i < attrIList.size(); i++) {
			Object attrI = attrIList.get(i);
			breakOut: for (int j = 1; j < attrJList.size(); j++) {
				Object attrJ = attrJList.get(j);
				if (attrI.toString().equals(attrJ.toString())) {
					equalsCount++;
					break breakOut;
				}
			}
		}
		if (equalsCount == attrIList.size() - 1) {
			String featureIdx = simpleFeatureI.getID();
			Property featuerIDPro = simpleFeatureI.getProperty("feature_id");
			String featureID = (String) featuerIDPro.getValue();
			Geometry geometryI = (Geometry) simpleFeatureI.getDefaultGeometry();
			ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID, EntityDuplicated.Type.ENTITYDUPLICATED.errType(),
					EntityDuplicated.Type.ENTITYDUPLICATED.errName(), geometryI.getInteriorPoint());

			return errFeature;
		} else {
			return null;
		}
	}
	
	
	
	
	
}
