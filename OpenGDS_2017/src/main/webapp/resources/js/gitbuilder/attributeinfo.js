/**
 * 애트리뷰트 정보 객체를 정의한다.
 * 
 * @author yijun.so
 * @date 2017. 06. 20
 * @version 0.01
 */
var gb;
if (!gb)
	gb = {};
if (!gb.inform)
	gb.inform = {};
gb.inform.AttributeInformation = function(obj) {
	var options = obj;
	this.fieldName = options.fieldName ? options.fieldName : null;
	this.type = options.type ? options.type : null;
	this.decimal = options.decimal ? options.decimal : null;
	this.size = options.size ? options.size : null;
	this.isUnique = options.isUnique ? options.isUnique : null;
	this.nullable = options.nullable ? options.nullable : null; 
}
gb.inform.AttributeInformation.prototype.setFieldName = function(fname) {
	this.fieldName = fname;
};
gb.inform.AttributeInformation.prototype.getFieldName = function() {
	return this.fieldName;
};

gb.inform.AttributeInformation.prototype.setType = function(type) {
	this.type = type;
};
gb.inform.AttributeInformation.prototype.getType = function() {
	return this.type;
};

gb.inform.AttributeInformation.prototype.setDecimal = function(dcm) {
	this.decimal = dcm;
};
gb.inform.AttributeInformation.prototype.getDecimal = function() {
	return this.decimal;
};

gb.inform.AttributeInformation.prototype.setSize = function(size) {
	this.size = size;
};
gb.inform.AttributeInformation.prototype.getSize = function() {
	return this.size;
};

gb.inform.AttributeInformation.prototype.setUnique = function(unq) {
	this.isUnique = unq;
};
gb.inform.AttributeInformation.prototype.getUnique = function() {
	return this.isUnique;
};

gb.inform.AttributeInformation.prototype.setNull = function(nll) {
	this.nullable = nll;
};
gb.inform.AttributeInformation.prototype.getNull = function() {
	return this.nullable;
};

gb.inform.AttributeInformation.prototype.clone = function() {
	var that = this;
	var obj = new gb.inform.AttributeInformation({
		fieldName : this.getFieldName(),
		type : this.getType(),
		decimal : this.getDecimal(),
		size : this.getSize(),
		isUnique : this.getUnique(),
		nullable : this.getNull()
	});
	return obj;
};

gb.inform.AttributeInformation.prototype.toString = function() {
	var that = this;
	var obj = {
		fieldName : this.getFieldName(),
		type : this.getType(),
		decimal : this.getDecimal(),
		size : this.getSize(),
		isUnique : this.getUnique(),
		nullable : this.getNull()
	};
	var str = JSON.stringify(obj);
	return str;
};