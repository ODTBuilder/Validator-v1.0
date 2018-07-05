/**
 * 
 * @name $.jstree.defaults.functionmarker
 * @plugin functionmarker
 * @comment 적용중인 기능 표시
 * @author SOYIJUN
 */
$.jstree.defaults.functionmarker = {
// "snap" : "fa fa-magnet"
};

$.jstree.plugins.functionmarker = function(options, parent) {
	this.init = function(el, options) {
		this._data.functionmarker = {};
		var optKeys = Object.keys(options.functionmarker);
		for (var i = 0; i < optKeys.length; i++) {
			this._data.functionmarker[optKeys[i]] = {
				"list" : [],
				"icon" : options.functionmarker[optKeys[i]]
			};
		}
		console.log(this._data.functionmarker);
		parent.init.call(this, el, options);
	};
	this.refresh = function(skip_loading, forget_state) {
		parent.refresh.call(this, skip_loading, forget_state);
	};
	this.bind = function() {
		this.element.on('model.jstree', $.proxy(function(e, data) {
			var m = this._model.data, dpc = data.nodes, i, j, c = 'default', k, optKeys = Object.keys(this._data.functionmarker);
			for (i = 0, j = dpc.length; i < j; i++) {
				for (var k = 0; k < optKeys.length; k++) {
					var list = this._data.functionmarker[optKeys[k]].list;
					if (list.indexOf(m[dpc[i]].id) !== -1) {
						m[dpc[i]].state[optKeys[k]] = true;
					} else {
						m[dpc[i]].state[optKeys[k]] = false;
					}
				}
			}
		}, this)).on('delete_node_layer.jstree', $.proxy(function(e, data) {
			console.log("delete layer");
			var optKeys = Object.keys(this._data.functionmarker), node = data.node;
			for (var k = 0; k < optKeys.length; k++) {
				var list = this._data.functionmarker[optKeys[k]].list;
				for (var l = 0; l < list.length; l++) {
					if (list.indexOf(node.id) !== -1) {
						list.splice(list.indexOf(node.id), 1);
					}
				}
			}
		}, this));

		parent.bind.call(this);
	};
	this.redraw_node = function(obj, deep, is_callback, force_render) {
		obj = parent.redraw_node.apply(this, arguments);
		if (obj) {
			var fnmks = Object.keys(this._data.functionmarker);
			for (var i = 0; i < fnmks.length; i++) {
				var nobj = this.get_node(obj.id);
				if (nobj.state[fnmks[i]]) {
					var ic = $("<i>").attr({
						"role" : "presentation"
					}).addClass("jstree-icon").addClass("jstree-themeicon-custom").addClass(this._data.functionmarker[fnmks[i]].icon);
					$(obj.childNodes[1]).append(ic);
				}
			}
		}
		return obj;
	};
	/**
	 * set flag
	 * 
	 * @name show_visibilityes()
	 * @plugin visibility
	 */
	this.set_flag = function(obj, funcname, flag) {
		obj.state[funcname] = flag;
		if (flag) {
			var list = this._data.functionmarker[funcname].list;
			if (list.indexOf(obj.id) === -1) {
				list.push(obj.id);
				this.redraw_node(obj.id);
			}
		} else {
			var list = this._data.functionmarker[funcname].list;
			if (list.indexOf(obj.id) !== -1) {
				list.splice(list.indexOf(obj.id), 1);
				this.redraw_node(obj.id);
			}
		}
	};
};
// include the functionmarker plugin by default
// $.jstree.defaults.plugins.push("functionmarker");
// ============================================================================
/**
 * stores all defaults for the geoserver plugin
 * 
 * @name $.jstree.defaults.geoserver
 * @plugin geoserver
 */
$.jstree.defaults.geoserver = {
	/**
	 * 레이어가 편입될 ol.Map객체
	 */
	map : undefined,
	/**
	 * 유저정보
	 */
	user : undefined,
	/**
	 * 레이어 정보
	 */
	layerInfo : undefined,

	layerInfoURL : undefined,
	downloadNGIDXF : undefined,
	downloadGeoserver : undefined
};

