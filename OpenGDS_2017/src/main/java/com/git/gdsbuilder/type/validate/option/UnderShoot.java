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
 * UnderShoot 정보를 담고 있는 클래스
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:09:34
 * */
public class UnderShoot extends ValidatorOption {

	String boundaryName;
	double tolerence;

	/**
	 * UnderShoot 타입 정보를 담고 있는 클래스
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:10:55
	 * */
	public enum Type {

		UNDERSHOOT("UnderShoot", "GeometricError");
		String errName;
		String errType;

		/**
		 * Type 생성자
		 * @param errName
		 * @param errType
		 */
		Type(String errName, String errType) {
			this.errName = errName;
			this.errType = errType;
		}

		/**
		 * errName getter
		 * @author DY.Oh
		 * @Date 2017. 4. 18. 오후 3:09:38
		 * @return String
		 * @throws
		 * */
		public String errName() {
			return errName;
		}

		/**
		 * errType getter
		 * @author DY.Oh
		 * @Date 2017. 4. 18. 오후 3:09:40
		 * @return String
		 * @throws
		 * */
		public String errType() {
			return errType;
		}
	}
	
	/**
	 * UnderShoot 생성자
	 * @param boundaryName
	 * @param tolerence
	 */
	public UnderShoot(String boundaryName, double tolerence) {
		super();
		this.boundaryName = boundaryName;
		this.tolerence = tolerence;
	}

	/**
	 * tolorence getter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:23:29
	 * @return double
	 * @throws
	 * */
	public double getTolerence() {
		return tolerence;
	}

	/**
	 * tolorence setter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:23:31
	 * @param tolerence void
	 * @throws
	 * */
	public void setTolerence(double tolerence) {
		this.tolerence = tolerence;
	}

	/**
	 * boundaryName getter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:23:33
	 * @return String
	 * @throws
	 * */
	public String getBoundaryName() {
		return boundaryName;
	}

	/**
	 * boundaryName setter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:23:34
	 * @param boundaryName void
	 * @throws
	 * */
	public void setBoundaryName(String boundaryName) {
		this.boundaryName = boundaryName;
	}

}
