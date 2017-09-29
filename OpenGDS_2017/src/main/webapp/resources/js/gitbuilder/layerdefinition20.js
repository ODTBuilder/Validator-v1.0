/**
 * 레이어 코드를 정의하는 객체를 정의한다.
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
gitbuilder.ui.LayerDefinition20 = $.widget("gitbuilder.layerdefinition20", {
	widnow : undefined,
	layerDef : undefined,
	tbody : undefined,
	message : undefined,
	file : undefined,
	options : {
		definition : undefined,
		appendTo : "body"
	},
	_create : function() {
		this.layerDef = $.extend({}, this.options.definition);

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
		htag.text("Layer Definition");
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
		var tdhead1 = $("<td>").text("#");
		var tdhead2 = $("<td>").text("Layer Name");
		var tdhead3 = $("<td>").text("Layer Code");
		var tdhead4 = $("<td>").text("Geometry Type");
		var tdhead5 = $("<td>").text("Delete");
		var tdhead6 = $("<td>").text("QA Area");
		// var tdhead7 = $("<td>").text("Weight");
		var trhead = $("<tr>").append(tdhead1).append(tdhead2).append(tdhead3).append(tdhead4).append(tdhead5).append(tdhead6);
		var thead = $("<thead>").append(trhead);
		that.tbody = $("<tbody>");
		var tb = $("<table>").append(thead).append(that.tbody);
		this._addClass(tb, "table");
		this._addClass(tb, "table-striped");
		this._addClass(tb, "text-center");
		this.update();
		$(document).on("click", ".layerdefinition20-del", function(event) {
			var laName;
			if (event.target === this) {
				laName = $(event.target).parent().parent().find("td:eq(1) > input").val();
				$(event.target).parent().parent().remove();
			} else if ($(event.target).parent()[0] === this) {
				laName = $(event.target).parent().parent().parent().find("td:eq(1) > input").val();
				$(event.target).parent().parent().parent().remove();
			}
		});
		var addBtn = $("<button>").attr({
			"type" : "button"
		});
		this._addClass(addBtn, "btn");
		this._addClass(addBtn, "btn-default");
		$(addBtn).text("Add Row");
		this._on(false, addBtn, {
			click : function(event) {
				if (event.target === addBtn[0]) {
					var no = $("<span>").css({
						"vertical-align" : "-webkit-baseline-middle"
					}).text($(that.tbody).find("tr:last").index() + 2)
					var td1 = $("<td>").append(no);
					var lname = $("<input>").val("");
					this._addClass(lname, "form-control");
					var td2 = $("<td>").append(lname);
					$(td2).attr({
						"type" : "text"
					});
					var lcode = $("<input>").val("");
					this._addClass(lcode, "form-control");
					var td3 = $("<td>").append(lcode);
					$(td3).attr({
						"type" : "text"
					});
					var ty1 = $("<option>").text("Point").val("point");
					var ty11 = $("<option>").text("MultiPoint").val("multipoint");
					var ty2 = $("<option>").text("LineString").val("linestring");
					var ty22 = $("<option>").text("MultiLineString").val("multilinestring");
					var ty3 = $("<option>").text("Polygon").val("polygon");
					var ty33 = $("<option>").text("MultiPolygon").val("multipolygon");
					var ty4 = $("<option>").text("Text").val("text");
					var gtype = $("<select>").append(ty1).append(ty11).append(ty2).append(ty22).append(ty3).append(ty33).append(ty4);
					this._addClass(gtype, "form-control");
					var td4 = $("<td>").append(gtype);
					var icon = $("<i>").attr("aria-hidden", true);
					this._addClass(icon, "fa");
					this._addClass(icon, "fa-times");
					var delBtn = $("<button>").append(icon);
					this._addClass(delBtn, "btn");
					this._addClass(delBtn, "btn-default");
					this._addClass(delBtn, "layerdefinition20-del");
					var td5 = $("<td>").append(delBtn);
					var radio = $("<input>").attr({
						"type" : "radio",
						"name" : "layerdefinition20-area"
					}).css({
						"vertical-align" : "-webkit-baseline-middle"
					});
					var td6 = $("<td>").append(radio);
					// var weight = $("<input>").attr({
					// "type" : "number",
					// "min" : 1,
					// "max" : 100
					// });
					// that._addClass(weight, "form-control");
					// var td7 = $("<td>").append(weight);
					var tr = $("<tr>").append(td1).append(td2).append(td3).append(td4).append(td5).append(td6);
					$(that.tbody).append(tr);
				}
			}
		});
		var upper = $("<div>").css({
			"overflow-y" : "auto",
			"height" : "400px"
		}).append(tb);
		this.message = $("<p>").css({
			"display" : "none"
		});
		this._addClass(this.message, "text-danger");
		var mid = $("<div>").append(this.message).append(addBtn);
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
						var obj = JSON.parse(reader.result.replace(/(\s*)/g, ''));
						that.update(obj);
						$(lower).css("display", "none");
					}
				});
			}
		});
		var body = $("<div>").append(upper).append(mid).append(lower);
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
					var obj = that.getDefinitionForm();
					if (!!obj) {
						that.setDefinition(obj);
						that.close();
					}
				}
			}
		});

		var pright = $("<span>").css("float", "right");
		// this._addClass(pright, "text-right");
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
		this.layerDef = $.extend({}, this.options.definition);
	},
	downloadSetting : function() {
		var def = this.getDefinitionForm();
		if (!!def) {
			var setting = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(def));
			var anchor = $("<a>").attr({
				"href" : setting,
				"download" : "layer_setting.json"
			});
			$(anchor)[0].click();
		}
	},
	getDefinitionForm : function() {
		var that = this;
		var flag = true;
		var tbody = this.tbody;
		var def = {};
		var result;
		var children = $(tbody).children();
		var error = [];
		// var tWeight = 0;
		for (var i = 0; i < children.length; i++) {
			// var wei =
			// parseInt($(children[i]).find("td:eq(6)>input[type=number]").val());
			// tWeight = tWeight + wei;
			// if (wei < 1) {
			// flag = false;
			// $(that.message).css({
			// "display" : "block"
			// }).text("Each weight must be over than 0%");
			// }
			$(children[i]).removeClass("danger");
			for (var j = 0; j < children.length; j++) {
				if (i !== j) {
					if ($(children[i]).find("td:eq(1)>input").val() !== ""
							&& $(children[i]).find("td:eq(1)>input").val().replace(/(\s*)/g, '') === $(children[j]).find("td:eq(1)>input")
									.val().replace(/(\s*)/g, '')) {
						error.push(children[i]);
						error.push(children[j]);
						flag = false;
						$(that.message).css({
							"display" : "block"
						}).text("Same layer names are not allowed.");
					}
				}
			}
			var area = $("input[name=layerdefinition20-area]:checked");
			if (area.length === 0) {
				flag = false;
				$(that.message).css({
					"display" : "block"
				}).text("QA area must be set.");
			}
			if ($(children[i]).find("td:eq(1)>input").val() === "" || $(children[i]).find("td:eq(2)>input").val() === "") {
				$(children[i]).addClass("warning");
				flag = false;
				$(that.message).css({
					"display" : "block"
				}).text("Blank spaces are not allowed.");
			} else {
				$(children[i]).removeClass("warning");
			}

			var code = $(children[i]).find("td:eq(2)>input").val();
			code.replace(/(\s*)/g, '');
			var spCode = code.split(",");
			var geom = $(children[i]).find("td:eq(3)>select").val();
			var area;
			if ($(children[i]).find("td:eq(5)>input").prop("checked")) {
				area = true;
			} else {
				area = false;
			}
			// var wVal =
			// parseInt($(children[i]).find("td:eq(6)>input[type=number]").val());
			def[$(children[i]).find("td:eq(1)>input").val().replace(/(\s*)/g, '')] = {
				"code" : spCode,
				"geom" : geom,
				"area" : area
			// ,
			// "weight" : wVal
			};
		}
		// if (tWeight !== 100) {
		// flag = false;
		// $(that.message).css({
		// "display" : "block"
		// }).text("Total weight must be 100%");
		// }
		for (var k = 0; k < error.length; k++) {
			$(error[k]).addClass("danger");
		}
		if (flag) {
			$(that.message).css({
				"display" : "none"
			});
			result = def;
		}
		return result;
	},
	setDefinition : function(obj) {
		this.layerDef = obj;
	},
	getDefinition : function() {
		return this.layerDef;
	},
	update : function(obj) {
		var that = this;
		$(this.tbody).empty();
		$(that.message).css({
			"display" : "none"
		});
		if (!obj) {
			obj = this.layerDef;
		}
		var keys = Object.keys(obj);
		for (var i = 0; i < keys.length; i++) {
			var no = $("<span>").css({
				"vertical-align" : "-webkit-baseline-middle"
			}).text((i + 1));
			var td1 = $("<td>").append(no);
			var lname = $("<input>").attr({
				"type" : "text"
			}).val(keys[i]);
			this._addClass(lname, "form-control");
			var td2 = $("<td>").append(lname);

			var lcode = $("<input>").attr({
				"type" : "text"
			}).val(obj[keys[i]].code.toString());
			this._addClass(lcode, "form-control");
			var td3 = $("<td>").append(lcode);

			var ty1 = $("<option>").text("Point").val("point");
			var ty11 = $("<option>").text("MultiPoint").val("multipoint");
			var ty2 = $("<option>").text("LineString").val("linestring");
			var ty22 = $("<option>").text("MultiLineString").val("multilinestring");
			var ty3 = $("<option>").text("Polygon").val("polygon");
			var ty33 = $("<option>").text("MultiPolygon").val("multipolygon");
			var ty4 = $("<option>").text("Text").val("text");
			var gtype = $("<select>").append(ty1).append(ty11).append(ty2).append(ty22).append(ty3).append(ty33).append(ty4).val(
					obj[keys[i]].geom);
			this._addClass(gtype, "form-control");
			var td4 = $("<td>").append(gtype);

			var icon = $("<i>").attr("aria-hidden", true);
			this._addClass(icon, "fa");
			this._addClass(icon, "fa-times");
			var delBtn = $("<button>").append(icon);
			this._addClass(delBtn, "btn");
			this._addClass(delBtn, "btn-default");
			this._addClass(delBtn, "layerdefinition20-del");
			var td5 = $("<td>").append(delBtn);
			var radio = $("<input>").attr({
				"type" : "radio",
				"name" : "layerdefinition20-area"
			}).css({
				"vertical-align" : "-webkit-baseline-middle"
			});
			if (obj[keys[i]].area) {
				$(radio).prop("checked", true);
			} else {
				$(radio).prop("checked", false);
			}
			var td6 = $("<td>").append(radio);
			// var weight = $("<input>").attr({
			// "type" : "number",
			// "min" : 1,
			// "max" : 100
			// }).val(obj[keys[i]].weight);
			// that._addClass(weight, "form-control");
			// var td7 = $("<td>").append(weight);
			var tr = $("<tr>").append(td1).append(td2).append(td3).append(td4).append(td5).append(td6);
			$(that.tbody).append(tr);
		}
	},
	open : function() {
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