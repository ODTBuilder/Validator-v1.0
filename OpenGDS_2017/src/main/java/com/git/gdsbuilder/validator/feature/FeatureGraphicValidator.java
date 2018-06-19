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

/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2012, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */

package com.git.gdsbuilder.validator.feature;

import java.util.List;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.SchemaException;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import com.git.gdsbuilder.type.validate.error.ErrorFeature;

/**
 * SimpleFeature를 그래픽 검수하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:34:07
 */
public interface FeatureGraphicValidator {

	/**
	 * 검수 항목 중 "계층 오류(LayerMiss)" 검수
	 * 
	 * @param simpleFeature
	 *            검수 대상 Simplefeature 객체
	 * @param typeNames
	 *            계층 타입(Geometry 타입)
	 * @return ErrorFeature
	 * @throws SchemaException
	 */
	public ErrorFeature validateLayerMiss(SimpleFeature simpleFeature, List<String> typeNames) throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 교차 오류(ConIntersected)” 검수를 수행
	 * 
	 * @param simpleFeatureI
	 *            검수 대상 Simplefeature 객체
	 * @param simpleFeatureJ
	 *            비교 대상 Simplefeature 객체
	 * @return List<ErrorFeature>
	 * @throws SchemaException
	 */
	public List<ErrorFeature> validateConIntersected(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ)
			throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 꺾임 오류(ConOverDegree)” 검수를 수행
	 * 
	 * @param simpleFeature
	 *            검수 대상 Simplefeature 객체
	 * @param inputDegree
	 *            등고선 꺾임 허용 각도
	 * @return List<ErrorFeature>
	 * @throws SchemaException
	 */
	public List<ErrorFeature> validateConOverDegree(SimpleFeature simpleFeature, double inputDegree)
			throws SchemaException;

	/**
	 * 검수 항목 중 “요소 중복 오류(EntityDuplicaated)” 검수를 수행
	 * 
	 * @param simpleFeatureI
	 *            검수 대상 Simplefeature 객체
	 * @param simpleFeatureJ
	 *            비교 대상 Simplefeature 객체
	 * @return ErrorFeature
	 * @throws SchemaException
	 */
	public ErrorFeature validateEntityDuplicated(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ)
			throws SchemaException;

	/**
	 * 검수 항목 중 “중복점오류(Duplicated Point)” 검수를 수행
	 * 
	 * @param simpleFeature
	 *            검수 대상 Simplefeature 객체
	 * @return List<ErrorFeature>
	 */
	public List<ErrorFeature> validatePointDuplicated(SimpleFeature simpleFeature);

	/**
	 * 검수 항목 중 두 SimpleFeature 객체의 “단독 존재 오류 (Self Entity)” 검수를 수행
	 * 
	 * @param simpleFeatureI
	 *            검수 대상 Simplefeature 객체
	 * @param simpleFeatureJ
	 *            비교 대상 Simplefeature 객체
	 * @param selfEntityTolerance
	 *            공간분석 정밀도 설정 (m) tolerance
	 * @param polygonInvadedTolorence
	 *            면형이 면형 객체 침범 (m2) tolerance
	 * @return List<ErrorFeature>
	 * @throws SchemaException
	 */
	public List<ErrorFeature> validateSelfEntity(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ,
			double selfEntityTolerance, double polygonInvadedTolorence) throws SchemaException;

	/**
	 * 검수 항목 중 “허용 범위 이하 면적 (Small Area)” 검수
	 * 
	 * @param simpleFeature
	 *            검수 대상 Simplefeature 객체
	 * @param inputArea
	 *            허용 범위 면적
	 * @return ErrorFeature
	 * @throws SchemaException
	 */
	public ErrorFeature validateSmallArea(SimpleFeature simpleFeature, double inputArea) throws SchemaException;

