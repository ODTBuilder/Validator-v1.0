package com.git.gdsbuilder.generalization.rep;

import java.io.Serializable;

import com.git.gdsbuilder.generalization.rep.type.DTGeneralReportNums;

/**
 * 일반화결과 레포트
 * @author SG.Lee
 * @Date 2016.10
 * */
public class DTGeneralReport implements Serializable {
	private DTGeneralReportNums entityNums;
	private DTGeneralReportNums pointNums; 
	
	/**
	 * 일반화레포트 Type
	 * @author SG.Lee
	 * @Date 2016.10.24
	 * */
	public enum DTGeneralReportNumsType {
		ENTITY("entity"),
		POINT("point"),
		UNKNOWN(null);

		private final String typeName;

		private DTGeneralReportNumsType(String typeName) {
			this.typeName = typeName;
		}

		public static DTGeneralReportNumsType get(String typeName) {
			for (DTGeneralReportNumsType type : values()) {
				if(type == UNKNOWN)
					continue;
				if(type.typeName.equals(typeName))
					return type;
			}
			return UNKNOWN;
		}
		public String getTypeName(){
			return this.typeName;
		}
	};
	
	public DTGeneralReport(){
		
	}
	
	public DTGeneralReport(DTGeneralReportNums entityNums, DTGeneralReportNums pointNums) {
		// TODO Auto-generated constructor stub
		this.entityNums = entityNums;
		this.pointNums = pointNums;
	}
	
	public DTGeneralReportNums getDTGeneralReportNums(DTGeneralReportNumsType numsType) {
		if(numsType == DTGeneralReportNumsType.ENTITY){
			return this.entityNums;
		}
		else if(numsType == DTGeneralReportNumsType.POINT){
			return this.pointNums;
		}
		else
			return null;
	}
	
	
	public void setEntityNum(DTGeneralReportNums entityNums) {
		this.entityNums = entityNums;
	}
	public void setPointNum(DTGeneralReportNums pointNum) {
		this.pointNums = pointNum;
	}
}
