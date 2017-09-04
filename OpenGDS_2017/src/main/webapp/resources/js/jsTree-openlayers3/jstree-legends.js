/**
 * ### legends plugin
 * 
 * This plugin renders legend icons in front of each node, making multiple
 * selection much easier. It also supports tri-state behavior, meaning that if a
 * node has a few of its children checked it will be rendered as undetermined,
 * and state will be propagated up.
 */

var _legend = document.createElement('legend');
_legend.className = 'jstreeol3-icon jstreeol3-legend';
_legend.setAttribute('role', 'presentation');
/**
 * stores all defaults for the legend plugin
 * 
 * @name $.jstreeol3.defaults.legend
 * @plugin legend
 */
$.jstreeol3.defaults.legend = {
	/**
	 * a boolean indicating if legendes should be visible (can be changed at a
	 * later time using `show_legendes()` and `hide_legendes`). Defaults to
	 * `true`.
	 * 
	 * @name $.jstreeol3.defaults.legend.visible
	 * @plugin legend
	 */
	visible : true,
	/**
	 * a boolean indicating if legendes should cascade down and have an
	 * undetermined state. Defaults to `true`.
	 * 
	 * @name $.jstreeol3.defaults.legend.three_state
	 * @plugin legend
	 */
	three_state : true,
	/**
	 * a boolean indicating if clicking anywhere on the node should act as
	 * clicking on the legend. Defaults to `true`.
	 * 
	 * @name $.jstreeol3.defaults.legend.whole_node
	 * @plugin legend
	 */
	whole_node : true,
	/**
	 * a boolean indicating if the selected style of a node should be kept, or
	 * removed. Defaults to `true`.
	 * 
	 * @name $.jstreeol3.defaults.legend.keep_selected_style
	 * @plugin legend
	 */
	keep_selected_style : true,
	/**
	 * This setting controls how cascading and undetermined nodes are applied.
	 * If 'up' is in the string - cascading up is enabled, if 'down' is in the
	 * string - cascading down is enabled, if 'undetermined' is in the string -
	 * undetermined nodes will be used. If `three_state` is set to `true` this
	 * setting is automatically set to 'up+down+undetermined'. Defaults to ''.
	 * 
	 * @name $.jstreeol3.defaults.legend.cascade
	 * @plugin legend
	 */
	cascade : '',
	/**
	 * This setting controls if legend are bound to the general tree selection
	 * or to an internal array maintained by the legend plugin. Defaults to
	 * `true`, only set to `false` if you know exactly what you are doing.
	 * 
	 * @name $.jstreeol3.defaults.legend.tie_selection
	 * @plugin legend
	 */
	tie_selection : true
};
$.jstreeol3.plugins.legend = function(options, parent) {
	this.bind = function() {
		parent.bind.call(this);
		this._data.legend.uto = false;
		this._data.legend.selected = [];
		if (this.settings.legend.three_state) {
			this.settings.legend.cascade = 'up+down+undetermined';
		}
		this.element.on("init.jstreeol3", $.proxy(function() {
			this._data.legend.visible = this.settings.legend.visible;
			if (!this.settings.legend.keep_selected_style) {
				this.element.addClass('jstreeol3-legend-no-clicked');
			}
			if (this.settings.legend.tie_selection) {
				this.element.addClass('jstreeol3-legend-selection');
			}
		}, this)).on("loading.jstreeol3", $.proxy(function() {
			this[this._data.legend.visible ? 'show_legendes' : 'hide_legendes']();
		}, this));
		
		if (!this.settings.legend.tie_selection) {
			this.element.on('model.jstreeol3', $.proxy(function(e, data) {
				var m = this._model.data, p = m[data.parent], dpc = data.nodes, i, j;
				for (i = 0, j = dpc.length; i < j; i++) {
					m[dpc[i]].state.checked = m[dpc[i]].state.checked
							|| (m[dpc[i]].original && m[dpc[i]].original.state && m[dpc[i]].original.state.checked);
					if (m[dpc[i]].state.checked) {
						this._data.legend.selected.push(dpc[i]);
					}
				}
			}, this));
		}
	};
	/**
	 * set the undetermined state where and if necessary. Used internally.
	 * 
	 * @private
	 * @name _undetermined()
	 * @plugin legend
	 */
	this._undetermined = function() {
		if (this.element === null) {
			return;
		}
		var i, j, k, l, o = {}, m = this._model.data, t = this.settings.legend.tie_selection, s = this._data[t ? 'core' : 'legend'].selected, p = [], tt = this;
		for (i = 0, j = s.length; i < j; i++) {
			if (m[s[i]] && m[s[i]].parents) {
				for (k = 0, l = m[s[i]].parents.length; k < l; k++) {
					if (o[m[s[i]].parents[k]] !== undefined) {
						break;
					}
					if (m[s[i]].parents[k] !== $.jstreeol3.root) {
						o[m[s[i]].parents[k]] = true;
						p.push(m[s[i]].parents[k]);
					}
				}
			}
		}
		// attempt for server side undetermined state
		this.element.find('.jstreeol3-closed').not(':has(.jstreeol3-children)').each(
				function() {
					var tmp = tt.get_node(this), tmp2;
					if (!tmp.state.loaded) {
						if (tmp.original && tmp.original.state && tmp.original.state.undetermined
								&& tmp.original.state.undetermined === true) {
							if (o[tmp.id] === undefined && tmp.id !== $.jstreeol3.root) {
								o[tmp.id] = true;
								p.push(tmp.id);
							}
							for (k = 0, l = tmp.parents.length; k < l; k++) {
								if (o[tmp.parents[k]] === undefined && tmp.parents[k] !== $.jstreeol3.root) {
									o[tmp.parents[k]] = true;
									p.push(tmp.parents[k]);
								}
							}
						}
					} else {
						for (i = 0, j = tmp.children_d.length; i < j; i++) {
							tmp2 = m[tmp.children_d[i]];
							if (!tmp2.state.loaded && tmp2.original && tmp2.original.state && tmp2.original.state.undetermined
									&& tmp2.original.state.undetermined === true) {
								if (o[tmp2.id] === undefined && tmp2.id !== $.jstreeol3.root) {
									o[tmp2.id] = true;
									p.push(tmp2.id);
								}
								for (k = 0, l = tmp2.parents.length; k < l; k++) {
									if (o[tmp2.parents[k]] === undefined && tmp2.parents[k] !== $.jstreeol3.root) {
										o[tmp2.parents[k]] = true;
										p.push(tmp2.parents[k]);
									}
								}
							}
						}
					}
				});

		this.element.find('.jstreeol3-undetermined').removeClass('jstreeol3-undetermined');
		for (i = 0, j = p.length; i < j; i++) {
			if (!m[p[i]].state[t ? 'selected' : 'checked']) {
				s = this.get_node(p[i], true);
				if (s && s.length) {
					s.children('.jstreeol3-anchor').children('.jstreeol3-legend').addClass('jstreeol3-undetermined');
				}
			}
		}
	};
	this.redraw_node = function(obj, deep, is_callback, force_render) {
		obj = parent.redraw_node.apply(this, arguments);
		if (obj) {
			var i, j, tmp = null, icon = null;
			for (i = 0, j = obj.childNodes.length; i < j; i++) {
				if (obj.childNodes[i] && obj.childNodes[i].className && obj.childNodes[i].className.indexOf("jstreeol3-anchor") !== -1) {
					tmp = obj.childNodes[i];
					break;
				}
			}
			if (tmp) {
				if (!this.settings.legend.tie_selection && this._model.data[obj.id].state.checked) {
					tmp.className += ' jstreeol3-checked';
				}
				icon = _legend.cloneNode(false);
				if (this._model.data[obj.id].state.legend_disabled) {
					icon.className += ' jstreeol3-legend-disabled';
				}
				tmp.insertBefore(icon, tmp.childNodes[0]);
			}
		}
		if (!is_callback && this.settings.legend.cascade.indexOf('undetermined') !== -1) {
			if (this._data.legend.uto) {
				clearTimeout(this._data.legend.uto);
			}
			this._data.legend.uto = setTimeout($.proxy(this._undetermined, this), 50);
		}
		return obj;
	};
	/**
	 * show the node legend icons
	 * 
	 * @name show_legendes()
	 * @plugin legend
	 */
	this.show_legendes = function() {
		this._data.core.themes.legendes = true;
		this.get_container_ul().removeClass("jstreeol3-no-legendes");
	};
	/**
	 * hide the node legend icons
	 * 
	 * @name hide_legendes()
	 * @plugin legend
	 */
	this.hide_legendes = function() {
		this._data.core.themes.legendes = false;
		this.get_container_ul().addClass("jstreeol3-no-legendes");
	};
	/**
	 * toggle the node icons
	 * 
	 * @name toggle_legendes()
	 * @plugin legend
	 */
	this.toggle_legendes = function() {
		if (this._data.core.themes.legendes) {
			this.hide_legendes();
		} else {
			this.show_legendes();
		}
	};
	/**
	 * checks if a node is in an undetermined state
	 * 
	 * @name is_undetermined(obj)
	 * @param {mixed}
	 *            obj
	 * @return {Boolean}
	 */
	this.is_undetermined = function(obj) {
		obj = this.get_node(obj);
		var s = this.settings.legend.cascade, i, j, t = this.settings.legend.tie_selection, d = this._data[t ? 'core' : 'legend'].selected, m = this._model.data;
		if (!obj || obj.state[t ? 'selected' : 'checked'] === true || s.indexOf('undetermined') === -1
				|| (s.indexOf('down') === -1 && s.indexOf('up') === -1)) {
			return false;
		}
		if (!obj.state.loaded && obj.original.state.undetermined === true) {
			return true;
		}
		for (i = 0, j = obj.children_d.length; i < j; i++) {
			if ($.inArray(obj.children_d[i], d) !== -1
					|| (!m[obj.children_d[i]].state.loaded && m[obj.children_d[i]].original.state.undetermined)) {
				return true;
			}
		}
		return false;
	};
	/**
	 * disable a node's legend
	 * 
	 * @name disable_legend(obj)
	 * @param {mixed}
	 *            obj an array can be used too
	 * @trigger disable_legend.jstreeol3
	 * @plugin legend
	 */
	this.disable_legend = function(obj) {
		var t1, t2, dom;
		if ($.isArray(obj)) {
			obj = obj.slice();
			for (t1 = 0, t2 = obj.length; t1 < t2; t1++) {
				this.disable_legend(obj[t1]);
			}
			return true;
		}
		obj = this.get_node(obj);
		if (!obj || obj.id === $.jstreeol3.root) {
			return false;
		}
		dom = this.get_node(obj, true);
		if (!obj.state.legend_disabled) {
			obj.state.legend_disabled = true;
			if (dom && dom.length) {
				dom.children('.jstreeol3-anchor').children('.jstreeol3-legend').addClass('jstreeol3-legend-disabled');
			}
			/**
			 * triggered when an node's legend is disabled
			 * 
			 * @event
			 * @name disable_legend.jstreeol3
			 * @param {Object}
			 *            node
			 * @plugin legend
			 */
			this.trigger('disable_legend', {
				'node' : obj
			});
		}
	};
	/**
	 * enable a node's legend
	 * 
	 * @name disable_legend(obj)
	 * @param {mixed}
	 *            obj an array can be used too
	 * @trigger enable_legend.jstreeol3
	 * @plugin legend
	 */
	this.enable_legend = function(obj) {
		var t1, t2, dom;
		if ($.isArray(obj)) {
			obj = obj.slice();
			for (t1 = 0, t2 = obj.length; t1 < t2; t1++) {
				this.enable_legend(obj[t1]);
			}
			return true;
		}
		obj = this.get_node(obj);
		if (!obj || obj.id === $.jstreeol3.root) {
			return false;
		}
		dom = this.get_node(obj, true);
		if (obj.state.legend_disabled) {
			obj.state.legend_disabled = false;
			if (dom && dom.length) {
				dom.children('.jstreeol3-anchor').children('.jstreeol3-legend').removeClass('jstreeol3-legend-disabled');
			}
			/**
			 * triggered when an node's legend is enabled
			 * 
			 * @event
			 * @name enable_legend.jstreeol3
			 * @param {Object}
			 *            node
			 * @plugin legend
			 */
			this.trigger('enable_legend', {
				'node' : obj
			});
		}
	};

	this.activate_node = function(obj, e) {
		if ($(e.target).hasClass('jstreeol3-legend-disabled')) {
			return false;
		}
		if (this.settings.legend.tie_selection && (this.settings.legend.whole_node || $(e.target).hasClass('jstreeol3-legend'))) {
			e.ctrlKey = true;
		}
		if (this.settings.legend.tie_selection || (!this.settings.legend.whole_node && !$(e.target).hasClass('jstreeol3-legend'))) {
			return parent.activate_node.call(this, obj, e);
		}
		if (this.is_disabled(obj)) {
			return false;
		}
		if (this.is_checked(obj)) {
			this.uncheck_node(obj, e);
		} else {
			this.check_node(obj, e);
		}
		this.trigger('activate_node', {
			'node' : this.get_node(obj)
		});
	};

	/**
	 * check a node (only if tie_selection in legend settings is false,
	 * otherwise select_node will be called internally)
	 * 
	 * @name check_node(obj)
	 * @param {mixed}
	 *            obj an array can be used to check multiple nodes
	 * @trigger check_node.jstreeol3
	 * @plugin legend
	 */
	this.check_node = function(obj, e) {
		if (this.settings.legend.tie_selection) {
			return this.select_node(obj, false, true, e);
		}
		var dom, t1, t2, th;
		if ($.isArray(obj)) {
			obj = obj.slice();
			for (t1 = 0, t2 = obj.length; t1 < t2; t1++) {
				this.check_node(obj[t1], e);
			}
			return true;
		}
		obj = this.get_node(obj);
		if (!obj || obj.id === $.jstreeol3.root) {
			return false;
		}
		dom = this.get_node(obj, true);
		if (!obj.state.checked) {
			obj.state.checked = true;
			this._data.legend.selected.push(obj.id);
			if (dom && dom.length) {
				dom.children('.jstreeol3-anchor').addClass('jstreeol3-checked');
			}
			/**
			 * triggered when an node is checked (only if tie_selection in
			 * legend settings is false)
			 * 
			 * @event
			 * @name check_node.jstreeol3
			 * @param {Object}
			 *            node
			 * @param {Array}
			 *            selected the current selection
			 * @param {Object}
			 *            event the event (if any) that triggered this
			 *            check_node
			 * @plugin legend
			 */
			this.trigger('check_node', {
				'node' : obj,
				'selected' : this._data.legend.selected,
				'event' : e
			});
		}
	};
	/**
	 * uncheck a node (only if tie_selection in legend settings is false,
	 * otherwise deselect_node will be called internally)
	 * 
	 * @name uncheck_node(obj)
	 * @param {mixed}
	 *            obj an array can be used to uncheck multiple nodes
	 * @trigger uncheck_node.jstreeol3
	 * @plugin legend
	 */
	this.uncheck_node = function(obj, e) {
		if (this.settings.legend.tie_selection) {
			return this.deselect_node(obj, false, e);
		}
		var t1, t2, dom;
		if ($.isArray(obj)) {
			obj = obj.slice();
			for (t1 = 0, t2 = obj.length; t1 < t2; t1++) {
				this.uncheck_node(obj[t1], e);
			}
			return true;
		}
		obj = this.get_node(obj);
		if (!obj || obj.id === $.jstreeol3.root) {
			return false;
		}
		dom = this.get_node(obj, true);
		if (obj.state.checked) {
			obj.state.checked = false;
			this._data.legend.selected = $.vakata.array_remove_item(this._data.legend.selected, obj.id);
			if (dom.length) {
				dom.children('.jstreeol3-anchor').removeClass('jstreeol3-checked');
			}
			/**
			 * triggered when an node is unchecked (only if tie_selection in
			 * legend settings is false)
			 * 
			 * @event
			 * @name uncheck_node.jstreeol3
			 * @param {Object}
			 *            node
			 * @param {Array}
			 *            selected the current selection
			 * @param {Object}
			 *            event the event (if any) that triggered this
			 *            uncheck_node
			 * @plugin legend
			 */
			this.trigger('uncheck_node', {
				'node' : obj,
				'selected' : this._data.legend.selected,
				'event' : e
			});
		}
	};
	/**
	 * checks all nodes in the tree (only if tie_selection in legend settings is
	 * false, otherwise select_all will be called internally)
	 * 
	 * @name check_all()
	 * @trigger check_all.jstreeol3, changed.jstreeol3
	 * @plugin legend
	 */
	this.check_all = function() {
		if (this.settings.legend.tie_selection) {
			return this.select_all();
		}
		var tmp = this._data.legend.selected.concat([]), i, j;
		this._data.legend.selected = this._model.data[$.jstreeol3.root].children_d.concat();
		for (i = 0, j = this._data.legend.selected.length; i < j; i++) {
			if (this._model.data[this._data.legend.selected[i]]) {
				this._model.data[this._data.legend.selected[i]].state.checked = true;
			}
		}
		this.redraw(true);
		/**
		 * triggered when all nodes are checked (only if tie_selection in legend
		 * settings is false)
		 * 
		 * @event
		 * @name check_all.jstreeol3
		 * @param {Array}
		 *            selected the current selection
		 * @plugin legend
		 */
		this.trigger('check_all', {
			'selected' : this._data.legend.selected
		});
	};
	/**
	 * uncheck all checked nodes (only if tie_selection in legend settings is
	 * false, otherwise deselect_all will be called internally)
	 * 
	 * @name uncheck_all()
	 * @trigger uncheck_all.jstreeol3
	 * @plugin legend
	 */
	this.uncheck_all = function() {
		if (this.settings.legend.tie_selection) {
			return this.deselect_all();
		}
		var tmp = this._data.legend.selected.concat([]), i, j;
		for (i = 0, j = this._data.legend.selected.length; i < j; i++) {
			if (this._model.data[this._data.legend.selected[i]]) {
				this._model.data[this._data.legend.selected[i]].state.checked = false;
			}
		}
		this._data.legend.selected = [];
		this.element.find('.jstreeol3-checked').removeClass('jstreeol3-checked');
		/**
		 * triggered when all nodes are unchecked (only if tie_selection in
		 * legend settings is false)
		 * 
		 * @event
		 * @name uncheck_all.jstreeol3
		 * @param {Object}
		 *            node the previous selection
		 * @param {Array}
		 *            selected the current selection
		 * @plugin legend
		 */
		this.trigger('uncheck_all', {
			'selected' : this._data.legend.selected,
			'node' : tmp
		});
	};
	/**
	 * checks if a node is checked (if tie_selection is on in the settings this
	 * function will return the same as is_selected)
	 * 
	 * @name is_checked(obj)
	 * @param {mixed}
	 *            obj
	 * @return {Boolean}
	 * @plugin legend
	 */
	this.is_checked = function(obj) {
		if (this.settings.legend.tie_selection) {
			return this.is_selected(obj);
		}
		obj = this.get_node(obj);
		if (!obj || obj.id === $.jstreeol3.root) {
			return false;
		}
		return obj.state.checked;
	};
	/**
	 * get an array of all checked nodes (if tie_selection is on in the settings
	 * this function will return the same as get_selected)
	 * 
	 * @name get_checked([full])
	 * @param {mixed}
	 *            full if set to `true` the returned array will consist of the
	 *            full node objects, otherwise - only IDs will be returned
	 * @return {Array}
	 * @plugin legend
	 */
	this.get_checked = function(full) {
		if (this.settings.legend.tie_selection) {
			return this.get_selected(full);
		}
		return full ? $.map(this._data.legend.selected, $.proxy(function(i) {
			return this.get_node(i);
		}, this)) : this._data.legend.selected;
	};
	/**
	 * get an array of all top level checked nodes (ignoring children of checked
	 * nodes) (if tie_selection is on in the settings this function will return
	 * the same as get_top_selected)
	 * 
	 * @name get_top_checked([full])
	 * @param {mixed}
	 *            full if set to `true` the returned array will consist of the
	 *            full node objects, otherwise - only IDs will be returned
	 * @return {Array}
	 * @plugin legend
	 */
	this.get_top_checked = function(full) {
		if (this.settings.legend.tie_selection) {
			return this.get_top_selected(full);
		}
		var tmp = this.get_checked(true), obj = {}, i, j, k, l;
		for (i = 0, j = tmp.length; i < j; i++) {
			obj[tmp[i].id] = tmp[i];
		}
		for (i = 0, j = tmp.length; i < j; i++) {
			for (k = 0, l = tmp[i].children_d.length; k < l; k++) {
				if (obj[tmp[i].children_d[k]]) {
					delete obj[tmp[i].children_d[k]];
				}
			}
		}
		tmp = [];
		for (i in obj) {
			if (obj.hasOwnProperty(i)) {
				tmp.push(i);
			}
		}
		return full ? $.map(tmp, $.proxy(function(i) {
			return this.get_node(i);
		}, this)) : tmp;
	};
	/**
	 * get an array of all bottom level checked nodes (ignoring selected
	 * parents) (if tie_selection is on in the settings this function will
	 * return the same as get_bottom_selected)
	 * 
	 * @name get_bottom_checked([full])
	 * @param {mixed}
	 *            full if set to `true` the returned array will consist of the
	 *            full node objects, otherwise - only IDs will be returned
	 * @return {Array}
	 * @plugin legend
	 */
	this.get_bottom_checked = function(full) {
		if (this.settings.legend.tie_selection) {
			return this.get_bottom_selected(full);
		}
		var tmp = this.get_checked(true), obj = [], i, j;
		for (i = 0, j = tmp.length; i < j; i++) {
			if (!tmp[i].children.length) {
				obj.push(tmp[i].id);
			}
		}
		return full ? $.map(obj, $.proxy(function(i) {
			return this.get_node(i);
		}, this)) : obj;
	};
	this.load_node = function(obj, callback) {
		var k, l, i, j, c, tmp;
		if (!$.isArray(obj) && !this.settings.legend.tie_selection) {
			tmp = this.get_node(obj);
			if (tmp && tmp.state.loaded) {
				for (k = 0, l = tmp.children_d.length; k < l; k++) {
					if (this._model.data[tmp.children_d[k]].state.checked) {
						c = true;
						this._data.legend.selected = $.vakata.array_remove_item(this._data.legend.selected, tmp.children_d[k]);
					}
				}
			}
		}
		return parent.load_node.apply(this, arguments);
	};
	this.get_state = function() {
		var state = parent.get_state.apply(this, arguments);
		if (this.settings.legend.tie_selection) {
			return state;
		}
		state.legend = this._data.legend.selected.slice();
		return state;
	};
	this.set_state = function(state, callback) {
		var res = parent.set_state.apply(this, arguments);
		if (res && state.legend) {
			if (!this.settings.legend.tie_selection) {
				this.uncheck_all();
				var _this = this;
				$.each(state.legend, function(i, v) {
					_this.check_node(v);
				});
			}
			delete state.legend;
			this.set_state(state, callback);
			return false;
		}
		return res;
	};
	this.refresh = function(skip_loading, forget_state) {
		if (!this.settings.legend.tie_selection) {
			this._data.legend.selected = [];
		}
		return parent.refresh.apply(this, arguments);
	};
};