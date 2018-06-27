/**
 * 레이어 편집 이력을 관리하는 객체
 * 
 * @class gb.edit.LayerRecord
 * @memberof gb.edit
 * @param {Object}
 *            obj - 생성자 옵션을 담은 객체
 * @param {String}
 *            obj.id - 레이어의 ID
 * @version 0.01
 * @author 소이준
 * @date 2017.05.18
 */
gb.edit.LayerRecord = function(obj) {
	this.created = {};
	this.modified = {};
	this.removed = {};
	this.id = obj.id ? obj.id : false;
}
/**
 * 임시보관 중인 새로운 layer들을 반환한다.
 * 
 * @method gb.edit.LayerRecord#getCreated
 * @function
 * @return {Object} 새로 생성한 Layer
 */
gb.edit.LayerRecord.prototype.getCreated = function() {
	return this.created;
};
/**
 * 임시보관 중인 변경한 layer들을 반환한다.
 * 
 * @method gb.edit.LayerRecord#getModified
 * @function
 * @return {Object} 변경한 Layer
 */
gb.edit.LayerRecord.prototype.getModified = function() {
	return this.modified;
};
/**
 * 임시보관 중인 삭제한 layer들을 반환한다.
 * 
 * @method gb.edit.LayerRecord#getRemoved
 * @function
 * @return {Object} 삭제한 Layer
 */
gb.edit.LayerRecord.prototype.getRemoved = function() {
	return this.removed;
};
/**
 * 임시보관 중인 모든 레이어 이력을 삭제한다.
 * 
 * @method gb.edit.LayerRecord#clearAll
 * @function
 */
gb.edit.LayerRecord.prototype.clearAll = function() {
	this.created = {};
	this.modified = {};
	this.removed = {};
};
/**
 * 임시보관 중인 새로 만든 layer들의 이력을 삭제한다.
 * 
 * @method gb.edit.LayerRecord#clearCreated
 * @function
 */
gb.edit.LayerRecord.prototype.clearCreated = function() {
	this.created = {};
};
/**
 * 임시보관 중인 변경한 layer들의 이력을 삭제한다.
 * 
 * @method gb.edit.LayerRecord#clearModified
 * @function
 */
gb.edit.LayerRecord.prototype.clearModified = function() {
	this.modified = {};
};
/**
 * 임시보관 중인 삭제한 layer들의 이력을 삭제한다.
 * 
 * @method gb.edit.LayerRecord#clearRemoved
 * @function
 */
gb.edit.LayerRecord.prototype.clearRemoved = function() {
	this.removed = {};
};
/**
 * 해당 Layer가 삭제되었는지 임시보관 목록에서 조회한다.
 * 
 * @method gb.edit.LayerRecord#isRemoved
 * @function
 * @param {ol.layer.Base}
 *            layer - 편집이력(삭제)에서 확인할 layer 객체
 * @return {Boolean} 해당 Layer의 편집이력(삭제) 존재 여부
 */
gb.edit.LayerRecord.prototype.isRemoved = function(layer) {
	var isRemoved = false;
	if (this.removed.hasOwnProperty(layer.get("id"))) {
		isRemoved = true;
	}
	return isRemoved;
};
/**
 * 도엽단위의 그룹 레이어 생성 이력을 추가한다.
 * 
 * @method gb.edit.LayerRecord#createByMapsheet
 * @function
 * @param {ol.layer.Base}
 *            mapsheetLayer - 새로 생성할 도엽단위 layer 객체
 */
