package com.git.opengds.editor.service;

import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.edit.qa10.EditQA10Collection;
import com.git.gdsbuilder.edit.qa10.EditQA10LayerCollectionList;
import com.git.gdsbuilder.edit.qa20.EditQA20Collection;
import com.git.gdsbuilder.edit.qa20.EditQA20LayerCollectionList;
import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa10.layer.QA10LayerList;
import com.git.gdsbuilder.type.qa20.layer.QA20Layer;
import com.git.gdsbuilder.type.qa20.layer.QA20LayerList;
import com.git.opengds.editor.EditLayerCondition;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.parser.json.BuilderJSONQA10Parser;
import com.git.opengds.parser.json.BuilderJSONQA20Parser;
import com.git.opengds.user.domain.UserVO;

@Service
public class EditLayerServiceImpl implements EditLayerService {

	protected static final String none = "none";
	protected static final String isModified = "modify";
	protected static final String isCreated = "create";
	protected static final String isDeleted = "remove";

	protected static final String isNgi = "ngi";
	protected static final String isDxf = "dxf";
	protected static final String isShp = "shp";

	@Autowired
	EditDBManagerService editDBManager;

	@Autowired
	GeoserverService geoserver;
	
/*	public EditLayerServiceImpl(UserVO userVO) {
		// TODO Auto-generated constructor stub
		editDBManager = new EditDBManagerServiceImpl(userVO);
		geoserver = new GeoserverServiceImpl(userVO);
	}*/


