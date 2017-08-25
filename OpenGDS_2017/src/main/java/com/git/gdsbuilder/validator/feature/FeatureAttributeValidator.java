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

import java.util.List;
import java.util.Map;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.SchemaException;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.type.validate.error.ErrorLayer;

/**
 * SimpleFeatureCollection를 속성 검수하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:32:00
 */
public interface FeatureAttributeValidator {

	/**
	 * 검수 항목 중 “필수 속성 오류(Attribute Fix)” 검수 @author DY.Oh @Date 2017. 4. 18. 오후
	 * 3:32:33 @param validatorLayer @param notNullAtt @return @throws
	 * SchemaException ErrorLayer @throws
	 */
	public ErrorFeature validateAttributeFix(SimpleFeature simpleFeature, Map<String, List<String>> notNullAtt)
			throws SchemaException;

	/**
	 * 검수 항목 중 “고도값 오류 (Z-Value Abmiguous)” 검수 @author DY.Oh @Date 2017. 4. 18.
	 * 오후 3:32:55 @param simpleFeature @param notNullAtt @return @throws
	 * SchemaException ErrorFeature @throws
	 */
	public ErrorFeature validateZvalueAmbiguous(SimpleFeature simpleFeature,  Map<String, List<String>> attributeKey)
			throws SchemaException;

	/**
	 * 검수 항목 중 "교량명 오류(Bridge Name)" 검수
	 * @author JY.Kim
	 * @Date 2017. 8. 16. 오후 2:16:42
	 * @param simpleFeature
	 * @param relationSimpleFeature
	 * @return ErrorFeature
	 * @throws SchemaException ErrorFeature
	 * @throws
	 * */
	public ErrorFeature validateBridgeName(SimpleFeature simpleFeature, SimpleFeature relationSimpleFeature)
			throws SchemaException;
	/**
	 * 검수 항목 중 "행정경계 오류(Admin)" 검수
	 * @author JY.Kim
	 * @Date 2017. 8. 16. 오후 2:15:28
	 * @param simpleFeatureI
	 * @param simpleFeatureJ
	 * @return ErrorFeature
	 * @throws
	 * */
	public ErrorFeature validateAdmin(SimpleFeature simpleFeature) throws SchemaException;

	public ErrorFeature validateEntityDuplicated(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ);
	
	/**
	 * 검수 항목 중 "일반주택 주기값 오류(House Attribute)" 검수
	 * @author JY.Kim
	 * @Date 2017. 8. 16. 오후 2:14:56
	 * @param simpleFeature
	 * @return ErrorFeature
	 * */
	public ErrorFeature validateHouseAttribute(SimpleFeature simpleFeature);
	
	/**
	 * 검수 항목 중 "UFID 길이오류(UFID Length)" 검수
	 * @author JY.Kim
	 * @Date 2017. 8. 16. 오후 2:14:27
	 * @param simpleFeature
	 * @param length
	 * @return ErrorFeature
	 * @throws
	 * */
	public ErrorFeature validateUFIDLength(SimpleFeature simpleFeature, double length);
	
	/**
	 * 검수 항목 중 "도엽 속성 오류(NeatLine Attribute)" 검수
	 * @author JY.Kim
	 * @Date 2017. 8. 16. 오후 2:12:41
	 * @param simpleFeature
	 * @return ErrorFeature
	 * */
	public ErrorFeature validateNeatLineAttribute(SimpleFeature simpleFeature);
	
	/**
	 * 검수 항목 중 "수치값 오류(Numerical Value)" 검수
	 * @author JY.Kim
	 * @Date 2017. 8. 16. 오후 2:10:42
	 * @param simpleFeature
	 * @param attribute - 속성값
	 * @param condition - 조건
	 * @param figure  - 수치
	 * @return ErrorFeature
	 * */
	public ErrorFeature validateNumericalValue(SimpleFeature simpleFeature, String attribute, String condition, double figure);
	
	/**
	 * 검수 항목 중 "UFID 규칙 오류(UFID Rule)" 검수
	 * @author JY.Kim
	 * @Date 2017. 8. 16. 오후 2:09:41
	 * @param simpleFeature
	 * @return ErrorFeature
	 * */
	public ErrorFeature validateUFIDRule(SimpleFeature simpleFeature);
	
}
