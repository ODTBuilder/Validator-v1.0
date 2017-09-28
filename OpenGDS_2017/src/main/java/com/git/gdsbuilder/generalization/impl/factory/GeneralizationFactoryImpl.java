package com.git.gdsbuilder.generalization.impl.factory;

import org.geotools.data.simple.SimpleFeatureCollection;

import com.git.gdsbuilder.generalization.Aggregation;
import com.git.gdsbuilder.generalization.Elimination;
import com.git.gdsbuilder.generalization.Generalization;
import com.git.gdsbuilder.generalization.Simplification;
import com.git.gdsbuilder.generalization.factory.GeneralizationFactory;
import com.git.gdsbuilder.generalization.impl.AggregationImpl;
import com.git.gdsbuilder.generalization.impl.EliminationImpl;
import com.git.gdsbuilder.generalization.impl.GeneralizationImpl;
import com.git.gdsbuilder.generalization.impl.GeneralizationImpl.GeneralizationOrder;
import com.git.gdsbuilder.generalization.impl.SimplificationImpl;
import com.git.gdsbuilder.generalization.opt.AggregationOption;
import com.git.gdsbuilder.generalization.opt.EliminationOption;
import com.git.gdsbuilder.generalization.opt.SimplificationOption;

public class GeneralizationFactoryImpl implements GeneralizationFactory{
	
	public Generalization createGeneralization(SimpleFeatureCollection collection, SimplificationOption simplificationOption, EliminationOption eliminationOption, GeneralizationOrder order, boolean topologyFlag){
		return new GeneralizationImpl(collection, simplificationOption, eliminationOption, order, topologyFlag);
	}
	
	
	public Simplification createSimplification(SimpleFeatureCollection featureCollection, SimplificationOption option){
		return new SimplificationImpl(featureCollection, option);
	}
	
	public Elimination createElimination(SimpleFeatureCollection featureCollection, EliminationOption option){
		return new EliminationImpl(featureCollection, option);
	}
	
	public Aggregation createAggregation(SimpleFeatureCollection featureCollection, AggregationOption option){
		return new AggregationImpl(featureCollection, option);
	}
}
