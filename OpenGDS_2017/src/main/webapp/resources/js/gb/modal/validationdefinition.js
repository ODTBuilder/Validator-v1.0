/**
 * 검수 옵션 설정 모달 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2017. 07.26
 * @version 0.01
 * @class gb.modal.ValidationDefinition
 * @constructor
 */
var gb;
if (!gb)
	gb = {};
if (!gb.modal)
	gb.modal = {};
gb.modal.ValidationDefinition = function(obj) {
	obj["width"] = 900;
	obj["height"] = 710;
	gb.modal.Base.call(this, obj);
	var options = obj ? obj : {};
	this.definition = obj.definition ? obj.definition : undefined;
	this.layerDefinition = obj.layerDefinition ? obj.layerDefinition : undefined;
	this.itemList = {
		polyline : [ "SelfEntity", "BuildingOpen", "WaterOpen", "EntityNone", "EdgeMatchMiss", "ConBreak", "ConIntersected",
				"ConOverDegree", "UselessEntity", "EntityDuplicated", "PointDuplicated", "UselessPoint", "LayerMiss", "ZValueAmbiguous",
				"OverShoot", "UnderShoot", "RefLayerMiss", "RefZValueMiss" ],
		lwpolyline : [ "SelfEntity", "BSymbolOutSided", "BuildingOpen", "WaterOpen", "EntityNone", "EdgeMatchMiss", "ConBreak",
				"ConIntersected", "ConOverDegree", "UselessEntity", "EntityDuplicated", "PointDuplicated", "UselessPoint", "LayerMiss",
				"ZValueAmbiguous", "OverShoot", "UnderShoot", "RefLayerMiss", "RefZValueMiss" ],
		text : [ "SelfEntity", "LayerMiss", "UselessEntity", "EntityDuplicated", "OverShoot", "UFIDLength", "NumericalValue", "UFIDRule" ],
		insert : [ "SelfEntity", "LayerMiss", "UselessEntity", "EntityDuplicated", "BSymbolOutSided", "OverShoot" ],
		point : [ "LayerMiss", "UselessEntity", "EntityDuplicated", "SelfEntity", "AttributeFix", "OutBoundary", "CharacterAccuracy",
				"OverShoot", "UnderShoot", "UFIDLength", "NumericalValue", "UFIDRule", "UFIDDuplicated" ],
		multipoint : [ "LayerMiss", "UselessEntity", "EntityDuplicated", "SelfEntity", "AttributeFix", "OutBoundary", "CharacterAccuracy",
				"OverShoot", "UnderShoot", "UFIDLength", "NumericalValue", "UFIDRule", "UFIDDuplicated" ],
		linestring : [ "RefAttributeMiss", "EdgeMatchMiss", "UselessEntity", "LayerMiss", "RefLayerMiss", "SmallLength",
				"EntityDuplicated", "SelfEntity", "PointDuplicated", "ConIntersected", "ConOverDegree", "ConBreak", "AttributeFix",
				"OutBoundary", "ZValueAmbiguous", "UselessPoint", "OverShoot", "UnderShoot", "RefZValueMiss", "UFIDLength",
				"NeatLineAttribute", "NumericalValue", "UFIDRule", "LinearDisconnection", "MultiPart", "UFIDDuplicated", "NodeMiss" ],
		multilinestring : [ "RefAttributeMiss", "EdgeMatchMiss", "UselessEntity", "LayerMiss", "RefLayerMiss", "SmallLength",
				"EntityDuplicated", "SelfEntity", "PointDuplicated", "ConIntersected", "ConOverDegree", "ConBreak", "AttributeFix",
				"OutBoundary", "ZValueAmbiguous", "UselessPoint", "OverShoot", "UnderShoot", "RefZValueMiss", "UFIDLength",
				"NeatLineAttribute", "NumericalValue", "UFIDRule", "LinearDisconnection", "MultiPart", "UFIDDuplicated", "NodeMiss" ],
		polygon : [ "Admin", "CrossRoad", "RefAttributeMiss", "BridgeName", "EdgeMatchMiss", "UselessEntity", "LayerMiss", "RefLayerMiss",
				"SmallArea", "EntityDuplicated", "SelfEntity", "PointDuplicated", "AttributeFix", "OutBoundary", "OverShoot", "UnderShoot",
				"OneAcre", "OneStage", "BuildingSiteDanger", "BuildingSiteRelaxation", "UFIDLength", "HouseAttribute", "CemeterySite",
				"NumericalValue", "RiverBoundaryMiss", "UFIDRule", "HoleMisplacement", "CenterLineMiss", "EntityInHole", "TwistedPolygon",
				"MultiPart", "UFIDDuplicated" ],
		multipolygon : [ "Admin", "CrossRoad", "RefAttributeMiss", "BridgeName", "EdgeMatchMiss", "UselessEntity", "LayerMiss",
				"RefLayerMiss", "SmallArea", "EntityDuplicated", "SelfEntity", "PointDuplicated", "AttributeFix", "OutBoundary",
				"OverShoot", "UnderShoot", "OneAcre", "OneStage", "BuildingSiteDanger", "BuildingSiteRelaxation", "UFIDLength",
				"HouseAttribute", "CemeterySite", "NumericalValue", "RiverBoundaryMiss", "UFIDRule", "HoleMisplacement", "CenterLineMiss",
				"EntityInHole", "TwistedPolygon", "MultiPart", "UFIDDuplicated" ]
	};
	this.optItem = {
		"UFIDDuplicated" : {
			"title" : "UFID Duplication",
			"alias" : "UFIDDuplicated",
			"type" : "none"
		},
		"MultiPart" : {
			"title" : "Entity Looking Like Multipart",
			"alias" : "MultiPart",
			"type" : "none"
		},
		"TwistedPolygon" : {
			"title" : "Twisted Polygon",
			"alias" : "TwistedPolygon",
			"type" : "none"
		},
		"LinearDisconnection" : {
			"title" : "Linear Disconnection",
			"alias" : "LinearDisconnection",
			"type" : "relation"
		},
		"EntityInHole" : {
			"title" : "Entity In Hole",
			"alias" : "EntityInHole",
			"type" : "relation"
		},
		"HoleMisplacement" : {
			"title" : "Misplaced Hole Error",
			"alias" : "HoleMisplacement",
			"type" : "none"
		},
		"CenterLineMiss" : {
			"title" : "Missing Centerline Error",
			"alias" : "CenterLineMiss",
			"type" : "relation"
		},
		"UFIDRule" : {
			"title" : "UFID Rule Violation",
			"alias" : "UFIDRule",
			"type" : "none"
		},
		"RiverBoundaryMiss" : {
			"title" : "Missing River Boundary Error",
			"alias" : "RiverBoundaryMiss",
			"type" : "relation"
		},
		"NumericalValue" : {
			"title" : "Numerical Value Error",
			"alias" : "NumericalValue",
			"type" : "conditionalfigure"
		},
		"CemeterySite" : {
			"title" : "Cemetery Site Error",
			"alias" : "CemeterySite",
			"type" : "relation"
		},
		"HouseAttribute" : {
			"title" : "House Attribute Error",
			"alias" : "HouseAttribute",
			"type" : "none"
		},
		"NeatLineAttribute" : {
			"title" : "Neat Line Attribute Error",
			"alias" : "NeatLineAttribute",
			"type" : "none"
		},
		"UFIDLength" : {
			"title" : "UFID Length Error",
			"alias" : "UFIDLength",
			"type" : "figure",
			"unit" : "Characters"
		},
		"BuildingSiteDanger" : {
			"title" : "Building Site Error(Dangerous Facility)",
			"alias" : "BuildingSiteDanger",
			"type" : "labelnrelation",
			"multi" : false
		},
		"BuildingSiteRelaxation" : {
			"title" : "Building Site Error(Relaxation Facility)",
			"alias" : "BuildingSiteRelaxation",
			"type" : "labelnrelation",
			"multi" : false
		},
		"OneAcre" : {
			"title" : "Tributary Error",
			"alias" : "OneAcre",
			"type" : "relation"
		},
		"OneStage" : {
			"title" : "Farmland Error",
			"alias" : "OneStage",
			"type" : "relation"
		},
		"Admin" : {
			"title" : "Administrative Boundary Error",
			"alias" : "Admin",
			"type" : "none"
		},
		"CrossRoad" : {
			"title" : "Crossroad Boundary Error",
			"alias" : "CrossRoad",
			"type" : "relation"
		},
		"RefAttributeMiss" : {
			"title" : "Attribute Error Between Map Sheets",
			"alias" : "RefAttributeMiss",
			"type" : "notnull",
			"multi" : true
		},
		"BridgeName" : {
			"title" : "Bridge Name Error",
			"alias" : "BridgeName",
			"type" : "relation"
		},
		"NodeMiss" : {
			"title" : "Node Missing Error",
			"alias" : "NodeMiss",
			"type" : "relation"
		},
		"BSymbolOutSided" : {
			"title" : "Erroneous Position of Building Symbol",
			"alias" : "BSymbolOutSided",
			"type" : "relation"
		},
		"BuildingOpen" : {
			"title" : "Building Boundary Error",
			"alias" : "BuildingOpen",
			"type" : "none"
		},
		"WaterOpen" : {
			"title" : "Water Boundary Error",
			"alias" : "WaterOpen",
			"type" : "none"
		},
		"EntityNone" : {
			"title" : "Entity missing in current map sheet",
			"alias" : "EntityNone",
			"type" : "none"
		},
		"EdgeMatchMiss" : {
			"title" : "Entity missing in adjacent map sheet",
			"alias" : "EdgeMatchMiss",
			"type" : "none"
		},
		"UselessEntity" : {
			"title" : "Unknown use entity",
			"alias" : "UselessEntity",
			"type" : "none"
		},
		"LayerMiss" : {
			"title" : "Layer Error",
			"alias" : "LayerMiss",
			"type" : "geometry"
		},
		"RefLayerMiss" : {
			"title" : "Layer Error Between Map Sheets",
			"alias" : "RefLayerMiss",
			"type" : "none"
		},
		"RefZValueMiss" : {
			"title" : "Altitude Error Between Map Sheets",
			"alias" : "RefZValueMiss",
			"type" : "notnull",
			"multi" : false
		},
		"CharacterAccuracy" : {
			"title" : "Accuracy of Characters",
			"alias" : "CharacterAccuracy",
			"type" : "none"
		},
		"EntityDuplicated" : {
			"title" : "Entity Duplication",
			"alias" : "EntityDuplicated",
			"type" : "none"
		},
		"SelfEntity" : {
			"title" : "Overlapping Entities",
			"alias" : "SelfEntity",
			"type" : "relation"
		},
		"AttributeFix" : {
			"title" : "Fixed Attribute",
			"alias" : "AttributeFix",
			"type" : "attribute",
			"multi" : true
		},
		"OutBoundary" : {
			"title" : "Out-of-bounds Error",
			"alias" : "OutBoundary",
			"type" : "relation"
		},
		"PointDuplicated" : {
			"title" : "Points Duplication",
			"alias" : "PointDuplicated",
			"type" : "none"
		},
		"SmallLength" : {
			"title" : "Unallowable Length of Entities",
			"alias" : "SmallLength",
			"type" : "figure",
			"unit" : "meter"
		},
		"SmallArea" : {
			"title" : "Unallowable Area of Entities",
			"alias" : "SmallArea",
			"type" : "figure",
			"unit" : "squaremeter"
		},
		"ConIntersected" : {
			"title" : "Contour Line Intersections",
			"alias" : "ConIntersected",
			"type" : "none"
		},
		"ConOverDegree" : {
			"title" : "Unsmooth Contour Line Curves",
			"alias" : "ConOverDegree",
			"type" : "figure",
			"unit" : "degree"
		},
		"ConBreak" : {
			"title" : "Contour Line Disconnections",
			"alias" : "ConBreak",
			"type" : "none"
		},
		"ZValueAmbiguous" : {
			"title" : "Altitude Error",
			"alias" : "ZValueAmbiguous",
			"type" : "attribute",
			"multi" : false
		},
		"UselessPoint" : {
			"title" : "Contour Straightening Error",
			"alias" : "UselessPoint",
			"type" : "none"
		},
		"OverShoot" : {
			"title" : "Entity Crossing the Baseline",
			"alias" : "OverShoot",
			"type" : "figure",
			"unit" : "meter"
		},
		"UnderShoot" : {
			"title" : "Entity Not Reaching the Baseline",
			"alias" : "UnderShoot",
			"type" : "figure",
			"unit" : "meter"
		}
	};
	var that = this;
	this.optDef = undefined;
	this.optDefCopy = undefined;
	this.layerDef = undefined;
	this.selectedLayerNow = undefined;
	this.selectedValidationNow = undefined;
	this.selectedDetailNow = undefined;
	this.selectedLayerBefore = undefined;
	this.lAlias = undefined;
	this.vItem = undefined;
	this.dOption = undefined;
	this.codeSelect = undefined;
	this.nnullCodeSelect = undefined;
	this.labelCodeSelect = undefined;
	this.attrForm = undefined;
	this.nnullAttrForm = undefined;
	this.labelAttrForm = undefined;
	this.addBtn = undefined;
	this.addAttrBtn = undefined;
	this.addLabelAttrBtn = undefined;
	this.file = undefined;
	this.emptyLayers = undefined;
	this.radio = undefined;
	this.conAttr = undefined;
	this.conSelect = undefined;
	this.conFigure = undefined;

	this.lAlias = $("<ul>").addClass("gb-list-group").css({
		"margin-bottom" : 0,
		"cursor" : "pointer"
	});
	this.vItem = $("<ul>").addClass("gb-list-group").css({
		"margin-bottom" : 0,
		"cursor" : "pointer"
	});
	this.dOption = $("<ul>").addClass("gb-list-group").css({
		"margin-bottom" : 0,
		"float" : "left"
	});
	this.codeSelect = $("<select>").addClass("gb-form").change(function() {
		var isMulti = that.optItem[that.selectedValidationNow].multi;
		that._updateAttribute($(this).val(), isMulti);
	});
	this.nnullCodeSelect = $("<select>").addClass("gb-form").change(function() {
		var isMulti = that.optItem[that.selectedValidationNow].multi;
		that._updateNotNullAttribute($(this).val(), isMulti);
	});
	this.labelCodeSelect = $("<select>").addClass("gb-form").change(function() {
		var isMulti = that.optItem[that.selectedValidationNow].multi;
		that._updateLabelAttribute($(this).val(), isMulti);
	});
	this.attrForm = $("<tbody>");
	this.nnullAttrForm = $("<tbody>");
	this.labelAttrForm = $("<tbody>");
	this.addBtn = $("<button>").text("Add Attribute").addClass("gb-button").addClass("gb-button-default").click(function() {
		var text = $("<input>").attr({
			"type" : "text"
		}).css({
			"display" : "inline-block"
		});
		$(text).addClass("gb-form");
		$(text).addClass("optiondefinition-attr-text");
		var td1 = $("<td>").append(text);

		var icon = $("<i>").attr("aria-hidden", true);
		$(icon).addClass("fa");
		$(icon).addClass("fa-times");
		var btn = $("<button>").css({
			"display" : "inline-block"
		}).append(icon);
		$(btn).addClass("gb-button");
		$(btn).addClass("gb-button-default");
		$(btn).addClass("optiondefinition-attr-del");
		var td2 = $("<td>").append(btn);

		var text2 = $("<input>").attr({
			"type" : "text"
		});
		$(text2).addClass("gb-form");
		$(text2).addClass("optiondefinition-attr-text2");
		var td3 = $("<td>").attr({
			"colspan" : "2"
		}).css({
			"border-top" : 0
		}).append(text2);

		var btr1 = $("<tr>").append(td1).append(td2);
		var btr2 = $("<tr>").append(td3);

		$(that.attrForm).append(btr1).append(btr2);
	});
	this.addAttrBtn = $("<button>").text("Add Attribute").addClass("gb-button").addClass("gb-button-default").click(function() {
		var text = $("<input>").attr({
			"type" : "text"
		}).css({
			"display" : "inline-block"
		});
		$(text).addClass("gb-form");
		$(text).addClass("optiondefinition-nnullattr-text");
		var td1 = $("<td>").append(text);

		var icon = $("<i>").attr("aria-hidden", true);
		$(icon).addClass("fa");
		$(icon).addClass("fa-times");
		var btn = $("<button>").css({
			"display" : "inline-block"
		}).append(icon);
		$(btn).addClass("gb-button");
		$(btn).addClass("gb-button-default");
		$(btn).addClass("optiondefinition-nnullattr-del");
		var td2 = $("<td>").append(btn);

		var btr1 = $("<tr>").append(td1).append(td2);

		$(that.nnullAttrForm).append(btr1);
	});
	this.addLabelAttrBtn = $("<button>").text("Add Attribute").addClass("gb-button").addClass("gb-button-default").click(function() {
		var text = $("<input>").attr({
			"type" : "text"
		}).css({
			"display" : "inline-block"
		});
		$(text).addClass("gb-form");
		$(text).addClass("optiondefinition-labelattr-text");
		var td1 = $("<td>").append(text);

		var icon = $("<i>").attr("aria-hidden", true);
		$(icon).addClass("fa");
		$(icon).addClass("fa-times");
		var btn = $("<button>").css({
			"display" : "inline-block"
		}).append(icon);
		$(btn).addClass("gb-button");
		$(btn).addClass("gb-button-default");
		$(btn).addClass("optiondefinition-labelattr-del");
		var td2 = $("<td>").append(btn);

		var text2 = $("<input>").attr({
			"type" : "text"
		});
		$(text2).addClass("gb-form");
		$(text2).addClass("optiondefinition-labelattr-text2");
		var td3 = $("<td>").attr({
			"colspan" : "2"
		}).css({
			"border-top" : 0
		}).append(text2);

		var btr1 = $("<tr>").append(td1).append(td2);
		var btr2 = $("<tr>").append(td3);

		$(that.labelAttrForm).append(btr1).append(btr2);
	});
	this.file = $("<input>").attr({
		"type" : "file"
	});

	this.updateLayerDefinition();
	this.setDefinition(this.definition);
	if (this.getDefinition()) {
		this.setOptDefCopy(Object.assign({}, this.getDefinition()));
	}

	var phead1 = $("<div>").text("Layer Alias");
	$(phead1).addClass("gb-article-head");
	this.pbody1 = $("<div>").css({
		"max-height" : "500px",
		"overflow-y" : "auto"
	}).append(this.lAlias);
	// $(document).on("click", this.eventNamespace + " .optiondefinition-alias",
	// function() {
	// // that.optDefCopy =
	// // JSON.parse(JSON.stringify(that.optDef));
	// // that.setOptDefCopy(JSON.parse(JSON.stringify(that.getDefinition())));
	// $(that.dOption).empty();
	// var chldr = $(this).parent().children();
	// for (var i = 0; i < chldr.length; i++) {
	// $(chldr).removeClass("active");
	// }
	// $(this).addClass("active");
	// var text = $(this).find(".optiondefinition-alias-span").text();
	// that.selectedLayerNow = text;
	// var opt = that.getOptDefCopy()[text];
	// var mix = {
	// "obj" : opt,
	// "geom" : that.getLayerDefinition()[text].geom
	// };
	// that._printValidationItem(mix);
	// });
	$(this.pbody1).addClass("gb-article-body");
	var panel1 = $("<div>").css({
		"float" : "left",
		"width" : "279px"
	}).append(phead1).append(this.pbody1);
	$(panel1).addClass("gb-article");

	var phead2 = $("<div>").text("Validation Item");
	$(phead2).addClass("gb-article-head");
	this.pbody2 = $("<div>").css({
		"max-height" : "500px",
		"overflow-y" : "auto"
	}).append(this.vItem);
	// $(document).on("click", this.eventNamespace + " .optiondefinition-item",
	// function() {
	// var chldr = $(this).parent().children();
	// for (var i = 0; i < chldr.length; i++) {
	// $(chldr).removeClass("active");
	// }
	// $(this).addClass("active");
	// var name = $(this).find("input").val();
	// that.selectedValidationNow = name;
	// var opt;
	// if (!!that.getOptDefCopy()[that.selectedLayerNow]) {
	// opt =
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow];
	// }
	// var mix = {
	// "obj" : opt,
	// "vtem" : name
	// };
	// that._printDetailedOption(mix);
	// });
	// $(document).on("change", this.eventNamespace + "
	// .optiondefinition-item-check", function() {
	// if ($(this).prop("checked")) {
	// if (that.optItem[$(this).val()].type === "none") {
	// if (!that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
	// that.getOptDefCopy()[that.selectedLayerNow] = {};
	// }
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow] =
	// true;
	// }
	// } else if (that.optItem[$(this).val()].type === "relation") {
	// if (that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
	// if
	// (that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow))
	// {
	// if
	// (that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow].hasOwnProperty("relation"))
	// {
	// var relation =
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow].relation;
	// that.updateRelation(relation, "down");
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow];
	// }
	// }
	// var keys = Object.keys(that.getOptDefCopy()[that.selectedLayerNow]);
	// if (keys.length === 0) {
	// delete that.getOptDefCopy()[that.selectedLayerNow];
	// }
	// }
	// } else {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow];
	// }
	// });
	//
	// $(document).on("input", this.eventNamespace + "
	// .optiondefinition-figure-text", function() {
	// if ($(this).val() === "") {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow];
	// that._toggleCheckbox(that.selectedValidationNow, false);
	// } else if ($.isNumeric($(this).val().replace(/(\s*)/g, ''))) {
	// if (!that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
	// that.getOptDefCopy()[that.selectedLayerNow] = {};
	// }
	// if
	// (!that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow))
	// {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow] =
	// {
	// "figure" : undefined
	// };
	// }
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["figure"]
	// = $(this).val().replace(/(\s*)/g, '');
	// that._toggleCheckbox(that.selectedValidationNow, true);
	// } else {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow];
	// that._toggleCheckbox(that.selectedValidationNow, false);
	// $(this).val("");
	// }
	// });
	// $(document).on(
	// "change",
	// this.eventNamespace + " input.optiondefinition-label-rel:checkbox",
	// function() {
	// if ($(this).prop("checked")) {
	// if (!that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
	// that.getOptDefCopy()[that.selectedLayerNow] = {};
	// }
	// if
	// (!that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow))
	// {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow] =
	// {};
	// }
	// if
	// (!that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow].hasOwnProperty("relation"))
	// {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["relation"]
	// = [];
	// }
	// if
	// (that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["relation"].indexOf($(this).val())
	// === -1) {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["relation"].push($(this).val());
	// that.updateRelation($(this).val(), "up");
	// console.log(that.emptyLayers);
	// }
	// that._toggleCheckbox(that.selectedValidationNow, true);
	// } else {
	// if
	// (that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["relation"].indexOf($(this).val())
	// !== -1) {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["relation"].splice(
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["relation"].indexOf($(this).val()),
	// 1);
	// that.updateRelation($(this).val(), "down");
	// console.log(that.emptyLayers);
	// }
	// }
	// var checks = $(this).parent().parent().parent().find("input:checked");
	// if (checks.length === 0) {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow];
	// that._toggleCheckbox(that.selectedValidationNow, false);
	// }
	// });
	// $(document)
	// .on(
	// "change",
	// this.eventNamespace + " input.optiondefinition-label-rel:radio",
	// function() {
	// console.log("radio");
	// if ($(this).prop("checked")) {
	// if (!that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
	// that.getOptDefCopy()[that.selectedLayerNow] = {};
	// }
	// if
	// (!that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow))
	// {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow] =
	// {};
	// }
	// if
	// (!that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow].hasOwnProperty("relation"))
	// {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["relation"]
	// = [];
	// }
	// if (that.radio !== undefined) {
	// if
	// (that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["relation"].indexOf(that.radio)
	// !== -1) {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["relation"].splice(that
	// .getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["relation"]
	// .indexOf(that.radio), 1);
	// }
	// }
	//
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["relation"].push($(this).val());
	// that.updateRelation($(this).val(), "upradio");
	// that.radio = $(this).val();
	// console.log(that.emptyLayers);
	// that._toggleCheckbox(that.selectedValidationNow, true);
	// }
	// var checks = $(this).parent().parent().parent().find("input:checked");
	// if (checks.length === 0) {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow];
	// that._toggleCheckbox(that.selectedValidationNow, false);
	// }
	// });
	// $(document).on(
	// "change",
	// this.eventNamespace + " .optiondefinition-rel-check",
	// function() {
	// if ($(this).prop("checked")) {
	// if (!that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
	// that.getOptDefCopy()[that.selectedLayerNow] = {};
	// }
	// if
	// (!that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow))
	// {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow] =
	// {
	// "relation" : []
	// };
	// }
	// if
	// (that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["relation"].indexOf($(this).val())
	// === -1) {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["relation"].push($(this).val());
	// that.updateRelation($(this).val(), "up");
	// console.log(that.emptyLayers);
	// }
	// that._toggleCheckbox(that.selectedValidationNow, true);
	// } else {
	// if
	// (that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["relation"].indexOf($(this).val())
	// !== -1) {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["relation"].splice(
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["relation"].indexOf($(this).val()),
	// 1);
	// that.updateRelation($(this).val(), "down");
	// console.log(that.emptyLayers);
	// }
	// }
	// var checks = $(this).parent().parent().parent().find("input:checked");
	// if (checks.length === 0) {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow];
	// that._toggleCheckbox(that.selectedValidationNow, false);
	// }
	// });
	// $(document).on(
	// "change",
	// this.eventNamespace + " .optiondefinition-geom-check",
	// function() {
	// if ($(this).prop("checked")) {
	// if (!that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
	// that.getOptDefCopy()[that.selectedLayerNow] = {};
	// }
	// if
	// (!that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow))
	// {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow] =
	// [];
	// }
	// if
	// (that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow].indexOf($(this).val())
	// === -1) {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow].push($(this).val());
	// }
	// that._toggleCheckbox(that.selectedValidationNow, true);
	// } else {
	// if
	// (that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow].indexOf($(this).val())
	// !== -1) {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow].splice(
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow].indexOf($(this).val()),
	// 1);
	// }
	// }
	// var checks = $(this).parent().parent().parent().find("input:checked");
	// if (checks.length === 0) {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow];
	// that._toggleCheckbox(that.selectedValidationNow, false);
	// }
	// // console.log(that.getOptDefCopy()[that.selectedLayerNow]);
	// });
	// $(document).on("change", this.eventNamespace + "
	// .optiondefinition-attr-select", function() {
	// var isMulti = that.optItem[that.selectedValidationNow].multi;
	// that._updateAttribute($(this).val(), isMulti);
	// });
	// $(document).on("change", this.eventNamespace + "
	// .optiondefinition-nnullattr-select", function() {
	// var isMulti = that.optItem[that.selectedValidationNow].multi;
	// that._updateNotNullAttribute($(this).val(), isMulti);
	// });
	// $(document).on("change", this.eventNamespace + "
	// .optiondefinition-labelattr-select", function() {
	// var isMulti = that.optItem[that.selectedValidationNow].multi;
	// that._updateLabelAttribute($(this).val(), isMulti);
	// });
	// $(document).on("click", this.eventNamespace + "
	// .optiondefinition-attr-del", function() {
	// var row1 = $(this).parent().parent();
	// var row2 = $(this).parent().parent().next();
	// var keyname = $(row1).find("input[type=text]").val();
	// var selected = $(that.codeSelect).val();
	// if (keyname !== "") {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][selected][keyname];
	// var keys =
	// Object.keys(that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]);
	// var count = 0;
	// for (var i = 0; i < keys.length; i++) {
	// var length =
	// Object.keys(that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][keys[i]]).length;
	// if (length === 0) {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][keys[i]]
	// }
	// count = count + length;
	// }
	// if (!count) {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow];
	// }
	// that._toggleCheckbox(that.selectedValidationNow, !!count);
	// }
	//
	// $(row2).remove();
	// $(row1).remove();
	// });
	// $(document).on("click", this.eventNamespace + "
	// .optiondefinition-nnullattr-del", function() {
	// var row1 = $(this).parent().parent();
	// var keyname = $(row1).find("input[type=text]").val().replace(/(\s*)/g,
	// '');
	// var selected = $(that.nnullCodeSelect).val().replace(/(\s*)/g, '');
	// if
	// (that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow))
	// {
	// if
	// (that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow].hasOwnProperty(selected))
	// {
	// var optArr =
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][selected];
	// var idx;
	// if (Array.isArray(optArr)) {
	// idx =
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][selected].indexOf(keyname);
	// var deletedArr =
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][selected];
	// deletedArr.splice(idx, 1);
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][selected]
	// = deletedArr;
	// }
	// var keys =
	// Object.keys(that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]);
	// var count = 0;
	// for (var i = 0; i < keys.length; i++) {
	// var length =
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][keys[i]].length;
	// if (length === 0) {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][keys[i]]
	// }
	// count = count + length;
	// }
	// if (!count) {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow];
	// }
	// that._toggleCheckbox(that.selectedValidationNow, !!count);
	// } else {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow];
	// that._toggleCheckbox(that.selectedValidationNow, false);
	// }
	// }
	//
	// $(row1).remove();
	// });
	// $(document).on("click", this.eventNamespace + "
	// .optiondefinition-labelattr-del", function() {
	// var row1 = $(this).parent().parent();
	// var row2 = $(this).parent().parent().next();
	// var keyname = $(row1).find("input[type=text]").val();
	// var selected = $(that.labelCodeSelect).val();
	// if (keyname !== "") {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["label"][selected][keyname];
	// var keys =
	// Object.keys(that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["label"]);
	// var count = 0;
	// for (var i = 0; i < keys.length; i++) {
	// var length =
	// Object.keys(that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["label"][keys[i]]).length;
	// if (length === 0) {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["label"][keys[i]]
	// }
	// count = count + length;
	// }
	// if (!count) {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow];
	// }
	// that._toggleCheckbox(that.selectedValidationNow, !!count);
	// }
	//
	// $(row2).remove();
	// $(row1).remove();
	// });
	// $(document).on("click", this.eventNamespace + "
	// .optiondefinition-nnullattr-addrow", function() {
	// var text = $("<input>").attr({
	// "type" : "text"
	// }).css({
	// "display" : "inline-block"
	// });
	// that._addClass(text, "gb-form");
	// that._addClass(text, "optiondefinition-nnullattr-text");
	// var td1 = $("<td>").append(text);
	//
	// var icon = $("<i>").attr("aria-hidden", true);
	// that._addClass(icon, "fa");
	// that._addClass(icon, "fa-times");
	// var btn = $("<button>").css({
	// "display" : "inline-block"
	// }).append(icon);
	// that._addClass(btn, "gb-button");
	// that._addClass(btn, "gb-button-default");
	// that._addClass(btn, "optiondefinition-nnullattr-del");
	// var td2 = $("<td>").append(btn);
	//
	// var btr1 = $("<tr>").append(td1).append(td2);
	//
	// $(that.nnullAttrForm).append(btr1);
	// });
	// $(document).on("click", this.eventNamespace + "
	// .optiondefinition-attr-addrow", function() {
	// var text = $("<input>").attr({
	// "type" : "text"
	// }).css({
	// "display" : "inline-block"
	// });
	// that._addClass(text, "gb-form");
	// that._addClass(text, "optiondefinition-attr-text");
	// var td1 = $("<td>").append(text);
	//
	// var icon = $("<i>").attr("aria-hidden", true);
	// that._addClass(icon, "fa");
	// that._addClass(icon, "fa-times");
	// var btn = $("<button>").css({
	// "display" : "inline-block"
	// }).append(icon);
	// that._addClass(btn, "gb-button");
	// that._addClass(btn, "gb-button-default");
	// that._addClass(btn, "optiondefinition-attr-del");
	// var td2 = $("<td>").append(btn);
	//
	// var text2 = $("<input>").attr({
	// "type" : "text"
	// });
	// that._addClass(text2, "gb-form");
	// that._addClass(text2, "optiondefinition-attr-text2");
	// var td3 = $("<td>").attr({
	// "colspan" : "2"
	// }).css({
	// "border-top" : 0
	// }).append(text2);
	//
	// var btr1 = $("<tr>").append(td1).append(td2);
	// var btr2 = $("<tr>").append(td3);
	//
	// $(that.attrForm).append(btr1).append(btr2);
	// });
	// $(document).on("click", this.eventNamespace + "
	// .optiondefinition-labelattr-addrow", function() {
	// var text = $("<input>").attr({
	// "type" : "text"
	// }).css({
	// "display" : "inline-block"
	// });
	// that._addClass(text, "gb-form");
	// that._addClass(text, "optiondefinition-labelattr-text");
	// var td1 = $("<td>").append(text);
	//
	// var icon = $("<i>").attr("aria-hidden", true);
	// that._addClass(icon, "fa");
	// that._addClass(icon, "fa-times");
	// var btn = $("<button>").css({
	// "display" : "inline-block"
	// }).append(icon);
	// that._addClass(btn, "gb-button");
	// that._addClass(btn, "gb-button-default");
	// that._addClass(btn, "optiondefinition-labelattr-del");
	// var td2 = $("<td>").append(btn);
	//
	// var text2 = $("<input>").attr({
	// "type" : "text"
	// });
	// that._addClass(text2, "gb-form");
	// that._addClass(text2, "optiondefinition-labelattr-text2");
	// var td3 = $("<td>").attr({
	// "colspan" : "2"
	// }).css({
	// "border-top" : 0
	// }).append(text2);
	//
	// var btr1 = $("<tr>").append(td1).append(td2);
	// var btr2 = $("<tr>").append(td3);
	//
	// $(that.labelAttrForm).append(btr1).append(btr2);
	// });
	// $(document).on(
	// "input",
	// this.eventNamespace + " .optiondefinition-attr-text,
	// .optiondefinition-attr-text2",
	// function() {
	// var attrs = $(that.attrForm).find("input.optiondefinition-attr-text");
	// var obj = {};
	// for (var i = 0; i < attrs.length; i++) {
	// var key = $(attrs[i]).val();
	// var values =
	// $(attrs[i]).parent().parent().next().find("input[type=text].optiondefinition-attr-text2").val().replace(
	// /(\s*)/g, '').split(",");
	// obj[key] = values;
	// }
	// var selected = $(that.codeSelect).val();
	// if (!that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
	// that.getOptDefCopy()[that.selectedLayerNow] = {};
	// }
	// if
	// (!that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow))
	// {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow] =
	// {};
	// }
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][selected]
	// = obj;
	// var keys =
	// Object.keys(that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]);
	// var count = 0;
	// for (var i = 0; i < keys.length; i++) {
	// var length =
	// Object.keys(that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][keys[i]]).length;
	// if (length === 0) {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][keys[i]]
	// }
	// count = count + length;
	// }
	// that._toggleCheckbox(that.selectedValidationNow, !!count);
	// // var flag = false;
	// // var keys =
	// //
	// Object.keys(that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow]);
	// // if (keys.length > 0) {
	// // for (var i = 0; i < keys.length;
	// // i++) {
	// // var arr =
	// //
	// that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow][keys[i]];
	// // if (arr.length > 0) {
	// // flag = true;
	// // }
	// // }
	// // }
	// // that._toggleCheckbox(that.selectedValidationNow,
	// // flag);
	// });
	// $(document).on("input", this.eventNamespace + "
	// .optiondefinition-nnullattr-text", function() {
	// // that._updateNotNullForm();
	// var attrs =
	// $(that.nnullAttrForm).find("input.optiondefinition-nnullattr-text");
	// var obj = [];
	// var selected = $(that.nnullCodeSelect).val();
	// var curOpt;
	// if (!that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
	// that.getOptDefCopy()[that.selectedLayerNow] = {};
	// }
	// if
	// (!that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow))
	// {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow] =
	// {};
	// }
	// if
	// (!that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow].hasOwnProperty(selected))
	// {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][selected]
	// = [];
	// }
	// // curOpt =
	// //
	// that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow][selected];
	// curOpt = [];
	// for (var i = 0; i < attrs.length; i++) {
	// if (curOpt.indexOf($(attrs[i]).val()) === -1 && $(attrs[i]).val() !== "")
	// {
	// curOpt.push($(attrs[i]).val());
	// }
	// }
	//
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][selected]
	// = curOpt;
	// var keys =
	// Object.keys(that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]);
	// var count = 0;
	// for (var i = 0; i < keys.length; i++) {
	// var length =
	// Object.keys(that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][keys[i]]).length;
	// if (length === 0) {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][keys[i]]
	// }
	// count = count + length;
	// }
	// if (!count) {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow];
	// }
	// that._toggleCheckbox(that.selectedValidationNow, !!count);
	// // console.log(that.getOptDefCopy());
	// });
	// $(document)
	// .on(
	// "input",
	// this.eventNamespace + " .optiondefinition-labelattr-text,
	// .optiondefinition-labelattr-text2",
	// function() {
	// var attrs =
	// $(that.labelAttrForm).find("input.optiondefinition-labelattr-text");
	// var obj = {};
	// for (var i = 0; i < attrs.length; i++) {
	// var key = $(attrs[i]).val();
	// var values =
	// $(attrs[i]).parent().parent().next().find("input[type=text].optiondefinition-labelattr-text2")
	// .val().replace(/(\s*)/g, '').split(",");
	// obj[key] = values;
	// }
	// var selected = $(that.labelCodeSelect).val();
	// if (!that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
	// that.getOptDefCopy()[that.selectedLayerNow] = {};
	// }
	// if
	// (!that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow))
	// {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow] =
	// {};
	// }
	// if
	// (!that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow].hasOwnProperty("label"))
	// {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["label"]
	// = {};
	// }
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["label"][selected]
	// = obj;
	// var keys =
	// Object.keys(that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["label"]);
	// var count = 0;
	// for (var i = 0; i < keys.length; i++) {
	// var length = Object
	// .keys(that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["label"][keys[i]]).length;
	// if (length === 0) {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["label"][keys[i]];
	// }
	// count = count + length;
	// }
	// that._toggleCheckbox(that.selectedValidationNow, !!count);
	// // var flag = false;
	// // var keys =
	// //
	// Object.keys(that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow]);
	// // if (keys.length > 0) {
	// // for (var i = 0; i < keys.length;
	// // i++) {
	// // var arr =
	// //
	// that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow][keys[i]];
	// // if (arr.length > 0) {
	// // flag = true;
	// // }
	// // }
	// // }
	// // that._toggleCheckbox(that.selectedValidationNow,
	// // flag);
	// });
	// $(document).on("input", this.eventNamespace + "
	// .optiondefinition-conditionalfigure-text", function() {
	// console.log("attr");
	// if ($(that.conAttr).val() !== "" && $(that.conFigure).val() !== "") {
	// if (!that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
	// that.getOptDefCopy()[that.selectedLayerNow] = {};
	// }
	// if
	// (!that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow))
	// {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow] =
	// {};
	// }
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["attribute"]
	// = $(that.conAttr).val();
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["condition"]
	// = $(that.conSelect).val();
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["figure"]
	// = $(that.conFigure).val();
	// } else {
	// if (that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
	// if
	// (that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow))
	// {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow];
	// }
	// }
	// }
	// if (that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
	// if
	// (that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow))
	// {
	// var keys =
	// Object.keys(that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]);
	// var count = 0;
	// for (var i = 0; i < keys.length; i++) {
	// var length =
	// Object.keys(that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][keys[i]]).length;
	// if (length === 0) {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][keys[i]]
	// }
	// count = count + length;
	// }
	// if (!count) {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow];
	// }
	// that._toggleCheckbox(that.selectedValidationNow, !!count);
	// }
	// }
	//
	// });
	// $(document).on("input", this.eventNamespace + "
	// .optiondefinition-conditionalfigure-select", function() {
	// console.log("select");
	// if ($(that.conAttr).val() !== "" && $(that.conFigure).val() !== "") {
	// if (!that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
	// that.getOptDefCopy()[that.selectedLayerNow] = {};
	// }
	// if
	// (!that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow))
	// {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow] =
	// {};
	// }
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["attribute"]
	// = $(that.conAttr).val();
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["condition"]
	// = $(that.conSelect).val();
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["figure"]
	// = $(that.conFigure).val();
	// } else {
	// if (that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
	// if
	// (that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow))
	// {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow];
	// }
	// }
	// }
	// if (that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
	// if
	// (that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow))
	// {
	// var keys =
	// Object.keys(that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]);
	// var count = 0;
	// for (var i = 0; i < keys.length; i++) {
	// var length =
	// Object.keys(that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][keys[i]]).length;
	// if (length === 0) {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][keys[i]]
	// }
	// count = count + length;
	// }
	// if (!count) {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow];
	// }
	// that._toggleCheckbox(that.selectedValidationNow, !!count);
	// }
	// }
	// });
	// $(document).on("input", this.eventNamespace + "
	// .optiondefinition-conditionalfigure-figure", function() {
	// console.log("figure");
	// if ($(that.conAttr).val() !== "" && $(that.conFigure).val() !== "") {
	// if (!that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
	// that.getOptDefCopy()[that.selectedLayerNow] = {};
	// }
	// if
	// (!that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow))
	// {
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow] =
	// {};
	// }
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["attribute"]
	// = $(that.conAttr).val();
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["condition"]
	// = $(that.conSelect).val();
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["figure"]
	// = $(that.conFigure).val();
	// } else {
	// if (that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
	// if
	// (that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow))
	// {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow];
	// that._toggleCheckbox(that.selectedValidationNow, false);
	// }
	// }
	// }
	// if (that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
	// if
	// (that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow))
	// {
	// var keys =
	// Object.keys(that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]);
	// var count = 0;
	// for (var i = 0; i < keys.length; i++) {
	// var length =
	// Object.keys(that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][keys[i]]).length;
	// if (length === 0) {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][keys[i]]
	// }
	// count = count + length;
	// }
	// if (!count) {
	// delete
	// that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow];
	// }
	// that._toggleCheckbox(that.selectedValidationNow, !!count);
	// }
	// }
	// });

	$(this.pbody2).addClass("gb-article-body");
	var panel2 = $("<div>").css({
		"float" : "left",
		"width" : "279px"
	}).append(phead2).append(this.pbody2);
	$(panel2).addClass("gb-article");

	var phead3 = $("<div>").text("Detailed Option");
	$(phead3).addClass("gb-article-head");
	this.pbody3 = $("<div>").css({
		"max-height" : "500px",
		"overflow-y" : "auto"
	}).append(this.dOption);
	$(this.pbody3).addClass("gb-article-body");
	var panel3 = $("<div>").css({
		"float" : "left",
		"width" : "279px"
	}).append(phead3).append(this.pbody3);
	$(panel3).addClass("gb-article");

	var left = $("<div>").append(panel1);
	// $(left).addClass("col-md-4");

	var mid = $("<div>").append(panel2);
	// $(mid).addClass("col-md-4");

	var right = $("<div>").append(panel3);
	// $(right).addClass("col-md-4");

	var upper = $("<div>").append(left).append(mid).append(right);
	// $(upper).addClass("row");
	this.file = $("<input>").attr({
		"type" : "file"
	}).css({
		"float" : "left",
		"display" : "inline-block"
	});
	var lower = $("<div>").css({
		"display" : "none",
		"height" : "30px",
		"margin" : "5px 0"
	}).append(this.file);

	$(this.file).on("change", function(event) {
		var fileList = that.file[0].files;
		var reader = new FileReader();
		if (fileList.length === 0) {
			return;
		}
		reader.readAsText(fileList[0]);
		$(reader).on("load", function(event) {
			var obj = JSON.parse(reader.result.replace(/(\s*)/g, ''));
			that.setOptDefCopy(obj);
			that.update();
			that.resetRelation();
			$(lower).css("display", "none");
		});
	});

	var body = $("<div>").append(upper).append(lower);
	// $(body).addClass("modal-body");
	$(this.getModalBody()).css({
		"height" : "607px"
	});
	$(this.getModalBody()).append(body);
	/*
	 * 
	 * 
	 * body end
	 * 
	 * 
	 */
	var uploadBtn = $("<button>").attr({
		"type" : "button"
	});
	$(uploadBtn).addClass("gb-button");
	$(uploadBtn).addClass("gb-button-default");
	$(uploadBtn).text("Upload");
	$(uploadBtn).click(function(event) {
		if (event.target === uploadBtn[0]) {
			if ($(lower).css("display") === "none") {
				$(lower).css("display", "block");
			} else {
				$(lower).css("display", "none");
			}
		}
	});

	var downloadBtn = $("<button>").attr({
		"type" : "button"
	});
	$(downloadBtn).addClass("gb-button");
	$(downloadBtn).addClass("gb-button-default");
	$(downloadBtn).text("Download");
	$(downloadBtn).click(function(event) {
		if (event.target === downloadBtn[0]) {
			that.downloadSetting();
		}
	});

	var pleft = $("<span>").css("float", "left");
	// this._addClass(pleft, "text-left");
	$(pleft).append(uploadBtn).append(downloadBtn);

	this.closeBtn = $("<button>").addClass("gb-button").addClass("gb-button-default").text("Close").click(this, function(evt) {
		evt.data.close();
	});
	this.okBtn = $("<button>").addClass("gb-button").addClass("gb-button-primary").text("OK").click(this, function(evt) {
		// evt.data.requestGeneralization();
		// this.beforeSaveRelation();
		var cobj = that.getOptDefCopy();
		that.setDefinition(cobj);
		console.log(cobj);
		that.setOptDefCopy(undefined);
		that.close();
	});

	this.buttonArea = $("<span>").addClass("gb-modal-buttons").append(this.closeBtn).append(this.okBtn);
	this.modalFooter = $("<div>").addClass("gb-modal-footer").append(this.buttonArea);
	$(this.getModal()).append(this.modalFooter);
	/*
	 * 
	 * 
	 * footer end
	 * 
	 * 
	 */
	// var content = $("<div>").append(header).append(body).append(footer);
	// $(content).addClass("modal-content");
	//
	// var dialog = $("<div>").html(content);
	// $(dialog).addClass("modal-dialog");
	// $(dialog).addClass("modal-lg");
	//
	// this.window = $("<div>").hide().attr({
	// // Setting tabIndex makes the div focusable
	// tabIndex : -1,
	// role : "dialog"
	// }).html(dialog);
};
gb.modal.ValidationDefinition.prototype = Object.create(gb.modal.Base.prototype);
gb.modal.ValidationDefinition.prototype.constructor = gb.modal.ValidationDefinition;

