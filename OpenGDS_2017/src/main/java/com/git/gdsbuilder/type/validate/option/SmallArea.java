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
 * SmallArea(허용범위 이하 면적) 정보를 담고 있는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:09:34
 */
public class SmallArea extends ValidatorOption {

	/**
	 * 허용범위 면적
	 */
	double area;

	public enum Type {

		SMALLAREA("SmallArea", "GeometricError");

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
	 * SmallArea 생성자
	 * 
	 * @param area
	 *            허용범위 면적
	 */
	public SmallArea(double area) {
		super();
		this.area = area;
	}

	/**
	 * 허용범위 면적 반환
	 * 
	 * @return double
	 */
	public double getArea() {
		return area;
	}

	/**
	 * 허용범위 면적 설정
	 * 
	 * @param area
	 *            허용범위 면적
	 */
	public void setArea(double area) {
		this.area = area;
	}
}
