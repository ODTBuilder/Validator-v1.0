/**
 * 레이어 스타일 패널 객체를 정의한다.
 * 
 * @class gb.style.LayerStyle
 * @memberof gb.style
 * @param {Object}
 *            obj - 생성자 옵션을 담은 객체
 * @param {Boolean}
 *            obj.autoOpen - 패널을 선언과 동시에 표출 할 것인지 선택
 * @param {ol.layer.Base}
 *            obj.layer - 스타일을 변경할 레이어 객체
 * @version 0.01
 * @author yijun.so
 * @date 2018. 06.04
 */
var gb;
if (!gb)
	gb = {};
if (!gb.style)
	gb.style = {};
gb.style.LayerStyle = function(obj) {
	var that = this;
	obj.width = 192;
	obj.height = 420;
	obj.positionX = 380;
	obj.positionY = 535;
	gb.panel.Base.call(this, obj);
	var options = obj ? obj : {};
	this.layer = options.layer instanceof ol.layer.Base ? options.layer : undefined;
	this.geom = undefined;
	this.layerName = $("<div>").text("Choose a layer").css({
		"margin-bottom" : "8px",
		"overflow-x" : "hidden",
		"text-overflow" : "ellipsis",
		"font-size" : "1.1em",
		"font-weight" : "bold"
	});

	this.lineLabel = $("<div>").text("Outline");
	this.linePicker = $("<input>").attr({
		"type" : "text"
	});
	this.lineContent = $("<div>").append(this.linePicker);
	this.lineArea = $("<div>").append(this.lineLabel).append(this.lineContent).css({
		"margin-bottom" : "5px"
	});

	this.fillLabel = $("<div>").text("Fill");
	this.fillPicker = $("<input>").attr({
		"type" : "text"
	});
	this.fillContent = $("<div>").append(this.fillPicker);
	this.fillArea = $("<div>").append(this.fillLabel).append(this.fillContent).css({
		"margin-bottom" : "5px"
	});

	this.widthLabel = $("<div>").text("Width");
	this.widthInput = $("<input>").attr({
		"type" : "number",
		"min" : "1"
	}).css({
		"width" : "175px",
		"height" : "26px"
	}).val("1");
	this.widthContent = $("<div>").append(this.widthInput);
	this.widthArea = $("<div>").append(this.widthLabel).append(this.widthContent).css({
		"margin-bottom" : "5px"
	});

	this.radLabel = $("<div>").text("Radius");
	this.radInput = $("<input>").attr({
		"type" : "number",
		"min" : "0"
	}).css({
		"width" : "175px",
		"height" : "26px"
	}).val("5");
	this.radContent = $("<div>").append(this.radInput);
	this.radArea = $("<div>").append(this.radLabel).append(this.radContent).css({
		"margin-bottom" : "5px"
	});

	this.outlineLabel = $("<div>").text("Outline Style");
	this.outline1 = $("<option>").attr({
		"value" : "outline1"
	}).text("─────────────────────");
	this.outline2 = $("<option>").attr({
		"value" : "outline2",
		"dash" : "5,5"
	}).text("- - - - - - - - - - - - - - - - - - - - - - - - -");
	this.outline3 = $("<option>").attr({
		"value" : "outline3",
		"dash" : "8,5,1,5"
	}).text("─ · ─ · ─ · ─ · ─ · ─ · ─ · ─ · ─ · ─ ·");
	this.outline4 = $("<option>").attr({
		"value" : "outline4",
		"dash" : "8,5,1,5,1,5"
	}).text("─ · · ─ · · ─ · · ─ · · ─ · · ─ · · ─ · · ─");
	this.outlineInput = $("<select>").append(this.outline1).append(this.outline2).append(this.outline3).append(this.outline4).css({
		"width" : "175px",
		"height" : "26px"
	});
	this.outlineContent = $("<div>").append(this.outlineInput);
	this.outlineArea = $("<div>").append(this.outlineLabel).append(this.outlineContent).css({
		"margin-bottom" : "5px"
	});

	this.opaFigure = $("<span>");
	this.opaLabel = $("<div>").append("Opacity: ").append(this.opaFigure);
	this.opaPicker = $("<input>").attr({
		"type" : "range",
		"min" : "0",
		"max" : "1",
		"step" : "0.01"
	}).val("1");
	$(this.opaPicker).on("input", function() {
		$(that.opaFigure).empty();
		$(that.opaFigure).text($(this).val());
	});
	this.opaContent = $("<div>").append(this.opaPicker);
	this.opaArea = $("<div>").append(this.opaLabel).append(this.opaContent).css({
		"margin-bottom" : "5px"
	});

	this.saveBtn = $("<button>").text("OK").addClass("gb-button").addClass("gb-button-primary").click(function() {
		that.applyStyle();
	});
	this.btnArea = $("<div>").append(this.saveBtn).css({
		"float" : "right",
		"margin" : "5px",
		"position" : "absolute",
		"bottom" : 0,
		"right" : 0
	});
	$(this.panelBody).append(this.layerName).append(this.fillArea).append(this.lineArea).append(this.widthArea).append(this.radArea)
			.append(this.outlineArea).append(this.opaArea).append(this.btnArea);
	$(this.panelBody).css({
		"padding" : "8px"
	});
	$("body").append(this.panel);

	$(this.linePicker).spectrum({
		showInput : true,
		showAlpha : true,
		preferredFormat : "rgb"
	});

	$(this.fillPicker).spectrum({
		showInput : true,
		showAlpha : true,
		preferredFormat : "rgb"
	});

	$(this.lineContent).find(".sp-replacer").css({
		"width" : "175px"
	});
	$(this.lineContent).find(".sp-preview").css({
		"width" : "149px"
	});
	$(this.fillContent).find(".sp-replacer").css({
		"width" : "175px"
	});
	$(this.fillContent).find(".sp-preview").css({
		"width" : "149px"
	});

	var temp = this.getLayer();
	if (temp instanceof ol.layer.Base) {
		this.setLayer(temp);
	}
};
gb.style.LayerStyle.prototype = Object.create(gb.panel.Base.prototype);
gb.style.LayerStyle.prototype.constructor = gb.style.LayerStyle;

