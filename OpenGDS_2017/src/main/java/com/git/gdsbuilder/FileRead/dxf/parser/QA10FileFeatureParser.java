package com.git.gdsbuilder.FileRead.dxf.parser;

import org.geotools.feature.SchemaException;
import org.kabeja.dxf.DXFArc;
import org.kabeja.dxf.DXFCircle;
import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFInsert;
import org.kabeja.dxf.DXFLWPolyline;
import org.kabeja.dxf.DXFLine;
import org.kabeja.dxf.DXFPolyline;
import org.kabeja.dxf.DXFSolid;
import org.kabeja.dxf.DXFText;

import com.git.gdsbuilder.type.qa10.feature.QA10Feature;
import com.git.gdsbuilder.type.qa10.feature.style.DTDXFArcStyle;
import com.git.gdsbuilder.type.qa10.feature.style.DTDXFCircleStyle;
import com.git.gdsbuilder.type.qa10.feature.style.DTDXFInsertStyle;
import com.git.gdsbuilder.type.qa10.feature.style.DTDXFLWPolylineStyle;
import com.git.gdsbuilder.type.qa10.feature.style.DTDXFPolylineStyle;
import com.git.gdsbuilder.type.qa10.feature.style.DTDXFStyle;
import com.git.gdsbuilder.type.qa10.feature.style.DTDXFTextSyle;
import com.vividsolutions.jts.geom.Geometry;

public class QA10FileFeatureParser {

	public static QA10Feature parseDTLineFeaeture(DXFEntity dxfEntity) throws SchemaException {

		Geometry geom = null;
		DTDXFStyle style = null;
		String entityType = dxfEntity.getType();
		if (entityType.equals("LINE")) {
			DXFLine dxfLine = (DXFLine) dxfEntity;
			// attribute
			// style = QA10FileStyleParser.parseLineStyle(dxfEntity);
			// gemo
			geom = QA10FileGeomParser.parseDTLine(dxfLine.getStartPoint(), dxfLine.getEndPoint());

			String entityID = dxfLine.getID();
			QA10Feature dxfFeature = new QA10Feature(entityID);
			dxfFeature.setFeatureType(entityType);
			dxfFeature.setGeom(geom);
			// dxfFeature.setProperties(EnDXFCommon.getProperties(style));
			return dxfFeature;
		} else {
			return null;
		}
	}

	public static QA10Feature parseDTPolylineFeature(DXFEntity dxfEntity) {

		Geometry geom = null;
		DTDXFPolylineStyle style = null;
		String entityType = dxfEntity.getType();
		if (entityType.equals("POLYLINE")) {
			DXFPolyline dxfPolyline = (DXFPolyline) dxfEntity;
			// attribute
			// style = QA10FileStyleParser.parsePolylinetStyle(dxfPolyline);
			// gemo
			boolean flag = dxfPolyline.isClosed();
			geom = QA10FileGeomParser.parseDTLineString(flag, dxfPolyline.getVertexIterator(),
					dxfPolyline.getVertexCount());
			String entityID = dxfPolyline.getID();
			QA10Feature dxfFeature = new QA10Feature(entityID);
			dxfFeature.setFeatureType(entityType);
			dxfFeature.setGeom(geom);
			// dxfFeature.setProperties(EnDXFPolyline.getProperties(style));
			return dxfFeature;
		} else {
			return null;
		}
	}

	public static QA10Feature parseDTLWPolylineFeature(DXFEntity dxfEntity) {

		Geometry geom = null;
		DTDXFLWPolylineStyle style = null;
		String entityType = dxfEntity.getType();
		if (entityType.equals("LWPOLYLINE")) {
			DXFLWPolyline dxfLwPolyline = (DXFLWPolyline) dxfEntity;
			// attribute
			// style = QA10FileStyleParser.parseLWPolylinetStyle(dxfEntity);
			// gemo

			double elevation = dxfLwPolyline.getElevation();
			boolean flag = dxfLwPolyline.isClosed();
			geom = QA10FileGeomParser.parseDTLineString(flag, dxfLwPolyline.getVertexIterator(),
					dxfLwPolyline.getVertexCount());
			String entityID = dxfLwPolyline.getID();
			QA10Feature dxfFeature = new QA10Feature(entityID);
			dxfFeature.setFeatureType(entityType);
			dxfFeature.setGeom(geom);
			dxfFeature.setElevation(elevation);
			// dxfFeature.setProperties(EnDXFPolyline.getProperties(style));
			return dxfFeature;
		} else {
			return null;
		}
	}

