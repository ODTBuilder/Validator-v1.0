package com.git.opengds.editor.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONObject;
import org.postgresql.util.PSQLException;

import com.git.opengds.user.domain.UserVO;
import com.vividsolutions.jts.io.ParseException;

public interface EditDTLayerService {

	public boolean editLayer(UserVO userVO, JSONObject layerEditObj)
			throws FileNotFoundException, IOException, ParseException, SchemaException, PSQLException, Exception;

}
