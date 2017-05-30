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

package com.git.gdsbuilder.FileRead.ngi.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.git.gdsbuilder.type.qa20.feature.QA20Feature;
import com.git.gdsbuilder.type.qa20.header.NDAHeader;
import com.git.gdsbuilder.type.qa20.header.NGIField;
import com.git.gdsbuilder.type.qa20.header.NGIHeader;
import com.git.gdsbuilder.type.qa20.layer.QA20Layer;
import com.git.gdsbuilder.type.qa20.layer.QA20LayerList;

/**
 * ngi/nda 파일의 모든 layer를 QA20LayerList객체로 파싱하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오전 11:19:17
 */
public class QA20FileLayerParser {

	BufferedReader ngiReader;
	BufferedReader ndaReader;

	/**
	 * QA20FileLayerParser 생성자
	 * 
	 * @param ngiReader
	 */
	public QA20FileLayerParser(BufferedReader ngiReader) {
		this.ngiReader = ngiReader;
	}

	/**
	 * QA20FileLayerParser 생성자
	 * 
	 * @param ngiReader
	 * @param ndaReader
	 */
	public QA20FileLayerParser(BufferedReader ngiReader, BufferedReader ndaReader) {
		this.ngiReader = ngiReader;
		this.ndaReader = ndaReader;
	}

	/**
	 * ngi파일과 동일한 파일명을 가진 nda파일이 있는 경우 ngi의 모든 레이어를 QA20LayerList로 파싱 @author
	 * DY.Oh @Date 2017. 3. 11. 오전 11:26:17 @return QA20LayerList @throws
	 * IOException @throws
	 */
	public QA20LayerList parseQA20LayersWithAtt() throws IOException {

		QA20LayerList layers = new QA20LayerList();
		String line = ngiReader.readLine();
		while (line != null) {
			if (line.equalsIgnoreCase("<LAYER_START>")) {
				layers.addAll(parseQA20LayerWithAtt());
			}
			line = ngiReader.readLine();
		}
		return layers;
	}

	/**
	 * ngi파일과 동일한 파일명을 가진 nda파일이 있는 경우 ngi의 레이어를 QA20LayerList로 파싱 @author
	 * DY.Oh @Date 2017. 3. 11. 오전 11:26:17 @return QA20LayerList @throws
	 * IOException @throws
	 */
	private QA20LayerList parseQA20LayerWithAtt() throws IOException {

		QA20LayerList layers = new QA20LayerList();
		String layerID = getLayerID();
		String layerName = getLayerName().replaceAll("\"", "");

		NGIHeader ngiHeader = getNgiHeader();
		NDAHeader ndaHeader = getNdaHeader();
		String[] layerTypes = getLayerTypes(ngiHeader);

		for (String type : layerTypes) {
			boolean isEquals = layers.isEqualsLayer(layerID, type);
			if (isEquals == false) {
				String layerID_type = layerID + "_" + type;
				QA20Layer layer = new QA20Layer(layerID_type, true);
				layer.setLayerName(layerName + "_" + type);
				layer.setLayerType(type);
				layer.setNgiHeader(ngiHeader);
				layer.setNdaHeader(ndaHeader);
				layers.add(layer);
			}
		}
		String ngiLine = ngiReader.readLine();
		String ndaLine = ndaReader.readLine();
		while (ngiLine != null) {
			// getDATA
			if (ngiLine.equalsIgnoreCase("<DATA>") && ndaLine.equalsIgnoreCase("<DATA>")) {
				ngiLine = ngiReader.readLine();
				ndaLine = ndaReader.readLine();
				while (!ngiLine.equalsIgnoreCase("<END>")) {
					String featureID = ngiLine;
					QA20FileFeatureParser featureParser = new QA20FileFeatureParser(ngiReader, ndaReader);
					QA20Feature feature = featureParser.parserDTFeature(featureID);
					String ndaFeatureID = ndaLine;
					if (featureID.equals(ndaFeatureID)) {
						feature.setProperties(featureParser.getFeatureAttrib(ndaHeader, true));
					} else {
						feature.setProperties(featureParser.getFeatureAttrib(ndaHeader, false));
					}
					String type = feature.getFeatureType();
					layers.getQA20Layer(layerID + "_" + type).add(feature);
					ngiReader = featureParser.getNgiReader();
					ndaReader = featureParser.getNdaReader();

					if (!ndaLine.equalsIgnoreCase("<END>")) {
						ngiLine = ngiReader.readLine();
						ndaLine = ndaReader.readLine();
					} else {
						ngiLine = ngiReader.readLine();
					}
					if (ngiLine.equalsIgnoreCase("<END>") && ndaLine.equalsIgnoreCase("<END>")) {
						ngiLine = ngiReader.readLine();
						ndaLine = ndaReader.readLine();
						break;
					}
				}
			}
			if (ngiLine.equalsIgnoreCase("<LAYER_END>") && ndaLine.equalsIgnoreCase("<LAYER_END>")) {
				break;
			}
			ngiLine = ngiReader.readLine();
			ndaLine = ndaReader.readLine();
		}
		return layers;
	}

