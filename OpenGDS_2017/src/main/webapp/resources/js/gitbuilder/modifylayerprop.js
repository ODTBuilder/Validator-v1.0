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
	// this.url = options.URL ? options.URL : null;
	this.format = undefined;
	this.type = undefined;
	this.refer = options.refer ? options.refer : undefined;
	this.map = options.map ? options.map : undefined;
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
		"name" : "gitbuilder-createvector-radio",
		"disabled" : true
	}).change(function() {
		that.setForm("shp", "layer");
	});
	var label3 = $("<label>").addClass("radio-inline").append(this.formatRadio3).append("SHP");
	$(formatArea).append(label1).append(label2).append(label3);

	this.body = $("<div>").append(formatArea).append(div1).append(this.layerNameForm).append(this.typeForm).append(this.attrForm).append(
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
gb.edit.ModifyLayerProperties.prototype.open = function() {
	this.window.modal('show');
};
gb.edit.ModifyLayerProperties.prototype.close = function() {
	this.window.modal('hide');
};
