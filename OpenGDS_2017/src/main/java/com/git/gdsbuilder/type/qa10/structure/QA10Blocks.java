package com.git.gdsbuilder.type.qa10.structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kabeja.dxf.DXFBlock;
import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFVariable;

import jj2000.j2k.entropy.encoder.EBCOTRateAllocator;

public class QA10Blocks {

	String collectionName;

	public void test(Iterator blockIT) {

		List<DXFVariable> blocks = new ArrayList<DXFVariable>();

		while (blockIT.hasNext()) {
			DXFBlock dxfBlock = (DXFBlock) blockIT.next();
			String layerName = dxfBlock.getLayerID();
			String blockName = dxfBlock.getName();

			// block
			DXFVariable block = new DXFVariable(layerName);
			block.setValue("8_object", layerName);
			block.setValue("2_object", blockName);
			block.setValue("70_object", "64");
			block.setValue("10_object", String.valueOf(dxfBlock.getReferencePoint().getX()));
			block.setValue("20_object", String.valueOf(dxfBlock.getReferencePoint().getY()));
			block.setValue("30_object", String.valueOf(dxfBlock.getReferencePoint().getZ()));
			block.setValue("3_object", blockName);

			// entities
			Iterator entityIt = dxfBlock.getDXFEntitiesIterator();
			while(entityIt.hasNext()) {
				DXFEntity dxfEntity = (DXFEntity) entityIt.next();
				
				// entity
				DXFVariable entity = new DXFVariable(layerName);
				String type = dxfEntity.getType();
				entity.setValue("0_entity", type);
				entity.setValue("8_entity", layerName);
				if(type.equals("POLYLINE")) {
					entity.setValue("66_entity", "1");
				}

			}
		}
	}

}
