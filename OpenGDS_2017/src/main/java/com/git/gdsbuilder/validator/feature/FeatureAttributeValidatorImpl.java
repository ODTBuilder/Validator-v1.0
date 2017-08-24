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
import java.util.StringTokenizer;
import java.util.regex.Pattern;

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
import com.git.gdsbuilder.type.validate.option.NeatLineAttribute;
import com.git.gdsbuilder.type.validate.option.NumericalValue;
import com.git.gdsbuilder.type.validate.option.UFIDLength;
import com.git.gdsbuilder.type.validate.option.UFIDRule;
import com.git.gdsbuilder.type.validate.option.ZValueAmbiguous;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

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
		Object titleObj = simpleFeature.getAttribute("명칭").toString();
		Object divisionObj = simpleFeature.getAttribute("구분").toString();

		String featureIdx = simpleFeature.getID();
		Property featuerIDPro = simpleFeature.getProperty("feature_id");
		String featureID = (String) featuerIDPro.getValue();
		
		if (titleObj == null || divisionObj == null) {
			ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID, Admin.Type.ADMIN.errType(),
					Admin.Type.ADMIN.errName(), geometry.getInteriorPoint());
			return errorFeature;
		} else {
			String title = titleObj.toString();
			int titleLength = title.length();
			String division = divisionObj.toString();
			int divisionLength = division.length();
			
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
		String featureIdx = simpleFeature.getID();
		Property featuerIDPro = simpleFeature.getProperty("feature_id");
		String featureID = (String) featuerIDPro.getValue();
		if(featureID.equals("RECORD 459")){
			System.out.println();
		}
		
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Object kindsAttr = simpleFeature.getAttribute("종류");
		Object notNullAttr = simpleFeature.getAttribute("주기");
		
		if(kindsAttr != null){
			String kinds = kindsAttr.toString();
			if(kinds.equals("일반주택") || kinds.equals("일반 주택")){
				if (notNullAttr != null) {
					ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID, HouseAttribute.Type.HOUSEATTRIBUTE.errType(),
							HouseAttribute.Type.HOUSEATTRIBUTE.errName(), geometry.getInteriorPoint());
					return errorFeature;
				}else{
					return null;
				}
			}
		}
		return null;
	}
	
	public ErrorFeature validateUFIDLength(SimpleFeature simpleFeature, double length){
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Object attUFIDObj = (String) simpleFeature.getAttribute("UFID"); // null 체크
		
		// 코딩하기
		if(attUFIDObj != null){
			String attUFIDStr = attUFIDObj.toString(); 
			double attUFID = attUFIDStr.length();
			String featureIdx = simpleFeature.getID();
			Property featureIDPro = simpleFeature.getProperty("feature_id");
			String featureID = (String) featureIDPro.getValue();
			if(attUFID != length){
				ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID, UFIDLength.Type.UFIDLENGTH.errType(),
						UFIDLength.Type.UFIDLENGTH.errName(), geometry.getInteriorPoint());
				return errorFeature;
			}else{
				return null;
			}
		}
		return null;
	}
	
	public ErrorFeature validateNeatLineAttribute(SimpleFeature simpleFeature){
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Object codeObj = simpleFeature.getAttribute("도엽코드");
		Object nameObj = simpleFeature.getAttribute("도엽명");
		
		if(codeObj != null && nameObj != null){
			String pattern =  "^[가-힣]*$";
			String codeinput = codeObj.toString();
			String nameInput = nameObj.toString();
			boolean codeIsTrue = Pattern.matches(pattern, codeinput);
			boolean nameIsTrue = Pattern.matches(pattern, nameInput);
			
			if(codeIsTrue){
				if(!nameIsTrue){
					String featureIdx = simpleFeature.getID();
					Property featureIDPro = simpleFeature.getProperty("feature_id");
					String featureID = (String) featureIDPro.getValue();
					ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID, NeatLineAttribute.Type.NEATLINEATTRIBUTE.errType(),
							NeatLineAttribute.Type.NEATLINEATTRIBUTE.errName(), geometry.getInteriorPoint());
					return errorFeature;
				}
			}
		}
		return null;
	}
	
	public ErrorFeature validateNumericalValue(SimpleFeature simpleFeature, String attribute, String condition, double figure){
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		Object attributeObj = simpleFeature.getAttribute(attribute);
		if(attributeObj != null && ! attributeObj.equals("")){
			String attributeStr = attributeObj.toString();
			String featureIdx = simpleFeature.getID();
			Property featuerIDPro = simpleFeature.getProperty("feature_id");
			String featureID = (String) featuerIDPro.getValue();
			if(featureID.equals("RECORD 458")){
				System.out.println();
			}
			if(isStringDouble(attributeStr)){
				double attributeDou = Double.parseDouble(attributeStr);
				
				if(condition.equals("over")){
					if(attributeDou <= figure){
						//error
						ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID, NumericalValue.Type.NUMERICALVALUE.errType(), 
								NumericalValue.Type.NUMERICALVALUE.errName(), geometry.getInteriorPoint());
						return errorFeature;
					}
				}else if(condition.equals("under")){
					if(attributeDou >= figure){
						// error
						ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID, NumericalValue.Type.NUMERICALVALUE.errType(), 
								NumericalValue.Type.NUMERICALVALUE.errName(), geometry.getInteriorPoint());
						return errorFeature;
					}
				}else if(condition.equals("equal")){
					if(attributeDou != figure){
						//error
						ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID, NumericalValue.Type.NUMERICALVALUE.errType(),
								NumericalValue.Type.NUMERICALVALUE.errName(), geometry.getInteriorPoint());
						return errorFeature;
					}
				}
			}
		}
		return null;
	}

	private static boolean isStringDouble(String s) {
	    try {
	        Double.parseDouble(s);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	  }
	
	
	public ErrorFeature validateUFIDRule(SimpleFeature simpleFeature){
		
		String ufid = simpleFeature.getAttribute("feature_id").toString();
		
		Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
		String featureIdx = simpleFeature.getID();
		Property featuerIDPro = simpleFeature.getProperty("feature_id");
		String featureID = (String) featuerIDPro.getValue();
		Object attUFIDObj = (String) simpleFeature.getAttribute("UFID"); // null 체크
		String simFeatureName = simpleFeature.getFeatureType().getName().getLocalPart();
		int index = simFeatureName.indexOf(":");
		String name = simFeatureName.substring(index + 1);
		StringTokenizer tokens = new StringTokenizer(name); //geo_dxf_37712003_A0013110_LWPOLYLINE"
		String type = tokens.nextToken("_");
		String fileType = tokens.nextToken("_");
		String neatLineName = tokens.nextToken("_");
		String layerName = tokens.nextToken("_");
		String layerType = tokens.nextToken("_");
		
		
//		1000   // 지형지물 관리코드
//		037712014  // 도협번호 필드
//		E003  // 레이어 4자리
//		1 
//		000000000002578
//		1
		
		if(attUFIDObj != null){
			String attUFIDStr = attUFIDObj.toString();
			double attUFIDLength = attUFIDStr.length();
			if(attUFIDLength == 34){
				String AgencyCode = attUFIDStr.substring(0, 4); //4자리
				String neatLine = attUFIDStr.substring(4,13); //9자리
				String layer = attUFIDStr.substring(13,17); //4자리
				String field = attUFIDStr.substring(17,18); //1자리
				String controlNumber = attUFIDStr.substring(18,33);// 15자리
				String errorField = attUFIDStr.substring(33,34);//
				if(neatLine.contains(neatLineName)){
					String layerNameSub = layerName.substring(0,4);
					if(layer.equals(layerNameSub)){
						if(field.equals("1")||field.equals("2")){
							return null;
						}else{
							//error
							ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID, UFIDRule.Type.UFIDRULE.errType(),
									UFIDRule.Type.UFIDRULE.errName(), geometry.getInteriorPoint());
							return errorFeature;
						}
					}else{
						//error
						ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID, UFIDRule.Type.UFIDRULE.errType(), 
								UFIDRule.Type.UFIDRULE.errName(), geometry.getInteriorPoint());
						return errorFeature;
					}
				}else{
					//error
					ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID, UFIDRule.Type.UFIDRULE.errType(),
							UFIDRule.Type.UFIDRULE.errName(), geometry.getInteriorPoint());
					return errorFeature;
				}
				
			}else{
				// error
				ErrorFeature errorFeature = new ErrorFeature(featureIdx, featureID, UFIDRule.Type.UFIDRULE.errType(),
						UFIDRule.Type.UFIDRULE.errName(), geometry.getInteriorPoint());
				return errorFeature;
			}
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
				if (attrI != null && attrJ != null) {
					if (attrI.toString().equals(attrJ.toString())) {
						equalsCount++;
						break breakOut;
					}
				} else if (attrI == null) {
					if (attrJ == null) {
						equalsCount++;
						break breakOut;
					}
				}
			}
		}
		if (equalsCount == attrIList.size() - 1) {
			String featureIdx = simpleFeatureI.getID();
			Property featuerIDPro = simpleFeatureI.getProperty("feature_id");
			String featureID = (String) featuerIDPro.getValue();
			Geometry geometryI = (Geometry) simpleFeatureI.getDefaultGeometry();
			ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
					EntityDuplicated.Type.ENTITYDUPLICATED.errType(), EntityDuplicated.Type.ENTITYDUPLICATED.errName(),
					geometryI.getInteriorPoint());

			return errFeature;
		} else {
			return null;
		}
	}
	
	
	
	
	
}
