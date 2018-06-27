/**
 * 에디팅 툴 패널 객체를 정의한다.
 * 
 * @class gb.panel.EditingTool
 * @extends gb.panel.Base
 * @memberof gb.panel
 * @param {Object}
 *            obj - 생성자 옵션을 담은 객체
 * @param {Number}
 *            obj.width - 패널의 너비 (픽셀)
 * @param {Number}
 *            obj.height - 패널의 높이 (픽셀)
 * @param {Number}
 *            obj.positionX - 패널의 페이지 왼편으로 부터의 거리 (픽셀)
 * @param {Number}
 *            obj.positionY - 패널의 페이지 상단으로 부터의 거리 (픽셀)
 * @param {Boolean}
 *            obj.autoOpen - 패널이 선언과 동시에 표출 할 것인지 선택
 * @param {gb.edit.FeatureRecord}
 *            obj.featureRecord - 저장하기 전까지 편집 이력을 임시 보관
 * @param {jsTreeCustom}
 *            obj.treeInstance - ol3 레이어 객체를 tree 형태로 표현 및 보관
 * @param {function}
 *            obj.selected - 현재 선택된 레이어를 반환하는 함수
 * @param {String}
 *            obj.getFeatureInfo - WMS 레이어의 feature 정보를 요청하기 위한 URL
 * @param {String}
 *            obj.layerInfo - WMS 레이어의 정보를 요청하기 위한 URL
 * @param {String}
 *            obj.imageTile - WMS 레이어의 이미지 타일을 요청하기 위한 URL
 * @param {String}
 *            obj.getFeature - WFS 레이어의 feature 정보를 요청하기 위한 URL
 * @param {String}
 *            obj.getFeatureInfo - WMS 레이어의 feature 정보를 요청하기 위한 URL
 * @version 0.01
 * @author yijun.so
 * @date 2017. 07.26
 */
gb.panel.EditingTool = function(obj) {
	gb.panel.Base.call(this, obj);
	var options = obj ? obj : {};
	this.map = options.map ? options.map : undefined;
	console.log(this.map.getView().getProjection());
	this.featureRecord = options.featureRecord ? options.featureRecord : undefined;
	this.treeInstance = options.treeInstance ? options.treeInstance : undefined;
	this.selected = options.selected ? options.selected : undefined;
	this.layerInfo = options.layerInfo ? options.layerInfo : undefined;
	this.imageTile = options.imageTile ? options.imageTile : undefined;
	this.getFeature = options.getFeature ? options.getFeature : undefined;
	this.getFeatureInfo = options.getFeatureInfo ? options.getFeatureInfo : undefined;
	this.layers = undefined;
	this.layer = undefined;

	this.snapWMS = [];
	this.snapSource = new ol.source.Vector();

	this.snapVector = new ol.Collection();

	this.features = new ol.Collection();
	this.tempSource = new ol.source.Vector({
		features : this.features
	});
	this.tempVector = new ol.layer.Vector({
		map : this.map,
		source : this.tempSource
	});

	this.managed = new ol.layer.Vector({
		source : this.tempSource
	});
	this.managed.set("name", "temp_vector");
	this.managed.set("id", "temp_vector");

	this.styles = [ new ol.style.Style({
		stroke : new ol.style.Stroke({
			color : 'rgba(0,153,255,1)',
			width : 2
		}),
		fill : new ol.style.Fill({
			color : 'rgba(255, 255, 255, 0.5)'
		})
	}), new ol.style.Style({
		image : new ol.style.Circle({
			radius : 10,
			fill : new ol.style.Fill({
				color : 'rgba(0,153,255,0.4)'
			})
		})
	}) ];

	this.highlightStyles1 = [ new ol.style.Style({
		stroke : new ol.style.Stroke({
			color : 'rgba(255,0,0,1)',
			width : 2
		})
	}), new ol.style.Style({
		image : new ol.style.Circle({
			radius : 10,
			stroke : new ol.style.Stroke({
				color : 'rgba(255,0,0,1)',
				width : 2
			})
		})
	}) ];

	this.highlightStyles2 = [ new ol.style.Style({
		stroke : new ol.style.Stroke({
			color : 'rgba(0, 0, 255, 1)',
			width : 2
		})
	}), new ol.style.Style({
		image : new ol.style.Circle({
			radius : 10,
			stroke : new ol.style.Stroke({
				color : 'rgba(0, 0, 255, 1)',
				width : 2
			})
		})
	}) ];

	this.selectedStyles = [ new ol.style.Style({
		stroke : new ol.style.Stroke({
			color : 'rgba(0,153,255,1)',
			width : 2
		}),
		fill : new ol.style.Fill({
			color : 'rgba(255, 255, 255, 0.5)'
		})
	}), new ol.style.Style({
		image : new ol.style.Circle({
			radius : 10,
			fill : new ol.style.Fill({
				color : 'rgba(0,153,255,0.4)'
			})
		}),
		geometry : function(feature) {

			var coordinates;
			var geom;

			if (feature.getGeometry() instanceof ol.geom.MultiPolygon) {
				coordinates = feature.getGeometry().getCoordinates()[0][0];
				geom = new ol.geom.MultiPoint(coordinates);
			} else if (feature.getGeometry() instanceof ol.geom.Polygon) {
				coordinates = feature.getGeometry().getCoordinates()[0];
				geom = new ol.geom.MultiPoint(coordinates);
			} else if (feature.getGeometry() instanceof ol.geom.MultiLineString) {
				coordinates = feature.getGeometry().getCoordinates()[0];
				geom = new ol.geom.MultiPoint(coordinates);
			} else if (feature.getGeometry() instanceof ol.geom.LineString) {
				coordinates = feature.getGeometry().getCoordinates();
				geom = new ol.geom.MultiPoint(coordinates);
			} else if (feature.getGeometry() instanceof ol.geom.MultiPoint) {
				coordinates = feature.getGeometry().getCoordinates();
				geom = new ol.geom.MultiPoint(coordinates);
			} else if (feature.getGeometry() instanceof ol.geom.Point) {
				coordinates = [ feature.getGeometry().getCoordinates() ];
				geom = new ol.geom.MultiPoint(coordinates);
			}

			return geom;
		}
	}) ];

	this.interval = undefined;
	this.count = 1;

	this.btn = {
		selectBtn : undefined,
		drawBtn : undefined,
		moveBtn : undefined,
		rotateBtn : undefined,
		modiBtn : undefined,
		delBtn : undefined
	};
	this.isOn = {
		select : false,
		draw : false,
		move : false,
		remove : false,
		modify : false,
		rotate : false,
		snap : false
	};
	this.interaction = {
		select : undefined,
		selectWMS : undefined,
		dragbox : undefined,
		draw : undefined,
		updateDraw : undefined,
		move : undefined,
		rotate : undefined,
		modify : undefined,
		snap : undefined,
		remove : undefined
	};

	var that = this;
	var i1 = $("<i>").addClass("fa").addClass("fa-mouse-pointer").attr("aria-hidden", true);
	this.btn.selectBtn = $("<button>").addClass("gb-panel-editingtool-btn").click(function() {
		console.log("click select");
		that.select(that.updateSelected());
	}).append(i1);
	var float1 = $("<div>").css({
		"float" : "left"
	}).append(this.btn.selectBtn);

	var i2 = $("<i>").addClass("fa").addClass("fa-pencil").attr("aria-hidden", true);
	this.btn.drawBtn = $("<button>").addClass("gb-panel-editingtool-btn").click(function() {
		console.log("click draw");
		that.draw(that.updateSelected());
	}).append(i2);
	var float2 = $("<div>").css({
		"float" : "left"
	}).append(this.btn.drawBtn);

	var i3 = $("<i>").addClass("fa").addClass("fa-arrows").attr("aria-hidden", true);
	this.btn.moveBtn = $("<button>").addClass("gb-panel-editingtool-btn").click(function() {
		console.log("click move");
		that.move(that.updateSelected());
	}).append(i3);
	var float3 = $("<div>").css({
		"float" : "left"
	}).append(this.btn.moveBtn);

	var i4 = $("<i>").addClass("fa").addClass("fa-wrench").attr("aria-hidden", true);
	this.btn.modiBtn = $("<button>").addClass("gb-panel-editingtool-btn").click(function() {
		console.log("click modify");
		that.modify(that.updateSelected());
	}).append(i4);
	var float4 = $("<div>").css({
		"float" : "left"
	}).append(this.btn.modiBtn);

	var i5 = $("<i>").addClass("fa").addClass("fa-repeat").attr("aria-hidden", true);
	this.btn.rotateBtn = $("<button>").addClass("gb-panel-editingtool-btn").click(function() {
		console.log("click rotate");
		that.rotate(that.updateSelected());
	}).append(i5);
	var float5 = $("<div>").css({
		"float" : "left"
	}).append(this.btn.rotateBtn);

	var i6 = $("<i>").addClass("fa").addClass("fa-eraser").attr("aria-hidden", true);
	this.btn.delBtn = $("<button>").addClass("gb-panel-editingtool-btn").click(function() {
		console.log("click remove");
		that.remove(that.updateSelected());
	}).append(i6);
	var float6 = $("<div>").css({
		"float" : "left"
	}).append(this.btn.delBtn);

	$(this.panelBody).append(float1).append(float2).append(float3).append(float4).append(float5).append(float6);

	if (this.autoOpen) {
		this.open();
	} else {
		this.close();
	}
	$("body").append(this.panel);

	var fth1 = $("<th>").text("Index");
	var fth2 = $("<th>").text("Description");
	var ftr = $("<tr>").append(fth1).append(fth2);
	var fhd = $("<thead>").append(ftr);
	this.featureTB = $("<tbody>");
	// .mouseleave(function() {
	// that.map.getView().fit(that.tempSelectSource.getExtent(),
	// that.map.getSize());
	// });
	var ftb = $("<table>").addClass("gb-table").append(fhd).append(this.featureTB);

	this.featurePop = new gb.panel.Base({
		"width" : "240px",
		"positionX" : 0,
		"positionY" : 0,
		"autoOpen" : false,
		"body" : ftb
	});

	$(this.featurePop.getPanel()).find(".gb-panel-body").css({
		"max-height" : "300px",
		"overflow-y" : "auto"
	});
	var ath1 = $("<th>").text("Key");
	var ath2 = $("<th>").text("Value");
	var atr = $("<tr>").append(ath1).append(ath2);
	var ahd = $("<thead>").append(atr);
	this.attrTB = $("<tbody>");
	var atb = $("<table>").addClass("gb-table").append(ahd).append(this.attrTB);
	this.attrPop = new gb.panel.Base({
		"width" : "300px",
		"positionX" : 0,
		"positionY" : 0,
		"autoOpen" : false,
		"body" : atb
	});
	// var ahead = $("<div>").addClass("panel-heading").css({
	// "padding" : 0
	// }).append(" ");
	// var alist =
	// $("<div>").addClass("panel-body").addClass("gb-edit-sel-apan").css({
	// "max-height" : "300px",
	// "overflow-y" : "auto"
	// }).append(this.attrTB);
	// this.attrPop = $("<div>").css({
	// "width" : "250px",
	// // "max-height" : "300px",
	// "top" : 0,
	// "right" : 0,
	// "position" : "absolute",
	// "z-Index" : "999",
	// "margin-bottom" : 0
	// }).addClass("panel").addClass("panel-default").append(ahead).append(alist);
	// $("body").append(this.attrPop);
	// $(this.attrPop).hide();
	// $(this.attrPop).draggable({
	// appendTo : "body"
	// });

	this.map.on('postcompose', function(evt) {
		that.map.getInteractions().forEach(function(interaction) {
			if (interaction instanceof gb.interaction.MultiTransform) {
				if (interaction.getFeatures().getLength()) {
					interaction.drawMbr(evt);
				}
			}
		});
	});
	this.map.on('moveend', (function() {
		that.loadSnappingLayer(this.getView().calculateExtent(this.getSize()));
	}));
};
gb.panel.EditingTool.prototype = Object.create(gb.panel.Base.prototype);
gb.panel.EditingTool.prototype.constructor = gb.panel.EditingTool;
/**
 * 피처목록을 생성한다.
 * 
 * @method gb.panel.EditingTool#gb.panel.EditingTool#setFeatureList_
 * @private
 */
