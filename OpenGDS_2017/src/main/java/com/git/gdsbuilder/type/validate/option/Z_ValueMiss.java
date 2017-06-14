/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

/** 
 * @ClassName: Z_ValueMiss 
 * @Description: Z_ValueMiss 정보를 담고 있는 클래스
 * @author JY.Kim 
 * @date 2017. 6. 13. 오후 4:54:10 
 *  
 */
public class Z_ValueMiss extends ValidatorOption{
	public enum Type{

		Z_VALUEMISS("Z_ValueMiss", "GeometricError");

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
