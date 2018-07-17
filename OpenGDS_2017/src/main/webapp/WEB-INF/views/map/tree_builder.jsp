<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/common/common3.jsp" />
<script src='${pageContext.request.contextPath}/resources/js/login/login.js'></script>
<title>OpenGDS/Builder</title>
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
			<li><a href="#" id="uploadFile" title="Upload File" onclick="gitbuilder.ui.NewFileWindow()">File</a></li>
			<!-- 			<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" -->
			<!-- 				aria-expanded="false" title="New layer">New</a> -->
			<!-- 				<ul class="dropdown-menu" role="menu"> -->
			<!-- 					<li><a href="#" id="uploadFile" title="Upload File" onclick="gitbuilder.ui.NewFileWindow()">File</a></li> -->
			<!-- 					<li><a href="#" id="newVector" title="Vector">Layer</a></li> -->
			<!-- 				</ul></li> -->
			<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
				aria-expanded="false" title="Save">Save</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#" id="savePart">Save</a></li>
					<li><a href="#" id="saveAll">Save All</a></li>
				</ul></li>

			<li><a href="#" title="Edit" id="edit">Edit</a></li>
			<li><a href="#" title="Base map" id="changeBase">Base Map</a></li>
			<!-- <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
				aria-expanded="false" title="Validation">QA 1.0</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#" title="Layer Definition" id="layerDefinition1">Layer Definition</a></li>
					<li><a href="#" title="Validating Option" id="validDefinition1">Validating Option</a></li>
					<li><a href="#" title="Layer Weight" id="weight1">Layer Weight</a></li>
					<li><a href="#" title="Validation" id="validation1">Validation</a></li>
				</ul></li> -->
			<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
				aria-expanded="false" title="Validation">QA</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#" title="Layer Definition" id="layerDefinition">Layer Definition</a></li>
					<li><a href="#" title="Validating Option" id="validDefinition2">Validating Option</a></li>
					<li><a href="#" title="Layer Weight" id="weight">Layer Weight</a></li>
					<li><a href="#" title="Validation" id="validation">Validation</a></li>
				</ul></li>
			<li><a href="#" title="QA Edit" id="qaedit">QA Edit</a></li>
			<li><a href="#" title="QA Status" id="qastat">QA Status</a></li>
		<!-- 	<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
				aria-expanded="false" title="Generalization">Generalization</a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#" title="Generalization Process" id="gen">Generalization</a></li>
					<li><a href="#" title="Generalization Result" id="genstat">Result</a></li>
										<li><a href="#" title="Validating Option" id="sepview">Separate View</a></li>
				</ul></li> -->
			<!-- 			<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" -->
			<!-- 				aria-expanded="false" title="ToolBox">ToolBox</a> -->
			<!-- 				<ul class="dropdown-menu" role="menu"> -->
			<!-- 					<li><a href="#">Base CRS</a></li> -->
			<!-- 					<li><a href="#">Spatial Operation</a></li> -->
			<!-- 				</ul></li> -->

			<!-- 			<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" -->
			<!-- 				aria-expanded="false" title="History">History</a> -->
			<!-- 				<ul class="dropdown-menu" role="menu"> -->
			<!-- 					<li><a href="#">History</a></li> -->
			<!-- 					<li role="presentation" class="divider"></li> -->
			<!-- 					<li><a href="#">Download History</a></li> -->
			<!-- 					<li><a href="#">Upload History</a></li> -->
			<!-- 				</ul></li> -->

			<li><a href="#" title="Information" id="binfo">Information</a></li>
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

				<div class="panel-body" style="padding: 0;">
					<input type="text" class="form-control builder-tree-search" id="inputSearchServer" />
					<div id="builderServerLayer" class="gitbuilder-layer-panel"></div>
				</div>
			</div>
			<div id="builderLayerClientPanel" class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title" style="display: inline-block;">Layer</h3>
					<button id="crefresh" class="pull-right gitbuilder-clearbtn">
						<i class="fa fa-refresh" aria-hidden="true"></i>
					</button>
				</div>
				<div class="panel-body" style="padding: 0;">
					<input type="text" class="form-control builder-tree-search" id="inputSearchClient" />
					<div id="builderClientLayer" class="gitbuilder-layer-panel"></div>
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
			<span class="text-muted">OpenGDS Builder/Validator</span> <span class="text-muted"><a href="#"
				class="epsg-now"></a></span>
		</div>
	</nav>

	<script type="text/javascript">
		var projection = ol.proj.get('EPSG:3857');

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
				//현재 보이는 브라우저 내부 영역의 높이
				var winHeight = $(window).innerHeight();
				//컨텐츠 영역의 높이 지정
				//#mainHeader -> 헤더1
				//#builderHeader -> 헤더2
				//#builderFooter -> 푸터
				// 없으면 삭제한다.
				var conHeight = winHeight
						- ($("#mainHeader").outerHeight(true) + $("#builderHeader").outerHeight(true) + $("#builderFooter").outerHeight(
								true));
				//현재 보이는 브라우저 내부 영역의 너비
				var winWidth = $(window).innerWidth();
				//컨텐츠 (지도) 영역의 너비 지정
				//#builderLayer -> 사이드바
				var mapWidth = winWidth - ($("#builderLayer").outerWidth(true));
				//사이드바의 높이 지정
				$("#builderLayer").outerHeight(conHeight);
				//컨텐츠 영역의 너비 지정
				$("#bind").outerWidth(mapWidth).outerHeight(conHeight);
				$("#builderMap").outerWidth(mapWidth).outerHeight(conHeight);
				$("#builderBaseMap").outerWidth(mapWidth).outerHeight(conHeight);
				//컨텐츠 영역(겹친 지도 부분, 베이스맵과 편집영역을 겹쳐서 베이스맵이 편집에 영향이 없도록하기 위함)의 위치를 같게함
				var str = "-" + conHeight + "px";
				$("#builderBaseMap").css("top", str);
				//편집영역이 베이스맵 위로 오도록 겹친 영역의 z-index를 조정
				$("#builderBaseMap").find(".ol-viewport").css("z-index", 1);
				$("#builderMap").find(".ol-viewport").css("z-index", 2);
				//16은 아래 마진, 1은 위 아래 보더 
				var listHeight = $("#builderLayer").innerHeight() / 2 - (16 + 1 + 1);
				// 				41은 패널 헤더의 높이
				var treeHeight = listHeight - (41);
				var searchHeight = $(".builder-tree-search").outerHeight();
				$(".gitbuilder-layer-panel").outerHeight(treeHeight - searchHeight);
				$("#builderLayerGeoServerPanel").outerHeight(listHeight);
				$("#builderLayerClientPanel").outerHeight(listHeight);
				//현재 컨텐츠 사이즈를 오픈레이어스에 반영
				map.updateSize();
				map2.updateSize();
			},
			addRemoveHistoryList : function(layer, arr) {
				if (layer instanceof ol.layer.Group) {
					if (layer.get("id")) {
						arr.push(layer.get("id"));
					}
					var layers = layer.getLayers();
					for (var i = 0; i < layers.getLength(); i++) {
						gitrnd.addRemoveHistoryList(layers.item(i), arr);
					}
				} else if (layer instanceof ol.layer.Base) {
					var git = layer.get("git");
					if (git) {
						var fake = git.fake;
						if (fake) {
							if (fake === "parent") {
								arr.push(layer.get("id"));
								var layers = layer.get("git").layers;
								for (var i = 0; i < layers.getLength(); i++) {
									gitrnd.addRemoveHistoryList(layers.item(i), arr);
								}
							} else if (fake === "child") {
								arr.push(layer.get("id"));
							}
						} else {
							arr.push(layer.get("id"));
						}
					} else {
						arr.push(layer.get("id"));
					}
				}
				return arr;
			},
			addRefreshWMSList : function(node, arr) {
				var clayer = $('#builderClientLayer').jstreeol3(true).get_LayerById(node);
				if (clayer instanceof ol.layer.Group) {
					var children = $('#builderClientLayer').jstreeol3(true).get_node(node).children;
					for (var i = 0; i < children.length; i++) {
						gitrnd.addRefreshWMSList(children[i], arr);
					}
				} else if (clayer instanceof ol.layer.Tile) {
					arr.push(clayer);
				} else if (clayer instanceof ol.layer.Vector) {

				} else if (clayer instanceof ol.layer.Base) {
					var cnode = $('#builderClientLayer').jstreeol3(true).get_node(node);
					var parent = cnode.parent;
					gitrnd.addRefreshWMSList(parent, arr);
				}
				return arr;
			}
		}

		// 		gitrnd.search("5186");

		$(window).resize(function() {
			gitrnd.resize();
		});

		$(document).ready(function() {
			gitrnd.resize();
		});

		var crs = new gb.modal.BaseCRS({
			"autoOpen" : false,
			"title" : "Base CRS",
			"message" : $(".epsg-now"),
			"map" : [ map, map2 ],
			"epsg" : "5186"
		});

		$(".epsg-now").click(function() {
			crs.open();
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

		var epan = new gb.panel.EditingTool({
			width : 84,
			height : 145,
			positionX : 425,
			positionY : 100,
			autoOpen : false,
			map : map,
			featureRecord : frecord,
			treeInstance : $('#builderClientLayer').jstreeol3(true),
			selected : function() {
				return $('#builderClientLayer').jstreeol3("get_selected_layer");
			},
			getFeatureInfo : "geoserver/geoserverWMSGetFeatureInfo.ajax",
			layerInfo : "geoserver/getGeoLayerInfoList.ajax",
			imageTile : "geoserver/geoserverWMSLayerLoad.do",
			getFeature : "geoserver/geoserverWFSGetFeature.ajax"
		});

		var lstyle = new gb.panel.LayerStyle({

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
				"layerRecord" : lrecord,
				"featureRecord" : frecord,
				"style" : lstyle,
				"editingTool" : epan
			},
			"search" : {
				show_only_matches : true
			},
			"legends" : {
				"types" : {
					"#" : {
						"valid_children" : [ "default", "Group", "Vector", "Raster", "ImageTile" ]
					},
					// 편집도구에서 지원할 타입
					"Group" : {
						"icon" : "fa fa-folder-o",
						"valid_children" : [ "default", "Group", "Vector", "Raster", "ImageTile" ]
					},
					// 이외의 기본형
					"default" : {
						"icon" : "fa fa-file-o",
						"valid_children" : []
					},
					"Vector" : {
						"icon" : "fa fa-file-image-o",
						"valid_children" : []
					},
					"Raster" : {
						"icon" : "fa fa-file-image-o",
						"valid_children" : []
					},
					"ImageTile" : {
						"icon" : "fa fa-file-image-o",
						"valid_children" : []
					}
				},
				"geoserver" : {
					"url" : "geoserver/getWMSGetLegendGraphic.ajax",
					"width" : "15",
					"height" : "15",
					"format" : "image/png"
				}
			},
			"functionmarker" : {
				"snapping" : "fa fa-magnet"
			},
			plugins : [ "contextmenu", "dnd", "search", "state", "sort", "visibility", "layerproperties", "legends", "functionmarker" ]
		});

		var transfer = new gb.edit.RecordTransfer({
			url : "editLayerCollection/editLayerCollection.ajax",
			layer : lrecord,
			feature : frecord,
			map : map
		});

		$("#savePart").click(function() {
			// 			transfer.sendStructure();
			// gitrnd.addRemoveHistoryList(layer, arr);
			var selected = $('#builderClientLayer').jstreeol3(true).get_selected();
			var olselected = [];
			var ollayers = new ol.Collection();
			for (var i = 0; i < selected.length; i++) {
				var layer = $('#builderClientLayer').jstreeol3(true).get_LayerById(selected[i]);
				gitrnd.addRefreshWMSList(selected[i], ollayers);
				gitrnd.addRemoveHistoryList(layer, olselected);
			}
			console.log(olselected);
			var nodupliobj = {};
			for (var i = 0; i < olselected.length; i++) {
				nodupliobj[olselected[i]] = true;
			}
			var nodupliarr = Object.keys(nodupliobj);
			console.log(nodupliarr);
			if (nodupliarr.length > 0) {
				transfer.sendPartStructure(nodupliarr, ollayers, epan);
				swal('Saved!', 'Layers have been saved.', 'success');
			}

		});

		$("#saveAll").click(function() {
			var root = $('#builderClientLayer').jstreeol3(true).get_node("#");
			var children = root.children;
			var olselected = [];
			var ollayers = new ol.Collection();
			for (var i = 0; i < children.length; i++) {
				var layer = $('#builderClientLayer').jstreeol3(true).get_LayerById(children[i]);
				gitrnd.addRefreshWMSList(children[i], ollayers);
				gitrnd.addRemoveHistoryList(layer, olselected);
			}
			console.log(olselected);
			var nodupliobj = {};
			for (var i = 0; i < olselected.length; i++) {
				nodupliobj[olselected[i]] = true;
			}
			var nodupliarr = Object.keys(nodupliobj);
			console.log(nodupliarr);
			if (nodupliarr.length > 0) {
				transfer.sendStructure(map.getLayers(), epan);
				swal('Saved!', 'Layers have been saved.', 'success');
			}
		});

		$("#changeBase").changebase({
			map : map2
		});

		$("#layerDefinition1").layerdefinition10({});

		var optwin1 = new gb.modal.ValidationDefinition({
			"autoOpen" : false,
			"title" : "Option Definition",
			"layerDefinition" : function() {
				return $("#layerDefinition1").layerdefinition10("getDefinition");
			}
		});
		$("#validDefinition1").click(function() {
			optwin1.open();
		});

		$("#weight1").weightdefinition({
			layerDefinition : function() {
				return $("#layerDefinition1").layerdefinition10("getDefinition");
			},
			optionDefinition : function() {
				return optwin1.getDefinition();
			}
		});

		$("#validation1").validation({
			validatorURL : "validator/validate.ajax",
			layersURL : "geoserver/getGeolayerCollectionTree.ajax?treeType=qa1.0",
			layerDefinition : function() {
				return $("#layerDefinition1").layerdefinition10("getDefinition");
			},
			optionDefinition : function() {
				return optwin1.getDefinition();
			},
			weightDefinition : function() {
				return $("#weight1").weightdefinition("getDefinition");
			},
			map : map
		});

		$("#layerDefinition").layerdefinition20({});

		var optwin2 = new gb.modal.ValidationDefinition({
			"autoOpen" : false,
			"title" : "Option Definition",
			"layerDefinition" : function() {
				return $("#layerDefinition").layerdefinition20("getDefinition");
			}
		});
		$("#validDefinition2").click(function() {
			optwin2.open();
		});

		$("#weight").weightdefinition({
			layerDefinition : function() {
				return $("#layerDefinition").layerdefinition20("getDefinition");
			},
			optionDefinition : function() {
				return optwin2.getDefinition();
			}
		});

		$("#validation").validation({
			validatorURL : "validator/validate.ajax",
			layersURL : "geoserver/getGeolayerCollectionTree.ajax?treeType=shp",
			layerDefinition : function() {
				return $("#layerDefinition").layerdefinition20("getDefinition");
			},
			optionDefinition : function() {
				return optwin2.getDefinition();
			},
			weightDefinition : function() {
				return $("#weight").weightdefinition("getDefinition");
			},
			map : map
		});

		$("#qaedit").qaedit({
			map : map,
			editingTool : epan,
			treeInstance : $('#builderClientLayer').jstreeol3(true),
			linkKey : "feature_idx",
			user : "admin",
			layersURL : 'geoserver/getGeolayerCollectionTree.ajax?treeType=all',
			featureWMSURL : "geoserver/geoserverWMSLayerLoad.do",
			featureWFSURL : "geoserver/geoserverWFSGetFeature.ajax",
			groupInfoURL : "geoserver/getGeoGroupLayerInfoList.ajax",
			layerInfoURL : "geoserver/getGeoLayerInfoList.ajax"
		});

		var createLayer = new gb.geoserver.CreateLayer({
			map : map,
			URL : "editLayerCollection/editLayerCollection.ajax"
		});
		var deleteGroupLayer = new gb.geoserver.DeleteGroupLayer({
			URL : "editLayerCollection/editLayerCollection.ajax"
		});
		var deleteLayer = new gb.geoserver.DeleteLayer({
			URL : "editLayerCollection/editLayer.ajax"
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
				"WMSLayerURL" : "geoserver/geoserverWMSLayerLoad.do",
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

		var genstat = new gb.gen.GenStatus({
			"statusURL" : "generalization/getGeneralizationResult.ajax",
			"errorURL" : "",
			"downloadURL" : "fileExport/fileExport.ajax"
		});
		$("#genstat").click(function() {
			genstat.open();
		});

		$("#edit").click(function() {
			epan.open();
		});

		var modal = new gb.modal.Generalization({
			"width" : 850,
			"autoOpen" : false,
			"title" : "Generalization",
			"jstreeURL" : "geoserver/getGeolayerCollectionTree.ajax?treeType=all",
			"items" : [ "Simplification", "Elimination" ],
			"requestURL" : "generalization/exeGeneralization.ajax",
			"map" : map
		});

		$("#gen").click(function() {
			modal.open();
		});

		$(function() {
			var to = false;
			$('#inputSearchServer').keyup(function() {
				if (to) {
					clearTimeout(to);
				}
				to = setTimeout(function() {
					var v = $('#inputSearchServer').val();
					$('#builderServerLayer').jstree(true).search(v);
				}, 250);
			});

			var to2 = false;
			$('#inputSearchClient').keyup(function() {
				if (to) {
					clearTimeout(to);
				}
				to2 = setTimeout(function() {
					var v = $('#inputSearchClient').val();
					$('#builderClientLayer').jstreeol3(true).search(v);
				}, 250);
			});

		});

		var binfo = new gb.modal.Base(
				{
					"title" : "Information",
					"width" : "600px",
					"autoOpen" : false,
					"body" : $("<div>")
							.css("margin", "10px")
							.html(
									'이 프로젝트는 국토공간정보연구사업 중,<br/> [공간정보 SW 활용을 위한 오픈소스 가공기술 개발]과제 연구성과 입니다.<br/> 정식 버전은 차후에 통합된 환경에서 제공될 예정입니다. 이 프로그램들은 완성되지 않았으며, 최종 완료 전 까지 문제가 발생할 수도 있습니다. 발생된 문제는 최종 사용자에게 있으며, 완료가 된다면 제시된 라이선스 및 규약을 적용할 예정입니다.<br/>감사합니다. <br/>공간정보기술(주) 연구소 http://www.git.co.kr/ <br/>OpenGeoDT 팀')
				});
		$("#binfo").click(function() {
			binfo.open();
		});
	</script>

</body>
</html>