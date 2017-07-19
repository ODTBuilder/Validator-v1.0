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
gb.edit.FeatureRecord.prototype.clearAll = function() {
	this.created = {};
	this.modified = {};
	this.removed = {};
};
gb.edit.FeatureRecord.prototype.clearCreated = function() {
	this.created = {};
};
gb.edit.FeatureRecord.prototype.clearModified = function() {
	this.modified = {};
};
gb.edit.FeatureRecord.prototype.clearRemoved = function() {
	this.removed = {};
};
gb.edit.FeatureRecord.prototype.isRemoved = function(layer, feature) {
	var isRemoved = false;
	var lid;
	if (layer instanceof ol.layer.Base) {
		lid = layer.get("id");
	} else if (typeof layer === "string") {
		lid = layer;
	}
	if (this.removed.hasOwnProperty(lid)) {
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
	console.log(this.modified);
}
gb.edit.FeatureRecord.prototype.getStructure = function() {
	var format = new ol.format.GeoJSON();
	var obj = {};
	var cLayers = Object.keys(this.created);
	for (var i = 0; i < cLayers.length; i++) {
		if (Object.keys(this.created[cLayers[i]]).length < 1) {
			continue;
		}
		obj[cLayers[i]] = {};
	}

	for (var j = 0; j < cLayers.length; j++) {
		var names = Object.keys(this.created[cLayers[j]]);
		for (var k = 0; k < names.length; k++) {
			if (!obj[cLayers[j]].hasOwnProperty("created")) {
				obj[cLayers[j]]["created"] = {};
			}
			if (!obj[cLayers[j]]["created"].hasOwnProperty("features")) {
				obj[cLayers[j]]["created"]["features"] = [];
			}
			obj[cLayers[j]]["created"]["features"].push(format.writeFeature(this.created[cLayers[j]][names[k]]));
		}
	}

	var mLayers = Object.keys(this.modified);
	for (var i = 0; i < mLayers.length; i++) {
		if (Object.keys(this.modified[mLayers[i]]).length < 1 || obj.hasOwnProperty(mLayers[i])) {
			continue;
		}
		obj[mLayers[i]] = {};
	}

	for (var j = 0; j < mLayers.length; j++) {
		var names = Object.keys(this.modified[mLayers[j]]);
		for (var k = 0; k < names.length; k++) {
			if (!obj[mLayers[j]].hasOwnProperty("modified")) {
				obj[mLayers[j]]["modified"] = {};
			}
			if (!obj[mLayers[j]]["modified"].hasOwnProperty("features")) {
				obj[mLayers[j]]["modified"]["features"] = [];
			}
			var clone = this.modified[mLayers[j]][names[k]];
			if (this.id) {
				clone.setId(clone.get(this.id));
			}
			obj[mLayers[j]]["modified"]["features"].push(format.writeFeature(clone));
		}
	}

	var rLayers = Object.keys(this.removed);
	for (var i = 0; i < rLayers.length; i++) {
		if (Object.keys(this.removed[rLayers[i]]).length < 1 || obj.hasOwnProperty(rLayers[i])) {
			continue;
		}
		obj[rLayers[i]] = {};
	}

	for (var j = 0; j < rLayers.length; j++) {
		var names = Object.keys(this.removed[rLayers[j]]);
		for (var k = 0; k < names.length; k++) {
			if (!obj[rLayers[j]].hasOwnProperty("removed")) {
				obj[rLayers[j]]["removed"] = [];
			}
			obj[rLayers[j]]["removed"].push(names[k]);
		}
	}

	return obj;
}
