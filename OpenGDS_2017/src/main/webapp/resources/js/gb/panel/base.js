/**
 * 드래그 패널 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2017. 07.26
 * @version 0.01
 * @class gb.panel.Base
 * @constructor
 * @param {String}
 *            width (ex: 10px)
 * @param {String}
 *            height (ex: 10px)
 * @param {Number}
 *            positionX
 * @param {Number}
 *            positionY
 * @param {Boolean}
 *            autoOpen
 */
var gb;
if (!gb)
	gb = {};
if (!gb.panel)
	gb.panel = {};
gb.panel.Base = function(obj) {
	var that = this;
	var options = obj ? obj : {};
	this.width = options.width ? options.width : "auto";
	this.height = options.height ? options.height : "auto";
	this.x = options.positionX ? options.positionX : 0;
	this.y = options.positionY ? options.positionY : 0;
	this.autoOpen = options.autoOpen ? true : false;
	var span = $("<span>").html("&times;");
	var btn = $("<button>").append(span).click(function() {
		that.close();
	});
	this.panelHead = $("<div>").addClass("gb-panel-head").append(btn);
	var body = typeof options.body === "function" ? options.body() : options.body;
	this.panelBody = $("<div>").addClass("gb-panel-body");
	if (body) {
		$(this.panelBody).append(body);
	}
	this.panel = $("<div>").addClass("gb-panel").css({
		"width" : this.width,
		"height" : this.height,
		"position" : "absolute",
		"z-Index" : "999",
		"top" : this.y + "px",
		"left" : this.x + "px"
	}).append(this.panelHead).append(this.panelBody);

	// $(this.panel).draggable({
	// appendTo : "body",
	// containment : "body"
	// });

	$("body").append(this.panel);

	if (this.autoOpen) {
		this.open();
	} else {
		this.close();
	}
};
/**
 * 패널 바디를 설정한다.
 * 
 * @method setPanelBody
 * @param {DOM}
 */
gb.panel.Base.prototype.setPanelBody = function(body) {
	if (typeof body === "function") {
		$(this.panelBody).append(body());
	} else {
		$(this.panelBody).append(body);
	}
};
/**
 * 패널을 반환한다.
 * 
 * @method getPanel
 * @return {DOM}
 */
gb.panel.Base.prototype.getPanel = function() {
	return this.panel;
};
/**
 * 패널을 나타낸다.
 * 
 * @method open
 */
gb.panel.Base.prototype.open = function() {
	this.panel.css("display", "block");
};
/**
 * 패널을 숨긴다.
 * 
 * @method open
 */
gb.panel.Base.prototype.close = function() {
	this.panel.css("display", "none");
};
/**
 * 너비를 설정한다.
 * 
 * @method setWidth
 * @param {
 *            Number } width - panel width
 */
gb.panel.Base.prototype.setWidth = function(width) {
	this.width = width;
	$(this.panel).css("width", (width + "px"));
};
/**
 * 너비를 반환한다.
 * 
 * @method getWidth
 * @return { Number } panel width
 */
gb.panel.Base.prototype.getWidth = function() {
	return this.width;
};
/**
 * 높이를 설정한다.
 * 
 * @method setHeight
 * @param {Number}
 *            height - panel height
 */
gb.panel.Base.prototype.setHeight = function(height) {
	this.height = height;
	$(this.panel).css("height", (height + "px"));
};

/**
 * 높이를 반환한다.
 * 
 * @method getHeight
 * @return {Number} panel height
 */
gb.panel.Base.prototype.getHeight = function() {
	return this.height;
};
/**
 * x위치를 설정한다.
 * 
 * @method setPositionX
 * @param {Number}
 *            x - panel position x
 */
gb.panel.Base.prototype.setPositionX = function(x) {
	this.positionX = x;
	$(this.panel).css("left", (x + "px"));
};

/**
 * x위치를 반환한다.
 * 
 * @method getPositionX
 * @return {Number} panel position x
 */
gb.panel.Base.prototype.getPositionX = function() {
	return this.positionX;
};
/**
 * y위치를 설정한다.
 * 
 * @method setPositionY
 * @param {Number}
 *            y - panel position y
 */
gb.panel.Base.prototype.setPositionY = function(y) {
	this.positionY = y;
	$(this.panel).css("top", (y + "px"));
};

/**
 * y위치를 반환한다.
 * 
 * @method getPositionY
 * @return {Number} panel position y
 */
gb.panel.Base.prototype.getPositionY = function() {
	return this.positionY;
};