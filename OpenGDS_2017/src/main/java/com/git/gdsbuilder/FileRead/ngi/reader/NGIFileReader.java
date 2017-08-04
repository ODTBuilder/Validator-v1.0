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

import com.git.gdsbuilder.FileRead.ngi.parser.NGIFileParser;
import com.git.gdsbuilder.type.ngi.collection.DTNGILayerCollection;
import com.git.gdsbuilder.FileRead.ngi.parser.NGIFileLayerParser;

/**
 * ngi/nda 파일을 QA20LayerCollection 객체로 파싱하는 클래스
 * @author DY.Oh
 * @Date 2017. 5. 11. 오전 10:36:07
 * */
public class NGIFileReader {

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
	public DTNGILayerCollection read(String ngiFilePath, String srs, String charset) throws IOException {
		
		DTNGILayerCollection collection = new DTNGILayerCollection();
		NGIFileParser parser = new NGIFileParser();
		NGIDataStore dataStore = parser.parse(ngiFilePath, srs, charset);

		if (dataStore.isNDA) {
			NGIFileLayerParser dtlayers = new NGIFileLayerParser(dataStore.getNgiReader(), dataStore.getNdaReader());
			collection.addAllQA20Layers(dtlayers.parseQA20LayersWithAtt());
			collection.setId("TEST");
			return collection;
		} else {
			NGIFileLayerParser dtlayers = new NGIFileLayerParser(dataStore.getNgiReader());
			collection.addAllQA20Layers(dtlayers.parseQA20Layers());
			collection.setId("TEST");
			return collection;
		}
	}
}
