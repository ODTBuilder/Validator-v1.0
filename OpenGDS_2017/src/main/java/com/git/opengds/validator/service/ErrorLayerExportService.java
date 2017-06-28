package com.git.opengds.validator.service;

import java.io.IOException;

public interface ErrorLayerExportService {

	public void exportErrorLayer(String format, String type, String name) throws IOException;
}
