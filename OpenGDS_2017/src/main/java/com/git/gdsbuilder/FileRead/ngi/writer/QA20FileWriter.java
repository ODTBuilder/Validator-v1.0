package com.git.gdsbuilder.FileRead.ngi.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.git.gdsbuilder.type.qa20.collection.QA20LayerCollection;
import com.git.gdsbuilder.type.qa20.feature.QA20Feature;
import com.git.gdsbuilder.type.qa20.feature.QA20FeatureList;
import com.git.gdsbuilder.type.qa20.header.NDAField;
import com.git.gdsbuilder.type.qa20.header.NDAHeader;
import com.git.gdsbuilder.type.qa20.header.NGIHeader;
import com.git.gdsbuilder.type.qa20.layer.QA20Layer;
import com.git.gdsbuilder.type.qa20.layer.QA20LayerList;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

public class QA20FileWriter {

	protected static String layerStartTg = "<LAYER_START>";
	protected static String layerEndTg = "<LAYER_END>";
	protected static String headerTg = "<HEADER>";
	protected static String dataTg = "<DATA>";
	protected static String endTg = "<END>";

	BufferedWriter ngiWriter;
	BufferedWriter ndaWriter;

	public void writeNGIFile(QA20LayerCollection qa20LayerCollection) throws IOException {

		String collectionId = qa20LayerCollection.getId();
		String ngiFileRoot = "D:\\err_" + collectionId + ".ngi";
		String ndaFileRoot = "D:\\err_" + collectionId + ".nda";
		this.ngiWriter = new BufferedWriter(new FileWriter(ngiFileRoot, true));
		this.ndaWriter = new BufferedWriter(new FileWriter(ndaFileRoot, true));

		QA20LayerList layerList = qa20LayerCollection.getQa20LayerList();
		for (int i = 0; i < layerList.size(); i++) {
			QA20Layer layer = layerList.get(i);
			writeNgiFile(i, layer);
			NDAHeader ndaHeader = layer.getNdaHeader();
			if (ndaHeader != null) {
				writeNdaFile(i, layer, ndaHeader);
			}
		}
	}

	private void writeNdaFile(int i, QA20Layer layer, NDAHeader ndaHeader) throws IOException {

		ndaWriter.write(layerStartTg);
		ndaWriter.newLine();
		writeNdaLayer(i, layer, ndaHeader);
		ndaWriter.write(layerEndTg);
		ndaWriter.flush();
		ndaWriter.close();
	}

	private void writeNdaLayer(int i, QA20Layer layer, NDAHeader ndaHeader) throws IOException {

		ndaWriter.write("$LAYER_ID");
		ndaWriter.newLine();
		ndaWriter.write(String.valueOf(i + 1));
		ndaWriter.newLine();
		ndaWriter.write("$END");
		ndaWriter.newLine();
		ndaWriter.write("$LAYER_NAME");
		ndaWriter.newLine();
		ndaWriter.write(layer.getLayerName());
		ndaWriter.newLine();
		ndaWriter.write("$END");
		ndaWriter.newLine();
		writeNdaHeader(ndaHeader);
		writeNdaData(layer.getFeatures(), ndaHeader);
	}

	private void writeNdaData(QA20FeatureList features, NDAHeader ndaHeader) throws IOException {

		ndaWriter.write(dataTg);
		ndaWriter.newLine();

		List<NDAField> fields = ndaHeader.getAspatial_field_def();
		for (int i = 0; i < features.size(); i++) {
			QA20Feature feature = features.get(i);
			String featureID = feature.getFeatureID();
			ndaWriter.write("$RECORD " + featureID);
			ndaWriter.newLine();

			String fieldStr = "";
			HashMap<String, Object> properties = feature.getProperties();
			for (int j = 0; j < fields.size(); j++) {
				NDAField field = fields.get(j);
				String fieldName = field.getFieldName();
				String type = field.getType();
				if (type.equals("NUMERIC")) {
					fieldStr += properties.get(fieldName);
				} else if (type.equals("STRING")) {
					fieldStr += "\"" + properties.get(fieldName) + "\"";
				}
				properties.get(fieldName);
				if (j != fields.size() - 1) {
					fieldStr += ",";
				}
			}
			ndaWriter.write(fieldStr);
			ndaWriter.newLine();
		}
		ndaWriter.write(endTg);
		ndaWriter.newLine();
	}

