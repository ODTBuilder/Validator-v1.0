package com.git.gdsbuilder.FileRead.dxf.writer;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.git.gdsbuilder.type.qa10.collection.QA10LayerCollection;
import com.git.gdsbuilder.type.qa10.layer.QA10Layer;
import com.git.gdsbuilder.type.qa10.structure.QA10Blocks;
import com.git.gdsbuilder.type.qa10.structure.QA10Entities;
import com.git.gdsbuilder.type.qa10.structure.QA10Header;
import com.git.gdsbuilder.type.qa10.structure.QA10Tables;

public class QA10FileWriter {

	BufferedWriter dxfWriter;

	protected static String exe = ".dxf";
	protected static String startSectionCode = "0";
	protected static String sectionNameCode = "2";
	protected static String section = "SECTION";
	protected static String endSectionCode = "0";
	protected static String endSection = "ENDSEC";

	protected static String header = "HEADER";

	protected static String tables = "TABLES";
	protected static String startTableCode = "0";
	protected static String table = "TABLE";
	protected static String layer = "LAYER";
	protected static String style = "STYLE";
	protected static String endTableCode = "0";
	protected static String endTable = "ENDTAB";

	protected static String startBlockCode = "0";
	protected static String blocks = "BLOCKS";
	protected static String block = "BLOCK";

	protected static String startEndBlocksCode = "0";
	protected static String endBlock = "ENDBLK";

	protected static String entities = "ENTITIES";

