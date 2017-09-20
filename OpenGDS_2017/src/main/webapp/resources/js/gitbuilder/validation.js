/**
 * 검수 수행창 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2017. 04
 * @version 0.01
 */
var gitbuilder;
if (!gitbuilder)
	gitbuilder = {};
if (!gitbuilder.ui)
	gitbuilder.ui = {};
gitbuilder.ui.Validation = $.widget("gitbuilder.validation", {
	widnow : undefined,
	webSocket : undefined,
	sendValiDefFlag : false,
	valiDef : undefined,
	optDef : undefined,
	layerDef : undefined,
	weightDef : undefined,
	tbody : undefined,
	message : undefined,
	file : undefined,
	tree : undefined,
	ws : false,
	wsfirst : true,
	fileType : undefined,
	info : undefined,
	bar : undefined,
	warning : undefined, 
	okBtn : undefined,
	options : {
		layerDefinition : undefined,
		optionDefinition : undefined,
		weightDefinition : undefined,
		validatorURL : undefined,
		layersURL : undefined,
		appendTo : "body"
	},
	_create : function() {
		this.layerDef = this.options.layerDefinition;
		this.optDef = this.options.optionDefinition;
		this.weightDef = this.options.weightDefinition;

		var that = this;

		this._on(false, this.element, {
			click : function(event) {
				if (event.target === that.element[0]) {
					that.open();
				}
			}
		});
		var xSpan = $("<span>").attr({
			"aria-hidden" : true
		}).html("&times;");
		var xButton = $("<button>").attr({
			"type" : "button",
			"data-dismiss" : "modal",
			"aria-label" : "Close"
		}).html(xSpan);
		this._addClass(xButton, "close");

		var htag = $("<h4>");
		htag.text("Validation");
		this._addClass(htag, "modal-title");

		var header = $("<div>").append(xButton).append(htag);
		this._addClass(header, "modal-header");
		/*
		 * 
		 * 
		 * header end
		 * 
		 * 
		 */
		var icls = $("<i>").addClass("fa").addClass("fa-refresh").attr({
			"aria-hidden" : "true"
		});
		var refBtn = $("<button>").addClass("pull-right").addClass("gitbuilder-clearbtn").append(icls).click(function(){
			$(that.tree).jstree("refresh");
		});
		var listhead = $("<div>").append("Layer List").append(refBtn);
		this._addClass(listhead, "panel-heading");
		this.tree = $("<div>").attr({
			"id" : "gitlayers"
		});
		var listbody = $("<div>").css({
			"min-height" : "260px",
			"max-height" : "500px",
			"padding" : 0,
			"overflow-y" : "auto"
		}).append(this.tree);
		this._addClass(listbody, "panel-body");
		$(this.tree).jstree({
			"core" : {
				"animation" : 0,
				"check_callback" : true,
				"themes" : {
					"stripes" : true
				},
				'data' : {
					'url' : function() {
						return that.options.layersURL;
					}
				}
			},
			"plugins" : [ "search", "types" ]
		});
		$(this.tree).on('deselect_node.jstree', function(e, data) {
			var r = [];
			for (var i = 0; i < data.selected.length; i++) {
				if (data.instance.get_node(data.selected[i]).type === "n_ngi_group" || data.instance.get_node(data.selected[i]).type === "n_dxf_group" || data.instance.get_node(data.selected[i]).type === "n_shp_group") {
					if (r.length === 0 && data.instance.get_node(data.selected[i]).type === "n_ngi_group") {
						r.push(data.instance.get_node(data.selected[i]));
						that.fileType = "ngi";
						continue;
					} else if (r.length === 0 && data.instance.get_node(data.selected[i]).type === "n_dxf_group") {
						r.push(data.instance.get_node(data.selected[i]));
						that.fileType = "dxf";
						continue;
					}else if (r.length === 0 && data.instance.get_node(data.selected[i]).type === "n_shp_group") {
						r.push(data.instance.get_node(data.selected[i]));
						that.fileType = "shp";
						continue;
					}

					if (r.length > 0 && that.fileType === "ngi" && data.instance.get_node(data.selected[i]).type === "n_ngi_group") {
						r.push(data.instance.get_node(data.selected[i]));
						continue;
					} else if (r.length > 0 && that.fileType === "dxf" && data.instance.get_node(data.selected[i]).type === "n_dxf_group") {
						r.push(data.instance.get_node(data.selected[i]));
						continue;
					}else if (r.length > 0 && that.fileType === "shp" && data.instance.get_node(data.selected[i]).type === "n_shp_group") {
						r.push(data.instance.get_node(data.selected[i]));
						continue;
					}
					if(that.fileType === "ngi" && data.instance.get_node(data.selected[i]).type === "n_dxf_group") {
						data.instance.deselect_node(data.instance.get_node(data.selected[i]));
					} else if(that.fileType === "dxf" && data.instance.get_node(data.selected[i]).type === "n_ngi_group") {
						data.instance.deselect_node(data.instance.get_node(data.selected[i]));
					}else if(that.fileType === "shp" && data.instance.get_node(data.selected[i]).type === "n_shp_group") {
						data.instance.deselect_node(data.instance.get_node(data.selected[i]));
					}
				} else {
					data.instance.deselect_node(data.instance.get_node(data.selected[i]));
					that.updateLayerList(r);
				}
			}
// if (r.length > 0) {
			that.updateValidationDef(r);
			that.updateLayerList(r);
// }
		});
		$(this.tree).on('select_node.jstree', function(e, data) {
			var r = [];
			for (var i = 0; i < data.selected.length; i++) {
				if (data.instance.get_node(data.selected[i]).type === "n_ngi_group" || data.instance.get_node(data.selected[i]).type === "n_dxf_group"|| data.instance.get_node(data.selected[i]).type === "n_shp_group") {
					if (r.length === 0 && data.instance.get_node(data.selected[i]).type === "n_ngi_group") {
						r.push(data.instance.get_node(data.selected[i]));
						that.fileType = "ngi";
						continue;
					} else if (r.length === 0 && data.instance.get_node(data.selected[i]).type === "n_dxf_group") {
						r.push(data.instance.get_node(data.selected[i]));
						that.fileType = "dxf";
						continue;
					} else if (r.length === 0 && data.instance.get_node(data.selected[i]).type === "n_shp_group") {
						r.push(data.instance.get_node(data.selected[i]));
						that.fileType = "shp";
						continue;
					} 

					if (r.length > 0 && that.fileType === "ngi" && data.instance.get_node(data.selected[i]).type === "n_ngi_group") {
						r.push(data.instance.get_node(data.selected[i]));
						continue;
					} else if (r.length > 0 && that.fileType === "dxf" && data.instance.get_node(data.selected[i]).type === "n_dxf_group") {
						r.push(data.instance.get_node(data.selected[i]));
						continue;
					}else if (r.length > 0 && that.fileType === "shp" && data.instance.get_node(data.selected[i]).type === "n_shp_group") {
						r.push(data.instance.get_node(data.selected[i]));
						continue;
					}
					
					if(that.fileType === "ngi" && data.instance.get_node(data.selected[i]).type === "n_dxf_group") {
						data.instance.deselect_node(data.instance.get_node(data.selected[i]));
					} else if(that.fileType === "dxf" && data.instance.get_node(data.selected[i]).type === "n_ngi_group") {
						data.instance.deselect_node(data.instance.get_node(data.selected[i]));
					}else if(that.fileType === "shp" && data.instance.get_node(data.selected[i]).type === "n_shp_group") {
						data.instance.deselect_node(data.instance.get_node(data.selected[i]));
					}
				} else {
					data.instance.deselect_node(data.instance.get_node(data.selected[i]));
					that.updateLayerList(r);
				}
			}
// if (r.length > 0) {
			that.updateValidationDef(r);
			that.updateLayerList(r);
// }
		});
		var layerlist = $("<div>").append(listhead).append(listbody);
		this._addClass(layerlist, "panel");
		this._addClass(layerlist, "panel-default");
		var uleft = $("<div>").append(layerlist);
		this._addClass(uleft, "col-md-5");

		var infohead = $("<div>").text("Information");
		this._addClass(infohead, "panel-heading");
		this.bar = $("<div>").addClass("progress-bar").attr({
			"role" : "progressbar",
			"aria-valuemin" : 0,
			"aria-valuemax" : 100,
			"aria-valuenow" : 0
		}).css({
			"width" : "0",
			"min-width" : "2em"
		}).text("0%");
		var prog = $("<div>").append(this.bar).css("display", "none");
		this._addClass(prog, "progress");
		this.info = $("<div>").addClass("well");
		var infobody = $("<div>").append(this.info).append(prog);
		this._addClass(infobody, "panel-body");
		var layerInfo = $("<div>").append(infohead).append(infobody);
		this._addClass(layerInfo, "panel");
		this._addClass(layerInfo, "panel-default");

		var tdhead1 = $("<td>").text("#");
		var tdhead2 = $("<td>").text("Name");
		var trhead = $("<tr>").append(tdhead1).append(tdhead2);
		var thead = $("<thead>").append(trhead);
		that.tbody = $("<tbody>");
		var tb = $("<table>").css({
			"margin-bottom" : 0
		}).append(thead).append(that.tbody);
		this._addClass(tb, "table");
		this._addClass(tb, "table-striped");
		this._addClass(tb, "text-center");
		var infobody2 = $("<div>").css({
			"max-height" : "330px",
			"overflow-y" : "auto",
			"padding" : 0
		}).append(tb);
		this._addClass(infobody2, "panel-body");
		var layerInfo2 = $("<div>").append(infobody2);
		this._addClass(layerInfo2, "panel");
		this._addClass(layerInfo2, "panel-default");
		var uright = $("<div>").append(layerInfo).append(layerInfo2);
		this._addClass(uright, "col-md-7");
		var row = $("<div>").append(uleft).append(uright);
		this._addClass(row, "row");
		this.warning = $("<div>").addClass("alert").addClass("alert-success");
		$(this.warning).hide();
		var row2 = $("<div>").append(this.warning);
		var body = $("<div>").append(row).append(row2);
		this._addClass(body, "modal-body");
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
		this._addClass(closeBtn, "btn");
		this._addClass(closeBtn, "btn-default");
		$(closeBtn).text("Close");

		this.okBtn = $("<button>").attr({
			"type" : "button"
		});
		this._addClass(this.okBtn, "btn");
		this._addClass(this.okBtn, "btn-primary");
		this._addClass(this.okBtn, "validation-btn-start");
		$(this.okBtn).text("Start");

		this._on(false, this.okBtn, {
			click : function(event) {
				if (event.target === this.okBtn[0]) {
					that.start();
				}
			}
		});

		var footer = $("<div>").append(closeBtn).append(this.okBtn);
		this._addClass(footer, "modal-footer");
		/*
		 * 
		 * 
		 * footer end
		 * 
		 * 
		 */
		var content = $("<div>").append(header).append(body).append(footer);
		this._addClass(content, "modal-content");

		var dialog = $("<div>").html(content);
		this._addClass(dialog, "modal-dialog");
		this._addClass(dialog, "modal-lg");

		this.window = $("<div>").hide().attr({
			tabIndex : -1,
			role : "dialog",
		}).html(dialog);
		this._addClass(this.window, "modal");
		this._addClass(this.window, "fade");

		this.window.appendTo(this._appendTo());
		this.window.modal({
			backdrop : "static",
			keyboard : true,
			show : false,
		});
	},
	_init : function() {
		var that = this;
		this.setMessage('Select map sheets for QA');
		this.setProgress(0);
		$(this.tree).jstree("refresh");
		$(this.okBtn).prop("disabled", false);
		$(this.warning).hide();
	},
	setLayerDefinition : function(obj) {
		this.layerDef = obj;
	},
	getLayerDefinition : function() {
		var result;
		if (typeof this.layerDef === "function") {
			result = this.layerDef();
		} else if (typeof this.layerDef === "object") {
			result = this.layerDef;
		} else {
			console.error("invalid type");
			return;
		}
		return result;
	},
	setOptionDefinition : function(obj) {
		this.optDef = obj;
	},
	getOptionDefinition : function() {
		var result;
		if (typeof this.optDef === "function") {
			result = this.optDef();
		} else if (typeof this.optDef === "object") {
			result = this.optDef;
		} else {
			console.error("invalid type");
			return;
		}
		return result;
	},
	setWeightDefinition : function(obj){
		this.weightDef = obj;
	},
	getWeightDefinition : function(){
		var result;
		if (typeof this.weightDef === "function") {
			result = this.weightDef();
		} else if (typeof this.weightDef === "object") {
			result = this.weightDef;
		} else {
			console.error("invalid type");
			return;
		}
		return result;
	},
	setValidationDefinition : function(obj) {
		this.valiDef = obj;
	},
	getValidationDefinition : function() {
		return this.valiDef;
	},
	disconnect : function() {
		this.webSocket.close();
	},
	start : function() {
		var that = this;
		if (!!this.valiDef) {
			console.log(this.valiDef);
			if (this.valiDef.layerCollections.collectionName.length < 1) {
				this.setMessage("Error. No selected map sheets.");
				return;
			}
			if (this.valiDef.typeValidate.length < 1) {
				this.setMessage("Error. There is no validating option.");
				return;
			}
			$.ajax({
				url : that.options.validatorURL,
				type : "POST",
				contentType : "application/json; charset=UTF-8",
				cache : false,
				data : JSON.stringify(this.valiDef),
				beforeSend : function() { // 호출전실행
					// loadImageShow();
				},
				traditional : true,
				success : function(data, textStatus, jqXHR) {
					console.log(data);
					// if (!data.ErrorLayer && !data["Publising
					// ErrorLayer"]) {
					// that.setMessage("No errors. Not published.");
					// that.setProgress(0);
					// } else if(data.ErrorLayer && data["Publising
					// ErrorLayer"]){
					// that.setMessage("Validation complete");
					// that.setProgress(100);
					// } else if(data.ErrorLayer && !data["Publising
					// ErrorLayer"]){
					// that.setMessage("Error detected. Not published.");
					// that.setProgress(0);
					// }
					
				},
				error : function( jqXHR,  textStatus,  errorThrown ){
					console.log(jqXHR);
					if (jqXHR.status === 500) {
						that.setMessage("Error. Check the validating option.");
					}
				}
			});
			that.afterRequest();
		} else {
			that.setMessage("Validating option is not assigned.");
		}
	},
	afterRequest : function(){
		$(this.okBtn).prop("disabled", true);
		var that = this;
		setTimeout(function(){ 
			$(that.warning).text("Request completed. This window will be closed in 5 second.");
			$(that.warning).show();
		}, 1000);
		setTimeout(function(){ 
			$(that.warning).text("Request completed. This window will be closed in 4 second.");
		}, 2000);
		setTimeout(function(){ 
			$(that.warning).text("Request completed. This window will be closed in 3 second.");
		}, 3000);
		setTimeout(function(){ 
			$(that.warning).text("Request completed. This window will be closed in 2 second.");
		}, 4000);
		setTimeout(function(){ 
			$(that.warning).text("Request completed. This window will be closed in 1 second.");
		}, 5000);
		setTimeout(function(){ 
			$(that.warning).text("Request completed. This window will be closed in 0 second.");
			that.close();
			$(that.warning).hide();
		}, 6000);
	},
	setProgress : function(figure) {
		var int = parseInt(figure);
		$(this.bar).attr({"aria-valuenow" : int}).css({
			"width" : int+"%"}).text(int+"%");
	},
	getProgress : function() {
		return $(this.bar).attr("aria-valuenow");
	},
	setMessage : function(msg) {
		$(this.info).text(msg);
	},
	updateLayerList : function(names) {
		$(this.tbody).empty();
		for (var i = 0; i < names.length; i++) {
			var td1 = $("<td>").text((i + 1));
			var td2 = $("<td>").text(names[i].text);
			var tr = $("<tr>").append(td1).append(td2);
			$(this.tbody).append(tr);
		}
		if (names.length > 0) {
			this.setMessage('Press the "Start" to start the QA');	
		} else {
			this.setMessage('Select map sheets for QA');
		}
	},
	updateValidationDef : function(names) {
		if (!Array.isArray(names)) {
			console.error("Invalid type");
			return;
		} else {
			if (names.length === 0) {
				console.error("no elements");
				return;
			}
		}
		console.log(names);
		var newNames = [];
		for (var i = 0; i < names.length; i++) {
			newNames.push(names[i].text);
		}
		var totalObj = {};
		var layerColl = {
				"collectionName" : newNames
		};
		var ldef = this.getLayerDefinition();
		var odef = this.getOptionDefinition();
		var wdef = this.getWeightDefinition();
// if (Object.keys(ldef).length === 0 || Object.keys(odef).length === 0 ||
// Object.keys(wdef).length === 0) {
// console.error("required option missing");
// this.setMessage('Error : Check the options (Layer definition, Option
// definition, Weight definition)');
// $(this.window).find(".validation-btn-start").prop("disabled", true);
// } else {
// $(this.window).find(".validation-btn-start").prop("disabled", false);
// }
		var lkeys = Object.keys(odef);
		var layers = [];

		var typeValidate = [];
		for (var i = 0; i < lkeys.length; i++) {
// console.log(lkeys[i]);
			if (!ldef[lkeys[i]].hasOwnProperty("area")) {
				console.error(lkeys[i]+"레이어의 검수영역이 없음");
			}
			if (!ldef[lkeys[i]].hasOwnProperty("code")) {
				console.error(lkeys[i]+"레이어의 레이어 코드가 없음");
			}
			if (!ldef[lkeys[i]].hasOwnProperty("geom")) {
				console.error(lkeys[i]+"레이어의 지오메트리가 없음");
			}
			if (ldef[lkeys[i]].area) {
				if (ldef[lkeys[i]].code.length !== 1) {
					console.error("Validating area must be single");
					this.valiDef = undefined;
					return;
				}
				layerColl["neatLineLayer"] = ldef[lkeys[i]].code + "_" + (ldef[lkeys[i]].geom.toUpperCase());
			}
			// 타입 벨리데이트 내부의 레이어스
			var tLayers = [];
			var codes = ldef[lkeys[i]].code;
			for (var j = 0; j < codes.length; j++) {
				console.log(lkeys[i]);
				console.log(ldef[lkeys[i]]);
				var name = codes[j] + "_" + (ldef[lkeys[i]].geom.toUpperCase());
				tLayers.push(name);
			}
			var tvObj = {
					"typeName" : lkeys[i],
					"layers" : tLayers
			};
			if (wdef.hasOwnProperty(lkeys[i])) {
				tvObj["weight"] = wdef[lkeys[i]];
			} 
			if (odef.hasOwnProperty(lkeys[i])) {
				if (Object.keys(odef[lkeys[i]]).length > 0 ) {
					tvObj["option"] = odef[lkeys[i]];	
				}
			}
			typeValidate.push(tvObj);
		}

		var inner = [];
		for (var i = 0; i < names.length; i++) {
			inner = inner.concat($(this.tree).jstree("get_node", names[i]).children);
		}
		var map = {};
		for (var i = 0; i < inner.length; i++) {
			map[inner[i]] = 0;
		}
		var keys = Object.keys(map);
		var notDupObj = {};
		for (var i = 0; i < keys.length; i++) {
			var type = "default";
			if ($(this.tree).jstree("get_node", keys[i]).type === "n_ngi_layer_pt" || $(this.tree).jstree("get_node", keys[i]).type === "n_shp_layer_pt") {
				type = "POINT";
			} else if ($(this.tree).jstree("get_node", keys[i]).type === "n_ngi_layer_ln" || $(this.tree).jstree("get_node", keys[i]).type === "n_shp_layer_ln") {
				type = "LINESTRING";
			} else if ($(this.tree).jstree("get_node", keys[i]).type === "n_ngi_layer_pg" || $(this.tree).jstree("get_node", keys[i]).type === "n_shp_layer_pg") {
				type = "POLYGON";
			} else if ($(this.tree).jstree("get_node", keys[i]).type === "n_ngi_layer_mpt" || $(this.tree).jstree("get_node", keys[i]).type === "n_shp_layer_mpt") {
				type = "MULTIPOINT";
			} else if ($(this.tree).jstree("get_node", keys[i]).type === "n_ngi_layer_mln" || $(this.tree).jstree("get_node", keys[i]).type === "n_shp_layer_mln") {
				type = "MULTILINESTRING";
			} else if ($(this.tree).jstree("get_node", keys[i]).type === "n_ngi_layer_mpg" || $(this.tree).jstree("get_node", keys[i]).type === "n_shp_layer_mpg") {
				type = "MULTIPOLYGON";
			} else if ($(this.tree).jstree("get_node", keys[i]).type === "n_ngi_layer_txt") {
				type = "TEXT";
			} else if ($(this.tree).jstree("get_node", keys[i]).type === "n_dxf_layer_arc") {
				type = "ARC";
			} else if ($(this.tree).jstree("get_node", keys[i]).type === "n_dxf_layer_cir") {
				type = "CIRCLE";
			}  else if ($(this.tree).jstree("get_node", keys[i]).type === "n_dxf_layer_ins") {
				type = "INSERT";
			}  else if ($(this.tree).jstree("get_node", keys[i]).type === "n_dxf_layer_lpl") {
				type = "LWPOLYLINE";
			}  else if ($(this.tree).jstree("get_node", keys[i]).type === "n_dxf_layer_pl") {
				type = "POLYLINE";
			}  else if ($(this.tree).jstree("get_node", keys[i]).type === "n_dxf_layer_txt") {
				type = "TEXT";
			} 
// layers.push($(this.tree).jstree("get_node", keys[i]).text+"_"+type);
			notDupObj[$(this.tree).jstree("get_node", keys[i]).text+"_"+type] = 0;
		}
		var dkeys = Object.keys(notDupObj);
		for (var i = 0; i < dkeys.length; i++) {
// layers.push($(this.tree).jstree("get_node", dkeys[i]).text+"_"+type);
			layers.push(dkeys[i]);
		}

		layerColl["layers"] = layers;
		layerColl["fileType"] = this.fileType;
		totalObj["layerCollections"] = layerColl;
		totalObj["typeValidate"] = typeValidate;
		console.log(totalObj);

		this.valiDef = totalObj;
		// return totalObj;
	},
	open : function() {
		this.window.modal('show');
		this._init();
		$(this.tree).jstree("refresh");
		var arr = $(this.tree).jstree("get_selected");
		var r = [];

		for (var i = 0; i < arr.length; i++) {
			if ($(this.tree).jstree("get_node", arr[i]).type === "n_ngi_group" || $(this.tree).jstree("get_node", arr[i]).type === "n_dxf_group"|| $(this.tree).jstree("get_node", arr[i]).type === "n_shp_group") {
				if (r.length === 0 && $(this.tree).jstree("get_node", arr[i]).type === "n_ngi_group") {
					r.push($(this.tree).jstree("get_node", arr[i]));
					this.fileType = "ngi";
					continue;
				} else if (r.length === 0 && $(this.tree).jstree("get_node", arr[i]).type === "n_dxf_group") {
					r.push($(this.tree).jstree("get_node", arr[i]));
					this.fileType = "dxf";
					continue;
				}else if (r.length === 0 && $(this.tree).jstree("get_node", arr[i]).type === "n_shp_group") {
					r.push($(this.tree).jstree("get_node", arr[i]));
					this.fileType = "shp";
					continue;
				}

				if (r.length > 0 && this.fileType === "ngi" && $(this.tree).jstree("get_node", arr[i]).type === "n_ngi_group") {
					r.push($(this.tree).jstree("get_node", arr[i]));
					continue;
				} else {
					$(this.tree).jstree("deselect_node", $(this.tree).jstree("get_node", arr[i]));
				}
				if (r.length > 0 && this.fileType === "dxf" && $(this.tree).jstree("get_node", arr[i]).type === "n_dxf_group") {
					r.push($(this.tree).jstree("get_node", arr[i]));
					continue;
				} else {
					$(this.tree).jstree("deselect_node", $(this.tree).jstree("get_node", arr[i]));
				}
				if (r.length > 0 && this.fileType === "shp" && $(this.tree).jstree("get_node", arr[i]).type === "n_shp_group") {
					r.push($(this.tree).jstree("get_node", arr[i]));
					continue;
				} else {
					$(this.tree).jstree("deselect_node", $(this.tree).jstree("get_node", arr[i]));
				}
			} else {
				data.instance.deselect_node(data.instance.get_node(data.selected[i]));
				that.updateLayerList(r);
			}
		}

// if (r.length > 0) {
		this.updateValidationDef(r);
		this.updateLayerList(r);
// }
	},
	close : function() {
		this.window.modal('hide');
	},
	destroy : function() {
		this.element.off("click");
		$(this.window).find("button").off("click");
		$(this.window).find("input").off("change").off("load");
		this.window = undefined;
	},
	_appendTo : function() {
		var element = this.options.appendTo;
		if (element && (element.jquery || element.nodeType)) {
			return $(element);
		}
		return this.document.find(element || "body").eq(0);
	},
	_removeClass : function(element, keys, extra) {
		return this._toggleClass(element, keys, extra, false);
	},

	_addClass : function(element, keys, extra) {
		return this._toggleClass(element, keys, extra, true);
	},

	_toggleClass : function(element, keys, extra, add) {
		add = (typeof add === "boolean") ? add : extra;
		var shift = (typeof element === "string" || element === null), options = {
			extra : shift ? keys : extra,
					keys : shift ? element : keys,
							element : shift ? this.element : element,
									add : add
		};
		options.element.toggleClass(this._classes(options), add);
		return this;
	},

	_on : function(suppressDisabledCheck, element, handlers) {
		var delegateElement;
		var instance = this;

		// No suppressDisabledCheck flag, shuffle arguments
		if (typeof suppressDisabledCheck !== "boolean") {
			handlers = element;
			element = suppressDisabledCheck;
			suppressDisabledCheck = false;
		}

		// No element argument, shuffle and use this.element
		if (!handlers) {
			handlers = element;
			element = this.element;
			delegateElement = this.widget();
		} else {
			element = delegateElement = $(element);
			this.bindings = this.bindings.add(element);
		}

		$.each(handlers, function(event, handler) {
			function handlerProxy() {

				// Allow widgets to customize the disabled
				// handling
				// - disabled as an array instead of boolean
				// - disabled class as method for disabling
				// individual parts
				if (!suppressDisabledCheck && (instance.options.disabled === true || $(this).hasClass("ui-state-disabled"))) {
					return;
				}
				return (typeof handler === "string" ? instance[handler] : handler).apply(instance, arguments);
			}

			// Copy the guid so direct unbinding works
			if (typeof handler !== "string") {
				handlerProxy.guid = handler.guid = handler.guid || handlerProxy.guid || $.guid++;
			}

			var match = event.match(/^([\w:-]*)\s*(.*)$/);
			var eventName = match[1] + instance.eventNamespace;
			var selector = match[2];

			if (selector) {
				delegateElement.on(eventName, selector, handlerProxy);
			} else {
				element.on(eventName, handlerProxy);
			}
		});
	},

	_off : function(element, eventName) {
		eventName = (eventName || "").split(" ").join(this.eventNamespace + " ") + this.eventNamespace;
		element.off(eventName).off(eventName);

		// Clear the stack to avoid memory leaks (#10056)
		this.bindings = $(this.bindings.not(element).get());
		this.focusable = $(this.focusable.not(element).get());
		this.hoverable = $(this.hoverable.not(element).get());
	}
});