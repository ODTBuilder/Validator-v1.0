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
gb.edit.ModifyLayerProperties = function(obj) {
	var that = this;
	var options = obj;
	this.window;
	this.format = undefined;
	this.type = undefined;
	this.info = undefined;
	this.layer = undefined;
	this.layerRecord = options.layerRecord ? options.layerRecord : undefined;
	this.featureRecord = options.featureRecord ? options.featureRecord : undefined;
	this.refer = options.refer ? options.refer : undefined;
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
	this.htag.text("Layer Properties");
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
	this.div1 = $("<div>").css({
		"margin-bottom" : "10px"
	}).append(sheetNum).append(this.sheetNumInput);

	var layerName = $("<p>").text("Layer Name");
	this.layerNameInput = $("<input>").addClass("form-control").attr({
		"type" : "text"
	});
	this.layerNameForm = $("<div>").css({
		"margin-bottom" : "15px"
	}).append(layerName).append(this.layerNameInput);

	this.geomForm = $("<div>").css({
		"margin-bottom" : "10px"
	});

	this.attrForm = $("<div>").css({
		"margin-bottom" : "10px"
	});

	this.expertForm = $("<div>").css({
		"margin-bottom" : "10px"
	});

	this.formatRadio1 = $("<input>").attr({
		"type" : "radio",
		"value" : "ngi",
		"name" : "gitbuilder-modifylayerprop-radio",
		"disabled" : true
	}).prop({
		"checked" : true
	});
	var label1 = $("<label>").addClass("radio-inline").append(this.formatRadio1).append("NGI");

	this.formatRadio2 = $("<input>").attr({
		"type" : "radio",
		"value" : "dxf",
		"name" : "gitbuilder-modifylayerprop-radio",
		"disabled" : true
	});
	var label2 = $("<label>").addClass("radio-inline").append(this.formatRadio2).append("DXF");

	this.formatRadio3 = $("<input>").attr({
		"type" : "radio",
		"value" : "shp",
		"name" : "gitbuilder-modifylayerprop-radio",
		"disabled" : true
	});
	var label3 = $("<label>").addClass("radio-inline").append(this.formatRadio3).append("SHP");
	$(formatArea).append(label1).append(label2).append(label3);

	this.body = $("<div>").append(formatArea).append(this.div1).append(this.layerNameForm).append(this.geomForm).append(this.attrForm)
			.append(this.expertForm);
	// that.setForm("ngi", "layer");
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
		that.close();
		that.refresh();
	});
	$(okBtn).addClass("btn");
	$(okBtn).addClass("btn-primary");
	$(okBtn).text("OK");

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
gb.edit.ModifyLayerProperties.prototype.getLayerRecord = function() {
	return this.layerRecord;
};
gb.edit.ModifyLayerProperties.prototype.setLayerRecord = function(record) {
	this.layerRecord = record;
};
gb.edit.ModifyLayerProperties.prototype.getFeatureRecord = function() {
	return this.featureRecord;
};
gb.edit.ModifyLayerProperties.prototype.setFeatureRecord = function(record) {
	this.featureRecord = record;
};
gb.edit.ModifyLayerProperties.prototype.open = function() {
	this.window.modal('show');
};
gb.edit.ModifyLayerProperties.prototype.close = function() {
	this.window.modal('hide');
};
gb.edit.ModifyLayerProperties.prototype.setRefer = function(refer) {
	this.refer = refer;
};
gb.edit.ModifyLayerProperties.prototype.getRefer = function() {
	return this.refer;
};
gb.edit.ModifyLayerProperties.prototype.refresh = function() {
	this.refer.refresh();
};
gb.edit.ModifyLayerProperties.prototype.setLayer = function(layer) {
	this.layer = layer;
	this.setInformation(layer.get("git").information);
	this.setForm(layer.get("git").information);
	console.log(layer.get("git"));
};
gb.edit.ModifyLayerProperties.prototype.getLayer = function() {
	return this.layer;
};
gb.edit.ModifyLayerProperties.prototype.setInformation = function(info) {
	this.information = info;
};
gb.edit.ModifyLayerProperties.prototype.getInformation = function() {
	return this.information;
};
gb.edit.ModifyLayerProperties.prototype.setForm = function() {
	var info = this.getInformation();
	this.format = info.getFormat();
	this.type = info instanceof gb.layer.LayerInfo ? "layer" : "mapsheet";
	if (this.type === "mapsheet") {
		if (this.format === "dxf") {
			$(this.htag).text("Map sheet Properties(DXF)");
		} else if (this.format === "ngi") {
			$(this.htag).text("Map sheet Properties(NGI)");
		}
		$(this.sheetNumInput).val("");
		$(this.layerNameForm).hide();
		$(this.geomForm).hide();
		$(this.attrForm).hide();
		$(this.expertForm).hide();
	} else if (this.type === "layer") {
		$(this.htag).text("Layer Properties");
		// $(this.sheetNumInput).val(sheetNum);
		$(this.sheetNumInput).hide();
		switch (this.format) {
		case "ngi":
			$(this.formatRadio1).prop("checked", true);
			break;
		case "dxf":
			$(this.formatRadio2).prop("checked", true);
			break;
		case "shp":
			$(this.formatRadio3).prop("checked", true);
			break;
		default:
			break;
		}
		$(this.layerNameInput).val(info.getName());
		$(this.layerNameForm).show();
		if (this.format === "dxf") {
			this.initGeomForm("dxf", info.getGeometry());
			$(this.geomForm).show();
			$(this.attrForm).hide();
			$(this.expertForm).hide();
		} else if (this.format === "ngi") {
			this.initGeomForm("ngi", info.getGeometry());
			$(this.geomForm).show();
			this.initAttrForm(info.getAttributes());
			$(this.attrForm).show();
			this.initExpertForm();
			$(this.expertForm).show();
		}
	}
};
gb.edit.ModifyLayerProperties.prototype.initGeomForm = function(type, selected) {
	var select = $("<select>").prop({
		"disabled" : true
	}).addClass("form-control");
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
	$(select).val(selected);
	$(this.geomForm).empty();
	var tp = $("<p>").text("Type");
	$(this.geomForm).append(tp).append(select);
};
gb.edit.ModifyLayerProperties.prototype.initAttrForm = function(attrs) {
	if (!Array.isArray(attrs)) {
		return;
	}
	var that = this;
	var htd1 = $("<td>").text("Name");
	var htd2 = $("<td>").text("Type");
	var htd3 = $("<td>").text("Not Null");
	var htd4 = $("<td>").text("Unique");
	var thd = $("<thead>").append(htd1).append(htd2).append(htd3).append(htd4);

	this.geomFormBody = $("<tbody>");

	for (var i = 0; i < attrs.length; i++) {
		var key = $("<input>").addClass("form-control").attr({
			"type" : "text"
		}).val(attrs[i].fieldName);
		var td1 = $("<td>").append(key);

		var opt1 = $("<option>").text("Integer");
		var opt2 = $("<option>").text("Double");
		var opt3 = $("<option>").text("String");
		var opt4 = $("<option>").text("Date");
		var opt5 = $("<option>").text("Boolean");
		var type = $("<select>").addClass("form-control").append(opt1).append(opt2).append(opt3).append(opt4).append(opt5).val(
				attrs[i].type).prop("disabled", true);
		var td2 = $("<td>").append(type);

		var nullable = $("<input>").attr({
			"type" : "checkbox"
		}).prop("checked", attrs[i].nullable ? false : true).prop("disabled", true);
		var td3 = $("<td>").append(nullable);

		var unique = $("<input>").attr({
			"type" : "checkbox"
		}).prop("checked", attrs[i].isUnique ? true : false).prop("disabled", true);
		var td4 = $("<td>").append(unique);

		var tr1 = $("<tr>").append(td1).append(td2).append(td3).append(td4);
		$(this.geomFormBody).append(tr1);
	}

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
		var tr1 = $("<tr>").append(td1).append(td2).append(td3).append(td4);
		$(that.geomFormBody).append(tr1);
	});
	$(this.attrForm).empty();
	var tp = $("<p>").text("Attribute");
	$(this.attrForm).append(tp).append(table).append(addBtn);
};
gb.edit.ModifyLayerProperties.prototype.initExpertForm = function() {
	var that = this;
	var info = this.getInformation();
	var htd1 = $("<td>").text("Version");
	var htd2 = $("<td>").text("Dimension");
	var htd3 = $("<td>").text("Represent");
	var thd = $("<thead>").append(htd1).append(htd2).append(htd3);

	var veropt1 = $("<option>").text("1");
	var veropt2 = $("<option>").text("2");
	this.ver = $("<select>").addClass("form-control").append(veropt1).append(veropt2).val(info.getNGIVersion());
	var td1 = $("<td>").append(this.ver);

	var dimopt1 = $("<option>").text("2");
	var dimopt2 = $("<option>").text("3");
	this.dim = $("<select>").addClass("form-control").append(dimopt1).append(dimopt2).val(info.getNGIDim());
	var td2 = $("<td>").append(this.dim);

	this.rep = $("<input>").addClass("form-control").attr({
		"type" : "text"
	}).val(info.getNGIRep());
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
	}).val(info.getMbound()[0][0]);
	var td12 = $("<td>").append(this.minx);
	this.miny = $("<input>").addClass("form-control").attr({
		"type" : "text"
	}).val(info.getMbound()[0][1]);
	var td22 = $("<td>").append(this.miny);
	this.maxx = $("<input>").addClass("form-control").attr({
		"type" : "text"
	}).val(info.getMbound()[1][0]);
	var td32 = $("<td>").append(this.maxx);
	this.maxy = $("<input>").addClass("form-control").attr({
		"type" : "text"
	}).val(info.getMbound()[1][1]);
	var td42 = $("<td>").append(this.maxy);

	var tr12 = $("<tr>").append(td12).append(td22).append(td32).append(td42);

	this.expertFormBodyUnder = $("<tbody>").append(tr12);

	var table2 = $("<table>").addClass("table").addClass("text-center").append(thd2).append(this.expertFormBodyUnder);

	$(this.expertForm).empty();
	var tp = $("<p>").text("NGI Setting");
	$(this.expertForm).append(tp).append(table).append(table2);
};
gb.edit.ModifyLayerProperties.prototype.getDefinitionForm = function() {
	if (this.type === "mapsheet") {

	} else if (this.type === "layer") {
		if ($(this.layerNameInput).val().replace(/(\s*)/g, '') === "") {
			console.error("no layer name");
			return;
		}
		var layer = this.getLayer();
		var oldLayerId = layer.get("id");
		var info = layer.get("git").information;

		if (info.getName() !== $(this.layerNameInput).val().replace(/(\s*)/g, '')) {
			info.setName($(this.layerNameInput).val().replace(/(\s*)/g, ''));
			layer.set("id", info.updateId());
			layer.set("name", $(this.layerNameInput).val().replace(/(\s*)/g, ''));
		}

		if (info.getMbound()[0][0] !== $(this.minx).val().replace(/(\s*)/g, '') || info.getMbound()[0][1] !== $(this.miny).val()
				|| info.getMbound()[1][0] !== $(this.maxx).val() || info.getMbound()[1][0] !== $(this.maxy).val()) {
			info.setMbound([ [ $(this.minx).val(), $(this.miny).val() ], [ $(this.maxx).val(), $(this.maxy).val() ] ]);
		}

		if (info.getNGIDim() !== $(this.dim).val().replace(/(\s*)/g, '')) {
			info.setNGIDim($(this.dim).val().replace(/(\s*)/g, ''));
		}

		if (info.getNGIVersion() !== $(this.ver).val().replace(/(\s*)/g, '')) {
			info.setNGIVersion($(this.ver).val().replace(/(\s*)/g, ''));
		}

		if (info.getNGIRep() !== $(this.rep).val().replace(/(\s*)/g, '')) {
			info.setNGIRep($(this.rep).val().replace(/(\s*)/g, ''));
		}

		if (info.getFormat() === "ngi") {
			var attrs = $(this.attrForm).find("tr");
			var oattrs = info.getAttributes();
			for (var i = 0; i < attrs.length; i++) {
				if ($(attrs[i]).children().eq(0).find("input:text").val().replace(/(\s*)/g, '') === "") {
					console.error("blank key name");
					break;
				}

				var oattr = oattrs[i];
				if (!oattr) {
					var nattr = new gb.layer.Attribute({
						originFieldName : $(attrs[i]).children().eq(0).find("input:text").val().replace(/(\s*)/g, ''),
						fieldName : $(attrs[i]).children().eq(0).find("input:text").val().replace(/(\s*)/g, ''),
						type : $(attrs[i]).children().eq(1).find("select").val(),
						decimal : $(attrs[i]).children().eq(1).find("select").val() === "Double" ? 30 : null,
						size : 256,
						isUnique : $(attrs[i]).children().eq(3).find("input:checkbox").prop("checked") ? true : false,
						nullable : $(attrs[i]).children().eq(2).find("input:checkbox").prop("checked") ? false : true,
						isNew : true
					});
					info.setAttribute(nattr);
				} else {
					if (oattr.getFieldName() !== $(attrs[i]).children().eq(0).find("input:text").val().replace(/(\s*)/g, '')) {
						oattr.setFieldName($(attrs[i]).children().eq(0).find("input:text").val().replace(/(\s*)/g, ''));
					}
				}
				layer.get("git").attribute = info.getAttributesJSON();
			}
		}
		this.getLayerRecord().update(info.getFormat(), info.getSheetNumber(), layer, oldLayerId);
	}
};
gb.edit.ModifyLayerProperties.prototype.getImageTileInfo = function(url, layer) {
	var that = this;
	var arr = {
		"geoLayerList" : [ layer.get("id") ]
	}
	var names = [];
	// console.log(JSON.stringify(arr));
	var info;
	$.ajax({
		url : url,
		method : "POST",
		contentType : "application/json; charset=UTF-8",
		cache : false,
		data : JSON.stringify(arr),
		beforeSend : function() { // 호출전실행
			$("body").css("cursor", "wait");
		},
		complete : function() {
			$("body").css("cursor", "default");
		},
		traditional : true,
		success : function(data2, textStatus, jqXHR) {
			console.log(data2);
			if (Array.isArray(data2)) {
				if (data2.length === 1) {
					var arra = [];
					info = new gb.layer.LayerInfo({
						oldName : that.getRefer().get_node(data2[0].lName).text,
						id : data2[0].lName,
						sheetNum : that.getRefer().get_node(refer.get_node(data2[0].lName).parent).text,
						attributes : 5,
						format : 6,
						epsg : 7,
						ngi : {
							"version" : 8,
							"dim" : 9,
							"represent" : 10
						},
						mbound : 11,
						lbound : 12,
						isNew : 13,
						geometry : 14
					});
//					var wms = new ol.layer.Tile({
//						source : new ol.source.TileWMS({
//							url : that._data.geoserver.WMSLayerURL,
//							params : {
//								'LAYERS' : data2[0].lName,
//								'TILED' : true,
//								'FORMAT' : 'image/png8',
//								'VERSION' : '1.0.0',
//								'CRS' : 'EPSG:5186',
//								'SRS' : 'EPSG:5186',
//								'BBOX' : data2[0].nbBox.minx.toString() + "," + data2[0].nbBox.miny.toString() + ","
//										+ data2[0].nbBox.maxx.toString() + "," + data2[0].nbBox.maxy.toString()
//							},
//							serverType : 'geoserver'
//						})
//					});
					// var git = {
					// "validation" : false,
					// "geometry" : data2[0].geomType,
					// "editable" : true,
					// "attribute" : data2[0].attInfo,
					// "fake" : "child"
					// }
					// wms.set("name", obj.refer.get_node(data2[i].lName).text);
					// wms.set("id", data2[i].lName);
					// // wms.setVisible(false);
					// console.log(wms.get("id"));
					// // wms.set("type", "ImageTile");
					// wms.set("git", git);
					// arra.push(wms);
					// console.log(wms);
				}

				$("body").css("cursor", "default");
			}
		}
	});
};