/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

/** 
* @ClassName: WaterOpen 
* @Description: 
* @author JY.Kim 
* @date 2017. 5. 30. 오후 1:57:50 
*  
*/
public class WaterOpen extends ValidatorOption{

	public enum Type{

		WATEROPEN("WaterOpen", "GeometricError");

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
