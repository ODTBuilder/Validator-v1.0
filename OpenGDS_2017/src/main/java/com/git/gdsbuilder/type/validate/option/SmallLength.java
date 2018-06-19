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

/**
 * SmallLength(허용면적 이하 길이) 정보를 담고 있는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:09:34
 */
public class SmallLength extends ValidatorOption {

	/**
	 * 허용면적 길이
	 */
	double length;

	public enum Type {

		SMALLLENGTH("SmallLength", "GeometricError");

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
	};

	/**
	 * SmallLength 생성자
	 * 
	 * @param length
	 *            허용면적 길이
	 */
	public SmallLength(double length) {
		super();
		this.length = length;
	}

	/**
	 * 허용면적 길이 반환
	 * 
	 * @return double
	 */
	public double getLength() {
		return length;
	}

	/**
	 * 허용면적 길이 설정
	 * 
	 * @param length
	 *            허용면적 길이
	 */
	public void setLength(double length) {
		this.length = length;
	}

}
