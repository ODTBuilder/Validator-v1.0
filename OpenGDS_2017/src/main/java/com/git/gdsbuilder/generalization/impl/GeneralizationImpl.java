package com.git.gdsbuilder.generalization.impl;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;

import com.git.gdsbuilder.generalization.Aggregation;
import com.git.gdsbuilder.generalization.Elimination;
import com.git.gdsbuilder.generalization.Generalization;
import com.git.gdsbuilder.generalization.Simplification;
import com.git.gdsbuilder.generalization.data.TopoGeneralizationData;
import com.git.gdsbuilder.generalization.data.res.DTGeneralAfLayer;
import com.git.gdsbuilder.generalization.data.res.DTGeneralEAfLayer;
import com.git.gdsbuilder.generalization.data.topo.TopologyTable;
import com.git.gdsbuilder.generalization.opt.AggregationOption;
import com.git.gdsbuilder.generalization.opt.EliminationOption;
import com.git.gdsbuilder.generalization.opt.SimplificationOption;
import com.git.gdsbuilder.generalization.rep.DTGeneralReport;
import com.git.gdsbuilder.generalization.rep.DTGeneralReport.DTGeneralReportNumsType;
import com.git.gdsbuilder.generalization.rep.type.DTGeneralReportNums;

/**
 * 일반화를 처리한다.
 * @author SG.Lee
 * @Date 2016.10.24 
 * */
public class GeneralizationImpl extends TopoGeneralizationData implements Generalization {
	
	private SimplificationOption simplificationOption;
	
	private EliminationOption eliminationOption;
	
//	private AggregationOption aggregationOption;
	
	
	//일반화 순서
	private GeneralizationOrder order;
	
	//일반화 단계
//	private GeneralizationStep step;
	
	boolean topologyFlag = false;
	
		
/*	public GeneralizationImpl(SimpleFeatureCollection collection , SimplificationOption simplificationOption, EliminationOption eliminationOption, AggregationOption aggregationOption, GeneralizationOrder order, boolean topologyFlag){
		super(collection);
		this.simplificationOption = simplificationOption;
		this.eliminationOption = eliminationOption;
//		this.aggregationOption = aggregationOption;
		this.order = order;
//		this.step = step;
		this.topologyFlag = topologyFlag;
	}
	*/
	
	public GeneralizationImpl(SimpleFeatureCollection collection, SimplificationOption simplificationOption, EliminationOption eliminationOption, GeneralizationOrder order, boolean topologyFlag){
		super(collection);
		this.simplificationOption = simplificationOption;
		this.eliminationOption = eliminationOption;
		this.order = order;
		this.topologyFlag = topologyFlag;
//		this.step = step;
	}
	
		
	
	/**
	 * Generalization 순서 Enum
	 * @author SG.Lee
	 * @Date 2016.10.24
	 * */
	public enum GeneralizationOrder {
		SIMPLIFICATION("simplification"),
		ELIMINATION("elimination"),
		UNKNOWN(null);

		private final String typeName;

		private GeneralizationOrder(String typeName) {
			this.typeName = typeName;
		}

