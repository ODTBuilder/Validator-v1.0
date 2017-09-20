/**
 * 레이어 및 피처 편집 이력을 전송하는 객체
 * 
 * @author 소이준
 * @date 2017.06.13
 */

var gb;
if (!gb)
	gb = {};
if (!gb.edit)
	gb.edit = {};
gb.edit.RecordTransfer = function(obj) {
	this.feature = obj.feature;
	this.layer = obj.layer;
	this.url = obj.url;
}
gb.edit.RecordTransfer.prototype.getFeatureRecord = function() {
	return this.feature;
};
gb.edit.RecordTransfer.prototype.getLayerRecord = function() {
	return this.layer;
};
gb.edit.RecordTransfer.prototype.getPartStructure = function(savingLayer) {
	var obj = {};
	// if (this.layer instanceof gb.edit.LayerRecord) {
	// obj["layer"] = this.layer.getPartStructure(savingLayer);
	// }

	if (this.feature instanceof gb.edit.FeatureRecord) {
		obj["feature"] = this.feature.getPartStructure(savingLayer);
	}
	console.log(obj);
	return obj;
};

gb.edit.RecordTransfer.prototype.getStructure = function() {
	var obj = {};
	if (this.layer instanceof gb.edit.LayerRecord) {
		obj["layer"] = this.layer.getStructure();
	}

	if (this.feature instanceof gb.edit.FeatureRecord) {
		obj["feature"] = this.feature.getStructure();
	}
	console.log(obj);
	return obj;
};
gb.edit.RecordTransfer.prototype.refresh = function(layer, editingTool) {
	if (layer instanceof ol.layer.Group) {
		var layers = layer.getLayers();
		for (var i = 0; i < layers.getLength(); i++) {
			this.refresh(layers.item(i), editingTool);
		}
	} else if (layer instanceof ol.layer.Tile) {
		var params = layer.getSource().getParams();
		params["time"] = Date.now();
		layer.getSource().updateParams(params);
		layer.getSource().refresh();
		editingTool.removeFeatureFromUnmanaged(layer);
	}
};
gb.edit.RecordTransfer.prototype.sendStructure = function(ollayers, editingTool) {
	var that = this;
	var featureObj = this.getFeatureRecord();
	console.log(this.getStructure());
	$.ajax({
		url : this.url,
		type : "POST",
		data : JSON.stringify(this.getStructure()),
		contentType : "application/json; charset=UTF-8",
		dataType : 'json',
		beforeSend : function() {
			$("body").css("cursor", "wait");
		},
		complete : function() {
			$("body").css("cursor", "default");
		},
		success : function(data) {
			console.log(data);
			featureObj.clearAll();
			for (var i = 0; i < ollayers.getLength(); i++) {
				that.refresh(ollayers.item(i), editingTool);
			}
		}
	});
};

gb.edit.RecordTransfer.prototype.sendPartStructure = function(layers, ollayers, editingTool) {
	console.log(this.getPartStructure(layers));
	var featureObj = this.getFeatureRecord();

	$.ajax({
		url : this.url,
		type : "POST",
		data : JSON.stringify(this.getPartStructure(layers)),
		contentType : "application/json; charset=UTF-8",
		dataType : 'json',
		beforeSend : function() {
			$("body").css("cursor", "wait");
		},
		complete : function() {
			$("body").css("cursor", "default");
		},
		success : function(data) {
			console.log(data);
			for (var i = 0; i < layers.length; i++) {
				featureObj.removeByLayer(layers[i]);
			}
			for (var i = 0; i < ollayers.getLength(); i++) {
				if (ollayers.item(i) instanceof ol.layer.Tile) {
					var params = ollayers.item(i).getSource().getParams();
					params["time"] = Date.now();
					ollayers.item(i).getSource().updateParams(params);
					ollayers.item(i).getSource().refresh();
					editingTool.removeFeatureFromUnmanaged(ollayers.item(i));
				}
			}
		}
	});
};