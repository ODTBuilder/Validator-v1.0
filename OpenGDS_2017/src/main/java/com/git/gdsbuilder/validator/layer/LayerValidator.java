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
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import com.git.gdsbuilder.type.geoserver.layer.GeoLayer;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerList;
import com.git.gdsbuilder.type.validate.collection.close.ValidateCloseCollectionLayer;
import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.type.validate.error.ErrorLayer;

/**
 * Layer를 검수하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:32:00
 */
public interface LayerValidator {

	/**
	 * 검수 항목 중 “등고선 끊김 오류(ConBreak)” 검수 @author DY.Oh @Date 2017. 4. 18. 오후
	 * 3:50:52 @param neatLayer @return ErrorLayer @throws
	 * SchemaException @throws
	 */
	public ErrorLayer validateConBreakLayers(GeoLayer neatLayer, double tolerence) throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 교차 오류(ConIntersected)” 검수를 수행 @author DY.Oh @Date 2017. 4.
	 * 18. 오후 3:50:53 @return ErrorLayer @throws SchemaException @throws
	 */
	public ErrorLayer validateConIntersected() throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 꺾임 오류(ConOverDegree)” 검수를 수행 @author DY.Oh @Date 2017. 4.
	 * 18. 오후 3:50:54 @param degree @return @throws SchemaException @throws
	 */
	public ErrorLayer validateConOverDegree(double degree) throws SchemaException;

	/**
	 * 검수 항목 중 “고도값 오류 (Z-Value Abmiguous)” 검수 @author DY.Oh @Date 2017. 4. 18.
	 * 오후 3:50:55 @param key @return @throws SchemaException @throws
	 */
	public ErrorLayer validateZValueAmbiguous(JSONObject attributeKey) throws SchemaException;

	/**
	 * 검수 항목 중 “허용 범위 이하 면적 (Small Area)” 검수 @author DY.Oh @Date 2017. 4. 18. 오후
	 * 3:50:57 @param area @return @throws SchemaException @throws
	 */
	public ErrorLayer validateSmallArea(double area) throws SchemaException;

	/**
	 * 검수 항목 중 “허용 범위 이하 길이 (Small Length)” 검수 @author DY.Oh @Date 2017. 4. 18.
	 * 오후 3:50:58 @param length @return @throws SchemaException @throws
	 */
	public ErrorLayer validateSmallLength(double length) throws SchemaException;

	/**
	 * 검수 항목 중 두 레이어의 “기준점 초과 (OverShoot)” 검수 @author DY.Oh @Date 2017. 4. 18.
	 * 오후 3:50:59 @param neatLayer @param tolerence @return @throws
	 * SchemaException @throws
	 */
	public ErrorLayer validateOverShoot(GeoLayer neatLayer, double tolerence) throws SchemaException;

	/**
	 * 검수 항목 중 두 레이어의 “기준점 미달 (UnderShoot)” 검수 @author DY.Oh @Date 2017. 4. 18.
	 * 오후 3:51:00 @param neatLayer @param tolerence @return @throws
	 * SchemaException @throws
	 */
	public ErrorLayer validateUnderShoot(GeoLayer neatLayer, double tolerence) throws SchemaException;

	/**
	 * 검수 항목 중 두 레이어의 “단독 존재 오류 (Self Entity)” 검수를 수행 @author DY.Oh @Date 2017.
	 * 4. 18. 오후 3:51:01 @param relationLayers @return @throws
	 * SchemaException @throws
	 */
	public ErrorLayer validateSelfEntity(List<GeoLayer> relationLayers, double selfEntityLineTolerance, double polygonInvadedTolorence) throws SchemaException;

	/**
	 * 검수 항목 중 두 레이어의 “경계초과오류 (Out Boundary)” 검수 @author DY.Oh @Date 2017. 4.
	 * 18. 오후 3:51:02 @param relationLayers @return @throws
	 * SchemaException @throws
	 */
	public ErrorLayer validateOutBoundary(List<GeoLayer> relationLayers, double spatialAccuracyTolorence)
			throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 직선화 미처리 오류 (Useless Point)” 검수 @author DY.Oh @Date 2017. 4.
	 * 18. 오후 3:51:03 @return @throws NoSuchAuthorityCodeException @throws
	 * SchemaException @throws FactoryException @throws
	 * TransformException @throws
	 */
	public ErrorLayer validateUselessPoint()
			throws NoSuchAuthorityCodeException, SchemaException, FactoryException, TransformException;

