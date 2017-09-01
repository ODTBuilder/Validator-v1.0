/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

import java.util.ArrayList;
import java.util.List;

/** 
* @ClassName: LinearDisconnection 
* @Description: LinearDisconnection정보를 담고 있는 클래스
* @author JY.Kim 
* @date 2017. 8. 24. 오후 5:09:00 
*  
*/
public class LinearDisconnection extends ValidatorOption {
	List<String> relationType;
	
	public enum Type{

		LINEARDISCONNECTION("LinearDisconnection", "GeometricError");

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
	public LinearDisconnection(List<String> relationType) {
		super();
		this.relationType = relationType;
	}
	
	public LinearDisconnection(){
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