gb.modal.ValidationDefinition.prototype.setDefinition = function(obj) {
	console.log(obj);
	this.optDef = $.extend({}, obj);
};
gb.modal.ValidationDefinition.prototype.getDefinition = function() {
	return this.optDef;
};
gb.modal.ValidationDefinition.prototype.setLayerDefinition = function(obj) {
	this.layerDef = $.extend({}, obj);
};
gb.modal.ValidationDefinition.prototype.getLayerDefinition = function() {
	return this.layerDef;
};
gb.modal.ValidationDefinition.prototype.setOptDefCopy = function(def) {
	this.optDefCopy = $.extend({}, def);
};
gb.modal.ValidationDefinition.prototype.getOptDefCopy = function() {
	return this.optDefCopy;
};
gb.modal.ValidationDefinition.prototype.updateLayerDefinition = function() {
	if (typeof this.layerDefinition === "function") {
		this.layerDef = $.extend({}, this.layerDefinition());
		this.setLayerDefinition(this.layerDefinition());
	} else {
		this.layerDef = $.extend({}, this.layerDefinition);
		this.setLayerDefinition(this.layerDefinition);
	}
};
gb.modal.ValidationDefinition.prototype.beforeSaveRelation = function() {
	var cobj = this.getOptDefCopy();
	var ekeys = Object.keys(this.emptyLayers);
	for (var i = 0; i < ekeys.length; i++) {
		if (this.emptyLayers[ekeys[i]] < 1 && cobj.hasOwnProperty(ekeys[i])) {
			var keys = Object.keys(cobj[ekeys[i]]);
			if (keys.length === 0) {
				delete cobj[ekeys[i]];
			}
		} else if (this.emptyLayers[ekeys[i]] > 0 && !cobj.hasOwnProperty(ekeys[i])) {
			cobj[ekeys[i]] = {};
		}
	}
	console.log("beforeSaveRelation: ");
	console.log(this.emptyLayers);
};
gb.modal.ValidationDefinition.prototype.getRelation = function() {
	return this.emptyLayers;
};
gb.modal.ValidationDefinition.prototype.resetRelation = function(obj) {
	// if (!this.getRelation()) {
	this.emptyLayers = {};
	// }
	var ldefinition = this.getLayerDefinition();
	var ldefKeys = Object.keys(ldefinition);
	for (var i = 0; i < ldefKeys.length; i++) {
		this.emptyLayers[ldefKeys[i]] = 0;
	}

	var def = this.getOptDefCopy();
	var dkeys = Object.keys(def);
	for (var i = 0; i < dkeys.length; i++) {
		var vkeys = Object.keys(def[dkeys[i]]);
		for (var j = 0; j < vkeys.length; j++) {
			console.log(vkeys[j]);
			if (this.optItem[vkeys[j]].type === "relation" || this.optItem[vkeys[j]].type === "labelnrelation") {
				var relation = def[dkeys[i]][vkeys[j]].relation;
				for (var k = 0; k < relation.length; k++) {
					this.updateRelation(relation[k], "up");
					if (!def.hasOwnProperty(relation[k])) {
						this.getOptDefCopy()[relation[k]] = {};
						console.log(this.getOptDefCopy());
					}
				}
			}
		}
	}
	console.log("resetRelation: ");
	console.log(this.emptyLayers);
};

