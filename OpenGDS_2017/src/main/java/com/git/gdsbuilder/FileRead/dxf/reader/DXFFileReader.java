package com.git.gdsbuilder.FileRead.dxf.reader;

import java.util.Iterator;

import org.kabeja.dxf.DXFDocument;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.parser.DXFParser;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;

import com.git.gdsbuilder.FileRead.dxf.parser.DXFFileLayerParser;
import com.git.gdsbuilder.type.dxf.collection.DTDXFLayerCollection;
import com.git.gdsbuilder.type.dxf.layer.DTDXFLayerList;
import com.git.gdsbuilder.type.dxf.structure.DTDXFBlocks;
import com.git.gdsbuilder.type.dxf.structure.DTDXFHeader;
import com.git.gdsbuilder.type.dxf.structure.DTDXFTables;
import com.git.opengds.upload.domain.FileMeta;

public class DXFFileReader {

	public DTDXFLayerCollection read(FileMeta fileMeta) throws Exception {

		// String filePath = "C:\\Users\\GIT\\Desktop\\test.dxf";

		String filePath = fileMeta.getFilePath();
		String fileName = fileMeta.getFileName();

		DTDXFLayerCollection dtCollection = new DTDXFLayerCollection("", fileName);
		Parser parser = ParserBuilder.createDefaultParser();
		// try {
		parser.parse(filePath, DXFParser.DEFAULT_ENCODING);
		DXFDocument doc = parser.getDocument();
		// readDXF
		Iterator layerIterator = doc.getDXFLayerIterator();
		while (layerIterator.hasNext()) {
			DXFLayer dxfLayer = (DXFLayer) layerIterator.next();
			String layerId = dxfLayer.getName();
			if (layerId.equals("B0014114")) {
				System.out.println("");
			}
			if (layerId.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*")) {
				DTDXFLayerList dtLayers = DXFFileLayerParser.parseDTLayer(dxfLayer);
				dtCollection.addAllQA10Layers(dtLayers);
			} else {
				continue;
			}
		}

		// 1. header
		dtCollection.setHeader(getHeader(fileName));

		// 2. blocks
		dtCollection.setBlocks(getBlocks(fileName, doc.getDXFBlockIterator()));

		// 3. table
		dtCollection.setTables(getTables(fileName, doc.getDXFLineTypeIterator(), doc.getDXFLayerIterator(),
				doc.getDXFStyleIterator()));

		// dtCollection.setId("도엽번호");
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// }
		return dtCollection;
	}

	private DTDXFBlocks getBlocks(String fileName, Iterator blockIterator) {

		DTDXFBlocks blocks = new DTDXFBlocks();
		blocks.setCollectionName(fileName);
		blocks.setBlocksValues(blockIterator);
		return blocks;
	}

	private DTDXFHeader getHeader(String fileName) {

		DTDXFHeader header = new DTDXFHeader();
		header.setDefaultHeaderValues();
		return header;
	}

	private DTDXFTables getTables(String fileName, Iterator lineTypeIterator, Iterator layerIterator,
			Iterator styleIterator) {

		DTDXFTables tables = new DTDXFTables();
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
