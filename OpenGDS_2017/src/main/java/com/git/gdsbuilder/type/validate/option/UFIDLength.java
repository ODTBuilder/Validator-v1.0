/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

/** 
 * @ClassName: UFIDLength 
 * @Description: UFIDLength 정보를 담고 있는 클래스
 * @author JY.Kim 
 * @date 2017. 8. 1. 오후 5:03:10 
 *  
 */
public class UFIDLength extends ValidatorOption{
	
	double length;

	/**
	 * SmallLength 타입 정보를 담고 있는 클래스
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:10:55
	 * */
	public enum Type {

		UFIDLENGTH("UFIDLength", "AttributeError");

		String errName;
		String errType;

		/**
		 * Type 생성자
		 * @param errName
		 * @param errType
		 */
		Type(String errName, String errType) {
			this.errName = errName;
			this.errType = errType;
		}

		/**
		 * errName getter
		 * @author DY.Oh
		 * @Date 2017. 4. 18. 오후 3:09:38
		 * @return String
		 * @throws
		 * */
		public String errName() {
			return errName;
		}

		/**
		 * errType getter
		 * @author DY.Oh
		 * @Date 2017. 4. 18. 오후 3:09:40
		 * @return String
		 * @throws
		 * */
		public String errType() {
			return errType;
		}
	};

	/**
	 * SmallLength 생성자
	 * @param length
	 */
	public UFIDLength(double length) {
		super();
		this.length = length;
	}

	/**
	 * length getter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:20:21
	 * @return double
	 * @throws
	 * */
	public double getLength() {
		return length;
	}

	/**
	 * length setter
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:20:23
	 * @param length void
	 * @throws
	 * */
	public void setLength(double length) {
		this.length = length;
	}
}
