package com.git.gdsbuilder.type.validate.option;

import java.util.HashMap;
import java.util.List;

public class RefZValueMiss extends ValidatorOption {
	
	HashMap<String, String> refZValueMissOpts;
	
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
	
	public RefZValueMiss(HashMap<String, String> refZValueMissOpts) {
		super();
		this.refZValueMissOpts = refZValueMissOpts;
	}

	public HashMap<String, String> getRefZValueMissOpts() {
		return refZValueMissOpts;
	}
	
	public String getRefZValueMissOpt(String layerName) {
		String colunm = "";
		if(!layerName.isEmpty()&&!layerName.equals("")){
			colunm = this.refZValueMissOpts.get(layerName);
		}
		return colunm;
	}

	public void setRefZValueMissOpts(HashMap<String, String> refZValueMissOpts) {
		this.refZValueMissOpts = refZValueMissOpts;
	}
	
}
