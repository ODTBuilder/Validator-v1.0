package com.git.gdsbuilder.geoserver.factory;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.gdsbuilder.geoserver.service.en.EnLayerBboxRecalculate;
import com.git.gdsbuilder.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import com.git.gdsbuilder.geosolutions.geoserver.rest.HTTPUtils;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.GSLayerEncoder;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.GSLayerGroupEncoder;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.GSResourceEncoder;
import com.git.gdsbuilder.geosolutions.geoserver.rest.encoder.feature.GSFeatureTypeEncoder;
import com.git.gdsbuilder.type.geoserver.layer.GeoLayerInfo;

public class DTGeoserverPublisher extends GeoServerRESTPublisher
{
  private static final Logger LOGGER = LoggerFactory.getLogger(DTGeoserverPublisher.class);
  private final String restURL;
  private final String gsuser;
  private final String gspass;

  public DTGeoserverPublisher(String restURL, String username, String password)
  {
    super(restURL, username, password);
    this.restURL = HTTPUtils.decurtSlash(restURL);
    this.gsuser = username;
    this.gspass = password;
  }


  public boolean publishErrLayer(String wsName, String dsName, GeoLayerInfo geoLayerInfo)
  {
    String fileName = geoLayerInfo.getFileName();
    String src = geoLayerInfo.getOriginSrc();
 //   String fullName = "err_" +geoLayerInfo.getFileType()+"_"+ fileName;
    String fullName = geoLayerInfo.getFileName();

    GSFeatureTypeEncoder fte = new GSFeatureTypeEncoder();
    GSLayerEncoder layerEncoder = new GSLayerEncoder();

    fte.setProjectionPolicy(GSResourceEncoder.ProjectionPolicy.REPROJECT_TO_DECLARED);
    fte.setTitle(fullName);
    fte.setNativeName(fullName);
    fte.setName(fullName);
    fte.setSRS(src);

    layerEncoder.setDefaultStyle("defaultStyle");
    boolean flag = super.publishDBLayer(wsName, dsName, fte, layerEncoder);
    return flag;
  }

  public boolean removeLayers(String wsName, List<String> layerNameList)
  {
    boolean flag = false;
    int flagCount = 0;
    for (int i = 0; i < layerNameList.size(); i++) {
      String layerName = (String)layerNameList.get(i);
      flag = removeLayer(wsName, layerName);
      if (!flag) {
        flagCount++;
      }
    }

    if (flagCount > 0) {
      flag = false;
    }
    else {
      flag = true;
    }
    return flag;
  }

  public boolean updateFeatureType(String workspace, String storename, String layername, GSFeatureTypeEncoder fte, GSLayerEncoder layerEncoder, boolean attChangeFlag)
  {
    String ftypeXml = fte.toString();

    StringBuilder putUrl = new StringBuilder(this.restURL).append("/rest/workspaces/")
      .append(workspace).append("/datastores/").append(storename).append("/featuretypes/").append(layername + ".xml");

    if ((layername == null) || (layername.isEmpty())) {
      if (LOGGER.isErrorEnabled())
        LOGGER.error("GSFeatureTypeEncoder has no valid name associated, try using GSFeatureTypeEncoder.setName(String)");
      return false;
    }

    String configuredResult = HTTPUtils.putXml(putUrl.toString(), ftypeXml, this.gsuser, 
      this.gspass);
    boolean updated = configuredResult != null;
    boolean configured = false;

    if (!updated) {
      LOGGER.warn("Error in updating (" + configuredResult + ") " + workspace + ":" + 
        storename + "/" + layername);
    } else {
      LOGGER.info("DB layer successfully updated (layer:" + layername + ")");

      if (layerEncoder == null) {
        if (LOGGER.isErrorEnabled())
          LOGGER.error("GSLayerEncoder is null: Unable to find the defaultStyle for this layer");
        return false;
      }

      configured = configureLayer(workspace, layername, layerEncoder);

      if (!configured)
        LOGGER.warn("Error in configuring (" + configuredResult + ") " + workspace + ":" + 
          storename + "/" + layername);
      else {
    	  if(attChangeFlag){// attribute 수정시 서버 리로드
    		  reload();
    	  }
        LOGGER.info("layer successfully configured (layer:" + layername + ")");
      }
    }
    return (updated) && (configured);
  }
  

  public boolean recalculate(String workspace, String storename, String layername, EnLayerBboxRecalculate type)
  {
    String recalculateType = "";
    GSFeatureTypeEncoder fte = new GSFeatureTypeEncoder();
    fte.setName(layername);

    if (type == null) {
      if (LOGGER.isErrorEnabled())
        LOGGER.error("EnLayerBBoxRecalculate has no valid value associated");
      return false;
    }

    recalculateType = type.getValue();

    String ftypeXml = fte.toString();
    StringBuilder putUrl = new StringBuilder(this.restURL).append("/rest/workspaces/")
      .append(workspace).append("/datastores/").append(storename).append("/featuretypes/").append(layername + "?" + recalculateType);

    if ((layername == null) || (layername.isEmpty())) {
      if (LOGGER.isErrorEnabled())
        LOGGER.error("Please enter a layername");
      return false;
    }
    String configuredResult = HTTPUtils.putXml(putUrl.toString(), ftypeXml, this.gsuser, 
      this.gspass);
    boolean updated = configuredResult != null;
    boolean configured = false;

    if (!updated)
      LOGGER.warn("Error in recalculating (" + configuredResult + ") " + workspace + ":" + 
        storename + "/" + layername);
    else {
      LOGGER.info("layer successfully recalculated (layer:" + layername + ")");
    }
    return (updated) && (configured);
  }
}