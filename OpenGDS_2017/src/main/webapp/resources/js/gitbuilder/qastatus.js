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
if (!gb.qa)
	gb.qa = {};
gb.qa.QAStatus = function(obj) {
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
	this.htag.text("QA Status");
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

	this.tb = $("<table>").addClass("gb-qastatus-list");
	this.listArea = $("<div>").append(this.tb);

	$(document).on('click', '.gb-qastatus-list tbody .gb-qastatus-reportBtn', function() {
		var data = that.listInstance.row($(this).parents('tr')).data();
		that.setReport(data[2]);
		console.log(data);
	});
	$(document).on('click', '.gb-qastatus-list tbody .gb-qastatus-downBtn', function() {
		// var data = table.row( $(this).parents('tr') ).data();
		// alert( data[0] +"'s salary is: "+ data[ 5 ] );
		console.log("down");
	});

	this.rtb = $("<table>").addClass("table").addClass("table-striped");
	this.reportArea = $("<div>").append(this.rtb);

	this.body = $("<div>").append(this.naviArea).append(this.listArea).append(this.reportArea);
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
		show : false
	});
};
gb.qa.QAStatus.prototype.open = function() {
	this.window.modal('show');
	this.setList();
	this.switchArea("list");
};
gb.qa.QAStatus.prototype.close = function() {
	this.window.modal('hide');
};
gb.qa.QAStatus.prototype.switchNavi = function(area) {
	var that = this;
	if (area === "list") {
		$(this.naviArea).empty();
	} else if (area === "report") {
		$(this.naviArea).empty();
		var icon = $("<i>").addClass("fa").addClass("fa-reply").attr({
			"aria-hidden" : true
		});
		var backBtn = $("<button>").addClass("btn").addClass("btn-default").append(icon).append(" Back").click(function() {
			that.switchArea("list");
		});
		$(this.naviArea).append(backBtn);
	}
};
gb.qa.QAStatus.prototype.switchArea = function(area) {
	this.switchNavi(area);
	if (area === "list") {
		$(this.listArea).show();
		$(this.reportArea).hide();
	} else if (area === "report") {
		$(this.listArea).hide();
		$(this.reportArea).show();
	}
};
gb.qa.QAStatus.prototype.setList = function() {
	var that = this;
	var obj = {
		"atest" : "hi"
	};
	$.ajax({
		url : that.getStatusURL(),
		method : "POST",
		contentType : "application/json; charset=UTF-8",
		cache : false,
		// async : false,
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
			var qa1 = data["QA10"];
			var qa2 = data["QA20"];
			var total = [];
			if (Array.isArray(qa1)) {
				for (var i = 0; i < qa1.length; i++) {
					var stat = qa1[i].state;
					var statText;
					switch (stat) {
					case 0:
						statText = "Request successful";
						break;
					case 1:
						statText = "Validating";
						break;
					case 2:
						statText = "Validation successful";
						break;
					case 3:
						statText = "Validation failure";
						break;
					case 4:
						statText = "Error layer created";
						break;
					case 5:
						statText = "Error layer failed";
						break;
					default:
						break;
					}

					var tbData = [ qa1[i].fileType, qa1[i].collectionName, qa1[i].errLayerName, statText, qa1[i].requestTime,
							qa1[i].responseTime ];
					total.push(tbData);

				}
			}
			if (Array.isArray(qa2)) {
				for (var i = 0; i < qa2.length; i++) {
					var stat = qa2[i].state;
					var statText;
					switch (stat) {
					case 0:
						statText = "Request successful";
						break;
					case 1:
						statText = "Validating";
						break;
					case 2:
						statText = "Validation successful";
						break;
					case 3:
						statText = "Validation failure";
						break;
					case 4:
						statText = "Error layer created";
						break;
					case 5:
						statText = "Error layer failed";
						break;
					default:
						break;
					}

					var tbData = [ qa2[i].fileType, qa2[i].collectionName, qa2[i].errLayerName, statText, qa2[i].requestTime,
							qa2[i].responseTime ];
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
					title : "Format"
				}, {
					title : "Map sheet number"
				}, {
					title : "Layer name"
				}, {
					title : "Status"
				}, {
					title : "Requested time"
				}, {
					title : "Completed time"
				}, {
					title : "Report"
				}, {
					title : "Download"
				} ],
				"columnDefs" : [ {
					"targets" : 6,
					"data" : null,
					"defaultContent" : "<button class='btn btn-default gb-qastatus-reportBtn'>Click</button>"
				}, {
					"targets" : 7,
					"data" : null,
					"defaultContent" : "<button class='btn btn-default gb-qastatus-downBtn'>Click</button>"
				} ]
			});

		}
	});

};
gb.qa.QAStatus.prototype.setReport = function(layer) {
	var that = this;
	var obj = {
		"errorLayer" : layer
	};
	$.ajax({
		url : "errrorLayer/errorReport.ajax",
		method : "POST",
		contentType : "application/json; charset=UTF-8",
		cache : false,
		// async : false,
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

			if (!!that.reportInstance) {
				that.reportInstance.destroy();
				that.reportInstance = undefined;
			}
			that.reportInstance = $(this.rtb).DataTable({
				"data" : null,
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

		}
	});

};
gb.qa.QAStatus.prototype.setStatusURL = function(url) {
	if (typeof url === "string") {
		this.statusURL = url;
	}
};
gb.qa.QAStatus.prototype.getStatusURL = function() {
	return this.statusURL
};