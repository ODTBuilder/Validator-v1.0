/**
 * 공간정보 편집도구를 구성하는 함수를 정의한다.(Interaction)
 * 
 * @author yijun.so
 * @date 2016. 10. 21
 * @version 0.01
 */
var gb;
if (!gb)
	gb = {};
if (!gb.interaction)
	gb.interaction = {};

gb.interaction.DeleteVertex = function() {
	ol.interaction.Modify.call(this, {
		condition : gb.interaction.DeleteVertex.prototype.condition
	});
};
/**
 * 드로우 인터렉션에 그리는중에 타입을 바꾸는 함수 추가5
 */
ol.interaction.Draw.prototype.switchType_ = function(){
	this.type_ = this.selectedType();
	/**
	 * Drawing mode (derived from geometry type.
	 * 
	 * @type {ol.interaction.Draw.Mode_}
	 * @private
	 */
	this.mode_ = ol.interaction.Draw.getMode_(this.type_);
	/**
	 * The number of points that must be drawn before a polygon ring or line
	 * string can be finished. The default is 3 for polygon rings and 2 for line
	 * strings.
	 * 
	 * @type {number}
	 * @private
	 */
	if (this.mode_ === ol.interaction.Draw.Mode_.POLYGON) {
		this.minPoints_ = 3;
	} else {
		this.minPoints_ = 2;
	}

	var geometryFunction;
	if (!geometryFunction) {
		if (this.type_ === ol.geom.GeometryType.CIRCLE) {
			/**
			 * @param {ol.Coordinate|Array.
			 *            <ol.Coordinate>|Array.<Array.<ol.Coordinate>>}
			 *            coordinates The coordinates.
			 * @param {ol.geom.SimpleGeometry=}
			 *            opt_geometry Optional geometry.
			 * @return {ol.geom.SimpleGeometry} A geometry.
			 */
			geometryFunction = function(coordinates, opt_geometry) {
				var circle = opt_geometry ? /** @type {ol.geom.Circle} */ (opt_geometry) :
					new ol.geom.Circle([NaN, NaN]);
				var squaredLength = ol.coordinate.squaredDistance(
						coordinates[0], coordinates[1]);
				circle.setCenterAndRadius(coordinates[0], Math.sqrt(squaredLength));
				return circle;
			};
		} else {
			var Constructor;
			var mode = this.mode_;
			if (mode === ol.interaction.Draw.Mode_.POINT) {
				Constructor = ol.geom.Point;
			} else if (mode === ol.interaction.Draw.Mode_.LINE_STRING) {
				Constructor = ol.geom.LineString;
			} else if (mode === ol.interaction.Draw.Mode_.POLYGON) {
				Constructor = ol.geom.Polygon;
			}
			/**
			 * @param {ol.Coordinate|Array.
			 *            <ol.Coordinate>|Array.<Array.<ol.Coordinate>>}
			 *            coordinates The coordinates.
			 * @param {ol.geom.SimpleGeometry=}
			 *            opt_geometry Optional geometry.
			 * @return {ol.geom.SimpleGeometry} A geometry.
			 */
			geometryFunction = function(coordinates, opt_geometry) {
				var geometry = opt_geometry;
				if (geometry) {
					if (mode === ol.interaction.Draw.Mode_.POLYGON) {
						geometry.setCoordinates([coordinates[0].concat([coordinates[0][0]])]);
					} else {
						geometry.setCoordinates(coordinates);
					}
				} else {
					geometry = new Constructor(coordinates);
				}
				return geometry;
			};
		}
	}

	/**
	 * @type {ol.DrawGeometryFunctionType}
	 * @private
	 */
	this.geometryFunction_ = geometryFunction;
	console.log(this.sketchCoords_);
	console.log(this.sketchLineCoords_);
}
/**
 * 드로우 인터렉션 핸들다운 이벤트 오버라이드
 * 
 * @param {ol.MapBrowserPointerEvent}
 *            event Event.
 * @return {boolean} Start drag sequence?
 * @this {ol.interaction.Draw}
 * @private
 */
ol.interaction.Draw.handleDownEvent_ = function(event) {
	this.switchType_();
	this.shouldHandle_ = !this.freehand_;

	if (this.freehand_) {
		this.downPx_ = event.pixel;
		if (!this.finishCoordinate_) {
			this.startDrawing_(event);
		}
		return true;
	} else if (this.condition_(event)) {
		this.downPx_ = event.pixel;
		return true;
	} else {
		return false;
	}
};

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
	this.url_ = options.url ? options.url : null;
}
ol.inherits(gb.interaction.SelectWMS, ol.interaction.Interaction);

gb.interaction.SelectWMS.prototype.handleEvent = function(evt) {
	var that = this;
	this.map_ = evt.map;
	if (evt.type === "singleclick") {
		var coord = evt.coordinate;
// this.setCoordinate(coord);
		this.setExtent([coord[0],coord[1],coord[0],coord[1]]);
	}
	return true;
};

gb.interaction.SelectWMS.prototype.getFeatures = function() {
	return this.features_;
};

