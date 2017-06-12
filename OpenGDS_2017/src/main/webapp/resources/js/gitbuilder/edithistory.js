/**
 * 피처 편집 이력을 관리하는 객체
 * 
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
	this.id = obj.id ? obj.id : false;
}
gb.edit.FeatureRecord.prototype.getCreated = function() {
	return this.created;
};
gb.edit.FeatureRecord.prototype.getModified = function() {
	return this.modified;
};
gb.edit.FeatureRecord.prototype.getRemoved = function() {
	return this.removed;
};
gb.edit.FeatureRecord.prototype.isRemoved = function(layer, feature) {
	var isRemoved = false;
	if (this.removed.hasOwnProperty(layer.get("id"))) {
		if (this.removed[layer.get("id")].hasOwnProperty(this.id ? feature.get(this.id) : feature.getId())) {
			isRemoved = true;
		}
	}
	return isRemoved;
};
gb.edit.FeatureRecord.prototype.create = function(layer, feature) {
	if (!this.created[layer.get("id")]) {
		this.created[layer.get("id")] = {};
	}
	this.created[layer.get("id")][feature.getId()] = feature;
	// this.created[layer.get("id")][feature.getId()] = new
	// ol.format.GeoJSON().writeFeature(feature);
	console.log(this.created);
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
		this.removed[layer.get("id")][this.id ? feature.get(this.id) : feature.getId()] = feature;
		if (this.modified.hasOwnProperty(layer.get("id"))) {
			if (this.modified[layer.get("id")].hasOwnProperty(this.id ? feature.get(this.id) : feature.getId())) {
				delete this.modified[layer.get("id")][this.id ? feature.get(this.id) : feature.getId()];
			}
		}
	}
	console.log(this.removed);
}
gb.edit.FeatureRecord.prototype.update = function(layer, feature) {
	if (!this.modified) {
		this.modified = {};
	}
	if (feature.getId().search(".new") !== -1) {
		this.created[layer.get("id")][feature.getId()] = feature;
	} else {
		if (!this.modified[layer.get("id")]) {
			this.modified[layer.get("id")] = {};
		}
		this.modified[layer.get("id")][this.id ? feature.get(this.id) : feature.getId()] = feature;
	}
	// this.modified[layer.get("id")][this.id ? feature.get(this.id) :
	// feature.getId()] = new ol.format.GeoJSON().writeFeature(feature);
	console.log(this.modified);
}
gb.edit.FeatureRecord.prototype.getStructure = function() {
	var obj = {};

	return obj;
}
