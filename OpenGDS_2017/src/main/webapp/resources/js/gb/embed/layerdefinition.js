/**
 * 임베드 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2017. 07.26
 * @version 0.01
 * @class gb.embed.Base
 * @constructor
 * 
 */
var gb;
if (!gb)
	gb = {};
if (!gb.embed)
	gb.embed = {};
gb.embed.LayerDefinition = function(obj) {
	var that = this;
	this.structure = [];
	this.geometryType = [ "point", "linestring", "polygon", "multipoint", "multilinestring", "multipolygon" ];
	this.dataType = [ "DATE", "DATETIME", "INTEGER", "NUMBER", "VARCHAR2", "VARCHAR3", "VARCHAR4" ];
	var options = obj ? obj : {};
	// this.panelBody = $("<div>").addClass("panel-body");
	this.panelBody = $("<div>").addClass("gb-layerdefinition-body");
	// this.panel =
	// $("<div>").addClass("panel").addClass("panel-default").append(this.panelBody);
	this.panel = $("<div>").append(this.panelBody);
	if (typeof options.append === "string") {
		$(options.append).append(this.panel);
	}
	this.msg = typeof options.msgClass === "string" ? options.msgClass : undefined;
	this.fileParent = undefined;
	this.fileClass = undefined;
	this.file = undefined;
	if (typeof options.fileClass === "string") {
		this.file = $("." + options.fileClass)[0];
		this.fileClass = $(this.file).attr("class");
		var jclass = "." + this.fileClass;
		this.fileParent = $(jclass).parent();
		$(this.fileParent).on("change", jclass, function(event) {
			var fileList = that.file.files;
			var reader = new FileReader();
			if (fileList.length === 0) {
				return;
			}
			reader.readAsText(fileList[0]);
			$(reader).on("load", function(event) {
				var obj = JSON.parse(reader.result.replace(/(\s*)/g, ''));
				var flag = that.setStructure(obj);
				that.updateStructure();
			});
			$(that.file).remove();
			that.file = $("<input>").attr({
				"type" : "file"
			}).css("display", "none").addClass(that.fileClass)[0];
			$(that.fileParent).append(that.file);
		});
	}
	$(this.panelBody).on("click", ".gb-layerdefinition-delete-category", function() {
		that.deleteCategory(this);
		console.log(that.getStructure());
	});

	$(this.panelBody).on("click", ".gb-layerdefinition-add-layer", function() {
		that.addLayer(this);
		console.log(that.getStructure());
	});

	$(this.panelBody).on("click", ".gb-layerdefinition-toggle-category", function() {
		that.toggleLayerArea(this);
	});

	$(this.panelBody).on("click", ".gb-layerdefinition-add-attribute", function() {
		that.addAttribute(this);
		console.log(that.getStructure());
	});

	$(this.panelBody).on("input", ".gb-layerdefinition-input-categoryname", function() {
		that.inputCategoryName(this);
		console.log(that.getStructure());
	});

	$(this.panelBody).on("input", ".gb-layerdefinition-input-layercode", function() {
		that.inputLayerCode(this);
		console.log(that.getStructure());
	});

	$(this.panelBody).on("change", ".gb-layerdefinition-select-geometry", function() {
		that.selectLayerGeometry(this);
		console.log(that.getStructure());
	});

	$(this.panelBody).on("click", ".gb-layerdefinition-delete-layer", function() {
		that.deleteLayer(this);
		console.log(that.getStructure());
	});

	$(this.panelBody).on("click", ".gb-layerdefinition-delete-attribute", function() {
		that.deleteAttribute(this);
		console.log(that.getStructure());
	});

	$(this.panelBody).on("input", ".gb-layerdefinition-input-attributename", function() {
		that.inputAttributeName(this);
		console.log(that.getStructure());
	});

	$(this.panelBody).on("change", ".gb-layerdefinition-select-attributetype", function() {
		that.selectAttributeType(this);
		console.log(that.getStructure());
	});

	$(this.panelBody).on("input", ".gb-layerdefinition-input-attributelength", function() {
		that.inputAttributeLength(this);
		console.log(that.getStructure());
	});

	$(this.panelBody).on("change", ".gb-layerdefinition-check-attributenull", function() {
		that.checkAttributeNull(this);
		console.log(that.getStructure());
	});

	$(this.panelBody).on("input", ".gb-layerdefinition-input-attributevalues", function() {
		that.inputAttributeValues(this);
		console.log(that.getStructure());
	});

	$(this.panelBody).tooltip({
		"html" : true,
		"placement" : "right",
		"selector" : ".gb-layerdefinition-input-categoryname"
	});

};
gb.embed.LayerDefinition.prototype = Object.create(gb.embed.LayerDefinition.prototype);
gb.embed.LayerDefinition.prototype.constructor = gb.embed.LayerDefinition;

