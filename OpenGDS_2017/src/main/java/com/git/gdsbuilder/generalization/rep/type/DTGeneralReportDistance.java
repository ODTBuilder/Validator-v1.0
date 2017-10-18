package com.git.gdsbuilder.generalization.rep.type;

public class DTGeneralReportDistance {

	private int totalLine; // 전체 선분수
	private int afLine; // 1000m 초과 선분수
	private int totalObj; // 전체 객체수
	private int afObj; // 500m 이상 객체수

	public int getTotalLine() {
		return totalLine;
	}

	public void setTotalLine(int totalLine) {
		this.totalLine = totalLine;
	}

	public int getAfLine() {
		return afLine;
	}

	public void setAfLine(int afLine) {
		this.afLine = afLine;
	}

	public int getTotalObj() {
		return totalObj;
	}

	public void setTotalObj(int totalObj) {
		this.totalObj = totalObj;
	}

	public int getAfObj() {
		return afObj;
	}

	public void setAfObj(int afObj) {
		this.afObj = afObj;
	}

}
