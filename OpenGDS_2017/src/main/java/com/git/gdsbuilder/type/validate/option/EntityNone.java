/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

/** 
* @ClassName: EntityNone 
* @Description: EntityNone 정보를 담고 있는 클래스
* @author JY.Kim 
* @date 2017. 6. 13. 오후 4:56:22 
*  
*/
public class EntityNone extends ValidatorOption{
	public enum Type{

		ENTITYNONE("RefEntityMiss", "CloseCollectionError");

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
