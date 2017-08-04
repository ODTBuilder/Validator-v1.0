package com.git.gdsbuilder.type.dxf.structure;

public class DTDXFClass {

	String dxfRecordName;
	String cppClassName;
	String code90;
	String code280;
	String code281;

	public DTDXFClass(String dxfRecordName, String cppClassName, String code90, String code280, String code281) {
		super();
		this.dxfRecordName = dxfRecordName;
		this.cppClassName = cppClassName;
		this.code90 = code90;
		this.code280 = code280;
		this.code281 = code281;
	}

	public String getDxfRecordName() {
		return dxfRecordName;
	}

	public void setDxfRecordName(String dxfRecordName) {
		this.dxfRecordName = dxfRecordName;
	}

	public String getCppClassName() {
		return cppClassName;
	}

	public void setCppClassName(String cppClassName) {
		this.cppClassName = cppClassName;
	}

	public String getCode90() {
		return code90;
	}

	public void setCode90(String code90) {
		this.code90 = code90;
	}

	public String getCode280() {
		return code280;
	}

	public void setCode280(String code280) {
		this.code280 = code280;
	}

	public String getCode281() {
		return code281;
	}

	public void setCode281(String code281) {
		this.code281 = code281;
	}

}
