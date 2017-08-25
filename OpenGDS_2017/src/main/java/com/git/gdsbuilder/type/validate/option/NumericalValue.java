/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

/** 
* @ClassName: NumericalValue 
* @Description: NumericalValue 정보를 담고 있는 클래스
* @author JY.Kim 
* @date 2017. 8. 8. 오후 4:23:07 
*  
*/
public class NumericalValue extends ValidatorOption {
	String attribute;
	String condition;
	double figure;
	
	public enum Type {

		NUMERICALVALUE("NumericalValue", "AttributeError");

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
	}

	/**
	 * @return the attribute
	 */
	public String getAttribute() {
		return attribute;
	}

	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * @return the figure
	 */
	public double getFigure() {
		return figure;
	}

	/**
	 * @param figure the figure to set
	 */
	public void setFigure(double figure) {
		this.figure = figure;
	}

	/**
	 * @param attribute
	 * @param condition
	 * @param figure
	 */
	public NumericalValue(String attribute, String condition, double figure) {
		super();
		this.attribute = attribute;
		this.condition = condition;
		this.figure = figure;
	};
	
	
	
}
