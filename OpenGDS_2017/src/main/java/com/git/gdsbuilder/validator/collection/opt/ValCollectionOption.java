	package com.git.gdsbuilder.validator.collection.opt;

import java.util.HashMap;
import java.util.List;

import com.git.gdsbuilder.validator.collection.opt.ValCollectionOption.ValCollectionOptionType;

public class ValCollectionOption extends HashMap<ValCollectionOptionType,Object>{
	/**
	 *
	 * @author SG.Lee
	 * @Date 2017. 7. 4. 오후 4:14:36
	 * */
	private static final long serialVersionUID = 1L;

	public enum ValCollectionOptionType {
		ENTITYNONE("EntityNONE"),
		EDGEMATCHMISS("EdgeMatchMiss"),
		REFZVALUEMISS("RefZValueMiss"),
		REFATTRIBUTEMISS("RefAttributeMiss"),
		UNDERSHOOT("UnderShoot");
	
			private String typeName;
	
			public String getTypeName() {
				return typeName;
			}

			ValCollectionOptionType(String typeName) {
				this.typeName = typeName;
			}
	}
	
	public void putEntityNoneOption(boolean flag){
		super.put(ValCollectionOptionType.ENTITYNONE, flag);
	}
	
	public void putEdgeMatchMissOption(boolean flag){
		super.put(ValCollectionOptionType.EDGEMATCHMISS, flag);
	}
	
	public void putRefZValueMissOption(List<String>  colunm){
		super.put(ValCollectionOptionType.REFZVALUEMISS, colunm);
	}
	
	public void putRefAttributeMissOption(List<String> colunms){
		super.put(ValCollectionOptionType.REFATTRIBUTEMISS, colunms);
	}
	
	public void putUnderShootOption(double tolerence){
		super.put(ValCollectionOptionType.UNDERSHOOT, tolerence);
	}
	
}
