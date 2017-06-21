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
import org.json.simple.JSONObject;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.type.validate.error.ErrorLayer;

/**
 * SimpleFeatureCollection를 속성 검수하는 클래스
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:32:00
 * */
public interface FeatureAttributeValidator {

	/**
	 * 검수 항목 중 “필수 속성 오류(Attribute Fix)” 검수
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:32:33
	 * @param validatorLayer
	 * @param notNullAtt
	 * @return
	 * @throws SchemaException ErrorLayer
	 * @throws
	 * */
	public ErrorFeature validateAttributeFix(SimpleFeature simpleFeature, JSONObject notNullAtt)
			throws SchemaException;

	/**
	 * 검수 항목 중 “고도값 오류 (Z-Value Abmiguous)” 검수
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:32:55
	 * @param simpleFeature
	 * @param notNullAtt
	 * @return
	 * @throws SchemaException ErrorFeature
	 * @throws
	 * */
	public ErrorFeature validateZvalueAmbiguous(SimpleFeature simpleFeature, String notNullAtt)
			throws SchemaException;

	/**
	 * 검수 항목 중 “문자의 정확성 오류 (character Accuracy)” 검수
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:33:07
	 * @param validatorLayer
	 * @param string
	 * @param relationLayer
	 * @param string2
	 * @return
	 * @throws SchemaException ErrorLayer
	 * @throws
	 * */
	public ErrorLayer validateCharacterAccuracy(SimpleFeatureCollection validatorLayer, String string,
			SimpleFeatureCollection relationLayer, String string2) throws SchemaException;
	
	public ErrorFeature validateBridgeName(SimpleFeature simpleFeature, SimpleFeature relationSimpleFeature) throws SchemaException;
	public ErrorFeature validateAdmin(SimpleFeature simpleFeature) throws SchemaException;
	
}
