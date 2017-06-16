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
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2012, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */

package com.git.gdsbuilder.type.geoserver.parser;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.SchemaException;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import com.git.gdsbuilder.convertor.impl.JsonFromUrl;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayer;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerList;

/**
 * GeoServer로부터 레이어 정보를 받아오는 클래스
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 1:39:13
 * */
public class GeoLayerParser {

	String collectionName;
	String layerName;
	JSONArray layers;
	GeoLayer layer;
	GeoLayerList layerList;
	
	/**
	 * LayerParser 생성자
	 * @param collectionName
	 * @param layerName
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws SchemaException
	 */
	public GeoLayerParser(String collectionName, String layerName)
			throws FileNotFoundException, IOException, ParseException, SchemaException {
		this.collectionName = collectionName;
		this.layerName = layerName;
		this.layer = layerParse();
	}

	/**
	 * LayerParser 생성자
	 * @param collectionName
	 * @param layers
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws SchemaException
	 */
	public GeoLayerParser(String collectionName, JSONArray layers)
			throws FileNotFoundException, IOException, ParseException, SchemaException {
		this.collectionName = collectionName;
		this.layers = layers;
		this.layerList = layersParse();
	}

	/**
	 * layer getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 1:39:21
	 * @return Layer
	 * @throws
	 * */
	public GeoLayer getLayer() {
		return layer;
	}

	/**
	 * layer setter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 1:39:30
	 * @param layer void
	 * @throws
	 * */
	public void setLayer(GeoLayer layer) {
		this.layer = layer;
	}

	/**
	 * layerList getter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 1:39:28
	 * @return LayerList
	 * @throws
	 * */
	public GeoLayerList getLayerList() {
		return layerList;
	}

	/**
	 * layerList setter
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 1:39:26
	 * @param layerList void
	 * @throws
	 * */
	public void setLayerList(GeoLayerList layerList) {
		this.layerList = layerList;
	}

	/**
	 * 해당하는 Layer 객체를 GeoServer로부터 받아 반환
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 1:39:24
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws SchemaException Layer
	 * @throws
	 * */
	public GeoLayer layerParse() throws FileNotFoundException, IOException, ParseException, SchemaException {

		JsonFromUrl jsonFromUrl = new JsonFromUrl();
		SimpleFeatureCollection sfc = jsonFromUrl.readJsonFromUrl(collectionName, layerName, 10000, true, "ngi");
		//SimpleFeatureCollection sfc = jsonFromUrl.readJsonFromUrl(collectionName, layerName, 10000, true, "dxf");

		if(sfc != null) {
			GeoLayer layer = new GeoLayer();
			layer.setLayerName(this.layerName);
			layer.setLayerType("layerType");
			layer.setSimpleFeatureCollection(sfc);
			return layer;
		} else {
			return null;
		}
	}

	/**
	 * 해당하는 Layer 객체를 GeoServer로부터 받아 반환
	 * @author DY.Oh
	 * @Date 2017. 3. 11. 오후 1:39:35
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws SchemaException LayerList
	 * @throws
	 * */
	public GeoLayerList layersParse() throws FileNotFoundException, IOException, ParseException, SchemaException {

		GeoLayerList layerList = new GeoLayerList();
		for (int i = 0; i < layers.size(); i++) {
			this.layerName = (String) layers.get(i);
			GeoLayer layer = layerParse();
			if(layer != null) {
				layerList.add(layer);
			}
		}
		return layerList;
	}
}
