package com.git.gdsbuilder.geoserver.service.wms;

public class WMSGetLegendGraphic {

	private final static String SERVICE = "WMS";
	private final static String REQUEST = "GetLegendGraphic";
	private String serverURL = "";
	private String version = "1.0.0";
	private String format = "";
	private int width = 0;
	private int height = 0;
	private String layer = "";
	private int scale = 0;
	private String legend_options = "";
	
	
	public WMSGetLegendGraphic(String serverURL, String version, String format, int width, int height, String layer, int scale, String legend_options){
		super();
		if (!serverURL.trim().equals("")) {
			this.serverURL = serverURL;
		}
		if (!version.trim().equals("")) {
			this.version = version;
		}
		if (!format.trim().equals("")) {
			this.format = format;
		}
		if (width != 0) {
			this.width = width;
		}
		if (height != 0) {
			this.height = height;
		}
		if (scale != 0) {
			this.scale = scale;
		}
		if (!layer.trim().equals("")) {
			this.layer = layer;
		}
		if (!legend_options.trim().equals("")) {
			this.legend_options = legend_options;
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
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getLayer() {
		return layer;
	}
	public void setLayer(String layer) {
		this.layer = layer;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public String getLegend_options() {
		return legend_options;
	}
	public void setLegend_options(String legend_options) {
		this.legend_options = legend_options;
	}
	public static String getService() {
		return SERVICE;
	}
	public static String getRequest() {
		return REQUEST;
	}
	
	public String getWMSGetLegendGraphicURL() {
		StringBuffer urlBuffer = new StringBuffer();
		if (!this.serverURL.equals("")) {
			urlBuffer.append(serverURL);
			urlBuffer.append("?");
			urlBuffer.append("request=" + REQUEST);
			urlBuffer.append("&");
			urlBuffer.append("service=" + SERVICE);
			if (!this.version.equals("")) {
				urlBuffer.append("&");
				urlBuffer.append("version=" + version);
			}
			if (!this.format.equals("")) {
				urlBuffer.append("&");
				urlBuffer.append("format=" + format);
			}
			if (!this.layer.equals("")) {
				urlBuffer.append("&");
				urlBuffer.append("layer=" + layer);
			}
			if (!this.legend_options.equals("")) {
				urlBuffer.append("&");
				urlBuffer.append("srs=" + legend_options);
			}
			urlBuffer.append("&");
			urlBuffer.append("width=" + String.valueOf(this.width));
			urlBuffer.append("&");
			urlBuffer.append("height=" + String.valueOf(this.height));
			urlBuffer.append("&");
			urlBuffer.append("scale=" + String.valueOf(this.scale));
		} else
			return "";
		return urlBuffer.toString();
	}
}
