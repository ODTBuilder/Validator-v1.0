/**
 * 도엽 편집 이력을 관리하는 객체
 * 
 * @author 소이준
 * @date 2017.07.17
 */

var gb;
if (!gb)
	gb = {};
if (!gb.mapsheet)
	gb.mapsheet = {};
gb.mapsheet.Mapsheet = function(opt) {
	var options = opt ? opt : null;
	this.number = options.number ? options.number : null;
	this.id = options.id ? options.id : null;
	this.format = options.format ? options.format : null;
	this.layers = options.layers ? options.layers : undefined;
}

gb.mapsheet.Mapsheet.prototype.getLayers = function() {
	return this.layers;
};
gb.mapsheet.Mapsheet.prototype.setLayers = function(layers) {
	this.layers = layers;
};

gb.mapsheet.Mapsheet.prototype.getSheetNumber = function() {
	return this.number;
};
gb.mapsheet.Mapsheet.prototype.setSheetNumber = function(number) {
	this.number = number;
};

gb.mapsheet.Mapsheet.prototype.setId = function(id) {
	this.id = id;
};
gb.mapsheet.Mapsheet.prototype.getId = function() {
	return this.id;
};

gb.mapsheet.Mapsheet.prototype.setFormat = function(format) {
	this.format = format;
};
gb.mapsheet.Mapsheet.prototype.getFormat = function() {
	return this.format;
};

gb.mapsheet.Mapsheet.prototype.toString = function() {

}
