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
 * Layer를  검수하는 클래스
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:32:00
 * */
public interface LayerValidator {

	/**
	 *  검수 항목 중 “등고선 끊김 오류(ConBreak)” 검수
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:50:52
	 * @param neatLayer
	 * @return ErrorLayer
	 * @throws SchemaException 
	 * @throws
	 * */
	public ErrorLayer validateConBreakLayers(GeoLayer neatLayer) throws SchemaException;

	/**
	 *  검수 항목 중 “등고선 교차 오류(ConIntersected)” 검수를 수행
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:50:53
	 * @return ErrorLayer
	 * @throws SchemaException 
	 * @throws
	 * */
	public ErrorLayer validateConIntersected() throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 꺾임 오류(ConOverDegree)” 검수를 수행
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:50:54
	 * @param degree
	 * @return
	 * @throws SchemaException 
	 * @throws
	 * */
	public ErrorLayer validateConOverDegree(double degree) throws SchemaException;

	/**
	 * 검수 항목 중 “고도값 오류 (Z-Value Abmiguous)” 검수
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:50:55
	 * @param key
	 * @return
	 * @throws SchemaException 
	 * @throws
	 * */
	public ErrorLayer validateZ_ValueAmbiguous(String attributeKey) throws SchemaException;

	/**
	 * 검수 항목 중 “허용 범위 이하 면적 (Small Area)” 검수
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:50:57
	 * @param area
	 * @return
	 * @throws SchemaException 
	 * @throws
	 * */
	public ErrorLayer validateSmallArea(double area) throws SchemaException;

	/**
	 * 검수 항목 중 “허용 범위 이하 길이 (Small Length)” 검수
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:50:58
	 * @param length
	 * @return
	 * @throws SchemaException 
	 * @throws
	 * */
	public ErrorLayer validateSmallLength(double length) throws SchemaException;

	/**
	 * 검수 항목 중 두 레이어의 “기준점 초과 (OverShoot)” 검수
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:50:59
	 * @param neatLayer
	 * @param tolerence
	 * @return
	 * @throws SchemaException 
	 * @throws
	 * */
	public ErrorLayer validateOverShoot(GeoLayer neatLayer, double tolerence) throws SchemaException;

	/**
	 * 검수 항목 중 두 레이어의 “기준점 미달 (UnderShoot)” 검수
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:51:00
	 * @param neatLayer
	 * @param tolerence
	 * @return
	 * @throws SchemaException 
	 * @throws
	 * */
	public ErrorLayer validateUnderShoot(GeoLayer neatLayer, double tolerence) throws SchemaException;

	/**
	 * 검수 항목 중 두 레이어의 “단독 존재 오류 (Self Entity)” 검수를 수행
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:51:01
	 * @param relationLayers
	 * @return
	 * @throws SchemaException 
	 * @throws
	 * */
	public ErrorLayer validateSelfEntity(List<GeoLayer> relationLayers) throws SchemaException;

	/**
	 * 검수 항목 중 두 레이어의 “경계초과오류 (Out Boundary)” 검수
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:51:02
	 * @param relationLayers
	 * @return
	 * @throws SchemaException 
	 * @throws
	 * */
	public ErrorLayer validateOutBoundary(List<GeoLayer> relationLayers) throws SchemaException;

	/**
	 * 검수 항목 중 “등고선 직선화 미처리 오류 (Useless Point)” 검수
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:51:03
	 * @return
	 * @throws NoSuchAuthorityCodeException
	 * @throws SchemaException
	 * @throws FactoryException
	 * @throws TransformException 
	 * @throws
	 * */
	public ErrorLayer validateUselessPoint()
			throws NoSuchAuthorityCodeException, SchemaException, FactoryException, TransformException;
	
	/**
	 * 검수 항목 중 “요소 중복 오류(EntityDuplicaated)” 검수를 수행
	 * @author DY.Oh
	 * @Date 2017. 4. 18. 오후 3:51:05
	 * @return
	 * @throws SchemaException 
	 * @throws
	 * */
	public ErrorLayer validateEntityDuplicated() throws SchemaException;
	
	/**
	 * 검수 항목 중 "불확실한사용요소오류(UselessEntity)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 6. 26. 오후 4:19:17
	 * @return
	 * @throws SchemaException ErrorLayer
	 * @throws
	 * */
	public ErrorLayer validateUselessEntity() throws SchemaException;
	
	/**
	 * 검수 항목 중 "건물페합오류(BuildingOpen)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 6. 26. 오후 4:20:17
	 * @return
	 * @throws SchemaException ErrorLayer
	 * @throws
	 * */
	public ErrorLayer validateBuildingOpen() throws SchemaException;
	
	/**
	 * 검수항목 중 "수부코드폐합오류(WaterOpen)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 6. 26. 오후 4:20:42
	 * @return
	 * @throws SchemaException ErrorLayer
	 * @throws
	 * */
	public ErrorLayer validateWaterOpen() throws SchemaException;
	
	/**
	 * 검수항목 중 "계층오류(LayerMiss)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 6. 26. 오후 4:21:17
	 * @param typeNames
	 * @return
	 * @throws SchemaException ErrorLayer
	 * @throws
	 * */
	public ErrorLayer validateLayerMiss(List<String> typeNames) throws SchemaException;
	
	/**
	 * 검수항목 중 "건물기호위치오류(B_SymbolOutSided)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 6. 26. 오후 4:21:53
	 * @param relationLayers
	 * @return
	 * @throws SchemaException ErrorLayer
	 * @throws
	 * */
	public ErrorLayer vallidateB_SymbolOutSided(List<GeoLayer> relationLayers) throws SchemaException;
	
	/**
	 * 검수항목 중 "교차로오류(CrossRoad)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 6. 26. 오후 4:22:33
	 * @param relationLayers
	 * @return
	 * @throws SchemaException ErrorLayer
	 * @throws
	 * */
	public ErrorLayer validateCrossRoad(List<GeoLayer> relationLayers) throws SchemaException;
	
	/**
	 * 검수항목 중 "교량명오류(BridgeName)" 검수를 수행
	 * @author JY.Kim
	 * @Date 2017. 6. 26. 오후 4:23:03
	 * @param relationLayers
	 * @return
	 * @throws SchemaException ErrorLayer
	 * @throws
	 * */
	public ErrorLayer validateBridgeName(List<GeoLayer> relationLayers) throws SchemaException;
	public ErrorLayer validateAdmin() throws SchemaException;
	public ErrorLayer validateTwistedPolygon() throws SchemaException;
	public ErrorLayer validateAttributeFix(JSONObject notNullAtt) throws SchemaException;
	public ErrorLayer validateNodeMiss(List<GeoLayer> relationLayers, String geomColumn, double tolerence) throws SchemaException, IOException;

}