gb.edit.LayerRecord.prototype.createByMapsheet = function(mapsheetLayer) {
	if (mapsheetLayer instanceof ol.layer.Base) {
		if (!mapsheetLayer.get("git")) {
			console.error("no git property");
			return;
		}
	}
	var git = mapsheetLayer.get("git");
	var info = git.information;
	if (!info) {
		console.error("no information");
		return;
	}
	var format = info.getFormat();
	var mapsheet = info.getSheetNumber();
	var layers;
	if (mapsheetLayer instanceof ol.layer.Group) {
		layers = mapsheetLayer.getLayers();
	} else if (mapsheetLayer instanceof ol.layer.Tile) {
		layers = git.layers;
	}
	if (!this.created.hasOwnProperty(format)) {
		this.created[format] = {};
	}
	if (!this.created[format].hasOwnProperty(mapsheet)) {
		this.created[format][mapsheet] = {};
	}
	for (var i = 0; i < layers.getLength(); i++) {
		this.created[format][mapsheet][layers.item(i).get("id")] = layers.item(i);
	}
	console.log(this.created);
}
/**
 * 도엽내에 레이어를 생성이력을 추가한다.
 * 
 * @method gb.edit.LayerRecord#create
 * @function
 * @param {String}
 *            format - 도엽의 원본 확장자
 * @param {String}
 *            mapsheet - 도엽번호
 * @param {ol.layer.Base}
 *            layer - 새로 생성할 도엽내 layer 객체
 */
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
/**
 * 도엽내에 레이어를 삭제한다.
 * 
 * @method gb.edit.LayerRecord#remove
 * @function
 * @param {String}
 *            format - 도엽의 원본 확장자
 * @param {String}
 *            mapsheet - 도엽번호
 * @param {ol.layer.Base}
 *            layer - 삭제할 도엽내 layer 객체
 */
