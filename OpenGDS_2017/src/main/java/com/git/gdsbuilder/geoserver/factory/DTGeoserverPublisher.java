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

import java.net.MalformedURLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.gdsbuilder.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import com.git.gdsbuilder.geosolutions.geoserver.rest.HTTPUtils;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.GSLayerEncoder;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.GSLayerGroupEncoder;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.GSResourceEncoder.ProjectionPolicy;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.feature.GSFeatureTypeEncoder;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;

/**
 * @ClassName: DTGeoserverPublisher
 * @Description: GeoSolution과 관련된 data modify, publish 기능
 * @author JY.Kim
 * @date 2017. 5. 2. 오후 2:37:32
 */
public class DTGeoserverPublisher extends GeoServerRESTPublisher {

	/** The logger for this class */
	private static final Logger LOGGER = LoggerFactory.getLogger(DTGeoserverPublisher.class);

	/**
	 * GeoServer instance base URL. E.g.:
	 * <TT>http://localhost:8080/geoserver</TT>.
	 */
	private final String restURL;

	/**
	 * GeoServer instance privileged username, with read & write permission on
	 * REST API
	 */
	private final String gsuser;

	/**
	 * GeoServer instance password for privileged username with r&w permission
	 * on REST API
	 */
	private final String gspass;

	public DTGeoserverPublisher(String restURL, String username, String password) {
		super(restURL, username, password);
		this.restURL = HTTPUtils.decurtSlash(restURL);
		this.gsuser = username;
		this.gspass = password;
	}

	/**
	 * LayerGroup 생성
	 * 
	 * @author JY.Kim
	 * @Date 2017. 5. 2. 오전 11:00:39
	 * @param wsName
	 *            - 작업공간명
	 * @param fileName
	 *            - 파일명
	 * @param successLayerList
	 *            - 레이어 리스트
	 */
	public void createLayersGroup(String wsName, String fileName, List<String> successLayerList) {
		GSLayerGroupEncoder group = new GSLayerGroupEncoder();
		for (int i = 0; i < successLayerList.size(); i++) {
			String Layer = successLayerList.get(i);
			group.addLayer(Layer);
		}
		super.createLayerGroup(wsName, fileName, group);
	}

	/**
	 * 에러 레이어 발행하기
	 * 
	 * @author JY.Kim
	 * @Date 2017. 5. 2. 오전 11:33:39
	 * @param wsName
	 *            - 작업공간명
	 * @param dsName
	 *            - 저장소명
	 * @param geoLayerInfo
	 *            - 레이어 정보
	 * @return boolean - true or false
	 */
	public boolean publishErrLayer(String wsName, String dsName, GeoLayerInfo geoLayerInfo) {
		String fileName = geoLayerInfo.getFileName();
		String src = geoLayerInfo.getOriginSrc();
		String fileType = geoLayerInfo.getFileType();
		String fullName = "err_" + fileType + "_" + fileName;

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

	/**
	 * 여러 레이어 삭제
	 * 
	 * @author JY.Kim
	 * @Date 2017. 4. 12 오전 9:52:31
	 * @param wsName
	 *            - 작업공간명
	 * @param layerNameList
	 *            - 레이어리스트
	 * @throws IllegalArgumentException
	 * @throws MalformedURLException
	 */
	public boolean removeLayers(String wsName, List<String> layerNameList) {
		boolean flag = false;
		int flagCount = 0;
		for (int i = 0; i < layerNameList.size(); i++) {
			String layerName = layerNameList.get(i);
			flag = this.removeLayer(wsName, layerName);
			if (flag == false) {
				flagCount++;
			}
		}
		// 삭제 실패를 하나라도 할경우 false 반환
		if (flagCount > 0) {
			flag = false;
		} else
			flag = true;

		return flag;
	}

	public boolean updateDBLayer(final String workspace, final String storename, final String layername,
			final GSFeatureTypeEncoder fte, final GSLayerEncoder layerEncoder) {
		String ftypeXml = fte.toString();
		/*
		 * StringBuilder putUrl = new
		 * StringBuilder(restURL).append("/rest/workspaces/")
		 * .append(workspace).append("/datastores/").append(storename).append(
		 * "/featuretypes");
		 */

		StringBuilder putUrl = new StringBuilder(restURL).append("/rest/workspaces/").append(workspace)
				.append("/datastores/").append(storename).append("/featuretypes/").append(layername + ".xml");

		if (layername == null || layername.isEmpty()) {
			if (LOGGER.isErrorEnabled())
				LOGGER.error(
						"GSFeatureTypeEncoder has no valid name associated, try using GSFeatureTypeEncoder.setName(String)");
			return false;
		}

		String configuredResult = HTTPUtils.putXml(putUrl.toString(), ftypeXml, this.gsuser, this.gspass);
		boolean updated = configuredResult != null;
		boolean configured = false;

		if (!updated) {
			LOGGER.warn(
					"Error in updating (" + configuredResult + ") " + workspace + ":" + storename + "/" + layername);
		} else {
			LOGGER.info("DB layer successfully updated (layer:" + layername + ")");

			if (layerEncoder == null) {
				if (LOGGER.isErrorEnabled())
					LOGGER.error("GSLayerEncoder is null: Unable to find the defaultStyle for this layer");
				return false;
			}

			configured = configureLayer(workspace, layername, layerEncoder);

			if (!configured) {
				LOGGER.warn("Error in configuring (" + configuredResult + ") " + workspace + ":" + storename + "/"
						+ layername);
			} else {
				LOGGER.info("DB layer successfully configured (layer:" + layername + ")");
			}
		}
		return updated && configured;
	}

	public boolean recalculate(final String workspace, final String storename, final String layername,
			final GSFeatureTypeEncoder fte, final GSLayerEncoder layerEncoder) {
		String ftypeXml = fte.toString();
		StringBuilder putUrl = new StringBuilder(restURL).append("/rest/workspaces/").append(workspace)
				.append("/datastores/").append(storename).append("/featuretypes/")
				.append(layername + "?recalculate=nativebbox,latlonbbox");

		if (layername == null || layername.isEmpty()) {
			if (LOGGER.isErrorEnabled())
				LOGGER.error(
						"GSFeatureTypeEncoder has no valid name associated, try using GSFeatureTypeEncoder.setName(String)");
			return false;
		}
		String configuredResult = HTTPUtils.putXml(putUrl.toString(), null, this.gsuser, this.gspass);
		/*
		 * String configuredResult = HTTPUtils.putXml(putUrl.toString(),
		 * ftypeXml, this.gsuser, this.gspass);
		 */
		boolean updated = configuredResult != null;
		boolean configured = false;

		if (!updated) {
			LOGGER.warn(
					"Error in updating (" + configuredResult + ") " + workspace + ":" + storename + "/" + layername);
		} else {
			LOGGER.info("DB layer successfully updated (layer:" + layername + ")");

			if (layerEncoder == null) {
				if (LOGGER.isErrorEnabled())
					LOGGER.error("GSLayerEncoder is null: Unable to find the defaultStyle for this layer");
				return false;
			}

			configured = configureLayer(workspace, layername, layerEncoder);

			if (!configured) {
				LOGGER.warn("Error in configuring (" + configuredResult + ") " + workspace + ":" + storename + "/"
						+ layername);
			} else {
				LOGGER.info("DB layer successfully configured (layer:" + layername + ")");
			}
		}
		return updated && configured;
	}
}
