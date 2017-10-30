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
 * SimpleFeatureCollection를 그래픽 검수하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:34:07
 */
public interface FeatureGraphicValidator {
	
	/**
	 * 검수 항목 중 "계층 오류(LayerMiss)" 검수 @author JY.Kim @Date 2017. 6. 12. 오전
	 * 11:42:43 @param simpleFeature @param typeNames @return
	 * ErrorFeature @throws SchemaException ErrorFeature @throws
	 */
	public ErrorFeature validateLayerMiss(SimpleFeature simpleFeature, List<String> typeNames) throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 교차 오류(ConIntersected)” 검수를 수행 @author DY.Oh @Date 2017. 4.
	 * 18. 오후 3:34:23 @param simpleFeatureI @param simpleFeatureJ @return
	 * List<ErrorFeature> @throws SchemaException @throws
	 */
	public List<ErrorFeature> validateConIntersected(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ)
			throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 꺾임 오류(ConOverDegree)” 검수를 수행 @author DY.Oh @Date 2017. 4.
	 * 18. 오후 3:34:34 @param simpleFeature @param inputDegree @return
	 * List<ErrorFeature> @throws SchemaException @throws
	 */
	public List<ErrorFeature> validateConOverDegree(SimpleFeature simpleFeature, double inputDegree)
			throws SchemaException;

	/**
	 * 검수 항목 중 “요소 중복 오류(EntityDuplicaated)” 검수를 수행 @author DY.Oh @Date 2017. 4.
	 * 18. 오후 3:34:44 @param simpleFeatureI @param simpleFeatureJ @return
	 * ErrorFeature @throws SchemaException @throws
	 */
	public ErrorFeature validateEntityDuplicated(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ)
			throws SchemaException;

	/**
	 * 검수 항목 중 “중복점오류(Duplicated Point)” 검수를 수행 @author DY.Oh @Date 2017. 4. 18.
	 * 오후 3:34:56 @param validatorLayer @return ErrorLayer @throws
	 * SchemaException @throws
	 */
	public List<ErrorFeature> validatePointDuplicated(SimpleFeature simpleFeature);

	/**
	 * 검수 항목 중 두 레이어의 “단독 존재 오류 (Self Entity)” 검수를 수행 @author DY.Oh @Date 2017.
	 * 4. 18. 오후 3:35:06 @param simpleFeatureI @param simpleFeatureJ @return
	 * List<ErrorFeature> @throws SchemaException @throws
	 */
	public List<ErrorFeature> validateSelfEntity(SimpleFeature simpleFeatureI, SimpleFeature simpleFeatureJ,
			double selfEntityTolerance, double polygonInvadedTolorence) throws SchemaException;

	/**
	 * 검수 항목 중 “허용 범위 이하 면적 (Small Area)” 검수 @author DY.Oh @Date 2017. 4. 18. 오후
	 * 3:35:21 @param simpleFeature @param inputArea @return
	 * ErrorFeature @throws SchemaException @throws
	 */
	public ErrorFeature validateSmallArea(SimpleFeature simpleFeature, double inputArea) throws SchemaException;

	/**
	 * 검수 항목 중 “허용 범위 이하 길이 (Small Length)” 검수 @author DY.Oh @Date 2017. 4. 18.
	 * 오후 3:35:32 @param simpleFeature @param inputLength @return
	 * ErrorFeature @throws SchemaException @throws
	 */
	public ErrorFeature validateSmallLength(SimpleFeature simpleFeature, double inputLength) throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 끊김 오류(ConBreak)” 검수 @author DY.Oh @Date 2017. 4. 18. 오후
	 * 3:35:40 @param validatorLayer @param aop @return
	 * List<ErrorFeature> @throws SchemaException @throws
	 */
	public List<ErrorFeature> validateConBreak(SimpleFeature validatorLayer, SimpleFeatureCollection aop,
			double tolerence) throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 직선화 미처리 오류 (Useless Point)” 검수 @author DY.Oh @Date 2017. 4.
	 * 18. 오후 3:35:48 @param simpleFeature @return List<ErrorFeature> @throws
	 * SchemaException @throws NoSuchAuthorityCodeException @throws
	 * FactoryException @throws TransformException @throws
	 */
	public List<ErrorFeature> validateUselessPoint(SimpleFeature simpleFeature)
			throws SchemaException, NoSuchAuthorityCodeException, FactoryException, TransformException;

	/**
	 * @param spatialAccuracyTolorence
	 *            검수 항목 중 두 레이어의 “경계초과오류 (Out Boundary)” 검수 @author DY.Oh @Date
	 *            2017. 4. 18. 오후 3:35:57 @param simpleFeature @param
	 *            relationSimpleFeature @return ErrorFeature @throws
	 *            SchemaException @throws
	 */
	public ErrorFeature validateOutBoundary(SimpleFeature simpleFeature, SimpleFeature relationSimpleFeature,
			double spatialAccuracyTolorence) throws SchemaException;

	/**
	 * 검수 항목 중 두 레이어의 “기준점 초과 (OverShoot)” 검수 @author DY.Oh @Date 2017. 4. 18.
	 * 오후 3:36:05 @param simpleFeature @param aop @param tolerence @return
	 * List<ErrorFeature> @throws SchemaException @throws
	 */
	public List<ErrorFeature> validateOverShoot(SimpleFeature simpleFeature, SimpleFeatureCollection aop,
			double tolerence) throws SchemaException;

	/**
	 * 검수 항목 중 "꼬인객체(TwistedPolygon)" 검수 @author JY.Kim @Date 2017. 7. 28. 오후
	 * 5:51:10 @param simpleFeature @return ErrorFeature @throws
	 * SchemaException @throws
	 */
	public ErrorFeature validateTwistedPolygon(SimpleFeature simpleFeature) throws SchemaException;

	/**
	 * 검수 항목 중 "노드오류(NodeMiss)" 검수 @author JY.Kim @Date 2017. 7. 28. 오후
	 * 5:53:08 @param simpleFeature @param relationSimpleFeature @param
	 * tolerence @return List<ErrorFeature> @throws SchemaException @throws
	 */
	public List<ErrorFeature> validateNodeMiss(SimpleFeature simpleFeature, SimpleFeatureCollection relationSfc,
			SimpleFeatureCollection sfc) throws SchemaException;

	/**
	 * 검수 항목 중 "등고선 교차오류(ConIntersected)" 검수 @author JY.Kim @Date 2017. 7. 28.
	 * 오후 5:53:36 @param tmpSimpleFeatureI @return List<ErrorFeature> @throws
	 */
	public List<ErrorFeature> validateConIntersected(SimpleFeature tmpSimpleFeatureI);

	public List<ErrorFeature> validateSelfEntity(SimpleFeature tmpSimpleFeatureI, double selfEntityLineTolerance,
			double polygonInvadedTolorence);

}
