package com.git.gdsbuilder.FileRead.ngi.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.git.gdsbuilder.type.qa20.collection.QA20LayerCollection;
import com.git.gdsbuilder.type.qa20.feature.QA20Feature;
import com.git.gdsbuilder.type.qa20.feature.QA20FeatureList;
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

	public void test(QA20LayerCollection qa20LayerCollection) throws IOException {

		String collectionId = qa20LayerCollection.getId();
		String fileName = "D:\\err_" + collectionId + ".ngi";
		this.ngiWriter = new BufferedWriter(new FileWriter(fileName, true));
		QA20LayerList layerList = qa20LayerCollection.getQa20LayerList();
		for (int i = 0; i < layerList.size(); i++) {
			QA20Layer layer = layerList.get(i);
			ngiWriter.write(layerStartTg);
			writeNgiLayer(i, layer);
			ngiWriter.write(layerEndTg);
			System.out.println("");
		}
		ngiWriter.close();
		System.out.println("");
	}

	private void writeNgiLayer(int i, QA20Layer layer) throws IOException {
		// layerID
		ngiWriter.write("$LAYER_ID");
		ngiWriter.write(i);
		ngiWriter.write("$END");
		ngiWriter.write("$LAYER_NAME");
		ngiWriter.write(layer.getLayerName());
		ngiWriter.write("$END");
		writeNgiHeader(layer.getNgiHeader());
		writeNgiData(layer.getFeatures());
	}

	private void writeNgiHeader(NGIHeader ngiHeader) throws IOException {

		ngiWriter.write(headerTg);
		ngiWriter.write("$VERSION");
		ngiWriter.write(ngiHeader.getVersion());
		ngiWriter.write("$END");
		ngiWriter.write("$POINT_REPRESENT");
		List<String> styles = ngiHeader.getPoint_represent();
		for (int i = 0; i < styles.size(); i++) {
			ngiWriter.write(i + " " + styles.get(i));
		}
		ngiWriter.write("$END");
		ngiWriter.write(endTg);
	}

	private void writeNgiData(QA20FeatureList qa20FeatureList) throws IOException {

		ngiWriter.write(dataTg);
		for (int i = 0; i < qa20FeatureList.size(); i++) {
			QA20Feature feature = qa20FeatureList.get(i);
			ngiWriter.write("$RECORD " + i);
			ngiWriter.write(feature.getFeatureType());
			Geometry geom = feature.getGeom();
			Coordinate coor = geom.getCoordinate();
			ngiWriter.write(coor.x + " " + coor.y);
		}
	}
}
