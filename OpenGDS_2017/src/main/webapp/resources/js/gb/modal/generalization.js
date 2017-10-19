/**
 * 일반화 모달 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2017. 07.26
 * @version 0.01
 * @class gb.modal.Generalization
 * @constructor
 */
var gb;
if (!gb)
	gb = {};
if (!gb.modal)
	gb.modal = {};
gb.modal.Generalization = function(obj) {
	gb.modal.Base.call(this, obj);
	var that = this;
	this.json = undefined;
	var options = obj ? obj : {};
	this.jstreeURL = options.jstreeURL ? options.jstreeURL : "";
	this.items = options.items ? options.items : undefined;
	// 일반화 순서(토폴로지 여부 포함한 객체 배열)
	this.genOrder = undefined;
	// 셀렉트 박스 아래 인풋영역의 DOM배열
	this.formArea = undefined;
	this.thresholdArea = undefined;
	this.fileType = undefined;
	this.requestURL = options.requestURL ? options.requestURL : undefined;
	this.closeBtn = $("<button>").css({
		"float" : "right"
	}).addClass("gb-button").addClass("gb-button-default").text("Close").click(this, function(evt) {
		evt.data.close();
	});
	this.okBtn = $("<button>").css({
		"float" : "right"
	}).addClass("gb-button").addClass("gb-button-primary").text("OK").click(this, function(evt) {
		evt.data.requestGeneralization();
	});
	this.message = $("<div>").addClass("gb-alert").addClass("gb-alert-success").hide();
	this.buttonArea = $("<span>").addClass("gb-modal-buttons").append(this.okBtn).append(this.closeBtn);
	this.modalFooter = $("<div>").addClass("gb-modal-footer").append(this.buttonArea);
	$(this.getModal()).append(this.modalFooter);
	var llist = $("<div>").text("Layer list");
	var treeHead = $("<div>").addClass("gb-article-head").append(llist);
	this.treeBody = $("<div>").addClass("gb-article-body").css({
		"height" : "400px",
		"max-height" : "400px"
	});
	this.tree = $("<div>").addClass("gb-article").append(treeHead).append(this.treeBody);

	var infohead = $("<div>").text("Processing order").addClass("gb-article-head");
	this.info = $("<div>").css({
		"margin-left" : "8px"
	});

	if (Array.isArray(this.items)) {
		this.formArea = [];
		this.thresholdArea = [];
		for (var i = 0; i < 3; i++) {
			var select = $("<select>").addClass("gb-form").css({
				"width" : "134px",
				"display" : "inline-block",
				"margin" : "5px"
			}).change(function() {
				// that.refreshThresholdArea($(this).val(),
				// $(this).parent().parent().index());

			});

			for (var j = 0; j < this.items.length; j++) {
				var opt = $("<option>").text(this.items[j]).attr("value", this.items[j].toLowerCase());
				$(select).append(opt);
			}
			var inc = $("<input>").attr({
				"type" : "checkbox"
			}).css({
				"vertical-align" : "top"
			});
			var span = $("<div>").append("Phase" + (i + 1) + " ").append(inc);

			var row1 = $("<div>").append(select);

			var text = $("<input>").attr({
				"type" : "number"
			}).addClass("gb-form").css({
				"display" : "inline-block",
				"width" : "134px",
				"margin" : "5px"
			})
					.on(
							"input",
							function() {
								console.log($(this).parent().index());
								console.log("check");
								console.log($(that.treeBody).jstree(true).get_selected(true).length);
								if ($(this).parent().parent().find("input[type=checkbox]").is(":checked")
										&& $(that.treeBody).jstree(true).get_selected(true).length === 1
										&& ($(that.treeBody).jstree(true).get_selected(true)[0].type === "n_shp_layer_ln"
												|| $(that.treeBody).jstree(true).get_selected(true)[0].type === "n_shp_layer_mln"
												|| $(that.treeBody).jstree(true).get_selected(true)[0].type === "n_shp_layer_pg" || $(
												that.treeBody).jstree(true).get_selected(true)[0].type === "n_shp_layer_mpg")) {
									console.log("here");
									console.log($(that.treeBody).jstree(true).get_selected(true)[0].id);
									if (typeof that.json === "undefined") {
										that.json = {};
									}
									if (!that.json.hasOwnProperty($(that.treeBody).jstree(true).get_selected(true)[0].id)) {
										that.json[$(that.treeBody).jstree(true).get_selected(true)[0].id] = {
											"define" : undefined,
											"phase" : [],
											"topology" : undefined
										};
									}
									that.json[$(that.treeBody).jstree(true).get_selected(true)[0].id].define = $(that.def).val();
									that.json[$(that.treeBody).jstree(true).get_selected(true)[0].id].phase[$(this).parent().index()] = {
										"method" : $(this).parent().find("select").val(),
										"threshold" : $(this).val()
									}
									that.json[$(that.treeBody).jstree(true).get_selected(true)[0].id].topology = $(that.check).is(
											":checked");
									that.updateLayerList();
									$(that.treeBody).jstree(true).set_flag($(that.treeBody).jstree(true).get_selected(true)[0], "checking",
											true);
									console.log(that.json);
								}
							});

			var odr = $("<div>").append(span).append(row1).append(text).css({
				"margin" : "5px",
				"display" : "inline-block",
				"vertical-align" : "top"
			}).addClass("gb-generalization-step");
			this.formArea[i] = odr;
			$(this.info).append(odr);
			// this.refreshThresholdArea($(select).val(), i);
		}
		console.log(this.formArea);
	}
	var opt1 = $("<option>").attr({
		"value" : "linestring"
	}).text("LineString");
	var opt2 = $("<option>").attr({
		"value" : "polygon"
	}).text("Polygon");
	this.def = $("<select>").append(opt1).append(opt2).addClass("gb-form").css({
		"width" : "443px",
		"margin-left" : "18px",
		"margin-bottom" : "5px",
		"margin-top" : "3px"
	});

	var label = $("<span>").text("Define").css({
		"margin-left" : "13px",
		"margin-right" : "5px"
	});
	this.check = $("<input>").attr({
		"type" : "checkbox"
	}).css({
		"vertical-align" : "top",
		"margin-left" : "5px"
	});
	var topo = $("<label>").css({
		"margin" : "5px 5px 5px 11px",
		"font-weight" : "100"
	}).append("Topology Check").append(this.check);
	var tocheck = $("<div>").append(topo);

	var infobody = $("<div>").append(label).append(this.def).append(tocheck).append(this.info).addClass("gb-article-body").css({
		"height" : "220px",
		"padding" : "5px"
	});
	var layerInfo = $("<div>").append(infohead).append(infobody).addClass("gb-article");

	var tdhead1 = $("<td>").text("#");
	var tdhead2 = $("<td>").text("Format");
	var tdhead3 = $("<td>").text("Map sheet");
	var tdhead4 = $("<td>").text("Layer");
	var trhead = $("<tr>").append(tdhead1).append(tdhead2).append(tdhead3).append(tdhead4);
	var thead = $("<thead>").append(trhead);
	this.tbody = $("<tbody>");
	var tb = $("<table>").css({
		"margin-bottom" : 0,
		"text-align" : "center"
	}).append(thead).append(this.tbody);

	$(tb).addClass("gb-table");
	var infobody2 = $("<div>").css({
		"max-height" : "168px",
		"height" : "168px",
		"overflow-y" : "auto",
		"padding" : 0
	}).append(tb).addClass("gb-article-body");
	var layerInfo2 = $("<div>").append(infobody2).addClass("gb-article");
	var uright = $("<div>").append(layerInfo).append(layerInfo2);

	var layouttd1 = $("<td>").append(this.tree).attr({
		"width" : "40%"
	});
	var layouttd2 = $("<td>").append(uright).attr({
		"width" : "60%"
	}).css({
		"vertical-align" : "baseline"
	});
	var layouttr = $("<tr>").append(layouttd1).append(layouttd2);
	var layouttb = $("<table>").append(layouttr).attr({
		"width" : "100%"
	});
	$(this.getModalBody()).append(layouttb).append(this.message);
	$(this.treeBody).jstree({
		"core" : {
			"animation" : 0,
			"check_callback" : true,
			"themes" : {
				"stripes" : true
			},
			'data' : {
				'url' : function() {
					return that.jstreeURL;
				}
			}
		},
		"functionmarker" : {
			"checking" : "fa fa-check"
		},
		"plugins" : [ "types", "functionmarker" ]
	});

	$(this.treeBody).on(
			'deselect_node.jstree',
			function(e, data) {
				var r = [];
				for (var i = 0; i < data.selected.length; i++) {
					if (data.instance.get_node(data.selected[i]).type === "n_shp_layer_ln"
							|| data.instance.get_node(data.selected[i]).type === "n_shp_layer_pg"
							|| data.instance.get_node(data.selected[i]).type === "n_shp_layer_mln"
							|| data.instance.get_node(data.selected[i]).type === "n_shp_layer_mpg") {
						that.printDetailOption(data.instance.get_node(data.selected[i]));
						continue;
					}
				}
			});
	$(this.treeBody).on(
			'select_node.jstree',
			function(e, data) {
				var r = [];
				for (var i = 0; i < data.selected.length; i++) {
					if (data.instance.get_node(data.selected[i]).type === "n_shp_layer_ln"
							|| data.instance.get_node(data.selected[i]).type === "n_shp_layer_pg"
							|| data.instance.get_node(data.selected[i]).type === "n_shp_layer_mln"
							|| data.instance.get_node(data.selected[i]).type === "n_shp_layer_mpg") {
						// data.instance.set_flag(data.instance.get_node(data.selected[i]),
						// "checking", true);
						// r.push(data.instance.get_node(data.selected[i]));
						that.printDetailOption(data.instance.get_node(data.selected[i]));
						continue;
					}
				}
				// that.updateLayerList(r);
			});
};
gb.modal.Generalization.prototype = Object.create(gb.modal.Base.prototype);
gb.modal.Generalization.prototype.constructor = gb.modal.Generalization;

