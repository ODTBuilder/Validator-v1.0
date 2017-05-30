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

package com.git.opengds.parser.edit.layer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.git.gdsbuilder.type.qa20.header.NDAHeader;
import com.git.gdsbuilder.type.qa20.header.NGIField;
import com.git.gdsbuilder.type.qa20.header.NGIHeader;
import com.git.gdsbuilder.type.qa20.layer.QA20Layer;
import com.git.gdsbuilder.type.simple.layer.Layer;
import com.vividsolutions.jts.io.ParseException;

/**
 * JSONObject를 QA20Layer 객체로 파싱하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:14:38
 */
public class EditLayerParser {

	protected static final int isNone = 0;
	protected static final int isEdited = 1;
	protected static final int isCreated = 2;
	protected static final int isDeleted = 3;

	JSONObject layerObj;
	String type;
	Layer layer;
	QA20Layer qa20Layer;

	/**
	 * EditLayerParser 생성자
	 * 
	 * @param layerName
	 * 
	 * @param layerObj
	 * @throws ParseException
	 */
	public EditLayerParser(String type, JSONObject layerObj) throws ParseException {
		this.type = type;
		this.layerObj = layerObj;
		if (type.equals("ngi")) {
			ngiLayerParse();
		}
	}

	public Layer getLayer() {
		return layer;
	}

	public void setLayer(Layer layer) {
		this.layer = layer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public QA20Layer getQa20Layer() {
		return qa20Layer;
	}

	public void setQa20Layer(QA20Layer qa20Layer) {
		this.qa20Layer = qa20Layer;
	}

	public void ngiLayerParse() throws ParseException {

		QA20Layer qa20Layer = new QA20Layer();
		NGIHeader ngiHeader = new NGIHeader();
		NDAHeader ndaHeader = new NDAHeader();

		Iterator iterator = layerObj.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (key.equals("layerName")) {
				String layerName = (String) layerObj.get(key);
				qa20Layer.setLayerName(layerName);
			} else if (key.equals("layerType")) {
				String layerType = (String) layerObj.get(key);
				qa20Layer.setLayerType(layerType);
				String mask = "MASK(" + layerType + ")";
				ngiHeader.setGeometric_metadata(mask);
			} else if (key.equals("attr")) {
				List<NGIField> fieldList = new ArrayList<NGIField>();
				JSONArray attrArray = (JSONArray) layerObj.get(key);
				for (int i = 0; i < attrArray.size(); i++) {
					JSONObject attrObj = (JSONObject) attrArray.get(i);
					String fieldName = (String) attrObj.get("fieldName");
					String type = (String) attrObj.get("type");
					String decimal = (String) attrObj.get("decimal");
					String size = (String) attrObj.get("size");
					String isUniqueStr = (String) attrObj.get("isUnique");
					boolean isUnique = true;
					if (isUniqueStr.equals("false")) {
						isUnique = false;
					}
					NGIField fied = new NGIField(fieldName, type, size, decimal, isUnique);
					fieldList.add(fied);
				}
				ndaHeader.setAspatial_field_def(fieldList);
			} else if (key.equals("version")) {
				String version = (String) layerObj.get(key);
				ngiHeader.setVersion(version);
			} else if (key.equals("dim")) {
				String dim = (String) layerObj.get(key);
				ngiHeader.setDim("DIM(" + dim + ")");
			} else if (key.equals("bound")) {
				JSONArray boundArr = (JSONArray) layerObj.get(key);
				JSONArray minXY = (JSONArray) boundArr.get(0);
				JSONArray maxXY = (JSONArray) boundArr.get(1);
				String bound = "BOUND(" + minXY.get(0).toString() + ", " + minXY.get(1).toString() + ", "
						+ maxXY.get(0).toString() + ", " + maxXY.get(1).toString() + ")";
				ngiHeader.setBound(bound);
			} else if (key.equals("represent")) {

			}
		}
		qa20Layer.setNgiHeader(ngiHeader);
		qa20Layer.setNdaHeader(ndaHeader);
		this.qa20Layer = qa20Layer;
	}
}
