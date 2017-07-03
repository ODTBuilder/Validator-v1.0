package com.git.gdsbuilder.validator.feature;

import java.util.List;

import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.type.validate.option.ValidatorOption;
import com.vividsolutions.jts.geom.LineString;

public interface FeatureCloseCollectionValidator{

	public List<ErrorFeature> ValidateCloseCollection(SimpleFeature simpleFeature, List<SimpleFeature> relationSimpleFeatures, List<ValidatorOption> closeValidateOptions,LineString nearLine,double tolerence);
}