gb.embed.LayerDefinition.prototype.setMessage = function(type, message) {
	var alert = "alert-";
	switch (type) {
	case "success":
		alert = alert + "success";
		break;
	case "info":
		alert = alert + "info";
		break;
	case "warning":
		alert = alert + "warning";
		break;
	case "danger":
		alert = alert + "danger";
		break;
	default:
		alert = alert + "info";
		break;
	}
	var span = $("<span>").attr("aria-hidden", "true").html("&times;");
	var xbtn = $("<button>").addClass("close").attr("type", "button").attr("data-dismiss", "alert").attr("aria-label", "Close")
			.append(span);
	var head = $("<strong>").text("알림 ");
	var div = $("<div>").addClass("alert").addClass(alert).addClass("alert-dismissible").attr("role", "alert").append(xbtn).append(head)
			.append(message);
	var jclass = "." + this.msg;
	$(jclass).append(div);
};

gb.embed.LayerDefinition.prototype.getInputFile = function() {
	return this.file;
};

gb.embed.LayerDefinition.prototype.updateStructure = function() {
	console.log(this.getStructure());
	$(this.panelBody).empty();
	var strc = this.getStructure();
	if (!Array.isArray(strc)) {
		return;
	}
	for (var a = 0; a < strc.length; a++) {
		// 카테고리 입력
		var toggleIcon = $("<i>").addClass("fas").addClass("fa-caret-up").addClass("fa-lg");
		var toggleBtn = $("<button>").addClass("btn").addClass("btn-link").addClass("gb-layerdefinition-toggle-category")
				.append(toggleIcon);
		var categoryName = $("<input>").attr({
			"type" : "text",
			"placeholder" : "분류명 EX) 등고선, F001 등",
			"title" : "some tips"
		}).css({
			"border" : "0",
			"border-bottom" : "solid 1px #9a9a9a",
			"height" : "32px",
			"width" : "100%"
		}).addClass("gb-layerdefinition-input-categoryname").val(strc[a].name);

		var catNameCol = $("<div>").addClass("col-md-11").append(categoryName);
		var toggleCol = $("<div>").addClass("col-md-1").addClass("text-right").append(toggleBtn);
		var categoryHeader = $("<div>").addClass("row").append(catNameCol).append(toggleCol);
		var deleteCategoryBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-layerdefinition-delete-category").text(
				"분류 삭제");
		var addLayerBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-layerdefinition-add-layer").text("레이어 추가");
		var wrap2 = $("<div>").addClass("col-md-12").addClass("text-right").append(addLayerBtn).append(deleteCategoryBtn);
		var categoryMid = $("<div>").addClass("row").append(wrap2);
		var layerArea = $("<div>").addClass("gb-layerdefinition-layerarea");

		// 레이어 입력
		var layers = strc[a].layers;
		if (Array.isArray(layers)) {
			for (var b = 0; b < layers.length; b++) {
				var codeCol1 = $("<div>").addClass("col-md-1").append("코드");
				var code = layers[b].code;
				var codeInput = $("<input>").attr({
					"type" : "text",
					"placeholder" : "레이어 코드 EX) F0010000"
				}).addClass("form-control").addClass("gb-layerdefinition-input-layercode").val(code);

				var codeCol2 = $("<div>").addClass("col-md-6").append(codeInput);
				var typeCol1 = $("<div>").addClass("col-md-1").text("타입");
				var geomSelect = $("<select>").addClass("form-control").addClass("gb-layerdefinition-select-geometry");
				var geometry = layers[b].geometry;
				for (var i = 0; i < this.geometryType.length; i++) {
					var option = $("<option>").text(this.geometryType[i].toUpperCase()).attr("value", this.geometryType[i]);
					if (geometry === $(option).val()) {
						$(option).attr("selected", "selected");
					}
					$(geomSelect).append(option);
				}
				var typeCol2 = $("<div>").addClass("col-md-2").append(geomSelect);
				var delBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-layerdefinition-delete-layer").text(
						"레이어 삭제").css("width", "100%");
				var deleLayerCol1 = $("<div>").addClass("col-md-2").append(delBtn);
				var row1o = $("<div>").addClass("row").append(codeCol1).append(codeCol2).append(typeCol1).append(typeCol2).append(
						deleLayerCol1);

				var fixAttr = $("<p>").text("고정 속성").css({
					"float" : "left"
				});
				var delFixAttr = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-layerdefinition-add-attribute").text(
						"고정 속성 추가").css({
					"width" : "100%"
				});
				var fixCol1 = $("<div>").addClass("col-md-10").append(fixAttr);
				var fixCol2 = $("<div>").addClass("col-md-2").append(delFixAttr);
				var row2o = $("<div>").addClass("row").append(fixCol1).append(fixCol2);

				var attrAreaCol1 = $("<div>").addClass("col-md-12").addClass("gb-layerdefinition-attributearea");

				var fix = layers[b].fix;
				if (Array.isArray(fix)) {
					for (var k = 0; k < fix.length; k++) {
						var r1col1 = $("<div>").addClass("col-md-1").text("속성명");

						var attrName = $("<input>").attr({
							"type" : "text",
							"placeholder" : "고정 값을 가질 속성명 EX) 구분"
						}).addClass("form-control").addClass("gb-layerdefinition-input-attributename").val(fix[k].name);
						var r1col2 = $("<div>").addClass("col-md-3").append(attrName);

						var r1col3 = $("<div>").addClass("col-md-1").text("타입");

						var dtypeSelect = $("<select>").addClass("form-control").addClass("gb-layerdefinition-select-attributetype");
						var nullOption = $("<option>").text("미설정").attr("value", "null");
						$(dtypeSelect).append(nullOption);
						for (var i = 0; i < this.dataType.length; i++) {
							var option = $("<option>").text(this.dataType[i]).attr("value", this.dataType[i]);
							if (fix[k].type === $(option).val()) {
								$(option).attr("selected", "selected");
							} else if (fix[k].type === null) {
								$(nullOption).attr("selected", "selected");
							}
							$(dtypeSelect).append(option);
						}
						var r1col4 = $("<div>").addClass("col-md-2").append(dtypeSelect);

						var r1col5 = $("<div>").addClass("col-md-1").text("길이");

						var attrLength = $("<input>").attr("type", "number").addClass("form-control").addClass(
								"gb-layerdefinition-input-attributelength").val(fix[k].length);
						var r1col6 = $("<div>").addClass("col-md-2").append(attrLength);

						var r1col7 = $("<div>").addClass("col-md-1").text("널 허용");

						var nullCheck = $("<input>").attr("type", "checkbox").addClass("gb-layerdefinition-check-attributenull");
						if (fix[k].isnull) {
							$(nullCheck).prop("checked", true);
						}
						var r1col8 = $("<div>").addClass("col-md-1").append(nullCheck);

						var row1 = $("<div>").addClass("row").append(r1col1).append(r1col2).append(r1col3).append(r1col4).append(r1col5)
								.append(r1col6).append(r1col7).append(r1col8);

						var r2col1 = $("<div>").addClass("col-md-1").text("허용값");
						var values = $("<input>").attr({
							"type" : "text",
							"placeholder" : "해당 속성이 가질 수 있는 값들을 콤마(,)로 구분하여 입력 EX) 주곡선,계곡선,간곡선,조곡선"
						}).addClass("form-control").addClass("gb-layerdefinition-input-attributevalues");

						var valuesToString = fix[k].values;
						if (Array.isArray(valuesToString)) {
							$(values).val(valuesToString.toString());
						}

						var r2col2 = $("<div>").addClass("col-md-11").append(values);
						var row2 = $("<div>").addClass("row").append(r2col1).append(r2col2);

						var delBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-layerdefinition-delete-attribute")
								.text("고정 속성 삭제");
						var r3col1 = $("<div>").addClass("col-md-12").addClass("text-right").append(delBtn);
						var row3 = $("<div>").addClass("row").append(r3col1);

						var pBody = $("<div>").addClass("panel-body").append(row1).append(row2).append(row3);
						var pn = $("<div>").addClass("panel").addClass("panel-default").append(pBody);
						$(attrAreaCol1).append(pn);
					}
				}

				var row3o = $("<div>").addClass("row").append(attrAreaCol1);
				var well = $("<div>").addClass("well").append(row1o).append(row2o).append(row3o);
				$(layerArea).append(well);
			}
		}

		var wrap3 = $("<div>").addClass("col-md-12").append(layerArea);
		var layerAreaRow = $("<div>").addClass("row").append(wrap3);
		var toggleArea = $("<div>").addClass("gb-layerdefinition-togglearea").append(categoryMid).append(layerAreaRow);
		var pBody = $("<div>").addClass("panel-body").append(categoryHeader).append(toggleArea);
		var pn = $("<div>").addClass("panel").addClass("panel-default").append(pBody);
		$(this.panelBody).append(pn);
	}
};

