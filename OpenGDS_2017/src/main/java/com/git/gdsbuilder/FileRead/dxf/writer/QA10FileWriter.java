package com.git.gdsbuilder.FileRead.dxf.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.git.gdsbuilder.type.qa10.collection.QA10LayerCollection;
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

	protected static String blocks = "BLOCKS";

	protected static String entities = "ENTITIES";

	public void writeDxfFile(QA10LayerCollection qa10LayerCollection) throws IOException {

		String collectionName = qa10LayerCollection.getCollectionName();
		String dxfFileRoot = "D:\\err_" + "dxfTest" + exe;

		this.dxfWriter = new BufferedWriter(new FileWriter(dxfFileRoot, true));

		// header
		writeDefaultHeaderSection(qa10LayerCollection.getHeader());
		// tables
		writeDefaultTablesSection(qa10LayerCollection.getTables());
		// blocks
		writeDefaultBlockSection();
		// entities
		writeEntitesSection(qa10LayerCollection.getEntities());

		dxfWriter.write("0");
		dxfWriter.newLine();
		dxfWriter.write("EOF");
		dxfWriter.newLine();

		dxfWriter.flush();
		dxfWriter.close();
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

	private void writeEntitesSection(QA10Entities qa10Entities) throws IOException {

		dxfWriter.write(startSectionCode);
		dxfWriter.newLine();
		dxfWriter.write(section);
		dxfWriter.newLine();
		dxfWriter.write(sectionNameCode);
		dxfWriter.newLine();
		dxfWriter.write(entities);
		dxfWriter.newLine();

		Map<String, Object> variablesMap = qa10Entities.getValues();
		Iterator entitiesIt = variablesMap.keySet().iterator();
		while (entitiesIt.hasNext()) {
			String layerId = (String) entitiesIt.next();
			List<LinkedHashMap<String, Object>> variables = (List<LinkedHashMap<String, Object>>) variablesMap
					.get(layerId);
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
		}
		dxfWriter.write(endSectionCode);
		dxfWriter.newLine();
		dxfWriter.write(endSection);
		dxfWriter.newLine();
	}

	private void writeDefaultTablesSection(QA10Tables qa10Tables) throws IOException {

		dxfWriter.write(startSectionCode);
		dxfWriter.newLine();
		dxfWriter.write(section);
		dxfWriter.newLine();
		dxfWriter.write(sectionNameCode);
		dxfWriter.newLine();
		dxfWriter.write(tables);
		dxfWriter.newLine();

		writeDefaultLTYPE(qa10Tables.getLineTypes());
		writeDefaultLAYER(qa10Tables.getLayers());
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

	private void writeDefaultLAYER(Map<String, Object> layers) throws IOException {

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
