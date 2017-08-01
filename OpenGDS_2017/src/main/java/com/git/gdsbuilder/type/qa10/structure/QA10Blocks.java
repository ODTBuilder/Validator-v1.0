package com.git.gdsbuilder.type.qa10.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.kabeja.dxf.DXFArc;
import org.kabeja.dxf.DXFBlock;
import org.kabeja.dxf.DXFCircle;
import org.kabeja.dxf.DXFLWPolyline;
import org.kabeja.dxf.DXFLine;
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

	public void setBlocksValue(HashMap<String, Object> commonMap, List<LinkedHashMap<String, Object>> entities) {

	}

	public void setBlocksValues(Iterator blockIterator) {

		List<LinkedHashMap<String, Object>> blocks = new ArrayList<LinkedHashMap<String, Object>>();

		while (blockIterator.hasNext()) {

			DXFBlock dxfBlock = (DXFBlock) blockIterator.next();
			String layerName = dxfBlock.getLayerID();
			String blockName = dxfBlock.getName();

			if (blockName.equals("*MODEL_SPACE") || blockName.equals("*PAPER_SPACE")) {
				continue;
			}

			// block
			LinkedHashMap<String, Object> block = new LinkedHashMap<String, Object>();
			block.put("8", layerName);
			block.put("2", blockName);
			block.put("70", "64");
			block.put("10", "0");
			block.put("20", "0");
			block.put("30", "0");
			block.put("3", blockName);

			boolean isCircle = false;
			boolean isPolyline = false;
			boolean isLWPolyline = false;
			boolean isVertex = false;
			boolean isArc = false;
			boolean isText = false;
			boolean isLine = false;

			// entities
			List<LinkedHashMap<String, Object>> entities = new ArrayList<LinkedHashMap<String, Object>>();
			Iterator entitiesIt = dxfBlock.getDXFEntitiesIterator();
			while (entitiesIt.hasNext()) {
				Object entityObj = entitiesIt.next();
				if (entityObj instanceof DXFCircle) {
					LinkedHashMap<String, Object> circleMap = getCircleValues((DXFCircle) entityObj);
					entities.add(circleMap);
					isCircle = true;
				} else if (entityObj instanceof DXFPolyline) {
					DXFPolyline polyline = (DXFPolyline) entityObj;
					String type = polyline.getType();
					LinkedHashMap<String, Object> polylineMap = getPolylineValues(polyline);
					entities.add(polylineMap);
					if (type.equals("POLYLINE")) {
						isPolyline = true;
					}
					if (type.equals("LWPOLYLINE")) {
						isLWPolyline = true;
					}
				} else if (entityObj instanceof DXFVertex) {
					LinkedHashMap<String, Object> vertexMap = getVertexValues((DXFVertex) entityObj);
					entities.add(vertexMap);
					isVertex = true;
				} else if (entityObj instanceof DXFArc) {
					LinkedHashMap<String, Object> arcMap = getArcValues((DXFArc) entityObj);
					entities.add(arcMap);
					isArc = true;
				} else if (entityObj instanceof DXFText) {
					LinkedHashMap<String, Object> textMap = getTextValues((DXFText) entityObj);
					entities.add(textMap);
					isText = true;
				} else if (entityObj instanceof DXFLine) {
					LinkedHashMap<String, Object> lineMap = getLineValues((DXFLine) entityObj);
					entities.add(lineMap);
					isLine = true;
				}
			}
			block.put("is_circle", isCircle);
			block.put("is_polyoine", isPolyline);
			block.put("is_vertex", isVertex);
			block.put("is_arc", isArc);
			block.put("is_text", isText);
			block.put("is_line", isLine);
			block.put("is_lwpolyoine", isLWPolyline);

			LinkedHashMap<String, Object> blockMap = new LinkedHashMap<String, Object>();
			blockMap.put("block", block);
			blockMap.put("entities", entities);
			blocks.add(blockMap);
		}
		this.blocks = blocks;
	}

	private LinkedHashMap<String, Object> getLWPolylineValues(DXFLWPolyline entityObj) {

		LinkedHashMap<String, Object> lwPolyline = new LinkedHashMap<String, Object>();
		lwPolyline.put("0", entityObj.getType());
		lwPolyline.put("330", entityObj.getID());
		lwPolyline.put("8", entityObj.getLayerName());

		return lwPolyline;
	}

	private LinkedHashMap<String, Object> getLineValues(DXFLine entityObj) {

		LinkedHashMap<String, Object> line = new LinkedHashMap<String, Object>();
		line.put("0", entityObj.getType());
		line.put("330", entityObj.getID());
		line.put("8", entityObj.getLayerName());
		line.put("10", entityObj.getStartPoint().getX());
		line.put("20", entityObj.getStartPoint().getY());
		line.put("30", entityObj.getStartPoint().getZ());
		line.put("11", entityObj.getEndPoint().getX());
		line.put("21", entityObj.getEndPoint().getY());
		line.put("31", entityObj.getEndPoint().getZ());

		return line;
	}

	private LinkedHashMap<String, Object> getTextValues(DXFText entityObj) {

		LinkedHashMap<String, Object> text = new LinkedHashMap<String, Object>();
		text.put("0", "TEXT");
		text.put("8", entityObj.getLayerName());
		text.put("330", entityObj.getID());
		text.put("10", entityObj.getInsertPoint().getX());
		text.put("20", entityObj.getInsertPoint().getY());
		text.put("30", entityObj.getInsertPoint().getZ());
		text.put("40", entityObj.getHeight());
		text.put("1", entityObj.getText());
		text.put("7", entityObj.getTextStyle());

		return text;
	}

	private LinkedHashMap<String, Object> getArcValues(DXFArc entityObj) {

		LinkedHashMap<String, Object> arc = new LinkedHashMap<String, Object>();
		arc.put("0", "ARC");
		arc.put("8", entityObj.getLayerName());
		arc.put("330", entityObj.getID());
		arc.put("10", entityObj.getCenterPoint().getX());
		arc.put("20", entityObj.getCenterPoint().getY());
		arc.put("30", entityObj.getCenterPoint().getZ());
		arc.put("40", entityObj.getRadius());
		arc.put("50", entityObj.getStartAngle());
		arc.put("51", entityObj.getEndAngle());

		return arc;
	}

	private LinkedHashMap<String, Object> getVertexValues(DXFVertex entityObj) {

		LinkedHashMap<String, Object> vertex = new LinkedHashMap<String, Object>();
		vertex.put("0", "VERTEX");
		vertex.put("8", entityObj.getLayerName());
		vertex.put("330", entityObj.getID());
		vertex.put("10", entityObj.getX());
		vertex.put("20", entityObj.getY());
		vertex.put("30", entityObj.getZ());

		return vertex;
	}

	private LinkedHashMap<String, Object> getPolylineValues(DXFPolyline entityObj) {

		String type = entityObj.getType();
		LinkedHashMap<String, Object> polyline = new LinkedHashMap<String, Object>();

		if (type.equals("POLYLINE")) {
			// polyline
			polyline.put("0", "POLYLINE");
			polyline.put("8", entityObj.getLayerName());
			// polyline.put("330", entityObj.getID());
			polyline.put("66", "1");
			polyline.put("10", "0");
			polyline.put("20", "0");
			polyline.put("30", "0");
			polyline.put("70", "0");

			// vertexs
			List<LinkedHashMap<String, Object>> vertexMapList = new ArrayList<LinkedHashMap<String, Object>>();
			Iterator vertexIt = entityObj.getVertexIterator();
			while (vertexIt.hasNext()) {
				DXFVertex vertex = (DXFVertex) vertexIt.next();
				LinkedHashMap<String, Object> vertexMap = getVertexValues(vertex);
				vertexMapList.add(vertexMap);
			}
			polyline.put("vertexs", vertexMapList);
		} else if (type.equals("LWPOLYLINE")) {

			polyline.put("0", "LWPOLYLINE");
			polyline.put("8", entityObj.getLayerName());
			// polyline.put("330", entityObj.getID());
			polyline.put("90", entityObj.getVertexCount());
			polyline.put("70", entityObj.getFlags());
			polyline.put("43", 0);

			// vertexs
			List<LinkedHashMap<String, Object>> vertexMapList = new ArrayList<LinkedHashMap<String, Object>>();
			Iterator vertexIt = entityObj.getVertexIterator();
			while (vertexIt.hasNext()) {
				DXFVertex vertex = (DXFVertex) vertexIt.next();
				LinkedHashMap<String, Object> vertexMap = getVertexValues(vertex);
				vertexMapList.add(vertexMap);
			}
			polyline.put("vertexs", vertexMapList);
		}
		return polyline;
	}

	private LinkedHashMap<String, Object> getCircleValues(DXFCircle entityObj) {

		LinkedHashMap<String, Object> circle = new LinkedHashMap<String, Object>();
		circle.put("0", "CIRCLE");
		circle.put("8", entityObj.getLayerName());
		circle.put("330", entityObj.getID());
		circle.put("10", entityObj.getCenterPoint().getX());
		circle.put("20", entityObj.getCenterPoint().getY());
		circle.put("30", entityObj.getCenterPoint().getZ());
		circle.put("40", entityObj.getRadius());

		return circle;
	}

	public static LinkedHashMap<String, Object> getCircleValues(HashMap<String, Object> entity) {

		LinkedHashMap<String, Object> circle = new LinkedHashMap<String, Object>();
		circle.put("0", entity.get("0"));
		circle.put("8", entity.get("8"));
		// circle.put("330", entity.get("330"));
		circle.put("10", entity.get("10"));
		circle.put("20", entity.get("20"));
		circle.put("30", entity.get("30"));
		circle.put("40", entity.get("40"));

		return circle;
	}

	public static LinkedHashMap<String, Object> getCommonsValue(HashMap<String, Object> blockCommonMap) {

		LinkedHashMap<String, Object> block = new LinkedHashMap<String, Object>();

		block.put("8", blockCommonMap.get("2"));
		block.put("2", blockCommonMap.get("2"));
		block.put("70", blockCommonMap.get("70"));
		block.put("10", blockCommonMap.get("10"));
		block.put("20", blockCommonMap.get("20"));
		block.put("30", blockCommonMap.get("30"));
		block.put("3", blockCommonMap.get("3"));

		return block;
	}

	public static LinkedHashMap<String, Object> getArcValues(HashMap<String, Object> blockArcMap) {

		LinkedHashMap<String, Object> arc = new LinkedHashMap<String, Object>();
		arc.put("0", blockArcMap.get("0"));
		arc.put("8", blockArcMap.get("8"));
		// arc.put("330", blockArcMap.get("330"));
		arc.put("10", blockArcMap.get("10"));
		arc.put("20", blockArcMap.get("20"));
		arc.put("30", blockArcMap.get("30"));
		arc.put("40", blockArcMap.get("40"));
		arc.put("50", blockArcMap.get("50"));
		arc.put("51", blockArcMap.get("51"));

		return arc;
	}

	public static LinkedHashMap<String, Object> getPolylineValue(HashMap<String, Object> blockPolylineMap,
			HashMap<String, Object> vertexMap) {

		// polyline
		LinkedHashMap<String, Object> polyline = new LinkedHashMap<String, Object>();
		polyline.put("0", blockPolylineMap.get("0"));
		polyline.put("8", blockPolylineMap.get("8"));
		// polyline.put("330", blockPolylineMap.get("330"));
		polyline.put("66", blockPolylineMap.get("66"));
		polyline.put("10", vertexMap.get("10"));
		polyline.put("20", vertexMap.get("20"));
		polyline.put("30", vertexMap.get("30"));
		polyline.put("70", "8");
		return polyline;
	}

	public static LinkedHashMap<String, Object> getTextValue(HashMap<String, Object> blockTextMap) {

		LinkedHashMap<String, Object> text = new LinkedHashMap<String, Object>();
		text.put("0", blockTextMap.get("0"));
		text.put("8", blockTextMap.get("8"));
		// text.put("330", blockTextMap.get("330"));
		text.put("10", blockTextMap.get("10"));
		text.put("20", blockTextMap.get("20"));
		text.put("30", blockTextMap.get("30"));
		text.put("40", blockTextMap.get("40"));
		text.put("1", blockTextMap.get("1"));
		text.put("7", blockTextMap.get("7"));

		return text;
	}

	public static LinkedHashMap<String, Object> getVertexValue(HashMap<String, Object> vertexMap) {

		LinkedHashMap<String, Object> vertex = new LinkedHashMap<String, Object>();
		vertex.put("0", "VERTEX");
		vertex.put("8", vertexMap.get("8"));
		vertex.put("10", vertexMap.get("10"));
		vertex.put("20", vertexMap.get("20"));
		vertex.put("30", vertexMap.get("30"));

		return vertex;
	}

	public static LinkedHashMap<String, Object> getLineValue(HashMap<String, Object> blockLineMap) {

		// polyline
		LinkedHashMap<String, Object> polyline = new LinkedHashMap<String, Object>();
		polyline.put("0", "POLYLINE");
		polyline.put("8", blockLineMap.get("8"));
		// polyline.put("330", blockPolylineMap.get("330"));
		polyline.put("10", blockLineMap.get("10"));
		polyline.put("20", blockLineMap.get("20"));
		polyline.put("30", blockLineMap.get("30"));
		polyline.put("66", "1");
		polyline.put("70", "8");

		return polyline;

	}

	public static LinkedHashMap<String, Object> getLWPolylineValue(HashMap<String, Object> blockLWPolylineMap,
			HashMap<String, Object> vertexMap) {

		// polyline
		LinkedHashMap<String, Object> polyline = new LinkedHashMap<String, Object>();
		polyline.put("0", "POLYLINE");
		polyline.put("8", blockLWPolylineMap.get("8"));
		// polyline.put("330", blockPolylineMap.get("330"));
		polyline.put("66", "1");
		polyline.put("10", vertexMap.get("10"));
		polyline.put("20", vertexMap.get("20"));
		polyline.put("30", vertexMap.get("30"));

		String flag = (String) blockLWPolylineMap.get("70");
		if (flag.equals("0")) {
			polyline.put("70", "0");
		} else if (flag.equals("1")) {
			polyline.put("70", "1");
		} else if (flag.equals("128")) {
			polyline.put("70", "8");
		}
		return polyline;
	}

	public static List<LinkedHashMap<String, Object>> getLineVertexsValue(HashMap<String, Object> blockLineMap) {

		LinkedHashMap<String, Object> firVertex = new LinkedHashMap<String, Object>();
		firVertex.put("0", "VERTEX");
		firVertex.put("8", blockLineMap.get("8"));
		firVertex.put("10", blockLineMap.get("10"));
		firVertex.put("20", blockLineMap.get("20"));
		firVertex.put("30", blockLineMap.get("30"));

		LinkedHashMap<String, Object> secVertex = new LinkedHashMap<String, Object>();
		secVertex.put("0", "VERTEX");
		secVertex.put("8", blockLineMap.get("8"));
		secVertex.put("10", blockLineMap.get("11"));
		secVertex.put("20", blockLineMap.get("21"));
		secVertex.put("30", blockLineMap.get("31"));

		List<LinkedHashMap<String, Object>> vertexEntityList = new ArrayList<LinkedHashMap<String, Object>>();
		vertexEntityList.add(firVertex);
		vertexEntityList.add(secVertex);

		return vertexEntityList;
	}
}
