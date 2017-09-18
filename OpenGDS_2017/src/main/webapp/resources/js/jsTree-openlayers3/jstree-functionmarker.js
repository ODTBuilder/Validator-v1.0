/**
 * ### functionmarker plugin
 */

/**
 * 
 * @name $.jstreeol3.defaults.functionmarker
 * @plugin functionmarker
 * @comment 적용중인 기능 표시
 * @author 소이준
 */
$.jstreeol3.defaults.functionmarker = {
// "snap" : "fa fa-magnet"
};

$.jstreeol3.plugins.functionmarker = function(options, parent) {
	this.init = function(el, options) {
		this._data.functionmarker = {};
		var optKeys = Object.keys(options.functionmarker);
		for (var i = 0; i < optKeys.length; i++) {
			this._data.functionmarker[optKeys[i]] = {
				"list" : [],
				"icon" : options.functionmarker[optKeys[i]]
			};
		}
		console.log(this._data.functionmarker);
		parent.init.call(this, el, options);
	};
	this.refresh = function(skip_loading, forget_state) {
		parent.refresh.call(this, skip_loading, forget_state);
	};
	this.bind = function() {
		this.element.on('model.jstreeol3', $.proxy(function(e, data) {
			var m = this._model.data, dpc = data.nodes, i, j, c = 'default', k, optKeys = Object.keys(this._data.functionmarker);
			for (i = 0, j = dpc.length; i < j; i++) {
				for (var k = 0; k < optKeys.length; k++) {
					var list = this._data.functionmarker[optKeys[k]].list;
					if (list.indexOf(m[dpc[i]].id) !== -1) {
						m[dpc[i]].state[optKeys[k]] = true;
					} else {
						m[dpc[i]].state[optKeys[k]] = false;
					}
				}
			}
		}, this)).on('delete_node_layer.jstreeol3', $.proxy(function(e, data) {
			console.log("delete layer");
			var optKeys = Object.keys(this._data.functionmarker), node = data.node;
			for (var k = 0; k < optKeys.length; k++) {
				var list = this._data.functionmarker[optKeys[k]].list;
				for (var l = 0; l < list.length; l++) {
					if (list.indexOf(node.id) !== -1) {
						list.splice(list.indexOf(node.id), 1);
					}
				}
			}
		}, this));

		parent.bind.call(this);
	};
	this.redraw_node = function(obj, deep, is_callback, force_render) {
		obj = parent.redraw_node.apply(this, arguments);
		if (obj) {
			var fnmks = Object.keys(this._data.functionmarker);
			for (var i = 0; i < fnmks.length; i++) {
				var nobj = this.get_node(obj.id);
				if (nobj.state[fnmks[i]]) {
					var ic = $("<i>").attr({
						"role" : "presentation"
					}).addClass("jstreeol3-icon").addClass("jstreeol3-themeicon-custom").addClass(this._data.functionmarker[fnmks[i]].icon);
					$(obj.childNodes[1]).append(ic);
				}
			}
		}
		return obj;
	};
	/**
	 * set flag
	 * 
	 * @name show_visibilityes()
	 * @plugin visibility
	 */
	this.set_flag = function(obj, funcname, flag) {
		obj.state[funcname] = flag;
		if (flag) {
			var list = this._data.functionmarker[funcname].list;
			if (list.indexOf(obj.id) === -1) {
				list.push(obj.id);
				this.redraw_node(obj.id);
			}
		} else {
			var list = this._data.functionmarker[funcname].list;
			if (list.indexOf(obj.id) !== -1) {
				list.splice(list.indexOf(obj.id), 1);
				this.redraw_node(obj.id);
			}
		}
	};
};
// include the functionmarker plugin by default
// $.jstreeol3.defaults.plugins.push("functionmarker");