/**
 * 선택한 스타일을 레이어에 적용 시킨다.
 * 
 * @method gb.style.LayerStyle#applyStyle
 */
gb.style.LayerStyle.prototype.applyStyle = function() {
	var layer = this.getLayer();
	if (layer instanceof ol.layer.Vector) {
		var style = new ol.style.Style({
			"fill" : this.geom === "Polygon" || this.geom === "MultiPolygon" ? new ol.style.Fill({
				"color" : [ $(this.fillPicker).spectrum("get").toRgb().r, $(this.fillPicker).spectrum("get").toRgb().g,
						$(this.fillPicker).spectrum("get").toRgb().b, $(this.fillPicker).spectrum("get").toRgb().a ]
			}) : undefined,
			"stroke" : this.geom === "Polygon" || this.geom === "MultiPolygon" || this.geom === "LineString"
					|| this.geom === "MultiLineString" ? new ol.style.Stroke({
				"color" : [ $(this.linePicker).spectrum("get").toRgb().r, $(this.linePicker).spectrum("get").toRgb().g,
						$(this.linePicker).spectrum("get").toRgb().b, $(this.linePicker).spectrum("get").toRgb().a ],
				"width" : parseFloat($(this.widthInput).val()),
				"lineDash" : $(this.outlineInput).find(":selected").attr("dash") !== undefined ? $(this.outlineInput).find(":selected")
						.attr("dash").split(",") : undefined,
				"lineCap" : "butt"
			}) : undefined,
			"image" : this.geom === "Point" || this.geom === "MultiPoint" ? new ol.style.Circle({
				"radius" : parseFloat($(this.radInput).val()),
				"fill" : new ol.style.Fill({
					"color" : [ $(this.fillPicker).spectrum("get").toRgb().r, $(this.fillPicker).spectrum("get").toRgb().g,
							$(this.fillPicker).spectrum("get").toRgb().b, $(this.fillPicker).spectrum("get").toRgb().a ]
				}),
				"stroke" : new ol.style.Stroke({
					"color" : [ $(this.linePicker).spectrum("get").toRgb().r, $(this.linePicker).spectrum("get").toRgb().g,
							$(this.linePicker).spectrum("get").toRgb().b, $(this.linePicker).spectrum("get").toRgb().a ],
					"width" : parseFloat($(this.widthInput).val()),
					"lineDash" : $(this.outlineInput).find(":selected").attr("dash") !== undefined ? $(this.outlineInput).find(":selected")
							.attr("dash").split(",") : undefined,
					"lineCap" : "butt"
				})
			}) : undefined
		});

		layer.setStyle(style);
	} else if (layer instanceof ol.layer.Tile) {
		if (this.geom === "Point" || this.geom === "MultiPoint") {
			var source = layer.getSource();
			console.log(source.getParams());
			var sldBody = '<StyledLayerDescriptor version="1.0.0"><UserLayer>';
			sldBody += '<Name>';
			sldBody += 'admin:geo_shp_37712012_A0030000_MULTIPOINT';
			sldBody += '</Name>';
			sldBody += '<UserStyle><Name>UserSelection</Name><FeatureTypeStyle>';
			sldBody += '<Rule><PointSymbolizer><Graphic>';
			sldBody += '<Mark>';
			sldBody += '<WellKnownName>circle</WellKnownName>';
			sldBody += '<Fill>';
			sldBody += '<CssParameter name="fill">#';
			sldBody += this.hexFromRGB($(this.fillPicker).spectrum("get").toRgb().r, $(this.fillPicker).spectrum("get").toRgb().g, $(
					this.fillPicker).spectrum("get").toRgb().b);
			sldBody += '</CssParameter>' + '<CssParameter name="fill-opacity">';
			sldBody += typeof $(this.fillPicker).spectrum("get").toRgb().a === "number" ? $(this.fillPicker).spectrum("get").toRgb().a : 1;
			sldBody += '</CssParameter>';
			sldBody += '</Fill>'
			sldBody += '<Stroke>';
			sldBody += '<CssParameter name="stroke">#';
			sldBody += this.hexFromRGB($(this.linePicker).spectrum("get").toRgb().r, $(this.linePicker).spectrum("get").toRgb().g, $(
					this.linePicker).spectrum("get").toRgb().b);
			sldBody += '</CssParameter>' + '<CssParameter name="stroke-opacity">';
			sldBody += typeof $(this.linePicker).spectrum("get").toRgb().a === "number" ? $(this.linePicker).spectrum("get").toRgb().a : 1
			sldBody += '</CssParameter>';
			sldBody += '<CssParameter name="stroke-width">' + parseFloat($(this.widthInput).val()) + '</CssParameter>'
			if ($(this.outlineInput).find(":selected").attr("dash") !== undefined) {
				sldBody += '<CssParameter name="stroke-dasharray">'
						+ $(this.outlineInput).find(":selected").attr("dash").replace(/,/gi, " ") + '</CssParameter>';
			}
			sldBody += '</Stroke>';
			sldBody += '</Mark>'
			sldBody += '<Size>' + parseFloat($(this.radInput).val()) * 2 + '</Size>'
			sldBody += '</Graphic>' + '</PointSymbolizer></Rule>'
			// '<Rule><LineSymbolizer><Stroke/></LineSymbolizer></Rule>'
			sldBody += '</FeatureTypeStyle></UserStyle></UserLayer></StyledLayerDescriptor>';
			console.log(sldBody);
			source.updateParams({
				'SLD_Body' : sldBody
			});
		} else if (this.geom === "LineString" || this.geom === "MultiLineString") {
			var source = layer.getSource();
			console.log(source.getParams());
			var sldBody = '<StyledLayerDescriptor version="1.0.0"><UserLayer>';
			sldBody += '<Name>';
			sldBody += 'admin:geo_shp_37712013_A0020000_MULTILINESTRING'
			sldBody += '</Name>'
			sldBody += '<UserStyle><Name>UserSelection</Name><FeatureTypeStyle>'
			sldBody += '<Rule><LineSymbolizer><Stroke>'
			sldBody += '<CssParameter name="stroke">#'
			sldBody += this.hexFromRGB($(this.linePicker).spectrum("get").toRgb().r, $(this.linePicker).spectrum("get").toRgb().g, $(
					this.linePicker).spectrum("get").toRgb().b)
					+ '</CssParameter>' + '<CssParameter name="stroke-opacity">'
			sldBody += typeof $(this.linePicker).spectrum("get").toRgb().a === "number" ? $(this.linePicker).spectrum("get").toRgb().a : 1
			sldBody += '</CssParameter>';
			sldBody += '<CssParameter name="stroke-width">' + parseFloat($(this.widthInput).val()) + '</CssParameter>'
			if ($(this.outlineInput).find(":selected").attr("dash") !== undefined) {
				sldBody += '<CssParameter name="stroke-dasharray">'
						+ $(this.outlineInput).find(":selected").attr("dash").replace(/,/gi, " ") + '</CssParameter>';
			}
			sldBody += '</Stroke>' + '</LineSymbolizer></Rule>'
			// '<Rule><LineSymbolizer><Stroke/></LineSymbolizer></Rule>'
			sldBody += '</FeatureTypeStyle></UserStyle></UserLayer></StyledLayerDescriptor>';
			console.log(sldBody);
			source.updateParams({
				'SLD_Body' : sldBody
			});
		} else if (this.geom === "Polygon" || this.geom === "MultiPolygon") {
			var source = layer.getSource();
			console.log(source.getParams());
			var sldBody = '<StyledLayerDescriptor version="1.0.0"><UserLayer>';
			sldBody += '<Name>';
			sldBody += 'admin:geo_shp_37712013_A0070000_MULTIPOLYGON'
			sldBody += '</Name>'
			sldBody += '<UserStyle><Name>UserSelection</Name><FeatureTypeStyle>'
			sldBody += '<Rule><PolygonSymbolizer><Fill>'
			sldBody += '<CssParameter name="fill">#'
			sldBody += this.hexFromRGB($(this.fillPicker).spectrum("get").toRgb().r, $(this.fillPicker).spectrum("get").toRgb().g, $(
					this.fillPicker).spectrum("get").toRgb().b)
			sldBody += '</CssParameter>' + '<CssParameter name="fill-opacity">'
			sldBody += typeof $(this.fillPicker).spectrum("get").toRgb().a === "number" ? $(this.fillPicker).spectrum("get").toRgb().a : 1
			sldBody += '</CssParameter></Fill>'
			sldBody += '<Stroke><CssParameter name="stroke">#'
			sldBody += this.hexFromRGB($(this.linePicker).spectrum("get").toRgb().r, $(this.linePicker).spectrum("get").toRgb().g, $(
					this.linePicker).spectrum("get").toRgb().b)
					+ '</CssParameter>' + '<CssParameter name="stroke-opacity">'
			sldBody += typeof $(this.linePicker).spectrum("get").toRgb().a === "number" ? $(this.linePicker).spectrum("get").toRgb().a : 1
			sldBody += '</CssParameter>' + '<CssParameter name="stroke-width">' + parseFloat($(this.widthInput).val()) + '</CssParameter>'
			if ($(this.outlineInput).find(":selected").attr("dash") !== undefined) {
				sldBody += '<CssParameter name="stroke-dasharray">'
						+ $(this.outlineInput).find(":selected").attr("dash").replace(/,/gi, " ") + '</CssParameter>';
			}
			sldBody += '</Stroke>' + '</PolygonSymbolizer></Rule>'
			// '<Rule><LineSymbolizer><Stroke/></LineSymbolizer></Rule>'
			sldBody += '</FeatureTypeStyle></UserStyle></UserLayer></StyledLayerDescriptor>';
			console.log(sldBody);
			source.updateParams({
				'SLD_Body' : sldBody
			});
		}
	}
	var opacity = parseFloat($(this.opaPicker).val());
	layer.setOpacity(opacity);
	this.close();
};
/**
 * RGB 색상코드를 16진수 색상코드로 변환한다.
 * 
 * @method gb.style.LayerStyle#hexFromRGB
 * @param {Number}
 *            r - r 계열 수치
 * @param {Number}
 *            g- g 계열 수치
 * @param {Number}
 *            b - b 계열 수치
 * @return {String} rgb코드
 */
