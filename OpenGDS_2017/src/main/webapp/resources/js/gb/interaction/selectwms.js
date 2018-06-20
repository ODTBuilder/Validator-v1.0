/**
 * WMS레이어의 선택 위치의 피처를 요청한다.
 * 
 * @param {ol.interaction.Select}
 *            select 연동할 실렉트 인터렉션
 * @param {ol.layer.Vector}
 *            destination 선택한 피처가 편입될 벡터레이어
 * @param {function ||
 *            ol.layer.Tile} layer 선택한 레이어 또는 레이어를 반환할 함수
 */
var gb;
if (!gb)
	gb = {};
if (!gb.interaction)
	gb.interaction = {};

gb.interaction.SelectWMS = function(opt_options) {
	var options = opt_options ? opt_options : {};
	this.map_ = null;
	this.coordinate_ = null;
	this.extent_ = null;
	this.conLayer = options.layer ? options.layer : undefined;
	this.layer = undefined;
	this.source_ = new ol.source.Vector();
	this.features_ = new ol.Collection();
	this.select_ = options.select;
	this.destination_ = options.destination ? options.destination : new ol.layer.Vector({
		source : this.source_
	});
	this.record = options.record;
	ol.interaction.Interaction.call(this, {
		handleEvent : gb.interaction.SelectWMS.prototype.handleEvent
	});
	this.getFeature_ = options.getFeature ? options.getFeature : null;
	this.getFeatureInfo_ = options.getFeatureInfo ? options.getFeatureInfo : null;
}
ol.inherits(gb.interaction.SelectWMS, ol.interaction.Interaction);

gb.interaction.SelectWMS.prototype.handleEvent = function(evt) {
	var that = this;
	this.map_ = evt.map;
	if (typeof this.conLayer === "function") {
		this.layer = this.conLayer();
	} else if (this.conLayer instanceof ol.layer.Base) {
		this.layer = this.conLayer;
	}
	if (evt.type === "singleclick") {
		if (this.layer instanceof ol.layer.Vector) {
			var lys = [ this.layer ];
			var layerFilter;
			if (lys) {
				if (typeof lys === 'function') {
					layerFilter = lys;
				} else {
					var layers = lys;
					layerFilter = function(layer) {
						return ol.array.includes(lys, layer);
					};
				}
			} else {
				layerFilter = ol.functions.TRUE;
			}

			/**
			 * @private
			 * @type {function(ol.layer.Layer): boolean}
			 */
			this.select_.layerFilter_ = layerFilter;

			this.select_.handleEvent(evt);
		} else if (this.layer instanceof ol.layer.Layer) {
			var lys = [ this.destination_ ];
			var layerFilter;
			if (lys) {
				if (typeof lys === 'function') {
					layerFilter = lys;
				} else {
					var layers = lys;
					layerFilter = function(layer) {
						return ol.array.includes(lys, layer);
					};
				}
			} else {
				layerFilter = ol.functions.TRUE;
			}
			/**
			 * @private
			 * @type {function(ol.layer.Layer): boolean}
			 */
			this.select_.layerFilter_ = layerFilter;

			this.setCoordinate(evt);
		}
	}
	return true;
};

gb.interaction.SelectWMS.prototype.getFeatures = function() {
	return this.features_;
};

