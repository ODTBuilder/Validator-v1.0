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

package com.git.gdsbuilder.type.validate.option;

import java.util.HashMap;

import org.json.simple.JSONObject;

/**
 * AttributeFix(속성오류) 검수항목 정보를 담고 있는 클래스
 * 
 * @author JY.Kim
 * @date 2017. 8. 16. 오후 2:08:58
 * 
 */
public class AttributeFix extends ValidatorOption {

	/**
	 * 속성 컬럼명, 속성값
	 */
	HashMap<String, Object> attributeKey;

	public enum Type {

		ATTRIBUTEFIX("AttributeFix", "AttributeError");

		String errName;
		String errType;

		Type(String errName, String errType) {
			this.errName = errName;
			this.errType = errType;
		}

		public String errName() {
			return errName;
		}

		public String errType() {
			return errType;
		}
	}

	/**
	 * AttributeFix 생성자
	 * 
	 * @param attributeKey
	 *            속성 컬럼값, 속성값
	 */
	public AttributeFix(HashMap<String, Object> attributeKey) {
		super();
		this.attributeKey = attributeKey;
	}

	/**
	 * AttributeFix 생성자
	 */
	public AttributeFix() {
		super();
		this.attributeKey = new HashMap<String, Object>();
	}

	/**
	 * 속성 반환
	 * 
	 * @return
	 */
	public HashMap<String, Object> getRelationType() {
		return attributeKey;
	}

	/**
	 * 속성 설정
	 * 
	 * @param attributeKey
	 *            속성 컬럼값, 속성값
	 */
	public void setAttributeType(HashMap<String, Object> attributeKey) {
		this.attributeKey = attributeKey;
	}

	/**
	 * 검수 대상 속성 추가
	 * 
	 * @param notNullAtt
	 *            속성 컬럼값
	 * @param attJsonObject
	 *            속성값
	 */
	public void addRelationLayerType(String notNullAtt, JSONObject attJsonObject) {
		attributeKey.put(notNullAtt, attJsonObject);
	}

}
