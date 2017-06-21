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
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.git.gdsbuilder.type.qa20.feature.QA20Feature;
import com.git.gdsbuilder.type.qa20.header.NDAHeader;
import com.git.gdsbuilder.type.qa20.header.NDAField;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;

/**
 * ngi/nda 파일의 feature를 QA20Feature객체로 파싱하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오전 11:18:11
 */
public class QA20FileFeatureParser {

	BufferedReader ngiReader;
	BufferedReader ndaReader;

	/**
	 * QA20FileLayerParser 생성자
	 * 
	 * @param ngiReader
	 * @param ndaReader
	 */
	public QA20FileFeatureParser(BufferedReader ngiReader, BufferedReader ndaReader) {
		this.ngiReader = ngiReader;
		this.ndaReader = ndaReader;
	}

	/**
	 * QA20FileFeatureParser 생성자
	 * 
	 * @param ngiReader
	 */
	public QA20FileFeatureParser(BufferedReader ngiReader) {
		this.ngiReader = ngiReader;
	}

	/**
	 * BufferedReader getter @author DY.Oh @Date 2017. 3. 11. 오전
	 * 10:42:35 @return BufferedReader @throws
	 */
	public BufferedReader getNgiReader() {
		return ngiReader;
	}

	/**
	 * BufferedReader setter @author DY.Oh @Date 2017. 3. 11. 오전 10:43:28 @param
	 * ngiReader void @throws
	 */
	public void setNgiReader(BufferedReader ngiReader) {
		this.ngiReader = ngiReader;
	}

	/**
	 * BufferedReader getter @author DY.Oh @Date 2017. 3. 11. 오전
	 * 10:43:41 @return BufferedReader @throws
	 */
	public BufferedReader getNdaReader() {
		return ndaReader;
	}

	/**
	 * BufferedReader setter @author DY.Oh @Date 2017. 3. 11. 오전 10:43:51 @param
	 * ndaReader void @throws
	 */
	public void setNdaReader(BufferedReader ndaReader) {
		this.ndaReader = ndaReader;
	}

	/**
	 * 문자열에서 특수문자 제거 @author DY.Oh @Date 2017. 3. 11. 오전 10:44:42 @param
	 * str @return String @throws
	 */
	private String StringReplace(String str) {
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
		str = str.replaceAll(match, "");
		return str;
	}

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
	 * ngi/nda 파일에서 featureID에 해당하는 객체를 QA20Feature객체로 파싱
	 * 
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오전 10:45:08
	 * @param featureID
	 * @return QA20Feature
	 * @throws IOException
	 */
	public QA20Feature parserDTFeature(String featureID) throws IOException {

		QA20Feature feature = null;

		String id = StringReplace(featureID);
		String line = ngiReader.readLine();
		if (line.equalsIgnoreCase("POLYGON")) {
			feature = parserDTPolygonFeature(id);
		}
		if (line.equalsIgnoreCase("LINESTRING")) {
			feature = parserDTLineStringFeature(id);
		}
		if (line.equalsIgnoreCase("POINT")) {
			feature = parserDTPointFeature(id);
		}
		if (line.startsWith("TEXT")) {
			feature = parserDTPointFeature(id, line);
		}
		return feature;
	}

	/**
	 * TEXT 타입의 객체를 QA20Feature객체로 파싱
	 * 
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오전 10:59:30
	 * @param featureID
	 * @param text
	 * @return QA20Feature
	 * @throws IOException
	 */
	private QA20Feature parserDTPointFeature(String featureID, String text) throws IOException {

		String textValue = getText(text);
		String line = ngiReader.readLine();
		String[] strCoor = line.split(" ");
		Coordinate coor = new Coordinate(Double.parseDouble(strCoor[0]), Double.parseDouble(strCoor[1]));
		GeometryFactory factory = new GeometryFactory();
		Geometry point = factory.createPoint(coor);
		String graphicID = getGraphicID();
		QA20Feature feature = new QA20Feature(featureID, "TEXT", null, null, point, graphicID);
		feature.putProperty("TEXT", textValue);
		return feature;
	}

