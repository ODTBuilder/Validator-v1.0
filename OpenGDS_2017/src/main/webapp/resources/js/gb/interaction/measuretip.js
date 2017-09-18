var gb;
if (!gb)
	gb = {};
if (!gb.interaction)
	gb.interaction = {};

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
