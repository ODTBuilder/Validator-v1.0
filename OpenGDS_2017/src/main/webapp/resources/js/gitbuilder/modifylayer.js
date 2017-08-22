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
gb.geoserver.ModifyLayer = function(obj) {
	var that = this;
	var options = obj;
	this.infoURL = options.infoURL ? options.infoURL : null;
	this.editURL = options.editURL ? options.editURL : null;
	this.layer;
	this.window;
	this.originInfo = {};
	this.currentInfo = {};
	this.currentInfo = {};
	this.sendObj = {};
	var xSpan = $("<span>").attr({
		"aria-hidden" : true
	}).html("&times;");
	var xButton = $("<button>").attr({
		"type" : "button",
		"data-dismiss" : "modal",
		"aria-label" : "Close"
	}).html(xSpan);
	$(xButton).addClass("close");

	var htag = $("<h4>");
	htag.text("Layer Properties");
	$(htag).addClass("modal-title");

	var header = $("<div>").append(xButton).append(htag);
	$(header).addClass("modal-header");
	/*
	 * 
	 * 
	 * header end
	 * 
	 * 
	 */

	// var name = $("<p>").text("Name");
	// var nameInput = $("<input>").addClass("form-control").attr({
	// "type" : "text"
	// });
	//
	// var title = $("<p>").text("Title");
	// var titleInput = $("<input>").addClass("form-control").attr({
	// "type" : "text"
	// });
	//
	// var summary = $("<p>").text("Summary");
	// var summaryInput = $("<textarea>").addClass("form-control").attr({
	// "rows" : "3"
	// });
	//
	// var minBound = $("<p>").text("Minimum Boundary of Original Data");
	//
	// var bminx = $("<p>").text("Min X");
	// var td1 = $("<td>").append(bminx);
	//
	// var bminy = $("<p>").text("Min Y");
	// var td2 = $("<td>").append(bminy);
	//
	// var bmaxx = $("<p>").text("Max X");
	// var td3 = $("<td>").append(bmaxx);
	//
	// var bmaxy = $("<p>").text("Max Y");
	// var td4 = $("<td>").append(bmaxy);
	//
	// var tr1 = $("<tr>").append(td1).append(td2).append(td3).append(td4);
	//
	// var bminx2 = $("<input>").addClass("form-control").attr({
	// "type" : "text"
	// });
	// var td11 = $("<td>").append(bminx2);
	//
	// var bminy2 = $("<input>").addClass("form-control").attr({
	// "type" : "text"
	// });
	// var td22 = $("<td>").append(bminy2);
	//
	// var bmaxx2 = $("<input>").addClass("form-control").attr({
	// "type" : "text"
	// });
	// var td33 = $("<td>").append(bmaxx2);
	//
	// var bmaxy2 = $("<input>").addClass("form-control").attr({
	// "type" : "text"
	// });
	// var td44 = $("<td>").append(bmaxy2);
	//
	// var tr11 = $("<tr>").append(td11).append(td22).append(td33).append(td44);
	//
	// var tb1 = $("<table>").addClass("table").append(tr1).append(tr11);
	//
	// var lonlatBound = $("<p>").text("Latitude / Longitude Area");
	//
	// var bminx1 = $("<p>").text("Min X");
	// var td111 = $("<td>").append(bminx1);
	//
	// var bminy1 = $("<p>").text("Min Y");
	// var td222 = $("<td>").append(bminy1);
	//
	// var bmaxx1 = $("<p>").text("Max X");
	// var td333 = $("<td>").append(bmaxx1);
	//
	// var bmaxy1 = $("<p>").text("Max Y");
	// var td444 = $("<td>").append(bmaxy1);
	//
	// var tr111 =
	// $("<tr>").append(td111).append(td222).append(td333).append(td444);
	//
	// var bminx3 = $("<input>").addClass("form-control").attr({
	// "type" : "text"
	// });
	// var td1111 = $("<td>").append(bminx3);
	//
	// var bminy3 = $("<input>").addClass("form-control").attr({
	// "type" : "text"
	// });
	// var td2222 = $("<td>").append(bminy3);
	//
	// var bmaxx3 = $("<input>").addClass("form-control").attr({
	// "type" : "text"
	// });
	// var td3333 = $("<td>").append(bmaxx3);
	//
	// var bmaxy3 = $("<input>").addClass("form-control").attr({
	// "type" : "text"
	// });
	// var td4444 = $("<td>").append(bmaxy3);
	//
	// var tr1111 =
	// $("<tr>").append(td1111).append(td2222).append(td3333).append(td4444);
	//
	// var tb2 = $("<table>").addClass("table").append(tr111).append(tr1111);
	//
	// var ftb = $("<table>");
	//
	// this.body =
	// $("<div>").append(name).append(nameInput).append(title).append(titleInput).append(summary).append(summaryInput).append(minBound).append(tb1).append(
	// lonlatBound).append(tb2);
	this.body = $("<div>");
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
		console.log("save");
		var obj2 = that.getStructure();
		console.log(obj2);
		that.save(obj2);
		that.close();
	});
	$(okBtn).addClass("btn");
	$(okBtn).addClass("btn-primary");
	$(okBtn).text("Save");

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
gb.geoserver.ModifyLayer.prototype.open = function() {
	this.window.modal('show');
};
gb.geoserver.ModifyLayer.prototype.close = function() {
	this.window.modal('hide');
};
gb.geoserver.ModifyLayer.prototype.save = function(obj) {
	var that = this;
	$.ajax({
		url : this.getEditURL(),
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
gb.geoserver.ModifyLayer.prototype.setReference = function(refer) {
	this.refer = refer;
};
gb.geoserver.ModifyLayer.prototype.getReference = function() {
	return this.refer;
};
gb.geoserver.ModifyLayer.prototype.getStructure = function() {
	this.getInformationForm();
	return this.structure;
};
gb.geoserver.ModifyLayer.prototype.getPosition = function(str, subString, index) {
	return str.split(subString, index).join(subString).length;
};
gb.geoserver.ModifyLayer.prototype.load = function(name, code) {
	var that = this;
	this.structure = {};
	var format = name.substring(this.getPosition(name, "_", 1) + 1, this.getPosition(name, "_", 2));
	var sheetNum = name.substring(this.getPosition(name, "_", 2) + 1, this.getPosition(name, "_", 3));
	this.structure["layer"] = {};
	this.structure["layer"][format] = {};
	this.structure["layer"][format][sheetNum] = {};
	this.structure["layer"][format][sheetNum]["modify"] = [ this.sendObj ];

	this.originInfo["nativeName"] = name;
	// this.originInfo["originLayerName"] =
	// name.substring(this.getPosition(name, "_", 3) + 1);
	this.originInfo["geoserver"] = {};
	this.originInfo["attr"] = [];
	var arr = {
		"geoLayerList" : [ name ]
	}
	console.log(JSON.stringify(arr));
	$.ajax({
		url : that.getInfoURL(),
		method : "POST",
		contentType : "application/json; charset=UTF-8",
		cache : false,
		// async : false,
		data : JSON.stringify(arr),
		beforeSend : function() {
			$("body").css("cursor", "wait");
		},
		complete : function() {
			$("body").css("cursor", "default");
		},
		traditional : true,
		success : function(data, textStatus, jqXHR) {
			console.log(data);
			if (data.length !== 1) {
				return;
			}
			var name = $("<p>").text("Name");
			that.nameInput = $("<input>").addClass("form-control").attr({
				"type" : "text"
			}).val(code).on("input", function() {
				that.currentInfo["currentLayerName"] = this.value;
			});
			that.sendObj["nativeName"] = data[0].nativeName;
			that.originInfo["code"] = code;
			that.originInfo["geomType"] = data[0].geomType;
			that.originInfo["originLayerName"] = code + "_" + (data[0].geomType.toUpperCase());
			var div1 = $("<div>").css("margin-bottom", "10px").append(name).append(that.nameInput);

			var title = $("<p>").text("Title");
			that.titleInput = $("<input>").addClass("form-control").attr({
				"type" : "text"
			}).val(data[0].title).on("input", function() {
				if (!that.currentInfo.hasOwnProperty("geoserver")) {
					that.currentInfo["geoserver"] = {};
				}
				that.currentInfo["geoserver"]["title"] = this.value;
			});
			that.originInfo["geoserver"]["title"] = data[0].title;
			var div2 = $("<div>").css("margin-bottom", "10px").append(title).append(that.titleInput);

			var summary = $("<p>").text("Summary");
			that.summaryInput = $("<textarea>").addClass("form-control").attr({
				"rows" : "3"
			}).text(data[0].abstractContent).on("input", function() {
				if (!that.currentInfo.hasOwnProperty("geoserver")) {
					that.currentInfo["geoserver"] = {};
				}
				that.currentInfo["geoserver"]["summary"] = $(this).val();
			});
			that.originInfo["geoserver"]["summary"] = data[0].abstractContent;
			var div3 = $("<div>").css("margin-bottom", "10px").append(summary).append(that.summaryInput);

			var minBound = $("<p>").text("Minimum Boundary of Original Data");

			var td1 = $("<td>").text("Min X");

			var td2 = $("<td>").text("Min Y");

			var td3 = $("<td>").text("Max X");

			var td4 = $("<td>").text("Max Y");

			var tr1 = $("<thead>").append(td1).append(td2).append(td3).append(td4);

			that.bminx2 = $("<input>").addClass("form-control").attr({
				"type" : "text",
				"disabled" : true
			}).val(data[0].nbBox.minx);
			var td11 = $("<td>").append(that.bminx2);

			that.bminy2 = $("<input>").addClass("form-control").attr({
				"type" : "text",
				"disabled" : true
			}).val(data[0].nbBox.miny);
			var td22 = $("<td>").append(that.bminy2);

			that.bmaxx2 = $("<input>").addClass("form-control").attr({
				"type" : "text",
				"disabled" : true
			}).val(data[0].nbBox.maxx);
			var td33 = $("<td>").append(that.bmaxx2);

			that.bmaxy2 = $("<input>").addClass("form-control").attr({
				"type" : "text",
				"disabled" : true
			}).val(data[0].nbBox.maxy);
			var td44 = $("<td>").append(that.bmaxy2);

			var tr11 = $("<tr>").append(td11).append(td22).append(td33).append(td44);
			var tbd = $("<tbody>").append(tr11);
			var tb1 = $("<table>").addClass("table").addClass("text-center").append(tr1).append(tbd);
			var div4 = $("<div>").css("margin-bottom", "10px").append(minBound).append(tb1);

			var lonlatBound = $("<p>").text("Latitude / Longitude Area");

			var td111 = $("<td>").text("Min X");

			var td222 = $("<td>").text("Min Y");

			var td333 = $("<td>").text("Max X");

			var td444 = $("<td>").text("Max Y");

			var tr111 = $("<thead>").append(td111).append(td222).append(td333).append(td444);

			that.bminx3 = $("<input>").addClass("form-control").attr({
				"type" : "text",
				"disabled" : true
			}).val(data[0].llbBox.minx);

			var td1111 = $("<td>").append(that.bminx3);

			that.bminy3 = $("<input>").addClass("form-control").attr({
				"type" : "text",
				"disabled" : true
			}).val(data[0].llbBox.miny);
			var td2222 = $("<td>").append(that.bminy3);

			that.bmaxx3 = $("<input>").addClass("form-control").attr({
				"type" : "text",
				"disabled" : true
			}).val(data[0].llbBox.maxx);
			var td3333 = $("<td>").append(that.bmaxx3);

			that.bmaxy3 = $("<input>").addClass("form-control").attr({
				"type" : "text",
				"disabled" : true
			}).val(data[0].llbBox.maxy);
			var td4444 = $("<td>").append(that.bmaxy3);

			that.originInfo["bound"] = [ [ data[0].nbBox.minx, data[0].nbBox.miny ], [ data[0].nbBox.maxx, data[0].nbBox.maxy ] ];
			that.originInfo["geoserver"]["nbBox"] = [ [ data[0].nbBox.minx, data[0].nbBox.miny ],
					[ data[0].nbBox.maxx, data[0].nbBox.maxy ] ];
			that.originInfo["geoserver"]["llbBox"] = [ [ data[0].llbBox.minx, data[0].llbBox.miny ],
					[ data[0].llbBox.maxx, data[0].llbBox.maxy ] ];

			var tr1111 = $("<tr>").append(td1111).append(td2222).append(td3333).append(td4444);
			var tbd2 = $("<tbody>").append(tr1111);
			var tb2 = $("<table>").addClass("table").addClass("text-center").append(tr111).append(tbd2);
			var div5 = $("<div>").css("margin-bottom", "10px").append(lonlatBound).append(tb2);

			var attrkey = $("<p>").text("Attributes");
			var thtd1 = $("<td>").text("Name");
			var thtd2 = $("<td>").text("Type");
			var thtd3 = $("<td>").text("Nullable");
//			var thtd4 = $("<td>").text("Unique");
			var thead = $("<thead>").append(thtd1).append(thtd2).append(thtd3);
			var tbody = $("<tbody>");
			var fttb = $("<table>").addClass("table").addClass("text-center").append(thead).append(tbody);
			var keys = Object.keys(data[0].attInfo).sort();
			for (var i = 0; i < keys.length; i++) {
				var input;
				if (format === "ngi") {
					if (keys[i] === "f_idx" || keys[i] === "feature_id" || keys[i] === "feature_type" || keys[i] === "geom"
							|| keys[i] === "num_rings" || keys[i] === "num_vertexes" || keys[i] === "style_id") {
						input = $("<input>").addClass("form-control").attr({
							"type" : "text",
							"disabled" : "disabled"
						}).val(keys[i]);
					} else {
						input = $("<input>").addClass("form-control").attr({
							"type" : "text"
						}).val(keys[i]).on("input", function() {
							if (!that.sendObj.hasOwnProperty("updateAttr")) {
								that.sendObj["updateAttr"] = [];
							}
							var trs = $(this).parent().parent().parent().find("tr");
							var idx = $(trs).index($(this).parent().parent());
							var oattr = that.originInfo["attr"][idx];

							console.log(idx);
							if (that.sendObj["updateAttr"].length === 0) {
								var obj = {
									"originFieldName" : oattr.originFieldName,
									"fieldName" : $(this).val(),
									"type" : oattr.type,
									"nullable" : oattr.nullable
								};
								that.sendObj["updateAttr"].push(obj);
							} else if (that.sendObj["updateAttr"].length > 0) {
								var isExist = true;
								for (var j = 0; j < that.sendObj["updateAttr"].length; j++) {
									var attr = that.sendObj["updateAttr"][j];
									if (attr.originFieldName === oattr.originFieldName) {
										var obj = {
											"originFieldName" : oattr.originFieldName,
											"fieldName" : $(this).val(),
											"type" : oattr.type,
											"nullable" : oattr.nullable
										};
										that.sendObj["updateAttr"][j] = obj;
										isExist = false;
										break;
									}
								}
								if (isExist) {
									var obj = {
										"originFieldName" : oattr.originFieldName,
										"fieldName" : $(this).val(),
										"type" : oattr.type,
										"nullable" : oattr.nullable
									};
									that.sendObj["updateAttr"].push(obj);
								}
							}
							console.log(that.sendObj);
							// that.sendObj["geoserver"]["title"] = this.value;
						});
					}
				} else if (format === "dxf") {
					input = $("<input>").addClass("form-control").attr({
						"type" : "text",
						"disabled" : "disabled"
					}).val(keys[i]);
				} else {
					input = $("<input>").addClass("form-control").attr({
						"type" : "text"
					}).val(keys[i]).on("input", function() {
						if (!that.sendObj.hasOwnProperty("updateAttr")) {
							that.sendObj["updateAttr"] = [];
						}
						var trs = $(this).parent().parent().parent().find("tr");
						var idx = $(trs).index($(this).parent().parent());
						var oattr = that.originInfo["attr"][idx];

						console.log(idx);
						if (that.sendObj["updateAttr"].length === 0) {
							var obj = {
								"originFieldName" : oattr.originFieldName,
								"fieldName" : $(this).val(),
								"type" : oattr.type,
								"nullable" : oattr.nullable
							};
							that.sendObj["updateAttr"].push(obj);
						} else if (that.sendObj["updateAttr"].length > 0) {
							var isExist = true;
							for (var j = 0; j < that.sendObj["updateAttr"].length; j++) {
								var attr = that.sendObj["updateAttr"][j];
								if (attr.originFieldName === oattr.originFieldName) {
									var obj = {
										"originFieldName" : oattr.originFieldName,
										"fieldName" : $(this).val(),
										"type" : oattr.type,
										"nullable" : oattr.nullable
									};
									that.sendObj["updateAttr"][j] = obj;
									isExist = false;
									break;
								}
							}
							if (isExist) {
								var obj = {
									"originFieldName" : oattr.originFieldName,
									"fieldName" : $(this).val(),
									"type" : oattr.type,
									"nullable" : oattr.nullable
								};
								that.sendObj["updateAttr"].push(obj);
							}
						}
						console.log(that.sendObj);
						// that.sendObj["geoserver"]["title"] = this.value;
					});
				}
				var td1 = $("<td>").append(input);

				var stritem = $("<option>").text("String");
				var dblitem = $("<option>").text("Double");
				var intitem = $("<option>").text("Integer");
				var select = $("<select>").attr({
					"disabled" : true
				}).addClass("form-control").append(stritem).append(dblitem).append(intitem).val(data[0].attInfo[keys[i]]["type"]);
				var td2 = $("<td>").append(select);

				var check = $("<input>").attr({
					"type" : "checkbox",
					"disabled" : true
				});
				if (data[0].attInfo[keys[i]]["nillable"] === "true") {
					$(check).prop("checked", true);
				}
				var td3 = $("<td>").append(check);

//				var ucheck = $("<input>").attr({
//					"type" : "checkbox",
//					"disabled" : true
//				});
//				var td4 = $("<td>").append(ucheck);

				var tr = $("<tr>").append(td1).append(td2).append(td3);
				$(tbody).append(tr);
				var attrObj = {
					"originFieldName" : keys[i],
					"type" : data[0].attInfo[keys[i]]["type"],
					"nullable" : data[0].attInfo[keys[i]]["nillable"] === "true" ? true : false
				};
				that.originInfo["attr"].push(attrObj);
			}
			var div6 = $("<div>").css("margin-bottom", "10px").append(attrkey).append(fttb);
			$(that.body).empty();
			$(that.body).append(div1).append(div2).append(div3).append(div4).append(div5).append(div6);
			// $(body).addClass("modal-body");
			console.log(that.originInfo);
			that.open();
		}
	});
};
gb.geoserver.ModifyLayer.prototype.getInformationForm = function() {
	this.currentInfo["code"] = $(this.nameInput).val().replace(/(\s*)/g, '');
	this.currentInfo["currentLayerName"] = (this.currentInfo["code"] + "_" + (this.originInfo["geomType"].toUpperCase())).replace(/(\s*)/g,
			'');
	this.currentInfo["geoserver"] = {};
	this.currentInfo["geoserver"]["title"] = $(this.titleInput).val();
	if (this.currentInfo["geoserver"]["title"] !== this.originInfo["geoserver"]["title"]) {
		if (!this.sendObj.hasOwnProperty("geoserver")) {
			this.sendObj["geoserver"] = {};
		}
		this.sendObj["geoserver"]["title"] = this.currentInfo["geoserver"]["title"];
	}

	this.currentInfo["geoserver"]["summary"] = $(this.summaryInput).val();

	if (this.currentInfo["geoserver"]["summary"] !== this.originInfo["geoserver"]["summary"]) {
		if (!this.sendObj.hasOwnProperty("geoserver")) {
			this.sendObj["geoserver"] = {};
		}
		this.sendObj["geoserver"]["summary"] = this.currentInfo["geoserver"]["summary"];
	}

	this.currentInfo["geoserver"]["nbBox"] = [
			[ parseFloat($(this.bminx2).val().replace(/(\s*)/g, '')), parseFloat($(this.bminy2).val().replace(/(\s*)/g, '')) ],
			[ parseFloat($(this.bmaxx2).val().replace(/(\s*)/g, '')), parseFloat($(this.bmaxy2).val().replace(/(\s*)/g, '')) ] ];
	this.currentInfo["geoserver"]["llbBox"] = [
			[ parseFloat($(this.bminx3).val().replace(/(\s*)/g, '')), parseFloat($(this.bminy3).val().replace(/(\s*)/g, '')) ],
			[ parseFloat($(this.bmaxx3).val().replace(/(\s*)/g, '')), parseFloat($(this.bmaxy3).val().replace(/(\s*)/g, '')) ] ];
	this.currentInfo["bound"] = this.currentInfo["geoserver"]["nbBox"];
	if (this.currentInfo["code"] !== this.originInfo["code"]) {
		this.sendObj["currentLayerName"] = this.currentInfo["currentLayerName"];
	}
	this.sendObj["originLayerName"] = this.originInfo["originLayerName"];
	for (var i = 0; i < this.currentInfo["geoserver"]["nbBox"].length; i++) {
		var celem = this.currentInfo["geoserver"]["nbBox"][i];
		var oelem = this.originInfo["geoserver"]["nbBox"][i];
		var llelem = this.originInfo["geoserver"]["llbBox"][i];
		var cllelem = this.currentInfo["geoserver"]["llbBox"][i];
		for (var j = 0; j < celem.length; j++) {
			if (celem[j] !== oelem[j]) {
				this.sendObj["bound"] = this.currentInfo["geoserver"]["nbBox"];
				if (!this.sendObj.hasOwnProperty("geoserver")) {
					this.sendObj["geoserver"] = {};
				}
				this.sendObj["geoserver"]["nbBox"] = this.currentInfo["geoserver"]["nbBox"];
			}
			if (llelem[j] !== cllelem[j]) {
				if (!this.sendObj.hasOwnProperty("geoserver")) {
					this.sendObj["geoserver"] = {};
				}
				this.sendObj["geoserver"]["llbBox"] = this.currentInfo["geoserver"]["llbBox"];
			}
		}
	}
	console.log(this.sendObj);
	return this.sendObj;
};
gb.geoserver.ModifyLayer.prototype.setInfoURL = function(url) {
	if (typeof url === "string") {
		this.infoURL = url;
	}
};
gb.geoserver.ModifyLayer.prototype.getInfoURL = function() {
	return this.infoURL;
};
gb.geoserver.ModifyLayer.prototype.setEditURL = function(url) {
	if (typeof url === "string") {
		this.editURL = url;
	}
};
gb.geoserver.ModifyLayer.prototype.getEditURL = function() {
	return this.editURL;
};
gb.geoserver.ModifyLayer.prototype.setName = function(name) {
	return;
};
gb.geoserver.ModifyLayer.prototype.setTitle = function(title) {
	return;
};
gb.geoserver.ModifyLayer.prototype.setAttributeType = function() {
	return;
};