	/**
	 * 문자열에서 괄호( )안에 해당되는 문자열을 반환 @author DY.Oh @Date 2017. 3. 11. 오전
	 * 11:09:53 @param line @return String @throws
	 */
	private String getText(String line) {
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
	 * ngi파일에서 객체의 numparts 정보를 반환 @author DY.Oh @Date 2017. 3. 11. 오전
	 * 11:11:16 @return String @throws IOException @throws
	 */
	private String getLinearRingCount() throws IOException {

		String line = ngiReader.readLine();
		if (line.startsWith("NUMPARTS")) {
			return line;
		} else {
			return null;
		}
	}

	/**
	 * ngi파일에서 객체의 포인트 갯수를 반환 @author DY.Oh @Date 2017. 3. 11. 오전
	 * 11:16:00 @return String @throws IOException @throws
	 */
	private String getCoordinateCount() throws IOException {
		return ngiReader.readLine();
	}

	/**
	 * ngi파일에서 객체의 그래픽 ID를 반환 @author DY.Oh @Date 2017. 3. 11. 오전
	 * 11:16:02 @return String @throws IOException @throws
	 */
	private String getGraphicID() throws IOException {
		return ngiReader.readLine();
	}

	/**
	 * POINT 타입의 객체를 QA20Feature객체로 파싱
	 * 
	 * @author DY.Oh
	 * @Date 2017. 5. 11. 오전 10:59:30
	 * @param featureID
	 * @param text
	 * @return QA20Feature
	 * @throws IOException
	 */
	private QA20Feature parserDTPointFeature(String featureID) throws IOException {

		String line = ngiReader.readLine();
		String[] strCoor = line.split(" ");
		Coordinate coor = new Coordinate(Double.parseDouble(strCoor[0]), Double.parseDouble(strCoor[1]));
		GeometryFactory factory = new GeometryFactory();
		Geometry point = factory.createPoint(coor);
		String graphicID = getGraphicID();
		QA20Feature feature = new QA20Feature(featureID, "POINT", null, null, point, graphicID);
		return feature;
	}

	/**
	 * LINESTRING 타입의 객체를 QA20Feature객체로 파싱
	 * 
	 * @author DY.Oh
	 * @Date 2017. 5. 11. 오전 10:59:30
	 * @param featureID
	 * @param text
	 * @return QA20Feature
	 * @throws IOException
	 */
	private QA20Feature parserDTLineStringFeature(String featureID) throws IOException {

		String strCount = getCoordinateCount();
		int i = 0;
		int count = Integer.valueOf(strCount);
		Coordinate[] coors = new Coordinate[count];
		while (i != count) {
			String line = ngiReader.readLine();
			String[] strCoor = line.split(" ");
			Coordinate coor = new Coordinate(Double.parseDouble(strCoor[0]), Double.parseDouble(strCoor[1]));
			coors[i] = coor;
			i++;
		}
		GeometryFactory factory = new GeometryFactory();
		Geometry lineString = factory.createLineString(coors);
		String graphicID = getGraphicID();
		QA20Feature feature = new QA20Feature(featureID, "LINESTRING", null, String.valueOf(count), lineString,
				graphicID);
		return feature;

	}

	/**
	 * POLYGON 타입의 객체를 QA20Feature객체로 파싱
	 * 
	 * @author DY.Oh
	 * @Date 2017. 5. 11. 오전 10:59:30
	 * @param featureID
	 * @param text
	 * @return QA20Feature
	 * @throws IOException
	 */
	public QA20Feature parserDTPolygonFeature(String featureID) throws IOException {

		// polygon 일 경우 linearing의 갯수
		String ringCountStr = getLinearRingCount().replace("NUMPARTS ", "");
		int ringCount = Integer.valueOf(ringCountStr);

		Geometry polygon = null;
		if (ringCount == 1) {
			polygon = createPolygon();
		}
		if (ringCount > 1) {
			polygon = createHullPolygon(ringCount);
		}
		String graphicID = getGraphicID();
		QA20Feature feature = new QA20Feature(featureID, "POLYGON", ringCountStr,
				String.valueOf(polygon.getNumPoints()), polygon, graphicID);
		return feature;
	}

	/**
	 * POLYGON 타입의 좌표값을 Geometry 객체로 생성 @author DY.Oh @Date 2017. 5. 11. 오전
	 * 11:04:10 @return @throws IOException Geometry @throws
	 */
	private Geometry createPolygon() throws IOException {

		String strCount = getCoordinateCount();
		int count = Integer.valueOf(strCount);
		int i = 0;
		Coordinate[] coors = new Coordinate[count + 1];
		while (i != count) {
			String line = ngiReader.readLine();
			String[] strCoor = line.split(" ");
			Coordinate coor = new Coordinate(Double.parseDouble(strCoor[0]), Double.parseDouble(strCoor[1]));
			coors[i] = coor;
			i++;
		}
		coors[i] = coors[0];
		GeometryFactory factory = new GeometryFactory();
		Geometry linearRing = factory.createLinearRing(coors);
		Geometry polygon = factory.createPolygon((LinearRing) linearRing, null);

		return polygon;
	}

	/**
	 * HULL POLYGON 타입의 좌표값을 Geometry 객체로 생성 @author DY.Oh @Date 2017. 5. 11. 오전
	 * 11:04:10 @return @throws IOException Geometry @throws
	 */
	private Geometry createHullPolygon(int ringCount) throws IOException {

		LinearRing shell = null;
		LinearRing[] hulls = new LinearRing[ringCount - 1];
		hulls[0] = shell;
		for (int j = 0; j < ringCount; j++) {
			String strCount = getCoordinateCount();
			int count = Integer.valueOf(strCount);
			int i = 0;
			Coordinate[] coors = new Coordinate[count + 1];
			while (i != count) {
				String line = ngiReader.readLine();
				String[] strCoor = line.split(" ");
				Coordinate coor = new Coordinate(Double.parseDouble(strCoor[0]), Double.parseDouble(strCoor[1]));
				coors[i] = coor;
				i++;
			}
			coors[i] = coors[0];
			GeometryFactory factory = new GeometryFactory();
			LinearRing linearRing = factory.createLinearRing(coors);
			if (j == 0) {
				shell = linearRing;
			} else {
				hulls[j - 1] = linearRing;
			}
		}
		GeometryFactory factory = new GeometryFactory();
		Geometry polygon = factory.createPolygon(shell, hulls);
		return polygon;
	}

	/**
	 * nda파일에서 객체의 속성값과 데이터 타입을 읽어 Hashtable 형태로 반환
	 * 
	 * @author DY.Oh
	 * @Date 2017. 5. 11. 오전 10:45:08
	 * @param ndaHeader
	 * @return Hashtable<String, Object>
	 * @throws IOException
	 */
	public HashMap<String, Object> getFeatureAttrib(NDAHeader ndaHeader, boolean isInvalid) throws IOException {

		if (isInvalid) {
			String line = ndaReader.readLine();
			String tmpLine = line.replaceAll(" ", "");

			HashMap<String, Object> properties = new HashMap<String, Object>();
			List<NDAField> fields = ndaHeader.getAspatial_field_def();

			for (NDAField field : fields) {
				String field_name = field.getFieldName();
				String valueType = field.getType();
				String decimal = field.getDecimal();
				if (valueType.equalsIgnoreCase("NUMERIC")) {
					int cutIndex = tmpLine.indexOf(",");
					String valueStr = tmpLine.substring(0, cutIndex);
					if (valueStr.equals("")) {
						properties.put(field_name, null);
						continue;
					}
					if (Integer.parseInt(decimal) > 0) {
						double doubleValue = Double.parseDouble(valueStr);
						properties.put(field_name, doubleValue);
						tmpLine = tmpLine.substring(cutIndex);
					} else if (Integer.parseInt(decimal) == 0) {
						int intValue = Integer.parseInt(valueStr);
						properties.put(field_name, intValue);
						tmpLine = tmpLine.substring(cutIndex);
					}
				} else if (valueType.equalsIgnoreCase("STRING")) {
					String valueStr = getQuotesText(tmpLine);
					if (valueStr.equals("")) {
						properties.put(field_name, null);
					} else {
						properties.put(field_name, valueStr);
					}
					String replacedStr = "";
					for (int idx = 0; idx < valueStr.length(); ++idx) {
						String strOne = new String(new char[] { valueStr.charAt(idx) }, 0, 1);
						if (strOne.equals("(") || strOne.equals(")") || strOne.equals("{") || strOne.equals("}")
								|| strOne.equals("^") || strOne.equals("[") || strOne.equals("'" + "]")) {
							replacedStr += "\\" + strOne;
						} else if (strOne.equals("*") || strOne.equals("+") || strOne.equals("$") || strOne.equals("|")
								|| strOne.equals("?")) {
							replacedStr += "[" + strOne + "]";
						} else {
							replacedStr += strOne;
						}
					}
					tmpLine = tmpLine.replaceAll(replacedStr, "");
				} else {
					properties.put(field_name, null);
				}
				int cutIndex2 = tmpLine.indexOf(",");
				tmpLine = tmpLine.substring(cutIndex2 + 1);
			}
			return properties;
		} else {
			HashMap<String, Object> properties = new HashMap<String, Object>();
			List<NDAField> fields = ndaHeader.getAspatial_field_def();
			for (NDAField field : fields) {
				String field_name = field.getFieldName();
				properties.put(field_name, null);
			}
			return properties;
		}
	}
}