gb.embed.LayerDefinition.prototype.clearStructure = function() {
	this.structure = [];
};

gb.embed.LayerDefinition.prototype.setStructure = function(strc) {
	var isOK = true;
	var elemName = [ "name", "layers" ];
	var elemLayers = [ "fix", "code", "geometry" ];
	var elemFix = [ "name", "type", "isnull", "length", "values" ];
	if (Array.isArray(strc)) {
		for (var i = 0; i < strc.length; i++) {
			var nameKeys = Object.keys(strc[i]);
			for (var j = 0; j < nameKeys.length; j++) {
				if (elemName.indexOf(nameKeys[j]) === -1) {
					isOK = false;
					this.setMessage("danger", " 키 네임 " + nameKeys[i] + "은/는 유효한 키 네임이 아닙니다.");
					console.error("키 네임 " + nameKeys[i] + "은/는 유효한 키 네임이 아닙니다.");
					break;
				}
			}
			if (!strc[i].hasOwnProperty("name")) {
				isOK = false;
				this.setMessage("danger", " " + (i + 1) + "번째 분류의 분류명을 입력해야 합니다.");
				console.error((i + 1) + "번째 분류의 분류명을 입력해야 합니다.");
				break;
			}
			if (strc[i].hasOwnProperty("layers")) {
				var layers = strc[i]["layers"];
				if (Array.isArray(layers)) {
					for (var j = 0; j < layers.length; j++) {
						var layerKeys = Object.keys(layers[j]);
						for (var k = 0; k < layerKeys.length; k++) {
							if (elemLayers.indexOf(layerKeys[k]) === -1) {
								isOK = false;
								this.setMessage("danger", " 키 네임 " + layerKeys[k] + "은/는 유효한 키 네임이 아닙니다.");
								console.error("키 네임 " + layerKeys[k] + "은/는 유효한 키 네임이 아닙니다.");
								break;
							}
						}
						if (layers.hasOwnProperty("fix")) {
							if (Array.isArray(layers[j]["fix"])) {
								for (var k = 0; k < layers[j]["fix"].length; k++) {
									var fixKeys = Object.keys(layers[j]["fix"][k]);
									for (var l = 0; l < fixKeys.length; l++) {
										if (elemFix.indexOf(fixKeys[l]) === -1) {
											isOK = false;
											this.setMessage("danger", " 키 네임 " + fixKeys[l] + "은/는 유효한 키 네임이 아닙니다.");
											console.error("키 네임 " + fixKeys[l] + "은/는 유효한 키 네임이 아닙니다.");
											break;
										}
									}
								}
							}
						}
					}
				}
			} else {
				isOK = false;
				this.setMessage("danger", " " + (i + 1) + "번째 분류에 포함된 레이어가 없습니다.");
				console.error((i + 1) + "번째 분류에 포함된 레이어가 없습니다.");
				break;
			}
		}
	} else {
		isOK = false;
		this.setMessage("danger", " 옵션의 최상위 구조는 배열을 형태여야 합니다.");
		console.error("옵션의 최상위 구조는 배열을 형태여야 합니다.");
	}
	if (isOK) {
		this.structure = strc;
		this.setMessage("success", " [레이어 정의]가 변경 되었습니다.");
	}
	return isOK;
};

