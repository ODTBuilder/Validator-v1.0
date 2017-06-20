/**
 * 검수 옵션을 정의하는 객체를 정의한다.
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
gitbuilder.ui.OptionDefinition20 = $.widget("gitbuilder.optiondefinition20", {
	widnow : undefined,
	optDef : undefined,
	optDefCopy : undefined,
	layerDef : undefined,
	itemList : {
		point : [ "EntityDuplicated", "SelfEntity", "AttributeFix", "OutBoundary", "CharacterAccuracy", "OverShoot", "UnderShoot" ],
		linestring : [ "SmallLength", "EntityDuplicated", "SelfEntity", "PointDuplicated", "ConIntersected", "ConOverDegree", "ConBreak", "AttributeFix", "OutBoundary",
				"zValueAmbiguous", "UselessPoint", "OverShoot", "UnderShoot" ],
		polygon : [ "SmallArea", "EntityDuplicated", "SelfEntity", "PointDuplicated", "AttributeFix", "OutBoundary", "OverShoot", "UnderShoot" ]
	},
	optItem : {
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
			"type" : "attribute"
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
		"zValueAmbiguous" : {
			"title" : "Altitude Error",
			"alias" : "zValueAmbiguous",
			"type" : "attribute"
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
	},
	selectedLayerNow : undefined,
	selectedValidationNow : undefined,
	selectedDetailNow : undefined,
	selectedLayerBefore : undefined,
	lAlias : $("<ul>").addClass("list-group").css({
		"margin-bottom" : 0,
		"cursor" : "pointer"
	}),
	vItem : $("<ul>").addClass("list-group").css({
		"margin-bottom" : 0,
		"cursor" : "pointer"
	}),
	dOption : $("<ul>").addClass("list-group").css({
		"margin-bottom" : 0
	}),
	codeSelect : $("<select>").addClass("form-control").addClass("optiondefinition20-attr-select"),
	attrForm : $("<tbody>"),
	addBtn : $("<button>").text("Add Attribute").addClass("optiondefinition20-attr-addrow").addClass("btn").addClass("btn-default"),
	file : $("<input>").attr({
		"type" : "file"
	}),
	options : {
		definition : undefined,
		layerDefinition : undefined,
		appendTo : "body"
	},
	_create : function() {
		if (typeof this.options.layerDefinition === "function") {
			this.layerDef = $.extend({}, this.options.layerDefinition());
		} else {
			this.layerDef = $.extend({}, this.options.layerDefinition);
		}
		this.optDef = $.extend({}, this.options.definition);
		this.optDefCopy = JSON.parse(JSON.stringify(this.optDef));

		var that = this;
		this._on(false, this.element, {
			click : function(event) {
				if (event.target === that.element[0]) {
					that.open();
				}
			}
		});
		var xSpan = $("<span>").attr({
			"aria-hidden" : true
		}).html("&times;");
		var xButton = $("<button>").attr({
			"type" : "button",
			"data-dismiss" : "modal",
			"aria-label" : "Close"
		}).html(xSpan);
		this._addClass(xButton, "close");

		var htag = $("<h4>");
		htag.text("Validation Option");
		this._addClass(htag, "modal-title");

		var header = $("<div>").append(xButton).append(htag);
		this._addClass(header, "modal-header");
		/*
		 * 
		 * 
		 * header end
		 * 
		 * 
		 */
		var phead1 = $("<div>").text("Layer Alias");
		this._addClass(phead1, "panel-heading");
		var pbody1 = $("<div>").css({
			"max-height" : "500px",
			"overflow-y" : "auto"
		}).append(this.lAlias);
		$(document).on("click", ".optiondefinition20-alias", function() {
			$(that.dOption).empty();
			var chldr = $(this).parent().children();
			for (var i = 0; i < chldr.length; i++) {
				$(chldr).removeClass("active");
			}
			$(this).addClass("active");
			var text = $(this).find(".optiondefinition20-alias-span").text();
			that.selectedLayerNow = text;
			var opt = that.optDefCopy[text];
			var mix = {
				"obj" : opt,
				"geom" : that.layerDef[text].geom
			};
			that._printValidationItem(mix);
		});
		this._addClass(pbody1, "panel-body");
		var panel1 = $("<div>").append(phead1).append(pbody1);
		this._addClass(panel1, "panel");
		this._addClass(panel1, "panel-default");

		var phead2 = $("<div>").text("Validation Item");
		this._addClass(phead2, "panel-heading");
		var pbody2 = $("<div>").css({
			"max-height" : "500px",
			"overflow-y" : "auto"
		}).append(this.vItem);
		$(document).on("click", ".optiondefinition20-item", function() {
			var chldr = $(this).parent().children();
			for (var i = 0; i < chldr.length; i++) {
				$(chldr).removeClass("active");
			}
			$(this).addClass("active");
			var name = $(this).find("input").val();
			that.selectedValidationNow = name;
			var opt;
			if (!!that.optDefCopy[that.selectedLayerNow]) {
				opt = that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow];
			}
			var mix = {
				"obj" : opt,
				"vtem" : name
			};
			that._printDetailedOption(mix);
		});
		$(document).on("change", ".optiondefinition20-item-check", function() {
			if ($(this).prop("checked")) {
				if (that.optItem[$(this).val()].type === "none") {
					if (!that.optDefCopy.hasOwnProperty(that.selectedLayerNow)) {
						that.optDefCopy[that.selectedLayerNow] = {};
					}
					that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow] = true;
				}
			} else {
				delete that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow];
			}
		});

		$(document).on("input", ".optiondefinition20-figure-text", function() {
			if ($(this).val() === "") {
				delete that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow];
				that._toggleCheckbox(that.selectedValidationNow, false);
			} else if ($.isNumeric($(this).val())) {
				if (!that.optDefCopy.hasOwnProperty(that.selectedLayerNow)) {
					that.optDefCopy[that.selectedLayerNow] = {};
				}
				if (!that.optDefCopy[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow)) {
					that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow] = {
						"figure" : undefined
					};
				}
				that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow]["figure"] = $(this).val();
				that._toggleCheckbox(that.selectedValidationNow, true);
			} else {
				delete that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow];
				that._toggleCheckbox(that.selectedValidationNow, false);
				$(this).val("");
			}
		});

		$(document).on(
				"change",
				".optiondefinition20-rel-check",
				function() {
					if ($(this).prop("checked")) {
						if (!that.optDefCopy.hasOwnProperty(that.selectedLayerNow)) {
							that.optDefCopy[that.selectedLayerNow] = {};
						}
						if (!that.optDefCopy[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow)) {
							that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow] = {
								"relation" : []
							};
						}
						if (that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow]["relation"].indexOf($(this).val()) === -1) {
							that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow]["relation"].push($(this).val());
						}
						that._toggleCheckbox(that.selectedValidationNow, true);
					} else {
						if (that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow]["relation"].indexOf($(this).val()) !== -1) {
							that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow]["relation"].splice(
									that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow]["relation"].indexOf($(this).val()), 0);
						}
					}
					var checks = $(this).parent().parent().parent().find("input:checked");
					if (checks.length === 0) {
						delete that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow];
						that._toggleCheckbox(that.selectedValidationNow, false);
					}
				});

		$(document).on("change", ".optiondefinition20-attr-select", function() {
			that._updateAttribute($(this).val());
		});
		$(document).on("click", ".optiondefinition20-attr-del", function() {
			var row1 = $(this).parent().parent();
			var row2 = $(this).parent().parent().next();
			var keyname = $(row1).find("input[type=text]").val();
			var selected = $(that.codeSelect).val();
			delete that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow][selected][keyname];
			var keys = Object.keys(that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow]);
			var count = 0;
			for (var i = 0; i < keys.length; i++) {
				var length = Object.keys(that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow][keys[i]]).length;
				if (length === 0) {
					delete that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow][keys[i]]
				}
				count = count + length;
			}
			that._toggleCheckbox(that.selectedValidationNow, !!count);
			$(row2).remove();
			$(row1).remove();
		});
		$(document).on("click", ".optiondefinition20-attr-addrow", function() {
			var text = $("<input>").attr({
				"type" : "text"
			}).css({
				"display" : "inline-block"
			});
			that._addClass(text, "form-control");
			that._addClass(text, "optiondefinition20-attr-text");
			var td1 = $("<td>").append(text);

			var icon = $("<i>").attr("aria-hidden", true);
			that._addClass(icon, "fa");
			that._addClass(icon, "fa-times");
			var btn = $("<button>").css({
				"display" : "inline-block"
			}).append(icon);
			that._addClass(btn, "btn");
			that._addClass(btn, "btn-default");
			that._addClass(btn, "optiondefinition20-attr-del");
			var td2 = $("<td>").append(btn);

			var text2 = $("<input>").attr({
				"type" : "text"
			});
			that._addClass(text2, "form-control");
			that._addClass(text2, "optiondefinition20-attr-text2");
			var td3 = $("<td>").attr({
				"colspan" : "2"
			}).css({
				"border-top" : 0
			}).append(text2);

			var btr1 = $("<tr>").append(td1).append(td2);
			var btr2 = $("<tr>").append(td3);

			$(that.attrForm).append(btr1).append(btr2);
		});
		$(document).on("input", ".optiondefinition20-attr-text, .optiondefinition20-attr-text2", function() {
			var attrs = $(that.attrForm).find("input.optiondefinition20-attr-text");
			var obj = {};
			for (var i = 0; i < attrs.length; i++) {
				var key = $(attrs[i]).val();
				var values = $(attrs[i]).parent().parent().next().find("input[type=text].optiondefinition20-attr-text2").val().split(",");
				obj[key] = values;
			}
			var selected = $(that.codeSelect).val();
			if (!that.optDefCopy.hasOwnProperty(that.selectedLayerNow)) {
				that.optDefCopy[that.selectedLayerNow] = {};
			}
			if (!that.optDefCopy[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow)) {
				that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow] = {};
			}
			that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow][selected] = obj;
			var keys = Object.keys(that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow]);
			var count = 0;
			for (var i = 0; i < keys.length; i++) {
				var length = Object.keys(that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow][keys[i]]).length;
				if (length === 0) {
					delete that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow][keys[i]]
				}
				count = count + length;
			}
			that._toggleCheckbox(that.selectedValidationNow, !!count);
			// var flag = false;
			// var keys =
			// Object.keys(that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow]);
			// if (keys.length > 0) {
			// for (var i = 0; i < keys.length; i++) {
			// var arr =
			// that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow][keys[i]];
			// if (arr.length > 0) {
			// flag = true;
			// }
			// }
			// }
			// that._toggleCheckbox(that.selectedValidationNow, flag);
		});

		this._addClass(pbody2, "panel-body");
		var panel2 = $("<div>").append(phead2).append(pbody2);
		this._addClass(panel2, "panel");
		this._addClass(panel2, "panel-default");

		var phead3 = $("<div>").text("Detailed Option");
		this._addClass(phead3, "panel-heading");
		var pbody3 = $("<div>").css({
			"max-height" : "500px",
			"overflow-y" : "auto"
		}).append(this.dOption);
		this._addClass(pbody3, "panel-body");
		var panel3 = $("<div>").append(phead3).append(pbody3);
		this._addClass(panel3, "panel");
		this._addClass(panel3, "panel-default");

		var left = $("<div>").append(panel1);
		this._addClass(left, "col-md-4");

		var mid = $("<div>").append(panel2);
		this._addClass(mid, "col-md-4");

		var right = $("<div>").append(panel3);
		this._addClass(right, "col-md-4");

		var upper = $("<div>").append(left).append(mid).append(right);
		this._addClass(upper, "row");
		var lower = $("<div>").append(this.file).css({
			"display" : "none"
		});
		this._on(false, this.file, {
			change : function(event) {
				var fileList = that.file[0].files;
				var reader = new FileReader();
				if (fileList.length === 0) {
					return;
				}
				reader.readAsText(fileList[0]);
				that._on(false, reader, {
					load : function(event) {
						var obj = JSON.parse(reader.result);
						that.optDefCopy = obj;
						that.update();
						$(lower).css("display", "none");
					}
				});
			}
		});
		var body = $("<div>").append(upper).append(lower);
		this._addClass(body, "modal-body");
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
		this._addClass(uploadBtn, "btn");
		this._addClass(uploadBtn, "btn-default");
		$(uploadBtn).text("Upload");
		this._on(false, uploadBtn, {
			click : function(event) {
				if (event.target === uploadBtn[0]) {
					if ($(lower).css("display") === "none") {
						$(lower).css("display", "block");
					} else {
						$(lower).css("display", "none");
					}
				}
			}
		});
		var downloadBtn = $("<button>").attr({
			"type" : "button"
		});
		this._addClass(downloadBtn, "btn");
		this._addClass(downloadBtn, "btn-default");
		$(downloadBtn).text("Download");
		this._on(false, downloadBtn, {
			click : function(event) {
				if (event.target === downloadBtn[0]) {
					that.downloadSetting();
				}
			}
		});

		var pleft = $("<span>").css("float", "left");
		// this._addClass(pleft, "text-left");
		$(pleft).append(uploadBtn).append(downloadBtn);

		var closeBtn = $("<button>").attr({
			"type" : "button",
			"data-dismiss" : "modal"
		});
		this._addClass(closeBtn, "btn");
		this._addClass(closeBtn, "btn-default");
		$(closeBtn).text("Close");

		var okBtn = $("<button>").attr({
			"type" : "button"
		});
		this._addClass(okBtn, "btn");
		this._addClass(okBtn, "btn-primary");
		$(okBtn).text("Save");

		this._on(false, okBtn, {
			click : function(event) {
				if (event.target === okBtn[0]) {
					that.setDefinition(that.optDefCopy);
					that.close();
				}
			}
		});

		var pright = $("<span>").css("float", "right");
		$(pright).append(closeBtn).append(okBtn);

		var footer = $("<div>").append(pleft).append(pright);
		this._addClass(footer, "modal-footer");
		/*
		 * 
		 * 
		 * footer end
		 * 
		 * 
		 */
		var content = $("<div>").append(header).append(body).append(footer);
		this._addClass(content, "modal-content");

		var dialog = $("<div>").html(content);
		this._addClass(dialog, "modal-dialog");
		this._addClass(dialog, "modal-lg");

		this.window = $("<div>").hide().attr({
			// Setting tabIndex makes the div focusable
			tabIndex : -1,
			role : "dialog",
		}).html(dialog);
		this._addClass(this.window, "modal");
		this._addClass(this.window, "fade");

		this.window.appendTo(this._appendTo());
		this.window.modal({
			backdrop : "static",
			keyboard : true,
			show : false,
		});
	},
	_init : function() {
		this.layerDef = $.extend({}, this.options.layerDefinition);
		this.optDef = $.extend({}, this.options.definition);
		this.optDefCopy = JSON.parse(JSON.stringify(this.optDef));
	},
	downloadSetting : function() {
		var setting = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(this.optDefCopy));
		var anchor = $("<a>").attr({
			"href" : setting,
			"download" : "validation_setting.json"
		});
		$(anchor)[0].click();
	},
	setDefinition : function(obj) {
		this.optDef = obj;
	},
	getDefinition : function() {
		return this.optDef;
	},
	setLayerDefinition : function(obj) {
		this.layerDef = obj;
	},
	getLayerDefinition : function() {
		return this.layerDef;
	},
	update : function(obj) {
		var that = this;
		if (!obj) {
			obj = this.layerDef;
		}
		if (typeof this.options.layerDefinition === "function") {
			this.layerDef = this.options.layerDefinition();
			obj = this.layerDef;
		} else {
			this.layerDef = this.options.layerDefinition;
			obj = this.layerDef;
		}

		$(this.lAlias).empty();
		var keys = Object.keys(obj);
		for (var i = 0; i < keys.length; i++) {
			var alias = $("<span>").text(keys[i]);
			that._addClass(alias, "optiondefinition20-alias-span");
			var span = $("<span>").text(obj[keys[i]].geom).css({
				"font-weight" : "100"
			});
			that._addClass(span, "badge");
			var anchor = $("<li>").attr({
				"title" : obj[keys[i]].code
			}).append(alias).append(span);
			that._addClass(anchor, "list-group-item");
			that._addClass(anchor, "optiondefinition20-alias");
			if (obj[keys[i]].area) {
				that._addClass(anchor, "list-group-item-info");
			}
			$(that.lAlias).append(anchor);
		}
		$(this.vItem).empty();
		$(this.dOption).empty();
	},
	_printValidationItem : function(mix) {
		var obj = mix.obj;
		var geom = mix.geom;
		// vItem
		var that = this;
		$(that.vItem).empty();
		var lower = geom.toLowerCase();
		var list;
		switch (lower) {
		case "point":
			list = that.itemList.point;
			break;
		case "linestring":
			list = that.itemList.linestring;
			break;
		case "polygon":
			list = that.itemList.polygon;
			break;
		default:
			break;
		}
		for (var i = 0; i < list.length; i++) {
			var checkbox = $("<input>").attr({
				"type" : "checkbox",
				"value" : that.optItem[list[i]].alias
			});
			that._addClass(checkbox, "optiondefinition20-item-check");
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

			that._addClass(li, "list-group-item");
			that._addClass(li, "optiondefinition20-item");
			$(that.vItem).append(li);
		}

	},
	_printDetailedOption : function(mix) {
		var optObj = mix.obj;
		var vtem = mix.vtem;
		var that = this;
		$(that.dOption).empty();
		var obj = this.optItem[vtem];
		switch (obj.type) {
		case "none":
			var li = $("<li>").text("No detailed option");
			that._addClass(li, "list-group-item");
			// that._addClass(li, "list-group-item-info");
			$(that.dOption).append(li);
			break;
		case "figure":
			var input = $("<input>").attr({
				"type" : "text"
			});
			that._addClass(input, "optiondefinition20-figure-text");
			that._addClass(input, "form-control");
			if (!!optObj) {
				var keys = Object.keys(optObj);
				if (keys.indexOf("figure") !== -1) {
					$(input).val(optObj["figure"]);
				}
			}
			var addon = $("<div>");
			that._addClass(addon, "input-group-addon");
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
			that._addClass(group, "input-group");
			$(that.dOption).append(group);
			break;
		case "relation":
			var keys = Object.keys(that.layerDef);
			for (var i = 0; i < keys.length; i++) {
				var checkbox = $("<input>").attr({
					"type" : "checkbox",
					"value" : keys[i]
				}).css({
					"vertical-align" : "top",
					"margin-right" : "3px"
				});
				that._addClass(checkbox, "optiondefinition20-rel-check");
				if (that.optDefCopy.hasOwnProperty(that.selectedLayerNow)) {
					if (that.optDefCopy[that.selectedLayerNow].hasOwnProperty(vtem)) {
						if (that.optDefCopy[that.selectedLayerNow][vtem].relation.indexOf(keys[i]) !== -1) {
							$(checkbox).prop("checked", true);
						}
					}
				}

				var label = $("<label>").append(checkbox).append(keys[i]);
				var li = $("<li>").css({
					"margin-top" : 0
				}).append(label);
				that._addClass(li, "list-group-item");
				$(that.dOption).append(li);
			}
			break;
		case "attribute":
			$(that.codeSelect).empty();
			var codes = that.layerDef[that.selectedLayerNow].code;
			var sCode;
			for (var i = 0; i < codes.length; i++) {
				var opt = $("<option>").text(codes[i]);
				$(that.codeSelect).append(opt);
				if (i === 0) {
					$(opt).prop("selected", true);
					sCode = codes[i];
				}
			}
			that._updateAttribute(sCode);

			var tb = $("<table>").append(that.attrForm);
			that._addClass(tb, "table");
			that._addClass(tb, "text-center");

			$(that.dOption).append(that.codeSelect).append(tb).append(that.addBtn);
			break;
		default:
			var li = $("<li>").text("Unknown");
			that._addClass(li, "danger");
			break;
		}
	},
	_updateAttribute : function(code) {
		var that = this;
		var attrs;
		if (that.optDefCopy.hasOwnProperty(that.selectedLayerNow)) {
			if (that.optDefCopy[that.selectedLayerNow].hasOwnProperty(that.selectedValidationNow)) {
				attrs = that.optDefCopy[that.selectedLayerNow][that.selectedValidationNow][code];
			}
		}
		$(that.attrForm).empty();
		if (!attrs) {
			return;
		}
		var keys = Object.keys(attrs);
		for (var j = 0; j < keys.length; j++) {
			var text = $("<input>").attr({
				"type" : "text",
				"value" : keys[j]
			}).css({
				"display" : "inline-block"
			});
			that._addClass(text, "form-control");
			that._addClass(text, "optiondefinition20-attr-text");
			var td1 = $("<td>").append(text);

			var icon = $("<i>").attr("aria-hidden", true);
			this._addClass(icon, "fa");
			this._addClass(icon, "fa-times");
			var btn = $("<button>").css({
				"display" : "inline-block"
			}).append(icon);
			that._addClass(btn, "btn");
			that._addClass(btn, "btn-default");
			that._addClass(btn, "optiondefinition20-attr-del");
			var td2 = $("<td>").append(btn);

			var text2 = $("<input>").attr({
				"type" : "text",
				"value" : attrs[keys[j]].toString()
			});
			that._addClass(text2, "form-control");
			that._addClass(text2, "optiondefinition20-attr-text2");
			var td3 = $("<td>").attr({
				"colspan" : "2"
			}).css({
				"border-top" : 0
			}).append(text2);

			var btr1 = $("<tr>").append(td1).append(td2);
			var btr2 = $("<tr>").append(td3);

			$(that.attrForm).append(btr1).append(btr2);
		}
	},
	_toggleCheckbox : function(vtem, bool) {
		var that = this;
		var inputs = $(that.vItem).find("input");
		for (var i = 0; i < inputs.length; i++) {
			if ($(inputs[i]).val() === vtem) {
				$(inputs[i]).prop("checked", bool);
			}
		}
	},
	open : function() {
		this.optDefCopy = JSON.parse(JSON.stringify(this.optDef));
		this.window.modal('show');
		this.update();
	},
	close : function() {
		this.window.modal('hide');
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