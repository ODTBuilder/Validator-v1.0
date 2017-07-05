package com.git.gdsbuilder.validator.feature;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.type.validate.option.EdgeMatchMiss;
import com.git.gdsbuilder.type.validate.option.EntityNone;
import com.git.gdsbuilder.type.validate.option.RefAttributeMiss;
import com.git.gdsbuilder.type.validate.option.RefZValueMiss;
import com.git.gdsbuilder.type.validate.option.ValidatorOption;
import com.git.gdsbuilder.validator.collection.opt.ValCollectionOption;
import com.git.gdsbuilder.validator.collection.opt.ValCollectionOption.ValCollectionOptionType;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;

public class FeatureCloseCollectionValidatorImpl implements FeatureCloseCollectionValidator {
	
	public FeatureCloseCollectionValidatorImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<ErrorFeature> ValidateCloseCollection(SimpleFeature simpleFeature, List<SimpleFeature> relationSimpleFeatures, ValCollectionOption closeValidateOptions,LineString nearLine,double tolerence){
		
		List<ErrorFeature> collectionErrors = new ArrayList<ErrorFeature>();
		
		GeometryFactory geometryFactory = new GeometryFactory();
		
		//인접도엽 옵션객체 선언
		boolean entityNone = false;
		boolean matchMiss = false;
		String refZValueMiss = "";
		List<String> refAttributeMiss = null;
		
		if(closeValidateOptions!=null){
			Iterator<ValCollectionOptionType> typeItr = closeValidateOptions.keySet().iterator();
		
			while(typeItr.hasNext()){
				if(typeItr.next().getTypeName().equals(ValCollectionOptionType.ENTITYNONE.getTypeName())){
					entityNone = (Boolean) closeValidateOptions.get(ValCollectionOptionType.ENTITYNONE);
				}
				if(typeItr.next().getTypeName().equals(ValCollectionOptionType.ENTITYNONE.getTypeName())){
					matchMiss = (Boolean) closeValidateOptions.get(ValCollectionOptionType.EDGEMATCHMISS);
				}
				if(typeItr.next().getTypeName().equals(ValCollectionOptionType.REFZVALUEMISS.getTypeName())){
					refZValueMiss = (String) closeValidateOptions.get(ValCollectionOptionType.REFZVALUEMISS);
				}
				if(typeItr.next().getTypeName().equals(ValCollectionOptionType.REFATTRIBUTEMISS.getTypeName())){
					refAttributeMiss = (List<String>) closeValidateOptions.get(ValCollectionOptionType.REFATTRIBUTEMISS);
				}
			}
		}
		else
			return null;
	
		Geometry targetGeometry = (Geometry) simpleFeature.getDefaultGeometry();
		
		boolean existFlag = false; //인접도엽 객체존재여부
		
		for(SimpleFeature relationSimpleFeature : relationSimpleFeatures){
			Geometry relationGeometry = (Geometry) relationSimpleFeature.getDefaultGeometry();
			if(Math.abs(targetGeometry.distance(relationGeometry)) < tolerence * 2){
				existFlag = true;
				
				if(matchMiss){
					Coordinate[] targetCoordinates = targetGeometry.getCoordinates();// 대상 객체 포인트들
					
					
					//대상 객체 포인트에서 도엽 근처에 있는 포인트 추출
					List<Geometry> etargetList = new ArrayList<Geometry>();
					for(Coordinate tCoordinate : targetCoordinates){
						Geometry tGeometry = geometryFactory.createPoint(tCoordinate);
						if(Math.abs(tGeometry.distance(nearLine))<tolerence){
							etargetList.add(tGeometry);
						}
					}
					
					Coordinate[] etargetCoordinates = new Coordinate[etargetList.size()];
					for(int i=0; i<etargetList.size();i++){
						etargetCoordinates[i] = etargetList.get(i).getCoordinate();
					}
					
					
					
					Coordinate[] relCoordinates = relationGeometry.getCoordinates(); // 관계 피처 포인트 추출
					
					//포인트끼리 비교
					for(Coordinate tCoordinate : etargetCoordinates){
						boolean matchFeatureFlag = false;
						Geometry tPoint = geometryFactory.createPoint(tCoordinate);
						for(Coordinate rCoordinate : relCoordinates){
							Geometry rPoint = geometryFactory.createPoint(rCoordinate);
							if(Math.abs(tPoint.distance(rPoint))<tolerence * 2){//대상 포인트 관계포인트 비교
								if(Math.abs(rPoint.distance(nearLine))<tolerence){ // 관계포인트랑 도곽이랑 비교
									matchFeatureFlag = true;
									break;
								}
							}
						}
						if(!matchFeatureFlag){
							ErrorFeature MMErrFeature = new ErrorFeature(simpleFeature.getID(), EdgeMatchMiss.Type.EDGEMATCHMISS.errType(),EdgeMatchMiss.Type.EDGEMATCHMISS.errName(),tPoint);
							collectionErrors.add(MMErrFeature);
						}
					}
				}

				if(!refZValueMiss.equals("")){
					Object tValue = simpleFeature.getAttribute(refZValueMiss); //대상 피쳐
					Object rValue = simpleFeature.getAttribute(refZValueMiss); //인접 피쳐
					
					String tValueString = "";
					String rValueString  = "";
					
					if(tValue!=null&&rValue!=null){
						tValueString = tValue.toString().trim();
						rValueString = rValue.toString().trim();
					}
					
					
					if(!tValueString.equals(rValueString)){
						ErrorFeature rZVErrFeature = new ErrorFeature(simpleFeature.getID(), RefZValueMiss.Type.REFZVALUEMISS.errType(),RefZValueMiss.Type.REFZVALUEMISS.errName(),targetGeometry.getInteriorPoint());
						collectionErrors.add(rZVErrFeature);
					}
				}
				
				if(refAttributeMiss!=null){
					List<String> colunms = refAttributeMiss;
					
					for(String colunm : colunms){
						Object tValue = simpleFeature.getAttribute(colunm);
						Object rValue = simpleFeature.getAttribute(colunm);
						
						String tValueString = "";
						String rValueString  = "";
						
						if(tValue!=null&&rValue!=null){
							tValueString = tValue.toString().trim();
							rValueString = rValue.toString().trim();
						}
						
						if(!tValueString.equals(rValueString)){
							ErrorFeature rAttErrFeature =  new ErrorFeature(simpleFeature.getID(),RefAttributeMiss.Type.RefAttributeMiss.errType(),RefAttributeMiss.Type.RefAttributeMiss.errName(),targetGeometry.getInteriorPoint());
							collectionErrors.add(rAttErrFeature);
							break;
						}
					}
				}
			}
		}
		if(!existFlag){
			if(entityNone){
				ErrorFeature eNoneErr = new ErrorFeature(simpleFeature.getID(), EntityNone.Type.ENTITYNONE.errType(), EntityNone.Type.ENTITYNONE.errName(), nearLine.getCentroid());
				collectionErrors.add(eNoneErr);
			}
		}
		return collectionErrors;
	}
}
