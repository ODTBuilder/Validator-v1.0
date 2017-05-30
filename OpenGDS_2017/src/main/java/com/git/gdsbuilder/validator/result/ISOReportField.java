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

package com.git.gdsbuilder.validator.result;

import org.json.JSONObject;

/**
 * ISOReportField 정보를 담고 있는 클래스
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:56:43
 * */
public class ISOReportField {

	String layerType;
	double numOfItem;
	double numOfErrItem;
	String ratioOferrItem;
	double accuracyValue;
	double weight;
	double weightedValue;

	/**
	 * ISOReportField 생성자
	 */
	public ISOReportField() {

	}
	
	/**
	 * ISOReportField 생성자
	 * @param layerType
	 * @param numOfFeature
	 * @param numOfErrFeature
	 * @param weight
	 */
	public ISOReportField(String layerType, double numOfFeature, double numOfErrFeature, double weight) {
		super();
		this.layerType = layerType;
		this.numOfItem = numOfFeature;
		this.numOfErrItem = numOfErrFeature;
		this.ratioOferrItem = ratioOfErr();
		this.accuracyValue = accuracy();
		this.weight = weight;
		this.weightedValue = weightedValue();
	}

	public String getLayerType() {
		return layerType;
	}

	public void setLayerType(String layerType) {
		this.layerType = layerType;
	}

	public double getNumOfItem() {
		return numOfItem;
	}

	public void setNumOfItem(double numOfItem) {
		this.numOfItem = numOfItem;
	}

	public double getNumOfErrItem() {
		return numOfErrItem;
	}

	public void setNumOfErrItem(double numOfErrItem) {
		this.numOfErrItem = numOfErrItem;
	}

	public String getRatioOferrItem() {
		return ratioOferrItem;
	}

	public void setRatioOferrItem(String ratioOferrItem) {
		this.ratioOferrItem = ratioOferrItem;
	}

	public double getAccuracyValue() {
		return accuracyValue;
	}

	public void setAccuracyValue(double accuracyValue) {
		this.accuracyValue = accuracyValue;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getWeightedValue() {
		return weightedValue;
	}

	public void setWeightedValue(double weightedValue) {
		this.weightedValue = weightedValue;
	}

	/**
	 * ISOReport 생성
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:57:13 void
	 * @throws
	 * */
	public void createISOReport() {

		this.ratioOferrItem = ratioOfErr();
		this.accuracyValue = accuracy();
		this.weightedValue = weightedValue();
	}

	/**
	 * ratioOfErr 계산
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:57:16
	 * @return String
	 * @throws
	 * */
	private String ratioOfErr() {

		return (int) this.numOfErrItem + "/" + (int) this.numOfItem;
	}

	/**
	 * accuracy 계산
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:57:18
	 * @return double
	 * @throws
	 * */
	private double accuracy() {

		double accuracy = 1.0 - (this.numOfErrItem / this.numOfItem);

		return Double.parseDouble(String.format("%.3f", accuracy));
	}

	/**
	 * weightedValue 계산
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:57:19
	 * @return double
	 * @throws
	 * */
	private double weightedValue() {

		double weightedValue = this.accuracyValue * (this.weight / 100);

		return Double.parseDouble(String.format("%.3f", weightedValue));
	}

	/**
	 * ISOReportField 객체를 JSONObject로 파싱
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:57:22
	 * @return JSONObject
	 * @throws
	 * */
	public JSONObject parseJSON() {

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("layerType", layerType);
		jsonObj.put("numOfItem", numOfItem);
		jsonObj.put("numOfErrItem", numOfErrItem);
		jsonObj.put("ratioOferrItem", ratioOferrItem);
		jsonObj.put("accuracyValue", accuracyValue);
		jsonObj.put("weight", weight);
		jsonObj.put("weightedValue", weightedValue);

		return jsonObj;
	}

}
