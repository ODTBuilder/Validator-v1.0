/**
 * 일반화 모달 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2017. 07.26
 * @version 0.01
 * @class gb.modal.BaseCRS
 * @constructor
 */
var gb;
if (!gb)
	gb = {};
if (!gb.modal)
	gb.modal = {};
gb.modal.BaseCRS = function(obj) {
	obj.width = 435;
	gb.modal.Base.call(this, obj);
	var that = this;
	var options = obj ? obj : {};
	this.message = obj.message ? obj.message : undefined;
	this.map = obj.map ? obj.map : undefined;
	this.epsg = obj.epsg ? obj.epsg : "3857";

	this.searchEPSGCode(this.epsg);

	var label = $("<span>").text("EPSG: ");
	this.searchBar = $("<input>").attr({
		"type" : "number"
	}).addClass("gb-form").css({
		"width" : "290px",
		"display" : "inline-block"
	});

	this.searchBtn = $("<button>").text("Search").addClass("gb-button").addClass("gb-button-default").css({
		"display" : "inline-block",
		"vertical-align" : "baseline"
	});
	$(this.searchBtn).click(function() {
		var val = $(that.searchBar).val().replace(/(\s*)/g, '');
		that.searchEPSGCode(val);
	});

	var a = $("<div>").append(label).append(this.searchBar).append(this.searchBtn).css({
		"margin" : "10px 10px"
	});
	$(this.getModalBody()).append(a);
};
gb.modal.BaseCRS.prototype = Object.create(gb.modal.Base.prototype);
gb.modal.BaseCRS.prototype.constructor = gb.modal.BaseCRS;

/**
 * @name getMap
 */
gb.modal.BaseCRS.prototype.getMap = function() {
	return this.map;
};

/**
 * @name setMap
 */
gb.modal.BaseCRS.prototype.setMap = function(map) {
	this.map = map;
};

/**
 * @name getMessage
 */
gb.modal.BaseCRS.prototype.getMessage = function() {
	return this.message;
};

/**
 * @name setMessage
 */
gb.modal.BaseCRS.prototype.setMessage = function(message) {
	this.message = message;
};

/**
 * @name getEPSGCode
 */
gb.modal.BaseCRS.prototype.getEPSGCode = function() {
	return this.epsg;
};

/**
 * @name getEPSGCode
 */
gb.modal.BaseCRS.prototype.setEPSGCode = function(code) {
	this.epsg = code;
};

/**
 * @name searchEPSGCode
 */
gb.modal.BaseCRS.prototype.searchEPSGCode = function(code) {
	console.log(code);
	var that = this;
	fetch('https://epsg.io/?format=json&q=' + code).then(function(response) {
		return response.json();
	}).then(function(json) {
		if (json.number_result !== 1) {
			console.error("no crs");
			return;
		}
		var results = json['results'];
		if (results && results.length > 0) {
			for (var i = 0, ii = results.length; i < ii; i++) {
				var result = results[i];
				if (result) {
					var code = result['code'], name = result['name'], proj4def = result['proj4'], bbox = result['bbox'];
					if (code && code.length > 0 && proj4def && proj4def.length > 0 && bbox && bbox.length == 4) {
						that.setProjection(code, name, proj4def, bbox);
						that.close();
						return;
					}
				}
			}
		}
		return;
	});
};

/**
 * @name setProjection
 */
gb.modal.BaseCRS.prototype.setProjection = function(code, name, proj4def, bbox) {
	var that = this;
	if (code === null || name === null || proj4def === null || bbox === null) {
		if (Array.isArray(this.getMap())) {
			var maps = this.getMap();
			for (var i = 0; i < maps.length; i++) {
				if (maps[i] instanceof ol.Map) {
					maps[i].setView(new ol.View({
						projection : 'EPSG:3857',
						center : [ 0, 0 ],
						zoom : 1
					}));
				}
			}
			return;
		} else if (this.getMap() instanceof ol.Map) {
			this.getMap().setView(new ol.View({
				projection : 'EPSG:3857',
				center : [ 0, 0 ],
				zoom : 1
			}));
			return;
		}
	}
	this.setEPSGCode(code);
	var newProjCode = 'EPSG:' + code;
	$(this.getMessage()).text("[" + newProjCode + "]");
	proj4.defs(newProjCode, proj4def);
	var newProj = ol.proj.get(newProjCode);
	var fromLonLat = ol.proj.getTransform('EPSG:4326', newProj);

	// very approximate calculation of projection extent
	var extent = ol.extent.applyTransform([ bbox[1], bbox[2], bbox[3], bbox[0] ], fromLonLat);
	newProj.setExtent(extent);
	var newView = new ol.View({
		projection : newProj
	});
	if (Array.isArray(this.getMap())) {
		var maps = this.getMap();
		for (var i = 0; i < maps.length; i++) {
			if (maps[i] instanceof ol.Map) {
				maps[i].setView(newView);
			}
		}
	} else if (this.getMap() instanceof ol.Map) {
		this.getMap().setView(newView);
	}
	newView.fit(extent);
	console.log(this.getEPSGCode());
};

/**
 * 모달을 연다
 * 
 * @name open
 */
gb.modal.BaseCRS.prototype.open = function() {
	gb.modal.Base.prototype.open.call(this);
	$(this.searchBar).val(this.getEPSGCode());
};