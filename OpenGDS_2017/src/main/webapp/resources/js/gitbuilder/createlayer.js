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
	var options = obj;
	this.window;
	this.url = options.url ? options.url : null;
	this.format = undefined;
	this.type = undefined;

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
	var div1 = $("<div>").append(sheetNum).append(this.sheetNumInput);

	var layerName = $("<p>").text("Layer Name");
	this.layerNameInput = $("<input>").addClass("form-control").attr({
		"type" : "text"
	});
	this.layerNameForm = $("<div>").append(layerName).append(this.layerNameInput);

	this.typeForm = $("<div>");

	this.attrForm = $("<div>");

	this.body = $("<div>").append(div1).append(this.layerNameForm).append(this.typeForm).append(this.attrForm);
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
gb.geoserver.CreateLayer.prototype.open = function() {
	this.window.modal('show');
};
gb.geoserver.CreateLayer.prototype.close = function() {
	this.window.modal('hide');
};
gb.geoserver.CreateLayer.prototype.setForm = function(format, type, sheetNum) {
	this.format = format;
	this.type = type;
	if (type === "mapsheet") {
		$(this.htag).text("Create a map sheet");
		$(this.sheetNumInput).val("");
		$(this.layerNameForm).hide();
		$(this.typeForm).hide();
		$(this.attrForm).hide();

	} else if (type === "layer") {
		$(this.htag).text("Create a layer");
		$(this.sheetNumInput).val(sheetNum);
		$(this.layerNameInput).val("");
		$(this.layerNameForm).show();
		if (format === "dxf") {
			this.initTypeForm("dxf");
			$(this.typeForm).show();
			$(this.attrForm).hide();
		} else if (format === "ngi") {
			this.initTypeForm("ngi");
			$(this.typeForm).show();
			$(this.attrForm).show();
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

};
gb.geoserver.CreateLayer.prototype.load = function(obj) {
	var that = this;

	console.log(JSON.stringify(arr));
	$.ajax({
		url : that.getUrl(),
		method : "POST",
		contentType : "application/json; charset=UTF-8",
		cache : false,
		// async : false,
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
		}
	});
};
gb.geoserver.CreateLayer.prototype.setUrl = function(url) {
	if (typeof url === "string") {
		this.url = url;
	}
};
gb.geoserver.CreateLayer.prototype.getUrl = function() {
	return this.url;
};