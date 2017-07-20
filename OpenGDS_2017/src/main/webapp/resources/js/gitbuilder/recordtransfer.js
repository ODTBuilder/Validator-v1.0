/**
 * 레이어 및 피처 편집 이력을 전송하는 객체
 * 
 * @author 소이준
 * @date 2017.06.13
 */

var gb;
if (!gb)
	gb = {};
if (!gb.edit)
	gb.edit = {};
gb.edit.RecordTransfer = function(obj) {
	this.feature = obj.feature;
	this.layer = obj.layer;
	this.url = obj.url;
}
gb.edit.RecordTransfer.prototype.getStructure = function() {
	var obj = {};
	if (this.layer instanceof gb.edit.LayerRecord) {
		obj["layer"] = this.layer.getStructure();
	}

	if (this.feature instanceof gb.edit.FeatureRecord) {
		obj["feature"] = this.feature.getStructure();
	}
	console.log(obj);
	return obj;
};

gb.edit.RecordTransfer.prototype.sendStructure = function() {
	$.ajax({
		url : this.url,
		type : "POST",
		data : JSON.stringify(this.getStructure()),
		contentType : "application/json; charset=UTF-8",
		dataType : 'json',
		beforeSend : function() {
			$("body").css("cursor", "wait");
		},
		complete : function() {
			$("body").css("cursor", "default");
		},
		success : function(data) {
			console.log(data);
		}
	});
};