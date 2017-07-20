package com.git.gdsbuilder.validator.feature;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.lang.model.type.ErrorType;

import org.geotools.geometry.jts.GeometryCoordinateSequenceTransformer;
import org.geotools.xml.gml.GMLComplexTypes.GeometryAssociationType;
import org.json.simple.JSONArray;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.type.validate.option.EdgeMatchMiss;
import com.git.gdsbuilder.type.validate.option.EntityNone;
import com.git.gdsbuilder.type.validate.option.RefAttributeMiss;
import com.git.gdsbuilder.type.validate.option.RefZValueMiss;
import com.git.gdsbuilder.type.validate.option.SelfEntity;
import com.git.gdsbuilder.type.validate.option.UnderShoot;
import com.git.gdsbuilder.validator.collection.opt.ValCollectionOption;
import com.git.gdsbuilder.validator.collection.opt.ValCollectionOption.ValCollectionOptionType;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import sun.security.x509.DistributionPointName;

public class FeatureCloseCollectionValidatorImpl implements FeatureCloseCollectionValidator {

	public FeatureCloseCollectionValidatorImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<ErrorFeature> ValidateCloseCollectionTarget(SimpleFeature simpleFeature,
			List<SimpleFeature> relationSimpleFeatures, ValCollectionOption closeValidateOptions, LineString nearLine,
			double tolerence, String direction) {

		List<ErrorFeature> collectionErrors = new ArrayList<ErrorFeature>();
		GeometryFactory geometryFactory = new GeometryFactory();

		String featureIdx = simpleFeature.getID();
		Property featuerIDPro = simpleFeature.getProperty("feature_id");
		String featureID = (String) featuerIDPro.getValue();

		// 인접도엽 옵션객체 선언
		boolean matchMiss = false;
		boolean refAttributeMiss = false;
		boolean refZvalueMiss = false;
		boolean entityNon = false;
		boolean underShoot = false;
		if (closeValidateOptions != null) {
			Iterator<ValCollectionOptionType> typeItr = closeValidateOptions.keySet().iterator();
			while (typeItr.hasNext()) {
				String iteratorVal = typeItr.next().getTypeName();
				if (iteratorVal.equals(ValCollectionOptionType.EDGEMATCHMISS.getTypeName())) {
					matchMiss = true;
				}
				if (iteratorVal.equals(ValCollectionOptionType.REFATTRIBUTEMISS.getTypeName())) {
					refAttributeMiss = true;
				}
				if (iteratorVal.equals(ValCollectionOptionType.REFZVALUEMISS.getTypeName())) {
					refZvalueMiss = true;
				}
				if (iteratorVal.equals(ValCollectionOptionType.ENTITYNONE.getTypeName())) {
					entityNon = true;
				}
				if(iteratorVal.equals(ValCollectionOptionType.UNDERSHOOT.getTypeName())){
					underShoot = true;
				}
			}
		} else
			return null;

		Geometry targetGeometry = (Geometry) simpleFeature.getDefaultGeometry();
		if (entityNon) {
			boolean isTrue = false;
			Geometry targetGeomBuuer = targetGeometry.buffer(tolerence * 2);
			for (SimpleFeature relationSimpleFeature : relationSimpleFeatures) {
				Geometry relationGeometry = (Geometry) relationSimpleFeature.getDefaultGeometry();
				if (relationGeometry.intersects(targetGeomBuuer)) {
					isTrue = true;
					break;
				}
			}
			if (!isTrue) {
				Coordinate[] targetCoors = targetGeometry.getCoordinates();
				innerFor: for (int j = 0; j < targetCoors.length; j++) {
					Point tmpPt = geometryFactory.createPoint(targetCoors[j]);
					double reDist = nearLine.distance(tmpPt);
					if (reDist >= 0 && reDist <= tolerence * 2) {
						ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
								EntityNone.Type.ENTITYNONE.errType(), EntityNone.Type.ENTITYNONE.errName(), tmpPt);
						collectionErrors.add(errFeature);
						break innerFor;
					}
				}
				return collectionErrors;
			}
		}

