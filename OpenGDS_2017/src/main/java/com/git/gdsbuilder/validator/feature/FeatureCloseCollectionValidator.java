package com.git.gdsbuilder.validator.feature;

import java.util.List;

import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.validator.collection.opt.ValCollectionOption;
import com.vividsolutions.jts.geom.LineString;

/**
 * SimpleFeature를 인접 검수하는 클래스
 * 
 * @author DY.Oh
 *
 */
public interface FeatureCloseCollectionValidator {

	/**
	 * 검수 대상 Simplefeature 객체 인접영역 검수
	 * 
	 * @param simpleFeature
	 *            검수 대상 Simplefeature 객체
	 * @param relationSimpleFeatures
	 *            인접 영역 내 Simplefeature 객체 목록
	 * @param closeValidateOptions
	 *            인접 검수 항목
	 * @param nearLine
	 *            검수 영역
	 * @param tolerance
	 *            공간분석 정밀도 설정 (m) tolerance
	 * @param direction
	 *            인접 영역 방향
	 * @return List<ErrorFeature>
	 */
	public List<ErrorFeature> ValidateCloseCollectionTarget(SimpleFeature simpleFeature,
			List<SimpleFeature> relationSimpleFeatures, ValCollectionOption closeValidateOptions, LineString nearLine,
			double tolerance, String direction);

	/**
	 * 검수 대상 인접 Simplefeature 객체 인접영역 검수
	 * 
	 * @param nearFeature
	 *            인접 영역 내 SimpleFeature 객체
	 * @param targetFeatureList
	 *            검수 대상 Simplefeature 객체
	 * @param nearLine
	 *            검수 영역
	 * @param tolerance
	 *            공간분석 정밀도 설정 (m) tolerance
	 * @return List<ErrorFeature>
	 */
	public List<ErrorFeature> ValidateCloseCollectionRelation(SimpleFeature nearFeature,
			List<SimpleFeature> targetFeatureList, LineString nearLine, double tolerance);
}
