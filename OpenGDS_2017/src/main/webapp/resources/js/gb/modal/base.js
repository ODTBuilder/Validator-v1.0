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
	this.width = options.width ? options.width : "auto";
	this.height = options.height ? options.height : "auto";
	this.x = undefined;
	this.y = undefined;
	this.autoOpen = options.autoOpen ? true : false;
	var span = $("<span>").html("&times;");
	var btn = $("<button>").append(span).click(function() {
		that.close();
	});
	this.modalHead = $("<div>").addClass("gb-modal-head").append(btn);
	var body = typeof options.body === "function" ? options.body() : options.body;
	this.modalBody = $("<div>").addClass("gb-modal-body");
	if (body) {
		$(this.modalBody).append(body);
	}
	this.modal = $("<div>").addClass("gb-modal").css({
		"width" : this.width,
		"height" : this.height,
		"position" : "absolute",
		"z-Index" : "999",
		"margin" : "0 auto"
	// "top" : this.y + "px",
	// "left" : this.x + "px"
	}).append(this.modalHead).append(this.modalBody);

	// $(this.modal).draggable({
	// appendTo : "body",
	// containment : "body"
	// });

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
 * 패널 바디를 설정한다.
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
 * 패널을 반환한다.
 * 
 * @method getmodal
 * @return {DOM}
 */
gb.modal.Base.prototype.getModal = function() {
	return this.modal;
};
/**
 * 패널을 나타낸다.
 * 
 * @method open
 */
gb.modal.Base.prototype.open = function() {
	$(".gb-modal-background").css("display", "block");
	this.modal.css("display", "block");
};
/**
 * 패널을 숨긴다.
 * 
 * @method open
 */
gb.modal.Base.prototype.close = function() {
	this.modal.css("display", "none");
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
	$(this.modal).css("width", (width + "px"));
};
/**
 * 너비를 반환한다.
 * 
 * @method getWidth
 * @return { Number } modal width
 */
gb.modal.Base.prototype.getWidth = function() {
	return this.width;
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
	$(this.modal).css("height", (height + "px"));
};

/**
 * 높이를 반환한다.
 * 
 * @method getHeight
 * @return {Number} modal height
 */
gb.modal.Base.prototype.getHeight = function() {
	return this.height;
};
/**
 * x위치를 설정한다.
 * 
 * @method setPositionX
 * @param {Number}
 *            x - modal position x
 */
gb.modal.Base.prototype.setPositionX = function(x) {
	this.positionX = x;
	$(this.modal).css("left", (x + "px"));
};

/**
 * x위치를 반환한다.
 * 
 * @method getPositionX
 * @return {Number} modal position x
 */
gb.modal.Base.prototype.getPositionX = function() {
	return this.positionX;
};
/**
 * y위치를 설정한다.
 * 
 * @method setPositionY
 * @param {Number}
 *            y - modal position y
 */
gb.modal.Base.prototype.setPositionY = function(y) {
	this.positionY = y;
	$(this.modal).css("top", (y + "px"));
};

/**
 * y위치를 반환한다.
 * 
 * @method getPositionY
 * @return {Number} modal position y
 */
gb.modal.Base.prototype.getPositionY = function() {
	return this.positionY;
};