	@Override
	public void editLayer(UserVO userVO, JSONObject layerEditObj) throws Exception {

		String src = "5186";

		Iterator layerIterator = layerEditObj.keySet().iterator();
		while (layerIterator.hasNext()) {
			String type = (String) layerIterator.next();
			if (type.equals(isNgi)) {
				EditQA20LayerCollectionList edtQA20CollectionListObj = BuilderJSONQA20Parser
						.parseEditLayerObj(layerEditObj, type);
				for (int i = 0; i < edtQA20CollectionListObj.size(); i++) {
					EditQA20Collection editCollection = edtQA20CollectionListObj.get(i);
					String collectionName = editCollection.getCollectionName();
					EditLayerCondition condition = new EditLayerCondition();
					// collection 중복 여부 확인
					Integer collectionIdx = editDBManager.checkQA20LayerCollectionName(userVO,collectionName);
					if (editCollection.isCreated()) {
						if (collectionIdx != null) {
							// 1. 중복되었을 시(이미 존재하는 collection에 레이어 테이블만
							// create)
							QA20LayerList createLayerList = editCollection.getCreateLayerList();
							for (int j = 0; j < createLayerList.size(); j++) {
								QA20Layer createLayer = createLayerList.get(j);
								String layerName = createLayer.getLayerName();
								boolean isSuccessed = editDBManager.createQA20Layer(userVO,isNgi, collectionIdx,
										collectionName, createLayer, src);
								if (isSuccessed) {
									condition.putSuccessedLayers(collectionName, layerName);
								} else {
									condition.putFailedLayers(collectionName, layerName);
								}
							}
						} else {
							// 2. 중복되지 않았을 시 collection insert 후 레이어 테이블
							// create
							Integer insertIdx = editDBManager.createQA20LayerCollection(userVO,isNgi, editCollection);
							QA20LayerList createLayerList = editCollection.getCreateLayerList();
							for (int j = 0; j < createLayerList.size(); j++) {
								QA20Layer createLayer = createLayerList.get(j);
								String layerName = createLayer.getLayerName();
								boolean isSuccessed = editDBManager.createQA20Layer(userVO,isNgi, insertIdx, collectionName,
										createLayer, src);
								if (isSuccessed) {
									condition.putSuccessedLayers(collectionName, layerName);
								} else {
									condition.putFailedLayers(collectionName, layerName);
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
							String originName = modifiedLayer.getOriginLayerName();
							Map<String, Object> geoLayerList = editCollection.getGeoLayerList();
							Map<String, Object> geoLayer = (Map<String, Object>) geoLayerList.get(originName);
							boolean isSuccessed = editDBManager.modifyQA20Layer(userVO,isNgi, collectionIdx, collectionName,
									modifiedLayer, geoLayer);
							if (isSuccessed) {
								condition.putSuccessedLayers(collectionName, layerName);
							} else {
								condition.putFailedLayers(collectionName, layerName);
							}
						}
					}
					if (editCollection.isDeleted()) {
						QA20LayerList layerList = editCollection.getDeletedLayerList();
						for (int j = 0; j < layerList.size(); j++) {
							QA20Layer layer = layerList.get(j);
							editDBManager.dropQA20Layer(userVO,isNgi, collectionIdx, collectionName, layer);
						}
						if (editCollection.isDeleteAll()) {
							geoserver.removeGeoserverGroupLayer(userVO,editCollection.getCollectionName());
						}
					}
				}
			} else if (type.equals(isDxf)) {
				EditQA10LayerCollectionList edtCollectionList = BuilderJSONQA10Parser.parseEditLayerObj(layerEditObj,
						type);
				for (int i = 0; i < edtCollectionList.size(); i++) {
					EditQA10Collection editCollection = edtCollectionList.get(i);
					String collectionName = editCollection.getCollectionName();
					EditLayerCondition condition = new EditLayerCondition();
					Integer collectionIdx = editDBManager.checkQA10LayerCollectionName(userVO,collectionName);
					if (editCollection.isCreated()) {
						if (collectionIdx != null) {
							// 1. 중복되었을 시(이미 존재하는 collection에 레이어 테이블만
							// create)
							QA10LayerList createLayerList = editCollection.getCreateLayerList();
							for (int j = 0; j < createLayerList.size(); j++) {
								QA10Layer createLayer = createLayerList.get(j);
								String layerId = createLayer.getLayerID();
								boolean isSuccessed = editDBManager.createQA10Layer(userVO,isDxf, collectionIdx,
										collectionName, createLayer, src);
								if (isSuccessed) {
									condition.putSuccessedLayers(collectionName, layerId);
								} else {
									condition.putFailedLayers(collectionName, layerId);
								}
							}
						} else {
							// 2. 중복되지 않았을 시 collection insert 후 레이어 테이블
							// create
							Integer insertIdx = editDBManager.createQA10LayerCollection(userVO,isDxf, editCollection);
							QA10LayerList createLayerList = editCollection.getCreateLayerList();
							for (int j = 0; j < createLayerList.size(); j++) {
								QA10Layer createLayer = createLayerList.get(j);
								String layerId = createLayer.getLayerID();
								boolean isSuccessed = editDBManager.createQA10Layer(userVO,isDxf, insertIdx, collectionName,
										createLayer, src);
								if (isSuccessed) {
									condition.putSuccessedLayers(collectionName, layerId);
								} else {
									condition.putFailedLayers(collectionName, layerId);
								}
							}
						}
					}
					if (editCollection.isModified()) {
						// 1. 수정할 레이어
						QA10LayerList modifiedLayerList = editCollection.getModifiedLayerList();
						for (int j = 0; j < modifiedLayerList.size(); j++) {
							QA10Layer modifiedLayer = modifiedLayerList.get(j);
							String layerId = modifiedLayer.getLayerID();
							String originID = modifiedLayer.getOriginLayerID();
							Map<String, Object> geoLayerList = editCollection.getGeoLayerList();
							Map<String, Object> geoLayer = (Map<String, Object>) geoLayerList.get(originID);
							boolean isSuccessed = editDBManager.modifyQA10Layer(userVO,isDxf, collectionIdx, collectionName,
									modifiedLayer, geoLayer);
							if (isSuccessed) {
								condition.putSuccessedLayers(collectionName, layerId);
							} else {
								condition.putFailedLayers(collectionName, layerId);
							}
						}
					}
					if (editCollection.isDeleted()) {
						if (editCollection.isDeleteAll()) {
							String groupName = "gro" + "_" + type + "_" + collectionName;
							geoserver.removeGeoserverGroupLayer(userVO,groupName);
						}
						QA10LayerList layerList = editCollection.getDeletedLayerList();
						for (int j = 0; j < layerList.size(); j++) {
							QA10Layer layer = layerList.get(j);
							editDBManager.dropQA10Layer(userVO,isDxf, collectionIdx, collectionName, layer);
						}
						if (editCollection.isDeleteAll()) {
							editDBManager.deleteQA10LayerCollectionTablesCommon(userVO,collectionIdx);
							editDBManager.deleteQA10LayerCollection(userVO,collectionIdx);
						}
					}
				}
			} else if (type.equals(isShp)) {

			}
		}
	}
}