gb.interaction.SelectWMS.prototype.setExtent = function(extent) {
	if (typeof this.conLayer === "function") {
		this.layer = this.conLayer();
	} else if (this.conLayer instanceof ol.layer.Base){
		this.layer = this.conLayer;
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

	var addr = this.url_;

	$.ajax({
		url : addr,
		data : params,
		dataType : 'jsonp',
		jsonpCallback : 'getJson',
		beforeSend : function(){
			$("body").css("cursor", "wait");
		},
		complete : function(){
			$("body").css("cursor", "default");
		},
		success : function(data){
			that.features_.clear();
			var features = new ol.format.GeoJSON().readFeatures(JSON.stringify(data));
			var ids = [];
			for (var i = 0; i < features.length; i++) {
				ids.push(features[i].getId());
			}
			that.destination_.getSource().addFeatures(features);
			that.destination_.setMap(that.map_);

			var selFeatures = that.select_.getFeatures();
			var cFeatures = [];
			for (var k = 0; k < selFeatures.getLength(); k++) {
				if (selFeatures.item(k).getId().search(that.layer.get("id")+".new") !== -1) {
					cFeatures.push(selFeatures.item(k));
				}
// else {
// if (!that.record.isRemoved(that.layer, selFeatures.item(k))) {
// cFeatures.push(selFeatures.item(k));
// }
// }
			}
			that.select_.getFeatures().clear();
			that.select_.getFeatures().extend(cFeatures);
			var newFeatures = [];
			for (var j = 0; j < ids.length; j++) {
				if (!that.record.isRemoved(that.layer, that.destination_.getSource().getFeatureById(ids[j]))) {
					newFeatures.push(that.destination_.getSource().getFeatureById(ids[j]));	
				}
			}
			that.select_.getFeatures().extend(newFeatures);
		}
	});
};
gb.interaction.SelectWMS.prototype.setCoordinate = function(coord) {
	if (typeof this.conLayer === "function") {
		this.layer = this.conLayer();
	} else if (this.conLayer instanceof ol.layer.Base){
		this.layer = this.conLayer;
	}
	this.coordinate_ = coord;
	var that = this;
	var params;
	if (that.layer instanceof ol.layer.Tile) {
		params = {
				"service" : "WFS",
				"version" : "1.0.0",
				"request" : "GetFeature",
				"typeName" : that.layer.getSource().getParams().LAYERS,
				"outputformat" : "text/javascript",
				"x" : coord[0],
				"y" : coord[1],
				"format_options" : "callback:getJson"
		};
	} else if (that.layer instanceof ol.layer.Base && that.layer.get("git").hasOwnProperty("fake")) {
// params = {
// "service" : "WMS",
// "version" : "1.0.0",
// "request" : "GetFeatureInfo",
// "query_layers" : that.layer.get("id"),
// "outputformat" : "text/javascript",
// // "cql_filter" :
// "INTERSECT(the_geom,%20Point%20("+coord[0]+"%20"+coord[1]+"))",
// "format_options" : "callback:getJson"
// };
		params = {
				"service" : "WMS",
				"version" : "1.0.0",
				"request" : "GetFeatureInfo",
				"layers" : that.layer.get("id"),
				"info_format" : "application/json",
// "cql_filter" :
// "INTERSECT(the_geom,%20Point%20("+coord[0]+"%20"+coord[1]+"))",
				"format_options" : "callback:getJson"
		};
	}

	var addr = this.url_;

	$.ajax({
		url : addr,
		data : params,
		dataType : 'jsonp',
		jsonpCallback : 'getJson',
		beforeSend : function(){
			$("body").css("cursor", "wait");
		},
		complete : function(){
			$("body").css("cursor", "default");
		},
		success : function(data){
			that.features_.clear();
			var features = new ol.format.GeoJSON().readFeatures(JSON.stringify(data));
			var ids = [];
			for (var i = 0; i < features.length; i++) {
				ids.push(features[i].getId());
			}
			that.destination_.getSource().addFeatures(features);
			that.destination_.setMap(that.map_);

			var selFeatures = that.select_.getFeatures();
			var cFeatures = [];
			for (var k = 0; k < selFeatures.getLength(); k++) {
				if (selFeatures.item(k).getId().search(that.layer.get("id")+".new") !== -1) {
					cFeatures.push(selFeatures.item(k));
				}
// else {
// if (!that.record.isRemoved(that.layer, selFeatures.item(k))) {
// cFeatures.push(selFeatures.item(k));
// }
// }
			}
			that.select_.getFeatures().clear();
			that.select_.getFeatures().extend(cFeatures);
			var newFeatures = [];
			for (var j = 0; j < ids.length; j++) {
				if (!that.record.isRemoved(that.layer, that.destination_.getSource().getFeatureById(ids[j]))) {
					newFeatures.push(that.destination_.getSource().getFeatureById(ids[j]));	
				}
			}
			that.select_.getFeatures().extend(newFeatures);
		}
	});
};
gb.interaction.SelectWMS.prototype.setLayer = function(layer) {
	this.layer = layer;
}
gb.interaction.SelectWMS.prototype.getLayer = function() {
	return this.layer;
}
gb.interaction.SelectWMS.prototype.setFeatureId = function(fid) {
// if (typeof this.conLayer === "function") {
// this.layer = this.conLayer();
// } else if (this.conLayer instanceof ol.layer.Base){
// this.layer = this.conLayer;
// }
	
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

	var addr = this.url_;

	$.ajax({
		url : addr,
		data : params,
		dataType : 'jsonp',
		jsonpCallback : 'getJson',
		beforeSend : function(){
			$("body").css("cursor", "wait");
		},
		complete : function(){
			$("body").css("cursor", "default");
		},
		success : function(data){
			that.features_.clear();
			var features = new ol.format.GeoJSON().readFeatures(JSON.stringify(data));
			var ids = [];
			for (var i = 0; i < features.length; i++) {
				ids.push(features[i].getId());
			}
			that.destination_.getSource().addFeatures(features);
			that.destination_.setMap(that.map_);

			var selFeatures = that.select_.getFeatures();
			var cFeatures = [];
			for (var k = 0; k < selFeatures.getLength(); k++) {
				if (selFeatures.item(k).getId().search(that.layer.get("id")+".new") !== -1) {
					cFeatures.push(selFeatures.item(k));
				}
// else {
// if (!that.record.isRemoved(that.layer, selFeatures.item(k))) {
// cFeatures.push(selFeatures.item(k));
// }
// }
			}
			that.select_.getFeatures().clear();
			that.select_.getFeatures().extend(cFeatures);
			var newFeatures = [];
			for (var j = 0; j < ids.length; j++) {
				if (!that.record.isRemoved(that.layer, that.destination_.getSource().getFeatureById(ids[j]))) {
					newFeatures.push(that.destination_.getSource().getFeatureById(ids[j]));	
				}
			}
			that.select_.getFeatures().extend(newFeatures);
		}
	});
};

gb.interaction.MultiTransform = function(opt_options) {

	var options = opt_options ? opt_options : {};

	ol.interaction.Pointer.call(this, {
		handleDownEvent : gb.interaction.MultiTransform.prototype.handleDownEvent,
		handleDragEvent : gb.interaction.MultiTransform.prototype.handleDragEvent,
		handleMoveEvent : gb.interaction.MultiTransform.prototype.handleMoveEvent,
		handleUpEvent : gb.interaction.MultiTransform.prototype.handleUpEvent
	});

	/**
	 * 현재 커서의 위치를 저장
	 * 
	 * @type {<Array>}
	 * @private
	 */
	this.cursorCoordinate_ = null;

	/**
	 * 이전 커서의 위치를 저장
	 * 
	 * @type {<Array>}
	 * @private
	 */
	this.prevCursor_ = null;

	/**
	 * MultiTransform을 적용시킬 feature들을 저장
	 * 
	 * @type {ol.Collection.<ol.Feature>}
	 * @private
	 */
	this.features_ = options.features;

	/**
	 * 수행할 작업을 저장 ('rotate', 'scale', 'filp')
	 * 
	 * @type {<String>}
	 * @private
	 */
	this.task_ = null;

	/**
	 * true 일 때 drag event로 feature scale 실행
	 * 
	 * @type {<Boolean>}
	 * @private
	 */
	this.scale_ = false;

	/**
	 * true 일 때 drag event로 feature rotate 실행
	 * 
	 * @type {<Boolean>}
	 * @private
	 */
	this.rotate_ = false;

	/**
	 * rotate, scale 함수 parameter 값을 위한 feature의 중점좌표
	 * 
	 * @type {<Array>}
	 * @private
	 */
	this.flatInteriorPoint_ = null;
};
ol.inherits(gb.interaction.MultiTransform, ol.interaction.Pointer);

/**
 * map 객체에 있는 interaction 중에 ol.interaction.Select 객체를 찾아 비활성화 시키거나 활성화 시킴
 * MultiTransform 버튼을 클릭하였을때 객체 선택이 풀리는 경우를 방지하기 위해 사용
 * 
 * @param {Boolean}
 * @this {gb.interaction.MultiTransform}
 */
gb.interaction.MultiTransform.prototype.setActiveSelect_ = function(bool) {
	var map = this.getMap();
	map.getInteractions().forEach(function(interaction) {
		if (interaction instanceof ol.interaction.Select) {
			interaction.setActive(bool);
		}
	});
}

/**
 * ol.interaction.Select의 active상태를 반환
 * 
 * @return {Boolean}
 * @this {gb.interaction.MultiTransform}
 */
gb.interaction.MultiTransform.prototype.getActiveSelect_ = function() {
	var map = this.getMap();
	var bool = null;
	map.getInteractions().forEach(function(interaction) {
		if (interaction instanceof ol.interaction.Select) {
			bool = interaction.getActive();
		}
	});
	return bool;
}

/**
 * down이벤트 발생시 우선적으로 select interaction의 active를 확인하고 false일 경우 true로 값을 설정해준다.
 * 선택된 피쳐가 있고 cursor가 버튼 위에 위치해 있다면 작업을 수행한다. 수행해야할 작업이 rotate라면 {this.rotate_}를
 * true로 설정하고 scale이면 {this.scale_}을 true로 설정한다. flip일 경우의 이벤트는 select
 * interaction의 click 이벤트와 겹치기때문에 동시 실행되어 예기치 못한 상황이 발생할 수 있으므로 select active를
 * false로 설정한다.
 * 
 * @return {Boolean} true 반환시 drag squence 시작
 * @this {gb.interaction.MultiTransform}
 */
gb.interaction.MultiTransform.prototype.handleDownEvent = function(evt) {
	var feature = this.features_.item(0);

	if (!this.getActiveSelect_()) {
		this.setActiveSelect_(true);
	}

	if (feature) {
		var map = evt.map;
		var element = evt.map.getTargetElement();
		var geometry = feature.getGeometry();
		var extent = geometry.getExtent();
		var x = extent[0] + (extent[2] - extent[0]) / 2;
		var y = extent[1] + (extent[3] - extent[1]) / 2;

		if (element.style.cursor !== '' && this.task_) {
			switch (this.task_) {
			case 'rotate':
				this.rotate_ = true;
				break;
			case 'scaleW':
			case 'scaleE':
				this.scale_ = true;
				break;
			default:
				if (this.getActiveSelect_()) {
					this.setActiveSelect_(false);
				}
			}
			this.flatInteriorPoint_ = [ x, y ];
		}
	}
	this.dispatchEvent(
			new gb.interaction.MultiTransform.Event(
					gb.interaction.MultiTransformEventType.TRANSFORMSTART, feature,
					evt)
	);
	return (!!feature && !!this.task_);
};

/**
 * rotate 또는 scale을 수행한다.
 * 
 * @this {gb.interaction.MultiTransform}
 */
gb.interaction.MultiTransform.prototype.handleDragEvent = function(evt) {

	var feature = this.features_.item(0);

	if (this.rotate_ === true) {
		var prev = this.prevCursor_;
		var current = evt.coordinate;
		var rad = this.rotateAlgorithm_(prev, current);

		// shift키 누른채로 mouse move시 rotate 회전 속도가 절반으로 줄어듬
		if (evt.originalEvent.shiftKey === true) {
			feature.getGeometry().rotate(rad / 2, this.flatInteriorPoint_);
		} else {
			feature.getGeometry().rotate(rad, this.flatInteriorPoint_);
		}
	}

	if (this.scale_ === true) {
		var cursorPoint = evt.coordinate;
		var magni = this.scaleAlgorithm_(feature, cursorPoint);

		if (magni[0] > magni[1]) {
			feature.getGeometry().scale(magni[0], magni[0], this.flatInteriorPoint_);
		} else {
			feature.getGeometry().scale(magni[1], magni[1], this.flatInteriorPoint_);
		}
	}
	this.dispatchEvent(
			new gb.interaction.MultiTransform.Event(
					gb.interaction.MultiTransformEventType.TRANSFORMING, feature,
					evt)
	);
	this.prevCursor_ = evt.coordinate;
};

/**
 * drag 실행 중에는 실행되지 않는다. {this.selectTask_} 함수를 통해 MultiTransform 버튼의 위치를 인식하고
 * 커서가 버튼위에 있을때 {this.task_}의 값과 마우스 커서 스타일 값을 변경한다.
 * 
 * @this {gb.interaction.MultiTransform}
 */
gb.interaction.MultiTransform.prototype.handleMoveEvent = function(evt) {
	if (!evt.dragging) {
		var map = evt.map
		var feature = this.features_.item(0);
		var element = evt.map.getTargetElement();
		var task = null;

		if (!!feature) {
			task = this.selectTask_(map, feature, evt.pixel);

			if (!!task) {
				switch (task) {
				case 'rotate':
					element.style.cursor = 'pointer';
					break;
				case 'scaleW':
					element.style.cursor = 'nw-resize';
					break;
				case 'scaleE':
					element.style.cursor = 'ne-resize';
					break;
				default:
					element.style.cursor = 'pointer';
				}
				this.task_ = task;
			} else {
				if (element.style.cursor !== '') {
					element.style.cursor = '';
				}
				if (this.task_) {
					this.task_ = null;
				}
			}
		} else {
			if (element.style.cursor !== '') {
				element.style.cursor = '';
			}
			if (this.task_) {
				this.task_ = null;
			}
		}
		this.cursorCoordinate_ = evt.coordinate;
	}
};

/**
 * {this.task_}가 flip 작업을 위한 값일때 flip을 실행한다. {this.rotate_}와 {this.scale_}의 값이
 * true 일때 해당작업들에서 설정되었던 멤버변수들을 모두 초기화시킨다.
 * 
 * @return {Boolean} false 반환시 drag squence 종료
 * @this {gb.interaction.MultiTransform}
 */
gb.interaction.MultiTransform.prototype.handleUpEvent = function(evt) {
	var feature = this.features_.item(0);

	if (feature) {
		var map = evt.map;
		var element = evt.map.getTargetElement();

		if (element.style.cursor !== '' && this.task_ !== 'rotate' && !this.task_.match(/^scale/i)) {
			this.flipAlgorithm_(feature, this.task_);
		}
	}

	if (this.rotate_ || this.scale_) {
		this.rotate_ = false;
		this.scale_ = false;
		this.prevCursor_ = null;
		this.flatInteriorPoint = null;
		element.style.cursor = '';
	}
	this.dispatchEvent(
			new gb.interaction.MultiTransform.Event(
					gb.interaction.MultiTransformEventType.TRANSFORMEND, feature,
					evt)
	);
	return false;
};

/**
 * 'flip', 'rotate', 'scale' 기능을 수행할 수 있는 이벤트 영역을 {ol.style.Style}객체로 그려낸다.
 * 
 * @param {ol.render.Event}
 * @this {gb.interaction.MultiTransform}
 */
gb.interaction.MultiTransform.prototype.drawMbr = function(evt) {

	var map = this.getMap();

	if (this.features_.item(0) !== undefined) {

		var mbr = null;
		var line = null;
		var circle = null;

		var triangle = [];
		var square = [];
		var point = [];

		var features = [];

		var strokes = {
				'line' : new ol.style.Stroke({
					color : 'rgba(152,152,152,0.6)',
					width : 3,
					lineDash : [ 1, 4 ]
				}),
				'default' : new ol.style.Stroke({
					color : 'rgba(152,152,152,0.8)',
					width : 1.5
				})
		};

		var fill = new ol.style.Fill({
			color : 'rgba(152,152,152,0.9)'
		});

		var styles = {
				'line' : new ol.style.Style({
					stroke : strokes['line'],
					image : new ol.style.Circle({
						radius : 10,
						stroke : strokes['line']
					})
				}),
				'circle' : new ol.style.Style({
					// stroke: strokes['circle'],
					image : new ol.style.Circle({
						radius : 8,
						stroke : strokes['default']
					})
				}),
				'square' : new ol.style.Style({
					image : new ol.style.RegularShape({
						stroke : strokes['default'],
						points : 4,
						radius : 8,
						angle : Math.PI / 4
					})
				}),
				'triangle' : new ol.style.Style({
					image : new ol.style.RegularShape({
						stroke : strokes['default'],
						points : 3,
						radius : 8
					})
				})
		};

		var extent = this.features_.item(0).getGeometry().getExtent();
		var coorX = (extent[0] + extent[2]) / 2;
		var rotatePositionA = (extent[2] - extent[0]) / 3;
		var rotatePositionB = (extent[3] - extent[1]) / 3;

		var vectorContext = evt.vectorContext;

		mbr = new ol.geom.Polygon(
				[ [ [ extent[0], extent[3] ], [ extent[0], extent[1] ], [ extent[2], extent[1] ], [ extent[2], extent[3] ], [ extent[0], extent[3] ] ] ]);

		if (rotatePositionA > rotatePositionB) {
			line = new ol.geom.LineString([ [ coorX, extent[3] ], [ coorX, extent[3] + rotatePositionB ] ]);
			circle = new ol.geom.Point([ coorX, extent[3] + rotatePositionB ]);
		} else {
			line = new ol.geom.LineString([ [ coorX, extent[3] ], [ coorX, extent[3] + rotatePositionA ] ]);
			circle = new ol.geom.Point([ coorX, extent[3] + rotatePositionA ]);
		}

		vectorContext.drawFeature(new ol.Feature({
			geometry : circle,
			name : 'rotate'
		}), styles['circle']);

		square.push(new ol.geom.Point([ extent[0], extent[1] ]));
		square.push(new ol.geom.Point([ extent[0], extent[3] ]));
		square.push(new ol.geom.Point([ extent[2], extent[1] ]));
		square.push(new ol.geom.Point([ extent[2], extent[3] ]));

		for ( var i in square) {
			vectorContext.drawFeature(new ol.Feature({
				geometry : square[i],
				name : 'scale'
			}), styles['square']);
		}

		triangle.push(new ol.geom.Point([ (extent[0] + extent[2]) / 2, extent[3] ]));
		triangle.push(new ol.geom.Point([ extent[2], (extent[1] + extent[3]) / 2 ]));
		triangle.push(new ol.geom.Point([ (extent[0] + extent[2]) / 2, extent[1] ]));
		triangle.push(new ol.geom.Point([ extent[0], (extent[1] + extent[3]) / 2 ]));

		for ( var i in triangle) {
			styles['triangle'].getImage().setRotation(i * Math.PI / 2);
			vectorContext.drawFeature(new ol.Feature({
				geometry : triangle[i],
				name : 'flip'
			}), styles['triangle']);
		}

		vectorContext.setStyle(styles['line']);
		vectorContext.drawGeometry(mbr);
		vectorContext.drawGeometry(line);
	}
};

/**
 * @return {ol.Collection.<ol.Feature>}
 * @this {gb.interaction.MultiTransform}
 */
gb.interaction.MultiTransform.prototype.getFeatures = function() {
	return this.features_;
};

/**
 * style로 그려진 MultiTransform의 버튼들의 영역을 설정하고 cursor가 그 위치에 있을때 해당버튼에 맞는 작업의 이름을
 * String으로 반환한다. 커서가 버튼 영역에 놓여있지 않다면 null 값을 반환한다.
 * 
 * @param {ol.Map}
 * @param {ol.Feature}
 * @param {Array}
 * @return {String}
 * @this {gb.interaction.MultiTransform}
 */
gb.interaction.MultiTransform.prototype.selectTask_ = function(map, feature, cursor) {

	const AREA = 6;

	var extent = feature.getGeometry().getExtent();
	var scale = [];
	var flip = [];
	var rotate = null;
	var task = null;
	var rotatePositionA = (extent[2] - extent[0]) / 3;
	var rotatePositionB = (extent[3] - extent[1]) / 3;

	if (rotatePositionA > rotatePositionB) {
		rotate = map.getPixelFromCoordinate([ (extent[0] + extent[2]) / 2, extent[3] + rotatePositionB ]);
	} else {
		rotate = map.getPixelFromCoordinate([ (extent[0] + extent[2]) / 2, extent[3] + rotatePositionA ]);
	}

	scale.push(map.getPixelFromCoordinate([ extent[0], extent[1] ]));
	scale.push(map.getPixelFromCoordinate([ extent[0], extent[3] ]));
	scale.push(map.getPixelFromCoordinate([ extent[2], extent[3] ]));
	scale.push(map.getPixelFromCoordinate([ extent[2], extent[1] ]));

	flip.push(map.getPixelFromCoordinate([ (extent[0] + extent[2]) / 2, extent[1] ]));
	flip.push(map.getPixelFromCoordinate([ (extent[0] + extent[2]) / 2, extent[3] ]));
	flip.push(map.getPixelFromCoordinate([ extent[0], (extent[1] + extent[3]) / 2 ]));
	flip.push(map.getPixelFromCoordinate([ extent[2], (extent[1] + extent[3]) / 2 ]));

	if ((cursor[0] >= rotate[0] - AREA && cursor[0] <= rotate[0] + AREA) && (cursor[1] >= rotate[1] - AREA && cursor[1] <= rotate[1] + AREA)) {
		task = 'rotate';
	}

	for ( var i in scale) {
		if ((cursor[0] >= scale[i][0] - AREA && cursor[0] <= scale[i][0] + AREA) && (cursor[1] >= scale[i][1] - AREA && cursor[1] <= scale[i][1] + AREA)) {
			(i % 2 === 0) ? task = 'scaleE' : task = 'scaleW';
		}
	}

	for ( var i in flip) {
		if ((cursor[0] >= flip[i][0] - AREA && cursor[0] <= flip[i][0] + AREA) && (cursor[1] >= flip[i][1] - AREA && cursor[1] <= flip[i][1] + AREA)) {
			if (i === '0') {
				task = 'down';
			} else if (i === '1') {
				task = 'up';
			} else if (i === '2') {
				task = 'left';
			} else if (i === '3') {
				task = 'right';
			}
		}
	}

	return task;
};

/**
 * 피처 회전 알고리즘. 'pointerdrag' 이벤트 발생전의 커서 위치값과 이벤트 발생시의 커서값을 인자로 받아 선택된 피쳐의 중점을
 * 기준으로 두 점 사이의 각도를 반환
 * 
 * @param {Array}
 *            'pointerdrag'이벤트 발생전의 커서 위치값
 * @param {Array}
 *            'pointerdrag'이벤트 발생시의 커서 위치값
 * @return {Float} 사이각을 radian 형식으로 반환
 * @this {gb.interaction.MultiTransform}
 */
gb.interaction.MultiTransform.prototype.rotateAlgorithm_ = function(prevCursorPosition, currentCursorPosition) {
	var feature = this.features_.item(0);
	var result = null;
	var extent = feature.getGeometry().getExtent();
	var pivot = this.flatInteriorPoint_;
	var prev = prevCursorPosition;
	if (!prev) {
		prev = [ (extent[2] + extent[0]) / 2, extent[3] ];
	}
	var current = currentCursorPosition;

	var currentRadi = Math.atan2(current[1] - pivot[1], current[0] - pivot[0]);
	var prevRadi = Math.atan2(prev[1] - pivot[1], prev[0] - pivot[0]);

	if (currentRadi > 0 && prevRadi < 0) {
		result = currentRadi - Math.abs(prevRadi);
	} else if (currentRadi < 0 && prevRadi > 0) {
		result = Math.abs(currentRadi) - prevRadi;
	} else {
		result = currentRadi - prevRadi;
	}
	return result;
};

/**
 * 피처 확대, 축소 알고리즘. 선택된 scale버튼의 이전 좌표값과 pointer를 drag함으로서 변경된 커서의 좌표, 두 좌표값사이
 * 길이의 배율값을 구하여 그 배율의 절대값을 리턴한다.
 * 
 * @param {ol.Feature}
 * @param {Array}
 *            drag를 통해 변경된 커서의 위치
 * @return {Array} 늘어난 x좌표, y좌표 배율의 절대값
 * @this {gb.interaction.MultiTransform}
 */
gb.interaction.MultiTransform.prototype.scaleAlgorithm_ = function(feature, currentCursorPoint) {
	var map = this.getMap();
	var extent = feature.getGeometry().getExtent();
	var cursor = currentCursorPoint;
	var center = this.flatInteriorPoint_;
	var result = [];

	var cursorPixel = map.getPixelFromCoordinate(currentCursorPoint);
	var centerPixel = map.getPixelFromCoordinate(center);
	var subX = Math.abs(cursorPixel[0] - centerPixel[0]);
	var subY = Math.abs(cursorPixel[1] - centerPixel[1]);
	var magniX = 0;
	var magniY = 0;

	if (subX < 2 || subY < 2) {
		magniX = 1;
		magniY = 1;
	} else {
		magniX = (cursor[0] - center[0]) / (extent[0] - center[0]);
		magniY = (cursor[1] - center[1]) / (extent[1] - center[1]);
	}

	result.push(Math.abs(magniX), Math.abs(magniY));
	return result;
};

/**
 * 피처 플립 알고리즘. 선택한 방향으로 feature를 Flip.
 * 
 * @param {String}
 *            Flip을 할 방향
 */
gb.interaction.MultiTransform.prototype.flipAlgorithm_ = function(feature, direction) {
	var extentIndex = null;
	var geometry = feature.getGeometry();
	var extent = feature.getGeometry().getExtent();
	var coordi = feature.getGeometry().getFlatCoordinates();
	var newCoordi = [];
	var newGeometry = null;

	if (direction === "up") {
		extentIndex = 3;
	} else if (direction === "down") {
		extentIndex = 1;
	} else if (direction === "right") {
		extentIndex = 2;
	} else if (direction === "left") {
		extentIndex = 0;
	} else {
		console.error('direction error');
		return;
	}

	for (var i = 0; i < coordi.length / 2; i++) {
		if (extentIndex === 1 || extentIndex === 3) {
			if (coordi[2 * i + 1] !== extent[extentIndex]) {
				newCoordi.push([ coordi[2 * i], 2 * extent[extentIndex] - coordi[2 * i + 1] ]);
			} else {
				newCoordi.push([ coordi[2 * i], coordi[2 * i + 1] ]);
			}
		} else {
			if (coordi[2 * i] !== extent[extentIndex]) {
				newCoordi.push([ 2 * extent[extentIndex] - coordi[2 * i], coordi[2 * i + 1] ]);
			} else {
				newCoordi.push([ coordi[2 * i], coordi[2 * i + 1] ]);
			}
		}
	}

	if (geometry instanceof ol.geom.MultiPolygon) {
		newGeometry = new ol.geom.MultiPolygon([ [ newCoordi ] ]);
	} else if (geometry instanceof ol.geom.Polygon) {
		newGeometry = new ol.geom.Polygon([ newCoordi ]);
	} else if (geometry instanceof ol.geom.MultiLineString) {
		newGeometry = new ol.geom.MultiLineString([ newCoordi ]);
	} else if (geometry instanceof ol.geom.LineString) {
		newGeometry = new ol.geom.LineString(newCoordi);
	} else {
		console.error("feature type error");
		return;
	}
	feature.setGeometry(newGeometry);
};

/*
 * MultiTransform event type
 */
gb.interaction.MultiTransformEventType = {
		TRANSFORMSTART : 'transformstart',
		TRANSFORMING : 'transforming',
		TRANSFORMEND : 'transformend'
};

/**
 * @classdesc Events emitted by {@link gb.interaction.MultiTransform} instances
 *            are instances of this type.
 * 
 * @constructor
 * @extends {ol.events.Event}
 * @param {ol.interaction.MultiTransformEventType}
 *            type Type.
 * @param {ol.Feature}
 *            feature The feature modified.
 * @param {ol.MapBrowserPointerEvent}
 *            mapBrowserPointerEvent Associated
 *            {@link ol.MapBrowserPointerEvent}.
 */
gb.interaction.MultiTransform.Event = function(type, feature, mapBrowserPointerEvent) {

	ol.events.Event.call(this, type);

	/**
	 * The features being modified.
	 * 
	 * @type {ol.Feature}
	 * @api
	 */
	this.feature = feature;

	/**
	 * Associated {@link ol.MapBrowserEvent}.
	 * 
	 * @type {ol.MapBrowserEvent}
	 * @api
	 */
	this.mapBrowserEvent = mapBrowserPointerEvent;
};
ol.inherits(gb.interaction.MultiTransform.Event, ol.events.Event);

gb.interaction.MeasureTip = function(opt_options) {

	ol.interaction.Interaction.call(this, {
		handleEvent : gb.interaction.MeasureTip.prototype.handleEvent
	});

	var options = opt_options ? opt_options : {};

	/**
	 * MeasureTip을 적용시킬 feature들을 저장 ol.interaction.Select 객체를 저장하면 선택된 feature만
	 * 적용시킬 수 있음
	 * 
	 * @type {ol.Collection.<ol.Feature>}
	 * @private
	 */
	this.features_ = options.features;

	/**
	 * listener
	 * 
	 * @type {Array<ol.events.Event>}
	 * @private
	 */
	this.listener_ = [];

	/**
	 * 이벤트 바인딩은 한번만 시행되어야 하므로 이벤트 바인딩을 하였을때 이 멤버변수를 true설정해주어 이미 이벤트 바인딩이 이루어졌음을
	 * handleEvent함수에 알려준다
	 * 
	 * @type {Boolean}
	 * @private
	 */
	this.constructor_ = false;

	/**
	 * The measure tooltip element.
	 * 
	 * @type {Overlay<Element>}
	 * @private
	 */
	this.measureTipElement_ = [];

	/**
	 * Overlay to show the measurement.
	 * 
	 * @type {Array<ol.Overlay>}
	 * @private
	 */
	this.measureTipOverlay_ = [];

	/**
	 * 현재 그리는 중인 feature
	 * 
	 * @type {ol.Feature}
	 * @private
	 */
	this.sketch_;

	/**
	 * measure 수치를 map에 나타내어줄 element와 overlay를 생성한다 두 개를 객체형태로 저장하여 반환한다
	 * 
	 * @type {Function}
	 * @return {Object}
	 * @private
	 */
	this.createMeasureTip_ = function() {
		var map = this.getMap();
		var measureTipElement;
		var measureTipOverlay;

		measureTipElement = document.createElement('div');
		measureTipElement.className = 'tip tip-measure';
		measureTipOverlay = new ol.Overlay({
			element : measureTipElement,
			offset : [ 0, -15 ],
			positioning : 'bottom-center'
		});
		map.addOverlay(measureTipOverlay);

		this.measureTipElement_.push(measureTipElement);
		this.measureTipOverlay_.push(measureTipOverlay);

		return {
			element : measureTipElement,
			overlay : measureTipOverlay
		};
	};
	/**
	 * 인자값으로 <ol.Feature>가 주어진다면 해당 feature에 관련된 element와 overlay만 삭제하고 인자값이
	 * 주어지지 않는다면 this.measureTipElement_와 this.measureTipOverlay_ 멤버변수에 저장되어 있는
	 * 모든 객체 또는 요소가 삭제된다.
	 * 
	 * @type {Function}
	 * @private
	 */
	this.removeMeasureTip_ = function(feature) {
		var element;
		var overlay;
		var length = this.measureTipElement_.length;

		if (feature) {
			element = this.measureTipElement_;
			overlay = this.measureTipOverlay_;

			for (var i = 0; i < length; ++i) {
				if (element[i].id === feature.getId()) {
					element[i].remove();
					element.splice(i, 1);
					break;
				}
			}

			for (var i = 0; i < length; ++i) {
				if (overlay[i].get('id') === feature.getId()) {
					overlay[i].getMap().removeOverlay(overlay[i]);
					overlay.splice(i, 1);
					break;
				}
			}
		} else {
			for (var i = 0; i < length; ++i) {
				element = this.measureTipElement_.pop();
				overlay = this.measureTipOverlay_.pop();

				element.remove();
				overlay.getMap().removeOverlay(overlay);
			}
		}
	};
	// /**
	// * 인자값으로 주어진 폴리곤 객체에 대한 면적을 계산하여 String 형식으로 반환한다
	// *
	// * @type {Function}
	// * @return {String}
	// * @private
	// */
	this.formatArea_ = function(polygon) {
		var area = polygon.getArea();
		var output;
		if (area > 10000) {
			output = (Math.round(area/1000000*100)/100) + ' ' + 'km<sup>2</sup>';
		} else {
			output = (Math.round(area*100)/100) + ' ' + 'm<sup>2</sup>';
		}
		return output;
	};

	/**
	 * 인자값으로 주어진 라인스트링 객체에 대한 길이를 계산하여 String 형식으로 반환한다 MultiLineString 객체일때에는
	 * getLength() 함수가 적용되지 않으므로 우선 LineString객체들을 추출해낸 다음 각 LineString객체들의 길이를
	 * 더하여 반환한다
	 * 
	 * @type {Function}
	 * @return {String}
	 * @private
	 */
	this.formatLength_ = function(line) {
		var length = 0;
		var output;
		if (line instanceof ol.geom.MultiLineString) {
			var lines = line.getLineStrings();
			for (var i = 0; i < lines.length; ++i) {
				length += Math.round(lines[i].getLength()*100)/100;
			}
		} else if (line instanceof ol.geom.LineString) {
			length = Math.round(line.getLength() * 100) / 100;
		} else {
			console.error('not support type');
			return;
		}

		if (length > 100) {
			output = (Math.round(length/1000*100)/100) + ' ' + 'km';
		} else {
			output = (Math.round(length*100)/100) + ' ' + 'm';
		}
		return output;
	};
};
ol.inherits(gb.interaction.MeasureTip, ol.interaction.Interaction);
/**
 * 모든 마우스 이벤트 발생시에 실행되는 함수. this.features_ 멤버변수가 존재한다면 add이벤트와 remove이벤트를 추가로
 * 바인딩 시켜주며 add 이벤트 발생시에는 선택된 feature에 대한 measure tip을 생성하고 remove 이벤트 발생시에는
 * tip을 삭제한다 또한 ol.Collection<ol.interaction> 객체에도 remove 이벤트를 바인딩하여
 * gb.interaction.MeasureTip 객체가 삭제되었을때 모든 element와 overlay들이 삭제되도록 하고 모든 이벤트
 * 리스너를 해제한다.
 * 
 * @param {ol.MapBrowserEvent}
 * @return {Boolean} true가 반환되면 다른 함수들에도 전파된다. false가 반환되면 전파가 중지된다.
 */
gb.interaction.MeasureTip.prototype.handleEvent = function(evt) {
	var that = this;
	var map = evt.map;
	var interactions = map.getInteractions();
	var listen;

	if (!this.constructor_) {
		if (this.features_) {
			var listener;
			// ol.Collection<ol.Feature> 객체에 피쳐가 추가 될때마다 이 이벤트가 실행된다
			// 추가된 피쳐에 measureTip 엘리먼트를 추가한다
			listener = this.features_.on('add', function(evt) {
				that.selectedMeasure(evt.element);
			});

			this.listener_.push(listener);

			listener = this.features_.on('remove', function(evt) {
				that.removeMeasureTip_(evt.element);
			});

			this.listener_.push(listener);
		}
		listen = interactions.on('remove', function(evt) {
			if (evt.element instanceof gb.interaction.MeasureTip) {
				evt.element.removeMeasureTip_();
				ol.Observable.unByKey(evt.element.listener_);
				ol.Observable.unByKey(listen);
			}
		});
		this.constructor_ = true;
	}
	return true;
};

/**
 * ol.interaction.Draw객체의 drawStart 이벤트 발생시 실행되는 함수 내부에 적어넣으면 현재 그리고 있는 feature에
 * 대한 면적 또는 길이를 보여준다
 * 
 * @param {ol.interaction.Draw.Event}
 */
gb.interaction.MeasureTip.prototype.drawStart = function(evt) {
	var that = this;
	var measureTip = this.createMeasureTip_();
	this.sketch_ = evt.feature;
	var tipCoord = evt.coordinate;

	var listener = this.sketch_.getGeometry().on('change', function(evt) {
		var geom = evt.target;
		var output;

		if (geom instanceof ol.geom.Polygon) {
			output = that.formatArea_(geom);
			tipCoord = geom.getLastCoordinate();
		} else if (geom instanceof ol.geom.LineString) {
			output = that.formatLength_(geom);
			tipCoord = geom.getLastCoordinate();
		}
		measureTip.element.innerHTML = output;
		measureTip.overlay.setPosition(tipCoord);
	});
	this.listener_.push(listener);
};

/**
 * ol.interaction.Draw객체의 drawEnd 이벤트 발생시 실행되는 함수 내부에 적어넣으면 생성된 feature에 대한 면적
 * 또는 길이를 해당 객체에 고정시킨다
 * 
 * @param {ol.interaction.Draw.Event}
 */
gb.interaction.MeasureTip.prototype.drawEnd = function(evt) {
	// 배열의 맨마지막에 있는 (가장 최근에 생성되어진) element와 overlay를 가져온다
	var element = this.measureTipElement_[this.measureTipElement_.length - 1];
	var overlay = this.measureTipOverlay_[this.measureTipOverlay_.length - 1];
	var geom = evt.feature.getGeometry();

	if (geom instanceof ol.geom.MultiPolygon) {
		overlay.setPosition(geom.getInteriorPoints().getCoordinates()[0]);
	} else if (geom instanceof ol.geom.Polygon) {
		overlay.setPosition(geom.getInteriorPoints().getCoordinates());
	}

	// drawing이 끝나면 element를 고정시킨다
	element.className = 'tip tip-static';
	overlay.setOffset([ 0, -7 ]);
	this.sketch_ = null;
	// 바인딩한 모든 이벤트를 해제시킨다
	ol.Observable.unByKey(this.listener_);
};

/**
 * 선택된 객체에 대한 면적 또는 길이를 계산하여 보여준다
 * 
 * @param {ol.Feature}
 */
gb.interaction.MeasureTip.prototype.selectedMeasure = function(feature) {
	var output;
	var tipCoord;
	var id = feature.getId();
	var geom = feature.getGeometry();
	var measureTip = this.createMeasureTip_();

	if (geom instanceof ol.geom.Polygon) {
		output = this.formatArea_(geom);
		tipCoord = geom.getInteriorPoint().getCoordinates();
	} else if (geom instanceof ol.geom.MultiPolygon) {
		output = this.formatArea_(geom);
		tipCoord = geom.getInteriorPoints().getCoordinates()[0];
	} else if (geom instanceof ol.geom.LineString || geom instanceof ol.geom.MultiLineString) {
		output = this.formatLength_(geom);
		tipCoord = geom.getLastCoordinate();
	}

	measureTip.element.innerHTML = output;
	measureTip.overlay.setPosition(tipCoord);
	measureTip.element.className = 'tip tip-static';
	measureTip.element.id = id;
	measureTip.overlay.set('id', id);
	measureTip.overlay.setOffset([ 0, -7 ]);
};
