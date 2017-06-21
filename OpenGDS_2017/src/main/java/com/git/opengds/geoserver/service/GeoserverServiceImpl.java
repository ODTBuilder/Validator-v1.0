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

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.geolayer.data.DTGeoGroupLayerList;
import com.git.gdsbuilder.geolayer.data.DTGeoLayerList;
import com.git.gdsbuilder.geoserver.data.GeoserverLayerCollectionTree;
import com.git.gdsbuilder.geoserver.factory.DTGeoserverPublisher;
import com.git.gdsbuilder.geoserver.factory.DTGeoserverReader;
import com.git.gdsbuilder.geosolutions.geoserver.rest.decoder.RESTFeatureType;
import com.git.gdsbuilder.geosolutions.geoserver.rest.decoder.RESTLayer;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.GSLayerEncoder;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.GSResourceEncoder.ProjectionPolicy;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.feature.GSAttributeEncoder;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.feature.GSFeatureTypeEncoder;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfoList;
import com.git.opengds.upload.domain.FileMeta;

/**
 * Geoserver와 관련된 요청을 처리하는 클래스
 * 
 * @author SG.Lee
 * @Date 2017. 5. 12. 오전 2:22:14
 */
@Service
public class GeoserverServiceImpl implements GeoserverService {
	private static final String URL;
	private static final String ID;
	private static final String PW;
	private static DTGeoserverReader dtReader;
	private static DTGeoserverPublisher dtPublisher;

