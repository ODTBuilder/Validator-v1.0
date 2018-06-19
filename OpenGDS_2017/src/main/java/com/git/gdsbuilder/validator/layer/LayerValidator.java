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

package com.git.gdsbuilder.validator.layer;

import java.io.IOException;
import java.util.List;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONObject;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import com.git.gdsbuilder.type.geoserver.layer.GeoLayer;
import com.git.gdsbuilder.type.validate.error.ErrorLayer;

/**
 * Layer를 검수하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:32:00
 */
public interface LayerValidator {

	/**
	 * 검수 항목 중 "계층 오류(LayerMiss)" 검수
	 * 
	 * @param typeNames
	 *            계층 타입(Geometry 타입)
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateLayerMiss(List<String> typeNames) throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 끊김 오류(ConBreak)” 검수
	 * 
	 * @param neatLayer
	 *            검수 영역
	 * @param tolerence
	 *            공간분석 정밀도 설정 (m) tolerance
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateConBreakLayers(GeoLayer neatLayer, double tolerence) throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 교차 오류(ConIntersected)” 검수를 수행
	 * 
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateConIntersected() throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 꺾임 오류(ConOverDegree)” 검수를 수행
	 * 
	 * @param degree
	 *            등고선 꺾임 허용 각도
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateConOverDegree(double degree) throws SchemaException;

	/**
	 * 검수 항목 중 “고도값 오류 (Z-Value Abmiguous)” 검수
	 * 
	 * @param attributeKey
	 *            고도값 속성 컬럼명
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateZValueAmbiguous(JSONObject attributeKey) throws SchemaException;

	/**
	 * 검수 항목 중 “허용 범위 이하 면적 (Small Area)” 검수
	 * 
	 * @param inputArea
	 *            허용 범위 면적
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateSmallArea(double area) throws SchemaException;

	/**
	 * 검수 항목 중 “허용 범위 이하 길이 (Small Length)” 검수
	 * 
	 * @param inputLength
	 *            허용 범위 길이
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateSmallLength(double length) throws SchemaException;

	/**
	 * 검수 항목 중 두 레이어의 “기준점 초과 (OverShoot)” 검수
	 * 
	 * @param neatLayer
	 *            검수 영역 레이어
	 * @param tolerance
	 *            공간분석 정밀도 설정 (m) tolerance
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateOverShoot(GeoLayer neatLayer, double tolerence) throws SchemaException;

	/**
	 * 검수 항목 중 두 SimpleFeature 객체의 “단독 존재 오류 (Self Entity)” 검수를 수행
	 * 
	 * @param simpleFeatureJ
	 *            비교 대상 GeoLayer 객체 목록
	 * @param selfEntityTolerance
	 *            공간분석 정밀도 설정 (m) tolerance
	 * @param polygonInvadedTolorence
	 *            면형이 면형 객체 침범 (m2) tolerance
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateSelfEntity(List<GeoLayer> relationLayers, double selfEntityLineTolerance,
			double polygonInvadedTolorence) throws SchemaException;

	/**
	 * “경계초과오류 (Out Boundary)” 검수
	 * 
	 * @param relationSimpleFeature
	 *            비교 대상 GeoLayer 객체 목록
	 * @param tolerance
	 *            공간분석 정밀도 설정 (m) tolerance
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateOutBoundary(List<GeoLayer> relationLayers, double spatialAccuracyTolorence)
			throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 직선화 미처리 오류 (Useless Point)” 검수
	 * 
	 * @return ErrorLayer
	 * @throws SchemaException
	 * @throws NoSuchAuthorityCodeException
	 * @throws FactoryException
	 * @throws TransformException
	 */
	public ErrorLayer validateUselessPoint()
			throws NoSuchAuthorityCodeException, SchemaException, FactoryException, TransformException;

	/**
	 * 검수 항목 중 “요소 중복 오류(EntityDuplicaated)” 검수를 수행
	 * 
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateEntityDuplicated() throws SchemaException;

	/**
	 * 검수 항목 중 "꼬인객체(TwistedPolygon)" 검수
	 * 
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateTwistedPolygon() throws SchemaException;

	/**
	 * 검수 항목 중 “속성 오류(Attribute Fix)” 검수. 검수 대상
	 * 
	 * @param notNullAtt
	 *            속성 컬럼명 및 속성 값
	 * @return ErrorLayer
	 * @throws SchemaException
	 */
	public ErrorLayer validateAttributeFix(JSONObject notNullAtt) throws SchemaException;

	/**
	 * 
	 * 검수 항목 중 "노드오류(NodeMiss)" 검수
	 * 
	 * @param relationLayers
	 *            비교 대상 GeoLayer 객체 목록
	 * @param geomColumn
	 *            geometry 컬럼명
	 * @param tolerence
	 *            공간분석 정밀도 설정 (m) tolerance
	 * @return ErrorLayer
	 * @throws SchemaException
	 * @throws IOException
	 */
	public ErrorLayer validateNodeMiss(List<GeoLayer> relationLayers, String geomColumn, double tolerence)
			throws SchemaException, IOException;

	/**
	 * 검수 항목 중 “중복점오류(Duplicated Point)” 검수를 수행
	 * 
	 * @return ErrorLayer
	 */
	public ErrorLayer validatePointDuplicated();

}
