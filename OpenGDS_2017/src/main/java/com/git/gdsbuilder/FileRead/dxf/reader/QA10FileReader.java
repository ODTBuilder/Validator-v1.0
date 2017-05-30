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
			// dtCollection.setId("도엽번호");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dtCollection;
	}
}
