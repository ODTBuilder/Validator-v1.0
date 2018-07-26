/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

import java.util.ArrayList;
import java.util.List;

/** 
* @ClassName: LayerMiss 
* @Description: LayerMiss 정보를 담고 있는 클래스
* @author JY.Kim 
* @date 2017. 5. 30. 오후 1:58:27 
*  
*/
public class LayerMiss extends ValidatorOption{
	
	List<String> layerType;
	
	public enum Type{

		LAYERMISS("LayerMiss", "GeometricError");

		String errName;
		String errType;

		Type(String errName, String errType) {
			this.errName = errName;
			this.errType = errType;
		}

		/**
		 * @return the errName
		 */
		public String errName() {
			return errName;
		}

		/**
		 * @return the errType
		 */
		public String errType() {
			return errType;
		}
	}

	/**
	 * @param layerType
	 */
	public LayerMiss(List<String> layerType) {
		super();
		this.layerType = layerType;
	}
	
	/**
	 * SelfEntity 생성자
	 */
	public LayerMiss() {
		super();
		this.layerType = new ArrayList<String>();
	}
	

	/**
	 * @return the layerType
	 */
	public List<String> getLayerType() {
		return layerType;
	}

	/**
	 * @param layerType the layerType to set
	 */
	public void setLayerType(List<String> layerType) {
		this.layerType = layerType;
	}
	
	
	
}

