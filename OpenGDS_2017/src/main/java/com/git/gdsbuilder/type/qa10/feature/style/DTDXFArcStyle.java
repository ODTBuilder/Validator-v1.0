package com.git.gdsbuilder.type.qa10.feature.style;

public class DTDXFArcStyle extends DTDXFStyle {

	private double radius = 0.0;
	private double start_angle;
	private double end_angle;
	private boolean counterclockwise = false;

	public DTDXFArcStyle() {
		super();
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getStart_angle() {
		return start_angle;
	}

	public void setStart_angle(double start_angle) {
		this.start_angle = start_angle;
	}

	public double getEnd_angle() {
		return end_angle;
	}

	public void setEnd_angle(double end_angle) {
		this.end_angle = end_angle;
	}

	public boolean isCounterclockwise() {
		return counterclockwise;
	}

	public void setCounterclockwise(boolean counterclockwise) {
		this.counterclockwise = counterclockwise;
	}

}
