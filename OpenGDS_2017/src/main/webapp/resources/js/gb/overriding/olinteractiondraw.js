/**
 * 드로우 인터렉션에 그리는중에 타입을 바꾸는 함수 추가5
 */
ol.interaction.Draw.prototype.switchType_ = function() {
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
				var circle = opt_geometry ? /** @type {ol.geom.Circle} */
				(opt_geometry) : new ol.geom.Circle([ NaN, NaN ]);
				var squaredLength = ol.coordinate.squaredDistance(coordinates[0], coordinates[1]);
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
						geometry.setCoordinates([ coordinates[0].concat([ coordinates[0][0] ]) ]);
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