	private void writeNdaHeader(NDAHeader ndaHeader) throws IOException {

		ndaWriter.write(headerTg);
		ndaWriter.newLine();
		ndaWriter.write("$VERSION");
		ndaWriter.newLine();
		ndaWriter.write(ndaHeader.getVersion());
		ndaWriter.newLine();
		ndaWriter.write("$END");
		ndaWriter.newLine();
		ndaWriter.write("$ASPATIAL_FIELD_DEF");
		ndaWriter.newLine();
		writeAspatialFieldDef(ndaHeader.getAspatial_field_def());
		ndaWriter.write("$END");
		ndaWriter.newLine();
		ndaWriter.write(endTg);
		ndaWriter.newLine();
	}

	private void writeAspatialFieldDef(List<NDAField> fields) throws IOException {

		for (int i = 0; i < fields.size(); i++) {

			NDAField field = fields.get(i);
			String fieldName = field.getFieldName();
			String type = field.getType();
			String size = field.getSize();
			String decimal = field.getDecimal();
			String isUniqueStr = "";
			boolean isUnique = field.isUnique();
			if (isUnique) {
				isUniqueStr = "TRUE";
			} else {
				isUniqueStr = "FALSE";
			}
			String fieldStr = "ATTRIB(" + "\"" + fieldName + "\"" + ", " + type + ", " + size + ", " + decimal + ", "
					+ isUniqueStr + ")";
			ndaWriter.write(fieldStr);
			ndaWriter.newLine();
		}
	}

	private void writeNgiFile(int i, QA20Layer layer) throws IOException {

		ngiWriter.write(layerStartTg);
		ngiWriter.newLine();
		writeNgiLayer(i, layer);
		ngiWriter.write(layerEndTg);

		ngiWriter.flush();
		ngiWriter.close();
	}

	private void writeNgiLayer(int i, QA20Layer layer) throws IOException {
		ngiWriter.write("$LAYER_ID");
		ngiWriter.newLine();
		ngiWriter.write(String.valueOf(i + 1));
		ngiWriter.newLine();
		ngiWriter.write("$END");
		ngiWriter.newLine();
		ngiWriter.write("$LAYER_NAME");
		ngiWriter.newLine();
		ngiWriter.write(layer.getLayerName());
		ngiWriter.newLine();
		ngiWriter.write("$END");
		ngiWriter.newLine();
		writeNgiHeader(layer.getNgiHeader());
		writeNgiData(layer.getFeatures());
	}

	private void writeNgiHeader(NGIHeader ngiHeader) throws IOException {

		ngiWriter.write(headerTg);
		ngiWriter.newLine();
		ngiWriter.write("$VERSION");
		ngiWriter.newLine();
		ngiWriter.write(ngiHeader.getVersion());
		ngiWriter.newLine();
		ngiWriter.write("$END");
		ngiWriter.newLine();
		ngiWriter.write("$POINT_REPRESENT");
		ngiWriter.newLine();
		List<String> styles = ngiHeader.getPoint_represent();
		for (int i = 0; i < styles.size(); i++) {
			ngiWriter.write(styles.get(i));
			ngiWriter.newLine();
		}
		ngiWriter.write("$END");
		ngiWriter.newLine();
		ngiWriter.write(endTg);
		ngiWriter.newLine();
	}

	private void writeNgiData(QA20FeatureList qa20FeatureList) throws IOException {

		ngiWriter.write(dataTg);
		ngiWriter.newLine();
		for (int i = 0; i < qa20FeatureList.size(); i++) {
			QA20Feature feature = qa20FeatureList.get(i);
			ngiWriter.write("$RECORD " + i);
			ngiWriter.newLine();
			ngiWriter.write(feature.getFeatureType());
			ngiWriter.newLine();
			Geometry geom = feature.getGeom();
			Coordinate coor = geom.getCoordinate();
			ngiWriter.write(coor.x + " " + coor.y);
			ngiWriter.newLine();
			ngiWriter.write(feature.getStyleID());
			ngiWriter.newLine();
		}
		ngiWriter.write(endTg);
		ngiWriter.newLine();
	}
}
