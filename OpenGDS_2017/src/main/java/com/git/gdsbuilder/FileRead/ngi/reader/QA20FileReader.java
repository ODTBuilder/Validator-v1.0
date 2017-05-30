/*
 *    OpenGDS/Builder
 *    http://git.co.kr
 *
 *    (C) 2014-2017, GeoSpatial Information Technology(GIT)
 *    
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 3 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */

package com.git.gdsbuilder.FileRead.ngi.reader;

import java.io.IOException;

import com.git.gdsbuilder.FileRead.ngi.parser.QA20FileParser;
import com.git.gdsbuilder.FileRead.ngi.parser.QA20FileLayerParser;
import com.git.gdsbuilder.type.qa20.collection.QA20LayerCollection;

/**
 * ngi/nda 파일을 QA20LayerCollection 객체로 파싱하는 클래스
 * @author DY.Oh
 * @Date 2017. 5. 11. 오전 10:36:07
 * */
public class QA20FileReader {

	/**
	 * ngi/nda 파일을 QA20LayerCollection 객체로 파싱하여 반환
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오전 11:43:36
	 * @param ngiFilePath
	 * @param srs
	 * @param charset
	 * @return QA20LayerCollection
	 * @throws IOException 
	 * @throws
	 * */
	public QA20LayerCollection read(String ngiFilePath, String srs, String charset) throws IOException {
		
		QA20LayerCollection collection = new QA20LayerCollection();
		QA20FileParser parser = new QA20FileParser();
		QA20DataStore dataStore = parser.parse(ngiFilePath, srs, charset);

		if (dataStore.isNDA) {
			QA20FileLayerParser dtlayers = new QA20FileLayerParser(dataStore.getNgiReader(), dataStore.getNdaReader());
			collection.addAllQA20Layers(dtlayers.parseQA20LayersWithAtt());
			collection.setId("TEST");
			return collection;
		} else {
			QA20FileLayerParser dtlayers = new QA20FileLayerParser(dataStore.getNgiReader());
			collection.addAllQA20Layers(dtlayers.parseQA20Layers());
			collection.setId("TEST");
			return collection;
		}
	}
}