gb.style.LayerStyle.prototype.hexFromRGB = function(r, g, b) {
	// 받아온 파라미터를 16진수로 변환한다
	var hex = [ r.toString(16), g.toString(16), b.toString(16) ];
	// 각각의 16진수의 길이가 1이라면 0을 추가해 2자리로 만든다
	$.each(hex, function(nr, val) {
		if (val.length === 1) {
			hex[nr] = "0" + val;
		}
	});
	// 16진수 rgb코드를 반환한다
	return hex.join("").toUpperCase();
};
/**
 * 16진수 색상코드를 RGB 색상코드로 변환한다.
 * 
 * @method gb.style.LayerStyle#decimalFromHex
 * @param {String}
 *            hex - 16진수 RGB 색상코드
 * @return {String} RGB 색상코드
 */
gb.style.LayerStyle.prototype.decimalFromHex = function(hex) {
	// r코드 획득
	var first = hex.substring(0, 2);
	var firstDeci = parseInt(first, 16);

	// g코드 획득
	var second = hex.substring(2, 4);
	var secondDeci = parseInt(second, 16);

	// b코드 획득
	var third = hex.substring(4, 6);
	var thirdDeci = parseInt(third, 16);

	// 합친후 반환
	var deciArr = [ firstDeci, secondDeci, thirdDeci ];
	return deciArr.join();
}
/**
 * SLD의 폴리곤 스타일 설정값을 추출한다.
 * 
 * @method gb.style.LayerStyle#parsePolygonSymbolizer
 * 
 * @param {String}
 *            sld - 레이어의 SLD
 * @return {Object} 스타일 정보를 가진 객체
 */
