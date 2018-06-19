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
 * OverShoot(기준점 초과 오류) 정보를 담고 있는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:09:34
 */
public class OverShoot extends ValidatorOption {

	/**
	 * 검수 영역 레이어 별칭
	 */
	String boundaryName;
	/**
	 * tolerance
	 */
	double tolerence;

	public enum Type {

		OVERSHOOT("OverShoot", "GeometricError");
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
	 * OverShoot 생성자
	 * 
	 * @param boundaryName
	 *            검수 영역 레이어 별칭
	 * @param tolerance
	 *            tolerance
	 */
	public OverShoot(String boundaryName, double tolerance) {
		super();
		this.boundaryName = boundaryName;
		this.tolerence = tolerance;
	}

	/**
	 * tolerance 반환
	 * 
	 * @return double
	 */
	public double getTolerence() {
		return tolerence;
	}

	/**
	 * tolerance 설정
	 * 
	 * @param tolerence
	 *            tolerance
	 */
	public void setTolerence(double tolerence) {
		this.tolerence = tolerence;
	}

	/**
	 * 검수 영역 레이어 별칭 반환
	 * 
	 * @return String
	 */
	public String getBoundaryName() {
		return boundaryName;
	}

	/**
	 * 검수 영역 레이어 별칭 설정
	 * 
	 * @param boundaryName
	 *            검수 영역 레이어 별칭
	 */
	public void setBoundaryName(String boundaryName) {
		this.boundaryName = boundaryName;
	}

}
