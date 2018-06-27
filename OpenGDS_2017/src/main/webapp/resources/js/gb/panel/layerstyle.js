/**
 * 레이어 스타일 패널 객체를 정의한다.
 * 
 * @class gb.panel.LayerStyle
 * @memberof gb.panel
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
if (!gb.panel)
	gb.panel = {};
gb.panel.LayerStyle = function(obj) {
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
		"margin-bottom" : "8px"
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
	$(this.panelBody).append(this.layerName).append(this.lineArea).append(this.fillArea).append(this.widthArea).append(this.radArea)
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
gb.panel.LayerStyle.prototype = Object.create(gb.panel.Base.prototype);
gb.panel.LayerStyle.prototype.constructor = gb.panel.LayerStyle;

/**
 * 선택한 스타일을 레이어에 적용 시킨다.
 * 
 * @method gb.panel.LayerStyle#applyStyle
 */
gb.panel.LayerStyle.prototype.applyStyle = function() {
	var style = new ol.style.Style(
			{
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
							.attr("dash").split(",") : undefined
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
						"lineDash" : $(this.outlineInput).find(":selected").attr("dash") !== undefined ? $(this.outlineInput).find(
								":selected").attr("dash").split(",") : undefined
					})
				}) : undefined
			});
	var opacity = parseFloat($(this.opaPicker).val());
	var layer = this.getLayer();
	layer.setStyle(style);
	layer.setOpacity(opacity);
	this.close();
};
/**
 * 스타일을 변경할 레이어를 설정한다.
 * 
 * @method setLayer
 */
gb.panel.LayerStyle.prototype.setLayer = function(layer) {
	this.layer = layer;
	console.log(layer);
	var name = layer.get("name");
	this.setLayerName(name);
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
						var opacity = layer.getOpacity();
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
						$(this.opaPicker).val(opacity);

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
						$(this.opaFigure).empty();
						$(this.opaFigure).text(opacity);
					}
				}
			} else if (this.geom === "LineString" || this.geom === "MultiLineString") {
				var stroke = style.getStroke();
				var strokeColor = stroke.getColor();
				var lineDash = stroke.getLineDash();
				var width = stroke.getWidth();
				var opacity = layer.getOpacity();
				if (Array.isArray(strokeColor)) {
					$(this.linePicker).spectrum(
							"set",
							"rgba(" + strokeColor[0] + ", " + strokeColor[1] + ", " + strokeColor[2] + ", "
									+ (strokeColor[3] === undefined || strokeColor[3] === null ? 1 : strokeColor[3]) + ")");
				} else if (typeof strokeColor === "string") {

				}

				$(this.widthInput).val(width);
				$(this.radInput).val(radius);
				$(this.opaPicker).val(opacity);

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
				$(this.opaFigure).empty();
				$(this.opaFigure).text(opacity);
			} else if (this.geom === "Polygon" || this.geom === "MultiPolygon") {
				var fill = style.getFill();
				var fillColor = fill.getColor();
				var stroke = style.getStroke();
				var strokeColor = stroke.getColor();
				var lineDash = stroke.getLineDash();
				var width = stroke.getWidth();
				var opacity = layer.getOpacity();
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
				$(this.opaPicker).val(opacity);

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
				$(this.opaFigure).empty();
				$(this.opaFigure).text(opacity);
			}
		} else {
			$(this.linePicker).spectrum("set", "rgb(0,0,0)");
			$(this.fillPicker).spectrum("set", "rgb(0,0,0)");
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
gb.panel.LayerStyle.prototype.setLayerName = function(name) {
	$(this.layerName).text(name);
};
/**
 * 내부 인터랙션 구조를 반환한다.
 * 
 * @method getInteractions_
 * @return {Mixed Obj} {select : ol.interaction.Select..}
 */
gb.panel.LayerStyle.prototype.getLayer = function() {
	return this.layer;
};