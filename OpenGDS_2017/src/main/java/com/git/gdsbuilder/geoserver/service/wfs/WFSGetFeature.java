package com.git.gdsbuilder.geoserver.service.wfs;

public class WFSGetFeature {
	private final static String SERVICE = "WFS";
	private final static String REQUEST = "GetFeature";
	
	private String serverURL ="";
	private String version="1.1.1";
	private String typeName="";
	private String outputformat="text/javascript";
	private String bbox="";
	private String format_options="callback:getJson";
	
	public WFSGetFeature(){};
	
	public WFSGetFeature(String serverURL, String version, String typeName, String outputformat, String bbox,
			String format_options) {
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
		if (!bbox.trim().equals("")) {
			this.bbox = bbox;
		}
		if (!format_options.trim().equals("")) {
			this.format_options = format_options;
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
	
	public String getWFSGetFeatureURL(){
		StringBuffer urlBuffer = new StringBuffer();
		if(!this.serverURL.equals("")){
			urlBuffer.append(serverURL);
			urlBuffer.append("?");
			urlBuffer.append("request="+REQUEST);
			urlBuffer.append("&");
			urlBuffer.append("service="+SERVICE);
			if(!this.version.equals("")){
				urlBuffer.append("&");
				urlBuffer.append("version="+version);
			}
			if(!this.typeName.equals("")){
				urlBuffer.append("&");
				urlBuffer.append("typeName="+typeName);
			}
			if(!this.outputformat.equals("")){
				urlBuffer.append("&");
				urlBuffer.append("outputformat="+outputformat);
			}
			if(!this.bbox.equals("")){
				urlBuffer.append("&");
				urlBuffer.append("bbox="+bbox);
			}
			if(!this.format_options.equals("")){
				urlBuffer.append("&");
				urlBuffer.append("format_options="+format_options);
			}
		}
		else
			return "";
		return urlBuffer.toString();
	}
}
