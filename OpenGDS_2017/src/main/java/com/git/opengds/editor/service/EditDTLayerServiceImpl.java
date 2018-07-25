package com.git.opengds.editor.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.inject.Inject;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.gdsbuilder.edit.shp.EditSHPLayerCollection;
import com.git.gdsbuilder.edit.shp.EditSHPLayerCollectionList;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;
import com.git.gdsbuilder.type.shp.layer.DTSHPLayer;
import com.git.gdsbuilder.type.shp.layer.DTSHPLayerList;
import com.git.opengds.editor.EditDTLayerCondition;
import com.git.opengds.geoserver.service.GeoserverService;
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

	protected static final String isShp = "shp";

	@Inject
	EditDBManagerService editDBManager;

	@Inject
	GeoserverService geoserverService;

	@Override
	public boolean editLayer(UserVO userVO, JSONObject layerEditObj) throws Exception {

		String src = "5186";

		boolean isSuccessed = false;
		if (layerEditObj.keySet() != null) {
			Iterator layerIterator = layerEditObj.keySet().iterator();
			while (layerIterator.hasNext()) {
				String type = (String) layerIterator.next();
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
								layerInfo.putLayerName(layerId);
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
						if (!editCollection.isDeleteAll()) {
							if (isSuccessed) {
								String layerTableName = "geo" + "_" + type + "_" + collectionName + "_" + layerName;
								String groupName = "gro" + "_" + type + "_" + collectionName;
								isSuccessed = geoserverService.removeDTGeoserverLayer(userVO, groupName,
										layerTableName);
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
}
