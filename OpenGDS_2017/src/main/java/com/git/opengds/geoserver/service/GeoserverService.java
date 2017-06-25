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
import org.json.simple.JSONObject;

import com.git.gdsbuilder.geolayer.data.DTGeoGroupLayerList;
import com.git.gdsbuilder.geolayer.data.DTGeoLayerList;
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
	 * 레이어를 중복체크한다.
	 * @author SG.Lee
	 * @Date 2017. 7
	 * @param layerList
	 * @return JSONObject - {레이어명 : 중복여부}
	 * */
	public JSONObject duplicateCheck(ArrayList<String> layerList);
	
	/**
	 * DTGeoLayerList를 조회한다.
	 * @author SG.Lee
	 * @Date 2017. 4
	 * @param layerList
	 * @return DTGeoLayerList - 레이어명 리스트
	 * */
	public DTGeoLayerList getGeoLayerList(ArrayList<String> layerList);
	
	/**
	 * DTGeoGroupLayerList를 조회한다.
	 * @author SG.Lee 
	 * @Date 2017. 4
	 * @param groupList
	 * @return DTGeoGroupLayerList - 그룹레이어명 리스트
	 * */
	public DTGeoGroupLayerList getGeoGroupLayerList(ArrayList<String> groupList);
	
	/**
	 * 단일 레이어를 삭제한다.
	 * @author SG.Lee
	 * @Date 2017. 6. 5. 오전 10:40:14
	 * @param layerName 삭제할 레이어 이름
	 * @return boolean - 삭제여부
	 * */
	public boolean removeGeoserverLayer(final String layerName);
	
	/**
	 * 다중 레이어를 삭제한다.
	 * @author SG.Lee
	 * @Date 2017. 6. 5. 오전 10:40:17
	 * @param layerNameList 삭제할 레이어 이름 리스트
	 * @return boolean - 삭제여부
	 * */
	public boolean removeGeoserverLayers(List<String> layerNameList);
	
	/**
	 *
	 * @author SG.Lee
	 * @Date 2017. 6. 5. 오전 11:08:03
	 * @param groupLayerName 삭제할 그룹레이어
	 * @return boolean - 삭제여부
	 * */
	public boolean removeGeoserverGroupLayer(final String groupLayerName);
	
	
	
	/**
	 *
	 * @author SG.Lee
	 * @Date 2017. 6. 19. 오후 9:15:07
	 * @return boolean
	 * */
	public List<String> getGeoserverStyleList();
	
	
	/**
	 * Geoserver 스타일을 생성한다.
	 * @author SG.Lee
	 * @Date 2017. 6. 7. 오후 6:15:55
	 * @param sldBody
	 * @param name
	 * @return boolean
	 * */
	public boolean publishStyle(final String sldBody, final String name);
	
	/**
	 * Geoserver 스타일을 수정한다.
	 * @author SG.Lee
	 * @Date 2017. 6. 7. 오후 6:15:57
	 * @param sldBody
	 * @param name
	 * @return boolean
	 * */
	public boolean updateStyle(final String sldBody, final String name);
	
	/**
	 * Geoserver 스타일을 삭제한다.
	 * @author SG.Lee
	 * @Date 2017. 6. 7. 오후 6:16:01
	 * @param styleName
	 * @return boolean
	 * */
	public boolean removeStyle(final String styleName);
	
	
	
	/**
	 * Geoserver 레이어를 업데이트한다.
	 * @author SG.Lee
	 * @Date 2017. 6. 19. 오후 7:45:22
	 * @param orginalName
	 * @param name
	 * @param title
	 * @param abstractContent
	 * @param style
	 * @param attChangeFlag
	 * @return boolean
	 * */
	public boolean updateFeatureType(final String orginalName,final String name,final String title,final String abstractContent,final String style, boolean attChangeFlag);
}


