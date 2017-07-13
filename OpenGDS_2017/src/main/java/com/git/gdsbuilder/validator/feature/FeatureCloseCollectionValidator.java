package com.git.gdsbuilder.validator.feature;

import java.util.List;

import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.validator.collection.opt.ValCollectionOption;
import com.vividsolutions.jts.geom.LineString;

public interface FeatureCloseCollectionValidator {

	public List<ErrorFeature> ValidateCloseCollectionMatchMiss(SimpleFeature simpleFeature,
			List<SimpleFeature> relationSimpleFeatures, ValCollectionOption closeValidateOptions, LineString nearLine,
			double tolerence);

	/**
	 *
	 * @author JY.Kim @Date 2017. 7. 13. 오전 10:01:40 @param targetFeature @param
	 * topFeatureList @param closeValidateOptions @param topLineString @param
	 * tolorence @return List<ErrorFeature> @throws
	 */
	public List<ErrorFeature> ValidateCloseCollectionEntityNone(SimpleFeature targetFeature,
			List<SimpleFeature> topFeatureList, ValCollectionOption closeValidateOptions, LineString topLineString,
			double tolorence);
}
