package com.git.gdsbuilder.FileRead.dxf.parser;

import org.kabeja.dxf.DXFArc;
import org.kabeja.dxf.DXFCircle;
import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFInsert;
import org.kabeja.dxf.DXFLWPolyline;
import org.kabeja.dxf.DXFPolyline;
import org.kabeja.dxf.DXFText;

import com.git.gdsbuilder.type.qa10.feature.style.DTDXFArcStyle;
import com.git.gdsbuilder.type.qa10.feature.style.DTDXFCircleStyle;
import com.git.gdsbuilder.type.qa10.feature.style.DTDXFInsertStyle;
import com.git.gdsbuilder.type.qa10.feature.style.DTDXFLWPolylineStyle;
import com.git.gdsbuilder.type.qa10.feature.style.DTDXFPolylineStyle;
import com.git.gdsbuilder.type.qa10.feature.style.DTDXFStyle;
import com.git.gdsbuilder.type.qa10.feature.style.DTDXFTextSyle;

public class QA10FileStyleParser {

	public static DTDXFStyle setCommonStyle(DXFEntity dxfEntity) {

		DTDXFStyle commStyle = null;
		String className = dxfEntity.getClass().getSimpleName();

		if (className.equals("DXFPolyline")) {
			commStyle = new DTDXFPolylineStyle();
		} else if (className.equals("DXFLWPolyline")) {
			commStyle = new DTDXFLWPolylineStyle();
		} else if (className.equals("DXFInsert")) {
			commStyle = new DTDXFInsertStyle();
		} else if (className.equals("DXFText")) {
			commStyle = new DTDXFTextSyle();
		} else if (className.equals("DXFCircle")) {
			commStyle = new DTDXFCircleStyle();
		} else if (className.equals("DXFLine")) {
			commStyle = new DTDXFStyle();
		} else if (className.equals("DXFSolid")) {
			commStyle = new DTDXFStyle();
		} else if (className.equals("DXFArc")) {
			commStyle = new DTDXFArcStyle();
		}
		commStyle.setLayerID(dxfEntity.getLayerName());
		commStyle.setFeatureID(dxfEntity.getID());
		// commStyle.setVisibile(dxfEntity.isVisibile());
		commStyle.setLineType(dxfEntity.getLineType());
		commStyle.setFlags(dxfEntity.getFlags());
		commStyle.setBlock(dxfEntity.isBlockEntity());
		commStyle.setLinetypeScaleFactor(dxfEntity.getLinetypeScaleFactor());
		commStyle.setColor(dxfEntity.getColor());
		commStyle.setColorRGB(dxfEntity.getColorRGB());
		commStyle.setLineWeight(dxfEntity.getLineWeight());
		commStyle.setTransparency(dxfEntity.getTransparency());
		commStyle.setThickness(dxfEntity.getThickness());

		return commStyle;
	}

	public static DTDXFTextSyle parseTextStyle(DXFEntity dxfEntity) {

		DXFText dxfText = (DXFText) dxfEntity;
		DTDXFTextSyle dtStyle = (DTDXFTextSyle) setCommonStyle(dxfEntity);

		dtStyle.setText(dxfText.getText());
		dtStyle.setTextHeight(dxfText.getHeight());
		dtStyle.setTextRotation(dxfText.getRotation());
		dtStyle.setxScale(dxfText.getScaleX());
		dtStyle.setAngle(dxfText.getObliqueAngle());
		dtStyle.setFlag(dxfText.getFlags());
		dtStyle.setTextStyle(dxfText.getTextStyle());

		return dtStyle;

	}