gb.panel.EditingTool.prototype.setFeatureList_ = function() {

};
/**
 * 내부 인터랙션 저장 객체를 반환한다.
 * 
 * @method gb.panel.EditingTool#getInteractions_
 * @private
 * @return {Object} 내부 인터랙션 저장 객체
 */
gb.panel.EditingTool.prototype.getInteractions_ = function() {
	return this.interaction;
};
/**
 * 내부 인터랙션 하나를 반환한다.
 * 
 * @method gb.panel.EditingTool#getInteraction_
 * @private
 * @param {String}
 *            반환할 인터랙션
 * @return {ol.interaction.Interaction}
 */
gb.panel.EditingTool.prototype.getInteraction_ = function(key) {
	return this.interaction[key];
};
/**
 * 내부 인터랙션 구조를 설정한다.
 * 
 * @method gb.panel.EditingTool#setInteraction_
 * @private
 * @param {String}
 *            key - 인터랙션 이름
 * @param {ol.interaction.Interaction}
 *            val - 인터랙션 객체
 */
gb.panel.EditingTool.prototype.setInteraction_ = function(key, val) {
	this.interaction[key] = val;
};
/**
 * 편집할 레이어를 설정한다.
 * 
 * @method gb.panel.EditingTool#setLayer
 * @param {ol.layer.Base}
 *            layer - 편집하고자 하는 레이어
 */
gb.panel.EditingTool.prototype.setLayer = function(layer) {
	if (layer instanceof ol.layer.Group) {
		console.error("group layer can not edit");
	} else if (layer instanceof ol.layer.Tile) {
		var git = layer.get("git");
		if (git) {
			console.error("fake parent layer can not edit");
		} else {
			this.layer = layer;
		}
	} else if (layer instanceof ol.layer.Base) {
		this.layer = layer;
	}
};
/**
 * 편집중인 레이어를 반환한다.
 * 
 * @method gb.panel.EditingTool#getLayer
 * @return {ol.layer.Base} 현재 편집중인 레이어
 */
gb.panel.EditingTool.prototype.getLayer = function() {
	return this.layer;
};

/**
 * 인터랙션을 활성화 시킨다.
 * 
 * @method gb.panel.EditingTool#activeIntrct_
 * @param {(String |
 *            String[])} intrct - 활성화 시킬 인터랙션 이름 또는 이름의 배열
 */
gb.panel.EditingTool.prototype.activeIntrct_ = function(intrct) {
	// var that = this;
	// var keys = Object.keys(this.getInteractions_());
	// for (var i = 0; i < keys.length; i++) {
	// if (this.getInteraction_(keys[i])) {
	// this.getInteraction_(keys[i]).setActive(false);
	// }
	// }
	if (Array.isArray(intrct)) {
		for (var j = 0; j < intrct.length; j++) {
			this.getInteraction_(intrct[j]).setActive(true);
			if (intrct[j] === "select" || intrct[j] === "selectWMS" || intrct[j] === "dragbox") {
				this.isOn["select"] = true;
			} else {
				this.isOn[intrct[j]] = true;
			}
		}
	} else if (typeof intrct === "string") {
		this.getInteraction_(intrct).setActive(true);
		if (intrct === "select" || intrct === "selectWMS" || intrct[j] === "dragbox") {
			this.isOn["select"] = true;
		} else {
			this.isOn[intrct] = true;
		}
	}
};
/**
 * 인터랙션을 비활성화 시킨다.
 * 
 * @method gb.panel.EditingTool#deactiveIntrct_
 * @param {(String |
 *            String[])} intrct - 인터랙션의 이름 또는 이름의 배열
 */