gb.edit.LayerRecord.prototype.remove = function(format, mapsheet, layer) {
	if (layer instanceof ol.layer.Group) {
		var layers = layer.getLayers();
		for (var i = 0; i < layers.getLength().length; i++) {
			var git = layers.item(i).get("git");
			if (!git) {
				console.error("no git property");
				return;
			}
			var info = git.information;
			if (!info) {
				console.error("no information");
				return;
			}

			if (info.getIsNew() === true) {
				delete this.created[format][mapsheet][layers.item(i).get("id")];
				var mkeys = Object.keys(this.created[format][mapsheet]);
				if (mkeys.length === 0) {
					delete this.created[format][mapsheet];
				}
				var fkeys = Object.keys(this.created[format]);
				if (fkeys.length === 0) {
					delete this.created[format];
				}
			} else {
				if (!this.removed.hasOwnProperty(format)) {
					this.removed[format] = {};
				}
				if (!this.removed[format].hasOwnProperty(mapsheet)) {
					this.removed[format][mapsheet] = {};
				}
				this.removed[format][mapsheet][layers.item(i).get("id")] = layers.item(i);
				if (this.modified.hasOwnProperty(format)) {
					if (this.modified[format].hasOwnProperty(mapsheet)) {
						if (this.modified[format][mapsheet].hasOwnProperty(layers.item(i).get("id"))) {
							delete this.modified[format][mapsheet][layers.item(i).get("id")];
							var mkeys = Object.keys(this.modified[format][mapsheet]);
							if (mkeys.length === 0) {
								delete this.modified[format][mapsheet];
							}
							var fkeys = Object.keys(this.modified[format]);
							if (fkeys.length === 0) {
								delete this.modified[format];
							}
						}
					}
				}
			}
		}
	} else {
		var git = layer.get("git");
		if (git.hasOwnProperty("fake")) {
			if (git["fake"] === "parent") {
				var layers = git.layers;
				for (var i = 0; i < layers.getLength().length; i++) {
					var git = layers.item(i).get("git");
					if (!git) {
						console.error("no git property");
						return;
					}
					var info = git.information;
					if (!info) {
						console.error("no information");
						return;
					}

					if (info.getIsNew() === true) {
						delete this.created[format][mapsheet][layers.item(i).get("id")];
						var mkeys = Object.keys(this.created[format][mapsheet]);
						if (mkeys.length === 0) {
							delete this.created[format][mapsheet];
						}
						var fkeys = Object.keys(this.created[format]);
						if (fkeys.length === 0) {
							delete this.created[format];
						}
					} else {
						if (!this.removed.hasOwnProperty(format)) {
							this.removed[format] = {};
						}
						if (!this.removed[format].hasOwnProperty(mapsheet)) {
							this.removed[format][mapsheet] = {};
						}
						this.removed[format][mapsheet][layers.item(i).get("id")] = layers.item(i);
						if (this.modified.hasOwnProperty(format)) {
							if (this.modified[format].hasOwnProperty(mapsheet)) {
								if (this.modified[format][mapsheet].hasOwnProperty(layers.item(i).get("id"))) {
									delete this.modified[format][mapsheet][layers.item(i).get("id")];
									var mkeys = Object.keys(this.modified[format][mapsheet]);
									if (mkeys.length === 0) {
										delete this.modified[format][mapsheet];
									}
									var fkeys = Object.keys(this.modified[format]);
									if (fkeys.length === 0) {
										delete this.modified[format];
									}
								}
							}
						}
					}
				}
			} else if (git["fake"] === "child") {
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

				if (info.getIsNew() === true) {
					delete this.created[format][mapsheet][layer.get("id")];
					var mkeys = Object.keys(this.created[format][mapsheet]);
					if (mkeys.length === 0) {
						delete this.created[format][mapsheet];
					}
					var fkeys = Object.keys(this.created[format]);
					if (fkeys.length === 0) {
						delete this.created[format];
					}
				} else {
					if (!this.removed.hasOwnProperty(format)) {
						this.removed[format] = {};
					}
					if (!this.removed[format].hasOwnProperty(mapsheet)) {
						this.removed[format][mapsheet] = {};
					}
					this.removed[format][mapsheet][layer.get("id")] = layer;
					if (this.modified.hasOwnProperty(format)) {
						if (this.modified[format].hasOwnProperty(mapsheet)) {
							if (this.modified[format][mapsheet].hasOwnProperty(layer.get("id"))) {
								delete this.modified[format][mapsheet][layer.get("id")];
								var mkeys = Object.keys(this.modified[format][mapsheet]);
								if (mkeys.length === 0) {
									delete this.modified[format][mapsheet];
								}
								var fkeys = Object.keys(this.modified[format]);
								if (fkeys.length === 0) {
									delete this.modified[format];
								}
							}
						}
					}
				}
			}
		} else {
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

			if (info.getIsNew() === true) {
				delete this.created[format][mapsheet][layer.get("id")];
				var mkeys = Object.keys(this.created[format][mapsheet]);
				if (mkeys.length === 0) {
					delete this.created[format][mapsheet];
				}
				var fkeys = Object.keys(this.created[format]);
				if (fkeys.length === 0) {
					delete this.created[format];
				}
			} else {
				if (!this.removed.hasOwnProperty(format)) {
					this.removed[format] = {};
				}
				if (!this.removed[format].hasOwnProperty(mapsheet)) {
					this.removed[format][mapsheet] = {};
				}
				this.removed[format][mapsheet][layer.get("id")] = layer;
				if (this.modified.hasOwnProperty(format)) {
					if (this.modified[format].hasOwnProperty(mapsheet)) {
						if (this.modified[format][mapsheet].hasOwnProperty(layer.get("id"))) {
							delete this.modified[format][mapsheet][layer.get("id")];
							var mkeys = Object.keys(this.modified[format][mapsheet]);
							if (mkeys.length === 0) {
								delete this.modified[format][mapsheet];
							}
							var fkeys = Object.keys(this.modified[format]);
							if (fkeys.length === 0) {
								delete this.modified[format];
							}
						}
					}
				}
			}
		}
	}

	console.log(this.removed);
	console.log(this.created);
	console.log(this.modified);
}
/**
 * 도엽내의 해당 Layer를 변경한다.
 * 
 * @method gb.edit.LayerRecord#update
 * @function
 * @param {String}
 *            format - 도엽의 원본 확장자
 * @param {String}
 *            mapsheet - 도엽번호
 * @param {ol.layer.Base}
 *            layer - 새로 생성할 도엽내 layer 객체
 * @param {String}
 *            oldLayerId - 변경될 예전 Layer ID
 */
