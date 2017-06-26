package com.git.gdsbuilder.geoserver.service.wfs;

import com.git.gdsbuilder.geoserver.service.en.EnGeoserverService;

public class WFSGetFeature {
	private final static String SERVICE = EnGeoserverService.WFS.getState();
	private final static String REQUEST = "GetFeature";
	
	private String serverURL ="";
	private String version="1.1.1";
	private String typeName="";
	private String outputformat="text/javascript";
	private int maxFeatures = 0; 
	private String bbox="";
	private String format_options="";
	private String featureID = "";

	public WFSGetFeature(){};
	
	public WFSGetFeature(String serverURL, String version, String typeName, String outputformat, int maxFeatures, String bbox,
			String format_options, String featureID) {
		super();
		if(!serverURL.trim().equals("")){
			this.serverURL = serverURL;
		}
		if (!version.trim().equals("")) {
			this.version = version;
		}
		if (!typeName.trim().equals("")) {
			this.typeName = typeName;
		}
		if (!outputformat.trim().equals("")) {
			this.outputformat = outputformat;
		}
		if (maxFeatures!=0) {
			this.maxFeatures = maxFeatures;
		}
		if (!bbox.trim().equals("")) {
			this.bbox = bbox;
		}
		if (!format_options.trim().equals("")) {
			this.format_options = format_options;
		}
		if (!featureID.trim().equals("")) {
			this.featureID = featureID;
		}
	}
	
	public WFSGetFeature(String serverURL, String version, String typeName, String bbox) {
		super();
		if(!serverURL.trim().equals("")){
			this.serverURL = serverURL;
		}
		if (!version.trim().equals("")) {
			this.version = version;
		}
		if (!typeName.trim().equals("")) {
			this.typeName = typeName;
		}
		if (!bbox.trim().equals("")) {
			this.bbox = bbox;
		}
	}
	
	public String getServerURL() {
		return serverURL;
	}
	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getOutputformat() {
		return outputformat;
	}
	public void setOutputformat(String outputformat) {
		this.outputformat = outputformat;
	}
	public int getMaxFeatures() {
		return maxFeatures;
	}
	public void setMaxFeatures(int maxFeatures) {
		this.maxFeatures = maxFeatures;
	}
	public String getBbox() {
		return bbox;
	}
	public void setBbox(String bbox) {
		this.bbox = bbox;
	}
	public String getFormat_options() {
		return format_options;
	}
	public void setFormat_options(String format_options) {
		this.format_options = format_options;
	}
	public static String getService() {
		return SERVICE;
	}
	public static String getRequest() {
		return REQUEST;
	}
	public String getFeatureID() {
		return featureID;
	}

	public void setFeatureID(String featureID) {
		this.featureID = featureID;
	}
	
	public String getWFSGetFeatureURL(){
		StringBuffer urlBuffer = new StringBuffer();
		if(!this.serverURL.trim().equals("")){
			urlBuffer.append(serverURL);
			urlBuffer.append("?");
			urlBuffer.append("request="+REQUEST);
			urlBuffer.append("&");
			urlBuffer.append("service="+SERVICE);
			if(!this.version.trim().equals("")){
				urlBuffer.append("&");
				urlBuffer.append("version="+version);
			}
			if(!this.typeName.trim().equals("")){
				urlBuffer.append("&");
				urlBuffer.append("typeName="+typeName);
			}
			if(!this.outputformat.trim().equals("")){
				urlBuffer.append("&");
				urlBuffer.append("outputformat="+outputformat);
			}
			if(this.maxFeatures!=0){
				urlBuffer.append("&");
				urlBuffer.append("maxFeatures="+String.valueOf(maxFeatures));
			}
			if(!this.bbox.trim().equals("")){
				urlBuffer.append("&");
				urlBuffer.append("bbox="+bbox);
			}
			if(!this.format_options.trim().equals("")){
				urlBuffer.append("&");
				urlBuffer.append("format_options="+format_options);
			}
			if(!this.featureID.trim().equals("")){
				urlBuffer.append("&");
				urlBuffer.append("featureID="+featureID);
			}
		}
		else
			return "";
		return urlBuffer.toString();
	}
}
