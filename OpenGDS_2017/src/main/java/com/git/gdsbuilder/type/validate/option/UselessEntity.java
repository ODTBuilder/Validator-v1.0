/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

/** 
* @ClassName: UselessEntity 
* @Description: UselessEntity 정보를 담고 있는 클래스
* @author JY.Kim 
* @date 2017. 5. 30. 오후 1:56:22 
*  
*/
public class UselessEntity extends ValidatorOption {

	public enum Type{
		
		USELESSENTITY("UselessEntity", "GeometricError");

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
