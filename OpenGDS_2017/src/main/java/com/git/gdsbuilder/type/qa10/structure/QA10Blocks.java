package com.git.gdsbuilder.type.qa10.structure;

import java.util.ArrayList;
import java.util.List;

import org.kabeja.dxf.DXFBlock;

public class QA10Blocks {

	String collectionName;
	List<DXFBlock> blocks;

	public QA10Blocks() {
		super();
		this.collectionName = "";
		this.blocks = new ArrayList<DXFBlock>();
	}

	public QA10Blocks(String collectionName, List<DXFBlock> blocks) {
		super();
		this.collectionName = collectionName;
		this.blocks = blocks;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public List<DXFBlock> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<DXFBlock> blocks) {
		this.blocks = blocks;
	}

}
