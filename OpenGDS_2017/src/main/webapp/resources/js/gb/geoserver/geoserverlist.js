/**
 * 지오서버 정보를 임시 저장한다.
 * 
 * @class gb.geoserver.GeoServerList
 * @memberof gb.geoserver
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
 * @version 0.01
 * @author yijun.so
 * @date 2017. 07.26
 */
gb.geoserver.GeoServerList = function(obj) {
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
gb.geoserver.GeoServerList.prototype = Object.create(gb.modal.Base.prototype);
gb.geoserver.GeoServerList.prototype.constructor = gb.geoserver.GeoServerList;

/**
 * 베이스 좌표계를 변경하고자 하는 ol.Map 객체를 반환한다.
 * 
 * @method gb.geoserver.GeoServerList#getMap
 * @return {ol.Map} 베이스 좌표계를 변경하고자 하는 ol.Map 객체
 */
gb.geoserver.GeoServerList.prototype.getMap = function() {
	return this.map;
};

/**
 * 베이스 좌표계를 변경하고자 하는 ol.Map 객체를 설정한다.
 * 
 * @method gb.geoserver.GeoServerList#setMap
 * @param {ol.Map}
 *            map - 베이스 좌표계를 변경하고자 하는 ol.Map 객체
 */
gb.geoserver.GeoServerList.prototype.setMap = function(map) {
	this.map = map;
};

/**
 * 현재 좌표계를 표시할 DOM 객체를 반환한다.
 * 
 * @method gb.geoserver.GeoServerList#getMessage
 * @return 현재 좌표계를 표시할 DOM 객체
 */
gb.geoserver.GeoServerList.prototype.getMessage = function() {
	return this.message;
};

/**
 * 현재 좌표계를 표시할 DOM 객체를 설정한다.
 * 
 * @method gb.geoserver.GeoServerList#setMessage
 * @param {DOM}
 *            message - 현재 좌표계를 표시할 DOM 객체
 */
gb.geoserver.GeoServerList.prototype.setMessage = function(message) {
	this.message = message;
};

/**
 * 모달을 연다
 * 
 * @method gb.geoserver.GeoServerList#open
 * @override
 */
gb.geoserver.GeoServerList.prototype.open = function() {
	gb.modal.Base.prototype.open.call(this);
	$(this.searchBar).val(this.getEPSGCode());
};