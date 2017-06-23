package com.git.opengds.editor.service;

import org.json.simple.JSONObject;

import com.vividsolutions.jts.io.ParseException;

public interface EditFeatureService {
	public void editFeature(JSONObject featureEditObj) throws ParseException, org.json.simple.parser.ParseException;
}
