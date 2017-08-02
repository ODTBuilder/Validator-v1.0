/**
 * 편집툴 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2017. 04
 * @version 0.01
 */
var gitbuilder;
if (!gitbuilder)
	gitbuilder = {};
if (!gitbuilder.ui)
	gitbuilder.ui = {};
gitbuilder.ui.EditingTool = $.widget("gitbuilder.editingtool", {
	window : undefined,
	options : {
		geoserverURL : undefined,
		map : undefined,
		selected : undefined,
		appendTo : "body",
		featureRecord : undefined,
		url : undefined
	},
	map : undefined,
	layers : undefined,
	layer : undefined,
	isOn : {
		select : false,
		draw : false,
		move : false,
		remove : false,
		modify : false,
		rotate : false
	},
	interaction : {
		select : undefined,
		selectWMS : undefined,
		dragbox : undefined,
		draw : undefined,
		updateDraw : undefined,
		move : undefined,
		rotate : undefined,
		modify : undefined,
		remove : undefined
	},
	btn : {
		selectBtn : undefined,
		drawBtn : undefined,
		moveBtn : undefined,
		rotateBtn : undefined,
		modiBtn : undefined,
		delBtn : undefined
	},
	features : undefined,
	tempSource : undefined,
	tempVector : undefined,
	managed : undefined,
	featurePop : undefined,
	featureTB : undefined,
	mouseX : undefined,
	mouseY : undefined,
	tempSelectSource : undefined,
	attrPop : undefined,
	attrTB : undefined,
	feature : undefined,
	_create : function() {
		var that = this;
		$(document).mousemove(function(e) {
			that.mouseX = e.pageX;
			that.mouseY = e.pageY;
		});
		this.map = this.options.map;

		this.featureTB = $("<table>").addClass("table").addClass("table-hover").addClass("table-condensed").css({
			"margin-bottom" : 0,
			"table-layout" : "fixed"
		});
		var fhead = $("<div>").addClass("panel-heading").css({
			"padding" : 0
		}).append("　");
		var flist = $("<div>").addClass("panel-body").addClass("gb-edit-sel-fpan").css({
			"max-height" : "300px",
			"overflow-y" : "auto"
		}).append(this.featureTB);
		this.featurePop = $("<div>").css({
			"width" : "250px",
			// "max-height" : "300px",
			"top" : 0,
			"right" : 0,
			"position" : "absolute",
			"z-Index" : "999",
			"margin-bottom" : 0
		}).addClass("panel").addClass("panel-default").append(fhead).append(flist);
		$("body").append(this.featurePop);
		$(this.featurePop).hide();
		$(this.featurePop).draggable({
			appendTo : "body",
			containment : "#" + that.map.getTarget()
		});

		this.attrTB = $("<table>").addClass("table").addClass("table-hover").addClass("table-condensed").css({
			"margin-bottom" : 0,
			"table-layout" : "fixed"
		});
		var ahead = $("<div>").addClass("panel-heading").css({
			"padding" : 0
		}).append("　");
		var alist = $("<div>").addClass("panel-body").addClass("gb-edit-sel-apan").css({
			"max-height" : "300px",
			"overflow-y" : "auto"
		}).append(this.attrTB);
		this.attrPop = $("<div>").css({
			"width" : "250px",
			// "max-height" : "300px",
			"top" : 0,
			"right" : 0,
			"position" : "absolute",
			"z-Index" : "999",
			"margin-bottom" : 0
		}).addClass("panel").addClass("panel-default").append(ahead).append(alist);
		$("body").append(this.attrPop);
		$(this.attrPop).hide();
		$(this.attrPop).draggable({
			appendTo : "body",
			containment : "#" + that.map.getTarget()
		});

		this.features = new ol.Collection();
		this.tempSource = new ol.source.Vector({
			features : this.features
		});
		this.tempVector = new ol.layer.Vector({
			map : this.map,
			source : this.tempSource
		});
		// this.tempVector.set("type", "Vector");
		// this.tempVector.set("name", "temp_vector");
		// this.tempVector.set("id", "temp_vector");
		this._on(false, this.element, {
			click : function(event) {
				if (event.target === that.element[0]) {
					that.open();
				}
			}
		});

		$(document).on("click", ".gitbuilder-editingtool-sel", function() {
			// var git = that.layer.get("git");
			console.log("click select");
			that.select(that.updateSelected());
		});
		$(document).on("click", ".gitbuilder-editingtool-dra", function() {
			// var git = that.layer.get("git");
			// console.log(that.layer);
			that.draw(that.updateSelected());
		});
		$(document).on("click", ".gitbuilder-editingtool-mov", function() {
			// var git = that.layer.get("git");
			// console.log(that.layer);
			var layer = that.options.selected();
			that.move(layer);
		});
		$(document).on("click", ".gitbuilder-editingtool-rot", function() {
			// var git = that.layer.get("git");
			// console.log(that.layer);
			var layer = that.options.selected();
			that.rotate(layer);
		});
		$(document).on("click", ".gitbuilder-editingtool-mod", function() {
			// var git = that.layer.get("git");
			// console.log(that.layer);
			var layer = that.options.selected();
			that.modify(layer);
		});
		$(document).on("click", ".gitbuilder-editingtool-del", function() {
			// var git = that.layer.get("git");
			// console.log(that.layer);
			var layer = that.options.selected();
			that.remove(layer);
		});

		var i1 = $("<i>").addClass("fa").addClass("fa-mouse-pointer").attr("aria-hidden", true);
		this.btn.selectBtn = $("<button>").css({
			"width" : "40px",
			"height" : "40px",
			"margin" : "5px 5px 0 5px"
		}).addClass("btn").addClass("btn-default").addClass("gitbuilder-editingtool-sel").append(i1);
		var float1 = $("<div>").css({
			"float" : "left"
		}).append(this.btn.selectBtn);

		var i2 = $("<i>").addClass("fa").addClass("fa-pencil").attr("aria-hidden", true);
		this.btn.drawBtn = $("<button>").css({
			"width" : "40px",
			"height" : "40px",
			"margin" : "5px 5px 0 5px"
		}).addClass("btn").addClass("btn-default").addClass("gitbuilder-editingtool-dra").append(i2);
		var float2 = $("<div>").css({
			"float" : "left"
		}).append(this.btn.drawBtn);

		var i3 = $("<i>").addClass("fa").addClass("fa-arrows").attr("aria-hidden", true);
		this.btn.moveBtn = $("<button>").css({
			"width" : "40px",
			"height" : "40px",
			"margin" : "5px 5px 0 5px"
		}).addClass("btn").addClass("btn-default").addClass("gitbuilder-editingtool-mov").append(i3);
		var float3 = $("<div>").css({
			"float" : "left"
		}).append(this.btn.moveBtn);

		var i4 = $("<i>").addClass("fa").addClass("fa-repeat").attr("aria-hidden", true);
		this.btn.rotateBtn = $("<button>").css({
			"width" : "40px",
			"height" : "40px",
			"margin" : "5px 5px 0 5px"
		}).addClass("btn").addClass("btn-default").addClass("gitbuilder-editingtool-rot").append(i4);
		var float4 = $("<div>").css({
			"float" : "left"
		}).append(this.btn.rotateBtn);

		var i5 = $("<i>").addClass("fa").addClass("fa-wrench").attr("aria-hidden", true);
		this.btn.modiBtn = $("<button>").css({
			"width" : "40px",
			"height" : "40px",
			"margin" : "5px 5px 0 5px"
		}).addClass("btn").addClass("btn-default").addClass("gitbuilder-editingtool-mod").append(i5);
		var float5 = $("<div>").css({
			"float" : "left"
		}).append(this.btn.modiBtn);

		var i6 = $("<i>").addClass("fa").addClass("fa-eraser").attr("aria-hidden", true);
		this.btn.delBtn = $("<button>").css({
			"width" : "40px",
			"height" : "40px",
			"margin" : "5px 5px 5px 5px"
		}).addClass("btn").addClass("btn-default").addClass("gitbuilder-editingtool-del").append(i6);
		var float6 = $("<div>").css({
			"float" : "left"
		}).append(this.btn.delBtn);

		var pbd = $("<div>").css({
			"padding" : 0
		}).addClass("panel-body").append(float1).append(float2).append(float3).append(float4).append(float5).append(float6);
		var phd = $("<div>").css("padding", 0).addClass("panel-heading").text("　");
		var pdf = $("<div>").addClass("panel").addClass("panel-default").append(phd).append(pbd);
		this.window = $("<div>").css({
			"width" : "50px",
			// "height" : "400px",
			"top" : "100px",
			"right" : 0,
			"position" : "absolute",
			"z-Index" : "999",
		}).append(pdf);

		$("body").append(this.window);
		$(this.window).hide();
		$(this.window).draggable({
			appendTo : "body",
			containment : "#" + that.map.getTarget()
		});

		$(document).on("mouseover", ".gb-edit-sel-flist", function() {
			var feature = that.tempSelectSource.getFeatureById($(this).text());
			that.map.getView().fit(feature.getGeometry().getExtent(), that.map.getSize());
		});
		$(document).on("click", ".gb-edit-sel-flist", function() {
			var feature = that.tempSelectSource.getFeatureById($(this).text());
			that.interaction.select.getFeatures().clear();
			that.interaction.select.getFeatures().push(feature);
			console.log(feature);
		});
		$(document).on("mouseleave", ".gb-edit-sel-fpan", function() {
			that.map.getView().fit(that.tempSelectSource.getExtent(), that.map.getSize());
		});
		$(document).on("input", ".gb-edit-sel-alist", function() {
			var obj = {};
			obj[$(this).parent().prev().text()] = $(this).val();
			that.feature.setProperties(obj);
			that.options.featureRecord.update(that.getLayer(), that.feature);
		});

		this.map.on('postcompose', function(evt) {
			that.map.getInteractions().forEach(function(interaction) {
				if (interaction instanceof gb.interaction.MultiTransform) {
					if (interaction.getFeatures().getLength()) {
						interaction.drawMbr(evt);
					}
				}
			});
		});

	},
	_init : function() {
		var that = this;
	},
	setLayer : function(layer) {
		this.layer = layer;
	},
	getLayer : function() {
		return this.layer;
	},
	setFeatures : function(newFeature) {
		var that = this;
		if (this.isOn.select) {
			if (!!this.interaction.selectWMS || !!this.interaction.select) {
				this.interaction.select.getFeatures().clear();
				this.deactiveIntrct([ "dragbox", "select", "selectWMS" ]);
			}
			this.deactiveBtn("selectBtn");
			this.isOn.select = false;
		}
		// var sourceLayer;
		// if (Array.isArray(layer)) {
		// if (layer.length > 1) {
		// console.error("please, select 1 layer");
		// return;
		// } else if (layer.length < 1) {
		// console.error("no selected layer");
		// return;
		// } else {
		// sourceLayer = layer[0];
		// }
		// } else if (layer instanceof ol.layer.Tile) {
		// sourceLayer = layer;
		// } else {
		// return;
		// }

		if (!this.tempSelectSource) {
			this.tempSelectSource = new ol.source.Vector();
		} else {
			this.tempSelectSource.clear();
		}
		if (this.interaction.select instanceof ol.interaction.Select) {
			this.interaction.select.getFeatures().clear();
		}
		this.map.removeInteraction(this.interaction.select);
		var styles = [ new ol.style.Style({
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
		this.interaction.select = new ol.interaction.Select({
			layers : [ this.tempVector ],
			toggleCondition : ol.events.condition.platformModifierKeyOnly,
			style : styles
		});
		this.map.addInteraction(this.interaction.select);
		this.map.removeInteraction(this.interaction.dragbox);

		this.interaction.select.getFeatures().on("change:length", function(evt) {
			that.features = that.interaction.select.getFeatures();
			$(that.featureTB).empty();
			that.tempSelectSource.clear();
			that.tempSelectSource.addFeatures(that.features.getArray());
			if (that.features.getLength() > 1) {
				$(that.featurePop).hide();
				for (var i = 0; i < that.features.getLength(); i++) {
					var anc = $("<a>").addClass("gb-edit-sel-flist").css("cursor", "pointer").attr({
						"title" : that.features.item(i).getId()
					}).text(that.features.item(i).getId());
					var td = $("<td>").css({
						"text-overflow" : "ellipsis",
						"overflow" : "hidden",
						"text-align" : "right"
					}).append(anc);
					var tr = $("<tr>").append(td);
					$(that.featureTB).append(tr);
				}

				$(that.featurePop).show();
				$(that.featurePop).position({
					"my" : "right center",
					"at" : "right center",
					"of" : document,
					"collision" : "fit"
				});
				$(that.attrPop).hide();
			} else if (that.features.getLength() === 1) {
				$(that.featurePop).hide();
				$(that.attrTB).empty();
				that.setLayer(that.updateSelected());
				var attrInfo = that.getLayer().get("git").attribute;
				that.feature = that.features.item(0);
				if (!!attrInfo) {
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
						}).val(attr[keys[i]]);
						var td2 = $("<td>").append(tform);
						var tr = $("<tr>").append(td1).append(td2);
						that.attrTB.append(tr);
					}
					$(that.attrPop).show();
					$(that.attrPop).position({
						"my" : "right bottom",
						"at" : "right bottom+100",
						"of" : document,
						"collision" : "fit"
					});
				}
			} else {
				$(that.featurePop).hide();
				$(that.attrPop).hide();
			}
		});

		var ids = [];
		for (var i = 0; i < newFeature.length; i++) {
			ids.push(newFeature[i].getId());
		}
		that.tempSource.addFeatures(newFeature);
		that.tempVector.setMap(that.map);

		var selFeatures = that.interaction.select.getFeatures();
		var cFeatures = [];
		for (var k = 0; k < selFeatures.getLength(); k++) {
			if (selFeatures.item(k).getId().indexOf(".new") !== -1) {
				cFeatures.push(selFeatures.item(k));
			}
			// else {
			// if (!that.record.isRemoved(that.layer, selFeatures.item(k)))
			// {
			// cFeatures.push(selFeatures.item(k));
			// }
			// }
		}
		that.interaction.select.getFeatures().clear();
		that.interaction.select.getFeatures().extend(cFeatures);
		var newFeatures = [];
		for (var j = 0; j < ids.length; j++) {
			if (!that.options.featureRecord.isRemoved(ids[j].substring(0, ids[j].indexOf(".")), that.tempSource.getFeatureById(ids[j]))) {
				newFeatures.push(that.tempSource.getFeatureById(ids[j]));
			}
		}
		that.interaction.select.getFeatures().extend(newFeatures);

		this.activeIntrct([ "select" ]);
		this.isOn.select = true;
		this.activeBtn("selectBtn");
		this.deactiveIntrct([ "move", "rotate" ]);
		this.open();
	},
	getFeatures : function() {
		return this.features;
	},
	activeIntrct : function(intrct) {
		// var that = this;
		var keys = Object.keys(this.interaction);
		for (var i = 0; i < keys.length; i++) {
			this.interaction[keys[i]].setActive(false);
		}
		if (Array.isArray(intrct)) {
			for (var j = 0; j < intrct.length; j++) {
				this.interaction[intrct[j]].setActive(true);
				if (intrct[j] === "select" || intrct[j] === "selectWMS" || intrct[j] === "dragbox") {
					this.isOn["select"] = true;
				} else {
					this.isOn[intrct[j]] = true;
				}
			}
		} else if (typeof intrct === "string") {
			this.interaction[intrct].setActive(true);
			if (intrct === "select" || intrct === "selectWMS" || intrct[j] === "dragbox") {
				this.isOn["select"] = true;
			} else {
				this.isOn[intrct] = true;
			}
		}
	},
	deactiveIntrct : function(intrct) {
		if (Array.isArray(intrct)) {
			for (var j = 0; j < intrct.length; j++) {
				if (!!this.interaction[intrct[j]]) {
					this.interaction[intrct[j]].setActive(false);
				}
				if (intrct[j] === "select" || intrct[j] === "selectWMS") {
					this.isOn["select"] = false;
					$(this.featurePop).hide();
				} else {
					this.isOn[intrct[j]] = false;
					this.tempVector.setMap(this.map);
					this.map.removeLayer(this.managed);
				}
			}
		} else if (typeof intrct === "string") {
			if (!!this.interaction[intrct]) {
				this.interaction[intrct].setActive(false);
			}
			if (intrct === "select" || intrct === "selectWMS") {
				this.isOn["select"] = false;
			} else {
				this.isOn[intrct] = false;
				this.tempVector.setMap(this.map);
				this.map.removeLayer(this.managed);
			}
		}
		// this.map.removeLayer(this.managed);
	},
	activeBtn : function(btn) {
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
	},
	deactiveBtn : function(btn) {
		if (this.btn[btn].hasClass("active")) {
			this.btn[btn].removeClass("active");
		}
	},
	select : function(layer) {
		var that = this;
		if (this.isOn.select) {
			if (!!this.interaction.selectWMS || !!this.interaction.select) {
				this.interaction.select.getFeatures().clear();
				this.deactiveIntrct([ "dragbox", "select", "selectWMS" ]);
			}
			this.deactiveBtn("selectBtn");
			this.isOn.select = false;
			return;
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

		if (sourceLayer instanceof ol.layer.Vector) {
			console.log("vector-select");
			if (!this.tempSelectSource) {
				this.tempSelectSource = new ol.source.Vector();
			} else {
				this.tempSelectSource.clear();
			}
			if (this.interaction.select instanceof ol.interaction.Select) {
				this.interaction.select.getFeatures().clear();
			}
			this.map.removeInteraction(this.interaction.select);
			var styles = [ new ol.style.Style({
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
			this.interaction.select = new ol.interaction.Select({
				layers : [ sourceLayer ],
				toggleCondition : ol.events.condition.platformModifierKeyOnly,
				style : styles
			});
			this.map.addInteraction(this.interaction.select);
			this.map.removeInteraction(this.interaction.dragbox);
			this.interaction.dragbox = new ol.interaction.DragBox({
				condition : ol.events.condition.shiftKeyOnly
			});
			this.map.addInteraction(this.interaction.dragbox);
			this.interaction.dragbox.on('boxend', function() {
				that.interaction.select.getFeatures().clear();
				sourceLayer.getSource().forEachFeatureIntersectingExtent(this.getGeometry().getExtent(), function(feature) {
					that.interaction.select.getFeatures().push(feature);
				});
				// that.interaction.selectWMS.setExtent(this.getGeometry().getExtent());
			});
			this.interaction.select.getFeatures().on("change:length", function(evt) {
				that.features = that.interaction.select.getFeatures();
				$(that.featureTB).empty();
				that.tempSelectSource.clear();
				that.tempSelectSource.addFeatures(that.features.getArray());
				if (that.features.getLength() > 1) {
					$(that.featurePop).hide();
					for (var i = 0; i < that.features.getLength(); i++) {
						var anc = $("<a>").addClass("gb-edit-sel-flist").css("cursor", "pointer").attr({
							"title" : that.features.item(i).getId()
						}).text(that.features.item(i).getId());
						var td = $("<td>").css({
							"text-overflow" : "ellipsis",
							"overflow" : "hidden",
							"text-align" : "right"
						}).append(anc);
						var tr = $("<tr>").append(td);
						$(that.featureTB).append(tr);
					}

					$(that.featurePop).show();
					$(that.featurePop).position({
						"my" : "right center",
						"at" : "right center",
						"of" : document,
						"collision" : "fit"
					});
					$(that.attrPop).hide();
				} else if (that.features.getLength() === 1) {
					$(that.featurePop).hide();
					$(that.attrTB).empty();
					that.setLayer(that.updateSelected());
					var attrInfo = that.getLayer().get("git").attribute;
					that.feature = that.features.item(0);
					if (!!attrInfo) {
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
							}).val(attr[keys[i]]);
							var td2 = $("<td>").append(tform);
							var tr = $("<tr>").append(td1).append(td2);
							that.attrTB.append(tr);
						}
						$(that.attrPop).show();
						$(that.attrPop).position({
							"my" : "right bottom",
							"at" : "right bottom+100",
							"of" : document,
							"collision" : "fit"
						});
					} else {
						// $(that.featurePop).hide();
						$(that.attrPop).hide();
					}
				} else {
					$(that.featurePop).hide();
					$(that.attrPop).hide();
				}

			});
			// this.map.addInteraction(this.interaction.selectWMS);
			this.activeIntrct([ "select", "dragbox" ]);
			this.isOn.select = true;
			this.activeBtn("selectBtn");
			this.deactiveIntrct([ "move", "rotate" ]);
		} else if (sourceLayer instanceof ol.layer.Base) {
			// && (sourceLayer.get("git").geometry === "Point" ||
			// sourceLayer.get("git").geometry === "LineString"
			// || sourceLayer.get("git").geometry === "Polygon" ||
			// sourceLayer.get("git").geometry === "MultiPoint"
			// || sourceLayer.get("git").geometry === "MultiLineString" ||
			// sourceLayer.get("git").geometry === "MultiPolygon")
			if (!sourceLayer instanceof ol.layer.Tile && !sourceLayer.get("git").hasOwnProperty("fake")) {
				return;
			}
			if (sourceLayer instanceof ol.layer.Base && sourceLayer.get("git").hasOwnProperty("fake")) {
				if (sourceLayer.get("git").fake === "child") {
					var arr = {
						"geoLayerList" : [ sourceLayer.get("id") ]
					}
					var names = [];
					// console.log(JSON.stringify(arr));

					$.ajax({
						url : "geoserver/getGeoLayerInfoList.ajax",
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
								for (var i = 0; i < data2.length; i++) {
									var source = new ol.source.TileWMS({
										url : "geoserver/geoserverWMSLayerLoad.do",
										params : {
											'LAYERS' : data2[i].lName,
											'TILED' : true,
											'FORMAT' : 'image/png8',
											'VERSION' : '1.1.0',
											'CRS' : 'EPSG:5186',
											'SRS' : 'EPSG:5186',
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
											epsg : "5186",
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
											epsg : "5186",
											mbound : [ [ data2[i].nbBox.minx.toString(), data2[i].nbBox.miny.toString() ],
													[ data2[i].nbBox.maxx.toString(), data2[i].nbBox.maxy.toString() ] ],
											isNew : false,
											lbound : [ [ 122.71, 28.6 ], [ 134.28, 40.27 ] ],
											isNew : false,
											geometry : id.substring(getPosition(id, "_", 4) + 1),
											sheetNum : id.substring((getPosition(id, "_", 2) + 1), getPosition(id, "_", 3))
										});
									}
									ogit["information"] = layer;
									// var git = {
									// "validation" : false,
									// "geometry" : data2[i].geomType,
									// "editable" : true,
									// "attribute" : data2[i].attInfo,
									// "fake" : "child"
									// }
									// wms.set("name",
									// obj.refer.get_node(data2[i].lName).text);
									// wms.set("id", data2[i].lName);
									// wms.setVisible(false);
									// console.log(wms.get("id"));
									// wms.set("type", "ImageTile");
									// wms.set("git", git);
									// console.log(wms);
								}

								$("body").css("cursor", "default");
							}
						}
					});
				}
			}
			console.log("image-select");
			if (!this.tempSelectSource) {
				this.tempSelectSource = new ol.source.Vector();
			} else {
				this.tempSelectSource.clear();
			}
			if (this.interaction.select instanceof ol.interaction.Select) {
				this.interaction.select.getFeatures().clear();
			}
			this.map.removeInteraction(this.interaction.select);
			var styles = [ new ol.style.Style({
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
			this.interaction.select = new ol.interaction.Select({
				layers : [ this.tempVector ],
				toggleCondition : ol.events.condition.platformModifierKeyOnly,
				style : styles
			});
			this.map.addInteraction(this.interaction.select);
			this.map.removeInteraction(this.interaction.dragbox);
			this.interaction.dragbox = new ol.interaction.DragBox({
				condition : ol.events.condition.shiftKeyOnly
			});
			this.map.addInteraction(this.interaction.dragbox);
			this.interaction.dragbox.on('boxend', function() {
				that.interaction.select.getFeatures().clear();
				that.tempSource.forEachFeatureIntersectingExtent(this.getGeometry().getExtent(), function(feature) {
					that.interaction.select.getFeatures().push(feature);
				});
				that.interaction.selectWMS.setExtent(this.getGeometry().getExtent());
			});
			this.interaction.selectWMS = new gb.interaction.SelectWMS({
				url : that.options.url,
				select : that.interaction.select,
				destination : that.tempVector,
				record : that.options.featureRecord,
				layer : function() {
					return that.updateSelected();
				}
			});
			this.interaction.select.getFeatures().on("change:length", function(evt) {
				that.features = that.interaction.select.getFeatures();
				$(that.featureTB).empty();
				that.tempSelectSource.clear();
				that.tempSelectSource.addFeatures(that.features.getArray());
				if (that.features.getLength() > 1) {
					$(that.featurePop).hide();
					for (var i = 0; i < that.features.getLength(); i++) {
						var anc = $("<a>").addClass("gb-edit-sel-flist").css("cursor", "pointer").attr({
							"title" : that.features.item(i).getId()
						}).text(that.features.item(i).getId());
						var td = $("<td>").css({
							"text-overflow" : "ellipsis",
							"overflow" : "hidden",
							"text-align" : "right"
						}).append(anc);
						var tr = $("<tr>").append(td);
						$(that.featureTB).append(tr);
					}

					$(that.featurePop).show();
					$(that.featurePop).position({
						"my" : "right center",
						"at" : "right center",
						"of" : document,
						"collision" : "fit"
					});
					$(that.attrPop).hide();
				} else if (that.features.getLength() === 1) {
					$(that.featurePop).hide();
					$(that.attrTB).empty();
					that.setLayer(that.updateSelected());
					var attrInfo = that.getLayer().get("git").attribute;
					that.feature = that.features.item(0);
					if (!!attrInfo) {
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
							}).val(attr[keys[i]]);
							var td2 = $("<td>").append(tform);
							var tr = $("<tr>").append(td1).append(td2);
							that.attrTB.append(tr);
						}
						$(that.attrPop).show();
						$(that.attrPop).position({
							"my" : "right bottom",
							"at" : "right bottom+100",
							"of" : document,
							"collision" : "fit"
						});
					}
				} else {
					$(that.featurePop).hide();
					$(that.attrPop).hide();
				}

			});
			this.map.addInteraction(this.interaction.selectWMS);
			this.activeIntrct([ "select", "selectWMS", "dragbox" ]);
			this.isOn.select = true;
			this.activeBtn("selectBtn");
			this.deactiveIntrct([ "move", "rotate" ]);
		}
	},

	draw : function(layer) {
		var that = this;
		if (this.isOn.draw) {
			if (!!this.interaction.draw || !!this.interaction.updateDraw) {
				this.deactiveIntrct("draw");
				this.deactiveBtn("drawBtn");
			}
			return;
		}
		if (!!this.interaction.select) {
			this.interaction.select.getFeatures().clear();
			this.deactiveIntrct([ "dragbox", "select", "selectWMS" ]);
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
		} else if (layer instanceof ol.layer.Tile || layer instanceof ol.layer.Vector) {
			sourceLayer = layer;
		} else {
			return;
		}

		var git = sourceLayer.get("git");
		if (git.editable === true && sourceLayer instanceof ol.layer.Tile) {

			if (!this.managed) {
				this.managed = new ol.layer.Vector({
					source : this.tempSource
				});
				this.managed.set("name", "temp_vector");
				this.managed.set("id", "temp_vector");
				this.map.addLayer(this.managed);
			}

			this.interaction.draw = new ol.interaction.Draw({
				source : this.tempSource,
				type : git.geometry
			});
			this.interaction.draw.selectedType = function() {
				return that.updateSelected().get("git").geometry;
			};
			this.interaction.draw.on("drawend", function(evt) {
				console.log(evt);
				var layers = that.options.selected();
				if (layers.length !== 1) {
					return;
				}
				if (that.getLayer().get("id") === layers[0].get("id")) {
					var feature = evt.feature;
					var c = that.options.featureRecord.getCreated();
					var l = c[that.getLayer().get("id")];
					if (!l) {
						var fid = that.getLayer().get("id") + ".new0";
						feature.setId(fid);
						that.options.featureRecord.create(layers[0], feature);
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
						that.options.featureRecord.create(layers[0], feature);
					}
				}
			});
			this.map.addInteraction(this.interaction.draw);
			this.deactiveIntrct([ "select", "selectWMS", "move", "modify", "rotate" ]);
			this.activeIntrct("draw");
			this.activeBtn("drawBtn");
		} else if (git.editable === true && sourceLayer instanceof ol.layer.Vector) {
			this.interaction.draw = new ol.interaction.Draw({
				source : sourceLayer.getSource(),
				type : git.geometry
			});

			this.interaction.draw.selectedType = function() {
				var irreGeom = that.updateSelected().get("git").geometry;
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
				var layers = that.options.selected();
				if (layers.length !== 1) {
					return;
				}
				if (that.getLayer().get("id") === layers[0].get("id")) {
					var feature = evt.feature;
					var c = that.options.featureRecord.getCreated();
					var l = c[that.getLayer().get("id")];
					if (!l) {
						var fid = that.getLayer().get("id") + ".new0";
						feature.setId(fid);
						that.options.featureRecord.create(layers[0], feature);
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
						that.options.featureRecord.create(layers[0], feature);
					}
				}
			});
			this.map.addInteraction(this.interaction.draw);
			this.deactiveIntrct([ "select", "selectWMS", "move", "modify", "rotate" ]);
			this.activeIntrct("draw");
			this.activeBtn("drawBtn");
		}

	},
	move : function(layer) {
		var that = this;
		if (this.interaction.select.getFeatures().getLength() > 0) {
			if (this.isOn.move) {
				if (!!this.interaction.move) {
					this.interaction.select.getFeatures().clear();
					this.deactiveIntrct("move");
					this.deactiveBtn("moveBtn");
					this.map.removeLayer(this.managed);
				}
				return;
			}
			if (!this.managed) {
				this.managed = new ol.layer.Vector({
					source : this.tempSource
				});
				this.managed.set("name", "temp_vector");
				this.managed.set("id", "temp_vector");
				this.map.addLayer(this.managed);
			}
			this.interaction.move = new ol.interaction.Translate({
				features : this.interaction.select.getFeatures()
			});
			this.interaction.move.on("translateend", function(evt) {
				console.log(evt);
				var layers = that.options.selected();
				if (layers.length !== 1) {
					return;
				}
				if (that.getLayer().get("id") === layers[0].get("id")) {
					var features = evt.features;
					for (var i = 0; i < features.getLength(); i++) {
						that.options.featureRecord.update(layers[0], features.item(i));
					}
				}
			});
			this.map.addInteraction(this.interaction.move);
			this.deactiveIntrct([ "select", "selectWMS", "modify", "rotate" ]);
			this.activeIntrct("move");
			this.activeBtn("moveBtn");
		} else {
			console.error("select features");
		}
	},
	rotate : function(layer) {
		var that = this;
		if (this.interaction.select.getFeatures().getLength() > 0) {
			if (this.isOn.rotate) {
				if (!!this.interaction.rotate) {
					this.interaction.select.getFeatures().clear();
					this.deactiveIntrct("rotate");
					this.deactiveBtn("rotateBtn");
				}
				return;
			}
			if (this.interaction.select.getFeatures().getLength() !== 1) {
				console.error("select 1 feature");
				return;
			}
			if (!this.managed) {
				this.managed = new ol.layer.Vector({
					source : this.tempSource
				});
				this.managed.set("name", "temp_vector");
				this.managed.set("id", "temp_vector");
				this.map.addLayer(this.managed);
			}
			this.interaction.rotate = new gb.interaction.MultiTransform({
				features : this.interaction.select.getFeatures()
			});
			this.interaction.rotate.on("transformend", function(evt) {
				console.log(evt);
				var layers = that.options.selected();
				if (layers.length !== 1) {
					return;
				}
				if (that.getLayer().get("id") === layers[0].get("id")) {
					var feature = evt.feature;
					that.options.featureRecord.update(layers[0], feature);
				}
			});
			this.map.addInteraction(this.interaction.rotate);
			this.deactiveIntrct([ "select", "selectWMS", "move", "modify" ]);
			this.activeIntrct("rotate");
			this.activeBtn("rotateBtn");
		} else {
			console.error("select features");
		}
	},
	modify : function(layer) {
		var that = this;
		if (this.interaction.select.getFeatures().getLength() > 0) {
			if (this.isOn.modify) {
				if (!!this.interaction.modify) {
					this.interaction.select.getFeatures().clear();
					this.deactiveIntrct("modify");
					this.deactiveBtn("modiBtn");
					this.map.removeLayer(this.managed);
				}
				return;
			}
			if (!this.managed) {
				this.managed = new ol.layer.Vector({
					source : this.tempSource
				});
				this.managed.set("name", "temp_vector");
				this.managed.set("id", "temp_vector");
				this.map.addLayer(this.managed);
			}
			this.interaction.modify = new ol.interaction.Modify({
				features : this.interaction.select.getFeatures()
			});
			this.interaction.modify.on("modifyend", function(evt) {
				console.log(evt);
				var layers = that.options.selected();
				if (layers.length !== 1) {
					return;
				}
				if (that.getLayer().get("id") === layers[0].get("id")) {
					var features = evt.features;
					for (var i = 0; i < features.getLength(); i++) {
						that.options.featureRecord.update(layers[0], features.item(i));
					}
				}
			});
			this.map.addInteraction(this.interaction.modify);
			this.deactiveIntrct([ "select", "selectWMS", "move", "rotate" ]);
			this.activeIntrct("modify");
			this.activeBtn("modiBtn");
		} else {
			console.error("select features");
		}
	},
	remove : function(layer) {
		var that = this;
		if (this.interaction.select.getFeatures().getLength() > 0) {
			if (this.isOn.remove) {
				if (!!this.interaction.remove) {
					this.interaction.select.getFeatures().clear();
					this.deactiveBtn("removeBtn");
					this.map.removeLayer(this.managed);
				}
				return;
			}
			if (!this.managed) {
				this.managed = new ol.layer.Vector({
					source : this.tempSource
				});
				this.managed.set("name", "temp_vector");
				this.managed.set("id", "temp_vector");
				this.map.addLayer(this.managed);
			}
			var layers = that.options.selected();
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
				for (var i = 0; i < features.getLength(); i++) {
					if (features.item(i).getId().search(".new") !== -1) {
						this.managed.getSource().removeFeature(features.item(i));
					} else {
						features.item(i).setStyle(style);
					}
					that.options.featureRecord.remove(layers[0], features.item(i));
				}
			}
			this.interaction.select.getFeatures().clear();

		} else {
			console.error("select features");
		}
	},
	setSelected : function(layers) {
		this.layers = layers;
	},
	getSelected : function() {
		return this.layers;
	},
	updateSelected : function() {
		var result;
		if (typeof this.options.selected === "function") {
			this.layers = this.options.selected();
			if (Array.isArray(this.layers)) {
				this.layer = this.layers[0];
				result = this.layer;
			}
		}
		return result;
	},
	open : function() {
		if (typeof this.options.selected === "function") {
			var layers = this.options.selected();
			if (layers.length === 1 && !(layers[0] instanceof ol.layer.Group)) {
				this.setLayer(layers[0]);
				$(this.window).show();
			} else {
				console.error("select a layer");
			}
		}
	},
	close : function() {
		$(this.window).hide();
	},
	destroy : function() {
		this.element.off("click");
		$(this.window).find("button").off("click");
		$(this.window).find("input").off("change").off("load");
		this.window = undefined;
	},
	_appendTo : function() {
		var element = this.options.appendTo;
		if (element && (element.jquery || element.nodeType)) {
			return $(element);
		}
		return this.document.find(element || "body").eq(0);
	},
	_removeClass : function(element, keys, extra) {
		return this._toggleClass(element, keys, extra, false);
	},

	_addClass : function(element, keys, extra) {
		return this._toggleClass(element, keys, extra, true);
	},

	_toggleClass : function(element, keys, extra, add) {
		add = (typeof add === "boolean") ? add : extra;
		var shift = (typeof element === "string" || element === null), options = {
			extra : shift ? keys : extra,
			keys : shift ? element : keys,
			element : shift ? this.element : element,
			add : add
		};
		options.element.toggleClass(this._classes(options), add);
		return this;
	},

	_on : function(suppressDisabledCheck, element, handlers) {
		var delegateElement;
		var instance = this;

		// No suppressDisabledCheck flag, shuffle arguments
		if (typeof suppressDisabledCheck !== "boolean") {
			handlers = element;
			element = suppressDisabledCheck;
			suppressDisabledCheck = false;
		}

		// No element argument, shuffle and use this.element
		if (!handlers) {
			handlers = element;
			element = this.element;
			delegateElement = this.widget();
		} else {
			element = delegateElement = $(element);
			this.bindings = this.bindings.add(element);
		}

		$.each(handlers, function(event, handler) {
			function handlerProxy() {

				// Allow widgets to customize the disabled
				// handling
				// - disabled as an array instead of boolean
				// - disabled class as method for disabling
				// individual parts
				if (!suppressDisabledCheck && (instance.options.disabled === true || $(this).hasClass("ui-state-disabled"))) {
					return;
				}
				return (typeof handler === "string" ? instance[handler] : handler).apply(instance, arguments);
			}

			// Copy the guid so direct unbinding works
			if (typeof handler !== "string") {
				handlerProxy.guid = handler.guid = handler.guid || handlerProxy.guid || $.guid++;
			}

			var match = event.match(/^([\w:-]*)\s*(.*)$/);
			var eventName = match[1] + instance.eventNamespace;
			var selector = match[2];

			if (selector) {
				delegateElement.on(eventName, selector, handlerProxy);
			} else {
				element.on(eventName, handlerProxy);
			}
		});
	},

	_off : function(element, eventName) {
		eventName = (eventName || "").split(" ").join(this.eventNamespace + " ") + this.eventNamespace;
		element.off(eventName).off(eventName);

		// Clear the stack to avoid memory leaks (#10056)
		this.bindings = $(this.bindings.not(element).get());
		this.focusable = $(this.focusable.not(element).get());
		this.hoverable = $(this.hoverable.not(element).get());
	}
});