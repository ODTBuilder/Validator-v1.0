<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/common/common3.jsp" />
<title>Builder Test 2017</title>
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
</style>

</head>
<body>

	<nav id="builderHeader"
		class="navbar navbar-toggleable-md navbar-default fixed-top">

		<ul class="nav navbar-nav">

			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown" role="button" aria-expanded="false"
				title="New layer"><i class="fa fa-file-o fa-lg"
					aria-hidden="true"></i> New</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#" id="newVector" title="Vector">New</a></li>
					<li><a href="#" id="uploadFile" title="Upload File"
						onclick="gitbuilder.ui.NewFileWindow()">File</a></li>
				</ul></li>
			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown" role="button" aria-expanded="false"
				title="Save"><i class="fa fa-floppy-o fa-lg" aria-hidden="true"></i>
					Save</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#">as a SHP</a></li>
					<li><a href="#" id="save">to Server</a></li>
				</ul></li>

			<li><a href="#" title="Edit" id="edit"><i
					class="fa fa-pencil-square-o fa-lg" aria-hidden="true"></i> Edit</a></li>
			<li><a href="#" title="Base map" id="changeBase"><i
					class="fa fa-map-o fa-lg" aria-hidden="true"></i> Base Map</a></li>
			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown" role="button" aria-expanded="false"
				title="Validation"><i class="fa fa-search fa-lg fa-lg"
					aria-hidden="true"></i> QA 1.0</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#" title="Layer Definition" id="layerDefinition1">Layer
							Definition</a></li>
					<li><a href="#" title="Validating Option"
						id="validDefinition1">Validating Option</a></li>
					<li><a href="#" title="Layer Weight" id="weight1">Layer
							Weight</a></li>
					<li><a href="#" title="Validation" id="validation1">Validation</a></li>
				</ul></li>
			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown" role="button" aria-expanded="false"
				title="Validation"><i class="fa fa-search fa-lg fa-lg"
					aria-hidden="true"></i> QA 2.0</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#" title="Layer Definition" id="layerDefinition">Layer
							Definition</a></li>
					<li><a href="#" title="Validating Option" id="validDefinition">Validating
							Option</a></li>
					<li><a href="#" title="Layer Weight" id="weight">Layer
							Weight</a></li>
					<li><a href="#" title="Validation" id="validation">Validation</a></li>
				</ul></li>
			<li><a href="#" title="QA Edit" id="qaedit"><i
					class="fa fa-object-group fa-lg" aria-hidden="true"></i> QA Edit</a></li>
			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown" role="button" aria-expanded="false"
				title="Validation Result"><i class="fa fa-list-alt fa-lg"
					aria-hidden="true"></i> Result</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#">Error Navigator</a></li>
					<li><a href="#">Error Report</a></li>
				</ul></li>

			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown" role="button" aria-expanded="false"
				title="ToolBox"><i class="fa fa-calculator fa-lg"
					aria-hidden="true"></i> ToolBox</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#">CRS Transformation</a></li>
					<li><a href="#">Spatial Operation</a></li>
				</ul></li>

			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown" role="button" aria-expanded="false"
				title="History"><i class="fa fa-history fa-lg"
					aria-hidden="true"></i> History</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#">History</a></li>
					<li role="presentation" class="divider"></li>
					<li><a href="#">Download History</a></li>
					<li><a href="#">Upload History</a></li>
				</ul></li>

			<li><a href="#" title="Information"><i
					class="fa fa-info-circle fa-lg" aria-hidden="true"></i> Information</a></li>
		</ul>
	</nav>

	<div id="builderContent" class="container-fluid">
		<div id="builderLayer">
			<div id="builderLayerGeoServerPanel" class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">GeoServer</h3>
				</div>
				<div class="panel-body gitbuilder-layer-panel">
					<div id="builderServerLayer"></div>
				</div>
			</div>
			<div id="builderLayerClientPanel" class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Layer</h3>
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
				var conHeight = winHeight - ($("#builderHeader").outerHeight(true) + $("#builderFooter").outerHeight(true));
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
				var listHeight = $("#builderLayer").innerHeight() / 2 - 16;
				// 				38은 패널 헤더의 높이
				var treeHeight = listHeight - 38;
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

		$('#builderClientLayer').jstreeol3({
			"core" : {
				"map" : map,
				"animation" : 0,
				"themes" : {
					"stripes" : true
				},
			}
		});

		var record = new gb.edit.FeatureRecord({
			id : "feature_id"
		});

		var transfer = new gb.edit.RecordTransfer({
			url : "editLayerCollection/editLayerCollection.ajax",
			feature : record
		});

		$("#save").click(function() {
			transfer.sendStructure();
		});

		$("#edit").editingtool({
			url : "geoserver2/geoserverWFSGetFeature.ajax",
			map : map,
			user : "admin",
			record : record,
			selected : function() {
				return $('#builderClientLayer').jstreeol3("get_selected_layer");
			}
		});

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
			layersURL : "geoserver2/getGeolayerCollectionTree.ajax",
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
			layersURL : "geoserver2/getGeolayerCollectionTree.ajax",
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

		$("#qaedit").qaedit({
			map : map,
			editingTool : $("#edit").editingtool("instance"),
			linkKey : "feature_id",
			user : "admin",
			layersURL : 'geoserver2/getGeolayerCollectionTree.ajax',
			featureWMSURL : "geoserver2/geoserverWMSLayerLoad.do",
			featureWFSURL : "geoserver2/geoserverWFSGetFeature.ajax",
			groupInfoURL : "geoserver2/getGeoGroupLayerInfoList.ajax",
			layerInfoURL : "geoserver2/getGeoLayerInfoList.ajax"
		});

		var layerInfo = new gb.edit.LayerInformation({
			url : "geoserver2/getGeoLayerInfoList.ajax"
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
						return 'geoserver2/getGeolayerCollectionTree.ajax';
					}
				}
			},
			"geoserver" : {
				"map" : map,
				"user" : "admin",
				"layerInfo" : layerInfo,
				"layerInfoURL" : "geoserver2/getGeoLayerInfoList.ajax",
				"downloadNGIDXF" : "fileExport/fileExport.ajax",
				"downloadGeoserver" : "geoserver2/downloadRequest.do"
			},
			"plugins" : [ "contextmenu", "search", "state", "types", "geoserver" ]
		});
	</script>

</body>
</html>