gb.style.LayerStyle.prototype.parsePolygonSymbolizer = function(sld) {
	var obj = {};
	var pgSym = sld.substring(sld.indexOf("<PolygonSymbolizer>") + 19, sld.indexOf("</PolygonSymbolizer>"));
	console.log(pgSym);
	var fill = pgSym.substring(pgSym.indexOf('<Fill>') + 6, pgSym.indexOf("</Fill>"));
	console.log(fill);
	var fillColor;
	if (fill.indexOf('<CssParameter name="fill">') !== -1) {
		fillColor = fill.substring(fill.indexOf('<CssParameter name="fill">') + 26, fill.indexOf("</CssParameter>"));
	}
	console.log(fillColor);
	var fillColorCode;
	var fillRGBColorCode;
	if (fillColor.indexOf("#") !== -1) {
		fillColorCode = fillColor.substring(fillColor.indexOf("#") + 1);
		fillRGBColorCode = this.decimalFromHex(fillColorCode);
		console.log(fillRGBColorCode);
		console.log(fillColorCode);
	}
	fill = fill.substring(fill.indexOf("</CssParameter>") + 15);
	console.log(fill);
	var fillOpacity;
	if (fill.indexOf('<CssParameter name="fill-opacity">') !== -1) {
		fillOpacity = fill.substring(fill.indexOf('<CssParameter name="fill-opacity">') + 34, fill.indexOf("</CssParameter>"));
	}
	console.log(fillOpacity);
	if (fillRGBColorCode !== undefined && fillOpacity !== undefined) {
		obj["fillRGBA"] = "rgba(" + fillRGBColorCode + "," + fillOpacity + ")";
	}
	pgSym = pgSym.substring(pgSym.indexOf("</Fill>") + 7);
	console.log(pgSym);
	var stroke = pgSym.substring(pgSym.indexOf('<Stroke>') + 8, pgSym.indexOf("</Stroke>"));
	console.log(stroke);
	var strokeColor;
	if (stroke.indexOf('<CssParameter name="stroke">') !== -1) {
		strokeColor = stroke.substring(stroke.indexOf('<CssParameter name="stroke">') + 28, stroke.indexOf("</CssParameter>"));
	}
	var strokeColorCode;
	var strokeRGBColorCode;
	if (strokeColor.indexOf("#") !== -1) {
		strokeColorCode = strokeColor.substring(strokeColor.indexOf("#") + 1);
		strokeRGBColorCode = this.decimalFromHex(strokeColorCode);
		console.log(strokeRGBColorCode);
		console.log(strokeColorCode);
	}
	stroke = stroke.substring(stroke.indexOf("</CssParameter>") + 15);
	console.log(stroke);
	var strokeOpacity;
	if (stroke.indexOf('<CssParameter name="stroke-opacity">') !== -1) {
		strokeOpacity = stroke.substring(stroke.indexOf('<CssParameter name="stroke-opacity">') + 36, stroke.indexOf("</CssParameter>"));
	}
	console.log(strokeOpacity);
	if (strokeRGBColorCode !== undefined && strokeOpacity !== undefined) {
		obj["strokeRGBA"] = "rgba(" + strokeRGBColorCode + "," + strokeOpacity + ")";
	}
	stroke = stroke.substring(stroke.indexOf("</CssParameter>") + 15);
	console.log(stroke);
	var strokeWidth;
	if (stroke.indexOf('<CssParameter name="stroke-width">') !== -1) {
		strokeWidth = stroke.substring(stroke.indexOf('<CssParameter name="stroke-width">') + 34, stroke.indexOf("</CssParameter>"));
	}
	if (strokeWidth !== undefined) {
		obj["strokeWidth"] = strokeWidth;
	}

	stroke = stroke.substring(stroke.indexOf("</CssParameter>") + 15);
	console.log(stroke);
	var strokeDashArray;
	if (stroke.indexOf('<CssParameter name="stroke-dasharray">') !== -1) {
		strokeDashArray = stroke
				.substring(stroke.indexOf('<CssParameter name="stroke-dasharray">') + 38, stroke.indexOf("</CssParameter>"));
	}
	if (strokeDashArray !== undefined) {
		strokeDashArray = strokeDashArray.replace(/ /gi, ",");
		obj["strokeDashArray"] = strokeDashArray;
	}

	pgSym = pgSym.substring(pgSym.indexOf("</Stroke>") + 9);
	console.log(pgSym);
	console.log(obj);
	return obj;
};
/**
 * SLD의 라인스트링 스타일 설정값을 추출한다.
 * 
 * @method gb.style.LayerStyle#parseLineStringSymbolizer
 * 
 * @param {String}
 *            sld - 레이어의 SLD
 */
