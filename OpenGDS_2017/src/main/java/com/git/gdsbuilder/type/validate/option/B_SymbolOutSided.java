/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

import java.util.ArrayList;
import java.util.List;

/** 
 * @ClassName: B_SymbolOutSided 
 * @Description: 
 * @author JY.Kim 
 * @date 2017. 6. 8. 오전 9:55:47 
 *  
 */
public class B_SymbolOutSided extends ValidatorOption{
	
	List<String> relationType;
	
	public enum Type{

		B_SYMBOLOUTSIDED("B_SymbolOutSided", "GeometricError");

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
	public B_SymbolOutSided(List<String> relationType) {
		super();
		this.relationType = relationType;
	}
	
	public B_SymbolOutSided(){
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