	public static QA10Feature parseDTInsertFeature(DXFEntity dxfEntity) {

		Geometry geom = null;
		DTDXFInsertStyle style = null;
		String entityType = dxfEntity.getType();
		if (entityType.equals("INSERT")) {
			DXFInsert dxfInsert = (DXFInsert) dxfEntity;
			// attribute
			// style = QA10FileStyleParser.parseInsertStyle(dxfEntity);
			// gemo
			geom = QA10FileGeomParser.parseDTPoint(dxfInsert.getPoint());

			String entityID = dxfInsert.getID();
			QA10Feature dxfFeature = new QA10Feature(entityID);
			dxfFeature.setFeatureType(entityType);
			dxfFeature.setGeom(geom);
			// dxfFeature.setProperties(EnDXFInsert.getProperties(style));
			return dxfFeature;
		} else {
			return null;
		}
	}

	public static QA10Feature parseDTCircleFeature(DXFEntity dxfEntity) {

		Geometry geom = null;
		DTDXFCircleStyle style = null;
		String entityType = dxfEntity.getType();
		if (entityType.equals("CIRCLE")) {
			DXFCircle dxfCircle = (DXFCircle) dxfEntity;
			// attribute
			// style = QA10FileStyleParser.parseCircleStyle(dxfCircle);
			// gemo
			geom = QA10FileGeomParser.parseDTCircle(dxfCircle.getCenterPoint(), dxfCircle.getRadius());
			String entityID = dxfCircle.getID();
			QA10Feature dxfFeature = new QA10Feature(entityID);
			dxfFeature.setFeatureType(entityType);
			dxfFeature.setGeom(geom);
			// dxfFeature.setProperties(EnDXFCircle.getProperties(style));
			return dxfFeature;
		} else {
			return null;
		}

	}

	public static QA10Feature parseDTSolidFeature(DXFEntity dxfEntity) {

		Geometry geom = null;
		DTDXFStyle style = null;
		String entityType = dxfEntity.getType();
		if (entityType.equals("SOLID")) {
			DXFSolid dxfSolid = (DXFSolid) dxfEntity;
			// attribute
			// style = QA10FileStyleParser.parseSolidStyle(dxfEntity);
			// gemo
			geom = QA10FileGeomParser.parseDTPolygon(dxfSolid.getPoint1(), dxfSolid.getPoint2(), dxfSolid.getPoint3(),
					dxfSolid.getPoint4());

			String entityID = dxfSolid.getID();
			QA10Feature dxfFeature = new QA10Feature(entityID);
			dxfFeature.setFeatureType(entityType);
			dxfFeature.setGeom(geom);
			// dxfFeature.setProperties(EnDXFCommon.getProperties(style));
			return dxfFeature;
		} else {
			return null;
		}

	}

	public static QA10Feature parseDTTextFeature(DXFEntity dxfEntity) {

		Geometry geom = null;
		DTDXFTextSyle style = null;
		String entityType = dxfEntity.getType();
		if (entityType.equals("TEXT")) {
			DXFText dxfText = (DXFText) dxfEntity;
			// attribute
			// style = QA10FileStyleParser.parseTextStyle(dxfText);
			// gemo
			geom = QA10FileGeomParser.parseDTPoint(dxfText.getInsertPoint());

			String entityID = dxfText.getID();
			QA10Feature dxfFeature = new QA10Feature(entityID);
			dxfFeature.setFeatureType(entityType);
			dxfFeature.setGeom(geom);
			dxfFeature.setTextValue(dxfText.getText());
			// dxfFeature.setProperties(EnDXFText.getProperties(style));
			return dxfFeature;
		} else {
			return null;
		}
	}

	public static QA10Feature parseDTArcFeature(DXFEntity dxfEntity) {

		Geometry geom = null;
		DTDXFArcStyle style = null;
		String entityType = dxfEntity.getType();
		if (entityType.equals("ARC")) {
			DXFArc dxfArc = (DXFArc) dxfEntity;
			// attribute
			// style = QA10FileStyleParser.parseArcStyle(dxfArc);
			// gemo
			geom = QA10FileGeomParser.parseDTArc(dxfArc.getCenterPoint(), dxfArc.getRadius(), dxfArc.getStartAngle(),
					dxfArc.getTotalAngle());

			String entityID = dxfArc.getID();
			QA10Feature dxfFeature = new QA10Feature(entityID);
			dxfFeature.setFeatureType(entityType);
			dxfFeature.setGeom(geom);
			// dxfFeature.setProperties(EnDXFArc.getProperties(style));
			return dxfFeature;
		} else {
			return null;
		}
	}

}
