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
 * ValidateLayerType 정보를 담고 있는 클래스. 검수 옵션 정보를 가지고 있음.
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 3:02:56
 */
public class ValidateLayerType {

	/**
	 * 검수 레이어 별칭
	 */
	String typeName;
	/**
	 * 검수를 수행할 레이어 이름 목록
	 */
	List<String> layerIDList;
	/**
	 * 검수 가중치
	 */
	double weight;
	/**
	 * 검수 항목 목록
	 */
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
	 *            검수 레이어 별칭
	 * @param layerIDList
	 *            검수를 수행할 레이어 이름 목록
	 * @param weight
	 *            검수 가중치
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
	 *            검수 레이어 별칭
	 * @param layerIDList
	 *            검수를 수행할 레이어 이름 목록
	 * @param optionList
	 *            검수 항목 목록
	 */
	public ValidateLayerType(String typeName, List<String> layerIDList, List<ValidatorOption> optionList) {
		super();
		this.typeName = typeName;
		this.layerIDList = layerIDList;
		this.optionList = optionList;
	}

	/**
	 * 검수 레이어 별칭 반환
	 * 
	 * @return String
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * 검수 레이어 별칭 설정
	 * 
	 * @param typeName
	 *            검수 레이어 별칭
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * 검수 가중치 반환
	 * 
	 * @return double
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * 검수 가중치 설정
	 * 
	 * @param weight
	 *            검수 가중치
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * 검수를 수행할 레이어 이름 목록 반환
	 * 
	 * @return List<String>
	 */
	public List<String> getLayerIDList() {
		return layerIDList;
	}

	/**
	 * 검수를 수행할 레이어 이름 목록 설정
	 * 
	 * @param layerIDList
	 *            검수를 수행할 레이어 이름 목록
	 */
	public void setLayerIDList(List<String> layerIDList) {
		this.layerIDList = layerIDList;
	}

	/**
	 * 검수 항목 목록 반환
	 * 
	 * @return List<ValidatorOption>
	 */
	public List<ValidatorOption> getOptionList() {
		return optionList;
	}

	/**
	 * 검수 항목 목록 설정
	 * 
	 * @param optionList
	 *            검수 항목 목록
	 */
	public void setOptionList(List<ValidatorOption> optionList) {
		this.optionList = optionList;
	}

}
