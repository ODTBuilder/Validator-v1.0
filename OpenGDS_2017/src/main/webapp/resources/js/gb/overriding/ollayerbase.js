/**
 * 오픈레이어스를 확장한다.
 * 
 * @author yijun.so
 * @date 2017. 07. 07
 * @version 0.01
 */
ol.layer.Base.prototype.setSource = function(source) {
	this.source = source;
};
ol.layer.Base.prototype.getSource = function() {
	return this.source;
};