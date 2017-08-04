<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
</head>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.6/summernote.css" rel="stylesheet">

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

.radioSpan {
  position: relative;
  border: 1px solid #95B8E7;
  background-color: #fff;
  vertical-align: middle;
  display: inline-block;
  overflow: hidden;
  white-space: nowrap;
  margin: 0;
  padding: 0;
  -moz-border-radius: 5px 5px 5px 5px;
  -webkit-border-radius: 5px 5px 5px 5px;
  border-radius: 5px 5px 5px 5px;
  display:block;
}
</style>
<script type="text/javascript">
function createEditor(){
	$('#summernote').summernote({
		height: 160,
		focus: true,
		lang: 'zh-CN'   	    		  
	});
}
$("#tabActivity").tabs({
	onSelect:function(title,index){
		if(index == 0){
			createEditor();
		}
	}
})

function preview(){  
    var simg = $('#stepUrl').val();
    wins = $("<div align='center' style='text-align:center; background:#90A4AE'><img id='simg'/></div>").dialog({
		title:'大图预览',
		width:'95%',
		height:'95%',
		maximizable:true,
		modal:true,
		onClose:function(){
	    		$(this).dialog("destroy");
	    },
	});
    $("#simg").attr("src",simg);  
}
</script>
<div align="center" >
	<div class="easyui-tabs" id="tabActivity" style="width:100%">
		 <div title="广告详情配置" style="padding:10px;text-align:center">
		 	<form id="taskDetailConfigForm" method="post">
				<table class="tableForm" style="width:99%;">
					<tr>
						<th>广告详情描述：</th>
						<td colspan="3" style="text-align:left">
							<div id="summernote" style="width:90%">
								${taskDetailVo.taskDesc}
							</div>
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
							<c:if test="${not empty taskDetailVo.stepUrl}"><a href="javascript:preview()">查看大图</a></c:if>
						</td>
					</tr>
					<tr>
						<th>动态审核字段：</th>
						<td colspan="3" style="text-align:left">
							<div id="editor" style="width:90%;height:220px;">
								${taskDetailVo.fields}
							</div>
						</td>
					</tr>
					<tr>
						<th>是否上传图片：</th>
						<td colspan="3" style="text-align:left">
							<span class="radioSpan" style="text-align:left">
								<input type="radio" name="isUploadRd" value="0"><label>否</label></input>
								<input type="radio" name="isUploadRd" value="1"><label>是</label></input>
							</span>
						</td>
					</tr>
					<tr>
						<th>上传图片数量：</th>
						<td colspan="3" style="text-align:left">
							<input name="imagesNum" value="${taskDetailVo.imagesNum}" class="easyui-numberbox"
								style="text-align:left"/>
						</td>
					</tr>
				</table>
				<input name="taskDesc" id="taskDesc" type="hidden" value=""/>
				<input name="fields" id="fields" type="hidden" value=""/>
				<input name="taskId" type="hidden" value="${taskDetailVo.taskId}"/>
				<input id="isUpload" name="isUpload" type="hidden" value="${taskDetailVo.isUpload}"/>
			</form>
		</div>
	</div> 
</div>
<script>
    var editor = ace.edit("editor");
    editor.setTheme("ace/theme/monokai");
    editor.getSession().setMode("ace/mode/javascript");
    //根据值，设置对应的radio选中
    $(":radio[name='isUploadRd'][value='" + $("#isUpload").val() + "']").prop("checked", "checked");
</script>
</html>