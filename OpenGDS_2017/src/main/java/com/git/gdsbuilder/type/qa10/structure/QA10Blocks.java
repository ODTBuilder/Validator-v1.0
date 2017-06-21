package com.git.gdsbuilder.type.qa10.structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.kabeja.dxf.DXFArc;
import org.kabeja.dxf.DXFBlock;
import org.kabeja.dxf.DXFCircle;
import org.kabeja.dxf.DXFPolyline;
import org.kabeja.dxf.DXFText;
import org.kabeja.dxf.DXFVertex;

public class QA10Blocks {

	String collectionName;
	List<LinkedHashMap<String, Object>> blocks;

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public List<LinkedHashMap<String, Object>> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<LinkedHashMap<String, Object>> blocks) {
		this.blocks = blocks;
	}

	public void setBlocksValues(Iterator blockIterator) {

		List<LinkedHashMap<String, Object>> blocks = new ArrayList<LinkedHashMap<String, Object>>();

		while (blockIterator.hasNext()) {

			DXFBlock dxfBlock = (DXFBlock) blockIterator.next();
			String layerName = dxfBlock.getLayerID();
			String blockName = dxfBlock.getName();

			// block
			LinkedHashMap<String, Object> block = new LinkedHashMap<String, Object>();
			block.put("8", layerName);
			block.put("2", blockName);
			block.put("70", "64");
			block.put("10", "0");
			block.put("20", "0");
			block.put("30", "0");
			block.put("3", blockName);

			// entities
			List<LinkedHashMap<String, Object>> entities = new ArrayList<LinkedHashMap<String, Object>>();
			Iterator entitiesIt = dxfBlock.getDXFEntitiesIterator();
			while (entitiesIt.hasNext()) {
				Object entityObj = entitiesIt.next();
				if (entityObj instanceof DXFCircle) {
					LinkedHashMap<String, Object> circleMap = getCircleValues((DXFCircle) entityObj, layerName);
					entities.add(circleMap);
				} else if (entityObj instanceof DXFPolyline) {
					LinkedHashMap<String, Object> polylineMap = getPolylineValues((DXFPolyline) entityObj, layerName);
					entities.add(polylineMap);
				} else if (entityObj instanceof DXFVertex) {
					LinkedHashMap<String, Object> vertexMap = getVertexValues((DXFVertex) entityObj, layerName);
					entities.add(vertexMap);
				} else if (entityObj instanceof DXFArc) {
					LinkedHashMap<String, Object> arcMap = getArcValues((DXFArc) entityObj, layerName);
					entities.add(arcMap);
				} else if (entityObj instanceof DXFText) {
					LinkedHashMap<String, Object> textMap = getTextValues((DXFText) entityObj, layerName);
					entities.add(textMap);
				}
			}
			LinkedHashMap<String, Object> blockMap = new LinkedHashMap<String, Object>();
			blockMap.put("block", block);
			blockMap.put("entities", entities);
			blocks.add(blockMap);
		}
		this.blocks = blocks;
	}

	private LinkedHashMap<String, Object> getTextValues(DXFText entityObj, String layerName) {

		LinkedHashMap<String, Object> text = new LinkedHashMap<String, Object>();
		text.put("0", "TEXT");
		text.put("8", layerName);
		text.put("10", entityObj.getAlignX());
		text.put("20", entityObj.getAlignY());
		text.put("30", entityObj.getAlignZ());
		text.put("40", entityObj.getHeight());
		text.put("1", entityObj.getText());
		text.put("7", entityObj.getTextStyle());

		return text;
	}

	private LinkedHashMap<String, Object> getArcValues(DXFArc entityObj, String layerName) {

		LinkedHashMap<String, Object> arc = new LinkedHashMap<String, Object>();
		arc.put("0", "ARC");
		arc.put("8", layerName);
		arc.put("10", entityObj.getCenterPoint().getX());
		arc.put("20", entityObj.getCenterPoint().getY());
		arc.put("30", entityObj.getCenterPoint().getZ());
		arc.put("40", entityObj.getRadius());
		arc.put("50", entityObj.getStartAngle());
		arc.put("51", entityObj.getEndAngle());

		return arc;
	}

	private LinkedHashMap<String, Object> getVertexValues(DXFVertex entityObj, String layerName) {

		LinkedHashMap<String, Object> vertex = new LinkedHashMap<String, Object>();
		vertex.put("0", "VERTEX");
		vertex.put("8", layerName);
		vertex.put("10", entityObj.getX());
		vertex.put("20", entityObj.getY());
		vertex.put("30", entityObj.getZ());

		return vertex;
	}

	private LinkedHashMap<String, Object> getPolylineValues(DXFPolyline entityObj, String layerName) {

		// polyline
		LinkedHashMap<String, Object> polyline = new LinkedHashMap<String, Object>();
		polyline.put("0", "POLYLINE");
		polyline.put("8", layerName);
		polyline.put("66", 1);
		polyline.put("10", 0);
		polyline.put("20", 0);
		polyline.put("30", 0);
		polyline.put("70", 8);

		// vertexs
		List<LinkedHashMap<String, Object>> vertexMapList = new ArrayList<LinkedHashMap<String, Object>>();
		Iterator vertexIt = entityObj.getVertexIterator();
		while (vertexIt.hasNext()) {
			DXFVertex vertex = (DXFVertex) vertexIt.next();
			LinkedHashMap<String, Object> vertexMap = getVertexValues(vertex, layerName);
			vertexMapList.add(vertexMap);
		}
		polyline.put("vertexs", vertexMapList);
		return polyline;
	}

	private LinkedHashMap<String, Object> getCircleValues(DXFCircle entityObj, String layerName) {

		LinkedHashMap<String, Object> circle = new LinkedHashMap<String, Object>();
		circle.put("0", "CIRCLE");
		circle.put("8", layerName);
		circle.put("10", entityObj.getCenterPoint().getX());
		circle.put("20", entityObj.getCenterPoint().getY());
		circle.put("30", entityObj.getCenterPoint().getZ());
		circle.put("40", entityObj.getRadius());

		return circle;
	}
}