	/**
	 * 검수 항목 중 “요소 중복 오류(EntityDuplicaated)” 검수를 수행 @author DY.Oh @Date 2017. 4.
	 * 18. 오후 3:51:05 @return @throws SchemaException @throws
	 */
	public ErrorLayer validateEntityDuplicated() throws SchemaException;

	/**
	 * 검수 항목 중 "불확실한사용요소오류(UselessEntity)" 검수를 수행 @author JY.Kim @Date 2017. 6.
	 * 26. 오후 4:19:17 @return @throws SchemaException ErrorLayer @throws
	 */
	public ErrorLayer validateUselessEntity() throws SchemaException;

	/**
	 * 검수 항목 중 "건물페합오류(BuildingOpen)" 검수를 수행 @author JY.Kim @Date 2017. 6. 26.
	 * 오후 4:20:17 @return @throws SchemaException ErrorLayer @throws
	 */
	public ErrorLayer validateBuildingOpen(GeoLayer neatLayer, double tolerence) throws SchemaException;

	/**
	 * 검수항목 중 "수부코드폐합오류(WaterOpen)" 검수를 수행 @author JY.Kim @Date 2017. 6. 26. 오후
	 * 4:20:42 @return @throws SchemaException ErrorLayer @throws
	 */
	public ErrorLayer validateWaterOpen(GeoLayer neatLayer, double tolerence) throws SchemaException;

	/**
	 * 검수항목 중 "계층오류(LayerMiss)" 검수를 수행 @author JY.Kim @Date 2017. 6. 26. 오후
	 * 4:21:17 @param typeNames @return @throws SchemaException
	 * ErrorLayer @throws
	 */
	public ErrorLayer validateLayerMiss(List<String> typeNames) throws SchemaException;

	/**
	 * 검수항목 중 "건물기호위치오류(B_SymbolOutSided)" 검수를 수행 @author JY.Kim @Date 2017. 6.
	 * 26. 오후 4:21:53 @param relationLayers @return @throws SchemaException
	 * ErrorLayer @throws
	 */
	public ErrorLayer vallidateB_SymbolOutSided(List<GeoLayer> relationLayers) throws SchemaException;

	/**
	 * 검수항목 중 "교차로오류(CrossRoad)" 검수를 수행 @author JY.Kim @Date 2017. 6. 26. 오후
	 * 4:22:33 @param relationLayers @return @throws SchemaException
	 * ErrorLayer @throws
	 */
	public ErrorLayer validateCrossRoad(List<GeoLayer> relationLayers, String geomColumn, double tolerence)
			throws SchemaException;

	/**
	 * 검수항목 중 "교량명오류(BridgeName)" 검수를 수행 @author JY.Kim @Date 2017. 6. 26. 오후
	 * 4:23:03 @param relationLayers @return @throws SchemaException
	 * ErrorLayer @throws
	 */
	public ErrorLayer validateBridgeName(List<GeoLayer> relationLayers) throws SchemaException;
	
	/**
	 * 검수항목 중 "행정경계오류(Admin)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 8. 18. 오전 10:20:46
	 * @return ErrorLayer
	 * @throws SchemaException ErrorLayer
	 * */
	public ErrorLayer validateAdmin() throws SchemaException;
	
	/**
	 * 검수항목 중 "꼬인객체오류(TwistedPolygon)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 8. 18. 오전 10:21:23
	 * @return ErrorLayer
	 * @throws SchemaException ErrorLayer
	 * @throws
	 * */
	public ErrorLayer validateTwistedPolygon() throws SchemaException;
	
	/**
	 * 검수항목 중 "속성오류(AttributeFix)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 8. 18. 오전 10:21:44
	 * @param notNullAtt 
	 * @return ErrorLayer
	 * @throws SchemaException ErrorLayer
	 * @throws
	 * */
	public ErrorLayer validateAttributeFix(JSONObject notNullAtt) throws SchemaException;
	