	/**
	 * 검수 항목 중 “허용 범위 이하 길이 (Small Length)” 검수
	 * 
	 * @param simpleFeature
	 *            검수 대상 Simplefeature 객체
	 * @param inputLength
	 *            허용 범위 길이
	 * @return ErrorFeature
	 * @throws SchemaException
	 */
	public ErrorFeature validateSmallLength(SimpleFeature simpleFeature, double inputLength) throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 끊김 오류(ConBreak)” 검수
	 * 
	 * @param simpleFeature
	 *            검수 대상 Simplefeature 객체
	 * @param aop
	 *            검수 영역
	 * @param tolerance
	 *            공간분석 정밀도 설정 (m) tolerance
	 * @return List<ErrorFeature>
	 * @throws SchemaException
	 */
	public List<ErrorFeature> validateConBreak(SimpleFeature simpleFeature, SimpleFeatureCollection aop,
			double tolerance) throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 직선화 미처리 오류 (Useless Point)” 검수
	 * 
	 * @param simpleFeature
	 *            검수 대상 Simplefeature 객체
	 * @return List<ErrorFeature>
	 * @throws SchemaException
	 * @throws NoSuchAuthorityCodeException
	 * @throws FactoryException
	 * @throws TransformException
	 */
	public List<ErrorFeature> validateUselessPoint(SimpleFeature simpleFeature)
			throws SchemaException, NoSuchAuthorityCodeException, FactoryException, TransformException;

	/**
	 * 검수 항목 중 두 레이어의 “경계초과오류 (Out Boundary)” 검수
	 * 
	 * @param simpleFeature
	 *            검수 대상 Simplefeature 객체
	 * @param relationSimpleFeature
	 *            비교 대상 Simplefeature 객체
	 * @param tolerance
	 *            공간분석 정밀도 설정 (m) tolerance
	 * @return ErrorFeature
	 * @throws SchemaException
	 */
	public ErrorFeature validateOutBoundary(SimpleFeature simpleFeature, SimpleFeature relationSimpleFeature,
			double tolerance) throws SchemaException;

	/**
	 * 검수 항목 중 두 레이어의 “기준점 초과 (OverShoot)” 검수
	 * 
	 * @param simpleFeature
	 *            검수 대상 Simplefeature 객체
	 * @param aop
	 *            검수 영역
	 * @param tolerance
	 *            공간분석 정밀도 설정 (m) tolerance
	 * @return List<ErrorFeature>
	 * @throws SchemaException
	 */
	public List<ErrorFeature> validateOverShoot(SimpleFeature simpleFeature, SimpleFeatureCollection aop,
			double tolerance) throws SchemaException;

	/**
	 * 검수 항목 중 "꼬인객체(TwistedPolygon)" 검수
	 * 
	 * @param simpleFeature
	 *            검수 대상 Simplefeature 객체
	 * @return ErrorFeature
	 * @throws SchemaException
	 */
	public ErrorFeature validateTwistedPolygon(SimpleFeature simpleFeature) throws SchemaException;

	/**
	 * 검수 항목 중 "노드오류(NodeMiss)" 검수
	 * 
	 * @param simpleFeature
	 *            검수 대상 Simplefeature 객체
	 * @param relationSfc
	 *            비교 대상 SimplefeatureCollection 객체
	 * @param simpleFeatureCollection
	 *            검수 대상 SimplefeatureCollection 객체
	 * @return List<ErrorFeature>
	 * @throws SchemaException
	 */
	public List<ErrorFeature> validateNodeMiss(SimpleFeature simpleFeature, SimpleFeatureCollection relationSfc,
			SimpleFeatureCollection simpleFeatureCollection) throws SchemaException;

	/**
	 * 검수 항목 중 단일 SimpleFeature 객체의 "등고선 교차오류(ConIntersected)" 검수
	 * 
	 * @param simpleFeature
	 *            검수 대상 Simplefeature 객체
	 * @return List<ErrorFeature>
	 */
	public List<ErrorFeature> validateConIntersected(SimpleFeature simpleFeature);

	/**
	 * 검수 항목 중 단일 SimpleFeature 객체의 “단독 존재 오류 (Self Entity)” 검수를 수행
	 * 
	 * @param simpleFeature
	 *            검수 대상 Simplefeature 객체
	 * @param selfEntityLineTolerance
	 *            공간분석 정밀도 설정 (m) tolerance
	 * @param polygonInvadedTolorence
	 *            면형이 면형 객체 침범 (m2) tolerance
	 * @return List<ErrorFeature>
	 */
	public List<ErrorFeature> validateSelfEntity(SimpleFeature simpleFeature, double selfEntityLineTolerance,
			double polygonInvadedTolorence);

}
