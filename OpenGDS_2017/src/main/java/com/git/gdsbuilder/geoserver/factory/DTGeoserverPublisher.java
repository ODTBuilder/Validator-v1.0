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
 *  GeoServer-Manager - Simple Manager idLibrary for GeoServer
 *  
 *  Copyright (C) 2007 - 2016 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.git.gdsbuilder.geoserver.factory;

import java.util.ArrayList;
import java.util.List;

import com.git.gdsbuilder.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.GSLayerEncoder;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.GSLayerGroupEncoder;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.GSResourceEncoder.ProjectionPolicy;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.feature.GSFeatureTypeEncoder;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.opengds.upload.domain.FileMeta;


/** 
* @ClassName: DTGeoserverPublisher 
* @Description: GeoSolution과 관련된 data modify, publish 기능 
* @author JY.Kim 
* @date 2017. 5. 2. 오후 2:37:32 
*/
public class DTGeoserverPublisher extends GeoServerRESTPublisher{

	public DTGeoserverPublisher(String restURL, String username, String password) {
		super(restURL, username, password);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Geoserver DBLayer 발행하기
	 * @author JY.Kim
	 * @Date 2017. 5. 2. 오전 11:00:32
	 * @param layerInfo - 레이어 정보
	 * @param wsName - 작업공간명
	 * @param dsName - 파일명
	 * @return FileMeta - 파일 메타 정보
	 * */
	public FileMeta publishGeoLayer(GeoLayerInfo layerInfo, String wsName, String dsName){
		
		FileMeta fileMeta = new FileMeta();
		
		String fileName = layerInfo.getFileName();
		List<String> layerNameList = layerInfo.getLayerNames();
		String originSrc = layerInfo.getOriginSrc();
		List<String> successLayerList = new ArrayList<String>();
		boolean flag = false;
		
		for (int i = 0; i < layerNameList.size(); i++) {
			GSFeatureTypeEncoder fte = new GSFeatureTypeEncoder();
			GSLayerEncoder layerEncoder = new GSLayerEncoder();
			String layerName = layerNameList.get(i);
			String layerFullName = "geo_" + fileName + "_" + layerName;
			
			fte.setProjectionPolicy(ProjectionPolicy.REPROJECT_TO_DECLARED); 
			fte.setTitle(layerFullName); // 제목
			fte.setName(layerFullName); // 이름
			fte.setSRS(originSrc); // 좌표
			fte.setNativeCRS(originSrc);
			fte.setNativeName(layerFullName); // nativeName
			
			layerEncoder.setDefaultStyle("defaultStyle");
			
			flag = super.publishDBLayer(wsName, dsName, fte, layerEncoder);
			
			if(flag == false){
				super.removeLayer(wsName, layerName);
				fileMeta.setServerPublishFlag(flag);
				return fileMeta;
			}
			successLayerList.add(layerFullName);
		}
		
		this.createLayersGroup(wsName, fileName, successLayerList);
		fileMeta.setServerPublishFlag(flag);
		
		return fileMeta;
	}
	
	/**
	 * LayerGroup 생성
	 * @author JY.Kim
	 * @Date 2017. 5. 2. 오전 11:00:39
	 * @param wsName - 작업공간명
	 * @param fileName - 파일명
	 * @param successLayerList - 레이어 리스트
	 * */
	public void createLayersGroup( String wsName, String fileName, List<String> successLayerList){
		GSLayerGroupEncoder group = new GSLayerGroupEncoder();
		for (int i = 0; i < successLayerList.size(); i++) {
			String Layer = successLayerList.get(i);
			group.addLayer(Layer);
		}
		super.createLayerGroup(wsName,fileName, group);
	}
	
	/**
	 * 에러 레이어 발행하기
	 * @author JY.Kim
	 * @Date 2017. 5. 2. 오전 11:33:39
	 * @param wsName - 작업공간명
	 * @param dsName - 저장소명
	 * @param geoLayerInfo - 레이어 정보
	 * @return boolean - true or false
	 * */
	public boolean publishErrLayer(String wsName, String dsName, GeoLayerInfo geoLayerInfo){
		String fileName = geoLayerInfo.getFileName();
		String src = geoLayerInfo.getOriginSrc();
		String fullName = "err_" + fileName;
		
		GSFeatureTypeEncoder fte = new GSFeatureTypeEncoder();
		GSLayerEncoder layerEncoder = new GSLayerEncoder();
		
		fte.setProjectionPolicy(ProjectionPolicy.REPROJECT_TO_DECLARED);
		fte.setTitle(fullName);
		fte.setNativeName(fullName);
		fte.setName(fullName);
		fte.setSRS(src);
		
		layerEncoder.setDefaultStyle("defaultStyle");
		boolean flag = super.publishDBLayer(wsName, dsName, fte, layerEncoder);
		return flag;
	}
}
