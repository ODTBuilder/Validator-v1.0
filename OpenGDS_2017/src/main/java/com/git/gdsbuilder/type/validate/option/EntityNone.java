/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

/**
 * EdgeMatchMiss(인접요소 오류) 정보를 담고 있는 클래스
 * 
 * @author JY.Kim
 * @date 2017. 6. 13. 오후 4:56:22
 * 
 */
public class EntityNone extends ValidatorOption {
	public enum Type {

		ENTITYNONE("RefEntityMiss", "CloseCollectionError");

		String errName;
		String errType;

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
	}
}
