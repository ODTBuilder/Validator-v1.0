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
if (!gb.geoserver)
	gb.geoserver = {};
gb.geoserver.CreateLayer = function(obj) {
	var that = this;
	var options = obj;
	this.window;
	this.url = options.URL ? options.URL : null;
	this.format = undefined;
	this.type = undefined;
	this.refer = options.refer ? options.refer : undefined;
	this.clientRefer = options.clientRefer ? options.clientRefer : undefined;

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
	var sheetNum = $("<p>").text("Map Sheet Number");
	this.sheetNumInput = $("<input>").addClass("form-control").attr({
		"type" : "text"
	});
	var div1 = $("<div>").css({
		"margin-bottom" : "10px"
	}).append(sheetNum).append(this.sheetNumInput);

	var layerName = $("<p>").text("Layer Name");
	this.layerNameInput = $("<input>").addClass("form-control").attr({
		"type" : "text"
	});
	this.layerNameForm = $("<div>").css({
		"margin-bottom" : "15px"
	}).append(layerName).append(this.layerNameInput);

	this.typeForm = $("<div>").css({
		"margin-bottom" : "10px"
	});

	this.attrForm = $("<div>").css({
		"margin-bottom" : "10px"
	});

	this.expertForm = $("<div>").css({
		"margin-bottom" : "10px"
	});

	this.message = $("<div>").addClass("text-danger").css({
		"margin-bottom" : "10px",
		"display" : "none"
	});
	this.body = $("<div>").append(div1).append(this.layerNameForm).append(this.typeForm).append(this.attrForm).append(this.expertForm)
			.append(this.message);
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
		var isExisting = that.isExisting([ that.futureId ]);
		if (isExisting) {
			$(that.message).empty();
			$(that.message).show();
			$(that.message).text("Layer name or Map sheet number is duplicated.");
		} else {
			that.save(opt);
			that.close();
		}
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
};

