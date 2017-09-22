<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<style>
.param_th{
text-align:center!important;
font-size:14px;
padding:5px;
}
.param_td_oper{
	text-align:center!important;
}
.imgItem_td_1{
	width:60px;
	height:100px;
	padding:2px;
	margin:2px;
}
.imgItem_td_1 img{
	width:60px;
}
.basic_td{
text-align:left!important;
}
</style>
<div align="center" >
	<div class="easyui-tabs" id="tabActivity" style="width:100%">
		 <div title="广告详情管理" style="padding:10px;text-align:center">
		 	<form id="advDetailConfigForm" method="post">
				<table class="tableForm" style="width:99%;">
					<tr>
						<th>标题：</th>
						<td colspan="3" style="text-align:left">
							<input name="advTitle" value="${advDetailVo.advTitle}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>配图url：</th>
						<td colspan="3" style="text-align:left">
							<input name="advImgs" value="${advDetailVo.advImgs}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>跳转链接url：</th>
						<td colspan="3" style="text-align:left">
							<input name="advUrl" value="${advDetailVo.advUrl}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>出现概率：</th>
						<td colspan="3" style="text-align:left">
							<input name="showRate" value="${advDetailVo.showRate}" data-options="required:true"
								class="easyui-numberbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>排序编号：</th>
						<td colspan="3" style="text-align:left">
							<input name="orderNum" value="${advDetailVo.orderNum}" data-options="required:true"
								class="easyui-numberbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>广告来源名：</th>
						<td colspan="3" style="text-align:left">
							<select id="advFkid" name="advFkid" class="easyui-combobox" value="${advDetailVo.advFkid}" 
								style="width:50%; " editable=false>
								<option value="15DF93E1E3623FAACD23">百度</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>新闻Tab标识名：</th>
						<td colspan="3" style="text-align:left">
							<select id="tagNameFlag" name="tagNameFlag" class="easyui-combobox" value="${advDetailVo.tagName}" 
								data-options=" 
								onSelect:function(data){
									$('#tagName').val(data.text);
								}" 
								style="width:50%; " editable=false>
								<option value="头条">头条</option>
								<option value="社会">社会</option>
								<option value="国内">国内</option>
								<option value="国际">国际</option>
								<option value="笑话">笑话</option>
								<option value="娱乐">娱乐</option>
								<option value="健康">健康</option>
								<option value="体育">体育</option>
								<option value="军事">军事</option>
								<option value="科技">科技</option>
								<option value="财经">财经</option>
								<option value="游戏">游戏</option>
								<option value="汽车">汽车</option>
								<option value="时尚">时尚</option>
							</select>
						</td>
					</tr>
				</table>
				<input name="tagName" id="tagName" value="${advDetailVo.tagName}" type="hidden"/>
				<input name="id" type="hidden" value="${advDetailVo.id}"/>
			</form>
		</div>
	</div> 
</div>
<script type="text/javascript">
$(function(){
	var select = document.getElementById("tagNameFlag");  
	var value = $('#tagName').val();
	var flag = 0;
	for(var i=0; i<select.options.length; i++){  
	    if(select.options[i].innerHTML == value){
	        select.options[i].selected = true;
	        flag = 1;
	        break;  
	    }
	}
	//默认首次值为头条
	if(flag == 0){
		$('#tagName').val('头条');
	}
});
</script>