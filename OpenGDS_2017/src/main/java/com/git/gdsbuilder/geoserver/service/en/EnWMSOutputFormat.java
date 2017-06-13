package com.git.gdsbuilder.geoserver.service.en;

/**
 * GeoserverService 타입
 * @author SG.Lee
 * @Date 2017. 6. 5. 오후 5:45:47
 * */
public enum EnWMSOutputFormat {
	PNG("PNG", "image/png"), 
	PNG8("PNG8", "image/png8"), 
	JPEG("JPEG", "image/jpeg"),
	GIF("GIF", "image/gif"),
	TIFF("TIFF", "image/tiff"),
	TIFF8("TIFF8", "image/tiff8"),
	GeoTIFF("GeoTIFF", "image/geotiff"),
	GeoTIFF8("GeoTIFF8", "image/geotiff8"),
	SVG("SVG", "image/svg"),
	PDF("PDF", "application/pdf"),
	GEORSS("GEORSS", "rss"),
	KML("KML", "kml"),
	KMZ("KMZ", "kmz"); 
	String state;
	String stateName;
	
	EnWMSOutputFormat(String state, String stateName) {
		this.state = state;
		this.stateName = stateName;
	}
	
	public String getState() {
		return state;
		
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
