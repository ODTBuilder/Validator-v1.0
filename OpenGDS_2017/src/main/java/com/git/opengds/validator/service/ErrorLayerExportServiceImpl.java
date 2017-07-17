package com.git.opengds.validator.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.gdsbuilder.FileRead.dxf.writer.QA10FileWriter;
import com.git.gdsbuilder.FileRead.ngi.writer.QA20FileWriter;
import com.git.gdsbuilder.type.qa10.collection.QA10LayerCollection;
import com.git.gdsbuilder.type.qa20.collection.QA20LayerCollection;
import com.git.gdsbuilder.type.qa20.layer.QA20Layer;
import com.git.opengds.file.dxf.persistence.QA10LayerCollectionDAO;
import com.git.opengds.file.ngi.dbManager.QA20DBQueryManager;
import com.git.opengds.file.ngi.persistence.QA20LayerCollectionDAO;
import com.git.opengds.parser.error.ErrorLayerDXFExportParser;
import com.git.opengds.parser.error.ErrorLayerNGIExportParser;
import com.git.opengds.validator.dbManager.ErrorLayerDBQueryManager;
import com.git.opengds.validator.persistence.ErrorLayerDAO;
import com.vividsolutions.jts.io.ParseException;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class ErrorLayerExportServiceImpl implements ErrorLayerExportService {

	@Inject
	private ErrorLayerDAO errLayerDAO;

	@Inject
	private QA20LayerCollectionDAO qa20LayerCollectionDAO;

	@Inject
	private QA10LayerCollectionDAO qa10LayerCollectionDAO;

	@Override
	public boolean exportErrorLayer(String format, String type, String name, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException {

		Map<String, Object> fileMap = null;
		ErrorLayerDBQueryManager dbManager = new ErrorLayerDBQueryManager();

		// try {
		// fileWrite
		HashMap<String, Object> selectQuery = dbManager.selectAllErrorFeaturesQuery(name);
		List<HashMap<String, Object>> errAllFeatures = errLayerDAO.selectAllErrorFeatures(selectQuery);
		if (format.equals("ngi")) {

			QA20LayerCollection qa20LayerCollection = new QA20LayerCollection();

			// 기존 파일 layer 합쳐합쳐
			QA20DBQueryManager qa20dbManager = new QA20DBQueryManager();

			// 기존 도엽 Collection 가져오기
			String[] nameSplit = name.split("_");
			String collectionName = nameSplit[2];
			HashMap<String, Object> selectLayerCollectionIdxQuery = qa20dbManager
					.getSelectQA20LayerCollectionIdx(collectionName);
			int cIdx = qa20LayerCollectionDAO.selectQA20LayerCollectionIdx(selectLayerCollectionIdxQuery);
			HashMap<String, Object> selectAllMetaIdxQuery = qa20dbManager.getSelectQA20LayerMetaDataIdxQuery(cIdx);
			List<HashMap<String, Object>> mIdxMapList = qa20LayerCollectionDAO
					.selectQA20LayerMetadataIdxs(selectAllMetaIdxQuery);
			
			// errlayer 합쳐합쳐
			QA20Layer errQA20Layer = ErrorLayerNGIExportParser.parseQA20ErrorLayer(name, errAllFeatures);
		//	qa20LayerCollection.addQA20Layer(errQA20Layer);
			
			for (int i = 0; i < mIdxMapList.size(); i++) {
				HashMap<String, Object> mIdxMap = mIdxMapList.get(i);
				int lmIdx = (Integer) mIdxMap.get("lm_idx");

				// layerMeata
				HashMap<String, Object> selectAllMetaQuery = qa20dbManager.getSelectAllQA20LayerMetaDataQuery(lmIdx);
				HashMap<String, Object> metaMap = qa20LayerCollectionDAO.selectQA20LayerMeataAll(selectAllMetaQuery);

				List<HashMap<String, Object>> textRepresenets = null;
				List<HashMap<String, Object>> regionRepresenets = null;
				List<HashMap<String, Object>> pointRepresenets = null;
				List<HashMap<String, Object>> lineRepresenets = null;
				List<HashMap<String, Object>> aspatialField = null;

				// tRepresent
				if ((Boolean) metaMap.get("ngi_mask_text")) {
					HashMap<String, Object> selectTextRepresentQuery = qa20dbManager.getSelectTextRepresentQuery(lmIdx);
					textRepresenets = qa20LayerCollectionDAO.selectTextRepresent(selectTextRepresentQuery);
				}

				// rRepresent
				if ((Boolean) metaMap.get("ngi_mask_region")) {
					HashMap<String, Object> selectRegionRepresentQuery = qa20dbManager
							.getSelectResionRepresentQuery(lmIdx);
					regionRepresenets = qa20LayerCollectionDAO.selectResionRepresent(selectRegionRepresentQuery);
				}

				// pRepresent
				if ((Boolean) metaMap.get("ngi_mask_point")) {
					HashMap<String, Object> selectPointRepresentQuery = qa20dbManager
							.getSelectPointRepresentQuery(lmIdx);
					pointRepresenets = qa20LayerCollectionDAO.selectPointRepresent(selectPointRepresentQuery);
				}

				// lRepresent
				if ((Boolean) metaMap.get("ngi_mask_linestring")) {
					HashMap<String, Object> selectLineRepresentQuery = qa20dbManager.getSelectLineRepresentQuery(lmIdx);
					lineRepresenets = qa20LayerCollectionDAO.selectLineStringRepresent(selectLineRepresentQuery);
				}

				// nda
				HashMap<String, Object> selectNdaAspatialFieldQuery = qa20dbManager
						.getSelectNadAspatialFieldQuery(lmIdx);
				aspatialField = qa20LayerCollectionDAO.selectNdaAspatialField(selectNdaAspatialFieldQuery);

				// layerTB
				String layerTbName = (String) metaMap.get("layer_t_name");
				HashMap<String, Object> selectAllFeaturesQuery = qa20dbManager.getSelectAllFeaturesQuery(layerTbName,
						aspatialField);
				List<HashMap<String, Object>> featuresMapList = qa20LayerCollectionDAO
						.selectAllQA20Features(selectAllFeaturesQuery);

				QA20Layer qa20Layer = ErrorLayerNGIExportParser.parseQA20Layer(metaMap, featuresMapList,
						pointRepresenets, lineRepresenets, regionRepresenets, textRepresenets, aspatialField);
				qa20LayerCollection.addQA20Layer(qa20Layer);
			}
			QA20FileWriter qa20Writer = new QA20FileWriter();
			fileMap = qa20Writer.writeNGIFile(qa20LayerCollection, errQA20Layer);
			String fileName = (String) fileMap.get("fileName");
			// ngi
			String ngiFileDir = (String) fileMap.get("NgifileDir");
			layerFileOutputStream(fileName, ngiFileDir, response);
			// nda
			String ndaFileDir = (String) fileMap.get("NdafileDir");
			layerFileOutputStream(fileName, ndaFileDir, response);
		} else if (format.equals("dxf")) {
			QA10LayerCollection qa10LayerCollection = ErrorLayerDXFExportParser.parseQA10LayerCollection(name,
					errAllFeatures);
			QA10FileWriter qa10Writer = new QA10FileWriter();
			fileMap = qa10Writer.writeDxfFile(qa10LayerCollection);
			String fileName = (String) fileMap.get("fileName");
			String dxfDir = (String) fileMap.get("fileDxfDir");
			layerFileOutputStream(fileName, dxfDir, response);
		}
		// } catch (Exception e) {
		// return false;
		// }
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