	/**
	 * ngi파일과 동일한 파일명을 가진 nda파일이 없는 경우 ngi의 모든 레이어를 QA20LayerList로 파싱 @author
	 * DY.Oh @Date 2017. 3. 11. 오전 11:26:17 @return QA20LayerList @throws
	 * IOException @throws
	 */
	public QA20LayerList parseQA20Layers() throws IOException {

		QA20LayerList layers = new QA20LayerList();
		String line = ngiReader.readLine();
		while (line != null) {
			if (line.equalsIgnoreCase("<LAYER_START>")) {
				layers.addAll(parseQA20Layer());
			}
			line = ngiReader.readLine();
		}
		return layers;
	}

	/**
	 * ngi파일과 동일한 파일명을 가진 nda파일이 없는 경우 ngi의 레이어를 QA20LayerList로 파싱 @author
	 * DY.Oh @Date 2017. 3. 11. 오전 11:28:09 @return QA20LayerList @throws
	 * IOException @throws
	 */
	private QA20LayerList parseQA20Layer() throws IOException {

		QA20LayerList layers = new QA20LayerList();
		String layerID = getLayerID();
		String layerName = getLayerName().replaceAll("\"", "");
		NGIHeader ngiHeader = getNgiHeader();
		String[] layerTypes = getLayerTypes(ngiHeader);
		for (String type : layerTypes) {
			boolean isEquals = layers.isEqualsLayer(layerID, type);
			if (!isEquals) {
				String layerID_type = layerID + "_" + type;
				QA20Layer layer = new QA20Layer(layerID_type, false);
				layer.setLayerName(layerName + "_" + type);
				layer.setLayerType(type);
				layer.setNgiHeader(ngiHeader);
				layers.add(layer);
			}
		}

		String ngiLine = ngiReader.readLine();
		while (ngiLine != null) {
			if (ngiLine.equalsIgnoreCase("<DATA>")) {
				ngiLine = ngiReader.readLine();
				while (!ngiLine.equalsIgnoreCase("<END>")) {
					String featureID = ngiLine;
					QA20FileFeatureParser featureParser = new QA20FileFeatureParser(ngiReader);
					QA20Feature feature = featureParser.parserDTFeature(featureID);
					String type = feature.getFeatureType();
					layers.getQA20Layer(layerID + "_" + type).add(feature);
					ngiReader = featureParser.getNgiReader();
					ngiLine = ngiReader.readLine();
				}
			}
			if (ngiLine.equalsIgnoreCase("<LAYER_END>")) {
				break;
			}
			ngiLine = ngiReader.readLine();
		}
		return layers;
	}

	/**
	 * ngi 파일의 header에 저장된 레이어 타입을 반환 @author DY.Oh @Date 2017. 3. 11. 오전
	 * 11:29:04 @param header @return String[] @throws
	 */
	private String[] getLayerTypes(NGIHeader header) {

		String geometric = header.getGeometric_metadata();
		int startIndex = geometric.indexOf("(");
		int lastIndex = geometric.indexOf(")");

		String metadata = geometric.substring(startIndex + 1, lastIndex);
		String[] types = metadata.split(",");

		return types;
	}

	/**
	 * ngi 파일의 header에 저장된 레이어 ID를 반환 @author DY.Oh @Date 2017. 3. 11. 오전
	 * 11:29:58 @return String @throws IOException @throws
	 */
	private String getLayerID() throws IOException {

		String line = ngiReader.readLine();
		String layerID = "";
		while (line != null) {
			// getLayerID
			if (line.equalsIgnoreCase("$LAYER_ID")) {
				String temp = ngiReader.readLine();
				layerID = temp;
			}
			if (line.equalsIgnoreCase("$END")) {
				break;
			}
			line = ngiReader.readLine();
		}
		return layerID;
	}

	/**
	 * ngi 파일의 header에 저장된 레이어 Name을 반환 @author DY.Oh @Date 2017. 3. 11. 오전
	 * 11:29:58 @return String @throws IOException @throws
	 */
	private String getLayerName() throws IOException {

		String line = ngiReader.readLine();
		String layerName = "";
		while (line != null) {
			// getLayerID
			if (line.equalsIgnoreCase("$LAYER_NAME")) {
				layerName = ngiReader.readLine();
			}
			if (line.equalsIgnoreCase("$END")) {
				break;
			}
			line = ngiReader.readLine();
		}
		return layerName;
	}

	/**
	 * nda 파일의 header 정보를 NDAHeader 객체로 파싱 @author DY.Oh @Date 2017. 3. 11. 오전
	 * 11:30:30 @return NDAHeader @throws IOException @throws
	 */
	private NDAHeader getNdaHeader() throws IOException {

		NDAHeader ndaHeader = new NDAHeader();
		String line = ndaReader.readLine();
		while (!line.equalsIgnoreCase("<END>")) {
			if (line.equalsIgnoreCase("$VERSION")) {
				ndaHeader.setVersion(ndaReader.readLine());
			}
			if (line.equalsIgnoreCase("$ASPATIAL_FIELD_DEF")) {
				ndaHeader.setAspatial_field_def(getAttrib());
			}
			if (line.equalsIgnoreCase("<END>")) {
				break;
			}
			line = ndaReader.readLine();
		}
		return ndaHeader;

	}

