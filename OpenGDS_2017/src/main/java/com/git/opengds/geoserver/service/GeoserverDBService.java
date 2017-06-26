package com.git.opengds.geoserver.service;

import com.git.gdsbuilder.FileRead.en.EnFileFormat;

public interface GeoserverDBService {

	public boolean selectEditLayerDuplicateCheck(EnFileFormat fileFormat, String fileName, String layerName);
}
