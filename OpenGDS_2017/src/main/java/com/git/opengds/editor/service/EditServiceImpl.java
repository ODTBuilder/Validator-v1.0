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

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;

import javax.inject.Inject;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.edit.qa20.EditQA20Collection;
import com.git.gdsbuilder.edit.qa20.EditQA20LayerCollectionList;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.opengds.file.ngi.service.QA20DBManagerService;
import com.git.opengds.file.ngi.service.QA20FileUploadService;
import com.git.opengds.parser.json.BuilderJSONParser;

@Service
public class EditServiceImpl implements EditService {

	protected static final String none = "none";
	protected static final String isModified = "modify";
	protected static final String isCreated = "create";
	protected static final String isDeleted = "remove";

	protected static final String isNgi = "ngi";
	protected static final String isDxf = "dxf";
	protected static final String isShp = "shp";

	@Inject
	EditDBManagerService editDBManager;

	@Inject
	QA20DBManagerService qa20DBManager;

	@Override
	public void editTest() throws Exception {

		JSONParser parser = new JSONParser();

		// 옵션 넘겨 받음
		Object obj = parser.parse(new FileReader("D:\\serverEdit.txt"));
		JSONObject collectionObject = (JSONObject) obj;

		JSONObject layerEditObj = (JSONObject) collectionObject.get("layer");
		Map<String, Object> edtCollectionListObj = BuilderJSONParser.parseEditLayerObj(layerEditObj);
		Iterator edtLayerIterator = edtCollectionListObj.keySet().iterator();
		while (edtLayerIterator.hasNext()) {
			String type = (String) edtLayerIterator.next();
			EditQA20LayerCollectionList edtCollectionList = (EditQA20LayerCollectionList) edtCollectionListObj
					.get(type);
			if (type.equals(this.isNgi)) {
				for (int i = 0; i < edtCollectionList.size(); i++) {
					EditQA20Collection editCollection = edtCollectionList.get(i);
					String collectionName = editCollection.getCollectionName();
					// collection 중복 여부 확인
					Integer collectionIdx = editDBManager.checkCollectionName(collectionName);
					if (collectionIdx != null) {
						// 1. 중복되었을 시(이미 존재하는 collection에 레이어 테이블만 create)

					} else {
						// 2. 중복되지 않았을 시 collection insert 후 레이어 테이블 create
						editDBManager.createQa20LayerCollection(type, editCollection);
					}
				}

			} else if (type.equals(this.isDxf)) {

			} else if (type.equals(this.isShp)) {

			}
		}

		// JSONObject featureEditObj = (JSONObject)
		// collectionObject.get("feature");
		// BuilderJSONParser.parseEditFeatureObj(featureEditObj);

	}
}
