$(function(){
	$('[ssr-show-detail]').on('click',showDetail);
	$('[ssr-delete-one]').on('click',deleteOne);
	$('[ssr-delete-all]').on('click',deleteAll);
	selectAll();//全选与子选
});

/**
 * 显示详细信息事件。操作ssr-show-detail 属性指定的class类
 * ssr-show-detail ： 要显示的类名称，隐藏class使用".hidden"
 */
function showDetail(e){
	if (e) e.preventDefault();
	var detail = $(this).attr("ssr-show-detail");
	if(!detail){
		detail = "detail";
	}
	$(this).toggleClass("glyphicon-chevron-down").toggleClass("glyphicon-chevron-up");
	$("."+detail).toggleClass("hidden");
}

/**
 * 确认对话框
 * @param title 标题
 * @param text 内容
 * @param callback 回调函数
 * @param buttons 按钮数组
 */
function confirm_dialog(title, text, callback, buttons) {
    if (typeof buttons !== "object") {
        buttons = {
            "确定": true,
            "取消": false
        };
    }
    var notice;
    text = $('<div>' + text + '<br style="clear: both;" /><div class="button-container" style="margin-top: 10px; text-align: right;"></div></div>');
    $.each(buttons, function(b, val) {
        text.find("div.button-container").append($('<button style="margin-left: 5px;" class="btn btn-default btn-small">' + b + '</button>').click(function() {
            notice.pnotify_remove();
            callback.call(notice, val);
        }));
    });
    notice = $.pnotify({
        title: title,
        text: text,
        insert_brs: false,
        hide: false,
        icon: 'glyphicon glyphicon-question-sign'
    });
}

/**
 * 删除一个对象，并删除改对象的父tr标签。
 * ssr-delete-one ： 无属性值
 * ssr-delete-url :(可选)删除路径，如果包含href属性，则优先使用herf属性 
 */
function deleteOne(e) {
	if (e) e.preventDefault();
	var path =	getDeleteUrl($(this));
	if(!path) return ;
	var $tr = $(this).parents("tr");
	confirm_dialog('询问', '您确定?', function(answer) {
		if(!answer) return false;
		ajaxDelete(path,{"_method":"delete"},$tr);
	});
}

/**
 * 批量删除。
 * ssr-delete-all ：要删除的checkbox 类名称
 * ssr-delete-url :(可选)删除路径，如果包含href属性，则优先使用herf属性 
 */
function deleteAll(e){
	if (e) e.preventDefault();
	var path =	getDeleteUrl($(this));
	if(!path) return ;
	var $item = $("."+$(this).attr("ssr-delete-all")+":checked");
	if($item.length == 0){
		$.pnotify({title: '请选择要删除的记录！',type: 'info'});
		return;
	}
	var ids =new Array();
	$item.each(function(){
		ids.push($(this).val());
	});
	confirm_dialog('询问', '您确定?', function(answer) {
		if(!answer) return false;
		ajaxDelete(path,{"_method":"delete","ids":ids.join(",")},$item.parents("tr"));
	});
}

/**
 * 获取要删除的URL路径
 * @param $this 删除事件对象
 * @returns 路径信息，没有路径返回false
 */
function getDeleteUrl($this){
	var path = $this.attr("href");
	path = (!path) ? $this.attr("ssr-delete-url") : path;
	if(!path){
		$.pnotify({title: '删除路径不正确！',type: 'info'});
		return false;
	}
	return path;
}
/**
 * Ajax异步删除元素
 * @param path 路径
 * @param params 参数
 * @param trSelect tr选择器(可选)
 */
function ajaxDelete(path,params,$tr){
	$.ajax({
		type:"post",
		url:path, 
		data:params,
		dataType:"json",
		success:function(data) {
				if (data.status == "OK"){
					$.pnotify({title: '删除成功！',type: 'success'});
					if($tr){
						$tr.fadeOut("slow", function() {
							$(this).remove();
						});
					}
				}else{
					$.pnotify({title: '删除失败！',text:data.message,type: 'error'});
				}
			},
		error:function(request, textStatus, e){
			$.pnotify({title: '删除失败！',type: 'error'});
		}
	});
}

/**
 * 选择所有复选框
 */
function selectAll(){
	//全选事件绑定
	$('[ssr-select-all]').on('click',function(){
		var selectItem = $(this).attr('ssr-select-all');
		$('.'+selectItem+':checkbox').prop("checked",this.checked);
	});
	//子选事件绑定
	$('[ssr-select-all]').each(function(i){
		var selectItem = $(this).attr('ssr-select-all');
		var $item = $('.'+selectItem+':checkbox');
		$item.on("click",function(){
	 		$('[ssr-select-all="'+selectItem+'"]').prop('checked',$item.length==$item.filter(':checked').length);
		 });
	});
}
