package com.git.gdsbuilder.generalization.rep.type;

public class DTGeneralReportNums {

	private int preNum;
	private int afNum;
	
	public DTGeneralReportNums(int preNum, int afNum){
		this.preNum = preNum;
		this.afNum = afNum;
	}
	
	public int getPreNum() {
		return preNum;
	}
	public void setPreNum(int preNum) {
		this.preNum = preNum;
	}
	public int getAfNum() {
		return afNum;
	}
	public void setAfNum(int afNum) {
		this.afNum = afNum;
	}
}
