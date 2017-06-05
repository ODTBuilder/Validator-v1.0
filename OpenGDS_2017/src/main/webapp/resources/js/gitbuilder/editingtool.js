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
gitbuilder.ui.EditingTool = $.widget("gitbuilder.editingtool",
		{
			window : undefined,
			options : {
				geoserverURL : undefined,
				map : undefined,
				user : undefined,
				selected : undefined,
				appendTo : "body",
				record : undefined
			},
			map : undefined,
			layers : undefined,
			layer : undefined,
			isOn : {
				select : false,
				move : false,
				remove : false,
				modify : false,
				rotate : false
			},
			interaction : {
				select : undefined,
				selectWMS : undefined,
				dragbox : undefined,
				// draw : ,
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
			_create : function() {
				var that = this;
				$(document).mousemove(function(e) {
					that.mouseX = e.pageX;
					that.mouseY = e.pageY;
				});

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
				}).addClass("panel").addClass("panel-default").append(fhead).append(flist);
				$("body").append(this.featurePop);
				$(this.featurePop).hide();
				$(this.featurePop).draggable({
					appendTo : "body",
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

				this.map = this.options.map;

				$(document).on("click", ".gitbuilder-editingtool-sel", function() {
					// var git = that.layer.get("git");
					console.log("click select");
					that.select(that.updateSelected());
				});
				$(document).on("click", ".gitbuilder-editingtool-dra", function() {
					// var git = that.layer.get("git");
					// console.log(that.layer);
					var layer = that.options.selected();
					that.draw(layer);
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
							this.map.addLayer(this.managed);
						}
					}
				} else if (typeof intrct === "string") {
					this.interaction[intrct].setActive(true);
					if (intrct === "select" || intrct === "selectWMS" || intrct[j] === "dragbox") {
						this.isOn["select"] = true;
					} else {
						this.isOn[intrct] = true;
						this.map.addLayer(this.managed);
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
					if (!!this.interaction.selectWMS && !!this.interaction.select) {
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
				} else if (layer instanceof ol.layer.Tile) {
					sourceLayer = layer;
				} else {
					return;
				}

				if (sourceLayer instanceof ol.layer.Vector) {
					console.log("vector-select");
					if (!!this.interaction.select) {
						this.activeIntrct("select");
					}
				} else if (sourceLayer instanceof ol.layer.Tile
						&& (sourceLayer.get("git").geometry === "Point" || sourceLayer.get("git").geometry === "LineString"
								|| sourceLayer.get("git").geometry === "Polygon" || sourceLayer.get("git").geometry === "MultiPoint"
								|| sourceLayer.get("git").geometry === "MultiLineString" || sourceLayer.get("git").geometry === "MultiPolygon")) {
					console.log("image-select");
					if (!this.tempSelectSource) {
						this.tempSelectSource = new ol.source.Vector();
					} else {
						this.tempSelectSource.clear();
					}
					this.map.removeInteraction(this.interaction.select);
					this.interaction.select = new ol.interaction.Select({
						layers : [ this.tempVector ],
						toggleCondition : ol.events.condition.platformModifierKeyOnly
					});
					this.map.addInteraction(this.interaction.select);
					this.map.removeInteraction(this.interaction.dragbox);
					this.interaction.dragbox = new ol.interaction.DragBox({
						condition : ol.events.condition.shiftKeyOnly
					});
					this.map.addInteraction(this.interaction.dragbox);
					this.interaction.dragbox.on('boxend', function() {
						that.interaction.selectWMS.setExtent(this.getGeometry().getExtent());
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
								"my" : "left center",
								"at" : "left+" + that.mouseX + " " + "top+" + that.mouseY,
								"of" : document
							});
						} else {
							$(that.featurePop).hide();
						}

					});
					this.interaction.selectWMS = new gb.interaction.SelectWMS({
						select : that.interaction.select,
						destination : that.tempVector,
						layer : function() {
							return that.updateSelected();
						}
					});
					this.map.addInteraction(this.interaction.selectWMS);
					this.activeIntrct([ "select", "selectWMS", "dragbox" ]);
					this.isOn.select = true;
					this.activeBtn("selectBtn");
					this.deactiveIntrct("move");
				}
			},

			draw : function(layer) {
				this.activeBtn("drawBtn");
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
						if (that.layer.get("id") === layers[0].get("id")) {
							var features = evt.features;
							for (var i = 0; i < features.getLength(); i++) {
								that.options.record.update(layers[0], features.item(i));
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
							this.map.removeLayer(this.managed);
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
						if (that.layer.get("id") === layers[0].get("id")) {
							var features = evt.features;
							that.options.record.update(layers[0], features);
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
						if (that.layer.get("id") === layers[0].get("id")) {
							var features = evt.features;
							for (var i = 0; i < features.getLength(); i++) {
								that.options.record.update(layers[0], features.item(i));
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
							// this.deactiveIntrct("move");
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
					}
					this.map.addLayer(this.managed);
					var layers = that.options.selected();
					if (layers.length !== 1) {
						return;
					}
					if (that.layer.get("id") === layers[0].get("id")) {
						var features = this.interaction.select.getFeatures();
						for (var i = 0; i < features.getLength(); i++) {
							this.managed.getSource().removeFeature(features.item(i));
							that.options.record.remove(layers[0], features.item(i));
						}
					}
					this.interaction.select.getFeatures().clear();
					this.map.removeLayer(this.managed);

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
					if (this.layers.length === 1) {
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
						this.layer = layers[0];
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