/**
 * 베이스 좌표계 변경 모달 객체를 정의한다.
 * 
 * @class gb.modal.BaseCRS
 * @memberof gb.modal
 * @param {Object}
 *            obj - 생성자 옵션을 담은 객체
 * @param {String}
 *            obj.title - 모달의 제목
 * @param {Number}
 *            obj.width - 모달의 너비 (픽셀)
 * @param {Number}
 *            obj.height - 모달의 높이 (픽셀)
 * @param {Boolean}
 *            obj.autoOpen - 선언과 동시에 표출 할 것인지 선택
 * @param {DOM}
 *            obj.message - 현재 좌표계를 출력할 DOM
 * @param {ol.Map}
 *            obj.map - 베이스 좌표계를 변경할 ol.Map 객체
 * @param {String}
 *            obj.epsg - 설정하고자 하는 좌표계의 EPSG 코드
 * @version 0.01
 * @author yijun.so
 * @date 2017. 07.26
 */
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
 * 베이스 좌표계를 변경하고자 하는 ol.Map 객체를 반환한다.
 * 
 * @method gb.modal.BaseCRS#getMap
 * @return {ol.Map} 베이스 좌표계를 변경하고자 하는 ol.Map 객체
 */
gb.modal.BaseCRS.prototype.getMap = function() {
	return this.map;
};

/**
 * 베이스 좌표계를 변경하고자 하는 ol.Map 객체를 설정한다.
 * 
 * @method gb.modal.BaseCRS#setMap
 * @param {ol.Map}
 *            map - 베이스 좌표계를 변경하고자 하는 ol.Map 객체
 */
gb.modal.BaseCRS.prototype.setMap = function(map) {
	this.map = map;
};

/**
 * 현재 좌표계를 표시할 DOM 객체를 반환한다.
 * 
 * @method gb.modal.BaseCRS#getMessage
 * @return 현재 좌표계를 표시할 DOM 객체
 */
gb.modal.BaseCRS.prototype.getMessage = function() {
	return this.message;
};

/**
 * 현재 좌표계를 표시할 DOM 객체를 설정한다.
 * 
 * @method gb.modal.BaseCRS#setMessage
 * @param {DOM}
 *            message - 현재 좌표계를 표시할 DOM 객체
 */
gb.modal.BaseCRS.prototype.setMessage = function(message) {
	this.message = message;
};

/**
 * 현재 적용된 베이스 좌표계의 EPSG 코드를 반환한다.
 * 
 * @method gb.modal.BaseCRS#getEPSGCode
 * @return {String} 현재 적용된 베이스 좌표계의 EPSG 코드
 */
gb.modal.BaseCRS.prototype.getEPSGCode = function() {
	return this.epsg;
};

/**
 * 현재 적용된 베이스 좌표계의 EPSG 코드를 설정한다.
 * 
 * @method gb.modal.BaseCRS#getEPSGCode
 * @param {String}
 *            code - 현재 적용된 베이스 좌표계의 EPSG 코드
 */
gb.modal.BaseCRS.prototype.setEPSGCode = function(code) {
	this.epsg = code;
};

/**
 * 베이스 좌표계를 변경하기 위한 EPSG 코드를 검색한다.
 * 
 * @method gb.modal.BaseCRS#searchEPSGCode
 * @param {String}
 *            code - 베이스 좌표계를 변경하기 위한 EPSG 코드
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
 * 베이스 좌표계를 적용한다.
 * 
 * @method gb.modal.BaseCRS#setProjection
 * @param {String}
 *            code - EPSG 코드
 * @param {String}
 *            name - 좌표계 이름
 * @param {String}
 *            proj4def - proj4 좌표계
 * @param {Number[]}
 *            bbox - 좌표계 영역
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
 * @method gb.modal.BaseCRS#open
 * @override
 */
gb.modal.BaseCRS.prototype.open = function() {
	gb.modal.Base.prototype.open.call(this);
	$(this.searchBar).val(this.getEPSGCode());
};