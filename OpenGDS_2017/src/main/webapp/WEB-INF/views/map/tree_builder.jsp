<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/common/common3.jsp" />
<script src='${pageContext.request.contextPath}/resources/js/login/login.js'></script>
<title>GeoDT Online</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
html {
	position: relative;
	min-height: 100%;
	overflow: hidden;
}

#builderHeader {
	border-radius: 0;
	margin-bottom: 0;
	min-height: 30px;
}

#builderContent {
	padding: 0;
}

#builderLayer {
	float: left;
	width: 380px;
	max-width: 380px;
	padding: 8px;
}

#bind {
	float: left;
}

#builderMap {
	
}

#builderBaseMap {
	position: relative;
	top: -906px;
	left: 0;
}

#builderFooter {
	min-height: 30px;
	line-height: 30px;
	margin-bottom: 0;
}

#builderLayerGeoServerPanel {
	margin-bottom: 16px;
}

#builderLayerClientPanel {
	margin-bottom: 0;
}

.gitbuilder-layer-panel {
	padding: 0;
	overflow-y: auto;
}

.gitbuilder-clearbtn {
	border: 0;
	background-color: transparent;
}

#builderHeader .navbar-nav>li>a {
	padding-top: 10px;
	padding-bottom: 10px;
}
</style>