	public Map<String, Object> writeDxfFile(QA10LayerCollection qa10LayerCollection, QA10Layer errQA20Layer)
			throws IOException {

		Map<String, Object> dxfFileMap = new HashMap<String, Object>();

		String collectionName = qa10LayerCollection.getCollectionName();
		dxfFileMap.put("fileName", collectionName);
		String dxfFileRoot = "D:\\" + collectionName + exe;
		dxfFileMap.put("fileDxfDir", dxfFileRoot);
		// this.dxfWriter = new BufferedWriter(new FileWriter(dxfFileRoot,
		// true));

		this.dxfWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dxfFileRoot), "euc-kr"));

		// header
		writeDefaultHeaderSection(qa10LayerCollection.getHeader());
		// tables
		writeTablesSection(qa10LayerCollection.getTables(), errQA20Layer);
		// blocks
		writeBlockSection(qa10LayerCollection.getBlocks());
		// entities
		writeEntitesSection(qa10LayerCollection.getEntities(), errQA20Layer);

		dxfWriter.write("0");
		dxfWriter.newLine();
		dxfWriter.write("EOF");
		dxfWriter.newLine();

		dxfWriter.flush();
		dxfWriter.close();

		return dxfFileMap;
	}

	private void writeBlockSection(QA10Blocks qa10Blocks) throws IOException {

		dxfWriter.write(startSectionCode);
		dxfWriter.newLine();
		dxfWriter.write(section);
		dxfWriter.newLine();
		dxfWriter.write(sectionNameCode);
		dxfWriter.newLine();
		dxfWriter.write(blocks);
		dxfWriter.newLine();

		List<LinkedHashMap<String, Object>> blocksMapList = qa10Blocks.getBlocks();
		for (int i = 0; i < blocksMapList.size(); i++) {
			LinkedHashMap<String, Object> blocksMap = blocksMapList.get(i);
			// common
			LinkedHashMap<String, Object> common = (LinkedHashMap<String, Object>) blocksMap.get("block");

			dxfWriter.write(startBlockCode);
			dxfWriter.newLine();
			dxfWriter.write(block);
			dxfWriter.newLine();
			Iterator commonIt = common.keySet().iterator();
			while (commonIt.hasNext()) {
				String commonKey = (String) commonIt.next();
				Object commonValue = common.get(commonKey);
				if (commonValue != null) {
					dxfWriter.write(commonKey);
					dxfWriter.newLine();
					dxfWriter.write((String) commonValue);
					dxfWriter.newLine();
				} else {
					continue;
				}
			}
			// entities
			List<LinkedHashMap<String, Object>> entities = (List<LinkedHashMap<String, Object>>) blocksMap
					.get("entities");
			writeEntities(entities);

			dxfWriter.write(startEndBlocksCode);
			dxfWriter.newLine();
			dxfWriter.write(endBlock);
			dxfWriter.newLine();
			dxfWriter.write("8");
			dxfWriter.newLine();
			dxfWriter.write("0");
			dxfWriter.newLine();
		}
		dxfWriter.write(endSectionCode);
		dxfWriter.newLine();
		dxfWriter.write(endSection);
		dxfWriter.newLine();
	}

	private void writeEntities(List<LinkedHashMap<String, Object>> entities) throws IOException {

		for (int j = 0; j < entities.size(); j++) {
			LinkedHashMap<String, Object> entity = entities.get(j);
			String entityType = (String) entity.get("0");
			String layerId = (String) entity.get("8");
			if (entityType.equals("POLYLINE") || entityType.equals("LWPOLYLINE")) {
				Iterator entityIt = entity.keySet().iterator();
				while (entityIt.hasNext()) {
					String entityKey = (String) entityIt.next();
					if (entityKey.equals("0")) {
						String entityValue = "POLYLINE";
						dxfWriter.write(entityKey);
						dxfWriter.newLine();
						dxfWriter.write(entityValue);
						dxfWriter.newLine();
					}
					if (entityKey.equals("vertexs")) {
						List<LinkedHashMap<String, Object>> entityValueList = (List<LinkedHashMap<String, Object>>) entity
								.get(entityKey);
						for (int k = 0; k < entityValueList.size(); k++) {
							LinkedHashMap<String, Object> entityValue = entityValueList.get(k);
							Iterator vertexIt = entityValue.keySet().iterator();
							while (vertexIt.hasNext()) {
								String vertexKey = (String) vertexIt.next();
								if (vertexKey.equals("8")) {
									String vertexValue = (String) entity.get("8");
									dxfWriter.write(vertexKey);
									dxfWriter.newLine();
									dxfWriter.write(vertexValue);
									dxfWriter.newLine();
								} else {
									Object vertexValue = entityValue.get(vertexKey);
									if (vertexValue != null) {
										dxfWriter.write(vertexKey);
										dxfWriter.newLine();
										dxfWriter.write((String) vertexValue);
										dxfWriter.newLine();
									} else {
										continue;
									}
								}
							}
						}
					}
					if (!entityKey.equals("0") && !entityKey.equals("vertexs")) {
						Object entityValue = entity.get(entityKey);
						if (entityValue != null) {
							dxfWriter.write(entityKey);
							dxfWriter.newLine();
							dxfWriter.write((String) entityValue);
							dxfWriter.newLine();
						} else {
							continue;
						}
					}
				}
				dxfWriter.write("0");
				dxfWriter.newLine();
				dxfWriter.write("SEQEND");
				dxfWriter.newLine();
				dxfWriter.write("8");
				dxfWriter.newLine();
				dxfWriter.write(layerId);
				dxfWriter.newLine();
			} else {
				Iterator entityIt = entity.keySet().iterator();
				while (entityIt.hasNext()) {
					String entityKey = (String) entityIt.next();
					Object entityValue = entity.get(entityKey);
					if (entityValue != null) {
						dxfWriter.write(entityKey);
						dxfWriter.newLine();
						dxfWriter.write((String) entityValue);
						dxfWriter.newLine();
					} else {
						continue;
					}
				}
			}
		}
	}

	private void writeDefaultBlockSection() throws IOException {

		dxfWriter.write(startSectionCode);
		dxfWriter.newLine();
		dxfWriter.write(section);
		dxfWriter.newLine();
		dxfWriter.write(sectionNameCode);
		dxfWriter.newLine();
		dxfWriter.write(blocks);
		dxfWriter.newLine();
		dxfWriter.write(endSectionCode);
		dxfWriter.newLine();
		dxfWriter.write(endSection);
		dxfWriter.newLine();
	}

	private void writeEntitesSection(QA10Entities qa10Entities, QA10Layer errQA20Layer) throws IOException {

		dxfWriter.write(startSectionCode);
		dxfWriter.newLine();
		dxfWriter.write(section);
		dxfWriter.newLine();
		dxfWriter.write(sectionNameCode);
		dxfWriter.newLine();
		dxfWriter.write(entities);
		dxfWriter.newLine();

		Map<String, Object> valuesMap = qa10Entities.getValues();
		Iterator layerIt = valuesMap.keySet().iterator();
		while (layerIt.hasNext()) {
			String layerID = (String) layerIt.next();
			List<LinkedHashMap<String, Object>> entitiyMapList = (List<LinkedHashMap<String, Object>>) valuesMap
					.get(layerID);
			writeEntities(entitiyMapList);
		}

		// err
		QA10Entities entity = new QA10Entities();
		entity.setErrTextValues(errQA20Layer);
		LinkedHashMap<String, Object> errEntity = (LinkedHashMap<String, Object>) entity.getValues();
		writeEntity((List<LinkedHashMap<String, Object>>) errEntity.get(errQA20Layer.getLayerID()));

		dxfWriter.write(endSectionCode);
		dxfWriter.newLine();
		dxfWriter.write(endSection);
		dxfWriter.newLine();
	}

	private void writeEntity(List<LinkedHashMap<String, Object>> entityList) throws IOException {

		for (int i = 0; i < entityList.size(); i++) {
			LinkedHashMap<String, Object> list = entityList.get(i);
			String entityType = (String) list.get("0");
			String layerId = (String) list.get("8");
			if (entityType.equals("POLYLINE") || entityType.equals("LWPOLYLINE")) {
				Iterator entityIt = list.keySet().iterator();
				while (entityIt.hasNext()) {
					String entityKey = (String) entityIt.next();
					if (entityKey.equals("0")) {
						String entityValue = "POLYLINE";
						dxfWriter.write(entityKey);
						dxfWriter.newLine();
						dxfWriter.write(entityValue);
						dxfWriter.newLine();
					}
					if (entityKey.equals("vertexs")) {
						List<LinkedHashMap<String, Object>> entityValueList = (List<LinkedHashMap<String, Object>>) list
								.get(entityKey);
						for (int k = 0; k < entityValueList.size(); k++) {
							LinkedHashMap<String, Object> entityValue = entityValueList.get(k);
							Iterator vertexIt = entityValue.keySet().iterator();
							while (vertexIt.hasNext()) {
								String vertexKey = (String) vertexIt.next();
								if (vertexKey.equals("8")) {
									String vertexValue = (String) list.get("8");
									dxfWriter.write(vertexKey);
									dxfWriter.newLine();
									dxfWriter.write(vertexValue);
									dxfWriter.newLine();
								} else {
									Object vertexValue = entityValue.get(vertexKey);
									if (vertexValue != null) {
										dxfWriter.write(vertexKey);
										dxfWriter.newLine();
										dxfWriter.write((String) vertexValue);
										dxfWriter.newLine();
									} else {
										continue;
									}
								}
							}
						}
					}
					if (!entityKey.equals("0") && !entityKey.equals("vertexs")) {
						Object entityValue = list.get(entityKey);
						if (entityValue != null) {
							dxfWriter.write(entityKey);
							dxfWriter.newLine();
							dxfWriter.write((String) entityValue);
							dxfWriter.newLine();
						} else {
							continue;
						}
					}
				}
				dxfWriter.write("0");
				dxfWriter.newLine();
				dxfWriter.write("SEQEND");
				dxfWriter.newLine();
				dxfWriter.write("8");
				dxfWriter.newLine();
				dxfWriter.write(layerId);
				dxfWriter.newLine();
			} else {
				Iterator entityIt = list.keySet().iterator();
				while (entityIt.hasNext()) {
					String entityKey = (String) entityIt.next();
					Object entityValue = list.get(entityKey);
					if (entityValue != null) {
						dxfWriter.write(entityKey);
						dxfWriter.newLine();
						dxfWriter.write((String) entityValue);
						dxfWriter.newLine();
					} else {
						continue;
					}
				}
			}
		}
	}

	private void writeTablesSection(QA10Tables qa10Tables, QA10Layer errQA20Layer) throws IOException {

		dxfWriter.write(startSectionCode);
		dxfWriter.newLine();
		dxfWriter.write(section);
		dxfWriter.newLine();
		dxfWriter.write(sectionNameCode);
		dxfWriter.newLine();
		dxfWriter.write(tables);
		dxfWriter.newLine();

		writeDefaultLTYPE(qa10Tables.getLineTypes());
		writeLAYER(qa10Tables.getLayers(), errQA20Layer);
		writeDefaultSTYLE(qa10Tables.getStyles());

		dxfWriter.write(endSectionCode);
		dxfWriter.newLine();
		dxfWriter.write(endSection);
		dxfWriter.newLine();

	}

	private void writeDefaultSTYLE(Map<String, Object> styles) throws IOException {

		dxfWriter.write(startTableCode);
		dxfWriter.newLine();
		dxfWriter.write(table);
		dxfWriter.newLine();

		// common
		LinkedHashMap<String, Object> commons = (LinkedHashMap<String, Object>) styles.get("common");
		Iterator commonsIt = commons.keySet().iterator();
		while (commonsIt.hasNext()) {
			String commonsKey = (String) commonsIt.next();
			String commonValue = (String) commons.get(commonsKey);
			dxfWriter.write(commonsKey);
			dxfWriter.newLine();
			dxfWriter.write(commonValue);
			dxfWriter.newLine();

		}
		List<LinkedHashMap<String, Object>> lineTypes = (List<LinkedHashMap<String, Object>>) styles.get("styles");
		for (int i = 0; i < lineTypes.size(); i++) {
			LinkedHashMap<String, Object> variable = lineTypes.get(i);
			Iterator variableIt = variable.keySet().iterator();
			while (variableIt.hasNext()) {
				String variableKey = (String) variableIt.next();
				String variableValue = (String) variable.get(variableKey);
				dxfWriter.write(variableKey);
				dxfWriter.newLine();
				dxfWriter.write(variableValue);
				dxfWriter.newLine();
			}
		}
		dxfWriter.write(endTableCode);
		dxfWriter.newLine();
		dxfWriter.write(endTable);
		dxfWriter.newLine();
	}

	private void writeLAYER(Map<String, Object> layers, QA10Layer errQA20Layer) throws IOException {

		dxfWriter.write(startTableCode);
		dxfWriter.newLine();
		dxfWriter.write(table);
		dxfWriter.newLine();

		// common
		LinkedHashMap<String, Object> commons = (LinkedHashMap<String, Object>) layers.get("common");
		Iterator commonsIt = commons.keySet().iterator();
		while (commonsIt.hasNext()) {
			String commonsKey = (String) commonsIt.next();
			String commonValue = (String) commons.get(commonsKey);
			dxfWriter.write(commonsKey);
			dxfWriter.newLine();
			dxfWriter.write(commonValue);
			dxfWriter.newLine();

		}
		List<LinkedHashMap<String, Object>> lineTypes = (List<LinkedHashMap<String, Object>>) layers.get("layers");
		for (int i = 0; i < lineTypes.size(); i++) {
			LinkedHashMap<String, Object> variable = lineTypes.get(i);
			Iterator variableIt = variable.keySet().iterator();
			while (variableIt.hasNext()) {
				String variableKey = (String) variableIt.next();
				String variableValue = (String) variable.get(variableKey);
				dxfWriter.write(variableKey);
				dxfWriter.newLine();
				dxfWriter.write(variableValue);
				dxfWriter.newLine();
			}
		}
		// err
		LinkedHashMap<String, Object> layer = QA10Tables.getLayerValues(errQA20Layer);
		Iterator variableIt = layer.keySet().iterator();
		while (variableIt.hasNext()) {
			String variableKey = (String) variableIt.next();
			String variableValue = (String) layer.get(variableKey);
			dxfWriter.write(variableKey);
			dxfWriter.newLine();
			dxfWriter.write(variableValue);
			dxfWriter.newLine();
		}
		dxfWriter.write(endTableCode);
		dxfWriter.newLine();
		dxfWriter.write(endTable);
		dxfWriter.newLine();
	}

	private void writeDefaultLTYPE(Map<String, Object> lineTypseMap) throws IOException {

		dxfWriter.write(startTableCode);
		dxfWriter.newLine();
		dxfWriter.write(table);
		dxfWriter.newLine();

		// common
		LinkedHashMap<String, Object> commons = (LinkedHashMap<String, Object>) lineTypseMap.get("common");
		Iterator commonsIt = commons.keySet().iterator();
		while (commonsIt.hasNext()) {
			String commonsKey = (String) commonsIt.next();
			String commonValue = (String) commons.get(commonsKey);
			dxfWriter.write(commonsKey);
			dxfWriter.newLine();
			dxfWriter.write(commonValue);
			dxfWriter.newLine();

		}
		LinkedHashMap<String, Object> variable = (LinkedHashMap<String, Object>) lineTypseMap.get("lineTypes");
		Iterator variableIt = variable.keySet().iterator();
		while (variableIt.hasNext()) {
			String variableKey = (String) variableIt.next();
			String variableValue = (String) variable.get(variableKey);
			dxfWriter.write(variableKey);
			dxfWriter.newLine();
			dxfWriter.write(variableValue);
			dxfWriter.newLine();
		}
		dxfWriter.write(endTableCode);
		dxfWriter.newLine();
		dxfWriter.write(endTable);
		dxfWriter.newLine();
	}

	private void writeDefaultHeaderSection(QA10Header qa10Header) throws IOException {

		dxfWriter.write(startSectionCode);
		dxfWriter.newLine();
		dxfWriter.write(section);
		dxfWriter.newLine();
		dxfWriter.write(sectionNameCode);
		dxfWriter.newLine();
		dxfWriter.write(header);
		dxfWriter.newLine();

		qa10Header.setDefaultHeaderValues();
		Map<String, Object> defaultValues = qa10Header.getDefaultValues();
		List<LinkedHashMap<String, Object>> variables = (List<LinkedHashMap<String, Object>>) defaultValues
				.get("defualtHeader");
		for (int i = 0; i < variables.size(); i++) {
			LinkedHashMap<String, Object> variable = variables.get(i);
			Iterator variableIt = variable.keySet().iterator();
			while (variableIt.hasNext()) {
				String variableKey = (String) variableIt.next();
				String variableValue = (String) variable.get(variableKey);
				dxfWriter.write(variableKey);
				dxfWriter.newLine();
				dxfWriter.write(variableValue);
				dxfWriter.newLine();
			}
		}
		dxfWriter.write(endSectionCode);
		dxfWriter.newLine();
		dxfWriter.write(endSection);
		dxfWriter.newLine();
	}
}