gb.panel.EditingTool.prototype.deactiveIntrct_ = function(intrct) {
	if (Array.isArray(intrct)) {
		for (var j = 0; j < intrct.length; j++) {
			if (!!this.interaction[intrct[j]]) {
				this.interaction[intrct[j]].setActive(false);
			}
			if (intrct[j] === "select" || intrct[j] === "selectWMS" || intrct[j] === "dragbox") {
				this.isOn["select"] = false;
				this.featurePop.close();
			} else {
				this.isOn[intrct[j]] = false;
				this.tempVector.setMap(this.map);
				// this.map.removeLayer(this.managed);
			}
		}
	} else if (typeof intrct === "string") {
		if (!!this.interaction[intrct]) {
			this.interaction[intrct].setActive(false);
		}
		if (intrct === "select" || intrct === "selectWMS" || intrct === "dragbox") {
			this.isOn["select"] = false;
		} else {
			this.isOn[intrct] = false;
			this.tempVector.setMap(this.map);
			// this.map.removeLayer(this.managed);
		}
	}
	// this.map.removeLayer(this.managed);
};
/**
 * 버튼을 누른 상태로 스타일을 변경한다.
 * 
 * @method gb.panel.EditingTool#activeBtn_
 * @private
 * @param {String}
 *            btn - 버튼 이름
 */
gb.panel.EditingTool.prototype.activeBtn_ = function(btn) {
	if (!this.btn[btn].hasClass("active")) {
		this.btn[btn].addClass("active");
	}
	var keys = Object.keys(this.btn);
	for (var i = 0; i < keys.length; i++) {
		if (keys[i] !== btn) {
			if (this.btn[keys[i]].hasClass("active")) {
				this.btn[keys[i]].removeClass("active");
			}
		}
	}
};
/**
 * 버튼을 안 누른 상태로 스타일을 변경한다.
 * 
 * @method gb.panel.EditingTool#deactiveBtn_
 * @private
 * @param {String}
 *            btn - 버튼 이름
 */
gb.panel.EditingTool.prototype.deactiveBtn_ = function(btn) {
	if (this.btn[btn].hasClass("active")) {
		this.btn[btn].removeClass("active");
	}
};
/**
 * 피처 선택을 활성화 한다
 * 
 * @method gb.panel.EditingTool#select
 * @param {ol.layer.Base}
 *            layer - 편집할 레이어
 */
