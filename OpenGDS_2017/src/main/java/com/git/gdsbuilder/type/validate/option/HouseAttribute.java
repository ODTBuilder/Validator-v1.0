/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

/** 
 * @ClassName: HouseAttribute 
 * @Description: NeatLineAttribute 정보를 담고 있는 클래스
 * @author JY.Kim 
 * @date 2017. 8. 1. 오전 10:18:03 
 *  
 */
public class HouseAttribute extends ValidatorOption{

	public enum Type{

		HOUSEATTRIBUTE("HouseAttribute", "AttributeError");

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

}
