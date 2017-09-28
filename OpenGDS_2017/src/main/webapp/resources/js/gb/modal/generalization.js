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
	var options = obj ? obj : {};
	this.jstreeURL = options.jstreeURL ? options.jstreeURL : "";
	this.items = options.items ? options.items : undefined;
	// 일반화 순서(토폴로지 여부 포함한 객체 배열)
	this.genOrder = undefined;
	// 셀렉트 박스 아래 인풋영역의 DOM배열
	this.formArea = undefined;
	this.fileType = undefined;
	this.requestURL = options.requestURL ? options.requestURL : undefined;
	this.closeBtn = $("<button>").addClass("gb-button").addClass("gb-button-default").text("Close").click(this, function(evt) {
		evt.data.close();
	});
	this.okBtn = $("<button>").addClass("gb-button").addClass("gb-button-primary").text("OK").click(this, function(evt) {
		evt.data.requestGeneralization();
	});
	this.message = $("<div>").addClass("gb-alert").addClass("gb-alert-success").hide();
	this.buttonArea = $("<span>").addClass("gb-modal-buttons").append(this.closeBtn).append(this.okBtn);
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
	this.info = $("<div>");

	if (Array.isArray(this.items)) {
		this.formArea = [];
		for (var i = 0; i < this.items.length; i++) {
			var select = $("<select>").addClass("gb-form").css({
				"width" : "134px",
				"display" : "inline-block",
				"margin" : "5px"
			}).change(function() {
				that.refreshFormArea($(this).val(), $(this).parent().parent().index());
			});

			for (var j = 0; j < this.items.length; j++) {
				var opt = $("<option>").text(this.items[j]).attr("value", this.items[j].toLowerCase());
				$(select).append(opt);
			}
			var inc = $("<input>").attr({
				"type" : "checkbox"
			});
			var span = $("<div>").append("Step" + (i + 1)).append(inc);

			var row1 = $("<div>").append(select);

			var tol = $("<div>");
			this.formArea[i] = tol;
			console.log(this.formArea);
			var odr = $("<div>").append(span).append(row1).append(tol).css({
				"margin" : "10px",
				"display" : "inline-block",
				"vertical-align" : "top"
			}).addClass("gb-generalization-step");
			$(this.info).append(odr);
			this.refreshFormArea($(select).val(), i);
		}
	}

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

	var infobody = $("<div>").append(tocheck).append(this.info).addClass("gb-article-body").css({
		"height" : "176px",
		"padding" : "5px"
	});
	var layerInfo = $("<div>").append(infohead).append(infobody).addClass("gb-article");

	var tdhead1 = $("<td>").text("#");
	var tdhead2 = $("<td>").text("Name");
	var trhead = $("<tr>").append(tdhead1).append(tdhead2);
	var thead = $("<thead>").append(trhead);
	this.tbody = $("<tbody>");
	var tb = $("<table>").css({
		"margin-bottom" : 0,
		"text-align" : "center"
	}).append(thead).append(this.tbody);

	$(tb).addClass("gb-table");
	var infobody2 = $("<div>").css({
		"max-height" : "212px",
		"height" : "212px",
		"overflow-y" : "auto",
		"padding" : 0
	}).append(tb).addClass("gb-article-body");
	var layerInfo2 = $("<div>").append(infobody2).addClass("gb-article");
	var uright = $("<div>").append(layerInfo).append(layerInfo2);

	var layouttd1 = $("<td>").append(this.tree).attr({
		"width" : "35%"
	});
	var layouttd2 = $("<td>").append(uright).attr({
		"width" : "65%"
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
		"plugins" : [ "types" ]
	});

	$(this.treeBody).on('deselect_node.jstree', function(e, data) {
		var r = [];
		for (var i = 0; i < data.selected.length; i++) {
			// if (data.instance.get_node(data.selected[i]).type ===
			// "n_shp_layer_pt"
			// || data.instance.get_node(data.selected[i]).type ===
			// "n_shp_layer_ln"
			// || data.instance.get_node(data.selected[i]).type ===
			// "n_shp_layer_pg"
			// || data.instance.get_node(data.selected[i]).type ===
			// "n_shp_layer_mpt"
			// || data.instance.get_node(data.selected[i]).type ===
			// "n_shp_layer_mln"
			// || data.instance.get_node(data.selected[i]).type ===
			// "n_shp_layer_mpg") {
			// if (r.length === 0 &&
			// data.instance.get_node(data.selected[i]).type === "n_ngi_group")
			// {
			// r.push(data.instance.get_node(data.selected[i]));
			// that.fileType = "ngi";
			// continue;
			// } else if (r.length === 0 &&
			// data.instance.get_node(data.selected[i]).type === "n_dxf_group")
			// {
			// r.push(data.instance.get_node(data.selected[i]));
			// that.fileType = "dxf";
			// continue;
			// } else if (r.length === 0 &&
			// data.instance.get_node(data.selected[i]).type === "n_shp_group")
			// {
			r.push(data.instance.get_node(data.selected[i]));
			that.fileType = "shp";
			// continue;
			// }
			//
			// if (r.length > 0 && that.fileType === "ngi" &&
			// data.instance.get_node(data.selected[i]).type === "n_ngi_group")
			// {
			// r.push(data.instance.get_node(data.selected[i]));
			// continue;
			// } else if (r.length > 0 && that.fileType === "dxf"
			// && data.instance.get_node(data.selected[i]).type ===
			// "n_dxf_group") {
			// r.push(data.instance.get_node(data.selected[i]));
			// continue;
			// } else if (r.length > 0 && that.fileType === "shp"
			// && data.instance.get_node(data.selected[i]).type ===
			// "n_shp_group") {
			// r.push(data.instance.get_node(data.selected[i]));
			// continue;
			// }
			// if (that.fileType === "ngi" &&
			// data.instance.get_node(data.selected[i]).type === "n_dxf_group")
			// {
			// data.instance.deselect_node(data.instance.get_node(data.selected[i]));
			// } else if (that.fileType === "dxf" &&
			// data.instance.get_node(data.selected[i]).type === "n_ngi_group")
			// {
			// data.instance.deselect_node(data.instance.get_node(data.selected[i]));
			// } else if (that.fileType === "shp" &&
			// data.instance.get_node(data.selected[i]).type === "n_shp_group")
			// {
			// data.instance.deselect_node(data.instance.get_node(data.selected[i]));
			// }
			// } else {
			// data.instance.deselect_node(data.instance.get_node(data.selected[i]));
			// that.updateLayerList(r);
			// }
		}
		// for (var i = 0; i < data.selected.length; i++) {
		// if (data.instance.get_node(data.selected[i]).type ===
		// "n_ngi_group"
		// || data.instance.get_node(data.selected[i]).type ===
		// "n_dxf_group"
		// || data.instance.get_node(data.selected[i]).type ===
		// "n_shp_group") {
		// if (r.length === 0 &&
		// data.instance.get_node(data.selected[i]).type ===
		// "n_ngi_group") {
		// r.push(data.instance.get_node(data.selected[i]));
		// that.fileType = "ngi";
		// continue;
		// } else if (r.length === 0 &&
		// data.instance.get_node(data.selected[i]).type ===
		// "n_dxf_group") {
		// r.push(data.instance.get_node(data.selected[i]));
		// that.fileType = "dxf";
		// continue;
		// } else if (r.length === 0 &&
		// data.instance.get_node(data.selected[i]).type ===
		// "n_shp_group") {
		// r.push(data.instance.get_node(data.selected[i]));
		// that.fileType = "shp";
		// continue;
		// }
		//
		// if (r.length > 0 && that.fileType === "ngi" &&
		// data.instance.get_node(data.selected[i]).type ===
		// "n_ngi_group") {
		// r.push(data.instance.get_node(data.selected[i]));
		// continue;
		// } else if (r.length > 0 && that.fileType === "dxf"
		// && data.instance.get_node(data.selected[i]).type ===
		// "n_dxf_group") {
		// r.push(data.instance.get_node(data.selected[i]));
		// continue;
		// } else if (r.length > 0 && that.fileType === "shp"
		// && data.instance.get_node(data.selected[i]).type ===
		// "n_shp_group") {
		// r.push(data.instance.get_node(data.selected[i]));
		// continue;
		// }
		// if (that.fileType === "ngi" &&
		// data.instance.get_node(data.selected[i]).type ===
		// "n_dxf_group") {
		// data.instance.deselect_node(data.instance.get_node(data.selected[i]));
		// } else if (that.fileType === "dxf" &&
		// data.instance.get_node(data.selected[i]).type ===
		// "n_ngi_group") {
		// data.instance.deselect_node(data.instance.get_node(data.selected[i]));
		// } else if (that.fileType === "shp" &&
		// data.instance.get_node(data.selected[i]).type ===
		// "n_shp_group") {
		// data.instance.deselect_node(data.instance.get_node(data.selected[i]));
		// }
		// } else {
		// data.instance.deselect_node(data.instance.get_node(data.selected[i]));
		// that.updateLayerList(r);
		// }
		// }
		that.updateLayerList(r);
	});
	$(this.treeBody).on('select_node.jstree', function(e, data) {
		var r = [];
		for (var i = 0; i < data.selected.length; i++) {
			// if (data.instance.get_node(data.selected[i]).type ===
			// "n_ngi_group"
			// || data.instance.get_node(data.selected[i]).type ===
			// "n_dxf_group"
			// || data.instance.get_node(data.selected[i]).type ===
			// "n_shp_group") {
			// if (r.length === 0 &&
			// data.instance.get_node(data.selected[i]).type === "n_ngi_group")
			// {
			// r.push(data.instance.get_node(data.selected[i]));
			// that.fileType = "ngi";
			// continue;
			// } else if (r.length === 0 &&
			// data.instance.get_node(data.selected[i]).type === "n_dxf_group")
			// {
			// r.push(data.instance.get_node(data.selected[i]));
			// that.fileType = "dxf";
			// continue;
			// } else if (r.length === 0 &&
			// data.instance.get_node(data.selected[i]).type === "n_shp_group")
			// {
			r.push(data.instance.get_node(data.selected[i]));
			that.fileType = "shp";
			// continue;
			// }
			//
			// if (r.length > 0 && that.fileType === "ngi" &&
			// data.instance.get_node(data.selected[i]).type === "n_ngi_group")
			// {
			// r.push(data.instance.get_node(data.selected[i]));
			// continue;
			// } else if (r.length > 0 && that.fileType === "dxf"
			// && data.instance.get_node(data.selected[i]).type ===
			// "n_dxf_group") {
			// r.push(data.instance.get_node(data.selected[i]));
			// continue;
			// } else if (r.length > 0 && that.fileType === "shp"
			// && data.instance.get_node(data.selected[i]).type ===
			// "n_shp_group") {
			// r.push(data.instance.get_node(data.selected[i]));
			// continue;
			// }
			//
			// if (that.fileType === "ngi" &&
			// data.instance.get_node(data.selected[i]).type === "n_dxf_group")
			// {
			// data.instance.deselect_node(data.instance.get_node(data.selected[i]));
			// } else if (that.fileType === "dxf" &&
			// data.instance.get_node(data.selected[i]).type === "n_ngi_group")
			// {
			// data.instance.deselect_node(data.instance.get_node(data.selected[i]));
			// } else if (that.fileType === "shp" &&
			// data.instance.get_node(data.selected[i]).type === "n_shp_group")
			// {
			// data.instance.deselect_node(data.instance.get_node(data.selected[i]));
			// }
			// } else {
			// data.instance.deselect_node(data.instance.get_node(data.selected[i]));
			// that.updateLayerList(r);
			// }
		}
		that.updateLayerList(r);
	});
};
gb.modal.Generalization.prototype = Object.create(gb.modal.Base.prototype);
gb.modal.Generalization.prototype.constructor = gb.modal.Generalization;
/**
 * 일반화할 레이어를 목록에 표시한다.
 * 
 * @name updateLayerList
 * @param {Array}
 *            names
 */