/**
 * 
 */
gb.modal.Generalization.prototype.updateFormData = function() {
	var opt = {
		"define" : $(this.def).val(),
		"phase" : [],
		"topology" : $(this.check).is(":checked")
	};
};

/**
 * 선택한 레이어의 옵션 정보를 출력한다
 * 
 * @name printDetailOption
 * @param {Object}
 *            jstree node
 */
gb.modal.Generalization.prototype.printDetailOption = function(node) {
	var id = node.id;
	if (typeof this.json === "undefined") {
		this.json = {};
	}
	if (this.json.hasOwnProperty(id)) {
		$(this.def).val(this.json[id].define);
		$(this.check).prop("checked", this.json[id].topology);
		var phase = this.json[id].phase;
		if (phase.length > 0) {
			for (var i = 0; i < 3; i++) {
				if (typeof phase[i] !== "undefined") {
					$(this.formArea[i]).find("input[type=checkbox]").prop("checked", true);
					$(this.formArea[i]).find("select").val(phase[i].method);
					$(this.formArea[i]).find("input[type=number]").val(phase[i].threshold);
				} else {
					$(this.formArea[i]).find("input[type=checkbox]").prop("checked", false);
					$(this.formArea[i]).find("select").val("simplification");
					$(this.formArea[i]).find("input[type=number]").val("");
				}
			}
		}
	} else {
		$(this.def).val("linestring");
		$(this.check).prop("checked", false);
		for (var i = 0; i < 3; i++) {
			$(this.formArea[i]).find("input[type=checkbox]").prop("checked", false);
			$(this.formArea[i]).find("select").val("simplification");
			$(this.formArea[i]).find("input[type=number]").val("");
		}
	}
};

