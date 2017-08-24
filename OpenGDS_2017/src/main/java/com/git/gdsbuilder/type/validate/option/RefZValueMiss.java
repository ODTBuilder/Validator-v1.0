package com.git.gdsbuilder.type.validate.option;

import java.util.HashMap;
import java.util.List;

/** 
* @ClassName: RefZValueMiss 
* @Description: RefZValueMiss 정보를 담고 있는 클래스
* @author JY.Kim 
* @date 2017. 8. 11. 오후 3:37:23 
*  
*/
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
