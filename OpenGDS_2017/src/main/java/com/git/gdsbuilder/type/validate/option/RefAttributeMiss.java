package com.git.gdsbuilder.type.validate.option;

import java.util.ArrayList;
import java.util.List;

public class RefAttributeMiss extends ValidatorOption {
	
	List<String> colunms = new ArrayList<String>();
	
	public enum Type {

		
		
		RefAttributeMiss("RefAttributeMiss", "CloseCollectionError");

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
	
	public RefAttributeMiss(List<String> colunms) {
		super();
		this.colunms = colunms;
	}
	

	public List<String> getColunms() {
		return colunms;
	}

	public void setColunms(List<String> colunms) {
		this.colunms = colunms;
	}

}
