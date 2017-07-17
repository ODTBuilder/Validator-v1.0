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
	this.id = options.id ? options.id : null;
	this.type = options.type ? options.type : null;

	this.storedAttributes = undefined;
	if (Array.isArray(options.attributes)) {
		var arr = options.attributes;
		for (var i = 0; i < arr.length; i++) {
			this.storedAttributes[arr[i].getOriginFieldName()] = arr[i];
		}
	}

	this.format = options.format ? options.format : null;
	this.epsg = options.epsg ? options.epsg : null;
	this.ngi = {
		"version" : options.version ? options.version : 2,
		"dim" : options.dim ? options.dim : 2
	};
	this.mbound = options.mbound ? options.mbound : null;
	this.lbound = options.lbound ? options.lbound : null;
	this.represent = options.represent ? options.represent : null;
	this.isNew = options.isNew ? options.isNew : true;
}
gb.layer.LayerInfo.prototype.setAttributes = function(attrs) {
	this.storedAttributes = attrs;
};
gb.layer.LayerInfo.prototype.getAttributes = function() {
	return this.storedAttributes;
};

gb.layer.LayerInfo.prototype.setAttribute = function(key, attr) {
	this.storedAttributes[key] = attr;
};
gb.layer.LayerInfo.prototype.getAttribute = function(key) {
	this.storedAttributes[key];
};

gb.layer.LayerInfo.prototype.getName = function() {
	return this.name;
};

gb.layer.LayerInfo.prototype.setName = function(name) {
	this.name = name;
};
gb.layer.LayerInfo.prototype.getName = function() {
	return this.name;
};

gb.layer.LayerInfo.prototype.setId = function(id) {
	this.id = id;
};
gb.layer.LayerInfo.prototype.getId = function() {
	return this.id;
};

gb.layer.LayerInfo.prototype.setType = function(type) {
	this.type = type;
};
gb.layer.LayerInfo.prototype.getType = function() {
	return this.type;
};

gb.layer.LayerInfo.prototype.setFormat = function(format) {
	this.format = format;
};
gb.layer.LayerInfo.prototype.getFormat = function() {
	return this.format;
};

gb.layer.LayerInfo.prototype.setEPSG = function(epsg) {
	this.epsg = epsg;
};
gb.layer.LayerInfo.prototype.getEPSG = function() {
	return this.epsg;
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

gb.layer.LayerInfo.prototype.setRepresent = function(rp) {
	this.represent = rp;
};
gb.layer.LayerInfo.prototype.getRepresent = function() {
	return this.represent;
};

gb.layer.LayerInfo.prototype.setAttributes = function(attrs) {
	this.attributes = attrs;
};
gb.layer.LayerInfo.prototype.getAttributes = function() {
	return this.attributes;
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
		type : this.getType().toString(),
		format : this.getFormat().toString(),
		epsg : this.getEPSG().toString(),
		version : this.getNGIVersion().toString(),
		dim : this.getNGIDim().toString(),
		mbound : this.getMbound().slice(),
		lbound : this.getLbound().slice(),
		represent : this.getRepresent().toString(),
		// 나중에 애트리뷰트 각각 객체도 클론해야함
		attributes : arr
	});
	return obj;
};
gb.layer.LayerInfo.prototype.toString = function() {

}