		Coordinate[] tarCoors = targetGeometry.getCoordinates();
		int tarCoorsSize = tarCoors.length;
		if (targetGeometry.getGeometryType().equals("Polygon")) {
			tarCoorsSize = tarCoorsSize - 1;
		}
		List<SimpleFeature> reSimpleFeatures = new ArrayList<SimpleFeature>();
		for (int i = 0; i < tarCoorsSize; i++) {
			Point tmpPt = geometryFactory.createPoint(tarCoors[i]);
			double tarDist = nearLine.distance(tmpPt);
			if (tarDist < tolerence || tarDist == tolerence) {
				Geometry tarBuffer = tmpPt.buffer(tolerence * 2);
				boolean isError = true;
				innerFor: for (SimpleFeature relationSimpleFeature : relationSimpleFeatures) {
					Geometry relationGeometry = (Geometry) relationSimpleFeature.getDefaultGeometry();
					if (relationGeometry.intersects(tarBuffer)) {
						reSimpleFeatures.add(relationSimpleFeature);
						Coordinate[] relationCoors = relationGeometry.getCoordinates();
						int relCoorsSize = relationCoors.length;
						if (relationGeometry.getGeometryType().equals("Polygon")) {
							relCoorsSize = relCoorsSize - 1;
						}
						for (int j = 0; j < relCoorsSize; j++) {
							Point tmpRePt = geometryFactory.createPoint(relationCoors[j]);
							double reDist = nearLine.distance(tmpRePt);
							if (reDist >= 0 && reDist <= tolerence) {
								if (tmpRePt.distance(tmpPt) <= tolerence) {
									isError = false;
									reSimpleFeatures.add(relationSimpleFeature);
									break innerFor;
								}
							}
						}
					}
				}
				if (matchMiss && isError) {
					ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
							EdgeMatchMiss.Type.EDGEMATCHMISS.errType(), EdgeMatchMiss.Type.EDGEMATCHMISS.errName(),
							tmpPt);
					collectionErrors.add(errFeature);
				}
			}
		}


		if(underShoot){

			double underShootTolerance = (Double) closeValidateOptions.get(ValCollectionOptionType.UNDERSHOOT);

			Coordinate[] nearLineCoors = nearLine.getCoordinates();
			Coordinate nearLineCoor = nearLineCoors[0];

			// target객체 좌표 LIST 생성하기
			List<Coordinate> targetList = new ArrayList<Coordinate>();

			for (int i = 0; i < tarCoors.length; i++) {
				Coordinate targetCoor = tarCoors[i];
				targetList.add(targetCoor);
			}

			// target객체와 인접해있는 객체가 있는지 확인하기
			List<SimpleFeature> relationList = new ArrayList<SimpleFeature>();
			for (SimpleFeature relationSimpleFeature : relationSimpleFeatures) {
				Geometry relationGeometry = (Geometry) relationSimpleFeature.getDefaultGeometry();
				if(Math.abs(targetGeometry.distance(relationGeometry)) <= underShootTolerance){
					relationList.add(relationSimpleFeature);
				}
			}

			String upperType = simpleFeature.getAttribute("feature_type").toString().toUpperCase();

			if(upperType.equals("POLYGON")){
				if(relationList.size() > 0){
					if(direction.equals("TOP")){

						Coordinate coordinateFir = targetList.get(0);
						for (int i = 0; i < targetList.size()-1; i++) {
							Coordinate targetCoor = targetList.get(i);
							if(coordinateFir.y < targetCoor.y){
								coordinateFir = targetCoor;
							}
						}

						targetList.remove(coordinateFir);

						Coordinate coordinateSec = targetList.get(0);
						for (int i = 0; i < targetList.size()-1; i++) {
							Coordinate targetCoor = targetList.get(i);
							if(coordinateSec.y < targetCoor.y){
								coordinateSec = targetCoor;
							}
						}
						targetList.remove(coordinateSec);

						for (int i = 0; i < targetList.size(); i++) {
							Coordinate targetCoor = targetList.get(i);
							if(!targetCoor.equals(coordinateFir) && !targetCoor.equals(coordinateSec) ){
								Point targetPoint = geometryFactory.createPoint(targetCoor);
								if(coordinateFir.y - targetCoor.y < underShootTolerance){
									ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
											UnderShoot.Type.UNDERSHOOT.errType(), 
											UnderShoot.Type.UNDERSHOOT.errName(), targetPoint);
									collectionErrors.add(errFeature);
								}
							}
						}
					}
					if(direction.equals("BOTTOM")){

						Coordinate coordinateFir = targetList.get(0);
						for (int i = 0; i < targetList.size()-1; i++) {
							Coordinate targetCoor = targetList.get(i);
							if(coordinateFir.y > targetCoor.y){
								coordinateFir = targetCoor;
							}
						}

						targetList.remove(coordinateFir);

						Coordinate coordinateSec = targetList.get(0);
						for (int i = 0; i < targetList.size()-1; i++) {
							Coordinate targetCoor = targetList.get(i);
							if(coordinateSec.y > targetCoor.y){
								coordinateSec = targetCoor;
							}
						}
						targetList.remove(coordinateSec);

						for (int i = 0; i < targetList.size(); i++) {
							Coordinate targetCoor = targetList.get(i);
							if(!targetCoor.equals(coordinateFir) && !targetCoor.equals(coordinateSec) ){
								Point targetPoint = geometryFactory.createPoint(targetCoor);
								if(targetCoor.y - coordinateFir.y < underShootTolerance ){
									ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
											UnderShoot.Type.UNDERSHOOT.errType(), 
											UnderShoot.Type.UNDERSHOOT.errName(), targetPoint);
									collectionErrors.add(errFeature);
								}
							}
						}

					}
					if(direction.equals("RIGHT")){
						Coordinate coordinateFir = targetList.get(0);
						for (int i = 0; i < targetList.size()-1; i++) {
							Coordinate targetCoor = targetList.get(i);
							if(coordinateFir.x < targetCoor.x){
								coordinateFir = targetCoor;
							}
						}

						targetList.remove(coordinateFir);

						Coordinate coordinateSec = targetList.get(0);
						for (int i = 0; i < targetList.size()-1; i++) {
							Coordinate targetCoor = targetList.get(i);
							if(coordinateSec.x < targetCoor.x){
								coordinateSec = targetCoor;
							}
						}
						targetList.remove(coordinateSec);

						for (int i = 0; i < targetList.size(); i++) {
							Coordinate targetCoor = targetList.get(i);
							if(!targetCoor.equals(coordinateFir) && !targetCoor.equals(coordinateSec) ){
								Point targetPoint = geometryFactory.createPoint(targetCoor);
								if(coordinateFir.x - targetCoor.x < underShootTolerance ){
									ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
											UnderShoot.Type.UNDERSHOOT.errType(), 
											UnderShoot.Type.UNDERSHOOT.errName(), targetPoint);
									collectionErrors.add(errFeature);
								}
							}
						}

					}
					if(direction.equals("LEFT")){
						Coordinate coordinateFir = targetList.get(0);
						for (int i = 0; i < targetList.size()-1; i++) {
							Coordinate targetCoor = targetList.get(i);
							if(coordinateFir.x > targetCoor.x){
								coordinateFir = targetCoor;
							}
						}

						targetList.remove(coordinateFir);

						Coordinate coordinateSec = targetList.get(0);
						for (int i = 0; i < targetList.size()-1; i++) {
							Coordinate targetCoor = targetList.get(i);
							if(coordinateSec.x > targetCoor.x){
								coordinateSec = targetCoor;
							}
						}
						targetList.remove(coordinateSec);
						for (int i = 0; i < targetList.size(); i++) {
							Coordinate targetCoor = targetList.get(i);
							if(!targetCoor.equals(coordinateFir) && !targetCoor.equals(coordinateSec) ){
								Point targetPoint = geometryFactory.createPoint(targetCoor);
								if(targetCoor.x - coordinateFir.x < underShootTolerance ){
									ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
											UnderShoot.Type.UNDERSHOOT.errType(), 
											UnderShoot.Type.UNDERSHOOT.errName(), targetPoint);
									collectionErrors.add(errFeature);
								}
							}
						}
					}
				}else{
					for (int i = 0; i < targetList.size(); i++) {
						Coordinate targetCoor = targetList.get(i);
						Point targetPoint = geometryFactory.createPoint(targetCoor);

						if(targetPoint.distance(nearLine) < underShootTolerance){
							ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
									UnderShoot.Type.UNDERSHOOT.errType(), UnderShoot.Type.UNDERSHOOT.errName(), 

									targetPoint);
							collectionErrors.add(errFeature);
						}
					}
				}
			}else{
				Coordinate firstCoors = tarCoors[0];
				Coordinate lastCoors = tarCoors[tarCoors.length - 1];
				Point firstPoint = geometryFactory.createPoint(firstCoors);
				Point lastPoint = geometryFactory.createPoint(lastCoors);


				if(tarCoors.length > 2){
					if(firstPoint.distance(nearLine) <= underShootTolerance && lastPoint.distance(nearLine) <= underShootTolerance){
						if(direction.equals("TOP")){
							for (int i = 0; i < tarCoors.length; i++) {
								Coordinate targetCor = tarCoors[i];
								Point tarPoint = geometryFactory.createPoint(targetCor);
								if(!firstCoors.equals(targetCor) && !lastCoors.equals(targetCor)){
									double firDistance = tarPoint.distance(firstPoint);
									double lastDistance = tarPoint.distance(lastPoint);
									if(firDistance < lastDistance){
										if(firstCoors.y - targetCor.y < underShootTolerance){
											ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
													UnderShoot.Type.UNDERSHOOT.errType(), UnderShoot.Type.UNDERSHOOT.errName(), tarPoint);
											collectionErrors.add(errFeature);
										}
									}else{
										if(lastCoors.y - targetCor.y < underShootTolerance){
											ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
													UnderShoot.Type.UNDERSHOOT.errType(), UnderShoot.Type.UNDERSHOOT.errName(), tarPoint);
											collectionErrors.add(errFeature);
										}
									}
								}
							}
						}
						if(direction.equals("BOTTOM")){
							for (int i = 0; i < tarCoors.length; i++) {
								Coordinate targetCor = tarCoors[i];
								Point tarPoint = geometryFactory.createPoint(targetCor);
								if(!firstCoors.equals(targetCor) && !lastCoors.equals(targetCor)){
									double firDistance = tarPoint.distance(firstPoint);
									double lastDistance = tarPoint.distance(lastPoint);
									if(firDistance < lastDistance){
										if(targetCor.y - firstCoors.y < underShootTolerance){
											ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
													UnderShoot.Type.UNDERSHOOT.errType(), UnderShoot.Type.UNDERSHOOT.errName(), tarPoint);
											collectionErrors.add(errFeature);
										}
									}else{
										if(targetCor.y - lastCoors.y < underShootTolerance){
											ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
													UnderShoot.Type.UNDERSHOOT.errType(), UnderShoot.Type.UNDERSHOOT.errName(), tarPoint);
											collectionErrors.add(errFeature);
										}
									}
								}
							}
						}
						if(direction.equals("LEFT")){
							for (int i = 0; i < tarCoors.length; i++) {
								Coordinate targetCor = tarCoors[i];
								Point tarPoint = geometryFactory.createPoint(targetCor);
								if(!firstCoors.equals(targetCor) && !lastCoors.equals(targetCor)){
									double firDistance = tarPoint.distance(firstPoint);
									double lastDistance = tarPoint.distance(lastPoint);
									if(firDistance < lastDistance){
										if(targetCor.x - firstCoors.x < underShootTolerance){
											ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
													UnderShoot.Type.UNDERSHOOT.errType(), UnderShoot.Type.UNDERSHOOT.errName(), tarPoint);
											collectionErrors.add(errFeature);
										}
									}else{
										if(targetCor.x - lastCoors.x < underShootTolerance){
											ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
													UnderShoot.Type.UNDERSHOOT.errType(), UnderShoot.Type.UNDERSHOOT.errName(), tarPoint);
											collectionErrors.add(errFeature);
										}
									}
								}
							}
						}
						if(direction.equals("RIGHT")){
							for (int i = 0; i < tarCoors.length; i++) {
								Coordinate targetCor = tarCoors[i];
								Point tarPoint = geometryFactory.createPoint(targetCor);
								if(!firstCoors.equals(targetCor) && !lastCoors.equals(targetCor)){
									double firDistance = tarPoint.distance(firstPoint);
									double lastDistance = tarPoint.distance(lastPoint);
									if(firDistance < lastDistance){
										if(firstCoors.x - targetCor.x < underShootTolerance){
											ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
													UnderShoot.Type.UNDERSHOOT.errType(), UnderShoot.Type.UNDERSHOOT.errName(), tarPoint);
											collectionErrors.add(errFeature);
										}
									}else{
										if(lastCoors.x - targetCor.x < underShootTolerance){
											ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
													UnderShoot.Type.UNDERSHOOT.errType(), UnderShoot.Type.UNDERSHOOT.errName(), tarPoint);
											collectionErrors.add(errFeature);
										}
									}
								}
							}
						}

					}else{

						if(direction.equals("TOP")){
							if(firstCoors.y < lastCoors.y){
								for (int i = 0; i < tarCoors.length; i++) {
									Coordinate targetCor = tarCoors[i];
									Point tarPoint = geometryFactory.createPoint(targetCor);
									if(!targetCor.equals(firstCoors) && !targetCor.equals(lastCoors)){
										if(lastCoors.y - targetCor.y < underShootTolerance){
											ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
													UnderShoot.Type.UNDERSHOOT.errType(), 

													UnderShoot.Type.UNDERSHOOT.errName(), tarPoint);
											collectionErrors.add(errFeature);
										}
									}
								}
							}else{
								for (int i = 0; i < tarCoors.length; i++) {
									Coordinate targetCor = tarCoors[i];
									Point tarPoint = geometryFactory.createPoint(targetCor);
									if(!targetCor.equals(firstCoors) && !targetCor.equals(lastCoors)){
										if(firstCoors.y - targetCor.y < underShootTolerance){
											ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
													UnderShoot.Type.UNDERSHOOT.errType(), 

													UnderShoot.Type.UNDERSHOOT.errName(), tarPoint);
											collectionErrors.add(errFeature);
										}
									}
								}
							}
						}
						if(direction.equals("BOTTOM")){
							if(firstCoors.y < lastCoors.y){
								for (int i = 0; i < tarCoors.length; i++) {
									Coordinate targetCor = tarCoors[i];
									Point tarPoint = geometryFactory.createPoint(targetCor);
									if(!targetCor.equals(firstCoors) && !targetCor.equals(lastCoors)){
										if(targetCor.y - firstCoors.y < underShootTolerance){
											ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
													UnderShoot.Type.UNDERSHOOT.errType(), 

													UnderShoot.Type.UNDERSHOOT.errName(), tarPoint);
											collectionErrors.add(errFeature);
										}
									}
								}
							}else{
								for (int i = 0; i < tarCoors.length; i++) {
									Coordinate targetCor = tarCoors[i];
									Point tarPoint = geometryFactory.createPoint(targetCor);
									if(!targetCor.equals(firstCoors) && !targetCor.equals(lastCoors)){
										if(targetCor.y - lastCoors.y < underShootTolerance){
											ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
													UnderShoot.Type.UNDERSHOOT.errType(), 

													UnderShoot.Type.UNDERSHOOT.errName(), tarPoint);
											collectionErrors.add(errFeature);
										}
									}
								}
							}

						}
						if(direction.equals("LEFT")){
							if(firstCoors.x < lastCoors.x){
								for (int i = 0; i < tarCoors.length; i++) {
									Coordinate targetCor = tarCoors[i];
									Point tarPoint = geometryFactory.createPoint(targetCor);
									if(!targetCor.equals(firstCoors) && !targetCor.equals(lastCoors)){
										if(targetCor.x - firstCoors.x < underShootTolerance){
											ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
													UnderShoot.Type.UNDERSHOOT.errType(), 

													UnderShoot.Type.UNDERSHOOT.errName(), tarPoint);
											collectionErrors.add(errFeature);
										}
									}
								}
							}else{
								for (int i = 0; i < tarCoors.length; i++) {
									Coordinate targetCor = tarCoors[i];
									Point tarPoint = geometryFactory.createPoint(targetCor);
									if(!targetCor.equals(firstCoors) && !targetCor.equals(lastCoors)){
										if(targetCor.x - lastCoors.x < underShootTolerance){
											ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
													UnderShoot.Type.UNDERSHOOT.errType(), 

													UnderShoot.Type.UNDERSHOOT.errName(), tarPoint);
											collectionErrors.add(errFeature);
										}
									}
								}
							}
						}
						if(direction.equals("RIGHT")){
							if(firstCoors.x < lastCoors.x){
								for (int i = 0; i < tarCoors.length; i++) {
									Coordinate targetCor = tarCoors[i];
									Point tarPoint = geometryFactory.createPoint(targetCor);
									if(!targetCor.equals(firstCoors) && !targetCor.equals(lastCoors)){
										if(lastCoors.x - targetCor.x < underShootTolerance){
											ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
													UnderShoot.Type.UNDERSHOOT.errType(), 

													UnderShoot.Type.UNDERSHOOT.errName(), tarPoint);
											collectionErrors.add(errFeature);
										}
									}
								}
							}else{
								for (int i = 0; i < tarCoors.length; i++) {
									Coordinate targetCor = tarCoors[i];
									Point tarPoint = geometryFactory.createPoint(targetCor);
									if(!targetCor.equals(firstCoors) && !targetCor.equals(lastCoors)){
										if(firstCoors.x - targetCor.x < underShootTolerance){
											ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
													UnderShoot.Type.UNDERSHOOT.errType(), 

													UnderShoot.Type.UNDERSHOOT.errName(), tarPoint);
											collectionErrors.add(errFeature);
										}
									}
								}
							}
						}


					}

				}else{
					if(direction.equals("TOP")){
						if(firstCoors.y < lastCoors.y){
							if(lastCoors.y - firstCoors.y < underShootTolerance){
								ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
										UnderShoot.Type.UNDERSHOOT.errType(), 

										UnderShoot.Type.UNDERSHOOT.errName(), firstPoint);
								collectionErrors.add(errFeature);
							}
						}else{
							if(firstCoors.y - lastCoors.y  < underShootTolerance){
								ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
										UnderShoot.Type.UNDERSHOOT.errType(), 

										UnderShoot.Type.UNDERSHOOT.errName(), lastPoint);
								collectionErrors.add(errFeature);
							}
						}
					}
					if(direction.equals("BOTTOM")){
						if(firstCoors.y < lastCoors.y){
							if(lastCoors.y - firstCoors.y < underShootTolerance){
								ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
										UnderShoot.Type.UNDERSHOOT.errType(), 

										UnderShoot.Type.UNDERSHOOT.errName(), lastPoint);
								collectionErrors.add(errFeature);
							}
						}else{
							if(firstCoors.y - lastCoors.y  < underShootTolerance){
								ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
										UnderShoot.Type.UNDERSHOOT.errType(), 

										UnderShoot.Type.UNDERSHOOT.errName(), firstPoint);
								collectionErrors.add(errFeature);
							}
						}
					}
					if(direction.equals("LEFT")){
						if(firstCoors.x < lastCoors.x){
							if(lastCoors.x - firstCoors.x < underShootTolerance){
								ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
										UnderShoot.Type.UNDERSHOOT.errType(), 

										UnderShoot.Type.UNDERSHOOT.errName(), lastPoint);
								collectionErrors.add(errFeature);
							}
						}else{
							if(firstCoors.x - lastCoors.x < underShootTolerance){
								ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
										UnderShoot.Type.UNDERSHOOT.errType(), 

										UnderShoot.Type.UNDERSHOOT.errName(), firstPoint);
								collectionErrors.add(errFeature);
							}
						}
					}
					if(direction.equals("RIGHT")){
						if(firstCoors.x < lastCoors.x){
							if(lastCoors.x - firstCoors.x < underShootTolerance){
								ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
										UnderShoot.Type.UNDERSHOOT.errType(), 

										UnderShoot.Type.UNDERSHOOT.errName(), firstPoint);
								collectionErrors.add(errFeature);
							}
						}else{
							if(firstCoors.x - lastCoors.x < underShootTolerance){
								ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
										UnderShoot.Type.UNDERSHOOT.errType(), 

										UnderShoot.Type.UNDERSHOOT.errName(), lastPoint);
								collectionErrors.add(errFeature);
							}
						}
					}
				}
			}
		}


		if (reSimpleFeatures.size() > 0) {
			if (refAttributeMiss) {
				JSONArray attributesColumns = (JSONArray) closeValidateOptions
						.get(ValCollectionOptionType.REFATTRIBUTEMISS);
				boolean isMiss = false;
				outerFor: for (int i = 0; i < reSimpleFeatures.size(); i++) {
					SimpleFeature reSimpleFeature = reSimpleFeatures.get(i);
					for (int j = 0; j < attributesColumns.size(); j++) {
						String attriKey = (String) attributesColumns.get(j);
						Property relationProperty = reSimpleFeature.getProperty(attriKey);
						Property targetProperty = simpleFeature.getProperty(attriKey);
						if (targetProperty == null || relationProperty == null) {
							isMiss = true;
							break outerFor;
						} else {
							Object rePropertyValue = relationProperty.getValue();
							Object tarPropertyValue = targetProperty.getValue();
							if (rePropertyValue == null || tarPropertyValue == null) {
								isMiss = true;
								break outerFor;
							} else {
								String reValueStr = rePropertyValue.toString();
								String tarValueStr = tarPropertyValue.toString();
								if (!reValueStr.equals(tarValueStr)) {
									isMiss = true;
									break outerFor;
								}
							}
						}
					}
					if (!isMiss) {
						break outerFor;
					}
				}
				if (isMiss) {
					Point minDistPt = null;
					for (int i = 0; i < tarCoors.length; i++) {
						Point tmpPt = geometryFactory.createPoint(tarCoors[i]);
						double tarDist = nearLine.distance(tmpPt);
						if (tarDist >= 0 && tarDist <= tolerence) {
							minDistPt = tmpPt;
							break;
						}
					}
					ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
							RefAttributeMiss.Type.RefAttributeMiss.errType(),
							RefAttributeMiss.Type.RefAttributeMiss.errName(), minDistPt);
					collectionErrors.add(errFeature);
				}
			}
			if (refZvalueMiss) {
				JSONArray attributesColumns = (JSONArray) closeValidateOptions
						.get(ValCollectionOptionType.REFZVALUEMISS);
				boolean isMiss = false;
				outerFor: for (int i = 0; i < reSimpleFeatures.size(); i++) {
					SimpleFeature reSimpleFeature = reSimpleFeatures.get(i);
					for (int j = 0; j < attributesColumns.size(); j++) {
						String attriKey = (String) attributesColumns.get(j);
						Property relationProperty = reSimpleFeature.getProperty(attriKey);
						Property targetProperty = simpleFeature.getProperty(attriKey);
						if (targetProperty == null || relationProperty == null) {
							isMiss = true;
							break outerFor;
						} else {
							Object rePropertyValue = relationProperty.getValue();
							Object tarPropertyValue = targetProperty.getValue();
							if (rePropertyValue == null || tarPropertyValue == null) {
								isMiss = true;
								break outerFor;
							} else {
								String reValueStr = rePropertyValue.toString();
								String tarValueStr = tarPropertyValue.toString();
								if (!reValueStr.equals(tarValueStr)) {
									isMiss = true;
									break outerFor;
								}
							}
						}
					}
					if (!isMiss) {
						break outerFor;
					}
				}
				if (isMiss) {
					Point minDistPt = null;
					for (int i = 0; i < tarCoors.length; i++) {
						Point tmpPt = geometryFactory.createPoint(tarCoors[i]);
						double tarDist = nearLine.distance(tmpPt);
						if (tarDist >= 0 && tarDist <= tolerence) {
							minDistPt = tmpPt;
							break;
						}
					}
					ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
							RefZValueMiss.Type.REFZVALUEMISS.errType(), RefZValueMiss.Type.REFZVALUEMISS.errName(),
							minDistPt);
					collectionErrors.add(errFeature);
				}
			}
		}
		return collectionErrors;
	}

	@Override
	public List<ErrorFeature> ValidateCloseCollectionRelation(SimpleFeature nearFeature,
			List<SimpleFeature> targetFeatureList, LineString nearLine, double tolorence) {

		List<ErrorFeature> collectionErrs = new ArrayList<ErrorFeature>();
		GeometryFactory geometryFactory = new GeometryFactory();
		String featureIdx = nearFeature.getID();
		Property featureIDPro = nearFeature.getProperty("feature_id");
		String featureID = (String) featureIDPro.getValue();
		Geometry nearGeometry = (Geometry) nearFeature.getDefaultGeometry();
		Coordinate[] nearCoors = nearGeometry.getCoordinates();
		Point start = geometryFactory.createPoint(nearCoors[0]);
		Point end = geometryFactory.createPoint(nearCoors[nearCoors.length - 1]);
		for (int i = 0; i < nearCoors.length; i++) {
			Point nearPoint = geometryFactory.createPoint(nearCoors[i]);
			double nearDist = nearLine.distance(nearPoint);
			if (nearDist < tolorence || nearDist == tolorence) {
				if (start.equals(nearPoint) || end.equals(nearPoint)) {
					Geometry nearBuffer = nearPoint.buffer(tolorence * 2);
					boolean isTrue = false;
					innerFor: for (SimpleFeature targetSimpleFeature : targetFeatureList) {
						Geometry targetGeometry = (Geometry) targetSimpleFeature.getDefaultGeometry();
						if (targetGeometry.intersects(nearBuffer) || targetGeometry.intersects(nearGeometry)) {
							Coordinate[] targetCoors = targetGeometry.getCoordinates();
							Point firPt = geometryFactory.createPoint(targetCoors[0]);
							Point lastPt = geometryFactory.createPoint(targetCoors[targetCoors.length - 1]);
							if (firPt.distance(nearPoint) <= tolorence || lastPt.distance(nearPoint) <= tolorence) {
								isTrue = true;
								break innerFor;
							}
						}
					}
					if (!isTrue) {
						ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
								EntityNone.Type.ENTITYNONE.errType(), EntityNone.Type.ENTITYNONE.errName(), nearPoint);
						collectionErrs.add(errFeature);
					}
				}
			}
		}
		return collectionErrs;
	}
}
