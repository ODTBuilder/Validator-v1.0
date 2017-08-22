package com.git.opengds.editor.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONObject;

import com.git.opengds.user.domain.UserVO;
import com.vividsolutions.jts.io.ParseException;

public interface EditDTFeatureService {
	public void editFeature(UserVO userVO, JSONObject featureEditObj) throws ParseException,
			org.json.simple.parser.ParseException, SchemaException, FileNotFoundException, IOException;
}
