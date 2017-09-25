/**
 * 레이어 정의 패널 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2017. 07.26
 * @version 0.01
 * @class gb.panel.LayerDefinition
 * @constructor
 */
var gb;
if (!gb)
	gb = {};
if (!gb.panel)
	gb.panel = {};
gb.panel.LayerDefinition = function(obj) {
	gb.panel.Base.call(this, obj);
	var options = obj ? obj : {};
	this.geometry = obj.geometry ? obj.geometry : ["point", "linestring", "polygon"];
	
	if (this.autoOpen) {
		this.open();
	} else {
		this.close();
	}
	$("body").append(this.panel);
};
gb.panel.LayerDefinition.prototype = Object.create(gb.panel.Base.prototype);
gb.panel.LayerDefinition.prototype.constructor = gb.panel.LayerDefinition;
/**
 * 피처목록을 생성한다.
 * 
 * @method setFeatureList_
 */
gb.panel.LayerDefinition.prototype.setFeatureList_ = function() {
	return this.interaction;
};
/**
 * 내부 인터랙션 구조를 반환한다.
 * 
 * @method getInteractions_
 * @return {Mixed Obj} {select : ol.interaction.Select..}
 */
gb.panel.LayerDefinition.prototype.getInteractions_ = function() {
	return this.interaction;
};