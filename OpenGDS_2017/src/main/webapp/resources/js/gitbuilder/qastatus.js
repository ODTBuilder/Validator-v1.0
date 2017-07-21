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
	var htd0 = $("<td>").text("#");
	var htd1 = $("<td>").text("Format");
	var htd2 = $("<td>").text("Map sheet number");
	var htd3 = $("<td>").text("Status");
	var htd4 = $("<td>").text("Requested time");
	var htd5 = $("<td>").text("Completed time");
	var htd6 = $("<td>").text("Report");
	var htd7 = $("<td>").text("Download");
	var htr = $("<tr>").append(htd0).append(htd1).append(htd2).append(htd3).append(htd4).append(htd5).append(htd6).append(htd7);
	var thead = $("<thead>").append(htr);
	this.tbody = $("<tbody>");
	this.tb = $("<table>").addClass("table").addClass("text-center").append(thead).append(this.tbody);
	this.listArea = $("<div>").append(this.tb);

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
		}
	});
	var td0 = $("<td>").text("1");
	var td1 = $("<td>").text("NGI");
	var td2 = $("<td>").text("37712003");
	var td3 = $("<td>").text("Completed");
	var td4 = $("<td>").text("2017-07-20 15:38:49");
	var td5 = $("<td>").text("2017-07-20 15:40:49");
	var reportBtn = $("<button>").addClass("btn").addClass("btn-default").text("Click");
	var td6 = $("<td>").append(reportBtn);
	var downBtn = $("<button>").addClass("btn").addClass("btn-default").text("Click");
	var td7 = $("<td>").append(downBtn);
	var tr = $("<tr>").append(td0).append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7);
	$(this.tbody).append(tr);
};
gb.qa.QAStatus.prototype.setReport = function() {
	$(this.rtb).DataTable({
		columns : [ {
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
};
gb.qa.QAStatus.prototype.setStatusURL = function(url) {
	if (typeof url === "string") {
		this.statusURL = url;
	}
};
gb.qa.QAStatus.prototype.getStatusURL = function() {
	return this.statusURL
};