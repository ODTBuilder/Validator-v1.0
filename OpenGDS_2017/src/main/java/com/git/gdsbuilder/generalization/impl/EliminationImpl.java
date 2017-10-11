package com.git.gdsbuilder.generalization.impl;

import java.io.IOException;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.generalization.Elimination;
import com.git.gdsbuilder.generalization.data.TopoGeneralizationData;
import com.git.gdsbuilder.generalization.data.res.DTGeneralEAfLayer;
import com.git.gdsbuilder.generalization.opt.EliminationOption;
import com.git.gdsbuilder.generalization.rep.DTGeneralReport;
import com.git.gdsbuilder.generalization.rep.type.DTGeneralReportNums;
import com.vividsolutions.jts.geom.Geometry;

/**
 * 일반화 Type중 Elimination을 수행한다.
 * @author SG.Lee
 * @Date 2016.10
 * */
public class EliminationImpl implements Elimination {
	
	
	//Elimination 대상 collection
	private SimpleFeatureCollection collection;
	//일반화 옵션 
	private EliminationOption option;
	
	
	public EliminationImpl(SimpleFeatureCollection collection, EliminationOption option){
		this.collection = collection;
		this.option = option;
	}
	
	
	public SimpleFeatureCollection getCollection() {
		return collection;
	}
	public void setCollection(SimpleFeatureCollection collection) {
		this.collection = collection;
	}
	public EliminationOption getOption() {
		return option;
	}
	public void setOption(EliminationOption option) {
		this.option = option;
	}

	public DTGeneralEAfLayer getElimination(){
		//일반화 결과 레이어 생성 및 초기화
		//DTGeneralEAfLayer - 토폴로지 빌드를 하지 않은 결과레이어
		DTGeneralEAfLayer afLayer = new DTGeneralEAfLayer();
		
		//Elimination 결과 -> DTGeneralEAfLayer 선언 및 초기화
		DTGeneralReportNums entityNums = new DTGeneralReportNums(0,0);
		DTGeneralReportNums pointNums = new DTGeneralReportNums(0,0);
		
		//결과레포트 생성 및 초기화
		DTGeneralReport resultReport = new DTGeneralReport(entityNums, pointNums);
		
		//SimpleFeatureCollection 생성 및 초기화 -> 일반화 결과 SimpleFeatureCollection
		DefaultFeatureCollection resultCollection = new DefaultFeatureCollection();
		
		
		if(collection!=null&&option!=null){
			SimpleFeatureIterator featureIterator = collection.features();
			
			//Elimination Report Nums 결과  
			int entityPreNum = collection.size();
			int entityAfNum = 0;
			int pointPreNum = 0;
			int pointAfNum = 0;
			
			
			while(featureIterator.hasNext()){
				SimpleFeature feature = featureIterator.next();
				Geometry geom = (Geometry) feature.getDefaultGeometry();
				pointPreNum = pointPreNum + geom.getNumPoints();
				//lineString일 경우에 
				if (geom.getGeometryType().equals("LineString") || geom.getGeometryType().equals("MultiLineString")) {
					if (option != null) {
						//길이비교
						if (geom.getLength() > option.getOption()) {
							resultCollection.add(feature);
							pointAfNum = pointAfNum + geom.getNumPoints(); //추가된 Feature 포인트수 추가
						}
					} else
						resultCollection = null;

				//polygon일 경우에
				} else if (geom.getGeometryType().equals("Polygon") || geom.getGeometryType().equals("MultiPolygon")) {
					if (option != null) {
						//넓이 비교
						if (geom.getArea() > option.getOption()) {
							resultCollection.add(feature);
							pointAfNum = pointAfNum + geom.getNumPoints(); //추가된 Feature 포인트수 추가
						}
					} else
						resultCollection = null;
				} else {
					resultCollection = null;
				}
				System.out.println("객체삭제중이다");
			}
			//계산된 Feature수, 포인트수 input
			try {
				entityAfNum = resultCollection.getCount();
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
