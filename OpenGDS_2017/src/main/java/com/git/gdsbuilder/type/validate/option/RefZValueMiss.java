package com.git.gdsbuilder.type.validate.option;

import org.json.simple.JSONObject;

public class RefZValueMiss extends ValidatorOption {
	
	JSONObject refZValueMissOpts;
	
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
	
	public RefZValueMiss(JSONObject refZValueMissOpts) {
		super();
		this.refZValueMissOpts = refZValueMissOpts;
	}

	public JSONObject getRefZValueMissOpts() {
		return refZValueMissOpts;
	}

	public void setRefZValueMissOpts(JSONObject refZValueMissOpts) {
		this.refZValueMissOpts = refZValueMissOpts;
	}
	
}