gb.style.LayerStyle.prototype.parseLineSymbolizer = function(sld) {
	var obj = {};
	var lnSym = sld.substring(sld.indexOf("<LineSymbolizer>") + 16, sld.indexOf("</LineSymbolizer>"));
	console.log(lnSym);
	var stroke = lnSym.substring(lnSym.indexOf('<Stroke>') + 8, lnSym.indexOf("</Stroke>"));
	console.log(stroke);
	var strokeColor;
	if (stroke.indexOf('<CssParameter name="stroke">') !== -1) {
		strokeColor = stroke.substring(stroke.indexOf('<CssParameter name="stroke">') + 28, stroke.indexOf("</CssParameter>"));
	}
	var strokeColorCode;
	var strokeRGBColorCode;
	if (strokeColor.indexOf("#") !== -1) {
		strokeColorCode = strokeColor.substring(strokeColor.indexOf("#") + 1);
		strokeRGBColorCode = this.decimalFromHex(strokeColorCode);
		console.log(strokeRGBColorCode);
		console.log(strokeColorCode);
	}
	stroke = stroke.substring(stroke.indexOf("</CssParameter>") + 15);
	console.log(stroke);
	var strokeOpacity;
	if (stroke.indexOf('<CssParameter name="stroke-opacity">') !== -1) {
		strokeOpacity = stroke.substring(stroke.indexOf('<CssParameter name="stroke-opacity">') + 36, stroke.indexOf("</CssParameter>"));
	}
	console.log(strokeOpacity);
	if (strokeRGBColorCode !== undefined && strokeOpacity !== undefined) {
		obj["strokeRGBA"] = "rgba(" + strokeRGBColorCode + "," + strokeOpacity + ")";
	}
	stroke = stroke.substring(stroke.indexOf("</CssParameter>") + 15);
	console.log(stroke);
	var strokeWidth;
	if (stroke.indexOf('<CssParameter name="stroke-width">') !== -1) {
		strokeWidth = stroke.substring(stroke.indexOf('<CssParameter name="stroke-width">') + 34, stroke.indexOf("</CssParameter>"));
	}
	if (strokeWidth !== undefined) {
		obj["strokeWidth"] = strokeWidth;
	}

	stroke = stroke.substring(stroke.indexOf("</CssParameter>") + 15);
	console.log(stroke);
	var strokeDashArray;
	if (stroke.indexOf('<CssParameter name="stroke-dasharray">') !== -1) {
		strokeDashArray = stroke
				.substring(stroke.indexOf('<CssParameter name="stroke-dasharray">') + 38, stroke.indexOf("</CssParameter>"));
	}
	if (strokeDashArray !== undefined) {
		strokeDashArray = strokeDashArray.replace(/ /gi, ",");
		obj["strokeDashArray"] = strokeDashArray;
	}

	lnSym = lnSym.substring(lnSym.indexOf("</Stroke>") + 9);
	console.log(lnSym);
	console.log(obj);
	return obj;
};
/**
 * SLD의 포인트 스타일 설정값을 추출한다.
 * 
 * @method gb.style.LayerStyle#parsePointSymbolizer
 * 
 * @param {String}
 *            sld - 레이어의 SLD
 * @return {Object} 스타일 정보를 가진 객체
 */
