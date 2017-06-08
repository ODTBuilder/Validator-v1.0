/**
 * 피처 편집 이력을 관리하는 객체 
 * @author 소이준 
 * @date 2017.05.18
 */

var gb;
if (!gb)
	gb = {};
if (!gb.edit)
	gb.edit = {};
gb.edit.FeatureRecord = function(obj) {
	this.created = {};
	this.modified = {};
	this.removed = {};
}
gb.edit.FeatureRecord.prototype.getCreated = function(){
	return this.created;
};
gb.edit.FeatureRecord.prototype.getModified = function(){
	return this.modified;
};
gb.edit.FeatureRecord.prototype.getRemoved = function(){
	return this.removed;
};
gb.edit.FeatureRecord.prototype.create = function(layer, feature) {
	if (!this.created[layer.get("id")]) {
		this.created[layer.get("id")] = {};
	}
	this.created[layer.get("id")][feature.getId()] = feature;
}
gb.edit.FeatureRecord.prototype.remove = function(layer, feature) {
	if (!this.removed[layer.get("id")]) {
		this.removed[layer.get("id")] = {};
	}
	if (feature.getId().search(".new") !== -1) {
		var keys = Object.keys(this.created[layer.get("id")]);
		for (var i = 0; i < keys.length; i++) {
			if (this.created[layer.get("id")][keys[i]].getId() === feature.getId()) {
				delete this.created[layer.get("id")][keys[i]];
				break;
			}
		}
	} else {
		this.removed[layer.get("id")][feature.getId()] = feature;
	}
	console.log(this.removed);
}
gb.edit.FeatureRecord.prototype.update = function(layer, feature) {
	if (!this.modified) {
		this.modified = {};
	}
	if (!this.modified[layer.get("id")]) {
		this.modified[layer.get("id")] = {};
	}
	this.modified[layer.get("id")][feature.getId()] = new ol.format.GeoJSON().writeFeature(feature);
	console.log(this.modified);
}
gb.edit.FeatureRecord.prototype.getStructure = function() {
	var obj = {};

	return obj;
}
