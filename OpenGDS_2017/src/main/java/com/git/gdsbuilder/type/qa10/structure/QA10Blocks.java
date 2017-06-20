package com.git.gdsbuilder.type.qa10.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kabeja.dxf.DXFBlock;
import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFVariable;

public class QA10Blocks {

	String collectionName;
	DXFVariable block;
	Map<String, Object> defaultValues;
	
	
	public void setDefaultHeaderValues() {

		Map<String, Object> headerMap = new HashMap<String, Object>();

		List<DXFVariable> variables = new ArrayList<DXFVariable>();
		DXFVariable acadver = new DXFVariable("$ACADVER");
		acadver.setValue("$ACADVER_9", "$ACADVER");
		acadver.setValue("$ACADVER_1", "AC1014");
		variables.add(acadver);

		DXFVariable insbase = new DXFVariable("$INSBASE");
		insbase.setValue("$INSBASE_9", "$INSBASE");
		insbase.setValue("$INSBASE_10", "0.0");
		insbase.setValue("$INSBASE_20", "0.0");
		insbase.setValue("$INSBASE_30", "0.0");
		variables.add(insbase);

		DXFVariable extmin = new DXFVariable("$EXTMIN");
		extmin.setValue("$EXTMIN_9", "$EXTMIN");
		extmin.setValue("$EXTMIN_10", "0.0");
		extmin.setValue("$EXTMIN_20", "0.0");
		variables.add(extmin);

		DXFVariable extmax = new DXFVariable("$EXTMAX");
		extmax.setValue("$EXTMAX_9", "$EXTMAX");
		extmax.setValue("$EXTMAX_10", "10000000.0");
		extmax.setValue("$EXTMAX_20", "10000000.0");
		variables.add(extmax);

		DXFVariable linmin = new DXFVariable("$LIMMIN");
		linmin.setValue("$LIMMIN_9", "$LIMMIN");
		linmin.setValue("$LIMMIN_10", "0.0");
		linmin.setValue("$LIMMIN_20", "0.0");
		variables.add(linmin);

		DXFVariable linmax = new DXFVariable("$LIMMAX");
		linmax.setValue("$LIMMAX_9", "$LIMMAX");
		linmax.setValue("$LIMMAX_10", "10000000.0");
		linmax.setValue("$LIMMAX_20", "10000000.0");
		variables.add(linmax);

		headerMap.put("defualtHeader", variables);
		this.defaultValues = headerMap;
	}
	
	
	
	

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
