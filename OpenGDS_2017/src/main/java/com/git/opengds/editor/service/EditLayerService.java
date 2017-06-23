package com.git.opengds.editor.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.geotools.feature.SchemaException;
import org.json.simple.JSONObject;
import org.postgresql.util.PSQLException;

import com.vividsolutions.jts.io.ParseException;

public interface EditLayerService {

	public void editLayer(JSONObject layerEditObj)
			throws FileNotFoundException, IOException, ParseException, SchemaException, PSQLException, Exception;

}
