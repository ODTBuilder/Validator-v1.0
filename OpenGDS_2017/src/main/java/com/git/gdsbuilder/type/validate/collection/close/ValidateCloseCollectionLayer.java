package com.git.gdsbuilder.type.validate.collection.close;

import java.util.HashMap;
import java.util.Map;

import com.git.gdsbuilder.type.geoserver.layer.GeoLayer;
import com.git.gdsbuilder.validator.collection.opt.ValCollectionOption;
import com.git.gdsbuilder.validator.collection.rule.MapSystemRule;
import com.git.gdsbuilder.validator.collection.rule.MapSystemRule.MapSystemRuleType;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Polygon;

public class ValidateCloseCollectionLayer {
	private Map<MapSystemRuleType, GeoLayer> collectionMap;
	private double tolorence = 0.0;
	private ValCollectionOption closeValidateOptions;
	private Map<MapSystemRuleType, LineString> collectionBoundary = new HashMap<MapSystemRule.MapSystemRuleType, LineString>();
	private Map<MapSystemRuleType, Polygon> targetFeaturesGetBoundary = new HashMap<MapSystemRule.MapSystemRuleType, Polygon>();
	private Map<MapSystemRuleType, Polygon> nearFeaturesGetBoundary = new HashMap<MapSystemRule.MapSystemRuleType, Polygon>();
	
	
	public ValidateCloseCollectionLayer(GeoLayer targetLayer, Map<MapSystemRuleType, GeoLayer> collectionMap, double tolorence, ValCollectionOption closeValidateOptions, Map<MapSystemRuleType, LineString> collectionBoundary
			,Map<MapSystemRuleType, Polygon> targetFeaturesGetBoundary, Map<MapSystemRuleType, Polygon> nearFeaturesGetBoundary) {
		// TODO Auto-generated constructor stub
		this.collectionMap = collectionMap;
		this.collectionBoundary = collectionBoundary;
		this.tolorence = tolorence;
		this.closeValidateOptions = closeValidateOptions;
		this.targetFeaturesGetBoundary = targetFeaturesGetBoundary;
		this.nearFeaturesGetBoundary = nearFeaturesGetBoundary;
	}


	public Map<MapSystemRuleType, Polygon> getTargetFeaturesGetBoundary() {
		return targetFeaturesGetBoundary;
	}


	public void setTargetFeaturesGetBoundary(Map<MapSystemRuleType, Polygon> targetFeaturesGetBoundary) {
		this.targetFeaturesGetBoundary = targetFeaturesGetBoundary;
	}


	public Map<MapSystemRuleType, Polygon> getNearFeaturesGetBoundary() {
		return nearFeaturesGetBoundary;
	}


	public void setNearFeaturesGetBoundary(Map<MapSystemRuleType, Polygon> nearFeaturesGetBoundary) {
		this.nearFeaturesGetBoundary = nearFeaturesGetBoundary;
	}


	public Map<MapSystemRuleType, LineString> getCollectionBoundary() {
		return collectionBoundary;
	}
	public void setCollectionBoundary(Map<MapSystemRuleType, LineString> collectionBoundary) {
		this.collectionBoundary = collectionBoundary;
	}

	public Map<MapSystemRuleType, GeoLayer> getCollectionMap() {
		return collectionMap;
	}

	public void setCollectionMap(Map<MapSystemRuleType, GeoLayer> collectionMap) {
		this.collectionMap = collectionMap;
	}

	public double getTolorence() {
		return tolorence;
	}

	public void setTolorence(double tolorence) {
		this.tolorence = tolorence;
	}

	public ValCollectionOption getCloseValidateOptions() {
		return closeValidateOptions;
	}

	public void setCloseValidateOptions(ValCollectionOption closeValidateOptions) {
		this.closeValidateOptions = closeValidateOptions;
	}
}
