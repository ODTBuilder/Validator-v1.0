package com.git.gdsbuilder.type.validate.option;

import java.util.HashMap;

public class CharacterTypeMiss extends ValidatorOption {

	HashMap<String, Object> attributeType;

	public enum Type {

		CHARACTERTYPEMISS("CharacterTypeMiss", "AttributeError");

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

	public CharacterTypeMiss(HashMap<String, Object> attributeKey) {
		super();
		this.attributeType = attributeKey;
	}

	public CharacterTypeMiss() {
		super();
		this.attributeType = new HashMap<String, Object>();
	}

	public HashMap<String, Object> getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(HashMap<String, Object> attributeType) {
		this.attributeType = attributeType;
	}
}