$.jstree.plugins.geoserver = function(options, parent) {
	var that = this;

	this.init = function(el, options) {
		this._data.geoserver = {};
		parent.init.call(this, el, options);
	};
	this.bind = function() {
		parent.bind.call(this);
		this._data.geoserver.map = this.settings.geoserver.map;
		this._data.geoserver.user = this.settings.geoserver.user;
		this._data.geoserver.layerInfo = this.settings.geoserver.layerInfo;
		this._data.geoserver.layerInfoURL = this.settings.geoserver.layerInfoURL;
		this._data.geoserver.groupLayerInfoURL = this.settings.geoserver.groupLayerInfoURL;
		this._data.geoserver.createLayer = this.settings.geoserver.createLayer;
		this._data.geoserver.deleteLayer = this.settings.geoserver.deleteLayer;
		this._data.geoserver.downloadNGIDXF = this.settings.geoserver.downloadNGIDXF;
		this._data.geoserver.downloadGeoserver = this.settings.geoserver.downloadGeoserver;
		this._data.geoserver.clientRefer = this.settings.geoserver.clientRefer;
		this._data.geoserver.WMSLayerURL = this.settings.geoserver.WMSLayerURL;
	};
	this.import_fake_group_notload = function(obj) {
		// // =======================================
		var that = this;
		var parentLayer;
		var farr = {
			"geoLayerList" : obj.arr
		}
		console.log(JSON.stringify(farr));
		var parentParam;
		$.ajax({
			url : that._data.geoserver.groupLayerInfoURL,
			method : "POST",
			contentType : "application/json; charset=UTF-8",
			cache : false,
			data : JSON.stringify(farr),
			beforeSend : function() { // 호출전실행
				$("body").css("cursor", "wait");
			},
			complete : function() {
				$("body").css("cursor", "default");
			},
			traditional : true,
			success : function(data, textStatus, jqXHR) {
				console.log(data);
				if (Array.isArray(data)) {
					for (var i = 0; i < data.length; i++) {
						var wms = new ol.layer.Tile({
							source : new ol.source.TileWMS({
								url : that._data.geoserver.WMSLayerURL,
								params : {
									'TIME' : Date.now(),
									'LAYERS' : obj.refer.get_node(data[i].name).children.toString(),
									'TILED' : true,
									'FORMAT' : 'image/png8',
									'VERSION' : '1.1.0',
									'CRS' : that._data.geoserver.map.getView().getProjection().getCode(),
									'SRS' : that._data.geoserver.map.getView().getProjection().getCode(),
									'BBOX' : data[i].bbox.minx.toString() + "," + data[i].bbox.miny.toString() + ","
											+ data[i].bbox.maxx.toString() + "," + data[i].bbox.maxy.toString()
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
							var dtype = id.substring(id.split("_", 4).join("_").length + 1);
							var geom;
							switch (dtype) {
							case "LWPOLYLINE":
								geom = "LineString";
								break;
							case "POLYLINE":
								geom = "LineString";
								break;
							case "POINT":
								geom = "Point";
								break;
							case "MULTIPOINT":
								geom = "MultiPoint";
								break;
							case "INSERT":
								geom = "Point";
								break;
							case "POLYGON":
								geom = "Polygon";
								break;
							case "MULTIPOLYGON":
								geom = "MultiPolygon";
								break;
							case "LINESTRING":
								geom = "LineString";
								break;
							case "MULTILINESTRING":
								geom = "MultiLineString";
								break;
							case "TEXT":
								geom = "Point";
								break;

							default:
								break;
							}
							var gchild = {
								"validation" : false,
								"editable" : true,
								"fake" : "child",
								"geometry" : geom
							}
							layer.set("git", gchild);
							layer.set("id", id);
							layer.set("name", name);
							layers.push(layer);
						}
						var getPosition = function(str, subString, index) {
							return str.split(subString, index).join(subString).length;
						};
						var id = data[i].name.replace(/(\s*)/g, '');
						var format = id.substring((getPosition(id, "_", 1) + 1), getPosition(id, "_", 2));
						var mapsheet = new gb.mapsheet.Mapsheet({
							id : data[i].name.replace(/(\s*)/g, ''),
							number : obj.refer.get_node(data[i].name).text.replace(/(\s*)/g, ''),
							format : format
						});
						var git = {
							"validation" : false,
							"geometry" : data[i].geomType,
							"editable" : true,
							"fake" : "parent",
							"layers" : layers,
							"information" : mapsheet
						}

						wms.set("name", obj.refer.get_node(data[i].name).text);
						wms.set("id", data[i].name);
						wms.set("git", git);
						var mapLayers = that._data.geoserver.map.getLayers();

						for (var j = 0; j < mapLayers.getLength(); j++) {
							if (mapLayers.item(j).get("id") === wms.get("id") && mapLayers.item(j) instanceof ol.layer.Tile
									&& mapLayers.item(j).get("git").hasOwnProperty("fake")) {
								that._data.geoserver.map.removeLayer(mapLayers.item(j));
								break;
							}
						}
						console.log(wms);
						// wms.set("type", "ImageTile");
						that._data.geoserver.map.addLayer(wms);
						$("body").css("cursor", "default");
					}
				}
			}
		});
	};
	this.import_fake_image_notload = function(obj) {
		// // =======================================
		var that = this;
		var parentLayer;
		var farr = {
			"geoLayerList" : [ obj.parent ]
		}
		console.log(JSON.stringify(farr));
		$.ajax({
			url : that._data.geoserver.groupLayerInfoURL,
			method : "POST",
			contentType : "application/json; charset=UTF-8",
			cache : false,
			data : JSON.stringify(farr),
			beforeSend : function() { // 호출전실행
				$("body").css("cursor", "wait");
			},
			complete : function() {
				$("body").css("cursor", "default");
			},
			traditional : true,
			success : function(data, textStatus, jqXHR) {
				console.log(data);
				if (Array.isArray(data)) {
					var arra = [];
					for (var i = 0; i < data.length; i++) {
						var wms = new ol.layer.Tile({
							source : new ol.source.TileWMS({
								url : that._data.geoserver.WMSLayerURL,
								params : {
									'TIME' : Date.now(),
									'LAYERS' : obj.arr.toString(),
									// 'LAYERS' :
									// that._data.geoserver.user
									// +
									// ":" + data[i].name,
									'TILED' : true,
									'FORMAT' : 'image/png8',
									'VERSION' : '1.1.0',
									'CRS' : that._data.geoserver.map.getView().getProjection().getCode(),
									'SRS' : that._data.geoserver.map.getView().getProjection().getCode(),
									'BBOX' : data[i].bbox.minx.toString() + "," + data[i].bbox.miny.toString() + ","
											+ data[i].bbox.maxx.toString() + "," + data[i].bbox.maxy.toString()
								},
								serverType : 'geoserver'
							})
						});

						var layers = new ol.Collection();
						for (var j = 0; j < obj.arr.length; j++) {
							var layer = new ol.layer.Layer({
								opacity : 1,
								visible : true
							});
							var id = obj.arr[j];
							var name = id.substring((id.split("_", 3).join("_").length) + 1, id.split("_", 4).join("_").length);
							var dtype = id.substring(id.split("_", 4).join("_").length + 1);
							var geom;
							switch (dtype) {
							case "LWPOLYLINE":
								geom = "LineString";
								break;
							case "POLYLINE":
								geom = "LineString";
								break;
							case "POINT":
								geom = "Point";
								break;
							case "MULTIPOINT":
								geom = "MultiPoint";
								break;
							case "INSERT":
								geom = "Point";
								break;
							case "POLYGON":
								geom = "Polygon";
								break;
							case "MULTIPOLYGON":
								geom = "MultiPolygon";
								break;
							case "LINESTRING":
								geom = "LineString";
								break;
							case "MULTILINESTRING":
								geom = "MultiLineString";
								break;
							case "TEXT":
								geom = "Point";
								break;

							default:
								break;
							}
							var gchild = {
								"validation" : false,
								"editable" : true,
								"fake" : "child",
								"geometry" : geom
							}
							layer.set("git", gchild);
							layer.set("id", id);
							layer.set("name", name);
							layers.push(layer);
						}
						var getPosition = function(str, subString, index) {
							return str.split(subString, index).join(subString).length;
						};
						var id = data[i].name.replace(/(\s*)/g, '');
						var format = id.substring((getPosition(id, "_", 1) + 1), getPosition(id, "_", 2));
						var mapsheet = new gb.mapsheet.Mapsheet({
							id : data[i].name.replace(/(\s*)/g, ''),
							number : obj.refer.get_node(data[i].name).text.replace(/(\s*)/g, ''),
							format : format
						});
						var git = {
							"validation" : false,
							"editable" : true,
							"fake" : "parent",
							"layers" : layers,
							"information" : mapsheet
						}
						wms.set("name", obj.refer.get_node(data[i].name).text);
						wms.set("id", data[i].name);
						wms.set("git", git);
						// wms.set("type", "ImageTile");
						var mapLayers = that._data.geoserver.map.getLayers();
						var flag = true;
						var newCollection = [];
						for (var j = 0; j < mapLayers.getLength(); j++) {
							if (mapLayers.item(j).get("id") === obj.parent && mapLayers.item(j) instanceof ol.layer.Tile
									&& mapLayers.item(j).get("git").hasOwnProperty("fake")) {

								var befParams = mapLayers.item(j).getSource().getParams();
								var git = mapLayers.item(j).get("git");
								var lid = mapLayers.item(j).get("id");
								var lname = mapLayers.item(j).get("name");
								// 있다면 구 그룹의 콜렉션과 신 그룹의 콜렉션을
								// 비교
								var befCollection = mapLayers.item(j).get("git").layers;
								for (var l = 0; l < layers.getLength(); l++) {
									var dupl = false;
									for (var k = 0; k < befCollection.getLength(); k++) {
										if (layers.item(l).get("id") === befCollection.item(k).get("id")) {
											dupl = true;
										}
									}
									if (!dupl) {
										newCollection.push(layers.item(l));
									}
								}
								befCollection.extend(newCollection);
								var names = [];
								for (var i = 0; i < befCollection.getLength(); i++) {
									names.push(befCollection.item(i).get("id"));
								}
								befParams["LAYERS"] = names.toString();
								befParams['TIME'] = Date.now();
								mapLayers.item(j).getSource().updateParams(befParams);
								// that._data.geoserver.clientRefer.refresh();
								var group = new ol.layer.Group({
									layers : befCollection
								});
								var wms2 = new ol.layer.Tile({
									source : new ol.source.TileWMS({
										url : that._data.geoserver.WMSLayerURL,
										params : befParams,
										serverType : 'geoserver'
									})
								});
								wms2.set("name", lname);
								wms2.set("id", lid);
								wms2.set("git", git);
								wms.set("type", "Group");
								that._data.geoserver.map.removeLayer(mapLayers.item(j));
								that._data.geoserver.map.addLayer(wms2);
								flag = false;
								// console.log(wms2);
								$("body").css("cursor", "default");
								break;
							}
						}
						if (flag) {
							var info = wms.get("git");
							info["layers"] = layers;
							console.log(wms);
							that._data.geoserver.map.addLayer(wms);
							$("body").css("cursor", "default");
						}
					}
				}
			}
		});
	};

	/**
	 * wms레이어를 트리형태로 임포트
	 * 
	 * @name $.jstree.plugins.geoserver.import_fake_image
	 * @plugin geoserver
	 * @author 소이준
	 */
	this.import_fake_image = function(obj) {
		// // =======================================
		var that = this;
		var parentLayer;
		var farr = {
			"geoLayerList" : [ obj.parent ]
		}
		console.log(JSON.stringify(farr));
		var parentParam;
		$.ajax({
			url : that._data.geoserver.groupLayerInfoURL,
			method : "POST",
			contentType : "application/json; charset=UTF-8",
			cache : false,
			data : JSON.stringify(farr),
			beforeSend : function() { // 호출전실행
				$("body").css("cursor", "wait");
			},
			complete : function() {
				$("body").css("cursor", "default");
			},
			traditional : true,
			success : function(data, textStatus, jqXHR) {
				console.log(data);
				// parentParam = data;
				if (Array.isArray(data)) {
					for (var i = 0; i < data.length; i++) {
						var wms = new ol.layer.Tile({
							source : new ol.source.TileWMS({
								url : that._data.geoserver.WMSLayerURL,
								params : {
									'TIME' : Date.now(),
									'LAYERS' : obj.arr.toString(),
									// 'LAYERS' :
									// that._data.geoserver.user +
									// ":" + data[i].name,
									'TILED' : true,
									'FORMAT' : 'image/png8',
									'VERSION' : '1.1.0',
									'CRS' : that._data.geoserver.map.getView().getProjection().getCode(),
									'SRS' : that._data.geoserver.map.getView().getProjection().getCode(),
									'BBOX' : data[i].bbox.minx.toString() + "," + data[i].bbox.miny.toString() + ","
											+ data[i].bbox.maxx.toString() + "," + data[i].bbox.maxy.toString()
								},
								serverType : 'geoserver'
							})
						});
						wms.set("name", obj.refer.get_node(data[i].name).text);
						wms.set("id", data[i].name);
						var git = {
							"validation" : false,
							"geometry" : data[i].geomType,
							"editable" : true,
							"fake" : "parent"
						}
						wms.set("git", git);
						parentLayer = wms;
						console.log(wms);
						// wms.set("type", "ImageTile");
						// that._data.geoserver.map.addLayer(wms);
					}
				}
				// =======================================
				var arr = {
					"geoLayerList" : obj.arr
				}
				var names = [];
				console.log(JSON.stringify(arr));
				$.ajax({
					url : that._data.geoserver.layerInfoURL,
					method : "POST",
					contentType : "application/json; charset=UTF-8",
					cache : false,
					data : JSON.stringify(arr),
					beforeSend : function() { // 호출전실행
						// loadImageShow();
					},
					complete : function() {
						$("body").css("cursor", "default");
					},
					traditional : true,
					success : function(data2, textStatus, jqXHR) {
						console.log(data2);
						if (Array.isArray(data2)) {
							var arra = [];
							for (var i = 0; i < data2.length; i++) {
								var wms = new ol.layer.Tile({
									source : new ol.source.TileWMS({
										url : that._data.geoserver.WMSLayerURL,
										params : {
											'TIME' : Date.now(),
											'LAYERS' : data2[i].lName,
											'TILED' : true,
											'FORMAT' : 'image/png8',
											'VERSION' : '1.1.0',
											'CRS' : that._data.geoserver.map.getView().getProjection().getCode(),
											'SRS' : that._data.geoserver.map.getView().getProjection().getCode(),
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
								wms.set("name", obj.refer.get_node(data2[i].lName).text);
								wms.set("id", data2[i].lName);
								// wms.setVisible(false);
								console.log(wms.get("id"));
								// wms.set("type", "ImageTile");
								wms.set("git", git);
								arra.push(wms);
								console.log(wms);
							}
							var mapLayers = that._data.geoserver.map.getLayers();
							var flag = true;
							var newCollection = [];
							// 현재 맵에 같은 아이디의 타일레이어가 있는지
							for (var j = 0; j < mapLayers.getLength(); j++) {
								if (mapLayers.item(j).get("id") === obj.parent && mapLayers.item(j) instanceof ol.layer.Tile) {
									var befParams = mapLayers.item(j).getSource().getParams();
									var git = mapLayers.item(j).get("git");
									var lid = mapLayers.item(j).get("id");
									var lname = mapLayers.item(j).get("name");
									// 있다면 구 그룹의 콜렉션과 신 그룹의 콜렉션을 비교
									var befCollection = mapLayers.item(j).get("git").layers;
									for (var l = 0; l < arra.length; l++) {
										var dupl = false;
										for (var k = 0; k < befCollection.getLength(); k++) {
											if (arra[l].get("id") === befCollection.item(k).get("id")) {
												dupl = true;
											}
										}
										if (!dupl) {
											newCollection.push(arra[l]);
										}
									}
									befCollection.extend(newCollection);
									var names = [];
									for (var i = 0; i < befCollection.getLength(); i++) {
										names.push(befCollection.item(i).get("id"));
									}
									befParams["LAYERS"] = names.toString();
									befParams['TIME'] = Date.now();
									// var group = new
									// ol.layer.Group({
									// layers : befCollection
									// });
									var wms2 = new ol.layer.Tile({
										source : new ol.source.TileWMS({
											url : that._data.geoserver.WMSLayerURL,
											params : befParams,
											serverType : 'geoserver'
										})
									});
									wms2.set("name", lname);
									wms2.set("id", lid);
									wms2.set("git", git);
									// wms.set("type", "Group");
									that._data.geoserver.map.removeLayer(mapLayers.item(j));
									that._data.geoserver.map.addLayer(wms2);
									flag = false;
									break;
								}
							}
							if (flag) {
								// var group = new ol.layer.Group({
								// layers : arra
								// });
								var info = parentLayer.get("git");
								info["layers"] = new ol.Collection().extend(arra);
								console.log(parentLayer);
								that._data.geoserver.map.addLayer(parentLayer);
								// group.set("name",
								// obj.refer.get_node(obj.parent).text);
								// group.set("id", obj.parent);
								// group.set("type", "Group");
								// that._data.geoserver.map.addLayer(group);
							}
							$("body").css("cursor", "default");
						}
					}
				});
			}
		});
	};

	/**
	 * wms레이어를 클라이언트로 임포트
	 * 
	 * @name $.jstree.plugins.geoserver.import_image
	 * @plugin geoserver
	 * @author 소이준
	 */
	this.import_image = function(obj) {
		var that = this;
		var arr = {
			"geoLayerList" : obj.arr
		}
		console.log(JSON.stringify(arr));
		$.ajax({
			url : that._data.geoserver.layerInfoURL,
			method : "POST",
			contentType : "application/json; charset=UTF-8",
			cache : false,
			data : JSON.stringify(arr),
			beforeSend : function() { // 호출전실행
				// loadImageShow();
			},
			complete : function() {
				$("body").css("cursor", "default");
			},
			traditional : true,
			success : function(data, textStatus, jqXHR) {
				console.log(data);
				if (Array.isArray(data)) {
					var arra = [];
					for (var i = 0; i < data.length; i++) {
						var wms = new ol.layer.Tile({
							source : new ol.source.TileWMS({
								url : that._data.geoserver.WMSLayerURL,
								params : {
									'TIME' : Date.now(),
									'LAYERS' : data[i].lName,
									'TILED' : true,
									'FORMAT' : 'image/png8',
									'VERSION' : '1.1.0',
									'CRS' : that._data.geoserver.map.getView().getProjection().getCode(),
									'SRS' : that._data.geoserver.map.getView().getProjection().getCode(),
									'BBOX' : data[i].nbBox.minx.toString() + "," + data[i].nbBox.miny.toString() + ","
											+ data[i].nbBox.maxx.toString() + "," + data[i].nbBox.maxy.toString()
								},
								serverType : 'geoserver'
							})
						});
						var git = {
							"validation" : false,
							"geometry" : data[i].geomType,
							"editable" : true,
							"attribute" : data[i].attInfo
						}
						// wms.set("name",
						// obj.refer.get_node(data[i].lName).text);
						wms.set("name", data[i].lName);
						wms.set("id", data[i].lName);
						console.log(wms.get("id"));
						// wms.set("type", "ImageTile");
						wms.set("git", git);
						arra.push(wms);
					}
					var mapLayers = that._data.geoserver.map.getLayers();
					var flag = true;
					var newCollection = [];
					// 현재 맵에 같은 아이디의 그룹레이어가 있는지
					for (var j = 0; j < mapLayers.getLength(); j++) {
						if (mapLayers.item(j).get("id") === obj.parent && mapLayers.item(j) instanceof ol.layer.Group) {
							// 있다면 구 그룹의 콜렉션과 신 그룹의 콜렉션을 비교
							var befCollection = mapLayers.item(j).getLayers();
							for (var l = 0; l < arra.length; l++) {
								var dupl = false;
								for (var k = 0; k < befCollection.getLength(); k++) {
									if (arra[l].get("id") === befCollection.item(k).get("id")) {
										dupl = true;
									}
								}
								if (!dupl) {
									newCollection.push(arra[l]);
								}
							}
							befCollection.extend(newCollection);
							var group = new ol.layer.Group({
								layers : befCollection
							});
							group.set("name", obj.refer.get_node(obj.parent).text);
							group.set("id", obj.parent);
							// group.set("type", "Group");
							that._data.geoserver.map.removeLayer(mapLayers.item(j));
							that._data.geoserver.map.addLayer(group);
							flag = false;
						}
					}
					if (flag) {
						var group = new ol.layer.Group({
							layers : arra
						});
						group.set("name", obj.refer.get_node(obj.parent).text);
						group.set("id", obj.parent);
						// group.set("type", "Group");
						that._data.geoserver.map.addLayer(group);
					}
				}
			}
		});
	};

	/**
	 * 그룹wms레이어를 트리형태로 임포트
	 * 
	 * @name $.jstree.plugins.geoserver.import_fake_group
	 * @plugin geoserver
	 * @author 소이준
	 */
	this.import_fake_group = function(obj) {
		// // =======================================
		var that = this;
		var parentLayer;
		var farr = {
			"geoLayerList" : obj.parent
		}
		console.log(JSON.stringify(farr));
		var parentParam;
		$.ajax({
			url : that._data.geoserver.groupLayerInfoURL,
			method : "POST",
			contentType : "application/json; charset=UTF-8",
			cache : false,
			data : JSON.stringify(farr),
			beforeSend : function() { // 호출전실행
				$("body").css("cursor", "wait");
			},
			complete : function() {
				$("body").css("cursor", "default");
			},
			traditional : true,
			success : function(data, textStatus, jqXHR) {
				console.log(data);
				// parentParam = data;
				if (Array.isArray(data)) {
					for (var i = 0; i < data.length; i++) {
						var wms = new ol.layer.Tile({
							source : new ol.source.TileWMS({
								url : that._data.geoserver.WMSLayerURL,
								params : {
									'TIME' : Date.now(),
									'LAYERS' : obj.refer.get_node(data[i].name).children.toString(),
									// 'LAYERS' :
									// that._data.geoserver.user +
									// ":" + data[i].name,
									'TILED' : true,
									'FORMAT' : 'image/png8',
									'VERSION' : '1.1.0',
									'CRS' : that._data.geoserver.map.getView().getProjection().getCode(),
									'SRS' : that._data.geoserver.map.getView().getProjection().getCode(),
									'BBOX' : data[i].bbox.minx.toString() + "," + data[i].bbox.miny.toString() + ","
											+ data[i].bbox.maxx.toString() + "," + data[i].bbox.maxy.toString()
								},
								serverType : 'geoserver'
							})
						});
						wms.set("name", obj.refer.get_node(data[i].name).text);
						wms.set("id", data[i].name);
						var git = {
							"validation" : false,
							"geometry" : data[i].geomType,
							"editable" : true,
							"fake" : "parent"
						}
						wms.set("git", git);
						parentLayer = wms;
						console.log(wms);
						// wms.set("type", "ImageTile");
						// that._data.geoserver.map.addLayer(wms);
					}
				}
				// =======================================
				for (var m = 0; m < data.length; m++) {
					var arr = {
						"geoLayerList" : obj.refer.get_node(data[m].name).children
					}
					var names = [];
					// console.log(JSON.stringify(arr));
					$.ajax({
						url : that._data.geoserver.layerInfoURL,
						method : "POST",
						contentType : "application/json; charset=UTF-8",
						cache : false,
						data : JSON.stringify(arr),
						beforeSend : function() { // 호출전실행
							// loadImageShow();
						},
						complete : function() {
							$("body").css("cursor", "default");
						},
						traditional : true,
						success : function(data2, textStatus, jqXHR) {
							console.log(data2);
							if (Array.isArray(data2)) {
								var arra = [];
								for (var i = 0; i < data2.length; i++) {
									var wms = new ol.layer.Tile({
										source : new ol.source.TileWMS({
											url : that._data.geoserver.WMSLayerURL,
											params : {
												'TIME' : Date.now(),
												'LAYERS' : data2[i].lName,
												'TILED' : true,
												'FORMAT' : 'image/png8',
												'VERSION' : '1.1.0',
												'CRS' : that._data.geoserver.map.getView().getProjection().getCode(),
												'SRS' : that._data.geoserver.map.getView().getProjection().getCode(),
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
									wms.set("name", obj.refer.get_node(data2[i].lName).text);
									wms.set("id", data2[i].lName);
									// wms.setVisible(false);
									console.log(wms.get("id"));
									// wms.set("type", "ImageTile");
									wms.set("git", git);
									arra.push(wms);
									console.log(wms);
								}
								var mapLayers = that._data.geoserver.map.getLayers();
								var flag = true;
								var newCollection = [];
								// 현재 맵에 같은 아이디의 타일레이어가 있는지
								for (var j = 0; j < mapLayers.getLength(); j++) {
									if (mapLayers.item(j).get("id") === obj.parent && mapLayers.item(j) instanceof ol.layer.Tile) {
										var befParams = mapLayers.item(j).getSource().getParams();
										var git = mapLayers.item(j).get("git");
										var lid = mapLayers.item(j).get("id");
										var lname = mapLayers.item(j).get("name");
										// 있다면 구 그룹의 콜렉션과 신 그룹의 콜렉션을
										// 비교
										var befCollection = mapLayers.item(j).get("git").layers;
										for (var l = 0; l < arra.length; l++) {
											var dupl = false;
											for (var k = 0; k < befCollection.getLength(); k++) {
												if (arra[l].get("id") === befCollection.item(k).get("id")) {
													dupl = true;
												}
											}
											if (!dupl) {
												newCollection.push(arra[l]);
											}
										}
										befCollection.extend(newCollection);
										var names = [];
										for (var i = 0; i < befCollection.getLength(); i++) {
											names.push(befCollection.item(i).get("id"));
										}
										befParams["LAYERS"] = names.toString();
										befParams['TIME'] = Date.now();
										// var group = new
										// ol.layer.Group({
										// layers : befCollection
										// });
										var wms2 = new ol.layer.Tile({
											source : new ol.source.TileWMS({
												url : that._data.geoserver.WMSLayerURL,
												params : befParams,
												serverType : 'geoserver'
											})
										});
										wms2.set("name", lname);
										wms2.set("id", lid);
										wms2.set("git", git);
										// wms.set("type", "Group");
										that._data.geoserver.map.removeLayer(mapLayers.item(j));
										that._data.geoserver.map.addLayer(wms2);
										flag = false;
										break;
									}
								}
								if (flag) {
									// var group = new
									// ol.layer.Group({
									// layers : arra
									// });
									var info = parentLayer.get("git");
									info["layers"] = new ol.Collection().extend(arra);
									console.log(parentLayer);
									that._data.geoserver.map.addLayer(parentLayer);
									// group.set("name",
									// obj.refer.get_node(obj.parent).text);
									// group.set("id", obj.parent);
									// group.set("type", "Group");
									// that._data.geoserver.map.addLayer(group);
								}
								$("body").css("cursor", "default");
							}
						}
					});
				}
			}
		});
	};

	/**
	 * 그룹wms레이어를 클라이언트로 임포트
	 * 
	 * @name $.jstree.plugins.geoserver.import_group
	 * @plugin geoserver
	 * @author 소이준
	 */
	this.import_group = function(obj) {
		var that = this;
		var arr = {
			"geoLayerList" : obj
		}
		console.log(JSON.stringify(arr));
		$.ajax({
			url : that._data.geoserver.groupLayerInfoURL,
			method : "POST",
			contentType : "application/json; charset=UTF-8",
			cache : false,
			data : JSON.stringify(arr),
			beforeSend : function() { // 호출전실행
				// loadImageShow();
			},
			complete : function() {
				$("body").css("cursor", "default");
			},
			traditional : true,
			success : function(data, textStatus, jqXHR) {
				console.log(data);
				if (Array.isArray(data)) {
					var arra = [];
					for (var i = 0; i < data.length; i++) {
						var wms = new ol.layer.Tile({
							source : new ol.source.TileWMS({
								url : that._data.geoserver.WMSLayerURL,
								params : {
									'TIME' : Date.now(),
									'LAYERS' : data[i].name,
									// 'LAYERS' :
									// that._data.geoserver.user +
									// ":" + data[i].name,
									'TILED' : true,
									'FORMAT' : 'image/png8',
									'VERSION' : '1.1.0',
									'CRS' : that._data.geoserver.map.getView().getProjection().getCode(),
									'SRS' : that._data.geoserver.map.getView().getProjection().getCode(),
									'BBOX' : data[i].bbox.minx.toString() + "," + data[i].bbox.miny.toString() + ","
											+ data[i].bbox.maxx.toString() + "," + data[i].bbox.maxy.toString()
								},
								serverType : 'geoserver'
							})
						});
						wms.set("name", data[i].name);
						wms.set("id", data[i].name);
						var git = {
							"validation" : false,
							"geometry" : data[i].geomType,
							"editable" : true
						}
						wms.set("git", git);
						// wms.set("type", "ImageTile");
						that._data.geoserver.map.addLayer(wms);
					}
				}
			}
		});
	};

	/**
	 * wfs레이어를 클라이언트로 임포트
	 * 
	 * @name $.jstree.plugins.geoserver.import_vector
	 * @plugin geoserver
	 * @author 소이준
	 */
	this.import_vector = function(obj) {
		var that = this;

		// options.map.addLayer(layer);
		console.log("vector");
	};

};
// $.jstree.defaults.plugins.push("geoserver");
// ============================================================================
/**
 * 지오서버 레이어 목록을 표시한다.
 * 
 * @class gb.tree.GeoServer
 * @memberof gb.tree
 * @param {Object}
 *            obj - 생성자 옵션을 담은 객체
 * @param {String}
 *            obj.append - 영역 본문이 삽입될 부모 노드의 ID 또는 Class
 * @author SOYIJUN
 * @date 2018.07.02
 * @version 0.01
 * 
 */
var gb;
if (!gb)
	gb = {};
if (!gb.tree)
	gb.tree = {};
gb.tree.GeoServer = function(obj) {
	var that = this;
	var options = obj ? obj : {};
	this.panelTitle = $("<p>").text("GeoServer").css({
		"margin" : "0",
		"float" : "left"
	});
	var addIcon = $("<i>").addClass("fas").addClass("fa-plus");
	this.addBtn = $("<button>").addClass("gb-button-clear").append(addIcon).css({
		"float" : "right"
	}).click(function() {
		that.openAddGeoServer();
	});
	var refIcon = $("<i>").addClass("fas").addClass("fa-sync-alt");
	this.refBtn = $("<button>").addClass("gb-button-clear").append(refIcon).css({
		"float" : "right"
	}).click(function() {
		that.getJSTree().refresh();
	});
	var searchIcon = $("<i>").addClass("fas").addClass("fa-search");
	this.searchBtn = $("<button>").addClass("gb-button-clear").append(searchIcon).css({
		"float" : "right"
	});
	this.panelHead = $("<div>").addClass("gb-article-head").append(this.panelTitle).append(this.searchBtn).append(this.refBtn).append(
			this.addBtn);
	this.panelBody = $("<div>").addClass("gb-article-body").css({
		"overflow-y" : "hidden"
	});
	this.panel = $("<div>").addClass("gb-article").css({
		"margin" : "0"
	}).append(this.panelHead).append(this.panelBody);
	if (typeof options.append === "string") {
		$(options.append).append(this.panel);
	}

	$(document).ready(function() {
		var parentHeight = $(that.panel).parent().innerHeight();
		var bodyHeight = parentHeight - 40;
		$(that.panelBody).outerHeight(bodyHeight);
	});
	$(window).resize(function() {
		var parentHeight = $(that.panel).parent().innerHeight();
		var bodyHeight = parentHeight - 40;
		$(that.panelBody).outerHeight(bodyHeight);
	});
	$(this.panelBody)
			.jstree(
					{
						"core" : {
							"animation" : 0,
							"check_callback" : true,
							"themes" : {
								"stripes" : true
							},
							'data' : {
								'url' : function() {
									return 'geoserver/getGeolayerCollectionTree.ajax?treeType=all';
								}
							}
						},
						"geoserver" : {
							"map" : map,
							"user" : "admin",
							"layerInfo" : layerInfo,
							"layerInfoURL" : "geoserver/getGeoLayerInfoList.ajax",
							"groupLayerInfoURL" : "geoserver/getGeoGroupLayerInfoList.ajax",
							"WMSLayerURL" : "http://175.116.181.42:9990/geoserver/wms",
							"createLayer" : createLayer,
							"deleteGroupLayer" : deleteGroupLayer,
							"deleteLayer" : deleteLayer,
							"downloadNGIDXF" : "fileExport/fileExport.ajax",
							"downloadGeoserver" : "geoserver/downloadRequest.do",
							"clientRefer" : $('#builderClientLayer').jstreeol3(true)
						},
						"search" : {
							show_only_matches : true
						},
						"contextmenu" : {
							items : function(o, cb) { // Could be an object
								// directly
								return {
									"import" : {
										"separator_before" : true,
										"icon" : "fa fa-download",
										"separator_after" : true,
										"label" : "Import",
										"action" : false,
										"submenu" : {
											"image" : {
												"separator_before" : false,
												"icon" : "fa fa-file-image-o",
												"separator_after" : false,
												"label" : "Image",
												"action" : function(data) {
													var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
													var arr = inst.get_selected();
													var wmsInfo = {
														"refer" : inst,
														"arr" : arr,
														"parent" : inst.get_parent(obj)
													}
													inst.import_image(wmsInfo);
													/*
													 * if (obj.type ===
													 * "n_ngi_layer_pt" ||
													 * obj.type ===
													 * "n_ngi_layer_ln" ||
													 * obj.type ===
													 * "n_ngi_layer_pg" ||
													 * obj.type ===
													 * "n_ngi_layer_txt") { var
													 * arr =
													 * inst.get_selected(); if
													 * (inst.get_node(inst.get_parent(obj)).type
													 * === "n_ngi_group") { var
													 * wmsInfo = { "refer" :
													 * inst, "arr" : arr,
													 * "parent" :
													 * inst.get_parent(obj) } //
													 * inst.import_fake_image(wmsInfo);
													 * inst.import_fake_image_notload(wmsInfo); } }
													 * else if (obj.type ===
													 * "n_dxf_layer_arc" ||
													 * obj.type ===
													 * "n_dxf_layer_cir" ||
													 * obj.type ===
													 * "n_dxf_layer_ins" ||
													 * obj.type ===
													 * "n_dxf_layer_lpl" ||
													 * obj.type ===
													 * "n_dxf_layer_pl" ||
													 * obj.type ===
													 * "n_dxf_layer_txt") { var
													 * arr =
													 * inst.get_selected(); if
													 * (inst.get_node(inst.get_parent(obj)).type
													 * === "n_dxf_group") { var
													 * wmsInfo = { "refer" :
													 * inst, "arr" : arr,
													 * "parent" :
													 * inst.get_parent(obj) } //
													 * inst.import_fake_image(wmsInfo);
													 * inst.import_fake_image_notload(wmsInfo); } }
													 * else if (obj.type ===
													 * "n_shp_layer_pt" ||
													 * obj.type ===
													 * "n_shp_layer_ln" ||
													 * obj.type ===
													 * "n_shp_layer_pg" ||
													 * obj.type ===
													 * "n_shp_layer_mpt" ||
													 * obj.type ===
													 * "n_shp_layer_mln" ||
													 * obj.type ===
													 * "n_shp_layer_mpg") { var
													 * arr =
													 * inst.get_selected(); if
													 * (inst.get_node(inst.get_parent(obj)).type
													 * === "n_shp_group") { var
													 * wmsInfo = { "refer" :
													 * inst, "arr" : arr,
													 * "parent" :
													 * inst.get_parent(obj) } //
													 * inst.import_fake_image(wmsInfo);
													 * inst.import_fake_image_notload(wmsInfo); } }
													 * else if (obj.type ===
													 * "n_ngi_group" || obj.type
													 * === "n_dxf_group" ||
													 * obj.type ===
													 * "n_shp_group") { var arr =
													 * inst.get_selected(); //
													 * var arr2 = []; // for
													 * (var i = 0; i <
													 * arr.length; // i++) { //
													 * arr2.push(inst.get_node(arr[i]).id); // }
													 * var obj = { "refer" :
													 * inst, "arr" : arr }; //
													 * inst.import_fake_group(obj);
													 * inst.import_fake_group_notload(obj); }
													 */
												}
											},
											"vector" : {
												"separator_before" : false,
												"icon" : "fa fa-file-excel-o",
												"separator_after" : false,
												"label" : "Vector",
												"_disabled" : true,
												"action" : function(data) {
													var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
													inst.import_vector();
												}
											}
										}
									},
									"create" : {
										"separator_before" : false,
										"icon" : "fa fa-asterisk",
										"separator_after" : false,
										"_disabled" : false, // (this.check("rename_node",
										// data.reference,
										// this.get_parent(data.reference),
										// "")),
										"label" : "Create",
										/*
										 * ! "shortcut" : 113, "shortcut_label" :
										 * 'F2', "icon" : "glyphicon
										 * glyphicon-leaf",
										 */
										"action" : false,
										"submenu" : {
											"mapsheet" : {
												"separator_before" : false,
												"_disabled" : function(data) {
													return !($.jstree.reference(data.reference).get_node(data.reference).type === "n_ngi"
															|| $.jstree.reference(data.reference).get_node(data.reference).type === "n_dxf" || $.jstree
															.reference(data.reference).get_node(data.reference).type === "n_shp")
												},
												"icon" : "fa fa-file-image-o",
												"separator_after" : false,
												"label" : "Map sheet",
												"action" : function(data) {
													var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
													inst._data.geoserver.createLayer.setReference(inst);
													if (obj.type === "n_ngi") {
														var arr = inst.get_selected();
														// var arr2 = [];
														// for (var i = 0; i <
														// arr.length;
														// i++) {
														// arr2.push(inst.get_node(arr[i]).id);
														// }
														var obj = {
															"refer" : inst,
															"arr" : arr
														};
														// inst.import_fake_group(obj);
														console.log("map sheet");
														inst._data.geoserver.createLayer.setForm("ngi", "mapsheet");
														inst._data.geoserver.createLayer.open();
													} else if (obj.type === "n_dxf") {
														inst._data.geoserver.createLayer.setForm("dxf", "mapsheet");
														inst._data.geoserver.createLayer.open();
													} else if (obj.type === "n_shp") {
														inst._data.geoserver.createLayer.setForm("shp", "mapsheet");
														inst._data.geoserver.createLayer.open();
													}
												}
											},
											"layer" : {
												"separator_before" : false,
												"_disabled" : function(data) {
													return !($.jstree.reference(data.reference).get_node(data.reference).type === "n_ngi_group"
															|| $.jstree.reference(data.reference).get_node(data.reference).type === "n_dxf_group" || $.jstree
															.reference(data.reference).get_node(data.reference).type === "n_shp_group")
												},
												"icon" : "fa fa-file-image-o",
												"separator_after" : false,
												"label" : "Layer",
												"action" : function(data) {
													var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
													inst._data.geoserver.createLayer.setReference(inst);
													inst._data.geoserver.createLayer.setClientReference(inst._data.geoserver.clientRefer);
													if (obj.type === "n_ngi_group") {
														inst._data.geoserver.createLayer.setForm("ngi", "layer", obj.text);
														inst._data.geoserver.createLayer.open();
													} else if (obj.type === "n_dxf_group") {
														inst._data.geoserver.createLayer.setForm("dxf", "layer", obj.text);
														inst._data.geoserver.createLayer.open();
													} else if (obj.type === "n_shp_group") {
														inst._data.geoserver.createLayer.setForm("shp", "layer", obj.text);
														inst._data.geoserver.createLayer.open();
													}
												}
											}
										}
									},
									"delete" : {
										"separator_before" : false,
										"icon" : "fa fa-trash",
										"separator_after" : false,
										"_disabled" : false, // (this.check("rename_node",
										// data.reference,
										// this.get_parent(data.reference),
										// "")),
										"label" : "Delete",
										/*
										 * ! "shortcut" : 113, "shortcut_label" :
										 * 'F2', "icon" : "glyphicon
										 * glyphicon-leaf",
										 */
										"action" : function(data) {
											var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
											var getPosition = function(str, subString, index) {
												return str.split(subString, index).join(subString).length;
											}
											var arr = inst.get_selected();
											var sameGroupParentDXF = {};
											var sameGroupParentNGI = {};
											var sameGroupParentSHP = {};
											var sameParent = [];
											var editingtCheck = [];
											for (var i = 0; i < arr.length; i++) {
												var node = inst.get_node(arr[i]);
												var parent = inst.get_node(node.parent);
												if (parent.type === "n_ngi_group") {
													if (!sameGroupParentNGI.hasOwnProperty(parent.id)) {
														sameGroupParentNGI[parent.id] = {};
													}
													sameGroupParentNGI[parent.id][node.id] = node;
													editingtCheck.push(node.id);
												} else if (parent.type === "n_dxf_group") {
													if (!sameGroupParentDXF.hasOwnProperty(parent.id)) {
														sameGroupParentDXF[parent.id] = {};
													}
													sameGroupParentDXF[parent.id][node.id] = node;
													editingtCheck.push(node.id);
												} else if (parent.type === "n_shp_group") {
													if (!sameGroupParentSHP.hasOwnProperty(parent.id)) {
														sameGroupParentSHP[parent.id] = {};
													}
													sameGroupParentSHP[parent.id][node.id] = node;
													editingtCheck.push(node.id);
												} else if (parent.type === "n_ngi") {
													var children = node.children;
													editingtCheck = children.concat(children);
													var substr = [];
													for (var i = 0; i < children.length; i++) {
														var position = getPosition(children[i], "_", 3);
														substr.push(node.children[i].substring(position + 1));
													}
													inst._data.geoserver.deleteLayer.addStructure("ngi", node.text, "all", substr);
												} else if (parent.type === "n_dxf") {
													var children = node.children;
													editingtCheck = children.concat(children);
													var substr = [];
													for (var i = 0; i < children.length; i++) {
														var position = getPosition(children[i], "_", 3);
														substr.push(node.children[i].substring(position + 1));
													}
													inst._data.geoserver.deleteLayer.addStructure("dxf", node.text, "all", substr);
												} else if (parent.type === "n_shp") {
													var children = node.children;
													editingtCheck = children.concat(children);
													var substr = [];
													for (var i = 0; i < children.length; i++) {
														var position = getPosition(children[i], "_", 3);
														substr.push(node.children[i].substring(position + 1));
													}
													inst._data.geoserver.deleteLayer.addStructure("shp", node.text, "all", substr);
												}
												// else if (parent.type ===
												// "n_shp" ||
												// parent.type === "e_ngi" ||
												// parent.type
												// === "e_dxf"
												// || parent.type === "e_shp") {
												// sameParent.push(node);
												// }
											}

											// if (sameParent.length > 0) {
											// var part = [];
											// for (var j = 0; j <
											// sameParent.length; j++) {
											// part.push(sameParent[j].id);
											// }
											// inst._data.geoserver.deleteLayer.addStructure("part",
											// part);
											// }
											var pkeys = Object.keys(sameGroupParentNGI);
											if (pkeys.length > 0) {
												for (var i = 0; i < pkeys.length; i++) {
													var parent = inst.get_node(pkeys[i]);
													var group = [];
													var ckeys = Object.keys(sameGroupParentNGI[pkeys[i]]);
													for (var j = 0; j < ckeys.length; j++) {
														var position = getPosition(ckeys[j], "_", 3);
														group.push(ckeys[j].substring(position + 1));
													}
													inst._data.geoserver.deleteLayer.addStructure("ngi", parent.text, Object
															.keys(sameGroupParentNGI[pkeys[i]]).length === parent.children.length ? "all"
															: "part", group);
												}
											}
											pkeys = Object.keys(sameGroupParentDXF);
											if (pkeys.length > 0) {
												for (var i = 0; i < pkeys.length; i++) {
													var parent = inst.get_node(pkeys[i]);
													var group = [];
													var ckeys = Object.keys(sameGroupParentDXF[pkeys[i]]);
													for (var j = 0; j < ckeys.length; j++) {
														var position = getPosition(ckeys[j], "_", 3);
														group.push(ckeys[j].substring(position + 1));
													}
													inst._data.geoserver.deleteLayer.addStructure("dxf", parent.text, Object
															.keys(sameGroupParentDXF[pkeys[i]]).length === parent.children.length ? "all"
															: "part", group);
												}
											}
											pkeys = Object.keys(sameGroupParentSHP);
											if (pkeys.length > 0) {
												for (var i = 0; i < pkeys.length; i++) {
													var parent = inst.get_node(pkeys[i]);
													var group = [];
													var ckeys = Object.keys(sameGroupParentSHP[pkeys[i]]);
													for (var j = 0; j < ckeys.length; j++) {
														var position = getPosition(ckeys[j], "_", 3);
														group.push(ckeys[j].substring(position + 1));
													}
													inst._data.geoserver.deleteLayer.addStructure("shp", parent.text, Object
															.keys(sameGroupParentSHP[pkeys[i]]).length === parent.children.length ? "all"
															: "part", group);
												}
											}
											console.log(inst._data.geoserver.deleteLayer.getStructure());
											inst._data.geoserver.deleteLayer.setReference(inst);
											inst._data.geoserver.deleteLayer.setClientReference(inst._data.geoserver.clientRefer);
											var isEditing = inst._data.geoserver.deleteLayer.isEditing(editingtCheck);
											inst._data.geoserver.deleteLayer.alert();

										}
									},
									"download" : {
										"separator_before" : true,
										"icon" : "fa fa-files-o",
										"separator_after" : true,
										"label" : "Download",
										"action" : false,
										"submenu" : {
											"ngi" : {
												"separator_before" : false,
												"icon" : "fa fa-file-image-o",
												"separator_after" : false,
												"label" : "NGI",
												"action" : function(data) {
													var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
													var arr = inst.get_selected();
													console.log(arr);
													var downFiles = [];
													for (var i = 0; i < arr.length; i++) {
														var node = inst.get_node(arr[i]);
														if (node.type === "n_ngi_group" || node.type === "n_dxf_group") {
															var obj = {
																"format" : "ngi",
																"type" : "mapsheet",
																"name" : node.id
															}
															console.log(obj);
															downFiles.push(obj);
														} else {
															var obj = {
																"format" : "ngi",
																"type" : "layer",
																"name" : node.id
															}
															console.log(obj);
															downFiles.push(obj);
														}
													}
													for (var j = 0; j < downFiles.length; j++) {
														var path = inst._data.geoserver.downloadNGIDXF;
														var target = "gitWindow";
														var form = document.createElement("form");
														form.setAttribute("method", "post");
														form.setAttribute("action", path);
														var keys = Object.keys(downFiles[j]);
														for (var k = 0; k < keys.length; k++) {
															var hiddenField = document.createElement("input");
															hiddenField.setAttribute("type", "hidden");
															hiddenField.setAttribute("name", keys[k]);
															hiddenField.setAttribute("value", downFiles[j][keys[k]]);
															form.appendChild(hiddenField);
														}
														form.target = target;
														document.body.appendChild(form);
														form.submit();
													}
												}
											},
											"dxf" : {
												"separator_before" : false,
												"icon" : "fa fa-file-excel-o",
												"separator_after" : false,
												"label" : "DXF",
												"action" : function(data) {
													var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
													var arr = inst.get_selected();
													console.log(arr);
													var downFiles = [];
													for (var i = 0; i < arr.length; i++) {
														var node = inst.get_node(arr[i]);
														if (node.type === "n_ngi_group" || node.type === "n_dxf_group") {
															var obj = {
																"format" : "dxf",
																"type" : "mapsheet",
																"name" : node.id
															}
															console.log(obj);
															downFiles.push(obj);
														} else {
															var obj = {
																"format" : "dxf",
																"type" : "layer",
																"name" : node.id
															}
															console.log(obj);
															downFiles.push(obj);
														}
													}
													for (var j = 0; j < downFiles.length; j++) {
														var path = inst._data.geoserver.downloadNGIDXF;
														var target = "_blank";
														var form = document.createElement("form");
														form.setAttribute("method", "post");
														form.setAttribute("action", path);
														var keys = Object.keys(downFiles[j]);
														for (var k = 0; k < keys.length; k++) {
															var hiddenField = document.createElement("input");
															hiddenField.setAttribute("type", "hidden");
															hiddenField.setAttribute("name", keys[k]);
															hiddenField.setAttribute("value", downFiles[j][keys[k]]);
															form.appendChild(hiddenField);
														}
														form.target = target;
														document.body.appendChild(form);
														form.submit();
													}
												}
											},
											"shp" : {
												"separator_before" : true,
												"icon" : "fa fa-file-excel-o",
												"separator_after" : false,
												"label" : "SHP",
												"action" : function(data) {
													var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
													var selected = inst.get_selected();
													var selectedObj = inst.get_selected(true);
													for (var i = 0; i < selectedObj.length; i++) {
														if (selectedObj[i].type === "n_ngi_group" || selectedObj[i].type === "n_dxf_group"
																|| selectedObj[i].type === "n_shp_group") {
															console.error("not support");
															return;
														}
													}
													var arr = {
														"geoLayerList" : selected
													}
													var names = [];
													$.ajax({
														url : inst._data.geoserver.layerInfoURL,
														method : "POST",
														contentType : "application/json; charset=UTF-8",
														cache : false,
														data : JSON.stringify(arr),
														beforeSend : function() { // 호출전실행
															// loadImageShow();
														},
														traditional : true,
														success : function(data, textStatus, jqXHR) {
															var path = inst._data.geoserver.downloadGeoserver;

															var target = "_blank";
															for (var i = 0; i < data.length; i++) {
																var params = {
																	"serviceType" : "wfs",
																	"version" : "1.0.0",
																	"outputformat" : "SHAPE-ZIP",
																	"typeName" : data[i].lName
																}
																// var qstr =
																// $.param(params);
																// var url =
																// path+"?"+qstr;
																// console.log(url);
																var form = document.createElement("form");
																form.setAttribute("method", "post");
																form.setAttribute("action", path);
																var keys = Object.keys(params);
																for (var j = 0; j < keys.length; j++) {
																	var hiddenField = document.createElement("input");
																	hiddenField.setAttribute("type", "hidden");
																	hiddenField.setAttribute("name", keys[j]);
																	hiddenField.setAttribute("value", params[keys[j]]);
																	form.appendChild(hiddenField);
																}
																form.target = target;
																document.body.appendChild(form);
																$(form).submit();
															}
														}
													});

												}
											},
											"gml2" : {
												"separator_before" : false,
												"icon" : "fa fa-file-excel-o",
												"separator_after" : false,
												"label" : "GML2",
												"action" : function(data) {
													var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
													if (obj.type === "n_ngi_group" || obj.type === "n_dxf_group") {
														console.error("not support");
														return;
													}
													var arr = {
														"geoLayerList" : [ obj.id ]
													}
													var names = [];
													$.ajax({
														url : inst._data.geoserver.layerInfoURL,
														method : "POST",
														contentType : "application/json; charset=UTF-8",
														cache : false,
														data : JSON.stringify(arr),
														beforeSend : function() { // 호출전실행
															// loadImageShow();
														},
														traditional : true,
														success : function(data, textStatus, jqXHR) {
															var path = inst._data.geoserver.downloadGeoserver;
															var target = "_blank";
															for (var i = 0; i < data.length; i++) {
																var params = {
																	"serviceType" : "wfs",
																	"version" : "1.0.0",
																	"outputformat" : "gml2",
																	"typeName" : data[i].lName
																}
																var form = document.createElement("form");
																form.setAttribute("method", "post");
																form.setAttribute("action", path);
																var keys = Object.keys(params);
																for (var j = 0; j < keys.length; j++) {
																	var hiddenField = document.createElement("input");
																	hiddenField.setAttribute("type", "hidden");
																	hiddenField.setAttribute("name", keys[j]);
																	hiddenField.setAttribute("value", params[keys[j]]);
																	form.appendChild(hiddenField);
																}
																form.target = target;
																document.body.appendChild(form);
																form.submit();
															}
														}
													});
												}
											},
											"gml3" : {
												"separator_before" : false,
												"icon" : "fa fa-file-excel-o",
												"separator_after" : false,
												"label" : "GML3",
												"action" : function(data) {
													var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
													if (obj.type === "n_ngi_group" || obj.type === "n_dxf_group") {
														console.error("not support");
														return;
													}
													var arr = {
														"geoLayerList" : [ obj.id ]
													}
													var names = [];
													$.ajax({
														url : inst._data.geoserver.layerInfoURL,
														method : "POST",
														contentType : "application/json; charset=UTF-8",
														cache : false,
														data : JSON.stringify(arr),
														beforeSend : function() { // 호출전실행
															// loadImageShow();
														},
														traditional : true,
														success : function(data, textStatus, jqXHR) {
															var path = inst._data.geoserver.downloadGeoserver;
															var target = "_blank";
															for (var i = 0; i < data.length; i++) {
																var params = {
																	"serviceType" : "wfs",
																	"version" : "1.0.0",
																	"outputformat" : "gml3",
																	"typeName" : data[i].lName
																}
																var form = document.createElement("form");
																form.setAttribute("method", "post");
																form.setAttribute("action", path);
																var keys = Object.keys(params);
																for (var j = 0; j < keys.length; j++) {
																	var hiddenField = document.createElement("input");
																	hiddenField.setAttribute("type", "hidden");
																	hiddenField.setAttribute("name", keys[j]);
																	hiddenField.setAttribute("value", params[keys[j]]);
																	form.appendChild(hiddenField);
																}
																form.target = target;
																document.body.appendChild(form);
																form.submit();
															}
														}
													});
												}
											},
											"json" : {
												"separator_before" : false,
												"icon" : "fa fa-file-excel-o",
												"separator_after" : false,
												"label" : "JSON",
												"action" : function(data) {
													var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
													if (obj.type === "n_ngi_group" || obj.type === "n_dxf_group") {
														console.error("not support");
														return;
													}
													var arr = {
														"geoLayerList" : [ obj.id ]
													}
													var names = [];
													$.ajax({
														url : inst._data.geoserver.layerInfoURL,
														method : "POST",
														contentType : "application/json; charset=UTF-8",
														cache : false,
														data : JSON.stringify(arr),
														beforeSend : function() { // 호출전실행
															// loadImageShow();
														},
														traditional : true,
														success : function(data, textStatus, jqXHR) {
															var path = inst._data.geoserver.downloadGeoserver;
															var target = "_blank";
															for (var i = 0; i < data.length; i++) {
																var params = {
																	"serviceType" : "wfs",
																	"version" : "1.0.0",
																	"outputformat" : "json",
																	"typeName" : data[i].lName
																}
																var form = document.createElement("form");
																form.setAttribute("method", "post");
																form.setAttribute("action", path);
																var keys = Object.keys(params);
																for (var j = 0; j < keys.length; j++) {
																	var hiddenField = document.createElement("input");
																	hiddenField.setAttribute("type", "hidden");
																	hiddenField.setAttribute("name", keys[j]);
																	hiddenField.setAttribute("value", params[keys[j]]);
																	form.appendChild(hiddenField);
																}
																form.target = target;
																document.body.appendChild(form);
																form.submit();
															}
														}
													});
												}
											},
											"csv" : {
												"separator_before" : false,
												"icon" : "fa fa-file-excel-o",
												"separator_after" : false,
												"label" : "CSV",
												"action" : function(data) {
													var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
													if (obj.type === "n_ngi_group" || obj.type === "n_dxf_group") {
														console.error("not support");
														return;
													}
													var arr = {
														"geoLayerList" : [ obj.id ]
													}
													var names = [];
													$.ajax({
														url : inst._data.geoserver.layerInfoURL,
														method : "POST",
														contentType : "application/json; charset=UTF-8",
														cache : false,
														data : JSON.stringify(arr),
														beforeSend : function() { // 호출전실행
															// loadImageShow();
														},
														traditional : true,
														success : function(data, textStatus, jqXHR) {
															var path = inst._data.geoserver.downloadGeoserver;
															console.log(path);
															var target = "_blank";
															for (var i = 0; i < data.length; i++) {
																var params = {
																	"serviceType" : "wfs",
																	"version" : "1.0.0",
																	"outputformat" : "csv",
																	"typeName" : data[i].lName
																}
																var form = document.createElement("form");
																form.setAttribute("method", "post");
																form.setAttribute("action", path);
																var keys = Object.keys(params);
																for (var j = 0; j < keys.length; j++) {
																	var hiddenField = document.createElement("input");
																	hiddenField.setAttribute("type", "hidden");
																	hiddenField.setAttribute("name", keys[j]);
																	hiddenField.setAttribute("value", params[keys[j]]);
																	form.appendChild(hiddenField);
																}
																form.target = target;
																document.body.appendChild(form);
																form.submit();
															}
														}
													});
												}
											},
											"png" : {
												"separator_before" : true,
												"icon" : "fa fa-file-excel-o",
												"separator_after" : false,
												"label" : "PNG",
												"action" : function(data) {
													var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
													if (obj.type === "n_ngi_group" || obj.type === "n_dxf_group") {
														console.error("not support");
														return;
													}
													var arr = {
														"geoLayerList" : [ obj.id ]
													}
													var names = [];
													$.ajax({
														url : inst._data.geoserver.layerInfoURL,
														method : "POST",
														contentType : "application/json; charset=UTF-8",
														cache : false,
														data : JSON.stringify(arr),
														beforeSend : function() { // 호출전실행
															// loadImageShow();
														},
														traditional : true,
														success : function(data, textStatus, jqXHR) {
															console.log(data);
															var path = inst._data.geoserver.downloadGeoserver;
															console.log(path);
															var target = "_blank";
															for (var i = 0; i < data.length; i++) {
																var params = {
																	"serviceType" : "wms",
																	"version" : "1.1.0",
																	"format" : "image/png",
																	"crs" : data[i].srs,
																	"bbox" : [ data[i].nbBox.minx, data[i].nbBox.miny, data[i].nbBox.maxx,
																			data[i].nbBox.maxy ],
																	"layers" : data[i].lName,
																	"width" : 1024,
																	"height" : 768
																}
																var form = document.createElement("form");
																form.setAttribute("method", "post");
																form.setAttribute("action", path);
																var keys = Object.keys(params);
																for (var j = 0; j < keys.length; j++) {
																	var hiddenField = document.createElement("input");
																	hiddenField.setAttribute("type", "hidden");
																	hiddenField.setAttribute("name", keys[j]);
																	hiddenField.setAttribute("value", params[keys[j]]);
																	form.appendChild(hiddenField);
																}
																form.target = target;
																document.body.appendChild(form);
																form.submit();
															}
														}
													});
												}
											}
										}
									},
									"properties" : {
										"separator_before" : true,
										"icon" : "fa fa-info-circle",
										"separator_after" : true,
										"label" : "Properties",
										"action" : false,
										"submenu" : {
											"geoserver" : {
												"separator_before" : false,
												"icon" : "fa fa-server",
												"separator_after" : false,
												"_disabled" : function(data) {
													return !($.jstree.reference(data.reference).get_node(
															$.jstree.reference(data.reference).get_node(data.reference).parent).type === "n_ngi_group")
												},
												"label" : "Geoserver",
												"action" : function(data) {
													var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
													if (obj.type === "n_ngi_layer_pt" || obj.type === "n_ngi_layer_ln"
															|| obj.type === "n_ngi_layer_pg" || obj.type === "n_ngi_layer_txt"
															|| obj.type === "n_dxf_layer_arc" || obj.type === "n_dxf_layer_cir"
															|| obj.type === "n_dxf_layer_ins" || obj.type === "n_dxf_layer_lpl"
															|| obj.type === "n_dxf_layer_pl" || obj.type === "n_dxf_layer_txt"
															|| obj.type === "e_dxf_layer" || obj.type === "e_ngi_layer"
															|| obj.type === "e_shp_layer") {
														inst._data.geoserver.layerInfo.setReference(inst);
														inst._data.geoserver.layerInfo.load(obj.id, obj.text);
													}
												}
											},
											"ngi" : {
												"separator_before" : false,
												"icon" : "fa fa-file-excel-o",
												"separator_after" : false,
												"_disabled" : function(data) {
													return !($.jstree.reference(data.reference).get_node(
															$.jstree.reference(data.reference).get_node(data.reference).parent).type === "n_ngi_group")
												},
												"label" : "NGI",
												"action" : function(data) {
													var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
												}
											},
											"dxf" : {
												"separator_before" : false,
												"icon" : "fa fa-file-excel-o",
												"separator_after" : false,
												"_disabled" : function(data) {
													return !($.jstree.reference(data.reference).get_node(data.reference).type === "n_ngi" || $.jstree
															.reference(data.reference).get_node(data.reference).type === "n_dxf")
												},
												"label" : "DXF",
												"action" : function(data) {
													var inst = $.jstree.reference(data.reference), obj = inst.get_node(data.reference);
												}
											}
										}
									}
								};
							}
						},
						types : {
							"#" : {
								"valid_children" : [ "default", "normal", "error", "generalization" ]
							},
							"default" : {
								"icon" : "fa fa-file-o",
								"valid_children" : [ "default" ]
							},
							"normal" : {
								"icon" : "fa fa-folder-o",
								"valid_children" : [ "n_ngi", "n_dxf", "n_shp" ]
							},
							"n_ngi" : {
								"icon" : "fa fa-folder-o",
								"valid_children" : [ "n_ngi_group" ]
							},
							"n_dxf" : {
								"icon" : "fa fa-folder-o",
								"valid_children" : [ "n_dxf_group" ]
							},
							"n_shp" : {
								"icon" : "fa fa-folder-o",
								"valid_children" : [ "n_shp_group" ]
							},
							// "ngi" : {
							// "icon" : "fa fa-folder-o",
							// "valid_children" : [ "n_ngi_group" ]
							// },
							// "dxf" : {
							// "icon" : "fa fa-folder-o",
							// "valid_children" : [ "n_dxf_group" ]
							// },
							// "shp" : {
							// "icon" : "fa fa-folder-o",
							// "valid_children" : [ "n_shp_layer" ]
							// },
							"n_ngi_group" : {
								"icon" : "fa fa-map-o",
								"valid_children" : [ "n_ngi_layer_pt", "n_ngi_layer_ln", "n_ngi_layer_pg", "n_ngi_layer_mpt",
										"n_ngi_layer_mln", "n_ngi_layer_mpg", "n_ngi_layer_txt" ]
							},
							"n_dxf_group" : {
								"icon" : "fa fa-map-o",
								"valid_children" : [ "n_dxf_layer_arc", "n_dxf_layer_cir", "n_dxf_layer_ins", "n_dxf_layer_lpl",
										"n_dxf_layer_pl", "n_dxf_layer_txt" ]
							},
							"n_shp_group" : {
								"icon" : "fa fa-map-o",
								"valid_children" : [ "n_shp_layer_pt", "n_shp_layer_ln", "n_shp_layer_pg", "n_shp_layer_mpt",
										"n_shp_layer_mln", "n_shp_layer_mpg" ]
							},
							// "n_ngi_layer" : {
							// "icon" : "fa fa-file-image-o",
							// "valid_children" : []
							// },
							"n_ngi_layer_pt" : {
								"icon" : "fa fa-circle",
								"valid_children" : []
							},
							"n_ngi_layer_ln" : {
								"icon" : "fa fa-minus",
								"valid_children" : []
							},
							"n_ngi_layer_pg" : {
								"icon" : "fa fa-square",
								"valid_children" : []
							},
							"n_ngi_layer_mpt" : {
								"icon" : "fa fa-circle",
								"valid_children" : []
							},
							"n_ngi_layer_mln" : {
								"icon" : "fa fa-minus",
								"valid_children" : []
							},
							"n_ngi_layer_mpg" : {
								"icon" : "fa fa-square",
								"valid_children" : []
							},
							"n_ngi_layer_txt" : {
								"icon" : "fa fa-font",
								"valid_children" : []
							},
							// "n_dxf_layer" : {
							// "icon" : "fa fa-file-image-o",
							// "valid_children" : []
							// },
							"n_dxf_layer_arc" : {
								"icon" : "fa fa-circle-o-notch",
								"valid_children" : []
							},
							"n_dxf_layer_cir" : {
								"icon" : "fa fa-circle-o",
								"valid_children" : []
							},
							"n_dxf_layer_ins" : {
								"icon" : "fa fa-map-pin",
								"valid_children" : []
							},
							"n_dxf_layer_lpl" : {
								"icon" : "fa fa-minus",
								"valid_children" : []
							},
							"n_dxf_layer_pl" : {
								"icon" : "fa-window-minimize",
								"valid_children" : []
							},
							"n_dxf_layer_txt" : {
								"icon" : "fa fa-font",
								"valid_children" : []
							},
							// "n_shp_layer" : {
							// "icon" : "fa fa-file-image-o",
							// "valid_children" : []
							// },
							"n_shp_layer_pt" : {
								"icon" : "fa fa-circle",
								"valid_children" : []
							},
							"n_shp_layer_ln" : {
								"icon" : "fa fa-minus",
								"valid_children" : []
							},
							"n_shp_layer_pg" : {
								"icon" : "fa fa-square",
								"valid_children" : []
							},
							"n_shp_layer_mpt" : {
								"icon" : "fa fa-circle",
								"valid_children" : []
							},
							"n_shp_layer_mln" : {
								"icon" : "fa fa-minus",
								"valid_children" : []
							},
							"n_shp_layer_mpg" : {
								"icon" : "fa fa-square",
								"valid_children" : []
							},
							"error" : {
								"icon" : "fa fa-folder-o",
								"valid_children" : [ "e_ngi", "e_dxf", "e_shp" ]
							},
							// "error" : {
							// "icon" : "fa fa-folder-o",
							// "valid_children" : [ "e_group", "e_piece" ]
							// },
							"e_ngi" : {
								"icon" : "fa fa-folder-o",
								"valid_children" : [ "e_ngi_layer" ]
							},
							"e_dxf" : {
								"icon" : "fa fa-folder-o",
								"valid_children" : [ "e_dxf_layer" ]
							},
							"e_shp" : {
								"icon" : "fa fa-folder-o",
								"valid_children" : [ "e_shp_layer" ]
							},
							// "e_group" : {
							// "icon" : "fa fa-folder-o",
							// "valid_children" : [ "e_layer" ]
							// },
							// "e_piece" : {
							// "icon" : "fa fa-folder-o",
							// "valid_children" : [ "e_layer_p" ]
							// },
							"e_ngi_layer" : {
								"icon" : "fa fa-file-image-o",
								"valid_children" : []
							},
							"e_dxf_layer" : {
								"icon" : "fa fa-file-image-o",
								"valid_children" : []
							},
							"e_shp_layer" : {
								"icon" : "fa fa-file-image-o",
								"valid_children" : []
							},
							// "e_layer_p" : {
							// "icon" : "fa fa-file-image-o",
							// "valid_children" : []
							// },
							"generalization" : {
								"icon" : "fa fa-folder-o",
								"valid_children" : [ "g_ngi_layer", "g_dxf_layer", "g_shp_layer" ]
							},
							"g_ngi" : {
								"icon" : "fa fa-folder-o",
								"valid_children" : [ "g_ngi_layer" ]
							},
							"g_dxf" : {
								"icon" : "fa fa-folder-o",
								"valid_children" : [ "g_dxf_layer" ]
							},
							"g_shp" : {
								"icon" : "fa fa-folder-o",
								"valid_children" : [ "g_shp_layer" ]
							},
							"g_ngi_layer" : {
								"icon" : "fa fa-file-image-o",
								"valid_children" : []
							},
							"g_dxf_layer" : {
								"icon" : "fa fa-file-image-o",
								"valid_children" : []
							},
							"g_shp_layer" : {
								"icon" : "fa fa-file-image-o",
								"valid_children" : []
							}
						},
						"plugins" : [ "contextmenu", "search", "state", "types", "geoserver" ]
					});
	this.jstree = $(this.panelBody).jstreeol3(true);

	var gName = $("<div>").text("Name: ").css({
		"display" : "table-cell",
		"width" : "20%",
		"text-align" : "right",
		"vertical-align" : "middle"
	});
	this.gNameInput = $("<input>").attr({
		"type" : "text"
	}).css({
		"width" : "83%",
		"border" : "none",
		"border-bottom" : "solid 1px #a9a9a9",
		"margin-left" : "8px"
	});
	var gNameInputDiv = $("<div>").append(this.gNameInput).css({
		"display" : "table-cell",
		"width" : "80%",
		"vertical-align" : "middle"
	});
	var gNameArea = $("<div>").append(gName).append(gNameInputDiv).css({
		"display" : "table-row"
	});

	var gURL = $("<div>").text("URL: ").css({
		"display" : "table-cell",
		"width" : "20%",
		"text-align" : "right",
		"vertical-align" : "middle"
	});
	this.gURLInput = $("<input>").attr({
		"type" : "text"
	}).css({
		"width" : "83%",
		"border" : "none",
		"border-bottom" : "solid 1px #a9a9a9",
		"margin-left" : "8px"
	});
	var gURLInputDiv = $("<div>").append(this.gURLInput).css({
		"display" : "table-cell",
		"width" : "80%",
		"vertical-align" : "middle"
	});
	var gURLArea = $("<div>").append(gURL).append(gURLInputDiv).css({
		"display" : "table-row"
	});

	var gID = $("<div>").text("ID: ").css({
		"display" : "table-cell",
		"width" : "20%",
		"text-align" : "right",
		"vertical-align" : "middle"
	});
	this.gIDInput = $("<input>").attr({
		"type" : "text"
	}).css({
		"width" : "83%",
		"border" : "none",
		"border-bottom" : "solid 1px #a9a9a9",
		"margin-left" : "8px"
	});
	var gIDInputDiv = $("<div>").append(this.gIDInput).css({
		"display" : "table-cell",
		"width" : "80%",
		"vertical-align" : "middle"
	});
	var gIDArea = $("<div>").append(gID).append(gIDInputDiv).css({
		"display" : "table-row"
	});

	var gPass = $("<div>").text("Password: ").css({
		"display" : "table-cell",
		"width" : "20%",
		"text-align" : "right",
		"vertical-align" : "middle"
	});
	this.gPassInput = $("<input>").attr({
		"type" : "password"
	}).css({
		"width" : "83%",
		"border" : "none",
		"border-bottom" : "solid 1px #a9a9a9",
		"margin-left" : "8px"
	});
	var gPassInputDiv = $("<div>").append(this.gPassInput).css({
		"display" : "table-cell",
		"width" : "80%",
		"vertical-align" : "middle"
	});
	var gPassArea = $("<div>").append(gPass).append(gPassInputDiv).css({
		"display" : "table-row"
	});

	var closeBtn = $("<button>").css({
		"float" : "right"
	}).addClass("gb-button").addClass("gb-button-default").text("Close").click(function() {
		that.closeAddGeoServer();
	});
	var okBtn = $("<button>").css({
		"float" : "right"
	}).addClass("gb-button").addClass("gb-button-primary").text("Add").click(function() {
		that.addGeoServer();
	});

	this.buttonArea = $("<span>").addClass("gb-modal-buttons").append(okBtn).append(closeBtn);
	this.modalFooter = $("<div>").addClass("gb-modal-footer").append(this.buttonArea);

	var gBody = $("<div>").append(gNameArea).append(gURLArea).append(gIDArea).append(gPassArea).css({
		"display" : "table",
		"padding" : "10px"
	});
	this.addGeoServerModal = new gb.modal.Base({
		"title" : "Add GeoServer",
		"width" : 540,
		"height" : 400,
		"autoOpen" : false,
		"body" : gBody
	});
	$(this.addGeoServerModal.getModal()).append(this.modalFooter);
	var innerHeight = $(this.addGeoServerModal.getModal()).innerHeight();
	var headHeight = $(this.addGeoServerModal.getModal()).find(".gb-modal-head").outerHeight();
	var footerHeight = $(this.addGeoServerModal.getModal()).find(".gb-modal-footer").outerHeight();
	var bodyHeight = innerHeight - (headHeight + footerHeight);
	$(gBody).outerHeight(bodyHeight);
	$(gBody).innerWidth(538);
};
gb.tree.GeoServer.prototype = Object.create(gb.tree.GeoServer.prototype);
gb.tree.GeoServer.prototype.constructor = gb.tree.GeoServer;

/**
 * jstree 객체를 반환한다.
 * 
 * @method gb.tree.GeoServer#getJSTree
 */
gb.tree.GeoServer.prototype.getJSTree = function() {
	return this.jstree;
};

/**
 * jstree 객체를 설정한다.
 * 
 * @method gb.tree.GeoServer#setJSTree
 */
gb.tree.GeoServer.prototype.setJSTree = function(jstree) {
	this.jstree = jstree;
};

/**
 * GeoServer 등록창을 연다.
 * 
 * @method gb.tree.GeoServer#openAddGeoServer
 */
gb.tree.GeoServer.prototype.openAddGeoServer = function() {
	this.addGeoServerModal.open();
};
/**
 * GeoServer 등록창을 닫는다.
 * 
 * @method gb.tree.GeoServer#openAddGeoServer
 */
gb.tree.GeoServer.prototype.closeAddGeoServer = function() {
	this.addGeoServerModal.close();
};
/**
 * GeoServer를 등록한다.
 * 
 * @method gb.tree.GeoServer#addGeoServer
 */
gb.tree.GeoServer.prototype.addGeoServer = function() {
	console.log("add geoserver");
};
/**
 * GeoServer를 삭제한다.
 * 
 * @method gb.tree.GeoServer#deleteGeoServer
 */
gb.tree.GeoServer.prototype.deleteGeoServer = function() {
	console.log("delete geoserver");
};

/**
 * GeoServer 목록을 새로고침한다.
 * 
 * @method gb.tree.GeoServer#refreshGeoServer
 */
gb.tree.GeoServer.prototype.refreshGeoServer = function() {
	console.log("refresh geoserver");
};
/**
 * 레이어 검색창을 연다.
 * 
 * @method gb.tree.GeoServer#openSearchBar
 */
gb.tree.GeoServer.prototype.openSearchBar = function() {
	console.log("open search on geoserver");
};
/**
 * 레이어 검색창을 닫는다.
 * 
 * @method gb.tree.GeoServer#closeSearchBar
 */
gb.tree.GeoServer.prototype.closeSearchBar = function() {
	console.log("close search geoserver");
};