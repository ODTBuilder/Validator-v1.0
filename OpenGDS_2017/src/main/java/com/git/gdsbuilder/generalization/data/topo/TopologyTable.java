package com.git.gdsbuilder.generalization.data.topo;

import java.io.Serializable;
import java.util.ArrayList;

public class TopologyTable extends ArrayList<Topology> implements Serializable {
private static final long serialVersionUID = -4772221710449542370L;
	
	//Enum
	public enum Type {
		DTGEOLAYERLIST("topologytable"),
		UNKNOWN(null);

		private final String typeName;

		private Type(String typeName) {
			this.typeName = typeName;
		}

		public static Type get(String typeName) {
			for (Type type : values()) {
				if(type == UNKNOWN)
					continue;
				if(type.typeName.equals(typeName))
					return type;
			}
			return UNKNOWN;
		}
		
		public String getTypeName(){
			return this.typeName;
		}
	};
	
	
	public TopologyTable(){}
	
	
	/** 
	 * Topolgy List -> TopologyTable
	 * @param topologys 
	 */
	public TopologyTable(ArrayList<Topology> topologys){
		if(topologys.size()!=0){
			for(Topology topology : topologys){
				super.add(topology);
			}
		}
	}
	public boolean add(Topology topology){
		return super.add(topology);
	}
	
		
	public void allRemove(){
		super.clear();
	}
}