gb.geoserver.CreateLayer.prototype.isExisting = function(ids) {
	var result = false;
	var refer = this.getReference();
	if (!Array.isArray(ids)) {
		return;
	}
	for (var i = 0; i < ids.length; i++) {
		var layer = refer.get_node(ids[i]);
		if (!!layer) {
			result = true;
			break;
		}
	}

	return result;
};
gb.geoserver.CreateLayer.prototype.open = function() {
	$(this.message).hide();
	this.window.modal('show');
};
gb.geoserver.CreateLayer.prototype.close = function() {
	this.window.modal('hide');
};
gb.geoserver.CreateLayer.prototype.setClientReference = function(refer) {
	this.clientRefer = refer;
};
gb.geoserver.CreateLayer.prototype.getClientReference = function() {
	return this.clientRefer;
};
gb.geoserver.CreateLayer.prototype.setReference = function(refer) {
	this.refer = refer;
};
gb.geoserver.CreateLayer.prototype.getReference = function() {
	return this.refer;
};
gb.geoserver.CreateLayer.prototype.save = function(obj) {
	var that = this;
	$.ajax({
		url : this.getUrl(),
		method : "POST",
		contentType : "application/json; charset=UTF-8",
		cache : false,
		data : JSON.stringify(obj),
		beforeSend : function() {
			$("body").css("cursor", "wait");
		},
		complete : function() {
			$("body").css("cursor", "default");
		},
		traditional : true,
		success : function(data, textStatus, jqXHR) {
			console.log(data);
			that.getReference().refresh();
		}
	});
};
gb.geoserver.CreateLayer.prototype.setForm = function(format, type, sheetNum) {
	this.format = format;
	this.type = type;
	if (type === "mapsheet") {
		if (format === "dxf") {
			$(this.htag).text("Create a map sheet (DXF)");
		} else if (format === "ngi") {
			$(this.htag).text("Create a map sheet (NGI)");
		} else if (format === "shp") {
			$(this.htag).text("Create a map sheet (SHP)");
		}
		$(this.sheetNumInput).val("");
		$(this.layerNameForm).hide();
		$(this.typeForm).hide();
		$(this.attrForm).hide();
		$(this.expertForm).hide();
	} else if (type === "layer") {
		$(this.htag).text("Create a layer");
		$(this.sheetNumInput).val(sheetNum);
		$(this.layerNameInput).val("");
		$(this.layerNameForm).show();
		if (format === "dxf") {
			this.initTypeForm("dxf");
			$(this.typeForm).show();
			$(this.attrForm).hide();
			$(this.expertForm).hide();
		} else if (format === "ngi") {
			this.initTypeForm("ngi");
			$(this.typeForm).show();
			this.initAttrForm();
			$(this.attrForm).show();
			this.initExpertForm();
			$(this.expertForm).show();
		} else if (format === "shp") {
			this.initTypeForm("shp");
			$(this.typeForm).show();
			this.initAttrForm();
			$(this.attrForm).show();
			$(this.expertForm).hide();
		}
	}
};
gb.geoserver.CreateLayer.prototype.initTypeForm = function(type) {
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
	}
	$(this.typeForm).empty();
	var tp = $("<p>").text("Type");
	$(this.typeForm).append(tp).append(select);
};
gb.geoserver.CreateLayer.prototype.initAttrForm = function() {
	var that = this;
	var htd1 = $("<td>").text("Name");
	var htd2 = $("<td>").text("Type");
	var htd3 = $("<td>").text("Not Null");
	var htd4 = $("<td>").text("Unique");
	var thd = $("<thead>").append(htd1).append(htd2).append(htd3).append(htd4);

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

	var tr1 = $("<tr>").append(td1).append(td2).append(td3).append(td4);
	this.typeFormBody = $("<tbody>").append(tr1);

	var table = $("<table>").addClass("table").addClass("text-center").append(thd).append(this.typeFormBody);
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
		var tr1 = $("<tr>").append(td1).append(td2).append(td3).append(td4);
		$(that.typeFormBody).append(tr1);
	});
	$(this.attrForm).empty();
	var tp = $("<p>").text("Attribute");
	$(this.attrForm).append(tp).append(table).append(addBtn);
};
gb.geoserver.CreateLayer.prototype.initExpertForm = function() {
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
	var tp = $("<p>").text("NGI Setting");
	$(this.expertForm).append(tp).append(table).append(table2);
};
gb.geoserver.CreateLayer.prototype.getDefinitionForm = function() {
	var opt = {
		"layer" : {}
	};
	if (this.type === "mapsheet") {
		opt.layer[this.format] = {};
		opt.layer[this.format][$(this.sheetNumInput).val()] = {};
		if ($(this.sheetNumInput).val().replace(/(\s*)/g, '') === "") {
			console.error("no mapsheet number");
			return;
		}
		opt.layer[this.format][$(this.sheetNumInput).val()]["create"] = [];
		var layerObj = {
			"layerName" : "default",
			"layerType" : "Point"
		};
		opt.layer[this.format][$(this.sheetNumInput).val()]["create"].push(layerObj);
		if (this.format === "ngi") {
			var attr = {
				"fieldName" : "UFID",
				"type" : "String",
				"decimal" : null,
				"size" : 256,
				"isUnique" : true
			}
			layerObj["attr"] = [ attr ];
			layerObj["version"] = 2;
			layerObj["dim"] = 2;
			layerObj["bound"] = [ [ 122.6019287109375, 49.73690656023088 ], [ 122.14874267578125, 49.40918616182351 ] ];
			layerObj["represent"] = "255;0;102";
		} else if (this.format === "shp") {
			var attr = {
				"fieldName" : "UFID",
				"type" : "String",
				"nullable" : true
			}
			layerObj["attr"] = [ attr ];
			// layerObj["bound"] = [ [ 122.6019287109375, 49.73690656023088 ], [
			// 122.14874267578125, 49.40918616182351 ] ];
		}
		this.futureId = "gro_" + this.format + "_" + $(this.sheetNumInput).val().replace(/(\s*)/g, '');
	} else if (this.type === "layer") {
		opt.layer[this.format] = {};
		if ($(this.sheetNumInput).val().replace(/(\s*)/g, '') === "") {
			console.error("no mapsheet number");
			return;
		}
		opt.layer[this.format][$(this.sheetNumInput).val()] = {};
		opt.layer[this.format][$(this.sheetNumInput).val()]["create"] = [];
		if ($(this.layerNameInput).val().replace(/(\s*)/g, '') === "") {
			console.error("no layer name");
			return;
		}
		var layerObj = {
			"layerName" : $(this.layerNameInput).val(),
			"layerType" : $(this.typeForm).find("select").val()
		};
		opt.layer[this.format][$(this.sheetNumInput).val()]["create"].push(layerObj);
		var layerAttr = [];
		if (this.format === "ngi") {
			var attrs = $(this.attrForm).find("tr");
			for (var i = 0; i < attrs.length; i++) {
				if ($(attrs[i]).children().eq(0).find("input:text").val().replace(/(\s*)/g, '') === "") {
					break;
				}
				var attr = {
					"fieldName" : $(attrs[i]).children().eq(0).find("input:text").val().replace(/(\s*)/g, ''),
					"type" : $(attrs[i]).children().eq(1).find("select").val(),
					"nullable" : $(attrs[i]).children().eq(2).find("input:checkbox").prop("checked") ? false : true,
					"isUnique" : $(attrs[i]).children().eq(3).find("input:checkbox").prop("checked") ? true : false,
					"decimal" : $(attrs[i]).children().eq(1).find("select").val() === "Double" ? 30 : null,
					"size" : 256
				};
				layerAttr.push(attr);
			}
			layerObj["attr"] = layerAttr;
			layerObj["version"] = $(this.ver).val();
			layerObj["dim"] = $(this.dim).val();
			layerObj["bound"] = [ [ $(this.minx).val(), $(this.miny).val() ], [ $(this.maxx).val(), $(this.maxy).val() ] ];
			layerObj["represent"] = $(this.rep).val();
		}
		this.futureId = "geo_" + this.format + "_" + $(this.sheetNumInput).val().replace(/(\s*)/g, '') + "_" + $(this.layerNameInput).val()
				+ "_" + ($(this.typeForm).find("select").val().toUpperCase());
	}
	console.log(opt);
	return opt;
};
gb.geoserver.CreateLayer.prototype.setUrl = function(url) {
	if (typeof url === "string") {
		this.url = url;
	}
};
gb.geoserver.CreateLayer.prototype.getUrl = function() {
	return this.url;
};