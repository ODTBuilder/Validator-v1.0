package com.git.gdsbuilder.FileRead.dxf.reader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kabeja.dxf.DXFBlock;
import org.kabeja.dxf.DXFDimensionStyle;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.dxf.DXFHeader;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFLineType;
import org.kabeja.dxf.DXFStyle;
import org.kabeja.dxf.DXFVariable;
import org.kabeja.dxf.DXFView;
import org.kabeja.dxf.DXFViewport;
import org.kabeja.dxf.objects.DXFDictionary;
import org.kabeja.parser.DXFParser;
import org.kabeja.parser.ParseException;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;

import com.git.gdsbuilder.FileRead.dxf.parser.QA10FileLayerParser;
import com.git.gdsbuilder.type.qa10.collection.QA10LayerCollection;
import com.git.gdsbuilder.type.qa10.layer.QA10LayerList;
import com.git.gdsbuilder.type.qa10.structure.QA10Blocks;
import com.git.gdsbuilder.type.qa10.structure.QA10Classes;
import com.git.gdsbuilder.type.qa10.structure.QA10Header;
import com.git.gdsbuilder.type.qa10.structure.QA10Objects;
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
			dtCollection.setHeader(getHeader(fileName, doc));
			// 2. classes
			dtCollection.setClasses(getClasses(fileName, true));
			// 3. blocks
			dtCollection.setBlocks(getBlocks(fileName, doc));
			// 4. objects
			dtCollection.setObjects(getObjects(fileName, doc));
			// 5. table
			dtCollection.setTables(getTables(fileName, doc));

			// dtCollection.setId("도엽번호");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		}
		return dtCollection;
	}

	private QA10Objects getObjects(String fileName, DXFDocument doc) {

		final String dictionaryStr = "DICTIONARY";

		QA10Objects objects = new QA10Objects();
		objects.setCollectionName(fileName);

		// dictionary
		List dictionarys = doc.getDXFObjectsByType(dictionaryStr);
		int dSize = dictionarys.size();
		List<DXFDictionary> dictionaryList = new ArrayList<DXFDictionary>();
		if (dSize > 0) {
			for (int i = 0; i < dSize; i++) {
				DXFDictionary dictionary = (DXFDictionary) dictionarys.get(i);
				dictionaryList.add(dictionary);
				// Iterator objIter = dictionary.getDXFObjectIterator();
				// while(objIter.hasNext()) {
				// DXFDictionary obj = (DXFDictionary) objIter.next();
				// System.out.println("");
				// }
			}
		}
		objects.setObjects(dictionaryList);
		return objects;
	}

	private QA10Blocks getBlocks(String fileName, DXFDocument doc) {

		QA10Blocks blocks = new QA10Blocks();
		blocks.setCollectionName(fileName);

		Iterator blockIt = doc.getDXFBlockIterator();
		List<DXFBlock> blockList = new ArrayList<DXFBlock>();
		while (blockIt.hasNext()) {
			DXFBlock block = (DXFBlock) blockIt.next();
			blockList.add(block);
		}
		blocks.setBlocks(blockList);
		return blocks;
	}

	private QA10Classes getClasses(String fileName, boolean isDefault) {

		QA10Classes classes = new QA10Classes();
		if (isDefault) {
			classes.putDefaultValues();
		}
		return classes;
	}

	private QA10Header getHeader(String fileName, DXFDocument doc) {

		QA10Header qa10Header = new QA10Header();
		qa10Header.setCollectionName(fileName);

		DXFHeader header = doc.getDXFHeader();
		Iterator valueIt = header.getVarialbeIterator();
		List<DXFVariable> values = new ArrayList<DXFVariable>();
		while (valueIt.hasNext()) {
			DXFVariable value = (DXFVariable) valueIt.next();
			values.add(value);
		}
		qa10Header.setValues(values);
		return qa10Header;
	}

	private QA10Tables getTables(String fileName, DXFDocument doc) {

		QA10Tables tables = new QA10Tables();
		tables.setCollectionName(fileName);

		// lineType
		Iterator lineTypeIt = doc.getDXFLineTypeIterator();
		List<DXFLineType> lineType = new ArrayList<DXFLineType>();
		while (lineTypeIt.hasNext()) {
			DXFLineType value = (DXFLineType) lineTypeIt.next();
			lineType.add(value);
		}
		tables.setLineType(lineType);

		// dimStyle
		Iterator dimStyleIt = doc.getDXFDimensionStyleIterator();
		List<DXFDimensionStyle> dimStyle = new ArrayList<DXFDimensionStyle>();
		while (dimStyleIt.hasNext()) {
			DXFDimensionStyle value = (DXFDimensionStyle) dimStyleIt.next();
			dimStyle.add(value);
		}
		tables.setDimStyle(dimStyle);

		// view
		Iterator viewIt = doc.getDXFViewIterator();
		List<DXFView> view = new ArrayList<DXFView>();
		while (viewIt.hasNext()) {
			DXFView value = (DXFView) viewIt.next();
			view.add(value);
		}
		tables.setView(view);

		// style
		Iterator styleIt = doc.getDXFStyleIterator();
		List<DXFStyle> style = new ArrayList<DXFStyle>();
		while (styleIt.hasNext()) {
			DXFStyle value = (DXFStyle) styleIt.next();
			style.add(value);
		}
		tables.setStyle(style);

		// viewport
		Iterator viewPortIt = doc.getDXFViewportIterator();
		List<DXFViewport> viewPort = new ArrayList<DXFViewport>();
		while (viewPortIt.hasNext()) {
			DXFViewport value = (DXFViewport) viewPortIt.next();
			viewPort.add(value);
		}
		tables.setViewport(viewPort);

		return tables;
	}
}