gb.edit.LayerRecord.prototype.update = function(format, mapsheet, layer, oldLayerId) {
	if (!this.modified) {
		this.modified = {};
	}
	var info = layer.get("git").information;
	if (info.getIsNew() === true) {
		delete this.created[format][mapsheet][oldLayerId];
		this.created[format][mapsheet][layer.get("id")] = layer;
	} else {
		if (this.modified.hasOwnProperty(format)) {
			if (this.modified[format].hasOwnProperty(mapsheet)) {
				delete this.modified[format][mapsheet][oldLayerId];
				this.modified[format][mapsheet][layer.get("id")] = layer;
			}
		}
	}
	console.log(this.removed);
	console.log(this.created);
	console.log(this.modified);
}
/**
 * 도엽 단위의 Layer 정보 변경이력을 JSON형태로 반환한다.
 * 
 * @method gb.edit.LayerRecord#getStructure
 * @function
 * @return {Object} Layer 정보 편집이력
 */
gb.edit.LayerRecord.prototype.getStructure = function() {
	var obj = {};
	var created = this.getCreated();
	var cFormat = Object.keys(created);
	for (var i = 0; i < cFormat.length; i++) {
		if (!obj.hasOwnProperty(cFormat[i])) {
			obj[cFormat[i]] = {};
		}
		var cSheetNum = Object.keys(created[cFormat[i]]);
		for (var j = 0; j < cSheetNum.length; j++) {
			if (!obj[cFormat[i]].hasOwnProperty(cSheetNum[j])) {
				obj[cFormat[i]][cSheetNum[j]] = {};
				obj[cFormat[i]][cSheetNum[j]]["create"] = [];
			}
			var cLayers = Object.keys(created[cFormat[i]][cSheetNum[j]]);
			for (var k = 0; k < cLayers.length; k++) {
				var cLayer = created[cFormat[i]][cSheetNum[j]][cLayers[k]];
				var cInfo = cLayer.get("git").information;
				var cObj;
				if (cInfo.getFormat() === "ngi") {
					cObj = {
						"layerName" : cInfo.getName() + "_" + (cInfo.getGeometry().toUpperCase()),
						"layerType" : cInfo.getGeometry(),
						"version" : cInfo.getNGIVersion(),
						"dim" : cInfo.getNGIDim(),
						"bound" : cInfo.getMbound(),
						"represent" : cInfo.getNGIRep()
					};
					var attrs = [];
					var cAttrs = cInfo.getAttributes();
					if (!!cAttrs) {
						for (var l = 0; l < cAttrs.length; l++) {
							attrs.push(cAttrs[l].getStructure());
						}
						cObj["attr"] = attrs;
					}
				} else if (cInfo.getFormat() === "dxf") {
					cObj = {
						"layerName" : cInfo.getName() + "_" + (cInfo.getGeometry().toUpperCase()),
						"layerType" : cInfo.getGeometry()
					};
				}
				obj[cFormat[i]][cSheetNum[j]]["create"].push(cObj);
			}
		}
	}

	var modified = this.getModified();
	var mFormat = Object.keys(modified);
	for (var i = 0; i < mFormat.length; i++) {
		if (!obj.hasOwnProperty(mFormat[i])) {
			obj[mFormat[i]] = {};
		}
		var mSheetNum = Object.keys(modified[mFormat[i]]);
		for (var j = 0; j < mSheetNum.length; j++) {
			if (!obj[mFormat[i]].hasOwnProperty(mSheetNum[j])) {
				obj[mFormat[i]][mSheetNum[j]] = {};
				obj[mFormat[i]][mSheetNum[j]]["modify"] = [];
			}
			var mLayers = Object.keys(modified[mFormat[i]][mSheetNum[j]]);
			for (var k = 0; k < mLayers.length; k++) {
				var mLayer = modified[mFormat[i]][mSheetNum[j]][mLayers[k]];
				var mInfo = mLayer.get("git").information;

				var mObj = {
					"nativeName" : mInfo.getId(),
					"originLayerName" : mInfo.getOldName() + "_" + (mInfo.getGeometry().toUpperCase()),
					"currentLayerName" : mInfo.getName() + "_" + (mInfo.getGeometry().toUpperCase()),
					// "layerType" : mInfo.getGeometry(),
					// "version" : mInfo.getNGIVersion(),
					// "dim" : mInfo.getNGIDim(),
					"bound" : mInfo.getMbound(),
					"represent" : mInfo.getNGIRep()
				};
				var attrs = [];
				var mAttrs = mInfo.getAttributes();
				if (!!mAttrs) {
					for (var l = 0; l < mAttrs.length; l++) {
						attrs.push(mAttrs[l].getStructure());
					}
					mObj["updateAttr"] = attrs;
				}
				obj[mFormat[i]][mSheetNum[j]]["modify"].push(mObj);
			}
		}
	}

	var removed = this.getRemoved();
	var rFormat = Object.keys(removed);
	for (var i = 0; i < rFormat.length; i++) {
		if (!obj.hasOwnProperty(rFormat[i])) {
			obj[rFormat[i]] = {};
		}
		var rSheetNum = Object.keys(removed[rFormat[i]]);
		for (var j = 0; j < rSheetNum.length; j++) {
			if (!obj[rFormat[i]].hasOwnProperty(rSheetNum[j])) {
				obj[rFormat[i]][rSheetNum[j]] = {};
				obj[rFormat[i]][rSheetNum[j]]["remove"] = {};
			}
			var scope = "part";
			var layer = [];
			var rLayers = Object.keys(removed[rFormat[i]][rSheetNum[j]]);
			for (var k = 0; k < rLayers.length; k++) {
				var rLayer = removed[rFormat[i]][rSheetNum[j]][rLayers[k]];
				var rInfo = rLayer.get("git").information;
				var name = info.rInfo.getOldName() + "_" + (rInfo.getGeometry().toUpperCase());
				layer.push(name);
			}
			obj[rFormat[i]][rSheetNum[j]]["remove"]["scope"] = scope;
			obj[rFormat[i]][rSheetNum[j]]["remove"]["layer"] = layer;
		}
	}
	return obj;
}
/**
 * 선택한 도엽 단위의 Layer 정보 변경이력을 JSON형태로 반환한다.
 * 
 * @method gb.edit.LayerRecord#getPartStructure
 * @function
 * @param {String}
 *            bringLayer - Layer ID 배열
 * @return {Object} Layer 정보 편집이력
 */
