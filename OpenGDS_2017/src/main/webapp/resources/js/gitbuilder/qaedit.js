/**
 * 검수편집 객체를 정의한다.
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
gitbuilder.ui.QAEdit = $.widget("gitbuilder.qaedit",
		{
			window : undefined,
			naviWindow : undefined,
			options : {
				geoserverURL : undefined,
				map : undefined,
				user : undefined,
				appendTo : "body",
				layersURL : undefined,
				featureWMSURL : undefined,
				featureWFSURL : undefined,
				groupURL : undefined,
				editingTool : undefined,
				linkKey : undefined
			},
			map : undefined,
			mapsheet : undefined,
			upbtn : undefined,
			leftbtn : undefined,
			centerbtn : undefined,
			rightbtn : undefined,
			downbtn : undefined,
			errbtn : undefined,
			td2 : undefined,
			tbody : undefined,
			error : undefined,
			source : undefined,
			features : undefined,
			count : undefined,
			feature : undefined,
			lid : undefined,
			list : undefined,
			lastMapSheet : undefined,
			_create : function() {
				var that = this;
				console.log(this.options.editingTool);
				this._on(false, this.element, {
					click : function(event) {
						if (event.target === that.element[0]) {
							that.open();
						}
					}
				});
				this.mapsheet = {};
				this.map = this.options.map;
				var xSpan = $("<span>").attr({
					"aria-hidden" : true
				}).html("&times;");
				var xButton = $("<button>").attr({
					"type" : "button",
					"data-dismiss" : "modal",
					"aria-label" : "Close"
				}).html(xSpan);
				$(xButton).addClass("close");

				var htag = $("<h4>");
				htag.text("QA Edit");
				$(htag).addClass("modal-title");

				var header = $("<div>").append(xButton).append(htag);
				$(header).addClass("modal-header");
				/*
				 * 
				 * 
				 * header end
				 * 
				 * 
				 */
				var icls = $("<i>").addClass("fa").addClass("fa-refresh").attr({
					"aria-hidden" : "true"
				});
				var refBtn = $("<button>").addClass("pull-right").addClass("gitbuilder-clearbtn").append(icls).click(function() {
					$(that.list).jstree("refresh");
				});
