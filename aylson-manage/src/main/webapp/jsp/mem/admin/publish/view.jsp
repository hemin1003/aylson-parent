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
			 items : ['fullscreen'], 
					
	});
});

</script>
<style>
<!--
th{
width:150px;
}
td{
width:200px;
}
-->
</style>
<div align="center">
	<form id="publishForm" method="post">
		<table class="tableForm">
			<tr>
				<th >标题：</th>
				<td ><input name="title" value="${publishVo.title}"
					class="easyui-textbox" data-options="required:true"
					style="width: 300px;" /></td>
				<th rowspan="2" style="width:50px;text-align:center"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-large-picture',size:'large',iconAlign:'top'" onclick="uploadImg('img','thumb','dc-publish')" id="uploadImg" style="margin-bottom:10px;">上传缩略图</a></th>
				<td rowspan="2" style="width: 300px;" >
					<div style="width:240px;height:120px">
					<c:if test="${not empty publishVo.thumb}"><img id="img" src="${publishVo.thumb}" style="width:240px;height:100%"/></c:if>
					<c:if test="${empty publishVo.thumb }"><img id="img" src="" style="width:240px;height:100%"/></c:if>
					<input id="thumb" name="thumb" value="${publishVo.thumb}" type="hidden"/>
					</div>
				</td>
			</tr>
			<tr>
				<th>摘要：</th>
				<td><input name="summary" value="${publishVo.summary}"
					class="easyui-textbox" data-options=""
					style="width:300px;height:80px;" multiline="true"/></td>
			</tr>
			<tr>
				<th>内容：</th>
				<td colspan="3"> 
					<textarea id="editor_id"  style="width:650px;height:300px;">
							${publishVo.content}
					</textarea>
				</td>
			</tr>
		</table>
		<input name="id" type="hidden" value="${publishVo.id}"/>
		<input name="type" type="hidden" value="${publishVo.type}"/>
		<input name="content" id="content" type="hidden" value=""/>
	</form>
</div>