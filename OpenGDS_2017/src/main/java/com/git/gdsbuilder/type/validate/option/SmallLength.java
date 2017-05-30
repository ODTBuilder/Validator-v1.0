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
 * SmallLength 정보를 담고 있는 클래스
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:09:34
 * */
public class SmallLength extends ValidatorOption {

	double length;

	/**
	 * SmallLength 타입 정보를 담고 있는 클래스
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:10:55
	 * */
	public enum Type {

		SMALLLENGTH("SmallLength", "GeometricError");

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
	};

	/**
	 * SmallLength 생성자
	 * @param length
	 */
	public SmallLength(double length) {
		super();
		this.length = length;
	}

	/**
	 * length getter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:20:21
	 * @return double
	 * @throws
	 * */
	public double getLength() {
		return length;
	}

	/**
	 * length setter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:20:23
	 * @param length void
	 * @throws
	 * */
	public void setLength(double length) {
		this.length = length;
	}

}
