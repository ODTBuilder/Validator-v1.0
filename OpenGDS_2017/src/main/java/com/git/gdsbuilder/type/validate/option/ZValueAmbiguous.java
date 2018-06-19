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

/**
 * Z_ValueAmbiguous(고도값 오류) 정보를 담고 있는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:09:34
 */
public class ZValueAmbiguous extends ValidatorOption {

	/**
	 * 고도값 속성 컬럼명
	 */
	HashMap<String, Object> attributeKey;

	public enum Type {

		ZVALUEAMBIGUOUS("ZValueAmbiguous", "AttributeError");

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
	 * @param attributeKey
	 *            고도값 속성 컬럼명
	 */
	public ZValueAmbiguous(HashMap<String, Object> attributeKey) {
		super();
		this.attributeKey = attributeKey;
	}

	/**
	 * 
	 */
	public ZValueAmbiguous() {
		super();
		this.attributeKey = new HashMap<String, Object>();
	}

	/**
	 * 고도값 속성 컬럼명 반환
	 * 
	 * @return HashMap<String, Object>
	 */
	public HashMap<String, Object> getRelationType() {
		return attributeKey;
	}

	/**
	 * 고도값 속성 컬럼명 설정
	 * 
	 * @param attributeKey
	 *            고도값 속성 컬럼명
	 */
	public void setAttributeType(HashMap<String, Object> attributeKey) {
		this.attributeKey = attributeKey;
	}

	/**
	 * 검수 고도값 속성 추가
	 * 
	 * @param key
	 *            속성 컬럼명
	 * @param zValue
	 *            속성값
	 */
	public void addRelationLayerType(String key, String zValue) {
		attributeKey.put(key, zValue);
	}

}
