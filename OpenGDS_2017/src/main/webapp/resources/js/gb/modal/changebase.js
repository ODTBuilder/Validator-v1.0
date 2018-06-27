/**
 * 베이스맵 변경 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2016. 10
 * @version 0.03
 */
/*
 * ! OpenGDS/Builder 편집도구 레이어 메뉴 JQuery UI selectable, sortable 소스를 참조하여 제작함.
 * required : Widget, :data Selector, scrollParent, Mouse.
 */
var gitbuilder;
if (!gitbuilder)
	gitbuilder = {};
if (!gitbuilder.ui)
	gitbuilder.ui = {};
gitbuilder.ui.ChangeBase = $.widget("gitbuilder.changebase", {
	widnow : undefined,
	now : undefined,
	bases : undefined,
	options : {
		map : undefined,
		layers : undefined,
		appendTo : "body"
	},
	_create : function() {
		this.bases = {
			osm : {
				name : "OpenStreetMap",
				thumb : "GitBuilder-OsmThumbnail",
				layer : new ol.layer.Tile({
					visible : false,
					source : new ol.source.OSM()
				})
			},
			bing : {
				name : "Bing Map",
				thumb : "GitBuilder-BingThumbnail",
				layer : new ol.layer.Tile({
					visible : false,
					preload : Infinity,
					source : new ol.source.BingMaps({
						key : 'AtZS5HHiM9JIaF1cUX-x-zQT_97S8YkWkjxDowNNUGD1D8jWUtgVmdaxsiitNQbo',
						imagerySet : "AerialWithLabels"
					})
				})
			},
			black : {
				name : "Black",
				thumb : "GitBuilder-NoThumbnail",
				layer : undefined
			},
			white : {
				name : "White",
				thumb : "GitBuilder-NoThumbnail",
				layer : undefined
			}
		};
		if ($.isArray(this.options.layers)) {
			for (var i = 0; i < this.options.layers.length; i++) {
				if (this.options.layers[i].hasOwnProperty("value") && this.options.layers[i].hasOwnProperty("name")
						&& this.options.layers[i].hasOwnProperty("layer")) {
					if (typeof this.options.layers[i].value === "string" && typeof this.options.layers[i].name === "string"
							&& this.options.layers[i].layer instanceof ol.layer.Base) {
						if (this.options.layers[i].thumb === undefined) {
							this.options.layers[i].thumb = "GitBuilder-NoThumbnail"
						}
						var obj = {
							name : this.options.layers[i].name,
							thumb : this.options.layers[i].thumb,
							layer : this.options.layers[i].layer
						}
						this.bases[this.options.layers[i].value] = obj;
					}
				}
			}
		}
		var keys = Object.keys(this.bases);
		for (var i = 0; i < keys.length; i++) {
			if (!(keys[i] === "black" || keys[i] === "white")) {
				this.options.map.addLayer(this.bases[keys[i]].layer);
			}
		}
		this.changeLayer("black")
	},
	_init : function() {
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
		htag.text("Base map selection");
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
		var body = $("<div>");
		this._addClass(body, "modal-body");

		var keys = Object.keys(this.bases);
		for (var i = 0; i < keys.length; i++) {
			var radio = $("<input>").attr({
				"type" : "radio",
				"name" : "basemap",
				"value" : keys[i]
			});
			var label = $("<label>").append(radio);
			this._addClass(label, "radio-inline");
			var span = $("<span>").text(this.bases[keys[i]].name);
			label.append(span);

			var heading = $("<div>");
			this._addClass(heading, "panel-heading");

			$(heading).append(label);

			var pBody = $("<div>");
			this._addClass(pBody, "panel-body");

			var img = $("<div>").css("width", "120px").css("height", "80px").css("margin", "0 auto");
			this._addClass(img, this.bases[keys[i]].thumb);

			$(pBody).append(img);

			var pDefault = $("<div>").css("width", "165px").css("display", "inline-block").css("margin", "12px");
			this._addClass(pDefault, "panel");
			this._addClass(pDefault, "panel-primary");

			$(pDefault).append(heading);
			$(pDefault).append(pBody);

			$(body).append(pDefault);
		}
		/*
		 * 
		 * 
		 * body end
		 * 
		 * 
		 */
		var closeBtn = $("<button>").attr({
			"type" : "button",
			"data-dismiss" : "modal"
		});
		this._addClass(closeBtn, "btn");
		this._addClass(closeBtn, "btn-default");
		$(closeBtn).text("Close");

		var okBtn = $("<button>").attr({
			"type" : "button",
			"data-dismiss" : "modal"
		});
		this._addClass(okBtn, "btn");
		this._addClass(okBtn, "btn-primary");
		$(okBtn).text("OK");

		this._on(false, okBtn, {
			click : function(event) {
				if (event.target === okBtn[0]) {
					var val = $(':radio[name="basemap"]:checked').val();
					that.changeLayer(val);
				}
			}
		});

		var footer = $("<div>").append(closeBtn).append(okBtn);
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

		this.window = $("<div>").hide().attr({
			// Setting tabIndex makes the div focusable
			tabIndex : -1,
			role : "dialog",
		}).html(dialog);
		this._addClass(this.window, "modal");
		this._addClass(this.window, "fade");

		this.window.appendTo(this._appendTo());
		this.window.modal({
			backdrop : true,
			keyboard : true,
			show : false,
		});
	},
	open : function() {
		var keys = Object.keys(this.bases);
		for (var i = 0; i < keys.length; i++) {
			if (!(keys[i] === "black" || keys[i] === "white")) {
				if (this.bases[keys[i]].layer.getVisible()) {
					$("input:radio[name='basemap']:radio[value='" + keys[i] + "']").prop("checked", true);
				}
			} else {
				if (this.now === keys[i]) {
					$("input:radio[name='basemap']:radio[value='" + keys[i] + "']").prop("checked", true);
				}
			}
		}
		this.window.modal('show');
	},
	close : function() {
		this.window.modal('hide');
	},
	changeLayer : function(value) {
		var keys = Object.keys(this.bases);
		for (var i = 0; i < keys.length; i++) {
			if (value === keys[i]) {
				if (value === "black") {
					var div = this.options.map.getTarget();
					$("#" + div).css({
						"background-color" : "#000"
					});
					this.now = value;
				} else if (value === "white") {
					var div = this.options.map.getTarget();
					$("#" + div).css({
						"background-color" : "#fff"
					});
					this.now = value;
				} else {
					this.bases[keys[i]].layer.setVisible(true);
					this.now = value;
				}
			} else {
				if (!(keys[i] === "black" || keys[i] === "white")) {
					this.bases[keys[i]].layer.setVisible(false);
				}
			}
		}
	},
	destroy : function() {
		this.element.off("click");
		$(this.window).find("button").off("click");

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