gb.embed.LayerDefinition.prototype.getStructure = function() {
	return this.structure;
};

gb.embed.LayerDefinition.prototype.setJSONFile = function() {

};

gb.embed.LayerDefinition.prototype.getJSONFile = function() {
	// Opera 8.0+
	var isOpera = (!!window.opr && !!opr.addons) || !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;

	// Firefox 1.0+
	var isFirefox = typeof InstallTrigger !== 'undefined';

	// Safari 3.0+ "[object HTMLElementConstructor]"
	var isSafari = /constructor/i.test(window.HTMLElement) || (function(p) {
		return p.toString() === "[object SafariRemoteNotification]";
	})(!window['safari'] || (typeof safari !== 'undefined' && safari.pushNotification));

	// Internet Explorer 6-11
	var isIE = /* @cc_on!@ */false || !!document.documentMode;

	// Edge 20+
	var isEdge = !isIE && !!window.StyleMedia;

	// Chrome 1+
	var isChrome = !!window.chrome && !!window.chrome.webstore;

	// Blink engine detection
	var isBlink = (isChrome || isOpera) && !!window.CSS;

	if (isIE) {
		download(JSON.stringify(this.getStructure()), "layer_setting.json", "text/plain");
	} else {
		var setting = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(this.getStructure()));
		var anchor = $("<a>").attr({
			"href" : setting,
			"download" : "layer_setting.json"
		});
		$(anchor)[0].click();
	}
};

