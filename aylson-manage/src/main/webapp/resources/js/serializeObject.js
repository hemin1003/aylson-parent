/**
 * @author 郑宇
 * @requires jQuery
 * 将form表单元素的值序列化成对象
 * @returns object
 */
var serializeObject = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};