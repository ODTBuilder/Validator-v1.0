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
	this.init = function(el, options) {

		parent.init.call(this, el, options);
	};
	this.refresh = function(skip_loading, forget_state) {
		parent.refresh.call(this, skip_loading, forget_state);
	};
	this.bind = function() {
		this.element
				.on(
						'model.jstreeol3',
						$
								.proxy(
										function(e, data) {
											var m = this._model.data, dpc = data.nodes, t = this.settings.legends, i, j, c = 'default', k;
											for (i = 0, j = dpc.length; i < j; i++) {
												// console.log(m[dpc[i]]);
												var nd = this.get_node(m[dpc[i]]);
												nd.icon = "http://175.116.181.42:9990/geoserver/wms?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=15&HEIGHT=15&LAYER=geo_ngi_37712003_A0010000_LINESTRING";
												// var dom =
												// this.get_node(m[dpc[i]],
												// true);
												// console.log(dom);
												// if (dom) {
												// var img = $("<img>")
												// .attr(
												// {
												// "src" :
												// "http://175.116.181.42:9990/geoserver/wms?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=15&HEIGHT=15&LAYER=geo_ngi_37712003_A0010000_LINESTRING"
												// });
												// $(dom).find('.jstreeol3-anchor').append(img);
												// }

												// c = 'default';
												// if (m[dpc[i]].original &&
												// m[dpc[i]].original.legend &&
												// t[m[dpc[i]].original.legend])
												// {
												// c =
												// m[dpc[i]].original.legend;
												// }
												// if (m[dpc[i]].data &&
												// m[dpc[i]].data.jstreeol3 &&
												// m[dpc[i]].data.jstreeol3.legend
												// &&
												// t[m[dpc[i]].data.jstreeol3.legend])
												// {
												// c =
												// m[dpc[i]].data.jstreeol3.legend;
												// }
												// m[dpc[i]].legend = c;
												// if (m[dpc[i]].icon === true
												// && t[c].icon !== undefined) {
												// m[dpc[i]].icon = t[c].icon;
												// }
												// if (t[c].li_attr !==
												// undefined && typeof
												// t[c].li_attr ===
												// 'object') {
												// for (k in t[c].li_attr) {
												// if
												// (t[c].li_attr.hasOwnProperty(k))
												// {
												// if (k === 'id') {
												// continue;
												// } else if
												// (m[dpc[i]].li_attr[k] ===
												// undefined) {
												// m[dpc[i]].li_attr[k] =
												// t[c].li_attr[k];
												// } else if (k === 'class') {
												// m[dpc[i]].li_attr['class'] =
												// t[c].li_attr['class'] + ' ' +
												// m[dpc[i]].li_attr['class'];
												// }
												// }
												// }
												// }
												// if (t[c].a_attr !== undefined
												// && typeof t[c].a_attr ===
												// 'object') {
												// for (k in t[c].a_attr) {
												// if
												// (t[c].a_attr.hasOwnProperty(k))
												// {
												// if (k === 'id') {
												// continue;
												// } else if
												// (m[dpc[i]].a_attr[k] ===
												// undefined) {
												// m[dpc[i]].a_attr[k] =
												// t[c].a_attr[k];
												// } else if (k === 'href' &&
												// m[dpc[i]].a_attr[k] === '#')
												// {
												// m[dpc[i]].a_attr['href'] =
												// t[c].a_attr['href'];
												// } else if (k === 'class') {
												// m[dpc[i]].a_attr['class'] =
												// t[c].a_attr['class'] + ' ' +
												// m[dpc[i]].a_attr['class'];
												// }
												// }
												// }
												// }
											}
											// m[$.jstreeol3.root].legend =
											// $.jstreeol3.root;
										}, this));
		parent.bind.call(this);
	};
};
// =================================소이준===================================
