/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

import java.util.ArrayList;
import java.util.List;

/**
 * LayerMiss(계층오류) 정보를 담고 있는 클래스
 * 
 * @author JY.Kim
 * @date 2017. 5. 30. 오후 1:58:27
 * 
 */
public class LayerMiss extends ValidatorOption {

	/**
	 * 계층 타입(Geometry 타입)
	 */
	List<String> layerType;

	public enum Type {

		LAYERMISS("LayerMiss", "GeometricError");

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
	 * LayerMiss 생성자
	 * 
	 * @param layerType
	 *            계층 타입(Geometry 타입)
	 */
	public LayerMiss(List<String> layerType) {
		super();
		this.layerType = layerType;
	}

	/**
	 * LayerMiss 생성자
	 */
	public LayerMiss() {
		super();
		this.layerType = new ArrayList<String>();
	}

	/**
	 * 계층 타입(Geometry 타입) 반환
	 * 
	 * @return List<String>
	 */
	public List<String> getLayerType() {
		return layerType;
	}

	/**
	 * 계층 타입 설정
	 * 
	 * @param layerType
	 *            계층 타입(Geometry 타입)
	 */
	public void setLayerType(List<String> layerType) {
		this.layerType = layerType;
	}

}
