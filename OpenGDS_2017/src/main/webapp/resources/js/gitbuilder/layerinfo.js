/**
 * 레이어 편집 이력을 관리하는 객체
 * 
 * @author 소이준
 * @date 2017.05.18
 */

var gb;
if (!gb)
	gb = {};
if (!gb.layer)
	gb.layer = {};
gb.layer.LayerInfo = function(opt) {
	var options = opt ? opt : null;
	this.name = options.name ? options.name : null;
	this.oldName = options.name ? options.name : null;
	this.id = options.id ? options.id : null;
	this.sheetNum = options.sheetNum ? options.sheetNum : undefined;
	this.attributes = options.attributes ? options.attributes : undefined;
	this.format = options.format ? options.format : null;
	this.srs = options.srs ? options.srs : null;
	this.ngi = {
		"version" : options.NGIVer ? options.NGIVer : 2,
		"dim" : options.NGIDim ? options.NGIDim : 2,
		"represent" : options.NGIRep ? options.NGIRep : null
	};
	this.mbound = options.mbound ? options.mbound : null;
	this.lbound = options.lbound ? options.lbound : null;
	this.isNew = options.isNew !== undefined ? options.isNew : true;
	this.geometry = options.geometry ? options.geometry : "Point";
};
gb.layer.LayerInfo.prototype.updateId = function() {
	var id = "geo_" + this.getFormat() + "_" + this.getSheetNumber() + "_" + this.getName() + "_" + this.getGeometry();
	this.setId(id);
	return id;
};
gb.layer.LayerInfo.prototype.getSheetNumber = function() {
	return this.sheetNum;
};
gb.layer.LayerInfo.prototype.setSheetNumber = function(num) {
	this.sheetNum = num;
};
gb.layer.LayerInfo.prototype.getIsNew = function() {
	return this.isNew;
};
gb.layer.LayerInfo.prototype.setIsNew = function(bool) {
	this.isNew = bool;
};
gb.layer.LayerInfo.prototype.getAttributesJSON = function() {
	var obj = {};
	var attrs = this.attributes;
	for (var i = 0; i < attrs.length; i++) {
		obj[attrs[i].getOriginFieldName()] = {
			"type" : attrs[i].getType(),
			"nillable" : attrs[i].getNull()
		};
	}
	return obj;
};
gb.layer.LayerInfo.prototype.setGeometry = function(geom) {
	this.geometry = geom;
};
gb.layer.LayerInfo.prototype.getGeometry = function() {
	return this.geometry;
};
gb.layer.LayerInfo.prototype.setAttributes = function(attrs) {
	this.attributes = attrs;
};
gb.layer.LayerInfo.prototype.getAttributes = function() {
	return this.attributes;
};

gb.layer.LayerInfo.prototype.setAttribute = function(attr) {
	this.attributes.push(attr);
};
gb.layer.LayerInfo.prototype.getAttribute = function(key) {
	var attrs = this.attributes;
	var result;
	for (var i = 0; i < attrs.length; i++) {
		if (attrs[i].getOriginFieldName() === key) {
			result = attrs[i];
		}
	}
	return result;
};

gb.layer.LayerInfo.prototype.getOldName = function() {
	return this.oldName;
};

gb.layer.LayerInfo.prototype.setName = function(name) {
	this.name = name;
};
gb.layer.LayerInfo.prototype.getName = function() {
	return this.name === null ? this.getOldName() : this.name;
};

gb.layer.LayerInfo.prototype.setId = function(id) {
	this.id = id;
};
gb.layer.LayerInfo.prototype.getId = function() {
	return this.id;
};

gb.layer.LayerInfo.prototype.setFormat = function(format) {
	this.format = format;
};
gb.layer.LayerInfo.prototype.getFormat = function() {
	return this.format;
};

gb.layer.LayerInfo.prototype.setSRS = function(srs) {
	this.srs = srs;
};
gb.layer.LayerInfo.prototype.getSRS = function() {
	return this.srs;
};

gb.layer.LayerInfo.prototype.setNGIVersion = function(ver) {
	this.ngi.version = ver;
};
gb.layer.LayerInfo.prototype.getNGIVersion = function() {
	return this.ngi.version;
};

gb.layer.LayerInfo.prototype.setNGIDim = function(dim) {
	this.ngi.dim = dim;
};
gb.layer.LayerInfo.prototype.getNGIDim = function() {
	return this.ngi.dim;
};

gb.layer.LayerInfo.prototype.setMbound = function(mb) {
	this.mbound = mb;
};
gb.layer.LayerInfo.prototype.getMbound = function() {
	return this.mbound;
};

gb.layer.LayerInfo.prototype.setLbound = function(lb) {
	this.lbound = lb;
};
gb.layer.LayerInfo.prototype.getLbound = function() {
	return this.lbound;
};

gb.layer.LayerInfo.prototype.setNGIRep = function(rp) {
	this.ngi.represent = rp;
};
gb.layer.LayerInfo.prototype.getNGIRep = function() {
	return this.ngi.represent;
};

gb.layer.LayerInfo.prototype.clone = function() {
	var that = this;
	var arr = [];
	for (var i = 0; i < this.getAttributes().length; i++) {
		arr.push(this.getAttributes()[i].clone());
	}
	var obj = new gb.layer.LayerInfo({
		name : this.getName().toString(),
		id : this.getId().toString(),
		format : this.getFormat().toString(),
		srs : this.getSRS().toString(),
		NGIVer : this.getNGIVersion().toString(),
		NGIDim : this.getNGIDim().toString(),
		mbound : this.getMbound().slice(),
		lbound : this.getLbound().slice(),
		NGIRep : this.getRepresent().toString(),
		attributes : arr,
		isNew : this.getIsNew(),
		geometry : this.getGeometry()
	});
	return obj;
};
gb.layer.LayerInfo.prototype.toString = function() {

}
