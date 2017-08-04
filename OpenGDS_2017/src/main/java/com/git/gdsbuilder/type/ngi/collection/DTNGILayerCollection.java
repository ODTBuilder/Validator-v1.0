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

package com.git.gdsbuilder.type.ngi.collection;

import org.apache.commons.lang.StringUtils;

import com.git.gdsbuilder.type.ngi.layer.DTNGILayer;
import com.git.gdsbuilder.type.ngi.layer.DTNGILayerList;

/**
 * QA20LayerCollection 정보를 저장하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:29:59
 */
public class DTNGILayerCollection {

	String id = StringUtils.EMPTY;
	String fileName = StringUtils.EMPTY;
	DTNGILayerList qa20LayerList = new DTNGILayerList();

	/**
	 * QA20LayerCollection 생성자
	 */
	public DTNGILayerCollection() {
		super();
	}

	/**
	 * QA20LayerCollection 생성자
	 * 
	 * @param layrerID
	 */
	public DTNGILayerCollection(String layrerID) {
		super();
		this.id = layrerID;
	}

	/**
	 * QA20LayerCollection 생성자
	 * 
	 * @param id
	 * @param fileName
	 * @param qa20LayerList
	 */
	public DTNGILayerCollection(String id, String fileName, DTNGILayerList qa20LayerList) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.qa20LayerList = qa20LayerList;
	}

	/**
	 * id getter @author DY.Oh @Date 2017. 3. 11. 오후 2:30:09 @return
	 * String @throws
	 */
	public String getId() {
		return id;
	}

	/**
	 * id setter @author DY.Oh @Date 2017. 3. 11. 오후 2:30:11 @param id
	 * void @throws
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * fileName getter @author DY.Oh @Date 2017. 3. 11. 오후 2:30:13 @return
	 * String @throws
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * fileName setter @author DY.Oh @Date 2017. 3. 11. 오후 2:30:16 @param
	 * fileName void @throws
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * QA20LayerList getter @author DY.Oh @Date 2017. 3. 11. 오후 2:30:18 @return
	 * QA20LayerList @throws
	 */
	public DTNGILayerList getQa20LayerList() {
		return qa20LayerList;
	}

	/**
	 * QA20LayerList setter @author DY.Oh @Date 2017. 3. 11. 오후 2:30:20 @param
	 * qa20LayerList void @throws
	 */
	public void setQa20LayerList(DTNGILayerList qa20LayerList) {
		this.qa20LayerList = qa20LayerList;
	}

	/**
	 * qa20LayerList에 qa20Layer를 더함 @author DY.Oh @Date 2017. 3. 11. 오후
	 * 2:30:22 @param qa20Layer void @throws
	 */
	public void addQA20Layer(DTNGILayer qa20Layer) {
		qa20LayerList.add(qa20Layer);
	}

	/**
	 * qa20LayerList에 qa20LayerList를 더함 @author DY.Oh @Date 2017. 3. 11. 오후
	 * 2:30:24 @param layers void @throws
	 */
	public void addAllQA20Layers(DTNGILayerList qa20LayerList) {
		this.qa20LayerList.addAll(qa20LayerList);
	}

	public void addEqualIdQA20Layer(DTNGILayer qa20Layer) {

		for (int i = 0; i < qa20LayerList.size(); i++) {
			DTNGILayer listLayer = qa20LayerList.get(i);
			String listLayerId = listLayer.getLayerID();
		}
	}
}