gb.modal.ValidationDefinition.prototype.updateRelation = function(alias, und) {
	if (typeof alias === "string") {
		if (und === "up") {
			this.emptyLayers[alias]++;
		} else if (und === "down") {
			this.emptyLayers[alias]--;
		} else if (und === "upradio") {
			if (this.radio !== undefined) {
				this.emptyLayers[this.radio]--;
			}
			this.emptyLayers[alias]++;
		} else if (und === "downradio") {
			if (this.radio !== undefined) {
				this.emptyLayers[this.radio]++;
			}
			this.emptyLayers[alias]--;
		}
	} else if (Array.isArray(alias)) {
		for (var i = 0; i < alias.length; i++) {
			if (und === "up") {
				this.emptyLayers[alias[i]]++;
			} else if (und === "down") {
				this.emptyLayers[alias[i]]--;
			}
		}
	}
	console.log("updateRelation: ");
	console.log(this.emptyLayers);
};
gb.modal.ValidationDefinition.prototype.downloadSetting = function() {
	var setting = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(this.getOptDefCopy()));
	var anchor = $("<a>").attr({
		"href" : setting,
		"download" : "validation_setting.json"
	});
	$(anchor)[0].click();
};
gb.modal.ValidationDefinition.prototype.update = function(obj) {
	var that = this;
	if (!obj) {
		obj = this.layerDef;
	}
	this.updateLayerDefinition();
	obj = this.getLayerDefinition();
	$(this.pbody1).empty();
	$(this.pbody1).append(this.lAlias)
	$(this.lAlias).empty();
	var keys = Object.keys(obj);
	for (var i = 0; i < keys.length; i++) {
		var alias = $("<span>").text(keys[i]);
		$(alias).addClass("optiondefinition-alias-span");
		var span = $("<span>").text(obj[keys[i]].geom).css({
			"font-weight" : "100"
		});
		$(span).addClass("badge");
		var anchor = $("<li>").attr({
			"title" : obj[keys[i]].code
		}).append(alias).append(span);
		$(anchor).addClass("list-group-item");
		$(anchor).addClass("optiondefinition-alias").click(function() {
			// that.optDefCopy =
			// JSON.parse(JSON.stringify(that.optDef));
			// that.setOptDefCopy(JSON.parse(JSON.stringify(that.getDefinition())));
			$(that.dOption).empty();
			var chldr = $(this).parent().children();
			for (var i = 0; i < chldr.length; i++) {
				$(chldr).removeClass("active");
			}
			$(this).addClass("active");
			var text = $(this).find(".optiondefinition-alias-span").text();
			that.selectedLayerNow = text;
			var opt = that.getOptDefCopy()[text];
			var mix = {
				"obj" : opt,
				"geom" : that.getLayerDefinition()[text].geom
			};
			that._printValidationItem(mix);
		});
		if (obj[keys[i]].area) {
			$(anchor).addClass("list-group-item-info");
		}
		$(that.lAlias).append(anchor);
	}
	$(this.vItem).empty();
	$(this.dOption).empty();
};
gb.modal.ValidationDefinition.prototype._printValidationItem = function(mix) {
	var obj = mix.obj;
	var geom = mix.geom;
	// vItem
	var that = this;
	$(this.pbody2).empty();
	$(this.pbody2).append(that.vItem);
	$(this.vItem).empty();
	var lower = geom.toLowerCase();
	var list;
	switch (lower) {
	case "point":
		list = that.itemList.point;
		break;
	case "multipoint":
		list = that.itemList.multipoint;
		break;
	case "linestring":
		list = that.itemList.linestring;
		break;
	case "multilinestring":
		list = that.itemList.multilinestring;
		break;
	case "polygon":
		list = that.itemList.polygon;
		break;
	case "multipolygon":
		list = that.itemList.multipolygon;
		break;
	case "line":
		list = that.itemList.line;
		break;
	case "polyline":
		list = that.itemList.polyline;
		break;
	case "lwpolyline":
		list = that.itemList.lwpolyline;
		break;
	case "text":
		list = that.itemList.text;
		break;
	case "insert":
		list = that.itemList.insert;
		break;
	default:
		break;
	}
	for (var i = 0; i < list.length; i++) {
		var checkbox = $("<input>").attr({
			"type" : "checkbox",
			"value" : that.optItem[list[i]].alias
		});
		$(checkbox).addClass("optiondefinition-item-check");
		if (!!obj) {
			var keys = Object.keys(obj);
			if (keys.indexOf(list[i]) !== -1) {
				$(checkbox).prop("checked", true);
			}
		}
		var title = $("<span>").css({
			"vertical-align" : "text-bottom",
			"margin-left" : "5px"
		}).text(that.optItem[list[i]].title);
		var li = $("<li>").append(checkbox).append(title);

		$(li).addClass("list-group-item");
		$(li).addClass("optiondefinition-item");
		$(that.vItem).append(li);
	}

};
gb.modal.ValidationDefinition.prototype._printDetailedOption = function(mix) {
	var optObj = mix.obj;
	var vtem = mix.vtem;
	var that = this;
	$(this.pbody3).empty();
	$(this.pbody3).append(this.dOption);
	$(this.dOption).empty();
	var obj = this.optItem[vtem];
	switch (obj.type) {
	case "none":
		var li = $("<li>").text("No detailed option");
		$(li).addClass("list-group-item");
		// that._addClass(li, "list-group-item-info");
		$(that.dOption).append(li);
		break;
	case "figure":
		var input = $("<input>").attr({
			"type" : "text"
		});
		$(input).addClass("optiondefinition-figure-text");
		$(input).addClass("gb-form");
		if (!!optObj) {
			var keys = Object.keys(optObj);
			if (keys.indexOf("figure") !== -1) {
				$(input).val(optObj["figure"]);
			}
		}
		var addon = $("<div>");
		$(addon).addClass("input-group-addon");
		switch (obj.unit) {
		case "meter":
			$(addon).text("m");
			break;
		case "squaremeter":
			var sup = $("<sup>").text("2");
			$(addon).append("m").append(sup);
			break;
		case "degree":
			var sup = $("<sup>").html("&deg;");
			$(addon).append(sup);
			break;
		default:
			$(addon).text(obj.unit);
			break;
		}
		var group = $("<div>").append(input).append(addon);
		$(group).addClass("input-group");
		$(that.dOption).append(group);
		break;
	case "relation":
		var keys = Object.keys(that.getLayerDefinition());
		for (var i = 0; i < keys.length; i++) {
			var checkbox = $("<input>").attr({
				"type" : "checkbox",
				"value" : keys[i]
			}).css({
				"vertical-align" : "top",
				"margin-right" : "3px"
			});
			$(checkbox).addClass("optiondefinition-rel-check");
			if (that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
				if (that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(vtem)) {
					if (that.getOptDefCopy()[that.selectedLayerNow][vtem].relation.indexOf(keys[i]) !== -1) {
						$(checkbox).prop("checked", true);
					}
				}
			}

			var label = $("<label>").append(checkbox).append(keys[i]);
			var li = $("<li>").css({
				"margin-top" : 0
			}).append(label);
			$(li).addClass("list-group-item");
			$(that.dOption).append(li);
		}
		break;
	case "attribute":
		$(that.codeSelect).empty();
		var codes = that.getLayerDefinition()[that.selectedLayerNow].code;
		var geom = that.getLayerDefinition()[that.selectedLayerNow].geom.toUpperCase();
		var sCode;
		for (var i = 0; i < codes.length; i++) {
			var opt = $("<option>").text(codes[i] + "_" + geom);
			$(that.codeSelect).append(opt);
			if (i === 0) {
				$(opt).prop("selected", true);
				sCode = codes[i] + "_" + geom;
			}
		}
		that._updateAttribute(sCode, obj.multi);

		var tb = $("<table>").append(that.attrForm);
		$(tb).addClass("table");
		$(tb).addClass("text-center");
		if (obj.multi) {
			$(that.dOption).append(that.codeSelect).append(tb).append(that.addBtn);
		} else {
			$(that.dOption).append(that.codeSelect).append(tb);
		}

		break;
	case "notnull":
		$(that.nnullCodeSelect).empty();
		var codes = that.getLayerDefinition()[that.selectedLayerNow].code;
		var geom = that.getLayerDefinition()[that.selectedLayerNow].geom.toUpperCase();
		var sCode;
		for (var i = 0; i < codes.length; i++) {
			var opt = $("<option>").text(codes[i] + "_" + geom);
			$(that.nnullCodeSelect).append(opt);
			if (i === 0) {
				$(opt).prop("selected", true);
				sCode = codes[i] + "_" + geom;
			}
		}
		that._updateNotNullAttribute(sCode, obj.multi);

		var tb = $("<table>").append(that.nnullAttrForm);
		$(tb).addClass("table");
		$(tb).addClass("text-center");

		if (obj.multi) {
			$(that.dOption).append(that.nnullCodeSelect).append(tb).append(that.addAttrBtn);
		} else {
			$(that.dOption).append(that.nnullCodeSelect).append(tb);
		}
		break;
	case "geometry":
		var keys = Object.keys(that.itemList);
		for (var i = 0; i < keys.length; i++) {
			var enType;
			switch (keys[i]) {
			case "point":
				enType = "Point";
				break;
			case "multipoint":
				enType = "MultiPoint";
				break;
			case "linestring":
				enType = "LineString";
				break;
			case "multilinestring":
				enType = "MultiLineString";
				break;
			case "polygon":
				enType = "Polygon";
				break;
			case "multipolygon":
				enType = "MultiPolygon";
				break;
			case "line":
				enType = "Line";
				break;
			case "polyline":
				enType = "Polyline";
				break;
			case "lwpolyline":
				enType = "LWPolyline";
				break;
			case "text":
				enType = "Text";
				break;
			case "insert":
				enType = "Insert";
				break;
			default:
				enType = "Unknown";
				break;
			}

			var checkbox = $("<input>").attr({
				"type" : "checkbox",
				"value" : enType
			}).css({
				"vertical-align" : "top",
				"margin-right" : "3px"
			});
			$(checkbox).addClass("optiondefinition-geom-check");
			if (that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
				if (that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(vtem)) {
					if (that.getOptDefCopy()[that.selectedLayerNow][vtem].indexOf(enType) !== -1) {
						$(checkbox).prop("checked", true);
					}
				}
			}

			var label = $("<label>").append(checkbox).append(enType);
			var li = $("<li>").css({
				"margin-top" : 0
			}).append(label);
			$(li).addClass("list-group-item");
			$(that.dOption).append(li);
		}
		break;
	case "labelnrelation":
		this.radio = undefined;
		$(that.labelCodeSelect).empty();
		var codes = that.getLayerDefinition()[that.selectedLayerNow].code;
		var geom = that.getLayerDefinition()[that.selectedLayerNow].geom.toUpperCase();
		var sCode;
		for (var i = 0; i < codes.length; i++) {
			var opt = $("<option>").text(codes[i] + "_" + geom);
			$(that.labelCodeSelect).append(opt);
			if (i === 0) {
				$(opt).prop("selected", true);
				sCode = codes[i] + "_" + geom;
			}
		}
		that._updateLabelAttribute(sCode, obj.multi);

		var tb = $("<table>").append(that.labelAttrForm);
		$(tb).addClass("table");
		$(tb).addClass("text-center");
		if (obj.multi) {
			$(that.dOption).append(that.labelCodeSelect).append(tb).append(that.addLabelAttrBtn);
		} else {
			$(that.dOption).append(that.labelCodeSelect).append(tb);
		}

		var keys = Object.keys(that.getLayerDefinition());
		for (var i = 0; i < keys.length; i++) {
			var checkbox = $("<input>").attr({
				"type" : obj.multi ? "checkbox" : "radio",
				"value" : keys[i],
				"name" : "optiondefinition-label-rel"
			}).css({
				"vertical-align" : "top",
				"margin-right" : "3px"
			});
			$(checkbox).addClass("optiondefinition-label-rel");
			if (that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
				if (that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(vtem)) {
					if (that.getOptDefCopy()[that.selectedLayerNow][vtem].relation.indexOf(keys[i]) !== -1) {
						$(checkbox).prop("checked", true);
					}
				}
			}
			var label = $("<label>").append(checkbox).append(keys[i]);
			var li = $("<li>").css({
				"margin-top" : 0
			}).append(label);
			$(li).addClass("list-group-item");
			$(that.dOption).append(li);
		}
		// that.updateRelation($(this).val(), "upradio");
		// that.radio = $(this).val();
		break;
	case "conditionalfigure":
		var attr;
		var select;
		var figure;
		if (that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
			if (that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(vtem)) {
				attr = that.getOptDefCopy()[that.selectedLayerNow][vtem]["attribute"];
				select = that.getOptDefCopy()[that.selectedLayerNow][vtem]["condition"];
				figure = that.getOptDefCopy()[that.selectedLayerNow][vtem]["figure"];
			}
		}
		this.conAttr = $("<input>").attr({
			"type" : "text",
			"placeholder" : "Attribute"
		}).val(attr ? attr : "");
		$(this.conAttr).addClass("optiondefinition-conditionalfigure-text");
		$(this.conAttr).addClass("gb-form");
		var div1 = $("<div>").css({
			"width" : "90px",
			"display" : "inline-block"
		}).append(this.conAttr);
		$(that.dOption).append(div1);

		var over = $("<option>").attr({
			"value" : "over"
		}).text(">");
		var under = $("<option>").attr({
			"value" : "under"
		}).text("<");
		var equal = $("<option>").attr({
			"value" : "equal"
		}).text("=");
		this.conSelect = $("<select>").addClass("gb-form").addClass("optiondefinition-conditionalfigure-select").append(over).append(under)
				.append(equal);
		if (select) {
			this.conSelect.val(select);
		} else {
			this.conSelect.val("over");
		}
		var div2 = $("<div>").css({
			"width" : "55px",
			"display" : "inline-block"
		}).append(this.conSelect);
		$(that.dOption).append(div2);

		this.conFigure = $("<input>").attr({
			"type" : "number",
			"placeholder" : "figure"
		}).val(figure ? figure : "");
		$(this.conFigure).addClass("optiondefinition-conditionalfigure-figure");
		$(this.conFigure).addClass("gb-form");
		var div3 = $("<div>").css({
			"width" : "85px",
			"display" : "inline-block"
		}).append(this.conFigure);
		$(that.dOption).append(div3);
		break;
	default:
		var li = $("<li>").text("Unknown");
		$(li).addClass("danger");
		break;
	}
};
gb.modal.ValidationDefinition.prototype._updateAttribute = function(code, multi) {
	var that = this;
	var attrs;
	if (that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
		if (that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow)) {
			attrs = that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][code];
		}
	}
	$(that.attrForm).empty();
	if (!attrs && multi) {
		return;
	} else if (attrs && multi) {
		var keys = Object.keys(attrs);
		for (var j = 0; j < keys.length; j++) {
			var text = $("<input>").attr({
				"type" : "text",
				"value" : keys[j],
				"placeholder" : "Key"
			}).css({
				"display" : "inline-block"
			});
			$(text).addClass("gb-form");
			$(text).addClass("optiondefinition-attr-text");
			var td1 = $("<td>").append(text);

			var icon = $("<i>").attr("aria-hidden", true);
			$(icon).addClass("fa");
			$(icon).addClass("fa-times");
			var btn = $("<button>").css({
				"display" : "inline-block"
			}).append(icon);
			$(btn).addClass("gb-button");
			$(btn).addClass("gb-button-default");
			$(btn).addClass("optiondefinition-attr-del");
			var td2 = $("<td>").append(btn);

			var text2 = $("<input>").attr({
				"type" : "text",
				"value" : attrs[keys[j]].toString(),
				"placeholder" : "Value"
			});
			$(text2).addClass("gb-form");
			$(text2).addClass("optiondefinition-attr-text2");
			var td3 = $("<td>").attr({
				"colspan" : "2"
			}).css({
				"border-top" : 0
			}).append(text2);

			var btr1 = $("<tr>").append(td1).append(td2);
			var btr2 = $("<tr>").append(td3);

			$(that.attrForm).append(btr1).append(btr2);
		}
	} else if (attrs && !multi) {
		var keys = Object.keys(attrs);

		var text = $("<input>").attr({
			"type" : "text",
			"value" : keys[0] ? keys[0] : "",
			"placeholder" : "Key"
		}).css({
			"display" : "inline-block"
		});
		$(text).addClass("gb-form");
		$(text).addClass("optiondefinition-attr-text");
		var td1 = $("<td>").append(text);

		var text2 = $("<input>").attr({
			"type" : "text",
			"value" : attrs[keys[0]] ? attrs[keys[0]].toString() : "",
			"placeholder" : "Value"
		});
		$(text2).addClass("gb-form");
		$(text2).addClass("optiondefinition-attr-text2");
		var td3 = $("<td>").attr({
			"colspan" : "2"
		}).css({
			"border-top" : 0
		}).append(text2);

		var btr1 = $("<tr>").append(td1);
		var btr2 = $("<tr>").append(td3);

		$(that.attrForm).append(btr1).append(btr2);
	} else if (!attrs && !multi) {
		var text = $("<input>").attr({
			"type" : "text",
			"placeholder" : "Key"
		}).css({
			"display" : "inline-block"
		});
		$(text).addClass("gb-form");
		$(text).addClass("optiondefinition-attr-text");
		var td1 = $("<td>").append(text);

		var text2 = $("<input>").attr({
			"type" : "text",
			"placeholder" : "Value"
		});
		$(text2).addClass("gb-form");
		$(text2).addClass("optiondefinition-attr-text2");
		var td3 = $("<td>").attr({
			"colspan" : "2"
		}).css({
			"border-top" : 0
		}).append(text2);

		var btr1 = $("<tr>").append(td1);
		var btr2 = $("<tr>").append(td3);

		$(that.attrForm).append(btr1).append(btr2);
	} else {
		console.error("unknown");
	}

};
gb.modal.ValidationDefinition.prototype._updateLabelAttribute = function(code, multi) {
	var that = this;
	var attrs;
	if (that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
		if (that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow)) {
			if (that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow].hasOwnProperty("label")) {
				// .hasOwnProperty(code)
				attrs = that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow]["label"][code];
			}
		}
	}
	$(that.labelAttrForm).empty();
	if (!attrs && multi) {
		return;
	} else if (attrs && multi) {
		var keys = Object.keys(attrs);
		for (var j = 0; j < keys.length; j++) {
			var text = $("<input>").attr({
				"type" : "text",
				"value" : keys[j],
				"placeholder" : "Key"
			}).css({
				"display" : "inline-block"
			});
			$(text).addClass("gb-form");
			$(text).addClass("optiondefinition-labelattr-text");
			var td1 = $("<td>").append(text);

			var icon = $("<i>").attr("aria-hidden", true);
			$(icon).addClass("fa");
			$(icon).addClass("fa-times");
			var btn = $("<button>").css({
				"display" : "inline-block"
			}).append(icon);
			$(btn).addClass("gb-button");
			$(btn).addClass("gb-button-default");
			$(btn).addClass("optiondefinition-labelattr-del");
			var td2 = $("<td>").append(btn);

			var text2 = $("<input>").attr({
				"type" : "text",
				"value" : attrs[keys[j]].toString(),
				"placeholder" : "Value"
			});
			$(text2).addClass("gb-form");
			$(text2).addClass("optiondefinition-labelattr-text2");
			var td3 = $("<td>").attr({
				"colspan" : "2"
			}).css({
				"border-top" : 0
			}).append(text2);

			var btr1 = $("<tr>").append(td1).append(td2);
			var btr2 = $("<tr>").append(td3);

			$(that.labelAttrForm).append(btr1).append(btr2);
		}
	} else if (attrs && !multi) {
		var keys = Object.keys(attrs);

		var text = $("<input>").attr({
			"type" : "text",
			"value" : keys[0] ? keys[0] : "",
			"placeholder" : "Key"
		}).css({
			"display" : "inline-block"
		});
		$(text).addClass("gb-form");
		$(text).addClass("optiondefinition-labelattr-text");
		var td1 = $("<td>").append(text);

		var text2 = $("<input>").attr({
			"type" : "text",
			"value" : attrs[keys[0]] ? attrs[keys[0]].toString() : "",
			"placeholder" : "Value"
		});
		$(text2).addClass("gb-form");
		$(text2).addClass("optiondefinition-labelattr-text2");
		var td3 = $("<td>").attr({
			"colspan" : "2"
		}).css({
			"border-top" : 0
		}).append(text2);

		var btr1 = $("<tr>").append(td1);
		var btr2 = $("<tr>").append(td3);

		$(that.labelAttrForm).append(btr1).append(btr2);
	} else if (!attrs && !multi) {
		var text = $("<input>").attr({
			"type" : "text",
			"placeholder" : "Key"
		}).css({
			"display" : "inline-block"
		});
		$(text).addClass("gb-form");
		$(text).addClass("optiondefinition-labelattr-text");
		var td1 = $("<td>").append(text);

		var text2 = $("<input>").attr({
			"type" : "text",
			"placeholder" : "Value"
		});
		$(text2).addClass("gb-form");
		$(text2).addClass("optiondefinition-labelattr-text2");
		var td3 = $("<td>").attr({
			"colspan" : "2"
		}).css({
			"border-top" : 0
		}).append(text2);

		var btr1 = $("<tr>").append(td1);
		var btr2 = $("<tr>").append(td3);

		$(that.labelAttrForm).append(btr1).append(btr2);
	} else {
		console.error("unknown");
	}

};
gb.modal.ValidationDefinition.prototype._updateNotNullAttribute = function(code, multi) {
	var that = this;
	var attrs;
	if (that.getOptDefCopy().hasOwnProperty(that.selectedLayerNow)) {
		if (that.getOptDefCopy()[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow)) {
			attrs = that.getOptDefCopy()[that.selectedLayerNow][that.selectedValidationNow][code];
		}
	}
	$(that.nnullAttrForm).empty();
	if (!attrs && multi) {
		return;
	} else if (!attrs && !multi) {
		var text = $("<input>").attr({
			"type" : "text",
			"value" : ""
		}).css({
			"display" : "inline-block"
		});
		$(text).addClass("gb-form");
		$(text).addClass("optiondefinition-nnullattr-text");
		var td1 = $("<td>").append(text);
		btr1 = $("<tr>").append(td1);
		$(that.nnullAttrForm).append(btr1);
	} else if (attrs && multi) {
		var keys = attrs;
		for (var j = 0; j < keys.length; j++) {
			var text = $("<input>").attr({
				"type" : "text",
				"value" : keys[j]
			}).css({
				"display" : "inline-block"
			});
			$(text).addClass("gb-form");
			$(text).addClass("optiondefinition-nnullattr-text");
			var td1 = $("<td>").append(text);

			var icon = $("<i>").attr("aria-hidden", true);
			$(icon).addClass("fa");
			$(icon).addClass("fa-times");

			var btr1;

			var btn = $("<button>").css({
				"display" : "inline-block"
			}).append(icon);
			$(btn).addClass("gb-button");
			$(btn).addClass("gb-button-default");
			$(btn).addClass("optiondefinition-nnullattr-del");
			var td2 = $("<td>").append(btn);

			btr1 = $("<tr>").append(td1).append(td2);
			$(that.nnullAttrForm).append(btr1);
		}
	} else if (attrs && !multi) {
		var keys = attrs;
		for (var j = 0; j < keys.length; j++) {
			var text = $("<input>").attr({
				"type" : "text",
				"value" : keys[j]
			}).css({
				"display" : "inline-block"
			});
			$(text).addClass("gb-form");
			$(text).addClass("optiondefinition-nnullattr-text");
			var td1 = $("<td>").append(text);
			btr1 = $("<tr>").append(td1);
			$(that.nnullAttrForm).append(btr1);
		}
	} else {
		console.error("unknown");
	}

};

/**
 * 모달을 연다
 * 
 * @name open
 */
gb.modal.ValidationDefinition.prototype.open = function() {
	gb.modal.Base.prototype.open.call(this);
	this.updateLayerDefinition();
	this.setOptDefCopy(Object.assign({}, this.getDefinition()));
	this.update();
	this.resetRelation();
};