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
import java.util.Properties;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.git.gdsbuilder.generalization.Simplification;
import com.git.gdsbuilder.generalization.impl.SimplificationImpl;
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


	}
}