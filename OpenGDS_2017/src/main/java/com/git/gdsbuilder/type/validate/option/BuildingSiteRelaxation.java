/**
 * 
 */
package com.git.gdsbuilder.type.validate.option;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;

/**
 * @ClassName: BuildingSite
 * @Description: BuildingSite 정보를 담고 있는 클래스
 * @author JY.Kim
 * @date 2017. 8. 3. 오전 11:43:00
 * 
 */
public class BuildingSiteRelaxation extends ValidatorOption {

	HashMap<String, Object> attributeKey;
	List<String> relationType;

	public enum Type {

		BUILDINGSITERELAXATION("BuildingSiteRelaxation", "GeometricError");

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

	/**
	 * @param relationType
	 */
	public BuildingSiteRelaxation(HashMap<String, Object> attributeKey, List<String> relationType) {
		super();
		this.attributeKey = attributeKey;
		this.relationType = relationType;
	}

	public BuildingSiteRelaxation() {
		super();
		this.attributeKey = new HashMap<String, Object>();
		this.relationType = new ArrayList<String>();
	}

	/**
	 * @return the attributeKey
	 */
	public HashMap<String, Object> getAttributeKey() {
		return attributeKey;
	}

	/**
	 * @param attributeKey
	 *            the attributeKey to set
	 */
	public void setAttributeKey(HashMap<String, Object> attributeKey) {
		this.attributeKey = attributeKey;
	}

	/**
	 * @return the relationType
	 */
	public List<String> getRelationType() {
		return relationType;
	}

	/**
	 * @param relationType
	 *            the relationType to set
	 */
	public void setRelationType(List<String> relationType) {
		this.relationType = relationType;
	}

	public void addRelationType(String relationTypeLayer) {
		relationType.add(relationTypeLayer);
	}

	public void addAttributeKey(String notNullAtt, JSONObject attJsonObject) {
		attributeKey.put(notNullAtt, attJsonObject);
	}

	/**
	 * @param relationType
	 *            the relationType to set
	 */
	public void setAttributeType(HashMap<String, Object> attributeKey, List<String> relationType) {
		this.attributeKey = attributeKey;
		this.relationType = relationType;
	}

}
