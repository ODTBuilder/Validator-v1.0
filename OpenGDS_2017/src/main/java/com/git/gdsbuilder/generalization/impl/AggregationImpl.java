package com.git.gdsbuilder.generalization.impl;

import org.geotools.data.simple.SimpleFeatureCollection;

import com.git.gdsbuilder.generalization.Aggregation;
import com.git.gdsbuilder.generalization.data.TopoGeneralizationData;
import com.git.gdsbuilder.generalization.data.res.DTGeneralAfLayer;
import com.git.gdsbuilder.generalization.impl.GeneralizationImpl.GeneralizationOrder;
import com.git.gdsbuilder.generalization.opt.AggregationOption;

public class AggregationImpl extends TopoGeneralizationData implements Aggregation {
	
	private AggregationOption option;

	public AggregationImpl(AggregationOption option) {
		// TODO Auto-generated constructor stub
		this.option = option;
	}
	
	public AggregationImpl(SimpleFeatureCollection collection , AggregationOption option){
		super(collection);
		this.option=option;
	}
	
	public AggregationOption getOption() {
		return option;
	}

	public void setOption(AggregationOption option) {
		this.option = option;
	}
	
	public DTGeneralAfLayer getAggregation(){
		DTGeneralAfLayer afLayer = new DTGeneralAfLayer();
		
		
		
		
		return afLayer;
	}
}
