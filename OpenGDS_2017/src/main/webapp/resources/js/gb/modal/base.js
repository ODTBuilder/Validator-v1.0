/**
 * 모달 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2017. 07.26
 * @version 0.01
 * @class gb.modal.Base
 * @constructor
 */
var gb;
if (!gb)
	gb = {};
if (!gb.modal)
	gb.modal = {};
gb.modal.Base = function(obj) {
	var that = this;
	var options = obj ? obj : {};
	this.title = options.title ? options.title : "";
	this.width = options.width ? options.width : "auto";
	this.height = options.height ? options.height : "auto";
	this.autoOpen = options.autoOpen ? true : false;
	var span = $("<span>").html("&times;");
	var btn = $("<button>").append(span).click(function() {
		that.close();
	});
	this.titleArea = $("<span>").addClass("gb-modal-title").text(this.title);
	this.modalHead = $("<div>").addClass("gb-modal-head").append(this.titleArea).append(btn);
	var body = typeof options.body === "function" ? options.body() : options.body;
	this.modalBody = $("<div>").addClass("gb-modal-body");
	if (body) {
		$(this.modalBody).append(body);
	}
// this.buttonArea = $("<span>").addClass("gb-modal-buttons");
// this.modalFooter =
// $("<div>").addClass("gb-modal-footer").append(this.buttonArea);
	this.modal = $("<div>").addClass("gb-modal").css({
		"width" : typeof this.width === "number" ? this.width+"px" : this.width,
		"height" : typeof this.height === "number" ? this.height+"px" : this.height,
		"position" : "absolute",
		"z-Index" : "999"
	}).append(this.modalHead).append(this.modalBody);

	if (!$(".gb-modal-background")[0]) {
		this.background = $("<div>").addClass("gb-modal-background");
		$("body").append(this.background);
	}
	$("body").append(this.modal);

	if (this.autoOpen) {
		this.open();
	} else {
		this.close();
	}
};
/**
 * 모달 바디를 반환한다.
 * 
 * @method getModalBody
 * @param {DOM}
 */
gb.modal.Base.prototype.getModalBody = function() {
	return this.modalBody;;
};
/**
 * 모달 바디를 설정한다.
 * 
 * @method setModalBody
 * @param {DOM}
 */
gb.modal.Base.prototype.setModalBody = function(body) {
	if (typeof body === "function") {
		$(this.modalBody).append(body());
	} else {
		$(this.modalBody).append(body);
	}
};
/**
 * 모달을 반환한다.
 * 
 * @method getmodal
 * @return {DOM}
 */
gb.modal.Base.prototype.getModal = function() {
	return this.modal;
};
/**
 * 모달을 나타낸다.
 * 
 * @method open
 */
gb.modal.Base.prototype.open = function() {
	$(".gb-modal-background").css("display", "block");
	this.modal.css("display", "block");
	this.refreshPosition();
};
/**
 * 모달을 숨긴다.
 * 
 * @method open
 */
gb.modal.Base.prototype.close = function() {
	$(".gb-modal-background").css("display", "none");
	this.modal.css("display", "none");
};
/**
 * 모달위치를 최신화한다.
 * 
 * @method refreshPosition
 */
gb.modal.Base.prototype.refreshPosition = function() {
	$(this.modal).css({
		"top" : ($(window).innerHeight() / 2) - (this.getHeight()/2+50) + "px",
		"left" : ($(window).innerWidth() / 2) - (this.getWidth()/2) + "px"
	});
};
/**
 * 너비를 설정한다.
 * 
 * @method setWidth
 * @param {
 *            Number } width - modal width
 */
gb.modal.Base.prototype.setWidth = function(width) {
	this.width = width;
	var value;
	if (typeof width === "number") {
		if (height > 0) {
			value = width + "px";
		} else {
			value = 0;
		}
	} else if (width === "auto"){
		value = "auto";
	}
	$(this.modal).css("width", value);
	this.refreshPosition();
};
/**
 * 너비를 반환한다.
 * 
 * @method getWidth
 * @return { Number } modal width
 */
gb.modal.Base.prototype.getWidth = function() {
	return $(this.modal).outerWidth();
};
/**
 * 높이를 설정한다.
 * 
 * @method setHeight
 * @param {Number}
 *            height - modal height
 */
gb.modal.Base.prototype.setHeight = function(height) {
	this.height = height;
	var value;
	if (typeof height === "number") {
		if (height > 0) {
			value = height + "px";
		} else {
			value = 0;
		}
	} else if (height === "auto"){
		value = "auto";
	}
	$(this.modal).css("height", value);
	this.refreshPosition();
};

/**
 * 높이를 반환한다.
 * 
 * @method getHeight
 * @return {Number} modal height
 */
gb.modal.Base.prototype.getHeight = function() {
	return $(this.modal).outerHeight();
};