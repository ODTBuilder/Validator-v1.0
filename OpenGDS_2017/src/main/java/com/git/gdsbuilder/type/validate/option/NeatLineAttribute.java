/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

/** 
 * @ClassName: NeatLineAttribute 
 * @Description: NeatLineAttribute 정보를 담고 있는 클래스
 * @author JY.Kim 
 * @date 2017. 8. 4. 오후 3:37:05 
 *  
 */
public class NeatLineAttribute extends ValidatorOption {

	public enum Type{

		NEATLINEATTRIBUTE("NeatLineAttribute", "GeometricError");

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