gb.interaction.SelectWMS.prototype.setExtent = function(extent) {
	if (!this.getLayer().getVisible() || this.getLayer() instanceof ol.layer.Vector) {
		return;
	}
	this.extent_ = extent;
	var that = this;
	var params;
	if (that.layer instanceof ol.layer.Tile) {
		params = {
			"service" : "WFS",
			"version" : "1.0.0",
			"request" : "GetFeature",
			"typeName" : that.layer.getSource().getParams().LAYERS,
			"outputformat" : "text/javascript",
			"bbox" : extent.toString(),
			"format_options" : "callback:getJson"
		};
	} else if (that.layer instanceof ol.layer.Base && that.layer.get("git").hasOwnProperty("fake")) {
		params = {
			"service" : "WFS",
			"version" : "1.0.0",
			"request" : "GetFeature",
			"typeName" : that.layer.get("id"),
			"outputformat" : "text/javascript",
			"bbox" : extent.toString(),
			"format_options" : "callback:getJson"
		};
	}
	console.log(extent);
	var addr = this.getFeature_;

	$.ajax({
		url : addr,
		data : params,
		dataType : 'jsonp',
		jsonpCallback : 'getJson',
		beforeSend : function() {
			$("body").css("cursor", "wait");
		},
		complete : function() {
			$("body").css("cursor", "default");
		},
		success : function(data) {
			that.features_.clear();
			var features = new ol.format.GeoJSON().readFeatures(JSON.stringify(data));

			that.destination_.getSource().addFeatures(features);
			that.destination_.setMap(that.map_);
			that.select_.getFeatures().clear();

			that.destination_.getSource().forEachFeatureIntersectingExtent(extent, function(feature) {
				if (feature.getId().indexOf(that.layer.get("id")) !== -1 && !that.record.isRemoved(that.layer, feature)) {
					that.select_.getFeatures().push(feature);
				}
			});
		}
	});
};
gb.interaction.SelectWMS.prototype.setCoordinate = function(evt) {
	if (!this.getLayer().getVisible() || this.getLayer() instanceof ol.layer.Vector) {
		return;
	}
	var that = this;
	this.coordinate_ = evt.coordinate;
	var viewResolution = (this.map_.getView().getResolution());
	var wmsSource = new ol.source.TileWMS({
		url : this.getFeatureInfo_,
		params : this.getLayer().getSource().getParams()
	});
	var url = wmsSource.getGetFeatureInfoUrl(evt.coordinate, viewResolution, this.map_.getView().getProjection().getCode(), {
		'INFO_FORMAT' : 'text/javascript',
		'FORMAT_OPTIONS' : 'callback:getJson'
	});
	if (url) {
		this.getFeatureAJAX(url);
	}
};
gb.interaction.SelectWMS.prototype.setLayer = function(layer) {
	this.layer = layer;
}
gb.interaction.SelectWMS.prototype.getLayer = function() {
	return this.layer;
}
gb.interaction.SelectWMS.prototype.setFeatureId = function(fid) {
	var that = this;
	var params;
	if (that.layer instanceof ol.layer.Tile) {
		params = {
			"service" : "WFS",
			"version" : "1.0.0",
			"request" : "GetFeature",
			"typeName" : this.layer.get("id"),
			"outputformat" : "text/javascript",
			"featureID" : fid,
			"format_options" : "callback:getJson"
		};
	} else if (that.layer instanceof ol.layer.Base && that.layer.get("git").hasOwnProperty("fake")) {
		params = {
			"service" : "WFS",
			"version" : "1.0.0",
			"request" : "GetFeature",
			"typeName" : this.layer.get("id"),
			"outputformat" : "text/javascript",
			"featureID" : fid,
			"format_options" : "callback:getJson"
		};
	}

	var addr = this.getFeature_;

	$.ajax({
		url : addr,
		data : params,
		dataType : 'jsonp',
		jsonpCallback : 'getJson',
		beforeSend : function() {
			$("body").css("cursor", "wait");
		},
		complete : function() {
			$("body").css("cursor", "default");
		},
		success : function(data) {

			that.features_.clear();
			var features = new ol.format.GeoJSON().readFeatures(JSON.stringify(data));
			var ids = [];
			for (var i = 0; i < features.length; i++) {
				ids.push(features[i].getId());
			}
			that.destination_.getSource().addFeatures(features);
			that.destination_.setMap(that.map_);

			console.log(that.select_.getFeatures());
			var vf = that.select_.getFeatures();
			for (var i = 0; i < vf.getLength(); i++) {
				if (vf.item(i).getId().indexOf(that.layer.get("id")) !== -1) {
					ids.push(vf.item(i).getId());
				}
			}
			that.select_.getFeatures().clear();

			for (var i = 0; i < ids.length; i++) {
				var f = that.destination_.getSource().getFeatureById(ids[i]);
				if (f.getId().indexOf(that.layer.get("id")) !== -1 && !that.record.isRemoved(that.layer, f)) {
					that.select_.getFeatures().push(f);
				}
			}

			// that.features_.clear();
			// var features = new
			// ol.format.GeoJSON().readFeatures(JSON.stringify(data));
			// var ids = [];
			// for (var i = 0; i < features.length; i++) {
			// ids.push(features[i].getId());
			// }
			// that.destination_.getSource().addFeatures(features);
			// that.destination_.setMap(that.map_);
			//
			// var selFeatures = that.select_.getFeatures();
			// var cFeatures = [];
			// for (var k = 0; k < selFeatures.getLength(); k++) {
			// if (selFeatures.item(k).getId().search(that.layer.get("id") +
			// ".new") !== -1)
			// {
			// cFeatures.push(selFeatures.item(k));
			// } else {
			// if (!that.record.isRemoved(that.layer, selFeatures.item(k))) {
			// cFeatures.push(selFeatures.item(k));
			// }
			// }
			// }
			// that.select_.getFeatures().clear();
			// that.select_.getFeatures().extend(cFeatures);
			// var newFeatures = [];
			// for (var j = 0; j < ids.length; j++) {
			// if (!that.record.isRemoved(that.layer,
			// that.destination_.getSource().getFeatureById(ids[j]))) {
			// newFeatures.push(that.destination_.getSource().getFeatureById(ids[j]));
			// }
			// }
			// that.select_.getFeatures().extend(newFeatures);
		}
	});
};

