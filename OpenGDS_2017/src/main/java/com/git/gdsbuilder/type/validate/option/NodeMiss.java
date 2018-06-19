/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

import java.util.ArrayList;
import java.util.List;

/**
 * NodeMiss(노드오류) 정보를 담고 있는 클래스
 * 
 * @author JY.Kim
 * @date 2017. 6. 21. 오후 8:06:33
 * 
 */
public class NodeMiss extends ValidatorOption {

	/**
	 * 관계 레이어 별칭 목록
	 */
	List<String> relationType;

	public enum Type {

		NODEMISS("NodeMiss", "GeometricError");

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
	 * NodeMiss 생성자
	 * 
	 * @param relationType
	 *            관계 레이어 별칭
	 */
	public NodeMiss(List<String> relationType) {
		super();
		this.relationType = relationType;
	}

	/**
	 * NodeMiss 생성자
	 */
	public NodeMiss() {
		super();
		this.relationType = new ArrayList<String>();
	}

	/**
	 * 관계 레이어 별칭 목록 반환
	 * 
	 * @return
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
