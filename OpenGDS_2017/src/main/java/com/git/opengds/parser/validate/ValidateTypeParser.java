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

package com.git.opengds.parser.validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.git.gdsbuilder.type.validate.layer.ValidateLayerType;
import com.git.gdsbuilder.type.validate.layer.ValidateLayerTypeList;
import com.git.gdsbuilder.type.validate.option.AttributeFix;
import com.git.gdsbuilder.type.validate.option.ConBreak;
import com.git.gdsbuilder.type.validate.option.ConIntersected;
import com.git.gdsbuilder.type.validate.option.ConOverDegree;
import com.git.gdsbuilder.type.validate.option.EdgeMatchMiss;
import com.git.gdsbuilder.type.validate.option.EntityDuplicated;
import com.git.gdsbuilder.type.validate.option.EntityNone;
import com.git.gdsbuilder.type.validate.option.NodeMiss;
import com.git.gdsbuilder.type.validate.option.OutBoundary;
import com.git.gdsbuilder.type.validate.option.OverShoot;
import com.git.gdsbuilder.type.validate.option.PointDuplicated;
import com.git.gdsbuilder.type.validate.option.RefAttributeMiss;
import com.git.gdsbuilder.type.validate.option.SelfEntity;
import com.git.gdsbuilder.type.validate.option.SmallArea;
import com.git.gdsbuilder.type.validate.option.SmallLength;
import com.git.gdsbuilder.type.validate.option.TwistedPolygon;
import com.git.gdsbuilder.type.validate.option.UselessPoint;
import com.git.gdsbuilder.type.validate.option.ValidatorOption;
import com.git.gdsbuilder.type.validate.option.ZValueAmbiguous;

/**
 * 검수 옵션 JSONArray를 ValidateLayerTypeList 객체로 파싱하는 클래스
 * 
 * @author DY.Oh
 * @Date 2017. 4. 18. 오후 3:25:49
 */
public class ValidateTypeParser {

	/**
	 * 검수 옵션 JSONArray
	 */
	JSONArray validateTypeArray;
	/**
	 * ValidateLayerTypeList 객체
	 */
	ValidateLayerTypeList validateLayerTypeList;

	/**
	 * ValidateTypeParser 생성자
	 * 
	 * @param validateTypeArray
	 *            검수 옵션 JSONArray
	 */
	public ValidateTypeParser(JSONArray validateTypeArray) {
		this.validateTypeArray = validateTypeArray;
		typeListParser();
	}

	/**
	 * ValidateLayerTypeList 객체 반환
	 * 
	 * @return ValidateLayerTypeList
	 */
	public ValidateLayerTypeList getValidateLayerTypeList() {
		return validateLayerTypeList;
	}

	/**
	 * ValidateLayerTypeList 객체 설정
	 * 
	 * @param validateLayerTypeList
	 *            ValidateLayerTypeList 객체
	 */
	public void setValidateLayerTypeList(ValidateLayerTypeList validateLayerTypeList) {
		this.validateLayerTypeList = validateLayerTypeList;
	}

	/**
	 * 검수 옵션 JSONArray를 ValidateLayerTypeList 객체로 파싱
	 */
	public void typeListParser() {

		if (validateTypeArray.size() != 0) {
			this.validateLayerTypeList = new ValidateLayerTypeList();
			for (int j = 0; j < validateTypeArray.size(); j++) {
				JSONObject layerType = (JSONObject) validateTypeArray.get(j);
				ValidateLayerType type = typeOptionParser(layerType);
				validateLayerTypeList.add(type);
				validateLayerTypeList.addAllLayerIdList(type.getLayerIDList());
			}
		}
	}

	/**
	 * 검수 옵션 JSONObject를 ValidateLayerType 객체로 파싱
	 * 
	 * @param layerType
	 *            검수 레이어 및 옵션 JSONObject
	 * @return ValidateLayerType
	 */
	public ValidateLayerType typeOptionParser(JSONObject layerType) {

		ValidateLayerType type = new ValidateLayerType();
		String typeName = (String) layerType.get("typeName");
		JSONArray typeLayers = (JSONArray) layerType.get("layers");
		Object obj = layerType.get("option");
		if (obj != null) {
			JSONObject qaOptionObj = (JSONObject) obj;
			List<ValidatorOption> qaOptions = parseOption(qaOptionObj);
			type.setTypeName(typeName);
			type.setLayerIDList(typeLayers);
			type.setOptionList(qaOptions);
			return type;
		} else {
			type.setTypeName(typeName);
			type.setLayerIDList(typeLayers);
			return type;
		}
	}

