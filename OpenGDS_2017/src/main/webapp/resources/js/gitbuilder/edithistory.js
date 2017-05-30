/**
 * 피처 편집 이력을 관리하는 객체 @ author 소이준 @ date 2017.05.18
 */

var gb;
if (!gb)
	gb = {};
if (!gb.edit)
	gb.edit = {};
gb.edit.FeatureRecord = function(obj) {
	this.features = {};
	this.created = {};
	this.modified = {};
	this.removed = {};
}
gb.edit.FeatureRecord.prototype.create = function(sheetId, layer, feature) {
	if (!this.created[sheetId]) {
		this.created[sheetId] = {};
	}
	if (!this.created[sheetId][layer]) {
		this.created[sheetId][layer] = {};
	}
	this.created[sheetId][layer][feature.getId()] = feature;
}
gb.edit.FeatureRecord.prototype.remove = function(sheetId, layer, feature) {
	if (!this.removed[sheetId]) {
		this.removed[sheetId] = {};
	}
	if (!this.removed[sheetId][layer]) {
		this.removed[sheetId][layer] = {};
	}
	if (feature.getId().search(".new") !== -1) {
		for (var i = 0; i < this.created[sheetId][layer].length; i++) {
			if (this.created[sheetId][layer][i].feature.getId() === feature.getId()) {
				this.created[sheetId][layer].splice(i, 1);
				break;
			}
		}
	} else {
		this.removed[sheetId][layer][feature.getId()] = feature;
	}
}
gb.edit.FeatureRecord.prototype.add = function(sheetId, layer, feature) {
	if (!this.features[sheetId]) {
		this.features[sheetId] = {};
	}
	if (!this.features[sheetId][layer]) {
		this.features[sheetId][layer] = {};
	}
	this.features[sheetId][layer][feature.getId()] = {
		"feature" : feature,
		"revision" : feature.getRevision()
	}
}
gb.edit.FeatureRecord.prototype.updateOne = function(sheetId, layer, feature) {
	if (!this.modified) {
		this.modified = {};
	}
	if (!this.modified[sheetId]) {
		this.modified[sheetId] = {};
	}
	if (!this.modified[sheetId[layer]]) {
		this.modified[sheetId][layer] = {};
	}
	this.modified[sheetId][layer][feature.getId()] = feature;

}
gb.edit.FeatureRecord.prototype.updateAll = function() {
	if (!this.modified) {
		this.modified = {};
	}
	var keys = Object.keys(this.features);
	for (var i = 0; i < keys.length; i++) {
		var keys2 = Object.keys(this.features[keys[i]]);
		for (var j = 0; j < key2.length; j++) {
			var keys3 = Object.keys(this.features[keys[i]][keys[j]]);
			for (var k = 0; k < keys3.length; k++) {
				if (this.features[keys[i]][keys2[j]][keys3[k]].revision < this.features[keys[i]][keys2[j]][keys[k]].feature.getRevision()) {
					if (!this.modified[keys[i]]) {
						this.modified[keys[i]] = {};
					}
					if (!this.modified[keys[i]][keys[j]]) {
						this.modified[keys[i]][keys[j]] = {};
					}
					this.modified[keys[i]][keys2[j]][keys3[k]] = this.features[keys[i]][keys2[j]][keys3[k]].feature;
				}
			}

		}
	}
	return this.modified;
}
gb.edit.FeatureRecord.prototype.getModified = function() {
	return this.modified;
}
gb.edit.FeatureRecord.prototype.getStructure = function() {
	var obj = {};

	var ckeys = Object.keys(this.created);
	for (var i = 0; i < ckeys.length; i++) {
		if (obj.hasOwnProperty(ckeys[i])) {
			obj[ckeys[i]] = {};
		}
		var lkeys = Object.keys(this.created[ckeys[i]]);
		for (var j = 0; j < lkeys.length; j++) {
			if (obj[ckeys[i]].hasOwnProperty(lkeys[j])) {
				obj[ckeys[i]][lkeys[j]] = {};
			}
			var fkeys = Object.keys(this.created[ckeys[i]][lkeys[j]]);
			for (var k = 0; k < fkeys.length; k++) {
				obj[ckeys[i]][lkeys[j]]["created"][fkeys[k]] = {
					"geom" : this.created[ckeys[i]][lkeys[j]][fkeys[k]].getGeometry().getCoordinates(),
					"attr" : this.created[ckeys[i]][lkeys[j]][fkeys[k]].getProperties()
				};
			}
		}
	}

	var ckeys2 = Object.keys(this.modified);
	for (var i = 0; i < ckeys2.length; i++) {
		if (obj.hasOwnProperty(ckeys2[i])) {
			obj[ckeys2[i]] = {};
		}
		var lkeys2 = Object.keys(this.modified[ckeys2[i]]);
		for (var j = 0; j < lkeys2.length; j++) {
			if (obj[ckeys2[i]].hasOwnProperty(lkeys2[j])) {
				obj[ckeys2[i]][lkeys2[j]] = {};
			}
			var fkeys2 = Object.keys(this.modified[ckeys2[i]][lkeys2[j]]);
			for (var k = 0; k < fkeys2.length; k++) {
				obj[ckeys2[i]][lkeys2[j]]["modified"][fkeys2[k]] = {
					"geom" : this.modified[ckeys2[i]][lkeys2[j]][fkeys2[k]].getGeometry().getCoordinates(),
					"attr" : this.modified[ckeys2[i]][lkeys2[j]][fkeys2[k]].getProperties()
				};
			}
		}
	}

	var ckeys3 = Object.keys(this.removed);
	for (var i = 0; i < ckeys3.length; i++) {
		if (obj.hasOwnProperty(ckeys3[i])) {
			obj[ckeys3[i]] = {};
		}
		var lkeys3 = Object.keys(this.removed[ckeys3[i]]);
		for (var j = 0; j < lkeys3.length; j++) {
			if (obj[ckeys3[i]].hasOwnProperty(lkeys3[j])) {
				obj[ckeys3[i]][lkeys3[j]] = {};
			}
			var fkeys3 = Object.keys(this.removed[ckeys3[i]][lkeys3[j]]);
			for (var k = 0; k < fkeys3.length; k++) {
				obj[ckeys3[i]][lkeys3[j]]["removed"][fkeys3[k]] = {
					"geom" : this.removed[ckeys3[i]][lkeys3[j]][fkeys3[k]].getGeometry().getCoordinates(),
					"attr" : this.removed[ckeys3[i]][lkeys3[j]][fkeys3[k]].getProperties()
				};
			}
		}
	}

	return obj;
}
