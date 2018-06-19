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
 * OutBoundary(경계초과오류) 정보를 담고 있는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:09:34
 */
public class OutBoundary extends ValidatorOption {
	/**
	 * 관계 레이어 별칭 목록
	 */
	List<String> relationType;

	public enum Type {

		OUTBOUNDARY("OutBoundary", "GeometricError");

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
	 * OutBoundary 생성자
	 * 
	 * @param relationType
	 *            관계 레이어 별칭 목록
	 */
	public OutBoundary(List<String> relationType) {
		super();
		this.relationType = relationType;
	}

	/**
	 * 관계 레이어 별칭 목록 반환
	 * 
	 * @return List<String>
	 */
	public List<String> getRelationType() {
		return relationType;
	}

	/**
	 * 관계 레이어 별칭 목록 설정
	 * 
	 * @param relationType
	 *            관계 레이어 별칭 목록
	 */
	public void setRelationType(List<String> relationType) {
		this.relationType = relationType;
	}

	/**
	 * 관계 레이어 별칭 추가
	 * 
	 * @param layerTypeName
	 *            관계 레이어 별칭
	 */
	public void addRelationLayerType(String layerTypeName) {
		relationType.add(layerTypeName);
	}
}
