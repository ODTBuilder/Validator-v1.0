package com.git.gdsbuilder.FileRead.ngi.writer;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	protected static String ngiExe = ".ngi";
	protected static String ndaExe = ".nda";

	protected static String layerStartTg = "<LAYER_START>";
	protected static String layerEndTg = "<LAYER_END>";
	protected static String headerTg = "<HEADER>";
	protected static String dataTg = "<DATA>";
	protected static String endTg = "<END>";

	BufferedWriter ngiWriter;
	BufferedWriter ndaWriter;

	int recordCount = 1;

	public Map<String, Object> writeNGIFile(QA20LayerCollection qa20LayerCollection, QA20Layer errLayer)
			throws IOException {

		String collectionName = qa20LayerCollection.getFileName();
		String ngiFileRoot = "D:\\" + collectionName + ngiExe;
		String ndaFileRoot = "D:\\" + collectionName + ndaExe;

		Map<String, Object> ngiFileMap = new HashMap<String, Object>();
		ngiFileMap.put("fileName", collectionName);
		ngiFileMap.put("NgifileDir", ngiFileRoot);
		ngiFileMap.put("NdafileDir", ndaFileRoot);

		this.ngiWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ngiFileRoot), "euc-kr"));
		this.ndaWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ndaFileRoot), "euc-kr"));
		
		// fileLayer
		QA20LayerList layerList = qa20LayerCollection.getQa20LayerList();
		int size = layerList.size();
		for (int i = 0; i < size; i++) {
			QA20Layer layer = layerList.get(i);
			writeNgiFile(i, layer);
			NDAHeader ndaHeader = layer.getNdaHeader();
			if (ndaHeader != null) {
				writeNdaFile(i, layer, ndaHeader);
			}
		}
		// errLayer
		writeErrNgiFile(size, errLayer);
		NDAHeader ndaHeader = errLayer.getNdaHeader();
		if (ndaHeader != null) {
			writeErrNdaFile(size, errLayer, ndaHeader);
		}
		ngiWriter.close();
		ndaWriter.close();
		return ngiFileMap;
	}

	private void writeNdaFile(int i, QA20Layer layer, NDAHeader ndaHeader) throws IOException {

		ndaWriter.write(layerStartTg);
		ndaWriter.newLine();
		writeNdaLayer(i, layer, ndaHeader);
		ndaWriter.write(layerEndTg);
		ndaWriter.newLine();
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
			ndaWriter.write("$" + featureID);
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
		ngiWriter.newLine();
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
		ngiWriter.write("$GEOMETRIC_METADATA");
		ngiWriter.newLine();
		ngiWriter.write(ngiHeader.getGeometric_metadata());
		ngiWriter.newLine();
		ngiWriter.write(ngiHeader.getDim());
		ngiWriter.newLine();
		ngiWriter.write(ngiHeader.getBound());
		ngiWriter.newLine();
		ngiWriter.write("$END");
		ngiWriter.newLine();

		List<String> ptStyles = ngiHeader.getPoint_represent();
		if (ptStyles != null) {
			ngiWriter.write("$POINT_REPRESENT");
			ngiWriter.newLine();
			for (int i = 0; i < ptStyles.size(); i++) {
				int tmp = i + 1;
				ngiWriter.write(tmp + " " + ptStyles.get(i));
				ngiWriter.newLine();
			}
			ngiWriter.write("$END");
			ngiWriter.newLine();
		}
		List<String> lnStyles = ngiHeader.getLine_represent();
		if (lnStyles != null) {
			ngiWriter.write("$LINE_REPRESENT");
			ngiWriter.newLine();
			for (int i = 0; i < lnStyles.size(); i++) {
				int tmp = i + 1;
				ngiWriter.write(tmp + " " + lnStyles.get(i));
				ngiWriter.newLine();
			}
			ngiWriter.write("$END");
			ngiWriter.newLine();
		}
		List<String> pgStyles = ngiHeader.getRegion_represent();
		if (pgStyles != null) {
			ngiWriter.write("$REGION_REPRESENT");
			ngiWriter.newLine();
			for (int i = 0; i < pgStyles.size(); i++) {
				int tmp = i + 1;
				ngiWriter.write(tmp + " " + pgStyles.get(i));
				ngiWriter.newLine();
			}
			ngiWriter.write("$END");
			ngiWriter.newLine();
		}
		List<String> txtStyles = ngiHeader.getText_represent();
		if (txtStyles != null) {
			ngiWriter.write("$TEXT_REPRESENT");
			ngiWriter.newLine();
			for (int i = 0; i < txtStyles.size(); i++) {
				int tmp = i + 1;
				ngiWriter.write(tmp + " " + txtStyles.get(i));
				ngiWriter.newLine();
			}
			ngiWriter.write("$END");
			ngiWriter.newLine();
		}
		ngiWriter.write(endTg);
		ngiWriter.newLine();
	}

	private void writeNgiData(QA20FeatureList qa20FeatureList) throws IOException {

		ngiWriter.write(dataTg);
		ngiWriter.newLine();
		for (int i = 0; i < qa20FeatureList.size(); i++) {
			QA20Feature feature = qa20FeatureList.get(i);
			String featureID = feature.getFeatureID();
			ngiWriter.write("$" + featureID);
			ngiWriter.newLine();

			String textType = feature.getFeatureType();
			if (textType.equals("TEXT")) {
				String textValue = feature.getText();
				ngiWriter.write(feature.getFeatureType() + "(\"" + textValue + "\")");
				ngiWriter.newLine();
			} else {
				ngiWriter.write(feature.getFeatureType());
				ngiWriter.newLine();
			}
			String numparts = feature.getNumparts();
			if (numparts != null) {
				ngiWriter.write("NUMPARTS " + numparts);
				ngiWriter.newLine();
			}
			String coorSize = feature.getCoordinateSize();
			if (coorSize != null) {
				ngiWriter.write(coorSize);
				ngiWriter.newLine();
			}
			Geometry geom = feature.getGeom();
			Coordinate[] coors = geom.getCoordinates();
			int coorsLenth = coors.length;
			if (geom.getGeometryType().equals("Polygon")) {
				coorsLenth = coors.length - 1;
			}
			for (int j = 0; j < coorsLenth; j++) {
				Coordinate coor = coors[j];
				ngiWriter.write(coor.x + " " + coor.y);
				ngiWriter.newLine();
			}
			ngiWriter.write(feature.getStyleID());
			ngiWriter.newLine();
			recordCount++;
		}
		ngiWriter.write(endTg);
		ngiWriter.newLine();
	}

	private void writeErrNdaFile(int i, QA20Layer layer, NDAHeader ndaHeader) throws IOException {

		ndaWriter.write(layerStartTg);
		ndaWriter.newLine();
		writeErrNdaLayer(i, layer, ndaHeader);
		ndaWriter.write(layerEndTg);
	}

	private void writeErrNdaLayer(int i, QA20Layer layer, NDAHeader ndaHeader) throws IOException {

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
		writeErrNdaHeader(ndaHeader);
		writeErrNdaData(layer.getFeatures(), ndaHeader);
	}

	private void writeErrNdaData(QA20FeatureList features, NDAHeader ndaHeader) throws IOException {

		ndaWriter.write(dataTg);
		ndaWriter.newLine();

		List<NDAField> fields = ndaHeader.getAspatial_field_def();
		for (int i = 0; i < features.size(); i++) {
			QA20Feature feature = features.get(i);
			int tmp = i + recordCount;
			ndaWriter.write("$RECORD " + tmp);
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

	private void writeErrNdaHeader(NDAHeader ndaHeader) throws IOException {

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
		writeErrAspatialFieldDef(ndaHeader.getAspatial_field_def());
		ndaWriter.write("$END");
		ndaWriter.newLine();
		ndaWriter.write(endTg);
		ndaWriter.newLine();
	}

	private void writeErrAspatialFieldDef(List<NDAField> fields) throws IOException {

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

	private void writeErrNgiFile(int i, QA20Layer layer) throws IOException {

		ngiWriter.write(layerStartTg);
		ngiWriter.newLine();
		writeErrNgiLayer(i, layer);
		ngiWriter.write(layerEndTg);
	}

	private void writeErrNgiLayer(int i, QA20Layer layer) throws IOException {
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
		writeErrNgiHeader(layer.getNgiHeader());
		writeErrNgiData(layer.getFeatures());
	}

	private void writeErrNgiHeader(NGIHeader ngiHeader) throws IOException {

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

	private void writeErrNgiData(QA20FeatureList qa20FeatureList) throws IOException {

		ngiWriter.write(dataTg);
		ngiWriter.newLine();

		for (int i = 0; i < qa20FeatureList.size(); i++) {
			QA20Feature feature = qa20FeatureList.get(i);
			int tmp = i + recordCount;
			ngiWriter.write("$RECORD " + tmp);
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
