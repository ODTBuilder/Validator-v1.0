/**
 * ### visibility plugin
 * 
 * This plugin renders visibility icons in front of each node, making multiple
 * selection much easier. It also supports tri-state behavior, meaning that if a
 * node has a few of its children checked it will be rendered as undetermined,
 * and state will be propagated up.
 */

var _v = document.createElement('I');
_v.className = 'jstreeol3-icon jstreeol3-visibility';
_v.setAttribute('role', 'presentation');
/**
 * stores all defaults for the visibility plugin
 * 
 * @name $.jstreeol3.defaults.visibility
 * @plugin visibility
 */
$.jstreeol3.defaults.visibility = {
	/**
	 * a boolean indicating if visibilityes should be visible (can be changed at
	 * a later time using `show_visibilityes()` and `hide_visibilityes`).
	 * Defaults to `true`.
	 * 
	 * @name $.jstreeol3.defaults.visibility.visible
	 * @plugin visibility
	 */
	visible : true,
	/**
	 * a boolean indicating if visibilityes should cascade down and have an
	 * undetermined state. Defaults to `true`.
	 * 
	 * @name $.jstreeol3.defaults.visibility.three_state
	 * @plugin visibility
	 */
	three_state : true,
	/**
	 * a boolean indicating if clicking anywhere on the node should act as
	 * clicking on the visibility. Defaults to `true`.
	 * 
	 * @name $.jstreeol3.defaults.visibility.whole_node
	 * @plugin visibility
	 */
	whole_node : false,
	/**
	 * a boolean indicating if the selected style of a node should be kept, or
	 * removed. Defaults to `true`.
	 * 
	 * @name $.jstreeol3.defaults.visibility.keep_selected_style
	 * @plugin visibility
	 */
	keep_selected_style : true,
	/**
	 * This setting controls how cascading and undetermined nodes are applied.
	 * If 'up' is in the string - cascading up is enabled, if 'down' is in the
	 * string - cascading down is enabled, if 'undetermined' is in the string -
	 * undetermined nodes will be used. If `three_state` is set to `true` this
	 * setting is automatically set to 'up+down+undetermined'. Defaults to ''.
	 * 
	 * @name $.jstreeol3.defaults.visibility.cascade
	 * @plugin visibility
	 */
	cascade : '',
	/**
	 * This setting controls if visibility are bound to the general tree
	 * selection or to an internal array maintained by the visibility plugin.
	 * Defaults to `true`, only set to `false` if you know exactly what you are
	 * doing.
	 * 
	 * @name $.jstreeol3.defaults.visibility.tie_selection
	 * @plugin visibility
	 */
	tie_selection : false
};
$.jstreeol3.plugins.visibility = function(options, parent) {
	this.bind = function() {
		parent.bind.call(this);
		this._data.visibility.uto = false;
		this._data.visibility.hiding = [];
		if (this.settings.visibility.three_state) {
			this.settings.visibility.cascade = 'up+down+undetermined';
		}
		this.element.on("init.jstreeol3", $.proxy(function() {
			this._data.visibility.visible = this.settings.visibility.visible;
			if (!this.settings.visibility.keep_selected_style) {
				this.element.addClass('jstreeol3-visibility-no-clicked');
			}
			// if (this.settings.visibility.tie_selection) {
			// this.element.addClass('jstreeol3-visibility-selection');
			// }
		}, this)).on("loading.jstreeol3", $.proxy(function() {
			this[this._data.visibility.visible ? 'show_visibility' : 'hide_visibility']();
		}, this));
		if (this.settings.visibility.cascade.indexOf('undetermined') !== -1) {
			this.element
					.on(
							'changed.jstreeol3 uncheck_node.jstreeol3 check_node.jstreeol3 uncheck_all.jstreeol3 check_all.jstreeol3 move_node.jstreeol3 copy_node.jstreeol3 redraw.jstreeol3 open_node.jstreeol3',
							$.proxy(function() {
								// only if undetermined is
								// in
								// setting
								if (this._data.visibility.uto) {
									clearTimeout(this._data.visibility.uto);
								}
								this._data.visibility.uto = setTimeout($.proxy(this._undetermined, this), 50);
							}, this));
		}
		// if (!this.settings.visibility.tie_selection) {
		this.element.on('model.jstreeol3', $.proxy(function(e, data) {
			var m = this._model.data, p = m[data.parent], dpc = data.nodes, i, j;
			for (i = 0, j = dpc.length; i < j; i++) {
				m[dpc[i]].state.hiding = m[dpc[i]].state.hiding
						|| (m[dpc[i]].original && m[dpc[i]].original.state && m[dpc[i]].original.state.hiding);
				if (m[dpc[i]].state.hiding) {
					this._data.visibility.hiding.push(dpc[i]);
				}
			}
		}, this));
		// }
		if (this.settings.visibility.cascade.indexOf('up') !== -1 || this.settings.visibility.cascade.indexOf('down') !== -1) {
			this.element
					.on(
							'model.jstreeol3',
							$
									.proxy(
											function(e, data) {
												var m = this._model.data, p = m[data.parent], dpc = data.nodes, chd = [], c, i, j, k, l, tmp, s = this.settings.visibility.cascade;

												if (s.indexOf('down') !== -1) {
													// apply down
													if (p.state["hiding"]) {
														for (i = 0, j = dpc.length; i < j; i++) {
															m[dpc[i]].state["hiding"] = true;
														}
														// this._data[t ? 'core'
														// :
														// 'visibility'].selected
														// = this._data[t ?
														// 'core' :
														// 'visibility'].selected
														// .concat(dpc);
													} else {
														for (i = 0, j = dpc.length; i < j; i++) {
															if (m[dpc[i]].state["hiding"]) {
																for (k = 0, l = m[dpc[i]].children_d.length; k < l; k++) {
																	m[m[dpc[i]].children_d[k]].state["hiding"] = true;
																}
																// this._data[t
																// ? 'core' :
																// 'visibility'].selected
																// =
																// this._data[t
																// ? 'core'
																// :
																// 'visibility'].selected.concat(m[dpc[i]].children_d);
															}
														}
													}
												}

												if (s.indexOf('up') !== -1) {
													// apply up
													for (i = 0, j = p.children_d.length; i < j; i++) {
														if (!m[p.children_d[i]].children.length) {
															chd.push(m[p.children_d[i]].parent);
														}
													}
													chd = $.vakata.array_unique(chd);
													for (k = 0, l = chd.length; k < l; k++) {
														p = m[chd[k]];
														while (p && p.id !== $.jstreeol3.root) {
															c = 0;
															for (i = 0, j = p.children.length; i < j; i++) {
																c += m[p.children[i]].state["hiding"];
															}
															if (c === j) {
																p.state["hiding"] = true;
																// this._data[t
																// ? 'core' :
																// 'visibility'].selected.push(p.id);
																tmp = this.get_node(p, true);
																if (tmp && tmp.length) {
																	tmp.attr('aria-selected', true).children('.jstreeol3-anchor').addClass(
																			'jstreeol3-checked');
																	var nobj = this.get_node(p);
																	this._updateTileLayer(nobj);
																}
															} else {
																break;
															}
															p = this.get_node(p.parent);
														}
													}
												}

												this._data['visibility'].hiding = $.vakata.array_unique(this._data['visibility'].hiding);
											}, this))
					.on(
							'check_node.jstreeol3',
							$.proxy(
									function(e, data) {
										var obj = data.node, m = this._model.data, par = this.get_node(obj.parent), dom = this.get_node(
												obj, true), i, j, c, tmp, s = this.settings.visibility.cascade;
										// t =
										// this.settings.visibility.tie_selection,
										var sel = {}, cur = this._data['visibility'].hiding;

										for (i = 0, j = cur.length; i < j; i++) {
											sel[cur[i]] = true;
										}
										// apply down
										if (s.indexOf('down') !== -1) {
											// this._data[ t
											// ? 'core' :
											// 'visibility'
											// ].selected =
											// $.vakata.array_unique(this._data[
											// t ? 'core' :
											// 'visibility'
											// ].selected.concat(obj.children_d));
											for (i = 0, j = obj.children_d.length; i < j; i++) {
												sel[obj.children_d[i]] = true;
												tmp = m[obj.children_d[i]];
												tmp.state["hiding"] = true;
												if (tmp && tmp.original && tmp.original.state && tmp.original.state.undetermined) {
													tmp.original.state.undetermined = false;
												}
											}
										}

										// apply up
										if (s.indexOf('up') !== -1) {
											while (par && par.id !== $.jstreeol3.root) {
												c = 0;
												for (i = 0, j = par.children.length; i < j; i++) {
													c += m[par.children[i]].state["hiding"];
												}
												if (c === j) {
													par.state["hiding"] = true;
													sel[par.id] = true;
													// this._data[
													// t ?
													// 'core'
													// :
													// 'visibility'
													// ].selected.push(par.id);
													tmp = this.get_node(par, true);
													if (tmp && tmp.length) {
														tmp.attr('aria-selected', true).children('.jstreeol3-anchor').addClass(
																'jstreeol3-checked');
														var nobj = this.get_node(par);
														this._updateTileLayer(nobj);
													}
												} else {
													break;
												}
												par = this.get_node(par.parent);
											}
										}

										cur = [];
										for (i in sel) {
											if (sel.hasOwnProperty(i)) {
												cur.push(i);
											}
										}
										this._data['visibility'].hiding = cur;

										// apply down
										// (process
										// .children
										// separately?)
										if (s.indexOf('down') !== -1 && dom.length) {
											dom.find('.jstreeol3-anchor').addClass('jstreeol3-checked').parent()
													.attr('aria-selected', true);
										}
									}, this))
					.on('uncheck_all.jstreeol3', $.proxy(function(e, data) {
						var obj = this.get_node($.jstreeol3.root), m = this._model.data, i, j, tmp;
						for (i = 0, j = obj.children_d.length; i < j; i++) {
							tmp = m[obj.children_d[i]];
							if (tmp && tmp.original && tmp.original.state && tmp.original.state.undetermined) {
								tmp.original.state.undetermined = false;
							}
						}
					}, this))
					.on(
							'uncheck_node.jstreeol3',
							$
									.proxy(
											function(e, data) {
												var obj = data.node, dom = this.get_node(obj, true), i, j, tmp, s = this.settings.visibility.cascade;
												// t =
												// this.settings.visibility.tie_selection,
												var cur = this._data['visibility'].hiding, sel = {};
												if (obj && obj.original && obj.original.state && obj.original.state.undetermined) {
													obj.original.state.undetermined = false;
												}

												// apply down
												if (s.indexOf('down') !== -1) {
													for (i = 0, j = obj.children_d.length; i < j; i++) {
														tmp = this._model.data[obj.children_d[i]];
														tmp.state["hiding"] = false;
														if (tmp && tmp.original && tmp.original.state && tmp.original.state.undetermined) {
															tmp.original.state.undetermined = false;
														}
													}
												}

												// apply up
												if (s.indexOf('up') !== -1) {
													for (i = 0, j = obj.parents.length; i < j; i++) {
														tmp = this._model.data[obj.parents[i]];
														tmp.state["hiding"] = false;
														if (tmp && tmp.original && tmp.original.state && tmp.original.state.undetermined) {
															tmp.original.state.undetermined = false;
														}
														tmp = this.get_node(obj.parents[i], true);
														if (tmp && tmp.length) {
															tmp.attr('aria-selected', false).children('.jstreeol3-anchor').removeClass(
																	'jstreeol3-checked');
															var nobj = this.get_node(obj.parents[i]);
															this._updateTileLayer(nobj);
														}
													}
												}
												sel = {};
												for (i = 0, j = cur.length; i < j; i++) {
													// apply down +
													// apply up
													if ((s.indexOf('down') === -1 || $.inArray(cur[i], obj.children_d) === -1)
															&& (s.indexOf('up') === -1 || $.inArray(cur[i], obj.parents) === -1)) {
														sel[cur[i]] = true;
													}
												}
												cur = [];
												for (i in sel) {
													if (sel.hasOwnProperty(i)) {
														cur.push(i);
													}
												}
												this._data['visibility'].hiding = cur;

												// apply down
												// (process
												// .children
												// separately?)
												if (s.indexOf('down') !== -1 && dom.length) {
													dom.find('.jstreeol3-anchor').removeClass('jstreeol3-checked').parent().attr(
															'aria-selected', false);
												}
											}, this));
		}
		if (this.settings.visibility.cascade.indexOf('up') !== -1) {
			this.element.on('delete_node.jstreeol3', $.proxy(function(e, data) {
				// apply up (whole
				// handler)
				var p = this.get_node(data.parent), m = this._model.data, i, j, c, tmp;
				// t = this.settings.visibility.tie_selection;
				while (p && p.id !== $.jstreeol3.root && !p.state["hiding"]) {
					c = 0;
					for (i = 0, j = p.children.length; i < j; i++) {
						c += m[p.children[i]].state["hiding"];
					}
					if (j > 0 && c === j) {
						p.state["hiding"] = true;
						this._data['visibility'].hiding.push(p.id);
						tmp = this.get_node(p, true);
						if (tmp && tmp.length) {
							tmp.attr('aria-selected', true).children('.jstreeol3-anchor').addClass('jstreeol3-checked');
							var nobj = this.get_node(p);
							this._updateTileLayer(nobj);
						}
					} else {
						break;
					}
					p = this.get_node(p.parent);
				}
			}, this))
					.on(
							'move_node.jstreeol3',
							$
									.proxy(
											function(e, data) {
												// apply up (whole
												// handler)
												var is_multi = data.is_multi, old_par = data.old_parent, new_par = this
														.get_node(data.parent), m = this._model.data, p, c, i, j, tmp;
												// t =
												// this.settings.visibility.tie_selection;
												if (!is_multi) {
													p = this.get_node(old_par);
													while (p && p.id !== $.jstreeol3.root && !p.state["hiding"]) {
														c = 0;
														for (i = 0, j = p.children.length; i < j; i++) {
															c += m[p.children[i]].state["hiding"];
														}
														if (j > 0 && c === j) {
															p.state["hiding"] = true;
															this._data['visibility'].hiding.push(p.id);
															tmp = this.get_node(p, true);
															if (tmp && tmp.length) {
																tmp.attr('aria-selected', true).children('.jstreeol3-anchor').addClass(
																		'jstreeol3-checked');
																var nobj = this.get_node(p);
																this._updateTileLayer(nobj);
															}
														} else {
															break;
														}
														p = this.get_node(p.parent);
													}
												}
												p = new_par;
												while (p && p.id !== $.jstreeol3.root) {
													c = 0;
													for (i = 0, j = p.children.length; i < j; i++) {
														c += m[p.children[i]].state["hiding"];
													}
													if (c === j) {
														if (!p.state["hiding"]) {
															p.state["hiding"] = true;
															this._data['visibility'].hiding.push(p.id);
															tmp = this.get_node(p, true);
															if (tmp && tmp.length) {
																tmp.attr('aria-selected', true).children('.jstreeol3-anchor').addClass(
																		'jstreeol3-checked');
																var nobj = this.get_node(p);
																this._updateTileLayer(nobj);
															}
														}
													} else {
														if (p.state["hiding"]) {
															p.state["hiding"] = false;
															this._data['visibility'].hiding = $.vakata.array_remove_item(
																	this._data['visibility'].hiding, p.id);
															tmp = this.get_node(p, true);
															if (tmp && tmp.length) {
																tmp.attr('aria-selected', false).children('.jstreeol3-anchor').removeClass(
																		'jstreeol3-checked');
																var nobj = this.get_node(p);
																this._updateTileLayer(nobj);
															}
														} else {
															break;
														}
													}
													p = this.get_node(p.parent);
												}
											}, this));
		}
	};
	/**
	 * wms 레이어를 보이기/숨기기
	 * 
	 * @private
	 * @name _displayTileLayer()
	 * @plugin visibility
	 */
	this._displayTileLayer = function(node, bool) {
		var layer = this.get_LayerById(node.id);
		layer.setVisible(bool);
		if (layer instanceof ol.layer.Tile) {
			var showing = [];
			var children = JSON.parse(JSON.stringify(node.children)).reverse();
			// console.log(children);
			for (var i = 0; i < children.length; i++) {
				showing.push(this.get_LayerById(children[i]).get("id"));
			}
			// console.log(showing);
			var source = layer.getSource();
			var params = source.getParams();
			params["LAYERS"] = showing.toString();
			source.updateParams(params);
		}
	};
	/**
	 * wms 레이어를 업데이트한다.
	 * 
	 * @private
	 * @name _updateTileLayer()
	 * @plugin visibility
	 */
	this._updateTileLayer = function(node) {
		var layer = this.get_LayerById(node.id);
		if (layer instanceof ol.layer.Tile) {
			var showing = [];
			var hiding = this._data.visibility.hiding;
			var children = JSON.parse(JSON.stringify(node.children)).reverse();
			// console.log(children);
			for (var i = 0; i < hiding.length; i++) {
				if (children.indexOf(hiding[i]) !== -1) {
					children.splice(children.indexOf(hiding[i]), 1);
				}
			}
			for (var i = 0; i < children.length; i++) {
				showing.push(this.get_LayerById(children[i]).get("id"));
			}
			// console.log(showing);
			var source = layer.getSource();
			var params = source.getParams();
			if (showing.length === 0) {
				layer.setVisible(false);
			} else if (showing.length > 0) {
				params["LAYERS"] = showing.toString();
				layer.setVisible(true);
			}
			source.updateParams(params);
		}
		return layer;
	};
	/**
	 * set the undetermined state where and if necessary. Used internally.
	 * 
	 * @private
	 * @name _undetermined()
	 * @plugin visibility
	 */
	this._undetermined = function() {
		if (this.element === null) {
			return;
		}
		var i, j, k, l, o = {}, m = this._model.data;
		// t = this.settings.visibility.tie_selection,
		var s = this._data['visibility'].hiding, p = [], tt = this;
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
			if (!m[p[i]].state["hiding"]) {
				s = this.get_node(p[i], true);
				// console.log(s);
				if (s && s.length) {
					s.children('.jstreeol3-anchor').children('.jstreeol3-visibility').addClass('jstreeol3-undetermined');
				}
				var nobj = this.get_node(p[i]);
				this._updateTileLayer(nobj);
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
				if (this._model.data[obj.id].state.hiding) {
					tmp.className += ' jstreeol3-checked';
				}
				icon = _v.cloneNode(false);
				if (this._model.data[obj.id].state.visibility_disabled) {
					icon.className += ' jstreeol3-visibility-disabled';
				}
				tmp.insertBefore(icon, tmp.childNodes[0]);
			}
		}
		if (!is_callback && this.settings.visibility.cascade.indexOf('undetermined') !== -1) {
			if (this._data.visibility.uto) {
				clearTimeout(this._data.visibility.uto);
			}
			this._data.visibility.uto = setTimeout($.proxy(this._undetermined, this), 50);
		}
		return obj;
	};
	/**
	 * show the node visibility icons
	 * 
	 * @name show_visibilityes()
	 * @plugin visibility
	 */
	this.show_visibility = function() {
		this._data.core.themes.visibility = true;
		this.get_container_ul().removeClass("jstreeol3-no-visibility");
	};
	/**
	 * hide the node visibility icons
	 * 
	 * @name hide_visibility()
	 * @plugin visibility
	 */
	this.hide_visibility = function() {
		this._data.core.themes.visibility = false;
		this.get_container_ul().addClass("jstreeol3-no-visibility");
	};
	/**
	 * toggle the node icons
	 * 
	 * @name toggle_visibility()
	 * @plugin visibility
	 */
	this.toggle_visibility = function() {
		if (this._data.core.themes.visibility) {
			this.hide_visibility();
		} else {
			this.show_visibility();
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
		var s = this.settings.visibility.cascade, i, j;
		// t = this.settings.visibility.tie_selection,
		var d = this._data['visibility'].hiding, m = this._model.data;
		if (!obj || obj.state["hiding"] === true || s.indexOf('undetermined') === -1
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
	 * disable a node's visibility
	 * 
	 * @name disable_visibility(obj)
	 * @param {mixed}
	 *            obj an array can be used too
	 * @trigger disable_visibility.jstreeol3
	 * @plugin visibility
	 */
	this.disable_visibility = function(obj) {
		var t1, t2, dom;
		if ($.isArray(obj)) {
			obj = obj.slice();
			for (t1 = 0, t2 = obj.length; t1 < t2; t1++) {
				this.disable_visibility(obj[t1]);
			}
			return true;
		}
		obj = this.get_node(obj);
		if (!obj || obj.id === $.jstreeol3.root) {
			return false;
		}
		dom = this.get_node(obj, true);
		if (!obj.state.visibility_disabled) {
			obj.state.visibility_disabled = true;
			if (dom && dom.length) {
				dom.children('.jstreeol3-anchor').children('.jstreeol3-visibility').addClass('jstreeol3-visibility-disabled');
			}
			/**
			 * triggered when an node's visibility is disabled
			 * 
			 * @event
			 * @name disable_visibility.jstreeol3
			 * @param {Object}
			 *            node
			 * @plugin visibility
			 */
			this.trigger('disable_visibility', {
				'node' : obj
			});
		}
	};
	/**
	 * enable a node's visibility
	 * 
	 * @name disable_visibility(obj)
	 * @param {mixed}
	 *            obj an array can be used too
	 * @trigger enable_visibility.jstreeol3
	 * @plugin visibility
	 */
	this.enable_visibility = function(obj) {
		var t1, t2, dom;
		if ($.isArray(obj)) {
			obj = obj.slice();
			for (t1 = 0, t2 = obj.length; t1 < t2; t1++) {
				this.enable_visibility(obj[t1]);
			}
			return true;
		}
		obj = this.get_node(obj);
		if (!obj || obj.id === $.jstreeol3.root) {
			return false;
		}
		dom = this.get_node(obj, true);
		if (obj.state.visibility_disabled) {
			obj.state.visibility_disabled = false;
			if (dom && dom.length) {
				dom.children('.jstreeol3-anchor').children('.jstreeol3-visibility').removeClass('jstreeol3-visibility-disabled');
			}
			/**
			 * triggered when an node's visibility is enabled
			 * 
			 * @event
			 * @name enable_visibility.jstreeol3
			 * @param {Object}
			 *            node
			 * @plugin visibility
			 */
			this.trigger('enable_visibility', {
				'node' : obj
			});
		}
	};

	this.activate_node = function(obj, e) {
		if ($(e.target).hasClass('jstreeol3-visibility-disabled')) {
			return false;
		}
		if ((this.settings.visibility.whole_node || $(e.target).hasClass('jstreeol3-visibility'))) {
			e.ctrlKey = true;
		}
		if ((!this.settings.visibility.whole_node && !$(e.target).hasClass('jstreeol3-visibility'))) {
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
	 * 안보이게 한다. check a node (only if tie_selection in visibility settings is
	 * false, otherwise select_node will be called internally)
	 * 
	 * @name check_node(obj)
	 * @param {mixed}
	 *            obj an array can be used to check multiple nodes
	 * @trigger check_node.jstreeol3
	 * @plugin visibility
	 */
	this.check_node = function(obj, e) {
		// if (this.settings.visibility.tie_selection) {
		// return this.select_node(obj, false, true, e);
		// }
		// console.log("node checked");
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
		if (!obj.state.hiding) {
			obj.state.hiding = true;
			this._data.visibility.hiding.push(obj.id);
			if (dom && dom.length) {
				dom.children('.jstreeol3-anchor').addClass('jstreeol3-checked');
			}
			// console.log(obj);
			this._displayTileLayer(obj, false);
			// var layer = this._updateTileLayer(obj);
			// if (layer instanceof ol.layer.Base) {
			// layer.setVisible(false);
			// }
			/**
			 * triggered when an node is checked (only if tie_selection in
			 * visibility settings is false)
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
			 * @plugin visibility
			 */
			this.trigger('check_node', {
				'node' : obj,
				'selected' : this._data.visibility.hiding,
				'event' : e
			});
		}
	};
	/**
	 * 보이게 한다. uncheck a node (only if tie_selection in visibility settings is
	 * false, otherwise deselect_node will be called internally)
	 * 
	 * @name uncheck_node(obj)
	 * @param {mixed}
	 *            obj an array can be used to uncheck multiple nodes
	 * @trigger uncheck_node.jstreeol3
	 * @plugin visibility
	 */
	this.uncheck_node = function(obj, e) {
		// if (this.settings.visibility.tie_selection) {
		// return this.deselect_node(obj, false, e);
		// }
		// console.log("node unchecked");
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
		if (obj.state.hiding) {
			// console.log(obj);
			obj.state.hiding = false;
			this._data.visibility.hiding = $.vakata.array_remove_item(this._data.visibility.hiding, obj.id);
			if (dom.length) {
				dom.children('.jstreeol3-anchor').removeClass('jstreeol3-checked');
			}
			this._displayTileLayer(obj, true);
			// var layer = this._updateTileLayer(obj);
			// if (layer instanceof ol.layer.Base) {
			// layer.setVisible(true);
			// }
			/**
			 * triggered when an node is unchecked (only if tie_selection in
			 * visibility settings is false)
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
			 * @plugin visibility
			 */
			this.trigger('uncheck_node', {
				'node' : obj,
				'selected' : this._data.visibility.hiding,
				'event' : e
			});
		}
	};
	/**
	 * checks all nodes in the tree (only if tie_selection in visibility
	 * settings is false, otherwise select_all will be called internally)
	 * 
	 * @name check_all()
	 * @trigger check_all.jstreeol3, changed.jstreeol3
	 * @plugin visibility
	 */
	this.check_all = function() {
		// if (this.settings.visibility.tie_selection) {
		// return this.select_all();
		// }
		var tmp = this._data.visibility.hiding.concat([]), i, j;
		this._data.visibility.hiding = this._model.data[$.jstreeol3.root].children_d.concat();
		for (i = 0, j = this._data.visibility.hiding.length; i < j; i++) {
			if (this._model.data[this._data.visibility.hiding[i]]) {
				this._model.data[this._data.visibility.hiding[i]].state.hiding = true;
			}
		}
		this.redraw(true);
		/**
		 * triggered when all nodes are checked (only if tie_selection in
		 * visibility settings is false)
		 * 
		 * @event
		 * @name check_all.jstreeol3
		 * @param {Array}
		 *            selected the current selection
		 * @plugin visibility
		 */
		this.trigger('check_all', {
			'selected' : this._data.visibility.hiding
		});
	};
	/**
	 * uncheck all checked nodes (only if tie_selection in visibility settings
	 * is false, otherwise deselect_all will be called internally)
	 * 
	 * @name uncheck_all()
	 * @trigger uncheck_all.jstreeol3
	 * @plugin visibility
	 */
	this.uncheck_all = function() {
		// if (this.settings.visibility.tie_selection) {
		// return this.deselect_all();
		// }
		var tmp = this._data.visibility.hiding.concat([]), i, j;
		for (i = 0, j = this._data.visibility.hiding.length; i < j; i++) {
			if (this._model.data[this._data.visibility.hiding[i]]) {
				this._model.data[this._data.visibility.hiding[i]].state.hiding = false;
			}
		}
		this._data.visibility.hiding = [];
		this.element.find('.jstreeol3-checked').removeClass('jstreeol3-checked');
		/**
		 * triggered when all nodes are unchecked (only if tie_selection in
		 * visibility settings is false)
		 * 
		 * @event
		 * @name uncheck_all.jstreeol3
		 * @param {Object}
		 *            node the previous selection
		 * @param {Array}
		 *            selected the current selection
		 * @plugin visibility
		 */
		this.trigger('uncheck_all', {
			'selected' : this._data.visibility.hiding,
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
	 * @plugin visibility
	 */
	this.is_checked = function(obj) {
		// if (this.settings.visibility.tie_selection) {
		// return this.is_selected(obj);
		// }
		obj = this.get_node(obj);
		if (!obj || obj.id === $.jstreeol3.root) {
			return false;
		}
		return obj.state.hiding;
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
	 * @plugin visibility
	 */
	this.get_checked = function(full) {
		// if (this.settings.visibility.tie_selection) {
		// return this.get_selected(full);
		// }
		return full ? $.map(this._data.visibility.hiding, $.proxy(function(i) {
			return this.get_node(i);
		}, this)) : this._data.visibility.hiding;
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
	 * @plugin visibility
	 */
	this.get_top_checked = function(full) {
		// if (this.settings.visibility.tie_selection) {
		// return this.get_top_selected(full);
		// }
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
	 * @plugin visibility
	 */
	this.get_bottom_checked = function(full) {
		// if (this.settings.visibility.tie_selection) {
		// return this.get_bottom_selected(full);
		// }
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
		if (!$.isArray(obj)) {
			tmp = this.get_node(obj);
			if (tmp && tmp.state.loaded) {
				for (k = 0, l = tmp.children_d.length; k < l; k++) {
					if (this._model.data[tmp.children_d[k]].state.hiding) {
						c = true;
						this._data.visibility.hiding = $.vakata.array_remove_item(this._data.visibility.hiding, tmp.children_d[k]);
					}
				}
			}
		}
		return parent.load_node.apply(this, arguments);
	};
	this.get_state = function() {
		var state = parent.get_state.apply(this, arguments);
		// if (this.settings.visibility.tie_selection) {
		// return state;
		// }
		state.visibility = this._data.visibility.hiding.slice();
		return state;
	};
	this.set_state = function(state, callback) {
		var res = parent.set_state.apply(this, arguments);
		if (res && state.visibility) {
			// if (!this.settings.visibility.tie_selection) {
			this.uncheck_all();
			var _this = this;
			$.each(state.visibility, function(i, v) {
				_this.check_node(v);
			});
			// }
			delete state.visibility;
			this.set_state(state, callback);
			return false;
		}
		return res;
	};
	this.refresh = function(skip_loading, forget_state) {
		// if (!this.settings.visibility.tie_selection) {
		// this._data.visibility.selected = [];
		// }
		return parent.refresh.apply(this, arguments);
	};
};

// include the visibility plugin by default
// $.jstreeol3.defaults.plugins.push("visibility");
