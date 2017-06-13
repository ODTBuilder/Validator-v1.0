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

package com.git.opengds.geoserver.service;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;

import com.git.gdsbuilder.geolayer.data.DTGeoGroupLayerList;
import com.git.gdsbuilder.geolayer.data.DTGeoLayerList;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.GSLayerEncoder;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.feature.GSFeatureTypeEncoder;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfoList;
import com.git.opengds.upload.domain.FileMeta;



/** 
* @ClassName: GeoserverService 
* @Description: Geoserver와 관련된 데이터를 처리한다.
* @author JY.Kim 
* @date 2017. 4. 3. 오후 2:59:12 
*/
public interface GeoserverService {
	
	/**
	 * DB에 저장된 Layer를 Geoserver에 발행하기
	 * @author JY.Kim
	 * @Date 2017. 5. 9. 오후 5:45:40
	 * @param layerInfo - 레이어 정보 객체
	 * @return FileMeta - fileMeta 정보를 담은 객체
	 * @throws IllegalArgumentException
	 * @throws MalformedURLException FileMeta
	 * */
	public FileMeta dbLayerPublishGeoserver(GeoLayerInfo layerInfo) throws IllegalArgumentException, MalformedURLException;

	/**
	 * Geoserver에 errorLayer 발행하기
	 * @author JY.Kim
	 * @Date 2017. 5. 2. 오후 3:16:03
	 * @param geoLayerInfoList - geoLayerInfo 리스트
	 * */
	public void errLayerPublishGeoserver(GeoLayerInfoList geoLayerInfoList);
	
	/**
	 * Tree 형태의 GeoaerverLayerCollection JSONObject 객체
	 * @author JY.Kim
	 * @Date 2017. 4. 10. 오후 3:17:23
	 * @return JSONObject - Tree 형태의 GeoaerverLayerCollection JSONObject 객체 반환
	 * */
	public JSONArray getGeoserverLayerCollectionTree();
	
	/**
	 * DTGeoLayerList를 조회한다.
	 * @author SG.Lee
	 * @Date 2017. 4
	 * @param layerList
	 * @return DTGeoLayerList - 레이어명 리스트
	 * @throws
	 * */
	public DTGeoLayerList getGeoLayerList(ArrayList<String> layerList);
	
	/**
	 * DTGeoGroupLayerList를 조회한다.
	 * @author SG.Lee 
	 * @Date 2017. 4
	 * @param groupList
	 * @return DTGeoGroupLayerList - 그룹레이어명 리스트
	 * @throws
	 * */
	public DTGeoGroupLayerList getGeoGroupLayerList(ArrayList<String> groupList);
	
	/**
	 * 단일 레이어를 삭제한다.
	 * @author SG.Lee
	 * @Date 2017. 6. 5. 오전 10:40:14
	 * @param layerName 삭제할 레이어 이름
	 * @return boolean - 삭제여부
	 * @throws
	 * */
	public boolean removeGeoserverLayer(String layerName);
	
	/**
	 * 다중 레이어를 삭제한다.
	 * @author SG.Lee
	 * @Date 2017. 6. 5. 오전 10:40:17
	 * @param layerNameList 삭제할 레이어 이름 리스트
	 * @return boolean - 삭제여부
	 * @throws
	 * */
	public boolean removeGeoserverLayers(List<String> layerNameList);
	
	/**
	 *
	 * @author SG.Lee
	 * @Date 2017. 6. 5. 오전 11:08:03
	 * @param groupLayerName 삭제할 그룹레이어
	 * @return boolean - 삭제여부
	 * @throws
	 * */
	public boolean removeGeoserverGroupLayer(String groupLayerName);
	
	
	
	/**
	 * Geoserver 스타일을 생성한다.
	 * @author SG.Lee
	 * @Date 2017. 6. 7. 오후 6:15:55
	 * @param sldBody
	 * @param name
	 * @return boolean
	 * @throws
	 * */
	public boolean publishStyle(final String sldBody, final String name);
	
	/**
	 * Geoserver 스타일을 수정한다.
	 * @author SG.Lee
	 * @Date 2017. 6. 7. 오후 6:15:57
	 * @param sldBody
	 * @param name
	 * @return boolean
	 * @throws
	 * */
	public boolean updateStyle(final String sldBody, final String name);
	
	/**
	 * Geoserver 스타일을 삭제한다.
	 * @author SG.Lee
	 * @Date 2017. 6. 7. 오후 6:16:01
	 * @param styleName
	 * @return boolean
	 * @throws
	 * */
	public boolean removeStyle(String styleName);
	
	
	
	
	public boolean updateDBLayer(String workspace, String storename, String layername,GSFeatureTypeEncoder fte, GSLayerEncoder layerEncoder);
}


