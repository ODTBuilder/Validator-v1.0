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
	this.closeBtn = $("<button>").addClass("gb-button").addClass("gb-button-default").text("Close").click(this, function(evt) {
		evt.data.close();
	});
	this.okBtn = $("<button>").addClass("gb-button").addClass("gb-button-primary").text("OK").click(this, function() {
		console.log("do something");
	});

	this.buttonArea = $("<span>").addClass("gb-modal-buttons").append(this.closeBtn).append(this.okBtn);
	this.modalFooter = $("<div>").addClass("gb-modal-footer").append(this.buttonArea);
	$(this.getModal()).append(this.modalFooter);
	var llist = $("<div>").text("Layer list");
	var treeHead = $("<div>").addClass("gb-article-head").append(llist);
	this.treeBody = $("<div>").addClass("gb-article-body").css({
		"height" : "300px",
		"max-height" : "300px"
	});
	this.tree = $("<div>").addClass("gb-article").append(treeHead).append(this.treeBody).css({
		"display" : "inline-block",
		// "float" : "left",
		"width" : (that.getWidth() - 20) * 0.3 + "px"
	});

	var infohead = $("<div>").text("Information");
	this.info = $("<div>");
	var infobody = $("<div>").append(this.info);
	var layerInfo = $("<div>").append(infohead).append(infobody);

	// var tdhead1 = $("<td>").text("#");
	// var tdhead2 = $("<td>").text("Name");
	// var trhead = $("<tr>").append(tdhead1).append(tdhead2);
	// var thead = $("<thead>").append(trhead);
	// that.tbody = $("<tbody>");
	// var tb = $("<table>").css({
	// "margin-bottom" : 0
	// }).append(thead).append(that.tbody);
	// this._addClass(tb, "table");
	// this._addClass(tb, "table-striped");
	// this._addClass(tb, "text-center");
	// var infobody2 = $("<div>").css({
	// "max-height" : "330px",
	// "overflow-y" : "auto",
	// "padding" : 0
	// }).append(tb);
	// this._addClass(infobody2, "panel-body");
	// var layerInfo2 = $("<div>").append(infobody2);
	// this._addClass(layerInfo2, "panel");
	// this._addClass(layerInfo2, "panel-default");
	// var uright = $("<div>").append(layerInfo).append(layerInfo2);
	var uright = $("<div>").append(layerInfo).css({
		"display" : "inline-block",
		"float" : "right",
		"width" : (that.getWidth() - 20) * 0.7 + "px"
	});
	console.log($(this.tree).outerHeight());
	$(this.getModalBody()).append(this.tree).append(uright);

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
};
gb.modal.Generalization.prototype = Object.create(gb.modal.Base.prototype);
gb.modal.Generalization.prototype.constructor = gb.modal.Generalization;