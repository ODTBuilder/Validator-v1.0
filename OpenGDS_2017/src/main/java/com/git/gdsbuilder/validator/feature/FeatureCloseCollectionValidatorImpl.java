package com.git.gdsbuilder.validator.feature;

import java.util.ArrayList;
import java.util.List;

import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.type.validate.option.EdgeMatchMiss;
import com.git.gdsbuilder.type.validate.option.EntityNone;
import com.git.gdsbuilder.type.validate.option.RefAttributeMiss;
import com.git.gdsbuilder.type.validate.option.RefZValueMiss;
import com.git.gdsbuilder.type.validate.option.ValidatorOption;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;

public class FeatureCloseCollectionValidatorImpl implements FeatureCloseCollectionValidator {
	
	public FeatureCloseCollectionValidatorImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<ErrorFeature> ValidateCloseCollection(SimpleFeature simpleFeature, List<SimpleFeature> relationSimpleFeatures, List<ValidatorOption> closeValidateOptions,LineString nearLine,double tolerence){
		
		List<ErrorFeature> collectionErrors = new ArrayList<ErrorFeature>();
		
		GeometryFactory geometryFactory = new GeometryFactory();
		
		//인접도엽 옵션객체 선언
		EntityNone entityNone = null;
		EdgeMatchMiss matchMiss = null;
		RefZValueMiss refZValueMiss = null;
		RefAttributeMiss refAttributeMiss = null;
		
		for(ValidatorOption validatorOption : closeValidateOptions){
			if (validatorOption instanceof EntityNone) {
				entityNone = (EntityNone) validatorOption;
			}
			if (validatorOption instanceof EdgeMatchMiss) {
				matchMiss = (EdgeMatchMiss) validatorOption;
			}
			if (validatorOption instanceof RefZValueMiss) {
				refZValueMiss = (RefZValueMiss) validatorOption;
			}
			if (validatorOption instanceof RefAttributeMiss) {
				refAttributeMiss = (RefAttributeMiss) validatorOption;
			}
		}
		
		Geometry targetGeometry = (Geometry) simpleFeature.getDefaultGeometry();
		
		boolean existFlag = false; //인접도엽 존재여부
		
		for(SimpleFeature relationSimpleFeature : relationSimpleFeatures){
			
			Geometry relationGeometry = (Geometry) relationSimpleFeature.getDefaultGeometry();
			if(targetGeometry.distance(relationGeometry) < tolerence * 2){
				existFlag = true;
				
				if(matchMiss!=null){
					Geometry bufferGeom = targetGeometry.buffer(tolerence);
					Geometry intersectionGeom = bufferGeom.intersection(nearLine);
					
					Coordinate[] targetCoordinates = intersectionGeom.getCoordinates();
					Coordinate[] relCoordinates = relationGeometry.getCoordinates();
					
					for(Coordinate tCoordinate : targetCoordinates){
						
						Geometry tPoint = geometryFactory.createPoint(tCoordinate);
						boolean matchMissFlag = false;
						for(Coordinate rCoordinate : relCoordinates){
							Geometry rPoint = geometryFactory.createPoint(rCoordinate);
							if(Math.abs(tPoint.distance(rPoint))<tolerence){
								matchMissFlag = true;
								break;
							}
						}
						if(!matchMissFlag){
							ErrorFeature MMErrFeature = new ErrorFeature(simpleFeature.getID(), EdgeMatchMiss.Type.EDGEMATCHMISS.errType(),EdgeMatchMiss.Type.EDGEMATCHMISS.errName(),tPoint);
							collectionErrors.add(MMErrFeature);
						}
					}
				}

				if(refZValueMiss!=null){
					Object tValue = simpleFeature.getAttribute(refZValueMiss.getAttributeKey()); //대상 피쳐
					Object rValue = simpleFeature.getAttribute(refZValueMiss.getAttributeKey()); //인접 피쳐
					
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
					List<String> colunms = refAttributeMiss.getColunms();
					
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
			if(entityNone!=null){
				ErrorFeature eNoneErr = new ErrorFeature(simpleFeature.getID(), EntityNone.Type.ENTITYNONE.errType(), EntityNone.Type.ENTITYNONE.errName(), nearLine.getCentroid());
				collectionErrors.add(eNoneErr);
			}
		}
		return collectionErrors;
	}
}
