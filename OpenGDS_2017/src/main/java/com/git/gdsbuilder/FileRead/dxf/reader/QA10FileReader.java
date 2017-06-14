package com.git.gdsbuilder.FileRead.dxf.reader;

import java.util.Iterator;

import org.kabeja.dxf.DXFDocument;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.parser.DXFParser;
import org.kabeja.parser.ParseException;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;

import com.git.gdsbuilder.FileRead.dxf.parser.QA10FileLayerParser;
import com.git.gdsbuilder.type.qa10.collection.QA10LayerCollection;
import com.git.gdsbuilder.type.qa10.layer.QA10LayerList;
import com.git.gdsbuilder.type.qa10.structure.QA10Header;
import com.git.gdsbuilder.type.qa10.structure.QA10Tables;
import com.git.opengds.upload.domain.FileMeta;

public class QA10FileReader {

	public QA10LayerCollection read(FileMeta fileMeta) throws Exception {

		// String filePath = "C:\\Users\\GIT\\Desktop\\test.dxf";

		String filePath = fileMeta.getFilePath();
		String fileName = fileMeta.getFileName();

		QA10LayerCollection dtCollection = new QA10LayerCollection("", fileName);
		Parser parser = ParserBuilder.createDefaultParser();
		try {
			parser.parse(filePath, DXFParser.DEFAULT_ENCODING);
			DXFDocument doc = parser.getDocument();
			// readDXF
			Iterator layerIterator = doc.getDXFLayerIterator();
			while (layerIterator.hasNext()) {
				DXFLayer dxfLayer = (DXFLayer) layerIterator.next();
				QA10LayerList dtLayers = QA10FileLayerParser.parseDTLayer(dxfLayer);
				dtCollection.addAllQA10Layers(dtLayers);
			}

			// 1. header
			dtCollection.setHeader(getHeader(fileName));

			// 2. blocks
			// dtCollection.setBlocks(getBlocks(fileName, doc));

			// 3. table
			dtCollection.setTables(getTables(fileName, doc.getDXFLineTypeIterator(), doc.getDXFLayerIterator(),
					doc.getDXFStyleIterator()));

			// dtCollection.setId("도엽번호");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		}
		return dtCollection;
	}

	private QA10Header getHeader(String fileName) {

		QA10Header header = new QA10Header();
		header.setDefaultHeaderValues();
		return header;
	}

	private QA10Tables getTables(String fileName, Iterator lineTypeIterator, Iterator layerIterator,
			Iterator styleIterator) {

		QA10Tables tables = new QA10Tables();
		tables.setCollectionName(fileName);
		tables.setLineTypeValues(lineTypeIterator);
		tables.setLayerValues(layerIterator);
		tables.setStyleValues(styleIterator);
		tables.setLineTypes(true);
		tables.setLayers(true);
		tables.setStyles(true);
		return tables;
	}

}
