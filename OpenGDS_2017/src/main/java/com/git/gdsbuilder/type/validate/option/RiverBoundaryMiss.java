/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

import java.util.ArrayList;
import java.util.List;

/** 
* @ClassName: RiverBoundaryMiss 
* @Description: 
* @author JY.Kim 
* @date 2017. 8. 10. 오전 9:56:32 
*  
*/
public class RiverBoundaryMiss extends ValidatorOption{

	List<String> relationType;
	
	public enum Type{

		RIVERBOUNDARYMISS("RiverBoundaryMiss", "GeometricError");

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
	 * SelfEntity 생성자
	 * @param relationLayerID
	 */
	public RiverBoundaryMiss(List<String> relationLayerID) {
		super();
		this.relationType = relationLayerID;
	}

	/**
	 * SelfEntity 생성자
	 */
	public RiverBoundaryMiss() {
		super();
		this.relationType = new ArrayList<String>();
	}

	public List<String> getRelationType() {
		return relationType;
	}

	public void setRelationType(List<String> relationType) {
		this.relationType = relationType;
	}
	
	public void addRelationLayerType(String layerTypeName) {
		relationType.add(layerTypeName);
	}
	
}
