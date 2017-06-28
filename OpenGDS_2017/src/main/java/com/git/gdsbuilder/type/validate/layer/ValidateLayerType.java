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

package com.git.gdsbuilder.type.validate.layer;

import java.util.List;

import com.git.gdsbuilder.type.validate.option.ValidatorOption;

/**
 * ValidateLayerType 정보를 담고 있는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 3:02:56
 */
public class ValidateLayerType {

	String typeName;
	List<String> layerIDList;
	double weight;
	List<ValidatorOption> optionList;

	/**
	 * ValidateLayerType 생성자
	 */
	public ValidateLayerType() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * ValidateLayerType 생성자
	 * 
	 * @param typeName
	 * @param layerIDList
	 * @param weight
	 */
	public ValidateLayerType(String typeName, List<String> layerIDList, double weight) {
		super();
		this.typeName = typeName;
		this.layerIDList = layerIDList;
		this.weight = weight;
	}

	/**
	 * ValidateLayerType 생성자
	 * 
	 * @param typeName
	 * @param layerIDList
	 * @param optionList
	 */
	public ValidateLayerType(String typeName, List<String> layerIDList, List<ValidatorOption> optionList) {
		super();
		this.typeName = typeName;
		this.layerIDList = layerIDList;
		this.optionList = optionList;
	}

	/**
	 * typeName getter @author DY.Oh @Date 2017. 3. 11. 오후 3:03:08 @return
	 * String @throws
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * typeName setter @author DY.Oh @Date 2017. 3. 11. 오후 3:03:10 @param
	 * typeName void @throws
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * weight getter @author DY.Oh @Date 2017. 3. 11. 오후 3:03:12 @return
	 * double @throws
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * weight setter @author DY.Oh @Date 2017. 3. 11. 오후 3:03:15 @param weight
	 * void @throws
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * layerIDList getter @author DY.Oh @Date 2017. 3. 11. 오후 3:03:16 @return
	 * List<String> @throws
	 */
	public List<String> getLayerIDList() {
		return layerIDList;
	}

	/**
	 * layerIDList setter @author DY.Oh @Date 2017. 3. 11. 오후 3:03:18 @param
	 * layerIDList void @throws
	 */
	public void setLayerIDList(List<String> layerIDList) {
		this.layerIDList = layerIDList;
	}

	/**
	 * optionList getter @author DY.Oh @Date 2017. 3. 11. 오후 3:03:20 @return
	 * List<ValidatorOption> @throws
	 */
	public List<ValidatorOption> getOptionList() {
		return optionList;
	}

	/**
	 * optionList setter @author DY.Oh @Date 2017. 3. 11. 오후 3:03:21 @param
	 * optionList void @throws
	 */
	public void setOptionList(List<ValidatorOption> optionList) {
		this.optionList = optionList;
	}

}
