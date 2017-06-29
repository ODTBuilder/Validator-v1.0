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
gitbuilder.ui.WeightDefinition = $.widget("gitbuilder.weightdefinition", {
	widnow : undefined,
	weightDef : undefined,
	layerDef : undefined,
	optDef : undefined,
	tbody : undefined,
	message : undefined,
	file : undefined,
	options : {
		definition : undefined,
		layerDefinition : undefined,
		optionDefinition : undefined,
		appendTo : "body"
	},
	_create : function() {
		this.setDefinition(this.options.definition);
		if (typeof this.options.layerDefinition === "function") {
			this.setLayerDef(this.options.layerDefinition());
		} else {
			this.setLayerDef(this.options.layerDefinition);
		}
		if (typeof this.options.optionDefinition === "function") {
			this.setOptDef(this.options.optionDefinition());
		} else {
			this.setOptDef(this.options.optionDefinition);
		}

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
		htag.text("Layer Weight Definition");
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
		var tdhead1 = $("<td>").addClass("col-md-1").text("#");
		var tdhead2 = $("<td>").addClass("col-md-3").text("Layer Name");
		var tdhead3 = $("<td>").addClass("col-md-4").text("Layer Code");
		var tdhead4 = $("<td>").addClass("col-md-2").text("Geometry Type");
		var tdhead7 = $("<td>").addClass("col-md-2").text("Weight");
		var trhead = $("<tr>").append(tdhead1).append(tdhead2).append(tdhead3).append(tdhead4).append(tdhead7);
		var thead = $("<thead>").append(trhead);
		that.tbody = $("<tbody>");
		var tb = $("<table>").css({
			"table-layout" : "fixed"
		}).append(thead).append(that.tbody);
		this._addClass(tb, "table");
		this._addClass(tb, "table-striped");
		this._addClass(tb, "text-center");
		this.update();

		var upper = $("<div>").css({
			"overflow-y" : "auto",
			"height" : "400px"
		}).append(tb);
		this.message = $("<p>").css({
			"display" : "none"
		});
		this._addClass(this.message, "text-danger");
		var mid = $("<div>").append(this.message);
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
		this.setDefinition(this.options.definition);
		if (typeof this.options.layerDefinition === "function") {
			this.setLayerDef(this.options.layerDefinition());
		} else {
			this.setLayerDef(this.options.layerDefinition);
		}
		if (typeof this.options.optionDefinition === "function") {
			this.setOptDef(this.options.optionDefinition());
		} else {
			this.setOptDef(this.options.optionDefinition);
		}
	},
	downloadSetting : function() {
		var def = this.getDefinitionForm();
		if (!!def) {
			var setting = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(def));
			var anchor = $("<a>").attr({
				"href" : setting,
				"download" : "weight_setting.json"
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
		var tWeight = 0;
		for (var i = 0; i < children.length; i++) {
			var wei = parseInt($(children[i]).find("td:eq(4)>input[type=number]").val());
			tWeight = tWeight + wei;
			if (wei < 1) {
				flag = false;
				$(that.message).css({
					"display" : "block"
				}).text("Each weight must be over than 0%");
			}
			$(children[i]).removeClass("danger");

			var wVal = parseInt($(children[i]).find("td:eq(4)>input[type=number]").val());
			def[$(children[i]).find("td:eq(1)").text()] = wVal;
		}
		if (tWeight !== 100) {
			flag = false;
			$(that.message).css({
				"display" : "block"
			}).text("Total weight must be 100%");
		}
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
		this.weightDef = $.extend({}, obj);
	},
	getDefinition : function() {
		return this.weightDef;
	},
	getOptDef : function() {
		return this.optDef;
	},
	setOptDef : function(def) {
		this.optDef = $.extend({}, def);
	},
	getLayerDef : function() {
		return this.layerDef;
	},
	setLayerDef : function(def) {
		this.layerDef = $.extend({}, def);
	},
	update : function(obj) {
		var that = this;

		$(this.tbody).empty();
		$(that.message).css({
			"display" : "none"
		});
		if (typeof this.options.layerDefinition === "function") {
			this.setLayerDef(this.options.layerDefinition());
		} else {
			this.setLayerDef(this.options.layerDefinition);
		}
		if (typeof this.options.optionDefinition === "function") {
			this.setOptDef(this.options.optionDefinition());
		} else {
			this.setOptDef(this.options.optionDefinition);
		}

		var okeys = Object.keys(this.getOptDef());
		var wkeys = [];
		for (var i = 0; i < okeys.length; i++) {
			var keys = Object.keys(this.getOptDef()[okeys[i]]);
			if (keys.length > 0) {
				wkeys.push(okeys[i]);
			}
		}

		for (var i = 0; i < wkeys.length; i++) {
			var no = $("<span>").css({
				"vertical-align" : "-webkit-baseline-middle"
			}).text((i + 1));
			var td1 = $("<td>").append(no);

			var td2 = $("<td>").text(wkeys[i]);

			var td3 = $("<td>").css({
				"text-overflow" : "ellipsis",
				"overflow" : "hidden"
			}).text(this.getLayerDef()[wkeys[i]].code.toString());

			var td4 = $("<td>").text(this.getLayerDef()[wkeys[i]].geom);

			var weight = $("<input>").attr({
				"type" : "number",
				"min" : 1,
				"max" : 100
			});
			if (!obj) {
				$(weight).val(this.weightDef.hasOwnProperty(wkeys[i]) ? this.weightDef[wkeys[i]] : "0");
			} else {
				$(weight).val(obj.hasOwnProperty(wkeys[i]) ? obj[wkeys[i]] : "0");
			}

			that._addClass(weight, "form-control");
			var td7 = $("<td>").append(weight);
			var tr = $("<tr>").append(td1).append(td2).append(td3).append(td4).append(td7);
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