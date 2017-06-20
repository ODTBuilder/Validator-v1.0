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
gb.edit.LayerInformation = function() {
	this.layer;
	this.window;

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
	htag.text("Layer Information");
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

	var name = $("<p>").text("Name");
	var nameInput = $("<input>").addClass("form-control").attr({
		"type" : "text"
	});

	var title = $("<p>").text("Title");
	var titleInput = $("<input>").addClass("form-control").attr({
		"type" : "text"
	});

	var summary = $("<p>").text("Summary");
	var summaryInput = $("<textarea>").addClass("form-control").attr({
		"rows" : "3"
	});

	var minBound = $("<p>").text("Minimum Boundary of Original Data");

	var bminx = $("<p>").text("Min X");
	var td1 = $("<td>").append(bminx);

	var bminy = $("<p>").text("Min Y");
	var td2 = $("<td>").append(bminy);

	var bmaxx = $("<p>").text("Max X");
	var td3 = $("<td>").append(bmaxx);

	var bmaxy = $("<p>").text("Max Y");
	var td4 = $("<td>").append(bmaxy);

	var tr1 = $("<tr>").append(td1).append(td2).append(td3).append(td4);

	var bminx2 = $("<input>").addClass("form-control").attr({
		"type" : "text"
	});
	var td11 = $("<td>").append(bminx2);

	var bminy2 = $("<input>").addClass("form-control").attr({
		"type" : "text"
	});
	var td22 = $("<td>").append(bminy2);

	var bmaxx2 = $("<input>").addClass("form-control").attr({
		"type" : "text"
	});
	var td33 = $("<td>").append(bmaxx2);

	var bmaxy2 = $("<input>").addClass("form-control").attr({
		"type" : "text"
	});
	var td44 = $("<td>").append(bmaxy2);

	var tr11 = $("<tr>").append(td11).append(td22).append(td33).append(td44);

	var tb1 = $("<table>").addClass("table").append(tr1).append(tr11);

	var lonlatBound = $("<p>").text("Latitude / Longitude Area");

	var bminx1 = $("<p>").text("Min X");
	var td111 = $("<td>").append(bminx1);

	var bminy1 = $("<p>").text("Min Y");
	var td222 = $("<td>").append(bminy1);

	var bmaxx1 = $("<p>").text("Max X");
	var td333 = $("<td>").append(bmaxx1);

	var bmaxy1 = $("<p>").text("Max Y");
	var td444 = $("<td>").append(bmaxy1);

	var tr111 = $("<tr>").append(td111).append(td222).append(td333).append(td444);

	var bminx3 = $("<input>").addClass("form-control").attr({
		"type" : "text"
	});
	var td1111 = $("<td>").append(bminx3);

	var bminy3 = $("<input>").addClass("form-control").attr({
		"type" : "text"
	});
	var td2222 = $("<td>").append(bminy3);

	var bmaxx3 = $("<input>").addClass("form-control").attr({
		"type" : "text"
	});
	var td3333 = $("<td>").append(bmaxx3);

	var bmaxy3 = $("<input>").addClass("form-control").attr({
		"type" : "text"
	});
	var td4444 = $("<td>").append(bmaxy3);

	var tr1111 = $("<tr>").append(td1111).append(td2222).append(td3333).append(td4444);

	var tb2 = $("<table>").addClass("table").append(tr111).append(tr1111);

	var ftb = $("<table>");

	var body = $("<div>").append(name).append(nameInput).append(title).append(titleInput).append(summary).append(summaryInput).append(minBound).append(tb1).append(
			lonlatBound).append(tb2);
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
	var content = $("<div>").append(header).append(body).append(footer);
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
gb.edit.LayerInformation.prototype.open = function() {
	this.window.modal('show');
};
gb.edit.LayerInformation.prototype.close = function() {
	this.window.modal('hide');
};
gb.edit.LayerInformation.prototype.save = function(obj) {

};
gb.edit.LayerInformation.prototype.load = function(obj) {

};
gb.edit.LayerInformation.prototype.setName = function(name) {
	return;
};
gb.edit.LayerInformation.prototype.setTitle = function(title) {
	return;
};
gb.edit.LayerInformation.prototype.setAttributeType = function() {
	return;
};