</head>
<body>
	<script>
		var checkUnload = true;
		$(window).on("beforeunload", function() {
			if (checkUnload)
				return "이 페이지를 벗어나면 작성된 내용은 저장되지 않습니다.";
		});
	</script>


	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<nav id="builderHeader" class="navbar navbar-default fixed-top">
		<ul class="nav navbar-nav">
			<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
				aria-expanded="false" title="New layer">New</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#" id="uploadFile" title="Upload File" onclick="gitbuilder.ui.NewFileWindow()">File</a></li>
					<li><a href="#" id="newVector" title="Vector">Layer</a></li>
				</ul></li>
			<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
				aria-expanded="false" title="Save">Save</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#">as a SHP</a></li>
					<li><a href="#" id="save">to Server</a></li>
				</ul></li>

			<li><a href="#" title="Edit" id="edit">Edit</a></li>
			<li><a href="#" title="Base map" id="changeBase">Base Map</a></li>
			<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
				aria-expanded="false" title="Validation">QA 1.0</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#" title="Layer Definition" id="layerDefinition1">Layer Definition</a></li>
					<li><a href="#" title="Validating Option" id="validDefinition1">Validating Option</a></li>
					<li><a href="#" title="Layer Weight" id="weight1">Layer Weight</a></li>
					<li><a href="#" title="Validation" id="validation1">Validation</a></li>
				</ul></li>
			<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
				aria-expanded="false" title="Validation">QA 2.0</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#" title="Layer Definition" id="layerDefinition">Layer Definition</a></li>
					<li><a href="#" title="Validating Option" id="validDefinition">Validating Option</a></li>
					<li><a href="#" title="Layer Weight" id="weight">Layer Weight</a></li>
					<li><a href="#" title="Validation" id="validation">Validation</a></li>
				</ul></li>
			<li><a href="#" title="QA Edit" id="qaedit">QA Edit</a></li>
			<li><a href="#" title="QA Status" id="qastat">QA Status</a></li>

			<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
				aria-expanded="false" title="ToolBox">ToolBox</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#">CRS Transformation</a></li>
					<li><a href="#">Spatial Operation</a></li>
				</ul></li>

			<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
				aria-expanded="false" title="History">History</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#">History</a></li>
					<li role="presentation" class="divider"></li>
					<li><a href="#">Download History</a></li>
					<li><a href="#">Upload History</a></li>
				</ul></li>

			<li><a href="#" title="Information">Information</a></li>
		</ul>
	</nav>

	<div id="builderContent" class="container-fluid">
		<div id="builderLayer">
			<div id="builderLayerGeoServerPanel" class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title" style="display: inline-block;">GeoServer</h3>
					<button id="srefresh" class="pull-right gitbuilder-clearbtn">
						<i class="fa fa-refresh" aria-hidden="true"></i>
					</button>
				</div>
				<div class="panel-body gitbuilder-layer-panel">
					<div id="builderServerLayer"></div>
				</div>
			</div>
			<div id="builderLayerClientPanel" class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title" style="display: inline-block;">Layer</h3>
					<button id="crefresh" class="pull-right gitbuilder-clearbtn">
						<i class="fa fa-refresh" aria-hidden="true"></i>
					</button>
				</div>
				<div class="panel-body gitbuilder-layer-panel">
					<div id="builderClientLayer"></div>
				</div>
			</div>
		</div>
		<div id="bind">
			<div id="builderMap"></div>
			<div id="builderBaseMap"></div>
		</div>
	</div>
	<nav id="builderFooter" class="navbar navbar-default">
		<div class="container-fluid">
			<span class="text-muted">OpenGDS Builder/Validator</span>
		</div>
	</nav>

	<script type="text/javascript">
		var projection = ol.proj.get('EPSG:5186');

		var view = new ol.View({
			center : ol.proj.fromLonLat([ 37.41, 8.82 ]),
			zoom : 4
		});

		var map = new ol.Map({
			target : 'builderMap',
			layers : [],
			view : view,
			controls : [ new ol.control.Zoom(), new ol.control.ZoomSlider() ]
		});

		var map2 = new ol.Map({
			target : 'builderBaseMap',
			controls : [],
			layers : [],
			view : view
		});

		var gitrnd = {
			resize : function() {
				var winHeight = $(window).innerHeight();
				var conHeight = winHeight
						- ($("#mainHeader").outerHeight(true) + $("#builderHeader").outerHeight(true) + $("#builderFooter").outerHeight(
								true));
				var winWidth = $(window).innerWidth();
				var mapWidth = winWidth - ($("#builderLayer").outerWidth(true));
				$("#builderLayer").outerHeight(conHeight);
				$("#bind").outerWidth(mapWidth).outerHeight(conHeight);
				$("#builderMap").outerWidth(mapWidth).outerHeight(conHeight);
				$("#builderBaseMap").outerWidth(mapWidth).outerHeight(conHeight);
				var str = "-" + conHeight + "px";
				$("#builderBaseMap").css("top", str);
				$("#builderBaseMap").find(".ol-viewport").css("z-index", 1);
				$("#builderMap").find(".ol-viewport").css("z-index", 2);
				//16은 아래 마진, 1은 위 아래 보더 
				var listHeight = $("#builderLayer").innerHeight() / 2 - (16 + 1 + 1);
				// 				41은 패널 헤더의 높이
				var treeHeight = listHeight - (41);
				$(".gitbuilder-layer-panel").outerHeight(treeHeight);
				$("#builderLayerGeoServerPanel").outerHeight(listHeight);
				$("#builderLayerClientPanel").outerHeight(listHeight);
				map.updateSize();
				map2.updateSize();
			},
			setProjection : function(code, name, proj4def, bbox) {
				if (code === null || name === null || proj4def === null || bbox === null) {
					map.setView(new ol.View({
						projection : 'EPSG:3857',
						center : [ 0, 0 ],
						zoom : 1
					}));
					return;
				}

				var newProjCode = 'EPSG:' + code;
				proj4.defs(newProjCode, proj4def);
				var newProj = ol.proj.get(newProjCode);
				var fromLonLat = ol.proj.getTransform('EPSG:4326', newProj);

				// very approximate calculation of projection extent
				var extent = ol.extent.applyTransform([ bbox[1], bbox[2], bbox[3], bbox[0] ], fromLonLat);
				newProj.setExtent(extent);
				var newView = new ol.View({
					projection : newProj
				});
				map.setView(newView);
				map2.setView(newView);
				newView.fit(extent);
			},
			search : function(query) {
				fetch('https://epsg.io/?format=json&q=' + query).then(function(response) {
					return response.json();
				}).then(function(json) {
					var results = json['results'];
					if (results && results.length > 0) {
						for (var i = 0, ii = results.length; i < ii; i++) {
							var result = results[i];
							if (result) {
								var code = result['code'], name = result['name'], proj4def = result['proj4'], bbox = result['bbox'];
								if (code && code.length > 0 && proj4def && proj4def.length > 0 && bbox && bbox.length == 4) {
									gitrnd.setProjection(code, name, proj4def, bbox);
									return;
								}
							}
						}
					}
					gitrnd.setProjection(null, null, null, null);
				});
			}

		}

		gitrnd.search("5186");

		$(window).resize(function() {
			gitrnd.resize();
		});

		$(document).ready(function() {
			gitrnd.resize();
		});

		var lrecord = new gb.edit.LayerRecord({});

		var frecord = new gb.edit.FeatureRecord({
			id : "feature_id"
		});

		var lprop = new gb.edit.ModifyLayerProperties({
			layerRecord : lrecord,
			featureRecord : frecord
		});

		var nlayer = new gb.edit.CreateVectorLayer({
			refer : $('#builderClientLayer').jstreeol3(true),
			map : map,
			layerRecord : lrecord
		});
		$("#newVector").click(function() {
			nlayer.open();
		});

		$('#builderClientLayer').jstreeol3({
			"core" : {
				"map" : map,
				"animation" : 0,
				"themes" : {
					"stripes" : true
				},
			},
			"layerproperties" : {
				"properties" : lprop,
				"layerRecord" : lrecord
			},
			plugins : [ "contextmenu", "dnd", "search", "state", "types", "sort", "visibility", "layerproperties" ]
		});

		var transfer = new gb.edit.RecordTransfer({
			url : "editLayerCollection/editLayerCollection.ajax",
			layer : lrecord,
			feature : frecord
		});

		$("#save").click(function() {
			transfer.sendStructure();
		});

		// 		$("#edit").editingtool({
		// 			url : "geoserver/geoserverWFSGetFeature.ajax",
		// 			map : map,
		// 			featureRecord : frecord,
		// 			treeInstance : $('#builderClientLayer').jstreeol3(true),
		// 			selected : function() {
		// 				return $('#builderClientLayer').jstreeol3("get_selected_layer");
		// 			}
		// 		});

		$("#changeBase").changebase({
			map : map2
		});

		$("#layerDefinition1").layerdefinition10({});

		$("#validDefinition1").optiondefinition({
			layerDefinition : function() {
				return $("#layerDefinition1").layerdefinition10("getDefinition");
			}
		});

		$("#weight1").weightdefinition({
			layerDefinition : function() {
				return $("#layerDefinition1").layerdefinition10("getDefinition");
			},
			optionDefinition : function() {
				return $("#validDefinition1").optiondefinition("getDefinition");
			}
		});

		$("#validation1").validation({
			validatorURL : "validator/validate.ajax",
			layersURL : "geoserver/getGeolayerCollectionTree.ajax",
			layerDefinition : function() {
				return $("#layerDefinition1").layerdefinition10("getDefinition");
			},
			optionDefinition : function() {
				return $("#validDefinition1").optiondefinition("getDefinition");
			},
			weightDefinition : function() {
				return $("#weight1").weightdefinition("getDefinition");
			}
		});

		$("#layerDefinition").layerdefinition20({

		});

		$("#validDefinition").optiondefinition({
			layerDefinition : function() {
				return $("#layerDefinition").layerdefinition20("getDefinition");
			}
		});

		$("#weight").weightdefinition({
			layerDefinition : function() {
				return $("#layerDefinition").layerdefinition20("getDefinition");
			},
			optionDefinition : function() {
				return $("#validDefinition").optiondefinition("getDefinition");
			}
		});

		$("#validation").validation({
			validatorURL : "validator/validate.ajax",
			layersURL : "geoserver/getGeolayerCollectionTree.ajax",
			layerDefinition : function() {
				return $("#layerDefinition").layerdefinition20("getDefinition");
			},
			optionDefinition : function() {
				return $("#validDefinition").optiondefinition("getDefinition");
			},
			weightDefinition : function() {
				return $("#weight").weightdefinition("getDefinition");
			}
		});

		var epan = new gb.panel.EditingTool({
			width : "84px",
			height : "145px",
			positionX : 425,
			positionY : 100,
			autoOpen : false,
			map : map,
			featureRecord : frecord,
			treeInstance : $('#builderClientLayer').jstreeol3(true),
			selected : function() {
				return $('#builderClientLayer').jstreeol3("get_selected_layer");
			},
			infoURL : "geoserver/getGeoLayerInfoList.ajax",
			wmsURL : "geoserver/geoserverWMSLayerLoad.do",
			wfsURL : "geoserver/geoserverWFSGetFeature.ajax"
		});

		$("#qaedit").qaedit({
			map : map,
			editingTool : epan,
			treeInstance : $('#builderClientLayer').jstreeol3(true),
			linkKey : "feature_idx",
			user : "admin",
			layersURL : 'geoserver/getGeolayerCollectionTree.ajax',
			featureWMSURL : "geoserver/geoserverWMSLayerLoad.do",
			featureWFSURL : "geoserver/geoserverWFSGetFeature.ajax",
			groupInfoURL : "geoserver/getGeoGroupLayerInfoList.ajax",
			layerInfoURL : "geoserver/getGeoLayerInfoList.ajax"
		});

		var createLayer = new gb.geoserver.CreateLayer({
			URL : "editLayerCollection/editLayerCollection.ajax"
		});
		var deleteLayer = new gb.geoserver.DeleteLayer({
			URL : "editLayerCollection/editLayerCollection.ajax"
		});
		var layerInfo = new gb.geoserver.ModifyLayer({
			infoURL : "geoserver/getGeoLayerInfoList.ajax",
			editURL : "editLayerCollection/editLayerCollection.ajax"
		});
		$("#builderServerLayer").jstree({
			"core" : {
				"animation" : 0,
				"check_callback" : true,
				"themes" : {
					"stripes" : true
				},
				'data' : {
					'url' : function() {
						return 'geoserver/getGeolayerCollectionTree.ajax';
					}
				}
			},
			"geoserver" : {
				"map" : map,
				"user" : "admin",
				"layerInfo" : layerInfo,
				"layerInfoURL" : "geoserver/getGeoLayerInfoList.ajax",
				"groupLayerInfoURL" : "geoserver/getGeoGroupLayerInfoList.ajax",
				"WMSLayerURL" : "geoserver/geoserverWMSLayerLoad.do",
				"createLayer" : createLayer,
				"deleteLayer" : deleteLayer,
				"downloadNGIDXF" : "fileExport/fileExport.ajax",
				"downloadGeoserver" : "geoserver/downloadRequest.do",
				"clientRefer" : $('#builderClientLayer').jstreeol3(true)
			},
			"plugins" : [ "contextmenu", "search", "state", "types", "geoserver" ]
		});

		$("#crefresh").click(function() {
			$('#builderClientLayer').jstreeol3(true).refresh();
		});
		$("#srefresh").click(function() {
			$("#builderServerLayer").jstree(true).refresh();
		});
		var qastat = new gb.qa.QAStatus({
			"statusURL" : "validateProgress/validateProgress.ajax",
			"errorURL" : "",
			"downloadNGIDXFURL" : "fileExport/fileExport.ajax"
		});
		$("#qastat").click(function() {
			qastat.open();
		});

		$("#edit").click(function() {
			epan.open();
		});
	</script>

</body>
</html>