/**
 * 일반화할 레이어를 목록에 표시한다.
 * 
 * @name updateLayerList
 * @param {Array}
 *            names
 */
gb.modal.Generalization.prototype.updateLayerList = function() {
	var names = Object.keys(this.json).sort();
	$(this.tbody).empty();
	for (var i = 0; i < names.length; i++) {
		var td1 = $("<td>").text((i + 1));
		var td2 = $("<td>").text("not yet");
		var td3 = $("<td>").text($(this.treeBody).jstree(true).get_node($(this.treeBody).jstree(true).get_node(names[i]).parent).text);
		var td4 = $("<td>").text($(this.treeBody).jstree(true).get_node(names[i]).text);
		var tr = $("<tr>").append(td1).append(td2).append(td3).append(td4);
		$(this.tbody).append(tr);
	}
};

/**
 * 일반화 프로세스에 맞는 입력폼을 출력한다
 * 
 * @name refreshThresholdArea
 * @param {String}
 *            method
 * @param {Number}
 *            idx
 */
gb.modal.Generalization.prototype.refreshThresholdArea = function(method, idx) {
	var that = this;
	switch (method) {
	case "simplification":
		$(this.thresholdArea[idx]).empty();
		var text = $("<input>").attr({
			"type" : "number"
		}).addClass("gb-form").css({
			"display" : "inline-block",
			"width" : "134px",
			"margin" : "5px"
		}).on(
				"input",
				function() {
					console.log($(this).parent().parent().index());
					console.log("check");
					console.log($(that.treeBody).jstree(true).get_selected(true).length);
					if ($(this).parent().parent().find("input[type=checkbox]").is(":checked")
							&& $(that.treeBody).jstree(true).get_selected(true).length === 1
							&& ($(that.treeBody).jstree(true).get_selected(true)[0].type === "n_shp_layer_ln"
									|| $(that.treeBody).jstree(true).get_selected(true)[0].type === "n_shp_layer_mln"
									|| $(that.treeBody).jstree(true).get_selected(true)[0].type === "n_shp_layer_pg" || $(that.treeBody)
									.jstree(true).get_selected(true)[0].type === "n_shp_layer_mpg")) {
						console.log("here");
						console.log($(that.treeBody).jstree(true).get_selected(true)[0].id);
						if (typeof that.json === "undefined") {
							that.json = {};
						}
						if (!that.json.hasOwnProperty($(that.treeBody).jstree(true).get_selected(true)[0].id)) {
							that.json[$(that.treeBody).jstree(true).get_selected(true)[0].id] = {
								"define" : undefined,
								"phase" : [],
								"topology" : undefined
							};
						}
						that.json[$(that.treeBody).jstree(true).get_selected(true)[0].id].define = $(that.def).val();
						that.json[$(that.treeBody).jstree(true).get_selected(true)[0].id].phase[$(this).parent().parent().index()] = {
							"method" : $(this).parent().parent().find("select").val(),
							"threshold" : $(this).val()
						}
						that.json[$(that.treeBody).jstree(true).get_selected(true)[0].id].topology = $(that.check).is(":checked");
						that.updateLayerList();
						$(that.treeBody).jstree(true).set_flag($(that.treeBody).jstree(true).get_selected(true)[0], "checking", true);
						console.log(that.json);
					}
				});

		$(this.thresholdArea[idx]).append(text);
		break;
	case "elimination":
		$(this.thresholdArea[idx]).empty();
		var text = $("<input>").attr({
			"type" : "number"
		}).addClass("gb-form").css({
			"display" : "inline-block",
			"width" : "134px",
			"margin" : "5px"
		});
		var toler = $("<div>").append(text);

		$(this.thresholdArea[idx]).append(toler);
		break;
	default:
		break;
	}
};
/**
 * 일반화를 요청한다.
 * 
 * @name requestGeneralization
 */