gb.panel.EditingTool.prototype.select = function(layer) {
	var that = this;
	if (this.isOn.select) {
		if (!!this.interaction.selectWMS || !!this.interaction.select) {
			this.interaction.select.getFeatures().clear();
			this.deactiveIntrct_([ "dragbox", "select", "selectWMS" ]);
		}
		this.deactiveBtn_("selectBtn");
		this.isOn.select = false;
		return;
	}
	this.map.removeLayer(this.managed);
	var sourceLayer;
	if (Array.isArray(layer)) {
		if (layer.length > 1) {
			console.error("please, select 1 layer");
			return;
		} else if (layer.length < 1) {
			console.error("no selected layer");
			return;
		} else {
			this.setLayer(layer[0]);
		}
	} else if (layer instanceof ol.layer.Base) {
		this.setLayer(layer);
	} else {
		return;
	}
	sourceLayer = this.getLayer();
	if (!sourceLayer instanceof ol.layer.Base) {
		return;
	}
	if (sourceLayer instanceof ol.layer.Base && sourceLayer.get("git").hasOwnProperty("fake")) {
		if (sourceLayer.get("git").fake === "child") {
			var source = sourceLayer.getSource();
			if (!source) {
				this.setWMSSource(sourceLayer);
			}
		}
	}

	if (!this.tempSelectSource) {
		this.tempSelectSource = new ol.source.Vector();
	} else {
		this.tempSelectSource.clear();
	}
	if (this.interaction.select instanceof ol.interaction.Select) {
		this.interaction.select.getFeatures().clear();
	}
	this.map.removeInteraction(this.interaction.select);

	if (sourceLayer instanceof ol.layer.Vector) {
		this.interaction.select = new ol.interaction.Select({
			layers : [ sourceLayer ],
			toggleCondition : ol.events.condition.platformModifierKeyOnly,
			style : this.selectedStyles
		});
	} else if (sourceLayer instanceof ol.layer.Base) {
		this.interaction.select = new ol.interaction.Select({
			layers : [ this.tempVector ],
			toggleCondition : ol.events.condition.platformModifierKeyOnly,
			style : this.selectedStyles
		});
	}
	this.map.addInteraction(this.interaction.select);
	this.map.removeInteraction(this.interaction.dragbox);
	this.interaction.dragbox = new ol.interaction.DragBox({
		condition : ol.events.condition.shiftKeyOnly
	});
	this.map.addInteraction(this.interaction.dragbox);

	this.interaction.selectWMS = new gb.interaction.SelectWMS({
		getFeature : this.getFeature,
		getFeatureInfo : this.getFeatureInfo,
		select : this.interaction.select,
		dragbox : this.interaction.dragbox,
		destination : this.tempVector,
		record : this.featureRecord,
		layer : function() {
			return that.updateSelected();
		}
	});
	this.map.addInteraction(this.interaction.selectWMS);

	this.interaction.dragbox.on('boxend', function() {
		if (that.getLayer() instanceof ol.layer.Vector) {
			that.interaction.select.getFeatures().clear();
			that.getLayer().getSource().forEachFeatureIntersectingExtent(this.getGeometry().getExtent(), function(feature) {
				that.interaction.select.getFeatures().push(feature);
			});
		} else if (that.getLayer() instanceof ol.layer.Layer) {
			that.interaction.select.getFeatures().clear();
			that.tempSource.forEachFeatureIntersectingExtent(this.getGeometry().getExtent(), function(feature) {
				that.interaction.select.getFeatures().push(feature);
			});
			that.interaction.selectWMS.setExtent(this.getGeometry().getExtent());
		}
	});

	this.interaction.select.getFeatures().on("change:length", function(evt) {
		that.features = that.interaction.select.getFeatures();
		$(that.featureTB).empty();
		that.tempSelectSource.clear();
		that.tempSelectSource.addFeatures(that.features.getArray());
		if (that.features.getLength() > 1) {
			that.featurePop.close();
			for (var i = 0; i < that.features.getLength(); i++) {
				var idx = that.features.item(i).getId().substring(that.features.item(i).getId().indexOf(".") + 1);
				var td1 = $("<td>").text(idx);
				var anc = $("<a>").addClass("gb-edit-sel-flist").css("cursor", "pointer").attr({
					"value" : that.features.item(i).getId()
				}).text("Selecting feature").click(function() {
					var feature = that.tempSelectSource.getFeatureById($(this).attr("value"));
					that.count = 1;
					clearInterval(that.interval);
					feature.setStyle(undefined);
					that.interaction.select.getFeatures().clear();
					that.interaction.select.getFeatures().push(feature);
					that.featurePop.close();
					console.log(feature);
				});
				var td2 = $("<td>").append(anc);
				var tr = $("<tr>").append(td1).append(td2).mouseenter(function(evt) {
					var fid = $(this).find("a").attr("value");
					var feature = that.tempSelectSource.getFeatureById(fid);
					feature.setStyle(that.highlightStyles1);
					that.interval = setInterval(function() {
						var val = that.count % 2;
						if (val === 0) {
							feature.setStyle(that.highlightStyles1);
						} else {
							feature.setStyle(that.highlightStyles2);
						}
						that.count++;
					}, 500);
				}).mouseleave(function() {
					var fid = $(this).find("a").attr("value");
					var feature = that.tempSelectSource.getFeatureById(fid);
					that.count = 1;
					clearInterval(that.interval);
					feature.setStyle(undefined);
				});
				$(that.featureTB).append(tr);
			}

			that.featurePop.open();
			that.featurePop.getPanel().position({
				"my" : "left top",
				"at" : "right top",
				"of" : that.getPanel(),
				"collision" : "fit"
			});
			that.attrPop.close();
		} else if (that.features.getLength() === 1) {
			that.featurePop.close();
			$(that.attrTB).empty();
			that.setLayer(that.updateSelected());
			var attrInfo = that.getLayer().get("git").attribute;
			that.feature = that.features.item(0);
			if (attrInfo) {
				var attr = that.features.item(0).getProperties();
				var keys = Object.keys(attrInfo);
				for (var i = 0; i < keys.length; i++) {
					if (keys[i] === "geometry") {
						continue;
					}
					var td1 = $("<td>").text(keys[i]);
					var tform = $("<input>").addClass("gb-edit-sel-alist").attr({
						"type" : "text"
					}).css({
						"width" : "100%",
						"border" : "none"
					}).val(attr[keys[i]]).on("input", function() {
						var attrTemp = attrInfo[$(this).parent().prev().text()];
						console.log(attrTemp.type);
						switch (attrTemp.type) {
						case "String":
							if (that.isString($(this).val()) || ($(this).val() === "")) {
								var obj = {};
								obj[$(this).parent().prev().text()] = $(this).val();
								that.feature.setProperties(obj);
								that.featureRecord.update(that.getLayer(), that.feature);
								console.log("set");
							} else {
								$(this).val("");
							}
							break;
						case "Integer":
							if (that.isInteger($(this).val()) || ($(this).val() === "")) {
								var obj = {};
								obj[$(this).parent().prev().text()] = $(this).val();
								that.feature.setProperties(obj);
								that.featureRecord.update(that.getLayer(), that.feature);
								console.log("set");
							} else {
								$(this).val("");
							}
							break;
						case "Double":
							if (that.isDouble($(this).val()) || ($(this).val() === "")) {
								var obj = {};
								obj[$(this).parent().prev().text()] = $(this).val();
								that.feature.setProperties(obj);
								that.featureRecord.update(that.getLayer(), that.feature);
								console.log("set");
							} else {
								$(this).val("");
							}
							break;
						case "Boolean":
							var valid = [ "t", "tr", "tru", "true", "f", "fa", "fal", "fals", "false" ];
							if (valid.indexOf($(this).val()) !== -1) {
								if (that.isBoolean($(this).val())) {
									var obj = {};
									obj[$(this).parent().prev().text()] = $(this).val();
									that.feature.setProperties(obj);
									that.featureRecord.update(that.getLayer(), that.feature);
									console.log("set");
								}
							} else if ($(this).val() === "") {
								var obj = {};
								obj[$(this).parent().prev().text()] = $(this).val();
								that.feature.setProperties(obj);
								that.featureRecord.update(that.getLayer(), that.feature);
								console.log("set");
							} else {
								$(this).val("");
							}
							break;
						case "Date":
							if ($(this).val().length === 10) {
								if (that.isDate($(this).val())) {
									var obj = {};
									obj[$(this).parent().prev().text()] = $(this).val();
									that.feature.setProperties(obj);
									that.featureRecord.update(that.getLayer(), that.feature);
									console.log("set");
								} else {
									$(this).val("");
								}
							} else if ($(this).val().length > 10) {
								$(this).val("");
							}
							break;
						default:
							break;
						}

					});
					var td2 = $("<td>").append(tform);
					var tr = $("<tr>").append(td1).append(td2);
					that.attrTB.append(tr);
				}
				that.attrPop.open();
				that.attrPop.getPanel().position({
					"my" : "left top",
					"at" : "right top",
					"of" : that.getPanel(),
					"collision" : "fit"
				});
			} else {
				that.attrPop.close();
			}
		} else {
			that.featurePop.close();
			that.attrPop.close();
		}

	});

	this.deactiveIntrct_([ "draw", "move", "rotate", "modify" ]);
	if (sourceLayer instanceof ol.layer.Vector) {
		this.activeIntrct_([ "select", "dragbox" ]);
	} else if (sourceLayer instanceof ol.layer.Base) {
		this.activeIntrct_([ "select", "selectWMS", "dragbox" ]);
	}

	this.isOn.select = true;
	this.activeBtn_("selectBtn");

};
/**
 * 피처 그리기를 활성화 한다
 * 
 * @method gb.panel.EditingTool#draw
 * @param {ol.layer.Base}
 *            layer - 편집할 레이어
 */
gb.panel.EditingTool.prototype.draw = function(layer) {

	if (this.isOn.draw) {
		if (!!this.interaction.draw || !!this.interaction.updateDraw) {
			this.deactiveIntrct_("snap");
			this.deactiveIntrct_("draw");
			this.deactiveBtn_("drawBtn");
			this.map.removeLayer(this.managed);
		}
		return;
	}
	this.map.removeLayer(this.managed);
	var that = this;
	if (this.interaction.select) {
		this.interaction.select.getFeatures().clear();
		this.deactiveIntrct_([ "dragbox", "select", "selectWMS" ]);
	}
	var sourceLayer;
	if (Array.isArray(layer)) {
		if (layer.length > 1) {
			console.error("please, select 1 layer");
			return;
		} else if (layer.length < 1) {
			console.error("no selected layer");
			return;
		} else {
			sourceLayer = layer[0];
		}
	} else if (layer instanceof ol.layer.Base) {
		sourceLayer = layer;
	} else {
		return;
	}

	var git = sourceLayer.get("git");
	if (git.editable === true && sourceLayer instanceof ol.layer.Vector) {
		this.interaction.draw = new ol.interaction.Draw({
			source : sourceLayer.getSource(),
			type : git.geometry
		});
		this.interaction.snap = new ol.interaction.Snap({
			source : this.snapSource
		});
		this.interaction.draw.selectedType = function() {
			var layer = that.updateSelected();
			if (!layer) {
				return;
			}
			var irreGeom = layer.get("git").geometry;
			var geom;
			switch (irreGeom) {
			case "Polyline":
				geom = "LineString";
				break;
			case "LWPolyline":
				geom = "LineString";
				break;
			case "Insert":
				geom = "Point";
				break;
			case "Text":
				geom = "Point";
				break;
			default:
				geom = that.updateSelected().get("git").geometry;
				break;
			}
			return geom;
		};

		this.interaction.draw.on("drawend", function(evt) {
			console.log(evt);
			var layers = that.selected();
			if (layers.length !== 1) {
				return;
			}
			if (that.getLayer().get("id") === layers[0].get("id")) {
				var feature = evt.feature;
				var c = that.featureRecord.getCreated();
				var l = c[that.getLayer().get("id")];
				if (!l) {
					var fid = that.getLayer().get("id") + ".new0";
					feature.setId(fid);
					that.featureRecord.create(layers[0], feature);
				} else {
					var keys = Object.keys(l);
					var count;
					if (keys.length === 0) {
						count = 0;
					} else {
						var id = keys[keys.length - 1];
						var nposit = (id.search(".new")) + 4;
						count = (parseInt(id.substr(nposit, id.length)) + 1);
					}
					var fid = that.getLayer().get("id") + ".new" + count;
					feature.setId(fid);
					that.featureRecord.create(layers[0], feature);
				}
			}
		});
		this.deactiveIntrct_([ "move", "modify", "rotate" ]);
		this.map.addInteraction(this.interaction.draw);
		this.map.addInteraction(this.interaction.snap);
		this.activeIntrct_("draw");
		this.activeIntrct_("snap");
		this.activeBtn_("drawBtn");
	} else if (git.editable === true && sourceLayer instanceof ol.layer.Base) {
		this.map.addLayer(this.managed);

		this.interaction.draw = new ol.interaction.Draw({
			source : this.tempSource,
			type : git.geometry
		});
		this.interaction.snap = new ol.interaction.Snap({
			source : this.snapSource
		});
		this.interaction.draw.selectedType = function() {
			var result = false;
			var layer = that.updateSelected();
			if (layer) {
				result = layer.get("git").geometry;
			}
			return result;
		};
		this.interaction.draw.on("drawend", function(evt) {
			console.log(evt);
			var layers = that.selected();
			if (layers.length !== 1) {
				return;
			}
			if (that.getLayer().get("id") === layers[0].get("id")) {
				var feature = evt.feature;
				var c = that.featureRecord.getCreated();
				var l = c[that.getLayer().get("id")];
				if (!l) {
					var fid = that.getLayer().get("id") + ".new0";
					feature.setId(fid);
					that.featureRecord.create(layers[0], feature);
				} else {
					var keys = Object.keys(l);
					var count;
					if (keys.length === 0) {
						count = 0;
					} else {
						var id = keys[keys.length - 1];
						var nposit = (id.search(".new")) + 4;
						count = (parseInt(id.substr(nposit, id.length)) + 1);
					}
					var fid = that.getLayer().get("id") + ".new" + count;
					feature.setId(fid);
					that.featureRecord.create(layers[0], feature);
				}
			}
		});
		this.deactiveIntrct_([ "move", "modify", "rotate" ]);
		this.map.addInteraction(this.interaction.draw);
		this.map.addInteraction(this.interaction.snap);
		this.activeIntrct_("draw");
		this.activeIntrct_("snap");
		this.activeBtn_("drawBtn");
	}

};
/**
 * 피처 이동을 활성화 한다
 * 
 * @method gb.panel.EditingTool#move
 * @param {ol.layer.Base}
 *            layer - 편집할 레이어
 */