	static {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			properties.load(classLoader.getResourceAsStream("geoserver.properties"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		URL = properties.getProperty("url");
		ID = properties.getProperty("id");
		PW = properties.getProperty("pw");
		try {
			dtReader = new DTGeoserverReader(URL, ID, PW);
			dtPublisher = new DTGeoserverPublisher(URL, ID, PW);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @since 2017. 5. 12.
	 * @author SG.Lee
	 * @param layerInfo
	 * @return
	 * @throws IllegalArgumentException
	 * @throws MalformedURLException
	 * @see com.git.opengds.geoserver.service.GeoserverService#dbLayerPublishGeoserver(com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo)
	 */
	@SuppressWarnings("unused")
	public FileMeta dbLayerPublishGeoserver(GeoLayerInfo layerInfo)
			throws IllegalArgumentException, MalformedURLException {
		String wsName = ID;
		String dsName = ID;
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
			String upperLayerName = layerName.toUpperCase();
			String fileType = layerInfo.getFileType();
			String layerFullName = "geo_" + fileType + "_" + fileName + "_" + layerName;

			fte.setProjectionPolicy(ProjectionPolicy.REPROJECT_TO_DECLARED);
			fte.setTitle(layerFullName); // 제목
			fte.setName(layerFullName); // 이름
			fte.setSRS(originSrc); // 좌표
			fte.setNativeCRS(originSrc);
			fte.setNativeName(layerFullName); // nativeName
			// fte.setLatLonBoundingBox(minx, miny, maxx, maxy, originSrc);

			boolean styleFlag = dtReader.existsStyle(upperLayerName);
			if (styleFlag) {
				layerEncoder.setDefaultStyle(upperLayerName);
			} else {
				layerEncoder.setDefaultStyle("defaultStyle");
			}

			flag = dtPublisher.publishDBLayer(wsName, dsName, fte, layerEncoder);

			if (flag == false) {
				dtPublisher.removeLayer(wsName, layerName);
				fileMeta.setServerPublishFlag(flag);
				return fileMeta;
			}
			successLayerList.add(layerFullName);
		}

		dtPublisher.createLayersGroup(wsName, fileName, successLayerList);
		fileMeta.setServerPublishFlag(flag);

		return fileMeta;
	}

	// 에러레이어 발행하기
	public void errLayerPublishGeoserver(GeoLayerInfoList geoLayerInfoList) {
		for (int i = 0; i < geoLayerInfoList.size(); i++) {
			GeoLayerInfo geoLayerInfo = geoLayerInfoList.get(i);
			dtPublisher.publishErrLayer(ID, ID, geoLayerInfo);
		}
	}

	/**
	 * @since 2017. 5. 12.
	 * @author SG.Lee
	 * @return
	 * @see com.git.opengds.geoserver.service.GeoserverService#getGeoserverLayerCollectionTree()
	 */
	@Override
	public JSONArray getGeoserverLayerCollectionTree() {
		GeoserverLayerCollectionTree collectionTree = dtReader.getGeoserverLayerCollectionTree(ID);
		return collectionTree;
	}

	/**
	 * @since 2017. 4
	 * @author SG.Lee
	 * @param layerList
	 * @return
	 * @see com.git.opengds.geoserver.service.GeoserverService#getGeoLayerList(java.util.ArrayList)
	 */
	@Override
	public DTGeoLayerList getGeoLayerList(ArrayList<String> layerList) {
		if (layerList == null)
			throw new IllegalArgumentException("LayerNames may not be null");
		if (layerList.size() == 0)
			throw new IllegalArgumentException("LayerNames may not be null");
		return dtReader.getDTGeoLayerList(ID, layerList);
	}

	/**
	 * @since 2017. 4
	 * @author SG.Lee
	 * @param groupList
	 * @return
	 * @see com.git.opengds.geoserver.service.GeoserverService#getGeoGroupLayerList(java.util.ArrayList)
	 */
	@Override
	public DTGeoGroupLayerList getGeoGroupLayerList(ArrayList<String> groupList) {
		if (groupList == null)
			throw new IllegalArgumentException("GroupNames may not be null");
		if (groupList.size() == 0)
			throw new IllegalArgumentException("GroupNames may not be null");
		return dtReader.getDTGeoGroupLayerList(ID, groupList);
	}

	/**
	 * @since 2017. 6. 5.
	 * @author SG.Lee
	 * @param layerName
	 * @return
	 * @see com.git.opengds.geoserver.service.GeoserverService#removeGeoserverLayer(java.lang.String)
	 */
	@Override
	public boolean removeGeoserverLayer(String layerName) {
		return dtPublisher.removeLayer(ID, layerName);
	}

	/**
	 * @since 2017. 6. 5.
	 * @author SG.Lee
	 * @param layerNameList
	 * @return
	 * @see com.git.opengds.geoserver.service.GeoserverService#removeGeoserverLayers(java.util.List)
	 */
	@Override
	public boolean removeGeoserverLayers(List<String> layerNameList) {
		return dtPublisher.removeLayers(ID, layerNameList);
	}

	/**
	 * 
	 * @since 2017. 6. 5.
	 * @author SG.Lee
	 * @param groupLayerName
	 * @return
	 * @see com.git.opengds.geoserver.service.GeoserverService#removeGeoserverGroupLayer(java.lang.String)
	 */
	@Override
	public boolean removeGeoserverGroupLayer(String groupLayerName) {
		return dtPublisher.removeLayerGroup(ID, groupLayerName);
	}
	
	/**
	 *
	 * @author SG.Lee
	 * @Date 2017. 6. 19. 오후 9:15:07
	 * @return boolean
	 * */
	@Override
	public List<String> getGeoserverStyleList(){
		return dtReader.getStyles().getNames();
	}

	/**
	 * 
	 * @since 2017. 6. 7.
	 * @author SG.Lee
	 * @param sldBody
	 * @param name
	 * @return
	 * @see com.git.opengds.geoserver.service.GeoserverService#publishStyle(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public boolean publishStyle(final String sldBody, final String name) {
		return dtPublisher.publishStyle(sldBody, name);
	};

	/**
	 * 
	 * @since 2017. 6. 7.
	 * @author SG.Lee
	 * @param sldBody
	 * @param name
	 * @return
	 * @see com.git.opengds.geoserver.service.GeoserverService#updateStyle(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public boolean updateStyle(final String sldBody, final String name) {
		return dtPublisher.updateStyle(sldBody, name);
	};

	/**
	 * 
	 * @since 2017. 6. 7.
	 * @author SG.Lee
	 * @param styleName
	 * @return
	 * @see com.git.opengds.geoserver.service.GeoserverService#removeStyle(java.lang.String)
	 */
	@Override
	public boolean removeStyle(String styleName) {
		return dtPublisher.removeStyle(styleName);
	};

	@Override
	public boolean updateFeatureType(String orginalName, String name, String title, String abstractContent, String style,  boolean attChangeFlag) {
		boolean updateFlag = false;
		GSFeatureTypeEncoder fte = new GSFeatureTypeEncoder();
		GSLayerEncoder layerEncoder = null;
		

		if (orginalName == null){
//            throw new IllegalArgumentException("OriginalName may not be null!");
            return false;
		}
        if (orginalName.isEmpty()){
//            throw new IllegalArgumentException("OriginalName may not be empty!");
            return false;
        }
        if(name != null && !name.isEmpty()){
        	fte.setName(name);
        }
        if(title != null && !title.isEmpty()){
        	fte.setTitle(title);
        }
        if(abstractContent != null && !abstractContent.isEmpty()){
        	fte.setAbstract(abstractContent);
        }
        if(style != null && !style.isEmpty()){
        	layerEncoder = new GSLayerEncoder();
        	layerEncoder.setDefaultStyle(style);
        }
        
		

//		boolean flag = dtPublisher.recalculate(workspace, storename, layerFullName, testFte, testLayerEncoder);
		updateFlag = dtPublisher.updateFeatureType(ID, ID, orginalName,fte, layerEncoder, attChangeFlag);

		return updateFlag;
	}
}
