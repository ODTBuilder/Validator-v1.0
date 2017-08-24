/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

import java.util.ArrayList;
import java.util.List;

/** 
* @ClassName: CenterLineMiss 
* @Description: CenterLineMiss 정보를 담고 있는 클래스
* @author JY.Kim 
* @date 2017. 8. 18. 오전 9:46:25 
*  
*/
public class CenterLineMiss extends ValidatorOption{
	List<String> relationType;
	
	public enum Type{

		CENTERLINEMISS("CenterLineMiss", "GeometricError");

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
	 * @param relationType
	 */
	public CenterLineMiss(List<String> relationType) {
		super();
		this.relationType = relationType;
	}
	
	public CenterLineMiss(){
		super();
		this.relationType = new ArrayList<String>();
	}

	/**
	 * @return the relationType
	 */
	public List<String> getRelationType() {
		return relationType;
	}

	/**
	 * @param relationType the relationType to set
	 */
	public void setRelationType(List<String> relationType) {
		this.relationType = relationType;
	}
	
	public void addRelationLayerType(String layerTypeName){
		relationType.add(layerTypeName);
	}
}
