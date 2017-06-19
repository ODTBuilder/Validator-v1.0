/**
 * 레이어 정보를 변경하는 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2017. 06. 16
 * @version 0.01
 */
var gb;
if (!gb)
	gb = {};
if (!gb.edit)
	gb.edit = {};
gb.edit.LayerInformation = function(obj) {
	this.layer = obj.layer ? obj.layer : null;
	
}
gb.edit.LayerInformation.prototype.setName = function(name) {
	return;
};
gb.edit.LayerInformation.prototype.setTitle = function(title) {
	return;
};
gb.edit.LayerInformation.prototype.setAttributeType = function() {
	return;
};