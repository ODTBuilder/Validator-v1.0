package com.git.gdsbuilder.generalization.factory;

import org.geotools.data.simple.SimpleFeatureCollection;

import com.git.gdsbuilder.generalization.Aggregation;
import com.git.gdsbuilder.generalization.Elimination;
import com.git.gdsbuilder.generalization.Generalization;
import com.git.gdsbuilder.generalization.Simplification;
import com.git.gdsbuilder.generalization.impl.GeneralizationImpl.GeneralizationOrder;
import com.git.gdsbuilder.generalization.opt.AggregationOption;
import com.git.gdsbuilder.generalization.opt.EliminationOption;
import com.git.gdsbuilder.generalization.opt.SimplificationOption;

public interface GeneralizationFactory {
	
	Generalization createGeneralization(SimpleFeatureCollection collection, SimplificationOption simplificationOption, EliminationOption eliminationOption, GeneralizationOrder order, boolean topologyFlag);
	
	Simplification createSimplification(SimpleFeatureCollection collection, SimplificationOption option);
	 
	Elimination createElimination(SimpleFeatureCollection collection, EliminationOption option);

	Aggregation createAggregation(SimpleFeatureCollection collection, AggregationOption option);
}
