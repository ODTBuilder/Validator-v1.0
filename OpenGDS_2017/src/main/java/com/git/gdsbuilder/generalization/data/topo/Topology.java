package com.git.gdsbuilder.generalization.data.topo;

import java.util.List;

public class Topology {
	private String objID; //객체 id
	private List<String> firstObjs; //첫점과 연결된 객체id List
	private List<String> lastObjs; //끝점과 연결된 객체id List
	private Double alValue; // 해당객체의 면적 or 길이

	public Topology(){}
	
	//생성자
	public Topology(String objID, List<String> firstObj, List<String> lastObj, Double alValue){
		this.objID=objID;
		this.firstObjs=firstObj;
		this.lastObjs=lastObj;
		this.alValue=alValue;
	}
	
	
	/**
	 * Topology GET, SET 
	 * @author SG.Lee
	 * @Date 2016.10.05
	 * */
	public String getObjID() {
		return objID;
	}
	public void setObjID(String objID) {
		this.objID = objID;
	}
	public List<String> getFirstObjs() {
		return firstObjs;
	}
	public void setFirstObjs(List<String> firstObj) {
		this.firstObjs = firstObj;
	}
	public List<String> getLastObjs() {
		return lastObjs;
	}
	public void setLastObjs(List<String> lastObj) {
		this.lastObjs = lastObj;
	}
	public Double getAlValue() {
		return alValue;
	}
	public void setAlValue(Double alValue) {
		this.alValue = alValue;
	}
}