	/**
	 * 검수항목 중 "노드오류(NodeMiss)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 8. 18. 오전 10:22:39
	 * @param relationLayers
	 * @param geomColumn
	 * @param tolerence
	 * @return ErrorLayer
	 * @throws SchemaException
	 * @throws IOException ErrorLayer
	 * @throws
	 * */
	public ErrorLayer validateNodeMiss(List<GeoLayer> relationLayers, String geomColumn, double tolerence)
			throws SchemaException, IOException;
	
	/**
	 * 검수항목 중 "중복점오류(PointDuplicated)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 8. 18. 오전 10:23:26
	 * @return ErrorLayer
	 * @throws
	 * */
	public ErrorLayer validatePointDuplicated();

	public ErrorLayer validateOneAcre(GeoLayerList typeLayers, double spatialAccuracyTolorence);

	public ErrorLayer validateOneStage(GeoLayerList typeLayers, double spatialAccuracyTolorence);
	
	/**
	 * 검수항목 중 "일반주택주기값오류(HouseAttribute)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 8. 18. 오전 10:23:50
	 * @return ErrorLayer
	 * @throws
	 * */
	public ErrorLayer valildateHouseAttribute();
	
	/**
	 * 검수항목 중 "UFID길이오류(UFIDLength)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 8. 18. 오전 10:24:38
	 * @param length
	 * @return ErrorLayer
	 * @throws
	 * */
	public ErrorLayer validateUFIDLength(double length);
	
	/**
	 * 검수항목 중 "묘지계 오류(CemeterySite)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 8. 18. 오전 10:24:58
	 * @param relationLayers
	 * @return ErrorLayer
	 * @throws
	 * */
	public ErrorLayer validateCemeterySite(List<GeoLayer> relationLayers);
	
	/**
	 * 검수항목 중 "건물 부지 오류(BuildingSite)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 8. 18. 오전 10:25:18
	 * @param attributeJson
	 * @param relationLayers
	 * @return ErrorLayer
	 * @throws
	 * */
	public ErrorLayer validateBuildingSite(JSONObject attributeJson, List<GeoLayer> relationLayers);
	
	/**
	 * 검수항목 중 "도엽속성 오류(NeatLineAttribute)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 8. 18. 오전 10:25:36
	 * @return ErrorLayer
	 * @throws
	 * */
	public ErrorLayer validateNeatLineAttribute();
	
	/**
	 * 검수항목 중 "수치값 오류(NumericalValue)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 8. 18. 오전 10:25:58
	 * @param attribute
	 * @param condition
	 * @param figure
	 * @return ErrorLayer
	 * @throws
	 * */
	public ErrorLayer validateNumericalValue(String attribute, String condition, double figure);
	
	/**
	 * 검수항목 중 "하천경계 오류(RiverBoundaryMiss)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 8. 18. 오전 10:26:16
	 * @param relationLayers
	 * @return ErrorLayer
	 * @throws
	 * */
	public ErrorLayer validateRiverBoundaryMiss(List<GeoLayer> relationLayers);
	
	/**
	 * 검수항목 중 "UFID규칙 오류(UFIDRule)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 8. 18. 오전 10:26:36
	 * @return ErrorLayer
	 * @throws
	 * */
	public ErrorLayer validateUFIDRule();
	
	/**
	 * 검수항목 중 "중심선 누락 오류(CenterLineMiss)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 8. 18. 오전 10:26:59
	 * @param relationLayers
	 * @return ErrorLayer
	 * @throws
	 * */
	public ErrorLayer validateCenterLineMiss(List<GeoLayer> relationLayers, double lineInvadedTolorence);
	
	/**
	 * 검수항목 중 "홀 존재 오류(HoleMisplacement)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 8. 18. 오후 5:14:25
	 * @return ErrorLayer
	 * @throws
	 * */
	public ErrorLayer validateHoleMisplacement();
	
	public ErrorLayer validateEntityInHole(List<GeoLayer> relationLayers);
	
	public ErrorLayer validateCloseCollection(ValidateCloseCollectionLayer closeCollectionLayer, String geomColunm);

}
