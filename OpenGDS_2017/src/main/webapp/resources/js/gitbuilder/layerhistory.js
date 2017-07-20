/**
 * 레이어 편집 이력을 관리하는 객체
 * 
 * @author 소이준
 * @date 2017.05.18
 */

var gb;
if (!gb)
	gb = {};
if (!gb.edit)
	gb.edit = {};
gb.edit.LayerRecord = function(obj) {
	this.created = {};
	this.modified = {};
	this.removed = {};
	this.id = obj.id ? obj.id : false;
}
gb.edit.LayerRecord.prototype.getCreated = function() {
	return this.created;
};
gb.edit.LayerRecord.prototype.getModified = function() {
	return this.modified;
};
gb.edit.LayerRecord.prototype.getRemoved = function() {
	return this.removed;
};
gb.edit.LayerRecord.prototype.isRemoved = function(layer) {
	var isRemoved = false;
	if (this.removed.hasOwnProperty(layer.get("id"))) {
		isRemoved = true;
	}
	return isRemoved;
};
gb.edit.LayerRecord.prototype.create = function(format, mapsheet, layer) {
	if (!this.created.hasOwnProperty(format)) {
		this.created[format] = {};
	}
	if (!this.created[format].hasOwnProperty(mapsheet)) {
		this.created[format][mapsheet] = {};
	}
	this.created[format][mapsheet][layer.get("id")] = layer;
	console.log(this.created);
}
gb.edit.LayerRecord.prototype.remove = function(format, mapsheet, layer) {
	var git = layer.get("git");
	if (!git) {
		console.error("no git property");
		return;
	}
	var info = git.information;
	if (!info) {
		console.error("no information");
		return;
	}
	if (!this.removed.hasOwnProperty(format)) {
		this.removed[format] = {};
	}
	this.removed[format][mapsheet] = {};

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
gb.edit.LayerRecord.prototype.update = function(layer, feature) {
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
gb.edit.LayerRecord.prototype.getStructure = function() {
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
