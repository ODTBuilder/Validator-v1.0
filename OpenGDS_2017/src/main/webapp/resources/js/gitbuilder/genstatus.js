/**
 * 검수현황을 조회하는 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2017. 06. 16
 * @version 0.01
 */
var gb;
if (!gb)
	gb = {};
if (!gb.gen)
	gb.gen = {};
gb.gen.GenStatus = function(obj) {
	var that = this;
	var options = obj;
	this.window;
	this.statusURL = options.statusURL ? options.statusURL : undefined;
	this.errorURL = options.errorURL ? options.errorURL : undefined;
	this.downloadURL = options.downloadURL ? options.downloadURL : undefined;
	this.listInstance = undefined;
	this.reportInstance = undefined;

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
	this.htag.text("Generalization Status");
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
	this.naviArea = $("<div>").css("margin-bottom", "10px");

	this.tb = $("<table>").addClass("gb-genstatus-list");
	this.listArea = $("<div>").append(this.tb);

	// $(document).on('click', '.gb-genstatus-list tbody
	// .gb-genstatus-reportBtn', function() {
	// var data = that.listInstance.row($(this).parents('tr')).data();
	// that.setReport(data[2]);
	// console.log(data);
	// });
	// $(document).on('click', '.gb-genstatus-list tbody .gb-genstatus-downBtn',
	// function() {
	// // var data = table.row( $(this).parents('tr') ).data();
	// // alert( data[0] +"'s salary is: "+ data[ 5 ] );
	// console.log("down");
	// var data = that.listInstance.row($(this).parents('tr')).data();
	// var format = data[0];
	// var layerName = data[2];
	// that.downloadGenLayer(format, layerName);
	// });

	this.rtb = $("<table>").addClass("table").addClass("table-striped");
	this.reportArea = $("<div>").append(this.rtb);

	this.body = $("<div>").append(this.naviArea).append(this.listArea).append(
			this.reportArea);
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

	var pright = $("<span>").css("float", "right");
	$(pright).append(closeBtn);

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
	$(dialog).addClass("modal-dialog").css("width", "1101px");
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
		show : false
	});
};
gb.gen.GenStatus.prototype.open = function() {
	this.window.modal('show');
	this.setList();
	this.switchArea("list");
};
gb.gen.GenStatus.prototype.close = function() {
	this.window.modal('hide');
};
gb.gen.GenStatus.prototype.downloadGenLayer = function(format, layerName) {
	var that = this;
	var obj = {
		"format" : format,
		"type" : "layer",
		"name" : layerName
	}

	var path = this.downloadURL;
	var target = "gitWindow";
	var form = document.createElement("form");
	form.setAttribute("method", "post");
	form.setAttribute("action", path);
	var keys = Object.keys(obj);
	for (var k = 0; k < keys.length; k++) {
		var hiddenField = document.createElement("input");
		hiddenField.setAttribute("type", "hidden");
		hiddenField.setAttribute("name", keys[k]);
		hiddenField.setAttribute("value", obj[keys[k]]);
		form.appendChild(hiddenField);
	}
	form.target = target;
	document.body.appendChild(form);
	form.submit();
};
gb.gen.GenStatus.prototype.switchNavi = function(area) {
	var that = this;
	if (area === "list") {
		$(this.naviArea).empty();
	} else if (area === "report") {
		$(this.naviArea).empty();
		var icon = $("<i>").addClass("fa").addClass("fa-reply").attr({
			"aria-hidden" : true
		});
		var backBtn = $("<button>").addClass("btn").addClass("btn-default")
				.append(icon).append(" Back").click(function() {
					that.switchArea("list");
				});
		$(this.naviArea).append(backBtn);
	}
};
gb.gen.GenStatus.prototype.switchArea = function(area) {
	this.switchNavi(area);
	if (area === "list") {
		$(this.listArea).show();
		$(this.reportArea).hide();
	} else if (area === "report") {
		$(this.listArea).hide();
		$(this.reportArea).show();
	}
};
gb.gen.GenStatus.prototype.setList = function() {
	var that = this;
	var obj = {
		"atest" : "hi"
	};
	$.ajax({
		url : that.getStatusURL(),
		method : "POST",
		contentType : "application/json; charset=UTF-8",
		cache : false,
		data : JSON.stringify(obj),
		beforeSend : function() { // 호출전실행
			$("body").css("cursor", "wait");
		},
		complete : function() {
			$("body").css("cursor", "default");
		},
		traditional : true,
		success : function(data, textStatus, jqXHR) {
			console.log(data);
			// var dxf = data["dxf"];
			// var ngi = data["ngi"];
			var shp = data["shp"];
			var total = [];
			if (Array.isArray(shp)) {
				for (var i = 0; i < shp.length; i++) {
					var tbData = [ shp[i].CollectionName, shp[i].LayerName,
							shp[i].GenLayerName, shp[i].BeforeFeatures,
							shp[i].AfterFeatures, shp[i].BeforePoints,
							shp[i].AfterPoints ];
					total.push(tbData);
				}
			}
			if (!!that.listInstance) {
				that.listInstance.destroy();
				that.listInstance = undefined;
			}
			that.listInstance = $(that.tb).DataTable({
				"data" : total,
				"columns" : [ {
					title : "Collection Name"
				}, {
					title : "Original Layer Name"
				}, {
					title : "Generalized Layer Name"
				}, {
					title : "Features(Before)"
				}, {
					title : "Features(After)"
				}, {
					title : "Points(Before)"
				}, {
					title : "Points(After)"
				} ]
			});

		}
	});

};
gb.gen.GenStatus.prototype.setReport = function(layer) {
	var that = this;
	var obj = {
		"errorLayer" : layer
	};
	$.ajax({
		url : "errorLayer/errorReport.ajax",
		method : "POST",
		contentType : "application/json; charset=UTF-8",
		cache : false,
		data : JSON.stringify(obj),
		beforeSend : function() { // 호출전실행
			$("body").css("cursor", "wait");
		},
		complete : function() {
			$("body").css("cursor", "default");
		},
		traditional : true,
		success : function(data, textStatus, jqXHR) {
			if (data === null) {
				return;
			}
			var json = JSON.parse(data);
			var arr = json.fields;
			var tableData = [];
			for (var i = 0; i < arr.length; i++) {
				var row = [];
				row.push(arr[i].collectionName);
				row.push(arr[i].layerName);
				row.push(arr[i].featureID);
				row.push(arr[i].errorType);
				row.push(arr[i].errorName);
				row.push(arr[i].errorCoordinateX);
				row.push(arr[i].errorCoordinateY);
				tableData.push(row);
			}
			if (!!that.reportInstance) {
				that.reportInstance.destroy();
				that.reportInstance = undefined;
			}
			that.reportInstance = $(that.rtb).DataTable({
				"data" : tableData,
				"columns" : [ {
					title : "Map sheet number"
				}, {
					title : "Layer name"
				}, {
					title : "Feature ID"
				}, {
					title : "Error type"
				}, {
					title : "Error name"
				}, {
					title : "Coordinate X"
				}, {
					title : "Coordinate Y"
				} ]
			});
			that.switchNavi("report");
			that.switchArea("report");
		}
	});

};
gb.gen.GenStatus.prototype.setStatusURL = function(url) {
	if (typeof url === "string") {
		this.statusURL = url;
	}
};
gb.gen.GenStatus.prototype.getStatusURL = function() {
	return this.statusURL
};