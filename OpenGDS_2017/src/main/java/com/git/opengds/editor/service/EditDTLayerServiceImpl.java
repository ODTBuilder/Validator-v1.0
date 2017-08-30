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
				editNGILayer(userVO, layerEditObj, type);
			} else if (type.equals(isDxf)) {
				editDXFLayer(userVO, layerEditObj, type);
			} else if (type.equals(isShp)) {
				editSHPLayer(userVO, layerEditObj, type);
			}
		}

	}

	private void editSHPLayer(UserVO userVO, JSONObject layerEditObj, String type)
			throws FileNotFoundException, IOException, ParseException, SchemaException {
		String src = "5186";
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
						boolean isSuccessed = editDBManager.createSHPLayer(userVO, isDxf, collectionIdx, collectionName,
								createLayer, src);
						if (isSuccessed) {
							condition.putSuccessedLayers(collectionName, layerName);
						} else {
							condition.putFailedLayers(collectionName, layerName);
						}
					}
				} else {
					// 2. 중복되지 않았을 시 collection insert 후 레이어 테이블
					// create
					Integer insertIdx = editDBManager.createSHPLayerCollection(userVO, isDxf, editCollection);
					DTSHPLayerList createLayerList = editCollection.getCreateLayerList();
					for (int j = 0; j < createLayerList.size(); j++) {
						DTSHPLayer createLayer = createLayerList.get(j);
						String layerName = createLayer.getLayerName();
						boolean isSuccessed = editDBManager.createSHPLayer(userVO, isDxf, insertIdx, collectionName,
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

			}
			if (editCollection.isDeleted()) {
				DTSHPLayerList layerList = editCollection.getDeletedLayerList();
				for (int j = 0; j < layerList.size(); j++) {
					DTSHPLayer layer = layerList.get(j);
					editDBManager.dropSHPLayer(userVO, isShp, collectionIdx, collectionName, layer);
				}
				if (editCollection.isDeleteAll()) {
					editDBManager.deleteSHPLayerCollection(userVO, collectionIdx);
				}
				if (editCollection.isDeleteAll()) {
					String groupName = "gro" + "_" + type + "_" + collectionName;
					geoserver.removeDTGeoserverAllLayer(userVO, groupName);
				}
			}
		}
	}

	private void editDXFLayer(UserVO userVO, JSONObject layerEditObj, String type)
			throws FileNotFoundException, IOException, ParseException, SchemaException {

		String src = "5186";

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
						boolean isSuccessed = editDBManager.createDXFLayer(userVO, isDxf, collectionIdx, collectionName,
								createLayer, src);
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
						boolean isSuccessed = editDBManager.createDXFLayer(userVO, isDxf, insertIdx, collectionName,
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
					editDBManager.dropDXFLayer(userVO, isDxf, collectionIdx, collectionName, layer);
				}
				if (editCollection.isDeleteAll()) {
					editDBManager.deleteDXFLayerCollectionTablesCommon(userVO, collectionIdx);
					editDBManager.deleteDXFLayerCollection(userVO, collectionIdx);
				}
				if (editCollection.isDeleteAll()) {
					String groupName = "gro" + "_" + type + "_" + collectionName;
					geoserver.removeDTGeoserverAllLayer(userVO, groupName);
					
				}
			}
		}
	}

	private void editNGILayer(UserVO userVO, JSONObject layerEditObj, String type)
			throws FileNotFoundException, IOException, ParseException, SchemaException {

		String src = "5186";

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
						boolean isSuccessed = editDBManager.createNGILayer(userVO, isNgi, collectionIdx, collectionName,
								createLayer, src);
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
						boolean isSuccessed = editDBManager.createNGILayer(userVO, isNgi, insertIdx, collectionName,
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
				DTNGILayerList modifiedLayerList = editCollection.getModifiedLayerList();
				for (int j = 0; j < modifiedLayerList.size(); j++) {
					DTNGILayer modifiedLayer = modifiedLayerList.get(j);
					String layerName = modifiedLayer.getLayerName();
					String originName = modifiedLayer.getOriginLayerName();
					Map<String, Object> geoLayerList = editCollection.getGeoLayerList();
					Map<String, Object> geoLayer = (Map<String, Object>) geoLayerList.get(originName);
					boolean isSuccessed = editDBManager.modifyNGILayer(userVO, isNgi, collectionIdx, collectionName,
							modifiedLayer, geoLayer);
					if (isSuccessed) {
						condition.putSuccessedLayers(collectionName, layerName);
					} else {
						condition.putFailedLayers(collectionName, layerName);
					}
				}
			}
			if (editCollection.isDeleted()) {
				DTNGILayerList layerList = editCollection.getDeletedLayerList();
				for (int j = 0; j < layerList.size(); j++) {
					DTNGILayer layer = layerList.get(j);
					editDBManager.dropNGILayer(userVO, isNgi, collectionIdx, collectionName, layer);
				}
				if (editCollection.isDeleteAll()) {
					editDBManager.deleteNGILayerCollection(userVO, collectionIdx);
				}
				if (editCollection.isDeleteAll()) {
					String groupName = "gro" + "_" + type + "_" + collectionName;
					geoserver.removeDTGeoserverAllLayer(userVO, groupName);
				}
			}
		}
	}
}
