package com.git.gdsbuilder.type.dxf.feature.style;

public class DTDXFLWPolylineStyle extends DTDXFPolylineStyle {

	private double constantwidth = 0.0;
	private double elevation = 0.0;

	public DTDXFLWPolylineStyle() {
		super();
	}

	public double getConstantwidth() {
		return constantwidth;
	}

	public void setConstantwidth(double constantwidth) {
		this.constantwidth = constantwidth;
	}

	public double getElevation() {
		return elevation;
	}

	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

}
