package com.git.gdsbuilder.generalization.data.res;

import com.git.gdsbuilder.generalization.data.topo.TopologyTable;

/**
 * 일반화한후 레이어 정보객체
 * @author SG.Lee
 * @Date 2016.09
 * */
public class DTGeneralAfLayer extends DTGeneralEAfLayer {
	private TopologyTable topologyTable; // 일반화 TopologyTable -> 모든 객체관계정보
	
	public TopologyTable getTopologyTable() {
		return topologyTable;
	}

	public void setTopologyTable(TopologyTable topologyTable) {
		this.topologyTable = topologyTable;
	}
}
