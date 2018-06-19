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
gb.geoserver.DeleteLayer = function(obj) {
	var that = this;
	var options = obj;
	this.url = options.URL ? options.URL : null;
	this.format = undefined;
	this.type = undefined;
	this.refer = options.refer ? options.refer : undefined;
	this.structure = {};
	this.window = undefined;
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

	var htag = $("<h4>");
	htag.text("Alert");
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
	this.message = $("<div>").text("Layers will be removed.");
	var body = $("<div>").append(this.message);
	$(body).addClass("modal-body");
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

	this.okBtn = $("<button>").attr({
		"type" : "button",
		"data-dismiss" : "modal"
	}).click(function() {
		that.save(that.getStructure());
	});
	$(this.okBtn).addClass("btn");
	$(this.okBtn).addClass("btn-primary");
	$(this.okBtn).text("OK");

	var footer = $("<div>").append(closeBtn).append(this.okBtn);
	$(footer).addClass("modal-footer");
	/*
	 * 
	 * 
	 * footer end
	 * 
	 * 
	 */
	var content = $("<div>").append(header).append(body).append(footer);
	$(content).addClass("modal-content");

	var dialog = $("<div>").html(content);
	$(dialog).addClass("modal-dialog");
	$(dialog).addClass("modal-sm");
	this.window = $("<div>").hide().attr({
		// Setting tabIndex makes the div focusable
		tabIndex : -1,
		role : "dialog",
	}).html(dialog);
	$(this.window).addClass("modal");
	$(this.window).addClass("fade");

	$(this.window).appendTo("body");
	this.window.modal({
		backdrop : "static",
		keyboard : true,
		show : false,
	});
};
gb.geoserver.DeleteLayer.prototype.setClientReference = function(refer) {
	this.clientRefer = refer;
};
gb.geoserver.DeleteLayer.prototype.getClientReference = function() {
	return this.clientRefer;
};
gb.geoserver.DeleteLayer.prototype.setReference = function(refer) {
	this.refer = refer;
};
gb.geoserver.DeleteLayer.prototype.getReference = function() {
	return this.refer;
};
gb.geoserver.DeleteLayer.prototype.isEditing = function(ids) {
	var result = false;
	var crefer = this.getClientReference();
	if (!Array.isArray(ids)) {
		return;
	}
	for (var i = 0; i < ids.length; i++) {
		var layer = crefer.get_LayerByOLId(ids[i]);
		if (layer instanceof ol.layer.Base) {
			result = true;
			break;
		}
	}

	if (result) {
		$(this.message).text("Can't remove. The layer is now editing.");
		$(this.okBtn).hide();
	} else {
		$(this.message).text("Layers will be removed.");
		$(this.okBtn).show();
	}
};
gb.geoserver.DeleteLayer.prototype.alert = function() {
	this.isEditing();
	this.window.modal('show');
	// var alert =
	// gb.geoserver.DeleteLayer.prototype.save(obj);
};
gb.geoserver.DeleteLayer.prototype.save = function(obj) {
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
			that.setStructure({});
		}
	});
};
gb.geoserver.DeleteLayer.prototype.addStructure = function(format, mapsheet, scope, layers) {
	var strc = this.getStructure();
	if (typeof strc === "object") {
		if (!strc.hasOwnProperty("layer")) {
			strc["layer"] = {};
		}
		if (!strc["layer"].hasOwnProperty(format)) {
			strc["layer"][format] = {};
		}
		if (!strc["layer"][format].hasOwnProperty(mapsheet)) {
			strc["layer"][format][mapsheet] = {};
		}
		// if (!strc["layer"][format][mapsheet].hasOwnProperty("remove")) {
		// strc["layer"][format][mapsheet]["remove"] = {};
		// }
	}
	var obj = {};
	obj["scope"] = typeof scope === "string" ? scope : false;
	obj["layer"] = Array.isArray(layers) ? layers : false;
	if (!obj["layer"] || !obj["scope"]) {
		return;
	}
	strc["layer"][format][mapsheet]["remove"] = obj;
	// this.structure.push(obj);
};
gb.geoserver.DeleteLayer.prototype.getStructure = function() {
	return this.structure;
};
gb.geoserver.DeleteLayer.prototype.setStructure = function(obj) {
	this.structure = obj;
};
gb.geoserver.DeleteLayer.prototype.setUrl = function(url) {
	if (typeof url === "string") {
		this.url = url;
	}
};
gb.geoserver.DeleteLayer.prototype.getUrl = function() {
	return this.url;
};