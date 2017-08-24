/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

/** 
* @ClassName: UFIDRule 
* @Description: UFIDRule 정보를 담고 있는 클래스
* @author JY.Kim 
* @date 2017. 8. 11. 오후 3:37:23 
*  
*/
public class UFIDRule extends ValidatorOption{
	public enum Type {

		UFIDRULE("UFIDRule", "AttributeError");

		String errName;
		String errType;

		/**
		 * Type 생성자
		 * @param errName
		 * @param errType
		 */
		Type(String errName, String errType) {
			this.errName = errName;
			this.errType = errType;
		}

		public String errName() {
			return errName;
		
		}
	
		public String errType() {
			return errType;
		}
	};
}
