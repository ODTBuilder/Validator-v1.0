package com.git.gdsbuilder.generalization.data.res;

import org.geotools.data.simple.SimpleFeatureCollection;

import com.git.gdsbuilder.generalization.data.topo.TopologyTable;
import com.git.gdsbuilder.generalization.rep.DTGeneralReport;

/**
 *
 * @author SG.Lee
 * @Date 2017. 9. 27. 오후 4:34:28
 * */
public class DTGeneralEAfLayer {
	private SimpleFeatureCollection collection; //일반화 하고 난후의 결과
	private TopologyTable topologyTable; // 일반화 TopologyTable -> 모든 객체관계정보
	private DTGeneralReport report; // 일반화 레포트
	
	
	public DTGeneralEAfLayer(){
		
	}
	
	
	// DTGeneralAfLayer
	// GET, SET
	public SimpleFeatureCollection getCollection() {
		return collection;
	}
	public void setCollection(SimpleFeatureCollection collection) {
		this.collection = collection;
	}
	public DTGeneralReport getReport() {
		return report;
	}
	public void setReport(DTGeneralReport report) {
		this.report = report;
	}
	public TopologyTable getTopologyTable() {
		return topologyTable;
	}
	public void setTopologyTable(TopologyTable topologyTable) {
		this.topologyTable = topologyTable;
	}
	
	/**
	 * 일반화를 실행
	 * @author SG.Lee
	 * @Date 2016.09
	 * @param preLayer - 일반화 하기전 레이어 객체
	 *        order - 일반화 순서
	 * @return DTGeneralAfLayer - 일반화 결과
	 * @throws 
	 * */
	/*public DTGeneralAfLayer generalization(DTGeneralPreLayer preLayer, GeneralizationOrder order, GeneralizationStep step) throws IOException{
		return (new GeneralizationImpl(preLayer,order,step)).getGeneralzation();
	}*/
}
