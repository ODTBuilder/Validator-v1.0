// =================================소이준===================================
/**
 * ### legend plugin
 * 
 * 지오서버 범례를 표시해주는  플러그인
 */

/**
 * 
 * 
 * @name $.jstreeol3.defaults.legends
 * @plugin legends
 * @comment 범례
 * @author 소이준
 */

$.jstreeol3.defaults.legends = {
	// "#" : {
	// "valid_children" : [ "default" ]
	// },
	// "default" : {
	// "icon" : "fa fa-file-o",
	// "valid_children" : []
	// }
	"url" : "http://175.116.181.42:9990/geoserver/wms",
	"width" : "15",
	"height" : "15",
	"format" : "image/png"
};
$.jstreeol3.defaults.legends[$.jstreeol3.root] = {};

$.jstreeol3.plugins.legends = function(options, parent) {
	// this.init = function(el, options) {
	// var i, j;
	// if (options && options.legends && options.legends['default']) {
	// for (i in options.legends) {
	// if (i !== "default" && i !== $.jstreeol3.root &&
	// options.legends.hasOwnProperty(i)) {
	// for (j in options.legends['default']) {
	// if (options.legends['default'].hasOwnProperty(j) && options.legends[i][j]
	// === undefined) {
	// options.legends[i][j] = options.legends['default'][j];
	// }
	// }
	// }
	// }
	// }
	// parent.init.call(this, el, options);
	// this._model.data[$.jstreeol3.root].legend = $.jstreeol3.root;
	// };
	this.refresh = function(skip_loading, forget_state) {
		parent.refresh.call(this, skip_loading, forget_state);
		this._model.data[$.jstreeol3.root].legend = $.jstreeol3.root;
	};
	this.bind = function() {
		this.element.on('model.jstreeol3', $.proxy(function(e, data) {
			var m = this._model.data, dpc = data.nodes, t = this.settings.legends, i, j, c = 'default', k;
			for (i = 0, j = dpc.length; i < j; i++) {
				console.log(m[dpc[i]]);
				// c = 'default';
				// if (m[dpc[i]].original && m[dpc[i]].original.legend &&
				// t[m[dpc[i]].original.legend]) {
				// c = m[dpc[i]].original.legend;
				// }
				// if (m[dpc[i]].data && m[dpc[i]].data.jstreeol3 &&
				// m[dpc[i]].data.jstreeol3.legend &&
				// t[m[dpc[i]].data.jstreeol3.legend]) {
				// c = m[dpc[i]].data.jstreeol3.legend;
				// }
				// m[dpc[i]].legend = c;
				 if (m[dpc[i]].icon === true && t[c].icon !== undefined) {
				 m[dpc[i]].icon = t[c].icon;
				 }
				// if (t[c].li_attr !== undefined && typeof t[c].li_attr ===
				// 'object') {
				// for (k in t[c].li_attr) {
				// if (t[c].li_attr.hasOwnProperty(k)) {
				// if (k === 'id') {
				// continue;
				// } else if (m[dpc[i]].li_attr[k] === undefined) {
				// m[dpc[i]].li_attr[k] = t[c].li_attr[k];
				// } else if (k === 'class') {
				// m[dpc[i]].li_attr['class'] = t[c].li_attr['class'] + ' ' +
				// m[dpc[i]].li_attr['class'];
				// }
				// }
				// }
				// }
				// if (t[c].a_attr !== undefined && typeof t[c].a_attr ===
				// 'object') {
				// for (k in t[c].a_attr) {
				// if (t[c].a_attr.hasOwnProperty(k)) {
				// if (k === 'id') {
				// continue;
				// } else if (m[dpc[i]].a_attr[k] === undefined) {
				// m[dpc[i]].a_attr[k] = t[c].a_attr[k];
				// } else if (k === 'href' && m[dpc[i]].a_attr[k] === '#') {
				// m[dpc[i]].a_attr['href'] = t[c].a_attr['href'];
				// } else if (k === 'class') {
				// m[dpc[i]].a_attr['class'] = t[c].a_attr['class'] + ' ' +
				// m[dpc[i]].a_attr['class'];
				// }
				// }
				// }
				// }
			}
			m[$.jstreeol3.root].legend = $.jstreeol3.root;
		}, this));
		parent.bind.call(this);
	};
	// this.get_json = function(obj, options, flat) {
	// var i, j, m = this._model.data, opt = options ? $.extend(true, {},
	// options, {
	// no_id : false
	// }) : {}, tmp = parent.get_json.call(this, obj, opt, flat);
	// if (tmp === false) {
	// return false;
	// }
	// if ($.isArray(tmp)) {
	// for (i = 0, j = tmp.length; i < j; i++) {
	// tmp[i].legend = tmp[i].id && m[tmp[i].id] && m[tmp[i].id].legend ?
	// m[tmp[i].id].legend : "default";
	// if (options && options.no_id) {
	// delete tmp[i].id;
	// if (tmp[i].li_attr && tmp[i].li_attr.id) {
	// delete tmp[i].li_attr.id;
	// }
	// if (tmp[i].a_attr && tmp[i].a_attr.id) {
	// delete tmp[i].a_attr.id;
	// }
	// }
	// }
	// } else {
	// tmp.legend = tmp.id && m[tmp.id] && m[tmp.id].legend ? m[tmp.id].legend :
	// "default";
	// if (options && options.no_id) {
	// tmp = this._delete_ids(tmp);
	// }
	// }
	// return tmp;
	// };
	// this._delete_ids = function(tmp) {
	// if ($.isArray(tmp)) {
	// for (var i = 0, j = tmp.length; i < j; i++) {
	// tmp[i] = this._delete_ids(tmp[i]);
	// }
	// return tmp;
	// }
	// delete tmp.id;
	// if (tmp.li_attr && tmp.li_attr.id) {
	// delete tmp.li_attr.id;
	// }
	// if (tmp.a_attr && tmp.a_attr.id) {
	// delete tmp.a_attr.id;
	// }
	// if (tmp.children && $.isArray(tmp.children)) {
	// tmp.children = this._delete_ids(tmp.children);
	// }
	// return tmp;
	// };
	// this.check = function(chk, obj, par, pos, more) {
	// if (parent.check.call(this, chk, obj, par, pos, more) === false) {
	// return false;
	// }
	// obj = obj && obj.id ? obj : this.get_node(obj);
	// par = par && par.id ? par : this.get_node(par);
	// var m = obj && obj.id ? (more && more.origin ? more.origin :
	// $.jstreeol3.reference(obj.id)) : null, tmp, d, i, j;
	// m = m && m._model && m._model.data ? m._model.data : null;
	// switch (chk) {
	// case "create_node":
	// case "move_node":
	// case "copy_node":
	// if (chk !== 'move_node' || $.inArray(obj.id, par.children) === -1) {
	// tmp = this.get_rules(par);
	// if (tmp.max_children !== undefined && tmp.max_children !== -1 &&
	// tmp.max_children === par.children.length) {
	// this._data.core.last_error = {
	// 'error' : 'check',
	// 'plugin' : 'legends',
	// 'id' : 'legends_01',
	// 'reason' : 'max_children prevents function: ' + chk,
	// 'data' : JSON.stringify({
	// 'chk' : chk,
	// 'pos' : pos,
	// 'obj' : obj && obj.id ? obj.id : false,
	// 'par' : par && par.id ? par.id : false
	// })
	// };
	// return false;
	// }
	// if (tmp.valid_children !== undefined && tmp.valid_children !== -1
	// && $.inArray((obj.legend || 'default'), tmp.valid_children) === -1) {
	// this._data.core.last_error = {
	// 'error' : 'check',
	// 'plugin' : 'legends',
	// 'id' : 'legends_02',
	// 'reason' : 'valid_children prevents function: ' + chk,
	// 'data' : JSON.stringify({
	// 'chk' : chk,
	// 'pos' : pos,
	// 'obj' : obj && obj.id ? obj.id : false,
	// 'par' : par && par.id ? par.id : false
	// })
	// };
	// return false;
	// }
	// if (m && obj.children_d && obj.parents) {
	// d = 0;
	// for (i = 0, j = obj.children_d.length; i < j; i++) {
	// d = Math.max(d, m[obj.children_d[i]].parents.length);
	// }
	// d = d - obj.parents.length + 1;
	// }
	// if (d <= 0 || d === undefined) {
	// d = 1;
	// }
	// do {
	// if (tmp.max_depth !== undefined && tmp.max_depth !== -1 && tmp.max_depth
	// < d) {
	// this._data.core.last_error = {
	// 'error' : 'check',
	// 'plugin' : 'legends',
	// 'id' : 'legends_03',
	// 'reason' : 'max_depth prevents function: ' + chk,
	// 'data' : JSON.stringify({
	// 'chk' : chk,
	// 'pos' : pos,
	// 'obj' : obj && obj.id ? obj.id : false,
	// 'par' : par && par.id ? par.id : false
	// })
	// };
	// return false;
	// }
	// par = this.get_node(par.parent);
	// tmp = this.get_rules(par);
	// d++;
	// } while (par);
	// }
	// break;
	// }
	// return true;
	// };
	// /**
	// * used to retrieve the legend settings object for a node
	// *
	// * @name get_rules(obj)
	// * @param {mixed}
	// * obj the node to find the rules for
	// * @return {Object}
	// * @plugin legends
	// */
	// this.get_rules = function(obj) {
	// obj = this.get_node(obj);
	// if (!obj) {
	// return false;
	// }
	// var tmp = this.get_legend(obj, true);
	// if (tmp.max_depth === undefined) {
	// tmp.max_depth = -1;
	// }
	// if (tmp.max_children === undefined) {
	// tmp.max_children = -1;
	// }
	// if (tmp.valid_children === undefined) {
	// tmp.valid_children = -1;
	// }
	// return tmp;
	// };
	// /**
	// * used to retrieve the legend string or settings object for a node
	// *
	// * @name get_legend(obj [, rules])
	// * @param {mixed}
	// * obj the node to find the rules for
	// * @param {Boolean}
	// * rules if set to `true` instead of a string the settings object
	// * will be returned
	// * @return {String|Object}
	// * @plugin legends
	// */
	// this.get_legend = function(obj, rules) {
	// obj = this.get_node(obj);
	// return (!obj) ? false : (rules ? $.extend({
	// 'legend' : obj.legend
	// }, this.settings.legends[obj.legend]) : obj.legend);
	// };
	// /**
	// * used to change a node's legend
	// *
	// * @name set_legend(obj, legend)
	// * @param {mixed}
	// * obj the node to change
	// * @param {String}
	// * legend the new legend
	// * @plugin legends
	// */
	// this.set_legend = function(obj, legend) {
	// var m = this._model.data, t, t1, t2, old_legend, old_icon, k, d, a;
	// if ($.isArray(obj)) {
	// obj = obj.slice();
	// for (t1 = 0, t2 = obj.length; t1 < t2; t1++) {
	// this.set_legend(obj[t1], legend);
	// }
	// return true;
	// }
	// t = this.settings.legends;
	// obj = this.get_node(obj);
	// if (!t[legend] || !obj) {
	// return false;
	// }
	// d = this.get_node(obj, true);
	// if (d && d.length) {
	// a = d.children('.jstreeol3-anchor');
	// }
	// old_legend = obj.legend;
	// old_icon = this.get_icon(obj);
	// obj.legend = legend;
	// if (old_icon === true || !t[old_legend] || (t[old_legend].icon !==
	// undefined && old_icon === t[old_legend].icon)) {
	// this.set_icon(obj, t[legend].icon !== undefined ? t[legend].icon : true);
	// }
	//
	// // remove old legend props
	// if (t[old_legend] && t[old_legend].li_attr !== undefined && typeof
	// t[old_legend].li_attr === 'object') {
	// for (k in t[old_legend].li_attr) {
	// if (t[old_legend].li_attr.hasOwnProperty(k)) {
	// if (k === 'id') {
	// continue;
	// } else if (k === 'class') {
	// m[obj.id].li_attr['class'] = (m[obj.id].li_attr['class'] ||
	// '').replace(t[old_legend].li_attr[k], '');
	// if (d) {
	// d.removeClass(t[old_legend].li_attr[k]);
	// }
	// } else if (m[obj.id].li_attr[k] === t[old_legend].li_attr[k]) {
	// m[obj.id].li_attr[k] = null;
	// if (d) {
	// d.removeAttr(k);
	// }
	// }
	// }
	// }
	// }
	// if (t[old_legend] && t[old_legend].a_attr !== undefined && typeof
	// t[old_legend].a_attr === 'object') {
	// for (k in t[old_legend].a_attr) {
	// if (t[old_legend].a_attr.hasOwnProperty(k)) {
	// if (k === 'id') {
	// continue;
	// } else if (k === 'class') {
	// m[obj.id].a_attr['class'] = (m[obj.id].a_attr['class'] ||
	// '').replace(t[old_legend].a_attr[k], '');
	// if (a) {
	// a.removeClass(t[old_legend].a_attr[k]);
	// }
	// } else if (m[obj.id].a_attr[k] === t[old_legend].a_attr[k]) {
	// if (k === 'href') {
	// m[obj.id].a_attr[k] = '#';
	// if (a) {
	// a.attr('href', '#');
	// }
	// } else {
	// delete m[obj.id].a_attr[k];
	// if (a) {
	// a.removeAttr(k);
	// }
	// }
	// }
	// }
	// }
	// }
	//
	// // add new props
	// if (t[legend].li_attr !== undefined && typeof t[legend].li_attr ===
	// 'object') {
	// for (k in t[legend].li_attr) {
	// if (t[legend].li_attr.hasOwnProperty(k)) {
	// if (k === 'id') {
	// continue;
	// } else if (m[obj.id].li_attr[k] === undefined) {
	// m[obj.id].li_attr[k] = t[legend].li_attr[k];
	// if (d) {
	// if (k === 'class') {
	// d.addClass(t[legend].li_attr[k]);
	// } else {
	// d.attr(k, t[legend].li_attr[k]);
	// }
	// }
	// } else if (k === 'class') {
	// m[obj.id].li_attr['class'] = t[legend].li_attr[k] + ' ' +
	// m[obj.id].li_attr['class'];
	// if (d) {
	// d.addClass(t[legend].li_attr[k]);
	// }
	// }
	// }
	// }
	// }
	// if (t[legend].a_attr !== undefined && typeof t[legend].a_attr ===
	// 'object') {
	// for (k in t[legend].a_attr) {
	// if (t[legend].a_attr.hasOwnProperty(k)) {
	// if (k === 'id') {
	// continue;
	// } else if (m[obj.id].a_attr[k] === undefined) {
	// m[obj.id].a_attr[k] = t[legend].a_attr[k];
	// if (a) {
	// if (k === 'class') {
	// a.addClass(t[legend].a_attr[k]);
	// } else {
	// a.attr(k, t[legend].a_attr[k]);
	// }
	// }
	// } else if (k === 'href' && m[obj.id].a_attr[k] === '#') {
	// m[obj.id].a_attr['href'] = t[legend].a_attr['href'];
	// if (a) {
	// a.attr('href', t[legend].a_attr['href']);
	// }
	// } else if (k === 'class') {
	// m[obj.id].a_attr['class'] = t[legend].a_attr['class'] + ' ' +
	// m[obj.id].a_attr['class'];
	// if (a) {
	// a.addClass(t[legend].a_attr[k]);
	// }
	// }
	// }
	// }
	// }
	//
	// return true;
	// };
};
// =================================소이준===================================
