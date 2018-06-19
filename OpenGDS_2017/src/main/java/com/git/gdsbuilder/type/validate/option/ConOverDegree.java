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
 * ConOverDegree(등고선 꺾임 오류) 정보를 담고 있는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:09:34
 */
public class ConOverDegree extends ValidatorOption {

	/**
	 * 등고선 꺾임 허용 각도
	 */
	double degree;

	public enum Type {

		CONOVERDEGREE("ConOverDegree", "GeometricError");

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
	 * ConOverDegree 생성자
	 * 
	 * @param degree
	 *            등고선 꺾임 허용 각도
	 */
	public ConOverDegree(double degree) {
		super();
		this.degree = degree;
	}

	/**
	 * 등고선 꺾임 허용 각도 반환
	 * 
	 * @return double
	 */
	public double getDegree() {
		return degree;
	}

	/**
	 * 등고선 꺾임 각도 설정
	 * 
	 * @param degree
	 *            등고선 꺾임 허용 각도
	 */
	public void setDegree(double degree) {
		this.degree = degree;
	}

}