		public static GeneralizationOrder get(String typeName) {
			for (GeneralizationOrder type : values()) {
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
	/*
	public enum GeneralizationStep {
		ONESEPT(1),
		TWOSEPT(2),
		THREESEPT(3),
		UNKNOWN(0);

		private final int stepValue;

		private GeneralizationStep(int stepValue) {
			this.stepValue = stepValue;
		}

		public static GeneralizationStep get(int stepValue) {
			for (GeneralizationStep step : values()) {
				if(step == UNKNOWN)
					continue;
				if(step.stepValue==stepValue)
					return step;
			} 
			return UNKNOWN;
		}
		public int getStepValue(){
			return this.stepValue;
		}
	};*/
	@Override
	public DTGeneralEAfLayer getGeneralization(){
		DTGeneralEAfLayer returnLayerResult = new DTGeneralEAfLayer();
		
		
		if(this.topologyFlag){
			super.topologyBuilder();
			
			SimpleFeatureCollection disConCollection = super.getDisConnectCollection();
			SimpleFeatureCollection conCollection = super.getConnectCollection();
			
			
//			if(this.order.getTypeName().equals("simplification")){
				DTGeneralEAfLayer conSimpleEAfLayer = new SimplificationImpl(conCollection,simplificationOption).getSimplification();
				DTGeneralReport conSimpleReport = conSimpleEAfLayer.getReport();

				//conCollection 객체, 포인트 수
				DTGeneralReportNums conEntityNums = conSimpleReport.getDTGeneralReportNums(DTGeneralReportNumsType.ENTITY);
				DTGeneralReportNums conPointNums = conSimpleReport.getDTGeneralReportNums(DTGeneralReportNumsType.POINT);
				
				
				DTGeneralEAfLayer disConSimpleEAfLayer = new SimplificationImpl(disConCollection,simplificationOption).getSimplification();
				DTGeneralReport disConSimpleReport = disConSimpleEAfLayer.getReport();

				
				//disConCollection 객체, 포인트 수
				DTGeneralReportNums disConEntityNums = disConSimpleReport.getDTGeneralReportNums(DTGeneralReportNumsType.ENTITY);
				DTGeneralReportNums disConPointNums = disConSimpleReport.getDTGeneralReportNums(DTGeneralReportNumsType.POINT);
				
				
				DTGeneralEAfLayer eliEAfLayer = new EliminationImpl(disConSimpleEAfLayer.getCollection(), eliminationOption).getElimination();
				DTGeneralReport eliReport = eliEAfLayer.getReport();
				
				
				DTGeneralReportNums eliEntityNums = eliReport.getDTGeneralReportNums(DTGeneralReportNumsType.ENTITY);
				DTGeneralReportNums eliPointNums = eliReport.getDTGeneralReportNums(DTGeneralReportNumsType.POINT);
				
				DefaultFeatureCollection returnCollection = new DefaultFeatureCollection();
				
				SimpleFeatureCollection conReturnCollection = conSimpleEAfLayer.getCollection();
				SimpleFeatureCollection disReturnConCollection = eliEAfLayer.getCollection();
				
				
				if(conReturnCollection!=null){
					SimpleFeatureIterator conIterator = conReturnCollection.features();
					while(conIterator.hasNext()){
						returnCollection.add(conIterator.next());
					}
				}
				
				if(disReturnConCollection!=null){
					SimpleFeatureIterator disIterator = disReturnConCollection.features();
					while(disIterator.hasNext()){
						returnCollection.add(disIterator.next());
					}
				}
				
				
				//최종 return Report
				DTGeneralReportNums returnEntityNums = new DTGeneralReportNums(conEntityNums.getPreNum()+disConEntityNums.getPreNum(),conEntityNums.getAfNum()+eliEntityNums.getAfNum());
				DTGeneralReportNums returnPointNums = new DTGeneralReportNums(conPointNums.getPreNum()+disConPointNums.getPreNum(),conPointNums.getAfNum()+eliPointNums.getAfNum());
				
				DTGeneralReport returnReport = new DTGeneralReport(returnEntityNums, returnPointNums);
				
				returnLayerResult.setCollection(returnCollection);
				returnLayerResult.setReport(returnReport);
//			}
		}
		else{
			if(this.order.getTypeName().equals("simplification")){
				DTGeneralEAfLayer simpleEAfLayer = new SimplificationImpl(super.getCollection(),simplificationOption).getSimplification();
				DTGeneralReport simpleReport = simpleEAfLayer.getReport();

				//최초 객체, 포인트 수
				DTGeneralReportNums entityNums = simpleReport.getDTGeneralReportNums(DTGeneralReportNumsType.ENTITY);
				DTGeneralReportNums pointNums = simpleReport.getDTGeneralReportNums(DTGeneralReportNumsType.POINT);
				
				DTGeneralReportNums returnEntityNums = new DTGeneralReportNums(entityNums.getPreNum(),entityNums.getAfNum());
				DTGeneralReportNums returnPointNums = new DTGeneralReportNums(pointNums.getPreNum(),pointNums.getAfNum());
				
				
				simpleReport.setEntityNum(returnEntityNums);
				simpleReport.setPointNum(returnPointNums);
				
				returnLayerResult = simpleEAfLayer;
				/*if(simpleEAfLayer!=null){
					SimpleFeatureCollection simpleFeatureCollection = simpleEAfLayer.getCollection();
					DTGeneralEAfLayer eliEAfLayer = new EliminationImpl(simpleFeatureCollection, eliminationOption).getElimination();
					DTGeneralReport eliReport = eliEAfLayer.getReport();
					
					
					DTGeneralReportNums eliEntityNums = eliReport.getDTGeneralReportNums(DTGeneralReportNumsType.ENTITY);
					DTGeneralReportNums eliPointNums = eliReport.getDTGeneralReportNums(DTGeneralReportNumsType.POINT);
					
					
					//최종 return Report
					DTGeneralReportNums returnEntityNums = new DTGeneralReportNums(entityNums.getPreNum(),eliEntityNums.getAfNum());
					DTGeneralReportNums returnPointNums = new DTGeneralReportNums(pointNums.getPreNum(),eliPointNums.getAfNum());
					
					eliReport.setEntityNum(returnEntityNums);
					eliReport.setPointNum(returnPointNums);
					
					eliEAfLayer.setReport(eliReport);
					
					returnLayerResult = eliEAfLayer;
				}*/
			}else{
				DTGeneralEAfLayer eliEAfLayer = new EliminationImpl(super.getCollection(), eliminationOption).getElimination();
				DTGeneralReport eliReport = eliEAfLayer.getReport();
				
				//최초 객체, 포인트 수
				DTGeneralReportNums entityNums = eliReport.getDTGeneralReportNums(DTGeneralReportNumsType.ENTITY);
				DTGeneralReportNums pointNums = eliReport.getDTGeneralReportNums(DTGeneralReportNumsType.POINT);
				
				
				if(eliEAfLayer!=null){
					SimpleFeatureCollection simpleFeatureCollection = eliEAfLayer.getCollection();
					DTGeneralEAfLayer simpleEAfLayer = new SimplificationImpl(simpleFeatureCollection,simplificationOption).getSimplification();
					DTGeneralReport simpleReport = simpleEAfLayer.getReport();
					
					DTGeneralReportNums simEntityNums = simpleReport.getDTGeneralReportNums(DTGeneralReportNumsType.ENTITY);
					DTGeneralReportNums simPointNums = simpleReport.getDTGeneralReportNums(DTGeneralReportNumsType.POINT);
					
					//최종 return Report
					DTGeneralReportNums returnEntityNums = new DTGeneralReportNums(entityNums.getPreNum(),simEntityNums.getAfNum());
					DTGeneralReportNums returnPointNums = new DTGeneralReportNums(pointNums.getPreNum(),simPointNums.getAfNum());
					
					simpleReport.setEntityNum(returnEntityNums);
					simpleReport.setPointNum(returnPointNums);
					
					simpleEAfLayer.setReport(simpleReport);
					
					returnLayerResult = simpleEAfLayer;
				}
			}
		}
		return returnLayerResult;
	}
}
