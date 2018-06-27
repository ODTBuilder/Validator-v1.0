/**
 * 검수 코멘트 정보 모달
 * 
 * @author yijun.so
 * @date 2018. 03.28
 * @version 0.01
 * @class gb.modal.LayerDefinition
 * @constructor
 */
var gb;
if (!gb)
	gb = {};
if (!gb.modal)
	gb.modal = {};
gb.modal.DetailInformation = function(obj) {
	var options = obj ? obj : {};
	var that = this;
	this.target = typeof options.target === "string" ? options.target : undefined;

	this.content = $("<div>");

	var xSpan = $("<span>").attr({
		"aria-hidden" : "true"
	}).append("&times;");
	var xBtn = $("<button>").addClass("close").attr({
		"data-dismiss" : "modal",
		"aria-label" : "Close"
	}).append(xSpan);
	var title = $("<h4>").addClass("modal-title").text("상세 정보");
	var header = $("<div>").addClass("modal-header").append(xBtn).append(title);

	var body = $("<div>").addClass("modal-body").append(this.content);

	var closeBtn = $("<button>").addClass("btn").addClass("btn-default").attr({
		"type" : "button",
		"data-dismiss" : "modal"
	}).text("확인");

	var footer = $("<div>").addClass("modal-footer").append(closeBtn);

	var cont = $("<div>").addClass("modal-content").append(header).append(body).append(footer);
	var dial = $("<div>").addClass("modal-dialog").append(cont);
	this.modal = $("<div>").addClass("modal").addClass("fade").append(dial);
	$("body").append(this.modal);
	$(this.modal).modal("hide");

	$(document).on("click", this.target, function() {
		that.open(this);
	});
};
gb.modal.DetailInformation.prototype.constructor = gb.modal.DetailInformation;

gb.modal.DetailInformation.prototype.open = function(btn) {
	$(this.content).empty();
	$(this.content).html($(btn).attr("comment"));
	$(this.modal).modal("show");
};