gb.edit.LayerRecord.prototype.getPartStructure = function(bringLayer) {
	if (!Array.isArray(bringLayer)) {
		console.error("type error");
		return;
	}
	var obj = {};
	var created = this.getCreated();
	var cFormat = Object.keys(created);
	for (var i = 0; i < cFormat.length; i++) {
		if (bringLayer.indexOf(cFormat[i]) === -1) {
			continue;
		}
		if (!obj.hasOwnProperty(cFormat[i])) {
			obj[cFormat[i]] = {};
		}
		var cSheetNum = Object.keys(created[cFormat[i]]);
		for (var j = 0; j < cSheetNum.length; j++) {
			if (!obj[cFormat[i]].hasOwnProperty(cSheetNum[j])) {
				obj[cFormat[i]][cSheetNum[j]] = {};
				obj[cFormat[i]][cSheetNum[j]]["create"] = [];
			}
			var cLayers = Object.keys(created[cFormat[i]][cSheetNum[j]]);
			for (var k = 0; k < cLayers.length; k++) {
				var cLayer = created[cFormat[i]][cSheetNum[j]][cLayers[k]];
				var cInfo = cLayer.get("git").information;
				var cObj;
				if (cInfo.getFormat() === "ngi") {
					cObj = {
						"layerName" : cInfo.getName() + "_" + (cInfo.getGeometry().toUpperCase()),
						"layerType" : cInfo.getGeometry(),
						"version" : cInfo.getNGIVersion(),
						"dim" : cInfo.getNGIDim(),
						"bound" : cInfo.getMbound(),
						"represent" : cInfo.getNGIRep()
					};
					var attrs = [];
					var cAttrs = cInfo.getAttributes();
					if (!!cAttrs) {
						for (var l = 0; l < cAttrs.length; l++) {
							attrs.push(cAttrs[l].getStructure());
						}
						cObj["attr"] = attrs;
					}
				} else if (cInfo.getFormat() === "dxf") {
					cObj = {
						"layerName" : cInfo.getName() + "_" + (cInfo.getGeometry().toUpperCase()),
						"layerType" : cInfo.getGeometry()
					};
				}
				obj[cFormat[i]][cSheetNum[j]]["create"].push(cObj);
			}
		}
	}

	var modified = this.getModified();
	var mFormat = Object.keys(modified);
	for (var i = 0; i < mFormat.length; i++) {
		if (bringLayer.indexOf(mFormat[i]) === -1) {
			continue;
		}
		if (!obj.hasOwnProperty(mFormat[i])) {
			obj[mFormat[i]] = {};
		}
		var mSheetNum = Object.keys(modified[mFormat[i]]);
		for (var j = 0; j < mSheetNum.length; j++) {
			if (!obj[mFormat[i]].hasOwnProperty(mSheetNum[j])) {
				obj[mFormat[i]][mSheetNum[j]] = {};
				obj[mFormat[i]][mSheetNum[j]]["modify"] = [];
			}
			var mLayers = Object.keys(modified[mFormat[i]][mSheetNum[j]]);
			for (var k = 0; k < mLayers.length; k++) {
				var mLayer = modified[mFormat[i]][mSheetNum[j]][mLayers[k]];
				var mInfo = mLayer.get("git").information;

				var mObj = {
					"nativeName" : mInfo.getId(),
					"originLayerName" : mInfo.getOldName() + "_" + (mInfo.getGeometry().toUpperCase()),
					"currentLayerName" : mInfo.getName() + "_" + (mInfo.getGeometry().toUpperCase()),
					// "layerType" : mInfo.getGeometry(),
					// "version" : mInfo.getNGIVersion(),
					// "dim" : mInfo.getNGIDim(),
					"bound" : mInfo.getMbound(),
					"represent" : mInfo.getNGIRep()
				};
				var attrs = [];
				var mAttrs = mInfo.getAttributes();
				if (!!mAttrs) {
					for (var l = 0; l < mAttrs.length; l++) {
						attrs.push(mAttrs[l].getStructure());
					}
					mObj["updateAttr"] = attrs;
				}
				obj[mFormat[i]][mSheetNum[j]]["modify"].push(mObj);
			}
		}
	}

	var removed = this.getRemoved();
	var rFormat = Object.keys(removed);
	for (var i = 0; i < rFormat.length; i++) {
		if (bringLayer.indexOf(rFormat[i]) === -1) {
			continue;
		}
		if (!obj.hasOwnProperty(rFormat[i])) {
			obj[rFormat[i]] = {};
		}
		var rSheetNum = Object.keys(removed[rFormat[i]]);
		for (var j = 0; j < rSheetNum.length; j++) {
			if (!obj[rFormat[i]].hasOwnProperty(rSheetNum[j])) {
				obj[rFormat[i]][rSheetNum[j]] = {};
				obj[rFormat[i]][rSheetNum[j]]["remove"] = {};
			}
			var scope = "part";
			var layer = [];
			var rLayers = Object.keys(removed[rFormat[i]][rSheetNum[j]]);
			for (var k = 0; k < rLayers.length; k++) {
				var rLayer = removed[rFormat[i]][rSheetNum[j]][rLayers[k]];
				var rInfo = rLayer.get("git").information;
				var name = info.rInfo.getOldName() + "_" + (rInfo.getGeometry().toUpperCase());
				layer.push(name);
			}
			obj[rFormat[i]][rSheetNum[j]]["remove"]["scope"] = scope;
			obj[rFormat[i]][rSheetNum[j]]["remove"]["layer"] = layer;
		}
	}
	return obj;
}