gb.panel.EditingTool.prototype.move = function(layer) {
	if (this.interaction.select === undefined) {
		return;
	}
	if (this.isOn.move) {
		if (this.interaction.move) {
			this.interaction.select.getFeatures().clear();
			this.deactiveIntrct_("move");
			this.deactiveBtn_("moveBtn");
			this.map.removeLayer(this.managed);
		}
		return;
	}
	this.map.removeLayer(this.managed);
	var that = this;
	if (this.interaction.select.getFeatures().getLength() > 0) {
		if (!(layer instanceof ol.layer.Vector)) {
			// if (!this.managed) {
			// this.managed = new ol.layer.Vector({
			// source : this.tempSource
			// });
			// this.managed.set("name", "temp_vector");
			// this.managed.set("id", "temp_vector");
			// }
			this.map.addLayer(this.managed);
		}

		this.interaction.move = new ol.interaction.Translate({
			features : this.interaction.select.getFeatures()
		});
		this.interaction.move.on("translateend", function(evt) {
			console.log(evt);
			var layers = that.selected();
			if (layers.length !== 1) {
				return;
			}
			if (that.getLayer().get("id") === layers[0].get("id")) {
				var features = evt.features;
				for (var i = 0; i < features.getLength(); i++) {
					that.featureRecord.update(layers[0], features.item(i));
				}
			}
		});
		this.deactiveIntrct_([ "select", "selectWMS", "dragbox", "draw", "modify", "rotate" ]);
		this.map.addInteraction(this.interaction.move);
		this.activeIntrct_("move");
		this.activeBtn_("moveBtn");
	} else {
		console.error("select features");
	}
};
/**
 * 피처 멀티편집을 활성화 한다
 * 
 * @method gb.panel.EditingTool#rotate
 * @param {ol.layer.Base}
 *            layer - 편집할 레이어
 */
gb.panel.EditingTool.prototype.rotate = function(layer) {
	if (this.interaction.select === undefined) {
		return;
	}
	if (this.isOn.rotate) {
		if (!!this.interaction.rotate) {
			this.interaction.select.getFeatures().clear();
			this.deactiveIntrct_("rotate");
			this.deactiveBtn_("rotateBtn");
		}
		return;
	}
	this.map.removeLayer(this.managed);
	var that = this;
	if (this.interaction.select.getFeatures().getLength() > 0) {

		if (this.interaction.select.getFeatures().getLength() !== 1) {
			console.error("select 1 feature");
			return;
		}
		// if (!this.managed) {
		// this.managed = new ol.layer.Vector({
		// source : this.tempSource
		// });
		// this.managed.set("name", "temp_vector");
		// this.managed.set("id", "temp_vector");
		// }
		this.map.addLayer(this.managed);
		this.interaction.rotate = new gb.interaction.MultiTransform({
			features : this.interaction.select.getFeatures()
		});
		this.interaction.rotate.on("transformend", function(evt) {
			console.log(evt);
			var layers = that.selected();
			if (layers.length !== 1) {
				return;
			}
			if (that.getLayer().get("id") === layers[0].get("id")) {
				var feature = evt.feature;
				that.featureRecord.update(layers[0], feature);
			}
		});
		this.map.addInteraction(this.interaction.rotate);
		this.deactiveIntrct_([ "select", "selectWMS", "draw", "move", "modify" ]);
		this.activeIntrct_("rotate");
		this.activeBtn_("rotateBtn");
	} else {
		console.error("select features");
	}
};
/**
 * 피처 수정을 활성화 한다
 * 
 * @method gb.panel.EditingTool#modify
 * @param {ol.layer.Base}
 *            layer - 편집할 레이어
 */
gb.panel.EditingTool.prototype.modify = function(layer) {
	if (this.interaction.select === undefined) {
		return;
	}
	if (this.isOn.modify) {
		if (!!this.interaction.modify) {
			this.interaction.select.getFeatures().clear();
			this.deactiveIntrct_("modify");
			this.deactiveBtn_("modiBtn");
			this.map.removeLayer(this.managed);
		}
		return;
	}
	this.map.removeLayer(this.managed);
	var that = this;
	if (this.interaction.select.getFeatures().getLength() > 0) {

		// if (!this.managed) {
		// this.managed = new ol.layer.Vector({
		// source : this.tempSource
		// });
		// this.managed.set("name", "temp_vector");
		// this.managed.set("id", "temp_vector");
		//
		// }
		this.map.addLayer(this.managed);
		this.interaction.modify = new ol.interaction.Modify({
			features : this.interaction.select.getFeatures()
		});
		this.interaction.modify.on("modifyend", function(evt) {
			console.log(evt);
			var layers = that.selected();
			if (layers.length !== 1) {
				return;
			}
			if (that.getLayer().get("id") === layers[0].get("id")) {
				var features = evt.features;
				for (var i = 0; i < features.getLength(); i++) {
					that.featureRecord.update(layers[0], features.item(i));
				}
			}
		});
		this.deactiveIntrct_([ "select", "selectWMS", "dragbox", "draw", "move", "rotate" ]);
		this.map.addInteraction(this.interaction.modify);

		var sourceLayer;
		if (Array.isArray(layer)) {
			if (layer.length > 1) {
				console.error("please, select 1 layer");
				return;
			} else if (layer.length < 1) {
				console.error("no selected layer");
				return;
			} else {
				sourceLayer = layer[0];
			}
		} else if (layer instanceof ol.layer.Base) {
			sourceLayer = layer;
		} else {
			return;
		}
		if (sourceLayer instanceof ol.layer.Vector) {
			this.interaction.snap = new ol.interaction.Snap({
				source : this.snapSource
			});
		} else if (sourceLayer instanceof ol.layer.Base) {
			this.interaction.snap = new ol.interaction.Snap({
				source : this.snapSource
			});
		}
		this.map.addInteraction(this.interaction.snap);
		this.activeIntrct_("snap");

		this.activeIntrct_("modify");
		this.activeBtn_("modiBtn");
	} else {
		console.error("select features");
	}
};
/**
 * 피처를 삭제한다
 * 
 * @method gb.panel.EditingTool#remove
 * @param {ol.layer.Base}
 *            layer - 편집할 레이어
 */
