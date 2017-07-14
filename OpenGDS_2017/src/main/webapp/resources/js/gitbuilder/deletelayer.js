/**
 * 레이어 정보를 변경하는 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2017. 06. 16
 * @version 0.01
 */
var gb;
if (!gb)
	gb = {};
if (!gb.geoserver)
	gb.geoserver = {};
gb.geoserver.DeleteLayer = function(obj) {
	var that = this;
	var options = obj;
	this.url = options.URL ? options.URL : null;
	this.format = undefined;
	this.type = undefined;
	this.refer = undefined;
	this.structure = {};
};
gb.geoserver.DeleteLayer.prototype.setReference = function(refer) {
	this.refer = refer;
};
gb.geoserver.DeleteLayer.prototype.getReference = function() {
	return this.refer;
};
gb.geoserver.DeleteLayer.prototype.save = function(obj) {
	var that = this;
	$.ajax({
		url : this.getUrl(),
		method : "POST",
		contentType : "application/json; charset=UTF-8",
		cache : false,
		// async : false,
		data : JSON.stringify(obj),
		beforeSend : function() {
			$("body").css("cursor", "wait");
		},
		complete : function() {
			$("body").css("cursor", "default");
		},
		traditional : true,
		success : function(data, textStatus, jqXHR) {
			console.log(data);
			that.getReference().refresh();
		}
	});
};
gb.geoserver.DeleteLayer.prototype.addStructure = function(format, mapsheet, scope, layers) {
	var strc = this.getStructure();
	if (typeof strc === "object") {
		if (!strc.hasOwnProperty("layer")) {
			strc["layer"] = {};
		}
		if (!strc["layer"].hasOwnProperty(format)) {
			strc["layer"][format] = {};
		}
		if (!strc["layer"][format].hasOwnProperty(mapsheet)) {
			strc["layer"][format][mapsheet] = {};
		}
		// if (!strc["layer"][format][mapsheet].hasOwnProperty("remove")) {
		// strc["layer"][format][mapsheet]["remove"] = {};
		// }
	}
	var obj = {};
	obj["scope"] = typeof scope === "string" ? scope : false;
	obj["layer"] = Array.isArray(layers) ? layers : false;
	if (!obj["layer"] || !obj["scope"]) {
		return;
	}
	strc["layer"][format][mapsheet]["remove"] = obj;
	// this.structure.push(obj);
};
gb.geoserver.DeleteLayer.prototype.getStructure = function() {
	return this.structure;
};
gb.geoserver.DeleteLayer.prototype.setStructure = function(obj) {
	this.structure = obj;
};
gb.geoserver.DeleteLayer.prototype.setUrl = function(url) {
	if (typeof url === "string") {
		this.url = url;
	}
};
gb.geoserver.DeleteLayer.prototype.getUrl = function() {
	return this.url;
};