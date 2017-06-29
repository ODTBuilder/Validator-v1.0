package com.git.opengds.validator.service;

import java.io.File;
import java.io.FileInputStream;
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
import com.git.opengds.parser.error.ErrorLayerDXFExportParser;
import com.git.opengds.parser.error.ErrorLayerNGIExportParser;
import com.git.opengds.validator.dbManager.ErrorLayerDBQueryManager;
import com.git.opengds.validator.persistence.ErrorLayerDAO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class ErrorLayerExportServiceImpl implements ErrorLayerExportService {

	@Inject
	private ErrorLayerDAO errLayerDAO;

	@Override
	public boolean exportErrorLayer(String format, String type, String name, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> fileMap = null;
		ErrorLayerDBQueryManager dbManager = new ErrorLayerDBQueryManager();

		try {
			// fileWrite
			HashMap<String, Object> selectQuery = dbManager.selectAllErrorFeaturesQuery(name);
			List<HashMap<String, Object>> errAllFeatures = errLayerDAO.selectAllErrorFeatures(selectQuery);
			if (format.equals("ngi")) {
				QA20LayerCollection qa20LayerCollection = ErrorLayerNGIExportParser.parseQA20LayerCollection(name,
						errAllFeatures);
				QA20FileWriter qa20Writer = new QA20FileWriter();
				fileMap = qa20Writer.writeNGIFile(qa20LayerCollection);
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
