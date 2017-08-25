/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

import java.util.ArrayList;
import java.util.List;

/** 
* @ClassName: RiverBoundaryMiss 
* @Description: RiverBoundaryMiss 정보를 담고 있는 클래스
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

	public RiverBoundaryMiss(List<String> relationLayerID) {
		super();
		this.relationType = relationLayerID;
	}

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
