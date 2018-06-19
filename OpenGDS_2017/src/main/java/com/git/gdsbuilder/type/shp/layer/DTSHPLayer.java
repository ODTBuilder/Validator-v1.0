package com.git.gdsbuilder.type.shp.layer;

import org.geotools.data.simple.SimpleFeatureCollection;

/**
 * SHP 파일 레이어 정보를 저장하고 있는 클래스
 * 
 * @author DY.Oh
 *
 */
public class DTSHPLayer {

	/**
	 * SHP 파일 레이어 이름
	 */
	String layerName;
	/**
	 * SHP 파일 레이어 타입
	 */
	String layerType;
	/**
	 * SimpleFeatureCollection 객체
	 */
	SimpleFeatureCollection simpleFeatureCollection;

	/**
	 * DTSHPLayer 생성자
	 */
	public DTSHPLayer() {
		super();
	}

	/**
	 * DTSHPLayer 생성자
	 * 
	 * @param layerName
	 *            SHP 파일 레이어 이름
	 * @param layerType
	 *            SHP 파일 레이어 타입
	 * @param simpleFeatureCollection
	 *            SimpleFeatureCollection 객체
	 */
	public DTSHPLayer(String layerName, String layerType, SimpleFeatureCollection simpleFeatureCollection) {
		super();
		this.layerName = layerName;
		this.layerType = layerType;
		this.simpleFeatureCollection = simpleFeatureCollection;
	}

	/**
	 * DTSHPLayer 생성자
	 * 
	 * @param layerName
	 *            SHP 파일 레이어 이름
	 * @param layerType
	 *            SHP 파일 레이어 타입
	 */
	public DTSHPLayer(String layerName, String layerType) {
		super();
		this.layerName = layerName;
		this.layerType = layerType;
	}

	/**
	 * SHP 파일 레이어 이름 반환
	 * 
	 * @return String
	 */
	public String getLayerName() {
		return layerName;
	}

	/**
	 * SHP 파일 레이어 이름 설정
	 * 
	 * @param layerName
	 */
	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	/**
	 * SHP 파일 레이어 타입 반환
	 * 
	 * @return String
	 */
	public String getLayerType() {
		return layerType;
	}

	/**
	 * SHP 파일 레이어 타입 설정
	 * 
	 * @param layerType
	 */
	public void setLayerType(String layerType) {
		this.layerType = layerType;
	}

	/**
	 * SimpleFeatureCollection 객체 반환
	 * 
	 * @return SimpleFeatureCollection
	 */
	public SimpleFeatureCollection getSimpleFeatureCollection() {
		return simpleFeatureCollection;
	}

	/**
	 * SimpleFeatureCollection 객체 설정
	 * 
	 * @param simpleFeatureCollection
	 *            SimpleFeatureCollection 객체
	 */
	public void setSimpleFeatureCollection(SimpleFeatureCollection simpleFeatureCollection) {
		this.simpleFeatureCollection = simpleFeatureCollection;
	}

}
