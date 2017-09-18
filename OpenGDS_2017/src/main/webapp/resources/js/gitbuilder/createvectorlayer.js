/**
 * 레이어 정보를 변경하는 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2017. 06. 16
 * @version 0.01
 */
var gb;
if (!gb)
	gb = {};
if (!gb.edit)
	gb.edit = {};
gb.edit.CreateVectorLayer = function(obj) {
	var that = this;
	var options = obj;
	this.window;
	// this.url = options.URL ? options.URL : null;
	this.format = undefined;
	this.type = undefined;
	this.refer = options.refer ? options.refer : undefined;
	this.map = options.map ? options.map : undefined;
	this.layerRecord = options.layerRecord ? options.layerRecord : undefined;
	// this.clientRefer = options.clientRefer ? options.clientRefer : undefined;

	var xSpan = $("<span>").attr({
		"aria-hidden" : true
	}).html("&times;");
	var xButton = $("<button>").attr({
		"type" : "button",
		"data-dismiss" : "modal",
		"aria-label" : "Close"
	}).html(xSpan);
	$(xButton).addClass("close");

	this.htag = $("<h4>");
	this.htag.text("Create layer");
	$(this.htag).addClass("modal-title");

	var header = $("<div>").append(xButton).append(this.htag);
	$(header).addClass("modal-header");
	/*
	 * 
	 * 
	 * header end
	 * 
	 * 
	 */

	var formatArea = $("<div>").css({
		"margin-bottom" : "10px"
	});
	var sheetNum = $("<p>").text("Map Sheet Number");
	this.sheetNumInput = $("<input>").addClass("form-control").attr({
		"type" : "text"
	});
	var div1 = $("<div>").css({
		"margin-bottom" : "20px"
	}).append(sheetNum).append(this.sheetNumInput);

	var layerName = $("<p>").text("Layer Name");
	this.layerNameInput = $("<input>").addClass("form-control").attr({
		"type" : "text"
	});
	this.layerNameForm = $("<div>").css({
		"margin-bottom" : "20px"
	}).append(layerName).append(this.layerNameInput);

	this.geomForm = $("<div>").css({
		"margin-bottom" : "20px"
	});

	this.attrForm = $("<div>").css({
		"margin-bottom" : "20px"
	});

	this.expertFormArea = $("<div>");

	this.expertForm = $("<div>").css({
		"margin-bottom" : "20px"
	}).append(this.expertFormArea);

	this.formatRadio1 = $("<input>").attr({
		"type" : "radio",
		"value" : "ngi",
		"name" : "gitbuilder-createvector-radio"
	}).prop({
		"checked" : true
	}).change(function() {
		that.setForm("ngi", "layer");
	});
	var label1 = $("<label>").addClass("radio-inline").append(this.formatRadio1).append("NGI");

	this.formatRadio2 = $("<input>").attr({
		"type" : "radio",
		"value" : "dxf",
		"name" : "gitbuilder-createvector-radio"
	}).change(function() {
		that.setForm("dxf", "layer");
	});
	var label2 = $("<label>").addClass("radio-inline").append(this.formatRadio2).append("DXF");

	this.formatRadio3 = $("<input>").attr({
		"type" : "radio",
		"value" : "shp",
		"name" : "gitbuilder-createvector-radio"
	}).change(function() {
		that.setForm("shp", "layer");
	});
	var label3 = $("<label>").addClass("radio-inline").append(this.formatRadio3).append("SHP");
	$(formatArea).append(label1).append(label2).append(label3);

	this.body = $("<div>").append(formatArea).append(div1).append(this.layerNameForm).append(this.geomForm).append(this.attrForm).append(
			this.expertForm);
	that.setForm("ngi", "layer");
	$(this.body).addClass("modal-body");
	/*
	 * 
	 * 
	 * body end
	 * 
	 * 
	 */

	var closeBtn = $("<button>").attr({
		"type" : "button",
		"data-dismiss" : "modal"
	});
	$(closeBtn).addClass("btn");
	$(closeBtn).addClass("btn-default");
	$(closeBtn).text("Close");

	var okBtn = $("<button>").attr({
		"type" : "button"
	}).on("click", function() {
		var opt = that.getDefinitionForm();
		that.save(opt);
		that.close();
	});
	$(okBtn).addClass("btn");
	$(okBtn).addClass("btn-primary");
	$(okBtn).text("Create");

	var pright = $("<span>").css("float", "right");
	$(pright).append(closeBtn).append(okBtn);

	var footer = $("<div>").append(pright);
	$(footer).addClass("modal-footer");
	/*
	 * 
	 * 
	 * footer end
	 * 
	 * 
	 */
	var content = $("<div>").append(header).append(this.body).append(footer);
	$(content).addClass("modal-content");

	var dialog = $("<div>").html(content);
	$(dialog).addClass("modal-dialog");
	$(dialog).addClass("modal-lg");
	this.window = $("<div>").hide().attr({
		// Setting tabIndex makes the div focusable
		tabIndex : -1,
		role : "dialog",
	}).html(dialog);
	$(this.window).addClass("modal");
	$(this.window).addClass("fade");

	this.window.appendTo("body");
	this.window.modal({
		backdrop : true,
		keyboard : true,
		show : false,
	});
}
gb.edit.CreateVectorLayer.prototype.open = function() {
	this.window.modal('show');
};
gb.edit.CreateVectorLayer.prototype.close = function() {
	this.window.modal('hide');
};
gb.edit.CreateVectorLayer.prototype.setClientReference = function(refer) {
	this.clientRefer = refer;
};
gb.edit.CreateVectorLayer.prototype.getClientReference = function() {
	return this.clientRefer;
};
gb.edit.CreateVectorLayer.prototype.setReference = function(refer) {
	this.refer = refer;
};
gb.edit.CreateVectorLayer.prototype.getReference = function() {
	return this.refer;
};
gb.edit.CreateVectorLayer.prototype.save = function(obj) {
	var that = this;

};
gb.edit.CreateVectorLayer.prototype.setForm = function(format, type) {
	this.format = format;
	this.type = type;
	if (type === "mapsheet") {
		if (format === "dxf") {
			$(this.htag).text("Create a map sheet (DXF)");
		} else if (format === "ngi") {
			$(this.htag).text("Create a map sheet (NGI)");
		}
		$(this.sheetNumInput).val("");
		$(this.layerNameForm).hide();
		$(this.geomForm).hide();
		$(this.attrForm).hide();
		$(this.expertForm).hide();
	} else if (type === "layer") {
		$(this.htag).text("Create a vector layer");
		$(this.sheetNumInput).val("");
		$(this.layerNameInput).val("");
		$(this.layerNameForm).show();
		if (format === "dxf") {
			this.initGeomForm("dxf");
			$(this.geomForm).show();
			$(this.attrForm).hide();
			$(this.expertForm).hide();
		} else if (format === "ngi") {
			this.initGeomForm("ngi");
			$(this.geomForm).show();
			this.initAttrForm("ngi");
			$(this.attrForm).show();
			this.initExpertForm();
			$(this.expertForm).show();
			$(this.expertFormArea).hide();
		} else if (format === "shp") {
			this.initGeomForm("shp");
			$(this.geomForm).show();
			this.initAttrForm("shp");
			$(this.attrForm).show();
			$(this.expertForm).hide();
		}
	}
};
gb.edit.CreateVectorLayer.prototype.initGeomForm = function(type) {
	var select = $("<select>").addClass("form-control");
	if (type === "dxf") {
		var option1 = $("<option>").text("LWPolyline");
		var option2 = $("<option>").text("Polyline");
		var option3 = $("<option>").text("Insert");
		var option4 = $("<option>").text("Text");
		$(select).append(option1).append(option2).append(option3).append(option4);
	} else if (type === "ngi") {
		var option1 = $("<option>").text("Point");
		var option2 = $("<option>").text("LineString");
		var option3 = $("<option>").text("Polygon");
		var option4 = $("<option>").text("Text");
		$(select).append(option1).append(option2).append(option3).append(option4);
	} else if (type === "shp") {
		var option1 = $("<option>").text("Point");
		var option2 = $("<option>").text("LineString");
		var option3 = $("<option>").text("Polygon");
		$(select).append(option1).append(option2).append(option3);
	}
	$(this.geomForm).empty();
	var tp = $("<p>").text("Type");
	$(this.geomForm).append(tp).append(select);
};
gb.edit.CreateVectorLayer.prototype.initAttrForm = function(format) {
	var that = this;
	var htd1 = $("<td>").text("Name");
	var htd2 = $("<td>").text("Type");
	var htd3 = $("<td>").text("Not Null");
	var htd4 = $("<td>").text("Unique");
	var htd5 = $("<td>").text("Delete");
	var thd;
	if (format === "ngi") {
		thd = $("<thead>").append(htd1).append(htd2).append(htd3).append(htd4).append(htd5);
	} else {
		thd = $("<thead>").append(htd1).append(htd2).append(htd3).append(htd5);
	}

	var key = $("<input>").addClass("form-control").attr({
		"type" : "text"
	});
	var td1 = $("<td>").append(key);

	var opt1 = $("<option>").text("Integer");
	var opt2 = $("<option>").text("Double");
	var opt3 = $("<option>").text("String");
	var opt4 = $("<option>").text("Date");
	var opt5 = $("<option>").text("Boolean");
	var type = $("<select>").addClass("form-control").append(opt1).append(opt2).append(opt3).append(opt4).append(opt5);
	var td2 = $("<td>").append(type);

	var nullable = $("<input>").attr({
		"type" : "checkbox"
	}).css({
		"vertical-align" : "-webkit-baseline-middle"
	});
	var td3 = $("<td>").append(nullable);

	var unique = $("<input>").attr({
		"type" : "checkbox"
	}).css({
		"vertical-align" : "-webkit-baseline-middle"
	});
	var td4 = $("<td>").append(unique);

	var delIcon = $("<i>").attr({
		"aria-hidden" : "true"
	}).addClass("fa").addClass("fa-times");
	var del = $("<button>").addClass("btn").addClass("btn-default").append(delIcon);

	var td5 = $("<td>").append(del);

	var tr1;
	if (format === "ngi") {
		tr1 = $("<tr>").append(td1).append(td2).append(td3).append(td4).append(td5);
	} else {
		tr1 = $("<tr>").append(td1).append(td2).append(td3).append(td5);
	}

	this.geomFormBody = $("<tbody>").append(tr1);

	var table = $("<table>").addClass("table").addClass("text-center").append(thd).append(this.geomFormBody);

	var addBtn = $("<input>").addClass("gitbuilder-createlayer-addattr").addClass("btn").addClass("btn-default").attr({
		"type" : "button",
		"value" : "Add Attribute"
	}).on("click", function() {
		console.log("attr");
		var key = $("<input>").addClass("form-control").attr({
			"type" : "text"
		});
		var td1 = $("<td>").append(key);

		var opt1 = $("<option>").text("Integer");
		var opt2 = $("<option>").text("Double");
		var opt3 = $("<option>").text("String");
		var opt4 = $("<option>").text("Date");
		var opt5 = $("<option>").text("Boolean");
		var type = $("<select>").addClass("form-control").append(opt1).append(opt2).append(opt3).append(opt4).append(opt5);
		var td2 = $("<td>").append(type);

		var nullable = $("<input>").attr({
			"type" : "checkbox"
		});
		var td3 = $("<td>").append(nullable);

		var unique = $("<input>").attr({
			"type" : "checkbox"
		});
		var td4 = $("<td>").append(unique);

		var delIcon = $("<i>").attr({
			"aria-hidden" : "true"
		}).addClass("fa").addClass("fa-times");
		var del = $("<button>").addClass("btn").addClass("btn-default").append(delIcon);

		var td5 = $("<td>").append(del);

		var tr1;
		if (that.getFormat() === "ngi") {
			tr1 = $("<tr>").append(td1).append(td2).append(td3).append(td4).append(td5);
		} else if (that.getFormat() === "shp") {
			tr1 = $("<tr>").append(td1).append(td2).append(td3).append(td5);
		}

		$(that.geomFormBody).append(tr1);
	});
	$(this.attrForm).empty();
	var tp = $("<p>").text("Attribute");
	$(this.attrForm).append(tp).append(table).append(addBtn);
};
gb.edit.CreateVectorLayer.prototype.initExpertForm = function() {
	var that = this;
	var htd1 = $("<td>").text("Version");
	var htd2 = $("<td>").text("Dimension");
	var htd3 = $("<td>").text("Represent");
	var thd = $("<thead>").append(htd1).append(htd2).append(htd3);

	var veropt1 = $("<option>").text("1");
	var veropt2 = $("<option>").text("2");
	this.ver = $("<select>").addClass("form-control").append(veropt1).append(veropt2).val("2");
	var td1 = $("<td>").append(this.ver);

	var dimopt1 = $("<option>").text("2");
	var dimopt2 = $("<option>").text("3");
	this.dim = $("<select>").addClass("form-control").append(dimopt1).append(dimopt2).val("2");
	var td2 = $("<td>").append(this.dim);

	this.rep = $("<input>").addClass("form-control").attr({
		"type" : "text"
	}).val("255;0;102");
	var td3 = $("<td>").append(this.rep);

	var tr1 = $("<tr>").append(td1).append(td2).append(td3);

	this.expertFormBody = $("<tbody>").append(tr1);

	var table = $("<table>").addClass("table").addClass("text-center").append(thd).append(this.expertFormBody);

	var htd12 = $("<td>").text("MinX");
	var htd22 = $("<td>").text("MinY");
	var htd32 = $("<td>").text("MaxX");
	var htd42 = $("<td>").text("MaxY");
	var thd2 = $("<thead>").append(htd12).append(htd22).append(htd32).append(htd42);

	this.minx = $("<input>").addClass("form-control").attr({
		"type" : "text"
	}).val("-219825.99");
	var td12 = $("<td>").append(this.minx);
	this.miny = $("<input>").addClass("form-control").attr({
		"type" : "text"
	}).val("-435028.96");
	var td22 = $("<td>").append(this.miny);
	this.maxx = $("<input>").addClass("form-control").attr({
		"type" : "text"
	}).val("819486.07");
	var td32 = $("<td>").append(this.maxx);
	this.maxy = $("<input>").addClass("form-control").attr({
		"type" : "text"
	}).val("877525.22");
	var td42 = $("<td>").append(this.maxy);

	var tr12 = $("<tr>").append(td12).append(td22).append(td32).append(td42);

	this.expertFormBodyUnder = $("<tbody>").append(tr12);

	var table2 = $("<table>").addClass("table").addClass("text-center").append(thd2).append(this.expertFormBodyUnder);

	$(this.expertForm).empty();

	var caret = $("<i>").addClass("fa").addClass("fa-caret-down");
	var exa = $("<a>").append("NGI Setting ").attr({
		"href" : "#"
	}).css({
		"color" : "#333",
		"cursor" : "point",
		"text-decoration" : "none"
	}).append(caret).click(function() {
		if ($(that.expertFormArea).is(":visible")) {
			$(that.expertFormArea).hide();
			$(this).find("i").removeClass("fa-caret-up").addClass("fa-caret-down");
		} else {
			$(that.expertFormArea).show();
			$(this).find("i").removeClass("fa-caret-down").addClass("fa-caret-up");
		}
	});
	var tp = $("<p>").append(exa);
	this.expertFormArea = $("<div>").append(table).append(table2);
	$(this.expertForm).append(tp).append(this.expertFormArea);

};
gb.edit.CreateVectorLayer.prototype.getDefinitionForm = function() {
	// var opt = {
	// "layer" : {}
	// };
	var mapsheet;
	if (this.type === "mapsheet") {
		// opt.layer[this.format] = {};
		// opt.layer[this.format][$(this.sheetNumInput).val()] = {};
		// if ($(this.sheetNumInput).val().replace(/(\s*)/g, '') === "") {
		// console.error("no mapsheet number");
		// return;
		// }
		// opt.layer[this.format][$(this.sheetNumInput).val()]["create"] = [];
		// var layerObj = {
		// "layerName" : "default",
		// "layerType" : "Point"
		// };
		// opt.layer[this.format][$(this.sheetNumInput).val()]["create"].push(layerObj);
		// if (this.format === "ngi") {
		// var attr = {
		// "fieldName" : "UFID",
		// "type" : "String",
		// "decimal" : null,
		// "size" : 256,
		// "isUnique" : true
		// }
		// layerObj["attr"] = [ attr ];
		// layerObj["version"] = 2;
		// layerObj["dim"] = 2;
		// layerObj["bound"] = [ [ 122.6019287109375, 49.73690656023088 ], [
		// 122.14874267578125, 49.40918616182351 ] ];
		// layerObj["represent"] = "255;0;102";
		// }
	} else if (this.type === "layer") {
		// opt.layer[this.format] = {};
		if ($(this.sheetNumInput).val().replace(/(\s*)/g, '') === "") {
			console.error("no mapsheet number");
			return;
		}
		mapsheet = new gb.mapsheet.Mapsheet({
			number : $(this.sheetNumInput).val().replace(/(\s*)/g, ''),
			id : "gro_" + this.format + "_" + $(this.sheetNumInput).val().replace(/(\s*)/g, ''),
			format : this.format
		});
		var groupLayer = new ol.layer.Group();
		groupLayer.set("id", mapsheet.getId());
		groupLayer.set("name", mapsheet.getSheetNumber());
		var gitGroup = {
			"information" : mapsheet
		};
		groupLayer.set("git", gitGroup);
		// opt.layer[this.format][$(this.sheetNumInput).val()] = {};
		// opt.layer[this.format][$(this.sheetNumInput).val()]["create"] = [];
		if ($(this.layerNameInput).val().replace(/(\s*)/g, '') === "") {
			console.error("no layer name");
			return;
		}
		var layer;
		if (this.format === "ngi") {
			layer = new gb.layer.LayerInfo({
				name : $(this.layerNameInput).val().replace(/(\s*)/g, ''),
				id : "geo_" + this.format + "_" + $(this.sheetNumInput).val().replace(/(\s*)/g, '') + "_"
						+ $(this.layerNameInput).val().replace(/(\s*)/g, '') + "_" + ($(this.geomForm).find("select").val().toUpperCase()),
				format : this.format,
				srs : "EPSG:5186",
				NGIVer : parseInt($(this.ver).val()),
				NGIDim : parseInt($(this.dim).val()),
				NGIRep : $(this.rep).val(),
				mbound : [ [ $(this.minx).val(), $(this.miny).val() ], [ $(this.maxx).val(), $(this.maxy).val() ] ],
				lbound : [ [ 122.71, 28.6 ], [ 134.28, 40.27 ] ],
				isNew : true,
				geometry : $(this.geomForm).find("select").val(),
				sheetNum : $(this.sheetNumInput).val().replace(/(\s*)/g, '')
			});
		} else if (this.format === "dxf") {
			layer = new gb.layer.LayerInfo({
				name : $(this.layerNameInput).val().replace(/(\s*)/g, ''),
				id : "geo_" + this.format + "_" + $(this.sheetNumInput).val().replace(/(\s*)/g, '') + "_"
						+ $(this.layerNameInput).val().replace(/(\s*)/g, '') + "_" + ($(this.geomForm).find("select").val().toUpperCase()),
				format : this.format,
				srs : "EPSG:5186",
				mbound : [ [ $(this.minx).val(), $(this.miny).val() ], [ $(this.maxx).val(), $(this.maxy).val() ] ],
				lbound : [ [ 122.71, 28.6 ], [ 134.28, 40.27 ] ],
				isNew : true,
				geometry : $(this.geomForm).find("select").val(),
				sheetNum : $(this.sheetNumInput).val().replace(/(\s*)/g, '')
			});
		} else if (this.format === "shp") {
			layer = new gb.layer.LayerInfo({
				name : $(this.layerNameInput).val().replace(/(\s*)/g, ''),
				id : "geo_" + this.format + "_" + $(this.sheetNumInput).val().replace(/(\s*)/g, '') + "_"
						+ $(this.layerNameInput).val().replace(/(\s*)/g, '') + "_" + ($(this.geomForm).find("select").val().toUpperCase()),
				format : this.format,
				srs : "EPSG:5186",
				mbound : [ [ $(this.minx).val(), $(this.miny).val() ], [ $(this.maxx).val(), $(this.maxy).val() ] ],
				lbound : [ [ 122.71, 28.6 ], [ 134.28, 40.27 ] ],
				isNew : true,
				geometry : $(this.geomForm).find("select").val(),
				sheetNum : $(this.sheetNumInput).val().replace(/(\s*)/g, '')
			});
		}
		var vectorLayer = new ol.layer.Vector({
			source : new ol.source.Vector()
		});
		vectorLayer.set("id", layer.getId());
		vectorLayer.set("name", layer.getName());
		var gitLayer = {
			"editable" : true,
			"geometry" : layer.getGeometry(),
			"validation" : false,
			"information" : layer
		};
		// layerObj["bound"] = [ [ $(this.minx).val(), $(this.miny).val() ], [
		// $(this.maxx).val(), $(this.maxy).val() ] ];
		// layerObj["represent"] = $(this.rep).val();
		// var layerObj = {
		// "layerName" : $(this.layerNameInput).val(),
		// "layerType" : $(this.geomForm).find("select").val()
		// };
		// opt.layer[this.format][$(this.sheetNumInput).val()]["create"].push(layerObj);
		var layerAttr = [];
		if (this.format === "ngi") {
			var attrs = $(this.attrForm).find("tr");
			for (var i = 0; i < attrs.length; i++) {
				if ($(attrs[i]).children().eq(0).find("input:text").val().replace(/(\s*)/g, '') === "") {
					break;
				}
				var attribute = new gb.layer.Attribute({
					originFieldName : $(attrs[i]).children().eq(0).find("input:text").val().replace(/(\s*)/g, ''),
					fieldName : $(attrs[i]).children().eq(0).find("input:text").val().replace(/(\s*)/g, ''),
					type : $(attrs[i]).children().eq(1).find("select").val(),
					decimal : $(attrs[i]).children().eq(1).find("select").val() === "Double" ? 30 : null,
					size : 256,
					isUnique : $(attrs[i]).children().eq(3).find("input:checkbox").prop("checked") ? true : false,
					nullable : $(attrs[i]).children().eq(2).find("input:checkbox").prop("checked") ? false : true,
					isNew : true
				});
				// var attr = {
				// "fieldName" :
				// $(attrs[i]).children().eq(0).find("input:text").val().replace(/(\s*)/g,
				// ''),
				// "type" : $(attrs[i]).children().eq(1).find("select").val(),
				// "nullable" :
				// $(attrs[i]).children().eq(2).find("input:checkbox").prop("checked")
				// ? false : true,
				// "isUnique" :
				// $(attrs[i]).children().eq(3).find("input:checkbox").prop("checked")
				// ? true : false,
				// "decimal" : $(attrs[i]).children().eq(1).find("select").val()
				// === "Double" ? 30 : null,
				// "size" : 256
				// };
				layerAttr.push(attribute);
			}
			// layerObj["attr"] = layerAttr;
			layer.setAttributes(layerAttr);
			gitLayer["attribute"] = layer.getAttributesJSON();
		} else if (this.format === "shp") {
			var attrs = $(this.attrForm).find("tr");
			for (var i = 0; i < attrs.length; i++) {
				if ($(attrs[i]).children().eq(0).find("input:text").val().replace(/(\s*)/g, '') === "") {
					break;
				}
				var attribute = new gb.layer.Attribute({
					originFieldName : $(attrs[i]).children().eq(0).find("input:text").val().replace(/(\s*)/g, ''),
					fieldName : $(attrs[i]).children().eq(0).find("input:text").val().replace(/(\s*)/g, ''),
					type : $(attrs[i]).children().eq(1).find("select").val(),
					// decimal :
					// $(attrs[i]).children().eq(1).find("select").val() ===
					// "Double" ? 30 : null,
					// size : 256,
					isUnique : false,
					nullable : $(attrs[i]).children().eq(2).find("input:checkbox").prop("checked") ? false : true,
					isNew : true
				});
				// var attr = {
				// "fieldName" :
				// $(attrs[i]).children().eq(0).find("input:text").val().replace(/(\s*)/g,
				// ''),
				// "type" : $(attrs[i]).children().eq(1).find("select").val(),
				// "nullable" :
				// $(attrs[i]).children().eq(2).find("input:checkbox").prop("checked")
				// ? false : true,
				// "isUnique" :
				// $(attrs[i]).children().eq(3).find("input:checkbox").prop("checked")
				// ? true : false,
				// "decimal" : $(attrs[i]).children().eq(1).find("select").val()
				// === "Double" ? 30 : null,
				// "size" : 256
				// };
				layerAttr.push(attribute);
			}
			// layerObj["attr"] = layerAttr;
			layer.setAttributes(layerAttr);
			gitLayer["attribute"] = layer.getAttributesJSON();
		}
		vectorLayer.set("git", gitLayer);
		console.log(vectorLayer.get("git"));
		var col = new ol.Collection();
		col.push(vectorLayer);
		groupLayer.setLayers(col);
		this.layerRecord.createByMapsheet(groupLayer);
		this.map.addLayer(groupLayer);
	}
};
gb.edit.CreateVectorLayer.prototype.setUrl = function(url) {
	if (typeof url === "string") {
		this.url = url;
	}
};
gb.edit.CreateVectorLayer.prototype.getUrl = function() {
	return this.url;
};
gb.edit.CreateVectorLayer.prototype.setFormat = function(format) {
	this.format = format;
};
gb.edit.CreateVectorLayer.prototype.getFormat = function() {
	return this.format;
};