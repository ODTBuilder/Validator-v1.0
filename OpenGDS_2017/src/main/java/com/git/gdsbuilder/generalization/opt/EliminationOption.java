package com.git.gdsbuilder.generalization.opt;

public class EliminationOption {

	private Double option;

	
	public EliminationOption(Double option){
		this.option = option;
	}
	
	public EliminationOption(double option){
		this.option = new Double(option);
	}
	
	public Double getOption() {
		return option;
	}

	public void setOption(Double option) {
		this.option = option;
	}
}
