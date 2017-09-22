// =================================소이준===================================
/**
 * ### Geoserver plugin
 * 
 * 레이어에 따라 다른 컨텍스트 메뉴 및 요청을 처리
 */
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
									'VERSION' : '1.0.0',
									'CRS' : 'EPSG:5186',
									'SRS' : 'EPSG:5186',
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
							case "INSERT":
								geom = "Point";
								break;
							case "POLYGON":
								geom = "Polygon";
								break;
							case "LINESTRING":
								geom = "LineString";
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
									'VERSION' : '1.0.0',
									'CRS' : 'EPSG:5186',
									'SRS' : 'EPSG:5186',
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
							case "INSERT":
								geom = "Point";
								break;
							case "POLYGON":
								geom = "Polygon";
								break;
							case "LINESTRING":
								geom = "LineString";
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
									'VERSION' : '1.0.0',
									'CRS' : 'EPSG:5186',
									'SRS' : 'EPSG:5186',
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
											'VERSION' : '1.0.0',
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
									'VERSION' : '1.0.0',
									'CRS' : 'EPSG:5186',
									'SRS' : 'EPSG:5186',
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
						wms.set("name", obj.refer.get_node(data[i].lName).text);
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
									'VERSION' : '1.0.0',
									'CRS' : 'EPSG:5186',
									'SRS' : 'EPSG:5186',
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
												'VERSION' : '1.0.0',
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
									'VERSION' : '1.0.0',
									'CRS' : 'EPSG:5186',
									'SRS' : 'EPSG:5186',
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
// =================================소이준===================================
