package com.git.gdsbuilder.validator.feature;

import java.util.List;

import org.opengis.feature.simple.SimpleFeature;

import com.git.gdsbuilder.type.validate.error.ErrorFeature;
import com.git.gdsbuilder.validator.collection.opt.ValCollectionOption;
import com.vividsolutions.jts.geom.LineString;

public interface FeatureCloseCollectionValidator{

	public List<ErrorFeature> ValidateCloseCollection(SimpleFeature simpleFeature, List<SimpleFeature> relationSimpleFeatures, ValCollectionOption closeValidateOptions,LineString nearLine,double tolerence);
}