gb.modal.Generalization.prototype.updateLayerList = function(names) {
	$(this.tbody).empty();
	for (var i = 0; i < names.length; i++) {
		var td1 = $("<td>").text((i + 1));
		var td2 = $("<td>").text(names[i].text);
		var tr = $("<tr>").append(td1).append(td2);
		$(this.tbody).append(tr);
	}
};

/**
 * 일반화 프로세스에 맞는 입력폼을 출력한다
 * 
 * @name refreshFormArea
 * @param {String}
 *            method
 * @param {Number}
 *            idx
 */
gb.modal.Generalization.prototype.refreshFormArea = function(method, idx) {
	switch (method) {
	case "simplification":
		$(this.formArea[idx]).empty();
		var text = $("<input>").attr({
			"type" : "number"
		}).addClass("gb-form").css({
			"display" : "inline-block",
			"width" : "134px",
			"margin" : "5px"
		});

		var toler = $("<div>").append(text);

		$(this.formArea[idx]).append(toler);
		break;
	case "elimination":
		$(this.formArea[idx]).empty();
		var text = $("<input>").attr({
			"type" : "number"
		}).addClass("gb-form").css({
			"display" : "inline-block",
			"width" : "134px",
			"margin" : "5px"
		});
		var toler = $("<div>").append(text);

		$(this.formArea[idx]).append(toler);
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