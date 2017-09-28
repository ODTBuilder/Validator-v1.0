package com.git.gdsbuilder.generalization.opt;

public class SimplificationOption {
	private Double option;

	public SimplificationOption(Double option){
		this.option = option;
	}
	public SimplificationOption(double option){
		this.option = new Double(option);
	}
	
	public Double getOption() {
		return option;
	}
	public void setOption(Double option) {
		this.option = option;
	}
}