gb.panel.EditingTool.prototype.remove = function(layer) {
	if (this.interaction.select === undefined) {
		return;
	}
	var that = this;
	if (this.interaction.select.getFeatures().getLength() > 0) {
		var layers = that.selected();
		if (layers.length !== 1) {
			return;
		}
		if (that.getLayer().get("id") === layers[0].get("id")) {
			var features = this.interaction.select.getFeatures();
			var fill = new ol.style.Fill({
				color : "rgba(255,0,0,0.01)"
			});

			var stroke = new ol.style.Stroke({
				color : "rgba(255,0,0,0.7)",
				width : 1.25
			});

			var text = new ol.style.Text({});
			var style = new ol.style.Style({
				image : new ol.style.Circle({
					fill : fill,
					stroke : stroke,
					radius : 5
				}),
				fill : fill,
				stroke : stroke
			});
			if (layer instanceof ol.layer.Vector) {
				for (var i = 0; i < features.getLength(); i++) {
					if (features.item(i).getId().search(".new") !== -1) {
						layer.getSource().removeFeature(features.item(i));
					} else {
						features.item(i).setStyle(style);
					}
					that.featureRecord.remove(layers[0], features.item(i));
				}
			} else if (layer instanceof ol.layer.Base) {
				for (var i = 0; i < features.getLength(); i++) {
					if (features.item(i).getId().search(".new") !== -1) {
						this.managed.getSource().removeFeature(features.item(i));
					} else {
						features.item(i).setStyle(style);
					}
					that.featureRecord.remove(layers[0], features.item(i));
				}
			}

		}
		this.interaction.select.getFeatures().clear();

	} else {
		console.error("select features");
	}
};
/**
 * 선택한 레이어를 업데이트한다
 * 
 * @method gb.panel.EditingTool#updateSelected
 * @return {ol.layer.Base} 업데이트한 레이어
 */
gb.panel.EditingTool.prototype.updateSelected = function() {
	var result;
	if (typeof this.selected === "function") {
		this.layers = this.selected();
		if (Array.isArray(this.layers)) {
			var layer = this.layers[0];

			if (layer instanceof ol.layer.Group) {
				console.error("group layer can not edit");
			} else if (layer instanceof ol.layer.Tile) {
				var git = layer.get("git");
				if (git.hasOwnProperty("fake")) {
					if (git.fake === "parent") {
						console.error("fake parent layer can not edit");
					} else {
						this.setLayer(layer);
						result = layer;
					}
				} else {
					this.setLayer(layer);
					result = layer;
				}
			} else if (layer instanceof ol.layer.Vector) {
				this.setLayer(layer);
				result = layer;
			} else if (layer instanceof ol.layer.Base) {
				var source = layer.getSource();
				if (!source) {
					this.setWMSSource(layer);
				}
				this.setLayer(layer);
				result = layer;
			}
		}
	}
	return result;
};
/**
 * 피처를 선택한다
 * 
 * @method gb.panel.EditingTool#setFeatures
 * @param {ol.Feature}
 *            newFeature - 선택할 feature
 */
gb.panel.EditingTool.prototype.setFeatures = function(newFeature) {
	var that = this;
	if (this.isOn.select) {
		if (!!this.interaction.selectWMS || !!this.interaction.select) {
			this.interaction.select.getFeatures().clear();
			this.deactiveIntrct_([ "dragbox", "select", "selectWMS" ]);
		}
		this.deactiveBtn_("selectBtn");
		this.isOn.select = false;
	}
	this.select(this.layer);
	if (newFeature.length === 1) {
		this.interaction.selectWMS.setLayer(this.layer);
		this.interaction.selectWMS.setFeatureId(newFeature[0].getId());
		// this.interaction.select.getFeatures().extend(newFeature);
		this.open();
		this.attrPop.getPanel().position({
			"my" : "left top",
			"at" : "right top",
			"of" : this.getPanel(),
			"collision" : "fit"
		});
	}

};
/**
 * 선택한 피처를 반환한다.
 * 
 * @method gb.panel.EditingTool#getFeatures
 * @return {ol.Collection<ol.Feature>} 선택한 feature들
 */
gb.panel.EditingTool.prototype.getFeatures = function() {
	return this.features;
};
/**
 * 삭제한 레이어에 포함되는 피처를 임시 레이어에서 지운다
 * 
 * @method gb.panel.EditingTool#removeFeatureFromUnmanaged
 * @param {ol.layer.Base}
 *            layer - feature를 삭제한 레이어
 */
gb.panel.EditingTool.prototype.removeFeatureFromUnmanaged = function(layer) {
	var that = this;

	if (layer instanceof ol.layer.Group) {
		var layers = layer.getLayers();
		for (var i = 0; i < layers.getLength(); i++) {
			this.featureRecord.removeByLayer(layers.item(i).get("id"));
			// that.tempVector.setMap(this.map);
			this.removeFeatureFromUnmanaged(layers.item(i));
		}
	} else if (layer instanceof ol.layer.Base) {
		var git = layer.get("git");
		if (git) {
			// git 변수가 있음
			if (git.hasOwnProperty("fake")) {
				// 가짜 레이어임
				if (git["fake"] === "parent") {
					// 가짜 그룹 레이어임
					var layers = git["layers"];
					for (var i = 0; i < layers.getLength(); i++) {
						this.featureRecord.removeByLayer(layers.item(i).get("id"));
						// that.tempVector.setMap(this.map);
						this.removeFeatureFromUnmanaged(layers.item(i));
					}
				} else if (git["fake"] === "child") {
					var layerId = layer.get("id");
					this.tempVector.getSource().forEachFeature(function(feature) {
						var id = feature.getId();
						if (id.indexOf(layerId) !== -1) {
							that.tempVector.getSource().removeFeature(feature);
							// that.tempVector.setMap(this.map);
						}
					});
				}
			} else {
				var layerId = layer.get("id");
				this.tempVector.getSource().forEachFeature(function(feature) {
					var id = feature.getId();
					if (id.indexOf(layerId) !== -1) {
						that.tempVector.getSource().removeFeature(feature);
						// that.tempVector.setMap(this.map);
					}
				});
			}
		} else {
			var layerId = layer.get("id");
			this.tempVector.getSource().forEachFeature(function(feature) {
				var id = feature.getId();
				if (id.indexOf(layerId) !== -1) {
					that.tempVector.getSource().removeFeature(feature);
					// that.tempVector.setMap(this.map);
				}
			});
		}
	}
	that.tempVector.setMap(this.map);
	return;
};

/**
 * 임시 레이어에 있는 피처를 전부 삭제한다.
 * 
 * @method gb.panel.EditingTool#clearUnmanaged
 */
gb.panel.EditingTool.prototype.clearUnmanaged = function() {
	if (this.tempVector instanceof ol.layer.Vector) {
		this.tempVector.clear();
	}
	this.tempVector.setMap(this.map);
	return;
};

/**
 * 패널을 나타낸다.
 * 
 * @override
 * @method gb.panel.EditingTool#open
 */
