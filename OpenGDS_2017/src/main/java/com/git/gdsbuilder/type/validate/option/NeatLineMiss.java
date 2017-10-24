package com.git.gdsbuilder.type.validate.option;

public class NeatLineMiss extends ValidatorOption {

	public enum Type {

		NEATLINEMISS("NeatLineMiss", "GeometricError");

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
