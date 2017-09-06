/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

public class MultiPart extends ValidatorOption {

	public enum Type {

		MULTIPART("MultiPart", "GeometricError");

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

}
