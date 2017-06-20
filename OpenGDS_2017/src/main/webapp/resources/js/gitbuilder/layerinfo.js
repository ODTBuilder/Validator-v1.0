/**
 * 레이어 편집 이력을 관리하는 객체
 * 
 * @author 소이준
 * @date 2017.05.18
 */

var gb;
if (!gb)
	gb = {};
if (!gb.inform)
	gb.inform = {};
gb.inform.LayerInformation = function(opt) {
	var options = opt ? opt : null;
	this.name = options.name ? options.name : null;
	this.id = options.id ? options.id : null;
	this.type = options.type ? options.type : null;
	this.format = options.format ? options.format : null;
	this.epsg = options.epsg ? options.epsg : null; 
	this.ngi = {
		"version" : options.version ? options.version : 2,
		"dim" : options.dim ? options.dim : 2
	}
	this.mbound = options.mbound ? options.mbound : null;
	this.lbound = options.lbound ? options.lbound : null;
	this.represent = options.represent ? options.represent : null;
	this.attributes = options.attributes ? options.attributes : null;
}
gb.inform.LayerInformation.prototype.setName = function(name) {
	this.name = name;
};
gb.inform.LayerInformation.prototype.getName = function() {
	return this.name;
};

gb.inform.LayerInformation.prototype.setId = function(id) {
	this.id = id;
};
gb.inform.LayerInformation.prototype.getId = function() {
	return this.id;
};

gb.inform.LayerInformation.prototype.setType = function(type) {
	this.type = type;
};
gb.inform.LayerInformation.prototype.getType = function() {
	return this.type;
};

gb.inform.LayerInformation.prototype.setFormat = function(format) {
	this.format = format;
};
gb.inform.LayerInformation.prototype.getFormat = function() {
	return this.format;
};

gb.inform.LayerInformation.prototype.setEPSG = function(epsg) {
	this.epsg = epsg;
};
gb.inform.LayerInformation.prototype.getEPSG = function() {
	return this.epsg;
};

gb.inform.LayerInformation.prototype.setNGIVersion = function(ver) {
	this.ngi.version = ver;
};
gb.inform.LayerInformation.prototype.getNGIVersion = function() {
	return this.ngi.version;
};

gb.inform.LayerInformation.prototype.setNGIDim = function(dim) {
	this.ngi.dim = dim;
};
gb.inform.LayerInformation.prototype.getNGIDim = function() {
	return this.ngi.dim;
};

gb.inform.LayerInformation.prototype.setMbound = function(mb) {
	this.mbound = mb;
};
gb.inform.LayerInformation.prototype.getMbound = function() {
	return this.mbound;
};

gb.inform.LayerInformation.prototype.setLbound = function(lb) {
	this.lbound = lb;
};
gb.inform.LayerInformation.prototype.getLbound = function() {
	return this.lbound;
};

gb.inform.LayerInformation.prototype.setRepresent = function(rp) {
	this.represent = rp;
};
gb.inform.LayerInformation.prototype.getRepresent = function() {
	return this.represent;
};

gb.inform.LayerInformation.prototype.setAttributes = function(attrs) {
	this.attributes = attrs;
};
gb.inform.LayerInformation.prototype.getAttributes = function() {
	return this.attributes;
};