	public static DTDXFPolylineStyle parsePolylinetStyle(DXFEntity dxfEntity) {

		DXFPolyline dxfPolyline = (DXFPolyline) dxfEntity;
		DTDXFPolylineStyle dtStyle = (DTDXFPolylineStyle) setCommonStyle(dxfEntity);
		dtStyle.setColumns(dxfPolyline.getColumns());
		dtStyle.setEndWidth(dxfPolyline.getEndWidth());
		dtStyle.setFeatureID(dxfPolyline.getID());
		dtStyle.setRows(dxfPolyline.getRows());
		dtStyle.setStartWidth(dxfPolyline.getStartWidth());
		dtStyle.setSurefaceDensityColumns(dxfPolyline.getSurefaceDensityColumns());
		dtStyle.setSurefaceDensityRows(dxfPolyline.getSurefaceDensityRows());
		dtStyle.setSurefaceType(dxfPolyline.getSurefaceType());

		return dtStyle;
	}

	public static DTDXFLWPolylineStyle parseLWPolylinetStyle(DXFEntity dxfEntity) {

		DXFLWPolyline dxflwPolyline = (DXFLWPolyline) dxfEntity;
		DTDXFLWPolylineStyle dtStyle = (DTDXFLWPolylineStyle) setCommonStyle(dxfEntity);

		dtStyle.setColumns(dxflwPolyline.getColumns());
		dtStyle.setConstantwidth(dxflwPolyline.getContstantWidth());
		dtStyle.setEndWidth(dxflwPolyline.getEndWidth());
		dtStyle.setFeatureID(dxflwPolyline.getID());
		dtStyle.setRows(dxflwPolyline.getRows());
		dtStyle.setStartWidth(dxflwPolyline.getStartWidth());
		dtStyle.setSurefaceDensityColumns(dxflwPolyline.getSurefaceDensityColumns());
		dtStyle.setSurefaceDensityRows(dxflwPolyline.getSurefaceDensityRows());
		dtStyle.setSurefaceType(dxflwPolyline.getSurefaceType());
		dtStyle.setConstantwidth(dxflwPolyline.getContstantWidth());
		dtStyle.setElevation(dxflwPolyline.getElevation());

		return dtStyle;

	}

	public static DTDXFInsertStyle parseInsertStyle(DXFEntity dxfEntity) {

		DXFInsert dxfInsert = (DXFInsert) dxfEntity;
		DTDXFInsertStyle dtStyle = (DTDXFInsertStyle) setCommonStyle(dxfEntity);

		dtStyle.setScale_x(dxfInsert.getScaleX());
		dtStyle.setScale_y(dxfInsert.getScaleY());
		dtStyle.setScale_z(dxfInsert.getScaleZ());
		dtStyle.setRotate(dxfInsert.getRotate());
		dtStyle.setRows(dxfInsert.getRows());
		dtStyle.setColumns(dxfInsert.getColumns());
		dtStyle.setRow_spacing(dxfInsert.getRowSpacing());
		dtStyle.setColumn_spacing(dxfInsert.getColumnSpacing());
		dtStyle.setBlockID(dxfInsert.getBlockID());

		return dtStyle;
	}

	public static DTDXFCircleStyle parseCircleStyle(DXFEntity dxfEntity) {

		DXFCircle dxfCircle = (DXFCircle) dxfEntity;
		DTDXFCircleStyle dtStyle = (DTDXFCircleStyle) setCommonStyle(dxfEntity);

		dtStyle.setRadius(dxfCircle.getRadius());

		return dtStyle;
	}

	public static DTDXFStyle parseLineStyle(DXFEntity dxfEntity) {

		return setCommonStyle(dxfEntity);
	}

	public static DTDXFStyle parseSolidStyle(DXFEntity dxfEntity) {

		return setCommonStyle(dxfEntity);
	}

	public static DTDXFArcStyle parseArcStyle(DXFArc dxfEntity) {

		DXFArc dxfArc = (DXFArc) dxfEntity;
		DTDXFArcStyle dtStyle = (DTDXFArcStyle) setCommonStyle(dxfEntity);

		dtStyle.setCounterclockwise(dxfArc.isCounterClockwise());
		dtStyle.setEnd_angle(dxfArc.getEndAngle());
		dtStyle.setRadius(dxfArc.getRadius());
		dtStyle.setStart_angle(dxfArc.getStartAngle());

		return dtStyle;
	}
}
