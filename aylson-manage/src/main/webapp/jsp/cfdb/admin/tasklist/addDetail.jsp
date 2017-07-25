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
		 <div title="任务详情配置" style="padding:10px;text-align:center">
		 	<form id="taskDetailConfigForm" method="post">
				<table class="tableForm" style="width:99%;">
					<tr>
						<th>任务详情：</th>
						<td colspan="3" style="text-align:left">
							<textarea id="editor_id" style="width:400px;height:240px;">
									${taskDetailVo.taskDesc}
							</textarea>
						</td>
					</tr>
					<tr>
						<th>
							<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-large-picture',size:'large',iconAlign:'top'" 
								onclick="uploadImg('img','stepUrl','yfax-test')" id="uploadImg" style="margin-bottom:10px;">上传详情步骤图</a>
						</th>
						<td colspan="3" style="text-align:left">
							<div style="width:240px;height:120px">
								<c:if test="${not empty taskDetailVo.stepUrl}"><img id="img" src="${taskDetailVo.stepUrl}" style="width:120px;height:120px"/></c:if>
								<c:if test="${empty taskDetailVo.stepUrl }"><img id="img" src="" style="width:120px;height:120px"/></c:if>
								<input id="stepUrl" name="stepUrl" value="${taskDetailVo.stepUrl}" type="hidden"/>
							</div>
						</td>
					</tr>
				</table>
				<input name="taskDesc" id="taskDesc" type="hidden" value=""/>
				<input name="taskId" type="hidden" value="${taskDetailVo.taskId}"/>
			</form>
		</div>
	</div> 
</div>