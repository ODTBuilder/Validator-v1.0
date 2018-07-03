/**
 * 임베드 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2017. 07.26
 * @version 0.01
 * @class gb.embed.Base
 * @constructor
 * 
 */
var gb;
if (!gb)
	gb = {};
if (!gb.embed)
	gb.embed = {};
gb.embed.Base = function(obj) {
	var that = this;
	var options = obj ? obj : {};
	var body = typeof options.body === "function" ? options.body() : options.body;
	this.panelBody = $("<div>").addClass("panel-body");
	if (body) {
		$(this.panelBody).append(body);
	}
	this.panel = $("<div>").addClass("panel").addClass("panel-default").append(this.panelBody);
	if (typeof options.append === "string") {
		$(options.append).append(this.panel);
	}

};
/**
 * 패널 바디를 설정한다.
 * 
 * @method setPanelBody
 * @param {DOM}
 */
gb.embed.Base.prototype.setEmbedBody = function(body) {
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
gb.embed.Base.prototype.getEmbed = function() {
	return this.panel;
};
/**
 * 패널을 나타낸다.
 * 
 * @method open
 */
gb.embed.Base.prototype.open = function() {
	this.panel.css("display", "block");
};
/**
 * 패널을 숨긴다.
 * 
 * @method open
 */
gb.embed.Base.prototype.close = function() {
	this.panel.css("display", "none");
};