	/**
	 * nda 파일에 저장된 객체의 속성정보를 List<NGIField> 객체로 파싱 @author DY.Oh @Date 2017. 3.
	 * 11. 오전 11:31:07 @return List<NGIField> @throws IOException @throws
	 */
	private List<NGIField> getAttrib() throws IOException {

		List<NGIField> fields = new ArrayList<NGIField>();
		boolean isEnd = false;
		while (!isEnd) {
			String line = ndaReader.readLine();
			if (!line.equalsIgnoreCase("$END")) {
				String fieldStr = getAttribText(line);
				String[] fieldsStr = fieldStr.replaceAll(" ", "").split(",");
				// Attrfield
				String fieldName = getQuotesText(fieldsStr[0]);
				String type = fieldsStr[1];
				String size = fieldsStr[2];
				String decimal = fieldsStr[3];
				boolean isUnique;
				if (fieldsStr[4].equals("TRUE")) {
					isUnique = true;
				} else {
					isUnique = false;
				}
				NGIField field = new NGIField(fieldName, type, size, decimal, isUnique);
				fields.add(field);
			} else {
				isEnd = true;
			}
		}
		return fields;
	}

	/**
	 * 문자열에서 큰 따옴표("")안에 해당된 문자열을 반환 @author DY.Oh @Date 2017. 3. 11. 오전
	 * 11:08:21 @param line @return String @throws
	 */
	private String getQuotesText(String line) {
		Pattern p = Pattern.compile("\\\"(.*?)\\\"");
		Matcher m = p.matcher(line);
		if (m.find()) {
			String attr = m.group(1);
			return attr;
		} else {
			return null;
		}
	}

	/**
	 * 문자열에서 괄호( )안에 해당되는 문자열을 반환 @author DY.Oh @Date 2017. 3. 11. 오전
	 * 11:09:53 @param line @return String @throws
	 */
	private String getText(String line) {
		Pattern p = Pattern.compile("\\((.*?)\\)");
		Matcher m = p.matcher(line);
		if (m.find()) {
			String attr = m.group(1);
			return attr;
		} else {
			return null;
		}
	}

	private String getAttribText(String line) {

		int startIndex = line.indexOf("(");
		int endIndex = line.lastIndexOf(")");

		String returnStr = line.substring(startIndex + 1, endIndex);
		return returnStr;
	}

	/**
	 * ngi 파일의 header 정보를 NGIHeader 객체로 파싱 @author DY.Oh @Date 2017. 3. 11. 오전
	 * 11:30:30 @return NGIHeader @throws IOException @throws
	 */
	private NGIHeader getNgiHeader() throws IOException {

		NGIHeader header = new NGIHeader();
		String line = ngiReader.readLine();
		while (line != "<END>") {
			if (line.equalsIgnoreCase("$VERSION")) {
				header.setVersion(ngiReader.readLine());
			}
			if (line.equalsIgnoreCase("$GEOMETRIC_METADATA")) {
				header.setGeometric_metadata(ngiReader.readLine());
				header.setDim(ngiReader.readLine());
				header.setBound(ngiReader.readLine());
			}
			if (line.equalsIgnoreCase("$POINT_REPRESENT")) {
				List<String> point_represent = new ArrayList<String>();
				while (line != null) {
					line = ngiReader.readLine();
					if (!line.equals("$END")) {
						point_represent.add(line);
					} else {
						break;
					}
				}
				header.setPoint_represent(point_represent);
			}
			if (line.equalsIgnoreCase("$LINE_REPRESENT")) {
				List<String> lineString_represent = new ArrayList<String>();
				while (line != null) {
					line = ngiReader.readLine();
					if (!line.equals("$END")) {
						lineString_represent.add(line);
					} else {
						break;
					}
				}
				header.setLine_represent(lineString_represent);
			}
			if (line.equalsIgnoreCase("$REGION_REPRESENT")) {
				List<String> gegion_represent = new ArrayList<String>();
				while (line != null) {
					line = ngiReader.readLine();
					if (!line.equals("$END")) {
						gegion_represent.add(line);
					} else {
						break;
					}
				}
				header.setRegion_represent(gegion_represent);
			}
			if (line.equalsIgnoreCase("$TEXT_REPRESENT")) {
				List<String> text_represent = new ArrayList<String>();
				while (line != null) {
					line = ngiReader.readLine();
					if (!line.equals("$END")) {
						text_represent.add(line);
					} else {
						break;
					}
				}
				header.setText_represent(text_represent);
			}
			if (line.equalsIgnoreCase("<END>")) {
				break;
			}
			line = ngiReader.readLine();
		}
		return header;
	}
}