gb.embed.LayerDefinition.prototype.inputAttributeValues = function(inp) {
	var catIdx = $(inp).parents().eq(12).index();
	var layerIdx = $(inp).parents().eq(6).index();
	var attrIdx = $(inp).parents().eq(3).index();

	var strc = this.getStructure();
	if (Array.isArray(strc)) {
		var cat = strc[catIdx];
		var layers = cat["layers"];
		if (Array.isArray(layers)) {
			var layer = layers[layerIdx];
			var fix = layer["fix"];
			if (Array.isArray(fix)) {
				fixElem = fix[attrIdx];
				if (fixElem !== undefined && fixElem !== null) {
					fixElem["values"] = $(inp).val().replace(/(\s*)/g, '').split(",");
				} else {
					var obj = {
						"values" : $(inp).val().replace(/(\s*)/g, '').split(",")
					};
				}
			}
		}
	}
};

gb.embed.LayerDefinition.prototype.checkAttributeNull = function(chk) {
	var catIdx = $(chk).parents().eq(12).index();
	var layerIdx = $(chk).parents().eq(6).index();
	var attrIdx = $(chk).parents().eq(3).index();

	var strc = this.getStructure();
	if (Array.isArray(strc)) {
		var cat = strc[catIdx];
		var layers = cat["layers"];
		if (Array.isArray(layers)) {
			var layer = layers[layerIdx];
			var fix = layer["fix"];
			if (Array.isArray(fix)) {
				fixElem = fix[attrIdx];
				if (fixElem !== undefined && fixElem !== null) {
					fixElem["isnull"] = $(chk).is(":checked");
				} else {
					var obj = {
						"isnull" : $(chk).is(":checked")
					};
				}
			}
		}
	}
};

gb.embed.LayerDefinition.prototype.inputAttributeLength = function(inp) {
	var catIdx = $(inp).parents().eq(12).index();
	var layerIdx = $(inp).parents().eq(6).index();
	var attrIdx = $(inp).parents().eq(3).index();

	var strc = this.getStructure();
	if (Array.isArray(strc)) {
		var cat = strc[catIdx];
		var layers = cat["layers"];
		if (Array.isArray(layers)) {
			var layer = layers[layerIdx];
			var fix = layer["fix"];
			if (Array.isArray(fix)) {
				fixElem = fix[attrIdx];
				if (fixElem !== undefined && fixElem !== null) {
					fixElem["length"] = isNaN(parseInt($(inp).val())) ? null : parseInt($(inp).val());
				} else {
					var obj = {
						"length" : isNaN(parseInt($(inp).val())) ? null : parseInt($(inp).val())
					};
				}
			}
		}
	}
};

gb.embed.LayerDefinition.prototype.selectAttributeType = function(sel) {
	var catIdx = $(sel).parents().eq(12).index();
	var layerIdx = $(sel).parents().eq(6).index();
	var attrIdx = $(sel).parents().eq(3).index();

	var strc = this.getStructure();
	if (Array.isArray(strc)) {
		var cat = strc[catIdx];
		var layers = cat["layers"];
		if (Array.isArray(layers)) {
			var layer = layers[layerIdx];
			var fix = layer["fix"];
			if (Array.isArray(fix)) {
				fixElem = fix[attrIdx];
				if (fixElem !== undefined && fixElem !== null) {
					fixElem["type"] = $(sel).val() === "null" ? null : $(sel).val();
				} else {
					var obj = {
						"type" : $(sel).val() === "null" ? null : $(sel).val()
					};
				}
			}
		}
	}
};

