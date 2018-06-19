package com.git.gdsbuilder.type.validate.collection.close;

import java.util.HashMap;
import java.util.Map;

import com.git.gdsbuilder.type.geoserver.layer.GeoLayer;
import com.git.gdsbuilder.validator.collection.opt.ValCollectionOption;
import com.git.gdsbuilder.validator.collection.rule.MapSystemRule;
import com.git.gdsbuilder.validator.collection.rule.MapSystemRule.MapSystemRuleType;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Polygon;

/**
 * 인접 GeoLayerCollection 검수 정보를 담고 있는 클래스
 * 
 * @author DY.Oh
 *
 */
public class ValidateCloseCollectionLayer {

	/**
	 * 인접 GeoLayerCollection
	 */
	private Map<MapSystemRuleType, GeoLayer> collectionMap;
	/**
	 * tolerance
	 */
	private double tolerance = 0.0;
	/**
	 * 인접 검수 항목 옵션
	 */
	private ValCollectionOption closeValidateOptions;
	/**
	 * GeoLayerCollection 검수 영역
	 */
	private Map<MapSystemRuleType, LineString> collectionBoundary = new HashMap<MapSystemRule.MapSystemRuleType, LineString>();
	/**
	 * GeoLayerCollection 검수 영역 내 Features
	 */
	private Map<MapSystemRuleType, Polygon> targetFeaturesGetBoundary = new HashMap<MapSystemRule.MapSystemRuleType, Polygon>();
	/**
	 * GeoLayerCollection 인접 영역 내 Features
	 */
	private Map<MapSystemRuleType, Polygon> nearFeaturesGetBoundary = new HashMap<MapSystemRule.MapSystemRuleType, Polygon>();

	/**
	 * ValidateCloseCollectionLayer 생성자
	 * 
	 * @param targetLayer
	 *            GeoLayerCollection 검수 영역 내 검수 레이어
	 * @param collectionMap
	 *            인접 GeoLayerCollection
	 * @param tolerance
	 *            tolerance
	 * @param closeValidateOptions
	 *            인접 검수 항목 옵션
	 * @param collectionBoundary
	 *            GeoLayerCollection 검수 영역
	 * @param targetFeaturesGetBoundary
	 *            GeoLayerCollection 검수 영역 내 Features
	 * @param nearFeaturesGetBoundary
	 *            GeoLayerCollection 인접 영역 내 Features
	 */
	public ValidateCloseCollectionLayer(GeoLayer targetLayer, Map<MapSystemRuleType, GeoLayer> collectionMap,
			double tolerance, ValCollectionOption closeValidateOptions,
			Map<MapSystemRuleType, LineString> collectionBoundary,
			Map<MapSystemRuleType, Polygon> targetFeaturesGetBoundary,
			Map<MapSystemRuleType, Polygon> nearFeaturesGetBoundary) {
		// TODO Auto-generated constructor stub
		this.collectionMap = collectionMap;
		this.collectionBoundary = collectionBoundary;
		this.tolerance = tolerance;
		this.closeValidateOptions = closeValidateOptions;
		this.targetFeaturesGetBoundary = targetFeaturesGetBoundary;
		this.nearFeaturesGetBoundary = nearFeaturesGetBoundary;
	}

	/**
	 * GeoLayerCollection 검수 영역 내 Features 반환
	 * 
	 * @return GeoLayerCollection
	 */
	public Map<MapSystemRuleType, Polygon> getTargetFeaturesGetBoundary() {
		return targetFeaturesGetBoundary;
	}

	/**
	 * GeoLayerCollection 검수 영역 내 Features 설정
	 * 
	 * @param targetFeaturesGetBoundary
	 *            GeoLayerCollection 검수 영역 내 Features
	 */
	public void setTargetFeaturesGetBoundary(Map<MapSystemRuleType, Polygon> targetFeaturesGetBoundary) {
		this.targetFeaturesGetBoundary = targetFeaturesGetBoundary;
	}

	/**
	 * GeoLayerCollection 인접 영역 내 Features 반환
	 * 
	 * @return Map<MapSystemRuleType, Polygon>
	 */
	public Map<MapSystemRuleType, Polygon> getNearFeaturesGetBoundary() {
		return nearFeaturesGetBoundary;
	}

	/**
	 * GeoLayerCollection 인접 영역 내 Features 설정
	 * 
	 * @param nearFeaturesGetBoundary
	 *            GeoLayerCollection 인접 영역 내 Features
	 */
	public void setNearFeaturesGetBoundary(Map<MapSystemRuleType, Polygon> nearFeaturesGetBoundary) {
		this.nearFeaturesGetBoundary = nearFeaturesGetBoundary;
	}

	/**
	 * GeoLayerCollection 검수 영역 반환
	 * 
	 * @return Map<MapSystemRuleType, LineString>
	 */
	public Map<MapSystemRuleType, LineString> getCollectionBoundary() {
		return collectionBoundary;
	}

	/**
	 * GeoLayerCollection 검수 영역 설정
	 * 
	 * @param collectionBoundary
	 *            GeoLayerCollection 검수 영역
	 */
	public void setCollectionBoundary(Map<MapSystemRuleType, LineString> collectionBoundary) {
		this.collectionBoundary = collectionBoundary;
	}

	/**
	 * 인접 GeoLayerCollection 반환
	 * 
	 * @return Map<MapSystemRuleType, GeoLayer>
	 */
	public Map<MapSystemRuleType, GeoLayer> getCollectionMap() {
		return collectionMap;
	}

	/**
	 * 인접 GeoLayerCollection 설정
	 * 
	 * @param collectionMap
	 *            인접 GeoLayerCollection
	 */
	public void setCollectionMap(Map<MapSystemRuleType, GeoLayer> collectionMap) {
		this.collectionMap = collectionMap;
	}

	/**
	 * tolerance 반환
	 * 
	 * @return double
	 */
	public double getTolorence() {
		return tolerance;
	}

	/**
	 * tolerance 설정
	 * 
	 * @param tolorence
	 *            tolerance
	 */
	public void setTolorence(double tolorence) {
		this.tolerance = tolorence;
	}

	/**
	 * 인접 검수 항목 옵션 반환
	 * 
	 * @return ValCollectionOption
	 */
	public ValCollectionOption getCloseValidateOptions() {
		return closeValidateOptions;
	}

	/**
	 * 인접 검수 항목 옵션 설정
	 * 
	 * @param closeValidateOptions
	 *            인접 검수 항목 옵션
	 */
	public void setCloseValidateOptions(ValCollectionOption closeValidateOptions) {
		this.closeValidateOptions = closeValidateOptions;
	}
}
