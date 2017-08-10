package com.git.opengds.editor.service;

import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.edit.dxf.EditDXFCollection;
import com.git.gdsbuilder.edit.dxf.EditDXFLayerCollectionList;
import com.git.gdsbuilder.edit.ngi.EditNGICollection;
import com.git.gdsbuilder.edit.ngi.EditNGILayerCollectionList;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayer;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayerList;
import com.git.gdsbuilder.type.ngi.layer.DTNGILayer;
import com.git.gdsbuilder.type.ngi.layer.DTNGILayerList;
import com.git.opengds.editor.EditDTLayerCondition;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.parser.json.BuilderJSONDXFParser;
import com.git.opengds.parser.json.BuilderJSONNGIParser;
import com.git.opengds.user.domain.UserVO;

@Service
public class EditDTLayerServiceImpl implements EditDTLayerService {

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

	/*
	 * public EditLayerServiceImpl(UserVO userVO) { // TODO Auto-generated
	 * constructor stub editDBManager = new EditDBManagerServiceImpl(userVO);
	 * geoserver = new GeoserverServiceImpl(userVO); }
	 */

	@Override
	public void editLayer(UserVO userVO, JSONObject layerEditObj) throws Exception {

		String src = "5186";

		Iterator layerIterator = layerEditObj.keySet().iterator();
		while (layerIterator.hasNext()) {
			String type = (String) layerIterator.next();
			if (type.equals(isNgi)) {
				EditNGILayerCollectionList edtQA20CollectionListObj = BuilderJSONNGIParser
						.parseEditLayerObj(layerEditObj, type);
				for (int i = 0; i < edtQA20CollectionListObj.size(); i++) {
					EditNGICollection editCollection = edtQA20CollectionListObj.get(i);
					String collectionName = editCollection.getCollectionName();
					EditDTLayerCondition condition = new EditDTLayerCondition();
					// collection 중복 여부 확인
					Integer collectionIdx = editDBManager.checkNGILayerCollectionName(userVO, collectionName);
					if (editCollection.isCreated()) {
						if (collectionIdx != null) {
							// 1. 중복되었을 시(이미 존재하는 collection에 레이어 테이블만
							// create)
							DTNGILayerList createLayerList = editCollection.getCreateLayerList();
							for (int j = 0; j < createLayerList.size(); j++) {
								DTNGILayer createLayer = createLayerList.get(j);
								String layerName = createLayer.getLayerName();
								boolean isSuccessed = editDBManager.createNGILayer(userVO, isNgi, collectionIdx,
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
							Integer insertIdx = editDBManager.createNGILayerCollection(userVO, isNgi, editCollection);
							DTNGILayerList createLayerList = editCollection.getCreateLayerList();
							for (int j = 0; j < createLayerList.size(); j++) {
								DTNGILayer createLayer = createLayerList.get(j);
								String layerName = createLayer.getLayerName();
								boolean isSuccessed = editDBManager.createNGILayer(userVO, isNgi, insertIdx,
										collectionName, createLayer, src);
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
						DTNGILayerList modifiedLayerList = editCollection.getModifiedLayerList();
						for (int j = 0; j < modifiedLayerList.size(); j++) {
							DTNGILayer modifiedLayer = modifiedLayerList.get(j);
							String layerName = modifiedLayer.getLayerName();
							String originName = modifiedLayer.getOriginLayerName();
							Map<String, Object> geoLayerList = editCollection.getGeoLayerList();
							Map<String, Object> geoLayer = (Map<String, Object>) geoLayerList.get(originName);
							boolean isSuccessed = editDBManager.modifyNGILayer(userVO, isNgi, collectionIdx,
									collectionName, modifiedLayer, geoLayer);
							if (isSuccessed) {
								condition.putSuccessedLayers(collectionName, layerName);
							} else {
								condition.putFailedLayers(collectionName, layerName);
							}
						}
					}
					if (editCollection.isDeleted()) {
						if (editCollection.isDeleteAll()) {
							String groupName = "gro" + "_" + type + "_" + collectionName;
							geoserver.removeGeoserverGroupLayer(userVO, groupName);
						}
						DTNGILayerList layerList = editCollection.getDeletedLayerList();
						for (int j = 0; j < layerList.size(); j++) {
							DTNGILayer layer = layerList.get(j);
							editDBManager.dropNGILayer(userVO, isNgi, collectionIdx, collectionName, layer);
						}
						if (editCollection.isDeleteAll()) {
							editDBManager.deleteNGILayerCollection(userVO, collectionIdx);
						}
					}
				}
			} else if (type.equals(isDxf)) {
				EditDXFLayerCollectionList edtCollectionList = BuilderJSONDXFParser.parseEditLayerObj(layerEditObj,
						type);
				for (int i = 0; i < edtCollectionList.size(); i++) {
					EditDXFCollection editCollection = edtCollectionList.get(i);
					String collectionName = editCollection.getCollectionName();
					EditDTLayerCondition condition = new EditDTLayerCondition();
					Integer collectionIdx = editDBManager.checkDXFLayerCollectionName(userVO, collectionName);
					if (editCollection.isCreated()) {
						if (collectionIdx != null) {
							// 1. 중복되었을 시(이미 존재하는 collection에 레이어 테이블만
							// create)
							DTDXFLayerList createLayerList = editCollection.getCreateLayerList();
							for (int j = 0; j < createLayerList.size(); j++) {
								DTDXFLayer createLayer = createLayerList.get(j);
								String layerId = createLayer.getLayerID();
								boolean isSuccessed = editDBManager.createDXFLayer(userVO, isDxf, collectionIdx,
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
							Integer insertIdx = editDBManager.createDXFLayerCollection(userVO, isDxf, editCollection);
							DTDXFLayerList createLayerList = editCollection.getCreateLayerList();
							for (int j = 0; j < createLayerList.size(); j++) {
								DTDXFLayer createLayer = createLayerList.get(j);
								String layerId = createLayer.getLayerID();
								boolean isSuccessed = editDBManager.createDXFLayer(userVO, isDxf, insertIdx,
										collectionName, createLayer, src);
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
						DTDXFLayerList modifiedLayerList = editCollection.getModifiedLayerList();
						for (int j = 0; j < modifiedLayerList.size(); j++) {
							DTDXFLayer modifiedLayer = modifiedLayerList.get(j);
							String layerId = modifiedLayer.getLayerID();
							String originID = modifiedLayer.getOriginLayerID();
							Map<String, Object> geoLayerList = editCollection.getGeoLayerList();
							Map<String, Object> geoLayer = (Map<String, Object>) geoLayerList.get(originID);
							boolean isSuccessed = editDBManager.modifyDXFLayer(userVO, isDxf, collectionIdx,
									collectionName, modifiedLayer, geoLayer);
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
							geoserver.removeGeoserverGroupLayer(userVO, groupName);
						}
						DTDXFLayerList layerList = editCollection.getDeletedLayerList();
						for (int j = 0; j < layerList.size(); j++) {
							DTDXFLayer layer = layerList.get(j);
							editDBManager.dropDXFLayer(userVO, isDxf, collectionIdx, collectionName, layer);
						}
						if (editCollection.isDeleteAll()) {
							editDBManager.deleteDXFLayerCollectionTablesCommon(userVO, collectionIdx);
							editDBManager.deleteDXFLayerCollection(userVO, collectionIdx);
						}
					}
				}
			} else if (type.equals(isShp)) {

			}
		}
	}
}
