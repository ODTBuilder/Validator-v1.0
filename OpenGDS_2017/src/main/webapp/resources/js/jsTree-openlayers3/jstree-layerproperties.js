// =================================소이준===================================
/**
 * ### LayerProperties plugin
 * 
 * 레이어에 따라 다른 컨텍스트 메뉴 및 요청을 처리
 */
/**
 * stores all defaults for the layer properties plugin
 * 
 * @name $.jstreeol3.defaults.layerproperties
 * @plugin layerproperties
 */
$.jstreeol3.defaults.layerproperties = {

};

$.jstreeol3.plugins.layerproperties = function(options, parent) {
	var that = this;

	this.init = function(el, options) {
		this._data.layerproperties = {};
		parent.init.call(this, el, options);
	};
	this.bind = function() {
		parent.bind.call(this);
		this._data.layerproperties.properties = this.settings.layerproperties.properties;
		this._data.layerproperties.style = this.settings.layerproperties.style;
	};

};
// =================================소이준===================================