//				var serverTitle = $("<h3>").addClass("panel-title").text("GeoServer");
				var serverHead = $("<div>").addClass("panel-heading").append("GeoServer").append(refBtn);
				this.list = $("<div>").attr({
					"id" : "geoserverList"
				});
				var serverBody = $("<div>").css({
					"padding" : "0",
					"overflow" : "auto",
					"height" : "300px"
				}).addClass("panel-body").append(this.list);
				var panel = $("<div>").addClass("panel").addClass("panel-default").append(serverHead).append(serverBody);
				var left = $("<div>").append(panel);

				$(this.list).jstree({
					"core" : {
						"animation" : 0,
						"check_callback" : true,
						"themes" : {
							"stripes" : true
						},
						'data' : {
							'url' : function() {
								return that.options.layersURL;
							}
						}
					},
					"contextmenu" : {
						select_node : true,
						show_at_node : true,
						items : function(o, cb) { // Could be an
							// object
							// directly
							return {
								"center" : {
									"separator_before" : false,
									"icon" : "fa fa-dot-circle-o",
									"separator_after" : false,
									"_disabled" : false, // (this.check("rename_node",
									// data.reference,
									// this.get_parent(data.reference),
									// "")),
									"label" : "Center",
									/*
									 * ! "shortcut" : 113, "shortcut_label" :
									 * 'F2', "icon" : "glyphicon
									 * glyphicon-leaf",
									 */
									"action" : function(data) {
										var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
										if (obj.type === "n_ngi_group" || obj.type === "n_dxf_group" || obj.type === "n_shp_group") {
											that.assign_center(obj);
										}
									}
								},
								"up" : {
									"separator_before" : false,
									"icon" : "fa fa-chevron-circle-up",
									"separator_after" : false,
									"_disabled" : false, // (this.check("rename_node",
									// data.reference,
									// this.get_parent(data.reference),
									// "")),
									"label" : "Up",
									/*
									 * ! "shortcut" : 113, "shortcut_label" :
									 * 'F2', "icon" : "glyphicon
									 * glyphicon-leaf",
									 */
									"action" : function(data) {
										var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
										if (obj.type === "n_ngi_group" || obj.type === "n_dxf_group" || obj.type === "n_shp_group") {
											that.assign_up(obj);
										}
									}
								},
								"down" : {
									"separator_before" : false,
									"icon" : "fa fa-chevron-circle-down",
									"separator_after" : false,
									"_disabled" : false, // (this.check("rename_node",
									// data.reference,
									// this.get_parent(data.reference),
									// "")),
									"label" : "Down",
									/*
									 * ! "shortcut" : 113, "shortcut_label" :
									 * 'F2', "icon" : "glyphicon
									 * glyphicon-leaf",
									 */
									"action" : function(data) {
										var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
										if (obj.type === "n_ngi_group" || obj.type === "n_dxf_group" || obj.type === "n_shp_group") {
											that.assign_down(obj);
										}
									}
								},
								"left" : {
									"separator_before" : false,
									"icon" : "fa fa-chevron-circle-left",
									"separator_after" : false,
									"_disabled" : false, // (this.check("rename_node",
									// data.reference,
									// this.get_parent(data.reference),
									// "")),
									"label" : "Left",
									/*
									 * ! "shortcut" : 113, "shortcut_label" :
									 * 'F2', "icon" : "glyphicon
									 * glyphicon-leaf",
									 */
									"action" : function(data) {
										var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
										if (obj.type === "n_ngi_group" || obj.type === "n_dxf_group" || obj.type === "n_shp_group") {
											that.assign_left(obj);
										}
									}
								},
								"right" : {
									"separator_before" : false,
									"icon" : "fa fa-chevron-circle-right",
									"separator_after" : false,
									"_disabled" : false, // (this.check("rename_node",
									// data.reference,
									// this.get_parent(data.reference),
									// "")),
									"label" : "Right",
									/*
									 * ! "shortcut" : 113, "shortcut_label" :
									 * 'F2', "icon" : "glyphicon
									 * glyphicon-leaf",
									 */
									"action" : function(data) {
										var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
										if (obj.type === "n_ngi_group" || obj.type === "n_dxf_group" || obj.type === "n_shp_group") {
											that.assign_right(obj);
										}
									}
								},
								"error" : {
									"separator_before" : false,
									"icon" : "fa fa-info-circle",
									"separator_after" : false,
									"_disabled" : false, // (this.check("rename_node",
									// data.reference,
									// this.get_parent(data.reference),
									// "")),
									"label" : "Error",
									/*
									 * ! "shortcut" : 113, "shortcut_label" :
									 * 'F2', "icon" : "glyphicon
									 * glyphicon-leaf",
									 */
									"action" : function(data) {
										var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
										if (obj.type === "e_ngi_layer" || obj.type === "e_dxf_layer" || obj.type === "e_shp_layer") {
											that.assign_error(obj);
										}
									}
								}
							};
						}
					},
					"plugins" : [ "contextmenu", "search", "types" ]
				});

				$("body").on("contextmenu", function() {
					$("body").find(".vakata-context").css("z-index", "1100");
				});

				this.upbtn = $("<input>").addClass("gitbuilder-qaedit-delup").addClass("btn").addClass("btn-default").attr({
					"type" : "button",
					"value" : "Up : Not assigned"
				}).css({
					"width" : "100%",
					"margin-left" : 0
				});
				this.leftbtn = $("<input>").addClass("gitbuilder-qaedit-delleft").addClass("btn").addClass("btn-default").attr({
					"type" : "button",
					"value" : "Left : Not assigned"
				}).css({
					"width" : "100%",
					"margin-left" : 0
				});
				this.centerbtn = $("<input>").addClass("gitbuilder-qaedit-delcenter").addClass("btn").addClass("btn-default").attr({
					"type" : "button",
					"value" : "Center : Not assigned"
				}).css({
					"width" : "100%",
					"margin-left" : 0
				});
				this.rightbtn = $("<input>").addClass("gitbuilder-qaedit-delright").addClass("btn").addClass("btn-default").attr({
					"type" : "button",
					"value" : "Right : Not assigned"
				}).css({
					"width" : "100%",
					"margin-left" : 0
				});
				this.downbtn = $("<input>").addClass("gitbuilder-qaedit-deldown").addClass("btn").addClass("btn-default").attr({
					"type" : "button",
					"value" : "Down : Not assigned"
				}).css({
					"width" : "100%",
					"margin-left" : 0
				});
				this.errbtn = $("<input>").addClass("gitbuilder-qaedit-delerror").addClass("btn").addClass("btn-default").attr({
					"type" : "button",
					"value" : "Error : Not assigned"
				}).css({
					"width" : "100%",
					"margin-left" : 0
				});

				var td1 = $("<td>"), td2 = $("<td>").append(this.upbtn), td3 = $("<td>");
				var tr1 = $("<tr>").append(td1).append(td2).append(td3);

				var td11 = $("<td>").append(this.leftbtn), td22 = $("<td>").append(this.centerbtn), td33 = $("<td>").append(this.rightbtn);
				var tr11 = $("<tr>").append(td11).append(td22).append(td33);

				var td111 = $("<td>"), td222 = $("<td>").append(this.downbtn), td333 = $("<td>");
				var tr111 = $("<tr>").append(td111).append(td222).append(td333);

				var td1111 = $("<td>"), td2222 = $("<td>").attr({
					"colspan" : "3"
				}).append(this.errbtn), td3333 = $("<td>");
				var tr1111 = $("<tr>").append(td2222);

				var tb = $("<table>").addClass("table").addClass("table-bordered").addClass("text-center").append(tr1).append(tr11).append(
						tr111).append(tr1111);

				var sixLayers = $("<div>").append(tb);

				var outerRow = $("<div>").addClass("row").append(sixLayers).append(left);
				var body = $("<div>").append(outerRow);
				$(body).addClass("modal-body");
				/*
				 * 
				 * 
				 * body end
				 * 
				 * 
				 */
				var closeBtn = $("<button>").attr({
					"type" : "button",
					"data-dismiss" : "modal"
				});
				$(closeBtn).addClass("btn");
				$(closeBtn).addClass("btn-default");
				$(closeBtn).text("Close");

				var okBtn = $("<button>").attr({
					"type" : "button",
					"data-dismiss" : "modal"
				});
				$(okBtn).addClass("btn");
				$(okBtn).addClass("btn-primary");
				$(okBtn).addClass("gitbuilder-jstree-geoserver-qa-ok");
				$(okBtn).text("OK");
				$(document).on("click", ".gitbuilder-jstree-geoserver-qa-ok", function() {
					that.start();
				});
				var footer = $("<div>").append(closeBtn).append(okBtn);
				$(footer).addClass("modal-footer");
				/*
				 * 
				 * 
				 * footer end
				 * 
				 * 
				 */
				var content = $("<div>").append(header).append(body).append(footer);
				$(content).addClass("modal-content");

				var dialog = $("<div>").html(content);
				$(dialog).addClass("modal-dialog");
				$(dialog).addClass("modal-lg");
				this.window = $("<div>").hide().attr({
					// Setting tabIndex makes the div focusable
					tabIndex : -1,
					role : "dialog",
				}).html(dialog);
				this._addClass(this.window, this.eventNamespace.substr(1));
				$(this.window).addClass("modal");
				$(this.window).addClass("fade");

				$(this.window).appendTo("body");
				this.window.modal({
					backdrop : "static",
					keyboard : true,
					show : false,
				});

				$(document).on("click", this.eventNamespace + " .gitbuilder-qaedit-navigator-link", function() {
					console.log($(this).attr("value"));
					var fid = $(this).attr("value");
					var lid = fid.substring(0, fid.indexOf("."));
					if (lid.indexOf(".") !== -1) {
						console.error("layer name error");
						return;
					}
					that.selectOriginalFeature(lid, fid);
				});

				$(document).on("click", this.eventNamespace + " .gitbuilder-qaedit-delup", function() {
					that.mapsheet.up = undefined;
					$(this).val("Up : Not assigned");
					console.log(that.mapsheet);
				});
				$(document).on("click", this.eventNamespace + " .gitbuilder-qaedit-deldown", function() {
					that.mapsheet.down = undefined;
					$(this).val("Down : Not assigned");
					console.log(that.mapsheet);
				});
				$(document).on("click", this.eventNamespace + " .gitbuilder-qaedit-delleft", function() {
					that.mapsheet.left = undefined;
					$(this).val("Left : Not assigned");
					console.log(that.mapsheet);
				});
				$(document).on("click", this.eventNamespace + " .gitbuilder-qaedit-delright", function() {
					that.mapsheet.right = undefined;
					$(this).val("Right : Not assigned");
					console.log(that.mapsheet);
				});
				$(document).on("click", this.eventNamespace + " .gitbuilder-qaedit-delcenter", function() {
					that.mapsheet.center = undefined;
					$(this).val("Center : Not assigned");
					console.log(that.mapsheet);
				});
				$(document).on("click", this.eventNamespace + " .gitbuilder-qaedit-delerror", function() {
					that.mapsheet.error = undefined;
					$(this).val("Error : Not assigned");
					console.log(that.mapsheet);
				});

				this._create_navigator();
			},
			_init : function() {
				var that = this;
				this.mapsheet = {};
			},

			/**
			 * 오류 레이어 할당
			 */
			assign_error : function(obj) {
				this.mapsheet.error = obj.id;
				$(this.errbtn).val(obj.text);
				console.log(this.mapsheet);
			},

			/**
			 * 가운데 레이어 할당
			 */
			assign_center : function(obj) {
				this.mapsheet.center = obj.id;
				$(this.centerbtn).val(obj.text);
				console.log(this.mapsheet);
			},

			/**
			 * 위 레이어 할당
			 */
			assign_up : function(obj) {
				this.mapsheet.up = obj.id;
				$(this.upbtn).val(obj.text);
				console.log(this.mapsheet);
			},

			/**
			 * 아래 레이어 할당
			 */
			assign_down : function(obj) {
				this.mapsheet.down = obj.id;
				$(this.downbtn).val(obj.text);
				console.log(this.mapsheet);
			},

			/**
			 * 왼 레이어 할당
			 */
			assign_left : function(obj) {
				this.mapsheet.left = obj.id;
				$(this.leftbtn).val(obj.text);
				console.log(this.mapsheet);
			},

			/**
			 * 오른 레이어 할당
			 */
			assign_right : function(obj) {
				this.mapsheet.right = obj.id;
				$(this.rightbtn).val(obj.text);
				console.log(this.mapsheet);
			},
			_import_fake_group : function(jstreeRefer, group, groupInfoURL, wmsURL, wfsURL, groupLayerName, lastMapSheet, errorLayerName,
					errorCallback, inputLayer, instance) {
				// // =======================================
				var that = this;
				console.log(JSON.stringify(groupLayerName));
				$.ajax({
					url : groupInfoURL,
					method : "POST",
					contentType : "application/json; charset=UTF-8",
					cache : false,
					data : JSON.stringify(groupLayerName),
					beforeSend : function() { // 호출전실행
						$("body").css("cursor", "wait");
					},
					traditional : true,
					success : function(data, textStatus, jqXHR) {
						console.log(data);
						var getPosition = function(str, subString, index) {
							return str.split(subString, index).join(subString).length;
						};
						if (Array.isArray(data)) {
							for (var i = 0; i < data.length; i++) {
								var wms = new ol.layer.Tile({
									source : new ol.source.TileWMS({
										url : wmsURL,
										params : {
											'LAYERS' : jstreeRefer.get_node(data[i].name).children.toString(),
											'TILED' : true,
											'FORMAT' : 'image/png8',
											'VERSION' : '1.1.0',
											'CRS' : 'EPSG:5186',
											'SRS' : 'EPSG:5186',
											'BBOX' : data[i].bbox.minx.toString() + "," + data[i].bbox.miny.toString() + ","
													+ data[i].bbox.maxx.toString() + "," + data[i].bbox.maxy.toString(),
											"time" : Date.now()
										},
										serverType : 'geoserver'
									})
								});
								var layers = new ol.Collection();
								for (var j = 0; j < data[i].publishedList.names.length; j++) {
									var layer = new ol.layer.Layer({
										opacity : 1,
										visible : true
									});
									var id = data[i].publishedList.names[j];
									var name = id.substring((id.split("_", 3).join("_").length) + 1, id.split("_", 4).join("_").length);
									var geom = id.substring(getPosition(id, "_", 4) + 1);
									var realGeom;
									switch (geom) {
									case "POINT":
										realGeom = "Point";
										break;
									case "LINESTRING":
										realGeom = "LineString";
										break;
									case "POLYGON":
										realGeom = "Polygon";
										break;
									case "MULTIPOINT":
										realGeom = "MultiPoint";
										break;
									case "MULTILINESTRING":
										realGeom = "MultiLineString";
										break;
									case "MULTIPOLYGON":
										realGeom = "MultiPolygon";
										break;

									default:
										break;
									}
									var gchild = {
										"geometry" : realGeom,
										"validation" : false,
										"editable" : true,
										"fake" : "child"
									}
									layer.set("git", gchild);
									layer.set("id", id);
									layer.set("name", name);
									layers.push(layer);
								}
								var git = {
									"validation" : false,
									"geometry" : data[i].geomType,
									"editable" : true,
									"fake" : "parent",
									"layers" : layers
								}

								wms.set("name", jstreeRefer.get_node(data[i].name).text);
								wms.set("id", data[i].name);
								wms.set("git", git);

								group.getLayers().push(wms);
								console.log(wms);

								if (i === lastMapSheet) {
									errorCallback(jstreeRefer, wfsURL, errorLayerName, group, inputLayer, instance);
								}
								// wms.set("type", "ImageTile");
								// that._data.geoserver.map.addLayer(wms);
								// $("body").css("cursor", "default");
							}
						}
					}
				});
			},
			loadGroupLayer : function(jstreeRefer, layerInfoURL, groupInfoURL, wmsURL, wfsURL, groupLayerName, lastMapSheet,
					errorLayerName, childrenCallback) {
				console.log(groupLayerName);
				var that = this;
				var layers = new ol.Collection();
				var group = new ol.layer.Group({
					layers : layers
				});

				group.set("name", "Validator Layers");
				group.set("type", "Group");

				$.ajax({
					url : groupInfoURL,
					method : "POST",
					contentType : "application/json; charset=UTF-8",
					cache : false,
					data : JSON.stringify(groupLayerName),
					beforeSend : function() { // 호출전실행
						$("body").css("cursor", "wait");
					},
					traditional : true,
					success : function(data, textStatus, jqXHR) {
						console.log(data);
						// parentParam = data;
						if (Array.isArray(data)) {
							for (var i = 0; i < data.length; i++) {
								console.log(lastMapSheet);
								var wms = new ol.layer.Tile({
									source : new ol.source.TileWMS({
										url : wmsURL,
										params : {
											'LAYERS' : jstreeRefer.get_node(data[i].name).children.toString(),
											'TILED' : true,
											'FORMAT' : 'image/png8',
											'VERSION' : '1.1.0',
											'CRS' : 'EPSG:5186',
											'SRS' : 'EPSG:5186',
											'BBOX' : data[i].bbox.minx.toString() + "," + data[i].bbox.miny.toString() + ","
													+ data[i].bbox.maxx.toString() + "," + data[i].bbox.maxy.toString()
										},
										serverType : 'geoserver'
									})
								});
								wms.set("name", jstreeRefer.get_node(data[i].name).text);
								wms.set("id", data[i].name);
								var git = {
									"validation" : false,
									"geometry" : data[i].geomType,
									"editable" : true,
									"fake" : "parent"
								}
								wms.set("git", git);
								console.log(wms);
								// wms.set("type", "ImageTile");
								// that._data.geoserver.map.addLayer(wms);
								group.getLayers().push(wms);

								childrenCallback(jstreeRefer, layerInfoURL, wmsURL, wfsURL, group, lastMapSheet, i, jstreeRefer
										.get_node(data[i].name).children, wms, errorLayerName, that.loadErrorLayer, that);
							}
						}
					}
				});

			},
			loadChildrenLayer : function(jstreeRefer, layerInfoURL, wmsURL, wfsURL, group, lastSheet, nowSheet, childrenLayerName,
					parentLayer, errorLayerName, errorCallback, instance) {
				// var that = this;
				var arr = {
					"geoLayerList" : childrenLayerName
				}
				$.ajax({
					url : layerInfoURL,
					method : "POST",
					contentType : "application/json; charset=UTF-8",
					cache : false,
					data : JSON.stringify(arr),
					beforeSend : function() { // 호출전실행
						// loadImageShow();
					},
					traditional : true,
					success : function(data2, textStatus, jqXHR) {
						console.log(data2);
						if (Array.isArray(data2)) {
							var arra = [];
							for (var i = 0; i < data2.length; i++) {
								var wms = new ol.layer.Tile({
									source : new ol.source.TileWMS({
										url : wmsURL,
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
									})
								});
								var git = {
									"validation" : false,
									"geometry" : data2[i].geomType,
									"editable" : true,
									"attribute" : data2[i].attInfo,
									"fake" : "child"
								}
								wms.set("name", jstreeRefer.get_node(data2[i].lName).text);
								wms.set("id", data2[i].lName);
								// wms.setVisible(false);
								console.log(wms.get("id"));
								// wms.set("type", "ImageTile");
								wms.set("git", git);
								arra.push(wms);
								console.log(wms);
							}

							var info = parentLayer.get("git");
							info["layers"] = new ol.Collection().extend(arra);
							console.log(parentLayer);
							// that._data.geoserver.map.addLayer(parentLayer);
							// group.set("name",
							// obj.refer.get_node(obj.parent).text);
							// group.set("id", obj.parent);
							// group.set("type", "Group");
							// that._data.geoserver.map.addLayer(group);
						}
						// $("body").css("cursor", "default");
						if (lastSheet === nowSheet) {
							errorCallback(jstreeRefer, wfsURL, errorLayerName, group, instance.inputLayer, instance);
						}
					}
				});

			},
			loadErrorLayer : function(jstreeRefer, wfsURL, errorLayerName, group, inputCallback, instance) {
				// var that = this;
				var defaultParameters = {
					service : 'WFS',
					version : '1.0.0',
					request : 'GetFeature',
					typeName : errorLayerName,
					outputFormat : 'text/javascript',
					format_options : 'callback:getJson'
				};

				$.ajax({
					url : wfsURL,
					data : defaultParameters,
					dataType : 'jsonp',
					jsonpCallback : 'getJson',
					success : function(errorData, textStatus, jqXHR) {
						console.log(errorData);
						var format = new ol.format.GeoJSON().readFeatures(JSON.stringify(errorData));
						var source = new ol.source.Vector({
							features : format
						});
						var fill = new ol.style.Fill({
							color : "rgba(0,0,0,0)"
						});
						var stroke = new ol.style.Stroke({
							color : "rgba(255,0,0,1)",
							width : 2.7
						});
						var text = new ol.style.Text({});
						var styles = new ol.style.Style({
							image : new ol.style.Circle({
								fill : fill,
								stroke : stroke,
								radius : 40
							}),
							fill : fill,
							stroke : stroke,
							text : text
						});

						var layer = new ol.layer.Vector({
							source : source,
							style : styles
						});
						layer.set("name", jstreeRefer.get_node(errorLayerName).text);
						layer.set("id", jstreeRefer.get_node(errorLayerName).id);
						layer.set("type", "Point");
						layer.set("git", {
							"validation" : true
						});
						instance.error = layer;

						group.getLayers().push(layer);

						// var group = new ol.layer.Group({
						// layers : layers
						// });
						// group.set("name", "Validator Layers");
						// group.set("type", "Group");

						// 에러 레이어 로드 완료.
						inputCallback(group, instance);
					}
				});
			},
			inputLayer : function(vlayer, instance) {
				if (instance.map instanceof ol.Map) {
					var coll = instance.map.getLayers();
					for (var i = 0; i < coll.getLength(); i++) {
						if (coll.item(i).get("name") === "Validator Layers") {
							this.map.removeLayer(coll.item(i));
						}
					}
					instance.map.addLayer(vlayer);
					// instance.openNavigator();
					instance._initNavigator();
					$("body").css("cursor", "default");
				}
			},
			start : function() {
				var that = this;
				console.log(this.mapsheet);
				var mapsheet = this.mapsheet;
				var arr = {
					"geoLayerList" : []
				}

				if (mapsheet.center !== undefined && mapsheet.center !== null && mapsheet.center !== "") {
					arr.geoLayerList.push(mapsheet.center);
				}
				if (mapsheet.up !== undefined && mapsheet.up !== null && mapsheet.up !== "") {
					arr.geoLayerList.push(mapsheet.up);
				}
				if (mapsheet.down !== undefined && mapsheet.down !== null && mapsheet.down !== "") {
					arr.geoLayerList.push(mapsheet.down);
				}
				if (mapsheet.left !== undefined && mapsheet.left !== null && mapsheet.left !== "") {
					arr.geoLayerList.push(mapsheet.left);
				}
				if (mapsheet.right !== undefined && mapsheet.right !== null && mapsheet.right !== "") {
					arr.geoLayerList.push(mapsheet.right);
				}
				var errorLayerName;
				if (mapsheet.error !== undefined && mapsheet.error !== null && mapsheet.error !== "") {
					errorLayerName = mapsheet.error;
				}

				if (errorLayerName !== undefined && arr.geoLayerList.length > 0) {
					// this.loadGroupLayer($(that.list).jstree(true),
					// this.options.layerInfoURL, this.options.groupInfoURL,
					// this.options.featureWMSURL, this.options.featureWFSURL,
					// arr,
					// arr.geoLayerList.length - 1,
					// errorLayerName, this.loadChildrenLayer);
					var glayers = new ol.Collection();
					var group = new ol.layer.Group({
						layers : glayers
					});
					group.set("name", "Validator Layers");
					group.set("type", "Group");

					this._import_fake_group($(that.list).jstree(true), group, this.options.groupInfoURL, this.options.featureWMSURL,
							this.options.featureWFSURL, arr, arr.geoLayerList.length - 1, errorLayerName, this.loadErrorLayer,
							this.inputLayer, this);
				}
			},
			_create_navigator : function() {
				var that = this;
				var prevIcon = $("<span>").addClass("glyphicon").addClass("glyphicon-backward"), nextIcon = $("<span>").addClass(
						"glyphicon").addClass("glyphicon-forward");
				var btnPrev = $("<button>").addClass("gitbuilder-qaedit-navigator-prev").addClass("btn").addClass("btn-default").append(
						prevIcon), btnNext = $("<button>").addClass("gitbuilder-qaedit-navigator-next").addClass("btn").addClass(
						"btn-default").append(nextIcon);
				$(document).on("click", this.eventNamespace + " .gitbuilder-qaedit-navigator-prev", function() {
					that.options.editingTool.attrPop.close();
					that.prevError();
				});
				$(document).on("click", this.eventNamespace + " .gitbuilder-qaedit-navigator-next", function() {
					that.options.editingTool.attrPop.close();
					that.nextError();
				});
				var td1 = $("<div>").css({
					"width" : "100px",
					"display" : "inline-block"
				}).append(btnPrev), td3 = $("<div>").css({
					"width" : "100px",
					"display" : "inline-block"
				}).append(btnNext);
				this.td2 = $("<div>").css({
					"display" : "inline-block"
				});
				var tr1 = $("<div>").addClass("text-center").append(td1).append(this.td2).append(td3);
				var thead = $("<div>").css({
					"margin-bottom" : "10px"
				}).append(tr1);
				var xSpan = $("<span>").attr({
					"aria-hidden" : "true"
				}).append("&times;");
				var xBtn = $("<button>").click(function() {
					$(that.naviWindow).hide();
				}).attr({
					"data-dismiss" : "modal",
					"aria-label" : "Close"
				}).css({
					"display" : "inline-block",
					"float" : "right",
					"padding" : "0",
					"margin" : "0",
					"color" : "#ccc",
					"border" : "none",
					"background-color" : "transparent",
					"cursor" : "pointer",
					"outline" : "none",
					"color" : "#ccc"
				}).append(xSpan);

				var title = $("<span>").text("Error Navigator");
				this.tbody = $("<tbody>");
				var tb = $("<table>").addClass("table").append(this.tbody);
				var pbd = $("<div>").addClass("panel-body").append(thead).append(tb);
				var phd = $("<div>").addClass("panel-heading").append(title).append(xBtn);
				var pdf = $("<div>").addClass("panel").addClass("panel-default").append(phd).append(pbd);
				this.naviWindow = $("<div>").css({
					"max-width" : "500px",
					"top" : "100px",
					"right" : 0,
					"position" : "absolute",
					"z-Index" : "999",
				}).addClass(this.eventNamespace.substr(1)).append(pdf);

				$("body").append(this.naviWindow);
				$(this.naviWindow).hide();
				$(this.naviWindow).draggable({
					appendTo : "body",
				});
			},
			_initNavigator : function() {
				this.source = this.error.getSource();
				this.features = this.source.getFeatures();
				this.count = 1;
				this.lid = this.error.get("id");
				if (!this.source.getFeatureById(this.lid + "." + this.count)) {
					console.log("no feature. maybe there is no error");
					swal('No Errors!', 'There is no error feature!', 'success')
					return;
				}
				this.showFeatureInfo(this.source.getFeatureById(this.lid + "." + this.count));
				this.openNavigator();
			},
			prevError : function() {
				if (this.count > 1 && this.count < this.features.length + 1) {
					this.count--;
				}
				var feature = this.source.getFeatureById(this.lid + "." + this.count);
				if (feature) {
					this.showFeatureInfo(feature);
				}
			},
			nextError : function() {
				if (this.count > 0 && this.count < this.features.length) {
					this.count++;
				}
				var feature = this.source.getFeatureById(this.lid + "." + this.count);
				if (feature) {
					this.showFeatureInfo(feature);
				}
			},
			showFeatureInfo : function(feature) {
				if (!feature) {
					console.log("no feature maybe there is no error");
					return;
				}
				var fid = feature.getId();
				$(this.td2).text(fid);
				var prop = feature.getProperties();
				var keys = Object.keys(prop);
				$(this.tbody).empty();
				for (var i = 0; i < keys.length; i++) {
					var td1 = $("<td>").text(keys[i]);
					if (keys[i] === this.options.linkKey) {
						var anc = $("<a>").addClass("gitbuilder-qaedit-navigator-link").attr({
							"href" : "#",
							"value" : prop[keys[i]]
						}).text("Move to feature");
						var td2 = $("<td>").attr("colspan", 2).append(anc);
					} else {
						var td2 = $("<td>").attr("colspan", 2).text(prop[keys[i]]);
					}
					var tr1 = $("<tr>").append(td1).append(td2);
					$(this.tbody).append(tr1);
				}
				var geom = feature.getGeometry();
				this.map.getView().fit(geom.getExtent(), this.map.getSize());
				this.map.getView().setZoom(16);
			},
			selectOriginalFeature : function(layer, fid) {
				var that = this;
				var params = {
					"service" : "WFS",
					"version" : "1.0.0",
					"request" : "GetFeature",
					"typeName" : layer,
					"outputformat" : "text/javascript",
					"featureID" : fid,
					"format_options" : "callback:getJson"
				};
				var addr = this.options.featureWFSURL;

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
						console.log(data);
						var features = new ol.format.GeoJSON().readFeatures(JSON.stringify(data));
						// that.options.editingTool.open();
						var sourceLayer = that.options.treeInstance.get_LayerByOLId(layer);

						var arr = {
							"geoLayerList" : [ layer ]
						}

						$.ajax({
							url : that.options.layerInfoURL,
							method : "POST",
							contentType : "application/json; charset=UTF-8",
							cache : false,
							data : JSON.stringify(arr),
							beforeSend : function() { // 호출전실행
								// loadImageShow();
							},
							traditional : true,
							success : function(data2, textStatus, jqXHR) {
								console.log(data2);
								if (Array.isArray(data2)) {
									for (var i = 0; i < data2.length; i++) {
										// var wms = new ol.layer.Tile({
										// source : new ol.source.TileWMS({
										// url : wmsURL,
										// params : {
										// 'LAYERS' : data2[i].lName,
										// 'TILED' : true,
										// 'FORMAT' : 'image/png8',
										// 'VERSION' : '1.1.0',
										// 'CRS' : 'EPSG:5186',
										// 'SRS' : 'EPSG:5186',
										// 'BBOX' :
										// data2[i].nbBox.minx.toString() + ","
										// + data2[i].nbBox.miny.toString() +
										// ","
										// + data2[i].nbBox.maxx.toString() +
										// "," + data2[i].nbBox.maxy.toString()
										// },
										// serverType : 'geoserver'
										// })
										// });
										var source = new ol.source.TileWMS({
											url : that.options.featureWMSURL,
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
										var git = {
											"validation" : false,
											"geometry" : data2[i].geomType,
											"editable" : true,
											"attribute" : data2[i].attInfo,
											"fake" : "child"
										}
										sourceLayer.set("git", git)
										console.log(sourceLayer);
										// wms.set("name",
										// that.options.treeInstance.get_node(data2[i].lName).text);
										// wms.set("id", data2[i].lName);
										// wms.setVisible(false);
										// console.log(wms.get("id"));
										// wms.set("type", "ImageTile");
										// wms.set("git", git);
										// arra.push(wms);
										// console.log(wms);

										that.options.treeInstance.deselect_all();
										that.options.treeInstance.select_node(sourceLayer.get("treeid"));
										that.options.editingTool.setLayer(sourceLayer);
										that.options.editingTool.setFeatures(features);
									}
								}
							}
						});

					}
				});
			},
			getErrorLayer : function(map) {
				return this.error;
			},
			open : function() {
				$(this.list).jstree(true).refresh();
				this.window.modal('show');
			},
			openNavigator : function() {
				$(this.naviWindow).show();
			},
			close : function() {
				this.window.modal('hide');
			},
			closeNavigator : function() {
				$(this.naviWindow).hide();
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