<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/themes/default/default.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/plugins/code/prettify.css" />
<script charset="utf-8" src="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/kindeditor.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/lang/zh_CN.js"></script>
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
	width:150px;
	height:100px;
	padding:2px;
	margin:2px;
}
.imgItem_td_1 img{
	width:150px;
}
.basic_td{
text-align:left!important;
}
</style>
<script type="text/javascript">
function createEditor(){
	 window.editor = KindEditor.create('#editor_id',{
        resizeType:1,      
        urlType:projectName, // 带有域名的绝对路径
        allowFileManager : false,
        allowImageUpload : true,
        uploadJson : projectName+'/sys/fileHandle/kindeditorUpload?bucket=dc-gift',
		 items : [
				'source', '|','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				'insertunorderedlist', '|', 'emoticons', 'image', 'link','fullscreen'], 
				
	});
}
$("#tabActivity").tabs({
	onSelect:function(title,index){
		if(index == 0){
			createEditor();
		}
	}
})
</script>
<div align="center" >
	<div class="easyui-tabs" id="tabActivity" style="width:100%">
		 <div title="活动配置" style="padding:10px;text-align:center">
		 	<form id="couponConfigForm" method="post">
				<table class="tableForm" style="width:75%;">
					<tr>
						<th>活动类型：</th>
						<td colspan="3" style="text-align:left">
							<select name="activityType" class="easyui-combobox" value="${couponActivityVo.activityType}" 
								data-options="required:true" style="width: 200px;" editable=false readonly=true>
								<option value="1">线上活动</option>
								<option value="2">线下活动</option>
								<option value="3">电商活动</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>优惠券类型：</th>
						<td colspan="3" style="text-align:left">
							<select class="easyui-combobox" value="" 
								data-options="required:true" style="width: 200px;" editable=false readonly=true>
									<option value="1">现金券</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>使用优惠券：</th>
						<td colspan="3" style="text-align:left">
							<input name="couponFkid" value="${couponActivityVo.couponFkid}" 
							data-options="required:true,editable:false, url:'<%=request.getContextPath()%>/sys/couponDetail/getList?isEnabled=1', valueField:'id', textField:'couponName', method:'GET',
							onSelect:function(record){
								$('couponName').val(record.couponName);
							}
							" class="easyui-combobox" style="width: 200px;" prompt="如为空，则先配置优惠券..." readonly=true/>
						</td>
					</tr>
					<tr>
						<th>活动标题：</th>
						<td colspan="3">
							<input name="title" value="${couponActivityVo.title}"
								class="easyui-validatebox textbox" data-options="required:true,validType:'length[1,10]'"
								style="width: 650px;" readonly=true/>
						</td>
					</tr>
					<tr>
						<th>活动摘要：</th>
						<td colspan="3">
							<input name="summary" value="${couponActivityVo.summary}" 
								class="easyui-textbox"  data-options="required:true,multiline:true" 
								style="width:650px;height:50px" readonly=true>
						</td>
					</tr>
					<tr>
						<th>活动开始时间：</th>
						<td>
							<input name="startTimeStr" value="${couponActivityVo.startTimeStr}"
								class="easyui-datetimebox" data-options="required:true"
								style="width: 200px;" readonly=true />
						</td>
						<th rowspan="4" style="width:50px;text-align:center">
							<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-large-picture',size:'large',iconAlign:'top'" 
								onclick="" id="uploadImg" style="margin-bottom:10px;">上传活动图</a>
						</th>
						<td rowspan="4">
							<div style="width:240px;height:120px">
								<c:if test="${not empty couponActivityVo.url}"><img id="img" src="${couponActivityVo.url}" style="width:240px;height:100%"/></c:if>
								<c:if test="${empty couponActivityVo.url }"><img id="img" src="" style="width:240px;height:100%"/></c:if>
								<input id="url" name="url" value="${couponActivityVo.url}" type="hidden"/> 
							</div>
						</td>
					</tr>
					<tr>
						<th>活动终止时间：</th>
						<td>
							<input name="endTimeStr" value="${couponActivityVo.endTimeStr}"
								class="easyui-datetimebox" data-options="required:true"
								style="width: 200px;" readonly=true/>
						</td>
					</tr>
					<tr>
						<th>活动地点：</th>
						<td>
							<input name="location" value="${couponActivityVo.location}"
								class="easyui-textbox" data-options="required:true"
								style="width: 200px;" readonly=true/>
							</td>
					</tr>
					<tr>
						<th>活动人数限制：</th>
						<td colspan="1">
							<input name="limitNum" value="${couponActivityVo.limitNum}"
								class="easyui-numberbox" data-options="required:true"
								style="width: 200px;" readonly=true/>
						</td>
					</tr>
					<tr>
						<th>活动地区：</th>
						<td>
							<input name="province" value="${couponActivityVo.province}"
								class="easyui-textbox" data-options="required:true"
								style="width: 200px;" readonly=true/>
							<input name="city" value="${couponActivityVo.city}"
								class="easyui-textbox" data-options="required:true"
								style="width: 200px;" readonly=true/>
							<input name="area" value="${couponActivityVo.area}"
								class="easyui-textbox" data-options="required:true"
								style="width: 200px;" readonly=true/>
							</td>
					</tr>
					<tr>
						<th>活动内容：</th>
						<td colspan="3">
							<textarea id="editor_id" style="width:650px;height:300px;" readonly=true>
									${couponActivityVo.content}
							</textarea>
						</td>
					</tr>
					<tr>
						<th>审核意见：</th>
						<td colspan="3" style="text-align:left;">
								${couponActivityVo.auditOpinion}
						</td>
					</tr>
				</table>
				<input name="id" type="hidden" value="${couponActivityVo.id}"/>
				<input name="content" id="content" type="hidden" value=""/>
			</form>
		</div>
	</div> 
</div>