gb.embed.LayerDefinition.prototype.inputAttributeName = function(inp) {
	var catIdx = $(inp).parents().eq(12).index();
	var layerIdx = $(inp).parents().eq(6).index();
	var attrIdx = $(inp).parents().eq(3).index();

	var strc = this.getStructure();
	if (Array.isArray(strc)) {
		var cat = strc[catIdx];
		var layers = cat["layers"];
		if (Array.isArray(layers)) {
			var layer = layers[layerIdx];
			var fix = layer["fix"];
			if (Array.isArray(fix)) {
				fixElem = fix[attrIdx];
				if (fixElem !== undefined && fixElem !== null) {
					fixElem["name"] = $(inp).val();
				} else {
					var obj = {
						"name" : $(inp).val()
					};
				}
			}
		}
	}
};

gb.embed.LayerDefinition.prototype.deleteAttribute = function(btn) {
	var catIdx = $(btn).parents().eq(12).index();
	var layerIdx = $(btn).parents().eq(6).index();
	var attrIdx = $(btn).parents().eq(3).index();

	var strc = this.getStructure();
	if (Array.isArray(strc)) {
		var cat = strc[catIdx];
		var layers = cat["layers"];
		if (Array.isArray(layers)) {
			var layer = layers[layerIdx];
			var fix = layer["fix"];
			if (Array.isArray(fix)) {
				layer["fix"].splice(attrIdx, 1);
				$(btn).parents().eq(3).remove();
			}
		}
	}
};

gb.embed.LayerDefinition.prototype.deleteLayer = function(btn) {
	var catIdx = $(btn).parents().eq(8).index();
	console.log(catIdx);
	var layerIdx = $(btn).parents().eq(2).index();
	console.log(layerIdx);
	var strc = this.getStructure();
	if (Array.isArray(strc)) {
		var cat = strc[catIdx];
		var layers = cat["layers"];
		if (Array.isArray(layers)) {
			cat["layers"].splice(layerIdx, 1);
			$(btn).parents().eq(2).remove();
		}
	}
};

gb.embed.LayerDefinition.prototype.selectLayerGeometry = function(sel) {
	var catIdx = $(sel).parents().eq(8).index();
	console.log(catIdx);
	var codeIdx = $(sel).parents().eq(2).index();
	console.log(codeIdx);
	var strc = this.getStructure();
	if (Array.isArray(strc)) {
		var cat = strc[catIdx];
		var layers = cat["layers"];
		if (Array.isArray(layers)) {
			var layer = layers[codeIdx];
			layer["geometry"] = $(sel).val();
		}
	}
};

gb.embed.LayerDefinition.prototype.inputLayerCode = function(inp) {
	var catIdx = $(inp).parents().eq(8).index();
	console.log(catIdx);
	var codeIdx = $(inp).parents().eq(2).index();
	console.log(codeIdx);
	var strc = this.getStructure();
	if (Array.isArray(strc)) {
		var cat = strc[catIdx];
		var layers = cat["layers"];
		if (Array.isArray(layers)) {
			var layer = layers[codeIdx];
			layer["code"] = $(inp).val();
		}
	}
};

gb.embed.LayerDefinition.prototype.deleteCategory = function(btn) {
	var idx = $(btn).parents().eq(4).index();
	var strc = this.getStructure();
	var elem = strc[idx];
	if (elem !== undefined && elem !== null) {
		this.getStructure().splice(idx, 1);
	}
	$(btn).parents().eq(4).remove();
};

gb.embed.LayerDefinition.prototype.inputCategoryName = function(inp) {
	var idx = $(inp).parents().eq(3).index();
	var strc = this.getStructure();
	var elem = strc[idx];
	if (elem !== undefined && elem !== null) {
		elem["name"] = $(inp).val();
	} else {
		strc[idx] = {
			"name" : $(inp).val()
		};
	}
};

gb.embed.LayerDefinition.prototype.toggleLayerArea = function(btn) {
	var toggleArea = $(btn).parent().parent().next();
	var icon = $(btn).find("i");
	if ($(toggleArea).hasClass("gb-layerdefinition-togglearea")) {
		if ($(toggleArea).is(":visible")) {
			$(toggleArea).hide();
			if ($(icon).hasClass("fa-caret-up")) {
				$(icon).removeClass("fa-caret-up");
				$(icon).addClass("fa-caret-down");
			}
		} else {
			$(toggleArea).show();
			if ($(icon).hasClass("fa-caret-down")) {
				$(icon).removeClass("fa-caret-down");
				$(icon).addClass("fa-caret-up");
			}
		}
	}
};

