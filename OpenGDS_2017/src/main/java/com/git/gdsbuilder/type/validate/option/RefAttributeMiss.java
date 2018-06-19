package com.git.gdsbuilder.type.validate.option;

import java.util.HashMap;
import java.util.List;

/**
 * RefAttributeMiss(인접속성오류) 정보를 담고 있는 클래스
 * 
 * @author JY.Kim
 * @date 2017. 8. 16. 오후 2:06:42
 * 
 */
public class RefAttributeMiss extends ValidatorOption {

	/**
	 * 인접속성 오류 검수 시 필요한 속성 정보
	 */
	HashMap<String, List<String>> refAttributeMaissOpts;

	public enum Type {

		RefAttributeMiss("RefAttributeMiss", "CloseCollectionError");

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

	/**
	 * RefAttributeMiss 생성자
	 * 
	 * @param refAttributeMaissOpts
	 *            인접속성 오류 검수 시 필요한 속성 정보
	 */
	public RefAttributeMiss(HashMap<String, List<String>> refAttributeMaissOpts) {
		super();
		this.refAttributeMaissOpts = refAttributeMaissOpts;
	}

	/**
	 * 인접속성 오류 검수 시 필요한 속성 정보 반환
	 * 
	 * @return HashMap<String, List<String>>
	 */
	public HashMap<String, List<String>> getRefAttributeMaissOpts() {
		return refAttributeMaissOpts;
	}

	/**
	 * 인접속성 오류 검수 시 필요한 속성 정보 설정
	 * 
	 * @param refAttributeMaissOpts
	 *            인접속성 오류 검수 시 필요한 속성 정보
	 */
	public void setRefAttributeMaissOpts(HashMap<String, List<String>> refAttributeMaissOpts) {
		this.refAttributeMaissOpts = refAttributeMaissOpts;
	}

	/**
	 * layerName에 해당하는 인접속성 오류 검수 속성정보 반환
	 * 
	 * @param layerName
	 *            레이어 명
	 * @return List<String>
	 */
	public List<String> getRefAttributeMaissOpt(String layerName) {
		List<String> colunms = null;
		if (!layerName.isEmpty() && !layerName.equals("")) {
			colunms = this.refAttributeMaissOpts.get(layerName);
		}
		return colunms;
	}

}
