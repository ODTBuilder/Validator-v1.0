package com.git.gdsbuilder.type.validate.option;

public class UFIDDuplicated extends ValidatorOption {
	
	public enum Type {

		UFIDDUPLICATED("UFIDDuplicated", "AttributeError");

		String errName;
		String errType;

		/**
		 * Type 생성자
		 * 
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