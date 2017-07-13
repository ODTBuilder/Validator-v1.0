package com.git.gdsbuilder.validator.feature;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.type.validate.option.EdgeMatchMiss;
import com.git.gdsbuilder.validator.collection.opt.ValCollectionOption;
import com.git.gdsbuilder.validator.collection.opt.ValCollectionOption.ValCollectionOptionType;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

public class FeatureCloseCollectionValidatorImpl implements FeatureCloseCollectionValidator {

	public FeatureCloseCollectionValidatorImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<ErrorFeature> ValidateCloseCollectionTarget(SimpleFeature simpleFeature,
			List<SimpleFeature> relationSimpleFeatures, ValCollectionOption closeValidateOptions, LineString nearLine,
			double tolerence) {

		List<ErrorFeature> collectionErrors = new ArrayList<ErrorFeature>();
		GeometryFactory geometryFactory = new GeometryFactory();

		String featureIdx = simpleFeature.getID();
		Property featuerIDPro = simpleFeature.getProperty("feature_id");
		String featureID = (String) featuerIDPro.getValue();

		if (featureID.equals("70910")) {
			System.out.println("");
		}

		// 인접도엽 옵션객체 선언
		boolean matchMiss = false;
		boolean entityNone = false;
		if (closeValidateOptions != null) {
			Iterator<ValCollectionOptionType> typeItr = closeValidateOptions.keySet().iterator();

			while (typeItr.hasNext()) {
				String iteratorVal = typeItr.next().getTypeName();
				if (iteratorVal.equals(ValCollectionOptionType.ENTITYNONE.getTypeName())) {
					entityNone = (Boolean) closeValidateOptions.get(ValCollectionOptionType.ENTITYNONE);
				}
				if (iteratorVal.equals(ValCollectionOptionType.EDGEMATCHMISS.getTypeName())) {
					matchMiss = (Boolean) closeValidateOptions.get(ValCollectionOptionType.EDGEMATCHMISS);
				}
			}
		} else
			return null;

		Geometry targetGeometry = (Geometry) simpleFeature.getDefaultGeometry();
		if (matchMiss) {
			Coordinate[] tarCoors = targetGeometry.getCoordinates();
			for (int i = 0; i < tarCoors.length; i++) {
				Point tmpPt = geometryFactory.createPoint(tarCoors[i]);
				double tarDist = nearLine.distance(tmpPt);
				if (tarDist < tolerence || tarDist == tolerence) {
					Geometry tarBuffer = tmpPt.buffer(tolerence * 2);
					boolean isTrue = false;
					innerFor: for (SimpleFeature relationSimpleFeature : relationSimpleFeatures) {
						Geometry relationGeometry = (Geometry) relationSimpleFeature.getDefaultGeometry();
						if (relationGeometry.intersects(tarBuffer)) {
							Coordinate[] relationCoors = relationGeometry.getCoordinates();
							Point firPt = geometryFactory.createPoint(relationCoors[0]);
							Point lastPt = geometryFactory.createPoint(relationCoors[relationCoors.length - 1]);
							if (firPt.distance(tmpPt) <= tolerence || lastPt.distance(tmpPt) <= tolerence) {
								isTrue = true;
								break innerFor;
							}
						}
					}
					if (!isTrue) {
						ErrorFeature errFeature = new ErrorFeature(featureIdx, featureID,
								EdgeMatchMiss.Type.EDGEMATCHMISS.errType(), EdgeMatchMiss.Type.EDGEMATCHMISS.errName(),
								tmpPt);
						collectionErrors.add(errFeature);
					}
				}
			}
		}
		return collectionErrors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.git.gdsbuilder.validator.feature.FeatureCloseCollectionValidator#
	 * ValidateCloseCollectionRelation(org.opengis.feature.simple.SimpleFeature,
	 * java.util.List,
	 * com.git.gdsbuilder.validator.collection.opt.ValCollectionOption,
	 * com.vividsolutions.jts.geom.LineString, double)
	 */
	@Override
	public List<ErrorFeature> ValidateCloseCollectionRelation(SimpleFeature nearFeature,
			List<SimpleFeature> targetFeatureList, LineString nearLine, double tolorence) {
		// TODO Auto-generated method stub
		return null;
	}
}
