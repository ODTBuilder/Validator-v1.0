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
gb.edit.LayerInformation = function(obj) {
	var options = obj;
	this.url = options.url ? options.url : null;
	this.layer;
	this.window;
	this.originInfo;
	this.currentInfo;

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
gb.edit.LayerInformation.prototype.open = function() {
	this.window.modal('show');
};
gb.edit.LayerInformation.prototype.close = function() {
	this.window.modal('hide');
};
gb.edit.LayerInformation.prototype.save = function(obj) {

};
gb.edit.LayerInformation.prototype.load = function(name, code) {
	var that = this;
	var arr = {
		"geoLayerList" : [ name ]
	}
	console.log(JSON.stringify(arr));
	$.ajax({
		url : that.getUrl(),
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
			var nameInput = $("<input>").addClass("form-control").attr({
				"type" : "text"
			}).val(code);
			var div1 = $("<div>").css("margin-bottom", "10px").append(name).append(nameInput);

			var title = $("<p>").text("Title");
			var titleInput = $("<input>").addClass("form-control").attr({
				"type" : "text"
			}).val(data[0].title);
			var div2 = $("<div>").css("margin-bottom", "10px").append(title).append(titleInput);

			var summary = $("<p>").text("Summary");
			var summaryInput = $("<textarea>").addClass("form-control").attr({
				"rows" : "3"
			}).text(data[0].abstractContent);
			var div3 = $("<div>").css("margin-bottom", "10px").append(summary).append(summaryInput);

			var minBound = $("<p>").text("Minimum Boundary of Original Data");

			var td1 = $("<td>").text("Min X");

			var td2 = $("<td>").text("Min Y");

			var td3 = $("<td>").text("Max X");

			var td4 = $("<td>").text("Max Y");

			var tr1 = $("<thead>").append(td1).append(td2).append(td3).append(td4);

			var bminx2 = $("<input>").addClass("form-control").attr({
				"type" : "text",
				"disabled" : true
			}).val(data[0].nbBox.minx);
			var td11 = $("<td>").append(bminx2);

			var bminy2 = $("<input>").addClass("form-control").attr({
				"type" : "text",
				"disabled" : true
			}).val(data[0].nbBox.miny);
			var td22 = $("<td>").append(bminy2);

			var bmaxx2 = $("<input>").addClass("form-control").attr({
				"type" : "text",
				"disabled" : true
			}).val(data[0].nbBox.maxx);
			var td33 = $("<td>").append(bmaxx2);

			var bmaxy2 = $("<input>").addClass("form-control").attr({
				"type" : "text",
				"disabled" : true
			}).val(data[0].nbBox.maxy);
			var td44 = $("<td>").append(bmaxy2);

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

			var bminx3 = $("<input>").addClass("form-control").attr({
				"type" : "text",
				"disabled" : true
			}).val(data[0].llbBox.minx);
			var td1111 = $("<td>").append(bminx3);

			var bminy3 = $("<input>").addClass("form-control").attr({
				"type" : "text",
				"disabled" : true
			}).val(data[0].llbBox.miny);
			var td2222 = $("<td>").append(bminy3);

			var bmaxx3 = $("<input>").addClass("form-control").attr({
				"type" : "text",
				"disabled" : true
			}).val(data[0].llbBox.maxx);
			var td3333 = $("<td>").append(bmaxx3);

			var bmaxy3 = $("<input>").addClass("form-control").attr({
				"type" : "text",
				"disabled" : true
			}).val(data[0].llbBox.maxy);
			var td4444 = $("<td>").append(bmaxy3);

			var tr1111 = $("<tr>").append(td1111).append(td2222).append(td3333).append(td4444);
			var tbd2 = $("<tbody>").append(tr1111);
			var tb2 = $("<table>").addClass("table").addClass("text-center").append(tr111).append(tbd2);
			var div5 = $("<div>").css("margin-bottom", "10px").append(lonlatBound).append(tb2);

			var attrkey = $("<p>").text("Attribute");
			var thtd1 = $("<td>").text("Name");
			var thtd2 = $("<td>").text("Type");
			var thtd3 = $("<td>").text("Nullable");
			var thead = $("<thead>").append(thtd1).append(thtd2).append(thtd3);
			var tbody = $("<tbody>");
			var fttb = $("<table>").addClass("table").addClass("text-center").append(thead).append(tbody);
			var keys = Object.keys(data[0].attInfo);
			for (var i = 0; i < keys.length; i++) {
				var input = $("<input>").addClass("form-control").attr({
					"type" : "text"
				}).val(keys[i]);
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
				var tr = $("<tr>").append(td1).append(td2).append(td3);
				$(tbody).append(tr);
			}
			var div6 = $("<div>").css("margin-bottom", "10px").append(attrkey).append(fttb);
			$(that.body).empty();
			$(that.body).append(div1).append(div2).append(div3).append(div4).append(div5).append(div6);
			// $(body).addClass("modal-body");

			that.open();
		}
	});
};
gb.edit.LayerInformation.prototype.setUrl = function(url) {
	if (typeof url === "string") {
		this.url = url;
	}
};
gb.edit.LayerInformation.prototype.getUrl = function() {
	return this.url;
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
gb.edit.LayerInformation.prototype.getInformationForm = function() {
	this.body
	return;
};