	@SuppressWarnings("unchecked")
	private List<ValidatorOption> parseOption(JSONObject qaOptions) {

		List<ValidatorOption> optionList = new ArrayList<ValidatorOption>();
		Iterator optionNames = qaOptions.keySet().iterator();
		while (optionNames.hasNext()) {
			String optionName = (String) optionNames.next();
			if (optionName.equalsIgnoreCase(TwistedPolygon.Type.TWISTEDPOLYGON.errName())) {
				Boolean isTrue = (Boolean) qaOptions.get("TwistedPolygon");
				if (isTrue) {
					TwistedPolygon twistedPolygon = new TwistedPolygon();
					optionList.add(twistedPolygon);
				}
			}
			if (optionName.equalsIgnoreCase(ConBreak.Type.CONBREAK.errName())) {
				Boolean isTrue = (Boolean) qaOptions.get("ConBreak");
				if (isTrue) {
					ConBreak conBreak = new ConBreak();
					optionList.add(conBreak);
				}
			}
			if (optionName.equalsIgnoreCase(ConIntersected.Type.CONINTERSECTED.errName())) {
				Boolean isTrue = (Boolean) qaOptions.get("ConIntersected");
				if (isTrue) {
					ConIntersected conIntersected = new ConIntersected();
					optionList.add(conIntersected);
				}
			}
			if (optionName.equalsIgnoreCase(ConOverDegree.Type.CONOVERDEGREE.errName())) {
				Object getValue = qaOptions.get("ConOverDegree");
				if (getValue == null) {
					continue;
				} else {
					JSONObject conOverObj = (JSONObject) getValue;
					String stringValue = (String) conOverObj.get("figure");
					double value = Double.parseDouble(stringValue);
					ValidatorOption conOverDegree = new ConOverDegree(value);
					optionList.add(conOverDegree);
				}
			}
			if (optionName.equalsIgnoreCase(ZValueAmbiguous.Type.ZVALUEAMBIGUOUS.errName())) {
				Object z_Value = qaOptions.get("ZValueAmbiguous");
				if (z_Value == null) {
					continue;
				} else {
					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					JSONObject zvalueJsonObj = (JSONObject) z_Value;
					Iterator iterator = zvalueJsonObj.keySet().iterator();
					while (iterator.hasNext()) {
						String layerName = (String) iterator.next();
						JSONObject zValueObj = (JSONObject) zvalueJsonObj.get(layerName);
						hashMap.put(layerName, zValueObj);
					}
					ValidatorOption zValueAmbiguous = new ZValueAmbiguous(hashMap);
					optionList.add(zValueAmbiguous);
				}
			}
			if (optionName.equalsIgnoreCase(UselessPoint.Type.USELESSPOINT.errName())) {
				Boolean isTrue = (Boolean) qaOptions.get("UselessPoint");
				if (isTrue) {
					UselessPoint uselessPoint = new UselessPoint();
					optionList.add(uselessPoint);
				}
			}
			if (optionName.equalsIgnoreCase(EntityDuplicated.Type.ENTITYDUPLICATED.errName())) {
				Boolean isTrue = (Boolean) qaOptions.get("EntityDuplicated");
				if (isTrue) {
					EntityDuplicated entityDuplicated = new EntityDuplicated();
					optionList.add(entityDuplicated);
				}
			}
			if (optionName.equalsIgnoreCase(OutBoundary.Type.OUTBOUNDARY.errName())) {
				Object outBoundaryObj = qaOptions.get("OutBoundary");
				if (outBoundaryObj == null) {
					continue;
				} else {
					List<String> relations = new ArrayList<String>();
					JSONObject outBoundaryValue = (JSONObject) outBoundaryObj;
					JSONArray relationValues = (JSONArray) outBoundaryValue.get("relation");
					int valueSize = relationValues.size();
					for (int i = 0; i < valueSize; i++) {
						String relationID = (String) relationValues.get(i);
						relations.add(relationID);
					}
					ValidatorOption outBoundary = new OutBoundary(relations);
					optionList.add(outBoundary);
				}
			}
			if (optionName.equalsIgnoreCase(SmallArea.Type.SMALLAREA.errName())) {
				Object smallAreaObj = qaOptions.get("SmallArea");
				if (smallAreaObj == null) {
					continue;
				} else {
					String areaStr = (String) ((JSONObject) smallAreaObj).get("figure");
					double area = Double.parseDouble(areaStr);
					ValidatorOption smallArea = new SmallArea(area);
					optionList.add(smallArea);
				}
			}
			if (optionName.equalsIgnoreCase(SmallLength.Type.SMALLLENGTH.errName())) {
				Object smallLengthObj = qaOptions.get("SmallLength");
				if (smallLengthObj == null) {
					continue;
				} else {
					String lengthStr = (String) ((JSONObject) smallLengthObj).get("figure");
					double length = Double.parseDouble(lengthStr);
					ValidatorOption smallLength = new SmallLength(length);
					optionList.add(smallLength);
				}
			}
			if (optionName.equalsIgnoreCase(SelfEntity.Type.SELFENTITY.errName())) {
				Object selfEntityObj = qaOptions.get("SelfEntity");
				if (selfEntityObj == null) {
					continue;
				} else {
					List<String> relations = new ArrayList<String>();
					JSONObject selfEntityValue = (JSONObject) selfEntityObj;
					JSONArray relationValues = (JSONArray) selfEntityValue.get("relation");
					int valueSize = relationValues.size();
					for (int i = 0; i < valueSize; i++) {
						String relationID = (String) relationValues.get(i);
						relations.add(relationID);
					}
					ValidatorOption selfEntity = new SelfEntity(relations);
					optionList.add(selfEntity);
				}
			}
			if (optionName.equalsIgnoreCase(OverShoot.Type.OVERSHOOT.errName())) {
				Object overShootObj = qaOptions.get("OverShoot");
				if (overShootObj == null) {
					continue;
				} else {
					JSONObject overShootValue = (JSONObject) overShootObj;
					String boundaryName = (String) overShootValue.get("boundary");
					String figureStr = (String) overShootValue.get("figure");
					double figure = Double.parseDouble(figureStr);
					ValidatorOption overShoot = new OverShoot(boundaryName, figure);
					optionList.add(overShoot);
				}
			}
			if (optionName.equalsIgnoreCase(PointDuplicated.Type.POINTDUPLICATED.errName())) {
				Boolean isTrue = (Boolean) qaOptions.get("PointDuplicated");
				if (isTrue) {
					PointDuplicated pointDuplicated = new PointDuplicated();
					optionList.add(pointDuplicated);
				}
			}

			if (optionName.equalsIgnoreCase(AttributeFix.Type.ATTRIBUTEFIX.errName())) {
				Object attributeFixObj = qaOptions.get("AttributeFix");
				if (attributeFixObj == null) {
					continue;
				} else {
					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					JSONObject attributeFixValue = (JSONObject) attributeFixObj;
					Iterator iterator = attributeFixValue.keySet().iterator();
					while (iterator.hasNext()) {
						String layerName = (String) iterator.next();
						JSONObject attributeObj = (JSONObject) attributeFixValue.get(layerName);
						hashMap.put(layerName, attributeObj);
					}
					ValidatorOption attributeFix = new AttributeFix(hashMap);
					optionList.add(attributeFix);
				}
			}

			if (optionName.equalsIgnoreCase(NodeMiss.Type.NODEMISS.errName())) {
				Object nodeMissObj = qaOptions.get("NodeMiss");
				if (nodeMissObj == null) {
					continue;
				} else {
					List<String> relations = new ArrayList<String>();
					JSONObject nodeMissValue = (JSONObject) nodeMissObj;
					JSONArray relationValues = (JSONArray) nodeMissValue.get("relation");
					int valueSize = relationValues.size();
					for (int i = 0; i < valueSize; i++) {
						String relationID = (String) relationValues.get(i);
						relations.add(relationID);
					}
					ValidatorOption nodeMiss = new NodeMiss(relations);
					optionList.add(nodeMiss);
				}
			}
			if (optionName.equalsIgnoreCase(EntityNone.Type.ENTITYNONE.errName())) {
				Boolean isTrue = (Boolean) qaOptions.get("EntityNone");
				if (isTrue) {
					EntityNone entityNone = new EntityNone();
					optionList.add(entityNone);
				}
			}
			if (optionName.equalsIgnoreCase(EdgeMatchMiss.Type.EDGEMATCHMISS.errName())) {
				Boolean isTrue = (Boolean) qaOptions.get("EdgeMatchMiss");
				if (isTrue) {
					EdgeMatchMiss edgeMatchMiss = new EdgeMatchMiss();
					optionList.add(edgeMatchMiss);
				}
			}
			if (optionName.equalsIgnoreCase(RefAttributeMiss.Type.RefAttributeMiss.errName())) {
				Object refAttributeMissObj = qaOptions.get("RefAttributeMiss");
				if (refAttributeMissObj == null) {
					continue;
				} else {
					HashMap<String, List<String>> optMap = new HashMap<String, List<String>>();
					JSONObject refAttributeMissJSON = (JSONObject) refAttributeMissObj;
					Iterator<String> iterator = refAttributeMissJSON.keySet().iterator();
					while (iterator.hasNext()) {
						String layerName = (String) iterator.next();
						List<String> attLists = new ArrayList<String>();
						attLists = (List<String>) refAttributeMissJSON.get(layerName);
						optMap.put(layerName, attLists);
					}
					ValidatorOption refAttributeMiss = new RefAttributeMiss(optMap);
					optionList.add(refAttributeMiss);
				}
			}
		}
		return optionList;
	}
}
