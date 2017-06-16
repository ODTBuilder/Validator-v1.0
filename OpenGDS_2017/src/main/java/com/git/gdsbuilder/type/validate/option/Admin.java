/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

/** 
* @ClassName: Admin 
* @Description: 
* @author JY.Kim 
* @date 2017. 6. 16. 오후 4:16:32 
*  
*/
public class Admin extends ValidatorOption{
	public enum Type{

		ADMIN("Admin", "GeometricError");

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
