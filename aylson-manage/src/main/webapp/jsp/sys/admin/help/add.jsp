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
             uploadJson : projectName+'/sys/fileHandle/kindeditorUpload?bucket=dc-test',
             //uploadJson:projectName+'/resources/plugs/kindeditor/kindeditor-4.1.10/jsp/upload_json.jsp',
			 items : [
					'source', '|','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
					'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
					'insertunorderedlist', '|', 'emoticons', 'image', 'link','fullscreen'], 
					
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
	<form id="helpForm" method="post">
		<table class="tableForm">
			<tr>
				<th >序号：</th>
				<td >
					<input name="seq" value="${helpVo.seq}"
					class="easyui-numberbox" style="width: 300px;" />
				</td>
			</tr>
			<tr>
				<th >问题类型：</th>
				<td >
					<select name="type" class="easyui-combobox"  style="width: 300px;" editable=false>
						<option value="1">设计师</option>
						<option value="2">开拓者</option>
						<option value="3" selected>业主</option>
					</select>
				</td>
			</tr>
			<tr>
				<th >问题描述：</th>
				<td ><input name="question" value="${helpVo.question}"
					class="easyui-textbox" data-options="required:true"
					style="width: 300px;" /></td>
			</tr>
			<tr>
				<th>内容：</th>
				<td > 
					<textarea id="editor_id"  style="width:650px;height:300px;">
							${helpVo.answer}
					</textarea>
				</td>
			</tr>
		</table>
		<input name="id" type="hidden" value="${helpVo.id}"/>
		<input name="answer" id="answer" type="hidden" value=""/>
	</form>
</div>