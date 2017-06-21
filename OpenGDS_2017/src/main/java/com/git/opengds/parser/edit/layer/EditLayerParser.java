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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.git.gdsbuilder.type.qa20.header.NDAField;
import com.git.gdsbuilder.type.qa20.header.NDAHeader;
import com.git.gdsbuilder.type.qa20.header.NGIHeader;
import com.git.gdsbuilder.type.qa20.layer.QA20Layer;
import com.vividsolutions.jts.io.ParseException;

/**
 * JSONObject를 QA20Layer 객체로 파싱하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 3. 11. 오후 2:14:38
 */
public class EditLayerParser {

	protected static String create = "create";
	protected static String modify = "modify";

	JSONObject layerObj;
	String type;
	QA20Layer qa20Layer;
	Map<String, Object> geoLayer;

	boolean isUpdatedAttr = false;

	/**
	 * EditLayerParser 생성자
	 * 
	 * @param layerName
	 * 
	 * @param layerObj
	 * @param state
	 * @throws ParseException
	 */
	public EditLayerParser(String type, JSONObject layerObj, String state) throws ParseException {
		this.type = type;
		this.layerObj = layerObj;
		if (type.equals("ngi")) {
			if (state.equals(create)) {
				ngiCreatedLayerParse();
			} else if (state.equals(modify)) {
				ngiModifiedLayerParse();
				geoModifiedLayerParse();
			}
		}
	}

	public EditLayerParser(String layerID) {
		qa20Layer = new QA20Layer("1", false);
		qa20Layer.setLayerName(layerID);
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

	public void geoModifiedLayerParse() {

		JSONObject geoLayerObj = (JSONObject) layerObj.get("geoserver");
		// geoLayerObj.get("lbound");
		// geoLayerObj.get("style");
		String orignalName = (String) layerObj.get("originLayerName");
		String name = (String) layerObj.get("currentLayerName");
		String title = (String) geoLayerObj.get("title");
		String abstractContent = (String) geoLayerObj.get("stysummaryle");

		geoLayer = new HashMap<String, Object>();
		geoLayer.put("orignalName", orignalName);
		geoLayer.put("name", name);
		geoLayer.put("title", title);
		geoLayer.put("abstractContent", abstractContent);
		geoLayer.put("attChangeFlag", isUpdatedAttr);

	}

	public void ngiModifiedLayerParse() {
		
		QA20Layer modifiedQA20Layer = new QA20Layer();
		
		// name
		String orignName = (String) layerObj.get("originLayerName");
		String currentName = (String) layerObj.get("currentLayerName");
		modifiedQA20Layer.setOriginLayerName(orignName);
		modifiedQA20Layer.setLayerName(currentName);

		// attr
		List<NDAField> orignAttr = parseAttrQA20Layer(orignName, (JSONArray) layerObj.get("attr"));
		List<NDAField> updateAttr = parseAttrQA20Layer(currentName, (JSONArray) layerObj.get("updateAttr"));
		List<NDAField> returnAttr = mergeUpdatedQA20Layers(orignAttr, updateAttr);
		NDAHeader ndaHeader = new NDAHeader("1", returnAttr);
		modifiedQA20Layer.setNdaHeader(ndaHeader);

		// bound
		JSONArray boundArry = (JSONArray) layerObj.get("bound");
		JSONArray minArry = (JSONArray) boundArry.get(0);
		double minX = (Double) minArry.get(0);
		double minY = (Double) minArry.get(1);

		JSONArray maxArry = (JSONArray) boundArry.get(1);
		double maxX = (Double) maxArry.get(0);
		double maxY = (Double) maxArry.get(1);
		String boundStr = "BOUND(" + minX + ", " + minY + ", " + maxX + ", " + maxY + ")";

		NGIHeader ngiHeader = new NGIHeader();
		ngiHeader.setBound(boundStr);

		// represent
		String represent = (String) layerObj.get("represent");
		modifiedQA20Layer.setNgiHeader(ngiHeader);

		this.qa20Layer = modifiedQA20Layer;
	}

	private List<NDAField> mergeUpdatedQA20Layers(List<NDAField> orignAttr, List<NDAField> updateAttr) {

		List<NDAField> returnAttrFields = new ArrayList<NDAField>();
		for (int i = 0; i < orignAttr.size(); i++) {
			boolean isUpdated = false;
			NDAField orignField = orignAttr.get(i);
			NDAField updateField = updateAttr.get(i);

			NDAField returnField = new NDAField();
			if (updateField != null) {
				// fieldName
				String originFieldName = orignField.getFieldName();
				String updateFieldName = updateField.getFieldName();
				if (!originFieldName.equals(updateFieldName)) {
					returnField.setFieldName(updateFieldName);
					isUpdated = true;
				}
				// type
				String originType = orignField.getType();
				String updateType = updateField.getType();

				if (!originType.equals(updateType)) {
					returnField.setType(updateType);
					isUpdated = true;
				}
				// decimal
				String originDecimal = orignField.getDecimal();
				String updateDecimal = updateField.getDecimal();

				if (!originDecimal.equals(updateDecimal)) {
					returnField.setDecimal(updateDecimal);
					isUpdated = true;
				}
				// size
				String originSize = orignField.getSize();
				String updateSize = updateField.getSize();

				if (!originSize.equals(updateSize)) {
					returnField.setSize(updateSize);
					isUpdated = true;
				}
				// isUnique
				boolean originIsUnique = orignField.isUnique();
				boolean updateIsUnique = updateField.isUnique();

				if (!originIsUnique == updateIsUnique) {
					returnField.setUnique(updateIsUnique);
					isUpdated = true;
				}
				// nullable

				if (isUpdated) {
					returnAttrFields.add(returnField);
				}
			} else {
				isUpdated = true;
				continue;
			}
		}
		return returnAttrFields;
	}

	private List<NDAField> parseAttrQA20Layer(String layerName, JSONArray attrArry) {

		List<NDAField> fieldList = new ArrayList<NDAField>();
		for (int i = 0; i < attrArry.size(); i++) {
			JSONObject attr = (JSONObject) attrArry.get(i);
			String fieldName = (String) attr.get("fieldName");
			String type = (String) attr.get("type");
			String decimal = (String) attr.get("decimal");
			String size = (String) attr.get("size");
			String isUniqueStr = (String) attr.get("isUnique");
			boolean isUnique = false;
			if (isUniqueStr.equals("true")) {
				isUnique = true;
			}
			NDAField field = new NDAField(fieldName, type, size, decimal, isUnique);
			fieldList.add(field);
		}
		return fieldList;
	}

	public void ngiCreatedLayerParse() throws ParseException {

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
				List<NDAField> fieldList = new ArrayList<NDAField>();
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
					NDAField fied = new NDAField(fieldName, type, size, decimal, isUnique);
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
				String test = "1 REGIONATTR(SOLID, 1, 14606014, SOLID100, 14606014, 14606014)";
				ngiHeader.addRegion_represent(test);
			}
		}
		qa20Layer.setLayerID("1");
		qa20Layer.setNgiHeader(ngiHeader);
		qa20Layer.setNdaHeader(ndaHeader);
		this.qa20Layer = qa20Layer;
	}
}
