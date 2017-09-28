package com.git.gdsbuilder.generalization.impl;

import java.io.IOException;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.generalization.Simplification;
import com.git.gdsbuilder.generalization.data.TopoGeneralizationData;
import com.git.gdsbuilder.generalization.data.res.DTGeneralEAfLayer;
import com.git.gdsbuilder.generalization.opt.SimplificationOption;
import com.git.gdsbuilder.generalization.rep.DTGeneralReport;
import com.git.gdsbuilder.generalization.rep.type.DTGeneralReportNums;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.simplify.TopologyPreservingSimplifier;

public class SimplificationImpl implements Simplification {

	private SimplificationOption option;
	private SimpleFeatureCollection collection;
	
	
	public SimplificationImpl(SimpleFeatureCollection collection, SimplificationOption option){
		this.collection = collection;
		this.option = option;
	}
	
	
	public SimpleFeatureCollection getCollection() {
		return collection;
	}

	public void setCollection(SimpleFeatureCollection collection) {
		this.collection = collection;
	}
	public SimplificationOption getOption() {
		return option;
	}
	public void setOption(SimplificationOption option) {
		this.option = option;
	}

	
	
	public DTGeneralEAfLayer getSimplification(){
		//일반화 결과 레이어 생성 및 초기화
		//DTGeneralEAfLayer - 토폴로지 빌드를 하지 않은 결과레이어
		DTGeneralEAfLayer afLayer = new DTGeneralEAfLayer();
		
		//Simplification 결과 -> DTGeneralEAfLayer 선언 및 초기화
		DTGeneralReportNums entityNums = new DTGeneralReportNums(0,0);
		DTGeneralReportNums pointNums = new DTGeneralReportNums(0,0);
		
		//결과레포트 생성 및 초기화
		DTGeneralReport resultReport = new DTGeneralReport(entityNums, pointNums);
		
		//SimpleFeatureCollection 생성 및 초기화 -> 일반화 결과 SimpleFeatureCollection
		DefaultFeatureCollection resultCollection = new DefaultFeatureCollection();
		
		
		if(collection!=null&&option!=null){
			SimpleFeatureIterator featureIterator = collection.features();
			
			//Simplification Report Nums 결과  
			int entityPreNum = collection.size();
			int entityAfNum = 0;
			int pointPreNum = 0;
			int pointAfNum = 0;
			
			while(featureIterator.hasNext()){
				SimpleFeature feature = featureIterator.next();
				Geometry geom = (Geometry) feature.getDefaultGeometry();
				pointPreNum = pointPreNum + geom.getNumPoints();
				
				Geometry newGeom = TopologyPreservingSimplifier.simplify(geom, option.getOption());
				
				pointAfNum = pointAfNum + newGeom.getNumPoints();
				
				feature.setDefaultGeometry(newGeom);
				resultCollection.add(feature);
			}
			
			try {
				entityAfNum =resultCollection.getCount();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return null;
			}
			//객체, 포인트수 SET
			entityNums.setPreNum(entityPreNum);
			entityNums.setAfNum(entityAfNum);
			pointNums.setPreNum(pointPreNum);
			pointNums.setAfNum(pointAfNum);
			
			//결과레포트 SET
			resultReport.setEntityNum(entityNums);
			resultReport.setPointNum(pointNums);
			
			//결과레이어 SET
			afLayer.setCollection(resultCollection);
			afLayer.setReport(resultReport);
		}
		else
			return null;
		
		return afLayer;
	}
}