gb.embed.LayerDefinition.prototype.addCategory = function() {
	var toggleIcon = $("<i>").addClass("fas").addClass("fa-caret-up").addClass("fa-lg");
	var toggleBtn = $("<button>").addClass("btn").addClass("btn-link").addClass("gb-layerdefinition-toggle-category").append(toggleIcon);
	var categoryName = $("<input>").attr({
		"type" : "text",
		"placeholder" : "분류명 EX) 등고선, F001 등",
		"title" : "some tips"
	}).css({
		"border" : "0",
		"border-bottom" : "solid 1px #9a9a9a",
		"height" : "32px",
		"width" : "100%"
	}).addClass("gb-layerdefinition-input-categoryname");

	var catNameCol = $("<div>").addClass("col-md-11").append(categoryName);
	var toggleCol = $("<div>").addClass("col-md-1").addClass("text-right").append(toggleBtn);
	var categoryHeader = $("<div>").addClass("row").append(catNameCol).append(toggleCol);
	var deleteCategoryBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-layerdefinition-delete-category").text(
			"분류 삭제");
	var addLayerBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-layerdefinition-add-layer").text("레이어 추가");
	var wrap2 = $("<div>").addClass("col-md-12").addClass("text-right").append(addLayerBtn).append(deleteCategoryBtn);
	var categoryMid = $("<div>").addClass("row").append(wrap2);

	var layerArea = $("<div>").addClass("gb-layerdefinition-layerarea");
	var wrap3 = $("<div>").addClass("col-md-12").append(layerArea);
	var layerAreaRow = $("<div>").addClass("row").append(wrap3);
	var toggleArea = $("<div>").addClass("gb-layerdefinition-togglearea").append(categoryMid).append(layerAreaRow);
	var pBody = $("<div>").addClass("panel-body").append(categoryHeader).append(toggleArea);
	var pn = $("<div>").addClass("panel").addClass("panel-default").append(pBody);
	$(this.panelBody).append(pn);

	var strc = this.getStructure();
	var obj = {
		"name" : null,
		"layers" : null
	};
	strc.push(obj);
};

gb.embed.LayerDefinition.prototype.addLayer = function(btn) {
	var col1 = $("<div>").addClass("col-md-1").append("코드");
	var codeInput = $("<input>").attr({
		"type" : "text",
		"placeholder" : "레이어 코드 EX) F0010000"
	}).addClass("form-control").addClass("gb-layerdefinition-input-layercode");

	var col2 = $("<div>").addClass("col-md-6").append(codeInput);
	var col3 = $("<div>").addClass("col-md-1").text("타입");
	var geomSelect = $("<select>").addClass("form-control").addClass("gb-layerdefinition-select-geometry");
	for (var i = 0; i < this.geometryType.length; i++) {
		var option = $("<option>").text(this.geometryType[i].toUpperCase()).attr("value", this.geometryType[i]);
		if (i === 0) {
			$(option).attr("selected", "selected");
		}
		$(geomSelect).append(option);
	}
	var col4 = $("<div>").addClass("col-md-2").append(geomSelect);
	var delBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-layerdefinition-delete-layer").text("레이어 삭제").css(
			"width", "100%");
	var col5 = $("<div>").addClass("col-md-2").append(delBtn);
	var row1 = $("<div>").addClass("row").append(col1).append(col2).append(col3).append(col4).append(col5);

	var fixAttr = $("<p>").text("고정 속성").css({
		"float" : "left"
	});
	var delFixAttr = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-layerdefinition-add-attribute").text("고정 속성 추가")
			.css({
				"width" : "100%"
			});
	var r2col1 = $("<div>").addClass("col-md-10").append(fixAttr);
	var r2col2 = $("<div>").addClass("col-md-2").append(delFixAttr);
	var row2 = $("<div>").addClass("row").append(r2col1).append(r2col2);

	var r3col1 = $("<div>").addClass("col-md-12").addClass("gb-layerdefinition-attributearea");
	var row3 = $("<div>").addClass("row").append(r3col1);
	var well = $("<div>").addClass("well").append(row1).append(row2).append(row3);
	$(btn).parent().parent().next().find(".gb-layerdefinition-layerarea").append(well);

	var obj = {
		"code" : null,
		"geometry" : $(geomSelect).val(),
		"fix" : null
	}
	console.log(obj);
	var catIdx = $(well).parents().eq(5).index();
	var strc = this.getStructure();
	if (Array.isArray(strc)) {
		var cat = strc[catIdx];
		var layerIdx = $(well).index();
		var layers = cat["layers"];
		if (Array.isArray(layers)) {
			layers[layerIdx] = obj;
		} else {
			cat["layers"] = [];
			cat["layers"].push(obj);
		}
	}
};

