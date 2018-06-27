/**
 * 패널 객체를 정의한다.
 * 
 * @class gb.panel.Base
 * @memberof gb.panel
 * @param {Object}
 *            obj - 생성자 옵션을 담은 객체
 * @param {Number}
 *            obj.width - 패널의 너비 (픽셀)
 * @param {Number}
 *            obj.height - 패널의 높이 (픽셀)
 * @param {Number}
 *            obj.positionX - 패널의 페이지 왼편으로 부터의 거리 (픽셀)
 * @param {Number}
 *            obj.positionY - 패널의 페이지 상단으로 부터의 거리 (픽셀)
 * @param {Boolean}
 *            obj.autoOpen - 패널을 선언과 동시에 표출 할 것인지 선택
 * @version 0.01
 * @author yijun.so
 * @date 2017. 07.26
 */
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
		"width" : !isNaN(parseInt(this.width)) ? this.width + "px" : "auto",
		"height" : !isNaN(parseInt(this.height)) ? this.height + "px" : "auto",
		"position" : "absolute",
		"z-Index" : "999",
		"top" : !isNaN(parseInt(this.y)) ? this.y + "px" : 0,
		"left" : !isNaN(parseInt(this.x)) ? this.x + "px" : 0
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
 * 패널 바디를 삽입한다.
 * 
 * @method gb.panel.Base#setPanelBody
 * @function
 * @param {(DOM|function)}
 *            body - 패널에 표시될 내용
 */
gb.panel.Base.prototype.setPanelBody = function(body) {
	if (typeof body === "function") {
		$(this.panelBody).append(body());
	} else {
		$(this.panelBody).append(body);
	}
};
/**
 * 패널을 DOM 형태로 반환한다.
 * 
 * @method gb.panel.Base#getPanel
 * @return {DOM} DOM 형태의 패널
 */
gb.panel.Base.prototype.getPanel = function() {
	return this.panel;
};
/**
 * 패널을 나타낸다.
 * 
 * @method gb.panel.Base#open
 */
gb.panel.Base.prototype.open = function() {
	this.panel.css("display", "block");
};
/**
 * 패널을 숨긴다.
 * 
 * @method gb.panel.Base#close
 */
gb.panel.Base.prototype.close = function() {
	this.panel.css("display", "none");
};
/**
 * 패널의 너비를 설정한다.
 * 
 * @method gb.panel.Base#setWidth
 * @param {Number}
 *            width - 패널의 너비 (픽셀)
 */
gb.panel.Base.prototype.setWidth = function(width) {
	this.width = width;
	$(this.panel).css("width", (width + "px"));
};
/**
 * 너비를 반환한다.
 * 
 * @method gb.panel.Base#getWidth
 * @return {Number} 패널의 너비 (픽셀)
 */
gb.panel.Base.prototype.getWidth = function() {
	return this.width;
};
/**
 * 높이를 설정한다.
 * 
 * @method gb.panel.Base#setHeight
 * @param {Number}
 *            height - 패널의 높이 (픽셀)
 */
gb.panel.Base.prototype.setHeight = function(height) {
	this.height = height;
	$(this.panel).css("height", (height + "px"));
};

/**
 * 높이를 반환한다.
 * 
 * @method gb.panel.Base#getHeight
 * @return {Number} 패널의 높이 (픽셀)
 */
gb.panel.Base.prototype.getHeight = function() {
	return this.height;
};
/**
 * 패널의 수평 위치를 설정한다.
 * 
 * @method gb.panel.Base#setPositionX
 * @param {Number}
 *            x - body 왼편으로 부터의 거리 (픽셀)
 */
gb.panel.Base.prototype.setPositionX = function(x) {
	this.positionX = x;
	$(this.panel).css("left", (x + "px"));
};

/**
 * 패널의 수평 위치를 반환한다.
 * 
 * @method gb.panel.Base#getPositionX
 * @return {Number} body 왼편으로 부터의 거리 (픽셀)
 */
gb.panel.Base.prototype.getPositionX = function() {
	return this.positionX;
};
/**
 * 패널의 수직 위치를 설정한다.
 * 
 * @method gb.panel.Base#setPositionY
 * @param {Number}
 *            y - body 상단으로 부터의 거리 (픽셀)
 */
gb.panel.Base.prototype.setPositionY = function(y) {
	this.positionY = y;
	$(this.panel).css("top", (y + "px"));
};

/**
 * 패널의 수직 위치를 반환한다.
 * 
 * @method gb.panel.Base#getPositionY
 * @return {Number} body 상단으로 부터의 거리 (픽셀)
 */
gb.panel.Base.prototype.getPositionY = function() {
	return this.positionY;
};