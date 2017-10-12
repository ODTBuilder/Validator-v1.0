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
import java.util.HashMap;
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
import com.git.opengds.user.domain.UserVO;

@Service
public class GeneralizationServiceImpl implements GeneralizationService {

	@Inject
	GeneralizationLayerService generalizationLayerService;

	@Inject
	GeneralizationProgressService progressService;

	@Inject
	GeneralizationReportService generalizationReportService;

	private static final String URL;
	private static final String ID;

	protected static int requestSuccess = 0;
	protected static int genProgresing = 1;
	protected static int genSuccess = 2;
	protected static int genFail = 3;
	protected static int genLayerSuccess = 4;
	protected static int genLayerFail = 5;

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
			for (int i = 0; i < layersArray.size(); i++) {
				String layerName = (String) layersArray.get(i);

				int con = layerName.indexOf("_");
				if (con != -1) {
					String preName = layerName.substring(0, con); // 구분코드
					String cutLayerName = layerName.substring(con + 1);
					int dash = cutLayerName.indexOf("_");
					String fileType = cutLayerName.substring(0, dash);

					String lastName = cutLayerName.substring(dash + 1); // 파일명_레이어명
					int div = lastName.indexOf("_");
					String fileName = lastName.substring(0, div);

					// state 0 : 일반화 요청
					Integer pIdx = progressService.setStateToRequest(userVO, requestSuccess, fileName, fileType,
							layerName);

					String lastLayerName = lastName.substring(div + 1);

					int layerTypeDash = lastLayerName.lastIndexOf("_");
					String exTypelayerName = lastLayerName.substring(0, layerTypeDash);
					String layerType = lastLayerName.substring(layerTypeDash + 1);
					String suLayerType = "";

					String groupName = "gro_" + fileType + "_" + fileName;

					String src = "4326";

					// state 1 : 일반화 진행중
					progressService.setStateToProgressing(userVO, genProgresing, fileType, pIdx);
					SimpleFeatureCollection sfc = null;
					SimpleFeatureSource source = dataStore.getFeatureSource(layerName);
					sfc = source.getFeatures();
					DTGeneralEAfLayer resultLayer = new GeneralizationFactoryImpl()
							.createGeneralization(sfc, simplificationOption, eliminationOption, order, topoFlag)
							.getGeneralization();

					// state 2 : 일반화 성공
					if (resultLayer != null) {
						progressService.setStateToProgressing(userVO, genSuccess, fileType, pIdx);
						// 일반화 결과 테이블 생성 및 서버 발행
						String genTbName = generalizationLayerService.publishGenLayer(userVO, resultLayer, fileType,
								fileName, layerName, layerType, src);
						if (genTbName != null) {
							// state 4 : 발행 성공
							progressService.setStateToProgressing(userVO, genLayerSuccess, fileType, pIdx);
							DTGeneralReport resultReport = resultLayer.getReport();
							boolean isResult = generalizationReportService.insertGenralResult(userVO, fileName,
									layerName, genTbName, resultReport);
							if (isResult) {
								progressService.setStateToResponse(userVO, fileType, genTbName, pIdx);
							}
						} else {
							// state 5 : 발행 실패
							progressService.setStateToProgressing(userVO, genLayerFail, fileType, pIdx);
						}
					} else {
						// state 3 : 일반화 실패
						progressService.setStateToProgressing(userVO, genFail, fileType, pIdx);
					}
				}
			}
		}
		System.out.println("성공");
	}
}