package com.git.opengds.validator.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.git.gdsbuilder.FileRead.ngi.writer.QA20FileWriter;
import com.git.gdsbuilder.type.qa20.collection.QA20LayerCollection;
import com.git.opengds.parser.error.ErrorLayerNGIExportParser;
import com.git.opengds.validator.dbManager.ErrorLayerDBQueryManager;
import com.git.opengds.validator.persistence.ErrorLayerDAO;

@Service
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class ErrorLayerExportServiceImpl implements ErrorLayerExportService {

	@Inject
	private ErrorLayerDAO errLayerDAO;

	@Autowired
	private DataSourceTransactionManager txManager;

	@Override
	public void test() throws IOException {

		String tableName = "35811045Test5";
		String exportType = "ngi";

		ErrorLayerDBQueryManager dbManager = new ErrorLayerDBQueryManager();
		HashMap<String, Object> selectQuery = dbManager.selectAllErrorFeaturesQuery(tableName);
		List<HashMap<String, Object>> errAllFeatures = errLayerDAO.selectAllErrorFeatures(selectQuery);
		if (exportType.equals("ngi")) {
			QA20LayerCollection qa20LayerCollection = ErrorLayerNGIExportParser.parseQA20Feature(tableName,
					errAllFeatures);
			QA20FileWriter r = new QA20FileWriter();
			r.test(qa20LayerCollection);
		} else if (exportType.equals("dxf")) {

		}
	}
}
