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

package com.git.opengds.editor.service;

import java.io.FileReader;

import javax.inject.Inject;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import com.git.opengds.parser.json.BuilderJSONParser;

@Service
public class EditServiceImpl implements EditService {

	@Inject
	EditDBManagerService editDBManager;

	@Override
	public void editTest() throws Exception {

		JSONParser parser = new JSONParser();

		// 옵션 넘겨 받음
		Object obj = parser.parse(new FileReader("D:\\editFinal.txt"));
		JSONObject collectionObject = (JSONObject) obj;

		BuilderJSONParser.parseEditObj(collectionObject);

		// updata
		// EditLayerCollectionListParser collectionListParser = new
		// EditLayerCollectionListParser(collectionObject);
		// LayerCollectionList collecionList =
		// collectionListParser.getLayerCollectionList();
		// editDBManager.updateFeatures(collecionList);

		// insert

		// Delete

	}
}
