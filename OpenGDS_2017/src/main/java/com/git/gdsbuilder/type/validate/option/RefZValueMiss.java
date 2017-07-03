package com.git.gdsbuilder.type.validate.option;

public class RefZValueMiss extends ValidatorOption {
	
	String attributeKey;

	public enum Type {

		REFZVALUEMISS("RefZValueMiss", "CloseCollectionError");

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
	
	

	public RefZValueMiss(String attributeKey) {
		super();
		this.attributeKey = attributeKey;
	}

	public String getAttributeKey() {
		return attributeKey;
	}

	public void setAttributeKey(String attributeKey) {
		this.attributeKey = attributeKey;
	}
}
