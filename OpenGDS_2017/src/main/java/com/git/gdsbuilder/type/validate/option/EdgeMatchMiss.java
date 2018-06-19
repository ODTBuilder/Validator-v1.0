package com.git.gdsbuilder.type.validate.option;

/**
 * EdgeMatchMiss(인접요소 오류) 정보를 담고 있는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:09:34
 */
public class EdgeMatchMiss extends ValidatorOption {

	public enum Type {

		EDGEMATCHMISS("RefEntityMiss", "CloseCollectionError");

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