gb.style.LayerStyle.prototype.parsePointSymbolizer = function(sld) {
	var obj = {};
	var ptSym = sld.substring(sld.indexOf("<PointSymbolizer>") + 17, sld.indexOf("</PointSymbolizer>"));
	console.log(ptSym);
	var graphic = ptSym.substring(ptSym.indexOf('<Graphic>') + 9, ptSym.indexOf("</Graphic>"));
	var size;
	if (graphic.indexOf('<Size>') !== -1) {
		size = graphic.substring(graphic.indexOf('<Size>') + 6, graphic.indexOf("</Size>"));
		if (size !== undefined) {
			obj["pointSize"] = size;
		}
	}
	var mark;
	if (graphic.indexOf('<Mark>') !== -1) {
		mark = graphic.substring(graphic.indexOf('<Mark>') + 6, graphic.indexOf("</Mark>"));
	}
	var fill;
	if (mark.indexOf('<Fill>') !== -1) {
		fill = mark.substring(mark.indexOf('<Fill>') + 6, mark.indexOf("</Fill>"));
	}
	console.log(fill);
	var fillColor;
	if (fill.indexOf('<CssParameter name="fill">') !== -1) {
		fillColor = fill.substring(fill.indexOf('<CssParameter name="fill">') + 26, fill.indexOf("</CssParameter>"));
	}
	console.log(fillColor);
	var fillColorCode;
	var fillRGBColorCode;
	if (fillColor.indexOf("#") !== -1) {
		fillColorCode = fillColor.substring(fillColor.indexOf("#") + 1);
		fillRGBColorCode = this.decimalFromHex(fillColorCode);
		console.log(fillRGBColorCode);
		console.log(fillColorCode);
	}
	fill = fill.substring(fill.indexOf("</CssParameter>") + 15);
	console.log(fill);
	var fillOpacity;
	if (fill.indexOf('<CssParameter name="fill-opacity">') !== -1) {
		fillOpacity = fill.substring(fill.indexOf('<CssParameter name="fill-opacity">') + 34, fill.indexOf("</CssParameter>"));
	}
	console.log(fillOpacity);
	if (fillRGBColorCode !== undefined && fillOpacity !== undefined) {
		obj["fillRGBA"] = "rgba(" + fillRGBColorCode + "," + fillOpacity + ")";
	}
	mark = mark.substring(mark.indexOf("</Fill>") + 7);
	console.log(ptSym);
	var stroke = mark.substring(mark.indexOf('<Stroke>') + 8, mark.indexOf("</Stroke>"));
	console.log(stroke);
	var strokeColor;
	if (stroke.indexOf('<CssParameter name="stroke">') !== -1) {
		strokeColor = stroke.substring(stroke.indexOf('<CssParameter name="stroke">') + 28, stroke.indexOf("</CssParameter>"));
	}
	var strokeColorCode;
	var strokeRGBColorCode;
	if (strokeColor.indexOf("#") !== -1) {
		strokeColorCode = strokeColor.substring(strokeColor.indexOf("#") + 1);
		strokeRGBColorCode = this.decimalFromHex(strokeColorCode);
		console.log(strokeRGBColorCode);
		console.log(strokeColorCode);
	}
	stroke = stroke.substring(stroke.indexOf("</CssParameter>") + 15);
	console.log(stroke);
	var strokeOpacity;
	if (stroke.indexOf('<CssParameter name="stroke-opacity">') !== -1) {
		strokeOpacity = stroke.substring(stroke.indexOf('<CssParameter name="stroke-opacity">') + 36, stroke.indexOf("</CssParameter>"));
	}
	console.log(strokeOpacity);
	if (strokeRGBColorCode !== undefined && strokeOpacity !== undefined) {
		obj["strokeRGBA"] = "rgba(" + strokeRGBColorCode + "," + strokeOpacity + ")";
	}
	stroke = stroke.substring(stroke.indexOf("</CssParameter>") + 15);
	console.log(stroke);
	var strokeWidth;
	if (stroke.indexOf('<CssParameter name="stroke-width">') !== -1) {
		strokeWidth = stroke.substring(stroke.indexOf('<CssParameter name="stroke-width">') + 34, stroke.indexOf("</CssParameter>"));
	}
	if (strokeWidth !== undefined) {
		obj["strokeWidth"] = strokeWidth;
	}

	stroke = stroke.substring(stroke.indexOf("</CssParameter>") + 15);
	console.log(stroke);
	var strokeDashArray;
	if (stroke.indexOf('<CssParameter name="stroke-dasharray">') !== -1) {
		strokeDashArray = stroke
				.substring(stroke.indexOf('<CssParameter name="stroke-dasharray">') + 38, stroke.indexOf("</CssParameter>"));
	}
	if (strokeDashArray !== undefined) {
		strokeDashArray = strokeDashArray.replace(/ /gi, ",");
		obj["strokeDashArray"] = strokeDashArray;
	}

	mark = mark.substring(mark.indexOf("</Stroke>") + 9);
	console.log(ptSym);
	console.log(obj);
	return obj;
};
/**
 * 스타일을 변경할 레이어를 설정한다.
 * 
 * @method gb.style.LayerStyle#setLayer
 * @param {ol.layer.Base}
 *            layer - 스타일을 변경할 레이어 객체
 */
