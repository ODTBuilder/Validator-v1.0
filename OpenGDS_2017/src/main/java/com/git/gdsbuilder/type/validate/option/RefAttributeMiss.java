package com.git.gdsbuilder.type.validate.option;

import java.util.HashMap;
import java.util.List;

/** 
* @ClassName: RefAttributeMiss 
* @Description: RefAttributeMiss 정보를 담고 있는 클래스
* @author JY.Kim 
* @date 2017. 8. 16. 오후 2:06:42 
*  
*/
public class RefAttributeMiss extends ValidatorOption {
	
	HashMap<String,List<String>> refAttributeMaissOpts;
	
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
	
	public RefAttributeMiss(HashMap<String,List<String>> refAttributeMaissOpts) {
		super();
		this.refAttributeMaissOpts = refAttributeMaissOpts;
	}
	

	public HashMap<String,List<String>> getRefAttributeMaissOpts() {
		return refAttributeMaissOpts;
	}

	public void setRefAttributeMaissOpts(HashMap<String,List<String>> refAttributeMaissOpts) {
		this.refAttributeMaissOpts = refAttributeMaissOpts;
	}
	
	public List<String> getRefAttributeMaissOpt(String layerName) {
		List<String> colunms = null;
		if(!layerName.isEmpty()&&!layerName.equals("")){
			colunms = this.refAttributeMaissOpts.get(layerName);
		}
		return colunms;
	}

}
