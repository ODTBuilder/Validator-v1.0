package com.git.opengds.editor.service;

import java.util.Iterator;
import java.util.Map;

import javax.inject.Inject;

import org.json.simple.JSONObject;

import com.git.gdsbuilder.edit.qa20.EditQA20Collection;
import com.git.gdsbuilder.edit.qa20.EditQA20LayerCollectionList;
import com.git.gdsbuilder.type.qa20.layer.QA20Layer;
import com.git.gdsbuilder.type.qa20.layer.QA20LayerList;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.parser.json.BuilderJSONParser;

public class EditLayerServiceImpl implements EditLayerService {

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
	GeoserverService geoserver;

	@Override
	public void editLayer(JSONObject layerEditObj)
			throws Exception {

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
					EditLayerCondition condition = new EditLayerCondition();
					// collection 중복 여부 확인
					Integer collectionIdx = editDBManager.checkQA20LayerCollectionName(collectionName);
					if (editCollection.isCreated()) {
						if (collectionIdx != null) {
							// 1. 중복되었을 시(이미 존재하는 collection에 레이어 테이블만 create)
							QA20LayerList createLayerList = editCollection.getCreateLayerList();
							for (int j = 0; j < createLayerList.size(); j++) {
								QA20Layer createLayer = createLayerList.get(j);
								String layerName = createLayer.getLayerName();
								boolean isSuccessed = editDBManager.createQA20Layer(type, collectionIdx, collectionName,
										createLayer);
								if (isSuccessed) {
									condition.putSuccessedLayer(collectionName, layerName);
								} else {
									condition.putFailedLayer(collectionName, layerName);
								}
							}
						} else {
							// 2. 중복되지 않았을 시 collection insert 후 레이어 테이블 create
							Integer insertIdx = editDBManager.createQA20LayerCollection(type, editCollection);
							QA20LayerList createLayerList = editCollection.getCreateLayerList();
							for (int j = 0; j < createLayerList.size(); j++) {
								QA20Layer createLayer = createLayerList.get(j);
								String layerName = createLayer.getLayerName();
								boolean isSuccessed = editDBManager.createQA20Layer(type, insertIdx, collectionName,
										createLayer);
								if (isSuccessed) {
									condition.putSuccessedLayer(collectionName, layerName);
								} else {
									condition.putFailedLayer(collectionName, layerName);
								}
							}
						}
					}
					if (editCollection.isModified()) {
						// 1. 수정할 레이어
						QA20LayerList modifiedLayerList = editCollection.getModifiedLayerList();
						for (int j = 0; j < modifiedLayerList.size(); j++) {
							QA20Layer modifiedLayer = modifiedLayerList.get(j);
							String layerName = modifiedLayer.getLayerName();
							Map<String, Object> geoLayerList = editCollection.getGeoLayerList();
							Map<String, Object> geoLayer = (Map<String, Object>) geoLayerList.get(layerName);
							boolean isSuccessed = editDBManager.modifyQA20Layer(type, collectionIdx, modifiedLayer,
									geoLayer);
							if (isSuccessed) {
								condition.putSuccessedLayer(collectionName, layerName);
							} else {
								condition.putFailedLayer(collectionName, layerName);
							}
						}
					}
					if (editCollection.isDeleted()) {
						QA20LayerList layerList = editCollection.getDeletedLayerList();
						for (int j = 0; j < layerList.size(); j++) {
							QA20Layer layer = layerList.get(j);
							editDBManager.dropQA20Layer(type, collectionIdx, collectionName, layer);
						}
						if (editCollection.isDeleteAll()) {
							geoserver.removeGeoserverGroupLayer(editCollection.getCollectionName());
						}
					}
				}
			} else if (type.equals(this.isDxf)) {

			} else if (type.equals(this.isShp)) {

			}
		}

	}

}
