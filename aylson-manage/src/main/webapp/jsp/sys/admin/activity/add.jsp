<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/themes/default/default.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/plugins/code/prettify.css" />
<script charset="utf-8" src="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/kindeditor.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script type="text/javascript">
var editor;
$(function(){
   window.editor = KindEditor.create('#editor_id',{
             resizeType:1,      
             urlType:projectName, // 带有域名的绝对路径
             allowFileManager : false,
             allowImageUpload : true,
             uploadJson : projectName+'/sys/fileHandle/kindeditorUpload?bucket=dc-publish',
             //uploadJson:projectName+'/resources/plugs/kindeditor/kindeditor-4.1.10/jsp/upload_json.jsp',
			 items : [
					'source', '|','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
					'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
					'insertunorderedlist', '|', 'emoticons', 'image', 'link','fullscreen'], 
					
	});
});

</script>
<div align="center">
	<form id="activityForm" method="post">
		<table class="tableForm">
			<tr>
				<th>活动主题：</th>
				<td colspan="3"><input name="actTheme" value="${activityVo.actTheme}"
					class="easyui-textbox" data-options="required:true"
					style="width: 650px;" /></td>
			</tr>
			<tr>
				<th>活动地点：</th>
				<td colspan="3"><input name="actPlace" value="${activityVo.actPlace}"
					class="easyui-textbox" data-options="required:true"
					style="width: 650px;" /></td>
			</tr>
			<tr>
				<th>活动人数：</th>
				<td><input name="actNums" value="${activityVo.actNums}"
					class="easyui-numberbox" data-options="required:true"
					style="width: 200px;" /></td>
				<th rowspan="4" style="width:50px;text-align:center">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-large-picture',size:'large',iconAlign:'top'" onclick="uploadImg('img','thumb','dc-publish')" id="uploadImg" style="margin-bottom:10px;">上传活动图</a>
				</th>
				<td rowspan="4">
					<div style="width:240px;height:120px">
					<c:if test="${not empty activityVo.thumb}"><img id="img" src="${activityVo.thumb}" style="width:240px;height:100%"/></c:if>
					<c:if test="${empty activityVo.thumb }"><img id="img" src="" style="width:240px;height:100%"/></c:if>
					<input id="thumb" name="thumb" value="${activityVo.thumb}" type="hidden"/> 
					</div>
				</td>
			</tr>
			<tr>
				<th>主办方电话：</th>
				<td colspan="1"><input name="sponsorPhone" value="${activityVo.sponsorPhone}"
					class="easyui-textbox" data-options="required:true"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>活动开始时间：</th>
				<td><input name="actBeginTimeStr" value="${activityVo.actBeginTimeStr}"
					class="easyui-datetimebox" data-options="required:true"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>活动结束时间：</th>
				<td><input name="actEndTimeStr" value="${activityVo.actEndTimeStr}"
					class="easyui-datetimebox" data-options="required:true"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>活动介绍：</th>
				<td colspan="3">
					<textarea id="editor_id"  style="width:650px;height:300px;">
							${activityVo.actIntroduce}
					</textarea>
				</td>
			</tr>
			
		</table>
		<input name="id" type="hidden" value="${activityVo.id}"/>
		<input name="actIntroduce" id="actIntroduce" type="hidden" value=""/>
	</form>
</div>