/*
 *    OpenGDS/Builder
 *    http://git.co.kr
 *
 *    (C) 2014-2017, GeoSpatial Information Technology(GIT)
 *    
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 3 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */

package com.git.gdsbuilder.type.validate.option;

import java.util.HashMap;
import java.util.List;

public class AttributeFix extends ValidatorOption {

	HashMap<String, List<String>> attFixList = new HashMap<String, List<String>>();

	public enum Type{

		ATTRIBUTEFIX("AttributeFix", "AttributeError");

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
	public AttributeFix(HashMap<String, List<String>> attFixList) {
		super();
		this.attFixList = attFixList;
	}
	
	public AttributeFix(){
		super();
		this.attFixList = new HashMap<String, List<String>>();
	}

	/**
	 * @return the relationType
	 */
	public HashMap<String, List<String>> getAttributeFixList() {
		return attFixList;
	}

	/**
	 * @param relationType the relationType to set
	 */
	public void setAttributeFixList(HashMap<String, List<String>> attFixList) {
		this.attFixList = attFixList;
	}
}