gb.interaction.SelectWMS.prototype.getFeatureAJAX = function(url) {
	console.log(url);
	console.log(url.substring(0, url.indexOf("?")));
	var ctrlr = url.substring(0, url.indexOf("?"));
	var prm = url.substring(url.indexOf("?") + 1);
	console.log(prm);
	var QueryStringToJSON = function(str) {
		var pairs = str.split('&');

		var result = {};
		pairs.forEach(function(pair) {
			pair = pair.split('=');
			result[pair[0]] = decodeURIComponent(pair[1] || '');
		});

		return JSON.parse(JSON.stringify(result));
	}

	console.log(QueryStringToJSON(prm));
	var that = this;
	var params = QueryStringToJSON(prm);
	$.ajax({
		url : ctrlr,
		data : params,
		dataType : 'jsonp',
		jsonpCallback : 'getJson',
		beforeSend : function() {
			$("body").css("cursor", "wait");
		},
		complete : function() {
			$("body").css("cursor", "default");
		},
		success : function(data) {

			that.features_.clear();
			var features = new ol.format.GeoJSON().readFeatures(JSON.stringify(data));
			var ids = [];
			for (var i = 0; i < features.length; i++) {
				ids.push(features[i].getId());
			}
			that.destination_.getSource().addFeatures(features);
			that.destination_.setMap(that.map_);

			console.log(that.select_.getFeatures());
			var vf = that.select_.getFeatures();
			for (var i = 0; i < vf.getLength(); i++) {
				if (vf.item(i).getId().indexOf(that.layer.get("id")) !== -1) {
					ids.push(vf.item(i).getId());
				}
			}
			that.select_.getFeatures().clear();

			for (var i = 0; i < ids.length; i++) {
				var f = that.destination_.getSource().getFeatureById(ids[i]);
				if (f.getId().indexOf(that.layer.get("id")) !== -1 && !that.record.isRemoved(that.layer, f)) {
					that.select_.getFeatures().push(f);
				}
			}
		}
	});
}