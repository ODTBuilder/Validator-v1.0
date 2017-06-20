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

/*
 *  GeoServer-Manager - Simple Manager Library for GeoServer
 *  
 *  Copyright (C) 2007,2011 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.git.gdsbuilder.geolayer.data;

import java.util.List;

import org.jdom.Element;
import org.json.simple.JSONObject;

import com.git.gdsbuilder.geosolutions.geoserver.rest.decoder.RESTFeatureType;
import com.git.gdsbuilder.geosolutions.geoserver.rest.decoder.utils.JDOMBuilder;


/**
 * DTGroupLayer에 대한 정보를 담고있는 클래스
 * @author SG.Lee
 * @Date 2017. 2
 * */
public class DTGeoLayer {
	/*
	 * public enum Type { SHP("SHP"), DB("DB"), UNKNOWN(null);
	 * 
	 * private final String layerType;
	 * 
	 * private Type(String layerType) { this.layerType = layerType; }
	 * 
	 * public static Type get(String layerType) { for (Type type : values()) {
	 * if(type == UNKNOWN) continue; if(type.layerType.equals(layerType)) return
	 * type; } return UNKNOWN; } };
	 */

	private String nativeName; // 원본이름
	private String lName; // 레이어이름
	private String title; // 간략한 레이어 설명
	private String abstractContent;
	private String srs; // 좌표체계
	private JSONObject llbBox = new JSONObject(); // LatLonBoundingBox
	private JSONObject nbBox = new JSONObject(); // NativeBoundingBox
	private String dsType; // 저장소타입
	private String geomType; // 공간정보타입
	private JSONObject attInfo = new JSONObject(); // 속성정보
	private String style;

	/**
	 * DTGeoLayer Build
	 * @author SG.Lee
	 * @Date 2017. 2
	 * @param response - 요청 URL
	 * @return DTGeoLayer
	 * @throws
	 * */
	public static DTGeoLayer build(String response) {
		Element elem = JDOMBuilder.buildElement(response);
		return elem == null ? null : new DTGeoLayer(elem);
	}

	/**
	 * DTGeoLayer 생성자
	 * @param layerElem
	 */
	@SuppressWarnings("unchecked")
	public DTGeoLayer(Element layerElem) {
		RESTFeatureType featureType = new RESTFeatureType(layerElem);
		this.nativeName = featureType.getNativeName();
		this.lName = featureType.getName();
		this.title = featureType.getTitle();
		this.abstractContent = featureType.getAbstract();
		this.geomType = this.buildGeomType(layerElem);
		this.attInfo = this.buildAttType(layerElem);
		this.dsType = this.buildStoreType(layerElem);
		this.srs = this.buildSRS(layerElem);
		this.nbBox.put("minx", featureType.getNativeBoundingBox().getMinX());
		this.nbBox.put("miny", featureType.getNativeBoundingBox().getMinY());
		this.nbBox.put("maxx", featureType.getNativeBoundingBox().getMaxX());
		this.nbBox.put("maxy", featureType.getNativeBoundingBox().getMaxY());
		this.llbBox.put("minx", featureType.getLatLonBoundingBox().getMinX());
		this.llbBox.put("miny", featureType.getLatLonBoundingBox().getMinY());
		this.llbBox.put("maxx", featureType.getLatLonBoundingBox().getMaxX());
		this.llbBox.put("maxy", featureType.getLatLonBoundingBox().getMaxY());
		this.style = null;
	}

	
	/**
	 * DTGeoLayer의 Geom 파라미터 값 부여
	 * @author SG.Lee
	 * @Date 2017. 2
	 * @param layerElem
	 * @return String
	 * @throws
	 * */
	private String buildGeomType(Element layerElem) {
		String geomType = "";
		Element attsElement = layerElem.getChild("attributes");
		if (attsElement != null) {
			List<Element> list = attsElement.getChildren();
			for (int i = 0; i < list.size(); i++) {
				Element attElement = list.get(i);
				String nameAtt = attElement.getChildText("name");
				int flag = nameAtt.indexOf("geom");
				if (flag != -1) {
					String geomAtt = attElement.getChildText("binding");
					int size = geomAtt.length();
					if (size > 28) {
						geomType = attElement.getChildText("binding").substring(28);
						break;
					} else {
						geomType = "";
						break;
					}
				}
			}
		}
		return geomType;
	}

	/**
	 * DTGeoLayer의 attInfo 파라미터 값 리턴
	 * @author SG.Lee
	 * @Date 2017. 5. 10. 오후 9:40:23
	 * @param layerElem
	 * @return JSONObject
	 * @throws
	 * */
	@SuppressWarnings("unchecked")
	private JSONObject buildAttType(Element layerElem) {
		JSONObject object = new JSONObject();
		Element attsElement = layerElem.getChild("attributes");
		if (attsElement != null) {
			List<Element> list = attsElement.getChildren();
			for (int i = 0; i < list.size(); i++) {
				Element attElement = list.get(i);
				String nameAtt = attElement.getChildText("name");
				String nillable = attElement.getChildText("nillable");
				int flag = nameAtt.indexOf("geom");
				if (flag == -1) {
					String bingding = attElement.getChildText("binding");
					JSONObject attContent = new JSONObject();
					String type = bingding.substring(10);
					if (type.equals("BigDecimal")) {
						type = "Double";
					}
					attContent.put("type",type);
					attContent.put("nillable", nillable);
					object.put(nameAtt, attContent);
				}
			}
		}
		return object;
	}

	/**
	 * DTGeoLayer의 srs파라미터 값 리턴
	 * @author SG.Lee
	 * @Date 2017. 2
	 * @param layerElem
	 * @return String
	 * @throws
	 * */
	@SuppressWarnings("unused")
	private String buildSRS(Element layerElem) {
		return layerElem.getChildText("srs");
	}

	/**
	 * DTGeoLayer의 attInfo 파라미터 값 리턴
	 * @author SG.Lee
	 * @Date 2017. 2
	 * @param layerElem
	 * @return String
	 * @throws
	 * */
	@SuppressWarnings("unused")
	private String buildStoreType(Element layerElem) {
		return layerElem.getChild("store").getAttributeValue("class");
	}
	
	/**
	 * DTGeolayer GET, SET
	 * 
	 * @author SG.Lee
	 * @Date 2017.2
	 */
	public String getNativeName() {
		return nativeName;
	}

	public void setNativeName(String nativeName) {
		this.nativeName = nativeName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAbstractContent() {
		return abstractContent;
	}

	public void setAbstractContent(String abstractContent) {
		this.abstractContent = abstractContent;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getSrs() {
		return srs;
	}

	public void setSrs(String srs) {
		this.srs = srs;
	}

	public JSONObject getNbBox() {
		return nbBox;
	}

	public void setNbBox(JSONObject nbBox) {
		this.nbBox = nbBox;
	}

	public String getDsType() {
		return dsType;
	}

	public void setDsType(String dsType) {
		this.dsType = dsType;
	}

	public String getGeomType() {
		return geomType;
	}

	public void setGeomType(String geomType) {
		this.geomType = geomType;
	}

	public JSONObject getAttInfo() {
		return attInfo;
	}

	public void setAttInfo(JSONObject attInfo) {
		this.attInfo = attInfo;
	}

	public JSONObject getLlbBox() {
		return llbBox;
	}

	public void setLlbBox(JSONObject llbBox) {
		this.llbBox = llbBox;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

}
