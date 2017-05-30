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

import com.git.gdsbuilder.geosolutions.geoserver.rest.decoder.RESTLayerGroup;
import com.git.gdsbuilder.geosolutions.geoserver.rest.decoder.utils.JDOMBuilder;

/**
 * DTGeoGroupLayer에 대한 정보를 담고있는 클래스
 * @author SG.Lee
 * @Date 2017. 2
 * */
public class DTGeoGroupLayer {
	private String name;
	private JSONObject BBox = new JSONObject();
	private String crs;
//	private List<String> layerNames;
	
	/**
	 * DTGeoGroupLayer Build
	 * @author SG.Lee
	 * @Date 2017.2
	 * @param response - 요청 URL
	 * @return DTGeoGroupLayer 
	 * @throws
	 * */
	public static DTGeoGroupLayer build(String response) {
		Element elem = JDOMBuilder.buildElement(response);
		return elem == null ? null : new DTGeoGroupLayer(elem);
	}

	
	/**
	 * DTGeoGroupLayer 생성자
	 * @param groupLayerElem
	 */
	@SuppressWarnings("unchecked")
	public DTGeoGroupLayer(Element groupLayerElem) {
		RESTLayerGroup groupLayer = new RESTLayerGroup(groupLayerElem);
		this.name = groupLayer.getName();
		this.BBox.put("minx", groupLayer.getMinX());
		this.BBox.put("miny", groupLayer.getMinY());
		this.BBox.put("maxx", groupLayer.getMaxX());
		this.BBox.put("maxy", groupLayer.getMaxY());
		this.crs = groupLayer.getCRS();
//		this.layerNames = groupLayer.getLayerList().getNames();
	}
	
	/**
	 * DTGeoGroupLayer GET, SET
	 * 
	 * @author SG.Lee
	 * @Date 2017.3
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JSONObject getbBox() {
		return BBox;
	}

	public void setbBox(JSONObject BBox) {
		this.BBox = BBox;
	}

	public String getCrs() {
		return crs;
	}

	public void setCrs(String crs) {
		this.crs = crs;
	}

	/*public List<String> getLayerNames() {
		return layerNames;
	}

	public void setLayerNames(List<String> layerNames) {
		this.layerNames = layerNames;
	}*/
}