gb.modal.Generalization.prototype.requestGeneralization = function() {
	var obj = {
		"topology" : $(this.check).is(":checked") ? true : false,
		"order" : [],
		"layers" : undefined
	}
	var groups = $(this.treeBody).jstree(true).get_selected();
	obj.layers = groups;
	// var dup = {};
	// for (var i = 0; i < groups.length; i++) {
	// var parent = $(this.treeBody).jstree(true).get_node(groups[i]);
	// for (var j = 0; j < parent.children.length; j++) {
	// var node = $(this.treeBody).jstree(true).get_node(parent.children[j]);
	// dup[node.id] = null;
	// }
	// }
	// obj.layers = Object.keys(dup);
	for (var i = 0; i < this.items.length; i++) {
		if ($(this.info).find(".gb-generalization-step:eq(" + i + ")").find("input[type=checkbox]").is(":checked")) {
			if ($(this.info).find(".gb-generalization-step:eq(" + i + ")").find("select").val() === "simplification") {
				var step = {
					"method" : $(this.info).find(".gb-generalization-step:eq(" + i + ")").find("select").val(),
					"tolerance" : $(this.info).find(".gb-generalization-step:eq(" + i + ")").find("input[type=number]").val()
				};
				obj.order.push(step);
			} else if ($(this.info).find(".gb-generalization-step:eq(" + i + ")").find("select").val() === "elimination") {
				var step = {
					"method" : $(this.info).find(".gb-generalization-step:eq(" + i + ")").find("select").val(),
					"tolerance" : $(this.info).find(".gb-generalization-step:eq(" + i + ")").find("input[type=number]").val()
				};
				obj.order.push(step);
			}
		}
	}
	console.log(JSON.stringify(obj));
	$.ajax({
		url : this.requestURL,
		type : "POST",
		contentType : "application/json; charset=UTF-8",
		cache : false,
		data : JSON.stringify(obj),
		beforeSend : function() { // 호출전실행
			// loadImageShow();
		},
		traditional : true,
		success : function(data, textStatus, jqXHR) {
			console.log(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR);
			if (jqXHR.status === 500) {
				console.error(errorTrown);
			}
		}
	});
	this.afterRequest_();
};
/**
 * 요청후 메시지를 출력한다
 * 
 * @name afterRequest_
 */
gb.modal.Generalization.prototype.afterRequest_ = function() {
	$(this.okBtn).prop("disabled", true);
	var that = this;
	setTimeout(function() {
		$(that.message).text("Request completed. This window will be closed in 5 second.");
		$(that.message).show();
	}, 1000);
	setTimeout(function() {
		$(that.message).text("Request completed. This window will be closed in 4 second.");
	}, 2000);
	setTimeout(function() {
		$(that.message).text("Request completed. This window will be closed in 3 second.");
	}, 3000);
	setTimeout(function() {
		$(that.message).text("Request completed. This window will be closed in 2 second.");
	}, 4000);
	setTimeout(function() {
		$(that.message).text("Request completed. This window will be closed in 1 second.");
	}, 5000);
	setTimeout(function() {
		$(that.message).text("Request completed. This window will be closed in 0 second.");
		that.close();
		$(that.message).hide();
	}, 6000);
};
/**
 * 모달을 연다
 * 
 * @name open
 */
gb.modal.Generalization.prototype.open = function() {
	gb.modal.Base.prototype.open.call(this);
	$(this.okBtn).prop("disabled", false);
};