gb.panel.EditingTool.prototype.open = function() {
	var layer = this.updateSelected();
	if (layer instanceof ol.layer.Group) {
		console.error("group layer can not edit");
	} else if (layer instanceof ol.layer.Tile) {
		var git = layer.get("git");
		if (git.hasOwnProperty("fake")) {
			if (git.fake === "parent") {
				console.error("fake parent layer can not edit");
			} else {
				this.panel.css("display", "block");
			}
		} else {
			this.panel.css("display", "block");
		}
	} else if (layer instanceof ol.layer.Base) {
		this.panel.css("display", "block");
	}

};

/**
 * 베이스 타입 레이어에 소스를 입력한다.
 * 
 * @method gb.panel.EditingTool#setWMSSource
 * @param {ol.layer.Base}
 *            sourceLayer - WMS Source를 입력할 레이어
 * @param {function}
 *            [callback] - ol.source.Source에 정보를 넣기 위한 함수
 */
gb.panel.EditingTool.prototype.setWMSSource = function(sourceLayer, callback) {
	var that = this;
	if (sourceLayer instanceof ol.layer.Vector || sourceLayer instanceof ol.layer.Group) {
		return;
	}
	var arr = {
		"geoLayerList" : [ sourceLayer.get("id") ]
	}
	var names = [];
	// console.log(JSON.stringify(arr));

	$.ajax({
		url : this.layerInfo,
		method : "POST",
		contentType : "application/json; charset=UTF-8",
		cache : false,
		data : JSON.stringify(arr),
		beforeSend : function() { // 호출전실행
			$("body").css("cursor", "wait");
		},
		traditional : true,
		success : function(data2, textStatus, jqXHR) {
			console.log(data2);
			if (Array.isArray(data2)) {
				for (var i = 0; i < 1; i++) {
					var source = new ol.source.TileWMS({
						url : this.imageTile,
						params : {
							'LAYERS' : data2[i].lName,
							'TILED' : true,
							'FORMAT' : 'image/png8',
							'VERSION' : '1.1.0',
							'CRS' : that.getMap().getView().getProjection().getCode(),
							'SRS' : that.getMap().getView().getProjection().getCode(),
							'BBOX' : data2[i].nbBox.minx.toString() + "," + data2[i].nbBox.miny.toString() + ","
									+ data2[i].nbBox.maxx.toString() + "," + data2[i].nbBox.maxy.toString()
						},
						serverType : 'geoserver'
					});
					sourceLayer.setSource(source);
					var ogit = sourceLayer.get("git");
					ogit["attribute"] = data2[i].attInfo;
					ogit["geometry"] = data2[i].geomType;
					var getPosition = function(str, subString, index) {
						return str.split(subString, index).join(subString).length;
					};
					var id = sourceLayer.get("id");
					var format = id.substring((getPosition(id, "_", 1) + 1), getPosition(id, "_", 2));
					var layer;
					if (format === "ngi") {
						layer = new gb.layer.LayerInfo({
							name : sourceLayer.get("name"),
							id : id,
							format : format,
							epsg : data2[i].srs,
							mbound : [ [ data2[i].nbBox.minx.toString(), data2[i].nbBox.miny.toString() ],
									[ data2[i].nbBox.maxx.toString(), data2[i].nbBox.maxy.toString() ] ],
							lbound : [ [ 122.71, 28.6 ], [ 134.28, 40.27 ] ],
							isNew : false,
							geometry : id.substring(getPosition(id, "_", 4) + 1),
							sheetNum : id.substring((getPosition(id, "_", 2) + 1), getPosition(id, "_", 3))
						});
					} else if (format === "dxf") {
						layer = new gb.layer.LayerInfo({
							name : sourceLayer.get("name"),
							id : id,
							format : format,
							epsg : data2[i].srs,
							mbound : [ [ data2[i].nbBox.minx.toString(), data2[i].nbBox.miny.toString() ],
									[ data2[i].nbBox.maxx.toString(), data2[i].nbBox.maxy.toString() ] ],
							isNew : false,
							lbound : [ [ 122.71, 28.6 ], [ 134.28, 40.27 ] ],
							isNew : false,
							geometry : id.substring(getPosition(id, "_", 4) + 1),
							sheetNum : id.substring((getPosition(id, "_", 2) + 1), getPosition(id, "_", 3))
						});
					} else if (format === "shp") {
						layer = new gb.layer.LayerInfo({
							name : sourceLayer.get("name"),
							id : id,
							format : format,
							epsg : data2[i].srs,
							mbound : [ [ data2[i].nbBox.minx.toString(), data2[i].nbBox.miny.toString() ],
									[ data2[i].nbBox.maxx.toString(), data2[i].nbBox.maxy.toString() ] ],
							lbound : [ [ 122.71, 28.6 ], [ 134.28, 40.27 ] ],
							isNew : false,
							geometry : id.substring(getPosition(id, "_", 4) + 1),
							sheetNum : id.substring((getPosition(id, "_", 2) + 1), getPosition(id, "_", 3))
						});
					}
					ogit["information"] = layer;
					console.log(ogit["attribute"]);
					console.log("source injected");
					if (typeof callback === "function") {
						callback(source);
					}
				}
				$("body").css("cursor", "default");
			}
		}
	});
};
/**
 * ol.Map을 입력한다.
 * 
 * @method gb.panel.EditingTool#setMap
 * @param {ol.Map}
 *            map - 편집할 레이어를 포함하는 ol.Map
 */
gb.panel.EditingTool.prototype.setMap = function(map) {
	this.map = map;
}
/**
 * ol.Map을 반환한다.
 * 
 * @method gb.panel.EditingTool#getMap
 * @return {ol.Map} 현재 편집중인 레이어가 포함된 ol.Map
 */
gb.panel.EditingTool.prototype.getMap = function() {
	return this.map;
}
/**
 * String인지 검사한다.
 * 
 * @method gb.panel.EditingTool#isString
 * @param {*}
 *            va - String 인지 검사할 변수
 * @return {Boolean} String 여부
 */
gb.panel.EditingTool.prototype.isString = function(va) {
	var result = false;
	if (typeof va === "string") {
		result = true;
	}
	return result;
}
/**
 * Integer인지 검사한다.
 * 
 * @method gb.panel.EditingTool#isString
 * @param {*}
 *            va - Integer 인지 검사할 변수
 * @return {Boolean} Integer 여부
 */
gb.panel.EditingTool.prototype.isInteger = function(va) {
	var result = false;
	if (va == parseInt(va)) {
		result = true;
	}
	return result;
}
/**
 * Double인지 검사한다.
 * 
 * @method gb.panel.EditingTool#isDouble
 * @param {*}
 *            va - Double 인지 검사할 변수
 * @return {Boolean} Double 여부
 */
gb.panel.EditingTool.prototype.isDouble = function(va) {
	var result = false;
	if (typeof va === "string") {
		var p = parseFloat(va);
		if (!isNaN(p)) {
			result = true;
		}
	}
	return result;
}
/**
 * Boolean인지 검사한다.
 * 
 * @method gb.panel.EditingTool#isBoolean
 * @param {*}
 *            va - Boolean 인지 검사할 변수
 * @return {Boolean} Boolean 여부
 */
gb.panel.EditingTool.prototype.isBoolean = function(va) {
	var result = false;
	if (va == "true" || va == "false") {
		result = true;
	}
	return result;
}
/**
 * Date인지 검사한다.
 * 
 * @method gb.panel.EditingTool#isDate
 * @param {*}
 *            va - Date 인지 검사할 변수
 * @return {Boolean} Date 여부
 */
