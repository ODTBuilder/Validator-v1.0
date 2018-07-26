/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

/** 
 * @ClassName: TwistedPolygon 
 * @Description: TwistedPolygon 정보를 담고 있는 클래스
 * @author JY.Kim 
 * @date 2017. 6. 19. 오후 8:07:33 
 *  
 */
public class TwistedPolygon extends ValidatorOption{
	public enum Type{

		TWISTEDPOLYGON("TwistedPolygon", "GeometricError");

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
