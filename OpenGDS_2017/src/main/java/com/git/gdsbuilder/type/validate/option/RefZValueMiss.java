package com.git.gdsbuilder.type.validate.option;

import java.util.HashMap;
import java.util.List;

public class RefZValueMiss extends ValidatorOption {

	HashMap<String, List<String>> refZValueMissOpts;

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

	public RefZValueMiss(HashMap<String, List<String>> refZValueMissOpts) {
		super();
		this.refZValueMissOpts = refZValueMissOpts;
	}

	public HashMap<String, List<String>> getRefZValueMissOpts() {
		return refZValueMissOpts;
	}

	public void setRefZValueMissOpts(HashMap<String, List<String>> refZValueMissOpts) {
		this.refZValueMissOpts = refZValueMissOpts;
	}

}
