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

import java.util.List;

/**
 * OutBoundary 정보를 담고 있는 클래스
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:09:34
 * */
public class OutBoundary extends ValidatorOption {

	List<String> relationType;

	/**
	 * OutBoundary 타입 정보를 담고 있는 클래스
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:10:55
	 * */
	public enum Type {

		OUTBOUNDARY("OutBoundary", "GeometricError");

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
	 * OutBoundary 생성자
	 * @param relationType
	 */
	public OutBoundary(List<String> relationType) {
		super();
		this.relationType = relationType;
	}

	/**
	 * relationType getter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:13:55
	 * @return List<String>
	 * @throws
	 * */
	public List<String> getRelationType() {
		return relationType;
	}

	/**
	 * relationType setter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:13:57
	 * @param relationType void
	 * @throws
	 * */
	public void setRelationType(List<String> relationType) {
		this.relationType = relationType;
	}

	/**
	 * relationType에 layerTypeName를 더함
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:13:59
	 * @param layerTypeName void
	 * @throws
	 * */
	public void addRelationLayerType(String layerTypeName) {
		relationType.add(layerTypeName);
	}

}
