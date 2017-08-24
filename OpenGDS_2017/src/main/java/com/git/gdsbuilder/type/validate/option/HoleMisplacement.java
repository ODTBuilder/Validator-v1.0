/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

/** 
* @ClassName: HoleMisplacement 
* @Description: 
* @author JY.Kim 
* @date 2017. 8. 18. 오후 4:56:11 
*  
*/
public class HoleMisplacement extends ValidatorOption{
	public enum Type{

		HOLEMISPLACEMENT("HoleMisplacement", "GeometricError");

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
