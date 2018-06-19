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

package com.git.gdsbuilder.type.validate.result;

import org.json.JSONObject;

/**
 * ISOReportField 정보를 담고 있는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:56:43
 */
public class ISOReportField {

	/**
	 * 레이어 별칭
	 */
	String layerType;
	/**
	 * 피쳐 개수
	 */
	double numOfItem;
	/**
	 * 오류 피쳐 개수
	 */
	double numOfErrItem;
	/**
	 * 오류 피쳐 비율 (오류 피쳐 개수/피쳐 개수)
	 */
	String ratioOferrItem;
	/**
	 * 정확도 (오류 피쳐 비율 * 100)
	 */
	double accuracyValue;
	/**
	 * 가중치
	 */
	double weight;
	/**
	 * 가중값 (가중치 * 정확도)
	 */
	double weightedValue;

	/**
	 * ISOReportField 생성자
	 */
	public ISOReportField() {

	}

	/**
	 * ISOReportField 생성자
	 * 
	 * @param layerType
	 *            레이어 별칭
	 * @param numOfFeature
	 *            피쳐 개수
	 * @param numOfErrFeature
	 *            오류 피쳐 개수
	 * @param weight
	 *            가중치
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

	/**
	 * 레이어 별칭 반환
	 * 
	 * @return String
	 */
	public String getLayerType() {
		return layerType;
	}

	/**
	 * 레이어 별칭 설정
	 * 
	 * @param layerType
	 *            레이어 별칭
	 */
	public void setLayerType(String layerType) {
		this.layerType = layerType;
	}

	/**
	 * 피쳐 개수 반환
	 * 
	 * @return double
	 */
	public double getNumOfItem() {
		return numOfItem;
	}

	/**
	 * 피쳐 개수 설정
	 * 
	 * @param numOfItem
	 *            피쳐 개수
	 */
	public void setNumOfItem(double numOfItem) {
		this.numOfItem = numOfItem;
	}

	/**
	 * 오류 피쳐 개수 반환
	 * 
	 * @return double
	 */
	public double getNumOfErrItem() {
		return numOfErrItem;
	}

	/**
	 * 오류 피쳐 개수 설정
	 * 
	 * @param numOfErrItem
	 *            오류 피쳐 개수
	 */
	public void setNumOfErrItem(double numOfErrItem) {
		this.numOfErrItem = numOfErrItem;
	}

	/**
	 * 오류 피쳐 비율 반환
	 * 
	 * @return String
	 */
	public String getRatioOferrItem() {
		return ratioOferrItem;
	}

	/**
	 * 오류 피쳐 비율 설정
	 * 
	 * @param ratioOferrItem
	 *            오류 피쳐 비율
	 */
	public void setRatioOferrItem(String ratioOferrItem) {
		this.ratioOferrItem = ratioOferrItem;
	}

	/**
	 * 정확도 반환
	 * 
	 * @return double
	 */
	public double getAccuracyValue() {
		return accuracyValue;
	}

	/**
	 * 정확도 설정
	 * 
	 * @param accuracyValue
	 *            정확도
	 */
	public void setAccuracyValue(double accuracyValue) {
		this.accuracyValue = accuracyValue;
	}

	/**
	 * 가중치 반환
	 * 
	 * @return double
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * 가중치 설정
	 * 
	 * @param weight
	 *            가중치
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * 가중값 반환
	 * 
	 * @return
	 */
	public double getWeightedValue() {
		return weightedValue;
	}

	/**
	 * 가중값 설정
	 * 
	 * @param weightedValue
	 *            가중값
	 */
	public void setWeightedValue(double weightedValue) {
		this.weightedValue = weightedValue;
	}

	/**
	 * ISOReport 생성
	 */
	public void createISOReport() {

		this.ratioOferrItem = ratioOfErr();
		this.accuracyValue = accuracy();
		this.weightedValue = weightedValue();
	}

	/**
	 * ratioOfErr 계산
	 * 
	 * @return String
	 */
	private String ratioOfErr() {

		return (int) this.numOfErrItem + "/" + (int) this.numOfItem;
	}

	/**
	 * accuracy 계산
	 * 
	 * @return double
	 */
	private double accuracy() {

		double accuracy = 1.0 - (this.numOfErrItem / this.numOfItem);

		return Double.parseDouble(String.format("%.3f", accuracy));
	}

	/**
	 * 
	 * weightedValue 계산
	 * 
	 * @return double
	 */
	private double weightedValue() {

		double weightedValue = this.accuracyValue * (this.weight / 100);

		return Double.parseDouble(String.format("%.3f", weightedValue));
	}

	/**
	 * ISOReportField 객체를 JSONObject로 파싱
	 * 
	 * @return JSONObject
	 */
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