gb.style.LayerStyle.prototype.setLayer = function(layer) {
	this.layer = layer;
	console.log(layer);
	var name = layer.get("name");
	this.setLayerName(name);
	var opacity = layer.getOpacity();
	$(this.opaPicker).val(opacity);
	$(this.opaFigure).empty();
	$(this.opaFigure).text(opacity);
	var git = layer.get("git");
	if (git !== undefined && git !== null) {
		this.geom = git.geometry;
		if (this.geom === "Point") {
			$(this.lineArea).show();
			$(this.fillArea).show();
			$(this.radArea).show();
		} else if (this.geom === "MultiPoint") {
			$(this.lineArea).show();
			$(this.fillArea).show();
			$(this.radArea).show();
		} else if (this.geom === "LineString") {
			$(this.lineArea).show();
			$(this.fillArea).hide();
			$(this.radArea).hide();
		} else if (this.geom === "MultiLineString") {
			$(this.lineArea).show();
			$(this.fillArea).hide();
			$(this.radArea).hide();
		} else if (this.geom === "Polygon") {
			$(this.lineArea).show();
			$(this.fillArea).show();
			$(this.radArea).hide();
		} else if (this.geom === "MultiPolygon") {
			$(this.lineArea).show();
			$(this.fillArea).show();
			$(this.radArea).hide();
		}
	}
	if (layer instanceof ol.layer.Vector) {
		var style = layer.getStyle();
		if (style instanceof ol.style.Style) {
			if (this.geom === "Point" || this.geom === "MultiPoint") {
				var image = style.getImage();
				if (image instanceof ol.style.RegularShape) {
					if (image instanceof ol.style.Circle) {
						var fill = image.getFill();
						var fillColor = fill.getColor();
						var stroke = image.getStroke();
						var strokeColor = stroke.getColor();
						var lineDash = stroke.getLineDash();
						var width = stroke.getWidth();
						var radius = image.getRadius();

						if (Array.isArray(fillColor)) {
							$(this.fillPicker).spectrum(
									"set",
									"rgba(" + fillColor[0] + ", " + fillColor[1] + ", " + fillColor[2] + ", "
											+ (fillColor[3] === undefined || fillColor[3] === null ? 1 : fillColor[3]) + ")");
						} else if (typeof fillColor === "string") {
						}
						if (Array.isArray(strokeColor)) {
							$(this.linePicker).spectrum(
									"set",
									"rgba(" + strokeColor[0] + ", " + strokeColor[1] + ", " + strokeColor[2] + ", "
											+ (strokeColor[3] === undefined || strokeColor[3] === null ? 1 : strokeColor[3]) + ")");
						} else if (typeof strokeColor === "string") {

						}
						$(this.widthInput).val(width);
						$(this.radInput).val(radius);

						var children = $(this.outlineInput).children();
						if (lineDash === undefined || lineDash === null) {
							for (var i = 0; i < children.length; i++) {
								if ($(children[i]).val() === "outline1") {
									$(this.outlineInput).val("outline1");
								}
							}
						} else if (Array.isArray(lineDash)) {
							var dashStr = lineDash.toString();
							for (var i = 0; i < children.length; i++) {
								if ($(children[i]).attr("dash") === dashStr) {
									$(this.outlineInput).val($(children[i]).val());
								}
							}
						}

					}
				}
			} else if (this.geom === "LineString" || this.geom === "MultiLineString") {
				var stroke = style.getStroke();
				var strokeColor = stroke.getColor();
				var lineDash = stroke.getLineDash();
				var width = stroke.getWidth();
				if (Array.isArray(strokeColor)) {
					$(this.linePicker).spectrum(
							"set",
							"rgba(" + strokeColor[0] + ", " + strokeColor[1] + ", " + strokeColor[2] + ", "
									+ (strokeColor[3] === undefined || strokeColor[3] === null ? 1 : strokeColor[3]) + ")");
				} else if (typeof strokeColor === "string") {

				}

				$(this.widthInput).val(width);
				$(this.radInput).val(radius);

				var children = $(this.outlineInput).children();
				if (lineDash === undefined || lineDash === null) {
					for (var i = 0; i < children.length; i++) {
						if ($(children[i]).val() === "outline1") {
							$(this.outlineInput).val("outline1");
						}
					}
				} else if (Array.isArray(lineDash)) {
					var dashStr = lineDash.toString();
					for (var i = 0; i < children.length; i++) {
						if ($(children[i]).attr("dash") === dashStr) {
							$(this.outlineInput).val($(children[i]).val());
						}
					}
				}
			} else if (this.geom === "Polygon" || this.geom === "MultiPolygon") {
				var fill = style.getFill();
				var fillColor = fill.getColor();
				var stroke = style.getStroke();
				var strokeColor = stroke.getColor();
				var lineDash = stroke.getLineDash();
				var width = stroke.getWidth();
				if (Array.isArray(strokeColor)) {
					$(this.linePicker).spectrum(
							"set",
							"rgba(" + strokeColor[0] + ", " + strokeColor[1] + ", " + strokeColor[2] + ", "
									+ (strokeColor[3] === undefined || strokeColor[3] === null ? 1 : strokeColor[3]) + ")");
				} else if (typeof strokeColor === "string") {
					$(this.linePicker).spectrum("set", strokeColor);
				}
				if (Array.isArray(fillColor)) {
					$(this.fillPicker).spectrum(
							"set",
							"rgba(" + fillColor[0] + ", " + fillColor[1] + ", " + fillColor[2] + ", "
									+ (fillColor[3] === undefined || fillColor[3] === null ? 1 : fillColor[3]) + ")");
				} else if (typeof fillColor === "string") {
					$(this.fillPicker).spectrum("set", fillColor);
				}

				$(this.widthInput).val(width);
				$(this.radInput).val(radius);

				var children = $(this.outlineInput).children();
				if (lineDash === undefined || lineDash === null) {
					for (var i = 0; i < children.length; i++) {
						if ($(children[i]).val() === "outline1") {
							$(this.outlineInput).val("outline1");
						}
					}
				} else if (Array.isArray(lineDash)) {
					var dashStr = lineDash.toString();
					for (var i = 0; i < children.length; i++) {
						if ($(children[i]).attr("dash") === dashStr) {
							$(this.outlineInput).val($(children[i]).val());
						}
					}
				}
			}
		} else {
			$(this.linePicker).spectrum("set", "rgb(0,0,0)");
			$(this.fillPicker).spectrum("set", "rgb(0,0,0)");
		}
	} else if (layer instanceof ol.layer.Tile) {
		var source = layer.getSource();
		var params = source.getParams();
		if (params.hasOwnProperty("SLD_Body")) {
			var sld = params["SLD_Body"];
			if (this.geom === "Point" || this.geom === "MultiPoint") {
				var pointStyle = this.parsePointSymbolizer(sld);
				if (pointStyle.hasOwnProperty("fillRGBA")) {
					$(this.fillPicker).spectrum("set", pointStyle["fillRGBA"]);
				}
				if (pointStyle.hasOwnProperty("strokeRGBA")) {
					$(this.linePicker).spectrum("set", pointStyle["strokeRGBA"]);
				}
				if (pointStyle.hasOwnProperty("strokeWidth")) {
					$(this.widthInput).val(pointStyle["strokeWidth"]);
				}
				if (pointStyle.hasOwnProperty("pointSize")) {
					$(this.radInput).val(parseFloat(pointStyle["pointSize"] / 2));
				}
				if (pointStyle.hasOwnProperty("strokeDashArray")) {
					$(this.outlineInput)
					var children = $(this.outlineInput).children();
					for (var i = 0; i < children.length; i++) {
						if ($(children[i]).attr("dash") === pointStyle["strokeDashArray"]) {
							$(this.outlineInput).val($(children[i]).val());
						}
					}
				} else {
					$(this.outlineInput).val("outline1");
				}
			} else if (this.geom === "LineString" || this.geom === "MultiLineString") {
				var lineStyle = this.parseLineSymbolizer(sld);
				if (lineStyle.hasOwnProperty("strokeRGBA")) {
					$(this.linePicker).spectrum("set", lineStyle["strokeRGBA"]);
				}
				if (lineStyle.hasOwnProperty("strokeWidth")) {
					$(this.widthInput).val(lineStyle["strokeWidth"]);
				}
				if (lineStyle.hasOwnProperty("strokeDashArray")) {
					$(this.outlineInput)
					var children = $(this.outlineInput).children();
					for (var i = 0; i < children.length; i++) {
						if ($(children[i]).attr("dash") === lineStyle["strokeDashArray"]) {
							$(this.outlineInput).val($(children[i]).val());
						}
					}
				} else {
					$(this.outlineInput).val("outline1");
				}
			} else if (this.geom === "Polygon" || this.geom === "MultiPolygon") {
				var polyStyle = this.parsePolygonSymbolizer(sld);
				if (polyStyle.hasOwnProperty("fillRGBA")) {
					$(this.fillPicker).spectrum("set", polyStyle["fillRGBA"]);
				}
				if (polyStyle.hasOwnProperty("strokeRGBA")) {
					$(this.linePicker).spectrum("set", polyStyle["strokeRGBA"]);
				}
				if (polyStyle.hasOwnProperty("strokeWidth")) {
					$(this.widthInput).val(polyStyle["strokeWidth"]);
				}
				if (polyStyle.hasOwnProperty("strokeDashArray")) {
					$(this.outlineInput)
					var children = $(this.outlineInput).children();
					for (var i = 0; i < children.length; i++) {
						if ($(children[i]).attr("dash") === polyStyle["strokeDashArray"]) {
							$(this.outlineInput).val($(children[i]).val());
						}
					}
				} else {
					$(this.outlineInput).val("outline1");
				}
			}
		}

	}
};
/**
 * 패널에 레이어 이름을 표시한다.
 * 
 * @method setLayerName
 * @param {String}
 *            name - 표시할 레이어의 이름
 */
gb.style.LayerStyle.prototype.setLayerName = function(name) {
	$(this.layerName).text(name);
	$(this.layerName).attr("title", name);
};
/**
 * 스타일 편집중인 레이어를 반환한다.
 * 
 * @method gb.style.LayerStyle#getLayer
 * @return {ol.layer.Base} 스타일 편집중인 레이어 객체
 */
gb.style.LayerStyle.prototype.getLayer = function() {
	return this.layer;
};