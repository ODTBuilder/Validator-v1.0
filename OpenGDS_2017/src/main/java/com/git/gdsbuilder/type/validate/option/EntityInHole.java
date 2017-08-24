/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

import java.util.ArrayList;
import java.util.List;

/** 
* @ClassName: EntityInHole 
* @Description: EntityInHole 정보를 담고 있는 클래스
* @author JY.Kim 
* @date 2017. 8. 21. 오후 4:45:27 
*  
*/
public class EntityInHole extends ValidatorOption{
	List<String> relationType;
	
	public enum Type{

		ENTITYINHOLE("EntityInHole", "GeometricError");

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
	public EntityInHole(List<String> relationType) {
		super();
		this.relationType = relationType;
	}
	
	public EntityInHole(){
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