gb.panel.EditingTool.prototype.isDate = function(va) {
	var result = false;
	if (typeof va === "string") {
		if ((new Date(va)).getTime() > 0) {
			result = true;
		}
	}
	return result;
}
/**
 * 스냅핑 레이어를 설정한다.
 * 
 * @method gb.panel.EditingTool#addSnappingLayer
 * @param {ol.layer.Base}
 *            layer - Snapping을 활성화 할 레이어
 * @return {Boolean} Snapping 활성화 여부
 */
gb.panel.EditingTool.prototype.addSnappingLayer = function(layer) {
	var success = false;
	if (layer instanceof ol.layer.Group) {
		var layers = layer.getLayers();
		for (var i = 0; i < layers.getLength(); i++) {
			this.addSnappingLayer(layers.item(i));
		}
		success = true;
	} else if (layer instanceof ol.layer.Vector) {
		for (var i = 0; i < this.snapVector.getLength(); i++) {
			if (this.snapVector.item(i).get("id") === layer.get("id")) {
				success = true;
				break;
			}
		}
		if (!success) {
			this.snapVector.push(layer);
			success = true;
		}
	} else if (layer instanceof ol.layer.Tile) {
		if (this.snapWMS.indexOf(layer.get("id")) === -1) {
			this.snapWMS.push(layer.get("id"));
			success = true;
		}
	} else if (layer instanceof ol.layer.Layer) {
		var git = layer.get("git");
		if (git) {
			if (git.hasOwnProperty("fake")) {
				if (git.fake === "child") {
					if (this.snapWMS.indexOf(layer.get("id")) === -1) {
						this.snapWMS.push(layer.get("id"));
						success = true;
					}
				}
			}
		}
	}
	return success;
}
/**
 * 스냅핑 레이어를 삭제한다.
 * 
 * @method gb.panel.EditingTool#addSnappingLayer
 * @param {ol.layer.Base}
 *            layer - Snapping을 비활성화 할 레이어
 * @return {Boolean} Snapping 비활성화 여부
 */
gb.panel.EditingTool.prototype.removeSnappingLayer = function(layer) {
	var that = this;
	var success = false;
	if (layer instanceof ol.layer.Group) {
		var layers = layer.getLayers();
		for (var i = 0; i < layers.getLength(); i++) {
			this.removeSnappingLayer(layers.item(i));
		}
		success = true;
	} else if (layer instanceof ol.layer.Vector) {
		for (var i = 0; i < this.snapVector.getLength(); i++) {
			if (this.snapVector.item(i).get("id") === layer.get("id")) {
				this.snapVector.removeAt(i);
				success = true;
				break;
			}
		}
	} else if (layer instanceof ol.layer.Tile) {
		if (this.snapWMS.indexOf(layer.get("id")) !== -1) {
			this.snapWMS.splice(this.snapWMS.indexOf(layer.get("id")), 1);
			success = true;
		}
	} else if (layer instanceof ol.layer.Layer) {
		var git;
		if (layer) {
			git = layer.get("git");
		}
		if (!!git) {
			if (git.hasOwnProperty("fake")) {
				if (git.fake === "child") {
					if (this.snapWMS.indexOf(layer.get("id")) !== -1) {
						this.snapWMS.splice(this.snapWMS.indexOf(layer.get("id")), 1);
						success = true;
					}
				}
			} else {
				if (this.snapWMS.indexOf(layer.get("id")) !== -1) {
					this.snapWMS.splice(this.snapWMS.indexOf(layer.get("id")), 1);
					success = true;
				}
			}
		} else {
			if (this.snapWMS.indexOf(layer.get("id")) !== -1) {
				this.snapWMS.splice(this.snapWMS.indexOf(layer.get("id")), 1);
				success = true;
			}
		}
	}
	return success;
}
/**
 * 스냅핑 레이어를 로드한다.
 * 
 * @method gb.panel.EditingTool#loadSnappingLayer
 * @param {Number[]}
 *            extent - Snapping 하기위한 현재 화면 영역
 */
gb.panel.EditingTool.prototype.loadSnappingLayer = function(extent) {
	var that = this;
	if (this.getMap().getView().getZoom() >= 14) {
		that.snapSource.clear();
		if (that.snapWMS.length > 0) {
			var params = {
				"service" : "WFS",
				"version" : "1.0.0",
				"request" : "GetFeature",
				"typeName" : this.snapWMS.toString(),
				"outputformat" : "text/javascript",
				"bbox" : extent.toString(),
				"format_options" : "callback:getJson"
			};

			$.ajax({
				url : this.getFeature,
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
					var features = new ol.format.GeoJSON().readFeatures(JSON.stringify(data));
					if (that.interaction.snap instanceof ol.interaction.Snap) {
						that.snapSource.addFeatures(features);
					}
					console.log("snap feature injected");
				}
			});
		}
		if (this.snapVector.getLength() > 0) {
			for (var i = 0; i < this.snapVector.getLength(); i++) {
				this.snapVector.item(i).getSource().forEachFeatureIntersectingExtent(extent, function(feature) {
					that.snapSource.addFeature(feature);
				});
			}
		}
		if (this.tempSource.getFeatures().length > 0) {
			this.tempSource.forEachFeatureIntersectingExtent(extent, function(feature) {
				var lid = feature.getId().substring(0, feature.getId().indexOf("."));
				if (that.snapWMS.indexOf(lid) !== -1) {
					that.snapSource.addFeature(feature);
				}
			});
		}
	}
};
/**
 * 해당 레이어로 화면을 확대한다.
 * 
 * @method gb.panel.EditingTool#zoomToFit
 * @param {ol.layer.Base}
 *            layer - 화면을 확대할 레이어
 */
gb.panel.EditingTool.prototype.zoomToFit = function(layer) {
	var that = this;
	if (layer instanceof ol.layer.Group) {
		var extent = ol.extent.createEmpty();
		layer.getLayers().forEach(function(layer2) {
			if (layer2.getSource() instanceof ol.source.TileWMS) {
				var param = layer2.getSource().getParams();
				var keys = Object.keys(param);
				var bbx = "bbox";
				for (var i = 0; i < keys.length; i++) {
					if (keys[i].toLowerCase() === bbx.toLowerCase()) {
						var bbox = param[keys[i]].split(",");
						ol.extent.extend(extent, bbox);
						break;
					}
				}
			} else if (layer2.source instanceof ol.source.Vector) {
				ol.extent.extend(extent, layer2.getSource().getExtent());
			}
		});
		this.getMap().getView().fit(extent, this.getMap().getSize());
	} else if (layer instanceof ol.layer.Vector) {
		var view = this.getMap().getView();
		view.fit(source.getExtent(), this.getMap().getSize());
	} else if (layer instanceof ol.layer.Tile) {
		var source = layer.getSource();
		if (source instanceof ol.source.TileWMS) {
			var param = source.getParams();
			var keys = Object.keys(param);
			var bbx = "bbox";
			for (var i = 0; i < keys.length; i++) {
				if (keys[i].toLowerCase() === bbx.toLowerCase()) {
					var bbox = param[keys[i]].split(",");
					var view = this.getMap().getView();
					view.fit(bbox, this.getMap().getSize());
					break;
				}
			}
		}
	} else if (layer instanceof ol.layer.Layer) {
		var source = layer.getSource();
		var func = function(src) {
			var param = src.getParams();
			var keys = Object.keys(param);
			var bbx = "bbox";
			for (var i = 0; i < keys.length; i++) {
				if (keys[i].toLowerCase() === bbx.toLowerCase()) {
					var bbox = param[keys[i]].split(",");
					var view = that.getMap().getView();
					view.fit(bbox, that.getMap().getSize());
					break;
				}
			}
		};
		if (typeof source === "undefined" || source === null) {
			this.setWMSSource(layer, func);
		} else if (source instanceof ol.source.TileWMS) {
			func(source);
		}
	}
	return;
};