/**
 * 레이어 스타일 패널 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2018. 06.04
 * @version 0.01
 * @class gb.panel.LayerStyle
 * @constructor
 */
var gb;
if (!gb)
	gb = {};
if (!gb.panel)
	gb.panel = {};
gb.panel.LayerStyle = function(obj) {
	obj.width = 247;
	obj.height = 491;
	obj.positionX = 380;
	obj.positionY = 466;
	gb.panel.Base.call(this, obj);
	var options = obj ? obj : {};
	this.layer = options.layer instanceof ol.layer.Base ? options.layer : undefined;
	this.layerName = $("<div>");

	this.lineLabel = $("<div>").text("Outline");
	this.linePicker = $("<input>").attr({
		"type" : "text"
	});
	this.lineContent = $("<div>").append(this.linePicker);
	this.lineArea = $("<div>").append(this.lineLabel).append(this.lineContent);

	this.fillLabel = $("<div>").text("Fill");
	this.fillPicker = $("<input>").attr({
		"type" : "text"
	});
	this.fillContent = $("<div>").append(this.fillPicker);
	this.fillArea = $("<div>").append(this.fillLabel).append(this.fillContent);

	this.widthLabel = $("<div>").text("Width");
	this.widthInput = $("<input>").attr({
		"type" : "number"
	});
	this.widthContent = $("<div>").append(this.widthInput);
	this.widthArea = $("<div>").append(this.widthLabel).append(this.widthContent);

	this.outlineLabel = $("<div>").text("Outline Style");
	this.outlineInput = $("<select>");
	this.outlineContent = $("<div>").append(this.outlineInput);
	this.outlineArea = $("<div>").append(this.outlineLabel).append(this.outlineContent);

	$(this.panelBody).append(this.layerName).append(this.lineArea).append(this.fillArea).append(this.widthArea).append(this.outlineArea);
	$("body").append(this.panel);

	$(this.linePicker).spectrum({
		showInput : true,
		showAlpha : true,
		preferredFormat : "rgb",
	});
};
gb.panel.LayerStyle.prototype = Object.create(gb.panel.Base.prototype);
gb.panel.LayerStyle.prototype.constructor = gb.panel.LayerStyle;
/**
 * 스타일을 변경할 레이어를 설정한다.
 * 
 * @method setLayer
 */
gb.panel.LayerStyle.prototype.setLayer = function(layer) {

};
/**
 * 내부 인터랙션 구조를 반환한다.
 * 
 * @method getInteractions_
 * @return {Mixed Obj} {select : ol.interaction.Select..}
 */
gb.panel.LayerStyle.prototype.get = function() {
	return null;
};