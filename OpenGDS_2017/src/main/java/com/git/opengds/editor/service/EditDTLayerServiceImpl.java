package com.git.opengds.editor.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.inject.Inject;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.gdsbuilder.edit.dxf.EditDXFLayerCollection;
import com.git.gdsbuilder.edit.dxf.EditDXFLayerCollectionList;
import com.git.gdsbuilder.edit.ngi.EditNGILayerCollection;
import com.git.gdsbuilder.edit.ngi.EditNGILayerCollectionList;
import com.git.gdsbuilder.edit.shp.EditSHPLayerCollection;
import com.git.gdsbuilder.edit.shp.EditSHPLayerCollectionList;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayer;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayerList;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.ngi.layer.DTNGILayer;
import com.git.gdsbuilder.type.ngi.layer.DTNGILayerList;
import com.git.gdsbuilder.type.shp.layer.DTSHPLayer;
import com.git.gdsbuilder.type.shp.layer.DTSHPLayerList;
import com.git.opengds.editor.EditDTLayerCondition;
import com.git.opengds.geoserver.service.GeoserverService;
import com.git.opengds.parser.json.BuilderJSONDXFParser;
import com.git.opengds.parser.json.BuilderJSONNGIParser;
import com.git.opengds.parser.json.BuilderJSONSHPParser;
import com.git.opengds.user.domain.UserVO;
import com.vividsolutions.jts.io.ParseException;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class EditDTLayerServiceImpl implements EditDTLayerService {

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
	GeoserverService geoserverService;

	@Override
	public boolean editLayer(UserVO userVO, JSONObject layerEditObj) throws Exception {

		String src = "5186";

		boolean isSuccessed = false;

		Iterator layerIterator = layerEditObj.keySet().iterator();
		while (layerIterator.hasNext()) {
			String type = (String) layerIterator.next();
			if (type.equals(isNgi)) {
				isSuccessed = editNGILayer(userVO, layerEditObj, type);
			} else if (type.equals(isDxf)) {
				isSuccessed = editDXFLayer(userVO, layerEditObj, type);
			} else if (type.equals(isShp)) {
				isSuccessed = editSHPLayer(userVO, layerEditObj, type);
			}
		}
		return isSuccessed;
	}

	private boolean editSHPLayer(UserVO userVO, JSONObject layerEditObj, String type)
			throws FileNotFoundException, IOException, ParseException, SchemaException {
		String src = "5186";

		try {
			EditSHPLayerCollectionList edtCollectionList = BuilderJSONSHPParser.parseEditLayerObj(layerEditObj, type);
			for (int i = 0; i < edtCollectionList.size(); i++) {
				EditSHPLayerCollection editCollection = edtCollectionList.get(i);
				String collectionName = editCollection.getCollectionName();
				EditDTLayerCondition condition = new EditDTLayerCondition();
				Integer collectionIdx = editDBManager.selectSHPLayerCollectionIdx(userVO, collectionName);
				if (editCollection.isCreated()) {
					if (collectionIdx != null) {
						// 1. 중복되었을 시(이미 존재하는 collection에 레이어 테이블만
						// create)
						DTSHPLayerList createLayerList = editCollection.getCreateLayerList();
						for (int j = 0; j < createLayerList.size(); j++) {
							DTSHPLayer createLayer = createLayerList.get(j);
							String layerName = createLayer.getLayerName();
							boolean isSuccessed = editDBManager.createSHPLayer(userVO, isShp, collectionIdx,
									collectionName, createLayer, src);
							if (isSuccessed) {
								GeoLayerInfo layerInfo = new GeoLayerInfo();
								layerInfo.setOriginSrc(src);
								layerInfo.setTransSrc(src);
								layerInfo.setFileType("shp");
								layerInfo.setFileName(collectionName);
								String layerType = createLayer.getLayerType();
								String layerId = createLayer.getLayerName();
								layerInfo.putLayerName(layerId + "_" + layerType);
								layerInfo.putLayerType(layerId, layerType);
								geoserverService.dbLayerPublishGeoserver(userVO, layerInfo);
								condition.putSuccessedLayers(collectionName, layerName);
								condition.putFailedLayers(collectionName, layerName);
							}
						}
					} else {
						// 2. 중복되지 않았을 시 collection insert 후 레이어 테이블
						// create
						Integer insertIdx = editDBManager.createSHPLayerCollection(userVO, isShp, editCollection);
						DTSHPLayerList createLayerList = editCollection.getCreateLayerList();
						for (int j = 0; j < createLayerList.size(); j++) {
							DTSHPLayer createLayer = createLayerList.get(j);
							String layerName = createLayer.getLayerName();
							boolean isSuccessed = editDBManager.createSHPLayer(userVO, isShp, insertIdx, collectionName,
									createLayer, src);
							if (isSuccessed) {
								GeoLayerInfo layerInfo = new GeoLayerInfo();
								layerInfo.setOriginSrc(src);
								layerInfo.setTransSrc(src);
								layerInfo.setFileType("shp");
								layerInfo.setFileName(collectionName);
								String layerType = createLayer.getLayerType();
								String layerId = createLayer.getLayerName();
								layerInfo.putLayerName(layerId + "_" + layerType);
								layerInfo.putLayerType(layerId, layerType);
								geoserverService.dbLayerPublishGeoserver(userVO, layerInfo);
								condition.putSuccessedLayers(collectionName, layerName);
							} else {
								condition.putFailedLayers(collectionName, layerName);
							}
						}
					}
				}
				if (editCollection.isModified()) {
				}
				if (editCollection.isDeleted()) {
					DTSHPLayerList layerList = editCollection.getDeletedLayerList();
					
					for (int j = 0; j < layerList.size(); j++) {
						DTSHPLayer layer = layerList.get(j);
						String layerName = layer.getLayerName();
						boolean isSuccessed = editDBManager.dropSHPLayer(userVO, isShp, collectionIdx, collectionName,
								layer);
						if(!editCollection.isDeleted()){
							if (isSuccessed) {
								String layerTableName = "geo" + "_" + type + "_" + collectionName + "_" + layerName;
								String groupName = "gro" + "_" + type + "_" + collectionName;
								isSuccessed = geoserverService.removeDTGeoserverLayer(userVO, groupName, layerTableName);
							}
						}
					}
					if (editCollection.isDeleteAll()) {
						String groupName = "gro" + "_" + type + "_" + collectionName;
						geoserverService.removeDTGeoserverAllLayer(userVO, groupName);
					}
					if (editCollection.isDeleteAll()) {
						editDBManager.deleteSHPLayerCollection(userVO, collectionIdx);
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private boolean editDXFLayer(UserVO userVO, JSONObject layerEditObj, String type)
			throws FileNotFoundException, IOException, ParseException, SchemaException {

		String src = "5186";

		try {
			EditDXFLayerCollectionList edtCollectionList = BuilderJSONDXFParser.parseEditLayerObj(layerEditObj, type);
			for (int i = 0; i < edtCollectionList.size(); i++) {
				EditDXFLayerCollection editCollection = edtCollectionList.get(i);
				String collectionName = editCollection.getCollectionName();
				EditDTLayerCondition condition = new EditDTLayerCondition();
				Integer collectionIdx = editDBManager.selectDXFLayerCollectionIdx(userVO, collectionName);
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
								GeoLayerInfo layerInfo = new GeoLayerInfo();
								layerInfo.setOriginSrc(src);
								layerInfo.setTransSrc(src);
								layerInfo.setFileType(isDxf);
								layerInfo.setFileName(collectionName);
								String layerType = createLayer.getLayerType();
								layerInfo.putLayerName(layerId);
								layerInfo.putLayerType(layerId, layerType);
								geoserverService.dbLayerPublishGeoserver(userVO, layerInfo);
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
							boolean isSuccessed = editDBManager.createDXFLayer(userVO, isDxf, insertIdx, collectionName,
									createLayer, src);
							if (isSuccessed) {
								GeoLayerInfo layerInfo = new GeoLayerInfo();
								layerInfo.setOriginSrc(src);
								layerInfo.setTransSrc(src);
								layerInfo.setFileType(isDxf);
								layerInfo.setFileName(collectionName);
								String layerType = createLayer.getLayerType();
								layerInfo.putLayerName(layerId);
								layerInfo.putLayerType(layerId, layerType);
								geoserverService.dbLayerPublishGeoserver(userVO, layerInfo);
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
						boolean isSuccessed = editDBManager.modifyDXFLayer(userVO, isDxf, collectionIdx, collectionName,
								modifiedLayer, geoLayer);
						if (isSuccessed) {
							String originalName = (String) geoLayer.get("orignalName");
							String title = (String) geoLayer.get("title");
							String summary = (String) geoLayer.get("summary");
							boolean attChangeFlag = (Boolean) geoLayer.get("attChangeFlag");
							String tableName = "geo_" + type + "_" + collectionName + "_" + originalName;
							String tableNameCurrent = "geo_" + type + "_" + collectionName + "_" + layerId;
							geoserverService.updateFeatureType(userVO, tableName, tableNameCurrent, title, summary, "",
									attChangeFlag);
							condition.putSuccessedLayers(collectionName, layerId);
						} else {
							condition.putFailedLayers(collectionName, layerId);
						}
					}
				}

				if (editCollection.isDeleted()) {
					DTDXFLayerList layerList = editCollection.getDeletedLayerList();
					
					for (int j = 0; j < layerList.size(); j++) {
						DTDXFLayer layer = layerList.get(j);
						String layerId = layer.getLayerID();
						boolean isSuccessed = editDBManager.dropDXFLayer(userVO, isDxf, collectionIdx, collectionName,
								layer);
						if(!editCollection.isDeleted()){
							if (isSuccessed) {
								String layerTableName = "geo" + "_" + type + "_" + collectionName + "_" + layerId;
								String groupName = "gro" + "_" + type + "_" + collectionName;
								isSuccessed = geoserverService.removeDTGeoserverLayer(userVO, groupName, layerTableName);
							}
						}
					}
					if (editCollection.isDeleteAll()) {
						String groupName = "gro" + "_" + type + "_" + collectionName;
						geoserverService.removeDTGeoserverAllLayer(userVO, groupName);
					}
					if (editCollection.isDeleteAll()) {
						editDBManager.deleteDXFLayerCollectionTablesCommon(userVO, collectionIdx);
						editDBManager.deleteDXFLayerCollection(userVO, collectionIdx);
					}

				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private boolean editNGILayer(UserVO userVO, JSONObject layerEditObj, String type)
			throws FileNotFoundException, IOException, ParseException, SchemaException {

		String src = "5186";

		try {
			EditNGILayerCollectionList edtQA20CollectionListObj = BuilderJSONNGIParser.parseEditLayerObj(layerEditObj,
					type);
			for (int i = 0; i < edtQA20CollectionListObj.size(); i++) {
				EditNGILayerCollection editCollection = edtQA20CollectionListObj.get(i);
				String collectionName = editCollection.getCollectionName();
				EditDTLayerCondition condition = new EditDTLayerCondition();
				// collection 중복 여부 확인
				Integer collectionIdx = editDBManager.selectNGILayerCollectionIdx(userVO, collectionName);
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
								GeoLayerInfo layerInfo = new GeoLayerInfo();
								layerInfo.setOriginSrc(src);
								layerInfo.setTransSrc(src);
								layerInfo.setFileType(isNgi);
								layerInfo.setFileName(collectionName);
								String layerType = createLayer.getLayerType();
								layerInfo.putLayerName(layerName);
								layerInfo.putLayerType(layerName, layerType);
								geoserverService.dbLayerPublishGeoserver(userVO, layerInfo);
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
							boolean isSuccessed = editDBManager.createNGILayer(userVO, isNgi, insertIdx, collectionName,
									createLayer, src);
							if (isSuccessed) {
								GeoLayerInfo layerInfo = new GeoLayerInfo();
								layerInfo.setOriginSrc(src);
								layerInfo.setTransSrc(src);
								layerInfo.setFileType(isNgi);
								layerInfo.setFileName(collectionName);
								String layerType = createLayer.getLayerType();
								layerInfo.putLayerName(layerName);
								layerInfo.putLayerType(layerName, layerType);
								geoserverService.dbLayerPublishGeoserver(userVO, layerInfo);
								condition.putSuccessedLayers(collectionName, layerName);
							} else {
								condition.putFailedLayers(collectionName, layerName);
							}
						}
					}
				}
				if (editCollection.isModified()) {
					// 1. 수정할 레이어
					// DTNGILayerList modifiedLayerList =
					// editCollection.getModifiedLayerList();
					// for (int j = 0; j < modifiedLayerList.size(); j++) {
					// DTNGILayer modifiedLayer = modifiedLayerList.get(j);
					// String layerName = modifiedLayer.getLayerName();
					// String originName = modifiedLayer.getOriginLayerName();
					// Map<String, Object> geoLayerList =
					// editCollection.getGeoLayerList();
					// Map<String, Object> geoLayer = (Map<String, Object>)
					// geoLayerList.get(originName);
					// boolean isSuccessed =
					// editDBManager.modifyNGILayer(userVO, isNgi,
					// collectionIdx, collectionName,
					// modifiedLayer, geoLayer);
					// if (isSuccessed) {
					// condition.putSuccessedLayers(collectionName, layerName);
					// } else {
					// condition.putFailedLayers(collectionName, layerName);
					// }
					// }
				}
				if (editCollection.isDeleted()) {
					DTNGILayerList layerList = editCollection.getDeletedLayerList();
					
					for (int j = 0; j < layerList.size(); j++) {
						DTNGILayer layer = layerList.get(j);
						String layerName = layer.getLayerName();
						boolean isSuccess = editDBManager.dropNGILayer(userVO, isNgi, collectionIdx, collectionName,
								layer);
						if(!editCollection.isDeleted()){
							if (isSuccess) {
								String layerTableName = "geo" + "_" + type + "_" + collectionName + "_" + layerName;
								String groupName = "gro" + "_" + type + "_" + collectionName;
								geoserverService.removeDTGeoserverLayer(userVO, groupName, layerTableName);
							}
						}
					}
					if (editCollection.isDeleteAll()) {
						String groupName = "gro" + "_" + type + "_" + collectionName;
						geoserverService.removeDTGeoserverAllLayer(userVO, groupName);
					}
					if (editCollection.isDeleteAll()) {
						editDBManager.deleteNGILayerCollection(userVO, collectionIdx);
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
