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
gb.edit.FeatureRecord = function(obj) {
	this.created = {};
	this.modified = {};
	this.removed = {};
	this.id = obj.id ? obj.id : false;
}
gb.edit.FeatureRecord.prototype.getCreated = function() {
	return this.created;
};