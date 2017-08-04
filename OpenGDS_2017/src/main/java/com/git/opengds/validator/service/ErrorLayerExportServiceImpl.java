package com.git.opengds.validator.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.gdsbuilder.FileRead.dxf.writer.DXFFileWriter;
import com.git.gdsbuilder.FileRead.ngi.writer.NGIFileWriter;
import com.git.gdsbuilder.type.dxf.collection.DTDXFLayerCollection;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayer;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayerList;
import com.git.gdsbuilder.type.dxf.structure.DTDXFBlocks;
import com.git.gdsbuilder.type.dxf.structure.DTDXFEntities;
import com.git.gdsbuilder.type.dxf.structure.DTDXFHeader;
import com.git.gdsbuilder.type.dxf.structure.DTDXFTables;
import com.git.gdsbuilder.type.ngi.collection.DTNGILayerCollection;
import com.git.gdsbuilder.type.ngi.layer.DTNGILayer;
import com.git.opengds.file.dxf.dbManager.DXFDBQueryManager;
import com.git.opengds.file.dxf.persistence.DXFLayerCollectionDAO;
import com.git.opengds.file.ngi.dbManager.NGIDBQueryManager;
import com.git.opengds.file.ngi.persistence.NGILayerCollectionDAO;
import com.git.opengds.parser.error.DXFLayerExportParser;
import com.git.opengds.parser.error.NGILayerExportParser;
import com.git.opengds.user.domain.UserVO;
import com.git.opengds.validator.dbManager.ErrorLayerDBQueryManager;
import com.git.opengds.validator.persistence.ErrorLayerDAO;
import com.vividsolutions.jts.io.ParseException;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class ErrorLayerExportServiceImpl implements ErrorLayerExportService {

	@Inject
	private ErrorLayerDAO errLayerDAO;

	@Inject
	private NGILayerCollectionDAO qa20LayerCollectionDAO;

	@Inject
	private DXFLayerCollectionDAO qa10LayerCollectionDAO;

	/*
	 * public ErrorLayerExportServiceImpl(UserVO userVO) { // TODO
	 * Auto-generated constructor stub errLayerDAO = new
	 * ErrorLayerDAOImpl(userVO); qa20LayerCollectionDAO = new
	 * QA20LayerCollectionDAOImpl(userVO); qa10LayerCollectionDAO = new
	 * QA10LayerCollectionDAOImpl(userVO); }
	 */
	@Override
	public boolean exportErrorLayer(UserVO userVO, String format, String type, String name, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException {

		Map<String, Object> fileMap = null;
		ErrorLayerDBQueryManager dbManager = new ErrorLayerDBQueryManager();

		try {
			// fileWrite
			HashMap<String, Object> selectQuery = dbManager.selectAllErrorFeaturesQuery(name);
			List<HashMap<String, Object>> errAllFeatures = errLayerDAO.selectAllErrorFeatures(userVO, selectQuery);
			String[] nameSplit = name.split("_");
			String collectionName = nameSplit[2];
			if (format.equals("ngi")) {
				DTNGILayerCollection qa20LayerCollection = new DTNGILayerCollection();
				// 기존 파일 layer 합쳐합쳐
				NGIDBQueryManager qa20dbManager = new NGIDBQueryManager();
				// 기존 도엽 Collection 가져오기
				qa20LayerCollection.setFileName(collectionName);
				HashMap<String, Object> selectLayerCollectionIdxQuery = qa20dbManager
						.getSelectQA20LayerCollectionIdx(collectionName);
				int cIdx = qa20LayerCollectionDAO.selectQA20LayerCollectionIdx(userVO, selectLayerCollectionIdxQuery);
				HashMap<String, Object> selectAllMetaIdxQuery = qa20dbManager.getSelectQA20LayerMetaDataIdxQuery(cIdx);
				List<HashMap<String, Object>> mIdxMapList = qa20LayerCollectionDAO.selectQA20LayerMetadataIdxs(userVO,
						selectAllMetaIdxQuery);
				// errlayer 합쳐합쳐
				DTNGILayer errQA20Layer = NGILayerExportParser.parseQA20ErrorLayer(name, errAllFeatures);
				for (int i = 0; i < mIdxMapList.size(); i++) {
					HashMap<String, Object> mIdxMap = mIdxMapList.get(i);
					int lmIdx = (Integer) mIdxMap.get("lm_idx");
					// layerMeata
					HashMap<String, Object> selectAllMetaQuery = qa20dbManager
							.getSelectAllQA20LayerMetaDataQuery(lmIdx);
					HashMap<String, Object> metaMap = qa20LayerCollectionDAO.selectQA20LayerMeata(userVO,
							selectAllMetaQuery);

					List<HashMap<String, Object>> textRepresenets = null;
					List<HashMap<String, Object>> regionRepresenets = null;
					List<HashMap<String, Object>> pointRepresenets = null;
					List<HashMap<String, Object>> lineRepresenets = null;
					List<HashMap<String, Object>> aspatialField = null;
					// tRepresent
					if ((Boolean) metaMap.get("ngi_mask_text")) {
						HashMap<String, Object> selectTextRepresentQuery = qa20dbManager
								.getSelectTextRepresentQuery(lmIdx);
						textRepresenets = qa20LayerCollectionDAO.selectTextRepresent(userVO, selectTextRepresentQuery);
					}
					// rRepresent
					if ((Boolean) metaMap.get("ngi_mask_region")) {
						HashMap<String, Object> selectRegionRepresentQuery = qa20dbManager
								.getSelectResionRepresentQuery(lmIdx);
						regionRepresenets = qa20LayerCollectionDAO.selectResionRepresent(userVO,
								selectRegionRepresentQuery);
					}
					// pRepresent
					if ((Boolean) metaMap.get("ngi_mask_point")) {
						HashMap<String, Object> selectPointRepresentQuery = qa20dbManager
								.getSelectPointRepresentQuery(lmIdx);
						pointRepresenets = qa20LayerCollectionDAO.selectPointRepresent(userVO,
								selectPointRepresentQuery);
					}
					// lRepresent
					if ((Boolean) metaMap.get("ngi_mask_linestring")) {
						HashMap<String, Object> selectLineRepresentQuery = qa20dbManager
								.getSelectLineRepresentQuery(lmIdx);
						lineRepresenets = qa20LayerCollectionDAO.selectLineStringRepresent(userVO,
								selectLineRepresentQuery);
					}
					HashMap<String, Object> selectNdaAspatialFieldQuery = qa20dbManager
							.getSelectNadAspatialFieldQuery(lmIdx);
					aspatialField = qa20LayerCollectionDAO.selectNdaAspatialField(userVO, selectNdaAspatialFieldQuery);
					// layerTB
					String layerTbName = (String) metaMap.get("layer_t_name");
					if(layerTbName.equals("\"geo_ngi_37712002_H0040000_TEXT\"")) {
						System.out.println("");
					}
					HashMap<String, Object> selectAllFeaturesQuery = qa20dbManager
							.getSelectAllFeaturesQuery(layerTbName, aspatialField);
					List<HashMap<String, Object>> featuresMapList = qa20LayerCollectionDAO.selectAllQA20Features(userVO,
							selectAllFeaturesQuery);

					DTNGILayer qa20Layer = NGILayerExportParser.parseQA20Layer(metaMap, featuresMapList,
							pointRepresenets, lineRepresenets, regionRepresenets, textRepresenets, aspatialField);
					qa20LayerCollection.addQA20Layer(qa20Layer);
				}
				NGIFileWriter qa20Writer = new NGIFileWriter();
				fileMap = qa20Writer.writeNGIFile(qa20LayerCollection, errQA20Layer);
				String fileName = (String) fileMap.get("fileName");
				// ngi
				String ngiFileDir = (String) fileMap.get("NgifileDir");
				layerFileOutputStream(fileName, ngiFileDir, response);
				// nda
				String ndaFileDir = (String) fileMap.get("NdafileDir");
				layerFileOutputStream(fileName, ndaFileDir, response);
			} else if (format.equals("dxf")) {

				DTDXFLayer errQ120Layer = DXFLayerExportParser.parseQA10ErrorLayer(name, errAllFeatures);

				DTDXFLayerList layerList = new DTDXFLayerList();
				DXFDBQueryManager qa10dbQueryManager = new DXFDBQueryManager();
				// collectionIdx
				HashMap<String, Object> selectLayerCollectionIdxQuery = qa10dbQueryManager
						.getSelectLayerCollectionIdx(collectionName);
				int cIdx = qa10LayerCollectionDAO.selectQA10LayerCollectionIdx(userVO, selectLayerCollectionIdxQuery);

				HashMap<String, Object> selectAllMetaIdxQuery = qa10dbQueryManager.getSelectLayerMetaDataIdx(cIdx);
				List<HashMap<String, Object>> mIdxMapList = qa10LayerCollectionDAO.selectQA10LayerMetadataIdxs(userVO,
						selectAllMetaIdxQuery);

				List<LinkedHashMap<String, Object>> blocks = new ArrayList<LinkedHashMap<String, Object>>();
				for (int i = 0; i < mIdxMapList.size(); i++) {
					HashMap<String, Object> mIdxMap = mIdxMapList.get(i);
					int lmIdx = (Integer) mIdxMap.get("lm_idx");
					// layerMeata
					HashMap<String, Object> selectMetaQuery = qa10dbQueryManager.getSelectQA10LayerMetaDataQuery(lmIdx);
					HashMap<String, Object> metaMap = qa10LayerCollectionDAO.selectQA10LayerMeata(userVO,
							selectMetaQuery);

					String layerId = (String) metaMap.get("layer_id");
					String[] typeSplit = layerId.split("_");
					String layerType = typeSplit[1];
					String layerTbName = (String) metaMap.get("layer_t_name");

					// blockCommons
					String id = typeSplit[0];
					HashMap<String, Object> selectBlockCommonQuery = qa10dbQueryManager.getSelectBlockCommon(cIdx, id);
					HashMap<String, Object> blockCommonMap = qa10LayerCollectionDAO.selectQA10layerBlocksCommon(userVO,
							selectBlockCommonQuery);
					if (blockCommonMap != null) {
						LinkedHashMap<String, Object> block = new LinkedHashMap<String, Object>();
						LinkedHashMap<String, Object> blockCommons = DTDXFBlocks.getCommonsValue(blockCommonMap);
						block.put("block", blockCommons);

						int bcIdx = (Integer) blockCommonMap.get("bc_idx");
						// blockEntities
						List<LinkedHashMap<String, Object>> entities = new ArrayList<LinkedHashMap<String, Object>>();

						// arc
						if ((Boolean) blockCommonMap.get("is_arc")) {
							HashMap<String, Object> selectBlockArcList = qa10dbQueryManager.getSelectBlockArc(bcIdx);
							List<HashMap<String, Object>> blockArcMapList = qa10LayerCollectionDAO
									.selectBlockEntities(userVO, selectBlockArcList);
							if (blockArcMapList != null) {
								for (int j = 0; j < blockArcMapList.size(); j++) {
									HashMap<String, Object> blockArcMap = blockArcMapList.get(j);
									LinkedHashMap<String, Object> entity = DTDXFBlocks.getArcValues(blockArcMap);
									entities.add(entity);
								}
							}
						}
						// circle
						if ((Boolean) blockCommonMap.get("is_circle")) {
							HashMap<String, Object> selectBlockCircleList = qa10dbQueryManager
									.getSelectBlockCircle(bcIdx);
							List<HashMap<String, Object>> blockCircleMapList = qa10LayerCollectionDAO
									.selectBlockEntities(userVO, selectBlockCircleList);
							if (blockCircleMapList != null) {
								for (int j = 0; j < blockCircleMapList.size(); j++) {
									HashMap<String, Object> blockCircleMap = blockCircleMapList.get(j);
									LinkedHashMap<String, Object> entity = DTDXFBlocks.getCircleValues(blockCircleMap);
									entities.add(entity);
								}
							}
						}
						// polyline
						if ((Boolean) blockCommonMap.get("is_polyoine")) {
							HashMap<String, Object> selectBlockPolylineList = qa10dbQueryManager
									.getSelectBlockPolyline(bcIdx);
							List<HashMap<String, Object>> blockPolylineMapList = qa10LayerCollectionDAO
									.selectBlockEntities(userVO, selectBlockPolylineList);
							if (blockPolylineMapList != null) {
								for (int j = 0; j < blockPolylineMapList.size(); j++) {
									HashMap<String, Object> blockPolylineMap = blockPolylineMapList.get(j);
									int bpIdx = (Integer) blockPolylineMap.get("bp_idx");
									// vertext
									HashMap<String, Object> selectBlockVertexList = qa10dbQueryManager
											.getSelectBlockPolylineVertex(bpIdx);
									List<HashMap<String, Object>> blockVertexMapList = qa10LayerCollectionDAO
											.selectBlockEntities(userVO, selectBlockVertexList);
									if (blockVertexMapList != null) {
										List<LinkedHashMap<String, Object>> vertexEntityList = new ArrayList<LinkedHashMap<String, Object>>();
										for (int k = 0; k < blockVertexMapList.size(); k++) {
											LinkedHashMap<String, Object> vertextEntity = DTDXFBlocks
													.getVertexValue(blockVertexMapList.get(k));
											vertexEntityList.add(vertextEntity);
										}
										LinkedHashMap<String, Object> polylineEntity = DTDXFBlocks
												.getPolylineValue(blockPolylineMap, blockVertexMapList.get(0));
										polylineEntity.put("vertexs", vertexEntityList);
										entities.add(polylineEntity);
									}
								}
							}
						}
						// text
						if ((Boolean) blockCommonMap.get("is_text")) {
							HashMap<String, Object> selectBlockTextList = qa10dbQueryManager.getSelectBlockText(bcIdx);
							List<HashMap<String, Object>> blockTextMapList = qa10LayerCollectionDAO
									.selectBlockEntities(userVO, selectBlockTextList);
							if (blockTextMapList != null) {
								for (int j = 0; j < blockTextMapList.size(); j++) {
									HashMap<String, Object> blockTextMap = blockTextMapList.get(j);
									LinkedHashMap<String, Object> entity = DTDXFBlocks.getTextValue(blockTextMap);
									entities.add(entity);
								}
							}
						}

						// line
						if ((Boolean) blockCommonMap.get("is_line")) {
							HashMap<String, Object> selectBlockLineList = qa10dbQueryManager.getSelectBlockLine(bcIdx);
							List<HashMap<String, Object>> blockLineMapList = qa10LayerCollectionDAO
									.selectBlockEntities(userVO, selectBlockLineList);
							if (blockLineMapList != null) {
								for (int j = 0; j < blockLineMapList.size(); j++) {
									HashMap<String, Object> blockLineMap = blockLineMapList.get(j);
									LinkedHashMap<String, Object> lineEntity = DTDXFBlocks.getLineValue(blockLineMap);
									List<LinkedHashMap<String, Object>> vertexEntityList = DTDXFBlocks
											.getLineVertexsValue(blockLineMap);
									lineEntity.put("vertexs", vertexEntityList);
									entities.add(lineEntity);
								}
							}
						}
						// lwpolyline
						if ((Boolean) blockCommonMap.get("is_lwpolyoine")) {
							HashMap<String, Object> selectBlockLWPolylineList = qa10dbQueryManager
									.getSelectBlockLWPolyline(bcIdx);
							List<HashMap<String, Object>> blockLWPolylineMapList = qa10LayerCollectionDAO
									.selectBlockEntities(userVO, selectBlockLWPolylineList);
							if (blockLWPolylineMapList != null) {
								for (int j = 0; j < blockLWPolylineMapList.size(); j++) {
									HashMap<String, Object> blockLWPolylineMap = blockLWPolylineMapList.get(j);
									int blpIdx = (Integer) blockLWPolylineMap.get("blp_idx");
									// vertext
									HashMap<String, Object> selectBlockLWPolylineVertexList = qa10dbQueryManager
											.getSelectBlockLWPolylineVertex(blpIdx);
									List<HashMap<String, Object>> blockLWPolylineVertexMapList = qa10LayerCollectionDAO
											.selectBlockEntities(userVO, selectBlockLWPolylineVertexList);
									if (blockLWPolylineVertexMapList != null) {
										List<LinkedHashMap<String, Object>> vertexEntityList = new ArrayList<LinkedHashMap<String, Object>>();
										for (int k = 0; k < blockLWPolylineVertexMapList.size(); k++) {
											LinkedHashMap<String, Object> vertextEntity = DTDXFBlocks
													.getVertexValue(blockLWPolylineVertexMapList.get(k));
											vertexEntityList.add(vertextEntity);
										}
										LinkedHashMap<String, Object> entity = DTDXFBlocks.getLWPolylineValue(
												blockLWPolylineMap, blockLWPolylineVertexMapList.get(0));
										entity.put("vertexs", vertexEntityList);
										entities.add(entity);
									}
								}
							}
						}
						block.put("entities", entities);
						blocks.add(block);
					}

					// Entities
					HashMap<String, Object> selectFeaturesQuery = qa10dbQueryManager.getSelectFeatureQuery(layerTbName,
							layerType);
					List<HashMap<String, Object>> featuresMapList = qa10LayerCollectionDAO.selectQA10Features(userVO,
							selectFeaturesQuery);

					DTDXFLayer qa10Layer = DXFLayerExportParser.parseQA10Layer(layerId, featuresMapList);
					qa10Layer.setLayerType(layerType);

					layerList.add(qa10Layer);
				}
				DTDXFLayerCollection qa10LayerCollection = new DTDXFLayerCollection();
				qa10LayerCollection.setCollectionName(collectionName);

				// setEntitise
				DTDXFEntities qa10Entities = new DTDXFEntities();
				qa10Entities.setEntitiesValue(layerList);
				qa10LayerCollection.setEntities(qa10Entities);

				// setBlock
				DTDXFBlocks qa10Blocks = new DTDXFBlocks();
				qa10Blocks.setBlocks(blocks);
				qa10LayerCollection.setBlocks(qa10Blocks);

				// setDefaultHeaderValues
				DTDXFHeader header = new DTDXFHeader();
				header.setDefaultHeaderValues();
				qa10LayerCollection.setHeader(header);

				// tableCommonValue
				HashMap<String, Object> selectTablesCommonsQuery = qa10dbQueryManager.getSelectTableCommon(cIdx);
				HashMap<String, Object> tablesCommonMap = qa10LayerCollectionDAO.selectTablesCommon(userVO,
						selectTablesCommonsQuery);
				int tcIdx = (Integer) tablesCommonMap.get("tc_idx");

				// tableLayerValue
				HashMap<String, Object> selectTablesLayerQuery = qa10dbQueryManager.getSelectTableLayer(tcIdx);
				List<HashMap<String, Object>> tablesLayerMap = qa10LayerCollectionDAO.selectTablesLayer(userVO,
						selectTablesLayerQuery);

				// setDefaultTableLtype
				DTDXFTables tables = new DTDXFTables();
				tables.setDefaultLineTypeValues();
				tables.setLineTypes(true);
				tables.setDefaultStyleValues();
				tables.setStyles(true);
				tables.setLayerValues(tablesCommonMap, tablesLayerMap);
				tables.setLayers(true);
				qa10LayerCollection.setTables(tables);

				// writeFile
				DXFFileWriter qa10Writer = new DXFFileWriter();
				fileMap = qa10Writer.writeDxfFile(qa10LayerCollection, errQ120Layer);
				String fileName = (String) fileMap.get("fileName");
				String dxfDir = (String) fileMap.get("fileDxfDir");
				layerFileOutputStream(fileName, dxfDir, response);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private boolean layerFileOutputStream(String fileName, String fileDir, HttpServletResponse response) {

		try {
			// fileOutput
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

			File file = new File(fileDir);
			FileInputStream fileIn = new FileInputStream(file);
			ServletOutputStream out = response.getOutputStream();

			byte[] outputByte = new byte[4096];
			while (fileIn.read(outputByte, 0, 4096) != -1) {
				out.write(outputByte, 0, 4096);
			}
			fileIn.close();
			out.flush();
			out.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