gb.embed.LayerDefinition.prototype.addAttribute = function(btn) {
	var r1col1 = $("<div>").addClass("col-md-1").text("속성명");

	var attrName = $("<input>").attr({
		"type" : "text",
		"placeholder" : "고정 값을 가질 속성명 EX) 구분"
	}).addClass("form-control").addClass("gb-layerdefinition-input-attributename");
	var r1col2 = $("<div>").addClass("col-md-3").append(attrName);

	var r1col3 = $("<div>").addClass("col-md-1").text("타입");

	var dtypeSelect = $("<select>").addClass("form-control").addClass("gb-layerdefinition-select-attributetype");
	var nullOption = $("<option>").text("미설정").attr("value", "null");
	$(dtypeSelect).append(nullOption);
	for (var i = 0; i < this.dataType.length; i++) {
		var option = $("<option>").text(this.dataType[i]).attr("value", this.dataType[i]);
		/*
		 * if (i === 0) { $(option).attr("selected", "selected"); }
		 */
		$(dtypeSelect).append(option);
	}
	var r1col4 = $("<div>").addClass("col-md-2").append(dtypeSelect);

	var r1col5 = $("<div>").addClass("col-md-1").text("길이");

	var attrLength = $("<input>").attr("type", "number").addClass("form-control").addClass("gb-layerdefinition-input-attributelength");
	var r1col6 = $("<div>").addClass("col-md-2").append(attrLength);

	var r1col7 = $("<div>").addClass("col-md-1").text("널 허용");

	var nullCheck = $("<input>").attr("type", "checkbox").addClass("gb-layerdefinition-check-attributenull");
	var r1col8 = $("<div>").addClass("col-md-1").append(nullCheck);

	var row1 = $("<div>").addClass("row").append(r1col1).append(r1col2).append(r1col3).append(r1col4).append(r1col5).append(r1col6).append(
			r1col7).append(r1col8);

	var r2col1 = $("<div>").addClass("col-md-1").text("허용값");
	var values = $("<input>").attr({
		"type" : "text",
		"placeholder" : "해당 속성이 가질 수 있는 값들을 콤마(,)로 구분하여 입력 EX) 주곡선,계곡선,간곡선,조곡선"
	}).addClass("form-control").addClass("gb-layerdefinition-input-attributevalues");
	var r2col2 = $("<div>").addClass("col-md-11").append(values);
	var row2 = $("<div>").addClass("row").append(r2col1).append(r2col2);

	var delBtn = $("<button>").addClass("btn").addClass("btn-default").addClass("gb-layerdefinition-delete-attribute").text("고정 속성 삭제");
	var r3col1 = $("<div>").addClass("col-md-12").addClass("text-right").append(delBtn);
	var row3 = $("<div>").addClass("row").append(r3col1);

	var pBody = $("<div>").addClass("panel-body").append(row1).append(row2).append(row3);
	var pn = $("<div>").addClass("panel").addClass("panel-default").append(pBody);
	$(btn).parent().parent().next().find(".gb-layerdefinition-attributearea").append(pn);

	var obj = {
		"name" : null,
		"type" : $(dtypeSelect).val(),
		"isnull" : false,
		"length" : null,
		"values" : null
	};

	var catIdx = $(pn).parents().eq(8).index();
	var layerIdx = $(pn).parents().eq(2).index();
	var attrIdx = $(pn).index();

	var strc = this.getStructure();
	if (Array.isArray(strc)) {
		var cat = strc[catIdx];
		var layers = cat["layers"];
		if (Array.isArray(layers)) {
			var layer = layers[layerIdx];
			var fix = layer["fix"];
			if (Array.isArray(fix)) {
				layer["fix"][attrIdx] = obj;
			} else {
				layer["fix"] = [];
				layer["fix"].push(obj);
			}
		}
	}
};