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

package com.git.opengds.generalization.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.generalization.data.res.DTGeneralEAfLayer;
import com.git.gdsbuilder.generalization.impl.GeneralizationImpl.GeneralizationOrder;
import com.git.gdsbuilder.generalization.impl.factory.GeneralizationFactoryImpl;
import com.git.gdsbuilder.generalization.opt.EliminationOption;
import com.git.gdsbuilder.generalization.opt.SimplificationOption;
import com.git.gdsbuilder.generalization.rep.DTGeneralReport;
import com.git.gdsbuilder.generalization.rep.DTGeneralReport.DTGeneralReportNumsType;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.shp.collection.DTSHPLayerCollection;
import com.git.gdsbuilder.type.shp.layer.DTSHPLayer;
import com.git.opengds.editor.service.EditDBManagerService;
import com.git.opengds.file.shp.service.SHPDBManagerService;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.upload.domain.FileMeta;
import com.git.opengds.user.domain.UserVO;

import sun.print.resources.serviceui;

@Service
public class GeneralizationServiceImpl implements GeneralizationService {

	@Inject
	SHPDBManagerService shpDBManagerService;

	@Inject
	GeoserverService geoserverService;

	private static final String URL;
	private static final String ID;

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
	}

	@Override
	public void exeGeneralization(final UserVO userVO, String jsonObject) throws Exception {

		// 일반화 객체 파라미터 선언
		SimplificationOption simplificationOption = null;
		EliminationOption eliminationOption = null;
		GeneralizationOrder order = GeneralizationOrder.UNKNOWN;

		JSONParser parser = new JSONParser();
		JSONObject jsonObj = (JSONObject) parser.parse(jsonObject);

		// String strTopoFlag = (String) jsonObj.get("topology");
		boolean topoFlag = (boolean) jsonObj.get("topology");
		;

		/*
		 * if(strTopoFlag.equals("true")){ topoFlag = true; }
		 */

		JSONArray orderArray = (JSONArray) jsonObj.get("order");

		JSONArray layersArray = (JSONArray) jsonObj.get("layers");

		// 일반화 레이어 로드
		String getCapabilities = URL + "/wfs?REQUEST=GetCapabilities&version=1.0.0";
		Map connectionParameters = new HashMap();
		connectionParameters.put("WFSDataStoreFactory:GET_CAPABILITIES_URL", getCapabilities);
		DataStore dataStore = DataStoreFinder.getDataStore(connectionParameters);

		if (orderArray.size() > 0) {
			JSONObject firOrderJSON = (JSONObject) orderArray.get(0);
			String firMethod = (String) firOrderJSON.get("method");

			if (firMethod.trim().equals("simplification")) {
				order = GeneralizationOrder.SIMPLIFICATION;
			}
			if (firMethod.trim().equals("elimination")) {
				order = GeneralizationOrder.ELIMINATION;
			}

			for (int i = 0; i < orderArray.size(); i++) {// 옵션생성
				JSONObject orderJSON = (JSONObject) orderArray.get(i);
				String method = (String) orderJSON.get("method");
				Double tolerance = Double.parseDouble((String) orderJSON.get("tolerance"));

				if (method.trim().equals("simplification")) {
					simplificationOption = new SimplificationOption(tolerance);
				}
				if (method.trim().equals("elimination")) {
					eliminationOption = new EliminationOption(tolerance);
				}
			}
		}

		if (layersArray.size() > 0) {
			String layerName = (String) layersArray.get(0);
			SimpleFeatureCollection sfc = null;
			SimpleFeatureSource source = dataStore.getFeatureSource(layerName);
			sfc = source.getFeatures();
			System.out.println("데이터 가져왔다");
			DTGeneralEAfLayer resultLayer = new GeneralizationFactoryImpl()
					.createGeneralization(sfc, simplificationOption, eliminationOption, order, topoFlag)
					.getGeneralization();

			SimpleFeatureCollection resultCollection = resultLayer.getCollection();
			DTSHPLayer shpLayer = new DTSHPLayer(layerName, "MultiLineString", resultCollection);

			// create GeoLayerInfo
			GeoLayerInfo layerInfo = new GeoLayerInfo();
			layerInfo.setFileType("shp");
			layerInfo.setFileName("Coast");
			layerInfo.setOriginSrc("4326");
			List<String> layerNames = new ArrayList<String>();
			layerNames.add(layerName);
			layerInfo.setLayerNames(layerNames);

			/*boolean geoLayerInfo = shpDBManagerService.createGenSHPLayer(userVO, "shp", 3, "Coast", shpLayer, "4326");
			// publish Layer
			if (geoLayerInfo) {
				FileMeta geoserverFileMeta = geoserverService.dbLayerPublishGeoserver(userVO, layerInfo);
				boolean isPublished = geoserverFileMeta.isServerPublishFlag();
				System.out.println(isPublished);
			}*/

			DTGeneralReport resultReport = resultLayer.getReport();
			System.out.println("entityNums pre :"
					+ resultReport.getDTGeneralReportNums(DTGeneralReportNumsType.ENTITY).getPreNum());
			System.out.println("entityNums after :"
					+ resultReport.getDTGeneralReportNums(DTGeneralReportNumsType.ENTITY).getAfNum());
			System.out.println(
					"pointNums pre :" + resultReport.getDTGeneralReportNums(DTGeneralReportNumsType.POINT).getPreNum());
			System.out.println("pointNums after :"
					+ resultReport.getDTGeneralReportNums(DTGeneralReportNumsType.POINT).getAfNum());
		}
	}
}