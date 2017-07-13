package com.git.gdsbuilder.type.validate.option;

import java.util.List;

public class OneAcre extends ValidatorOption {

	List<String> relationType;

	public enum Type {

		ONEACRE("OneAcre", "GeometricError");

		String errName;
		String errType;

		/**
		 * Type 생성자
		 * 
		 * @param errName
		 * @param errType
		 */
		Type(String errName, String errType) {
			this.errName = errName;
			this.errType = errType;
		}

		/**
		 * errName getter @author DY.Oh @Date 2017. 4. 18. 오후 3:09:38 @return
		 * String @throws
		 */
		public String errName() {
			return errName;
		}

		/**
		 * errType getter @author DY.Oh @Date 2017. 4. 18. 오후 3:09:40 @return
		 * String @throws
		 */
		public String errType() {
			return errType;
		}
	}

	/**
	 * OutBoundary 생성자
	 * 
	 * @param relationType
	 */
	public OneAcre(List<String> relationType) {
		super();
		this.relationType = relationType;
	}

	/**
	 * relationType getter @author DY.Oh @Date 2017. 4. 18. 오후 3:13:55 @return
	 * List<String> @throws
	 */
	public List<String> getRelationType() {
		return relationType;
	}

	/**
	 * relationType setter @author DY.Oh @Date 2017. 4. 18. 오후 3:13:57 @param
	 * relationType void @throws
	 */
	public void setRelationType(List<String> relationType) {
		this.relationType = relationType;
	}

	/**
	 * relationType에 layerTypeName를 더함 @author DY.Oh @Date 2017. 4. 18. 오후
	 * 3:13:59 @param layerTypeName void @throws
	 */
	public void addRelationLayerType(String layerTypeName) {
		relationType.add(layerTypeName);
	}

}
