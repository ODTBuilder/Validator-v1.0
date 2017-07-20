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
if (!gb.layer)
	gb.layer = {};
gb.layer.Attribute = function(obj) {
	var options = obj;
	this.originFieldName = options.fieldName ? options.fieldName : null;
	this.fieldName = options.fieldName ? options.fieldName : null;
	this.type = options.type ? options.type : null;
	this.decimal = options.decimal ? options.decimal : null;
	this.size = options.size ? options.size : null;
	this.isUnique = options.isUnique ? options.isUnique : null;
	this.nullable = options.nullable !== undefined ? options.nullable : true;
	this.isNew = options.isNew ? options.isNew : true;
}
gb.layer.Attribute.prototype.setOriginFieldName = function(fname) {
	this.fieldName = fname;
};
gb.layer.Attribute.prototype.getOriginFieldName = function() {
	return this.fieldName;
};

gb.layer.Attribute.prototype.setFieldName = function(fname) {
	this.fieldName = fname;
};
gb.layer.Attribute.prototype.getFieldName = function() {
	return this.fieldName;
};

gb.layer.Attribute.prototype.setType = function(type) {
	this.type = type;
};
gb.layer.Attribute.prototype.getType = function() {
	return this.type;
};

gb.layer.Attribute.prototype.setDecimal = function(dcm) {
	this.decimal = dcm;
};
gb.layer.Attribute.prototype.getDecimal = function() {
	return this.decimal;
};

gb.layer.Attribute.prototype.setSize = function(size) {
	this.size = size;
};
gb.layer.Attribute.prototype.getSize = function() {
	return this.size;
};

gb.layer.Attribute.prototype.setUnique = function(unq) {
	this.isUnique = unq;
};
gb.layer.Attribute.prototype.getUnique = function() {
	return this.isUnique;
};

gb.layer.Attribute.prototype.setNull = function(nll) {
	this.nullable = nll;
};
gb.layer.Attribute.prototype.getNull = function() {
	return this.nullable;
};

gb.layer.Attribute.prototype.clone = function() {
	var that = this;
	var obj = new gb.layer.Attribute({
		originFieldName : this.getOriginFieldName(),
		fieldName : this.getFieldName(),
		type : this.getType(),
		decimal : this.getDecimal(),
		size : this.getSize(),
		isUnique : this.getUnique(),
		nullable : this.getNull()
	});
	return obj;
};

gb.layer.Attribute.prototype.getStructure = function() {
	var that = this;
	var obj;
	if (this.isNew === true) {
		obj = {
			fieldName : this.getFieldName(),
			type : this.getType(),
			decimal : this.getDecimal(),
			size : this.getSize(),
			isUnique : this.getUnique(),
			nullable : this.getNull()
		};
	} else {
		obj = {
			originFieldName : this.getOriginFieldName(),
			fieldName : this.getFieldName(),
			type : this.getType(),
			decimal : this.getDecimal(),
			size : this.getSize(),
			isUnique : this.getUnique(),
			nullable : this.getNull()
		};
	}
	return obj;
};